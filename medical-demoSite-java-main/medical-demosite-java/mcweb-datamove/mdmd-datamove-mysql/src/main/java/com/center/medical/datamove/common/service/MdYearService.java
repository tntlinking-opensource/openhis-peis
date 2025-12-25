package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdYear;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 年份表(MdYear)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:27
 */
public interface MdYearService extends IService<MdYear> {

    /**
     * 分页查询[年份表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdYear> getPage(PageParam<MdYear> page, MdYear param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdYear getInfoById(String id);

}

