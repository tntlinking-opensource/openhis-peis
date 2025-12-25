package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppQuestionnaireItemMapper;
import com.center.medical.datamove.oracle.bean.model.AppQuestionnaireItem;
import com.center.medical.datamove.oracle.service.AppQuestionnaireItemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信小程序问卷项目(AppQuestionnaireItem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:51
 */
@Slf4j
@Service("appQuestionnaireItemService")
@RequiredArgsConstructor
public class AppQuestionnaireItemServiceImpl extends ServiceImpl<AppQuestionnaireItemMapper, AppQuestionnaireItem> implements AppQuestionnaireItemService {

    private final AppQuestionnaireItemMapper appQuestionnaireItemMapper;

    /**
     * 分页查询[微信小程序问卷项目]列表
     *
     * @param page  分页参数
     * @param param AppQuestionnaireItem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppQuestionnaireItem> getPage(PageParam<AppQuestionnaireItem> page, AppQuestionnaireItem param) {
        return appQuestionnaireItemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppQuestionnaireItem getInfoById(String id) {
        return appQuestionnaireItemMapper.getInfoById(id);
    }

}


