package com.center.medical.outside.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Attachment;
import com.center.medical.common.core.domain.R;
import com.center.medical.outside.bean.dto.BoyingGetPatientInfoDto;
import com.center.medical.outside.bean.dto.BoyingWriteReportDto;
import com.center.medical.outside.bean.param.BoyingGetPatientInfoParam;
import com.center.medical.outside.bean.param.BoyingWriteReportParam;

/**
 * 博英心电信息管理系统对接
 * @author xhp
 * @since 2025-05-23 16:24
 */
public interface BoyingBusinessService extends IService<Attachment> {

    /**
     * 心电信息管理系统发起查询申请单请求后，HIS根据传入的参数查询并返回符合条件的申请单。
     * @param param
     * @return
     */
    BoyingGetPatientInfoDto getPatientInfo(BoyingGetPatientInfoParam param);

    /**
     * 心电信息管理系统发起计费、归档报告请求,失败
     * @param param
     * @param e
     * @return
     */
    BoyingWriteReportDto createWriteReportDto(BoyingWriteReportParam param,Exception e,boolean isSuccess);

    /**
     * 心电信息管理系统发起计费、归档报告请求
     * @param param
     * @return
     */
    BoyingWriteReportDto writeReport(BoyingWriteReportParam param);

    /**
     * 心电信息管理系统发起计费、归档报告请求失败，重新发起请求
     * @param patientCode
     * @return
     */
    R<String> againReportBack(String patientCode);
}
