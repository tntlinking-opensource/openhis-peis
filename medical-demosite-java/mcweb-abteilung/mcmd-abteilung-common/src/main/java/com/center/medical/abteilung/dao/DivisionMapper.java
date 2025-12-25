package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.dto.DiagnosticHeadDto;
import com.center.medical.abteilung.bean.dto.DivisionDto;
import com.center.medical.abteilung.bean.dto.GetC13dataDto;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.param.*;
import com.center.medical.abteilung.bean.vo.*;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.vo.SfxmVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KS科室检查结果主表(SectionResultMain)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:18
 */
public interface DivisionMapper extends BaseMapper<SectionResultMain> {


    /**
     * 获取科室列表数据
     *
     * @param cids
     * @param userNo
     * @return
     */
    List<DivisionDto> getListData(@Param("cids") List<String> cids, @Param("userNo") String userNo);

    /**
     * 获取科室列表数据有体检码
     *
     * @param cids
     * @param userNo
     * @param patientCode
     * @return
     */
    List<DivisionDto> getListDataByCode(@Param("cids") List<String> cids, @Param("userNo") String userNo, @Param("patientCode") String patientCode);

    /**
     * 查看列队数据(职业性问诊单独用getpatientdata)
     *
     * @param page
     * @param param
     * @return
     */
    IPage<RankDataVo> getRankData(PageParam<Peispatientexamitem> page, @Param("param") RankDataParam param);

    /**
     * c13读卡查询数据
     *
     * @param tjlx
     * @return
     */
    List<GetC13dataDto> C13NkdataDto(@Param("tjlx") String tjlx, @Param("tjh") String tjh, @Param("ksId") String ksId);

    /**
     * 查看档案 体检者列表数据
     *
     * @param page
     * @param patientarchiveno
     * @return
     */
    IPage<ArchiveDataVo> getArchiveData(PageParam<ArchiveDataVo> page, @Param("patientarchiveno") String patientarchiveno);

    /**
     * 查看档案右侧数据,科室医师小结等
     *
     * @param page
     * @param patientcode
     * @return
     */
    IPage<ResultDataVo> getResultData(PageParam<ResultDataVo> page, @Param("patientcode") String patientcode);

    /**
     * 我要提醒-获取科室 没有传体检号
     *
     * @param userNo
     * @param cid
     * @return
     */
    List<RemindKsVo> getRemindKs(@Param("userNo") String userNo, @Param("cId") String cid);

    /**
     * 我要提醒-获取科室 有体检号
     *
     * @param userNo
     * @param cid
     * @param patientcode
     * @return
     */
    List<RemindKsVo> getRemindKsByCode(@Param("userNo") String userNo, @Param("cId") String cid, @Param("patientcode") String patientcode);

    /**
     * 获取内科页面收费项目检查项目数据
     *
     * @return
     */
    List<GetC13dataDto> getNkdata(@Param("tjlx") String tjlx, @Param("tjh") String tjh, @Param("ksId") String ksId);

    /**
     * 科室加项左侧数据，创建套餐获取基础数据收费项目
     *
     * @param page
     * @param param
     * @return
     */
    IPage<SfxmVo> getSfxm(PageParam<SfxmVo> page, @Param("param") SfxmParam param);

    /**
     * 职业性问诊体检者列表数据
     *
     * @param page
     * @param param
     * @return
     */
    IPage<PatientDataVo> getPatientData(PageParam<PatientDataVo> page, @Param("param") PatientDataParam param);

    /**
     * 职业性问诊-职业病史列表数据
     *
     * @param page
     * @param param
     * @return
     */
    IPage<RemindPatientVo> getRemindPatient(PageParam<RemindPatientVo> page, @Param("param") RemindPatientParam param);

    /**
     * 普通预览科室报告 模板头数据
     *
     * @param param
     * @return
     */
    DiagnosticHeadDto diagnosticHead(@Param("param") DiagnosticParam param);

    /**
     * 查看档案 体检者列表数据 mysql
     *
     * @param patientarchiveno
     * @return
     */
    List<ArchiveDataVo> getArchiveDataMysql(@Param("patientarchiveno") String patientarchiveno);

    /**
     * 查看档案 体检者列表数据 Oracle
     *
     * @param ids
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    List<ArchiveDataVo> getArchiveDataOracle(@Param("ids") List<String> ids);


    /**
     * 查看档案右侧数据,科室医师小结等 Oracle
     *
     * @param patientcode
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    List<ResultDataVo> getResultDataOracle(@Param("patientcode") String patientcode);
}
