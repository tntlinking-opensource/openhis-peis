package com.center.medical.report.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.*;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.GetLungVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.bean.vo.ThirdPartyImagesVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.BasMerge;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.data.bean.model.Patienttype;
import com.center.medical.data.bean.vo.BasconclusionVo;
import com.center.medical.data.dao.PatienttypeMapper;
import com.center.medical.data.service.BasMergeConclusionService;
import com.center.medical.data.service.BasMergeService;
import com.center.medical.data.service.BasconclusionService;
import com.center.medical.reception.service.ReviewService;
import com.center.medical.report.bean.model.SectionTotal;
import com.center.medical.report.bean.model.TotalVerdict;
import com.center.medical.report.bean.param.*;
import com.center.medical.report.bean.vo.*;
import com.center.medical.report.service.*;
import com.center.medical.service.*;
import com.center.medical.system.bean.vo.DeptSimpleVo;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 总检管理-健康总检(Peispatientfeeitem)表控制层
 *
 * @author ay
 * @since 2022-12-19 17:23:15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "总检管理-健康总检")
@RequestMapping("total/health")
public class TotalHealthInspectionController extends BaseController {
    /**
     * 服务对象
     */
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final MapperFacade mapperFacade;
    private final ISysBranchService iSysBranchService;
    private final PeispatientService peispatientService;
    private final TotalHealthInspectionService totalHealthInspectionService;
    private final BasMergeConclusionService basMergeConclusionService;
    private final SectionTotalService sectionTotalService;
    private final ReportService reportService;
    private final TotalVerdictService totalVerdictService;
    private final BasconclusionService basconclusionService;
    private final BasMergeService basMergeService;
    private final ISysDeptService sysDeptService;
    private final ReviewService reviewService;
    private final DiseaseTotalService diseaseTotalService;
    private final PatienttypeMapper patienttypeMapper;
    private final AttachmentService attachmentService;
    private final AttachmentConfigService attachmentConfigService;
    private final SectionResultMainService sectionResultMainService;
    private final LungService lungsService;


