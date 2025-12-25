package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionDeploy1;

/**
 * 版本控制-更新记录(SysVersionDeploy1)服务接口
 *
 * @author makejava
 * @since 2024-01-23 10:36:18
 */
public interface SysVersionDeploy1Service extends IService<SysVersionDeploy1> {

    /**
     * 分页查询[版本控制-更新记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysVersionDeploy1> getPage(PageParam<SysVersionDeploy1> page, SysVersionDeploy1 param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersionDeploy1 getInfoById(String id);

}

