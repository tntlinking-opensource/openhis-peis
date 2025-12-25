package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.SectionAndRemind;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 科室提醒和科室关联表(SectionAndRemind)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:16
 */
public interface SectionAndRemindMapper extends BaseMapper<SectionAndRemind> {

    /**
     * 分页查询[科室提醒和科室关联表]列表
     *
     * @param page  分页参数
     * @param param SectionAndRemind查询参数
     * @return 分页数据
     */
    IPage<SectionAndRemind> getPage(PageParam<SectionAndRemind> page, @Param("param") SectionAndRemind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionAndRemind getInfoById(@Param("id") String id);

}
