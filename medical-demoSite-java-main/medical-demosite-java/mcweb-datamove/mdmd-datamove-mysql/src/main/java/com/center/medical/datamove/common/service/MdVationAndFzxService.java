package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdVationAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 团体任务分中心（不会被同步）(MdVationAndFzx)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:29
 */
public interface MdVationAndFzxService extends IService<MdVationAndFzx> {

    /**
     * 分页查询[团体任务分中心（不会被同步）]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdVationAndFzx> getPage(PageParam<MdVationAndFzx> page, MdVationAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdVationAndFzx getInfoById(String id);

}

