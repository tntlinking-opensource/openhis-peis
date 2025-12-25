package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.QrtzSimpropTriggersMapper;
import com.center.medical.datamove.common.bean.model.QrtzSimpropTriggers;
import com.center.medical.datamove.common.service.QrtzSimpropTriggersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步机制的行锁表(QrtzSimpropTriggers)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:32
 */
@Slf4j
@Service("qrtzSimpropTriggersService")
@RequiredArgsConstructor
public class QrtzSimpropTriggersServiceImpl extends ServiceImpl<QrtzSimpropTriggersMapper, QrtzSimpropTriggers> implements QrtzSimpropTriggersService {

    private final QrtzSimpropTriggersMapper qrtzSimpropTriggersMapper;

    /**
     * 分页查询[同步机制的行锁表]列表
     *
     * @param page  分页参数
     * @param param QrtzSimpropTriggers查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QrtzSimpropTriggers> getPage(PageParam<QrtzSimpropTriggers> page, QrtzSimpropTriggers param) {
        return qrtzSimpropTriggersMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    @Override
    public QrtzSimpropTriggers getInfoById(String id) {
        return qrtzSimpropTriggersMapper.getInfoById(id);
    }

}


