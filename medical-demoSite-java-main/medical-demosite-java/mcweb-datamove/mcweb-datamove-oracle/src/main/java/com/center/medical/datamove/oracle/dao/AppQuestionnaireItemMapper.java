package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppQuestionnaireItem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 微信小程序问卷项目(AppQuestionnaireItem)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:50
 */
public interface AppQuestionnaireItemMapper extends BaseMapper<AppQuestionnaireItem> {

    /**
     * 分页查询[微信小程序问卷项目]列表
     *
     * @param page  分页参数
     * @param param AppQuestionnaireItem查询参数
     * @return 分页数据
     */
    IPage<AppQuestionnaireItem> getPage(PageParam<AppQuestionnaireItem> page, @Param("param") AppQuestionnaireItem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppQuestionnaireItem getInfoById(@Param("id") String id);

}
