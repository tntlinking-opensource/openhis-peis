package com.center.medical.outside.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.RequestFlag;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.WebServiceClient;
import com.center.medical.common.utils.XmlUtils;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.outside.bean.model.*;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.outside.bean.dto.AgainReportBackDto;
import com.center.medical.outside.bean.dto.BoyingGetPatientInfoDto;
import com.center.medical.outside.bean.dto.BoyingWriteReportDto;
import com.center.medical.outside.bean.model.*;
import com.center.medical.outside.bean.param.BoyingGetPatientInfoParam;
import com.center.medical.outside.bean.param.BoyingWriteReportParam;
import com.center.medical.outside.constant.BoyingConstant;
import com.center.medical.outside.dao.BoyingBusinessMapper;
import com.center.medical.outside.service.BoyingBusinessService;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * 博英心电信息管理系统对接
 * @author xhp
 * @since 2025-05-23 16:24
 */
@Slf4j
@RequiredArgsConstructor
@Service("boyingBusinessService")
public class BoyingBusinessServiceImpl extends ServiceImpl<BoyingBusinessMapper, Attachment> implements BoyingBusinessService {
    private final ISysBranchService iSysBranchService;
    private final PeispatientMapper peispatientMapper;
    private final ISysConfigService iSysConfigService;

    /**
     * 心电信息管理系统发起查询申请单请求后，HIS根据传入的参数查询并返回符合条件的申请单。
     * @param param
     * @return
     */
    @Override
    public BoyingGetPatientInfoDto getPatientInfo(BoyingGetPatientInfoParam param){
        log.info("boying1 getPatientInfo:{}",param);
        //获取体检号
        String patientcode = StringUtils.firstNonEmpty(param.getPatCd(),param.getHisCd(),param.getOutpatientCd());

        List<BoyingGetPatientInfoData> data;
        //这是一个按体检号查询单条数据的接口，如果体检号为空，返回空。
        if(StrUtil.isEmpty(patientcode)){
            log.info("参数PatCd、HisCd、OutpatientCd同时为空,其中至少一个应为体检号。");
            data=null;
        }else{
            //判断体检号是否存在
            Peispatient peispatient;
            //判断orderId是否是8位
            if(patientcode.length()!=8){
                peispatient = peispatientMapper.getByPatientCode(patientcode);
            }else {
                peispatient = peispatientMapper.selectOne(new LambdaQueryWrapper<Peispatient> ()
                        .eq(Peispatient::getShortCode,patientcode)
                        .eq(Peispatient::getFPaused, 0)
                );
            }

            if(Objects.isNull(peispatient)){
                throw new ServiceException("博英心电图OrderID【"+patientcode+"】在体检系统中不存在");
            }
            patientcode=peispatient.getPatientcode();
            log.info("查询结果：{}",patientcode);
            /**
             * 查询数据
             * 所有中心数据库中共存在30条，同一个体检号有两个心电图科室项目且状态都正常的数据。不影响对接。查出list，取第一个。
             * 无值的字段设置为空串，因为最好不要省略xml节点，null会使对应字段的xml节点被自动省略。（因为我是直接把你返回给我返回系统，不确定系统有没有校验节点）
             * 只要回传了，就不能通过申请单接口查询到信息了。
             */
            data = getPatientInfoData(patientcode);
            log.info("查询结果：{}", data);
//            data=dataList.size()>0?dataList.get(0):null;
        }

        //返回结果
        BoyingGetPatientInfoDto dto=new BoyingGetPatientInfoDto();

        BoyingGetPatientInfoHead head=new BoyingGetPatientInfoHead();
        dto.setHead(head);
        head.setServiceName(BoyingConstant.SERVICE_NAME_GET_PATIENT_INFO);
        head.setSuccess(true);
        head.setMessCount(data==null?"0":data.size()+"");

        BoyingGetPatientInfoReturnParam returnParam=new BoyingGetPatientInfoReturnParam();
        head.setParam(returnParam);
        returnParam.setPatCd(param.getPatCd());
        returnParam.setHisCd(param.getHisCd());
        returnParam.setOutpatientCd(param.getOutpatientCd());

        BoyingGetPatientInfoMessage message=new BoyingGetPatientInfoMessage();
        message.setData(data);
        dto.setMessage(message);

        return dto;
    }

    private List<BoyingGetPatientInfoData> getPatientInfoData(String patientcode) {
        List<BoyingGetPatientInfoData> data = baseMapper.getPatientInfo(patientcode);;
        return data;
    }


