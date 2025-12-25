package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPacsItems;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * PACS-收费项目(MdPacsItems)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:34
 */
public interface MdPacsItemsService extends IService<MdPacsItems> {

    /**
     * 分页查询[PACS-收费项目]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPacsItems> getPage(PageParam<MdPacsItems> page, MdPacsItems param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsItems getInfoById(String id);

}

