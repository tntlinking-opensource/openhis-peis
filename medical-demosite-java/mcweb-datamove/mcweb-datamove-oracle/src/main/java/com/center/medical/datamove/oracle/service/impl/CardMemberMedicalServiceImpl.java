package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CardMemberMedicalMapper;
import com.center.medical.datamove.oracle.bean.model.CardMemberMedical;
import com.center.medical.datamove.oracle.service.CardMemberMedicalService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (CardMemberMedical)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:17:31
 */
@Slf4j
@Service("cardMemberMedicalService")
@RequiredArgsConstructor
public class CardMemberMedicalServiceImpl extends ServiceImpl<CardMemberMedicalMapper, CardMemberMedical> implements CardMemberMedicalService {

    private final CardMemberMedicalMapper cardMemberMedicalMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param CardMemberMedical查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CardMemberMedical> getPage(PageParam<CardMemberMedical> page, CardMemberMedical param) {
        return cardMemberMedicalMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CardMemberMedical getInfoById(String id) {
        return cardMemberMedicalMapper.getInfoById(id);
    }

}


