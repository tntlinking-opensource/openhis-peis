package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.FxReviewsituation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 本次职业健康检查复查人员复查情况一览表(FxReviewsituation)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:04
 */
public interface FxReviewsituationService extends IService<FxReviewsituation> {

    /**
     * 分页查询[本次职业健康检查复查人员复查情况一览表]列表
     *
     * @param page  分页参数
     * @param param FxReviewsituation查询参数
     * @return 分页数据
     */
    IPage<FxReviewsituation> getList(PageParam<FxReviewsituation> page, FxReviewsituation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxReviewsituation getInfoById(String id);

}

