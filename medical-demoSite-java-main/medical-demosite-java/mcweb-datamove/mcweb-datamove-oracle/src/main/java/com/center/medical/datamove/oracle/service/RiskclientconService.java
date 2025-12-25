package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Riskclientcon;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 高危人员关联表(Riskclientcon)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:28
 */
public interface RiskclientconService extends IService<Riskclientcon> {

    /**
     * 分页查询[高危人员关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Riskclientcon> getPage(PageParam<Riskclientcon> page, Riskclientcon param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Riskclientcon getInfoById(String id);

}

