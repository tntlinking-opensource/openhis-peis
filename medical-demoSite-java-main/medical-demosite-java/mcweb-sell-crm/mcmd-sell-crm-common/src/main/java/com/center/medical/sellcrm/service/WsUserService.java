package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.WsUser;
import com.center.medical.common.utils.page.PageParam;

/**
 * 用户信息表(WsUser)服务接口
 *
 * @author makejava
 * @since 2024-09-12 09:29:50
 */
public interface WsUserService extends IService<WsUser> {

    /**
     * 分页查询[用户信息表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WsUser> getPage(PageParam<WsUser> page, WsUser param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键userId
     * @return 详情信息
     */
    WsUser getInfoById(Long id);

}

