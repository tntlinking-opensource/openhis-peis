package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.OrderHistoryStatistics;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.OrderHistoryStatisticsMapper;
import com.center.medical.service.OrderHistoryStatisticsService;
import com.center.medical.system.dao.SysBranchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 订单历史平均折扣、变动成本率统计表(OrderHistoryStatistics)服务实现类
 *
 * @author ay
 * @since 2023-12-07 14:05:37
 */
@Slf4j
@Service("orderHistoryStatisticsService")
@RequiredArgsConstructor
public class OrderHistoryStatisticsServiceImpl extends ServiceImpl<OrderHistoryStatisticsMapper, OrderHistoryStatistics> implements OrderHistoryStatisticsService {

    private final OrderHistoryStatisticsMapper orderHistoryStatisticsMapper;
    private final SysBranchMapper sysBranchMapper;

    /**
     * 分页查询[订单历史平均折扣、变动成本率统计表]列表
     *
     * @param page  分页参数
     * @param param OrderHistoryStatistics查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrderHistoryStatistics> getPage(PageParam<OrderHistoryStatistics> page, OrderHistoryStatistics param) {
        return orderHistoryStatisticsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrderHistoryStatistics getInfoById(String id) {
        return orderHistoryStatisticsMapper.getInfoById(id);
    }


    /**
     * 审核时展示订单平均变动成本率以及历史变动成本率
     * @param idOrg
     * @return
     */
    @Override
    public String getOrderHistoryStatisticsStr(String idOrg) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        //显示前3年的
        int startYear = year-3;
        int lastYear = year-1;
        List<OrderHistoryStatistics> orderHistoryStatistics = orderHistoryStatisticsMapper.selectList(
                new QueryWrapper<OrderHistoryStatistics>()
                .orderByAsc("fzx_id","year")
                .eq("id_org",idOrg)
                .ge("year",startYear)
                .le("year",lastYear)
        );
        List<String> msgs=new ArrayList<>();
        for(OrderHistoryStatistics o:orderHistoryStatistics){
            SysBranch branch = sysBranchMapper.getByBranchId(o.getFzxId());
            msgs.add((branch==null?"":branch.getFzx())+o.getYear()+"年,平均折扣率"+o.getAverageDiscountRate()+",变动成本率"+o.getVariableCostRate());
        }
        return StringUtils.join(msgs,"\n");
    }

    /**
     * 插入订单历史折扣成本率等
     * @param param
     * @return
     */
    @Override
    public Boolean insertOrderHistoryStatistics(BaseParam param) {
        //老系统sql，如果当时候还没替换就要去老系统导出excel，然后再导入新系统
//        List<OrderHistoryStatistics> oldList = orderHistoryStatisticsMapper.getOldSql(param);
        //查询出来数据
        List<OrderHistoryStatistics> list = orderHistoryStatisticsMapper.insertOrderHistoryStatistics(param);
        saveBatch(list);
        return Boolean.TRUE;
    }
}

