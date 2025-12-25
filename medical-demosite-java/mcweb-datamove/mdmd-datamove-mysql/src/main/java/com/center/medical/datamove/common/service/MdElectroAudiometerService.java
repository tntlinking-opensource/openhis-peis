package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdElectroAudiometer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS电测听(MdElectroAudiometer)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:15
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

