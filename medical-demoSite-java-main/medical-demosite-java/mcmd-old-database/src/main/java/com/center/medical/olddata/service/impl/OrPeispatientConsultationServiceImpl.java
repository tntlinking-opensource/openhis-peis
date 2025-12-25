package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientConsultation;
import com.center.medical.olddata.dao.OrPeispatientConsultationMapper;
import com.center.medical.olddata.service.OrPeispatientConsultationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职业问诊(PeispatientConsultation)服务实现类
 *
 * @author ay
 * @since 2024-06-05 14:59:12
 */
@Slf4j
@Service("orPeispatientConsultationService")
@RequiredArgsConstructor
public class OrPeispatientConsultationServiceImpl extends ServiceImpl<OrPeispatientConsultationMapper, OrPeispatientConsultation> implements OrPeispatientConsultationService {

    private final OrPeispatientConsultationMapper orPeispatientConsultationMapper;

    /**
     * 分页查询[职业问诊]列表
     *
     * @param page  分页参数
     * @param param PeispatientConsultation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrPeispatientConsultation> getPage(PageParam<OrPeispatientConsultation> page, OrPeispatientConsultation param) {
        return orPeispatientConsultationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrPeispatientConsultation getInfoById(String id) {
        return orPeispatientConsultationMapper.getInfoById(id);
    }

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrPeispatientConsultation> getListByPatientCode(String patientCode) {
        return orPeispatientConsultationMapper.selectList(new LambdaQueryWrapper<OrPeispatientConsultation>()
                .eq(OrPeispatientConsultation::getPatientcode,patientCode));
    }
}

