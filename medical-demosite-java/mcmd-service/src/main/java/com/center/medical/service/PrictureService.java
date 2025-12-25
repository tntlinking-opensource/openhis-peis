package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Pricture;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS图片存储表(Pricture)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:05
 */
public interface PrictureService extends IService<Pricture> {

    /**
     * 分页查询[KS图片存储表]列表
     *
     * @param page  分页参数
     * @param param Pricture查询参数
     * @return 分页数据
     */
    IPage<Pricture> getList(PageParam<Pricture> page, Pricture param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Pricture getInfoById(String id);

}

