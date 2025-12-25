package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.*;
import com.center.medical.bean.model.*;
import com.center.medical.bean.enums.MarriageType;
import com.center.medical.bean.vo.CheckanalyzeVo;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.FxDetectionMapper;
import com.center.medical.dao.ReportContentMapper;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.ZyHarmClass;
import com.center.medical.data.service.BaseAttachmentConfigService;
import com.center.medical.data.service.HarmService;
import com.center.medical.data.service.ZyHarmClassService;
import com.center.medical.olddata.service.OrPeispatientService;
import com.center.medical.report.bean.dto.*;
import com.center.medical.report.bean.model.BallCheckReport;
import com.center.medical.report.bean.model.SamplePerson;
import com.center.medical.report.bean.param.GHSaveDataParam;
import com.center.medical.report.bean.param.GroupHealthParam;
import com.center.medical.report.bean.param.ImgJkParam;
import com.center.medical.report.bean.param.ReportAuditParam;
import com.center.medical.report.bean.vo.BallCheckReportVo;
import com.center.medical.report.bean.vo.GHExportDataVo;
import com.center.medical.report.bean.vo.RAExportDataVo;
import com.center.medical.report.dao.BallCheckReportMapper;
import com.center.medical.report.dao.ReportPrintingMapper;
import com.center.medical.report.service.BallCheckReportService;
import com.center.medical.report.service.SamplePersonService;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.service.*;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TJ团检报告主表(BallCheckReport)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:29
 */
@Slf4j
@Service("ballCheckReportService")
@RequiredArgsConstructor
public class BallCheckReportServiceImpl extends ServiceImpl<BallCheckReportMapper, BallCheckReport> implements BallCheckReportService {

    private final BallCheckReportMapper ballCheckReportMapper;
    private final CreateorderService createorderService;
    private final SellcustomerService sellcustomerService;
    private final SamplePersonService samplePersonService;
    private final ReportPrintingMapper reportPrintingMapper;
    private final BaseAttachmentConfigService baseAttachmentConfigService;
    private final FxCompletionService fxCompletionService;
    private final DanagerObjectService danagerObjectService;
    private final FxPersonnelviewService fxPersonnelviewService;
    private final FxReviewsituationService fxReviewsituationService;
    private final FxItemscheckService fxItemscheckService;
    private final FxDetectionService fxDetectionService;
    private final FxPositiveService fxPositiveService;
    private final FxNegativeService fxNegativeService;
    private final FxDetectionzyService fxDetectionzyService;
    private final FxSummaryService fxSummaryService;
    private final FxReviewInfoService fxReviewInfoService;
    private final FxHarmService fxHarmService;
    private final HarmService harmService;
    private final ZyHarmClassService zyHarmClassService;
    private final FxDetectionMapper fxDetectionMapper;
    private final ReportContentMapper reportContentMapper;
    private final OrPeispatientService orPeispatientService;
    private final SysUserMapper sysUserMapper;

    /**
     * 分页查询[TJ团检报告主表]列表
     *
     * @param page  分页参数
     * @param param BallCheckReport查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BallCheckReport> getList(PageParam<BallCheckReport> page, BallCheckReport param) {
        return ballCheckReportMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BallCheckReport getInfoById(String id) {
        return ballCheckReportMapper.getInfoById(id);
    }

    /**
     * 分页查询团检报告主表
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @Override
    public IPage<BallCheckReport> loadBallCheckData(PageParam<BallCheckReport> page, GroupHealthParam param) {
        return ballCheckReportMapper.loadBallCheckData(page, param);
    }

    /**
     * 左侧新增数据
     *
     * @param ballCheckReportVo 实体对象
     * @return 新增结果
     */
    @Override
    public String saveBallCheckData(BallCheckReportVo ballCheckReportVo) {

        if (ObjectUtils.isEmpty(ballCheckReportVo.getId())) {
            //插入
            Createorder c = createorderService.getInfoById(ballCheckReportVo.getDdid());
            BallCheckReport ballCheckReport = new BallCheckReport();
            ballCheckReport.setDiseaseHealth(ballCheckReportVo.getDiseaseHealth());

            ballCheckReport.setBeginTime(ballCheckReportVo.getBeginTime());
            ballCheckReport.setEndTime(ballCheckReportVo.getEndTime());

            ballCheckReport.setBcbgfx(ballCheckReportVo.getBcbgfx());
            ballCheckReport.setBgfx(ballCheckReportVo.getBgfx());
            ballCheckReport.setSglx(ballCheckReportVo.getSglx());
            ballCheckReport.setOrgDepart(ballCheckReportVo.getOrgDepart());
            ballCheckReport.setSampleName(ballCheckReportVo.getSampleName());
            ballCheckReport.setSampleType(ballCheckReportVo.getSampleType());
            ballCheckReport.setSex(ballCheckReportVo.getSex());
            ballCheckReport.setGroupId(c.getKhdwmcid());
            ballCheckReport.setDdh(c.getId());
            Sellcustomer sc = sellcustomerService.getInfoById(c.getKhdwmcid());
            if (sc != null) {
                ballCheckReport.setOrgName(sc.getKhdwmc());
            }
            ballCheckReport.setIsPrint(0);
            ballCheckReport.setStatus(0);// 去哪取 0:已总检；1：团检完成；2：团检已审批；3：团检已打印
            ballCheckReport.setCreateId(SecurityUtils.getUserNo());
            ballCheckReport.setScsflj(ballCheckReportVo.getScsflj());
            ballCheckReport.setBgfx(ballCheckReportVo.getBgfx());
            ballCheckReport.setHasUnchecked(ballCheckReportVo.getHasUnchecked());
            if (c != null) {
                ballCheckReport.setOrderId(c.getDdh());
            }
            ballCheckReport.setScbs(0);
            ballCheckReport.setFzxid(ballCheckReportVo.getFzxid());
            this.save(ballCheckReport);
            return "success";
        } else {
            //更新
            Createorder c = createorderService.getInfoById(ballCheckReportVo.getDdid());
            BallCheckReport ballCheckReport = this.ballCheckReportMapper.getInfoById(ballCheckReportVo.getId());
            if (ballCheckReport == null) {
                return "样本信息已被删除，请刷新重试！";
            }
            if (ballCheckReport.getStatus().intValue() == 1) {
                throw new RuntimeException("样本<font color='red'>" + ballCheckReport.getSampleName() + "</font>已提交，不能编辑！");
            } else if (ballCheckReport.getStatus().intValue() == 2) {
                throw new RuntimeException("样本<font color='red'>" + ballCheckReport.getSampleName() + "</font>已审核通过，不能编辑！");
            }
            ballCheckReport.setDiseaseHealth(ballCheckReportVo.getDiseaseHealth());
            ballCheckReport.setBeginTime(ballCheckReportVo.getBeginTime());
            ballCheckReport.setEndTime(ballCheckReportVo.getEndTime());
            ballCheckReport.setBcbgfx(ballCheckReportVo.getBcbgfx());
            ballCheckReport.setBgfx(ballCheckReportVo.getBgfx());
            ballCheckReport.setSglx(ballCheckReportVo.getSglx());
            ballCheckReport.setOrgDepart(ballCheckReportVo.getOrgDepart());
            ballCheckReport.setSampleName(ballCheckReportVo.getSampleName());
            ballCheckReport.setSampleType(ballCheckReportVo.getSampleType());
            Sellcustomer sc = sellcustomerService.getInfoById(c.getKhdwmcid());
            if (sc != null) {
                ballCheckReport.setOrgName(sc.getKhdwmc());
            }
            ballCheckReport.setSex(ballCheckReportVo.getSex());
            ballCheckReport.setGroupId(c.getKhdwmcid());
            ballCheckReport.setDdh(c.getId());
            ballCheckReport.setScsflj(ballCheckReportVo.getScsflj());
            ballCheckReport.setBgfx(ballCheckReportVo.getBgfx());
            ballCheckReport.setHasUnchecked(ballCheckReportVo.getHasUnchecked());
            if (c != null) {
                ballCheckReport.setOrderId(c.getDdh());
            }
            ballCheckReport.setScbs(0);
            ballCheckReport.setFzxid(ballCheckReportVo.getFzxid());
            this.updateById(ballCheckReport);
            return "success";
        }
    }

    @Override
    @Transactional
    public String addpeople(String reportId){
        BallCheckReport bcr = ballCheckReportMapper.getInfoById(reportId);
        if (bcr == null) {
            return "人员加入失败！样本已被删除！";
        }
        samplePersonService.deleteByCriteria(reportId, "");
        //加入条件
        String groupId = bcr.getGroupId();//团体Id
        String sglx = bcr.getSglx();//上岗类型
        String orderId = bcr.getOrderId();//订单号
        String orgDepart = bcr.getOrgDepart();
        int hasUnchecked = bcr.getHasUnchecked() == null ? 1 : bcr.getHasUnchecked();//是否包含未检

        //构造查询条件
        Peispatient peispatient = new Peispatient();
        peispatient.setOrgDepart(orgDepart);
        peispatient.setIdExamtype(bcr.getDiseaseHealth().toString());
        peispatient.setNumorgresv(orderId);
        peispatient.setIdOrg(groupId);
        if (bcr.getBeginTime() != null) {
            peispatient.setBeginTime(bcr.getBeginTime());
        }
        if (bcr.getEndTime() != null) {
            peispatient.setEndTime(bcr.getEndTime());
        }
        if (hasUnchecked == 0) {
            //不含未检查已登记的
            peispatient.setFRegistered(1);
        }
        if (bcr.getSex() != null) {
            peispatient.setIdSex(bcr.getSex());
        }
        String fzxid = bcr.getFzxid();
        if (StringUtils.isNotEmpty(fzxid)){
            String[] array = fzxid.split(",");
            List<String> list = Arrays.asList(array);
            peispatient.setBranchIds(list);
        }

        //查询新系统数据
        List<Peispatient> list = reportPrintingMapper.getList(peispatient);
        //查询老系统数据
        List<Peispatient> oldList = orPeispatientService.getList(peispatient);
        list.addAll(oldList);

        List<SamplePerson> spList = new ArrayList<>();
        for (Peispatient p : list) {
            SamplePerson sp = new SamplePerson();
            sp.setBallCheckId(reportId);
            sp.setPatientcode(p.getPatientcode());
            sp.setGroupId(groupId);
            sp.setPatientId(p.getId());
            //上岗类型相符的才插入
            if (StringUtils.isEmpty(sglx)) {
                spList.add(sp);
            } else if (StringUtils.isNotEmpty(p.getMedicaltype()) && sglx.contains(p.getMedicaltype())) {// 上岗类型
                spList.add(sp);
            }
        }
        samplePersonService.saveBatch(spList);
        return "success";
    }

