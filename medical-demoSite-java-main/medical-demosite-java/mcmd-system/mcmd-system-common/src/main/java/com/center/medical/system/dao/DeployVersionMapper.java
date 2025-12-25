package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.system.bean.model.DeployVersion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.param.DeployVersionListParam;
import org.apache.ibatis.annotations.Param;

/**
 * 自动部署-更新版本信息(DeployVersion)数据库访问层
 *
 * @author makejava
 * @since 2023-11-15 08:42:29
 */
public interface DeployVersionMapper extends BaseMapper<DeployVersion> {

    /**
     * 分页查询[自动部署-更新版本信息]列表
     *
     * @param page  分页参数
     * @param param DeployVersion查询参数
     * @return 分页数据
     */
    IPage<DeployVersion> getPage(PageParam<DeployVersion> page, @Param("param") DeployVersionListParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    DeployVersion getInfoById(@Param("id") Integer id);

    /**
     * 根据更新类型查询最新的需要更新的版本
     * @param updateType
     * @return
     */
    DeployVersion getVersion(@Param("updateType") Integer updateType);
}
