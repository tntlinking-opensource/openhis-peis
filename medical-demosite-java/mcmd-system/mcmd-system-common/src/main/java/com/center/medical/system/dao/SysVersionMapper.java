package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersion;
import com.center.medical.system.bean.param.SysVersionParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 版本控制-版本信息(SysVersion)数据库访问层
 *
 * @author makejava
 * @since 2024-03-01 18:02:36
 */
public interface SysVersionMapper extends BaseMapper<SysVersion> {

    /**
     * 分页查询[版本控制-版本信息]列表
     *
     * @param page  分页参数
     * @param param SysVersion查询参数
     * @return 分页数据
     */
    IPage<SysVersion> getPage(PageParam<SysVersion> page, @Param("param") SysVersionParam param);

    /**
     * 查询[版本控制-版本信息]列表
     *
     * @param param SysVersion查询参数
     * @return 分页数据
     */
    List<SysVersion> getList(@Param("param") SysVersionParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersion getInfoById(@Param("id") Integer id);

    /**
     * 获取历史版本信息列表
     *
     * @param page  分页信息
     * @param param 查询条件
     * @return 所有数据
     */
    IPage<SysVersion> getLastList(PageParam<SysVersion> page, @Param("param") SysVersionParam param);

    /**
     * 获取最新版本
     *
     * @return
     */
    SysVersion getlastVersion();
}
