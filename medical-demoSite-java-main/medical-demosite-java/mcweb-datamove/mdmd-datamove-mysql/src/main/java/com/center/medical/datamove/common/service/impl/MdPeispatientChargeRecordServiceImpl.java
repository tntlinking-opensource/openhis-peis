package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeispatientChargeRecordMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientChargeRecord;
import com.center.medical.datamove.common.service.MdPeispatientChargeRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 收费记录(MdPeispatientChargeRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:12
 */
@Slf4j
@Service("mdPeispatientChargeRecordService")
@RequiredArgsConstructor
public class MdPeispatientChargeRecordServiceImpl extends ServiceImpl<MdPeispatientChargeRecordMapper, MdPeispatientChargeRecord> implements MdPeispatientChargeRecordService {

    private final MdPeispatientChargeRecordMapper mdPeispatientChargeRecordMapper;

    /**
     * 分页查询[收费记录]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientChargeRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatientChargeRecord> getPage(PageParam<MdPeispatientChargeRecord> page, MdPeispatientChargeRecord param) {
        return mdPeispatientChargeRecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeispatientChargeRecord getInfoById(String id) {
        return mdPeispatientChargeRecordMapper.getInfoById(id);
    }

}


