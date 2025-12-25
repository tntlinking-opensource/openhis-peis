package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AttachmentConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (AttachmentConfig)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:15
 */
public interface AttachmentConfigService extends IService<AttachmentConfig> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AttachmentConfig> getPage(PageParam<AttachmentConfig> page, AttachmentConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AttachmentConfig getInfoById(String id);

}

