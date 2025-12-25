package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppCustomerServiceMapper;
import com.center.medical.datamove.oracle.bean.model.AppCustomerService;
import com.center.medical.datamove.oracle.service.AppCustomerServiceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 环信客服人员(AppCustomerService)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:27
 */
@Slf4j
@Service("appCustomerServiceService")
@RequiredArgsConstructor
public class AppCustomerServiceServiceImpl extends ServiceImpl<AppCustomerServiceMapper, AppCustomerService> implements AppCustomerServiceService {

    private final AppCustomerServiceMapper appCustomerServiceMapper;

    /**
     * 分页查询[环信客服人员]列表
     *
     * @param page  分页参数
     * @param param AppCustomerService查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppCustomerService> getPage(PageParam<AppCustomerService> page, AppCustomerService param) {
        return appCustomerServiceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppCustomerService getInfoById(String id) {
        return appCustomerServiceMapper.getInfoById(id);
    }

}


