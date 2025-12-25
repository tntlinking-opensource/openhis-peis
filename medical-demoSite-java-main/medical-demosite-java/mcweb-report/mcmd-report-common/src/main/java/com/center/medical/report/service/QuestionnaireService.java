package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Questionnaire;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.AppSatisfactionLevelParam;
import com.center.medical.member.bean.vo.AppSatisfactionLevelVo;

import java.util.List;

/**
 * 小程序问卷(Questionnaire)服务接口
 *
 * @author ay
 * @since 2023-12-05 08:57:22
 */
public interface QuestionnaireService extends IService<Questionnaire> {

    /**
     * 分页查询[小程序问卷]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Questionnaire> getPage(PageParam<Questionnaire> page, Questionnaire param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Questionnaire getInfoById(String id);

    /**
     * 小程序体检满意度
     * @param page
     * @param param
     * @return
     */
    IPage<AppSatisfactionLevelVo> getAppSatisfactionLevel(PageParam<AppSatisfactionLevelVo> page, AppSatisfactionLevelParam param);

    /**
     * 获取导出小程序体检满意度数据
     * @param param
     * @return
     */
    List<AppSatisfactionLevelVo> getExportData(AppSatisfactionLevelParam param);
}

