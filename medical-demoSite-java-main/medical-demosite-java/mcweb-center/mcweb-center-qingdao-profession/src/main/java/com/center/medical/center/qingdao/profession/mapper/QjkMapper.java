package com.center.medical.center.qingdao.profession.mapper;

import com.center.medical.center.qingdao.profession.entity.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * QT体检者表(Peispatient)数据库访问层
 *
 * @author ay
 * @since 2024-05-07 15:54:16
 */
@Mapper
@Component
public interface QjkMapper{

    /**
     * 获取没被删除的危害因素
     * @return
     */
    List<HarmDto> getHarmList();

    /**
     * 查询体检人信息
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    List<TjRenM> getTjRenM(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 获取检查数据
     * @param patientCodes
     * @return
     */
    List<TjRenDData> getTjRenDData(@Param("patientCodes") List<String> patientCodes);
    /**
     * 获取检查数据2
     * @param patientCodes
     * @return
     */
    List<TjRenDData> getTjRenDData2(@Param("patientCodes") List<String> patientCodes);

    /**
     * 获取科室小结数据
     * @param patientCodes
     * @return
     */
    List<TjRenMKeshiXJ> getTjRenMKeshiXJS(@Param("patientCodes") List<String> patientCodes);

    /**
     * 获取科室总结数据
     * @param patientCodes
     * @return
     */
    List<TjRenKeShiZj> getTjRenKeShiZj(@Param("patientCodes") List<String> patientCodes);

    /**
     * 获取接害信息
     * @param patientCodes
     * @return
     */
    List<TjRenMJieHai> getTjRenMJieHai(@Param("patientCodes") List<String> patientCodes);

    /**
     * 插入完成后修改状态
     * @param patientCodes
     */
    void tagPatients(@Param("patientCodes") List<String> patientCodes);

    /**
     * 获取需要报告赋码的数据
     * @return
     */
    List<NeedReportCodingDataDto> getNeedReportCodingData();

    /**
     * 插入附件表
     * @param param
     */
    void insertAttachment(@Param("param") InsertAttachmentDto param);

    /**
     * 获取默认分中心
     * @return
     */
    DefaultBranchDto getDefaultBranch();
}
