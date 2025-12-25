package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.bean.model.Report;
import com.center.medical.report.bean.param.*;
import org.apache.ibatis.annotations.Param;

/**
 * 健康一审数据库访问层
 *
 * @author fjj
 * @since 2022-11-23 17:11:14
 */
public interface HealthAuditMapper extends BaseMapper<Report> {

    /**
     * 分页查询健康初审页面数据
     *
     * @param page  分页参数
     * @param param Report查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getFirstListData(PageParam<Peispatient> page, @Param("param") HealthAuditParam param);

    /**
     * 分页查询健康复审页面数据
     *
     * @param page  分页参数
     * @param param Report查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getSecondListData(PageParam<Peispatient> page, @Param("param")HealthAuditParam param);

    /**
     * 分页查询健康终审页面数据
     *
     * @param page  分页参数
     * @param param Report查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getLastListData(PageParam<Peispatient> page, @Param("param")HealthAuditParam param);

    IPage<Peispatient> getProfessionFirstListData(PageParam<Peispatient> page, @Param("param")HealthAuditParam param);

    IPage<Peispatient> getProfessionSecondListData(PageParam<Peispatient> page, @Param("param")HealthAuditParam param);

    IPage<Peispatient> getProfessionLastListData(PageParam<Peispatient> page, @Param("param")HealthAuditParam param);
}
