package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysOperLogMapper;
import com.center.medical.datamove.common.bean.model.SysOperLog;
import com.center.medical.datamove.common.service.SysOperLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 操作日志记录(SysOperLog)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
@Slf4j
@Service("sysOperLogService")
@RequiredArgsConstructor
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

    private final SysOperLogMapper sysOperLogMapper;

    /**
     * 分页查询[操作日志记录]列表
     *
     * @param page  分页参数
     * @param param SysOperLog查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysOperLog> getPage(PageParam<SysOperLog> page, SysOperLog param) {
        return sysOperLogMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键operId
     * @return 详情信息
     */
    @Override
    public SysOperLog getInfoById(Long id) {
        return sysOperLogMapper.getInfoById(id);
    }

}


