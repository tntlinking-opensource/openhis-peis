package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.Report;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseAttachmentConfig;

/**
 * 基础附件配置(BaseAttachmentConfig)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:39
 */
public interface BaseAttachmentConfigService extends IService<BaseAttachmentConfig> {

    /**
     * 分页查询[基础附件配置]列表
     *
     * @param page  分页参数
     * @param param BaseAttachmentConfig查询参数
     * @return 分页数据
     */
    IPage<BaseAttachmentConfig> getList(PageParam<BaseAttachmentConfig> page, BaseAttachmentConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BaseAttachmentConfig getInfoById(String id);

    /**
     * 新增或修改数据
     * @param baseAttachmentConfig
     * @return
     */
    String saveOrUpdateBase(BaseAttachmentConfig baseAttachmentConfig);

    /**
     * 删除
     * @param ids
     * @return
     */
    String deleteBase(String ids);

    String getReportRealPath(Report report);

    BaseAttachmentConfig getLatestConfig();

    /**
     * 获取附件的地址
     * @param att
     * @return
     */
    String getAttRealPath(Attachment att);
}

