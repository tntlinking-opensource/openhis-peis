package com.center.medical.report.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.dao.*;
import com.center.medical.abteilung.bean.model.ElectroAudiometer;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.model.SectionResultTwo;
import com.center.medical.abteilung.dao.ElectroAudiometerMapper;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.abteilung.dao.SectionResultTwoMapper;
import com.center.medical.common.core.domain.entity.SysConfig;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.*;
import com.center.medical.reception.bean.model.ReviewProject;
import com.center.medical.reception.dao.ReviewMapper;
import com.center.medical.reception.dao.ReviewProjectMapper;
import com.center.medical.report.bean.model.CommentsProgessional;
import com.center.medical.report.bean.model.SectionTotal;
import com.center.medical.report.bean.model.TotalVerdict;
import com.center.medical.report.bean.param.*;
import com.center.medical.report.bean.vo.CommonDataVo;
import com.center.medical.report.bean.vo.DtPeispatientVo;
import com.center.medical.report.bean.vo.TreatmentDataVo;
import com.center.medical.report.bean.vo.VerdictDataVo;
import com.center.medical.report.dao.*;
import com.center.medical.report.service.DiseaseTotalService;
import com.center.medical.report.service.SectionTotalService;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.dao.PeisorgreservationgroupMapper;
import com.center.medical.system.bean.model.UserHarmClass;
import com.center.medical.system.dao.SysConfigMapper;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.dao.UserHarmClassMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 总检管理-职业总检服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-07 18:53:54
 */
@Slf4j
@Service("DiseaseTotalServiceImpl")
@RequiredArgsConstructor
public class DiseaseTotalServiceImpl extends ServiceImpl<DiseaseTotalMapper, Peispatient> implements DiseaseTotalService {

    private final DiseaseTotalMapper diseaseTotalMapper;
    private final SectionTotalMapper sectionTotalMapper;
    private final TotalDoctorMapper totalDoctorMapper;
    private final SysUserMapper sysUserMapper;
    private final PeispatientPhotoMapper peispatientPhotoMapper;
    private final SectionResultTwoMapper sectionResultTwoMapper;
    private final BasconclusionMapper basconclusionMapper;
    private final TotalVerdictMapper totalVerdictMapper;
    private final PeispatientMapper peispatientMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final CommentsProgessionalMapper commentsProgessionalMapper;
    private final SysDeptMapper sysDeptMapper;
    private final ZyVsSummaryMapper zyVsSummaryMapper;
    private final HarmMapper harmMapper;
    private final ZySummaryMapper zySummaryMapper;
    private final WzZybsMapper wzZybsMapper;
    private final OccupationDiseastMapper occupationDiseastMapper;
    private final BasexamltemSignMapper basexamltemSignMapper;
    private final MapperFacade mapperFacade;
    private final BasMergeConclusionMapper basMergeConclusionMapper;
    private final SectionTotalService sectionTotalService;
    private final ReportMapper reportMapper;
    private final SysConfigMapper sysConfigMapper;
    private final ReviewMapper reviewMapper;
    private final PeisStateMapper peisStateMapper;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PatienttypeMapper patienttypeMapper;
    private final ElectroAudiometerMapper electroAudiometerMapper;
    private final ReviewProjectMapper reviewProjectMapper;
    private final UserHarmClassMapper userHarmClassMapper;
    private final ZyHarmClassMapper zyHarmClassMapper;

    /**
     * 分页获取分检完成待总检人员
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DtPeispatientVo> getPage(PageParam<DtPeispatientVo> page, HealthTotalParam param) {
        IPage<DtPeispatientVo> page1 = diseaseTotalMapper.getPage(page, param);
        return page1;
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return diseaseTotalMapper.getInfoById(id);
    }


    /**
     * 分检-普通 界面 数据
     *
     * @param patientno
     * @return
     */
    @Override
    public List<CommonDataVo> getCommonListData(String patientno) {
        return diseaseTotalMapper.getCommonListData(patientno);
    }


    /**
     * 获取图片
     *
     * @param patient
     * @return
     */
    @Override
    public String getPicture(Peispatient patient) {
        if (patient == null) {
            return "";
        }
        PeispatientPhoto photo = peispatientPhotoMapper.selectOne(new QueryWrapper<PeispatientPhoto>()
                .eq("patientcode", patient.getPatientcode()));
        return photo == null || photo.getPicture() == null ? "" : photo.getPicture();
    }


    /**
     * 增加总检记录及总检结论词表初始化
     *
     * @param patientCode
     * @return
     */
    @Transactional(rollbackFor = Exception.class,
            propagation = Propagation.REQUIRED)
    @Override
    public SectionTotal addTotalContent(String patientCode) {
        //总检主表
        SectionTotal st = new SectionTotal();
        // 体检号
        st.setPatientcode(patientCode);
        // 总检医师
        st.setDoctorId(SecurityUtils.getUserNo());
        // 总检时间
        st.setTotalTime(new Date());
        // 录入人
        st.setWriteId(SecurityUtils.getUserNo());
        // 录入时间
        st.setWriteTime(new Date());
        // 职业
        st.setDiseaseHealth(1);
        // 删除状态 0:不删
        st.setIsDelete(0);
        sectionTotalMapper.insert(st);

        //查询id科室id结论词id通过体检号
        List<SectionResultTwo> sections = sectionResultTwoMapper.findIdByCode(patientCode);
        if (CollectionUtil.isNotEmpty(sections)) {
            String sectionId = st.getId();
            for (SectionResultTwo s : sections) {
                //总检结论词表
                TotalVerdict veidict = new TotalVerdict();
                //科室ID
                String depId = s.getDivisionId();
                //结论词ID
                String basId = s.getBasconclusionId();
                String basName = "";
                String basAdvice = "";
                if (StringUtils.isNotEmpty(basId) && !"null".equals(basId)) {
                    //总检结论词
                    Basconclusion bas = basconclusionMapper.getInfoById(basId);

                    if (ObjectUtils.isNotEmpty(bas)) {
                        basName = bas.getName();
                        basAdvice = bas.getSuggestion();
                    }
                }
                //插入总检结论词表
                veidict.setBasconclusionId(basId);
                veidict.setBasconclusionName(basName);
                veidict.setTotalAdvice(basAdvice);
                veidict.setTotalId(sectionId);
                veidict.setDivisionId(depId);
                veidict.setIsDelete(0);
                veidict.setDiseaseHealth(1);
                veidict.setFlag(1);
                veidict.setDivisionId(depId);
                totalVerdictMapper.insert(veidict);
            }
        }
        return st;
    }

