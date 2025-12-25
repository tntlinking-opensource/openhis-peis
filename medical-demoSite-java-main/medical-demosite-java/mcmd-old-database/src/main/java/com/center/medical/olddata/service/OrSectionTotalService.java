package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrSectionTotal;

import java.util.List;

/**
 * ZJ总检主表(SectionTotal)服务接口
 *
 * @author ay
 * @since 2024-06-05 14:39:41
 */
public interface OrSectionTotalService extends IService<OrSectionTotal> {

    /**
     * 分页查询[ZJ总检主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrSectionTotal> getPage(PageParam<OrSectionTotal> page, OrSectionTotal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrSectionTotal getInfoById(String id);

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    List<OrSectionTotal> getListByPatientCode(String patientCode);
}

