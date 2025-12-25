package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrSectionResultMain;

import java.util.List;

/**
 * KS科室检查结果主表(SectionResultMain)服务接口
 *
 * @author ay
 * @since 2024-06-05 14:39:40
 */
public interface OrSectionResultMainService extends IService<OrSectionResultMain> {

    /**
     * 分页查询[KS科室检查结果主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrSectionResultMain> getPage(PageParam<OrSectionResultMain> page, OrSectionResultMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrSectionResultMain getInfoById(String id);

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    List<OrSectionResultMain> getListByPatientCode(String patientCode);
}

