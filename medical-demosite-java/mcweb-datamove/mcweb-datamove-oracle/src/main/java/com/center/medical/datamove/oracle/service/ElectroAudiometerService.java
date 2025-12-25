package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ElectroAudiometer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS电测听(ElectroAudiometer)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:35
 */
public interface ElectroAudiometerService extends IService<ElectroAudiometer> {

    /**
     * 分页查询[KS电测听]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ElectroAudiometer> getPage(PageParam<ElectroAudiometer> page, ElectroAudiometer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ElectroAudiometer getInfoById(String id);

}

