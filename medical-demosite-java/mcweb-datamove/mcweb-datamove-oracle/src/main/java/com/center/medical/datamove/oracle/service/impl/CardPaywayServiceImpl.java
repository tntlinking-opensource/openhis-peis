package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CardPaywayMapper;
import com.center.medical.datamove.oracle.bean.model.CardPayway;
import com.center.medical.datamove.oracle.service.CardPaywayService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * QT卡办理收款方式表(CardPayway)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:32
 */
@Slf4j
@Service("cardPaywayService")
@RequiredArgsConstructor
public class CardPaywayServiceImpl extends ServiceImpl<CardPaywayMapper, CardPayway> implements CardPaywayService {

    private final CardPaywayMapper cardPaywayMapper;

    /**
     * 分页查询[QT卡办理收款方式表]列表
     *
     * @param page  分页参数
     * @param param CardPayway查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CardPayway> getPage(PageParam<CardPayway> page, CardPayway param) {
        return cardPaywayMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CardPayway getInfoById(String id) {
        return cardPaywayMapper.getInfoById(id);
    }

}


