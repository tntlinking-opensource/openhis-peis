package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrElectroAudiometer;

import java.util.List;

/**
 * KS电测听(ElectroAudiometer)服务接口
 *
 * @author ay
 * @since 2024-06-05 15:45:03
 */
public interface OrElectroAudiometerService extends IService<OrElectroAudiometer> {

    /**
     * 分页查询[KS电测听]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrElectroAudiometer> getPage(PageParam<OrElectroAudiometer> page, OrElectroAudiometer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrElectroAudiometer getInfoById(String id);

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    List<OrElectroAudiometer> getListByPatientCode(String patientCode);
}

