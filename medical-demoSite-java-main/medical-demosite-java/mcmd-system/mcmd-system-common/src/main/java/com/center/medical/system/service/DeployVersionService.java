package com.center.medical.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.system.bean.model.DeployVersion;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.param.DeployVersionListParam;
import com.center.medical.system.bean.param.DeployVersionSaveParam;
import com.center.medical.system.bean.param.DeployVersionUpdateParam;

import java.util.List;

/**
 * 自动部署-更新版本信息(DeployVersion)服务接口
 *
 * @author makejava
 * @since 2023-11-15 08:42:32
 */
public interface DeployVersionService extends IService<DeployVersion> {

    /**
     * 分页查询[自动部署-更新版本信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<DeployVersion> getPage(PageParam<DeployVersion> page, DeployVersionListParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    DeployVersion getInfoById(Integer id);

    /**
     * 新增
     */
    void saveVersion(DeployVersionSaveParam param);

    /**
     * 更新
     * @param param
     */
    void updateVersion(DeployVersionUpdateParam param);

    /**
     * 删除
     * @param ids
     */
    void deleteVersion(List<Integer> ids);
}
