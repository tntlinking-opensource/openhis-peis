package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CardTypeMapper;
import com.center.medical.datamove.oracle.bean.model.CardType;
import com.center.medical.datamove.oracle.service.CardTypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * CW卡类型(CardType)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:34
 */
@Slf4j
@Service("cardTypeService")
@RequiredArgsConstructor
public class CardTypeServiceImpl extends ServiceImpl<CardTypeMapper, CardType> implements CardTypeService {

    private final CardTypeMapper cardTypeMapper;

    /**
     * 分页查询[CW卡类型]列表
     *
     * @param page  分页参数
     * @param param CardType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CardType> getPage(PageParam<CardType> page, CardType param) {
        return cardTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CardType getInfoById(String id) {
        return cardTypeMapper.getInfoById(id);
    }

}


