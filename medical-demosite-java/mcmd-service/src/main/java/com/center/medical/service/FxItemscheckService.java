package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.FxItemscheck;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 综合分析-项目參检（健康）(FxItemscheck)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:38
 */
public interface FxItemscheckService extends IService<FxItemscheck> {

    /**
     * 分页查询[综合分析-项目參检（健康）]列表
     *
     * @param page  分页参数
     * @param param FxItemscheck查询参数
     * @return 分页数据
     */
    IPage<FxItemscheck> getList(PageParam<FxItemscheck> page, FxItemscheck param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxItemscheck getInfoById(String id);

    /**
     * 根据样本id查询
     *
     * @param analyzeId
     * @return
     */
    List<FxItemscheck> getListBySampleId(String analyzeId);
}

