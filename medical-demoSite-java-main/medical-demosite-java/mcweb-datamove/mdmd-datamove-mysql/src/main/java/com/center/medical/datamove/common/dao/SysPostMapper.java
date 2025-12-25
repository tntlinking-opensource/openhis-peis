package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysPost;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 岗位信息表(SysPost)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
public interface SysPostMapper extends BaseMapper<SysPost> {

    /**
     * 分页查询[岗位信息表]列表
     *
     * @param page  分页参数
     * @param param SysPost查询参数
     * @return 分页数据
     */
    IPage<SysPost> getPage(PageParam<SysPost> page, @Param("param") SysPost param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键postId
     * @return 详情信息
     */
    SysPost getInfoById(@Param("id") Long id);

}
