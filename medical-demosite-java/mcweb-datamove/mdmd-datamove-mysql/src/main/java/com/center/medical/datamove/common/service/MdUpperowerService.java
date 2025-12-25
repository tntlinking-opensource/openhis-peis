package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdUpperower;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 上下级关系管理表(MdUpperower)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:14
 */
public interface MdUpperowerService extends IService<MdUpperower> {

    /**
     * 分页查询[上下级关系管理表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdUpperower> getPage(PageParam<MdUpperower> page, MdUpperower param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdUpperower getInfoById(String id);

}

