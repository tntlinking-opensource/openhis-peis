package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Fylx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC费用类型(Fylx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:03
 */
public interface FylxService extends IService<Fylx> {

    /**
     * 分页查询[JC费用类型]列表
     *
     * @param page  分页参数
     * @param param Fylx查询参数
     * @return 分页数据
     */
    IPage<Fylx> getList(PageParam<Fylx> page, Fylx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Fylx getInfoById(String id);

}

