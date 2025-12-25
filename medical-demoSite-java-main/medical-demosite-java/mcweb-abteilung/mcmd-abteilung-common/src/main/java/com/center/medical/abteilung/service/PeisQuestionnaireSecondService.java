package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.PeisQuestionnaireSecond;
import com.center.medical.common.utils.page.PageParam;

import java.util.HashMap;

/**
 * 健康体检问卷(PeisQuestionnaireSecond)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:37
 */
public interface PeisQuestionnaireSecondService extends IService<PeisQuestionnaireSecond> {

    /**
     * 分页查询[健康体检问卷]列表
     *
     * @param page  分页参数
     * @param param PeisQuestionnaireSecond查询参数
     * @return 分页数据
     */
    IPage<PeisQuestionnaireSecond> getList(PageParam<PeisQuestionnaireSecond> page, PeisQuestionnaireSecond param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeisQuestionnaireSecond getInfoById(String id);

    /**
     * 查询
     * @param patientcode
     * @param ksId
     * @return
     */
    HashMap<String,Object> search(String patientcode, String ksId);

    /**
     * 添加健康体检问卷
     * @param peisQuestionnaireSecond
     * @return
     */
    Boolean saOrUp(PeisQuestionnaireSecond peisQuestionnaireSecond);
}

