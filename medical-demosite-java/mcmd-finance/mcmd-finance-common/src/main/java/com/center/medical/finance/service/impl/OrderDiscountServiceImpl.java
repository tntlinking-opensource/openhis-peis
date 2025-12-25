package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.OCPageParam;
import com.center.medical.finance.bean.vo.OCPageVo;
import com.center.medical.finance.dao.OrderDiscountMapper;
import com.center.medical.finance.service.OrderDiscountService;
import com.center.medical.sellcrm.bean.model.Createorder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 团检订单折扣(Createorder)表服务实现类
 *
 * @author ay
 * @since 2023-04-04 16:53:57
 */
@Slf4j
@Service("orderDiscountService")
@RequiredArgsConstructor
public class OrderDiscountServiceImpl extends ServiceImpl<OrderDiscountMapper, Createorder> implements OrderDiscountService {

    private final OrderDiscountMapper orderDiscountMapper;

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OCPageVo> getList(PageParam<OCPageVo> page, OCPageParam param) {
        if (ObjectUtils.isEmpty(param)) {
            //初始加载页面 显示本分中心
            List<String> branchIds = new ArrayList<>();
            branchIds.add(SecurityUtils.getCId());
            param.setBranchIds(branchIds);
        }
        //不是领导 或 没有决策管理权限
        if (!(SecurityUtils.isLeader() || SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE))) {
            param.setUserNo(SecurityUtils.getUserNo());
        }
        return orderDiscountMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Createorder getInfoById(String id) {
        return orderDiscountMapper.getInfoById(id);
    }

}

