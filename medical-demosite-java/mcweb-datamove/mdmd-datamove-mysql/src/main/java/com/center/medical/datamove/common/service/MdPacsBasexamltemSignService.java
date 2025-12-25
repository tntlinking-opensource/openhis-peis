package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPacsBasexamltemSign;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * PACS-体征词(MdPacsBasexamltemSign)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:33
 */
public interface MdPacsBasexamltemSignService extends IService<MdPacsBasexamltemSign> {

    /**
     * 分页查询[PACS-体征词]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPacsBasexamltemSign> getPage(PageParam<MdPacsBasexamltemSign> page, MdPacsBasexamltemSign param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsBasexamltemSign getInfoById(String id);

}

