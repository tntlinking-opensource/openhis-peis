package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionNotity;

/**
 * 版本控制-新版本通知记录(SysVersionNotity)服务接口
 *
 * @author makejava
 * @since 2024-04-26 10:52:08
 */
public interface SysVersionNotityService extends IService<SysVersionNotity> {

    /**
     * 分页查询[版本控制-新版本通知记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysVersionNotity> getPage(PageParam<SysVersionNotity> page, SysVersionNotity param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersionNotity getInfoById(Integer id);

}