    /**
     * 接口由医院HIS或集成平台提供服务，心电信息管理系统发起计费、归档报告请求，HIS需接受请求执行对应的操作。
     * @param param
     * @return
     */
    @Override
    public BoyingWriteReportDto writeReport(BoyingWriteReportParam param) {
        log.warn("博英心电图单项结果: {}", param);
        param.setReportDoc(param.getReportDoc().trim());
        BoyingWriteReportDto dto;
        //向线下推送结果
        try {
            String patientcode = param.getPatientID();
            if (StringUtils.isBlank(patientcode)){
                return createWriteReportDto(param, new ServiceException("博英心电图PatientID【"+patientcode+"】在体检系统中不存在"), false);
            }

            //1.写入缓存中
            // 以token为主键用户信息
            RedisUtil.set(BoyingConstant.RESULT_KEY + patientcode + param.getOrderId(), param, BoyingConstant.RESULT_EXPIRES_TIME);

            // 查询是否结果是否全部返回
            List<BoyingGetPatientInfoData> patientInfoData = getPatientInfoData(patientcode);
            log.info("博英心电图-缓存中的结果：patientInfoData.size()={}， patientInfoData={}", patientInfoData.size(), patientInfoData);
            if (patientInfoData.size() == 0) {
                return createWriteReportDto(param, new ServiceException("博英心电图PatientID【"+patientcode+"】在体检系统中不存在"), false);
            }
            Set<String> keys = RedisUtil.keys(BoyingConstant.RESULT_KEY + patientcode + "*");
            log.info("博英心电图-缓存中的结果：keys.size()={}， keys={}", keys.size(), keys);
            if (keys.size() < patientInfoData.size()){
                return createWriteReportDto(param, null, true);
            }

            String pdfUrls = "";
            String conclusion = "";
            //拼凑结果返回
            for (String key : keys) {
                BoyingWriteReportParam result = RedisUtil.get(key);
                for (BoyingGetPatientInfoData patientInfoDatum : patientInfoData) {
                    String itemIdDb = patientInfoDatum.getOrderID().split("-")[1];
                    String itemIdBy = result.getOrderId().split("-")[1];
                    if (itemIdDb.equals(itemIdBy)) {
                        if (StringUtils.isBlank(pdfUrls)){
                            pdfUrls += result.getReportUrl();
                            conclusion += (patientInfoDatum.getTestName().equals("心电图")? "常规心电图": patientInfoDatum.getTestName())+"："+result.getConclusion();
                        }else {
                            pdfUrls += "," + result.getReportUrl();
                            if (result.getConclusion().endsWith("\n")){
                                conclusion += (patientInfoDatum.getTestName().equals("心电图")? "常规心电图": patientInfoDatum.getTestName())+"："+result.getConclusion();
                            }else {
                                conclusion += "\n" + (patientInfoDatum.getTestName().equals("心电图")? "常规心电图": patientInfoDatum.getTestName())+"："+result.getConclusion();
                            }
                        }
                        break;
                    }
                }
            }
            if (StringUtils.isBlank(pdfUrls) || StringUtils.isBlank(conclusion)) {
                log.warn("博英心电图报告文件或者结论为空:【{}】, pdfUrls:{}, conclusion:{}", patientcode, pdfUrls, conclusion);
                return createWriteReportDto(param, new ServiceException("报告文件或者结论为空"), false);
            }
            param.setReportUrl(pdfUrls);
            param.setConclusion(conclusion);
            log.warn("博英心电图最终完整结果: {}", param);
            //2.判断所属分中心，并所属分中心发送一个线下请求
            //判断体检号是否存在
            Peispatient peispatient;
            //判断patientcode是否是8位
            if(patientcode.length()!=8){
                peispatient = peispatientMapper.getByPatientCode(patientcode);
            }else {
                peispatient = peispatientMapper.selectOne(new LambdaQueryWrapper<Peispatient> ()
                        .eq(Peispatient::getShortCode,patientcode)
                        .eq(Peispatient::getFPaused, 0)
                );
            }

            if(Objects.isNull(peispatient)){
                return createWriteReportDto(param, new ServiceException("博英心电图OrderID【"+patientcode+"】在体检系统中不存在"), false);
            }
            SysBranch branch = iSysBranchService.getByBranchId(peispatient.getHospitalcode());
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("params", JSONUtil.toJsonStr(param));
            paramMap.put("online", ZhongkangConfig.isOnline() ? 1 : 0);
            paramMap.put("branchId", branch.getBranchId());
            paramMap.put("bsFlagNum", RequestFlag.SENT_BOYING_ELE_RESULT_TO_OFFL);
            //向各中心下发请求
            Domain domain = iSysConfigService.getDomain();
            String post = HttpUtil.post(domain.getPlatformDomain()+"/open/api/request/sent", paramMap);
            log.info("请求结果：{}，{}", domain.getPlatformDomain()+"/open/api/request/sent", post);

            //3.返回结果
            dto = createWriteReportDto(param, null, true);
            //4.清楚缓存记录
            RedisUtil.delByKeys(keys);
        } catch (Exception e) {
            log.error("接收博英心电图报告异常：{}", e);
            dto = createWriteReportDto(param, e, false);
        }

        return dto;
    }


