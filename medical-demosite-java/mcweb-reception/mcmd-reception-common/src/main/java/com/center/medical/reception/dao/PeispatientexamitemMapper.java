package com.center.medical.reception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.param.DIGriddataParam;
import com.center.medical.reception.bean.param.DivisionInspectionParam;
import com.center.medical.reception.bean.vo.GetInspectReportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * LIS结果(LisPacs数据)(Peispatientexamitem)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:50
 */
public interface PeispatientexamitemMapper extends BaseMapper<Peispatientexamitem> {

    /**
     * 分页查询[LIS结果(LisPacs数据)]列表
     *
     * @param page  分页参数
     * @param param Peispatientexamitem查询参数
     * @return 分页数据
     */
    IPage<Peispatientexamitem> getList(PageParam<Peispatientexamitem> page, @Param("param") Peispatientexamitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Peispatientexamitem getInfoById(@Param("id") String id);

    /**
     * 检验科结果分页查询
     *
     * @param page
     * @param divisionInspectionParam
     * @return
     */
    IPage<Peispatientexamitem> searchDivision(PageParam<Peispatientexamitem> page, @Param("param") DivisionInspectionParam divisionInspectionParam);

    /**
     * 获取本次体检号的所有收费项目ID
     *
     * @param patientcode
     * @return
     */
    List<String> getItemId(@Param("patientcode") String patientcode);

    /**
     * 获取收费项目表格数据
     *
     * @param page
     * @param param
     * @return
     */
    IPage<Peispatientexamitem> getgriddata(PageParam<Peispatientexamitem> page, @Param("param") DIGriddataParam param);

    /**
     * 获取科室报告 LIS结果
     * @param patientcode
     * @return
     */
    List<GetInspectReportVo> getInspectReportVo(@Param("patientcode") String patientcode , @Param("dh")Integer dh);
}
