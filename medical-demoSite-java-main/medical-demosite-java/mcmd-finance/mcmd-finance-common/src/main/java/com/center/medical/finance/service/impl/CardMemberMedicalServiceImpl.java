package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.CardMemberMedical;
import com.center.medical.finance.dao.CardMemberMedicalMapper;
import com.center.medical.finance.service.CardMemberMedicalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 会员卡体检卡关联表(CardMemberMedical)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:36
 */
@Slf4j
@Service("cardMemberMedicalService")
@RequiredArgsConstructor
public class CardMemberMedicalServiceImpl extends ServiceImpl<CardMemberMedicalMapper, CardMemberMedical> implements CardMemberMedicalService {

    private final CardMemberMedicalMapper cardMemberMedicalMapper;

    /**
     * 分页查询[会员卡体检卡关联表]列表
     *
     * @param page  分页参数
     * @param param CardMemberMedical查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CardMemberMedical> getList(PageParam<CardMemberMedical> page, CardMemberMedical param) {
        return cardMemberMedicalMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public CardMemberMedical getInfoById(String id) {
        return cardMemberMedicalMapper.getInfoById(id);
    }

}

