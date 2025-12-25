package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.CardPayway;
import com.center.medical.finance.dao.CardPaywayMapper;
import com.center.medical.finance.service.CardPaywayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 卡办理收款方式表(CardPayway)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:37
 */
@Slf4j
@Service("cardPaywayService")
@RequiredArgsConstructor
public class CardPaywayServiceImpl extends ServiceImpl<CardPaywayMapper, CardPayway> implements CardPaywayService {

    private final CardPaywayMapper cardPaywayMapper;

    /**
     * 分页查询[卡办理收款方式表]列表
     *
     * @param page  分页参数
     * @param param CardPayway查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CardPayway> getList(PageParam<CardPayway> page, CardPayway param) {
        return cardPaywayMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public CardPayway getInfoById(String id) {
        return cardPaywayMapper.getInfoById(id);
    }


    /**
     * 根据卡办理id获取付款方式和收费员
     * @param processId
     * @return
     */
    @Override
    public List<CardPayway> selectFeechargerList(String processId) {
        return cardPaywayMapper.selectFeechargerList(processId);
    }
}

