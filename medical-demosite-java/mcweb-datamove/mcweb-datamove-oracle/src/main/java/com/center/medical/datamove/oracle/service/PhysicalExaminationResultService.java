package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.PhysicalExaminationResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检结果表（处理后）(PhysicalExaminationResult)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:35
 */
public interface PhysicalExaminationResultService extends IService<PhysicalExaminationResult> {

    /**
     * 分页查询[体检结果表（处理后）]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PhysicalExaminationResult> getPage(PageParam<PhysicalExaminationResult> page, PhysicalExaminationResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PhysicalExaminationResult getInfoById(String id);

}

