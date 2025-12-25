package com.center.medical.center.qingdao.profession.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.center.medical.center.qingdao.profession.constant.Constants;
import com.center.medical.center.qingdao.profession.entity.dto.*;
import com.center.medical.center.qingdao.profession.entity.persistent.Peispatient;
import com.center.medical.center.qingdao.profession.entity.properties.QjkProperties;
import com.center.medical.center.qingdao.profession.entity.properties.WfjkProperties;
import com.center.medical.center.qingdao.profession.exception.ErrorRuntimeException;
import com.center.medical.center.qingdao.profession.mapper.QjkMapper;
import com.center.medical.center.qingdao.profession.mapper.WfjkMapper;
import com.center.medical.center.qingdao.profession.repository.PeisStateExtRepository;
import com.center.medical.center.qingdao.profession.repository.PeispatientRepository;
import com.center.medical.center.qingdao.profession.service.LogService;
import com.center.medical.center.qingdao.profession.service.OccupationalHealthArchivesService;
import com.center.medical.center.qingdao.profession.service.QjkService;
import com.center.medical.center.qingdao.profession.service.WfjkService;
import com.center.medical.center.qingdao.profession.utils.*;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import springfox.documentation.swagger2.mappers.ModelMapper;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * QT体检者表(Peispatient)服务实现类
 *
 * @author ay
 * @since 2024-05-07 15:54:16
 */
@Service
@AllArgsConstructor
@Slf4j
public class WfjkServiceImpl implements WfjkService {

    private final WfjkMapper wfjkMapper;
    private final RequestService requestService;
    private WfjkProperties wfjkProperties;
    private final PeisStateExtRepository peisStateExtRepository;
    private final OccupationalHealthArchivesService occupationArchivesService;
    private final LogService logService;
    private final PeispatientRepository peispatientRepository;



    /**
     * 上传
     * @param startDate
     * @param endDate
     */
    @Override
    public void upload(Date startDate, Date endDate) throws Exception {
        log.info("开始上传潍坊疾控!");
        if ("true".equals(wfjkProperties.getOpen())){
            //查询需要上传的数据
            List<String> patientCodes = peisStateExtRepository.findByCreateDateRangle(startDate, endDate);
            if (patientCodes.size() < 1) {
                log.info("已全部上传");
            }
            for (String patientCode : patientCodes) {
                uploadData(patientCode);
            }
        }
        log.info("上传潍坊疾控结束!");
    }

    /**
     * 上传数据
     * @param patientCode
     */
    private void uploadData(String patientCode) {
        log.error("上传潍坊体检号数据：{}", patientCode);

        //上传数据
        String appId = wfjkProperties.getAppId();
        String IV = wfjkProperties.getIV();
        String pkey = wfjkProperties.getPkey();
        String appSecret = wfjkProperties.getAppSecret();
        String once = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        try {
            RDataDTO data = occupationArchivesService.getUploadData(patientCode);
            WfjkContentDto dto = setData(data);
            Map<String, Object> resultMap = new HashMap<>();

            resultMap.put("healthPhysicalReports", dto.getHealthPhysicalReports());
            resultMap.put("examConclusionLists", dto.getExamConclusionLists());
            resultMap.put("examItemResultLists", dto.getExamItemResultLists());
            log.error("入参对象resultMap={}",JSON.toJSONString(resultMap));
            String content=RequestUtils.encode(appId, resultMap, pkey, IV);
//            log.error("入参对象content={}",content);
            Map<String,Object> params=new TreeMap<>();
            params.put("appId",appId);
            params.put("once", once);
            params.put("content", content);
            String sign = HmacShaUtil.Sign256(params,appSecret);
//            log.error("签验串sign={}",sign);
            params.put("sign", sign);
//            params.put("seq", patientCode);
            //如果用体检号的话,没法重复上传，先用时间戳吧
            params.put("seq", System.currentTimeMillis());
            params.put("seqEndFlag", true);
//            log.error("入上传潍坊疾控params={}",JSON.toJSONString(params));
            log.error("上传潍坊疾控url：｛｝",wfjkProperties.getUrl());
            Result result = requestService.requestPostJson( wfjkProperties.getUrl() + "/openapi/healthreport/dataSync", params);
            log.error("上传报告返回值：{}", result);
            logService.saveLog(patientCode, result.getCode(), result.getMessage());
        } catch (Exception e) {
            log.error("上传潍坊疾控失败");
            log.error(patientCode + ":" + e.getMessage(), e);
            logService.saveLogError(patientCode, e.getMessage());
        }
    }

