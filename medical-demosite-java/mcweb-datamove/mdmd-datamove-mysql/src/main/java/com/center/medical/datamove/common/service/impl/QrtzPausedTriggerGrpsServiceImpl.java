package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.QrtzPausedTriggerGrpsMapper;
import com.center.medical.datamove.common.bean.model.QrtzPausedTriggerGrps;
import com.center.medical.datamove.common.service.QrtzPausedTriggerGrpsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 暂停的触发器表(QrtzPausedTriggerGrps)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
@Slf4j
@Service("qrtzPausedTriggerGrpsService")
@RequiredArgsConstructor
public class QrtzPausedTriggerGrpsServiceImpl extends ServiceImpl<QrtzPausedTriggerGrpsMapper, QrtzPausedTriggerGrps> implements QrtzPausedTriggerGrpsService {

    private final QrtzPausedTriggerGrpsMapper qrtzPausedTriggerGrpsMapper;

    /**
     * 分页查询[暂停的触发器表]列表
     *
     * @param page  分页参数
     * @param param QrtzPausedTriggerGrps查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QrtzPausedTriggerGrps> getPage(PageParam<QrtzPausedTriggerGrps> page, QrtzPausedTriggerGrps param) {
        return qrtzPausedTriggerGrpsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    @Override
    public QrtzPausedTriggerGrps getInfoById(String id) {
        return qrtzPausedTriggerGrpsMapper.getInfoById(id);
    }

}


