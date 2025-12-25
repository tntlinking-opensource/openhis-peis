package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Patienttype;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者类型(Patienttype)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:56
 */
public interface PatienttypeService extends IService<Patienttype> {

    /**
     * 分页查询[体检者类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Patienttype> getPage(PageParam<Patienttype> page, Patienttype param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Patienttype getInfoById(String id);

}

