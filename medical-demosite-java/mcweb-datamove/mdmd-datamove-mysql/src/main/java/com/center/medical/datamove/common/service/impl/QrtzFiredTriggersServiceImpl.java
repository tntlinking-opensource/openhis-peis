package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.QrtzFiredTriggersMapper;
import com.center.medical.datamove.common.bean.model.QrtzFiredTriggers;
import com.center.medical.datamove.common.service.QrtzFiredTriggersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 已触发的触发器表(QrtzFiredTriggers)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:30
 */
@Slf4j
@Service("qrtzFiredTriggersService")
@RequiredArgsConstructor
public class QrtzFiredTriggersServiceImpl extends ServiceImpl<QrtzFiredTriggersMapper, QrtzFiredTriggers> implements QrtzFiredTriggersService {

    private final QrtzFiredTriggersMapper qrtzFiredTriggersMapper;

    /**
     * 分页查询[已触发的触发器表]列表
     *
     * @param page  分页参数
     * @param param QrtzFiredTriggers查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QrtzFiredTriggers> getPage(PageParam<QrtzFiredTriggers> page, QrtzFiredTriggers param) {
        return qrtzFiredTriggersMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    @Override
    public QrtzFiredTriggers getInfoById(String id) {
        return qrtzFiredTriggersMapper.getInfoById(id);
    }

}


