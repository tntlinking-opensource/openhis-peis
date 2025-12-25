package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdRiskclientcon;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 高危人员关联表(MdRiskclientcon)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:20
 */
public interface MdRiskclientconService extends IService<MdRiskclientcon> {

    /**
     * 分页查询[高危人员关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdRiskclientcon> getPage(PageParam<MdRiskclientcon> page, MdRiskclientcon param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdRiskclientcon getInfoById(String id);

}

