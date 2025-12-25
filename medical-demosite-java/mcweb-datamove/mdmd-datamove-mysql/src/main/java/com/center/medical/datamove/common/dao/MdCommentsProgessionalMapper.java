package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdCommentsProgessional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 职业处理意见表：这个表根据体检号进行筛选出这个体检号下所做的所有职业处理意见，这里会有多条，主要用于存储多条处理意见。这里的多条处理意见从基础数据的维护中取得(MdCommentsProgessional)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:17
 */
public interface MdCommentsProgessionalMapper extends BaseMapper<MdCommentsProgessional> {

    /**
     * 分页查询[职业处理意见表：这个表根据体检号进行筛选出这个体检号下所做的所有职业处理意见，这里会有多条，主要用于存储多条处理意见。这里的多条处理意见从基础数据的维护中取得]列表
     *
     * @param page  分页参数
     * @param param MdCommentsProgessional查询参数
     * @return 分页数据
     */
    IPage<MdCommentsProgessional> getPage(PageParam<MdCommentsProgessional> page, @Param("param") MdCommentsProgessional param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCommentsProgessional getInfoById(@Param("id") String id);

}
