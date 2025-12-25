package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeispatientConsultationMapper;
import com.center.medical.datamove.oracle.bean.model.PeispatientConsultation;
import com.center.medical.datamove.oracle.service.PeispatientConsultationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 职业问诊(PeispatientConsultation)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:19
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
    public IPage<PeispatientConsultation> getPage(PageParam<PeispatientConsultation> page, PeispatientConsultation param) {
        return peispatientConsultationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeispatientConsultation getInfoById(String id) {
        return peispatientConsultationMapper.getInfoById(id);
    }

    ;

}


