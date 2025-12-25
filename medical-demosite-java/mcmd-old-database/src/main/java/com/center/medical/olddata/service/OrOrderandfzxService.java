package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrOrderandfzx;

import java.util.List;

/**
 * 订单与分中心关联表(Orderandfzx)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:10:28
 */
public interface OrOrderandfzxService extends IService<OrOrderandfzx> {

    /**
     * 分页查询[订单与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrOrderandfzx> getPage(PageParam<OrOrderandfzx> page, OrOrderandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrOrderandfzx getInfoById(String id);

    /**
     * 通过订单id查询
     *
     * @param id
     * @return
     */
    List<OrOrderandfzx> getByDdid(String id);

    /**
     * 通过订单id和分中心查询
     * @param id
     * @param fzxId
     * @return
     */
    OrOrderandfzx getByDdidAndFzxId(String id, String fzxId);
}

