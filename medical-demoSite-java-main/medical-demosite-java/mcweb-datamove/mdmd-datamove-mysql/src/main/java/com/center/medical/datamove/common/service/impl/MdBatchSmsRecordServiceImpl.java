package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBatchSmsRecordMapper;
import com.center.medical.datamove.common.bean.model.MdBatchSmsRecord;
import com.center.medical.datamove.common.service.MdBatchSmsRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 批量发送短信记录(MdBatchSmsRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:13
 */
@Slf4j
@Service("mdBatchSmsRecordService")
@RequiredArgsConstructor
public class MdBatchSmsRecordServiceImpl extends ServiceImpl<MdBatchSmsRecordMapper, MdBatchSmsRecord> implements MdBatchSmsRecordService {

    private final MdBatchSmsRecordMapper mdBatchSmsRecordMapper;

    /**
     * 分页查询[批量发送短信记录]列表
     *
     * @param page  分页参数
     * @param param MdBatchSmsRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBatchSmsRecord> getPage(PageParam<MdBatchSmsRecord> page, MdBatchSmsRecord param) {
        return mdBatchSmsRecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBatchSmsRecord getInfoById(String id) {
        return mdBatchSmsRecordMapper.getInfoById(id);
    }

}


