package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.config.OpenOrderConflictConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.OrderConflict;
import com.center.medical.sellcrm.bean.model.OrderPlan;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.bean.param.OrderPlanParam;
import com.center.medical.sellcrm.dao.OrderConflictMapper;
import com.center.medical.sellcrm.dao.OrderPlanMapper;
import com.center.medical.sellcrm.dao.SellcustomerMapper;
import com.center.medical.sellcrm.service.OrderPlanService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 签单计划(OrderPlan)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-05-16 16:47:56
 */
@Slf4j
@Service("orderPlanService")
@RequiredArgsConstructor
public class OrderPlanServiceImpl extends ServiceImpl<OrderPlanMapper, OrderPlan> implements OrderPlanService {

    private final OrderPlanMapper orderPlanMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final ISysConfigService iSysConfigService;
    private final OrderConflictMapper orderConflictMapper;

    /**
     * 分页查询[签单计划]列表
     *
     * @param page  分页参数
     * @param param OrderPlan查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrderPlan> getPage(PageParam<OrderPlan> page, OrderPlanParam param) {
        return orderPlanMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrderPlan getInfoById(String id) {
        return orderPlanMapper.getInfoById(id);
    }

    /**
     * 生成签单计划
     *
     * @param createOrder
     * @param bdlxList
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void saOrUp(Createorder createOrder, List<Integer> bdlxList) {
        //删除记录
        orderPlanMapper.delete(new LambdaQueryWrapper<OrderPlan>().eq(OrderPlan::getDdId, createOrder.getDdh()));
        //生成签单计划
        OrderPlan orderPlan = new OrderPlan();
        orderPlan.setDdId(createOrder.getDdh());
        orderPlan.setYearFlag(String.valueOf(DateUtil.year(new Date())));
        orderPlan.setBranchId(createOrder.getFzxid());
        orderPlan.setSalesId(createOrder.getXsjlid());
        Sellcustomer sellcustomer = sellcustomerMapper.getInfoById(createOrder.getKhdwmcid());
        if (ObjectUtils.isEmpty(sellcustomer.getSocialCreditCode())){
            throw new ServiceException("请先填写客户信息的社会信用代码!");
        }
        orderPlan.setSocialCreditCode(sellcustomer.getSocialCreditCode());
        orderPlan.setContent(createOrder.getDdmc() + createOrder.getKhdwmc() + sellcustomer.getProvince() + sellcustomer.getCity() + sellcustomer.getDistrict() + sellcustomer.getStreet() + createOrder.getTjlx());
        orderPlan.setStatus(0);
        if (CollectionUtil.isNotEmpty(bdlxList)) {
            for (Integer bdlx : bdlxList) {
                orderPlan.setId(null);
                orderPlan.setOrderTypeId(String.valueOf(bdlx));
                save(orderPlan);
            }
        } else {
            save(orderPlan);
        }

    }

    /**
     * 进行撞单排查处理
     *
     * @param ddh  订单号
     * @param ddmc 订单名称
     * @param xsjlid 销售经理id
     * @return
     */
    @Override
    public R<String> doConflict(String ddh, String ddmc ,String xsjlid) {
        //获取撞单配置信息
        OpenOrderConflictConfig config = iSysConfigService.getSysConfigObject(Constants.OPEN_ORDER_CONFLICT, OpenOrderConflictConfig.class);
        if (config.getOpen() == 0) {
            return R.ok();
        }

        //之前已经处理过的未撞单的也跳过
        if (orderConflictMapper.selectCount(new LambdaQueryWrapper<OrderConflict>()
                .eq(OrderConflict::getDdId, ddh).eq(OrderConflict::getStatus, 1).eq(OrderConflict::getDealResult,0)) > 0) {
            return R.ok();
        }

        //查看是否已有撞单记录
        if (orderConflictMapper.selectCount(new LambdaQueryWrapper<OrderConflict>()
                .eq(OrderConflict::getDdId, ddh).eq(OrderConflict::getStatus, 0)) > 0) {
            return R.fail("系统判断出你提交的订单【订单号：" + ddh + "，订单名称：" + ddmc + "】在系统已存在待处理的撞单记录，暂无法提交！");
        }

        List<OrderPlan> curList = orderPlanMapper.selectList(new LambdaQueryWrapper<OrderPlan>().eq(OrderPlan::getDdId, ddh));
        Boolean flag = false;
        StringBuilder msg = new StringBuilder("系统判断出你提交的订单【订单号：" + ddh + "+，订单名称：" + ddmc + "】和以下订单可能发送撞单行为：");
        List<String> oldIds = new ArrayList<>();
        List<String> similarList = new ArrayList<>();
        for (OrderPlan cur : curList) {
            List<OrderPlan> orderPlans = orderPlanMapper.selectList(new LambdaQueryWrapper<OrderPlan>()
                    .eq(OrderPlan::getSocialCreditCode, cur.getSocialCreditCode())
                    .eq(OrderPlan::getStatus, 1)
                    .eq(OrderPlan::getOrderTypeId, cur.getOrderTypeId())
                    .eq(OrderPlan::getYearFlag, cur.getYearFlag())
                    .ne(OrderPlan::getSalesId, xsjlid) //销售员不是自己的
                    .ne(OrderPlan::getDdId, ddh));
            cur.setStatus(1);
            if (CollectionUtil.isEmpty(orderPlans)) {
                continue;
            } else {
                for (OrderPlan old : orderPlans) {
                    //判断是否撞单成立
                    Double similar = StrUtil.similar(cur.getContent(), old.getContent());
                    log.info("撞单概率：{}、{}、{}", cur, old, similar);
                    if (similar * 100 > config.getRate()) {
                        //成立，生成撞单记录
                        flag = true;
                        if (!oldIds.contains(old.getDdId())) {
                            oldIds.add(old.getDdId());
                            similarList.add(NumberUtil.formatPercent(similar, 2));
                            msg.append("【订单号：" + old.getDdId() + "，概率：" + NumberUtil.formatPercent(similar, 2) + "】");
                        }
                    }
                }
            }

        }
        if (flag) {
            OrderConflict orderConflict = new OrderConflict();
            orderConflict.setDdId(ddh);
            orderConflict.setRate(CollectionUtil.join(similarList, ","));
            orderConflict.setPlanIds(CollectionUtil.join(oldIds, ","));
            orderConflict.setStatus(0);
            orderConflictMapper.insert(orderConflict);
            // TODO sms 发送通知，撞单处理
            msg.append("，请向相关的领导反应进行撞单处理！");
            return R.fail(msg.toString());
        }
        updateBatchById(curList);
        return R.ok();
    }

}

