package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageAdapter;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.OrderConflict;
import com.center.medical.sellcrm.bean.param.NeedChooseParam;
import com.center.medical.sellcrm.bean.param.OrderConflictDeallParam;
import com.center.medical.sellcrm.bean.param.OrderConflictParam;
import com.center.medical.sellcrm.bean.vo.ConflictOrderVo;
import com.center.medical.sellcrm.bean.vo.OrderConflictVo;
import com.center.medical.sellcrm.dao.CreateorderMapper;
import com.center.medical.sellcrm.dao.OrderConflictMapper;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.sellcrm.service.OrderConflictService;
import com.center.medical.workflow.bean.model.WorkflowItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 撞单记录(OrderConflict)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-04-27 18:17:31
 */
@Slf4j
@Service("orderConflictService")
@RequiredArgsConstructor
public class OrderConflictServiceImpl extends ServiceImpl<OrderConflictMapper, OrderConflict> implements OrderConflictService {

    private final OrderConflictMapper orderConflictMapper;
    private final CreateorderMapper createorderMapper;
    private final CreateorderService createorderService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询[撞单记录]列表
     *
     * @param page  分页参数
     * @param param OrderConflict查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrderConflictVo> getPage(PageParam<OrderConflictVo> page, OrderConflictParam param) {
        List<OrderConflict> list = orderConflictMapper.getPage(new PageAdapter(page), param);
        List<OrderConflictVo> orderConflictVos = mapperFacade.mapAsList(list, OrderConflictVo.class);
        log.info("撞单记录：{}、{}", list, orderConflictVos);
        for (OrderConflictVo item : orderConflictVos) {
            //获取相关的订单
            List<String> ddIds = Arrays.asList((item.getDdId() + "," + item.getPlanIds()).split(","));
            List<ConflictOrderVo> orderVoList = createorderMapper.getOrderConflictInfo(ddIds);
            log.info("获取相关的订单：{}、{}", ddIds, orderVoList);
            item.setConflictOrderList(orderVoList);
        }
        page.setRecords(orderConflictVos);
        page.setTotal(orderConflictMapper.countByParam(param));
        return page;

    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrderConflictVo getInfoById(String id) {
        OrderConflict item = orderConflictMapper.getInfoById(id);
        if (Objects.isNull(item)) {
            throw new ServiceException("撞单记录不存在或者已被删除！");
        }
        OrderConflictVo orderConflictVo = mapperFacade.map(item, OrderConflictVo.class);
        //获取相关的订单
        List<String> ddIds = Arrays.asList((item.getDdId() + "," + item.getPlanIds()).split(","));
        List<ConflictOrderVo> orderVoList = createorderMapper.getOrderConflictInfo(ddIds);
        log.info("获取相关的订单：{}、{}", ddIds, orderVoList);
        orderConflictVo.setConflictOrderList(orderVoList);
        return orderConflictVo;
    }

    /**
     * 撞单处理
     *
     * @param param 处理参数
     * @return 处理结果结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean deal(OrderConflictDeallParam param) {
        OrderConflict orderConflict = orderConflictMapper.selectById(param.getId());
        if (orderConflict.getStatus() == 1) {
            //已处理了
            throw new ServiceException(String.format("该撞单记录已于{}被【{}】处理，无需再重复处理", orderConflict.getDealDate(), orderConflict.getDealerName()));
        }
        String msg = "";
        if (param.getDealResult() == 0) {
            //不是撞单，将撞单记录更改为已处理
            orderConflict.setStatus(1);
            msg = "没有撞单可以提交！";
            orderConflict.setRemark(StrUtil.isBlank(param.getRemark()) ? msg : param.getRemark());
            orderConflict.setDealDate(new Date());
            orderConflict.setModifydate(new Date());
            orderConflict.setDealResult(param.getDealResult());
            orderConflict.setDealerId(SecurityUtils.getUserNo());
            orderConflict.setDealerName(SecurityUtils.getUsername());
            orderConflictMapper.updateById(orderConflict);
            //将订单更新为提交状态,统一层级有多个的不自动提交
            Createorder createorder = createorderMapper.selectOne(new LambdaQueryWrapper<Createorder>().eq(Createorder::getDdh, orderConflict.getDdId()));
            List<WorkflowItem> list = createorderService.needChoose(new NeedChooseParam(createorder.getId(),0));
            if (CollectionUtil.isEmpty( list)){
                if (R.isError(createorderService.commit(Arrays.asList(createorder.getId()), false,null))) {
                    throw new ServiceException("处理提交失败，更改订单提交状态失败！");
                }
            }
        } else {
            //是撞单，
            orderConflict.setStatus(1);
            msg = "确认撞单了，订单不能再提交！";
            orderConflict.setRemark(StrUtil.isBlank(param.getRemark()) ? msg : param.getRemark());
            orderConflict.setDealDate(new Date());
            orderConflict.setModifydate(new Date());
            orderConflict.setDealResult(param.getDealResult());
            orderConflict.setDealerId(SecurityUtils.getUserNo());
            orderConflict.setDealerName(SecurityUtils.getUsername());
            orderConflictMapper.updateById(orderConflict);
        }
        // TODO sms 发送通知，撞单处理结果
        return Boolean.TRUE;
    }

}

