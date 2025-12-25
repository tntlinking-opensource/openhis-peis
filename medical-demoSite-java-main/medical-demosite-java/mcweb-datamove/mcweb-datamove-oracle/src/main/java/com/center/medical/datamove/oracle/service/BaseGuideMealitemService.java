package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.BaseGuideMealitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (BaseGuideMealitem)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:34
 */
public interface BaseGuideMealitemService extends IService<BaseGuideMealitem> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BaseGuideMealitem> getPage(PageParam<BaseGuideMealitem> page, BaseGuideMealitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseGuideMealitem getInfoById(String id);

}

