package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.AnalyseTestParam;
import com.center.medical.statistics.bean.vo.*;

import java.util.List;

/**
 * 医生工作量(Peispatientexamitem)表服务接口
 *
 * @author ay
 * @since 2023-04-19 11:35:12
 */
public interface DivisionDoctorService extends IService<Peispatientexamitem> {

    /**
    * 分页查询[LIS结果(LisPacs数据)]列表
    *
    * @param page 分页参数
    * @param param 查询参数
    * @return 分页数据
    */
    IPage<AnalyseTestVo> getList(PageParam<AnalyseTestVo> page, AnalyseTestParam param);


    /**
     * 检验科科室医生工作量统计总计
     * @param page
     * @param param
     * @return
     */
    IPage<AnalyseTestVo> analyseTestTotal(PageParam<AnalyseTestVo> page, AnalyseTestParam param);

    /**
     * pacs科室医生工作量统计
     * @param page
     * @param param
     * @return
     */
    IPage<AnalysePacsVo> analysePacs(PageParam<AnalysePacsVo> page, AnalyseTestParam param);

    /**
     * pacs科室医生工作量统计总计
     * @param page
     * @param param
     * @return
     */
    IPage<AnalysePacsVo> analysePacsTotal(PageParam<AnalysePacsVo> page, AnalyseTestParam param);

    /**
     * 其他科室医生工作量统计
     * @param page
     * @param param
     * @return
     */
    IPage<AnalysePacsVo> analyse(PageParam<AnalysePacsVo> page, AnalyseTestParam param);

    /**
     * 其他科室医生工作量统计总计
     * @param page
     * @param param
     * @return
     */
    IPage<AnalysePacsVo> analyseTotal(PageParam<AnalysePacsVo> page, AnalyseTestParam param);

    /**
     * 导出pacs科室工作量
     * @param param
     * @return
     */
    List<ExportPacsVo> exportPacs(AnalyseTestParam param);


    /**
     * 导出pacs科室工作量明细
     * @param param
     * @return
     */
    List<ExportPacsInfoVo> exportPacsInfoVo(AnalyseTestParam param);

    /**
     * 导出其他科室工作量
     * @param param
     * @return
     */
    List<ExportOtherVo> exportOther(AnalyseTestParam param);

    /**
     * 导出其他科室工作量明细
     * @param param
     * @return
     */
    List<ExportOtherInfoVo> exportOtherInfo(AnalyseTestParam param);
}

