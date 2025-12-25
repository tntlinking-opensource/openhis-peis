package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.SellRemind;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (SellRemind)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:50
 */
public interface SellRemindService extends IService<SellRemind> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SellRemind> getPage(PageParam<SellRemind> page, SellRemind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SellRemind getInfoById(String id);

}

