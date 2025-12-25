package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.QrtzLocksMapper;
import com.center.medical.datamove.common.bean.model.QrtzLocks;
import com.center.medical.datamove.common.service.QrtzLocksService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 存储的悲观锁信息表(QrtzLocks)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
@Slf4j
@Service("qrtzLocksService")
@RequiredArgsConstructor
public class QrtzLocksServiceImpl extends ServiceImpl<QrtzLocksMapper, QrtzLocks> implements QrtzLocksService {

    private final QrtzLocksMapper qrtzLocksMapper;

    /**
     * 分页查询[存储的悲观锁信息表]列表
     *
     * @param page  分页参数
     * @param param QrtzLocks查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QrtzLocks> getPage(PageParam<QrtzLocks> page, QrtzLocks param) {
        return qrtzLocksMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    @Override
    public QrtzLocks getInfoById(String id) {
        return qrtzLocksMapper.getInfoById(id);
    }

}


