package com.center.medical.report.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.common.utils.*;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.dao.*;
import com.center.medical.abteilung.bean.dto.FindByCodeDhDto;
import com.center.medical.abteilung.bean.model.*;
import com.center.medical.abteilung.dao.*;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.config.SmsConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysConfig;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.sms.SDKTestSendTemplateSMS;
import com.center.medical.dao.*;
import com.center.medical.reception.bean.model.ReviewProject;
import com.center.medical.reception.dao.ReviewMapper;
import com.center.medical.reception.service.ReviewProjectService;
import com.center.medical.reception.service.ReviewService;
import com.center.medical.report.bean.dto.DiscardDto;
import com.center.medical.report.bean.model.*;
import com.center.medical.report.bean.param.*;
import com.center.medical.report.bean.vo.GetGriddataVo;
import com.center.medical.report.bean.vo.HtPeispatientVo;
import com.center.medical.report.bean.vo.RemindPatientVo;
import com.center.medical.report.dao.*;
import com.center.medical.report.service.ReportService;
import com.center.medical.report.service.SectionTotalService;
import com.center.medical.report.service.TotalHealthInspectionService;
import com.center.medical.report.service.TotalVerdictService;
import com.center.medical.sellcrm.bean.model.Comboexamitem;
import com.center.medical.sellcrm.dao.ComboexamitemMapper;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.dao.*;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.center.medical.common.constant.Constants.BC_SUMMARY_ID;

/**
 * 体检者收费项目表(Peispatientfeeitem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-18 19:40:46
 */
@Slf4j
@Service("totalHealthInspectionService")
@RequiredArgsConstructor
public class TotalHealthInspectionServiceImpl extends ServiceImpl<TotalHealthInspectionMapper, Peispatient> implements TotalHealthInspectionService {

    private final TotalHealthInspectionMapper totalHealthInspectionMapper;
    private final PeisStateMapper peisStateMapper;
    private final SectionTotalMapper sectionTotalMapper;
    private final BasconclusionMapper basconclusionMapper;
    private final TotalVerdictMapper totalVerdictMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final CommentsProgessionalMapper commentsProgessionalMapper;
    private final ZySummaryMapper zySummaryMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final SysDeptMapper sysDeptMapper;
    private final PeispatientMapper peispatientMapper;
    private final SectionResultTwoMapper sectionResultTwoMapper;
    private final ElectroAudiometerMapper electroAudiometerMapper;
    private final ZyVsSummaryMapper zyVsSummaryMapper;
    private final HarmMapper harmMapper;
    private final OccupationDiseastMapper occupationDiseastMapper;
    private final ReportMapper reportMapper;
    private final ReviewMapper reviewMapper;
    private final TotalDoctorMapper totalDoctorMapper;
    private final ShortmessageMapper shortmessageMapper;
    private final SysBranchMapper sysBranchMapper;
    private final BasMergeMapper basMergeMapper;
    private final BasMergeConclusionMapper basMergeConclusionMapper;
    private final BranchMapper branchMapper;
    private final ConclusionAndFzxMapper conclusionAndFzxMapper;
    private final SectionTotalService sectionTotalService;
    private final SysUserMapper sysUserMapper;
    private final SysConfigMapper sysConfigMapper;
    private final ReportService reportService;
    private final PeispatientService peispatientService;
    private final ItemsMapper itemsMapper;
    private final ReviewProjectService reviewProjectService;
    private final PeispatientPhotoMapper peispatientPhotoMapper;
    private final PatienttypeMapper patienttypeMapper;
    private final SectionAndRemindMapper sectionAndRemindMapper;
    private final SectionRemindMapper sectionRemindMapper;
    private final TotalVerdictService totalVerdictService;
    private final ISysBranchService iSysBranchService;
    private final ReviewService reviewService;
    private final ComboexamitemMapper comboexamitemMapper;
    private final PacsResultMapper pacsResultMapper;
    private final ISysConfigService iSysConfigService;

    private final static List<String> DEPT_NAMES = Arrays.asList(new String[]{"超声科", "彩超", "放射科(DR)", "普通透视", "放射科(CT)", "数字钼靶", "眼底", "放射科(X线)", "放射科(CR)", "磁共振"});
    @Autowired
    private LoadProperties loadProperties;


    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<HtPeispatientVo> getPage(PageParam<Peispatient> page, HealthTotalParam param) {
        param.setIsJk(1);
        return totalHealthInspectionMapper.getPage(page, param);
    }


    /**
     * 设置scbs
     *
     * @param patient
     * @param scbs
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PeisState setScbs(Peispatient patient, int scbs) {
        if (StringUtils.isEmpty(patient.getPatientcode())) {
            return null;
        }
        PeisState ps = peisStateMapper.selectOne(new QueryWrapper<PeisState>().eq("patientcode", patient.getPatientcode()));
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
     * 健康+职业-新增总检主表、子表
     *
     * @param patient
     * @param dh
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SectionTotal init(Peispatient patient, int dh) {
        String patientCode = patient.getPatientcode();
        //判断main表里的是否有未审核的，有的话需要审核一下
        judgmentReviewMain(patientCode);
        //主表
        SectionTotal sectionTotal = new SectionTotal();
        sectionTotal.setPatientcode(patientCode);// 体检号
        sectionTotal.setDiseaseHealth(dh);// 健康
        sectionTotal.setIsDelete(0);// 删除状态 0:不删
        sectionTotal.setScbs(0);//未上传
        sectionTotalMapper.insert(sectionTotal);
        String mainId = sectionTotal.getId();
        //子表
        //sectionResultTwo的数据
        List<FindByCodeDhDto> sections = sectionResultTwoMapper.findByCodeDh(patientCode, dh);
        //pacs sectionResultTwo的数据
        List<FindByCodeDhDto> pacs = sectionResultTwoMapper.findPacsByCodeDh(patientCode, dh);
        sections.addAll(pacs);

        for (int i = 0, s = sections.size(); i < s; i++) {
            FindByCodeDhDto srt = sections.get(i);
            Basconclusion bas = basconclusionMapper.getInfoById(srt.getBasconclusionId());
            if (ObjectUtils.isEmpty(bas)) {
                continue;
            }
            TotalVerdict verd = new TotalVerdict();
            verd.setBasconclusionId(srt.getBasconclusionId());
            verd.setTotalId(mainId);
            verd.setDivisionId(bas.getDivisionId());
            verd.setIsDelete(0);
            verd.setDiseaseHealth(dh);
            verd.setTotalAdvice(bas.getSuggestion());
            verd.setFlag(1);
            verd.setBasconclusionName(bas.getName());
            verd.setVerdictSort(i);
            totalVerdictMapper.insert(verd);
        }
        //首次进入职业总检时，系统判断是否存在职业必查项目弃检，如果存在，直接插入对应的补查职业处理意见。
        //职业报告结论也选择补查
        if (dh == 1) {
            List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", patientCode).eq("f_giveup", 1));
            if (feeitems.size() > 0) {
                String[] jhys = patient.getJhys().split(",");
                String medicalType = patient.getMedicaltype();
                List<String> itemids = feeitems.stream().map(f -> f.getIdExamfeeitem()).collect(Collectors.toList());
                List<String> sumaryIds = totalHealthInspectionMapper.selectSummary(medicalType, jhys, itemids);

                //插入对应的补查职业处理意见。
                for (int i = 0; i < sumaryIds.size(); i++) {
                    String progessional = sumaryIds.get(i);
                    CommentsProgessional cp = new CommentsProgessional();
                    cp.setPatientcode(patientCode);
                    cp.setProgessionalId(progessional);
                    commentsProgessionalMapper.insert(cp);
                }

                sectionTotal.setSummaryId(BC_SUMMARY_ID);
                ZySummary zs = zySummaryMapper.selectOne(new QueryWrapper<ZySummary>().eq("id", BC_SUMMARY_ID));
                sectionTotal.setReportConclusions(zs.getOccupationSummary() + "\n" + zs.getOccupationSummaryExplain());
            }
        }

        Map<String, String> map = generateInfo(sectionTotal, dh);
        sectionTotal.setSummarize(map.get("summarize"));// 综述
        sectionTotal.setVerdict(map.get("verdict"));// 结论
        sectionTotal.setOffer(map.get("offer"));// 建议
        if (dh == 1) {
            sectionTotal.setPosistive(map.get("posistive"));//职业阳性结果
            sectionTotal.setJkoffer(map.get("jkoffer"));
            //sectionTotal.setReportConclusions(map.get("reportConclusions"));
        }
        sectionTotalMapper.updateById(sectionTotal);
        return sectionTotal;
    }

    /**
     *  判断main表里的是否有未审核的，有的话需要审核一下
     * @param patientCode
     */
    private void judgmentReviewMain(String patientCode) {
        // 能进总检的，都是所有项目已做完的，
        // 但是可能会有补检、退项等，收费项目已做完，未生成md_section_result_main的
        // 还有md_section_result_main表没变成已审状态的，在这里判断和处理
        List<String> summaryAndPicturesDtoList = sectionResultMainMapper.getNotGenerated(patientCode);
        for (String ksId : summaryAndPicturesDtoList) {
            SectionResultMain main = sectionResultMainMapper.selectOne(new LambdaQueryWrapper<SectionResultMain>()
                    .eq(SectionResultMain::getPatientcode, patientCode)
                    .eq(SectionResultMain::getDepId, ksId));
            if (ObjectUtils.isEmpty(main)){
                log.info("体检号：{},科室id{},main表未生成，生成中!",patientCode,ksId);
                createImagingDeptMain(patientCode,ksId);
            }else if (ObjectUtils.isNotEmpty(main) && (ObjectUtils.isEmpty(main.getIsAudit()) || main.getIsAudit() == 0)){
                log.info("体检号：{},科室id{},main表重新审核!",patientCode,ksId);
                main.setIsAudit(1);
                sectionResultMainMapper.updateById(main);
            }
        }

    }

