package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.SurrenderDocuments;
import com.center.medical.bean.param.SurrenderDocumentParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.SurrenderDocumentsMapper;
import com.center.medical.service.SurrenderDocumentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 交单记录表(SurrenderDocuments)服务实现类
 *
 * @author ay
 * @since 2024-01-04 15:58:02
 */
@Slf4j
@Service("surrenderDocumentsService")
@RequiredArgsConstructor
public class SurrenderDocumentsServiceImpl extends ServiceImpl<SurrenderDocumentsMapper, SurrenderDocuments> implements SurrenderDocumentsService {

    private final SurrenderDocumentsMapper surrenderDocumentsMapper;

    /**
     * 分页查询[交单记录表]列表
     *
     * @param page  分页参数
     * @param param SurrenderDocuments查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SurrenderDocuments> getPage(PageParam<SurrenderDocuments> page, SurrenderDocumentParam param) {
        return surrenderDocumentsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SurrenderDocuments getInfoById(String id) {
        return surrenderDocumentsMapper.getInfoById(id);
    }

    /**
     * 通过体检号查询详情
     * @param patientCode
     * @return
     */
    @Override
    public SurrenderDocuments getByPatientCode(String patientCode) {
        return surrenderDocumentsMapper.getByPatientCode(patientCode);
    }
}

