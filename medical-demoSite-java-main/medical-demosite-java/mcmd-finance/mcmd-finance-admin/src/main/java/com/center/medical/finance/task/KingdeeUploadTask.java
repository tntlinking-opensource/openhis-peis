package com.center.medical.finance.task;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.utils.uuid.IdUtils;
import com.center.medical.finance.bean.dto.*;
import com.center.medical.finance.bean.dto.*;
import com.center.medical.finance.bean.param.UploadPeiDataParam;
import com.center.medical.finance.service.KdUploadService;
import com.center.medical.finance.utils.InvokeHelper;
import com.center.medical.system.service.ISysBranchService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;


import javax.annotation.Resource;


/**
 * 上传金蝶星空云定时任务
 */
@Component("kingdeeUploadTask")
public class KingdeeUploadTask {

    private static final Logger log = LoggerFactory.getLogger(KingdeeUploadTask.class);


    @Resource
    private KdUploadService kdUploadService;
    @Resource
    private ISysBranchService iSysBranchService;




    /**
     * 上传金蝶星空云定时任务
     *
     */
    public void start() throws Exception {
        log.info("上传金蝶星空云定时任务开始！");
        //有时候执行的时候不是00秒,查询30分钟前的数据
        Date endTime =  DateUtil.beginOfMinute(new Date());
        UploadPeiDataParam param = new UploadPeiDataParam(DateUtil.offsetMinute(endTime, -30),endTime);
        //上传体检者收费信息
        List<KingdeeUploadDataDto> list = kdUploadService.getKingdeeUploadData(param);
        if (CollectionUtil.isNotEmpty(list)){
            uploadTJZJB(list);
        }
        //上传应收单数据
        List<ReceivePaymentDto> ReceivePaymentlist = kdUploadService.getReceivePaymentData(param);
        if (CollectionUtil.isNotEmpty(list)){
            uploadReceiveNote(ReceivePaymentlist);
        }
        log.info("上传金蝶星空云定时任务结束！");
    }




    private void uploadReceiveNote(List<ReceivePaymentDto> list) throws Exception {
        log.info("上传金蝶应收单数据数据开始！");
        InvokeHelper.Login("640ec5b9a3c20d", "kingdee3", "123qwe!@#", 2052);
        String formid = "RYKG_SKDZJB";

        // 用Map存储相同的体检号
        Map<String, List<ReceivePaymentDto>> receivePaymentmap = new HashMap<>();
        for (ReceivePaymentDto dto : list) {
            String fid = dto.getFid();
            // 如果Map中已经包含该体检号，则将dto放入对应的List中
            receivePaymentmap.computeIfAbsent(fid, k -> new ArrayList<>()).add(dto);
        }

        Date now = new Date();
        String yyyyMMdd = DateFormatUtils.format(now, "yyyyMMdd");
        SysBranch branch = iSysBranchService.getDefaultBranch();
        for (Map.Entry<String, List<ReceivePaymentDto>> entry : receivePaymentmap.entrySet()) {
            List<kdReceivePaymentEntity> fEntityList = new ArrayList<>();
            for (ReceivePaymentDto dto : entry.getValue()) {
                kdReceivePaymentEntity entity = new kdReceivePaymentEntity(dto.getFid(),dto.getRemitter(),dto.getXsjl(),String.valueOf(dto.getMoneyamountpaid()),dto.getPaywayName(),branch.getCenterorgname());
                fEntityList.add(entity);
            }
            KdReceivePaymentDto.KingdeeUploadModel model = new KdReceivePaymentDto.KingdeeUploadModel(yyyyMMdd + IdUtils.simpleUUID(), fEntityList);
            KdReceivePaymentDto kingdeeUploadDto = new KdReceivePaymentDto(model);
//            log.info("上传金蝶应收单数据数据：{}",kingdeeUploadDto);
            String result = InvokeHelper.Save(formid, JSONUtil.toJsonStr(kingdeeUploadDto));
//            log.info("上传金蝶应收单数据返回结果：{}",result);
        }
        log.info("上传金蝶应收单数据数据结束！");
    }

    private void uploadTJZJB(List<KingdeeUploadDataDto> list) throws Exception {
        log.info("上传金蝶体检者信息开始！");
        InvokeHelper.Login("640ec5b9a3c20d", "kingdee3", "123qwe!@#", 2052);
        String formid = "RYKG_TJZJB";
        // 用Map存储相同的体检号
        Map<String, List<KingdeeUploadDataDto>> patientMap = new HashMap<>();
        for (KingdeeUploadDataDto dto : list) {
            String patientcode = dto.getPatientcode();
            // 如果Map中已经包含该体检号，则将dto放入对应的List中
            patientMap.computeIfAbsent(patientcode, k -> new ArrayList<>()).add(dto);
        }
        Date now = new Date();
        String yyyyMMdd = DateFormatUtils.format(now, "yyyyMMdd");

        for (Map.Entry<String, List<KingdeeUploadDataDto>> entry : patientMap.entrySet()) {
            List<KingdeeUploadFEntity> fEntityList = new ArrayList<>();
            for (KingdeeUploadDataDto dto : entry.getValue()) {
                KingdeeUploadFEntity fEntity = new KingdeeUploadFEntity(entry.getKey(),dto.getDateregister(),dto.getCenterorgname(),
                        dto.getFUsecodehiden(),dto.getOrgName(),dto.getExamfeeitemName(),dto.getCountNum(),dto.getMoneyamountpaid(),dto.getMoneyamountpaid(),
                        dto.getZkl(),dto.getDoctorapply(),dto.getIdPayway(),dto.getPayway(),dto.getIsjz());
                fEntityList.add(fEntity);
            }
            KingdeeUploadDto.KingdeeUploadModel model = new KingdeeUploadDto.KingdeeUploadModel(yyyyMMdd + IdUtils.simpleUUID(), fEntityList);
            KingdeeUploadDto kingdeeUploadDto = new KingdeeUploadDto(model);
//            log.info("上传金蝶星空云数据：{}",kingdeeUploadDto);
            String result = InvokeHelper.Save(formid, JSONUtil.toJsonStr(kingdeeUploadDto));
//            log.info("上传金蝶星空云返回结果：{}",result);
        }
        log.info("上传金蝶体检者信息结束！");
    }


    public static void main(String[] args) {
        Date endTime =  DateUtil.beginOfMinute(new  Date());//有时候执行的时候不是00秒
        System.out.println(new Date().toString());
        System.out.println(endTime.toString());
    }


}
