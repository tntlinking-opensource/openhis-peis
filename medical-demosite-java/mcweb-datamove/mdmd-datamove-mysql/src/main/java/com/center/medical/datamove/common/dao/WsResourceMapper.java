package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.WsResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 网站资源(WsResource)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:41
 */
public interface WsResourceMapper extends BaseMapper<WsResource> {

    /**
     * 分页查询[网站资源]列表
     *
     * @param page  分页参数
     * @param param WsResource查询参数
     * @return 分页数据
     */
    IPage<WsResource> getPage(PageParam<WsResource> page, @Param("param") WsResource param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WsResource getInfoById(@Param("id") String id);

}
