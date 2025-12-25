package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppInfoHits;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 小程序资讯点击记录(AppInfoHits)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:38
 */
public interface AppInfoHitsService extends IService<AppInfoHits> {

    /**
     * 分页查询[小程序资讯点击记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppInfoHits> getPage(PageParam<AppInfoHits> page, AppInfoHits param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppInfoHits getInfoById(String id);

}

