package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.FxSummary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 本次职业健康检查危害因素人员检查情况汇总一览表(FxSummary)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:26
 */
public interface FxSummaryService extends IService<FxSummary> {

    /**
     * 分页查询[本次职业健康检查危害因素人员检查情况汇总一览表]列表
     *
     * @param page  分页参数
     * @param param FxSummary查询参数
     * @return 分页数据
     */
    IPage<FxSummary> getList(PageParam<FxSummary> page, FxSummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxSummary getInfoById(String id);

}

