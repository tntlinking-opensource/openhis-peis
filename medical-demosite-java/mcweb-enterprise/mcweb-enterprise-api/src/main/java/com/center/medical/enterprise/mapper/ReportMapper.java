package com.center.medical.enterprise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.enterprise.bean.dto.KsResultsDto;
import com.center.medical.enterprise.bean.dto.ProfessionResultDto;
import com.center.medical.enterprise.bean.model.Report;
import com.center.medical.enterprise.bean.param.OrderListDataParam;
import com.center.medical.enterprise.bean.param.ReportListDataParam;
import com.center.medical.enterprise.bean.vo.OrderListDataVo;
import com.center.medical.enterprise.bean.vo.ReportExportVo;
import com.center.medical.enterprise.bean.vo.ReportExportZyVo;
import com.center.medical.enterprise.bean.vo.ReportListDataVo;
import com.center.medical.enterprise.common.util.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * BG报告主表(MdReport)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
public interface ReportMapper extends BaseMapper<Report> {

    /**
     * 分页查询[BG报告主表]列表
     *
     * @param page  分页参数
     * @param param MdReport查询参数
     * @return 分页数据
     */
    IPage<ReportListDataVo> getPage(PageParam<ReportListDataVo> page, @Param("param") ReportListDataParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Report getInfoById(@Param("id") String id);

    /**
     * 获取职业结论
     * @param patientcode
     * @return
     */
    ProfessionResultDto getProfessionResultById(@Param("patientcode") String patientcode);

    /**
     * 获取处理意见
     * @param patientcode
     * @param serialNo
     * @return
     */
    String getSummaryText(@Param("patientcode") String patientcode,@Param("serialNo") String serialNo);

    /**
     * 获取导出数据
     * @param param
     * @return
     */
    List<ReportExportVo> getExportData(@Param("param") ReportListDataParam param);

    /**
     * 职业结论导出
     * @param param
     * @return
     */
    List<ReportExportZyVo> getExportZyData(@Param("param") ReportListDataParam param);

    /**
     * 获取科室结果
     * @param patientcode
     * @return
     */
    List<KsResultsDto> getksResults(@Param("patientcode") String patientcode);

    /**
     * 查询历次结果
     * @param code
     * @param deptIds
     * @return
     */
    List<KsResultsDto> getPreviousResults(@Param("patientcode") String patientcode,@Param("deptIds") List<String> deptIds);

    /**
     * 获取订单信息
     * @param page
     * @param param
     * @return
     */
    IPage<OrderListDataVo> getOrderListData(PageParam<OrderListDataVo> page, @Param("param") OrderListDataParam param);
}
