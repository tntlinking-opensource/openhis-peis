package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFxDetectionzy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * TJ综合分析-检出人数（职业）(MdFxDetectionzy)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
public interface MdFxDetectionzyService extends IService<MdFxDetectionzy> {

    /**
     * 分页查询[TJ综合分析-检出人数（职业）]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFxDetectionzy> getPage(PageParam<MdFxDetectionzy> page, MdFxDetectionzy param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxDetectionzy getInfoById(String id);

}

