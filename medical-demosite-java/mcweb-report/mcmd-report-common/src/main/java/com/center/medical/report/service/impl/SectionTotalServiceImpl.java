package com.center.medical.report.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.ExamType;
import com.center.medical.data.bean.model.*;
import com.center.medical.abteilung.bean.dto.SrtPcDto;
import com.center.medical.abteilung.bean.model.ElectroAudiometer;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.model.SectionResultTwo;
import com.center.medical.abteilung.dao.ElectroAudiometerMapper;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.abteilung.service.SectionResultTwoService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.Render;
import com.center.medical.common.utils.StringUtil;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientarchiveMapper;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.data.dao.OccupationDiseastMapper;
import com.center.medical.data.dao.ZySummaryMapper;
import com.center.medical.data.service.BasconclusionService;
import com.center.medical.data.service.ZyVsSummaryService;
import com.center.medical.report.bean.model.CommentsProgessional;
import com.center.medical.report.bean.model.SectionTotal;
import com.center.medical.report.bean.model.TotalVerdict;
import com.center.medical.report.bean.vo.QjOsVo;
import com.center.medical.report.bean.vo.STHistoryVo;
import com.center.medical.report.dao.SectionTotalMapper;
import com.center.medical.report.dao.TotalVerdictMapper;
import com.center.medical.report.service.CommentsProgessionalService;
import com.center.medical.report.service.PatientfeeitemRService;
import com.center.medical.report.service.SectionTotalService;
import com.center.medical.report.service.TotalVerdictService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ZJ总检主表(SectionTotal)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-08 17:32:57
 */
@Slf4j
@Service("sectionTotalService")
@RequiredArgsConstructor
public class SectionTotalServiceImpl extends ServiceImpl<SectionTotalMapper, SectionTotal> implements SectionTotalService {

    private final SectionTotalMapper sectionTotalMapper;
    private final SectionResultTwoService sectionResultTwoService;
    private final BasconclusionService basconclusionService;
    private final TotalVerdictService totalVerdictService;
    private final PatientfeeitemRService patientfeeitemRService;
    private final ZyVsSummaryService zyVsSummaryService;
    private final CommentsProgessionalService commentsProgessionalService;
    private final ZySummaryMapper zySummaryMapper;
    private final SectionResultMainService sectionResultMainService;
    private final TotalVerdictMapper totalVerdictMapper;
    private final ISysDeptService iSysDeptService;
    private final PeispatientMapper peispatientMapper;
    private final ElectroAudiometerMapper electroAudiometerMapper;
    private final HarmMapper harmMapper;
    private final OccupationDiseastMapper occupationDiseastMapper;
    private final static String BC_SUMMARY_ID = "7";

    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final ISysConfigService iSysConfigService;

    @Autowired
    private LoadProperties loadProperties;


    /**
     * 分页查询[ZJ总检主表]列表
     *
     * @param page  分页参数
     * @param param SectionTotal查询参数
     * @return 分页数据
     */
    @Override

