package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysBranch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 分中心维护表(SysBranch)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:33
 */
public interface SysBranchService extends IService<SysBranch> {

    /**
     * 分页查询[分中心维护表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysBranch> getPage(PageParam<SysBranch> page, SysBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysBranch getInfoById(Integer id);

}