    /**
     * 获取综述,结论,意见
     *
     * @param patientCode
     * @return
     */
    @Override
    public Map<String, String> getThreeData(String patientCode) {
        //体检者表
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientCode));
        //接害因素
        String[] harmIds = ObjectUtils.isNotEmpty(patient.getJhys()) ? patient.getJhys().split(",") : null;

        //体检类别
        String medicalType = patient.getMedicaltype();
        //只出现有职业项目的科室
        List<SectionResultMain> sectionResultMain = sectionResultMainMapper.findOccupation(patientCode, medicalType, harmIds);
        //职业处理意见
        List<CommentsProgessional> commentsProgessional = commentsProgessionalMapper.selectList(new QueryWrapper<CommentsProgessional>()
                .eq("patientcode", patientCode));
        Map<String, String> data = new HashMap<String, String>();
        String summary = "";
        if (CollectionUtil.isNotEmpty(sectionResultMain)) {
            int j = 1;
            for (int i = 0; i < sectionResultMain.size(); i++) {
                //职业小结
                String ss = sectionResultMain.get(i).getZyConclusions();
                //部门表
                SysDept d = sysDeptMapper.getByDeptNo(sectionResultMain.get(i).getDepId());
                if (ObjectUtils.isNotEmpty(d) && ObjectUtils.isNotEmpty(ss) && !ss.equals("null")) {
                    //部门名称
                    String depname = d.getDeptName();
                    if (StringUtils.isNotEmpty(depname)) {
                        summary = summary + j + "、" + d.getDeptName()
                                + "\n"
                                + ss
                                + "\n\r";
                    } else {
                        summary = summary + j + "、" + "未知科室"
                                + "\n"
                                + ss
                                + "\n\r";
                    }
                    j++;
                }
            }
        }
        data.put("summary", summary == "" ? "空" : summary);
        String conclusion = "";
        String jkoffer = "";
        int jk = 1;
        //总检主表
        SectionTotal section = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientCode).eq("disease_health", 1)
                .eq("is_delete", 0));
        if (ObjectUtils.isNotEmpty(section)) {
            //总检结论词
            List<TotalVerdict> totals = totalVerdictMapper.selectList(new QueryWrapper<TotalVerdict>()
                    .eq("total_id", section.getId().trim()).eq("flag", 1));
            int j = 1;
            if (CollectionUtil.isNotEmpty(totals) && totals.size() > 0) {
                for (TotalVerdict t : totals) {
                    //科室ID
                    String depId = t.getDivisionId();
                    SysDept dep = null;
                    if (StringUtils.isNotEmpty(depId) && !"null".equals(depId)) {
                        dep = sysDeptMapper.getByDeptNo(depId);
                    }

                    if (ObjectUtils.isNotEmpty(dep) && StringUtils.isNotEmpty(dep.getDeptName()) && ObjectUtils.isNotEmpty(t)
                            && ObjectUtils.isNotEmpty(t.getBasconclusionName())
                            && !t.getBasconclusionName().equals("未见异常")
                            && !t.getBasconclusionName().equals("null")) {
                        conclusion += j + "、" + dep.getDeptName() + ":"
                                + t.getBasconclusionName() + "\n\r";
                        j++;
                        if (t.getTotalAdvice() != null) {
                            jkoffer += jk + "、" + t.getBasconclusionName() + "\n" + t.getTotalAdvice() + "\n\r";
                            jk++;
                        }
                    }

                }
            } else {
                List<SectionResultTwo> Seclist = sectionResultTwoMapper.findIdByCode(patientCode);
//
                if (CollectionUtil.isNotEmpty(Seclist) && Seclist.size() > 0) {
                    for (SectionResultTwo os : Seclist) {
                        SysDept d = sysDeptMapper.getByDeptNo(os.getDivisionId());
                        Basconclusion bas = basconclusionMapper.getInfoById(os.getBasconclusionId());

                        String name = "";
                        String offer = "";
                        String depName = "";

                        if (ObjectUtils.isNotEmpty(bas)) {
                            name = bas.getName();
                            offer = bas.getName();
                        }
                        if (ObjectUtils.isNotEmpty(d)) {
                            depName = d.getDeptName();
                        }

                        if (ObjectUtils.isNotEmpty(bas) && !name.equals("本次体检未见异常")) {
                            conclusion += j + "、" + d.getDeptName() + ":" + name
                                    + "\n\r";
                            j++;
                        }
                        if (d != null && bas != null && bas.getSuggestion() != null) {
                            jkoffer += jk + "、" + name + "\n" + bas.getSuggestion()
                                    + "\n\r";
                            jk++;
                        }
                    }
                }

            }
        }
        String posistive = "";
        /**把总述未检异常的去掉  就是职业阳性结果
         * 恩。其中一般检查需要处理一下。对于身高和体重血压结论正常的，体重指数正常的不用显示。
         *常规科室检查：内科、外科、视力辨色力等科室，就只显示异常的小结。
         */
        if (ObjectUtils.isNotEmpty(sectionResultMain) && sectionResultMain.size() > 0) {
            int j = 1;
            for (int i = 0; i < sectionResultMain.size(); i++) {
                //职业小结
                String ss = sectionResultMain.get(i).getZyConclusions();
                //部门表
                SysDept d = sysDeptMapper.getByDeptNo(sectionResultMain.get(i).getDepId());
                if (ObjectUtils.isNotEmpty(d) && ObjectUtils.isNotEmpty(ss) && !ss.equals("null")) {
                    //部门名称
                    String depname = d.getDeptName();
                    //一般检查
                    if (ObjectUtils.isNotEmpty(d.getSjbggs()) && d.getSjbggs().intValue() == 3) {
                        SectionResultTwo xy = sectionResultTwoMapper.selectOne(new QueryWrapper<SectionResultTwo>()
                                .eq("patientcode", patientCode).eq("verdict_id", "436"));
                        SectionResultTwo sg = sectionResultTwoMapper.selectOne(new QueryWrapper<SectionResultTwo>()
                                .eq("patientcode", patientCode).eq("verdict_id", "14"));
                        if (ObjectUtils.isEmpty(xy)) {
                            ss = ss.replaceAll("收缩压:[^;]*;", "");
                            ss = ss.replaceAll("舒张压:[^;]*;", "");
                            ss = ss.replaceAll("血压结论:[^;]*;", "");
                        }
                        if (ObjectUtils.isEmpty(sg)) {
                            ss = ss.replaceAll("身高:[^;]*;", "");
                            ss = ss.replaceAll("体重:[^;]*;", "");
                            ss = ss.replaceAll("体重指数:[^;]*;", "");
                        }
                        //职业的最多有个体重，显示体重指数，跟肥胖啥的
                        //奥，呼吸频率和营养状况是特殊危害因素做的，一般也不会做。最常见的是只测血压
                        ss = ss.replaceAll("脉搏:[^;]*;", "");
                        ss = ss.replaceAll("呼吸频率:[^;]*;", "");
                        ss = ss.replaceAll("营养状况:[^;]*;", "");
                        if (ss.trim().length() == 0) {
                            continue;
                        }
                    } else if ("未见异常；".equals(ss) || ("检验科:未见异常").equals(ss) || (depname + "：未见异常；").equals(ss)) {
                        continue;
                    }
                    if (!StringUtils.isEmpty(depname)) {
                        posistive = posistive + j + "、" + d.getDeptName() + "\n" + ss
                                + "\n\r";
                    } else {
                        posistive = posistive + j + "、" + "未知科室" + "\n" + ss
                                + "\n\r";
                    }
                    j++;
                }
            }
        }

        data.put("conclusion", conclusion == "" ? "空" : conclusion);
        data.put("posistive", posistive == "" ? "空" : posistive);
        data.put("jkoffer", jkoffer);
        String advice = "";
        if (ObjectUtils.isNotEmpty(commentsProgessional) && commentsProgessional.size() > 0) {
            int j = 1;
            for (int i = 0; i < commentsProgessional.size(); i++) {
                //职业病处理意见
                ZyVsSummary zyVsSummary = zyVsSummaryMapper.getInfoById(commentsProgessional.get(i).getProgessionalId());
                if (ObjectUtils.isNotEmpty(zyVsSummary) && StringUtils.isNotEmpty(zyVsSummary.getSummaryText())) {
                    //危害因素
                    Harm harm = harmMapper.getInfoById(zyVsSummary.getOccupationDiagnosis());
                    //职业病检查结论
                    ZySummary sum = zySummaryMapper.getInfoById(zyVsSummary.getOccupationSummary());
                    if (ObjectUtils.isNotEmpty(harm) && ObjectUtils.isNotEmpty(sum)) {
                        advice += j + "、" + harm.getHarmName() + sum.getOccupationSummary() + "\n" + zyVsSummary.getSummaryText() + "\n\r";
                        j++;
                    }
                }
            }
        }
        data.put("advice", advice == "" ? "空" : advice);
        SectionTotal st = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientCode).eq("disease_health", 1).eq("is_delete", 0));

        if (ObjectUtils.isNotEmpty(st)) {
            st.setSummarize(summary);
            st.setVerdict(conclusion);
            st.setOffer(advice);
            sectionTotalMapper.updateById(st);
        }
        return data;
    }


    /**
     * 根据档案号获取职业性病史
     *
     * @param patientarchiveno
     * @return
     */
    @Override
    public String getOccupationDiseast(String patientarchiveno) {
        // KS问诊
        List<WzZybs> w = wzZybsMapper.selectList(new QueryWrapper<WzZybs>().eq("id_patientarchive", patientarchiveno));

        String s = "";
        if (CollectionUtil.isNotEmpty(w)) {
            for (int i = 0; i < w.size(); i++) {
                //疾病名称
                String d = w.get(i).getOccupationDiseast();
                if (StringUtils.isNotEmpty(d) && !d.equals("null")) {
                    int j = 1;
                    s += j + ">" + d + "\n";
                    j++;
                }
            }
        }
        return s == null ? "空" : s;
    }

    /**
     * 职业处理意见界面 条件搜索
     *
     * @param param
     * @return
     */
    @Override
    public List<HashMap> searchTreatmentsuggestion(SearchTreatParam param) {
        //取出属性
        String patientno = param.getPatientno();
        String[] occupationDiagnosis = ObjectUtils.isNotEmpty(param.getOccupationDiagnosis()) ?
                param.getOccupationDiagnosis().split(",") : null;
        String occupationSummary = param.getOccupationSummary();
        String regimentationNote = param.getRegimentationNote();

        //根据条件获取职业处理意见 对象集合
        List<ZyVsSummary> summary = zyVsSummaryMapper.getZyVsSummarySearchData(
                regimentationNote, occupationSummary, occupationDiagnosis);

        //根据体检号获取职业处理意见表数据
        List<CommentsProgessional> commentsProgessional = commentsProgessionalMapper.getCommentsProgessionalList(patientno);

        List<String> summaryId = new ArrayList<String>();
        if (CollectionUtil.isNotEmpty(commentsProgessional) && commentsProgessional.size() > 0) {
            for (CommentsProgessional c : commentsProgessional) {
                //职业处理意见ID
                summaryId.add(c.getProgessionalId());
            }
        }
        List<ZyVsSummary> zyVsSummary = new ArrayList<ZyVsSummary>();
        if (summary != null && summary.size() > 0) {
            for (ZyVsSummary s : summary) {
                if (!summaryId.contains(s.getId())) {
                    zyVsSummary.add(s);
                }
            }
        }

        List<HashMap> list = new ArrayList<HashMap>();
        if (zyVsSummary != null && zyVsSummary.size() > 0) {
            for (int i = 0; i < zyVsSummary.size(); i++) {
                HashMap m = new HashMap();
                m.put("id", zyVsSummary.get(i).getId());
                // 体检类别
                m.put("regimentationNote", zyVsSummary.get(i).getRegimentationNote());
                //职业病检查结论
                ZySummary z = zySummaryMapper.getInfoById(zyVsSummary.get(i).getOccupationSummary());
                if (ObjectUtils.isNotEmpty(z)) {
                    // 健康状况结论
                    m.put("occupationSummary", z.getOccupationSummary());
                }
                //危害因素
                Harm h = harmMapper.getInfoById(zyVsSummary.get(i).getOccupationDiagnosis());
                if (ObjectUtils.isNotEmpty(h)) {
                    // 危害因素
                    m.put("occupationDiagnosis", h.getHarmName());
                }
                // 处理意见
                m.put("summaryText", zyVsSummary.get(i).getSummaryText());
                //禁忌疾病
                m.put("zyjjzdm", zyVsSummary.get(i).getDiagnosis());
                //可疑职业病
                String diseaseId = zyVsSummary.get(i).getOccupationDiseast();
                if (StringUtils.isNotEmpty(diseaseId)) {
                    //职业病名称
                    OccupationDiseast disease = occupationDiseastMapper.getInfoById(diseaseId);
                    if (ObjectUtils.isNotEmpty(disease)) {
                        //可疑职业病
                        m.put("kyzyb", disease.getOccupationDiseast());
                    }
                }
                list.add(m);
            }
        }
        return list;
    }


    /**
     * 科室小结 界面 数据
     *
     * @param
     * @param patientno
     * @return
     */
    @Override
    public List<VerdictDataVo> getVerdictData(String patientno) {
        //科室小结 界面 数据
        List<VerdictDataVo> list = diseaseTotalMapper.getVerdictData(patientno, "");

        for (VerdictDataVo verdictDataVo : list) {
            String positivesummary = "";
            int m = 0;
            if (ObjectUtils.isNotEmpty(verdictDataVo.getId())) {
                //科室检查结果表
                List<SectionResultTwo> sectionResultTwo = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                        .eq("main_id", verdictDataVo.getId()).eq("patientcode", patientno));
                if (ObjectUtils.isNotEmpty(sectionResultTwo) && sectionResultTwo.size() > 0) {
                    int n = 1;
                    for (int j = 0; j < sectionResultTwo.size(); j++) {
                        // 阳性结果
                        if (ObjectUtils.isNotEmpty(sectionResultTwo.get(j).getPosistive()) && sectionResultTwo.get(j).getPosistive() == 0) {
                            //查询检查项目体证词关联表
                            BasexamltemSign basExamLtemSign = basexamltemSignMapper.getInfoById(sectionResultTwo.get(j).getNodule());
                            //体证词名称不为空
                            if (ObjectUtils.isNotEmpty(basExamLtemSign) && ObjectUtils.isNotEmpty(basExamLtemSign.getName())) {
                                positivesummary += "(" + n + ")"
                                        + basExamLtemSign.getName()
                                        + ";";
                                n++;
                            }
                        }
                        //重症级别
                        Integer s = sectionResultTwo.get(j).getIntensiveLevel();
                        if (ObjectUtils.isNotEmpty(s) && s > m) {
                            m = s;
                        }
                    }
                }
            }
            verdictDataVo.setVer8(m);
            verdictDataVo.setVer9(positivesummary);
        }

        return list;
    }


    /**
     * 职业意见 界面 数据
     *
     * @param patientno
     * @return
     */
    @Override
    public List<TreatmentDataVo> getTreatmentDataString(String patientno) {
        //根据体检号获取职业处理意见表数据
        List<CommentsProgessional> list = commentsProgessionalMapper.getCommentsProgessionalList(patientno);
        List<TreatmentDataVo> mapList = mapperFacade.mapAsList(list, TreatmentDataVo.class);
        if (CollectionUtil.isNotEmpty(mapList)) {
            for (TreatmentDataVo m : mapList) {
                //职业病处理意见
                ZyVsSummary z = zyVsSummaryMapper.getInfoById(m.getProgessionalId());
                if (ObjectUtils.isNotEmpty(z)) {
                    //职业体检类别
                    m.setRegimentationNote(z.getRegimentationNote());
                    //职业病检查结论
                    ZySummary summary = zySummaryMapper.getInfoById(z.getOccupationSummary());
                    if (ObjectUtils.isNotEmpty(summary)) {
                        // 健康状况结论
                        m.setOccupationSummary(summary.getOccupationSummary());
                    } else {
                        m.setOccupationSummary("无");
                    }
                    m.setOccupationSummaryId(z.getOccupationSummary());
                    //危害因素
                    Harm harm = harmMapper.getInfoById(z.getOccupationDiagnosis());
                    if (ObjectUtils.isNotEmpty(harm)) {
                        m.setOccupationDiagnosis(harm.getHarmName());
                    } else {
                        m.setOccupationDiagnosis("无");
                    }
                    // 处理意见
                    m.setSummaryText(z.getSummaryText());
                    // 疾病
                    m.setZyjjzdm(z.getDiagnosis());

                    //可疑职业病
                    String diseaseId = z.getOccupationDiseast();
                    if (StringUtils.isNotEmpty(diseaseId)) {
                        //职业病名称
                        OccupationDiseast disease = occupationDiseastMapper.getInfoById(diseaseId);
                        if (ObjectUtils.isNotEmpty(disease)) {
                            //职业病名称
                            m.setKyzyb(disease.getOccupationDiseast());
                        }
                    }
                }
            }
            return mapList;
        } else {
            throw new ServiceException("职业处理意见表没有值!", 200);
        }
    }

    /**
     * 保存职业意见增加界面 数据
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> saveTreatment(SaveTreatParam param) {
        //取出属性
        String patientno = param.getPatientno();
        List<String> id = param.getData();
        //职业处理意见
        List<CommentsProgessional> com = commentsProgessionalMapper.selectList(new QueryWrapper<CommentsProgessional>().eq("patientcode", patientno));
        List<String> danagerId = new ArrayList<String>();
        if (CollectionUtil.isNotEmpty(com) && com.size() > 0) {
            for (CommentsProgessional c : com) {
                //职业处理意见ID
                String summaryId = c.getProgessionalId();
                if (StringUtils.isNotEmpty(summaryId) && !summaryId.equals("null")) {
                    ZyVsSummary summary = zyVsSummaryMapper.getInfoById(summaryId.trim());
                    if (ObjectUtils.isNotEmpty(summary)) {
                        //危害因素
                        String danagerid = summary.getOccupationDiagnosis();
                        if (StringUtils.isNotEmpty(danagerid) && !"null".equals(danagerid)) {
                            danagerId.add(danagerid);
                        }
                    }
                }
            }
        }


        if (CollectionUtil.isNotEmpty(id) && id.size() > 0) {
            for (int i = 0; i < id.size(); i++) {
                //职业病处理意见
                ZyVsSummary summary = zyVsSummaryMapper.getInfoById(id.get(i).trim());
                if (ObjectUtils.isNotEmpty(summary)) {
                    //危害因素
                    String danagerid = summary.getOccupationDiagnosis();
                    // TODO: 2024/4/20 去掉这个判断,同一危害因素既可以下补检也可以下
                    //如果包含这个危害因素就抛异常
//                    if (danagerId.contains(danagerid)) {
//                        //危害因素
//                        Harm harm = harmMapper.getInfoById(danagerid);
//                        String harmname = "";
//                        if (ObjectUtils.isNotEmpty(harm)) {
//                            harmname = harm.getHarmName();
//                            if (StringUtils.isEmpty(harmname) || "null".equals(harmname)) {
//                                harmname = "未知危害因素";
//                            }
//                        }
//                        throw new ServiceException(harmname + "重复，请重新选择");
//                    }
                }
                //根据职业处理意见ID和体检号查询个数
                Long count = commentsProgessionalMapper.selectCount(new QueryWrapper<CommentsProgessional>()
                        .eq("progessional_id", id.get(i)).eq("patientcode", patientno));
                //不存在就新建一个插入
                if (count == 0) {
                    CommentsProgessional cp = new CommentsProgessional();
                    cp.setPatientcode(patientno);
                    cp.setProgessionalId(id.get(i));
                    commentsProgessionalMapper.insert(cp);
                }
            }
            //生成职业处理意见
            String[] cs = sectionTotalService.generateCom(patientno);
            SectionTotal section = sectionTotalService.getOne(new QueryWrapper<SectionTotal>()
                    .eq("patientcode", patientno).eq("disease_health", 1).eq("is_delete", 0));
            if (ObjectUtils.isNotEmpty(section)) {
                section.setOffer(cs[0]);
                sectionTotalService.updateById(section);
            }
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("status", "success");
            result.put("offer", cs[0]);
            return result;
        } else {
            throw new ServiceException("请选择一条记录！");
        }
    }


    /**
     * 根据科室id和体检号 获取 体检项目和收费项目 和小结
     *
     * @param patientno
     * @param sectionId
     * @return
     */
    @Override
    public List<VerdictDataVo> getInspectChargeListData(String patientno, String sectionId) {
        List<VerdictDataVo> verdictData = diseaseTotalMapper.getVerdictData(patientno, sectionId);
        return verdictData;
    }


    /**
     * 根据体检号 ,保存综述,结论,健康建议到总检主表
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saOrUp(DiseaseSaOrUpParam param) {
        //取出属性
        String patientno = param.getPatientno();
        String summary = param.getSummary();
        String conclusion = param.getConclusion();
        String advice = param.getAdvice();
        String posistive = param.getPosistive();
        String jkoffer = param.getJkoffer();
        //体检者表总检信息
        Peispatient pp = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientno));
        if (ObjectUtils.isEmpty(pp)) {
            throw new ServiceException("体检者表无体检号:" + patientno);
        }
        Boolean bool = false;
        //总检主表
        SectionTotal sectionTotal = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientno).eq("disease_health", 1).eq("is_delete", 0));
        //为空就新建一个
        if (ObjectUtils.isEmpty(sectionTotal)) {
            sectionTotal = new SectionTotal();
        }
        // 体检号
        sectionTotal.setPatientcode(patientno);
        // 总检医师
        sectionTotal.setDoctorId(SecurityUtils.getUserNo());
        // 总检时间
        sectionTotal.setTotalTime(new Date());
        // 录入人
        sectionTotal.setWriteId(SecurityUtils.getUserNo());
        // 录入时间
        sectionTotal.setWriteTime(new Date());
        // 健康
        sectionTotal.setDiseaseHealth(1);
        // 综述
        sectionTotal.setSummarize(summary);
        // 结论
        sectionTotal.setVerdict(conclusion);
        // 建议
        sectionTotal.setOffer(advice);
        // 阳性
        sectionTotal.setPosistive(posistive);
        // 删除状态 0:不删
        sectionTotal.setIsDelete(0);
        // 职业总检：健康建议
        sectionTotal.setJkoffer(jkoffer);
        //更新或插入
        sectionTotalService.saveOrUpdate(sectionTotal);


        //插入报告
        Report report = reportMapper.selectOne(new QueryWrapper<Report>()
                .eq("patientcode", patientno).eq("disease_health", 1));
        if (ObjectUtils.isEmpty(report)) {
            report = new Report();
            report.setDiseaseHealth(1);
        }
        report.setPatientcode(patientno);
        //根据体检号获取短号
        report.setShortCode(ToolUtil.getShortCodeByLong(patientno));

        //根据key获取设置
        SysConfig sysConfig = sysConfigMapper.checkConfigKeyUnique("auditPrint");

        if (ObjectUtils.isNotEmpty(sysConfig)) {
            //系统内置（Y是 N否）
            if (sysConfig.getConfigType().equals("Y")) {
                //体检状态
                report.setStatus(2);
                //是否已总检
                report.setIsTotal(1);
                bool = true;
            } else {
                report.setStatus(1);
                report.setIsTotal(0);
            }
        } else {
            report.setStatus(1);
            report.setIsTotal(0);
        }
        report.setGrantId(pp.getIdPayway());
        report.setPatientname(pp.getPatientname());
        report.setAge((int) Math.floor(pp.getAge()));
        report.setIdOrg(pp.getIdOrg());
        report.setOrgName(pp.getOrgName());
        report.setDateregister(pp.getDateregister());
        report.setPhone(pp.getPhone());
        report.setIdOpendoctor(pp.getIdOpendoctor());
        report.setDoctorapply(pp.getDoctorapply());
        if (StringUtils.isNotBlank(pp.getNumorgresv()) && !"-1".equals(pp.getNumorgresv())) {
            report.setNumorgresv(pp.getNumorgresv());
        }
        report.setIsPrintMessage(1);
        report.setSex(pp.getIdSex());
        report.setDoctorfinalNameR(SecurityUtils.getUsername());
        report.setDatefinalexamed(new Date());
        Long count = reviewMapper.selectCount(new QueryWrapper<Review>()
                .eq("patientcode", patientno).eq("is_delete", 0));
        if (count == 0) {
            //是否打印复查通知单
            report.setIsPrintMessage(0);
        }
        //有id就是更新，没有就是插入
        if (ObjectUtils.isNotEmpty(report.getId())) {
            reportMapper.updateById(report);
        } else {
            reportMapper.insert(report);
        }


        // 职业总检时间
        pp.setDateregisternotime(new Date());
        pp.setPatientcodehiden(SecurityUtils.getUserNo());
        // 职业总检医生
        pp.setPatientnameencoded(SecurityUtils.getUsername());
        // 职业体检状态
        if (bool) {
            pp.setZytjzt(0);
        } else {
            pp.setZytjzt(0);
        }

        //有id更新，没id插入
        if (ObjectUtils.isNotEmpty(pp)) {
            this.updateById(pp);
        } else {
            this.save(pp);
        }


        return Boolean.TRUE;
    }


    /**
     * 设置体检者上传标志
     *
     * @param patient
     * @param scbs
     */
    @Override
    @Transactional
    public PeisState setScbs(Peispatient patient, int scbs) {
        if (StringUtils.isEmpty(patient.getPatientcode())) {
            return null;
        }
        //体检者上传状态
        PeisState ps = peisStateMapper.selectOne(new QueryWrapper<PeisState>()
                .eq("patientcode", patient.getPatientcode()));
        //如果为空就新建一个
        if (ObjectUtils.isEmpty(ps)) {
            ps = new PeisState(patient.getPatientcode());
            ps.setScbs(scbs);
            peisStateMapper.insert(ps);
        } else {
            ps.setScbs(scbs);
            peisStateMapper.updateById(ps);
        }
        return ps;
    }

    /**
     * 根据体检号和科室id 保存所有科室的检查结论词
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class,
            propagation = Propagation.REQUIRED)
    public Boolean saveSign(SaveSignAllParam param) {
        log.info("saveSign保存的数据：{}",param);
        //取出属性
        List<SaveSignParam> list = param.getSaveSignParams();
        String patientno = param.getPatientno();
        String name = param.getName();
        if (CollectionUtil.isNotEmpty(list)) {
            //总检主表
            SectionTotal sectionTotal = sectionTotalService.getOne(new QueryWrapper<SectionTotal>()
                    .eq("patientcode", patientno).eq("disease_health", 1).eq("is_delete", 0));
            for (int i = 0; i < list.size(); i++) {
                String status = list.get(i).getState();
                //总检结论词
                Basconclusion bc = basconclusionMapper.getInfoById(list.get(i).getBasconclusionId());
                String divisionId = list.get(i).getDivisionId();
                if (StringUtils.isNotEmpty(divisionId) && !"null".equals(divisionId)) {
                    SysDept dep = sysDeptMapper.getByDeptNo(divisionId);
                    if (ObjectUtils.isNotEmpty(dep)) {
                        String depName = dep.getDeptName();
                        if (StringUtils.isNotEmpty(depName) && !"null".equals(depName)) {
                            list.get(i).setDivision(depName);
                        } else {
                            list.get(i).setDivision("");
                        }
                    } else {
                        list.get(i).setDivision("");
                    }
                }
                //添加
                if ("added".equals(status)) {
                    if (StringUtils.isEmpty(divisionId)) {
                        throw new ServiceException("结论词" + bc.getName() + "没有关联科室，保存失败！");
                    }
                    TotalVerdict totalVerdict = new TotalVerdict();
                    String basId = list.get(i).getBasconclusionId();
                    String sectionId = null;
                    if (ObjectUtils.isNotEmpty(sectionTotal)) {
                        sectionId = sectionTotal.getId();
                    }
                    //总检结论词表
                    TotalVerdict verdict = null;
                    if (StringUtils.isNotEmpty(basId) && !"null".equals(basId)
                            && StringUtils.isNotEmpty(sectionId)
                            && !"null".equals(sectionId)) {
                        verdict = totalVerdictMapper.selectOne(new QueryWrapper<TotalVerdict>()
                                .eq("total_id", sectionId).eq("basconclusion_id", basId));
                    }

                    if (ObjectUtils.isNotEmpty(bc)) {
                        //总检建议
                        String ss = bc.getSuggestion();
                        if (StringUtils.isEmpty(ss)) {
                            ss = "空";
                        }
                        //结论名称
                        String basName = bc.getName();
                        if (StringUtils.isEmpty(basName) || "null".equals(basName)) {
                            basName = "";
                        }
                        totalVerdict.setBasconclusionName(basName);
                        totalVerdict.setTotalAdvice(ss);
                    }
                    // 状态 0:不删 1:删除
                    totalVerdict.setIsDelete(0);
                    //检查类型：0:健康 1:职业
                    totalVerdict.setDiseaseHealth(1);
                    totalVerdict.setBasconclusionId(list.get(i).getBasconclusionId());
                    totalVerdict.setDivisionId(list.get(i).getDivisionId());
                    totalVerdict.setTotalId(sectionTotal.getId());
                    if (ObjectUtils.isNotEmpty(list.get(i).getSee())) {
                        //标志：0不出现,1出现
                        totalVerdict.setFlag(Integer.parseInt(list.get(i).getSee()));
                    } else {
                        totalVerdict.setFlag(1);
                    }

                    //插入或更新
                    if (ObjectUtils.isEmpty(verdict)) {
                        totalVerdictMapper.insert(totalVerdict);
                    } else {
                        totalVerdictMapper.updateById(totalVerdict);
                    }

                    //更新
                } else if ("modified".equals(status)) {
                    if (StringUtils.isEmpty(divisionId)) {
                        throw new ServiceException("结论词" + bc.getName() + "没有关联科室，保存失败！");
                    }
                    //总检结论词表
                    TotalVerdict totalVerdict = totalVerdictMapper.getInfoById(list.get(i).getId());

                    if (ObjectUtils.isNotEmpty(bc)) {
                        String ss = bc.getSuggestion();
                        if (StringUtils.isEmpty(ss)) {
                            ss = "空";
                        }
                        totalVerdict.setTotalAdvice(ss);
                    }

                    totalVerdict.setBasconclusionId(list.get(i).getBasconclusionId());
                    totalVerdict.setDivisionId(list.get(i).getDivisionId());
                    totalVerdict.setTotalId(sectionTotal.getId());
                    if (ObjectUtils.isNotEmpty(list.get(i).getSee())) {
                        totalVerdict.setFlag(Integer.parseInt(list.get(i).getSee()));
                    } else {
                        totalVerdict.setFlag(1);
                    }
                    totalVerdictMapper.updateById(totalVerdict);
                    //删除
                } else if ("removed".equals(status)) {
                    totalVerdictMapper.delete(new QueryWrapper<TotalVerdict>().eq("id", list.get(i).getId()));
                }
            }
        }
        //保存追加的总检建议
        this.commitSign(patientno);
        return Boolean.TRUE;
    }


    /**
     * 保存追加的总检建议
     *
     * @param patientCode
     * @return
     */
    public Boolean commitSign(String patientCode) {
        try {

            Map<String, String> total = getThreeData(patientCode);
            SectionTotal section = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                    .eq("patientcode", patientCode));
            if (section != null) {
                // 结论counction
                section.setVerdict(total.get("conclusion"));
                section.setOffer(total.get("advice"));// 建议advice
                section.setJkoffer(total.get("jkoffer"));
                section.setPosistive(total.get("posistive"));
                sectionTotalMapper.updateById(section);
            } else {
                throw new ServiceException("无总检信息，请确认后再操作");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return Boolean.TRUE;
    }


    /**
     * 获取该体检者所有的体证词与所产生体证词的科室
     *
     * @param patientno
     * @return
     */
    @Override
    public List<HashMap> getSign(String patientno) {
        List<HashMap> list = new ArrayList<HashMap>();
        //总检主表
        SectionTotal section = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientno).eq("disease_health", 1).eq("is_delete", 0));
        if (ObjectUtils.isNotEmpty(section)) {
            //总检结论词表
            List<TotalVerdict> verdicts = totalVerdictMapper.selectList(new QueryWrapper<TotalVerdict>()
                    .eq("total_id", section.getId()));
            if (CollectionUtil.isNotEmpty(verdicts) && verdicts.size() > 0) {
                for (TotalVerdict v : verdicts) {
                    HashMap map = new HashMap();
                    SysDept dep = null;
                    String depId = v.getDivisionId();
                    //根据部门编号查询信息
                    if (!StringUtils.isEmpty(depId) && !"null".equals(depId)) {
                        dep = sysDeptMapper.getByDeptNo(depId);
                    }
                    String depName = null;
                    if (ObjectUtils.isNotEmpty(dep)) {
                        depName = dep.getDeptName();
                    }
                    if (StringUtils.isNotEmpty(depName)) {
                        Integer see = v.getFlag();
                        if (ObjectUtils.isEmpty(see)) {
                            see = 1;
                        }
                        map.put("see", see);
                        map.put("basconclusion", v.getBasconclusionName());
                        map.put("basconclusion_id", v.getBasconclusionId());
                        map.put("division_id", depId);
                        map.put("division", depName);
                        map.put("id", v.getId());
                        list.add(map);
                    }
                }
            }
            return list;
        }
        throw new ServiceException("无数据!");
    }

    /**
     * 获取该体检者所有的体证词与所产生体证词的科室
     *
     * @param srm
     * @param page
     * @param patientno
     * @return
     */
    @Override
    public IPage<Basconclusion> getConclusion(String srm, PageParam<Basconclusion> page, String patientno) {
        List<String> cons = new ArrayList<String>();
        //总检主表
        SectionTotal section = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientno).eq("disease_health", 1).eq("is_delete", 0));
        if (ObjectUtils.isNotEmpty(section)) {
            //总检结论词表
            List<TotalVerdict> verdict = totalVerdictMapper.selectList(new QueryWrapper<TotalVerdict>()
                    .eq("total_id", section.getId()));
            if (CollectionUtil.isNotEmpty(verdict) && verdict.size() > 0) {
                for (TotalVerdict t : verdict) {
                    //结论词ID
                    String basId = t.getBasconclusionId();
                    if (StringUtils.isNotEmpty(basId) && !"null".equals(basId)) {
                        cons.add(basId);
                    }
                }
            }
        }
        //条件构造器
        QueryWrapper<Basconclusion> con = new QueryWrapper<>();
        con.eq("is_delete", 0);
        if (StringUtils.isNotBlank(srm)) {
            srm = srm.trim().toUpperCase();
            con.like("input_code", srm);
        }
        if (cons.size() > 0) {
            con.notIn("id", cons);
        }
        //分页查询
        PageParam<Basconclusion> basconclusionPageParam = basconclusionMapper.selectPage(page, con);
        return basconclusionPageParam;
    }

    /**
     * 未完成
     *
     * @param patientno
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean unfinish(String patientno) {
        //体检者表
        Peispatient peispatient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientno));

        if (ObjectUtils.isNotEmpty(peispatient)) {
            //职业体检状态
            Integer status = peispatient.getZytjzt();
            if (ObjectUtils.isEmpty(status)) {
                throw new ServiceException("该体检号的职业体检状态为空！");
            }
            String str = "";
            if (status >= 9 || status == 7) {
                if (status == 7) {
                    str = "报告已终审，";
                } else if (status == 9) {
                    str = "报告已交接，";
                } else if (status == 10) {
                    str = "报告已通知，";
                } else if (status == 11) {
                    str = "报告已领取，";
                }
                throw new ServiceException(str + "无法反审");
            }
            //职业锁定不为当前的登录用户
            if (ObjectUtils.isNotEmpty(peispatient.getParsedassignedsuiteandfi())
                    && !peispatient.getParsedassignedsuiteandfi().equals(
                    SecurityUtils.getUsername())) {
                throw new ServiceException("该记录已经由" + peispatient.getParsedassignedsuiteandfi()
                        + "医师锁定,请等待该医生解锁!");
            }
            peispatient.setZytjzt(0);
            setScbs(peispatient, 0);
            peispatientMapper.updateById(peispatient);
            //报告
            Report report = reportMapper.selectOne(new QueryWrapper<Report>()
                    .eq("patientcode", patientno).eq("disease_health", 1));
            report.setStatus(1);
            reportMapper.updateById(report);
            return Boolean.TRUE;
        }
        throw new ServiceException("不存在记录!");
    }


    /**
     * 完成
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean finish(DiseaseSaOrUpParam param) {
        try {
            //取出属性
            String patientno = param.getPatientno();
            //体检者表
            Peispatient peispatient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientno));
            if (ObjectUtils.isNotEmpty(peispatient)) {
                //职业锁定人不为空并且不为当前用户
                if (ObjectUtils.isNotEmpty(peispatient.getParsedassignedsuiteandfi())
                        && !peispatient.getParsedassignedsuiteandfi().equals(
                        SecurityUtils.getUsername())) {
                    throw new ServiceException("该记录已经由" + peispatient.getParsedassignedsuiteandfi()
                            + "医师锁定,请等待该医生解锁!");
                }
                // 保存职业意见增加界面 数据
                saOrUp(param);
                // 总检锁定: 1.锁定 0.解锁 暂定
                peispatient.setIdGuidenurse(1.0);
                // 职业体检状态
                peispatient.setZytjzt(1);//
                setScbs(peispatient, 0);

                //生成复查
                OutputStream outputStream = null;
                OutputStreamWriter outputStreamWriter = null;
                InputStream inputStream = null;
                InputStreamReader inputStreamReader = null;
                BufferedReader reader = null;
                try {
                    // TODO: 2022/12/26 url参数，未完成
                    //参数
//                    String netUrl = Constants.CREATE_IP +"/report/health_report!createReview.action";
//                    String parameters = "patientcode=" + patientno;
//                    URL localURL = new URL(netUrl);
//                    URLConnection connection=localURL.openConnection();
//                    HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
//                    httpURLConnection.setDoOutput(true);
//                    httpURLConnection.setRequestMethod("POST");
//                    httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
//                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameters.length()));
//                    httpURLConnection.setConnectTimeout(30000);
//                    httpURLConnection.setReadTimeout(30000);
//                    outputStream = httpURLConnection.getOutputStream();
//                    outputStreamWriter = new OutputStreamWriter(outputStream,"utf-8");//utf-8编码
//                    outputStreamWriter.write(parameters);
//                    outputStreamWriter.flush();
//                    if (httpURLConnection.getResponseCode() >= 300) {
//                        throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (outputStreamWriter != null) {
                        outputStreamWriter.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
                int i = peispatientMapper.updateById(peispatient);
                return i == 0 ? false : true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ServiceException("不存在记录!");
    }


    /**
     * 分科电测听读卡
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    @Override
    public HashMap<String, Object> search(String patientCode, String ksId) {
        //体检者表
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientCode)
                .eq("f_registered", 1));

        if (ObjectUtils.isEmpty(patient)) {
            throw new ServiceException("error@该体检号尚未登记！");
        }
        //禁检
        if (patient.getFPaused() != null && patient.getFPaused().intValue() == 1) {
            throw new ServiceException("error@该体检号已被禁检！");
        }
        //任务分组ID
        String idOrgreservationgroup = patient.getIdOrgreservationgroup();
        if (ObjectUtils.isNotEmpty(idOrgreservationgroup)) {
            //体检者任务分组
            Peisorgreservationgroup org = peisorgreservationgroupMapper.getInfoById(idOrgreservationgroup);
            if (org != null && org.getFGrouppaused() != null && org.getFGrouppaused().intValue() == 1) {
                throw new ServiceException("error@该体检号已被禁检！");
            }
        }
        //体检者收费项目表
        Peispatientfeeitem feeitem = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode)
                .eq("id_ks", ksId)
                .isNull("f_transferedhl7")
                .eq("sfjj", 0)
                .eq("f_giveup", 0)
                .eq("change_item", 0)
        );
        if (feeitem == null) {
            throw new ServiceException("@该体检号没有本科室收费项目！");
        }
        //已收费
        if (feeitem.getFFeecharged() == null || feeitem.getFFeecharged() != 1) {
            throw new ServiceException("error@该体检号尚未缴费！");
        }
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("patient", patient);
        data.put("picture", getPicture(patient));

        data.put("isVIP", getIdPatientClass(patient));

        data.put("feeitem", feeitem);
        //科室检查结果主表
        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id", ksId)
                .eq("patientcode", patientCode)
        );
        //审核过
        if (main != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HashMap<String, String> main_map = new HashMap<String, String>();
            main_map.put("id", main.getId());
            main_map.put("conclusions", main.getConclusions());
            main_map.put("isAudit", main.getIsAudit() == null ? "0" : (main.getIsAudit() == 1 ? "1" : "0"));
            //检查人ID
            String rummagerId = main.getRummagerId();
            main_map.put("rummagerId", rummagerId);
            if (StringUtils.isNotEmpty(rummagerId)) {
                SysUser rummager = sysUserMapper.selectUserByUserNo(rummagerId);
                if (rummager != null) {
                    main_map.put("rummager", rummager.getUserName());
                }
            }
            main_map.put("rummagerTime", main.getRummagerTime() == null ? "" : sdf.format(main.getRummagerTime()));
            //录入人ID
            String writeId = main.getWriteId();
            //main_map.put("writeId",writeId);
            if (StringUtils.isNotEmpty(writeId)) {
                SysUser writer = sysUserMapper.selectUserByUserNo(writeId);
                if (writer != null) {
                    main_map.put("writeId", writer.getUserName());
                    //main_map.put("writeName", writer.getName());
                }
            }
            main_map.put("writeTime", main.getWriteTime() == null ? "" : sdf.format(main.getWriteTime()));
            data.put("main", main_map);
            /*电测听表*/
            ElectroAudiometer audiometer = electroAudiometerMapper.selectOne(new QueryWrapper<ElectroAudiometer>().eq("patient_code", patientCode));
            data.put("audiometry", audiometer);
        }
        return data;
    }


    /**
     * 获取体检者名称
     *
     * @param patient
     * @return
     */
    public final String getIdPatientClass(Peispatient patient) {
        String result = "";
        if (ObjectUtils.isNotEmpty(patient)) {
            Patienttype pt = patienttypeMapper.selectOne(new QueryWrapper<Patienttype>()
                    .eq("id", patient.getIdPatientclass()));
            if (ObjectUtils.isNotEmpty(pt)) {
                result = ObjectUtils.isEmpty(pt.getPatientName()) ? "" : pt.getPatientName();
            }
        }
        return result;
    }


    /**
     * 删除某条职业处理意见
     *
     * @param ids
     * @param patientno
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> removeRows(List<String> ids, String patientno) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请选择需要删除的记录!");
        }
        SectionTotal sectionTotal = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientno).eq("disease_health", 1));
        if (sectionTotal == null) {
            throw new ServiceException("数据错误，请查证后重新操作");
        }
        //删除职业处理意见
        commentsProgessionalMapper.delete(new QueryWrapper<CommentsProgessional>().in("id", ids));
        String[] cs = sectionTotalService.generateCom(patientno);
        //更新
        sectionTotal.setOffer(cs[0]);
        sectionTotalMapper.updateById(sectionTotal);
        //返回数据
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", "success");
        result.put("offer", cs[0]);
        return result;
    }


    /**
     * 判断是否需要选择总检医生
     *
     * @param patientcode
     * @return
     */
    @Override
    public Map<String, Object> checkHarm(String patientcode) {
        Map<String, Object> result = new HashMap<String, Object>();
        //如果下了复查处理意见，但没有复查项目，提示并弹出复查界面
        int count = diseaseTotalMapper.checkHarmTotal(patientcode);
        boolean hasReview = count > 0;
        //复查表
        Review review = reviewMapper.selectOne(new QueryWrapper<Review>().eq("patientcode", patientcode));
        //复查项目
        List<ReviewProject> rps = review == null ? null : reviewProjectMapper.selectList(new QueryWrapper<ReviewProject>().eq("review_id", review.getId()));
        if (hasReview && (review == null
                || rps == null || rps.size() == 0)) {
            result.put("status", "review");
            result.put("msg", "请选择复查项目！");
            return result;
        }
        if (rps != null && rps.size() > 0) {
            result.put("hasReviewPro", "1");
        }
        //体检者表
        Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
        String jhyss = patient.getJhys();
        Set<String> pclaSet = new HashSet<>();
        for (String jhId : jhyss.split(",")) {
            Harm harm = harmMapper.getInfoById(jhId);
            if (ObjectUtils.isNotEmpty(harm.getHarmClass())) {
                pclaSet.add(harm.getHarmClass());
            }
        }
        Set<String> uclaSet = getCurUserClaIds();
        StringBuilder builder = new StringBuilder("危害因素分类：<font color='red'>");
        Set<String> needClas = new HashSet<String>();
        Set<String> clasNames = new HashSet<String>();

        for (String cId : pclaSet) {
            if (!uclaSet.contains(cId)) {
                needClas.add(cId);
                String className = zyHarmClassMapper.getInfoById(cId).getName();
                clasNames.add(className);
                builder.append(className + "、");
            }
        }


        if (needClas.size() > 0) {
            result.put("status", "harm");
            result.put("ids", needClas);
            result.put("names", clasNames);
            result.put("msg", builder.substring(0, builder.length() - 1) + "</font>需要选择其他总检医生！");
        } else {
            result.put("status", "success");
        }
        return result;
    }


    public Set<String> getCurUserClaIds() {
        List<UserHarmClass> uhcs = userHarmClassMapper.selectList(new QueryWrapper<UserHarmClass>().eq("user_id", SecurityUtils.getUserNo()));
        Set<String> result = new HashSet<String>();
        for (UserHarmClass uhc : uhcs) {
            result.add(uhc.getClassId());
        }
        return result;
    }


    /**
     * 解锁
     *
     * @param patientcode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean unlock(String patientcode) {
        //体检者表
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientcode);
        if (ObjectUtils.isNotEmpty(peispatient)) {
            //职业体检状态
            if (ObjectUtils.isNotEmpty(peispatient.getZytjzt()) && peispatient.getZytjzt() == 1) {
                throw new ServiceException("总检完成不允许解锁！");
            }
            //职业锁定人不为空,锁定人不是当前登录用户
            if (ObjectUtils.isNotEmpty(peispatient.getParsedassignedsuiteandfi())
                    && !peispatient.getParsedassignedsuiteandfi().equals(SecurityUtils.getUsername())) {
                throw new ServiceException("该记录已经由" + peispatient.getParsedassignedsuiteandfi() + "医师锁定,请等待该医生解锁!");
            }
            //职业锁定完成
            if (ObjectUtils.isNotEmpty(peispatient.getIdGuidenurse()) && peispatient.getIdGuidenurse() == 0) {
                throw new ServiceException("该记录已经是未锁定状态!");
            }
            // 总检锁定: 1.锁定 0.解锁 暂定
            peispatient.setIdGuidenurse(0.0);
            // 总检锁定
            peispatient.setFFinallocked(0);
            peispatientMapper.updateById(peispatient);
        } else {
            throw new ServiceException("不存在记录!");
        }
        return Boolean.TRUE;
    }


    /**
     * 锁定
     *
     * @param patientcode
     * @return
     */
    @Override
    public Boolean lock(String patientcode) {
        Peispatient peispatient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientcode));

        if (ObjectUtils.isNotEmpty(peispatient)) {
            //职业锁定人不为空,锁定人不是当前登录用户
            if (StringUtils.isNotEmpty(peispatient.getParsedassignedsuiteandfi())
                    && !peispatient.getParsedassignedsuiteandfi().equals(SecurityUtils.getUsername())) {
                throw new ServiceException("该记录已经由" + peispatient.getParsedassignedsuiteandfi() + "医师锁定,请等待该医生解锁!");
            }
            //职业锁定完成
            if (ObjectUtils.isNotEmpty(peispatient.getIdGuidenurse()) && peispatient.getIdGuidenurse() == 1.0) {
                throw new ServiceException("该记录已经是锁定状态!");
            }
            // 总检锁定: 1.锁定 0.解锁 暂定
            peispatient.setIdGuidenurse(1.0);
            peispatientMapper.updateById(peispatient);
        } else {
            throw new ServiceException("不存在记录!");
        }
        return Boolean.TRUE;
    }
}

