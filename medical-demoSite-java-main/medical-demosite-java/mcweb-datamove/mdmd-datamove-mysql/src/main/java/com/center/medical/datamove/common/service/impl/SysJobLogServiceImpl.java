package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysJobLogMapper;
import com.center.medical.datamove.common.bean.model.SysJobLog;
import com.center.medical.datamove.common.service.SysJobLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务调度日志表(SysJobLog)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
@Slf4j
@Service("sysJobLogService")
@RequiredArgsConstructor
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements SysJobLogService {

    private final SysJobLogMapper sysJobLogMapper;

    /**
     * 分页查询[定时任务调度日志表]列表
     *
     * @param page  分页参数
     * @param param SysJobLog查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysJobLog> getPage(PageParam<SysJobLog> page, SysJobLog param) {
        return sysJobLogMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键jobLogId
     * @return 详情信息
     */
    @Override
    public SysJobLog getInfoById(Long id) {
        return sysJobLogMapper.getInfoById(id);
    }

}


