package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Upperower;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 上下级关系管理表(Upperower)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:42
 */
public interface UpperowerService extends IService<Upperower> {

    /**
     * 分页查询[上下级关系管理表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Upperower> getPage(PageParam<Upperower> page, Upperower param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Upperower getInfoById(String id);

}

