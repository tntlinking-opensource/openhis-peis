package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.FxDetection;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 综合分析-检出统计、团体小结（健康）(FxDetection)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
public interface FxDetectionService extends IService<FxDetection> {

    /**
     * 分页查询[综合分析-检出统计、团体小结（健康）]列表
     *
     * @param page  分页参数
     * @param param FxDetection查询参数
     * @return 分页数据
     */
    IPage<FxDetection> getList(PageParam<FxDetection> page, FxDetection param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxDetection getInfoById(String id);

    /**
     * 通过检验id查询综合分析
     * @param analyzeId
     * @return
     */
    List<FxDetection> findFxDetection(String analyzeId);
}

