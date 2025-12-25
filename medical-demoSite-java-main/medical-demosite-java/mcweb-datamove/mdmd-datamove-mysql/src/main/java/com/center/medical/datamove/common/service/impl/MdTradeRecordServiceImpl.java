package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdTradeRecordMapper;
import com.center.medical.datamove.common.bean.model.MdTradeRecord;
import com.center.medical.datamove.common.service.MdTradeRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 交易记录(MdTradeRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:13
 */
@Slf4j
@Service("mdTradeRecordService")
@RequiredArgsConstructor
public class MdTradeRecordServiceImpl extends ServiceImpl<MdTradeRecordMapper, MdTradeRecord> implements MdTradeRecordService {

    private final MdTradeRecordMapper mdTradeRecordMapper;

    /**
     * 分页查询[交易记录]列表
     *
     * @param page  分页参数
     * @param param MdTradeRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdTradeRecord> getPage(PageParam<MdTradeRecord> page, MdTradeRecord param) {
        return mdTradeRecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdTradeRecord getInfoById(String id) {
        return mdTradeRecordMapper.getInfoById(id);
    }

}


