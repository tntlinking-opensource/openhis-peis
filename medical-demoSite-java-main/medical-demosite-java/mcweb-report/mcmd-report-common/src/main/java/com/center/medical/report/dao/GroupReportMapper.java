package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.FxDetection;
import com.center.medical.bean.model.FxSummary;
import com.center.medical.bean.model.Report;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.report.bean.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BG报告主表(Report)表数据库访问层
 *
 * @author ay
 * @since 2023-04-21 16:55:19
 */
public interface GroupReportMapper extends BaseMapper<Report> {



    /**
     * 通过样本id 查询
     * @param analyzeId
     * @return
     */
    List<FxDetection> findFxDetection2(@Param("analyzeId")String analyzeId);

    /**
     * 根据样本ID查询出所有的人员体检号
     * @param analyzeId
     * @return
     */
    List<String> getAllPatientcode(@Param("analyzeId")String analyzeId);

    /**
     * 通过样本id获取危害因素
     * @param analyzeId
     * @return
     */
    List<Harm> findHarm(@Param("analyzeId") String analyzeId);

    /**
     * 通过样本id获取危害因素 兼容老数据
     * @param analyzeId
     * @return
     */
    List<FxSummary> findHarmSql(@Param("analyzeId") String analyzeId);

    /**
     * 主要职业病危害因素 按种类列举
     * @param analyzeId
     * @return
     */
    List<FourHarmClassDto> getFourHarmClass(@Param("analyzeId") String analyzeId);

    /**
     * 检查项目（按照职业病危害因素分别列出，体现不同因素的项目不同）
     * @param idName
     * @param type
     * @return
     */
    List<String> findHarmDepartment(@Param("idName")String idName,@Param("type") String type);

    /**
     * 查询危害因素和疾病
     * @param idName
     * @param type
     * @return
     */
    List<HarmAndIllnessDto> findHarmAndIllness(@Param("idName")String idName, @Param("type") String type);

    /**
     * 根据危害因素的ID组成的字符串查询所有的诊断标准
     * @param danagerIds
     * @param sglxs
     * @return
     */
    List<String> findDiagnosis(@Param("danagerIds")String[] danagerIds,@Param("sglxs") String[] sglxs);

    /**
     * 查询样本客户的一些信息
     * @param analyzeId
     * @return
     */
    List<CustomerMessageDto> findCustomerMessage(@Param("analyzeId")String analyzeId);

    /**
     * 查询结论数量
     * @param analyzeId
     * @return
     */
    List<SerialnoCountDto> serialnoCount(@Param("analyzeId")String analyzeId);

    /**
     * 查询结论1
     * @param analyzeId
     * @return
     */
    List<FindSerialno1Dto> findSerialno1(@Param("analyzeId")String analyzeId);

    /**
     * 查询结论2
     * @param analyzeId
     * @return
     */
    List<FindSerialno1Dto> findSerialno2(@Param("analyzeId")String analyzeId);

    /**
     * 查询结论3
     * @param analyzeId
     * @return
     */
    List<FindSerialno1Dto> findSerialno3(@Param("analyzeId")String analyzeId);

    /**
     * 查询数量
     * @param analyzeId
     * @return
     */
    List<SerialnoCountDto> getCount(@Param("analyzeId")String analyzeId);

    /**
     * 本次职业健康检查危害因素人员分布情况一览表统计
     * @param analyzeId
     * @return
     */
    List<AllPeopleNumDto> findAllPeopleNum(@Param("analyzeId")String analyzeId);

    /**
     * 本次职业健康检查危害因素人员检查情况汇总一览表
     * @param analyzeId
     * @return
     */
    List<AllCollectTableDto> countAllCollectTable(@Param("analyzeId")String analyzeId);

    /**
     * 根据样本ID获取所有危害因素
     * @param analyzeId
     * @return
     */
    List<String> findHarmIdsBySampleId(@Param("analyzeId") String analyzeId);

    /**
     * 查询复查注意事项
     * @param analyzeId
     * @param summarySerialNo
     * @return
     */
    List<String> findAnnouncements(@Param("analyzeId")String analyzeId,@Param("summarySerialNo") String summarySerialNo);

    /**
     * 查询所有危害因素对身体危害
     * @param danagerIds
     * @return
     */
    List<BodyHarmDto> findBodyHarm(@Param("danagerIds") String[] danagerIds);


    /**
     * 获取漏检人员个数通过样本id
     * @param analyzeId
     * @return
     */
    List<Integer> getCountByAnalyzeId(@Param("analyzeId") String analyzeId);
}

