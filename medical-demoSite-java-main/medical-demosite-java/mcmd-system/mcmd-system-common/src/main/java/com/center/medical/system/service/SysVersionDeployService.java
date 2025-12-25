package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionDeploy;

/**
 * 版本控制-分中心版本更新记录(SysVersionDeploy)服务接口
 *
 * @author makejava
 * @since 2024-03-01 18:02:36
 */
public interface SysVersionDeployService extends IService<SysVersionDeploy> {

    /**
     * 分页查询[版本控制-分中心版本更新记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysVersionDeploy> getPage(PageParam<SysVersionDeploy> page, SysVersionDeploy param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersionDeploy getInfoById(String id);

}

