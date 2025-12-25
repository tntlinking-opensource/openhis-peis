package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.bean.model.Report;
import com.center.medical.report.bean.param.HealthAuditParam;

import java.util.List;

/**
 * 健康审核复审服务接口
 *
 * @author 浮俊杰
 * @since 2022-12-17 10:15:51
 */
public interface HealthSecondAuditService extends IService<Report> {

    /**
     * 分页查询健康复审页面数据
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getListData(PageParam<Peispatient> page, HealthAuditParam param);

    /**
     * 健康复审页面数据通过
     *
     * @param ids     查询条件
     * @return 所有数据
     */
    Boolean pass(String ids);

    /**
     * 健康复审页面数据反审
     *
     * @param ids     查询条件
     * @return 所有数据
     */
    Boolean unaudit(String ids);

    /**
     * 健康复审页面数据不通过
     *
     * @param id, secondReason    查询条件
     * @return 所有数据
     */
    String uncheck(String id, String secondReason);

    /**
     * 健康复审获取体检者数据
     *
     * @param patientcode     查询条件
     * @return 所有数据
     */
    Peispatient getPatientData(String patientcode);

    /**
     * 批量通过健康复审
     *
     * @param peispatients 实体对象
     * @return 修改结果
     */
    String batchPass(List<Peispatient> peispatients);
}

