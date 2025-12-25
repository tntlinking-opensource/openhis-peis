package com.center.medical.center.qingdao.profession.utils;


import com.alibaba.fastjson.JSON;
import com.center.medical.center.qingdao.profession.entity.dto.WfjkDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RequestUtils {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);


    public static boolean checkSign(WfjkDto reqBody, String appSecret){
        Map<String,Object> params=new TreeMap<>();
        params.put("appId", reqBody.getAppId());
        params.put("once", reqBody.getOnce());
        params.put("content", reqBody.getContent());
        try {
            String sign = HmacShaUtil.Sign256(params,appSecret);
            if(sign.equals(reqBody.getSign())){
                return true;
            }else{
                logger.error("验签失败，请检查加密密钥...{}",reqBody);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //设置签名
    public static void setSign(WfjkDto reqBody,String appSecret) throws Exception {
        String once = UUID.randomUUID().toString(); // 使用标准 UUID 方法
        Map<String,Object> params=new TreeMap<>();
        params.put("appId",reqBody.getAppId());
        params.put("once",once);
        params.put("content",reqBody.getContent());
        reqBody.setOnce(once);
        String sign = HmacShaUtil.Sign256(params,appSecret);
        reqBody.setSign(sign);
    }

    //进行加密
    public static String encode(String AppId, Object obj,String pkey,String IV){
        ObjectMapper objectMapper=new ObjectMapper();
        String content = null;
        try {
            content = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String aesEncryptStr = AESUtils.aesEncryptStr(content, pkey,IV);
        return aesEncryptStr;

    }

    //对参数进行解密返回String
    public static String decode(String AppId,String content,String pkey,String IV){
        try {
            String aesDecodeStr = AESUtils.aesDecodeStr(content, pkey,IV);
            return aesDecodeStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String appId="rcssdrmyy_org";
        String IV="8834567890888876";
        String pkey = "03uNuCWJ3Dh7x6k8";
        String appSecret="“z68iw5zGvsawYc+AJbMgMw5xorKjMSp";
        try {
            Map<String, Object> resultMap = new HashMap<>();
            String healthJsonString = "[{\"documentId\": \"200618111\",\"phyExamName\": \"淄川济民医院\",\"phyExamCode\": \"370302040\",\"phyExamAddressName\": \"山东省淄博市淄川区松龄路街道\",\"phyExamAddressCode\": \"370302002\",\"physicalTypes\": \"1\",\"examDate\": \"20230226\",\"examNum\": \"23022660001\",\"workerName\": \"冯青龄\",\"idCardTypeCode\": \"01\",\"idCard\": \"370302196610186317\",\"genderCode\": \"1\",\"birthDate\": \"19661018\",\"age\": \"56\",\"birthplace\": null,\"nation\": \"汉族\",\"nationCode\": \"1\",\"maritalStatusName\": \"已婚\",\"maritalStatusCode\": \"20\",\"edBgName\": null,\"edBgCode\": null,\"workerTelphone\": \"13583381188\",\"information\": null,\"emergencyContact\": null,\"contactInformation\": null,\"homeAddress\": \"山东省淄博市淄川区岭子镇龙泉村224号\",\"smokeing\": null,\"smokeNumber\": \"20\",\"smokeYear\": \"30\",\"drinking\": null,\"drinkNumber\": null,\"drinkYear\": null,\"menstrMenarche\": null,\"menstrPeriod\": null,\"menstrCycle\": null,\"menstrMenopausalAge\": null,\"birthChildNumber\": null,\"birthAbortionNumber\": null,\"birthPrematureNumber\": null,\"birthStillbirthNumber\": null,\"birthAbnormalNumber\": null,\"pastHistory\": \"1\",\"otherPastHistory\": null,\"familyHistory\": \"1\",\"otherFamilyHistory\": null,\"marriageDate\": null,\"spouseExposureRadiation\": null,\"spouseOccu\": null,\"lifeHistory\": null,\"familyHistoryRadiation\": null,\"other\": null,\"examTypeCode\": \"01\",\"factorCode\": \"6008\",\"factorOther\": null,\"enterpriseName\": null,\"creditCode\": \"91370302MA948DLL1B\",\"economicTypeCode\": null,\"industryCategoryCode\": \"4790\",\"businessScaleCode\": \"温州东大矿建工程有限公司淄博分公司\",\"addressName\": \"山东省淄博市淄川区岭子镇\",\"addressCode\": \"370302106\",\"addressDetail\": \"山东省淄博市淄川区岭子镇龙泉村村委东1000米\",\"addressZipCode\": null,\"enterpriseContact\": \"孙飞\",\"contactTelphone\": \"68715858\",\"allName\": \"山东省淄博市淄川区岭子镇\",\"enterpriseNameEmployer\": \"温州东大矿建工程有限公司淄博分公司\",\"creditCodeEmployer\": \"91370302MA948DLL1B\",\"econocTypeCodeEmoyer\": \"010703\",\"indtryCatryCodeEmoyer\": \"4790\",\"busssScaleCodeEmoyer\": \"04\",\"addressCodeEmployer\": \"370302106\",\"allNameEmployer\": \"山东省淄博市淄川区岭子镇\",\"writePerson\": \"杨志娟\",\"writePersonTelphone\": \"0533-5285977\",\"writeDate\": \"20230226\",\"reportOrganCreditCode\": \"淄川济民医院\",\"workType\": null,\"workTypeCode\": \"46-537\",\"otherWorkType\": null,\"jcType\": \"01\",\"harmStartDate\": null,\"harmAgeYear\": \"0\",\"harmAgeMonth\": \"0\",\"isReview\": \"0\",\"reportPerson\": \"杨志娟\",\"reportPersonTel\": \"0533-5285977\",\"reportDate\": \"20230226\",\"contactFactorCode\": \"1031,3008\",\"operateType\": \"Add\",\"deptId\": \"105\"}]";
            String conJsonString="[{\"documentId\": \"200618111\",\"phyExamName\": \"淄川济民医院\",\"phyExamCode\": \"370302040\",\"examNum\": \"23022660001\",\"itamCode\": \"1\",\"itamName\": \"石灰石粉尘\",\"examConclusionCode\": \"01\",\"yszybCode\": \"无\",\"zyjjzName\": \"无\",\"qtjbName\": \"无\",\"deptId\": \"105\"}]";
            String itemJsonString="[{\"documentId\": \"200618111\",\"phyExamName\": \"内蒙古自治区人民医院\",\"phyExamCode\": \"370302040\",\"examNum\": \"2006181111\",\"examItemPname\": \"电生理\",\"examItemPcode\": \"3060000\",\"examItemName\": \"12 导联心电图\",\"examItemCode\": \"3060101\",\"hsExamItemName\": \"常规心电图\",\"hsExamItemCode\": \"10404\",\"examResultType\": \"02\",\"examResult\": \"正常心电图  窦性心律\",\"examItemUnitCode\": \"%\",\"referenceRangeMin\": \"无\",\"referenceRangeMax\": \"无\",\"abnormal\": \"0\",\"deptId\": \"105\"}]";
            resultMap.put("healthPhysicalReports", JSON.parse(healthJsonString).toString());
            resultMap.put("examConclusionLists", JSON.parse(conJsonString).toString());
            resultMap.put("examItemResultLists", JSON.parse(itemJsonString).toString());
            String content=RequestUtils.encode(appId, resultMap, pkey, IV);
            logger.info("入参对象content={}",content);
            Map<String,Object> params=new TreeMap<>();
            params.put("appId","rcssdrmyy_org");
            params.put("once", "202309120914");
            params.put("content", content);
            String sign = HmacShaUtil.Sign256(params,appSecret);
            logger.info("签验串sign={}",sign);
            logger.info("解密={}", RequestUtils.decode("",content, pkey,IV));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
