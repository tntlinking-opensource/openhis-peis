package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseZone;

/**
 * 所属地区(BaseZone)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:48
 */
public interface BaseZoneService extends IService<BaseZone> {

    /**
     * 分页查询[所属地区]列表
     *
     * @param page  分页参数
     * @param param BaseZone查询参数
     * @return 分页数据
     */
    IPage<BaseZone> getList(PageParam<BaseZone> page, BaseZone param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BaseZone getInfoById(String id);

}

