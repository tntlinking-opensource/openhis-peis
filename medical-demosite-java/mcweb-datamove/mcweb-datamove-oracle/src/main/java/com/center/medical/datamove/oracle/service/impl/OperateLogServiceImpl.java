package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.OperateLogMapper;
import com.center.medical.datamove.oracle.bean.model.OperateLog;
import com.center.medical.datamove.oracle.service.OperateLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 操作日志(OperateLog)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:23
 */
@Slf4j
@Service("operateLogService")
@RequiredArgsConstructor
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements OperateLogService {

    private final OperateLogMapper operateLogMapper;

    /**
     * 分页查询[操作日志]列表
     *
     * @param page  分页参数
     * @param param OperateLog查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OperateLog> getPage(PageParam<OperateLog> page, OperateLog param) {
        return operateLogMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OperateLog getInfoById(String id) {
        return operateLogMapper.getInfoById(id);
    }

}


