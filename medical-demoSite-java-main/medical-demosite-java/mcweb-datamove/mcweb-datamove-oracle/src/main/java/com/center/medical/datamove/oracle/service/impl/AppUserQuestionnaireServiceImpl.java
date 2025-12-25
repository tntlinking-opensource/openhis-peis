package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppUserQuestionnaireMapper;
import com.center.medical.datamove.oracle.bean.model.AppUserQuestionnaire;
import com.center.medical.datamove.oracle.service.AppUserQuestionnaireService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信小程序用户问卷关联表(AppUserQuestionnaire)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:10
 */
@Slf4j
@Service("appUserQuestionnaireService")
@RequiredArgsConstructor
public class AppUserQuestionnaireServiceImpl extends ServiceImpl<AppUserQuestionnaireMapper, AppUserQuestionnaire> implements AppUserQuestionnaireService {

    private final AppUserQuestionnaireMapper appUserQuestionnaireMapper;

    /**
     * 分页查询[微信小程序用户问卷关联表]列表
     *
     * @param page  分页参数
     * @param param AppUserQuestionnaire查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppUserQuestionnaire> getPage(PageParam<AppUserQuestionnaire> page, AppUserQuestionnaire param) {
        return appUserQuestionnaireMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppUserQuestionnaire getInfoById(String id) {
        return appUserQuestionnaireMapper.getInfoById(id);
    }

}


