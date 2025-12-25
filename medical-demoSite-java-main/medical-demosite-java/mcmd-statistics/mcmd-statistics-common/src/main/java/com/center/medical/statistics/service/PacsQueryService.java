package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacslis.bean.model.PacsPeispatient;
import com.center.medical.statistics.bean.param.AnalyseTestParam;
import com.center.medical.statistics.bean.param.PacsQueryParam;
import com.center.medical.statistics.bean.vo.AnalysePacsVo;
import com.center.medical.statistics.bean.vo.ExportTotalVo;
import com.center.medical.statistics.bean.vo.PacsQueryVo;

import java.util.List;
import java.util.Map;

/**
 * PACS-体检者表(PacsPeispatient)服务接口
 *
 * @author ay
 * @since 2023-09-16 16:37:07
 */
public interface PacsQueryService extends IService<PacsPeispatient> {

    /**
     * 分页查询[PACS-体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PacsQueryVo> getPage(PageParam<PacsQueryVo> page, PacsQueryParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PacsPeispatient getInfoById(String id);

    /**
     * 获取图表数据
     * @param param
     * @return
     */
    Map getTableData(PacsQueryParam param);


    /**
     * 导出科室工作量统计
     * @param param
     * @return
     */
    List<PacsQueryVo> getExportData(PacsQueryParam param);

    /**
     * 获取图表数据
     * @param param
     * @return
     */
    Map getPacsDoctorTableData(AnalyseTestParam param);

    /**
     * 导出工作量统计
     * @param param
     * @return
     */
    List<AnalysePacsVo> exportWorkData(AnalyseTestParam param);

    /**
     * 导出工作量统计总计
     * @param param
     * @return
     */
    List<ExportTotalVo> exportTotal(AnalyseTestParam param);
}

