package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.PositiveQueryParam;
import com.center.medical.statistics.bean.vo.PositiveQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 职业健康检查结果结论附表(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
public interface PositiveQueryMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<PositiveQueryVo> getList(PageParam<PositiveQueryVo> page, @Param("param") PositiveQueryParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 是否是最后一次体检
     * @param patientcode
     * @return
     */
    String isFinalPatient(@Param("patientcode") String patientcode);

    /**
     * 查询检查项目信息
     * @param patientcode
     * @return
     */
    String getReviewItemNames(@Param("patientcode") String patientcode);

    /**
     * 导出单位职业健康检查结果附表
     * @param param
     * @return
     */
    List<PositiveQueryVo> getExportData(@Param("param") PositiveQueryParam param);
}
