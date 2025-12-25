package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdAdvanceVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KF预检跟踪回访记录表(MdAdvanceVisit)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:05
 */
public interface MdAdvanceVisitMapper extends BaseMapper<MdAdvanceVisit> {

    /**
     * 分页查询[KF预检跟踪回访记录表]列表
     *
     * @param page  分页参数
     * @param param MdAdvanceVisit查询参数
     * @return 分页数据
     */
    IPage<MdAdvanceVisit> getPage(PageParam<MdAdvanceVisit> page, @Param("param") MdAdvanceVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdAdvanceVisit getInfoById(@Param("id") String id);

}
