package com.center.medical.enterprise.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.enterprise.bean.model.Createorder;
import com.center.medical.enterprise.bean.param.CreateOrderInfoDataParam;
import com.center.medical.enterprise.bean.param.CreateOrderInfoItemParam;
import com.center.medical.enterprise.bean.param.GetOrderListParam;
import com.center.medical.enterprise.bean.param.PeipatientDataParam;
import com.center.medical.enterprise.bean.vo.*;
import com.center.medical.enterprise.common.util.PageParam;

/**
 * 订单表(MdCreateorder)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:20
 */
public interface CreateorderService extends IService<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Createorder> getPage(PageParam<Createorder> page, Createorder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createorder getInfoById(String id);

    /**
     * 获取订单详情
     * @param page
     * @param param
     * @return
     */
    IPage<CreateOrderInfoDataVo> getInfoListData(PageParam<CreateOrderInfoDataVo> page, CreateOrderInfoDataParam param);

    /**
     * 获取套餐详情
     * @param page
     * @param id
     * @return
     */
    IPage<CreateOrderInfoMealVo> getInfoMealData(PageParam<CreateOrderInfoMealVo> page, String id);

    /**
     * 获取套餐详情
     * @param page
     * @param param
     * @return
     */
    IPage<CreateOrderInfoItemlVo> getInfoItemData(PageParam<CreateOrderInfoItemlVo> page, CreateOrderInfoItemParam param);

    /**
     * 获取订单列表
     * @param page
     * @param param
     * @return
     */
    IPage<GetOrderListVo> getOrderList(PageParam<GetOrderListVo> page, GetOrderListParam param);

    /**
     * 获取体检者数据
     * @param page
     * @param param
     * @return
     */
    IPage<PeipatientDataVo> getPeipatientDataList(PageParam<PeipatientDataVo> page, PeipatientDataParam param);
}

