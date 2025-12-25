package com.center.medical.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.bean.dto.DeployVersionDto;
import com.center.medical.system.bean.model.DeployVersionBranch;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.bean.param.AutoDeploySaveRecordParam;

import java.util.List;

/**
 * 自动部署-版本更新分中心关联表(DeployVersionBranch)服务接口
 *
 * @author makejava
 * @since 2023-11-15 08:58:49
 */
public interface DeployVersionBranchService extends IService<DeployVersionBranch> {

    /**
     * 分页查询[自动部署-版本更新分中心关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<DeployVersionBranch> getPage(PageParam<DeployVersionBranch> page, DeployVersionBranch param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    DeployVersionBranch getInfoById(Integer id);

    /**
     * 自动更新-获取更新版本信息
     * @param ksIp
     * @return
     */
    DeployVersionDto getVersion(String ksIp,int updateType);

    /**
     * 保存更新记录
     * @param autoDeploySaveRecordParam
     */
    void saveRecord(AutoDeploySaveRecordParam autoDeploySaveRecordParam);

    /**
     * 根据版本信息id查询各分中心更新状态
     * @param id deployVersion.id
     * @return
     */
    List<DeployVersionBranch> selectBranchListByVersionId(int id);
}
