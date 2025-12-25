package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysPost;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 岗位信息表(SysPost)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:37
 */
public interface SysPostService extends IService<SysPost> {

    /**
     * 分页查询[岗位信息表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysPost> getPage(PageParam<SysPost> page, SysPost param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键postId
     * @return 详情信息
     */
    SysPost getInfoById(Long id);

}

