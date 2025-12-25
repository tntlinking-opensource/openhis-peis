package com.center.medical.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.app.bean.model.AuthItem;

/**
 * 外部授权记录(AuthItem)服务接口
 *
 * @author makejava
 * @since 2023-09-19 11:55:00
 */
public interface AuthItemService extends IService<AuthItem> {

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AuthItem getInfoById(Integer id);

}

