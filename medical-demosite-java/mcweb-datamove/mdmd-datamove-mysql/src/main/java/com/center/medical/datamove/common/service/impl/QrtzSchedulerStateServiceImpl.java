package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.QrtzSchedulerStateMapper;
import com.center.medical.datamove.common.bean.model.QrtzSchedulerState;
import com.center.medical.datamove.common.service.QrtzSchedulerStateService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 调度器状态表(QrtzSchedulerState)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
@Slf4j
@Service("qrtzSchedulerStateService")
@RequiredArgsConstructor
public class QrtzSchedulerStateServiceImpl extends ServiceImpl<QrtzSchedulerStateMapper, QrtzSchedulerState> implements QrtzSchedulerStateService {

    private final QrtzSchedulerStateMapper qrtzSchedulerStateMapper;

    /**
     * 分页查询[调度器状态表]列表
     *
     * @param page  分页参数
     * @param param QrtzSchedulerState查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QrtzSchedulerState> getPage(PageParam<QrtzSchedulerState> page, QrtzSchedulerState param) {
        return qrtzSchedulerStateMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    @Override
    public QrtzSchedulerState getInfoById(String id) {
        return qrtzSchedulerStateMapper.getInfoById(id);
    }

}


