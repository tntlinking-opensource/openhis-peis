package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysSqlOptionLogMapper;
import com.center.medical.datamove.common.bean.model.SysSqlOptionLog;
import com.center.medical.datamove.common.service.SysSqlOptionLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SQL操作日志表(SysSqlOptionLog)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:38
 */
@Slf4j
@Service("sysSqlOptionLogService")
@RequiredArgsConstructor
public class SysSqlOptionLogServiceImpl extends ServiceImpl<SysSqlOptionLogMapper, SysSqlOptionLog> implements SysSqlOptionLogService {

    private final SysSqlOptionLogMapper sysSqlOptionLogMapper;

    /**
     * 分页查询[SQL操作日志表]列表
     *
     * @param page  分页参数
     * @param param SysSqlOptionLog查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysSqlOptionLog> getPage(PageParam<SysSqlOptionLog> page, SysSqlOptionLog param) {
        return sysSqlOptionLogMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysSqlOptionLog getInfoById(Long id) {
        return sysSqlOptionLogMapper.getInfoById(id);
    }

}


