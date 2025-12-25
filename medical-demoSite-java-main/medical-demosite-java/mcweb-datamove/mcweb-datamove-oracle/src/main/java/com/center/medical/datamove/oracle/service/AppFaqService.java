package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppFaq;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * APP常见问题(AppFaq)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:35
 */
public interface AppFaqService extends IService<AppFaq> {

    /**
     * 分页查询[APP常见问题]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppFaq> getPage(PageParam<AppFaq> page, AppFaq param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppFaq getInfoById(String id);

}

