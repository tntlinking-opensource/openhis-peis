package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PacsBasexamltem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * PACS-基础检查项(PacsBasexamltem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:55
 */
public interface PacsBasexamltemService extends IService<PacsBasexamltem> {

    /**
     * 分页查询[PACS-基础检查项]列表
     *
     * @param page  分页参数
     * @param param PacsBasexamltem查询参数
     * @return 分页数据
     */
    IPage<PacsBasexamltem> getList(PageParam<PacsBasexamltem> page, PacsBasexamltem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsBasexamltem getInfoById(String id);

}

