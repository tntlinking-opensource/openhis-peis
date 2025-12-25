package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdBaseUnit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 济南-计量单位(MdBaseUnit)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
public interface MdBaseUnitService extends IService<MdBaseUnit> {

    /**
     * 分页查询[济南-计量单位]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdBaseUnit> getPage(PageParam<MdBaseUnit> page, MdBaseUnit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBaseUnit getInfoById(String id);

}

