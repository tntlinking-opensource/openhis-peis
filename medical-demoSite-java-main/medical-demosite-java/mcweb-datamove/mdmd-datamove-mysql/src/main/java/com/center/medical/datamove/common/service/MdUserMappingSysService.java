package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdUserMappingSys;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 用户映射系统(MdUserMappingSys)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:23
 */
public interface MdUserMappingSysService extends IService<MdUserMappingSys> {

    /**
     * 分页查询[用户映射系统]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdUserMappingSys> getPage(PageParam<MdUserMappingSys> page, MdUserMappingSys param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键systemId
     * @return 详情信息
     */
    MdUserMappingSys getInfoById(String id);

}

