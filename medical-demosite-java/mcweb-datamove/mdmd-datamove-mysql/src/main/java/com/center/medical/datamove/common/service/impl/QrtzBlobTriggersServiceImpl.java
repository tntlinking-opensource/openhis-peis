package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.QrtzBlobTriggersMapper;
import com.center.medical.datamove.common.bean.model.QrtzBlobTriggers;
import com.center.medical.datamove.common.service.QrtzBlobTriggersService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Blob类型的触发器表(QrtzBlobTriggers)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:30
 */
@Slf4j
@Service("qrtzBlobTriggersService")
@RequiredArgsConstructor
public class QrtzBlobTriggersServiceImpl extends ServiceImpl<QrtzBlobTriggersMapper, QrtzBlobTriggers> implements QrtzBlobTriggersService {

    private final QrtzBlobTriggersMapper qrtzBlobTriggersMapper;

    /**
     * 分页查询[Blob类型的触发器表]列表
     *
     * @param page  分页参数
     * @param param QrtzBlobTriggers查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QrtzBlobTriggers> getPage(PageParam<QrtzBlobTriggers> page, QrtzBlobTriggers param) {
        return qrtzBlobTriggersMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    @Override
    public QrtzBlobTriggers getInfoById(String id) {
        return qrtzBlobTriggersMapper.getInfoById(id);
    }

}