    /**
     * 设置数据
     * @param data
     * @return
     */
    private WfjkContentDto setData(RDataDTO data) {
        //组装数据
        WfjkContentDto dto = new WfjkContentDto();
        HealthPhysicalReportDto healthPhysicalReportDto = new HealthPhysicalReportDto();
        RDataDTO.DataDTO dataDTO = data.getData().get(0);
        BeanUtils.copyProperties(dataDTO, healthPhysicalReportDto);
        healthPhysicalReportDto.setReportOrganCreditCode(dataDTO.getEnterpriseNameEmployer());
        healthPhysicalReportDto.setDocumentId(dataDTO.getDocumentid());
        healthPhysicalReportDto.setPhyExamName(dataDTO.getPhyexamname());
        healthPhysicalReportDto.setPhyExamCode(dataDTO.getPhyexamcode());
        healthPhysicalReportDto.setPhyExamAddressName(dataDTO.getPhyexamaddressName());
        healthPhysicalReportDto.setPhyExamAddressCode(dataDTO.getPhyexamaddressCode());
        healthPhysicalReportDto.setPhysicalTypes(dataDTO.getPhysicaltypes());
        healthPhysicalReportDto.setFactorOther(dataDTO.getFactorOther());
        healthPhysicalReportDto.setHarmStartDate(dataDTO.getHarmStartDate());
        healthPhysicalReportDto.setContactFactorOther(dataDTO.getContactFactorOther());
        healthPhysicalReportDto.setOperateType(dataDTO.getOperatetype());
        healthPhysicalReportDto.setDeptId(wfjkProperties.getDeptId());

        String workTypeCode = healthPhysicalReportDto.getWorkTypeCode();
        //00-14，00-33，99-9999，这三个其他条目，都要填其他工种名称,其他的不需要上传
        if ("00-14".equals(workTypeCode) || "00-33".equals(workTypeCode) || "99-9999".equals(workTypeCode)){
            healthPhysicalReportDto.setOtherWorkType(healthPhysicalReportDto.getWorkType());
        }
        //潍坊有一些不合法的工种，在这转换一下
        if (Constants.WEIFANG_WORK_TYPE.contains(workTypeCode)){
            healthPhysicalReportDto.setOtherWorkType(healthPhysicalReportDto.getWorkType());
            healthPhysicalReportDto.setWorkTypeCode("00-33");
            healthPhysicalReportDto.setWorkType("工厂其他工种");
        }
        dto.setHealthPhysicalReports(Arrays.asList(healthPhysicalReportDto));

        List<ExamConclusionDto> examConclusionLists = new ArrayList<>();
        for (RDataDTO.DataDTO.EXAMCONCLUSIONLISTDTO source : data.getData().get(0).getExamConclusionList()) {
            ExamConclusionDto target = new ExamConclusionDto();
            target.setDocumentId(source.getDocumentid());
            target.setPhyExamName(source.getPhyexamname());
            target.setPhyExamCode(source.getPhyexamcode());
            target.setExamNum(source.getExamNum());
            target.setItamCode(source.getItamCode());
            target.setItamName(source.getItamName());
            target.setExamConclusionCode(source.getExamConclusionCode());
            target.setYszybCode(source.getYszybCode());
            target.setZyjjzName(source.getZyjjzName());
            target.setQtjbName(source.getQtjbName());
            target.setDeptId(wfjkProperties.getDeptId());
            examConclusionLists.add(target);
        }
        dto.setExamConclusionLists(examConclusionLists);

        List<ExamItemResultDto> examItemResultDtos = new ArrayList<>();
        for (RDataDTO.DataDTO.EXAMITEMRESULTLISTDTO source : data.getData().get(0).getExamItemResultList()) {
            ExamItemResultDto target = new ExamItemResultDto();
            target.setDocumentId(source.getDocumentid());
            target.setPhyExamName(source.getPhyexamname());
            target.setPhyExamCode(source.getPhyexamcode());
            target.setExamNum(source.getExamNum());
            target.setExamItemPname(source.getExamItemPname());
            target.setExamItemPcode(source.getExamItemPcode());
            //省职业健康系统检查子项目名称,下面两个可能有问题
            target.setExamItemName(source.getExamItemName());
            target.setExamItemCode(source.getExamItemCode());
            target.setExamResultType(source.getExamResultType());
            target.setExamResult(source.getExamResult());
            target.setExamItemUnitCode(source.getExamItemUnitCode());
            target.setReferenceRangeMin(source.getReferenceRangeMin());
            target.setReferenceRangeMax(source.getReferenceRangeMax());
            target.setAbnormal(source.getAbnormal());
            target.setDeptId(wfjkProperties.getDeptId());
            examItemResultDtos.add(target);
        }
        dto.setExamItemResultLists(examItemResultDtos);
//        log.info("组装数据：{}",JSON.toJSONString(dto));
        return dto;
    }



