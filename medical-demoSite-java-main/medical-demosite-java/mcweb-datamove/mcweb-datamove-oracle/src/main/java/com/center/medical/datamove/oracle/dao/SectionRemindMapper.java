package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.SectionRemind;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (SectionRemind)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:41
 */
public interface SectionRemindMapper extends BaseMapper<SectionRemind> {

    /**
     * 分页查询[]列表
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
