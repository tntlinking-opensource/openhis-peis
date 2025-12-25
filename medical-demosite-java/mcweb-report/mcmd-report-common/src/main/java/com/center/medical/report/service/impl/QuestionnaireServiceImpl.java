package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Questionnaire;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.AppSatisfactionLevelParam;
import com.center.medical.member.bean.vo.AppSatisfactionLevelVo;
import com.center.medical.report.dao.QuestionnaireMapper;
import com.center.medical.report.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 小程序问卷(Questionnaire)服务实现类
 *
 * @author ay
 * @since 2023-12-05 08:57:22
 */
@Slf4j
@Service("questionnaireService")
@RequiredArgsConstructor
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements QuestionnaireService {

    private final QuestionnaireMapper questionnaireMapper;

    /**
     * 分页查询[小程序问卷]列表
     *
     * @param page  分页参数
     * @param param Questionnaire查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Questionnaire> getPage(PageParam<Questionnaire> page, Questionnaire param) {
        return questionnaireMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Questionnaire getInfoById(String id) {
        return questionnaireMapper.getInfoById(id);
    }


    /**
     * 小程序体检满意度
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<AppSatisfactionLevelVo> getAppSatisfactionLevel(PageParam<AppSatisfactionLevelVo> page, AppSatisfactionLevelParam param) {
        return questionnaireMapper.getAppSatisfactionLevel(page, param);
    }


    /**
     * 获取导出小程序体检满意度数据
     * @param param
     * @return
     */
    @Override
    public List<AppSatisfactionLevelVo> getExportData(AppSatisfactionLevelParam param) {
        List<AppSatisfactionLevelVo> list = questionnaireMapper.getExportData(param);
        int i = 1;
        for (AppSatisfactionLevelVo vo : list) {
            vo.setRownum(i++);
        }
        return list;
    }
}

