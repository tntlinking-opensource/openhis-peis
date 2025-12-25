package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.core.domain.entity.SysNoticeConfig;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.param.NoticeConfigPageParam;
import com.center.medical.system.bean.param.NoticeConfigSaOrUpParam;

import java.util.List;

/**
 * 通知配置表(SysNoticeConfig)服务接口
 *
 * @author makejava
 * @since 2025-02-26 16:42:50
 */
public interface SysNoticeConfigService extends IService<SysNoticeConfig> {

    /**
     * 分页查询[通知配置表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysNoticeConfig> getPage(PageParam<SysNoticeConfig> page, NoticeConfigPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysNoticeConfig getInfoById(String id);

    /**
     * 添加或更新
     * @param params
     * @return
     */
    boolean saOrUp(NoticeConfigSaOrUpParam params);

    /**
     * 获取需要发送通知的用户编号
     * @param value
     * @return
     */
    List<String> getUserNoById(String id);
}

