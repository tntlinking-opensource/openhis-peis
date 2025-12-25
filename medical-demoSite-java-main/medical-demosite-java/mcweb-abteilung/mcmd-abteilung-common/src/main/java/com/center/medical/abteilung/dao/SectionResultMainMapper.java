package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.dto.DepartmentReportDto;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.vo.AdiconGridDataVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KS科室检查结果主表(SectionResultMain)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:18
 */
public interface SectionResultMainMapper extends BaseMapper<SectionResultMain> {

    /**
     * 分页查询[KS科室检查结果主表]列表
     *
     * @param page  分页参数
     * @param param SectionResultMain查询参数
     * @return 分页数据
     */
    IPage<SectionResultMain> getPage(PageParam<SectionResultMain> page, @Param("param") SectionResultMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionResultMain getInfoById(@Param("id") String id);

    /**
     * 根据体检号获取检查结果
     *
     * @param patientCode 体检号
     * @return
     */
    List<SectionResultMain> getHealthSummarize(@Param("patientCode") String patientCode);

    /**
     * 根据体检号查全部
     * @param patientcode
     * @return
     */
    List<SectionResultMain> findResultmain(@Param("patientCode")String patientcode,@Param("sort")Integer sort);

    /**
     * 查询只出现有职业项目的科室
     * @param patientCode
     * @param medicalType
     * @param harmstr
     * @return
     */
    List<SectionResultMain> findOccupation(@Param("patientcode")String patientCode, @Param("medicaltype")String medicalType,@Param("harmstr") String[] harmstr);

    /**
     * 获取上次在本科室体检的小结
     * @param patientcode
     * @param ksID
     * @return
     */
    String getArchiveData(@Param("patientcode")String patientcode, @Param("ksid") String ksID);

    /**
     * 查询科室报告数据
     * @param patientcode
     * @param ksID
     * @return
     */
    DepartmentReportDto departmentReport(@Param("patientcode")String patientcode, @Param("ksID") String ksID);

    /**
     * 获取检验科艾迪康列表
     * @param patientCode
     * @return
     */
    List<AdiconGridDataVo> getAdiconGridData(@Param("patientCode") String patientCode);

    /**
     * 是否有外送项目
     * @param patientcode
     * @return
     */
    List<AdiconGridDataVo> haveOutwardDelivery(@Param("patientCode") String patientcode);

    /**
     * 获取未生成的数据
     * @param patientCode
     * @return
     */
    List<String> getNotGenerated(@Param("patientCode") String patientCode);
}
