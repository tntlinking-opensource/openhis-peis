package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.vo.CenterListDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分中心管理Mapper接口
 *
 * @author 路飞
 * @date 2022-10-21
 */
public interface SysBranchMapper extends BaseMapper<SysBranch> {

    /**
     * 查询分中心管理列表
     *
     * @param param 分中心管理
     * @return 分中心管理集合
     */
    IPage<SysBranch> getList(PageParam<SysBranch> page, @Param("param") SysBranch param);


    /**
     * 通过id获取分中心管理
     *
     * @param id 分中心管理
     * @return 分中心管理集合
     */
    SysBranch getInfoById(@Param("id") String id);

    /**
     * 通过userNo获取部门id和简码
     * @param curUserId
     * @return
     */
    List<SysBranch> getCenterPrefix(@Param("curuserid")String curUserId);

    /**
     * 获取默认的cid
     * @return
     */
    String getDefaultCid();

    /**
     * 获取默认的分中心
     * @return
     */
    SysBranch getDefaultBranch();

    /**
     * 登录页面分中心数据
     * @return
     */
    List<CenterListDataVo> centerListData();

    /**
     * 通过branchId获取分中心
     * @param branchId
     * @return
     */
    SysBranch getByBranchId(@Param("branchId") String branchId);

    /**
     * 通过分中心名称查询
     * @param name
     * @return
     */
    SysBranch getByBranchName(@Param("fzx") String name);
}
