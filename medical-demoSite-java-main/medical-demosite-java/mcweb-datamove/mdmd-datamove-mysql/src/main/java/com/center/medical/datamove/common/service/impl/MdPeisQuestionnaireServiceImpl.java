package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeisQuestionnaireMapper;
import com.center.medical.datamove.common.bean.model.MdPeisQuestionnaire;
import com.center.medical.datamove.common.service.MdPeisQuestionnaireService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者沃德国际健康问卷(MdPeisQuestionnaire)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:07
 */
@Slf4j
@Service("mdPeisQuestionnaireService")
@RequiredArgsConstructor
public class MdPeisQuestionnaireServiceImpl extends ServiceImpl<MdPeisQuestionnaireMapper, MdPeisQuestionnaire> implements MdPeisQuestionnaireService {

    private final MdPeisQuestionnaireMapper mdPeisQuestionnaireMapper;

    /**
     * 分页查询[体检者沃德国际健康问卷]列表
     *
     * @param page  分页参数
     * @param param MdPeisQuestionnaire查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeisQuestionnaire> getPage(PageParam<MdPeisQuestionnaire> page, MdPeisQuestionnaire param) {
        return mdPeisQuestionnaireMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeisQuestionnaire getInfoById(String id) {
        return mdPeisQuestionnaireMapper.getInfoById(id);
    }

}


