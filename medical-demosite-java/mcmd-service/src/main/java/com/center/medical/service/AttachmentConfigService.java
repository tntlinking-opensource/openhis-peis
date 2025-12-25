package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.AttachmentConfig;
import com.center.medical.common.utils.page.PageParam;

/**
 * (AttachmentConfig)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:22
 */
public interface AttachmentConfigService extends IService<AttachmentConfig> {

    /**
     * 分页查询[]列表
     *
     * @param page 分页参数
     * @return 分页数据
     */
    IPage<AttachmentConfig> getPage(PageParam<AttachmentConfig> page);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    AttachmentConfig getInfoById(String id);

    /**
     * 添加或修改
     *
     * @param attachmentConfig
     * @return
     */
    Boolean saOrUp(AttachmentConfig attachmentConfig);

    /**
     * 获取查询路径
     *
     * @param attachment
     * @return
     */
    String getAttVisitPath(Attachment attachment);

    /**
     * 获取设置
     *
     * @return
     */
    AttachmentConfig getLatestConfig();

    /**
     * 获取附件地址
     * @param att
     * @return
     */
    String getPath(Attachment att);
}

