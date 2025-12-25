package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CardRecheckRecordMapper;
import com.center.medical.datamove.oracle.bean.model.CardRecheckRecord;
import com.center.medical.datamove.oracle.service.CardRecheckRecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (CardRecheckRecord)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:33
 */
@Slf4j
@Service("cardRecheckRecordService")
@RequiredArgsConstructor
public class CardRecheckRecordServiceImpl extends ServiceImpl<CardRecheckRecordMapper, CardRecheckRecord> implements CardRecheckRecordService {

    private final CardRecheckRecordMapper cardRecheckRecordMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param CardRecheckRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CardRecheckRecord> getPage(PageParam<CardRecheckRecord> page, CardRecheckRecord param) {
        return cardRecheckRecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CardRecheckRecord getInfoById(String id) {
        return cardRecheckRecordMapper.getInfoById(id);
    }

}


