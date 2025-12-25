package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Orderandfzx;

import java.util.List;

/**
 * 订单与分中心关联表(Orderandfzx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-22 18:38:08
 */
public interface OrderandfzxService extends IService<Orderandfzx> {

    /**
     * 分页查询[订单与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Orderandfzx> getPage(PageParam<Orderandfzx> page, Orderandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Orderandfzx getInfoById(String id);

    /**
     * 通过订单id查询关联的分中心名称
     * @param ddid
     * @return
     */
    List<String> getBranchNameByDdid(String ddid);
}

