package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdCreateOrderQtxz;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 线上变更订单前台须知记录(MdCreateOrderQtxz)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:18
 */
public interface MdCreateOrderQtxzService extends IService<MdCreateOrderQtxz> {

    /**
     * 分页查询[线上变更订单前台须知记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdCreateOrderQtxz> getPage(PageParam<MdCreateOrderQtxz> page, MdCreateOrderQtxz param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCreateOrderQtxz getInfoById(String id);

}

