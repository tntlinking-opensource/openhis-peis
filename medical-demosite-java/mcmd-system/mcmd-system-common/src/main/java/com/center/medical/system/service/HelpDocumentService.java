package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.HelpDocument;

/**
 * 帮助文档(HelpDocument)服务接口
 *
 * @author ay
 * @since 2024-04-24 13:56:58
 */
public interface HelpDocumentService extends IService<HelpDocument> {

    /**
     * 分页查询[帮助文档]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<HelpDocument> getPage(PageParam<HelpDocument> page, HelpDocument param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    HelpDocument getInfoById(String id);

}

