package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.NotifySmsVisitMapper;
import com.center.medical.datamove.oracle.bean.model.NotifySmsVisit;
import com.center.medical.datamove.oracle.service.NotifySmsVisitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信回访表(NotifySmsVisit)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:14
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
    public IPage<NotifySmsVisit> getPage(PageParam<NotifySmsVisit> page, NotifySmsVisit param) {
        return notifySmsVisitMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public NotifySmsVisit getInfoById(String id) {
        return notifySmsVisitMapper.getInfoById(id);
    }

}


