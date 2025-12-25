package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TradeRecordMapper;
import com.center.medical.datamove.oracle.bean.model.TradeRecord;
import com.center.medical.datamove.oracle.service.TradeRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (TradeRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:40
 */
@Slf4j
@Service("tradeRecordService")
@RequiredArgsConstructor
public class TradeRecordServiceImpl extends ServiceImpl<TradeRecordMapper, TradeRecord> implements TradeRecordService {

    private final TradeRecordMapper tradeRecordMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param TradeRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TradeRecord> getPage(PageParam<TradeRecord> page, TradeRecord param) {
        return tradeRecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public TradeRecord getInfoById(String id) {
        return tradeRecordMapper.getInfoById(id);
    }

}


