package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.system.bean.model.DeployRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.vo.DeployRecordListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自动部署-更新记录(DeployRecord)数据库访问层
 *
 * @author makejava
 * @since 2023-11-15 08:28:42
 */
public interface DeployRecordMapper extends BaseMapper<DeployRecord> {

    /**
     * 分页查询[自动部署-更新记录]列表
     *
     * @param page  分页参数
     * @param param DeployRecord查询参数
     * @return 分页数据
     */
    IPage<DeployRecord> getPage(PageParam<DeployRecord> page, @Param("param") DeployRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    DeployRecord getInfoById(@Param("id") String id);

    /**
     * 根据版本信息分中心关联表id查询此中心各服务更新状态
     * @param branchId 分中心编码
     * @param versionId 版本信息id
     * @param updateType 更新类型 详见com.center.medical.bean.enums.DeployType
     * @return
     */
    List<DeployRecordListVo> selectRecordListByBranchId(
            @Param("branchId") String branchId
            ,@Param("versionId") int versionId
            ,@Param("updateType") int updateType );
}
