package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.system.bean.model.DeployVersionBranch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自动部署-版本更新分中心关联表(DeployVersionBranch)数据库访问层
 *
 * @author makejava
 * @since 2023-11-15 08:58:47
 */
public interface DeployVersionBranchMapper extends BaseMapper<DeployVersionBranch> {

    /**
     * 分页查询[自动部署-版本更新分中心关联表]列表
     *
     * @param page  分页参数
     * @param param DeployVersionBranch查询参数
     * @return 分页数据
     */
    IPage<DeployVersionBranch> getPage(PageParam<DeployVersionBranch> page, @Param("param") DeployVersionBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    DeployVersionBranch getInfoById(@Param("id") Integer id);

    /**
     * 查询未更新的科室数量
     * @param updateType
     * @param versionId
     * @return
     */
    int selectUnUpdatedCount(@Param("updateType") Integer updateType,@Param("versionId") Integer versionId,@Param("branchId") String branchId);

    /**
     * 根据版本信息id查询各分中心更新状态
     * @param id 版本信息id
     * @return
     */
    List<DeployVersionBranch> selectBranchListByVersionId(@Param("id") int id);

    /**
     * 查询需要更新sql语句的记录
     * @param branchId
     * @return
     */
    List<DeployVersionBranch> selectSqlUpdateList(@Param("branchId") String branchId);
}
