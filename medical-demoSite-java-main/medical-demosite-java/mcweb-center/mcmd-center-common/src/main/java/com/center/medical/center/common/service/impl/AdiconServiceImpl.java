package com.center.medical.center.common.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.center.common.bean.dto.AdiconItemDto;
import com.center.medical.center.common.constant.CenterConstant;
import com.center.medical.center.common.dao.AdiconMapper;
import com.center.medical.center.common.service.AdiconService;
import com.center.medical.center.common.util.FastJsonUtil;
import com.center.medical.center.common.util.PropertyUtil;
import com.center.medical.center.common.util.WebServiceUtil;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.pacslis.bean.dto.LisDto;
import com.center.medical.service.AttachmentService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * QT体检者表(MdPeispatient)服务实现类
 *
 * @author ay
 * @since 2023-09-07 14:39:48
 */
@Slf4j
@Service("adiconService")
@RequiredArgsConstructor
public class AdiconServiceImpl extends ServiceImpl<AdiconMapper, Peispatient> implements AdiconService {

    public final static String ADICON_FIELD="AdiconBarcode";//艾迪康代码字段名称
    public final static String TCT = "B0000196";//TCT项目的艾迪康代码
    private final AttachmentService attachmentService;

    private final SystemConfig systemConfig;
    private final ISysConfigService iSysConfigService;



