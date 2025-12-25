package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.PeisQuestionnaireSecond;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 健康体检问卷(PeisQuestionnaireSecond)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:00
 */
public interface PeisQuestionnaireSecondService extends IService<PeisQuestionnaireSecond> {

    /**
     * 分页查询[健康体检问卷]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeisQuestionnaireSecond> getPage(PageParam<PeisQuestionnaireSecond> page, PeisQuestionnaireSecond param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeisQuestionnaireSecond getInfoById(String id);

}

