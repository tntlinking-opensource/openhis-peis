package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.QrtzSimpleTriggersMapper;
import com.center.medical.datamove.common.bean.model.QrtzSimpleTriggers;
import com.center.medical.datamove.common.service.QrtzSimpleTriggersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 简单触发器的信息表(QrtzSimpleTriggers)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:32
 */
@Slf4j
@Service("qrtzSimpleTriggersService")
@RequiredArgsConstructor
public class QrtzSimpleTriggersServiceImpl extends ServiceImpl<QrtzSimpleTriggersMapper, QrtzSimpleTriggers> implements QrtzSimpleTriggersService {

    private final QrtzSimpleTriggersMapper qrtzSimpleTriggersMapper;

    /**
     * 分页查询[简单触发器的信息表]列表
     *
     * @param page  分页参数
     * @param param QrtzSimpleTriggers查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QrtzSimpleTriggers> getPage(PageParam<QrtzSimpleTriggers> page, QrtzSimpleTriggers param) {
        return qrtzSimpleTriggersMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    @Override
    public QrtzSimpleTriggers getInfoById(String id) {
        return qrtzSimpleTriggersMapper.getInfoById(id);
    }

}


