package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Review;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * ZJ复查表(Review)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:22
 */
public interface ReviewService extends IService<Review> {

    /**
     * 分页查询[ZJ复查表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Review> getPage(PageParam<Review> page, Review param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Review getInfoById(String id);

}

