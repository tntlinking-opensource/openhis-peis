package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFxItemscheck;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 综合分析-项目參检（健康）(MdFxItemscheck)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
public interface MdFxItemscheckService extends IService<MdFxItemscheck> {

    /**
     * 分页查询[综合分析-项目參检（健康）]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFxItemscheck> getPage(PageParam<MdFxItemscheck> page, MdFxItemscheck param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxItemscheck getInfoById(String id);

}

