package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Orderandcombo;
import com.center.medical.sellcrm.bean.param.ApproveTjtcDataParam;
import com.center.medical.sellcrm.bean.vo.OrderMealVo;

import java.util.List;
import java.util.Map;

/**
 * 订单与套餐关联表(Orderandcombo)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-22 18:38:08
 */
public interface OrderandcomboService extends IService<Orderandcombo> {

    /**
     * 分页查询[订单与套餐关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Orderandcombo> getPage(PageParam<Orderandcombo> page, Orderandcombo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Orderandcombo getInfoById(String id);

    /**
     * 编辑默认加载订单关联的套餐
     *
     * @param page
     * @param ddId
     * @param isCopy
     * @return
     */
    IPage<Orderandcombo> getTjtcData(PageParam<Orderandcombo> page, String ddId, Integer isCopy);

    /**
     * 获取审核订单下关联的套餐
     *
     * @param page
     * @param approveTjtcDataParam
     * @return
     */
    Map getApproveTjtcData(PageParam<Orderandcombo> page, ApproveTjtcDataParam approveTjtcDataParam);

    /**
     * 团检专属卡-套餐搜索
     *
     * @param id
     * @param key
     * @return
     */
    List<OrderMealVo> getOrderMealData(String id, String key);

}