    /**
     * 创建影像科室的main表
     * @param patientcode
     * @param deptNo
     */
    private void createImagingDeptMain(String patientcode, String deptNo) {
        SectionResultMain sectionResultMain = new SectionResultMain();
        sectionResultMain.setPatientcode(patientcode);
        sectionResultMain.setDepId(deptNo);
        sectionResultMain.setShortCode(ToolUtil.getShortCodeByLong(patientcode));
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientcode);
        String tjlx = peispatient.getIdExamtype();
        //生成影像科室的小结
        StringBuilder[] cons = getConclusions(peispatient.getIdExamtype(), patientcode, deptNo, peispatient.getJhys(), peispatient.getMedicaltype());
        sectionResultMain.setConclusions(cons[0].toString());
        sectionResultMain.setIsFinish(0);//未上传
        if ("1".equals(tjlx) || "3".equals(tjlx)) {
            sectionResultMain.setZyConclusions(sectionResultMain.getConclusions());
        } else if ("2".equals(tjlx)) {
            sectionResultMain.setZyConclusions(cons[1] == null ? null : cons[1].toString());
        }
        //录入人、审核人等，查询更新时间是最后的数据
        List<PacsResult> list = pacsResultMapper.selectList(
                new QueryWrapper<PacsResult>()
                        .eq("patientcode", patientcode)
                        .eq("dep_id", deptNo)
                        .orderByDesc("modifydate")
        );
        if (CollectionUtil.isNotEmpty(list)){
            PacsResult pacsResult = list.get(0);
            SysUser rummagerUser = sysUserMapper.getUserByUserName(pacsResult.getAuditDoctor());
            sectionResultMain.setRummagerId(rummagerUser.getUserNo());
            sectionResultMain.setRummagerName(pacsResult.getExamdoctor());
            sectionResultMain.setRummagerTime(pacsResult.getExamdatetime());
            SysUser writeUser = sysUserMapper.getUserByUserName(pacsResult.getAuditDoctor());
            sectionResultMain.setWriteId(writeUser.getUserNo());
            sectionResultMain.setWriteTime(pacsResult.getWriteDate());
            SysUser auditUser = sysUserMapper.getUserByUserName(pacsResult.getUsername());
            sectionResultMain.setAuditId(auditUser.getUserNo());
            sectionResultMain.setAuditName(auditUser.getUserName());
            sectionResultMain.setAuditTime(pacsResult.getWriteDate());
            sectionResultMain.setIsAudit(1);
            sectionResultMainMapper.insert(sectionResultMain);
        }
    }

    public String getHealthSummarize(String patientcode) {
        //综述
        List<SectionResultMain> sectionResultMains = sectionResultMainMapper.findResultmain(patientcode,StringUtils.equals(loadProperties.name, "hn")? 0 : 1);

        StringBuilder summary = new StringBuilder();
        int j = 1;
        for (SectionResultMain main : sectionResultMains) {
            String con = main.getConclusions();
            SysDept dept = sysDeptMapper.getByDeptNo(main.getDepId());
            if (StringUtils.isNotEmpty(con) && ObjectUtils.isNotEmpty(dept)) {
                summary.append(j + "、" + dept.getDeptName()
                        + "\n"
                        + con + StringUtil.BR);
                j++;
            }
        }
        return summary.toString();
    }


    @Override
    public Map<String, String> generateInfo(SectionTotal sectionTotal, int dh) {
        Map<String, String> data = new HashMap<String, String>();
        String patientCode = sectionTotal.getPatientcode();
        //健康
        if (dh == 0) {
            String summary = getHealthSummarize(patientCode);
            data.put("summarize", summary.length() == 0 ? "空" : summary.toString());

            //结论
            StringBuilder verdict = new StringBuilder();
            //建议
            StringBuilder offer = new StringBuilder();
            List<TotalVerdict> verdicts = totalVerdictMapper.selectList(new QueryWrapper<TotalVerdict>()
                    .orderByAsc("verdict_sort").eq("total_id", sectionTotal.getId()).eq("flag", 1));
            int m = 1, n = 1;
            Set<String> mergeIds = new HashSet<String>();
            for (TotalVerdict ver : verdicts) {
                String mergeId = ver.getMergeId();
                String conclusion_name = "";
                if (ObjectUtils.isNotEmpty(mergeId)) {
                    if (mergeIds.contains(mergeId)) {
                        continue;
                    }
                    conclusion_name = ver.getMergeName();
                } else {
                    conclusion_name = ver.getBasconclusionName();
                }
                if (!"本次体检未见异常".equals(conclusion_name)) {
                    SysDept dept = sysDeptMapper.getByDeptNo(ver.getDivisionId());
                    verdict.append(m + "、" + (ObjectUtils.isEmpty(dept) ? "" : dept.getDeptName()) + ":" + conclusion_name + StringUtil.BR);
                    m++;
                }
                String totalAdvice = ver.getTotalAdvice();
                if (ObjectUtils.isNotEmpty(totalAdvice)) {
                    offer.append(n + "、" + conclusion_name + "\n    " + totalAdvice + StringUtil.BR);
                    n++;
                }
                mergeIds.add(mergeId);
            }

            data.put("verdict", verdict.length() == 0 ? "空" : verdict.toString());
            data.put("offer", offer.length() == 0 ? "空" : offer.toString());
            //职业
        } else if (dh == 1) {
            Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode));
            String harmIds = patient.getJhys();
            String[] harmStr = ObjectUtils.isNotEmpty(harmIds) ? harmIds.split(",") : null;
            String medicalType = patient.getMedicaltype();
            //只出现有职业项目的科室
            List<SectionResultMain> sectionResultMains = sectionResultMainMapper.findOccupation(patientCode, medicalType, harmStr);


            //有弃检职业项目的科室
            List<DiscardDto> qjOs = totalHealthInspectionMapper.findDiscard(patientCode, harmStr, medicalType);

            //所有职业拒检项目，展现在综述和职业阳性结果
            List<String> jjlist = totalHealthInspectionMapper.findRefusal(patientCode, harmStr, medicalType);

            String jjxms = (jjlist.size() > 0 && jjlist.get(0) != null)
                    ? ("拒检项目：" + Render.getClob(jjlist.get(0)) + "。")
                    : "";
            //合并排序
            List<Map<String, Object>> zyOs = new ArrayList<Map<String, Object>>();
            Map<String, Map<String, Object>> zyMa = new HashMap<String, Map<String, Object>>();
            for (SectionResultMain srm : sectionResultMains) {
                Map<String, Object> ma = new HashMap<String, Object>();
                ma.put("srm", srm);
                SysDept dept = sysDeptMapper.getByDeptNo(srm.getDepId());

                ma.put("ks", dept);
                zyOs.add(ma);
                zyMa.put(srm.getDepId(), ma);
            }
            for (int i = 0; i < qjOs.size(); i++) {
                DiscardDto discardDto = qjOs.get(i);
                String ksId = discardDto.getDeptId();
                Map<String, Object> ma = zyMa.get(ksId);
                if (ObjectUtils.isEmpty(ma)) {
                    ma = new HashMap<String, Object>();
                    SysDept dept = sysDeptMapper.getByDeptNo(ksId);
                    ma.put("ks", dept);
                    ma.put("items", discardDto.getExamfeeitemName());
                    zyOs.add(ma);
                    zyMa.put(ksId, ma);
                } else {
                    ma.put("items", discardDto.getExamfeeitemName());
                }
            }
            Collections.sort(zyOs, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1,
                                   Map<String, Object> o2) {
                    SysDept dept1 = (SysDept) o1.get("ks");
                    SysDept dept2 = (SysDept) o2.get("ks");
                    Integer sort1 = dept1.getReportSort();
                    Integer sort2 = dept2.getReportSort();
                    return sort1 == null ? -1 : sort2 == null ? 1 : sort1.compareTo(sort2);
                }
            });

            //综述
            StringBuilder summary = new StringBuilder();
            //职业阳性结果
            StringBuilder posistive = new StringBuilder();
            //职业阳性结果科室
            List<String> posistiveDepts = new ArrayList<>();
            int j = 1, k = 1;
            for (Map<String, Object> map : zyOs) {
                SectionResultMain main = map.get("srm") == null ? null : (SectionResultMain) map.get("srm");
                SysDept dept = (SysDept) map.get("ks");
                String depname = dept.getDeptName();
                String items = map.get("items") == null ? null : map.get("items").toString();
                //综述显示弃检项目
                if (main != null) {
                    String con = main.getZyConclusions();
                    if (StringUtils.isNotEmpty(con)) {
                        summary.append(j + "、" + depname
                                + "\n"
                                + con);
                        j++;
                        if (StringUtils.isNotEmpty(items)) {
                            summary.append(StringUtil.BR + "弃检项目：" + items + StringUtil.BR);
                        } else {
                            summary.append(StringUtil.BR);
                        }
                    } else if (StringUtils.isNotEmpty(items)) {
                        summary.append(j + "、" + depname
                                + StringUtil.BR + "弃检项目："
                                + items + StringUtil.BR);
                        j++;
                    }
                } else if (StringUtils.isNotEmpty(items)) {
                    summary.append(j + "、" + depname
                            + StringUtil.BR + "弃检项目："
                            + items + StringUtil.BR);
                    j++;
                }


                if (ObjectUtils.isNotEmpty(main)) {
                    String con = main.getZyConclusions();

                    if (StringUtils.isNotEmpty(con) && ObjectUtils.isNotEmpty(dept)) {

                        if (ObjectUtils.isNotEmpty(dept.getSjbggs()) && dept.getSjbggs() == 3) {//一般检查
//                            SectionResultTwo xy = sectionResultTwoMapper.selectOne(new QueryWrapper<SectionResultTwo>().eq("patientcode", patientCode)
//                                    .eq("verdict_id", "436"));
//                            SectionResultTwo sg = sectionResultTwoMapper.selectOne(new QueryWrapper<SectionResultTwo>().eq("patientcode", patientCode)
//                                    .eq("verdict_id", "14"));
//                            if (ObjectUtils.isEmpty(xy)) {
//                                con = con.replaceAll("收缩压:[^;]*;", "");
//                                con = con.replaceAll("舒张压:[^;]*;", "");
//                                con = con.replaceAll("血压结论:[^;]*;", "");
//                            }
//                            if (sg == null) {
//                                con = con.replaceAll("身高:[^;]*;", "");
//                                con = con.replaceAll("体重:[^;]*;", "");
//                                con = con.replaceAll("体重指数:[^;]*;", "");
//                            }
                            //职业的最多有个体重，显示体重指数，跟肥胖啥的
                            //奥，呼吸频率和营养状况是特殊危害因素做的，一般也不会做。最常见的是只测血压

                           //只有出现血压异常的才展示在阳性结果里面
                           SectionResultTwo xy = sectionResultTwoMapper.selectOne(new QueryWrapper<SectionResultTwo>().eq("patientcode", patientCode)
                                            .eq("verdict_id", "436"));
                           //平度显示全部
                           if (ObjectUtils.isEmpty(xy) && !StringUtils.equals(loadProperties.name, "pingdu")){
                               continue;
                           }
                            //职业阳性结果只显示血压结论(除了平度)
                            if (!StringUtils.equals(loadProperties.name, "pingdu")) {
                                con = con.replaceAll("脉搏:[^;]*;", "");
                                con = con.replaceAll("呼吸频率:[^;]*;", "");
                                con = con.replaceAll("营养状况:[^;]*;", "");
//                            con = con.replaceAll("收缩压:[^;]*;", "");
//                            con = con.replaceAll("舒张压:[^;]*;", "");
                                con = con.replaceAll("身高:[^;]*;", "");
                                con = con.replaceAll("体重:[^;]*;", "");
                                con = con.replaceAll("体重指数:[^;]*;", "");
                            }
                            if (con.trim().length() == 0) {
                                continue;
                            }
                        } else if (con.indexOf("未见") != -1 && con.indexOf("异常") != -1) {
                            log.info("生成综述-科室包含未见和异常：｛｝",depname);
                            continue;
                        }
                        //有阳性体证词的或者是电测听科室(>=30) PACS科室(CR DR 彩超 CT) 小结进阳性结果
                        if (ObjectUtils.isNotEmpty(dept.getSjbggs()) && (dept.getSjbggs() == 9)) {
                            ElectroAudiometer ea = electroAudiometerMapper.selectOne(new QueryWrapper<ElectroAudiometer>().eq("patientcode", patientCode));
                            if (ObjectUtils.isNotEmpty(ea)) {
                                boolean in = false;
                                List<String> harmIdArr = Arrays.asList(harmIds.split(","));

                                //噪声
                                if (harmIdArr.contains("127")) {
                                    //左右耳气导任一500、1000、2000>25db 进阳性
                                    if ((ea.getAirLeft500() != null && ea.getAirLeft500() > 25)
                                            || (ea.getAirLeft1000() != null && ea.getAirLeft1000() > 25)
                                            || (ea.getAirLeft2000() != null && ea.getAirLeft2000() > 25)
                                            || (ea.getAirRight500() != null && ea.getAirRight500() > 25)
                                            || (ea.getAirRight1000() != null && ea.getAirRight1000() > 25)
                                            || (ea.getAirRight2000() != null && ea.getAirRight2000() > 25)
                                    ) {
                                        in = true;
                                    }

                                    //双耳大于等于30
                                    if (!in) {
                                        String testResult = ea.getTestResult();
                                        if (ObjectUtils.isNotEmpty(testResult)) {
                                            int start = testResult.indexOf("双耳高频平均听阈");
                                            int end = testResult.indexOf("dB。");
                                            if (start != -1 && end != -1) {
                                                BigDecimal bigDecimal = new BigDecimal(testResult.substring(start + 8, end));
                                                if (bigDecimal.compareTo(new BigDecimal(30)) >= 0) {
                                                    in = true;
                                                }
                                            }
                                        }
                                    }
                                }
                                //压力容器作业
                                if (!in && harmIdArr.contains("155")) {
                                    String testResult = ea.getTestResult();
                                    if (ObjectUtils.isNotEmpty(testResult)) {
                                        int start = testResult.indexOf("双耳高频平均听阈");
                                        int end = testResult.indexOf("dB。");
                                        if (start != -1 && end != -1) {
                                            if (new BigDecimal(testResult.substring(start + 8, end)).compareTo(new BigDecimal(25)) > 0) {
                                                in = true;
                                            }
                                        }
                                    }

                                }
                                //职业机动车驾驶作业
                                if (!in && harmIdArr.contains("158")) {
                                    String testResult = ea.getTestResult();
                                    if (testResult != null) {
                                        int start = testResult.indexOf("双耳高频平均听阈");
                                        int end = testResult.indexOf("dB。");
                                        if (start != -1 && end != -1) {
                                            if (new BigDecimal(testResult.substring(start + 8, end)).compareTo(new BigDecimal(30)) > 0) {
                                                in = true;
                                            }
                                        }
                                    }
                                }

                                if (in) {
                                    posistive.append(k + "、" + depname + "\n" + con + StringUtil.BR);
                                    posistiveDepts.add(depname);
                                    k++;
                                }
                            }

                        } else if ("143".equals(dept.getDeptNo())
                                || "171".equals(dept.getDeptNo())
                                || "24".equals(dept.getDeptNo())
                                || "173".equals(dept.getDeptNo())
                                ||
                                sectionResultTwoMapper.selectCount(new QueryWrapper<SectionResultTwo>()
                                        .eq("posistive", 1)
                                        .ne("tjlx", 0)
                                        .eq("main_id", main.getId())
                                        //内科、外科既往史，如果只有既往史有异常，不体现，如果其他检查项目有异常，体现其他项目和既往史
                                        .and(i -> i.isNull("verdict_id")
                                                .or()
                                                .notIn("verdict_id", Arrays.asList("388", "461")))
                                ) > 0) {
                            posistive.append(k + "、" + depname + "\n" + con + StringUtil.BR);
                            posistiveDepts.add(depname);
                            k++;
                        }
                    }

                }
            }
            if (StringUtils.isNotEmpty(jjxms)) {
                if (summary.length() > 0) {
                    summary.append(StringUtil.BR);
                }
                summary.append(jjxms);
                if (posistive.length() > 0) {
                    posistive.append(StringUtil.BR);
                }
                posistive.append(jjxms);
            }
            data.put("summarize", summary.length() == 0 ? "空" : summary.toString());
            data.put("posistive", posistive.length() == 0 ? "无异常" : posistive.toString());
            data.put("posistiveDepts", CollectionUtil.isEmpty(posistiveDepts) ? "无" : String.join(",", posistiveDepts));

            List<TotalVerdict> verdicts = totalVerdictMapper.selectList(new QueryWrapper<TotalVerdict>()
                    .orderByAsc("verdict_sort").eq("total_id", sectionTotal.getId()).eq("flag", 1));
            //结论
            StringBuilder verdict = new StringBuilder();
            //健康建议
            StringBuilder jkoffer = new StringBuilder();
            j = 1;
            k = 1;
            Set<String> mergeIds = new HashSet<String>();
            for (TotalVerdict ver : verdicts) {
                String mergeId = ver.getMergeId();
                String conclusion_name = "";
                if (mergeId != null) {
                    if (mergeIds.contains(mergeId)) {
                        continue;
                    }
                    conclusion_name = ver.getMergeName();
                } else {
                    conclusion_name = ver.getBasconclusionName();
                }
                if (!conclusion_name.equals("本次体检未见异常")
                        && !conclusion_name.equals("未见异常")) {
                    SysDept dept = sysDeptMapper.selectDeptById(Long.valueOf(ver.getDivisionId()));
                    verdict.append(j + "、" + (dept == null ? "" : dept.getDeptName()) + ":" + conclusion_name + StringUtil.BR);
                    j++;
                }
                String totalAdvice = ver.getTotalAdvice();
                if (ObjectUtils.isNotEmpty(totalAdvice)) {
                    jkoffer.append(k + "、" + conclusion_name + "\n" + totalAdvice + StringUtil.BR);
                    k++;
                }
                mergeIds.add(mergeId);
            }
            data.put("verdict", verdict.length() == 0 ? "空" : verdict.toString());
            data.put("jkoffer", jkoffer.length() == 0 ? "空" : jkoffer.toString());

            //职业结论及建议
            String[] cs = generateCom(patientCode);
            data.put("offer", cs[0]);
            data.put("reportConclusions", cs[1]);

        }

        return data;
    }


    /**
     * 生成职业处理意见。新增一条时，在diseaseTotalService.saveTreatment方法中生成
     *
     * @param patientCode
     * @return String
     * 所有职业处理意见 、职业报告结论都在这里生成
     * @Title: generateCom
     */
    public String[] generateCom(String patientCode) {
        List<CommentsProgessional> commentsProgessionals = commentsProgessionalMapper.selectList(
                new QueryWrapper<CommentsProgessional>().eq("patientcode", patientCode));
        //职业结论及建议
        StringBuilder offer = new StringBuilder();
        //职业报告结论词
        StringBuilder reportConclusions = new StringBuilder();
        int j = 1;
        Integer sn = 99;//最高优先级
        CommentsProgessional hignestCom = null;//最高优先级结论词
        //所有补查结论词，补查建议总出现在报告结论词中
        List<CommentsProgessional> buchaCom = new ArrayList<CommentsProgessional>();
        //电测听（>30db）、粉尘（肺纹理增强）等结论词的建议始终出现在报告结论词中
        List<CommentsProgessional> extraCom = new ArrayList<CommentsProgessional>();
        for (CommentsProgessional com : commentsProgessionals) {
            ZyVsSummary zyVsSummary = zyVsSummaryMapper.selectOne(new QueryWrapper<ZyVsSummary>()
                    .eq("id", com.getProgessionalId()));
            if (ObjectUtils.isNotEmpty(zyVsSummary) && ObjectUtils.isNotEmpty(zyVsSummary.getSummaryText())) {
                Harm harm = harmMapper.selectOne(new QueryWrapper<Harm>().eq("id", zyVsSummary.getOccupationDiagnosis()));
                ZySummary sum = zySummaryMapper.selectOne(new QueryWrapper<ZySummary>().eq("id", zyVsSummary.getOccupationSummary()));
                if (ObjectUtils.isNotEmpty(harm) && ObjectUtils.isNotEmpty(sum)) {
                    offer.append(j + "、" + harm.getHarmName() + "：" + sum.getOccupationSummary() + "\n");
                    Integer serialNo = sum.getSerialNo();
                    if (ObjectUtils.isNotEmpty(serialNo)) {
                        if (serialNo == 2 || serialNo == 3) {//复查或职业禁忌症 显示 : 禁忌症,
                            if (zyVsSummary.getDiagnosis() != null && !"无".equals(zyVsSummary.getDiagnosis().trim())) {
                                offer.append(zyVsSummary.getDiagnosis() + "，");
                            }
                        } else if (serialNo == 1) {//疑似职业病  显示 职业病，
                            if (zyVsSummary.getOccupationDiseast() != null) {
                                OccupationDiseast o = occupationDiseastMapper.getInfoById(zyVsSummary.getOccupationDiseast());
                                if (ObjectUtils.isNotEmpty(o) && ObjectUtils.isNotEmpty(o.getOccupationDiseast()) && !"无".equals(o.getOccupationDiseast().trim())) {
                                    offer.append(o.getOccupationDiseast() + "，");
                                }
                            }
                        }

                        //收集职业报告结论词信息
                        //默认最高级别只有一条（杨爽：如果多条，以后再说）
                        if (serialNo < sn) {
                            sn = serialNo;
                            hignestCom = com;
                        }
                        if (ObjectUtils.isNotEmpty(zyVsSummary.getAlwaysInReport())
                                && zyVsSummary.getAlwaysInReport() == 1) {
                            //语频正常，双耳高频平均听阈＞30dB，建议加强个人听力防护。
                            //肺纹理增强，建议禁烟、加强个人防护。
                            //这两种建议都要出现在职业报告结论词中
                            extraCom.add(com);
                        }
                        if (serialNo == 7) {
                            buchaCom.add(com);
                        }
                    }
                    offer.append(zyVsSummary.getSummaryText() + StringUtil.BR);
                    j++;
                }
            }
        }

        //生成职业报告结论词
        if (ObjectUtils.isNotEmpty(hignestCom)) {
            //仅一条不生成序号
            boolean onlyOne = false;
            if (buchaCom.size() > 1 || extraCom.size() > 1) {
                onlyOne = false;
            } else if (buchaCom.size() == 0 && extraCom.size() == 0) {
                onlyOne = true;
            } else {
                if (buchaCom.size() == 1 && !buchaCom.get(0).getId().equals(hignestCom.getId())) {
                    onlyOne = false;
                } else if (extraCom.size() == 1
                        && !extraCom.get(0).getId().equals(hignestCom.getId())) {
                    onlyOne = false;
                } else {
                    onlyOne = true;
                }
            }
            //序号
            int index = 1;
            ZyVsSummary zyVsSummary = zyVsSummaryMapper.selectOne(new QueryWrapper<ZyVsSummary>()
                    .eq("id", hignestCom.getProgessionalId()));
            ZySummary sum = zySummaryMapper.selectOne(new QueryWrapper<ZySummary>().eq("id", zyVsSummary.getOccupationSummary()));
            boolean bc = false;//最高级别为补查
            Set<String> comIds = new HashSet<String>();//去重(不同因素处理意见可能相同，这里用处理意见去重)
            String summaryText = StringUtil.addMark(zyVsSummary.getSummaryText());
            if (sn == 2 || sn == 3) {//复查或职业禁忌症 显示 : 禁忌症,
                reportConclusions.append(sum.getOccupationSummary() + ":" + "\n" + (onlyOne ? "" : ((index++) + "、")));
                if (ObjectUtils.isNotEmpty(zyVsSummary.getDiagnosis()) && !"无".equals(zyVsSummary.getDiagnosis().trim())) {
                    reportConclusions.append(zyVsSummary.getDiagnosis() + "，");
                }
                reportConclusions.append(summaryText + "\n");
                comIds.add(summaryText);
            } else if (sn == 1) {//疑似职业病  显示 职业病，
                reportConclusions.append(sum.getOccupationSummary() + ":" + "\n" + (onlyOne ? "" : ((index++) + "、")));
                if (ObjectUtils.isNotEmpty(zyVsSummary.getOccupationDiseast())) {
                    OccupationDiseast o = occupationDiseastMapper.getInfoById(zyVsSummary.getOccupationDiseast());
                    if (ObjectUtils.isNotEmpty(o) && ObjectUtils.isNotEmpty(o.getOccupationDiseast()) && !"无".equals(o.getOccupationDiseast().trim())) {
                        reportConclusions.append(o.getOccupationDiseast() + "，");
                    }
                }
                reportConclusions.append(summaryText + "\n");
                comIds.add(summaryText);
            } else if (buchaCom.size() > 0) {//如果最高级别不是 疑似职业病、职业禁忌症、复查，有补查就显示补查
                bc = true;
            } else {//显示最高级别
                reportConclusions.append(sum.getOccupationSummary() + ":" + "\n" + (onlyOne ? "" : ((index++) + "、")));
                reportConclusions.append(summaryText + "\n");
                comIds.add(summaryText);
            }

            buchaCom.addAll(extraCom);
            for (int i = 0; i < buchaCom.size(); i++) {
                CommentsProgessional c = buchaCom.get(i);
                ZyVsSummary zyVsSummaryi = zyVsSummaryMapper.selectOne(new QueryWrapper<ZyVsSummary>().eq("id", c.getProgessionalId()));
                String summaryTexti = StringUtil.addMark(zyVsSummaryi.getSummaryText());
                if (i == 0 && bc) {
                    ZySummary sumi = zySummaryMapper.selectOne(new QueryWrapper<ZySummary>().eq("id", zyVsSummaryi.getOccupationSummary()));
                    reportConclusions.append(sumi.getOccupationSummary() + ":" + "\n" + (onlyOne ? "" : ((index++) + "、")));
                    reportConclusions.append(summaryTexti + "\n");
                    comIds.add(summaryTexti);
                }
                if (!comIds.contains(summaryTexti)) {
                    reportConclusions.append((onlyOne ? "" : ((index++) + "、")) + summaryTexti + "\n");
                    comIds.add(summaryTexti);
                }
            }
        }

        return new String[]{offer.length() == 0 ? "空" : offer.toString(),
                reportConclusions.length() == 0 ? "空" : reportConclusions.toString()
        };
    }

    /**
     * 健康和职业总检保存
     *
     * @param totalHealthSaOrUpParam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> saOrUp(TotalHealthSaOrUpParam totalHealthSaOrUpParam) {
        log.info("健康和职业总检保存参数：{}",totalHealthSaOrUpParam);
        int dh = totalHealthSaOrUpParam.getDh();
        int type = totalHealthSaOrUpParam.getType();
        BgFormData formdata = totalHealthSaOrUpParam.getBgFormData();
        List<BgGriddata> griddata = totalHealthSaOrUpParam.getBgGriddata();

        Map<String, String> result = new HashMap<String, String>();
        if (ObjectUtils.isEmpty(formdata)) {
            throw new ServiceException("数据错误，请查证后重新操作");
        }

        String patientCode = formdata.getPatientCode();
        if (ObjectUtils.isEmpty(patientCode)) {
            throw new ServiceException("体检号为空，请查证后重新操作");
        }
        Peispatient pp = null;
        if (type != 0) {
            pp = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode));
            if (pp == null) {
                throw new ServiceException("此体检号的体检者信息不存在");
            }
            //@sqlorder
            pp.setModifydate(new Date());

        }
        /*主表*/
        SectionTotal sectionTotal = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientCode).eq("disease_health", dh));
        if (ObjectUtils.isEmpty(sectionTotal)) {
            throw new ServiceException("系统无法查询到总检信息，请查证后重新操作");
        }
        sectionTotal.setVerdict(formdata.getVerdict());
        sectionTotal.setOffer(formdata.getOffer());
        sectionTotal.setSummarize(formdata.getSummary());
        if (dh == 1) {
            sectionTotal.setJkoffer(formdata.getJkoffer());
            sectionTotal.setPosistive(formdata.getPosistive());
            sectionTotal.setReportConclusions(formdata.getReportConclusions());
            sectionTotal.setSummaryId(formdata.getSummaryId());
        }
        String userId = SecurityUtils.getUserNo();
        String userName = SecurityUtils.getUsername();
        Date now = new Date();
        if (type != 0) {
            sectionTotal.setDoctorId(userId);// 总检医师
            sectionTotal.setTotalTime(now);// 总检时间
            sectionTotal.setWriteId(userId);// 录入人
            sectionTotal.setWriteTime(now);// 录入时间
            sectionTotal.setScbs(0);//上传标志

            /* 插入报告 */
            Report report = reportMapper.selectOne(new QueryWrapper<Report>()
                    .eq("patientcode", patientCode).eq("disease_health", dh));
            if (ObjectUtils.isEmpty(report)) {
                report = new Report();
                report.setDiseaseHealth(dh);
            }
            report.setPatientcode(patientCode);
            report.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
            //取出auditPrint配置文件
            SysConfig sysConfig = sysConfigMapper.checkConfigKeyUnique("auditPrint");
            if (ObjectUtils.isNotEmpty(sysConfig)) {
                if (sysConfig.getConfigType().equals("Y")) {
                    report.setStatus(2);
                    report.setIsTotal(1);
                } else {
                    report.setStatus(1);
                    report.setIsTotal(0);
                }
            } else {
                report.setStatus(1);
                report.setIsTotal(0);
            }
            report.setGrantId(pp.getIdInformway());
            report.setPatientname(pp.getPatientname());
            report.setAge(pp.getAge() == null ? null : (int) Math.floor(pp.getAge()));
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
            report.setDoctorfinalNameR(userName);
            report.setDatefinalexamed(now);
            List<Review> reviews = reviewMapper.selectList(new QueryWrapper<Review>()
                    .eq("patientcode", patientCode).eq("is_delete", 0));
            if (CollectionUtils.isEmpty(reviews)) {
                report.setIsPrintMessage(0);//是否打印复查通知单
            }
            reportService.saveOrUpdate(report);

            /*体检者*/
            if (dh == 0) {
                pp.setIdDoctorfinal(SecurityUtils.getUserNo());
                pp.setDoctorfinalNameR(userName);
                pp.setDatefinalexamed(now);// 总检时间
                if (type == 1) {
                    pp.setJktjzt(0);
                } else {
                    pp.setJktjzt(1);
                    setScbs(pp, 0);
                    pp.setFFinallocked(1);
                }
            } else if (dh == 1) {
                pp.setDateregisternotime(now);//职业总检时间
                pp.setPatientcodehiden(SecurityUtils.getUserNo());//职业总检医生ID
                pp.setPatientnameencoded(userName);//职业总检医生名字
                if (type == 1) {
                    pp.setZytjzt(0);
                } else {
                    pp.setZytjzt(1);
                    setScbs(pp, 0);
                    pp.setIdGuidenurse(Double.valueOf(1));//职业锁定
                }
            }
            PeisState ps = setScbs(pp, 0);
            ps.setFInneroper(0);
            ps.setIdGuidancereturnedby(0);//收费项目上传标识
            peispatientMapper.updateById(pp);
        }
        sectionTotalMapper.updateById(sectionTotal);
        String mainId = sectionTotal.getId();

        /*子表*/
        if (dh == 0) {
            // 健康
            if (CollectionUtils.isNotEmpty(griddata)) {
                for (int i = 0, s = griddata.size(); i < s; i++) {
                    BgGriddata map = griddata.get(i);
                    String status = map.getState();
                    TotalVerdict totalVerdict = null;
                    //added添加，modified修改，removed删除
                    if ("added".equals(status)) {
                        totalVerdict = new TotalVerdict();
                    } else if ("modified".equals(status)) {
                        String id = map.getId();
                        totalVerdict = totalVerdictMapper.getInfoById(id);
                        if (ObjectUtils.isEmpty(totalVerdict)) {
                            totalVerdict = new TotalVerdict();
                        }
                    } else if ("removed".equals(status)) {
                        totalVerdictMapper.delete(new QueryWrapper<TotalVerdict>().eq("id", map.getId()));
                        continue;
                    }
                    //前段有时候会传过来一个basconclusionId是null的,跳过
                    String basconclusionId  = map.getBasconclusionId();
                    if (StringUtils.isBlank(basconclusionId)){
                        continue;
                    }
                    totalVerdict.setTotalId(mainId);
                    totalVerdict.setDiseaseHealth(dh);
                    totalVerdict.setBasconclusionId(basconclusionId);
                    totalVerdict.setDivisionId(map.getDivisionId());
                    totalVerdict.setIsDelete(0);
                    totalVerdict.setTotalAdvice(map.getTotalAdvice());
                    totalVerdict.setVerdictSort(Integer.parseInt(map.getSort()));
                    totalVerdict.setMergeId(map.getMergeId());
                    totalVerdict.setMergeName(map.getMergeName());
                    totalVerdict.setBasconclusionName(map.getBasconclusion());
                    totalVerdict.setFlag(map.getSee() == null ? 1 : map.getSee());
                    totalVerdict.setSuggestiongroup(map.getTjjy());
                    totalVerdictService.saveOrUpdate(totalVerdict);
                }
            }
        }
        /**审核医生保存*/
        if (type == 2 && dh == 1) {
            totalDoctorMapper.delete(new QueryWrapper<TotalDoctor>().eq("total_id", mainId));
            String doctors = formdata.getDoctors();
            if (StringUtils.isNotEmpty(doctors)) {
                for (String did : doctors.split(",")) {
                    TotalDoctor totalDoctor = new TotalDoctor();
                    totalDoctor.setTotalId(mainId);
                    totalDoctor.setUserId(did);
                    totalDoctor.setType(1);
                    totalDoctorMapper.insert(totalDoctor);
                }
            }
            TotalDoctor totalDoctor = new TotalDoctor();
            totalDoctor.setTotalId(mainId);
            totalDoctor.setUserId(userId);
            totalDoctor.setType(0);
            totalDoctorMapper.insert(totalDoctor);
        }

        /**发送短信(普通模板每天每号有20条限制，验证码模板为5条)*/
        //职业  审核
        if (dh == 1 && type == 2) {
            //如果有模板
            Shortmessage template = shortmessageMapper.selectOne(new QueryWrapper<Shortmessage>()
                    .eq("message_type", "4").eq("is_delete", 0));
            if (ObjectUtils.isNotEmpty(template)) {
                //如果配置了领导的电话
                String phone = Constants.LEADER_PHONE;
                if (StringUtils.isNotEmpty(phone)) {
                    //按体检号查询 最高级为禁忌症或职业病的 最高级级别
                    List<String> obj = totalHealthInspectionMapper.getUniqueResult(patientCode);

                    //如果存在职业病或禁忌症，发短信
                    if (CollectionUtils.isEmpty(obj)) {
                        List<String> params = new ArrayList<String>();
                        String paramTemplage = template.getParams();
                        if (ObjectUtils.isNotEmpty(paramTemplage)) {
                            for (String str : paramTemplage.split(",")) {
                                if (str.equals("1")) {
                                    params.add(pp.getPatientname());
                                } else if (str.equals("2")) {
                                    params.add(pp.getPatientcode());
                                } else if (str.equals("3")) {
                                    params.add("1".equals(obj.toString()) ? "可疑职业病" : "职业禁忌症");
                                }
                            }
                        }
                        SmsConfig smsConfig = iSysConfigService.getSysConfigObject(Constants.SMS_CONFIG,SmsConfig.class);
                        String sms_result = SDKTestSendTemplateSMS.sendMsg(smsConfig,phone, template.getTemplateId()
                                , params.toArray(new String[params.size()])
                                , template.getAppid());
                        if (!"success".equals(sms_result)) {
                            result.put("status", "success");
                            result.put("msg", "审核成功,<font color='red' style='font-size: 12px'>但给领导发送短信失败！</font>是否生成报告?");
                            return result;
                        }
                    }
                }
            }
        }
        result.put("status", "success");
        result.put("msg", type == 2 ? "审核成功！是否生成报告?" : "保存成功！");
        return result;
    }


    /**
     * 健康同步
     *
     * @param patientCode
     * @param dh
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean synchronize(String patientCode, int dh) {
        SectionTotal section = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientCode).eq("disease_health", dh)
        );
        if (ObjectUtils.isEmpty(section)) {
            throw new ServiceException("数据错误，请查证后重新操作");
        }
        String mainId = section.getId();
        List<TotalVerdict> verdicts = totalVerdictMapper.selectList(new QueryWrapper<TotalVerdict>()
                .orderByDesc("verdict_sort").eq("total_id", mainId));
        Set<String> savedConId = new HashSet<String>();
        for (TotalVerdict t : verdicts) {
            savedConId.add(t.getBasconclusionId());
        }
        int sort = verdicts.size() == 0 ? 0 : verdicts.size() + 1;

        //sectionResultTwo的数据
        List<FindByCodeDhDto> sections = sectionResultTwoMapper.findByCodeDh(patientCode, dh);
        //pacs sectionResultTwo的数据
        List<FindByCodeDhDto> pacs = sectionResultTwoMapper.findPacsByCodeDh(patientCode, dh);
        sections.addAll(pacs);

        for (int i = 0, s = sections.size(); i < s; i++) {
            FindByCodeDhDto os = sections.get(i);
            String basId = os.getBasconclusionId();
            if (savedConId.contains(basId)) {
                continue;
            }

            TotalVerdict ver = new TotalVerdict();
            //下结论的科室，应该为结论词科室
            ver.setDivisionId(os.getDivisionId());
            ver.setBasconclusionName(os.getName());
            ver.setTotalAdvice(os.getSuggestion());
            ver.setBasconclusionId(basId);
            ver.setTotalId(mainId);
            ver.setDiseaseHealth(Integer.valueOf(dh));
            ver.setFlag(1);
            ver.setVerdictSort(sort + i);
            totalVerdictMapper.insert(ver);
        }

        /**重新生成综述等*/
        Map<String, String> totaldata = generateInfo(section, dh);
        section.setSummarize(totaldata.get("summarize"));
        section.setVerdict(totaldata.get("verdict"));
        section.setOffer(totaldata.get("offer"));
        if (dh == 1) {
            section.setJkoffer(totaldata.get("jkoffer"));
            section.setPosistive(totaldata.get("posistive"));
        }
        sectionTotalMapper.updateById(section);
        return Boolean.TRUE;
    }

    /**
     * 是否可以总检
     *
     * @param patientcode
     * @param dh
     * @param flag
     * @return
     */
    @Override
    public Boolean canTotal(String patientcode, int dh, String flag) {
        if (StringUtils.isEmpty(patientcode)) {
            throw new ServiceException("error@体检号不能为空");
        }
        if (flag != null && flag.equals("true")) {
            patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
        }
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientcode));
        if (ObjectUtils.isEmpty(patient)) {
            throw new ServiceException("error@体检号" + patientcode + "不存在");
        }
        if (dh == 0) {
            if ("1".equals(patient.getIdExamtype())) {
                throw new ServiceException("error@此体检号的体检类型为职业体检，不能进健康总检！");
            }
            if ("3".equals(patient.getIdExamtype())) {
                throw new ServiceException("error@此体检号的体检类型为复查，不能进健康总检！");
            }
        } else {
            if ("0".equals(patient.getIdExamtype())) {
                throw new ServiceException("error@此体检号的体检类型为健康体检，不能进职业总检！");
            }
        }
        //体检者收费项目表
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientcode)
                .isNull("f_transferedhl7") //补检状态 0: 未补检 1：已补检
                .eq("sfjj", 0) //
                .eq("f_giveup", 0)
                .eq("change_item", 0)
                .ne("f_examinated", 1)
        );
        if (feeitems.size() > 0) {
            List<String> itemNames = new ArrayList<String>();
            for (Peispatientfeeitem feeitem : feeitems) {
                itemNames.add(feeitem.getExamfeeitemName());
            }
            throw new ServiceException("error@存在未检的收费项目:" + StringUtils.join(itemNames, "、") + "，不能总检");
        }

        //通过体检码查询收费项目名称
        List<String> list = peispatientfeeitemMapper.findExamitemNameByCode(patientcode);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new ServiceException("error@存在未获取结果的检验科项目：" + StringUtils.join(list, "、")
                    + "，不能总检");
        }
        return Boolean.TRUE;
    }


    /**
     * 存入词库（线下总检）
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String maintain(MaintainParam param) {
        //是否合并
        String isMerge = param.getIsMerge();
        if ("1".equals(isMerge)) {
            BasMerge bm = new BasMerge();
            bm.setMergeName(param.getName());
            bm.setCreater(SecurityUtils.getUsername());
            bm.setSuggestion(param.getZjjy());
            bm.setTjjy(param.getTjjy());
            bm.setInputCode(ToolUtil.getHanziPinyinHeadChar(bm.getMergeName()));
            bm.setIsDelete(0);
            basMergeMapper.insert(bm);
            String mainId = bm.getId();
            String conIds = param.getConIds();
            if (StringUtils.isEmpty(conIds)) {
                throw new ServiceException("没有选择需要合并的结论词");
            }
            String[] arr = conIds.split(",");
            for (String conId : arr) {
                //插入中间表
                BasMergeConclusion basMergeConclusion = new BasMergeConclusion();
                basMergeConclusion.setConclusionId(conId);
                basMergeConclusion.setMergeId(mainId);
                basMergeConclusionMapper.insert(basMergeConclusion);
            }
            return mainId;
        } else {
            //总检结论词id
            Basconclusion con = new Basconclusion();
            con.setCreater(SecurityUtils.getUsername());
            con.setName(param.getName());
            con.setSuggestion(param.getZjjy());
            con.setSuggestiongroup(param.getTjjy());
            con.setStatus(1);
            con.setIsDelete(0);
            con.setDivisionId(param.getKs());
            SysDept d = sysDeptMapper.getByDeptNo(con.getDivisionId());
            con.setDepName(d.getDeptName());
            con.setInputCode(ToolUtil.getHanziPinyinHeadChar(con.getName()));
            con.setIsLong(1);
            con.setIsPublic(0);

            String cid = branchMapper.selectOne(new QueryWrapper<Branch>().eq("is_default", 1)
                    .eq("is_delete", 0)).getBranchId();
            con.setFzxIds(cid);
            con.setAuditStatus(0);
            con.setScbz(0);
            basconclusionMapper.insert(con);


            //中间表
            ConclusionAndFzx conclusionAndFzx = new ConclusionAndFzx();
            conclusionAndFzx.setConclusionId(con.getId());
            conclusionAndFzx.setFzxId(cid);
            conclusionAndFzx.setTbzt(1);
            conclusionAndFzxMapper.insert(conclusionAndFzx);
            return con.getId();
        }
    }


    /**
     * 根据体检号锁定/解锁 改变总检状态
     *
     * @param ids
     * @param state
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean lock(List<String> ids, Integer state) {
        //获取所有记录
        List<Peispatient> peispatients = peispatientMapper.selectList(new LambdaQueryWrapper<Peispatient>()
                .in(Peispatient::getPatientcode, ids));
        //TODO 增加数据库锁，以避免多人重复操作
        if (CollectionUtil.isNotEmpty(peispatients)) {
            for (Peispatient peispatient : peispatients) {
                if (state == 1) {
                    //锁定
                    if (StringUtils.isNotBlank(peispatient.getIdDoctorapply())
                            && !StringUtils.equals(peispatient.getIdDoctorapply(), SecurityUtils.getUsername())) {
                        throw new ServiceException("该记录已经由" + peispatient.getIdDoctorapply() + "医师锁定,请等待该医生解锁!");
                    }
                    if (Objects.nonNull(peispatient.getFFinallocked()) && peispatient.getFFinallocked() == 1) {
                        throw new ServiceException("该记录已经是锁定状态!");
                    }
                    peispatient.setFFinallocked(1);// 总检锁定: 1.锁定 0.解锁
                    peispatient.setIdDoctorapply(SecurityUtils.getUsername());
                } else {
                    //解锁
                    if (StringUtils.isNotBlank(peispatient.getIdDoctorapply())
                            && !StringUtils.equals(peispatient.getIdDoctorapply(), SecurityUtils.getUsername())) {
                        throw new ServiceException("该记录已经由" + peispatient.getIdDoctorapply() + "医师锁定,请等待该医生解锁!");
                    }
                    if (Objects.nonNull(peispatient.getJktjzt()) && peispatient.getJktjzt() == 1) {
                        throw new ServiceException("总检完成不允许解锁!");
                    }
                    if (Objects.nonNull(peispatient.getFFinallocked()) && peispatient.getFFinallocked() == 0) {
                        throw new ServiceException("该记录已经是未锁定状态!");
                    }
                    peispatient.setFFinallocked(0);// 总检锁定: 1.锁定 0.解锁 暂定
                    peispatient.setIdDoctorapply("");
                }
            }
            //更新
            updateBatchById(peispatients);
            return Boolean.TRUE;
        } else {
            throw new ServiceException("操作对象不存在或者已被删除！");
        }
    }


    /**
     * 根据体检号获取所有总检对象 暂时取第一个
     *
     * @param patientno
     * @return
     */
    @Override
    public HashMap getSectionTotal(String patientno) {
        SectionTotal sectionTotal = sectionTotalService.getdata(patientno);
        HashMap result = new HashMap();
        if (ObjectUtils.isNotEmpty(sectionTotal)) {
            String writeId = sectionTotal.getWriteId();
            String doctorId = sectionTotal.getDoctorId();
            Date writeTime = sectionTotal.getWriteTime();
            Date totalTime = sectionTotal.getTotalTime();
            result.put("writeTime", writeTime);
            result.put("totalTime", totalTime);
            if (ObjectUtils.isNotEmpty(doctorId)) {
                SysUser doctorUser = sysUserMapper.selectUserByUserNo(doctorId);
                if (ObjectUtils.isNotEmpty(doctorUser)) {
                    doctorId = doctorUser.getUserName();
                    result.put("doctorId", doctorId);
                }
            }
            if (ObjectUtils.isNotEmpty(writeId)) {
                SysUser writeUser = sysUserMapper.selectUserByUserNo(writeId);
                if (ObjectUtils.isNotEmpty(writeUser)) {
                    writeId = writeUser.getUserName();
                    result.put("writeId", writeId);
                }
            }
        }
        return result;
    }


    /**
     * 根据体检号查询出所有科室的检查结论词
     *
     * @param patientno
     * @return
     */
    @Override
    public List<HashMap> getSign(String patientno) {
        List<HashMap> list = new ArrayList<HashMap>();
        //总检主表
        SectionTotal section = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientno).eq("disease_health", 0).eq("is_delete", 0));
        if (ObjectUtils.isNotEmpty(section)) {
            //总检结论词表
            List<TotalVerdict> verdicts = totalVerdictMapper.selectList(new QueryWrapper<TotalVerdict>()
                    .eq("total_id", section.getId()));
            //循环
            if (CollectionUtil.isNotEmpty(verdicts) && verdicts.size() > 0) {
                for (TotalVerdict v : verdicts) {
                    HashMap map = new HashMap();
                    SysDept dep = null;
                    String depId = v.getDivisionId();
                    //查询部门表
                    if (StringUtils.isNotEmpty(depId) && !"null".equals(depId)) {
                        dep = sysDeptMapper.getByDeptNo(depId);
                    }
                    //标志：0不出现,1出现
                    Integer see = v.getFlag();
                    if (ObjectUtils.isEmpty(see)) {
                        see = 1;
                    }
                    map.put("see", see);
                    map.put("basconclusion", v.getBasconclusionName());
                    map.put("basconclusion_id", v.getBasconclusionId());
                    if (ObjectUtils.isNotEmpty(dep)) {
                        map.put("division_id", depId);
                        map.put("division", dep.getDeptName());
                    }
                    map.put("id", v.getId());
                    list.add(map);

                }
            }
            return list;
        }
        throw new ServiceException("无数据");
    }


    /**
     * 点击完成保存或更新
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUpFinish(FinishParam param) {
        //取出属性
        String patientno = param.getPatientno();
        String summary = param.getSummary();
        String conclusion = param.getConclusion();
        String advice = param.getAdvice();

        //体检者表总检信息
        Peispatient pp = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientno));
        if (ObjectUtils.isEmpty(pp)) {
            throw new ServiceException("体检者表无体检号:" + patientno);
        }
        Boolean bool = false;
        //总检主表
        SectionTotal sectionTotal = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientno).eq("disease_health", 0).eq("is_delete", 0));
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
        sectionTotal.setDiseaseHealth(0);
        // 综述
        sectionTotal.setSummarize(summary);
        // 结论
        sectionTotal.setVerdict(conclusion);
        // 建议
        sectionTotal.setOffer(advice);
        // 删除状态 0:不删
        sectionTotal.setIsDelete(0);
        sectionTotalService.saveOrUpdate(sectionTotal);

        //插入报告
        Report report = reportMapper.selectOne(new QueryWrapper<Report>()
                .eq("patientcode", patientno).eq("disease_health", 0));
        //没有就新建
        if (ObjectUtils.isEmpty(report)) {
            report = new Report();
            report.setDiseaseHealth(0);
        }
        report.setPatientcode(patientno);
        report.setShortCode(ToolUtil.getShortCodeByLong(patientno));
        //参数配置表
        SysConfig sysConfig = sysConfigMapper.checkConfigKeyUnique("auditPrint");
        if (ObjectUtils.isNotEmpty(sysConfig)) {
            //系统内置（Y是 N否）
            if (sysConfig.getConfigType().equals("Y")) {
                report.setStatus(2);
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
            report.setIsPrintMessage(0);
        }
        reportService.saveOrUpdate(report);

        pp.setIdDoctorfinal(SecurityUtils.getUserNo());
        pp.setDoctorfinalNameR(SecurityUtils.getUsername());
        // 总检时间
        pp.setDatefinalexamed(new Date());
        pp.setJktjzt(0);

        peispatientService.saveOrUpdate(pp);

        return Boolean.TRUE;
    }


    /**
     * 追加结论词和健康建议
     *
     * @param param
     * @return
     */
    @Override
    public HashMap appendSign(AppendSignParam param) {
        //取出属性
        String patientno = param.getPatientno();
        String flag = param.getDh();
        List<BgGriddata> mapList = param.getBgGriddata();

        SectionTotal st = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientno).eq("disease_health", Integer.parseInt(flag)).eq("is_delete", 0));

        String text = "";
        String info = "";
        if (mapList != null && mapList.size() > 0) {
            int j = 1;
            for (int i = 0; i < mapList.size(); i++) {
                BgGriddata map = mapList.get(i);
                //标志：0不出现,1出现
                if (ObjectUtils.isNotEmpty(map.getSee()) && map.getSee().equals("0")) {
                    continue;
                }
                //总检结论词
                Basconclusion bc = basconclusionMapper.getInfoById(map.getBasconclusionId());
                if (ObjectUtils.isNotEmpty(bc)) {
                    //总检建议
                    String ss = bc.getSuggestion();
                    //总检结论词表
                    TotalVerdict verdict = totalVerdictMapper.selectOne(new QueryWrapper<TotalVerdict>()
                            .eq("basconclusion_id", bc.getId()));
                    String name = "";
                    if (ObjectUtils.isNotEmpty(verdict)) {
                        //总检结论词名称
                        name = verdict.getBasconclusionName();
                    }
                    if (StringUtils.isEmpty(ss)) {
                        ss = "空";
                    }

                    if (ObjectUtils.isNotEmpty(st)) {
                        //总检结论词表
                        TotalVerdict totalVerdict = totalVerdictMapper.selectOne(new QueryWrapper<TotalVerdict>()
                                .eq("total_id", st.getId()).eq("basconclusion_id", mapList.get(i).getBasconclusionId())
                                .eq("disease_health", Integer.parseInt(flag)));
                        if (ObjectUtils.isNotEmpty(totalVerdict)) {
                            //总检建议
                            ss = totalVerdict.getTotalAdvice() == null ? "空" : totalVerdict.getTotalAdvice();
                        }
                    }
                    text += j + "、" + mapList.get(i).getDivisionId() + "\n"
                            + name + "\n\t\r";
                    info += j + "、" + name + ":\n" + ss + "\n\t\r";
                    j++;
                }
            }
        }
        HashMap map = new HashMap();
        map.put("text", text == "" ? "空" : text);
        if (flag.equals("0")) {
            map.put("info", info == "" ? "空" : info);
        } else if (flag.equals("1")) {
            map.put("jkoffer", info == "" ? "空" : info);
            //职业处理意见
            List<CommentsProgessional> commentsProgessional = commentsProgessionalMapper.selectList(
                    new QueryWrapper<CommentsProgessional>().eq("patientcode", patientno));
            String advice = "";
            if (ObjectUtils.isNotEmpty(commentsProgessional) && commentsProgessional.size() > 0) {
                int j = 1;
                for (int i = 0; i < commentsProgessional.size(); i++) {
                    //职业病处理意见
                    ZyVsSummary zyVsSummary = zyVsSummaryMapper.getInfoById(commentsProgessional.get(i).getProgessionalId());
                    //处理意见不为空
                    if (ObjectUtils.isNotEmpty(zyVsSummary) && ObjectUtils.isNotEmpty(zyVsSummary.getSummaryText())) {
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
            map.put("info", advice == "" ? "空" : advice);
        }
        return map;
    }

    /**
     * 保存追加的总检建议
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saveNameAndAdvice(CommitSignParam param) {
        //取出属性
        String patientno = param.getPatientno();
        String tjlx = param.getDh();
        String advice = param.getAdvice();
        String conclusionId = param.getConclusionId();
        String name = param.getName();
        //总检主表
        SectionTotal section = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientno).eq("disease_health", Integer.parseInt(tjlx)).eq("is_delete", 0));

        if (ObjectUtils.isNotEmpty(section)) {
            //总检结论词
            TotalVerdict ver = totalVerdictMapper.selectOne(new QueryWrapper<TotalVerdict>()
                    .eq("basconclusion_id", conclusionId).eq("total_id", section.getId()));
            if (ObjectUtils.isNotEmpty(ver)) {
                ver.setBasconclusionName(name);
                ver.setTotalAdvice(advice);
                totalVerdictMapper.updateById(ver);
                return Boolean.TRUE;
            } else {
                throw new ServiceException("请先保存结论词再进行修改");
            }
        } else {
            throw new ServiceException("无总检信息，请确认后再操作");
        }
    }

    /**
     * 根据体检号获取该体检者所有收费项目
     *
     * @param patientno
     * @return
     */
    @Override
    public List<HashMap> getItemByPeople(String patientno) {
        //体检者表
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientno));
        //收费项目
        String[] Jhys = patient.getJhys().split(",");
        List<Items> pf = itemsMapper.getProItems(Jhys, patient.getMedicaltype());

        //复查表
        Review re = reviewMapper.selectOne(new QueryWrapper<Review>().eq("patientcode", patientno));
        List<ReviewProject> rp = null;
        if (ObjectUtils.isNotEmpty(re)) {
            Review review = reviewMapper.selectOne(new QueryWrapper<Review>().eq("patientcode", patientno));
            rp = reviewProjectService.getDataByPeople(review.getId());
        }
        List<HashMap> list = new ArrayList<HashMap>();
        List li = new ArrayList();
        if (ObjectUtils.isNotEmpty(rp)) {
            for (ReviewProject p : rp) {
                //收费项目ID
                li.add(p.getItemsId());
            }
        }
        for (int i = 0; i < pf.size(); i++) {
            Items os = pf.get(i);
            String itemId = os.getId();
            String itemName = os.getExamfeeitemName();
            //不包含id的话
            if (!li.contains(itemId)) {
                HashMap map = new HashMap();
                map.put("examfeeitemName", itemName);
                map.put("id", itemId);
                map.put("reviewMatters", os.getReviewMatters());
                list.add(map);
            }

        }
        return list;
    }

    /**
     * 根据体检号获取该体检者所有收费项目 右侧
     * 职业处理意见-复查-已保存的复查收费项目
     *
     * @param patientno
     * @return
     */
    @Override
    public List<HashMap> getRightItemByPeople(String patientno) {
        //复查表
        Review review = reviewMapper.selectOne(new QueryWrapper<Review>().eq("patientcode", patientno));
        if (ObjectUtils.isEmpty(review)) {
            throw new ServiceException("error@表中无数据");
        }
        //根据复查表id获取复查收费项目
        List<ReviewProject> rp = reviewProjectService.getDataByPeople(review.getId());
        List<HashMap> list = new ArrayList<HashMap>();
        if (ObjectUtils.isNotEmpty(rp)) {
            for (ReviewProject r : rp) {
                HashMap map = new HashMap();
                map.put("examfeeitemName", r.getItemsName());
                map.put("id", r.getItemsId());
                list.add(map);
            }
        }
        return list;
    }


    /**
     * 保存更新复查通知单
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveReview(SaveReviewParam param) {
        //取出属性
        List<ReGriddata> getlist = param.getReGriddatas();
        String patientno = param.getPatientno();
        Date dateFrom = param.getDateFrom();
        Date dateTo = param.getDateTo();
        String userId = param.getUserId();
        String noticeOfProceedingText = param.getNoticeOfProceedingText();


        Peispatient p = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientno));
        //更新体检者表复查字段
        p.setFIsrecheck(1);
        this.updateById(p);

        //复查表
        Review r = reviewMapper.selectOne(new QueryWrapper<Review>().eq("patientcode", patientno));
        if (ObjectUtils.isEmpty(r)) {
            r = new Review();
            r.setPatientcode(patientno);
        }
        r.setDateFrom(dateFrom);
        r.setDateTo(dateTo);
        r.setIdOrg(p.getIdOrg());
        r.setCallbackStation(0);
        r.setUserId(userId);
        r.setNoticeOfProceedingText(noticeOfProceedingText);
        r.setIsDelete(0);
        reviewService.saveOrUpdate(r);
        //删除复查项目表
        reviewProjectService.remove(new QueryWrapper<ReviewProject>().eq("review_id", r.getId()));
        //设置属性插入
        if (CollectionUtil.isNotEmpty(getlist) & getlist.size() > 0) {
            for (int i = 0; i < getlist.size(); i++) {
                ReviewProject rp = new ReviewProject();
                rp.setItemsId(getlist.get(i).getId());
                rp.setItemsName(getlist.get(i).getExamfeeitemName());
                rp.setReviewId(r.getId());
                reviewProjectService.save(rp);
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 保存职业处理意见
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveTreatment(SaveTreatmentParam param) {
        String patientno = param.getPatientno();
        List<String> getlist = param.getIds();
        // 删除体检号对应的总检id在总检结论词表的所有数据
        commentsProgessionalMapper.delete(new QueryWrapper<CommentsProgessional>()
                .eq("patientcode", patientno));
        //保存
        if (ObjectUtils.isNotEmpty(getlist) & getlist.size() > 0) {
            for (int i = 0; i < getlist.size(); i++) {
                CommentsProgessional cp = new CommentsProgessional();
                cp.setPatientcode(patientno);
                cp.setProgessionalId(getlist.get(i));
                commentsProgessionalMapper.insert(cp);
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 加载 总检结论词表
     *
     * @param patientno
     * @param flag
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<HashMap> loadSaveSign(String patientno, String flag) {

        SectionTotal s = sectionTotalService.getOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientno).eq("disease_health", Integer.parseInt(flag)).eq("is_delete", 0));

        if (ObjectUtils.isEmpty(s)) {
            throw new ServiceException("总检表无数据!");
        }
        //查询健康的id通过体检号
        List<SectionResultTwo> Seclist = sectionResultTwoMapper.findHeId(patientno);

        List<HashMap> list = new ArrayList<HashMap>();
        if (Seclist != null && Seclist.size() > 0) {
            for (SectionResultTwo srt : Seclist) {
                //修改的地方
                if (ObjectUtils.isEmpty(srt.getBasconclusionId())) {
                    continue;
                }
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("see", "1");
                //总检结论词
                Basconclusion bas = basconclusionMapper.getInfoById(srt.getBasconclusionId());
                String depId = bas.getDivisionId();
                SysDept dep = sysDeptMapper.getByDeptNo(depId);
                String basconclusion = "";
                String depName = "";
                if (ObjectUtils.isNotEmpty(bas)) {
                    basconclusion = bas.getName();
                }
                if (ObjectUtils.isNotEmpty(dep)) {
                    depName = dep.getDeptName();
                }

                map.put("basconclusion", basconclusion);
                map.put("basconclusion_id", srt.getBasconclusionId());

                if (ObjectUtils.isNotEmpty(dep)) {
                    map.put("division_id", depId);
                    map.put("division", depName);
                }
                TotalVerdict verd = new TotalVerdict();
                verd.setBasconclusionId(srt.getBasconclusionId());
                verd.setTotalId(s.getId());
                verd.setDivisionId(depId);
                verd.setIsDelete(0);
                verd.setDiseaseHealth(Integer.parseInt(flag));
                verd.setTotalAdvice(bas.getSuggestion());
                verd.setFlag(1);
                verd.setBasconclusionName(basconclusion);
                //保存
                totalVerdictMapper.insert(verd);
                String id = verd.getId();
                map.put("id", id);
                list.add(map);
            }
        }
        return list;
    }


    /**
     * 读卡
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public HashMap<String, Object> search(String patientCode, String ksId) {
        //体检者表
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode).eq("f_registered", 1));
        if (ObjectUtils.isEmpty(patient)) {
            throw new ServiceException("error@该体检号尚未登记！");
        }
        if (StringUtils.isEmpty(ksId)) {
            SysDept dept = sysDeptMapper.selectDeptByName("检验科");
            ksId = dept.getDeptNo();
        }
        //收费项目表
        Long i = peispatientfeeitemMapper.selectCount(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode).eq("id_ks", ksId)
                .eq("sfjj", 0).eq("f_giveup", 0).eq("change_item", 0)
                .isNull("f_transferedhl7")
        );
        if (i == 0) {
            throw new ServiceException("error@体检号" + patientCode + "没有本科室收费项目！");
        }
        //科室检查结果主表
        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id", ksId).eq("patientcode", patientCode)
        );
        HashMap<String, Object> data = new HashMap<String, Object>();
        if (ObjectUtils.isNotEmpty(main)) {//审核过
            HashMap<String, String> main_map = new HashMap<String, String>();
            main_map.put("id", main.getId());
            main_map.put("conclusions", main.getConclusions());
            main_map.put("isAudit", main.getIsAudit() == null ? "0" : main.getIsAudit().toString());
            data.put("main", main_map);
        }
        data.put("patient", patient);
        data.put("picture", getPicture(patient));
        data.put("isVIP", getIdPatientClass(patient));
        return data;
    }


    /**
     * 获取相片
     *
     * @param patient
     * @return
     */
    public String getPicture(Peispatient patient) {
        if (ObjectUtils.isEmpty(patient)) {
            return "";
        }
        PeispatientPhoto photo = peispatientPhotoMapper.selectOne(new QueryWrapper<PeispatientPhoto>()
                .eq("patientcode", patient.getPatientcode()));
        return photo == null || photo.getPicture() == null ? "" : photo.getPicture();
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
            //体检者类型表
            Patienttype pt = patienttypeMapper.selectOne(new QueryWrapper<Patienttype>()
                    .eq("id", patient.getIdPatientclass()));
            if (ObjectUtils.isNotEmpty(pt)) {
                result = ObjectUtils.isEmpty(pt.getPatientName()) ? "" : pt.getPatientName();
            }
        }
        return result;
    }


    /**
     * 获取分科检验数据
     *
     * @param page
     * @param patientCode
     * @param ksId
     * @return
     */
    @Override
    public IPage<GetGriddataVo> getGriddata(PageParam<GetGriddataVo> page, String patientCode, String ksId) {
        //不传科室id就用检验科的id
        if (StringUtils.isEmpty(ksId)) {
            SysDept dept = sysDeptMapper.selectDeptByName("检验科");
            ksId = dept.getDeptNo();
        }
        IPage<GetGriddataVo> iPage = totalHealthInspectionMapper.getGriddata(page, patientCode, ksId);
        return iPage;
    }


    /**
     * 获取提醒接口
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<RemindPatientVo> getRemindPatient(PageParam<RemindPatientVo> page, RemindPatientParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        IPage<RemindPatientVo> remindPatient = totalHealthInspectionMapper.getRemindPatient(page, param);
        List<RemindPatientVo> records = remindPatient.getRecords();
        for (RemindPatientVo record : records) {
            record.setContent(getContent(record.getPatientcode(), param.getKsID()));
        }
        remindPatient.setRecords(records);
        return remindPatient;
    }


    /**
     * 获取内容
     *
     * @param inputCode
     * @param ksID
     * @return
     */
    public String getContent(String inputCode, String ksID) {
        //添加条件
        QueryWrapper<SectionAndRemind> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patientcode", inputCode);
        if (StringUtils.isNotEmpty(ksID)) {
            queryWrapper.eq("dep_id", ksID);
        }
        //科室提醒和科室关联表中间表
        List<SectionAndRemind> sars = sectionAndRemindMapper.selectList(queryWrapper.orderByDesc("createdate"));
        if (sars.size() > 0) {
            StringBuilder builder = new StringBuilder();
            Set<String> mainIds = new HashSet<String>();
            for (SectionAndRemind sar : sars) {
                //id重复就跳过
                if (mainIds.contains(sar.getRemindId())) {
                    continue;
                } else {
                    mainIds.add(sar.getRemindId());
                }
                // 科室提醒主表
                SectionRemind sr = sectionRemindMapper.getInfoById(sar.getRemindId());
                builder.append(sr.getDepName() + ":");
                builder.append("\n\t");
                builder.append(sr.getRemindContent().replaceAll("\n", "\n\t"));
                builder.append("\n");
            }
            return builder.toString();
        }
        return "";
    }





    public StringBuilder[] getConclusions(String tjlx, String patientcode, String deptNo, String jhys, String medicaltype) {
        HashMap<String, Comboexamitem> ceis = null;
        if ("2".equals(tjlx)) {//按接害因素、职业体检类型匹配
            List<Comboexamitem> eis = comboexamitemMapper.selectList(
                    new QueryWrapper<Comboexamitem>()
                            .eq("medical_type", medicaltype)
                            .in("harm_id", jhys.split(","))
            );
            ceis = new HashMap<String, Comboexamitem>();
            for (Comboexamitem cei : eis) {
                ceis.put(cei.getItemId(), cei);
            }
        }
        StringBuilder conclusions = new StringBuilder();
        StringBuilder zyConclusions = null;
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(
                new QueryWrapper<Peispatientfeeitem>()
                        .eq("id_patient", patientcode)
                        .eq("id_ks", deptNo)
                        .eq("f_examinated", 1)
                        .eq("change_item",0)
        );
        List<PacsResult> prs = new ArrayList<PacsResult>();//所有已检的
        for (Peispatientfeeitem feeitem : feeitems) {
            PacsResult pr = pacsResultMapper.selectOne(
                    new QueryWrapper<PacsResult>()
                            .eq("patientcode", patientcode)
                            .eq("item_id", feeitem.getIdExamfeeitem())
            );
            if (pr != null) {
                prs.add(pr);
            }
        }
        String rn = "\n";//换行符
        for (PacsResult pr : prs) {
            if (pr.getExamresultdesc() != null) {
                conclusions.append("[" + pr.getItemName() + "]所见：" + rn);
                conclusions.append(pr.getExamresultdesc() + rn);
            }
            if (pr.getExamresultsummary() != null) {
                conclusions.append("[" + pr.getItemName() + "]提示：" + rn);
                conclusions.append(pr.getExamresultsummary() + rn);
            }
        }
        if (ceis != null) {
            zyConclusions = new StringBuilder();
            for (PacsResult pr : prs) {
                Items tjitem = itemsMapper.selectById(pr.getItemId());
                if (ceis.get(tjitem.getId()) != null) {
                    if (pr.getExamresultdesc() != null) {
                        zyConclusions.append("[" + pr.getItemName() + "]所见：" + rn);
                        zyConclusions.append(pr.getExamresultdesc() + rn);
                    }
                    if (pr.getExamresultsummary() != null) {
                        zyConclusions.append("[" + pr.getItemName() + "]提示：" + rn);
                        zyConclusions.append(pr.getExamresultsummary() + rn);
                    }
                }
            }
        }
        return new StringBuilder[]{conclusions, zyConclusions};
    }
}

