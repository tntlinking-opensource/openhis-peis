package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PeispatientChargeRecordMapper;
import com.center.medical.bean.model.PeispatientChargeRecord;
import com.center.medical.service.PeispatientChargeRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 收费记录(PeispatientChargeRecord)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:07
 */
@Slf4j
@Service("peispatientChargeRecordService")
@RequiredArgsConstructor
public class PeispatientChargeRecordServiceImpl extends ServiceImpl<PeispatientChargeRecordMapper, PeispatientChargeRecord> implements PeispatientChargeRecordService {

    private final PeispatientChargeRecordMapper peispatientChargeRecordMapper;

    /**
     * 分页查询[收费记录]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientChargeRecord> getList(PageParam<PeispatientChargeRecord> page, PeispatientChargeRecord param) {
        return peispatientChargeRecordMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PeispatientChargeRecord getInfoById(String id) {
        return peispatientChargeRecordMapper.getInfoById(id);
    }

    ;

}

