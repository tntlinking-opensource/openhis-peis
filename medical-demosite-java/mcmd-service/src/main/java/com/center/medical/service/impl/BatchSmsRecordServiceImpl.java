package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.BatchSmsRecordMapper;
import com.center.medical.bean.model.BatchSmsRecord;
import com.center.medical.service.BatchSmsRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 批量发送短信记录(BatchSmsRecord)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:01
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
    public IPage<BatchSmsRecord> getList(PageParam<BatchSmsRecord> page, BatchSmsRecord param) {
        return batchSmsRecordMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BatchSmsRecord getInfoById(String id) {
        return batchSmsRecordMapper.getInfoById(id);
    }

}

