package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.Ordersummary;
import com.center.medical.sellcrm.bean.param.OrdersummaryParam;
import com.center.medical.sellcrm.dao.CreateorderMapper;
import com.center.medical.sellcrm.dao.OrdersummaryMapper;
import com.center.medical.sellcrm.service.OrdersummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 订单总结表(Ordersummary)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 18:38:09
 */
@Slf4j
@Service("ordersummaryService")
@RequiredArgsConstructor
public class OrdersummaryServiceImpl extends ServiceImpl<OrdersummaryMapper, Ordersummary> implements OrdersummaryService {

    private final OrdersummaryMapper ordersummaryMapper;
    private final CreateorderMapper createorderMapper;

    /**
     * 分页查询[订单总结表]列表
     *
     * @param page  分页参数
     * @param param Ordersummary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Ordersummary> getPage(PageParam<Ordersummary> page, OrdersummaryParam param) {
        param.setBranchIds(Arrays.asList(SecurityUtils.getCId()));
        if (!SecurityUtils.isLeader()) {
            param.setXsjlid(SecurityUtils.getUserNo());
        }
        return ordersummaryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Ordersummary getInfoById(String id) {
        return ordersummaryMapper.getInfoById(id);
    }

    /**
     * 新增/编辑操作
     *
     * @param ordersummary
     * @return
     */
    @Override
    public Boolean saOrUp(Ordersummary ordersummary) {
        Date now = new Date();
        if (StringUtils.isNotBlank(ordersummary.getDdid())) {
            Createorder createorder = createorderMapper.getInfoById(ordersummary.getDdid());
            if (StringUtils.isBlank(ordersummary.getId())) {
                //新增
                //获取订单信息
                if (Objects.isNull(createorder)) {
                    throw new ServiceException("新增失败，销售订单不存在或者已删除！");
                }
                //单位名称
                ordersummary.setDdmc(createorder.getDdmc());
                //销售经理id
                ordersummary.setXsjlid(SecurityUtils.getUserNo());
                //分中心id
                ordersummary.setFzxid(SecurityUtils.getCId());
                //假删状态
                ordersummary.setIsDelete(0);
                ordersummary.setCreatedate(now);
                //数据保存
                ordersummaryMapper.insert(ordersummary);
            } else {
                //编辑
                Ordersummary orderSu = ordersummaryMapper.getInfoById(ordersummary.getId());
                if (Objects.isNull(orderSu)) {
                    throw new ServiceException("编辑失败，该订单总结已被删除！");
                }
                orderSu.setDdmc(createorder.getDdmc());
                orderSu.setModifydate(now);
                ordersummaryMapper.updateById(orderSu);
            }
        }

        return Boolean.TRUE;
    }

}

