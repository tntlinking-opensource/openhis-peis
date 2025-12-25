package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.report.bean.dto.*;
import com.center.medical.report.bean.param.RecordManageParam;
import com.center.medical.report.bean.vo.RecordManageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检者档案表(Peispatientarchive)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-24 10:08:11
 */
public interface RecordManageMapper extends BaseMapper<Peispatientarchive> {

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param param 参数
     * @return 分页数据
     */
    List<RecordManageVo> getPage(@Param("param") RecordManageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientarchive getInfoById(@Param("id") String id);

    /**
     * 获取医生数据
     * @param patientcode
     * @return
     */
    GetDoctorDto getDoctor(@Param("patientcode") String patientcode);

    /**
     * 查询各科室小结
     * @param patientcode_p 本次体检号
     * @param patientcode_m 上次体检号
     * @return
     */
    List<DepResult1Dto> getDepResult1(@Param("patientcode_p")String patientcode_p,@Param("patientcode_m") String patientcode_m);

    /**
     * 获取检验科本届数据
     * @param patientcode_m
     * @param patientcode_p
     * @param itemId
     * @return
     */
    List<TestResultDto> getTestResult(@Param("patientcode_m")String patientcode_m,@Param("patientcode_p") String patientcode_p,@Param("itemid") String itemId);

    /**
     * 导出对比报告
     * @param param
     * @return
     */
    List<RecordManageVo> getExportData(@Param("param") RecordManageParam param);

    /**
     * 三次对比报告的，上次做了也不显示
     * @param patientcode
     * @param secondPatientcode
     * @param firstPatientcode
     * @return
     */
    List<DepResult2Dto> getDepResult2(@Param("patientcode_p")String patientcode,@Param("patientcode_s") String secondPatientcode,@Param("patientcode_f") String firstPatientcode);

    /**
     * 通过体检号获取检查项目
     * @param patientcode_p
     * @return
     */
    List<InspectionData2ByCodeDto> getInspectionData2ByCode(@Param("patientcode_p")String patientcode_p);


    /**
     * 通过三个体检号获取检验科结果
     * @param patientcode_s
     * @param patientcode_f
     * @param patientcode_p
     * @param itemId
     * @return
     */
    List<InspectionData2ByThreeDto> getInspectionData2ByThreeCode(@Param("patientcode_s")String patientcode_s,@Param("patientcode_f") String patientcode_f,@Param("patientcode_p") String patientcode_p,@Param("itemId") String itemId);

    /**
     * 获取检查项目
     * @param patientcode_p
     * @return
     */
    List<IDItemIdsDto> getInspectionData(@Param("patientcode_p")String patientcode_p);

    /**
     * 通过两个个体检号获取检验科结果
     * @param patientcode_m
     * @param patientcode_p
     * @param itemId
     * @return
     */
    List<InspectionData2ByThreeDto> getTwoCodeInspect(@Param("patientcode_m")String patientcode_m,@Param("patientcode_p") String patientcode_p,@Param("itemId") String itemId);


    /**
     * 查询分页总数
     * @param param
     * @return
     */
    Long getMemberTotal(@Param("param") RecordManageParam param);
}
