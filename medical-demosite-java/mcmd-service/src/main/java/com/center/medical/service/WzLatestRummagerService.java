package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.WzLatestRummager;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——最近检查人(WzLatestRummager)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:38
 */
public interface WzLatestRummagerService extends IService<WzLatestRummager> {

    /**
     * 分页查询[KS问诊——最近检查人]列表
     *
     * @param page  分页参数
     * @param param WzLatestRummager查询参数
     * @return 分页数据
     */
    IPage<WzLatestRummager> getList(PageParam<WzLatestRummager> page, WzLatestRummager param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzLatestRummager getInfoById(String id);

}

