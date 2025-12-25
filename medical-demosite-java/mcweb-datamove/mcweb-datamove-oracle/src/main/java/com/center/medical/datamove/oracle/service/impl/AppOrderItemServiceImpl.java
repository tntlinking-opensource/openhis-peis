package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppOrderItemMapper;
import com.center.medical.datamove.oracle.bean.model.AppOrderItem;
import com.center.medical.datamove.oracle.service.AppOrderItemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 小程序微信订单项目(AppOrderItem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:48
 */
@Slf4j
@Service("appOrderItemService")
@RequiredArgsConstructor
public class AppOrderItemServiceImpl extends ServiceImpl<AppOrderItemMapper, AppOrderItem> implements AppOrderItemService {

    private final AppOrderItemMapper appOrderItemMapper;

    /**
     * 分页查询[小程序微信订单项目]列表
     *
     * @param page  分页参数
     * @param param AppOrderItem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppOrderItem> getPage(PageParam<AppOrderItem> page, AppOrderItem param) {
        return appOrderItemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppOrderItem getInfoById(String id) {
        return appOrderItemMapper.getInfoById(id);
    }

}


