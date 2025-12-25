package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysAuthLog;
import com.center.medical.system.bean.param.SysAuthLogParam;

/**
 * 系统授权记录(SysAuthLog)服务接口
 *
 * @author makejava
 * @since 2024-01-17 20:20:03
 */
public interface SysAuthLogService extends IService<SysAuthLog> {

    /**
     * 分页查询[系统授权记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysAuthLog> getPage(PageParam<SysAuthLog> page, SysAuthLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysAuthLog getInfoById(Long id);

    /**
     * 生成授权信息
     *
     * @param param 参数
     * @return 加密信息
     */
    SysAuthLog generate(SysAuthLogParam param);
}

