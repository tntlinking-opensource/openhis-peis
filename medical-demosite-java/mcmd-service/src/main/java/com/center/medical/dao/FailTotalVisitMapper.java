package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.FailTotalVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KF迟捡、阳性、不合格样本回访(FailTotalVisit)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:10
 */
public interface FailTotalVisitMapper extends BaseMapper<FailTotalVisit> {

    /**
     * 分页查询[KF迟捡、阳性、不合格样本回访]列表
     *
     * @param page  分页参数
     * @param param FailTotalVisit查询参数
     * @return 分页数据
     */
    IPage<FailTotalVisit> getList(PageParam<FailTotalVisit> page, @Param("param") FailTotalVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FailTotalVisit getInfoById(@Param("id") String id);

}