    public IPage<SectionTotal> getPage(PageParam<SectionTotal> page, SectionTotal param) {
        return sectionTotalMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SectionTotal getInfoById(String id) {
        return sectionTotalMapper.getInfoById(id);
    }

    /**
     * 健康+职业-新增总检主表、子表
     *
     * @param patient 体检者信息
     * @param dh      体检类型：0.健康体检 1.职业体检 2.综合 3.复查
     * @return
     * @see ExamType
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SectionTotal createNew(Peispatient patient, int dh) {
        String patientCode = patient.getPatientcode();
        //主表
        SectionTotal sectionTotal = new SectionTotal();
        sectionTotal.setPatientcode(patientCode);// 体检号
        sectionTotal.setDiseaseHealth(dh);// 健康
        sectionTotal.setIsDelete(0);// 删除状态 0:不删
        sectionTotal.setScbs(0);//未上传
        //插入
        sectionTotalMapper.insert(sectionTotal);

        //生成子表
        ArrayList<TotalVerdict> verdictList = new ArrayList<>();
        List<SrtPcDto> srtList = sectionResultTwoService.getListByPatientCode(patient.getPatientcode(), dh);
        int i = 0;
        for (SrtPcDto srtPcDto : srtList) {
            Basconclusion bas = basconclusionService.getInfoById(srtPcDto.getBasconclusionId());
            if (Objects.isNull(bas)) {
                continue;
            }
            TotalVerdict verd = new TotalVerdict();
            verd.setBasconclusionId(srtPcDto.getBasconclusionId());
            verd.setTotalId(sectionTotal.getId());
            verd.setDivisionId(bas.getDivisionId());
            verd.setIsDelete(0);
            verd.setDiseaseHealth(dh);
            verd.setTotalAdvice(bas.getSuggestion());
            verd.setFlag(1);
            verd.setBasconclusionName(bas.getName());
            verd.setVerdictSort(i++);
            verdictList.add(verd);
        }
        //插入子表
        totalVerdictService.saveBatch(verdictList);

        //首次进入职业总检时，系统判断是否存在职业必查项目弃检，如果存在，直接插入对应的补查职业处理意见。
        //职业报告结论也选择补查
        if (dh == 1) {
            List<Peispatientfeeitem> feeitems = patientfeeitemRService.list(new LambdaQueryWrapper<Peispatientfeeitem>()
                    .eq(Peispatientfeeitem::getIdPatient, patientCode).eq(Peispatientfeeitem::getFGiveup, 1));
            if (CollectionUtil.isNotEmpty(feeitems)) {
                List<String> odList = Arrays.stream(patient.getJhys().split(",")).collect(Collectors.toList());
                List<String> itemIds = feeitems.stream().map(Peispatientfeeitem::getIdExamfeeitem).collect(Collectors.toList());

                List<String> sumaryIds = zyVsSummaryService.getIdList(patient.getMedicaltype(), odList, itemIds);
                ArrayList<CommentsProgessional> cpList = new ArrayList<>();
                for (String sumaryId : sumaryIds) {
                    CommentsProgessional cp = new CommentsProgessional();
                    cp.setPatientcode(patientCode);
                    cp.setProgessionalId(sumaryId);
                    cpList.add(cp);
                }
                //插入
                commentsProgessionalService.saveBatch(cpList);
                sectionTotal.setSummaryId(BC_SUMMARY_ID);
                ZySummary zs = zySummaryMapper.getInfoById(BC_SUMMARY_ID);
                StringBuilder sb = new StringBuilder(zs.getOccupationSummary());
                sb.append("\n");
                sb.append(zs.getOccupationSummaryExplain());
                sectionTotal.setReportConclusions(sb.toString());
            }
        }

        //获取综述、结论、建议
        Map<String, String> map = generateInfo(sectionTotal, dh);
        sectionTotal.setSummarize(map.get("summarize"));// 综述
        sectionTotal.setVerdict(map.get("verdict"));// 结论
        sectionTotal.setOffer(map.get("offer"));// 建议
        if (dh == 1) {
            sectionTotal.setPosistive(map.get("posistive"));//职业阳性结果
            sectionTotal.setJkoffer(map.get("jkoffer"));
            sectionTotal.setReportConclusions(map.get("reportConclusions"));
        }

        sectionTotalMapper.updateById(sectionTotal);
        return sectionTotal;
    }

    /**
     * 健康总检-生成总检综述、结论、健康建议
     *
     * @param sectionTotal 总检记录
     * @param dh           体检类型：0.健康体检 1.职业体检
     * @return
     */
    @Override
    public Map<String, String> generateInfo(SectionTotal sectionTotal, int dh) {
        Map<String, String> data = new HashMap<String, String>();
        String patientCode = sectionTotal.getPatientcode();

        if (dh == 0) {
            //健康体检
            String summary = sectionResultMainService.getHealthSummarize(patientCode);
            data.put("summarize", StringUtils.isBlank(summary) ? "空" : summary.toString());
            //结论
            StringBuilder verdict = new StringBuilder();
            //建议
            StringBuilder offer = new StringBuilder();

            List<TotalVerdict> verdicts = totalVerdictMapper.selectList(new LambdaQueryWrapper<TotalVerdict>()
                    .eq(TotalVerdict::getTotalId, sectionTotal.getId()).eq(TotalVerdict::getFlag, 1)
                    .orderByAsc(TotalVerdict::getVerdictSort));
            int m = 1, n = 1;
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
                if (!conclusion_name.equals("本次体检未见异常")) {
                    SysDept dept = iSysDeptService.getByDeptNo(ver.getDivisionId());
                    verdict.append(m + "、" + (Objects.isNull(dept) ? "" : dept.getDeptName()) + ":" + conclusion_name + Constants.BR);
                    m++;
                }
                String totalAdvice = ver.getTotalAdvice();
                if (totalAdvice != null) {
                    offer.append(n + "、" + conclusion_name + "\n    " + totalAdvice + Constants.BR);
                    n++;
                }
                mergeIds.add(mergeId);
            }

            data.put("verdict", StringUtils.isBlank(verdict) ? "空" : verdict.toString());
            data.put("offer", StringUtils.isBlank(offer) ? "空" : offer.toString());
        } else {
            //职业体检
            //获取体检者信息
            Peispatient patient = peispatientMapper.selectOne(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, patientCode));
            List<String> harmIds = Arrays.asList(patient.getJhys().split(","));
            String medicalType = patient.getMedicaltype();
            //有已检职业项目的科室
            List<SectionResultMain> sectionResultMains = patientfeeitemRService.getExamedList(patientCode, harmIds, medicalType);
            //有弃检职业项目的科室
            List<QjOsVo> qjOsList = patientfeeitemRService.getGiveUpList(patientCode, harmIds, medicalType);
            //所有职业拒检项目，展现在综述和职业阳性结果
            List<String> jjlist = patientfeeitemRService.getRejectList(patientCode, harmIds, medicalType);
            String jjxms = (CollectionUtil.isNotEmpty(jjlist) && StringUtils.isNotEmpty(jjlist.get(0)))
                    ? ("拒检项目：" + Render.getClob(jjlist.get(0)) + "。")
                    : "";
            //合并排序
            List<Map<String, Object>> zyOs = new ArrayList<Map<String, Object>>();
            Map<String, Map<String, Object>> zyMa = new HashMap<String, Map<String, Object>>();
            for (SectionResultMain srm : sectionResultMains) {
                Map<String, Object> ma = new HashMap<String, Object>();
                ma.put("srm", srm);
                SysDept dept = iSysDeptService.getByDeptNo(srm.getDepId());
                ma.put("ks", dept);
                zyOs.add(ma);
                zyMa.put(srm.getDepId(), ma);
            }
            for (int i = 0; i < qjOsList.size(); i++) {
                QjOsVo qjOs = qjOsList.get(i);
                String ksId = qjOs.getId();
                Map<String, Object> ma = zyMa.get(ksId);
                if (Objects.isNull(ma)) {
                    ma = new HashMap<String, Object>();
                    SysDept dept = iSysDeptService.getByDeptNo(ksId);
                    ma.put("ks", dept);
                    ma.put("items", Render.getClob(qjOs.getFeeitemNames()));
                    zyOs.add(ma);
                    zyMa.put(ksId, ma);
                } else {
                    ma.put("items", Render.getClob(qjOs.getFeeitemNames()));
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
                            summary.append(Constants.BR + "弃检项目：" + items + Constants.BR);
                        } else {
                            summary.append(Constants.BR);
                        }
                    } else if (StringUtils.isNotEmpty(items)) {
                        summary.append(j + "、" + depname
                                + Constants.BR + "弃检项目："
                                + items + Constants.BR);
                        j++;
                    }
                } else if (StringUtils.isNotEmpty(items)) {
                    summary.append(j + "、" + depname
                            + Constants.BR + "弃检项目："
                            + items + Constants.BR);
                    j++;
                }

