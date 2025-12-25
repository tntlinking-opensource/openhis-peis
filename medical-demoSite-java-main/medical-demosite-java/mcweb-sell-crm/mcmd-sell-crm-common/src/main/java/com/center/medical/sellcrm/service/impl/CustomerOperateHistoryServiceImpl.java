package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.CustomerOperateHistory;
import com.center.medical.sellcrm.bean.param.CustomerOperateHistoryParam;
import com.center.medical.sellcrm.dao.CustomerOperateHistoryMapper;
import com.center.medical.sellcrm.service.CustomerOperateHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * 客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放(CustomerOperateHistory)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:24
 */
@Slf4j
@Service("customerOperateHistoryService")
@RequiredArgsConstructor
public class CustomerOperateHistoryServiceImpl extends ServiceImpl<CustomerOperateHistoryMapper, CustomerOperateHistory> implements CustomerOperateHistoryService {

    private final CustomerOperateHistoryMapper customerOperateHistoryMapper;

    /**
     * 分页查询[客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放]列表
     *
     * @param page  分页参数
     * @param customerOperateHistoryParam CustomerOperateHistory查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CustomerOperateHistory> getList(PageParam<CustomerOperateHistory> page, CustomerOperateHistoryParam customerOperateHistoryParam) {
        //开始日期不为空加上时间
        if (ObjectUtils.isNotEmpty(customerOperateHistoryParam.getStartTime())){
            DateTime startDateTime = DateUtil.beginOfDay(customerOperateHistoryParam.getStartTime());
            customerOperateHistoryParam.setStartTime(startDateTime);
        }
        //结束时间不为空加上时间
        if (ObjectUtils.isNotEmpty(customerOperateHistoryParam.getEndTime())){
            DateTime endDateTime = DateUtil.endOfDay(customerOperateHistoryParam.getEndTime());
            customerOperateHistoryParam.setEndTime(endDateTime);
        }
        return customerOperateHistoryMapper.getList(page, customerOperateHistoryParam);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public CustomerOperateHistory getInfoById(String id) {
        return customerOperateHistoryMapper.getInfoById(id);
    }

    ;

}

