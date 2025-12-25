package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxWsLogMapper;
import com.center.medical.datamove.oracle.bean.model.QxWsLog;
import com.center.medical.datamove.oracle.service.QxWsLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (QxWsLog)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:03
 */
@Slf4j
@Service("qxWsLogService")
@RequiredArgsConstructor
public class QxWsLogServiceImpl extends ServiceImpl<QxWsLogMapper, QxWsLog> implements QxWsLogService {

    private final QxWsLogMapper qxWsLogMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param QxWsLog查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxWsLog> getPage(PageParam<QxWsLog> page, QxWsLog param) {
        return qxWsLogMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxWsLog getInfoById(String id) {
        return qxWsLogMapper.getInfoById(id);
    }

}


