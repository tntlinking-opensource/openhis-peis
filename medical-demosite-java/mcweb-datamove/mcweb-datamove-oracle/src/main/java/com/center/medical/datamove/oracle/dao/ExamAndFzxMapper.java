package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ExamAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC检查项目和分中心关联表(ExamAndFzx)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:37
 */
public interface ExamAndFzxMapper extends BaseMapper<ExamAndFzx> {

    /**
     * 分页查询[JC检查项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ExamAndFzx查询参数
     * @return 分页数据
     */
    IPage<ExamAndFzx> getPage(PageParam<ExamAndFzx> page, @Param("param") ExamAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ExamAndFzx getInfoById(@Param("id") String id);

}
