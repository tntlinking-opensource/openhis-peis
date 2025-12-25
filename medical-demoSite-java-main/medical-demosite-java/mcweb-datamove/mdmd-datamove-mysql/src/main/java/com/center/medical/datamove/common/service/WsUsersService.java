package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.WsUsers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 网站用户(WsUsers)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:45
 */
public interface WsUsersService extends IService<WsUsers> {

    /**
     * 分页查询[网站用户]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WsUsers> getPage(PageParam<WsUsers> page, WsUsers param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WsUsers getInfoById(String id);

}

