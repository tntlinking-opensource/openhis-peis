package com.center.medical.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.finance.bean.dto.KingdeeUploadDataDto;
import com.center.medical.finance.bean.dto.ReceivePaymentDto;
import com.center.medical.finance.bean.param.UploadPeiDataParam;
import com.center.medical.sellcrm.bean.model.Sellcustomer;

import java.util.List;

/**
 * 金蝶上传
 * @author xhp
 * @since 2023-05-18 9:24
 */
public interface KdUploadService extends IService<Sellcustomer> {
    /**
     * 上传个检
     * @param startTime
     * @param endTime
     * @return
     */
    String taskAboutUpdateT(String startTime,String endTime);

    /**
     * 上传团检
     * @param startTime
     * @param endTime
     * @return
     */
    String taskAboutUpdateG(String startTime,String endTime);

    /**
     *上传个检结算
     * @param startTime
     * @param endTime
     * @return
     */
    String updateSettlement(String startTime,String endTime);

    /**
     *上传团体结算
     * @param startTime
     * @param endTime
     * @return
     */
    String updateGroupSettlement(String startTime,String endTime);

    /**
     * 积分和体检卡月度个检结算
     * @param startTime
     * @param endTime
     * @return
     */
    String updateMonth(String startTime,String endTime);

    /**
     *积分和体检卡月度团体结算
     * @param startTime
     * @param endTime
     * @return
     */
    String updateMonthGroup(String startTime,String endTime);

    /**
     * 上传统收
     * @param startTime
     * @param endTime
     * @return
     */
    String updateSettlementOfOrg(String startTime,String endTime);

    /**
     *检验统收团体金蝶名
     * @param startTime
     * @param endTime
     * @return
     */
    String checkOrgKingdeeName(String startTime,String endTime);

    /**
     * 检验匹配团体金蝶名
     * @return
     */
    String checkOrgName();

    /**
     * 获取上传金蝶星空云数据
     * @return
     */
    List<KingdeeUploadDataDto> getKingdeeUploadData(UploadPeiDataParam param);

    /**
     * 获取收款数据
     * @param baseParam
     * @return
     */
    List<ReceivePaymentDto> getReceivePaymentData(UploadPeiDataParam baseParam);
}
