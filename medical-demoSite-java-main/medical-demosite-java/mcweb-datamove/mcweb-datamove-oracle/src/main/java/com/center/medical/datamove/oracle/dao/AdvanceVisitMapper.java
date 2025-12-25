package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AdvanceVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KF预检跟踪回访记录表(AdvanceVisit)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:19
 */
public interface AdvanceVisitMapper extends BaseMapper<AdvanceVisit> {

    /**
     * 分页查询[KF预检跟踪回访记录表]列表
     *
     * @param page  分页参数
     * @param param AdvanceVisit查询参数
     * @return 分页数据
     */
    IPage<AdvanceVisit> getPage(PageParam<AdvanceVisit> page, @Param("param") AdvanceVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AdvanceVisit getInfoById(@Param("id") String id);

}
