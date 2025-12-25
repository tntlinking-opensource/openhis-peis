package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.AnalyseTestParam;
import com.center.medical.statistics.bean.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 医生工作量(Peispatientexamitem)表数据库访问层
 *
 * @author ay
 * @since 2023-04-19 11:35:12
 */
public interface DivisionDoctorMapper extends BaseMapper<Peispatientexamitem> {

    /**
    * 分页查询[LIS结果(LisPacs数据)]列表
    *
    * @param page 分页参数
    * @param param Peispatientexamitem查询参数
    * @return 分页数据
    */
    IPage<AnalyseTestVo> getList(PageParam<AnalyseTestVo> page, @Param("param") AnalyseTestParam param);


    /**
     * 检验科科室医生工作量统计总计
     * @param page
     * @param param
     * @return
     */
    IPage<AnalyseTestVo> analyseTestTotal(PageParam<AnalyseTestVo> page,@Param("param") AnalyseTestParam param);

    /**
     * pacs科室医生工作量统计
     * @param page
     * @param param
     * @return
     */
    IPage<AnalysePacsVo> analysePacs(PageParam<AnalysePacsVo> page, @Param("param") AnalyseTestParam param);

    /**
     * pacs科室医生工作量统计总计
     * @param page
     * @param param
     * @return
     */
    IPage<AnalysePacsVo> analysePacsTotal(PageParam<AnalysePacsVo> page,@Param("param") AnalyseTestParam param);

    /**
     * 其他科室医生工作量统计
     * @param page
     * @param param
     * @return
     */
    IPage<AnalysePacsVo> analyse(PageParam<AnalysePacsVo> page,@Param("param") AnalyseTestParam param);

    /**
     * 其他科室医生工作量统计总计
     * @param page
     * @param param
     * @return
     */
    IPage<AnalysePacsVo> analyseTotal(PageParam<AnalysePacsVo> page,@Param("param") AnalyseTestParam param);

    /**
     * 导出pacs科室工作量
     * @param param
     * @return
     */
    List<ExportPacsVo> exportPacs(@Param("param") AnalyseTestParam param);

    /**
     * 导出pacs科室工作量明细
     * @param param
     * @return
     */
    List<ExportPacsInfoVo> exportPacsInfoVo(@Param("param") AnalyseTestParam param);

    /**
     * 导出其他科室工作量
     * @param param
     * @return
     */
    List<ExportOtherVo> exportOther(@Param("param") AnalyseTestParam param);

    /**
     * 导出其他科室工作量明细
     * @param param
     * @return
     */
    List<ExportOtherInfoVo> exportOtherInfo(@Param("param") AnalyseTestParam param);
}
