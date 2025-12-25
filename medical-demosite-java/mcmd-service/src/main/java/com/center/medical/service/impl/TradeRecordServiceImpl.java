package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.TradeRecordMapper;
import com.center.medical.bean.model.TradeRecord;
import com.center.medical.service.TradeRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 交易记录(TradeRecord)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:17
 */
@Slf4j
@Service("tradeRecordService")
@RequiredArgsConstructor
public class TradeRecordServiceImpl extends ServiceImpl<TradeRecordMapper, TradeRecord> implements TradeRecordService {

    private final TradeRecordMapper tradeRecordMapper;

    /**
     * 分页查询[交易记录]列表
     *
     * @param page  分页参数
     * @param param TradeRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TradeRecord> getList(PageParam<TradeRecord> page, TradeRecord param) {
        return tradeRecordMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public TradeRecord getInfoById(String id) {
        return tradeRecordMapper.getInfoById(id);
    }

}

