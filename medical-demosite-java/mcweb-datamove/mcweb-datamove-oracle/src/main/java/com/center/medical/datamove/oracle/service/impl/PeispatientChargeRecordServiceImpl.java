package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeispatientChargeRecordMapper;
import com.center.medical.datamove.oracle.bean.model.PeispatientChargeRecord;
import com.center.medical.datamove.oracle.service.PeispatientChargeRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (PeispatientChargeRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:17
 */
@Slf4j
@Service("peispatientChargeRecordService")
@RequiredArgsConstructor
public class PeispatientChargeRecordServiceImpl extends ServiceImpl<PeispatientChargeRecordMapper, PeispatientChargeRecord> implements PeispatientChargeRecordService {

    private final PeispatientChargeRecordMapper peispatientChargeRecordMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientChargeRecord> getPage(PageParam<PeispatientChargeRecord> page, PeispatientChargeRecord param) {
        return peispatientChargeRecordMapper.getPage(page, param);
    }


}


