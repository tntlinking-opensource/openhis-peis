package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdNuclein;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 核酸检测报告上传记录(MdNuclein)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:28
 */
public interface MdNucleinService extends IService<MdNuclein> {

    /**
     * 分页查询[核酸检测报告上传记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdNuclein> getPage(PageParam<MdNuclein> page, MdNuclein param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdNuclein getInfoById(String id);

}

