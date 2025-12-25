package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseUnit;

/**
 * 济南-计量单位(BaseUnit)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:46
 */
public interface BaseUnitService extends IService<BaseUnit> {

    /**
     * 分页查询[济南-计量单位]列表
     *
     * @param page  分页参数
     * @param param BaseUnit查询参数
     * @return 分页数据
     */
    IPage<BaseUnit> getList(PageParam<BaseUnit> page, BaseUnit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BaseUnit getInfoById(String id);

}

