package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PeispatientConsultationMapper;
import com.center.medical.bean.model.PeispatientConsultation;
import com.center.medical.service.PeispatientConsultationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 职业问诊(PeispatientConsultation)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:27
 */
@Slf4j
@Service("peispatientConsultationService")
@RequiredArgsConstructor
public class PeispatientConsultationServiceImpl extends ServiceImpl<PeispatientConsultationMapper, PeispatientConsultation> implements PeispatientConsultationService {

    private final PeispatientConsultationMapper peispatientConsultationMapper;

    /**
     * 分页查询[职业问诊]列表
     *
     * @param page  分页参数
     * @param param PeispatientConsultation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientConsultation> getList(PageParam<PeispatientConsultation> page, PeispatientConsultation param) {
        return peispatientConsultationMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PeispatientConsultation getInfoById(String id) {
        return peispatientConsultationMapper.getInfoById(id);
    }

}

