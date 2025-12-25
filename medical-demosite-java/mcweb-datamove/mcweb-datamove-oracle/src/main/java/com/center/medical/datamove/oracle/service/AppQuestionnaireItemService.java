package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppQuestionnaireItem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 微信小程序问卷项目(AppQuestionnaireItem)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:51
 */
public interface AppQuestionnaireItemService extends IService<AppQuestionnaireItem> {

    /**
     * 分页查询[微信小程序问卷项目]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppQuestionnaireItem> getPage(PageParam<AppQuestionnaireItem> page, AppQuestionnaireItem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppQuestionnaireItem getInfoById(String id);

}

