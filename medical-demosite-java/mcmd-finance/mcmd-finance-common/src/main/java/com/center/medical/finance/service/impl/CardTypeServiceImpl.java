package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.CardType;
import com.center.medical.finance.dao.CardTypeMapper;
import com.center.medical.finance.service.CardTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 卡类型(CardType)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:16
 */
@Slf4j
@Service("cardTypeService")
@RequiredArgsConstructor
public class CardTypeServiceImpl extends ServiceImpl<CardTypeMapper, CardType> implements CardTypeService {

    private final CardTypeMapper cardTypeMapper;

    /**
     * 分页查询[卡类型]列表
     *
     * @param page  分页参数
     * @param param CardType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CardType> getList(PageParam<CardType> page, CardType param) {
        return cardTypeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public CardType getInfoById(String id) {
        return cardTypeMapper.getInfoById(id);
    }

}

