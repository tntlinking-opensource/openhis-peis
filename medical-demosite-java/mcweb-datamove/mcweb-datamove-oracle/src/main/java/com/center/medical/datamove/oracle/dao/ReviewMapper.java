package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Review;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * ZJ复查表(Review)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:21
 */
public interface ReviewMapper extends BaseMapper<Review> {

    /**
     * 分页查询[ZJ复查表]列表
     *
     * @param page  分页参数
     * @param param Review查询参数
     * @return 分页数据
     */
    IPage<Review> getPage(PageParam<Review> page, @Param("param") Review param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Review getInfoById(@Param("id") String id);

}
