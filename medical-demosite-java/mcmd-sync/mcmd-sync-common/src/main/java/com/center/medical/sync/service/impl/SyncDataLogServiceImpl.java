package com.center.medical.sync.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sync.bean.model.SyncDataLog;
import com.center.medical.sync.dao.SyncDataLogMapper;
import com.center.medical.sync.service.SyncDataLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 同步数据操作记录(SyncDataLog)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:33
 */
@Slf4j
@Service("syncLcDataLogService")
@RequiredArgsConstructor
public class SyncDataLogServiceImpl extends ServiceImpl<SyncDataLogMapper, SyncDataLog> implements SyncDataLogService {

    private final SyncDataLogMapper syncDataLogMapper;

    /**
     * 分页查询[同步数据操作记录]列表
     *
     * @param page  分页参数
     * @param param SyncDataLog查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SyncDataLog> getPage(PageParam<SyncDataLog> page, SyncDataLog param) {
        return syncDataLogMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键logId
     * @return 详情信息
     */
    @Override
    public SyncDataLog getInfoById(Long id) {
        return syncDataLogMapper.getInfoById(id);
    }

}

