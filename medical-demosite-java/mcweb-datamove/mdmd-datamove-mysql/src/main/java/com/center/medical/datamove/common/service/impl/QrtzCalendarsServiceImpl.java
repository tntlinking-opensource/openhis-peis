package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.QrtzCalendarsMapper;
import com.center.medical.datamove.common.bean.model.QrtzCalendars;
import com.center.medical.datamove.common.service.QrtzCalendarsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 日历信息表(QrtzCalendars)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:30
 */
@Slf4j
@Service("qrtzCalendarsService")
@RequiredArgsConstructor
public class QrtzCalendarsServiceImpl extends ServiceImpl<QrtzCalendarsMapper, QrtzCalendars> implements QrtzCalendarsService {

    private final QrtzCalendarsMapper qrtzCalendarsMapper;

    /**
     * 分页查询[日历信息表]列表
     *
     * @param page  分页参数
     * @param param QrtzCalendars查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QrtzCalendars> getPage(PageParam<QrtzCalendars> page, QrtzCalendars param) {
        return qrtzCalendarsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    @Override
    public QrtzCalendars getInfoById(String id) {
        return qrtzCalendarsMapper.getInfoById(id);
    }

}


