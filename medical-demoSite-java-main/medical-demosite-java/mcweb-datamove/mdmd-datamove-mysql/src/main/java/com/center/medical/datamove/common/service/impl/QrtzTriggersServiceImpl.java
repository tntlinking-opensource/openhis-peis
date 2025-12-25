package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.QrtzTriggersMapper;
import com.center.medical.datamove.common.bean.model.QrtzTriggers;
import com.center.medical.datamove.common.service.QrtzTriggersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 触发器详细信息表(QrtzTriggers)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:33
 */
@Slf4j
@Service("qrtzTriggersService")
@RequiredArgsConstructor
public class QrtzTriggersServiceImpl extends ServiceImpl<QrtzTriggersMapper, QrtzTriggers> implements QrtzTriggersService {

    private final QrtzTriggersMapper qrtzTriggersMapper;

    /**
     * 分页查询[触发器详细信息表]列表
     *
     * @param page  分页参数
     * @param param QrtzTriggers查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QrtzTriggers> getPage(PageParam<QrtzTriggers> page, QrtzTriggers param) {
        return qrtzTriggersMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    @Override
    public QrtzTriggers getInfoById(String id) {
        return qrtzTriggersMapper.getInfoById(id);
    }

}


