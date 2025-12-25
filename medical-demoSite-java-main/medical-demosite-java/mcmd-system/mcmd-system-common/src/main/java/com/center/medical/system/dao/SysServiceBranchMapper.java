package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysServiceBranch;
import org.apache.ibatis.annotations.Param;

/**
 * 系统服务-分中心关联记录(SysServiceBranch)数据库访问层
 *
 * @author makejava
 * @since 2024-03-01 18:02:35
 */
public interface SysServiceBranchMapper extends BaseMapper<SysServiceBranch> {

    /**
     * 分页查询[系统服务-分中心关联记录]列表
     *
     * @param page  分页参数
     * @param param SysServiceBranch查询参数
     * @return 分页数据
     */
    IPage<SysServiceBranch> getPage(PageParam<SysServiceBranch> page, @Param("param") SysServiceBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysServiceBranch getInfoById(@Param("id") Integer id);

}
