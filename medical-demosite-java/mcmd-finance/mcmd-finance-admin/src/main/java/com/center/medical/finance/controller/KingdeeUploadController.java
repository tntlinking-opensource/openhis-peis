package com.center.medical.finance.controller;

import cn.hutool.json.JSONUtil;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.uuid.IdUtils;
import com.center.medical.finance.bean.dto.*;
import com.center.medical.finance.bean.dto.*;
import com.center.medical.finance.bean.param.UploadPeiDataParam;
import com.center.medical.finance.service.KdUploadService;
import com.center.medical.finance.utils.InvokeHelper;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 销售员回款(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-06 16:22:37
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售提成核算-销售员回款")
@RequestMapping("finance/financeInput")
public class KingdeeUploadController extends BaseController {
    /**
     * 服务对象
     */
    private final KdUploadService kdUploadService;
    private final ISysBranchService iSysBranchService;


    /**
     * 上传体检者收费数据
     *
     * @param baseParam 实体对象
     * @return 新增结果
     */
    @PostMapping("/uploadPeiData")
    @ApiOperation(value = "上传体检者收费数据", notes = "上传体检者收费数据")
    public R uploadPeiData(@RequestBody UploadPeiDataParam param) throws Exception {
        log.info("上传金蝶星空云定时任务开始！");
        //RYKG_t_Cust_TJZJB表
        List<KingdeeUploadDataDto> list = kdUploadService.getKingdeeUploadData(param);
        if (CollectionUtils.isEmpty(list)){
            log.info("没有需要上传金蝶星空云的数据！");
            return R.ok();
        }
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
            log.info("上传金蝶星空云数据：{}",kingdeeUploadDto);
            String result = InvokeHelper.Save(formid, JSONUtil.toJsonStr(kingdeeUploadDto));
            log.info("上传金蝶星空云返回结果：{}",result);
        }
        log.info("上传金蝶星空云定时任务结束！");
        return R.ok();
    }



    @PostMapping("/receivePayment")
    @ApiOperation(value = "上传应收单数据", notes = "上传应收单数据")
    public R receivePayment(@RequestBody UploadPeiDataParam baseParam) throws Exception {
        log.info("上传金蝶应收单数据数据开始！");
        List<ReceivePaymentDto> list = kdUploadService.getReceivePaymentData(baseParam);
        if (CollectionUtils.isEmpty(list)){
            log.info("没有需要上传应收单数据！");
            return R.ok();
        }
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
            log.info("上传金蝶应收单数据数据：{}",kingdeeUploadDto);
            String result = InvokeHelper.Save(formid, JSONUtil.toJsonStr(kingdeeUploadDto));
            log.info("上传金蝶应收单数据返回结果：{}",result);
        }
        log.info("上传金蝶应收单数据定时任务结束！");
        return R.ok();
    }
}

