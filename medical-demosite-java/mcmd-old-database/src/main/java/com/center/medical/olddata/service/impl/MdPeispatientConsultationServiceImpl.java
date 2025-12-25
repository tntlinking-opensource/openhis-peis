package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientConsultation;
import com.center.medical.olddata.dao.MdPeispatientConsultationMapper;
import com.center.medical.olddata.service.MdPeispatientConsultationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 职业问诊(MdPeispatientConsultation)服务实现类
 *
 * @author ay
 * @since 2024-06-05 15:18:05
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

