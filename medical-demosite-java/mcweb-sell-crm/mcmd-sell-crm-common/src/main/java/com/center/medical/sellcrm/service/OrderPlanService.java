package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.OrderPlan;
import com.center.medical.sellcrm.bean.param.OrderPlanParam;

import java.util.List;

/**
 * 签单计划(OrderPlan)表服务接口
 *
 * @author 路飞船长
 * @since 2023-05-16 16:47:56
 */
public interface OrderPlanService extends IService<OrderPlan> {

    /**
     * 分页查询[签单计划]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrderPlan> getPage(PageParam<OrderPlan> page, OrderPlanParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrderPlan getInfoById(String id);

    /**
     * 生成签单计划
     *
     * @param createOrder
     * @param bdlxList
     */
    void saOrUp(Createorder createOrder, List<Integer> bdlxList);

    /**
     * 进行撞单排查处理
     *
     * @param ddh  订单号
     * @param ddmc 订单名称
     * @param xsjlid 销售经理id
     * @return
     */
    R<String> doConflict(String ddh, String ddmc,String xsjlid);
}

