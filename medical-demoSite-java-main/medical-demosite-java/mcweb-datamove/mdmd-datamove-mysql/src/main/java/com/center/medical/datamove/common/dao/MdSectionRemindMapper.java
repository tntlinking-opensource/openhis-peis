package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSectionRemind;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 科室提醒主表(MdSectionRemind)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:31
 */
public interface MdSectionRemindMapper extends BaseMapper<MdSectionRemind> {

    /**
     * 分页查询[科室提醒主表]列表
     *
     * @param page  分页参数
     * @param param MdSectionRemind查询参数
     * @return 分页数据
     */
    IPage<MdSectionRemind> getPage(PageParam<MdSectionRemind> page, @Param("param") MdSectionRemind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSectionRemind getInfoById(@Param("id") String id);

}