    /**
     * 心电信息管理系统发起计费、归档报告请求,失败
     * @param param
     * @param e
     * @return
     */
    @Override
    public BoyingWriteReportDto createWriteReportDto(BoyingWriteReportParam param,Exception e,boolean isSuccess){
        BoyingWriteReportDto dto=new BoyingWriteReportDto();

        BoyingWriteReportHead head=new BoyingWriteReportHead();
        dto.setHead(head);
        head.setServiceName(BoyingConstant.SERVICE_NAME_WRITE_REPORT);
        head.setSuccess(isSuccess);
        head.setMessCount("0");

        BoyingWriteReportReturnParam returnParam=new BoyingWriteReportReturnParam();
        head.setParam(returnParam);
        returnParam.setConclusion(param.getConclusion());
        returnParam.setExecStatus(param.getExecStatus());
        returnParam.setOrderId(param.getOrderId());
        returnParam.setReportUrl(param.getReportUrl());

        if(!isSuccess){
            log.error("boying writeReport failed",e);
            dto.setMessage(e.getMessage()==null?"":e.getMessage());
        }else{
            dto.setMessage("");
        }

        return dto;
    }

    /**
     * 心电信息管理系统发起计费、归档报告请求失败，重新发起请求
     * @param patientcode
     * @return
     */
    @Override
    public R<String> againReportBack(String patientcode) {
        log.warn("博英心电图重新获取报告开始:【{}】", patientcode);
        if (StringUtils.isBlank(patientcode)){
            return R.fail("博英心电图体检号【"+patientcode+"】不能为空");
        }

        Set<String> keys = RedisUtil.keys(BoyingConstant.RESULT_KEY + patientcode + "*");
        log.info("博英心电图redis中缓存的结果1:【{}】", keys);

        RedisUtil.delByKeys(keys);
        //获取新的结果
        List<BoyingGetPatientInfoData> patientInfoData = this.getPatientInfoData(patientcode);
        log.info("博英心电图patientInfoData:【{}】", patientInfoData);

        try {
            List<AgainReportBackDto> totalResult = new ArrayList<>();
            for (BoyingGetPatientInfoData item : patientInfoData) {
                String orderId = item.getOrderID();
                log.info("博英心电图orderId:【{}】", orderId);
                String url = BoyingConstant.SERVICE_URL;
                String soapAction = "http://tempuri.org/AgainReportBack";
                String methodName = "AgainReportBack";
                String paramName = "CID_Data";
                String paramXml = "<Request><OrderID>"+orderId+"</OrderID></Request>";
                String namespacePrefix = "tem";
                String namespaceURI = BoyingConstant.SERVICE_NAMESPACE;

                String result = WebServiceClient.callWebService(
                        url, soapAction, methodName, paramName, paramXml, namespacePrefix, namespaceURI
                );
                log.info("博英心电图返回结果:result={}", result);
                AgainReportBackDto data = XmlUtils.parseXmlToObject(result, AgainReportBackDto.class, true);
                if ("0".equals(data.getState())){
                    totalResult.add(data);
                }else {
                    return R.fail(data.getMsg()+"，稍后请重试");
                }

            }
            return R.ok(JSONUtil.toJsonStr(totalResult));
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    public static void main(String[] args) {
        //删掉线上redis中缓存的结果
//        DiyRedisUtil redisUtil = new DiyRedisUtil("123.249.111.34", 6679, "zkr@0825", 1);
////
//        System.out.println("Redis 连接测试: " + redisUtil.ping());
//
//
//        // 获取所有匹配的键
//        Set<String> keys = RedisUtil.keys( BoyingConstant.RESULT_KEY + "020232037544" + "*");
//        log.info("博英心电图redis中缓存的结果:【{}】", keys);
//        redisUtil.close();
        try {
            String url = BoyingConstant.SERVICE_URL;
            String soapAction = "http://tempuri.org/AgainReportBack";
            String methodName = "AgainReportBack";
            String paramName = "CID_Data";
            String paramXml = "<Request><OrderID>1983333592826155009-1942843742709092352</OrderID></Request>";
            String namespacePrefix = "tem";
            String namespaceURI = BoyingConstant.SERVICE_NAMESPACE;

            String result = WebServiceClient.callWebService(
                    url, soapAction, methodName, paramName, paramXml, namespacePrefix, namespaceURI
            );
            System.out.println("博英心电图返回结果:result="+result);
            AgainReportBackDto data = XmlUtils.parseXmlToObject(result, AgainReportBackDto.class, true);
            if ("0".equals(data.getState())){
                System.out.println(data.getMsg());
            }
            System.out.println(data.getMsg());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

//【[BoyingGetPatientInfoData(patientID=020232187741, orderID=1983333592826155009-276,  orderID=1983333592826155009-1942843129803837440, orderID=1983333592826155009-1942843742709092352

