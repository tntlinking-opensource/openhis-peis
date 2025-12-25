package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdBasMerge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 合并结伦词(MdBasMerge)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:08
 */
public interface MdBasMergeService extends IService<MdBasMerge> {

    /**
     * 分页查询[合并结伦词]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdBasMerge> getPage(PageParam<MdBasMerge> page, MdBasMerge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBasMerge getInfoById(String id);

}