    public static void main(String[] args) throws Exception {
//        String appId="wfzkdyjktjzx_org";
//        String IV="8834567890888876";
//        String pkey = "O3uNuCWJ3Dh7x6k8";
//        String appSecret="xJPEn8eUx4TIkMiUxKvPnjzIpsOQz5/P";
//        try {
//            Map<String, Object> resultMap = new HashMap<>();
//            String healthJsonString = "[{\"documentId\": \"200618111\",\"phyExamName\": \"淄川济民医院\",\"phyExamCode\": \"370302040\",\"phyExamAddressName\": \"山东省淄博市淄川区松龄路街道\",\"phyExamAddressCode\": \"370302002\",\"physicalTypes\": \"1\",\"examDate\": \"20230226\",\"examNum\": \"23022660001\",\"workerName\": \"冯青龄\",\"idCardTypeCode\": \"01\",\"idCard\": \"370302196610186317\",\"genderCode\": \"1\",\"birthDate\": \"19661018\",\"age\": \"56\",\"birthplace\": null,\"nation\": \"汉族\",\"nationCode\": \"1\",\"maritalStatusName\": \"已婚\",\"maritalStatusCode\": \"20\",\"edBgName\": null,\"edBgCode\": null,\"workerTelphone\": \"13583381188\",\"information\": null,\"emergencyContact\": null,\"contactInformation\": null,\"homeAddress\": \"山东省淄博市淄川区岭子镇龙泉村224号\",\"smokeing\": null,\"smokeNumber\": \"20\",\"smokeYear\": \"30\",\"drinking\": null,\"drinkNumber\": null,\"drinkYear\": null,\"menstrMenarche\": null,\"menstrPeriod\": null,\"menstrCycle\": null,\"menstrMenopausalAge\": null,\"birthChildNumber\": null,\"birthAbortionNumber\": null,\"birthPrematureNumber\": null,\"birthStillbirthNumber\": null,\"birthAbnormalNumber\": null,\"pastHistory\": \"1\",\"otherPastHistory\": null,\"familyHistory\": \"1\",\"otherFamilyHistory\": null,\"marriageDate\": null,\"spouseExposureRadiation\": null,\"spouseOccu\": null,\"lifeHistory\": null,\"familyHistoryRadiation\": null,\"other\": null,\"examTypeCode\": \"01\",\"factorCode\": \"6008\",\"factorOther\": null,\"enterpriseName\": null,\"creditCode\": \"91370302MA948DLL1B\",\"economicTypeCode\": null,\"industryCategoryCode\": \"4790\",\"businessScaleCode\": \"温州东大矿建工程有限公司淄博分公司\",\"addressName\": \"山东省淄博市淄川区岭子镇\",\"addressCode\": \"370302106\",\"addressDetail\": \"山东省淄博市淄川区岭子镇龙泉村村委东1000米\",\"addressZipCode\": null,\"enterpriseContact\": \"孙飞\",\"contactTelphone\": \"68715858\",\"allName\": \"山东省淄博市淄川区岭子镇\",\"enterpriseNameEmployer\": \"温州东大矿建工程有限公司淄博分公司\",\"creditCodeEmployer\": \"91370302MA948DLL1B\",\"econocTypeCodeEmoyer\": \"010703\",\"indtryCatryCodeEmoyer\": \"4790\",\"busssScaleCodeEmoyer\": \"04\",\"addressCodeEmployer\": \"370302106\",\"allNameEmployer\": \"山东省淄博市淄川区岭子镇\",\"writePerson\": \"杨志娟\",\"writePersonTelphone\": \"0533-5285977\",\"writeDate\": \"20230226\",\"reportOrganCreditCode\": \"淄川济民医院\",\"workType\": null,\"workTypeCode\": \"46-537\",\"otherWorkType\": null,\"jcType\": \"01\",\"harmStartDate\": null,\"harmAgeYear\": \"0\",\"harmAgeMonth\": \"0\",\"isReview\": \"0\",\"reportPerson\": \"杨志娟\",\"reportPersonTel\": \"0533-5285977\",\"reportDate\": \"20230226\",\"contactFactorCode\": \"1031,3008\",\"operateType\": \"Add\",\"deptId\": \"105\"}]";
//            String conJsonString="[{\"documentId\": \"200618111\",\"phyExamName\": \"淄川济民医院\",\"phyExamCode\": \"370302040\",\"examNum\": \"23022660001\",\"itamCode\": \"1\",\"itamName\": \"石灰石粉尘\",\"examConclusionCode\": \"01\",\"yszybCode\": \"无\",\"zyjjzName\": \"无\",\"qtjbName\": \"无\",\"deptId\": \"105\"}]";
//            String itemJsonString="[{\"documentId\": \"200618111\",\"phyExamName\": \"内蒙古自治区人民医院\",\"phyExamCode\": \"370302040\",\"examNum\": \"2006181111\",\"examItemPname\": \"电生理\",\"examItemPcode\": \"3060000\",\"examItemName\": \"12 导联心电图\",\"examItemCode\": \"3060101\",\"hsExamItemName\": \"常规心电图\",\"hsExamItemCode\": \"10404\",\"examResultType\": \"02\",\"examResult\": \"正常心电图  窦性心律\",\"examItemUnitCode\": \"%\",\"referenceRangeMin\": \"无\",\"referenceRangeMax\": \"无\",\"abnormal\": \"0\",\"deptId\": \"105\"}]";
//            resultMap.put("healthPhysicalReports", JSON.parse(healthJsonString));
//            resultMap.put("examConclusionLists", JSON.parse(conJsonString));
//            resultMap.put("examItemResultLists", JSON.parse(itemJsonString));
//            String content=RequestUtils.encode(appId, resultMap, pkey, IV);
//            log.info("入参对象content={}",content);
//            Map<String,Object> params=new TreeMap<>();
//            params.put("appId","pro_qdspt");
//            params.put("once", "202309120914");
//            params.put("content", content);
//            String sign = HmacShaUtil.Sign256(params,appSecret);
//            log.info("签验串sign={}",sign);
//            params.put("sign", sign);
//            params.put("seq", 1);
//            params.put("seqEndFlag", true);
//
////            Result result = requestService.requestPostJson("http://103.239.152.231:18063/data-api/openapi/healthreport/dataSync", params);
////            log.info("上传报告返回值：{}", result);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        boolean workTypeCode = Constants.WEIFANG_WORK_TYPE.contains("47-066");
        log.info("workTypeCode={}",workTypeCode);

    }


    /**
     * 重新上传
     * @param patientcodes
     */
    @Override
    public void sendMulti(List<String> patientcodes) {
        log.info("开始重新上传潍坊疾控!");
        if(patientcodes!=null&&patientcodes.size()>0){
            Thread thread=new Thread(()->{
                for(String patientcode:patientcodes){
                    uploadData(patientcode);
                }
            });
            thread.start();
        }
        log.info("开始重新上传潍坊疾控结束!");
    }
}

