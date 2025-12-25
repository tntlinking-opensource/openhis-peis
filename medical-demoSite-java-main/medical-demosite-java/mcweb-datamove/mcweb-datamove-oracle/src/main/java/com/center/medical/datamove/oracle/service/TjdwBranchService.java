package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.TjdwBranch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * XS体检单位：部门信息(TjdwBranch)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:31
 */
public interface TjdwBranchService extends IService<TjdwBranch> {

    /**
     * 分页查询[XS体检单位：部门信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TjdwBranch> getPage(PageParam<TjdwBranch> page, TjdwBranch param);


}

