package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPacsBasexamltem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * PACS-基础检查项(MdPacsBasexamltem)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:32
 */
public interface MdPacsBasexamltemService extends IService<MdPacsBasexamltem> {

    /**
     * 分页查询[PACS-基础检查项]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPacsBasexamltem> getPage(PageParam<MdPacsBasexamltem> page, MdPacsBasexamltem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsBasexamltem getInfoById(String id);

}

