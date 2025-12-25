package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPacsSectionResultTwo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * PACS-科室结果表(MdPacsSectionResultTwo)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
public interface MdPacsSectionResultTwoService extends IService<MdPacsSectionResultTwo> {

    /**
     * 分页查询[PACS-科室结果表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPacsSectionResultTwo> getPage(PageParam<MdPacsSectionResultTwo> page, MdPacsSectionResultTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsSectionResultTwo getInfoById(String id);

}

