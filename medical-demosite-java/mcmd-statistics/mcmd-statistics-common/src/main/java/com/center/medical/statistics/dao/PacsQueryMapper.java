package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacslis.bean.model.PacsPeispatient;
import com.center.medical.statistics.bean.dto.PQTableDataDto;
import com.center.medical.statistics.bean.param.AnalyseTestParam;
import com.center.medical.statistics.bean.param.PacsQueryParam;
import com.center.medical.statistics.bean.vo.AnalysePacsVo;
import com.center.medical.statistics.bean.vo.ExportTotalVo;
import com.center.medical.statistics.bean.vo.PacsQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PACS-体检者表(PacsPeispatient)数据库访问层
 *
 * @author ay
 * @since 2023-09-16 16:37:07
 */
public interface PacsQueryMapper extends BaseMapper<PacsPeispatient> {

    /**
     * 分页查询[PACS-体检者表]列表
     *
     * @param page  分页参数
     * @param param PacsPeispatient查询参数
     * @return 分页数据
     */
    IPage<PacsQueryVo> getPage(PageParam<PacsQueryVo> page, @Param("param") PacsQueryParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PacsPeispatient getInfoById(@Param("id") String id);

    /**
     * 查询pacs科室图表数据
     * @param param
     * @return
     */
    List<PQTableDataDto> getTableData(@Param("param") PacsQueryParam param);

    /**
     * 导出科室工作量统计
     * @param param
     * @return
     */
    List<PacsQueryVo> getExportData(@Param("param") PacsQueryParam param);

    /**
     * 获取图表数据
     * @param param
     * @return
     */
    List<PQTableDataDto> getPacsDoctorTableData(@Param("param") AnalyseTestParam param);

    /**
     * 导出工作量统计
     * @param param
     * @return
     */
    List<AnalysePacsVo> exportWorkData(@Param("param") AnalyseTestParam param);

    /**
     * 导出工作量统计总计
     * @param param
     * @return
     */
    List<ExportTotalVo> exportTotal(@Param("param") AnalyseTestParam param);
}
