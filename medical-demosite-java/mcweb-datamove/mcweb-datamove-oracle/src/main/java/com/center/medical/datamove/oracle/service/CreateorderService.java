package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Createorder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 订单表(Createorder)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:17:59
 */
public interface CreateorderService extends IService<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Createorder> getPage(PageParam<Createorder> page, Createorder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createorder getInfoById(String id);

}

