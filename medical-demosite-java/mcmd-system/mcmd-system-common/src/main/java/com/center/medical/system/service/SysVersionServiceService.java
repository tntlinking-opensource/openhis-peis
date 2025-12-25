package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionService;
import com.center.medical.system.bean.param.SysVersionExecuteParam;
import com.center.medical.system.bean.param.SysVersionServiceParam;

import java.util.List;

/**
 * 版本控制-系统服务更新包记录(SysVersionService)服务接口
 *
 * @author makejava
 * @since 2024-03-01 18:02:38
 */
public interface SysVersionServiceService extends IService<SysVersionService> {

    /**
     * 分页查询[版本控制-系统服务更新包记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysVersionService> getPage(PageParam<SysVersionService> page, SysVersionService param);

    /**
     * 查询版本控制-系统服务更新包记录列表
     *
     * @param param 查询条件
     * @return 所有数据
     */
    List<SysVersionService> getList(SysVersionServiceParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersionService getInfoById(Integer id);

    /**
     * 执行更新
     *
     * @param param 执行参数
     * @return 新增结果
     */
    Boolean execute(SysVersionExecuteParam param);
}

