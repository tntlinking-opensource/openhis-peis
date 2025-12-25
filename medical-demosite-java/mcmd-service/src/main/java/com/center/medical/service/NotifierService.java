package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Notifier;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * BG报告领取通知(Notifier)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:20
 */
public interface NotifierService extends IService<Notifier> {

    /**
     * 分页查询[BG报告领取通知]列表
     *
     * @param page  分页参数
     * @param param Notifier查询参数
     * @return 分页数据
     */
    IPage<Notifier> getList(PageParam<Notifier> page, Notifier param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Notifier getInfoById(String id);

}

