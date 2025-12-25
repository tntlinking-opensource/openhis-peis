package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppOrderMapper;
import com.center.medical.datamove.oracle.bean.model.AppOrder;
import com.center.medical.datamove.oracle.service.AppOrderService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 小程序微信订单(AppOrder)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:47
 */
@Slf4j
@Service("appOrderService")
@RequiredArgsConstructor
public class AppOrderServiceImpl extends ServiceImpl<AppOrderMapper, AppOrder> implements AppOrderService {

    private final AppOrderMapper appOrderMapper;

    /**
     * 分页查询[小程序微信订单]列表
     *
     * @param page  分页参数
     * @param param AppOrder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppOrder> getPage(PageParam<AppOrder> page, AppOrder param) {
        return appOrderMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppOrder getInfoById(String id) {
        return appOrderMapper.getInfoById(id);
    }

}


