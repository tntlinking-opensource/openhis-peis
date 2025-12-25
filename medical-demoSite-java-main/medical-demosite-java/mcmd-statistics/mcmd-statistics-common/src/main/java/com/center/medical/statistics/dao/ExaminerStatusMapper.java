package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.ExaminerStatusParam;
import com.center.medical.statistics.bean.vo.ExaminerStatusVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检状态统计(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
public interface ExaminerStatusMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<ExaminerStatusVo> getList(PageParam<ExaminerStatusVo> page, @Param("param") ExaminerStatusParam param);


    /**
     * 导出体检者状态统计
     * @param param
     * @return
     */
    List<ExaminerStatusVo> exportData(@Param("param") ExaminerStatusParam param);
}
