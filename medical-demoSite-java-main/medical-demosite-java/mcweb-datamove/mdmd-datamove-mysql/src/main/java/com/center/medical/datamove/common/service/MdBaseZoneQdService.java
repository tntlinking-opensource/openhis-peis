package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdBaseZoneQd;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 所属地区(MdBaseZoneQd)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:11
 */
public interface MdBaseZoneQdService extends IService<MdBaseZoneQd> {

    /**
     * 分页查询[所属地区]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdBaseZoneQd> getPage(PageParam<MdBaseZoneQd> page, MdBaseZoneQd param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBaseZoneQd getInfoById(String id);

}

