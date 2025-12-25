package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysUserBranch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户关联的分中心(SysUserBranch)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:39
 */
public interface SysUserBranchMapper extends BaseMapper<SysUserBranch> {

    /**
     * 分页查询[系统用户关联的分中心]列表
     *
     * @param page  分页参数
     * @param param SysUserBranch查询参数
     * @return 分页数据
     */
    IPage<SysUserBranch> getPage(PageParam<SysUserBranch> page, @Param("param") SysUserBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysUserBranch getInfoById(@Param("id") String id);

}
