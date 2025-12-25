package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdBkPatient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者(MdBkPatient)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:13
 */
public interface MdBkPatientService extends IService<MdBkPatient> {

    /**
     * 分页查询[体检者]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdBkPatient> getPage(PageParam<MdBkPatient> page, MdBkPatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBkPatient getInfoById(String id);

}

