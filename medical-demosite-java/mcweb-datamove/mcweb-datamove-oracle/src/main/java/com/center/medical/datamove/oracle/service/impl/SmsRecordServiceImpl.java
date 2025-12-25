package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SmsRecordMapper;
import com.center.medical.datamove.oracle.bean.model.SmsRecord;
import com.center.medical.datamove.oracle.service.SmsRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KF短信通知记录(SmsRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:01
 */
@Slf4j
@Service("smsRecordService")
@RequiredArgsConstructor
public class SmsRecordServiceImpl extends ServiceImpl<SmsRecordMapper, SmsRecord> implements SmsRecordService {

    private final SmsRecordMapper smsRecordMapper;

    /**
     * 分页查询[KF短信通知记录]列表
     *
     * @param page  分页参数
     * @param param SmsRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SmsRecord> getPage(PageParam<SmsRecord> page, SmsRecord param) {
        return smsRecordMapper.getPage(page, param);
    }


}


