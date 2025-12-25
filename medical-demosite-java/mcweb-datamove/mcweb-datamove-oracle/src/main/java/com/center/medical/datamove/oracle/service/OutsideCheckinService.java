package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.OutsideCheckin;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS外送项目图片关联表(OutsideCheckin)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:31
 */
public interface OutsideCheckinService extends IService<OutsideCheckin> {

    /**
     * 分页查询[KS外送项目图片关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OutsideCheckin> getPage(PageParam<OutsideCheckin> page, OutsideCheckin param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OutsideCheckin getInfoById(String id);

}

