package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.TjdwBranch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * XS体检单位：部门信息(TjdwBranch)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:39
 */
public interface TjdwBranchService extends IService<TjdwBranch> {

    /**
     * 分页查询[XS体检单位：部门信息]列表
     *
     * @param page  分页参数
     * @param param TjdwBranch查询参数
     * @return 分页数据
     */
    IPage<TjdwBranch> getList(PageParam<TjdwBranch> page, TjdwBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    TjdwBranch getInfoById(String id);

}

