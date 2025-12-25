package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.DanagerObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * TJ危害因素收费项目(DanagerObject)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:38
 */
public interface DanagerObjectService extends IService<DanagerObject> {

    /**
     * 分页查询[TJ危害因素收费项目]列表
     *
     * @param page  分页参数
     * @param param DanagerObject查询参数
     * @return 分页数据
     */
    IPage<DanagerObject> getList(PageParam<DanagerObject> page, DanagerObject param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DanagerObject getInfoById(String id);

}

