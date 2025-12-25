package com.center.medical.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.dto.ReportListDto;
import com.center.medical.bean.model.NewMobileReportParam;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.bean.param.LastAccessParam;
import com.center.medical.bean.param.ValidationCodeParam;
import com.center.medical.bean.vo.ValidationCodeVo;

import java.util.List;

/**
 * 生成报告内容(ReportContent)服务接口
 *
 * @author ay
 * @since 2023-08-14 14:54:49
 */
public interface MobileReportService extends IService<ReportContent> {

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportContent getInfoById(String id);

    /**
     * 获取报告列表
     *
     * @param phone
     * @return
     */
    List<ReportListDto> getReportList(String phone,String year);


    /**
     * 校验验证码并返回列表数据
     * @param param
     * @return
     */
    ValidationCodeVo validationCode(ValidationCodeParam param);


    /**
     * 分享报告-访问次数和ip
     * @param param
     * @return
     */
    Boolean lastAccess(LastAccessParam param);

    /**
     * 新版小程序查询手机报告列表
     * @param param
     * @return
     */
    List<ReportListDto> getNewReportList(NewMobileReportParam param);

    /**
     * 查询报告与手机号是否对应
     * @param patientcode
     * @param phone
     * @return
     */
    Boolean checkDetails(String patientcode, String phone);

    /**
     * 根据订单号、手机号查询报告列表
     * @param phone
     * @param orderNum
     * @return
     */
    List<ReportListDto> getReportListByOrderId(String phone, String orderNum);
}

