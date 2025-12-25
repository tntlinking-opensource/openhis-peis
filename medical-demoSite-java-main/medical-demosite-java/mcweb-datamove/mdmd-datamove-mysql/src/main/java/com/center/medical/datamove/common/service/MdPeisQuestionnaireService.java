package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPeisQuestionnaire;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者沃德国际健康问卷(MdPeisQuestionnaire)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:07
 */
public interface MdPeisQuestionnaireService extends IService<MdPeisQuestionnaire> {

    /**
     * 分页查询[体检者沃德国际健康问卷]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeisQuestionnaire> getPage(PageParam<MdPeisQuestionnaire> page, MdPeisQuestionnaire param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeisQuestionnaire getInfoById(String id);

}

