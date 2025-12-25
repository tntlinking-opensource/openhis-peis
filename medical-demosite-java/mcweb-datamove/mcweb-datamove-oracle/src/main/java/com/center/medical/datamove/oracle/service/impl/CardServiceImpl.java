package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CardMapper;
import com.center.medical.datamove.oracle.bean.model.Card;
import com.center.medical.datamove.oracle.service.CardService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * CW卡(Card)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:29
 */
@Slf4j
@Service("cardService")
@RequiredArgsConstructor
public class CardServiceImpl extends ServiceImpl<CardMapper, Card> implements CardService {

    private final CardMapper cardMapper;

    /**
     * 分页查询[CW卡]列表
     *
     * @param page  分页参数
     * @param param Card查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Card> getPage(PageParam<Card> page, Card param) {
        return cardMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Card getInfoById(String id) {
        return cardMapper.getInfoById(id);
    }

}


