package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.report.bean.param.PeispatientParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.vo.PeispatientVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:50
 */
public interface ReportPrintingMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表健康报告数据
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<PeispatientVo> getHealthReportData(PageParam<Peispatient> page, @Param("param") PeispatientParam param);

    /**
     * 分页查询[QT体检者表]列表职业报告数据
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<PeispatientVo> getDiseaseReportData(PageParam<Peispatient> page, @Param("param") PeispatientParam param);
    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 根据主键id获取记录详情
     *
     * @param patientcode
     * @return 详情信息
     */
    Peispatient getBypatientCode(@Param("patientcode")String patientcode);


    List<Peispatient> getList(@Param("param") Peispatient peispatient);

    List<Peispatient> getListByinPatientno(@Param("patientCode") String patientCode);

    /**
     * 查询有无隐私项目
     * @param patientcode
     * @return
     */
    Integer containsPrivate(@Param("patientcode")String patientcode);

    /**
     * 健康报告数据
     * @param param
     * @return
     */
    List<PeispatientVo> getHealthReportDataNew( @Param("param") PeispatientParam param);

    /**
     * 获取老系统报告数据
     * @param param
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    List<PeispatientVo> getHealthReportDataOld( @Param("param") PeispatientParam param);

    /**
     * 获取职业报告新系统数据
     * @param param
     * @return
     */
    List<PeispatientVo> getDiseaseReportDataNew( @Param("param") PeispatientParam param);

    /**
     * 获取职业报告新系统数据
     * @param param
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    List<PeispatientVo> getDiseaseReportDataOld( @Param("param") PeispatientParam param);

    /**
     * 查询是否报告隐私项目（导引单不展示的也查）
     * @param patientcode
     * @return
     */
    Integer containsAllPrivate(@Param("patientcode")String patientcode);
}
