package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.WzLatestRummager;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——最近检查人(WzLatestRummager)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:30:50
 */
public interface WzLatestRummagerService extends IService<WzLatestRummager> {

    /**
     * 分页查询[KS问诊——最近检查人]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WzLatestRummager> getPage(PageParam<WzLatestRummager> page, WzLatestRummager param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzLatestRummager getInfoById(String id);

}

