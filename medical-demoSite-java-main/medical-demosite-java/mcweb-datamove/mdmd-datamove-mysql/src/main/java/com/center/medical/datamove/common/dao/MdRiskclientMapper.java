package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdRiskclient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 高危人员管理表(MdRiskclient)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:20
 */
public interface MdRiskclientMapper extends BaseMapper<MdRiskclient> {

    /**
     * 分页查询[高危人员管理表]列表
     *
     * @param page  分页参数
     * @param param MdRiskclient查询参数
     * @return 分页数据
     */
    IPage<MdRiskclient> getPage(PageParam<MdRiskclient> page, @Param("param") MdRiskclient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdRiskclient getInfoById(@Param("id") String id);

}
