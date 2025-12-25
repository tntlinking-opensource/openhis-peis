package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.param.SmsDataParam;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.dao.SmsRecordMapper;
import com.center.medical.bean.model.SmsRecord;
import com.center.medical.service.SmsRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信发送记录(SmsRecord)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:12
 */
@Slf4j
@Service("smsRecordService")
@RequiredArgsConstructor
public class SmsRecordServiceImpl extends ServiceImpl<SmsRecordMapper, SmsRecord> implements SmsRecordService {

    private final SmsRecordMapper smsRecordMapper;

    /**
     * 分页查询[短信发送记录]列表
     *
     * @param page  分页参数
     * @param param SmsRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SmsRecord> getList(PageParam<SmsRecord> page, SmsRecord param) {
        return smsRecordMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public SmsRecord getInfoById(String id) {
        return smsRecordMapper.getInfoById(id);
    }


    /**
     * 查看短信数据
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<SmsRecord> getSmsData(PageParam<SmsRecord> page, SmsDataParam param) {
        //设置条件
        QueryWrapper<SmsRecord> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(param.getNotifyType())){
            queryWrapper.eq("notify_type",param.getNotifyType());
        }
        if(StringUtils.isNotEmpty(param.getPatientcode())){
            queryWrapper.eq("patientcode",param.getPatientcode());
        }
        if(StringUtils.isNotEmpty(param.getArchiveId())){
            queryWrapper.eq("archive_id",param.getArchiveId());
        }

        PageParam<SmsRecord> notifyTime = smsRecordMapper.selectPage(page, queryWrapper.orderByDesc("notify_time"));
        return notifyTime;
    }
}

