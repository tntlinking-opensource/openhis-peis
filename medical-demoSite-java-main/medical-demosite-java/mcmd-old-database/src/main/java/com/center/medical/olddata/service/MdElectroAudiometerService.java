package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdElectroAudiometer;

/**
 * KS电测听(MdElectroAudiometer)服务接口
 *
 * @author ay
 * @since 2024-06-05 16:03:15
 */
public interface MdElectroAudiometerService extends IService<MdElectroAudiometer> {

    /**
     * 分页查询[KS电测听]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdElectroAudiometer> getPage(PageParam<MdElectroAudiometer> page, MdElectroAudiometer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdElectroAudiometer getInfoById(String id);

}

