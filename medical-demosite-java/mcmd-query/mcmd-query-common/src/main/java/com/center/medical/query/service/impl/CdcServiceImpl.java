package com.center.medical.query.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.Render;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.query.bean.dto.CdcExportDto;
import com.center.medical.query.bean.param.CdcPageParam;
import com.center.medical.query.bean.vo.CdcPageVo;
import com.center.medical.query.dao.CdcMapper;
import com.center.medical.query.service.CdcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * CDC职业病直报数据查询(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
@Slf4j
@Service("cdcService")
@RequiredArgsConstructor
public class CdcServiceImpl extends ServiceImpl<CdcMapper, Peispatient> implements CdcService {

    private final CdcMapper cdcMapper;
    private final HarmMapper  harmMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CdcPageVo> getList(PageParam<CdcPageVo> page, CdcPageParam param) {
        IPage<CdcPageVo> ipage =  cdcMapper.getList(page, param);
        List<CdcPageVo> list = ipage.getRecords();
        if (CollectionUtils.isNotEmpty(list)){
            List<Harm> harms = harmMapper.selectList(new LambdaQueryWrapper<Harm>().eq(Harm::getIsDelete, 0));
            Map<String,String> harmNames=new HashMap<String, String>();
            for(Harm harm:harms) harmNames.put(harm.getId(), harm.getHarmName());
            //设置值
            for (CdcPageVo vo : list) {
                //接害因素
                vo.setJhys(getJhys(harmNames,vo.getJhys()));
                //校正电测听
                String sexPre = ("0".equals(vo.getIdSex()) ? "M" : "F") + "_List_";
                Integer age = vo.getAge();
                vo.setAirLeft500(getCorrectValue(vo.getAirLeft500(), sexPre + "500", Constants.DCT, age));
                vo.setAirLeft1000(getCorrectValue(vo.getAirLeft1000(), sexPre + "1k", Constants.DCT, age));
                vo.setAirLeft2000(getCorrectValue(vo.getAirLeft2000(), sexPre + "2k", Constants.DCT, age));
                vo.setAirLeft3000(getCorrectValue(vo.getAirLeft3000(), sexPre + "3k", Constants.DCT, age));
                vo.setAirLeft4000(getCorrectValue(vo.getAirLeft4000(), sexPre + "4k", Constants.DCT, age));
                vo.setAirLeft6000(getCorrectValue(vo.getAirLeft6000(), sexPre + "6k", Constants.DCT, age));
                vo.setAirRight500(getCorrectValue(vo.getAirRight500(), sexPre + "500", Constants.DCT, age));
                vo.setAirRight1000(getCorrectValue(vo.getAirRight1000(), sexPre + "1k", Constants.DCT, age));
                vo.setAirRight2000(getCorrectValue(vo.getAirRight2000(), sexPre + "2k", Constants.DCT, age));
                vo.setAirRight3000(getCorrectValue(vo.getAirRight3000(), sexPre + "3k", Constants.DCT, age));
                vo.setAirRight4000(getCorrectValue(vo.getAirRight4000(), sexPre + "4k", Constants.DCT, age));
                vo.setAirRight6000(getCorrectValue(vo.getAirRight6000(), sexPre + "6k", Constants.DCT, age));

            }
        }


        return ipage;
    }

    public static String getJhys(Map<String,String> harms,String jhys) {
        if(StringUtils.isEmpty(jhys))return "";
        Set<String> set=new HashSet<String>();
        for(String jh:jhys.toString().split(",")) {
            if(harms.get(jh)!=null) {
                set.add(harms.get(jh));
            }
        }
        return StringUtils.join(set, "、");
    }


    /**
     * 根据公式计算 校正值
     *
     * @param original
     * @param key
     * @param dctPro
     * @param age
     * @return
     */
    public Double getCorrectValue(Double original, String key, Map<String, String> dctPro, int age) {
        Double correct = null;
        if (original != null) {
            String value = dctPro.get(key);
            String[] valueArr = value.split(",");
            for (String itemStr : valueArr) {
                String[] itemArr = itemStr.split(":");
                String[] ageArr = itemArr[0].split("-");
                if (age >= Integer.parseInt(ageArr[0].trim())
                        && age <= Integer.parseInt(ageArr[1].trim())) {
                    correct = original - Double.parseDouble(itemArr[1]);
                }
            }
        }
        return correct == null ? null : correct;
    }

    /**
     * 导出自费收费汇总
     *
     * @param param
     * @return
     */
    @Override
    public List<CdcExportDto> getExportData(CdcPageParam param) {
        List<CdcExportDto> list =  cdcMapper.getExportData(param);
        List<Harm> harms = harmMapper.selectList(new LambdaQueryWrapper<Harm>().eq(Harm::getIsDelete, 0));
        Map<String,String> harmNames=new HashMap<String, String>();
        for(Harm harm:harms) harmNames.put(harm.getId(), harm.getHarmName());
        //设置值
        int i = 1;
        for (CdcExportDto vo : list) {
            vo.setRownum(i++);
            vo.setMedicaltype(Render.getMedicalType(vo.getMedicaltype()));
            vo.setIdSex(Render.getSex(vo.getIdSex()));
            //接害因素
            vo.setJhys(getJhys(harmNames,vo.getJhys()));
            //校正电测听
            String sexPre = ("0".equals(vo.getIdSex()) ? "M" : "F") + "_List_";
            Integer age = vo.getAge();
            vo.setAirLeft500(getCorrectValue(vo.getAirLeft500(), sexPre + "500", Constants.DCT, age));
            vo.setAirLeft1000(getCorrectValue(vo.getAirLeft1000(), sexPre + "1k", Constants.DCT, age));
            vo.setAirLeft2000(getCorrectValue(vo.getAirLeft2000(), sexPre + "2k", Constants.DCT, age));
            vo.setAirLeft3000(getCorrectValue(vo.getAirLeft3000(), sexPre + "3k", Constants.DCT, age));
            vo.setAirLeft4000(getCorrectValue(vo.getAirLeft4000(), sexPre + "4k", Constants.DCT, age));
            vo.setAirLeft6000(getCorrectValue(vo.getAirLeft6000(), sexPre + "6k", Constants.DCT, age));
            vo.setAirRight500(getCorrectValue(vo.getAirRight500(), sexPre + "500", Constants.DCT, age));
            vo.setAirRight1000(getCorrectValue(vo.getAirRight1000(), sexPre + "1k", Constants.DCT, age));
            vo.setAirRight2000(getCorrectValue(vo.getAirRight2000(), sexPre + "2k", Constants.DCT, age));
            vo.setAirRight3000(getCorrectValue(vo.getAirRight3000(), sexPre + "3k", Constants.DCT, age));
            vo.setAirRight4000(getCorrectValue(vo.getAirRight4000(), sexPre + "4k", Constants.DCT, age));
            vo.setAirRight6000(getCorrectValue(vo.getAirRight6000(), sexPre + "6k", Constants.DCT, age));

        }
        return list;


    }
}

