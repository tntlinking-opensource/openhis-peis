package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SysUploadLogMapper;
import com.center.medical.datamove.oracle.bean.model.SysUploadLog;
import com.center.medical.datamove.oracle.service.SysUploadLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据上传接收日志(SysUploadLog)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:08
 */
@Slf4j
@Service("sysUploadLogService")
@RequiredArgsConstructor
public class SysUploadLogServiceImpl extends ServiceImpl<SysUploadLogMapper, SysUploadLog> implements SysUploadLogService {

    private final SysUploadLogMapper sysUploadLogMapper;

    /**
     * 分页查询[数据上传接收日志]列表
     *
     * @param page  分页参数
     * @param param SysUploadLog查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysUploadLog> getPage(PageParam<SysUploadLog> page, SysUploadLog param) {
        return sysUploadLogMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysUploadLog getInfoById(String id) {
        return sysUploadLogMapper.getInfoById(id);
    }

}


