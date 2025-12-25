package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdSectionAndRemind;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 科室提醒和科室关联表(MdSectionAndRemind)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:30
 */
public interface MdSectionAndRemindMapper extends BaseMapper<MdSectionAndRemind> {

    /**
     * 分页查询[科室提醒和科室关联表]列表
     *
     * @param page  分页参数
     * @param param MdSectionAndRemind查询参数
     * @return 分页数据
     */
    IPage<MdSectionAndRemind> getPage(PageParam<MdSectionAndRemind> page, @Param("param") MdSectionAndRemind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSectionAndRemind getInfoById(@Param("id") String id);

}
