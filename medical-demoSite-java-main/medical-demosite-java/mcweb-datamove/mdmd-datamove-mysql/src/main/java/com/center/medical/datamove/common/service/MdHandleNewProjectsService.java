package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdHandleNewProjects;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS检验科加项处理(MdHandleNewProjects)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:21
 */
public interface MdHandleNewProjectsService extends IService<MdHandleNewProjects> {

    /**
     * 分页查询[KS检验科加项处理]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdHandleNewProjects> getPage(PageParam<MdHandleNewProjects> page, MdHandleNewProjects param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdHandleNewProjects getInfoById(String id);

}

