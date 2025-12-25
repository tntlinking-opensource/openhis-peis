package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.FxReviewInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 职业健康检查职业病危害效应相关指标异常需要复查人员(FxReviewInfo)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:38
 */
public interface FxReviewInfoService extends IService<FxReviewInfo> {

    /**
     * 分页查询[职业健康检查职业病危害效应相关指标异常需要复查人员]列表
     *
     * @param page  分页参数
     * @param param FxReviewInfo查询参数
     * @return 分页数据
     */
    IPage<FxReviewInfo> getList(PageParam<FxReviewInfo> page, FxReviewInfo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxReviewInfo getInfoById(String id);

}

