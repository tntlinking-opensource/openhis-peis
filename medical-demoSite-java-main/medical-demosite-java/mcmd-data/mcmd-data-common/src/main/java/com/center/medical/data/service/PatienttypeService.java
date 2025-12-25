package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Patienttype;

/**
 * 体检者类型(Patienttype)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-07 19:27:27
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

    /**
     * 获取体检者等级名称
     * @param patient
     * @return
     */
    String getIdPatientClass(Peispatient patient);
}

