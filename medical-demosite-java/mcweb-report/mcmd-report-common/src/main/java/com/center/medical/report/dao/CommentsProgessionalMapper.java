package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.CommentsProgessional;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 职业处理意见表(CommentsProgessional)数据库访问层：这个表根据体检号进行筛选出这个体检号下所做的所有职业处理意见，这里会有多条，主要用于存储多条处理意见。这里的多条处理意见从基础数据的维护中取得
 *
 * @author 路飞船长
 * @since 2022-12-08 19:27:07
 */
public interface CommentsProgessionalMapper extends BaseMapper<CommentsProgessional> {

    /**
     * 分页查询[职业处理意见表：这个表根据体检号进行筛选出这个体检号下所做的所有职业处理意见，这里会有多条，主要用于存储多条处理意见。这里的多条处理意见从基础数据的维护中取得]列表
     *
     * @param page  分页参数
     * @param param CommentsProgessional查询参数
     * @return 分页数据
     */
    IPage<CommentsProgessional> getPage(PageParam<CommentsProgessional> page, @Param("param") CommentsProgessional param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CommentsProgessional getInfoById(@Param("id") String id);

    /**
     * 获取所有职业处理意见分类号码
     * @param patientno
     * @return
     */
    List<String> findAllSerialNo(@Param("patientno")String patientno);

    /**
     * 根据体检号获取职业处理意见表数据
     * @param patientno
     * @return
     */
    List<CommentsProgessional> getCommentsProgessionalList(@Param("patientno")String patientno);
}
