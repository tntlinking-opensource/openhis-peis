package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.dto.WsUsers;
import com.center.medical.common.utils.page.PageParam;

/**
 * 网站用户(WsUsers)服务接口
 *
 * @author ay
 * @since 2024-05-29 16:31:10
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

