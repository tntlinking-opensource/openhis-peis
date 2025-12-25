package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPacsPdf;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * PACS PDF  海康医院使用(MdPacsPdf)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:34
 */
public interface MdPacsPdfService extends IService<MdPacsPdf> {

    /**
     * 分页查询[PACS PDF  海康医院使用]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPacsPdf> getPage(PageParam<MdPacsPdf> page, MdPacsPdf param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsPdf getInfoById(String id);

}

