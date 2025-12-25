package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.SectionRemind;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 科室提醒主表(SectionRemind)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:17
 */
public interface SectionRemindMapper extends BaseMapper<SectionRemind> {

    /**
     * 分页查询[科室提醒主表]列表
     *
     * @param page  分页参数
     * @param param SectionRemind查询参数
     * @return 分页数据
     */
    IPage<SectionRemind> getPage(PageParam<SectionRemind> page, @Param("param") SectionRemind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionRemind getInfoById(@Param("id") String id);

}
