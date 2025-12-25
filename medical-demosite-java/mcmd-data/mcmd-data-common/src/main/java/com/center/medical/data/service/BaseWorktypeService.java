package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseWorktype;

/**
 * 工种(BaseWorktype)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:40
 */
public interface BaseWorktypeService extends IService<BaseWorktype> {

    /**
     * 分页查询[工种]列表
     *
     * @param page  分页参数
     * @param param BaseWorktype查询参数
     * @return 分页数据
     */
    IPage<BaseWorktype> getList(PageParam<BaseWorktype> page, BaseWorktype param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BaseWorktype getInfoById(String id);

    /**
     * 工种信息查询
     *
     * @param typeName
     * @return
     */
    IPage<BaseWorktype> getBaseWorktypeSql(PageParam<BaseWorktype> page, String typeName);
}

