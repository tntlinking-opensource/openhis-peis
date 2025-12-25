package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSmsRecordMapper;
import com.center.medical.datamove.common.bean.model.MdSmsRecord;
import com.center.medical.datamove.common.service.MdSmsRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信发送记录(MdSmsRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:44
 */
@Slf4j
@Service("mdSmsRecordService")
@RequiredArgsConstructor
public class MdSmsRecordServiceImpl extends ServiceImpl<MdSmsRecordMapper, MdSmsRecord> implements MdSmsRecordService {

    private final MdSmsRecordMapper mdSmsRecordMapper;

    /**
     * 分页查询[短信发送记录]列表
     *
     * @param page  分页参数
     * @param param MdSmsRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSmsRecord> getPage(PageParam<MdSmsRecord> page, MdSmsRecord param) {
        return mdSmsRecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSmsRecord getInfoById(String id) {
        return mdSmsRecordMapper.getInfoById(id);
    }

}


