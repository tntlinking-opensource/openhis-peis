package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersion;
import com.center.medical.system.bean.param.SysVersionParam;

import java.util.List;

/**
 * 版本控制-版本信息(SysVersion)服务接口
 *
 * @author makejava
 * @since 2024-03-01 18:02:36
 */
public interface SysVersionSvService extends IService<SysVersion> {

    /**
     * 分页查询[版本控制-版本信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysVersion> getPage(PageParam<SysVersion> page, SysVersionParam param);

    /**
     * 查询[版本控制-版本信息]列表
     *
     * @param param 查询参数
     * @return 分页数据
     */
    List<SysVersion> getList(SysVersionParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersion getInfoById(Integer id);

    /**
     * 获取历史版本信息列表
     *
     * @param page  分页信息
     * @param param 查询条件
     * @return 所有数据
     */
    IPage<SysVersion> getLastList(PageParam<SysVersion> page, SysVersionParam param);

    /**
     * 获取最新版本
     *
     * @return
     */
    SysVersion getlastVersion();

    Boolean saOrUp(SysVersion sysVersion);
}