    /**
     * 获取艾迪康数据
     * @param patientcode
     * @return
     */
    @Override
    public List<LisDto> getAdicon(String patientcode,String loginid,String password) {
        if (StringUtils.isEmpty(loginid) || StringUtils.isEmpty(password)){
            return null;
        }
        //艾迪康从视图中获取体检号，视图中是8位短号，所以这里获取也要用8位短号
        WebServiceUtil webServiceUtil = new WebServiceUtil();
        String json= webServiceUtil.getJSONReportItemListByCustomerBarocde(patientcode.substring(patientcode.length()-8),loginid,password);
        //如果没有数据
//        log.error("打印一下艾迪康的返回数据数据:{}",json);
        if(WebServiceUtil.NODATA_MESSAGE.equals(json) ||  StringUtils.isEmpty(json)){
            return new ArrayList<>();
        }
        JSONObject obj= JSON.parseObject(json);
//        log.error("打印一下艾迪康的返回数据数据已转换:{}",obj);
        String errorMsg = obj.getString("message");
        if(StringUtils.isNotEmpty(errorMsg)){
            return new ArrayList<>();
        }
        List<LisDto> list = new ArrayList<>();
        Map<String,JSONObject> timeMap=new HashMap<String,JSONObject>();
        for(Entry<String,Object> entry:obj.entrySet()){
            Object value=entry.getValue();
            if(value==null){
                continue;
            }
            String str=value.toString();
            if(StringUtils.isEmpty(str)){
                continue;
            }
            JSONObject obj2=JSON.parseObject(str);
            JSONArray arr=obj2.getJSONArray("item");
            if(arr==null){
                continue;
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<String> itemCodes = new ArrayList<>();
            for(int i=0;i<arr.size();i++){
                JSONObject obj3=arr.getJSONObject(i);
                String itemCode=obj3.getString("ItemCode");
                //艾迪康的收费项目代码，同一项目不同体检者不一样,不同收费项目可能存在同一个检查项目，
                //但检查项目的结果不同，需要分别获取
                String adiconCode=obj3.getString(AdiconServiceImpl.ADICON_FIELD);
                //adicon收费项目代码+检查项目接口代码 判断重复
                String key=itemCode+"||"+adiconCode;
                Date reportDate= FastJsonUtil.getDate(obj3, "ReportDate", sdf, sdf2);
                JSONObject savedobj=timeMap.get(key);
                Date savedDate=savedobj==null?null:FastJsonUtil.getDate(savedobj, "ReportDate", sdf, sdf2);
                //用审核时间筛选最新的数据
                if(savedDate!=null&&reportDate!=null
                        &&reportDate.before(savedDate)){
                    continue;
                }
                timeMap.put(key, obj3);

                AdiconItemDto dto = obj3.toJavaObject(AdiconItemDto.class);
                //标本已退检的直接跳过
                if ("标本已退检".equals(dto.getResult())){
                    continue;
                }
                LisDto lis = new LisDto();
                lis.setAdiconCode(dto.getAdiconBarcode());
                lis.setExamDateTime(dto.getReceivedDate());
                lis.setReceiveDate(dto.getReceivedDate());
                lis.setAuditDate(dto.getReportDate());
                lis.setInspectName(dto.getTechnician());
                lis.setAuditName(dto.getCheckedBy());
                lis.setExamCode(dto.getItemCode());
                //巨细胞病毒艾迪康传过来的结果不是阴性,在这里处理一下
                if (dto.getItemNameCn().equals("巨细胞病毒(CMV)DNA测定") && dto.getResult().equals("<5.000E+02")){
                    lis.setExamItemValuesReport("阴性");
                } else {
                    lis.setExamItemValuesReport(dto.getResult());
                }

                //如果result是数字，result也是examItemValuesNumber，如果result不是数字，result也是examItemValuesShort
                if(NumberUtil.isNumber(dto.getResult())){
                    lis.setExamItemValuesNumber(Double.parseDouble(dto.getResult()));
                }else{
                    lis.setExamItemValuesShort(dto.getResult());
                }
                //bg极高  bd极低 g高 d低 z正常
                String status=("g".equals(dto.getResultHint())||"bg".equals(dto.getResultHint()))
                        ?"↑"
                        :("d".equals(dto.getResultHint())||"bd".equals(dto.getResultHint()))?"↓"
                        //长沙艾迪康resultHint可能是阳性或阴性
                        :"阳性".equals(dto.getResultHint())?"阳性"
                        :"";
                lis.setStatus(status);
                lis.setRefRange(dto.getResultReference());
                lis.setUnits(dto.getResultUnit());

                //获取艾迪康报告
                //去重,一个项目下可能有多个检查项目，同一个项目只查一次报告
                String reportId = dto.getStr2().replaceAll("[^\\d]", "");
                if (!itemCodes.contains(reportId)) {
                    itemCodes.add(reportId);

                    //霸州TCT图片转换有问题,换一个获取方式
                    byte[] base64 = null;
                    String property = PropertyUtil.getProperty("spring.profiles.active");
                    if (property.contains(CenterConstant.BZ) && lis.getExamCode().equals(TCT)){
                        base64 = webServiceUtil.getByteJpegReportById(reportId,loginid,password);
                    }else {
                        base64 = webServiceUtil.getByteReport(reportId,loginid,password);
                    }
                    String base64String = Base64Utils.encodeToString(base64);
                    lis.setAdiconPdfBase64(base64String);
                }

                list.add(lis);
            }
        }
        return list;
    }

    /**
     * 获取艾迪康结果
     * @param patientcode
     * @return
     */
    @Override
    public List<AdiconItemDto> getAdiconList(String patientcode,String loginid,String password) {
        if (StringUtils.isEmpty(loginid) || StringUtils.isEmpty(password)){
            return null;
        }
        //艾迪康从视图中获取体检号，视图中是8位短号，所以这里获取也要用8位短号
        WebServiceUtil webServiceUtil = new WebServiceUtil();
        String json= webServiceUtil.getJSONReportItemListByCustomerBarocde(patientcode.substring(patientcode.length()-8),loginid,password);
        log.info("艾迪康返回数据：{}",json);
        //如果没有数据
        if(WebServiceUtil.NODATA_MESSAGE.equals(json) ||  StringUtils.isEmpty(json)){
            return new ArrayList<>();
        }
        JSONObject obj= JSON.parseObject(json);
        String errorMsg = obj.getString("message");
        if(StringUtils.isNotEmpty(errorMsg)){
            throw new ServiceException(errorMsg);
        }
        List<AdiconItemDto> list = new ArrayList<>();
        Map<String,JSONObject> timeMap=new HashMap<String,JSONObject>();
        for(Entry<String,Object> entry:obj.entrySet()){
            Object value=entry.getValue();
            if(value==null){
                continue;
            }
            String str=value.toString();
            if(StringUtils.isEmpty(str)){
                continue;
            }
            JSONObject obj2=JSON.parseObject(str);
            JSONArray arr=obj2.getJSONArray("item");
            if(arr==null){
                continue;
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<String> itemCodes = new ArrayList<>();
            for(int i=0;i<arr.size();i++){
                JSONObject obj3=arr.getJSONObject(i);
                String itemCode=obj3.getString("ItemCode");
                //艾迪康的收费项目代码，同一项目不同体检者不一样,不同收费项目可能存在同一个检查项目，
                //但检查项目的结果不同，需要分别获取
                String adiconCode=obj3.getString(AdiconServiceImpl.ADICON_FIELD);
                //adicon收费项目代码+检查项目接口代码 判断重复
                String key=itemCode+"||"+adiconCode;
                Date reportDate= FastJsonUtil.getDate(obj3, "ReportDate", sdf, sdf2);
                JSONObject savedobj=timeMap.get(key);
                Date savedDate=savedobj==null?null:FastJsonUtil.getDate(savedobj, "ReportDate", sdf, sdf2);
                //用审核时间筛选最新的数据
                if(savedDate!=null&&reportDate!=null
                        &&reportDate.before(savedDate)){
                    continue;
                }
                timeMap.put(key, obj3);

                AdiconItemDto dto = obj3.toJavaObject(AdiconItemDto.class);
                list.add(dto);
            }
        }
        return list;
    }

    /**
     * 获取tap数据
     * @param patientcode
     * @return
     */
    @Override
    public List<LisDto> getTap(String patientcode) {
        String url = "http://XXX.XXX.XXX.XXX:9001/machine/readTxt/getTxt";
//        String url = "http://localhost:9001/machine/readTxt/getTxt";

        Map<String, Object> param = new HashMap<>();
        System.out.println("获取tap结果体检号" + patientcode);
        param.put("patientcode", patientcode);
        String s = HttpUtil.get(url, param);
        R responseEntity = JSONUtil.toBean(s, R.class);
        System.out.println("获取tap:" + responseEntity.getCode());
        System.out.println("获取tap结果:" + responseEntity.getData());
        if (200 != responseEntity.getCode()) {
            System.out.println("获取tap结果失败");
            return new ArrayList<>();
        }
        if (ObjectUtil.isEmpty(responseEntity.getData())){
            System.out.println("获取tap结果失败");
            return new ArrayList<>();
        }

        List<LisDto> lisDtos = ((List<cn.hutool.json.JSONObject>) responseEntity.getData()).stream().map(
                jsonObject -> jsonObject.toBean(LisDto.class)
        ).collect(Collectors.toList());
        return lisDtos;
    }
}

