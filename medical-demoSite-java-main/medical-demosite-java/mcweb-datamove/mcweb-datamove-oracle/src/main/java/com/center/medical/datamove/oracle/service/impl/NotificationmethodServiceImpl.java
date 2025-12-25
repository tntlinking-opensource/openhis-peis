package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.NotificationmethodMapper;
import com.center.medical.datamove.oracle.bean.model.Notificationmethod;
import com.center.medical.datamove.oracle.service.NotificationmethodService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 通知方式（领取方式）表(Notificationmethod)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:10
 */
@Slf4j
@Service("notificationmethodService")
@RequiredArgsConstructor
public class NotificationmethodServiceImpl extends ServiceImpl<NotificationmethodMapper, Notificationmethod> implements NotificationmethodService {

    private final NotificationmethodMapper notificationmethodMapper;

    /**
     * 分页查询[通知方式（领取方式）表]列表
     *
     * @param page  分页参数
     * @param param Notificationmethod查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Notificationmethod> getPage(PageParam<Notificationmethod> page, Notificationmethod param) {
        return notificationmethodMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Notificationmethod getInfoById(String id) {
        return notificationmethodMapper.getInfoById(id);
    }

}


