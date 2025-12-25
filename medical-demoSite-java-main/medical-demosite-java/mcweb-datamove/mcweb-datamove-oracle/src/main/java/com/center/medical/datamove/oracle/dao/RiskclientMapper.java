package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Riskclient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 高危人员管理表(Riskclient)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:26
 */
public interface RiskclientMapper extends BaseMapper<Riskclient> {

    /**
     * 分页查询[高危人员管理表]列表
     *
     * @param page  分页参数
     * @param param Riskclient查询参数
     * @return 分页数据
     */
    IPage<Riskclient> getPage(PageParam<Riskclient> page, @Param("param") Riskclient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Riskclient getInfoById(@Param("id") String id);

}
