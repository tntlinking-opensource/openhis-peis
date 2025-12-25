package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PeisQuestionnaireMapper;
import com.center.medical.datamove.oracle.bean.model.PeisQuestionnaire;
import com.center.medical.datamove.oracle.service.PeisQuestionnaireService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者沃德国际健康问卷(PeisQuestionnaire)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:59
 */
@Slf4j
@Service("peisQuestionnaireService")
@RequiredArgsConstructor
public class PeisQuestionnaireServiceImpl extends ServiceImpl<PeisQuestionnaireMapper, PeisQuestionnaire> implements PeisQuestionnaireService {

    private final PeisQuestionnaireMapper peisQuestionnaireMapper;

    /**
     * 分页查询[体检者沃德国际健康问卷]列表
     *
     * @param page  分页参数
     * @param param PeisQuestionnaire查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeisQuestionnaire> getPage(PageParam<PeisQuestionnaire> page, PeisQuestionnaire param) {
        return peisQuestionnaireMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeisQuestionnaire getInfoById(String id) {
        return peisQuestionnaireMapper.getInfoById(id);
    }

}


