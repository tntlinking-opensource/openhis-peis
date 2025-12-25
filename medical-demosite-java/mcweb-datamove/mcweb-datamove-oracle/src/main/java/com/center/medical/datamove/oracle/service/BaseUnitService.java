package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.BaseUnit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 济南-计量单位(BaseUnit)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:37
 */
public interface BaseUnitService extends IService<BaseUnit> {

    /**
     * 分页查询[济南-计量单位]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BaseUnit> getPage(PageParam<BaseUnit> page, BaseUnit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseUnit getInfoById(String id);

}