    @Override
    public String removeReport(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                BallCheckReport bcr = this.ballCheckReportMapper.getInfoById(id);
                if (bcr != null) {
                    if (bcr.getStatus().intValue() == 1) {
                        throw new RuntimeException("样本<font color='red'>" + bcr.getSampleName() + "</font>已提交，不能删除！");
                    } else if (bcr.getStatus().intValue() == 2) {
                        throw new RuntimeException("样本<font color='red'>" + bcr.getSampleName() + "</font>已审核通过，不能删除！");
                    }
                }
                samplePersonService.deleteByCriteria(id, "");
            }
            this.removeByIds(Arrays.asList(idArray));
            return "success";
        } else {
            return "请选择记录！";
        }
    }

    @Override
    public List<Peispatient> addSamplePersonData(String groupId, String idOrgid, String reportId, String key, String flag) {
        BallCheckReport bcr = this.getInfoById(reportId);
        Peispatient peispatient = new Peispatient();
        peispatient.setIdOrg(groupId);
        peispatient.setNumorgresv(bcr.getOrderId());
        key = key.trim().toUpperCase();
        if (StringUtils.isNotEmpty(key)) {
            if ("patientcode".equals(flag)) {
                peispatient.setPatientcode(key);
            } else if ("patientname".equals(flag)) {
                peispatient.setInputCode(key);
            }
        }
        Integer sex = bcr.getSex();
        if (sex != null) {
            peispatient.setIdSex(sex);
        }
        List<Peispatient> list = reportPrintingMapper.getList(peispatient);
        //查询老系统数据
        List<Peispatient> oldList = orPeispatientService.getList(peispatient);
        list.addAll(oldList);
        return list;
    }

    @Override
    public String saveData(GHSaveDataParam param) {
        String groupId = param.getGroupId();
        String reportId = param.getReportId();
        List<GHGridData> griddata = param.getGriddata();
        if ("".equals(groupId) && "".equals(reportId)) {
            throw new ServiceException("请先选择一个样本！");
        }
        BallCheckReport bcr = this.getInfoById(reportId);
        if (bcr == null) {
            throw new ServiceException("保存失败，样本信息不存在，请刷新重试！");
        }
        if (bcr.getStatus().intValue() == 1) {
            throw new ServiceException("样本" + bcr.getSampleName() + "已提交，不能编辑人员！");
        } else if (bcr.getStatus().intValue() == 2) {
            throw new ServiceException("样本" + bcr.getSampleName() + "已审核通过，不能编辑人员！");
        }
//        samplePersonService.deleteByCriteria(reportId, groupId);
        int s = griddata.size();
        for (int i = 0; i < s; i++) {
            GHGridData m = griddata.get(i);
            SamplePerson sp = new SamplePerson();
            sp.setId(m.getId());
            sp.setGroupId(groupId);
            sp.setPatientcode(m.getPatientcode());
            sp.setBallCheckId(reportId);
            sp.setPatientId(m.getId());
            samplePersonService.saveOrUpdate(sp);
        }
        return "success";
    }

    @Override
    public List<GHExportDataVo> export(String groupId, String reportId) {
        List<SamplePerson> list = samplePersonService.list(new QueryWrapper<SamplePerson>()
                .eq("group_id", groupId).eq("ball_check_id", reportId));
        List<String> patientIds = list.stream().map(SamplePerson::getPatientId).collect(Collectors.toList());
        //导出职业团检样本数据
        List<GHExportDataVo> list1 = ballCheckReportMapper.export(patientIds);
        //查询老系统数据
        List<String> newPatientIds = list1.stream().map(GHExportDataVo::getId).collect(Collectors.toList());
        patientIds.removeAll(newPatientIds);
        if (CollectionUtils.isNotEmpty(patientIds)){
            List<GHExportDataVo> oldList = ballCheckReportMapper.oldExport(patientIds);
            list1.addAll(oldList);
        }
        return list1;
    }

    @Override
    public IPage<BallCheckReport> loadAuditBallCheckData(PageParam<BallCheckReport> page, ReportAuditParam param) {
        //不是领导 或 没有决策管理权限的只能自己看自己的
        Boolean greatLeader = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        Boolean leader = SecurityUtils.isLeader();
        if (greatLeader) {
            //决策管理查所有的数据
        }else if (leader){
            //领导查他的下级数据
            List<String> lowerLevelIds = sysUserMapper.getLowerLevelId(SecurityUtils.getUserNo());
            lowerLevelIds.add(SecurityUtils.getUserNo());
            param.setLowerLevelIds(lowerLevelIds);
        }else {
            //查询自己的订单
            param.setUserNo(SecurityUtils.getUserNo());
        }
        return this.ballCheckReportMapper.loadAuditBallCheckData(page, param);
    }

    @Override
    public String commit(String ids) {
        if (StringUtils.isEmpty(ids.trim())) {
            return "请选择记录！";
        }
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            BallCheckReport bcr = this.ballCheckReportMapper.getInfoById(id);
            if (bcr == null) {
                throw new RuntimeException("数据已不存在，请刷新重试！");
            }
            int status = bcr.getStatus();
            if (status == 1 || status == 2) {
                throw new RuntimeException("所选样本<font color='red'>" + bcr.getSampleName() + "</font>已被提交，请勿重复操作！");
            }
            bcr.setStatus(1);
            this.updateById(bcr);
        }
        return "success";
    }

    @Override
    public String recall(String ids) {
        if (StringUtils.isEmpty(ids.trim())) {
            return "请选择记录！";
        }
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            BallCheckReport bcr = this.ballCheckReportMapper.getInfoById(id);
            if (bcr == null) {
                throw new RuntimeException("数据已不存在，请刷新重试！");
            }
            int status = bcr.getStatus();
            if (status != 1) {
                throw new RuntimeException("所选样本<font color='red'>" + bcr.getSampleName() + "</font>的状态不是'已提交'，请选择状态为已提交的数据！");
            }
            bcr.setStatus(4);
            this.updateById(bcr);
        }
        return "success";
    }

    @Override
    public List<Createorder> addBallCheckReportOrderData(String groupId) {
        List<Createorder> orders = createorderService.getDateByKhdwmcid(groupId);
        return orders;
    }

    @Override
    public List<HashMap> loadSamplePersonViewData(String id) {
        List<SamplePerson> samplePerson = this.samplePersonService.getListByBallCheckId(id);
        BallCheckReport bcp = this.ballCheckReportMapper.getInfoById(id);
        String groupId = bcp == null ? null : bcp.getGroupId();
        List<HashMap> list = new ArrayList<HashMap>();
        if (samplePerson != null & samplePerson.size() > 0) {
            for (SamplePerson s : samplePerson) {
                Peispatient p = null;
                if (s.getPatientcode() != null) {
                    p = this.reportPrintingMapper.getBypatientCode(s.getPatientcode());
                } else if (s.getPatientId() != null) {
                    p = this.reportPrintingMapper.getInfoById(s.getPatientId());
                } else {
                    continue;
                }
                HashMap map = new HashMap();
                map.put("groupId", groupId);
                map.put("patientcode", s.getPatientcode());
                map.put("ballCheckId", id);
                if (p.getFRegistered() == null || p.getFRegistered().intValue() == 0) {
                    map.put("tjzt", "未检");
                } else if (p.getFReadytofinal() == null || p.getFReadytofinal().intValue() == 0) {
                    map.put("tjzt", "在检");
                } else {
                    map.put("tjzt", "分检完成");
                }
                if (p != null) {
                    map.put("id", p.getId());
                    map.put("FExamstarted", p.getFExamstarted());
                    map.put("FFinalexamed", p.getFFinalexamed());
                    map.put("patientname", p.getPatientname());
                    map.put("idSex", p.getIdSex());
                    map.put("age", (int) Math.floor(p.getAge()));
                    map.put("orgName", p.getOrgName());
                    map.put("orgDepart", p.getOrgDepart());
                    map.put("jktjzt", p.getJktjzt());
                    map.put("zytjzt", p.getZytjzt());
                    list.add(map);
                }
            }
        }
        return list;
    }

    @Override
    public String finish(String id, String spyj, String spjg) {
        BallCheckReport bcr = this.ballCheckReportMapper.getInfoById(id);
        if (bcr == null) {
            throw new RuntimeException("数据已不存在，请刷新重试！");
        }
        if (spjg == null) {
            throw new RuntimeException("请选择审批结果！");
        }
        bcr.setStatus(Integer.parseInt(spjg));
        bcr.setReason(spyj);
        this.updateById(bcr);
        return "success";
    }

    @Override
    public String unfinish(String ids) {
        if (StringUtils.isEmpty(ids.trim())) {
            return "请选择记录！";
        }
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            BallCheckReport bcr = this.getInfoById(id);
            if (bcr == null) {
                throw new RuntimeException("数据已不存在，请刷新重试！");
            }
            int status = bcr.getStatus();
            if (status != 2 && status != 3) {
                throw new RuntimeException("所选样本<font color='red'>" + bcr.getSampleName() + "</font>未审核，请刷新重试！");
            }
            bcr.setStatus(1);
            this.updateById(bcr);
        }
        return "success";
    }

    @Override
    public List<HashMap> peopleanalyze_zy(String reportId) {
        List<FxCompletion> samplePerson = ballCheckReportMapper.getFxCompletionsBySampleId(reportId);
        List<HashMap> list = new ArrayList<HashMap>();
        int total = samplePerson.size(), boy = 0, girl = 0, boyexamed = 0, girlexamed = 0;
        // 人员
        if (samplePerson != null & samplePerson.size() > 0) {
            for (FxCompletion s : samplePerson) {
                if ("0".equals(s.getSex())) {
                    ++boy;
                    if (s.getFExamstarted() != null && 1 == s.getFExamstarted()) {// 体检者表
                        ++boyexamed;
                    }
                } else if ("1".equals(s.getSex())) {
                    ++girl;
                    if (s.getFExamstarted() != null && 1 == s.getFExamstarted()) {
                        ++girlexamed;
                    }
                }
            }
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        String[] s1 = {"计划", "参检", "未检"};
        Integer[] s2 = {boy, boyexamed, boy - boyexamed};
        Integer[] s3 = {girl, girlexamed, girl - girlexamed};
        Integer[] s4 = {total, boyexamed + girlexamed, total - (boyexamed + girlexamed)};
        String[] s5 = {numberFormat.format(boy * 1.00 / total * 100) + "%", numberFormat.format(boyexamed * 1.00 / (boyexamed + girlexamed) * 100) + "%", numberFormat.format((boy - boyexamed) * 1.00 / (total - (boyexamed + girlexamed)) * 100) + "%"};
        String[] s6 = {numberFormat.format(girl * 1.00 / total * 100) + "%", numberFormat.format(girlexamed * 1.00 / (boyexamed + girlexamed) * 100) + "%", numberFormat.format((girl - girlexamed) * 1.00 / (total - (boyexamed + girlexamed)) * 100) + "%"};
        String[] s7 = {"100%", numberFormat.format(boyexamed * 1.00 / boy * 100) + "%", numberFormat.format((boy - boyexamed) * 1.00 / boy * 100) + "%"};
        String[] s8 = {"100%", numberFormat.format((girlexamed) * 1.00 / girl * 100) + "%", numberFormat.format((girl - girlexamed) * 1.00 / girl * 100) + "%"};
        String[] s9 = {"100%", numberFormat.format((boyexamed + girlexamed) * 1.00 / total * 100) + "%", numberFormat.format((total - (boyexamed + girlexamed)) * 1.00 / total * 100) + "%"};
        if (boyexamed == 0) {
            s5[1] = "0%";
            s5[2] = "0%";
        }
        if (girlexamed == 0) {
            s6[1] = "0%";
            s6[2] = "0%";
        }

        if (boy == 0) {
            s7[1] = "0%";
            s7[2] = "0%";
        }
        if (girl == 0) {
            s8[1] = "0%";
            s8[2] = "0%";
        }
        if (total == 0) {
            s5[0] = "0%";
            s6[0] = "0%";
            s9[1] = "0%";
            s9[2] = "0%";
        }
        if (total == (boyexamed + girlexamed)) {
            s5[2] = "0%";
            s6[2] = "0%";
        }

        for (int i = 0; i < 3; i++) {
            HashMap map = new HashMap();
            map.put("type", s1[i]);
            map.put("boy1", s2[i]);
            map.put("girl1", s3[i]);
            map.put("total1", s4[i]);
            map.put("boy2", s5[i].replace("NaN", "0"));
            map.put("girl2", s6[i].replace("NaN", "0"));
            map.put("boy3", s7[i].replace("NaN", "0"));
            map.put("girl3", s8[i].replace("NaN", "0"));
            map.put("total3", s9[i].replace("NaN", "0"));
            list.add(map);
        }
        return list;
    }

    @Override
    public List<HashMap> ageanalyze_zy(String reportId) {
        List<HashMap> list = new ArrayList<HashMap>();
        List<AgeSqlZyDto> answer = this.ballCheckReportMapper.getAgeSqlZy(reportId);
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        if (answer != null && answer.size() > 0) {

            AgeSqlZyDto arr = answer.get(0);
            AgeSqlZyDto arr1 = answer.get(answer.size() - 1);
            int max = (int) Float.parseFloat(arr.getAge().toString()) / 5 + 1;
            int min = (int) Float.parseFloat(arr1.getAge().toString()) / 5;
            for (int i = min; i < max; i++) {
                int boy2 = 0, girl2 = 0, total2 = 0;
                int m = 0, n = 0, k = 0;
                HashMap map = new HashMap();
                map.put("agegroup", i * 5 + "~" + (i + 1) * 5);
                for (int j = 0; j < answer.size(); j++) {
                    int boy1 = 0, girl1 = 0, total1 = 0, age = 0;
                    AgeSqlZyDto o =  answer.get(j);
                    age = (int) Float.parseFloat(o.getAge().toString());
                    boy1 = (int) Float.parseFloat(o.getMan().toString());
                    girl1 = (int) Float.parseFloat(o.getWoman().toString());
                    total1 = (int) Float.parseFloat(o.getCount().toString());
                    boy2 += boy1;
                    girl2 += girl1;
                    total2 += total1;
                    if (age < (i + 1) * 5 && age >= i * 5) {
                        m += boy1;
                        n += girl1;
                        k += total1;
                        map.put("boy1", m);
                        map.put("girl1", n);
                        map.put("total1", k);
                    } else {
                        map.put("boy1", m);
                        map.put("girl1", n);
                        map.put("total1", k);
                    }
                    if (boy2 == 0) {
                        map.put("boy2", "0%");
                        map.put("boy1", "0");
                    } else {
                        map.put("boy2", numberFormat.format(m * 1.00 / boy2 * 100) + "%");
                    }
                    if (girl2 == 0) {
                        map.put("girl2", "0%");
                        map.put("girl1", "0");
                    } else {
                        map.put("girl2", numberFormat.format(n * 1.00 / girl2 * 100) + "%");
                    }
                    if (total2 == 0) {
                        map.put("total2", "0%");
                        map.put("total1", "0");
                    } else {
                        map.put("total2", numberFormat.format(k * 1.00 / total2 * 100) + "%");
                    }
                }
                list.add(map);

            }

        }
        return list;
    }

    @Override
    public List<HashMap> dateanalyze_zy(String reportId) {
        List<HashMap> list = new ArrayList<HashMap>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        List<AnalyzeZyDto> answer = this.ballCheckReportMapper.dateanalyze_zy(reportId);
        if (answer != null && answer.size() > 0) {
            try {
                for (int i = 0; i < answer.size(); i++) {
                    HashMap map = new HashMap();
                    AnalyzeZyDto arr = answer.get(i);
                    int dayForWeek = 0;
                    if (ObjectUtils.isNotEmpty(arr.getDateregister())) {
                        c.setTime(format.parse(arr.getDateregister()));
                        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                            dayForWeek = 7;
                        } else {
                            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                        }
                        map.put("date0", arr.getDateregister().toString());
                        map.put("date1", dayForWeek);
                        map.put("date2", arr.getMan().toString());
                        map.put("date3", arr.getWoman().toString());
                        map.put("date4", arr.getCount().toString());
                        list.add(map);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<DanagerObject> examanalyze_zy(String reportId) {
        return this.ballCheckReportMapper.examanalyze_zy(reportId);
    }

    @Override
    public List<FxPersonnelview> getPersonnelData(String reportId, String flag) {
        return this.ballCheckReportMapper.getPersonnelData(reportId, flag);
    }

    @Override
    public IPage<FxCompletion> finishanalyze_zy(PageParam<Report> page,String reportId) {
        return this.ballCheckReportMapper.finishanalyze_zy(page,reportId);
    }

    @Override
    public List<FxReviewsituation> getReviewData(String reportId) {
        return this.ballCheckReportMapper.getReviewData(reportId);
    }

    @Override
    public String saveAllImgJk(ImgJkParam param) throws IOException {
        BallCheckReport bcr = this.ballCheckReportMapper.getInfoById(param.getReportId());
        if (bcr == null) {
            throw new ServiceException("样本不存在");
        }
        bcr.setPicInspectM(param.getPic_inspect_m());
        bcr.setPicInspectW(param.getPic_inspect_w());
        bcr.setPicInspectT(param.getPic_inspect_t());
        bcr.setPicAgeColumnar(param.getPic_age_columnar());
        bcr.setPicAgePie(param.getPic_age_pie());
        bcr.setPicAgeW(param.getPic_age_w());
        bcr.setPicAgeM(param.getPic_age_m());
        bcr.setItemTotal(param.getItem_total());
        bcr.setExceptionM(param.getException_m());
        bcr.setExceptionW(param.getException_w());
        bcr.setExceptionT(param.getException_t());
        ballCheckReportMapper.updateById(bcr);
        return "success";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generateAnalyse(String ids, String bgdybs) {
        {
            if (StringUtils.isEmpty(ids)) {
                return "请选择样本!";
            }
            String[] ballIds = ids.split(",");
            String cid = SecurityUtils.getCId();
            boolean bhys = "1".equals(bgdybs) ? true : false;
            this.ballCheckReportMapper.deleteFxCompletion(ballIds);
            this.ballCheckReportMapper.deleteDanagerObject(ballIds);
            this.ballCheckReportMapper.deleteFxPersonnelview(ballIds);
            this.ballCheckReportMapper.deleteFxReviewsituation(ballIds);
            this.ballCheckReportMapper.deleteFxItemscheck(ballIds);
            this.ballCheckReportMapper.deleteFxDetection(ballIds);
            this.ballCheckReportMapper.deleteFxPositive(ballIds);
            this.ballCheckReportMapper.deleteFxNegative(ballIds);
            this.ballCheckReportMapper.deleteFxDetectionzy(ballIds);
            this.ballCheckReportMapper.deleteFxSummary(ballIds);
            this.ballCheckReportMapper.deleteFxReviewInfo(ballIds);
            this.ballCheckReportMapper.deleteFxHarm(ballIds);

            List<FxCompletion> completions = new ArrayList<FxCompletion>();
            List<DanagerObject> danagers = new ArrayList<DanagerObject>();
            List<FxPersonnelview> persons = new ArrayList<FxPersonnelview>();
            List<FxReviewsituation> reviews = new ArrayList<FxReviewsituation>();
            List<FxItemscheck> checks = new ArrayList<FxItemscheck>();
            List<FxDetection> dets = new ArrayList<FxDetection>();
            List<FxPositive> poss = new ArrayList<FxPositive>();
            List<FxNegative> unposs = new ArrayList<FxNegative>();
            List<FxDetectionzy> detzys = new ArrayList<FxDetectionzy>();
            List<BallCheckReport> bcrs = new ArrayList<BallCheckReport>();
            List<FxReviewInfo> ris = new ArrayList<FxReviewInfo>();
            List<FxSummary> sums = new ArrayList<FxSummary>();
            for (String id : ballIds) {
                BallCheckReport report = this.ballCheckReportMapper.getInfoById(id);
                if (report == null) {
                    throw new RuntimeException("样本已不存在，请刷新重试！");
                }
                bcrs.add(report);
                report.setPicInspectM(null);
                report.setPicInspectW(null);
                report.setPicInspectT(null);
                report.setPicAgeColumnar(null);
                report.setPicAgeM(null);
                report.setPicAgePie(null);
                report.setPicAgeW(null);
                report.setItemTotal(null);
                report.setExceptionM(null);
                report.setExceptionT(null);
                report.setExceptionW(null);
                if (report.getDiseaseHealth().intValue() == 0) {// 健康
                    /** 项目完成情况 */
                    List<FindXmwcqkDto> list = this.ballCheckReportMapper.findXmwcqk(bhys, id);
                    //老系统体检号
                    List<String> patientCodes = new ArrayList<>();
                    for (FindXmwcqkDto vo : list) {
                        if (StringUtils.isEmpty(vo.getPatientname()) && StringUtils.isNotEmpty(vo.getPatientcode())){
                            patientCodes.add(vo.getPatientcode());
                        }
                    }
                    if (CollectionUtils.isNotEmpty(patientCodes)){
                        List<FindXmwcqkDto> findXmwcqkDtoList = orPeispatientService.findXmwcqk(new ArrayList<>(patientCodes));
                        for (FindXmwcqkDto orPeispatient : findXmwcqkDtoList) {
                            for (FindXmwcqkDto findXmwcqkDto : list) {
                                if (findXmwcqkDto.getPatientcode().equals(orPeispatient.getPatientcode())){
                                    findXmwcqkDto.setPatientname(orPeispatient.getPatientname());
                                    findXmwcqkDto.setIdSex(orPeispatient.getIdSex());
                                    findXmwcqkDto.setAge(orPeispatient.getAge());
                                    findXmwcqkDto.setIdOrg(orPeispatient.getIdOrg());
                                    findXmwcqkDto.setOrgName(orPeispatient.getOrgName());
                                    findXmwcqkDto.setOrgDepart(orPeispatient.getOrgDepart());
                                    findXmwcqkDto.setIdMarriage(orPeispatient.getIdMarriage());
                                    findXmwcqkDto.setIdExamfeeitem(orPeispatient.getIdExamfeeitem());
                                    findXmwcqkDto.setExamFeeItemName(orPeispatient.getExamFeeItemName());
                                    findXmwcqkDto.setMedicaldate(orPeispatient.getMedicaldate());
                                    findXmwcqkDto.setJktjzt(orPeispatient.getJktjzt());
                                    findXmwcqkDto.setFReadytofinal(orPeispatient.getFReadytofinal());
                                    findXmwcqkDto.setFExamstarted(orPeispatient.getFExamstarted());
                                    findXmwcqkDto.setDateregister(orPeispatient.getDateregister());
                                    findXmwcqkDto.setStatus(orPeispatient.getStatus());
                                    findXmwcqkDto.setFRegistered(orPeispatient.getFRegistered());
                                    findXmwcqkDto.setIdcardno(orPeispatient.getIdcardno());
                                    break;
                                }
                            }
                        }
                    }


                    for (FindXmwcqkDto os : list) {
                        FxCompletion com = new FxCompletion();
                        com.setPatientcode(os.getPatientcode() == null ? null : os.getPatientcode());
                        com.setSampleId(id);// os[1]样本人员ID
                        com.setPatientname(os.getPatientname() == null ? null : os.getPatientname());
                        com.setSex(os.getIdSex() == null ? null : os.getIdSex());
                        com.setAge(os.getAge() == null ? null : os.getAge());
                        com.setIdOrg(os.getIdOrg() == null ? null : os.getIdOrg());
                        com.setOrgName(os.getOrgName() == null ? null : os.getOrgName());
                        com.setOrgDepart(os.getOrgDepart() == null ? null : os.getOrgDepart());
                        com.setIdMarriage(os.getIdMarriage() == null ? null : String.valueOf(os.getIdMarriage()));
                        com.setMarriage(os.getIdMarriage() == null ? null : MarriageType.getName(Integer.valueOf(os.getIdMarriage())));
                        com.setUncheckedItemids(os.getIdExamfeeitem() == null ? null : os.getIdExamfeeitem());
                        com.setUncheckedItems(os.getExamFeeItemName() == null ? null : os.getExamFeeItemName());
                        com.setMedicaldate(os.getMedicaldate() == null ? null : os.getMedicaldate());
                        com.setJktjzt(os.getJktjzt() == null ? null :os.getJktjzt());
                        com.setFReadytofinal(os.getFReadytofinal() == null ? null : os.getFReadytofinal());
                        com.setFExamstarted(os.getFExamstarted() == null ? null : os.getFExamstarted());
                        com.setDateregister(os.getDateregister() == null ? null : os.getDateregister());
                        com.setIsPrint(os.getStatus() == null ? 0 : os.getStatus() >= 2 ? 1 : 0);
                        com.setRegistered(os.getFRegistered() == null ? 0 : os.getFRegistered());
                        com.setIdcardno(os.getIdcardno());
                        com.setFRegistered((!"3".equals(os.getIdExamtype()) && StringUtils.isEmpty(os.getInsuranceno()))? 1 : 0);
                        completions.add(com);
                    }
                    /** 项目參检 */
                    List<FindXmcjDto> FindXmcjList = this.ballCheckReportMapper.findXmcj(bhys, id);
                    if (CollectionUtils.isNotEmpty(patientCodes)){
                        //查询老系统项目參检
                        List<FindXmcjDto> findXmcjDtos = orPeispatientService.findXmcj(new ArrayList<>(patientCodes));
                        for (FindXmcjDto orPeispatient : findXmcjDtos) {
                            Boolean flag = true;
                            for (FindXmcjDto findXmcjDto : FindXmcjList) {
                                if (findXmcjDto.getIdExamfeeitem().equals(orPeispatient.getIdExamfeeitem())) {
                                    flag = false;
                                    // 相加
                                    findXmcjDto.setCount1(findXmcjDto.getCount1() + orPeispatient.getCount1());
                                    findXmcjDto.setCount2(findXmcjDto.getCount2() + orPeispatient.getCount2());
                                    findXmcjDto.setCount3(findXmcjDto.getCount3() + orPeispatient.getCount3());
                                    findXmcjDto.setCount4(findXmcjDto.getCount4() + orPeispatient.getCount4());
                                    // 找到匹配项后，不再需要继续遍历，可以跳出内部循环
                                    break;
                                }
                            }
                            if (flag) {
                                // 如果没有找到就添加
                                FindXmcjList.add(orPeispatient);
                            }
                        }
                    }
                    for (int i = 0, l = FindXmcjList.size(); i < l; i++) {
                        FindXmcjDto os = FindXmcjList.get(i);
                        FxItemscheck check = new FxItemscheck();
                        check.setSampleId(id);
                        check.setDepId(os.getIdKs() == null ? null : os.getIdKs());
                        check.setDepName(os.getDeptName() == null ? null : os.getDeptName());
                        check.setItemId(os.getIdExamfeeitem() == null ? null : os.getIdExamfeeitem());
                        check.setItemName(os.getExamfeeitemNameprn() == null ? null : os.getExamfeeitemNameprn());
                        int checkMale = os.getCount1();
                        int checkTotal = os.getCount2();
                        int checkFemale = checkTotal - checkMale;
                        int allMale = os.getCount3();
                        int allTotal = os.getCount4();
                        int allFemale = allTotal - allMale;
                        double permale = allMale == 0 ? 0.0
                                : ((double) checkMale / allMale);
                        double perfemale = allFemale == 0 ? 0.0
                                : ((double) checkFemale / allFemale);
                        check.setCheckMale(checkMale);
                        check.setCheckTotal(checkTotal);
                        check.setCheckFemale(checkFemale);
                        check.setAllMale(allMale);
                        check.setAllTotal(allTotal);
                        check.setAllFemale(allFemale);
                        check.setPerMale(permale);
                        check.setPerFemale(perfemale);
                        check.setPerTotal(allTotal == 0 ? 0.0
                                : ((double) checkTotal / allTotal));
                        check.setReportSort(os.getReportSort() == null ? null : os.getReportSort());
                        check.setRowno(i + 1);
                        checks.add(check);
                    }
                    /** 检出统计 团体小结 */
                    List<FindJctjDto> findJctjList = this.ballCheckReportMapper.findJctj(id);
                    if (CollectionUtils.isNotEmpty(patientCodes)){
                        List<FindJctjDto> findJctjList2 = orPeispatientService.findJctj(new ArrayList<>(patientCodes));
                        //设置e和f
                        if (ObjectUtils.isNotEmpty(findJctjList)){
                            FindJctjDto findJctjDto = findJctjList.get(0);
                            for (FindJctjDto jctjDto : findJctjList2) {
                                jctjDto.setE(findJctjDto.getE());
                                jctjDto.setF(findJctjDto.getF());
                            }
                        }else {
                            //全是老系统的数据，重新查询一次
                            FindJctjDto findJctjDto = this.ballCheckReportMapper.findJctjEF(id);
                            for (FindJctjDto jctjDto : findJctjList2) {
                                jctjDto.setE(findJctjDto.getE());
                                jctjDto.setF(findJctjDto.getF());
                            }
                        }
                        findJctjList.addAll(findJctjList2);
                        //设置c和d

                        List<FindJctjDto> originalFindJctjList = new ArrayList<>(findJctjList);

                        // 遍历列表，将对象按 basconclusionId 分组存储
                        Map<String, List<FindJctjDto>> groupByBasconclusionId = new HashMap<>();
                        for (FindJctjDto obj : originalFindJctjList) {
                            String basconclusionId = obj.getBasconclusionId();
                            if (!groupByBasconclusionId.containsKey(basconclusionId)) {
                                groupByBasconclusionId.put(basconclusionId, new ArrayList<>());
                            }
                            groupByBasconclusionId.get(basconclusionId).add(obj);
                        }

                        // 去重操作，先移除属性 flag 相同的元素，再移除属性 c 和 d 相同的元素
                        for (List<FindJctjDto> group : groupByBasconclusionId.values()) {
                            // 移除属性 flag 相同的元素
                            Set<String> flagCheck = new HashSet<>();
                            group.removeIf(obj -> !flagCheck.add(obj.getFlag()));

                            // 移除属性 c 和 d 相同的元素
                            Set<Integer> cAndDCheck = new HashSet<>();
                            group.removeIf(obj -> !cAndDCheck.add(Objects.hash(obj.getC(), obj.getD())));
                        }

                        // 对相同 basconclusionId 的元素进行相加操作
                        for (List<FindJctjDto> group : groupByBasconclusionId.values()) {
                            if (group.size() > 1) {
                                FindJctjDto firstObj = group.get(0);
                                for (int i = 1; i < group.size(); i++) {
                                    FindJctjDto obj = group.get(i);
                                    firstObj.setC(firstObj.getC() + obj.getC());
                                    firstObj.setD(firstObj.getD() + obj.getD());
                                }
                            }
                            // 保留每组中的第一条记录，移除后续记录
                            group.subList(1, group.size()).clear();
                        }

                        // 更新原始列表 findJctjList 中的 c 和 d 属性
                        for (FindJctjDto obj : findJctjList) {
                            for (List<FindJctjDto> group : groupByBasconclusionId.values()) {
                                for (FindJctjDto groupedObj : group) {
                                    if (StringUtils.isNotEmpty(obj.getBasconclusionId()) && StringUtils.isNotEmpty(groupedObj.getBasconclusionId())
                                            && obj.getBasconclusionId().equals(groupedObj.getBasconclusionId())) {
                                        obj.setC(groupedObj.getC());
                                        obj.setD(groupedObj.getD());
                                    }
                                }
                            }
                        }


                    }
                    for (FindJctjDto os : findJctjList) {
                        FxDetection det = new FxDetection();
                        det.setSampleId(id);
                        det.setDepName(os.getDeptName() == null ? null : os.getDeptName());
                        det.setDepId(os.getDeptNo() == null ? null : os.getDeptNo());
                        det.setBasconclusionId(os.getBasconclusionId() == null ? null : os.getBasconclusionId());
                        det.setConclusion(os.getBasconclusionName() == null ? "未见异常" : os.getBasconclusionName());
                        if (os.getC() != null) {
                            int detectionTotal = os.getC();
                            int detectionMale = os.getD();
                            int detectionFemale = detectionTotal - detectionMale;
                            int allTotal = os.getE();
                            int allMale = os.getF();
                            int allFemale = allTotal - allMale;
                            det.setDetectionTotal(detectionTotal);
                            det.setDetectionFemale(detectionFemale);
                            det.setDetectionMale(detectionMale);
                            det.setAllFemale(allFemale);
                            det.setAllMale(allMale);
                            det.setAllTotal(allTotal);
                            det.setCheckMale(allMale == 0 ? null : ((double) detectionMale / allMale));
                            det.setCheckFemale(allFemale == 0 ? null : ((double) detectionFemale / allFemale));
                            det.setCheckTotal(allTotal == 0 ? null : ((double) detectionTotal / allTotal));
                        }
                        det.setPatientcode(os.getPatientCode() == null ? null : os.getPatientCode());
                        det.setPatientname(os.getPatientname() == null ? null : os.getPatientname());
                        det.setSex(os.getIdSex() == null ? null : Integer.parseInt(os.getIdSex()));
                        det.setAge(os.getAge() == null ? null : os.getAge());
                        det.setOrgDepart(os.getOrgDepart() == null ? null : os.getOrgDepart());
                        det.setReportSort(os.getReportSort() == null ? null : os.getReportSort());
                        dets.add(det);
                    }
                    /** 阳性结果 */
                    List<FindYangxjgDto> findYangxjgList = this.ballCheckReportMapper.findYangxjg(id);
                    if (CollectionUtils.isNotEmpty(patientCodes)){
                        List<FindYangxjgDto> findYangxjgList2 = orPeispatientService.findYangxjg(new ArrayList<>(patientCodes));
                        findYangxjgList.addAll(findYangxjgList2);
                    }
                    for (FindYangxjgDto os : findYangxjgList) {
                        String patientCode = os.getPatientcode() == null ? null : os.getPatientcode();
                        List<FindYinxjgDto> list_in = new ArrayList<>();
                        if (patientCodes.contains(patientCode)){
                            //查询老系统
                            list_in = orPeispatientService.findYinxjg(cid, patientCode, bhys);
                        }else {
                            //查询新系统
                            list_in = this.ballCheckReportMapper.findYinxjg(cid, patientCode, bhys);
                        }


                        if (list_in.size() == 0) {
                            //保存阴性结果
                            StringBuilder builder = new StringBuilder();
                            String dname = null;
                            int ino = 1;
                            for (int i = 0, l = list_in.size(); i < l; i++) {
                                FindYinxjgDto os_in = list_in.get(i);
                                String depName = os_in.getDeptName() == null ? "" : os_in.getDeptName();
                                if (StringUtils.isEmpty(dname) || !dname.equals(depName)) {
                                    dname = depName;
                                    builder.append(depName + ":");
                                    ino = 1;
                                }
                                String itemName = os_in.getExamfeeitemName() == null ? "" : os_in.getExamfeeitemName();
                                builder.append(ino + ">" + itemName + ":");
                                ino++;
                                builder.append(os_in.getMs() == null ? "" : os_in.getMs());
                                builder.append(";");
                            }
                            FxNegative pos = new FxNegative();
                            pos.setSampleId(id);
                            pos.setDateregister(os.getDateregister() == null ? null : os.getDateregister());
                            pos.setIdOrg(os.getIdOrg() == null ? null : os.getIdOrg());
                            pos.setOrgName(os.getOrgName() == null ? null : os.getOrgName());
                            pos.setOrgDepart(os.getOrgDepart() == null ? null : os.getOrgDepart());
                            pos.setPatientcode(patientCode);
                            pos.setPatientname(os.getPatientname() == null ? null : os.getPatientname());
                            pos.setSex(os.getIdSex() == null ? null :"0".equals(os.getIdSex()) ? "男" : "女" );
                            pos.setAge(os.getAge() == null ? null : os.getAge());
                            pos.setPositiveResult(builder.toString());
                            pos.setVerdict(os.getVerdict() == null ? null : os.getVerdict());
                            pos.setOffer(os.getOffer() == null ? null :  os.getOffer());
                            unposs.add(pos);
                        } else {
                            //保存阳性结果
                            StringBuilder builder = new StringBuilder();
                            String dname = null;
                            int ino = 1;
                            for (int i = 0, l = list_in.size(); i < l; i++) {
                                FindYinxjgDto os_in = list_in.get(i);
                                String depName = os_in.getDeptName() == null ? "" : os_in.getDeptName();
                                if (StringUtils.isEmpty(dname)
                                        || !dname.equals(depName)) {
                                    dname = depName;
                                    builder.append(depName + ":");
                                    ino = 1;
                                }
                                String itemName = os_in.getExamfeeitemName() == null ? "" : os_in.getExamfeeitemName();
                                builder.append(ino + ">" + itemName + ":");
                                ino++;
                                builder.append(os_in.getMs() == null ? "" : os_in.getMs()
                                        .toString());
                                builder.append(";");
                            }
                            FxPositive pos = new FxPositive();
                            pos.setSampleId(id);
                            pos.setDateregister(os.getDateregister() == null ? null : os.getDateregister());
                            pos.setIdOrg(os.getIdOrg() == null ? null : os.getIdOrg());
                            pos.setOrgName(os.getOrgName() == null ? null : os.getOrgName());
                            pos.setOrgDepart(os.getOrgDepart() == null ? null : os.getOrgDepart());
                            pos.setPatientcode(patientCode);
                            pos.setPatientname(os.getPatientname() == null ? null : os.getPatientname());
                            pos.setSex(os.getIdSex() == null ? null : "0".equals(os.getIdSex()) ? "男" : "女" );
                            pos.setAge(os.getAge() == null ? null : os.getAge());
                            pos.setPositiveResult(builder.toString());
                            pos.setVerdict(os.getVerdict() == null ? null : os.getVerdict());
                            pos.setOffer(os.getOffer() == null ? null :  os.getOffer());
                            poss.add(pos);
                        }
                    }
                } else {// 职业
                    /** 危害因素人员分布情况 */
                    Map<String, DanagerObject> dos = new HashMap<String, DanagerObject>();// key
                    // 接害因素:上岗类型
                    List<String> sglx_list = new ArrayList<String>(
                            Arrays.asList(report.getSglx().split(",")));// 样本上岗类型
                    String sellcustomerId = report.getGroupId();// 团体ID
                    String orderNo = report.getDdh();// 订单号
                    //查询大量体检者（600人），会造成堆内存溢出
                    List<FindDtjzDto> plist = this.ballCheckReportMapper.findDtjz(id);
                    //老系统体检号
                    List<String> patientCodes = new ArrayList<>();
                    for (FindDtjzDto vo : plist) {
                        if (StringUtils.isEmpty(vo.getMedicaltype()) && StringUtils.isNotEmpty(vo.getPatientcode())){
                            patientCodes.add(vo.getPatientcode());
                        }
                    }
                    if (CollectionUtils.isNotEmpty(patientCodes)){
                        List<FindDtjzDto> findDtjzDtos = orPeispatientService.findDtjz(new ArrayList<>(patientCodes));
                        for (FindDtjzDto orPeispatient : findDtjzDtos) {
                            for (FindDtjzDto findXmwcqkDto : plist) {
                                if (findXmwcqkDto.getPatientcode().equals(orPeispatient.getPatientcode())){
                                    findXmwcqkDto.setJhys(orPeispatient.getJhys());
                                    findXmwcqkDto.setIdSex(orPeispatient.getIdSex());
                                    findXmwcqkDto.setFRegistered(orPeispatient.getFRegistered());
                                    findXmwcqkDto.setMedicaltype(orPeispatient.getMedicaltype());
                                    break;
                                }
                            }
                        }
                    }
                    Integer peopleNum = plist.size();// 应查人数
                    Map<String, Integer> harmNumMap = new HashMap<String, Integer>();//接害因素：接触总人数
                    for (int i = 0; i < plist.size(); i++) {
                        FindDtjzDto os = plist.get(i);
                        String sglx = os.getMedicaltype();
                        if (!sglx_list.contains(sglx)) {
                            continue;
                        }
                        String[] jhyss = os.getJhys().split(",");
                        Integer freg = os.getFRegistered() == null ? 0 : os.getFRegistered();
                        for (String jhys : jhyss) {
                            if (harmNumMap.get(jhys) == null) {
                                harmNumMap.put(jhys, 1);
                            } else {
                                harmNumMap.put(jhys, harmNumMap.get(jhys) + 1);
                            }

                            String key = (jhys + ":" + sglx);
                            if (dos.get(key) == null) {
                                Harm harm = this.harmService.getInfoById(jhys);
                                if (harm == null) {
                                    throw new RuntimeException("接害因素:" + jhys +"相关职业危害因素已不存在！");
                                }
                                String harmTypeId = harm.getHarmClass();
                                ZyHarmClass harmClass = this.zyHarmClassService.getInfoById(harmTypeId);
                                if (harmClass == null) {
                                    throw new RuntimeException("相关职业危害因素分类已不存在！" + harmTypeId);
                                }
                                Object items = this.ballCheckReportMapper.findUniqueResult(jhys, sglx);
                                DanagerObject dan = new DanagerObject(
                                        harmClass.getName(), harmTypeId,
                                        harm.getHarmName(), jhys,
                                        items == null ? null : items.toString(),
                                        sellcustomerId, orderNo,
                                        sglx, id, peopleNum);
                                if ("0".equals(os.getIdSex().toString())) {
                                    dan.setManNum(1);
                                } else {
                                    dan.setWomenNum(1);
                                }
                                if (freg == 0) {
                                    dan.setUnexploredNum(1);
                                } else {
                                    dan.setInspectNum(1);
                                }
                                dos.put(key, dan);
                            } else {
                                DanagerObject dan = dos.get(key);
                                if ("0".equals(os.getIdSex().toString())) {
                                    dan.setManNum(dan.getManNum() + 1);
                                } else {
                                    dan.setWomenNum(dan.getWomenNum() + 1);
                                }
                                if (freg == 0) {
                                    dan.setUnexploredNum(dan.getUnexploredNum() + 1);
                                } else {
                                    dan.setInspectNum(dan.getInspectNum() + 1);
                                }
                            }
                        }
                    }
                    danagers.addAll(dos.values());
                    /**危害因素*/
                    Map<String, Integer> jhysMap = new HashMap<String, Integer>();
                    for (int i = 0; i < plist.size(); i++) {
                        FindDtjzDto os = plist.get(i);
                        String jhyss = os.getJhys();
                        if (StringUtils.isBlank(jhyss)) throw new ServiceException("体检号"+os.getPatientcode()+"的接害因素为空！");
                        String[] jhysarr = jhyss.split(",");
                        for (String jhysid : jhysarr) {
                            if (jhysMap.get(jhysid) == null) {
                                jhysMap.put(jhysid, 1);
                            } else {
                                jhysMap.put(jhysid, jhysMap.get(jhysid) + 1);
                            }
                        }
                    }
                    for (String jhysId : jhysMap.keySet()) {
                        Harm h = this.harmService.getInfoById(jhysId);
                        this.fxHarmService.save(new FxHarm(id, jhysId, String.valueOf(jhysMap.get(jhysId)), ObjectUtils.isNotEmpty(h)?h.getHarmName():""));
                    }
                    /* 项目完成情况 (只显示职业必查项目)*/
                    List<FindZybcxmDto> list = this.ballCheckReportMapper.findZybcxm(id);
                    if (CollectionUtils.isNotEmpty(patientCodes)){
                        //查询老系统数据
                        List<FindZybcxmDto> findZybcxmDtoList = orPeispatientService.findZybcxm(new ArrayList<>(patientCodes));
                        list.addAll(findZybcxmDtoList);
                    }
                    int uncheckedNum = 0;
                    for (FindZybcxmDto os : list) {
                        FxCompletion com = new FxCompletion();
                        com.setPatientcode(os.getPatientcode() == null ? null : os.getPatientcode());
                        com.setSampleId(id);// os[1]样本人员ID
                        com.setPatientname(os.getPatientname() == null ? null : os.getPatientname());
                        com.setSex(os.getIdSex() == null ? null : os.getIdSex());
                        com.setAge(os.getAge() == null ? null : os.getAge());
                        com.setJhgl(os.getJhgl() == null ? null : os.getJhgl());
                        String jhys = os.getJhys() == null ? null : os.getJhys();
                        if (jhys != null) {
                            com.setJhysIds(jhys);
                            List<Harm> harms = this.harmService.listByIds(Arrays.asList(jhys.split(",")));
                            if (harms.size() > 0) {
                                StringBuilder jhysNames = new StringBuilder();
                                for (Harm harm : harms) {
                                    jhysNames.append(harm.getHarmName() + "、");
                                }
                                com.setJhys(jhysNames.substring(0,
                                        jhysNames.length() - 1));
                            }
                        }
                        com.setIdOrg(os.getIdOrg() == null ? null : os.getIdOrg());
                        com.setOrgName(os.getOrgName() == null ? null : os.getOrgName());
                        com.setOrgDepart(os.getOrgDepart() == null ? null : os.getOrgDepart());
                        com.setIdMarriage(os.getIdMarriage() == null ? null : os.getIdMarriage());
                        com.setMarriage(os.getIdMarriage() == null ? null : os.getIdMarriage());
                        com.setUncheckedItemids(os.getIdExamfeeitem() == null ? null : os.getIdExamfeeitem());
                        com.setUncheckedItems(os.getExamfeeitemName() == null ? null : os.getExamfeeitemName());
                        com.setMedicaldate(os.getMedicaldate() == null ? null : os.getMedicaldate());
                        com.setZytjzt(os.getZytjzt() == null ? null : os.getZytjzt());
                        com.setFReadytofinal(os.getFReadytofinal() == null ? null :os.getFReadytofinal());
                        com.setFExamstarted(os.getFExamstarted() == null ? null :os.getFExamstarted());
                        com.setTrades(os.getTypeName() == null ? null : os.getTypeName());
                        com.setDateregister(os.getDateregister() == null ? null : os.getDateregister());
                        com.setRegistered(os.getFRegistered() == null ? 0 :os.getFRegistered());
                        com.setFRegistered((!"3".equals(os.getIdExamtype()) && StringUtils.isEmpty(os.getInsuranceno()))? 1 : 0);
                        com.setDateregisternotime(os.getDateregisternotime() == null ? null : os.getDateregisternotime());
                        com.setIdcardno(os.getIdcardno());
                        completions.add(com);
                        if (StringUtils.isNotEmpty(com.getUncheckedItems())) {
                            uncheckedNum++;
                        }
                    }
                    /** 人员一览表(其他疾病或异常、未见异常人员不展现存在拒检项目的人员) */
                    List<FindRyylDto> FindRyylList = this.ballCheckReportMapper.findRyyl(id);
                    if (CollectionUtils.isNotEmpty(patientCodes)){
                        //查询老系统数据
                        List<FindRyylDto> findRyylDtos = orPeispatientService.findRyyl(new ArrayList<>(patientCodes));
                        FindRyylList.addAll(findRyylDtos);
                    }
                    for (FindRyylDto os : FindRyylList) {
                        String jhys = os.getJhys() == null ? null : os.getJhys();
                        StringBuilder jhysNames = new StringBuilder();
                        if (jhys != null) {
                            List<Harm> harms = this.harmService.listByIds(Arrays.asList(jhys.split(",")));
                            if (harms.size() > 0) {
                                for (Harm harm : harms) {
                                    jhysNames.append(harm.getHarmName() + "、");
                                }
                                jhysNames.delete(jhysNames.length() - 1, jhysNames.length());
                            }
                        }
                        FxPersonnelview person = new FxPersonnelview(
                                os.getPatientcode(), id, os.getPosistive() == null ? null
                                : os.getPosistive(), os.getTest() == null ? null
                                : os.getTest(), os.getOccupationSummary() == null ? null
                                : os.getOccupationSummary(), os.getPatientname() == null ? null
                                : os.getPatientname(), os.getIdSex() == null ? null
                                : os.getIdSex(), os.getAge() == null ? null
                                : os.getAge(),
                                os.getJhgl() == null ? null : Integer.parseInt(os.getJhgl()
                                        .toString()), jhysNames.toString(), jhys,
                                os.getName() == null ? null : os.getName(),
                                os.getSerialNo() == null ? null : os.getSerialNo());
                        person.setIdcardno(os.getIdcardno());
                        persons.add(person);
                    }
                    /**复查明细*/
                    List<FindFcmxDto> findFcmxList = this.ballCheckReportMapper.findFcmx(id);
                    if (CollectionUtils.isNotEmpty(patientCodes)){
                        //查询老系统数据
                        List<FindFcmxDto> findRyylDtos = orPeispatientService.findFcmx(new ArrayList<>(patientCodes));
                        findFcmxList.addAll(findRyylDtos);
                    }
                    for (FindFcmxDto os : findFcmxList) {
                        FxReviewInfo ri = new FxReviewInfo(
                                id
                                , os.getOccupationDiagnosis()
                                , os.getHarmName()
                                , os.getDiagnosis() == null ? null : os.getDiagnosis()
                                , Integer.parseInt(os.getCount().toString())
                                , os.getSummaryText() == null ? null : os.getSummaryText());
                        ris.add(ri);
                    }
                    /** 复查情况 */
                    List<FindFcqkDto> findFcqkDtoList = this.ballCheckReportMapper.findFcqk(id);
                    if (CollectionUtils.isNotEmpty(patientCodes)){
                        //查询老系统数据
                        List<FindFcqkDto> findRyylDtos = orPeispatientService.findFcqk(new ArrayList<>(patientCodes));
                        findFcqkDtoList.addAll(findRyylDtos);
                    }
                    for (FindFcqkDto os : findFcqkDtoList) {
                        String jhys = os.getJhys() == null ? null : os.getJhys();
                        StringBuilder jhysNames = new StringBuilder();
                        if (jhys != null) {
                            List<Harm> harms = this.harmService.listByIds(Arrays.asList(jhys.split(",")));
                            if (harms.size() > 0) {
                                for (Harm harm : harms) {
                                    jhysNames.append(harm.getHarmName() + "、");
                                }
                                jhysNames.delete(jhysNames.length() - 1, jhysNames.length());
                            }
                        }
                        String patientCode = os.getPatientcode().toString();
                        FxReviewsituation review = new FxReviewsituation(id,
                                os.getPatientname() == null ? null : os.getPatientname(),
                                os.getIdSex() == null ? null : os.getIdSex(),
                                os.getAge() == null ? null :os.getAge().toString(), String.valueOf(jhysNames), jhys,
                                patientCode, os.getName() == null ? null : os.getName());
                        // 可能有多次复查，如果最后一次复查需要复查或未完成未登记，则复查未完成。如果一次也没有复查，则未复查。否则保存优先级最高的最后一次复查检查处理意见。
                        List<Peispatient> patients_review = new ArrayList<>();
                        if (patientCodes.contains(patientCode)){
                            //查询老系统
                            patients_review = orPeispatientService.getListByinPatientno(patientCode);
                            List<Peispatient> patients_review2= this.reportPrintingMapper.getListByinPatientno(patientCode);
                            patients_review.addAll(patients_review2);
                        }else {
                            //查询新系统
                            patients_review = this.reportPrintingMapper.getListByinPatientno(patientCode);
                        }

                        if (patients_review.size() == 0) {
                            review.setReviewStatus("未复查");
                        } else if (patients_review.size() == 1) {
                            Peispatient patient = patients_review.get(0);
                            if (patient.getFRegistered() == null || patient.getFRegistered() == 0) {
                                review.setReviewStatus("未复查");
                            } else {
                                if (patient.getZytjzt() == null || patient.getZytjzt() == 0) {
                                    review.setReviewStatus("复查未完成");
                                } else {
                                    String patientcode = patient.getPatientcode();// 复查体检号
                                    if (findNumByPatientcode(patientcode,patientCodes) == 0) {
                                        review.setReviewStatus("复查已完成");
                                    } else {
                                        review.setReviewStatus("复查未完成");
                                    }
                                    //先查新系统,新系统没有的话再查老系统
                                    List<FindListInDto> list_in = this.ballCheckReportMapper.findListIn(patientcode);
                                    if (CollectionUtils.isEmpty(list_in)){
                                        list_in = orPeispatientService.findListIn(patientCode);
                                    }

                                    if (list_in.size() > 0) {
                                        FindListInDto oin = list_in.get(0);
                                        review.setPositives(oin.getPosistive() == null ? null
                                                : oin.getPosistive());
                                        review.setSummaryText(oin.getSummaryText() == null ? null
                                                : oin.getSummaryText());
                                        review.setOccupationSummary(oin.getOccupationSummary() == null ? null
                                                : oin.getOccupationSummary());
                                        review.setSummarySerialno(oin.getSerialNo() == null ? null
                                                : oin.getSerialNo());
                                    }
                                }
                            }
                        } else {
                            Peispatient patient = patients_review.get(0);
                            Peispatient patient_last = patients_review.get(1);
                            String patientcode = patient.getPatientcode();// 复查体检号
                            if (patient.getZytjzt() == null
                                    || patient.getZytjzt().intValue() == 0) {
                                review.setReviewStatus("复查未完成");
                                patientcode = patient_last.getPatientcode();
                            } else {
                                if (findNumByPatientcode(patientcode,patientCodes) == 0) {
                                    review.setReviewStatus("复查已完成");
                                } else {
                                    review.setReviewStatus("复查未完成");
                                    patientcode = patient_last.getPatientcode();
                                }
                            }
                            List<FindListInDto> list_in = this.ballCheckReportMapper.findListIn(patientcode);
                            if (CollectionUtils.isEmpty(list_in)){
                                list_in = orPeispatientService.findListIn(patientCode);
                            }
                            if (list_in.size() > 0) {
                                FindListInDto oin = list_in.get(0);
                                review.setPositives(oin.getPosistive() == null ? null : oin.getPosistive());
                                review.setSummaryText(oin.getSummaryText() == null ? null
                                        : oin.getSummaryText());
                                review.setOccupationSummary(oin.getOccupationSummary() == null ? null
                                        : oin.getOccupationSummary());
                                review.setSummarySerialno(oin.getSerialNo() == null ? null
                                        : oin.getSerialNo());
                            }
                        }
                        reviews.add(review);
                    }
                    /**检出人数*/
                    //实查X名，职业健康检查结论是X人未见异常，X人听力异常，X人存在临床症状……。
                    //已登记为实查
                    int scrs = this.ballCheckReportMapper.findScrs(id);
                    if (CollectionUtils.isNotEmpty(patientCodes)){
                        scrs = scrs + orPeispatientService.findScrs(new ArrayList<>(patientCodes));
                    }
                    List<FindJcrsDto> findJcrsList = this.ballCheckReportMapper.findJcrs(id);
                    if (CollectionUtils.isNotEmpty(patientCodes)){
                        List<FindJcrsDto> findJcrsList2 = orPeispatientService.findJcrs(new ArrayList<>(patientCodes));
                        for (FindJcrsDto orPeispatient : findJcrsList2) {
                            Boolean flag = true;
                            for (FindJcrsDto findXmcjDto : findJcrsList) {
                                if (findXmcjDto.getJbmc().equals(orPeispatient.getJbmc())) {
                                    flag = false;
                                    findXmcjDto.setCount(findXmcjDto.getCount() + orPeispatient.getCount());
                                    break;
                                }
                            }
                            if (flag) {
                                // 如果没有找到就添加
                                findJcrsList.add(orPeispatient);
                            }
                        }
                    }
                    StringBuilder bcbgfx = new StringBuilder("实查" + scrs + "名");
                    if (findJcrsList.size() > 0 || uncheckedNum > 0) {
                        bcbgfx.append("，职业健康检查结论是");
                        for (FindJcrsDto os : findJcrsList) {
                            bcbgfx.append(os.getCount() + "人" + os.getJbmc() + "，");
                        }
                        if (uncheckedNum > 0) {
                            bcbgfx.append(uncheckedNum + "人未能完成检查，");
                        }
                        bcbgfx.delete(bcbgfx.length() - 1, bcbgfx.length());
                    }
                    bcbgfx.append("。");
                    report.setBcbgfx(bcbgfx.toString());
                    ballCheckReportMapper.updateById(report);

                    List<FindListDateDto> findListDatelist = this.ballCheckReportMapper.findListDate(id);
                    if (CollectionUtils.isNotEmpty(patientCodes)){
                        //查询老系统数据并合并
                        List<FindListDateDto> findListDatelist2 = orPeispatientService.findListDate(new ArrayList<>(patientCodes));
                        for (FindListDateDto orPeispatient : findListDatelist2) {
                            Boolean flag = true;
                            for (FindListDateDto findXmcjDto : findListDatelist) {
                                if (findXmcjDto.getHarmName().equals(orPeispatient.getHarmName())
                                        && findXmcjDto.getJbmc().equals(orPeispatient.getJbmc())
                                        && findXmcjDto.getJcjl().equals(orPeispatient.getJcjl())
                                ){
                                    flag = false;
                                    findXmcjDto.setCount(findXmcjDto.getCount() + orPeispatient.getCount());
                                    break;
                                }
                            }
                            if (flag) {
                                // 如果没有找到就添加
                                findListDatelist.add(orPeispatient);
                            }
                        }
                    }
                    for (FindListDateDto os : findListDatelist) {
                        String jh = os.getOccupationDiagnosis() == null ? null : os.getOccupationDiagnosis();
                        FxDetectionzy detzy = new FxDetectionzy(id
                                , os.getJbmc()== null ? null : os.getJbmc()
                                , os.getCount()
                                , os.getJcjl() == null ? null : os.getJcjl()
                                , jh
                                , os.getHarmName() == null ? null : os.getHarmName()
                                , os.getOccupationDiagnosis() == null ? null : harmNumMap.get(jh));
                        detzys.add(detzy);
                    }
                    /**检查情况汇总*/
                    List<FindJcqkhzDto> findJcqkhzList = this.ballCheckReportMapper.findJcqkhz(id);
                    if (CollectionUtils.isNotEmpty(patientCodes)){
                        // 查询老系统数据再合并
                        List<FindJcqkhzDto> findJcqkhzList2 = orPeispatientService.findJcqkhz(new ArrayList<>(patientCodes));
                        for (FindJcqkhzDto orPeispatient : findJcqkhzList2) {
                            Boolean flag = true;
                            for (FindJcqkhzDto findXmcjDto : findJcqkhzList) {
                                if (findXmcjDto.getHarmName().equals(orPeispatient.getHarmName())
                                        && StringUtils.isNotEmpty(findXmcjDto.getOrgDepart())
                                        && findXmcjDto.getOrgDepart().equals(orPeispatient.getOrgDepart())) {
                                    flag = false;
                                    findXmcjDto.setYszyb(findXmcjDto.getYszyb() + orPeispatient.getYszyb());
                                    findXmcjDto.setZyjjz(findXmcjDto.getZyjjz() + orPeispatient.getZyjjz());
                                    findXmcjDto.setFc(findXmcjDto.getFc() + orPeispatient.getFc());
                                    findXmcjDto.setQtjb(findXmcjDto.getQtjb() + orPeispatient.getQtjb());
                                    findXmcjDto.setWjyc(findXmcjDto.getWjyc() + orPeispatient.getWjyc());
                                    break;
                                }
                            }
                            if (flag) {
                                // 如果没有找到就添加
                                findJcqkhzList.add(orPeispatient);
                            }
                        }
                    }
                    for (FindJcqkhzDto os : findJcqkhzList) {
                        FxSummary sum = new FxSummary(id
                                , os.getRegimentationNote()
                                , os.getHarmName() == null ? null : os.getHarmName()
                                , os.getOccupationDiagnosis() == null ? null : os.getOccupationDiagnosis()
                                , os.getOrgDepart() == null ? null : os.getOrgDepart()
                                , os.getYszyb()
                                , os.getZyjjz()
                                , os.getFc()
                                , os.getQtjb()
                                , os.getWjyc()
                                , os.getAshclass() == null ? null : os.getAshclass()
                                , os.getHcclass() == null ? null : os.getHcclass());
                        sums.add(sum);
                    }
                }
            }
            this.fxCompletionService.saveBatch(completions);
            this.danagerObjectService.saveBatch(danagers);
            this.fxPersonnelviewService.saveBatch(persons);
            this.fxReviewsituationService.saveBatch(reviews);
            this.fxItemscheckService.saveBatch(checks);
            this.fxDetectionService.saveBatch(dets);
            this.fxPositiveService.saveBatch(poss);
            this.fxNegativeService.saveBatch(unposs);
            // TODO: 2023/1/13 啥都没改，插入干啥
//            this.saveBatch(bcrs);
            this.fxSummaryService.saveBatch(sums);
            this.fxDetectionzyService.saveBatch(detzys);
            this.fxReviewInfoService.saveBatch(ris);
            log.info("生成样本成功:" + ids);
            return "success";
        }
    }

    /**
     * 查询复查数量
     * @param patientcode
     * @return
     */
    private int findNumByPatientcode(String patientcode,List<String> patientcodes) {
        int num = 0;
        if (patientcodes.contains(patientcode)){
            //老系统 = 老系统 + 新系统
            num = orPeispatientService.findNumByPatientcode(patientcode)
                    + ballCheckReportMapper.findNumByPatientcode(patientcode);
        }else {
            num = ballCheckReportMapper.findNumByPatientcode(patientcode);
        }
        return num;
    }

    /**
     * 保存文件至本地
     */
    public String saveFile(String parentPath, String img64) throws IOException {
        OutputStream out = null;
        String path = null;
        try {
            byte[] b = new BASE64Decoder().decodeBuffer(img64.split(",")[1]);
            String name = UUID.randomUUID().toString().replaceAll("-", "") + ".png";
            out = new FileOutputStream(new File(parentPath, name));
            out.write(b);
            out.flush();
            path = parentPath + "\\" + name;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return path;
    }

    /**
     * 获取配置文件里的配置 String property 配置文件名称 String name 需要获取的配置参数名称 return String
     * 配置参数
     **/
    public static String getPropert(String property, String name) {
        String url = "";
        try {
            ClassLoader cls = Thread.currentThread().getContextClassLoader();
            InputStream in = cls.getResourceAsStream(property);
            Properties p = new Properties();

            p.load(in);
            if (p.containsKey(name)) {
                url = p.getProperty(name);
            }
        } catch (Exception e) {
        }

        return url;
    }


    /**
     * 修正
     *
     * @param ids
     * @return
     */
    @Override
    public Boolean undo(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new ServiceException("请选择记录！");
        }

        for (String id : ids) {
            //团检报告主表
            BallCheckReport bcr = ballCheckReportMapper.getInfoById(id);

            if (ObjectUtils.isEmpty(bcr)) {
                throw new ServiceException("数据已不存在，请刷新重试！");
            }
            //是否已打印：0.否 1.是
            int isPrint = bcr.getIsPrint() - 1;
            if (isPrint < 0) {
                isPrint = 0;
            }
            bcr.setIsPrint(isPrint);
            //更新
            ballCheckReportMapper.updateById(bcr);
        }
        return Boolean.TRUE;
    }

    /**
     * 主检审核
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mainAudit(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException("请选择一条记录");
        }
        BallCheckReport br = ballCheckReportMapper.getInfoById(id);
        if (ObjectUtils.isEmpty(br)) {
            throw new ServiceException("样本不存在，请刷新重试！");
        }
        //报告状态，报告状态：0.草稿 1.提交 2.通过 3.不通过 4.撤回 5.主检未审 6.主检开审 7.主检已审
        if (br.getStatus() != null && br.getStatus() == 5) {
            br.setStatus(7);
            ballCheckReportMapper.updateById(br);
        } else {
            throw new ServiceException("所选记录状态已改变，请刷新重试！");
        }
        //报告内容
        ReportContent reportContent = reportContentMapper.getInfoByAnalyzeId(id);
        if (ObjectUtils.isEmpty(reportContent)){
            throw new ServiceException("请先生成报告后再操作!");
        }
        reportContent.setCheckStatus(1);
        reportContentMapper.updateById(reportContent);
        return Boolean.TRUE;
    }


    /**
     * 参检分析
     *
     * @param reportId
     * @param groupId
     * @return
     */
    @Override
    public List<FxItemscheck> examanalyze(String reportId, String groupId) {
        List<FxItemscheck> list = fxItemscheckService.list(new QueryWrapper<FxItemscheck>()
                .orderByAsc("rowno").eq("sample_id", reportId));
        return list;
    }

    /**
     * 检出统计
     *
     * @param reportId
     * @param groupId
     * @return
     */
    @Override
    public IPage<CheckanalyzeVo> checkanalyze(PageParam<Report> page,String reportId, String groupId) {
        return fxDetectionMapper.checkanalyze(page,reportId);
    }


    /**
     * 异常前十 检出统计
     *
     * @param reportId
     * @param groupId
     * @return
     */
    @Override
    public HashMap loadcheckanalyze(String reportId, String groupId) {
        List boy = new ArrayList();
        List girl = new ArrayList();
        List total = new ArrayList();

        List<FxDetection> answer1 = fxDetectionMapper.selectList(new QueryWrapper<FxDetection>()
                .eq("sample_id", reportId)
                .ne("conclusion", "未见异常")
                .groupBy("basconclusion_id")
                .orderByDesc("detection_male")
        );

        List<FxDetection> answer2 = fxDetectionMapper.selectList(new QueryWrapper<FxDetection>()
                .eq("sample_id", reportId)
                .ne("conclusion", "未见异常")
                .groupBy("basconclusion_id")
                .orderByDesc("detection_female")
        );

        List<FxDetection> answer3 = fxDetectionMapper.selectList(new QueryWrapper<FxDetection>()
                .eq("sample_id", reportId)
                .ne("conclusion", "未见异常")
                .groupBy("basconclusion_id")
                .orderByDesc("detection_total")
        );
        if (answer1 != null && answer1.size() > 0) {
            try {
                for (int i = 0; i < answer1.size(); i++) {
                    if (i < 10) {
                        HashMap map = new HashMap();
                        FxDetection fxDetection = answer1.get(i);
                        map.put("name", fxDetection.getConclusion());
                        map.put("value", fxDetection.getDetectionMale());
                        boy.add(map);
                    } else {
                        break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (answer2 != null && answer2.size() > 0) {
            try {
                for (int i = 0; i < answer2.size(); i++) {
                    if (i < 10) {
                        HashMap map = new HashMap();
                        FxDetection fxDetection = answer2.get(i);
                        map.put("name", fxDetection.getConclusion());
                        map.put("value", fxDetection.getDetectionFemale());
                        girl.add(map);
                    } else {
                        break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (answer3 != null && answer3.size() > 0) {
            try {
                for (int i = 0; i < answer3.size(); i++) {
                    if (i < 10) {
                        HashMap map = new HashMap();
                        FxDetection fxDetection = answer3.get(i);
                        map.put("name", fxDetection.getConclusion());
                        map.put("value", fxDetection.getDetectionTotal());
                        total.add(map);
                    } else {
                        break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        HashMap map = new HashMap();

        map.put("boy", boy);
        map.put("girl", girl);
        map.put("total", total);
        return map;
    }


    /**
     * 导出阴性和阳性结果接口
     *
     * @param reportId
     * @return
     */
    @Override
    public List<RAExportDataVo> exportData(String reportId) {
        return ballCheckReportMapper.exportData(reportId);
    }
}

