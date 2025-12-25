package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ExamAndFzx;
import org.apache.ibatis.annotations.Param;

/**
 * 检查项目和分中心关联表(ExamAndFzx)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 10:51:34
 */
public interface ExamAndFzxMapper extends BaseMapper<ExamAndFzx> {

    /**
     * 插入公共检查项目分中心关联记录
     *
     * @param branchId
     */
    void addWithBrandId(@Param("branchId") String branchId);

    /**
     * 分页查询[检查项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ExamAndFzx查询参数
     * @return 分页数据
     */
    IPage<ExamAndFzx> getList(PageParam<ExamAndFzx> page, @Param("param") ExamAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id ExamAndFzx查询参数
     * @return 分页数据
     */
    ExamAndFzx getInfoById(@Param("id") String id);

}
