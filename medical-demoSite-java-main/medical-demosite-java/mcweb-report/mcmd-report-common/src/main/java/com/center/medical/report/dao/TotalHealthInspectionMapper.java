package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.dto.DiscardDto;
import com.center.medical.report.bean.dto.SubtableDto;
import com.center.medical.report.bean.param.HealthTotalParam;
import com.center.medical.report.bean.param.RemindPatientParam;
import com.center.medical.report.bean.vo.GetGriddataVo;
import com.center.medical.report.bean.vo.HtPeispatientVo;
import com.center.medical.report.bean.vo.RemindPatientVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检者收费项目表(Peispatientfeeitem)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-18 19:40:38
 */
public interface TotalHealthInspectionMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    IPage<HtPeispatientVo> getPage(PageParam<Peispatient> page, @Param("param") HealthTotalParam param);



    /**
     * 查询子表
     * @param patientCode
     * @param dh
     * @return
     */
    List<SubtableDto> selectSubtable(@Param("patientcode")String patientCode, @Param("dh") int dh);

    /**
     * 查询
     * @param medicalType
     * @param jhys
     * @param itemids
     * @return
     */
    List<String> selectSummary(@Param("medicalType")String medicalType,@Param("jhys") String[] jhys,@Param("itemids") List<String> itemids);

    /**
     * 有弃检职业项目的科室
     * @param patientCode
     * @param harmStr
     * @param medicalType
     * @return
     */
    List<DiscardDto> findDiscard(@Param("patientcode")String patientCode,@Param("harmstr") String[] harmStr,@Param("medicaltype") String medicalType);

    /**
     * 所有职业拒检项目，展现在综述和职业阳性结果
     * @param patientCode
     * @param harmStr
     * @param medicalType
     * @return
     */
    List<String> findRefusal(@Param("patientcode")String patientCode,@Param("harmstr") String[] harmStr,@Param("medicaltype") String medicalType);

    /**
     * 按体检号查询 最高级为禁忌症或职业病的 最高级级别
     * @param patientcode
     * @return
     */
    List<String> getUniqueResult(@Param("patientcode")String patientcode);


    /**
     * 获取分科检验数据
     * @param page
     * @param patientCode
     * @param ksId
     * @return
     */
    IPage<GetGriddataVo> getGriddata(PageParam<GetGriddataVo> page, @Param("patientcode") String patientCode, @Param("ksid") String ksId);

    /**
     * 获取提醒接口
     * @param page
     * @param param
     * @return
     */
    IPage<RemindPatientVo> getRemindPatient(PageParam<RemindPatientVo> page, @Param("param") RemindPatientParam param);
}
