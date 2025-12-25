package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Pricture;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS图片存储表(Pricture)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:37
 */
public interface PrictureService extends IService<Pricture> {

    /**
     * 分页查询[KS图片存储表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Pricture> getPage(PageParam<Pricture> page, Pricture param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Pricture getInfoById(String id);

}

