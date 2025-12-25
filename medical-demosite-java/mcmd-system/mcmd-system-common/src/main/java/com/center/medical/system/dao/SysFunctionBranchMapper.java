package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysFunctionBranch;
import org.apache.ibatis.annotations.Param;

/**
 * 业务功能-分中心关联(SysFunctionBranch)数据库访问层
 *
 * @author makejava
 * @since 2024-03-19 11:12:09
 */
public interface SysFunctionBranchMapper extends BaseMapper<SysFunctionBranch> {

    /**
     * 分页查询[业务功能-分中心关联]列表
     *
     * @param page  分页参数
     * @param param SysFunctionBranch查询参数
     * @return 分页数据
     */
    IPage<SysFunctionBranch> getPage(PageParam<SysFunctionBranch> page, @Param("param") SysFunctionBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysFunctionBranch getInfoById(@Param("id") String id);

    /**
     * 查询该分中心是否开启该业务
     * @param functionId
     * @param branchId
     * @return
     */
    Integer getByfunIdAndCid(@Param("functionId") int functionId,@Param("branchId") String branchId);
}
