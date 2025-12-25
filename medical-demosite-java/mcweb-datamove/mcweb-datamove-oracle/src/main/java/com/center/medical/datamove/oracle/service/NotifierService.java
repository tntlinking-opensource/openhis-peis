package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Notifier;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * BG报告领取通知(Notifier)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:11
 */
public interface NotifierService extends IService<Notifier> {

    /**
     * 分页查询[BG报告领取通知]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Notifier> getPage(PageParam<Notifier> page, Notifier param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Notifier getInfoById(String id);

}

