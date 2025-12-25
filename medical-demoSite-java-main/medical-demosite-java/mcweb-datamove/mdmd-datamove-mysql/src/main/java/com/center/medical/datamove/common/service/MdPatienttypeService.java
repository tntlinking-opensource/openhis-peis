package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPatienttype;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者类型(MdPatienttype)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
public interface MdPatienttypeService extends IService<MdPatienttype> {

    /**
     * 分页查询[体检者类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPatienttype> getPage(PageParam<MdPatienttype> page, MdPatienttype param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPatienttype getInfoById(String id);

}

