package com.center.medical.reception.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.ReviewProject;

import java.util.List;

/**
 * ZJ复查项目表(ReviewProject)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-22 20:52:28
 */
public interface ReviewProjectService extends IService<ReviewProject> {

    /**
     * 分页查询[ZJ复查项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReviewProject> getPage(PageParam<ReviewProject> page, ReviewProject param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReviewProject getInfoById(String id);

    /**
     * 根据复查表id获取复查收费项目
     * @param reviewId
     * @return
     */
    List<ReviewProject> getDataByPeople(String reviewId);
}

