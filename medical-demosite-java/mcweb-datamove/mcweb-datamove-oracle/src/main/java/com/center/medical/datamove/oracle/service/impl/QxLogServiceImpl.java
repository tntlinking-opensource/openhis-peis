package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.QxLogMapper;
import com.center.medical.datamove.oracle.bean.model.QxLog;
import com.center.medical.datamove.oracle.service.QxLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志(QxLog)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:47
 */
@Slf4j
@Service("qxLogService")
@RequiredArgsConstructor
public class QxLogServiceImpl extends ServiceImpl<QxLogMapper, QxLog> implements QxLogService {

    private final QxLogMapper qxLogMapper;

    /**
     * 分页查询[日志]列表
     *
     * @param page  分页参数
     * @param param QxLog查询参数
     * @return 分页数据
     */
    @Override
    public IPage<QxLog> getPage(PageParam<QxLog> page, QxLog param) {
        return qxLogMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public QxLog getInfoById(String id) {
        return qxLogMapper.getInfoById(id);
    }

}


