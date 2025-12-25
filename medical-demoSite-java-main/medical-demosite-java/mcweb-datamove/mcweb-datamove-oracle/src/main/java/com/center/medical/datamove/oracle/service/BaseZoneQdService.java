package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.BaseZoneQd;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 所属地区(BaseZoneQd)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:41
 */
public interface BaseZoneQdService extends IService<BaseZoneQd> {

    /**
     * 分页查询[所属地区]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BaseZoneQd> getPage(PageParam<BaseZoneQd> page, BaseZoneQd param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseZoneQd getInfoById(String id);

}

