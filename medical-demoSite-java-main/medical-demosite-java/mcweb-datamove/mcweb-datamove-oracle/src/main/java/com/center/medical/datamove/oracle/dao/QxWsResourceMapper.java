package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.QxWsResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (QxWsResource)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:04
 */
public interface QxWsResourceMapper extends BaseMapper<QxWsResource> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxWsResource查询参数
     * @return 分页数据
     */
    IPage<QxWsResource> getPage(PageParam<QxWsResource> page, @Param("param") QxWsResource param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    QxWsResource getInfoById(@Param("id") String id);

}
