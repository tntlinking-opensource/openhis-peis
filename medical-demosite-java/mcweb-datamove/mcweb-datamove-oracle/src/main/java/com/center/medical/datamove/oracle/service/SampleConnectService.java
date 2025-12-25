package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.SampleConnect;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS样本交接(SampleConnect)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:32
 */
public interface SampleConnectService extends IService<SampleConnect> {

    /**
     * 分页查询[KS样本交接]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SampleConnect> getPage(PageParam<SampleConnect> page, SampleConnect param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SampleConnect getInfoById(String id);

}

