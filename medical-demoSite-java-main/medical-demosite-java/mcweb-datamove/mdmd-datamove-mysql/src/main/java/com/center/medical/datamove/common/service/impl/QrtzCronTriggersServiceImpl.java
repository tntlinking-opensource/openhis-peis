package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.QrtzCronTriggersMapper;
import com.center.medical.datamove.common.bean.model.QrtzCronTriggers;
import com.center.medical.datamove.common.service.QrtzCronTriggersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Cron类型的触发器表(QrtzCronTriggers)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:30
 */
@Slf4j
@Service("qrtzCronTriggersService")
@RequiredArgsConstructor
public class QrtzCronTriggersServiceImpl extends ServiceImpl<QrtzCronTriggersMapper, QrtzCronTriggers> implements QrtzCronTriggersService {

    private final QrtzCronTriggersMapper qrtzCronTriggersMapper;

    /**
     * 分页查询[Cron类型的触发器表]列表
     *
     * @param page  分页参数
     * @param param QrtzCronTriggers查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QrtzCronTriggers> getPage(PageParam<QrtzCronTriggers> page, QrtzCronTriggers param) {
        return qrtzCronTriggersMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    @Override
    public QrtzCronTriggers getInfoById(String id) {
        return qrtzCronTriggersMapper.getInfoById(id);
    }

}


