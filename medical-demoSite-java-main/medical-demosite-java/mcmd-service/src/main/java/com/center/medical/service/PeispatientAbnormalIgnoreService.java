package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PeispatientAbnormalIgnore;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 体检者异常忽视关联表(PeispatientAbnormalIgnore)服务接口
 *
 * @author makejava
 * @since 2024-10-12 15:36:21
 */
public interface PeispatientAbnormalIgnoreService extends IService<PeispatientAbnormalIgnore> {

    /**
     * 分页查询[体检者异常忽视关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeispatientAbnormalIgnore> getPage(PageParam<PeispatientAbnormalIgnore> page, PeispatientAbnormalIgnore param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeispatientAbnormalIgnore getInfoById(String id);

    /**
     * 忽略数据
     * @param ids
     * @return
     */
    Boolean ignore(List<String> ids);
}

