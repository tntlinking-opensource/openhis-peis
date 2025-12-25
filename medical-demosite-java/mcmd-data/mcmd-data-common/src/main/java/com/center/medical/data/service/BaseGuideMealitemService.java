package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseGuideMealitem;

/**
 * 基础收费项目(BaseGuideMealitem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:17
 */
public interface BaseGuideMealitemService extends IService<BaseGuideMealitem> {

    /**
     * 分页查询[基础收费项目]列表
     *
     * @param page  分页参数
     * @param param BaseGuideMealitem查询参数
     * @return 分页数据
     */
    IPage<BaseGuideMealitem> getList(PageParam<BaseGuideMealitem> page, BaseGuideMealitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BaseGuideMealitem getInfoById(String id);

}

