package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdTjdwBranch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * XS体检单位：部门信息(MdTjdwBranch)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:07
 */
public interface MdTjdwBranchService extends IService<MdTjdwBranch> {

    /**
     * 分页查询[XS体检单位：部门信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdTjdwBranch> getPage(PageParam<MdTjdwBranch> page, MdTjdwBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTjdwBranch getInfoById(String id);

}

