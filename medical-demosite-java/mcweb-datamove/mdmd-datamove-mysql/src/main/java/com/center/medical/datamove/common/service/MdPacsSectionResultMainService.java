package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPacsSectionResultMain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * PACS-检查结果主表(MdPacsSectionResultMain)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:35
 */
public interface MdPacsSectionResultMainService extends IService<MdPacsSectionResultMain> {

    /**
     * 分页查询[PACS-检查结果主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPacsSectionResultMain> getPage(PageParam<MdPacsSectionResultMain> page, MdPacsSectionResultMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsSectionResultMain getInfoById(String id);

}

