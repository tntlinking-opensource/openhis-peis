package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysUserBranch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户关联的分中心(SysUserBranch)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-05-20 15:27:58
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

    /**
     * 根据用户编号获取分中心
     * @param userNo
     * @return
     */
    List<String> getByUserNo(@Param("userNo") String userNo);
}
