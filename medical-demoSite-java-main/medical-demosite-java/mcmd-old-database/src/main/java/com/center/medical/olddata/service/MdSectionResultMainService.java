package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdSectionResultMain;

import java.util.List;

/**
 * KS科室检查结果主表(MdSectionResultMain)服务接口
 *
 * @author ay
 * @since 2023-11-10 14:27:19
 */
public interface MdSectionResultMainService extends IService<MdSectionResultMain> {

    /**
     * 分页查询[KS科室检查结果主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSectionResultMain> getPage(PageParam<MdSectionResultMain> page, MdSectionResultMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSectionResultMain getInfoById(String id);

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    List<MdSectionResultMain> getListByPatientCode(String patientCode);
}