                if (main != null) {
                    String con = main.getZyConclusions();
                    if (StringUtils.isNotEmpty(con) && dept != null) {
                        if (dept.getSjbggs() != null && dept.getSjbggs().intValue() == 3) {
                            //一般检查
                            long xy = sectionResultTwoService.count(new LambdaQueryWrapper<SectionResultTwo>()
                                    .eq(SectionResultTwo::getPatientcode, patientCode).eq(SectionResultTwo::getVerdictId, "436"));
                            long sg = sectionResultTwoService.count(new LambdaQueryWrapper<SectionResultTwo>()
                                    .eq(SectionResultTwo::getPatientcode, patientCode).eq(SectionResultTwo::getVerdictId, "14"));
                            if (xy == 0) {
                                con = con.replaceAll("收缩压:[^;]*;", "");
                                con = con.replaceAll("舒张压:[^;]*;", "");
                                con = con.replaceAll("血压结论:[^;]*;", "");
                            }
                            if (sg == 0) {
                                con = con.replaceAll("身高:[^;]*;", "");
                                con = con.replaceAll("体重:[^;]*;", "");
                                con = con.replaceAll("体重指数:[^;]*;", "");
                            }
                            //职业的最多有个体重，显示体重指数，跟肥胖啥的
                            //奥，呼吸频率和营养状况是特殊危害因素做的，一般也不会做。最常见的是只测血压
                            con = con.replaceAll("脉搏:[^;]*;", "");
                            con = con.replaceAll("呼吸频率:[^;]*;", "");
                            con = con.replaceAll("营养状况:[^;]*;", "");
                            if (con.trim().length() == 0) {
                                continue;
                            }
                        } else if (con.indexOf("未见") != -1 && con.indexOf("异常") != -1) {
                            continue;
                        }
                        //有阳性体证词的或者是电测听科室(>=30) PACS科室(CR DR 彩超 CT) 小结进阳性结果
                        if (dept.getSjbggs() != null && (dept.getSjbggs().intValue() == 9)) {
                            ElectroAudiometer ea = electroAudiometerMapper.selectOne(new LambdaQueryWrapper<ElectroAudiometer>()
                                    .eq(ElectroAudiometer::getPatientcode, patientCode));
                            if (ea != null) {
                                boolean in = false;
                                //噪声
                                if (harmIds.contains("127")) {
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
                                        if (StringUtils.isNotEmpty(testResult)) {
                                            int start = testResult.indexOf("双耳高频听阈均值");
                                            int end = testResult.indexOf("dB");
                                            if (start != -1 && end != -1) {
                                                if (new BigDecimal(testResult.substring(start + 8, end)).compareTo(new BigDecimal(30)) >= 0) {
                                                    in = true;
                                                }
                                            }
                                        }
                                    }
                                }
                                //压力容器作业
                                if (!in && harmIds.contains("155")) {
                                    String testResult = ea.getTestResult();
                                    if (StringUtils.isNotEmpty(testResult)) {
                                        int start = testResult.indexOf("双耳语频听阈均值");
                                        int end = testResult.indexOf("dB");
                                        if (start != -1 && end != -1) {
                                            if (new BigDecimal(testResult.substring(start + 8, end)).compareTo(new BigDecimal(25)) > 0) {
                                                in = true;
                                            }
                                        }
                                    }

                                }
                                //职业机动车驾驶作业
                                if (!in && harmIds.contains("158")) {
                                    String testResult = ea.getTestResult();
                                    if (StringUtils.isNotEmpty(testResult)) {
                                        int start = testResult.indexOf("双耳语频听阈均值");
                                        int end = testResult.indexOf("dB");
                                        if (start != -1 && end != -1) {
                                            if (new BigDecimal(testResult.substring(start + 8, end)).compareTo(new BigDecimal(30)) > 0) {
                                                in = true;
                                            }
                                        }
                                    }
                                }

                                if (in) {
                                    posistive.append(k + "、" + depname + "\n" + con + Constants.BR);
                                    k++;
                                }
                            }

                        } else if ("143".equals(dept.getDeptNo())
                                || "171".equals(dept.getDeptNo())
                                || "24".equals(dept.getDeptNo())
                                || "173".equals(dept.getDeptNo())
                                || sectionResultTwoService.count(new LambdaQueryWrapper<SectionResultTwo>()
                                .eq(SectionResultTwo::getPosistive, 1).ne(SectionResultTwo::getTjlx, 0).eq(SectionResultTwo::getMainId, main.getId())
                                .or().isNull(SectionResultTwo::getVerdictId).notIn(SectionResultTwo::getVerdictId, Arrays.asList("388", "461"))) > 0) {
                            posistive.append(k + "、" + depname + "\n" + con + Constants.BR);
                            k++;
                        }
                    }
                }
            }

            if (StringUtils.isNotEmpty(jjxms)) {
                if (summary.length() > 0) {
                    summary.append(Constants.BR);
                }
                summary.append(jjxms);
                if (posistive.length() > 0) {
                    posistive.append(Constants.BR);
                }
                posistive.append(jjxms);
            }
            data.put("summarize", summary.length() == 0 ? "空" : summary.toString());
            data.put("posistive", posistive.length() == 0 ? "无异常" : posistive.toString());

            List<TotalVerdict> verdicts = totalVerdictService.list(new LambdaQueryWrapper<TotalVerdict>()
                    .eq(TotalVerdict::getTotalId, sectionTotal.getId()).eq(TotalVerdict::getFlag, 1)
                    .orderByAsc(TotalVerdict::getVerdictSort));
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
                if (StringUtils.isNotEmpty(mergeId)) {
                    if (mergeIds.contains(mergeId)) {
                        continue;
                    }
                    conclusion_name = ver.getMergeName();
                } else {
                    conclusion_name = ver.getBasconclusionName();
                }
                if (!conclusion_name.equals("本次体检未见异常")
                        && !conclusion_name.equals("未见异常")) {
                    SysDept dept = iSysDeptService.getByDeptNo(ver.getDivisionId());
                    verdict.append(j + "、" + (dept == null ? "" : dept.getDeptName()) + ":" + conclusion_name + Constants.BR);
                    j++;
                }
                String totalAdvice = ver.getTotalAdvice();
                if (totalAdvice != null) {
                    jkoffer.append(k + "、" + conclusion_name + "\n" + totalAdvice + Constants.BR);
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
     * 获取总检历史记录
     *
     * @param smId             总检id
     * @param dh               体检类型：0.健康 1.职业
     * @param patientarchiveno 档案id
     * @return
     */
    @Override
    public List<STHistoryVo> getHistory(String smId, int dh, String patientarchiveno) {
        return sectionTotalMapper.getHistory(smId, dh, patientarchiveno);
    }

    /**
     * 生成职业处理意见。新增一条时，在diseaseTotalService.saveTreatment方法中生成
     * <p>
     * 所有职业处理意见 、职业报告结论都在这里生成
     */
    public String[] generateCom(String patientCode) {
        List<CommentsProgessional> commentsProgessionals = commentsProgessionalService.list(new LambdaQueryWrapper<CommentsProgessional>()
                .eq(CommentsProgessional::getPatientcode, patientCode));
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
            ZyVsSummary zyVsSummary = zyVsSummaryService.getById(com.getProgessionalId());
            if (zyVsSummary != null && zyVsSummary.getSummaryText() != null) {
                Harm harm = harmMapper.selectById(zyVsSummary.getOccupationDiagnosis());
                ZySummary sum = zySummaryMapper.selectById(zyVsSummary.getOccupationSummary());
                if (harm != null && sum != null) {
                    offer.append(j + "、" + harm.getHarmName() + "：" + sum.getOccupationSummary() + "\n");
                    Integer serialNo = sum.getSerialNo();
                    if (serialNo != null) {
                        if (serialNo == 2 || serialNo == 3) {//复查或职业禁忌症 显示 : 禁忌症,
                            if (zyVsSummary.getDiagnosis() != null && !"无".equals(zyVsSummary.getDiagnosis().trim())) {
                                offer.append(zyVsSummary.getDiagnosis() + "，");
                            }
                        } else if (serialNo == 1) {//疑似职业病  显示 职业病，
                            if (zyVsSummary.getOccupationDiseast() != null) {
                                OccupationDiseast o = occupationDiseastMapper.selectById(zyVsSummary.getOccupationDiseast());
                                if (Objects.nonNull(o) && o.getOccupationDiseast() != null && !"无".equals(o.getOccupationDiseast().trim())) {
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
                        if (zyVsSummary.getAlwaysInReport() != null
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
                    offer.append(zyVsSummary.getSummaryText() + Constants.BR);
                    j++;
                }
            }
        }

        //生成职业报告结论词
        if (hignestCom != null) {
            boolean onlyOne = false;//仅一条不生成序号
            if (buchaCom.size() > 1 || extraCom.size() > 1) {
                onlyOne = false;
            } else if (buchaCom.size() == 0 && extraCom.size() == 0) {
                onlyOne = true;
            } else {
                if (buchaCom.size() == 1
                        && !buchaCom.get(0).getId().equals(hignestCom.getId())) {
                    onlyOne = false;
                } else if (extraCom.size() == 1
                        && !extraCom.get(0).getId().equals(hignestCom.getId())) {
                    onlyOne = false;
                } else {
                    onlyOne = true;
                }
            }
            int index = 1;//序号
            ZyVsSummary zyVsSummary = zyVsSummaryService.getById(hignestCom.getProgessionalId());
            ZySummary sum = zySummaryMapper.selectById(zyVsSummary.getOccupationSummary());
            boolean bc = false;//最高级别为补查
            Set<String> comIds = new HashSet<String>();//去重(不同因素处理意见可能相同，这里用处理意见去重)
            String summaryText = StringUtil.addMark(zyVsSummary.getSummaryText());
            if (sn == 2 || sn == 3) {//复查或职业禁忌症 显示 : 禁忌症,
                reportConclusions.append(sum.getOccupationSummary() + ":" + "\n" + (onlyOne ? "" : ((index++) + "、")));
                if (zyVsSummary.getDiagnosis() != null && !"无".equals(zyVsSummary.getDiagnosis().trim())) {
                    reportConclusions.append(zyVsSummary.getDiagnosis() + "，");
                }
                reportConclusions.append(summaryText + "\n");
                comIds.add(summaryText);
            } else if (sn == 1) {//疑似职业病  显示 职业病，
                reportConclusions.append(sum.getOccupationSummary() + ":" + "\n" + (onlyOne ? "" : ((index++) + "、")));
                if (zyVsSummary.getOccupationDiseast() != null) {
                    OccupationDiseast o = occupationDiseastMapper.selectById(zyVsSummary.getOccupationDiseast());
                    if (o != null && o.getOccupationDiseast() != null && !"无".equals(o.getOccupationDiseast().trim())) {
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
                ZyVsSummary zyVsSummaryi = zyVsSummaryService.getById(c.getProgessionalId());
                String summaryTexti = StringUtil.addMark(zyVsSummaryi.getSummaryText());
                if (i == 0 && bc) {
                    ZySummary sumi = zySummaryMapper.selectById(zyVsSummary.getOccupationSummary());
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
     * 获取历史数据
     *
     * @param page
     * @param smId
     * @param dh
     * @return
     */
    @Override
    public IPage<STHistoryVo> getHistoryData(PageParam<STHistoryVo> page, String smId, String dh) {
        //查询mysql数据
        SectionTotal st = sectionTotalMapper.getInfoById(smId);
        Peispatient p = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", st.getPatientcode()));
        //开启老系统数据查询，并且是线下
        if (iSysConfigService.oldSystemOpen() && !ZhongkangConfig.isOnline()){
            //查询mysql历史数据
            List<STHistoryVo> mysqlVo = sectionTotalMapper.getMysqlHistoryData(smId, dh, p.getPatientarchiveno());
            //查询oracle历史数据
            List<STHistoryVo> oracleVo = sectionTotalMapper.getOracleHistoryData(smId, dh, p.getIdcardno());
            mysqlVo.addAll(oracleVo);
            //oracleHis历史数据,PEISPATIENT_HIS 这个表只有东区有
            if (StringUtils.equals(loadProperties.name, "admin")){
                List<STHistoryVo> oracleHisVo = sectionTotalMapper.getOracleHisHistoryData(smId, dh, p.getIdcardno());
                mysqlVo.addAll(oracleHisVo);
            }
            //排序
            Comparator<STHistoryVo> comparator = new Comparator<STHistoryVo>() {
                @Override
                public int compare(STHistoryVo obj1, STHistoryVo obj2) {
                    // 处理空值情况
                    if (obj1.getTotalTime() == null && obj2.getTotalTime() == null) {
                        return 0;
                    } else if (obj1.getTotalTime() == null) {
                        return 1;
                    } else if (obj2.getTotalTime() == null) {
                        return -1;
                    }
                    // 默认比较规则
                    return obj2.getTotalTime().compareTo(obj1.getTotalTime());
                }
            };
            Collections.sort(mysqlVo, comparator);
            //设置分页返回数据
            IPage<STHistoryVo> ipage = ToolUtil.getPages((int) page.getCurrent(),(int) page.getSize(), mysqlVo);
            return ipage;
        }else {
            //锦都没有历史数据
            IPage<STHistoryVo> historyData = sectionTotalMapper.getHistoryData(page, smId, dh, p.getPatientarchiveno());
            return historyData;
        }
    }


    /**
     * 根据体检号获取所有总检对象 暂时取第一个
     *
     * @param patientno
     * @return
     */
    @Override
    public SectionTotal getdata(String patientno) {
        QueryWrapper<SectionTotal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patientcode", patientno);
        List<SectionTotal> sectionTotals = sectionTotalMapper.selectList(queryWrapper);
        SectionTotal sectionTotal = null;
        if (sectionTotals != null & sectionTotals.size() > 0) {
            sectionTotal = sectionTotals.get(0);
        }
        return sectionTotal;
    }

    /**
     * 客户授权查看影像报告
     * @param hospitalOrderId
     * @return
     */
    @Override
    public String getOffer(String hospitalOrderId) {
        Peispatient patient = peispatientMapper.getByPatientCode(hospitalOrderId);
        if(patient==null){
            throw new ServiceException("404");
        }
        if(patient.getCountreportoccupation()==null||patient.getCountreportoccupation()!=1){
            throw new ServiceException("401");
        }
        if(patient.getJktjzt()==null||patient.getJktjzt()<9){
            throw new ServiceException("405");
        }
        SectionTotal total = sectionTotalMapper.selectOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode",patient.getPatientcode())
                .eq("disease_health",0)
        );
        if(total==null){
            throw new ServiceException("406");
        }
        return total.getOffer();
    }
}

