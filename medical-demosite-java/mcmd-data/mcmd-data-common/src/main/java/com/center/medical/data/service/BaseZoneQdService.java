package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseZoneQd;

/**
 * 所属地区(BaseZoneQd)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:24
 */
public interface BaseZoneQdService extends IService<BaseZoneQd> {

    /**
     * 分页查询[所属地区]列表
     *
     * @param page  分页参数
     * @param param BaseZoneQd查询参数
     * @return 分页数据
     */
    IPage<BaseZoneQd> getList(PageParam<BaseZoneQd> page, BaseZoneQd param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BaseZoneQd getInfoById(String id);

}

