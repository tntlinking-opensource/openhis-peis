package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdOutsideCheckin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS外送项目图片关联表(MdOutsideCheckin)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:31
 */
public interface MdOutsideCheckinService extends IService<MdOutsideCheckin> {

    /**
     * 分页查询[KS外送项目图片关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdOutsideCheckin> getPage(PageParam<MdOutsideCheckin> page, MdOutsideCheckin param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOutsideCheckin getInfoById(String id);

}

