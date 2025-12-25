package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdBaseAttachmentConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 基础附件配置(MdBaseAttachmentConfig)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:09
 */
public interface MdBaseAttachmentConfigService extends IService<MdBaseAttachmentConfig> {

    /**
     * 分页查询[基础附件配置]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdBaseAttachmentConfig> getPage(PageParam<MdBaseAttachmentConfig> page, MdBaseAttachmentConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBaseAttachmentConfig getInfoById(String id);

}

