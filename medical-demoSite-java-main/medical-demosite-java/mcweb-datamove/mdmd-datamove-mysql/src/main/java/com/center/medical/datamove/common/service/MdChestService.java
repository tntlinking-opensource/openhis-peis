package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdChest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 订单柜子信息(MdChest)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:16
 */
public interface MdChestService extends IService<MdChest> {

    /**
     * 分页查询[订单柜子信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdChest> getPage(PageParam<MdChest> page, MdChest param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdChest getInfoById(String id);

}

