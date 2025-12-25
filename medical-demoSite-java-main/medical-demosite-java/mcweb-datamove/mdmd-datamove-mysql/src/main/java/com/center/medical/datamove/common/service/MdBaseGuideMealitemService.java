package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdBaseGuideMealitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 基础收费项目(MdBaseGuideMealitem)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
public interface MdBaseGuideMealitemService extends IService<MdBaseGuideMealitem> {

    /**
     * 分页查询[基础收费项目]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdBaseGuideMealitem> getPage(PageParam<MdBaseGuideMealitem> page, MdBaseGuideMealitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBaseGuideMealitem getInfoById(String id);

}

