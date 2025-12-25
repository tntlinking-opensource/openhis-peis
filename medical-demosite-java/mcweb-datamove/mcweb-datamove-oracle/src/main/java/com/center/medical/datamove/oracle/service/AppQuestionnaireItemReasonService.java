package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppQuestionnaireItemReason;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 问卷项目推荐原因(AppQuestionnaireItemReason)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:52
 */
public interface AppQuestionnaireItemReasonService extends IService<AppQuestionnaireItemReason> {

    /**
     * 分页查询[问卷项目推荐原因]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppQuestionnaireItemReason> getPage(PageParam<AppQuestionnaireItemReason> page, AppQuestionnaireItemReason param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppQuestionnaireItemReason getInfoById(String id);

}

