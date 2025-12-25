package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.dto.DivisionDto;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.param.*;
import com.center.medical.abteilung.bean.vo.*;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.data.bean.vo.SfxmVo;
import com.center.medical.system.bean.vo.AllUserDataVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * KS科室检查结果主表(SectionResultMain)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:18
 */
public interface DivisionService extends IService<SectionResultMain> {

    /**
     * 获取科室列表数据
     * @param patientCode
     * @return
     */
    List<DivisionDto> getListData(String patientCode);

    /**
     * 查看列队数据(职业性问诊单独用getpatientdata)
     * @param page
     * @param param
     * @return
     */
    IPage<RankDataVo> getRankData(PageParam<Peispatientexamitem> page,RankDataParam param);

    /**
     * 获取审核之后的数据
     * @return
     */
    NkCheckedVo getNkCheckedData(String patientcode,String ksID);

    /**
     * 结伦词列表数据
     * @param patientcode
     * @param ksID
     * @return
     */
    List<HashMap<String,String>> jlcData(String patientcode, String ksID);

    /**
     * C13读卡数据
     * @param map
     * @param tjzsfxm
     * @return
     */
    List getC13data(HashMap<String, String> map, List<Peispatientfeeitem> tjzsfxm);

    /**
     * 获取结论词
     * @param patientcode
     * @param ksID
     * @return
     */
    List<HashMap<String, String>> getJlcData(String patientcode, String ksID);

    /**
     * 查看档案 体检者列表数据
     * @param page
     * @param patientcode
     * @return
     */
    IPage<ArchiveDataVo> getArchiveData(PageParam<ArchiveDataVo> page, String patientcode);

    /**
     * 查看档案右侧数据,科室医师小结等
     * @param page
     * @param patientcode
     * @return
     */
    IPage<ResultDataVo> getResultData(PageParam<ResultDataVo> page, String patientcode);

    /**
     * 我要提醒-获取科室
     * @param patientcode
     * @return
     */
    List<RemindKsVo> getRemindKs(String patientcode);

    /**
     * 我要提醒-保存科室提醒
     * @param param
     * @return
     */
    Boolean saveRemind(SaveRemindParam param);

    /**
     * 查询并拼接提醒内容
     * @param patientcode
     * @param ksID
     * @return
     */
    String getContent(String patientcode, String ksID);

    /**
     * 科室详情（无图像检查）
     * @param patientcode
     * @param ksID
     * @return
     */
    Map case1(String patientcode, String ksID,String gridSeclect,String autoFill);

    /**
     * 科室加项左侧数据，创建套餐获取基础数据收费项目
     * @param page
     * @param param
     * @return
     */
    IPage<SfxmVo> getSfxm(PageParam<SfxmVo> page, SfxmParam param);

    /**
     * 科室加项保存数据
     * @param param
     * @return
     */
    Boolean divAddSaOrUp(DivAddParam param);

    /**
     * 职业性问诊体检者列表数据
     * @param page
     * @param param
     * @return
     */
    IPage<PatientDataVo> getPatientData(PageParam<PatientDataVo> page, PatientDataParam param);

    /**
     * 职业性问诊-职业病史列表数据
     * @param page
     * @param param
     * @return
     */
    IPage<RemindPatientVo> getRemindPatient(PageParam<RemindPatientVo> page, RemindPatientParam param);

    /**
     * 职业性问诊-审核
     * @param param
     * @return
     */
    Boolean commonjobinquirySave(ComSaveParam param);

    /**
     * 职业性问诊-反审核
     * @param id
     * @param ksID
     * @return
     */
    Boolean commonjobinquiryReverse(String id, String ksID);

    /**
     * 当前分中心所有医生
     * @param cId
     * @param ksID
     * @param key
     * @return
     */
    List<AllUserDataVo> allDoctors(String cId, String ksID, String key);

    /**
     * 总检结论词搜索
     * @param page
     * @param key
     * @return
     */
    IPage<Basconclusion> getConclusion(PageParam<Basconclusion> page, String key);

    /**
     * 结论词保存(图像检查)
     * @param param
     * @return
     */
    Boolean saveOrUpdateJlc(saOrUpJlcParam param);

    /**
     * 小结(图像检查)
     * @param param
     * @return
     */
    Boolean autosave(AutoSaveParam param);

    /**
     * 审核(图像检查)
     * @param param
     * @return
     */
    Boolean shenhe(AutoSaveParam param);

    /**
     * 反审核(图像检查)
     * @param patientcode
     * @param ksID
     * @return
     */
    Boolean caseReverse(String patientcode, String ksID);

    /**
     * 普通预览科室报告
     * @param param
     * @return
     */
    DiagnosticVo diagnosticReport(DiagnosticParam param);

    /**
     * 图片科室预览报告
     * @param param
     * @return
     */
    PicReportVo picReport(DiagnosticParam param);

    /**
     * 检验科科室报告
     * @param param
     * @return
     */
    InspectReportVo inspectReport(DiagnosticParam param);
}

