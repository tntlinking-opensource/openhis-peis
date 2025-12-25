package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.FailTotalVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KF迟捡、阳性、不合格样本回访(FailTotalVisit)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:40
 */
public interface FailTotalVisitMapper extends BaseMapper<FailTotalVisit> {

    /**
     * 分页查询[KF迟捡、阳性、不合格样本回访]列表
     *
     * @param page  分页参数
     * @param param FailTotalVisit查询参数
     * @return 分页数据
     */
    IPage<FailTotalVisit> getPage(PageParam<FailTotalVisit> page, @Param("param") FailTotalVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FailTotalVisit getInfoById(@Param("id") String id);

}
