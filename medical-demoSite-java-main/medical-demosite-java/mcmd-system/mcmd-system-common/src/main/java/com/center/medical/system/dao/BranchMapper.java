package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.param.BranchParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.vo.CenterOrgNameListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分中心维护表(Branch)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 15:23:09
 */
public interface BranchMapper extends BaseMapper<Branch> {

    /**
     * 分页查询[分中心维护表]列表
     *
     * @param page  分页参数
     * @param param Branch查询参数
     * @return 分页数据
     */
    IPage<Branch> getList(PageParam<Branch> page, @Param("param") BranchParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id
     */
    Branch getInfoById(@Param("id") Integer id);

    /**
     * 根据branchId获取记录详情
     *
     * @param branchId
     */
    Branch getInfoByBranchId(@Param("branchId") String branchId);

    /**
     * 获取套餐的分中心
     * @param id
     * @return
     */
    List<Branch> getBranchData(@Param("id")String id);

    /**
     * 批量查询
     * @param branchIds
     * @return
     */
    List<Branch> getListBranch(@Param("branchIds") List<String> branchIds);

    /**
     * 根据机构门店id获取记录详情
     * @param hospitalSubId
     * @return
     */
    Branch getInfoByHospitalSubId(@Param("hospitalSubId") String hospitalSubId);

    /**
     * 获取当前账号下所有所属组织
     * @param userNo
     * @return
     */
    List<CenterOrgNameListVo> getUserCenterOrgNameList(@Param("userNo") String userNo);
}
