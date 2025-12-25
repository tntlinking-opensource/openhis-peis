package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BatchSmsRecordMapper;
import com.center.medical.datamove.oracle.bean.model.BatchSmsRecord;
import com.center.medical.datamove.oracle.service.BatchSmsRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 批量发送短信记录(BatchSmsRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:48
 */
@Slf4j
@Service("batchSmsRecordService")
@RequiredArgsConstructor
public class BatchSmsRecordServiceImpl extends ServiceImpl<BatchSmsRecordMapper, BatchSmsRecord> implements BatchSmsRecordService {

    private final BatchSmsRecordMapper batchSmsRecordMapper;

    /**
     * 分页查询[批量发送短信记录]列表
     *
     * @param page  分页参数
     * @param param BatchSmsRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BatchSmsRecord> getPage(PageParam<BatchSmsRecord> page, BatchSmsRecord param) {
        return batchSmsRecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BatchSmsRecord getInfoById(String id) {
        return batchSmsRecordMapper.getInfoById(id);
    }

}


