package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Report;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.report.bean.dto.*;
import com.center.medical.report.bean.param.ReviewNoticeListParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BG报告主表(Report)表数据库访问层
 *
 * @author ay
 * @since 2023-04-21 16:55:19
 */
public interface IPersonalReportMapper extends BaseMapper<Report> {

    /**
     * 判断是否有隐私项目，有隐私项目时要生成隐私报告
     * @param patientcode
     * @return
     */
    List<PrivateItemDto> hasPrivateItem(@Param("patientcode") String patientcode);

    /**
     * 生成隐私小结，正常小结中不包含隐私项目
     * @param patientcode
     * @return
     */
    List<XjDataDto> xjdata(@Param("patientcode") String patientcode);

    /**
     * 只显示隐私项目
     * @param patientcode
     * @return
     */
    List<FindBgdybsDto> findBgdybs(@Param("patientcode") String patientcode);

    /**
     * 获取健康头模板所有坐标的值
     * @param patientcode 体检号
     * @param islrct 0否,1是
     * @return
     */
    AllOneHealthDto findAllOneHealth(@Param("patientcode")String patientcode, @Param("islrct") Integer islrct);

    /**
     *获取职业史信息
     * @param patientcode
     * @return
     */
    List<ExpHead2Dto> getExpHead2(@Param("patientcode")String patientcode);

    /**
     * 获取职业病史
     * @param patientcode
     * @return
     */
    List<ExpHead3Dto> getExpHead3(@Param("patientcode")String patientcode);

    /**
     * 职业获取所有科室
     * @param patientcode
     * @param jhys
     * @param medicaltype
     * @return
     */
    List<String> findAllDepD(@Param("patientcode")String patientcode,@Param("jhys") String[] jhys,@Param("medicaltype") String medicaltype);

    /**
     * 获取某个体检者本次体检都有哪些科室
     * @param patientcode
     * @return
     */
    List<String> findAllDep(@Param("patientcode")String patientcode);

    /**
     * 获取所有的图片科室健康
     * @param patientcode
     * @return
     */
    List<SysDept> findAllPicDepH(@Param("patientcode")String patientcode);


    /**
     * 获取所有的图片科室职业
     * @param patientcode
     * @return
     */
    List<SysDept> findAllPicDepD(@Param("patientcode")String patientcode,@Param("harmIds")String[] harmIds,@Param("medicaltype")String medicalType);

    /**
     * 获取职业史信息
     * @param patientcode
     * @return
     */
    List<ProfessionHead2Dto> getProfessionHead2(@Param("patientcode")String patientcode);

    /**
     * 获取职业病史
     * @param patientcode
     * @return
     */
    List<ProfessionHead3Dto> getProfessionHead3(@Param("patientcode")String patientcode);

    /**
     * 获取收费项目
     * @param patientcode
     * @param depId
     * @param dh
     * @return
     */
    List<CaseDataDto> getCaseData(@Param("patientcode")String patientcode,@Param("depId") String depId,@Param("dh")Integer dh);

    /**
     * 根据体检号获取所有与模板jyk_1.docx有关的数据
     * @param patientcode
     * @param dh
     * @return
     */
    List<AllInspectDto> findAllInspect(@Param("patientcode")String patientcode,@Param("dh") Integer dh);

    /**
     * 综合职业报告
     * @param feeItemId
     * @param jhyss
     * @param medicaltype
     * @return
     */
    String getZyAtts(@Param("feeItemId")String feeItemId,@Param("jhyss") String[] jhyss,@Param("medicaltype") String medicaltype);

    /**
     * 职业必查拒检项目
     * @param patientcode
     * @param harmIds
     * @param medicalType
     * @return
     */
    List<JjDataDto> getJjData(@Param("patientcode")String patientcode,@Param("harmIds") String[] harmIds,@Param("medicalType") String medicalType);

    /**
     * 外送图片
     * @param id
     * @param harmIds
     * @param medicalType
     * @return
     */
    List<String> getOutSidePicture(@Param("id")String id,@Param("harmIds") String[] harmIds,@Param("medicalType") String medicalType);

    /**
     * 获取复查名单
     * @param ddh
     * @return
     */
    List<ReviewNoticeListDto> getReviewNoticeList(@Param("param") ReviewNoticeListParam param);
}
