package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.CardRecheckRecord;
import com.center.medical.finance.dao.CardRecheckRecordMapper;
import com.center.medical.finance.service.CardRecheckRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 十周年卡复查金额记录表(CardRecheckRecord)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:36
 */
@Slf4j
@Service("cardRecheckRecordService")
@RequiredArgsConstructor
public class CardRecheckRecordServiceImpl extends ServiceImpl<CardRecheckRecordMapper, CardRecheckRecord> implements CardRecheckRecordService {

    private final CardRecheckRecordMapper cardRecheckRecordMapper;

    /**
     * 分页查询[十周年卡复查金额记录表]列表
     *
     * @param page  分页参数
     * @param param CardRecheckRecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CardRecheckRecord> getList(PageParam<CardRecheckRecord> page, CardRecheckRecord param) {
        return cardRecheckRecordMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public CardRecheckRecord getInfoById(String id) {
        return cardRecheckRecordMapper.getInfoById(id);
    }

}

