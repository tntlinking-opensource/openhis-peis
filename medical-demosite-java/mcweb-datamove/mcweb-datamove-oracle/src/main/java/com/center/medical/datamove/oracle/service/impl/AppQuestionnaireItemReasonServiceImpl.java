package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppQuestionnaireItemReasonMapper;
import com.center.medical.datamove.oracle.bean.model.AppQuestionnaireItemReason;
import com.center.medical.datamove.oracle.service.AppQuestionnaireItemReasonService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 问卷项目推荐原因(AppQuestionnaireItemReason)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:53
 */
@Slf4j
@Service("appQuestionnaireItemReasonService")
@RequiredArgsConstructor
public class AppQuestionnaireItemReasonServiceImpl extends ServiceImpl<AppQuestionnaireItemReasonMapper, AppQuestionnaireItemReason> implements AppQuestionnaireItemReasonService {

    private final AppQuestionnaireItemReasonMapper appQuestionnaireItemReasonMapper;

    /**
     * 分页查询[问卷项目推荐原因]列表
     *
     * @param page  分页参数
     * @param param AppQuestionnaireItemReason查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppQuestionnaireItemReason> getPage(PageParam<AppQuestionnaireItemReason> page, AppQuestionnaireItemReason param) {
        return appQuestionnaireItemReasonMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppQuestionnaireItemReason getInfoById(String id) {
        return appQuestionnaireItemReasonMapper.getInfoById(id);
    }

}


