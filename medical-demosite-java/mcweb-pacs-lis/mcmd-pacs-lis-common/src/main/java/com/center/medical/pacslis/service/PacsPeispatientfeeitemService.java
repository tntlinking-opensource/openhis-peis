package com.center.medical.pacslis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacslis.bean.model.PacsPeispatientfeeitem;

import java.util.List;

/**
 * PACS-体检者收费项目表(PacsPeispatientfeeitem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:20
 */
public interface PacsPeispatientfeeitemService extends IService<PacsPeispatientfeeitem> {

    /**
     * 分页查询[PACS-体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param PacsPeispatientfeeitem查询参数
     * @return 分页数据
     */
    IPage<PacsPeispatientfeeitem> getList(PageParam<PacsPeispatientfeeitem> page, PacsPeispatientfeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsPeispatientfeeitem getInfoById(String id);

    /**
     * 获取PACS登记信息查询右侧收费项目
     * @param patientCode
     * @param type
     * @return
     */
    List<PacsPeispatientfeeitem> getExamfeeByPatientCode(String patientCode, String type);
}

