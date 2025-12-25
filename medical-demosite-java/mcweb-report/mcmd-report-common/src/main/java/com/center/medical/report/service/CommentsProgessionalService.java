package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.CommentsProgessional;

/**
 * 职业处理意见(CommentsProgessional)表服务接口：这个表根据体检号进行筛选出这个体检号下所做的所有职业处理意见，这里会有多条，主要用于存储多条处理意见。这里的多条处理意见从基础数据的维护中取得
 *
 * @author 路飞船长
 * @since 2022-12-08 19:27:08
 */
public interface CommentsProgessionalService extends IService<CommentsProgessional> {

    /**
     * 分页查询[职业处理意见表：这个表根据体检号进行筛选出这个体检号下所做的所有职业处理意见，这里会有多条，主要用于存储多条处理意见。这里的多条处理意见从基础数据的维护中取得]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CommentsProgessional> getPage(PageParam<CommentsProgessional> page, CommentsProgessional param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CommentsProgessional getInfoById(String id);

}

