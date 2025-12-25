package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysFunctionBranch;

/**
 * 业务功能-分中心关联(SysFunctionBranch)服务接口
 *
 * @author makejava
 * @since 2024-03-19 11:12:09
 */
public interface SysFunctionBranchService extends IService<SysFunctionBranch> {

    /**
     * 分页查询[业务功能-分中心关联]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysFunctionBranch> getPage(PageParam<SysFunctionBranch> page, SysFunctionBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysFunctionBranch getInfoById(String id);

    /**
     * 查询该分中心是否开启该业务
     * @param functionId
     * @param branchId
     * @return
     */
    Integer getByfunIdAndCid(int functionId, String branchId);
}

