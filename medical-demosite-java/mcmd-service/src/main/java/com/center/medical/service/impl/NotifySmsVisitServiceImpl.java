package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.NotifySmsVisitMapper;
import com.center.medical.bean.model.NotifySmsVisit;
import com.center.medical.service.NotifySmsVisitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信回访表(NotifySmsVisit)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:47
 */
@Slf4j
@Service("notifySmsVisitService")
@RequiredArgsConstructor
public class NotifySmsVisitServiceImpl extends ServiceImpl<NotifySmsVisitMapper, NotifySmsVisit> implements NotifySmsVisitService {

    private final NotifySmsVisitMapper notifySmsVisitMapper;

    /**
     * 分页查询[短信回访表]列表
     *
     * @param page  分页参数
     * @param param NotifySmsVisit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<NotifySmsVisit> getList(PageParam<NotifySmsVisit> page, NotifySmsVisit param) {
        return notifySmsVisitMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public NotifySmsVisit getInfoById(String id) {
        return notifySmsVisitMapper.getInfoById(id);
    }

}

