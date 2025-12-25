package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.PeisQuestionnaire;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者沃德国际健康问卷(PeisQuestionnaire)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:59
 */
public interface PeisQuestionnaireService extends IService<PeisQuestionnaire> {

    /**
     * 分页查询[体检者沃德国际健康问卷]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeisQuestionnaire> getPage(PageParam<PeisQuestionnaire> page, PeisQuestionnaire param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeisQuestionnaire getInfoById(String id);

}

