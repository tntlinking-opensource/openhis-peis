package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.dto.FindByCodeDhDto;
import com.center.medical.abteilung.bean.dto.SrtPcDto;
import com.center.medical.abteilung.bean.model.SectionResultTwo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KS科室检查结果表-结论词、小结(SectionResultTwo)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:19
 */
public interface SectionResultTwoMapper extends BaseMapper<SectionResultTwo> {

    /**
     * 分页查询[KS科室检查结果表-结论词、小结]列表
     *
     * @param page  分页参数
     * @param param SectionResultTwo查询参数
     * @return 分页数据
     */
    IPage<SectionResultTwo> getPage(PageParam<SectionResultTwo> page, @Param("param") SectionResultTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionResultTwo getInfoById(@Param("id") String id);

    /**
     * 获取体检者分检结论
     *
     * @param patientcode 体检号
     * @param dh          体检类型：0.健康体检 1.职业体检 2.综合 3.复查
     * @return
     */
    List<SrtPcDto> getListByPatientCode(@Param("patientcode") String patientcode, @Param("dh") int dh);

    /**
     * 通过体检号和体检类型查询
     *
     * @param patientCode
     * @param dh
     * @return
     */
    List<FindByCodeDhDto> findByCodeDh(@Param("patientcode") String patientCode, @Param("dh") int dh);

    /**
     * 查询职业的id通过体检号
     *
     * @param patientCode
     * @return
     */
    List<SectionResultTwo> findIdByCode(@Param("patientcode") String patientCode);

    /**
     * 查询健康的id通过体检号
     *
     * @param patientno
     * @return
     */
    List<SectionResultTwo> findHeId(@Param("patientcode") String patientno);

    /**
     * 获取本届体检者都有哪些科室体检
     *
     * @param patientcode
     * @return
     */
    List<String> getdepId(@Param("patientcode") String patientcode);

    /**
     * 通过体检号和体检类型查询查询pacs科室
     * @param patientCode
     * @param dh
     * @return
     */
    List<FindByCodeDhDto> findPacsByCodeDh(@Param("patientcode")String patientCode,@Param("dh") int dh);
}
