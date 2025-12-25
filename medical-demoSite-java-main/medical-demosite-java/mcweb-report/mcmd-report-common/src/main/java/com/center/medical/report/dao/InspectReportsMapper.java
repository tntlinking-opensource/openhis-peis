package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.InspectReport;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.dto.InfoSqlDto;
import com.center.medical.report.bean.param.IRParam;
import com.center.medical.report.bean.vo.IRPageVo;
import com.center.medical.report.bean.vo.InfoListDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 检验报告生成记录(InspectReport)表数据库访问层
 *
 * @author ay
 * @since 2023-07-11 09:27:31
 */
public interface InspectReportsMapper extends BaseMapper<InspectReport> {

    /**
     * 分页查询[检验报告生成记录]列表
     *
     * @param page  分页参数
     * @param param InspectReport查询参数
     * @return 分页数据
     */
    IPage<IRPageVo> getList(PageParam<IRPageVo> page, @Param("param") IRParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    InspectReport getInfoById(@Param("id") String id);

    /**
     * 获取右侧详情
     *
     * @param page
     * @param patientcode
     * @return
     */
    IPage<InfoListDataVo> getInfoListData(PageParam<InfoListDataVo> page, @Param("patientcode") String patientcode);

    /**
     * 获取检验报告数据
     *
     * @param patientcode
     * @return
     */
    List<InfoSqlDto> findInfoSql(@Param("patientcode") String patientcode);
}