    /**
     * 【总检管理-健康总检】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【总检管理-健康总检】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/total/health/page", "10.总检-报告系统->总检管理-健康总检->分页查询", null),
                new InterfaceVo("健康和职业总检保存", "POST", "/total/health/saveOrUpdate", "10.总检-报告系统->总检管理-健康总检->健康和职业总检保存", null),
                new InterfaceVo("进入健康总检", "GET", "/total/health/gototal", "10.总检-报告系统->总检管理-健康总检->进入健康总检", null),
                new InterfaceVo("锁定/解锁", "PUT", "/total/health/lock", "10.总检-报告系统->总检管理-健康总检->锁定/解锁", null),
                new InterfaceVo("获取健康总检结论词列表数据", "GET", "/total/health/getVerdictListData", "10.总检-报告系统->总检管理-健康总检->获取健康总检结论词列表数据", null),
                new InterfaceVo("结论词下拉", "GET", "/total/health/getConclusionListData", "10.总检-报告系统->总检管理-健康总检->结论词下拉", null),
                new InterfaceVo("获取合并结论词", "GET", "/total/health/getMerge", "10.总检-报告系统->总检管理-健康总检->获取合并结论词", null),
                new InterfaceVo("拆分获取建议", "GET", "/total/health/getBreakUp", "10.总检-报告系统->总检管理-健康总检->拆分获取建议", null),
                new InterfaceVo("健康同步", "POST", "/total/health/synchronize", "10.总检-报告系统->总检管理-健康总检->健康同步", null),
                new InterfaceVo("获取历史数据", "GET", "/total/health/getHistoryData", "10.总检-报告系统->总检管理-健康总检->获取历史数据", null),
                new InterfaceVo("是否可以总检", "GET", "/total/health/canTotal", "10.总检-报告系统->总检管理-健康总检->是否可以总检", null),
                new InterfaceVo("存入词库", "POST", "/total/health/maintain", "10.总检-报告系统->总检管理-健康总检->存入词库", null),
                new InterfaceVo("获取科室", "GET", "/total/health/getKs", "10.总检-报告系统->总检管理-健康总检->获取科室", null),
                new InterfaceVo("获取合并的名称", "GET", "/total/health/getMergeConbination", "10.总检-报告系统->总检管理-健康总检->获取合并的名称", null),
                new InterfaceVo("打开科室小结界面", "GET", "/total/health/verdict", "10.总检-报告系统->总检管理-健康总检->打开科室小结界面", null),
                new InterfaceVo("打开分科检验界面", "GET", "/total/health/test", "10.总检-报告系统->总检管理-健康总检->打开分科检验界面", null),
                new InterfaceVo("打开复查通知界面", "GET", "/total/health/review", "10.总检-报告系统->总检管理-健康总检->打开复查通知界面", null),
                new InterfaceVo("打开分科普通界面", "GET", "/total/health/common", "10.总检-报告系统->总检管理-健康总检->打开分科普通界面", null),
                new InterfaceVo("删除总检结论词表的指定ID的数据", "DELETE", "/total/health/removeRow/{ids}", "10.总检-报告系统->总检管理-健康总检->删除总检结论词表的指定ID的数据", null),
                new InterfaceVo("同步总检结论词表", "POST", "/total/health/synchronization", "10.总检-报告系统->总检管理-健康总检->同步总检结论词表", null),
                new InterfaceVo("获取该体检者的体证词与科室", "GET", "/total/health/getSign", "10.总检-报告系统->总检管理-健康总检->获取该体检者的体证词与科室", null),
                new InterfaceVo("完成", "POST", "/total/health/finish", "10.总检-报告系统->总检管理-健康总检->完成", null),
                new InterfaceVo("未完成", "POST", "/total/health/unfinish", "10.总检-报告系统->总检管理-健康总检->未完成", null),
                new InterfaceVo("追加结论词和健康建议", "POST", "/total/health/appendSign", "10.总检-报告系统->总检管理-健康总检->追加结论词和健康建议", null),
                new InterfaceVo("选择结论词之后更改科室ID", "GET", "/total/health/changeDivice", "10.总检-报告系统->总检管理-健康总检->选择结论词之后更改科室ID", null),
                new InterfaceVo("选择结论词之后更改科室名字", "GET", "/total/health/changeDepName", "10.总检-报告系统->总检管理-健康总检->选择结论词之后更改科室名字", null),
                new InterfaceVo("保存追加的总检建议", "POST", "/total/health/commitSign", "10.总检-报告系统->总检管理-健康总检->保存追加的总检建议", null),
                new InterfaceVo("跳总检界面判断体检号是否存在", "GET", "/total/health/checkExamnumber", "10.总检-报告系统->总检管理-健康总检->跳总检界面判断体检号是否存在", null),
                new InterfaceVo("科室小结界面数据", "GET", "/total/health/getVerdictData", "10.总检-报告系统->总检管理-健康总检->科室小结界面数据", null),
                new InterfaceVo("分科普通界面数据", "GET", "/total/health/getCommonData", "10.总检-报告系统->总检管理-健康总检->分科普通界面数据", null),
                new InterfaceVo("获取体检项目和收费项目和小结", "GET", "/total/health/getInspectChargeListData", "10.总检-报告系统->总检管理-健康总检->获取体检项目和收费项目和小结", null),
                new InterfaceVo("获取该体检者所有收费项目", "GET", "/total/health/getItemByPeople", "10.总检-报告系统->总检管理-健康总检->获取该体检者所有收费项目", null),
                new InterfaceVo("根据体检号获取该体检者所有收费项目右侧", "GET", "/total/health/getRightItemByPeople", "10.总检-报告系统->总检管理-健康总检->根据体检号获取该体检者所有收费项目右侧", null),
                new InterfaceVo("保存更新复查通知单", "POST", "/total/health/saveReview", "10.总检-报告系统->总检管理-健康总检->保存更新复查通知单", null),
                new InterfaceVo("保存职业处理意见", "POST", "/total/health/saveTreatment", "10.总检-报告系统->总检管理-健康总检->保存职业处理意见", null),
                new InterfaceVo("保存综述结论建议职业和健康共用", "POST", "/total/health/saveSign", "10.总检-报告系统->总检管理-健康总检->保存综述结论建议职业和健康共用", null),
                new InterfaceVo("加载总检结论词表", "GET", "/total/health/loadSaveSign", "10.总检-报告系统->总检管理-健康总检->加载总检结论词表", null)
        );
        return R.ok(new FunctionVo("10.总检-报告系统", "总检管理-健康总检", interfaceVos, "10.总检/报告系统"));
    }

    /**
     * 健康检查总检增加页数据
     *
     * @param patientCode
     * @param flag
     * @return
     */
    @RepeatSubmit(interval = 3000,message = "不允许重复提交，请稍候再试")
    @GetMapping("/gototalAdd")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "健康检查总检增加页", notes = "健康检查总检增加页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检码"),
            @ApiImplicitParam(name = "flag", value = "是否补0，true是，false否")
    })
    public R gototalAdd(String patientCode, String flag) {

        Map<String, Object> data = new HashMap<>();
        //补0
        if ("true".equals(flag)) {
            patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        }
        // 获取当前体检号人员信息
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode));
        if (ObjectUtils.isNotEmpty(peispatient)) {
            data.put("age", peispatient.getAge() == null ? "" : peispatient.getAge().intValue());
            //总检锁定状态,开单医生不是当前登录人的话
            if (peispatient.getFFinallocked() != null
                    && peispatient.getFFinallocked() == 1
                    && peispatient.getIdDoctorapply() != null
                    && !peispatient.getIdDoctorapply().equals(
                    SecurityUtils.getUsername())) {
                data.put("islockman", 0);
                data.put("msg", "该记录由" + peispatient.getIdDoctorapply() + "医师锁定!");
            } else {
                if (ObjectUtils.isEmpty(peispatient.getJktjzt())) {
                    // 总检状态开始
                    peispatient.setJktjzt(0);
                }
                // 总检锁定: 1.锁定 0.解锁 暂定
                peispatient.setFFinallocked(1);
                // 锁定人
                peispatient.setIdDoctorapply(SecurityUtils.getUsername());
                // 总检时间
                peispatient.setDatefinalexamed(new java.sql.Date(new Date()
                        .getTime()));
                peispatientService.updateById(peispatient);
            }
            // 总检主表
            SectionTotal st = sectionTotalService.getOne(new QueryWrapper<SectionTotal>()
                    .eq("patientcode", patientCode).eq("disease_health", 0));
            if (ObjectUtils.isEmpty(st)) {
                st = new SectionTotal();
                st.setPatientcode(patientCode);// 体检号
                st.setDoctorId(SecurityUtils.getUserNo());// 总检医师
                st.setTotalTime(new Date());// 总检时间
                st.setWriteId(SecurityUtils.getUserNo());// 录入人
                st.setWriteTime(new Date());// 录入时间
                st.setDiseaseHealth(0);// 健康
                sectionTotalService.save(st);
                // 获取综述内容-
                Map<String, String> map = diseaseTotalService.getThreeData(patientCode);
                st.setSummarize(map.get("summary"));// 综述
                st.setVerdict(map.get("conclusion"));// 结论
                st.setOffer(map.get("advice"));// 建议
                st.setIsDelete(0);// 删除状态 0:不删
                sectionTotalService.updateById(st);
                //第一次进总检时生成结伦词
                totalHealthInspectionService.loadSaveSign(patientCode, "0");
                // 获取结论（所有结论词）--获取建议
                data.put("conclusion", map.get("conclusion"));
                data.put("advice", map.get("advice"));
                data.put("summary", map.get("summary"));
                data.put("evertotal", 1);//未总检过
            } else {
                data.put("evertotal", 0);
                data.put("conclusion", st.getVerdict());
                data.put("advice", st.getOffer());
                data.put("summary", st.getSummarize());
            }
            data.put("peispatient", peispatient);
            data.put("totalfinish", peispatient.getJktjzt());
            data.put("age", (int) Math.floor(peispatient.getAge()));

            SysDept sysDept = sysDeptService.selectDeptById(SecurityUtils.getDeptId());
            // 获取当前登录用户的部门id
            data.put("dpnoid", sysDept.getDeptNo());
            //部门名称
            data.put("dpno", sysDept.getDeptName());

            data.put("isVIP", getIdPatientClass(peispatient));
            return R.ok(data);
        }
        throw new ServiceException("体检号不存在！");
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
     * 获取分科检验数据
     *
     * @param pageParamSimple
     * @param patientCode
     * @param ksId
     * @return
     */
    @GetMapping("/getgriddata")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取分科检验数据", notes = "获取分科检验数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检码"),
            @ApiImplicitParam(name = "ksId", value = "科室id")
    })
    public R<IPage<GetGriddataVo>> getGriddata(PageParamSimple pageParamSimple, String patientCode, String ksId) {
        PageParam<GetGriddataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<GetGriddataVo> iPage = totalHealthInspectionService.getGriddata(page, patientCode, ksId);
        return R.ok(iPage);
    }


    /**
     * 获取提醒接口
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getRemindPatient")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取提醒接口", notes = "获取提醒接口")
    public R<IPage<RemindPatientVo>> getRemindPatient(PageParamSimple pageParamSimple, RemindPatientParam param) {
        PageParam<RemindPatientVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<RemindPatientVo> iPage = totalHealthInspectionService.getRemindPatient(page, param);
        return R.ok(iPage);
    }


    /**
     * 获取登录用户姓名
     *
     * @return
     */
    @GetMapping("/getLoginName")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取登录用户姓名", notes = "获取登录用户姓名")
    public R getLoginName() {
        String username = SecurityUtils.getUsername();
        return R.ok(username);
    }


    /**
     * 读卡
     *
     * @param autoFill
     * @param patientCode
     * @param ksId
     * @return
     */
    @GetMapping("/search")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "读卡", notes = "读卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "autoFill", value = "是否补0，true是false否"),
            @ApiImplicitParam(name = "patientCode", value = "体检码"),
            @ApiImplicitParam(name = "ksId", value = "科室id")
    })
    public R search(String autoFill, String patientCode, String ksId) {
        //体检号补0
        if ("true".equals(autoFill)) {
            patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        } else {
            patientCode = patientCode.trim().toUpperCase();
        }
        HashMap<String, Object> map = totalHealthInspectionService.search(patientCode, ksId);
        return R.ok(map);
    }


    /**
     * 分页获取待总检人员列表数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页获取待总检人员列表数据")
    public R<IPage<HtPeispatientVo>> getPage(PageParamSimple pageParamSimple, HealthTotalParam param) {
        PageParam<Peispatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.totalHealthInspectionService.getPage(page, param));
    }


    /**
     * 健康和职业总检保存
     *
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @RepeatSubmit(interval = 3000, message = "正在审核中，请稍等")
    @Log(title = "健康和职业总检审核", businessType = BusinessType.INSERT)
    @ApiOperation(value = "健康和职业总检保存", notes = "健康和职业总检保存")
    public R saveOrUpdate(@RequestBody TotalHealthSaOrUpParam param) {
        Map<String, String> result = totalHealthInspectionService.saOrUp(param);
        // TODO: 2023/1/17 添加成功后又调用了老系统的/report/health_report!createReview.action这个方法，但是没找到
//		String netUrl=ToolUtil.getPropert("doc_config.properties","create_ip")
//				+"/report/health_report!createReview.action";
        return R.ok(result);
    }


    /**
     * 进入健康总检
     *
     * @param patientno
     * @param flag
     * @return
     */
    @GetMapping("/gototal")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "进入健康总检", notes = "进入健康总检")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "flag", value = "体检号是否补0 ，true是false否")
    })
    public R gototal(String patientno, String flag) {
        Map<String, Object> map = new HashMap<>();
        String fpdf = "";
        String pdfUrl = "";
        SectionTotal sectionTotal = null;
        String username = SecurityUtils.getUsername();
        String patientCode = patientno;
        if (ObjectUtils.isNotEmpty(flag) && "true".equals(flag)) {
            patientCode = ToolUtil.patientCode(patientno, iSysBranchService.getBranchFlag(null));
        }
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientCode));// 获取当前体检号人员信息
        if (ObjectUtils.isEmpty(peispatient)) {
            throw new ServiceException("体检号不存在！");
        }
        //如果被锁定，且锁定人不是当前用户
        if (ObjectUtils.isNotEmpty(peispatient.getFFinallocked())
                && peispatient.getFFinallocked() == 1
                && ObjectUtils.isNotEmpty(peispatient.getIdDoctorapply())
                && !peispatient.getIdDoctorapply().equals(username)) {
            //是否锁定人
            String islockman = "0";
            map.put("islockman", islockman);
            map.put("msg","该记录由" + peispatient.getIdDoctorapply() + "医师锁定!");
        } else {
            if (ObjectUtils.isEmpty(peispatient.getJktjzt())) {
                peispatient.setJktjzt(0);// 总检状态开始
                totalHealthInspectionService.setScbs(peispatient, 0);
            }
            peispatient.setFFinallocked(1);// 总检锁定: 1.锁定 0.解锁 暂定
            peispatient.setIdDoctorapply(username);// 锁定人
            peispatientService.updateById(peispatient);
        }

        synchronized (basMergeConclusionService) {
            sectionTotal = sectionTotalService.getOne(new QueryWrapper<SectionTotal>()
                    .eq("patientcode", patientCode).eq("disease_health", 0));
            //如果是第一次总检  生成主表和子表
            if (ObjectUtils.isEmpty(sectionTotal)) {
                sectionTotal = totalHealthInspectionService.init(peispatient, 0);
            }
        }

        Report report = reportService.getOne(new QueryWrapper<Report>()
                .eq("patientcode", patientCode).eq("disease_health", 0).isNotNull("create_num"));
        if (ObjectUtils.isNotEmpty(report)) {
            //是否生成报告  1是
            fpdf = "1";
            pdfUrl = report.getUrlPdf();
        }
        String isVIP = ToolUtil.patientCode(patientno, iSysBranchService.getBranchFlag(null));
        String create_url = FileTypePath.CREATE_IP;
        Peispatient p = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", sectionTotal.getPatientcode()));
        Integer hasHistory = basMergeConclusionService.getHistory(sectionTotal.getId(), 0, p.getPatientarchiveno()).size() > 0 ? 1 : 0;
        map.put("pdfUrl", pdfUrl);
        map.put("sectionTotal", sectionTotal);
        map.put("isVIP", isVIP);
        map.put("create_url", create_url);
        map.put("peispatient", p);
        map.put("hasHistory", hasHistory);
        map.put("fpdf", fpdf);
        //小结
        List<SectionResultMain> mains = sectionResultMainService.list(new QueryWrapper<SectionResultMain>().eq("patientcode", patientCode));
        if (CollectionUtil.isNotEmpty(mains)) {
            //添加科室名称
            for (SectionResultMain main : mains) {
                SysDept sysDept = sysDeptService.getByDeptNo(main.getDepId());
                if (ObjectUtils.isNotEmpty(sysDept)) {
                    main.setDeptName(sysDept.getDeptName());
                }
            }
        }
        map.put("sectionResultMain", mains);
        return R.ok(map);
    }


    /**
     * 根据体检号锁定/解锁 改变总检状态
     *
     * @param ids   操作的对象主键id集合
     * @param state 操作标识：0.解锁 1.锁定
     * @return
     */
    @PutMapping("/lock")
    @ApiOperation(value = "锁定/解锁", notes = "根据体检号锁定/解锁 改变总检状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "要操作的对象体检号集合，多个以英文逗号隔开"),
            @ApiImplicitParam(name = "state", value = "操作标识：0.解锁 1.锁定")
    })
    public R<Boolean> lock(@RequestParam(name = "ids", required = true) List<String> ids, @RequestParam(name = "state", required = true) Integer state) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请选择要操作的对象");
        }
        return R.ok(totalHealthInspectionService.lock(ids, state));
    }


    /**
     * 获取健康总检结论词列表数据
     *
     * @param patientno
     * @param dh
     * @return
     */
    @GetMapping("/getVerdictListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取健康总检结论词列表数据", notes = "获取健康总检结论词列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "dh", value = "健康0 职业1")
    })
    public R<List<TotalVerdictVo>> getVerdictListData(String patientno, String dh) {
        //补全体检号
        patientno = ToolUtil.patientCode(patientno, iSysBranchService.getBranchFlag(null));
        List<TotalVerdictVo> list = totalVerdictService.getVerdictListData(patientno, dh);
        return R.ok(list);
    }


    /**
     * 健康+职业 结论词下拉
     *
     * @param pageParamSimple
     * @param srm
     * @return
     */
    @GetMapping("/getConclusionListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "健康+职业 结论词下拉", notes = "健康+职业 结论词下拉")
    @ApiImplicitParam(name = "srm", value = "输入码，就是老系统的key")
    public R<IPage<BasconclusionVo>> getConclusionListData(PageParamSimple pageParamSimple, String srm) {
        PageParam<BasconclusionVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<BasconclusionVo> ipage = basconclusionService.getConclusionListData(page, srm);
        return R.ok(ipage);
    }


    /**
     * 健康+职业 获取合并结论词
     *
     * @param ids
     * @return
     */
    @GetMapping("/getMerge")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "健康+职业 获取合并结论词", notes = "健康+职业 获取合并结论词")
    @ApiImplicitParam(name = "ids", value = "多个id，用,分割")
    public R getMerge(String ids) {
        Map<String, String> result = new HashMap<String, String>();
        if (StringUtils.isEmpty(ids)) {
            throw new ServiceException("error@请选择结论词");
        } else {
            BasMerge merge = basMergeService.getMergeByCon(ids.split(","), null);
            if (ObjectUtils.isEmpty(merge)) {
                throw new ServiceException("error@没有找到由所选结论词组成的合并结论词");
            } else {
                result.put("mergeId", merge.getId());
                result.put("mergeName", merge.getMergeName());
                result.put("suggestion", merge.getSuggestion());
                result.put("tjjy", merge.getTjjy());
            }
        }
        return R.ok(result);
    }


    /**
     * 健康+职业  拆分获取建议
     *
     * @param ids
     * @return
     */
    @GetMapping("/getBreakUp")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "健康+职业  拆分 获取建议", notes = "健康+职业  拆分 获取建议")
    @ApiImplicitParam(name = "ids", value = "多个id，用,分割")
    public R getBreakUp(String ids) {
        Map<String, String> result = new HashMap<String, String>();
        if (StringUtils.isEmpty(ids)) {
            throw new ServiceException("请选择结论词");
        } else {
            String[] arr = ids.split(",");
            List<Basconclusion> cons = basconclusionService.list(new QueryWrapper<Basconclusion>().in("id", arr));
            for (Basconclusion con : cons) {
                result.put(con.getId(), con.getSuggestion());
            }
        }
        return R.ok(result);
    }


    /**
     * 健康同步
     *
     * @return
     */
    @PostMapping("/synchronize")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "健康同步", notes = "健康同步")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "dh", value = "健康0 职业1")
    })
    public R synchronize(String patientno, int dh) {
        Boolean b = totalHealthInspectionService.synchronize(patientno, dh);
        return R.toResult(b);
    }


    /**
     * 获取历史数据
     *
     * @param id
     * @param dh
     * @param pageParamSimple
     * @return
     */
    @GetMapping("/getHistoryData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取历史数据", notes = "获取历史数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id"),
            @ApiImplicitParam(name = "dh", value = "健康0 职业1")
    })
    public R<IPage<STHistoryVo>> getHistoryData(PageParamSimple pageParamSimple, String id, String dh) {
        PageParam<STHistoryVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<STHistoryVo> iPage = sectionTotalService.getHistoryData(page, id, dh);
        return R.ok(iPage);
    }


    /**
     * 是否可以总检
     *
     * @param patientno
     * @param dh
     * @param flag
     * @return
     */
    @GetMapping("/canTotal")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "是否可以总检", notes = "是否可以总检")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "dh", value = "健康0 职业1"),
            @ApiImplicitParam(name = "flag", value = "体检号补0标识，true是，false否")
    })
    public R canTotal(String patientno, int dh, String flag) {
        Boolean b = totalHealthInspectionService.canTotal(patientno, dh, flag);
        return R.toResult(b);
    }


    /**
     * 存入词库（线下总检）
     *
     * @param param
     * @return
     */
    @PostMapping("/maintain")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "存入词库（线下总检）", notes = "存入词库（线下总检）")
    public R maintain(@RequestBody MaintainParam param) {
        String id = totalHealthInspectionService.maintain(param);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", id);
        result.put("creater", SecurityUtils.getUsername());
        return R.ok(result);
    }


    /**
     * 获取科室
     *
     * @param srm
     * @return
     */
    @GetMapping("/getKs")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取科室", notes = "获取科室")
    @ApiImplicitParam(name = "srm", value = "输入码，就是老系统的key")
    public R<List<DeptSimpleVo>> getKs(String srm) {
        List<DeptSimpleVo> list = sysDeptService.getKs(srm);
        return R.ok(list);
    }


    /**
     * 获取合并的名称
     *
     * @param id
     * @return
     */
    @GetMapping("/getMergeConbination")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取合并的名称", notes = "获取合并的名称")
    @ApiImplicitParam(name = "id", value = "id")
    public R getMergeConbination(String id) {
        List<String> s = basMergeConclusionService.getMergeConbination(id);
        return R.ok(s);
    }


    /**
     * 打开科室小结界面
     *
     * @param patientno
     * @return
     */
    @GetMapping("/verdict")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "打开科室小结界面", notes = "打开科室小结界面，获取对应的体检者表数据")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R<Peispatient> verdict(String patientno) {
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", patientno));
        return R.ok(peispatient);
    }


    /**
     * 打开健康总检详情页面
     *
     * @param patientno
     * @return
     */
    @GetMapping("/test")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "打开健康总检详情页面", notes = "打开健康总检详情页面")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R test(String patientno) {
        //返回map
        HashMap result = new HashMap();
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", patientno));
        HashMap map = totalHealthInspectionService.getSectionTotal(patientno);
        result.put("peispatient", peispatient);
        result.put("map", map);
        return R.ok(result);
    }


    /**
     * 打开复查通知界面
     *
     * @param patientno
     * @return
     */
    @GetMapping("/review")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "打开复查通知界面", notes = "打开复查通知界面")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R review(String patientno) {
        //返回map
        HashMap result = new HashMap();
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", patientno));
        result.put("peispatient", peispatient);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(peispatient.getMedicaldate());
        } catch (Exception e) {
            throw new ServiceException("体检日期为空！");
        }
        // 体检日期加一个月
        cal.add(Calendar.MONTH, 1);
        result.put("endTime", cal.getTime());
        result.put("age", (int) Math.floor(peispatient.getAge()));
        Review review = reviewService.getOne(new QueryWrapper<Review>().eq("patientcode", patientno));
        result.put("review", review);
        return R.ok(result);
    }


    /**
     * 打开分科-普通界面
     *
     * @param patientno
     * @return
     */
    @GetMapping("/common")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "打开分科-普通界面", notes = "打开分科-普通界面")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R<Peispatient> common(String patientno) {
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientno));
        return R.ok(peispatient);
    }


    /**
     * 删除总检结论词表的指定ID的数据
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/removeRow/{ids}")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "删除总检结论词表的指定ID的数据", notes = "删除总检结论词表的指定ID的数据")
    @ApiImplicitParam(name = "ids", value = "id的集合")
    @Log(title = "删除总检结论词表的指定ID的数据", businessType = BusinessType.DELETE)
    public R removeRow(@PathVariable List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请选择要删除的内容");
        }
        //删除
        boolean b = totalVerdictService.remove(new QueryWrapper<TotalVerdict>().in("id", ids));
        return R.toResult(b);
    }


    /**
     * 同步总检结论词表
     *
     * @param patientno
     * @return
     */
    @PostMapping("/synchronization")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "同步总检结论词表", notes = "同步总检结论词表")
    @ApiImplicitParam(name = "patientno", value = "体检码")
    public R synchronization(String patientno) {
        // TODO: 2022/12/27 同步未完成
        return R.ok();
    }


    /**
     * 获取该体检者所有的体证词与所产生体证词的科室
     *
     * @param patientno
     * @return
     */
    @GetMapping("/getSign")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取该体检者所有的体证词与所产生体证词的科室", notes = "获取该体检者所有的体证词与所产生体证词的科室")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R getSign(String patientno) {
        List<HashMap> list = totalHealthInspectionService.getSign(patientno);
        return R.ok(list);
    }


    /**
     * 完成
     *
     * @param param
     * @return
     */
    @PostMapping("/finish")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "完成", notes = "根据体检号 改变总检完成状态")
    public R finish(FinishParam param) {
        String patientno = param.getPatientno();
        //体检者表
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientno));
        if (ObjectUtils.isNotEmpty(peispatient)) {
            //锁定人不是当前用户
            if (ObjectUtils.isNotEmpty(peispatient.getIdDoctorapply())
                    && !peispatient.getIdDoctorapply().equals(
                    SecurityUtils.getUsername())) {
                throw new ServiceException("该记录由" + peispatient.getIdDoctorapply()
                        + "医师锁定,请等待该医师解锁!");
            }
            totalHealthInspectionService.saOrUpFinish(param);
            // 总检锁定: 1.锁定 0.解锁 暂定
            peispatient.setFFinallocked(1);
            peispatient.setJktjzt(1);//
            totalHealthInspectionService.setScbs(peispatient, 0);
            peispatientService.updateById(peispatient);
            return R.ok();
        }
        throw new ServiceException("不存在记录!");
    }


    /**
     * 未完成
     *
     * @param patientno
     * @return
     */
    @PostMapping("/unfinish")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "未完成", notes = "根据体检号 改变总检完成状态")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R unfinish(String patientno) {
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientno));

        if (ObjectUtils.isNotEmpty(peispatient)) {
            //总检状态
            Integer status = peispatient.getJktjzt();
            if (ObjectUtils.isEmpty(status)) {
                throw new ServiceException("该体检号的总检状态为空！");
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
            //锁定人不是当前登录用户
            if (ObjectUtils.isNotEmpty(peispatient.getIdDoctorapply())
                    && !peispatient.getIdDoctorapply().equals(
                    SecurityUtils.getUsername())) {
                throw new ServiceException("该记录由" + peispatient.getIdDoctorapply()
                        + "医师锁定,请等待该医师解锁!");
            }
            //报告
            Report report = reportService.getOne(new QueryWrapper<Report>().eq("patientcode", patientno)
                    .eq("disease_health", 0));
            report.setStatus(0);
            reportService.updateById(report);

            peispatient.setJktjzt(0);//
            totalHealthInspectionService.setScbs(peispatient, 0);
            peispatientService.updateById(peispatient);

            return R.ok();
        }
        throw new ServiceException("不存在记录!");
    }


    /**
     * 追加结论词和健康建议
     *
     * @param param
     * @return
     */
    @PostMapping("/appendSign")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "追加结论词和健康建议", notes = "追加结论词和健康建议")
    public R appendSign(@RequestBody AppendSignParam param) {
        HashMap map = totalHealthInspectionService.appendSign(param);
        return R.ok(map);

    }


    /**
     * 选择结论词之后更改科室ID
     *
     * @param conclusionId
     * @return
     */
    @GetMapping("/changeDivice")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "选择结论词之后更改科室ID", notes = "选择结论词之后更改科室ID")
    @ApiImplicitParam(name = "conclusionId", value = "结论词id")
    public R changeDivice(String conclusionId) {
        Basconclusion bas = basconclusionService.getInfoById(conclusionId);
        return R.ok(bas.getDivisionId());
    }


    /**
     * 选择结论词之后更改科室名字
     *
     * @param depId
     * @return
     */
    @GetMapping("/changeDepName")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "选择结论词之后更改科室名字", notes = "选择结论词之后更改科室名字")
    @ApiImplicitParam(name = "depId", value = "科室ID")
    public R changeDepName(String depId) {
        SysDept dep = sysDeptService.getByDeptNo(depId);
        return R.ok(dep.getDeptName());
    }


    /**
     * 保存追加的总检建议
     *
     * @param param
     * @return
     */
    @PostMapping("/commitSign")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "保存追加的总检建议", notes = "保存追加的总检建议")
    public R commitSign(CommitSignParam param) {
        Boolean b = totalHealthInspectionService.saveNameAndAdvice(param);
        return R.toResult(b);

    }


    /**
     * 跳总检界面需判断体检号是否存在,存在返回对应实体对象
     *
     * @param patientno
     * @return
     */
    @GetMapping("/checkExamnumber")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "跳总检界面需判断体检号是否存在", notes = "跳总检界面需判断体检号是否存在,存在返回对应实体对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "flag", value = "是否补0 ，true是false否"),
            @ApiImplicitParam(name = "dh", value = "健康0 职业1")
    })
    public R checkExamnumber(String patientno, String flag, Integer dh) {
        String patientCode = patientno;
        //体检号补0
        if (StringUtils.isNotEmpty(flag) && flag.equals("true")) {
            patientCode = ToolUtil.patientCode(patientno, iSysBranchService.getBranchFlag(null));
        }
        //体检者表
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientCode));
        if (ObjectUtils.isEmpty(peispatient)) {
            throw new ServiceException("体检号不存在！");
        }

        if (dh == 0) {
            if ("1".equals(peispatient.getIdExamtype())) {
                throw new ServiceException("此体检号的体检类型为职业体检，不能进健康总检！");
            }
            if ("3".equals(peispatient.getIdExamtype())) {
                throw new ServiceException("此体检号的体检类型为复查，不能进健康总检！");
            }
        } else {
            if ("0".equals(peispatient.getIdExamtype())) {
                throw new ServiceException("此体检号的体检类型为健康体检，不能进职业总检！");
            }
        }
        //分检未完成
        if (ObjectUtils.isEmpty(peispatient.getFReadytofinal()) || peispatient.getFReadytofinal() != 1) {
            // 列出所有未检查项目
            List<Peispatientfeeitem> list = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", patientCode).eq("f_examinated", 0)
                    .isNotNull("id_ks").isNull("f_transferedhl7").eq("f_giveup", 0)
                    .eq("sfjj", 0).eq("change_item", 0));

            StringBuilder unpaid = new StringBuilder();
            for (Peispatientfeeitem feeitem : list) {
                unpaid.append(feeitem.getExamfeeitemName());
                unpaid.append(",");
            }
            throw new ServiceException("分检未完成:" + unpaid.toString());
        }
        return R.ok();
    }


    /**
     * 科室小结界面数据
     *
     * @param patientno
     * @return
     */
    @GetMapping("/getVerdictData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "科室小结界面数据", notes = "科室小结 界面 数据")
    @ApiImplicitParam(name = "patientno", value = "体检码")
    public R<List<VerdictDataVo>> getVerdictData(String patientno) {
        List<VerdictDataVo> verdictData = diseaseTotalService.getVerdictData(patientno);
        return R.ok(verdictData);
    }


    /**
     * 分科普通界面数据
     *
     * @param patientno
     * @return
     */
    @GetMapping("/getCommonData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分科普通界面数据", notes = "科室小结 界面 数据")
    @ApiImplicitParam(name = "patientno", value = "体检码")
    public R<List<CommonDataVo>> getCommonData(String patientno) {
        List<CommonDataVo> list = diseaseTotalService.getCommonListData(patientno);
        return R.ok(list);
    }


    /**
     * 根据科室id和体检号获取体检项目和收费项目和小结
     *
     * @param patientno
     * @param sectionId
     * @return
     */
    @GetMapping("/getInspectChargeListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据科室id和体检号获取体检项目和收费项目和小结", notes = "返回的ver2，ver4，ver5，ver6，ver7,就是c1，c2，c3，c4，CommonSummary")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "sectionId", value = "科室id")
    })
    public R<List<VerdictDataVo>> getInspectChargeListData(String patientno, String sectionId) {
        List<VerdictDataVo> list = diseaseTotalService.getInspectChargeListData(patientno, sectionId);
        return R.ok(list);
    }

    /**
     * 根据体检号获取该体检者所有收费项目
     *
     * @param patientno
     * @return
     */
    @GetMapping("/getItemByPeople")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据体检号获取该体检者所有收费项目", notes = "根据体检号获取该体检者所有收费项目")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R getItemByPeople(String patientno) {
        List<HashMap> list = totalHealthInspectionService.getItemByPeople(patientno);
        return R.ok(list);
    }


    /**
     * 根据体检号获取该体检者所有收费项目 右侧
     *
     * @param patientno
     * @return
     */
    @GetMapping("/getRightItemByPeople")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据体检号获取该体检者所有收费项目 右侧", notes = "根据体检号获取该体检者所有收费项目 右侧")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R getRightItemByPeople(String patientno) {
        List<HashMap> list = totalHealthInspectionService.getRightItemByPeople(patientno);
        return R.ok(list);
    }


    /**
     * 保存更新复查通知单
     *
     * @param param
     * @return
     */
    @PostMapping("/saveReview")
    @ApiOperation(value = "保存更新复查通知单", notes = "保存更新复查通知单")
    public R saveReview(@RequestBody SaveReviewParam param) {
        String userId = SecurityUtils.getUserNo();
        param.setUserId(userId);
        Boolean b = totalHealthInspectionService.saveReview(param);
        return R.toResult(b);
    }


    /**
     * 保存职业处理意见
     *
     * @param param
     * @return
     */
    @PostMapping("/saveTreatment")
    @ApiOperation(value = "保存职业处理意见", notes = "保存职业处理意见")
    public R saveTreatment(SaveTreatmentParam param) {
        Boolean b = totalHealthInspectionService.saveTreatment(param);
        return R.toResult(b);
    }


    /**
     * 保存综述结论建议职业
     *
     * @param param
     * @return
     */
    @PostMapping("/saveSign")
    @ApiOperation(value = "保存综述结论建议职业和健康共用", notes = "保存综述 结论 建议 职业和健康共用")
    public R saveSign(@RequestBody SaveSignAllParam param) {
        Boolean b = diseaseTotalService.saveSign(param);
        return R.toResult(b);
    }


    /**
     * 加载 总检结论词表
     *
     * @param patientno
     * @param flag
     * @return
     */
    @GetMapping("/loadSaveSign")
    @ApiOperation(value = "加载总检结论词表", notes = "加载 总检结论词表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "flag", value = "0健康1职业")
    })
    public R loadSaveSign(String patientno, String flag) {
        List<HashMap> list = totalHealthInspectionService.loadSaveSign(patientno, flag);
        return R.ok(list);
    }


    /**
     * 总检查看科室图片
     *
     * @param patientno
     * @return
     */
    @GetMapping("/viewImgTotal")
    @ApiOperation(value = "总检查看科室图片", notes = "总检查看科室图片")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R viewImgTotal(String patientno) {
        LinkedHashMap<String, List<String>> totalImg = new LinkedHashMap<String, List<String>>();
        // 附件
        List<Attachment> attachments = attachmentService.list(new QueryWrapper<Attachment>()
                .orderByAsc("dep_id", "fee_item_id", "file_path")
                .eq("patientcode", patientno).in("file_type", new String[]{"科室图像", "ELE", "PACS", "PACSN"}));
        String fdepId = "";
        List<String> urls = null;
        //循环
        for (Attachment att : attachments) {
            String depId = att.getDepId();
            if (!depId.equals(fdepId)) {
                SysDept dept = sysDeptService.getByDeptNo(depId);
                if (dept == null) {
                    continue;
                }
                urls = new ArrayList<String>();
                totalImg.put(dept.getDeptName(), urls);
                fdepId = depId;
            }
            if ("PACS".equals(att.getFileType())) {
                // 去掉mec
                urls.add(ToolUtil.getPacsPath(att.getFilePath()));
            } else if ("PACSN".equals(att.getFileType())) {
                String path = getPath(att);
                urls.add(path);
            } else {
                urls.add(att.getFilePath());
            }
        }
        // 第三方报告
        List<ThirdPartyImagesVo> thirdPartyReport = attachmentService.getThirdPartyReport(patientno);
        if (CollectionUtil.isNotEmpty(thirdPartyReport)){
            Map<String, List<String>> result = thirdPartyReport.stream()
                    .collect(Collectors.groupingBy(
                            ThirdPartyImagesVo::getExamfeeitemName,
                            LinkedHashMap::new,
                            Collectors.mapping(ThirdPartyImagesVo::getFilePath, Collectors.toList())
                    ));
            totalImg.putAll(result);
        }
        // 排序，List<String> 数量少的排在上面
        LinkedHashMap<String, List<String>> sortedMap = new LinkedHashMap<>();
        totalImg.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparingInt(List::size)))
                .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));

        return R.ok(sortedMap);
    }

    public String getPath(Attachment att) {
        String configId = att.getConfigId();
        AttachmentConfig config = configId == null ? getLatestConfig() : attachmentConfigService.getInfoById(configId);
        return config.getVisitPath() + "/" + att.getFilePath();
    }

    public AttachmentConfig getLatestConfig() {
        List<AttachmentConfig> pacs = attachmentConfigService.list(new QueryWrapper<AttachmentConfig>().orderByDesc("flag"));
        return pacs.size() == 0 ? null : pacs.get(0);
    }



    /**
     * 获取肺功能数据
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getLung")
    @ApiOperation(value = "获取肺功能数据", notes = "获取肺功能数据")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R getLung(String patientcode) {
        List<GetLungVo> list = lungsService.getLung(patientcode);
        return R.ok(list);
    }
}

