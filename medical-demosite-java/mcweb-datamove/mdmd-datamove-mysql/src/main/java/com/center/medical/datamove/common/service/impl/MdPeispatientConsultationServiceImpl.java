package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeispatientConsultationMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientConsultation;
import com.center.medical.datamove.common.service.MdPeispatientConsultationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 职业问诊(MdPeispatientConsultation)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:12
 */
@Slf4j
@Service("mdPeispatientConsultationService")
@RequiredArgsConstructor
public class MdPeispatientConsultationServiceImpl extends ServiceImpl<MdPeispatientConsultationMapper, MdPeispatientConsultation> implements MdPeispatientConsultationService {

    private final MdPeispatientConsultationMapper mdPeispatientConsultationMapper;

    /**
     * 分页查询[职业问诊]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientConsultation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatientConsultation> getPage(PageParam<MdPeispatientConsultation> page, MdPeispatientConsultation param) {
        return mdPeispatientConsultationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeispatientConsultation getInfoById(String id) {
        return mdPeispatientConsultationMapper.getInfoById(id);
    }

}


