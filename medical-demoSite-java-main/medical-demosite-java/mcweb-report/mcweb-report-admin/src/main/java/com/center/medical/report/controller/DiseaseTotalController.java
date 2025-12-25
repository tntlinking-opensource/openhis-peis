package com.center.medical.report.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.*;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.service.*;
import com.center.medical.abteilung.bean.model.ElectroAudiometer;
import com.center.medical.abteilung.service.ElectroAudiometerService;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.ReportConstants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.reception.bean.model.ReviewProject;
import com.center.medical.reception.service.ReviewProjectService;
import com.center.medical.reception.service.ReviewService;
import com.center.medical.report.bean.model.SectionTotal;
import com.center.medical.report.bean.param.*;
import com.center.medical.report.bean.vo.*;
import com.center.medical.report.service.*;
import com.center.medical.service.DrugDiseaseService;
import com.center.medical.service.LungService;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.bean.vo.DoctorDataVo;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysDeptService;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * 总检管理-职业总检控制层
 *
 * @author 路飞船长
 * @since 2022-12-07 18:51:27
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "总检管理-职业总检")
@RequestMapping("/total/disease")
public class DiseaseTotalController extends BaseController {
    /**
     * 服务对象
     */
    private final DiseaseTotalService diseaseTotalService;
    private final MapperFacade mapperFacade;
    private final ISysBranchService iSysBranchService;
    private final SectionTotalService sectionTotalService;
    private final ReportService reportService;
    private final PeispatientService peispatientService;
    private final ISysUserService sysUserService;
    private final ReviewService reviewService;
    private final HarmService harmService;
    private final ISysDeptService sysDeptService;
    private final PatienttypeService patienttypeService;
    private final ZySummaryService zySummaryService;
    private final DrugDiseaseService drugDiseaseService;
    private final CommentsProgessionalService commentsProgessionalService;
    private final LungService lungService;
    private final ElectroAudiometerService electroAudiometerService;
    private final PeisStateService peisStateService;
    private final BasMergeConclusionService basMergeConclusionService;
    private final TotalHealthInspectionService totalHealthInspectionService;
    private final SectionResultMainService sectionResultMainService;
    private final ReviewProjectService reviewProjectService;
    private final ItemsService itemsService;


    /**
     * 【总检管理-职业总检】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【总检管理-职业总检】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/total/disease/page", "10.总检-报告系统->总检管理-职业总检->分页查询", null),
                new InterfaceVo("打开科室小结界面", "GET", "/total/disease/verdict", "10.总检-报告系统->总检管理-职业总检->打开科室小结界面", null),
                new InterfaceVo("分科普通界面数据", "GET", "/total/disease/getCommonData", "10.总检-报告系统->总检管理-职业总检->分科普通界面数据", null),
                new InterfaceVo("打开分科检验界面", "GET", "/total/disease/test", "10.总检-报告系统->总检管理-职业总检->打开分科检验界面", null),
                new InterfaceVo("打开复查通知界面", "GET", "/total/disease/review", "10.总检-报告系统->总检管理-职业总检->打开复查通知界面", null),
                new InterfaceVo("打开分科-普通界面", "GET", "/total/disease/common", "10.总检-报告系统->总检管理-职业总检->打开分科-普通界面", null),
                new InterfaceVo("跳转到职业总检页面", "GET", "/total/disease/gototal", "10.总检-报告系统->总检管理-职业总检->跳转到职业总检页面", null),
                new InterfaceVo("职业总检结论词同步", "PUT", "/total/disease/synchronization", "10.总检-报告系统->总检管理-职业总检->职业总检结论词同步", null),
                new InterfaceVo("打开职业处理意见窗口", "GET", "/total/disease/treatmentsuggestion", "10.总检-报告系统->总检管理-职业总检->打开职业处理意见窗口", null),
                new InterfaceVo("打开职业处理意见增加窗口", "GET", "/total/disease/treatmentsuggestionadd", "10.总检-报告系统->总检管理-职业总检->打开职业处理意见增加窗口", null),
                new InterfaceVo("展示危害因素", "GET", "/total/disease/showJhys", "10.总检-报告系统->总检管理-职业总检->展示危害因素", null),
                new InterfaceVo("显示所有的职业病检查结论", "GET", "/total/disease/showZySummaryService", "10.总检-报告系统->总检管理-职业总检->显示所有的职业病检查结论", null),
                new InterfaceVo("显示所有的禁忌疾病", "GET", "/total/disease/showHealthEvaluation", "10.总检-报告系统->总检管理-职业总检->显示所有的禁忌疾病", null),
                new InterfaceVo("职业处理意见界面 条件搜索", "GET", "/total/disease/searchTreatmentsuggestion", "10.总检-报告系统->总检管理-职业总检->职业处理意见界面 条件搜索", null),
                new InterfaceVo("科室小结界面数据", "GET", "/total/disease/getVerdictData", "10.总检-报告系统->总检管理-职业总检->科室小结界面数据", null),
                new InterfaceVo("职业意见界面数据", "GET", "/total/disease/getTreatmentData", "10.总检-报告系统->总检管理-职业总检->职业意见界面数据", null),
                new InterfaceVo("删除某条职业处理意见", "DELETE", "/total/disease/removeRows/{ids}", "10.总检-报告系统->总检管理-职业总检->删除某条职业处理意见", null),
                new InterfaceVo("保存职业意见增加界面数据", "POST", "/total/disease/saveTreatment", "10.总检-报告系统->总检管理-职业总检->保存职业意见增加界面数据", null),
                new InterfaceVo("获取体检项目和收费项目和小结", "GET", "/total/disease/getInspectChargeListData", "10.总检-报告系统->总检管理-职业总检->获取体检项目和收费项目和小结", null),
                new InterfaceVo("保存职业意见增加界面数据", "POST", "/total/disease/saveOrUpdate", "10.总检-报告系统->总检管理-职业总检->保存职业意见增加界面数据", null),
                new InterfaceVo("跳总检界面需判断体检号是否存在", "GET", "/total/disease/checkExamnumber", "10.总检-报告系统->总检管理-职业总检->跳总检界面需判断体检号是否存在", null),
                new InterfaceVo("锁定", "PUT", "/total/disease/lock", "10.总检-报告系统->总检管理-职业总检->锁定", null),
                new InterfaceVo("解锁", "PUT", "/total/disease/unlock", "10.总检-报告系统->总检管理-职业总检->解锁", null),
                new InterfaceVo("完成", "POST", "/total/disease/finish", "10.总检-报告系统->总检管理-职业总检->完成", null),
                new InterfaceVo("未完成", "PUT", "/total/disease/unfinish", "10.总检-报告系统->总检管理-职业总检->未完成", null),
                new InterfaceVo("保存综述", "POST", "/total/disease/saveSign", "10.总检-报告系统->总检管理-职业总检->保存综述", null),
                new InterfaceVo("获取科室", "GET", "/total/disease/getSign", "10.总检-报告系统->总检管理-职业总检->获取科室", null),
                new InterfaceVo("查询出符合要求的结论词", "GET", "/total/disease/serchConclusion", "10.总检-报告系统->总检管理-职业总检->查询出符合要求的结论词", null),
                new InterfaceVo("分科肺功能", "GET", "/total/disease/lung", "10.总检-报告系统->总检管理-职业总检->分科肺功能", null),
                new InterfaceVo("分科电测听", "GET", "/total/disease/audio", "10.总检-报告系统->总检管理-职业总检->分科电测听", null)
        );
        return R.ok(new FunctionVo("10.总检-报告系统", "总检管理-职业总检", interfaceVos, "10.总检/报告系统"));
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
    public R<IPage<DtPeispatientVo>> getPage(PageParamSimple pageParamSimple, HealthTotalParam param) {
        PageParam<DtPeispatientVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        if (StringUtils.isNotEmpty(param.getAutoFill()) && "true".equals(param.getAutoFill())) {
            param.setPatientcode(ToolUtil.patientCode(param.getPatientcode(), iSysBranchService.getBranchFlag(null)));
        }
        return R.ok(this.diseaseTotalService.getPage(page, param));
    }


    /**
     * 打开科室小结界面
     *
     * @return 所有数据
     */
    @GetMapping("/verdict")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "打开科室小结界面", notes = "打开科室小结界面")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R verdict(String patientno) {
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientno));
        return R.ok(peispatient);
    }


    /**
     * 分科普通 界面 数据
     *
     * @param patientno
     * @return
     */
    @GetMapping("/getCommonData")
    @ApiOperation(value = "分科普通 界面 数据", notes = "分科普通 界面 数据")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R getCommonData(String patientno) {
        List<CommonDataVo> list = diseaseTotalService.getCommonListData(patientno);
        return R.ok(list);
    }


    /**
     * 打开分科检验界面
     *
     * @param patientno
     * @return
     */
    @GetMapping("/test")
    @ApiOperation(value = "打开分科检验界面", notes = "打开分科检验界面")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R test(String patientno) {
        //体检者表
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientno));
        //根据体检号获取所有总检对象 暂时取第一个
        SectionTotal sectionTotal = null;
        List<SectionTotal> list = sectionTotalService.list(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientno));
        if (CollectionUtil.isNotEmpty(list) && list.size() > 0) {
            sectionTotal = list.get(0);
        }
        //返回map
        HashMap result = new HashMap();
        if (ObjectUtils.isNotEmpty(sectionTotal)) {
            //录入人ID
            String writeId = sectionTotal.getWriteId();
            //总检医生ID
            String doctorId = sectionTotal.getDoctorId();
            //时间
            Date writeTime = sectionTotal.getWriteTime();
            Date totalTime = sectionTotal.getTotalTime();
            result.put("writeTime", writeTime);
            result.put("totalTime", totalTime);
            if (ObjectUtils.isNotEmpty(doctorId)) {
                SysUser doctorUser = sysUserService.selectUserByUserNo(doctorId);
                if (doctorUser != null) {
                    doctorId = doctorUser.getUserName();
                    result.put("doctorId", doctorId);
                }
            }
            if (ObjectUtils.isNotEmpty(writeId)) {
                SysUser writeUser = sysUserService.selectUserByUserNo(writeId);
                if (ObjectUtils.isNotEmpty(writeUser)) {
                    writeId = writeUser.getUserName();
                    result.put("writeId", writeId);
                }
            }
        }
        result.put("peispatient", peispatient);
        return R.ok(result);
    }


    /**
     * 打开复查通知界面
     *
     * @param patientno
     * @return
     */
    @GetMapping("/review")
    @ApiOperation(value = "打开复查通知界面", notes = "打开复查通知界面")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R review(String patientno) {
        //返回map
        HashMap result = new HashMap();
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientno));
        result.put("peispatient", peispatient);
        //设置体检时间
        Calendar cal = Calendar.getInstance();
        if (ObjectUtils.isEmpty(peispatient.getMedicaldate())) {
            throw new ServiceException("体检日期为空!");
        }
        cal.setTime(peispatient.getMedicaldate());
        // 体检日期加一个月
        cal.add(Calendar.MONTH, 1);
        SectionTotal st = sectionTotalService.getOne(new QueryWrapper<SectionTotal>()
                .eq("patientcode", patientno).eq("disease_health", 1));
        //总检时间
        if (ObjectUtils.isNotEmpty(st)) {
            result.put("totalTime", st.getTotalTime());
        }
        result.put("endTime", cal.getTime());
        result.put("picture", diseaseTotalService.getPicture(peispatient));
        result.put("age", (int) Math.floor(peispatient.getAge()));
        //复查表
        Review review = reviewService.getOne(new QueryWrapper<Review>()
                .eq("patientcode", patientno));
        if (ObjectUtils.isNotEmpty(review)) {
            result.put("review", review);
        }
        return R.ok(result);
    }


    /**
     * 打开分科-普通界面
     *
     * @param patientno
     * @return
     */
    @GetMapping("/common")
    @ApiOperation(value = "打开分科-普通界面", notes = "打开分科-普通界面")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R common(String patientno) {
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", patientno));
        return R.ok(peispatient);
    }


    /**
     * 跳转到职业总检页面
     *
     * @param patientno
     * @return
     */
    @GetMapping("/gototal")
    @ApiOperation(value = "跳转到职业总检页面", notes = "跳转到职业总检页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "flag", value = "是否补0 ，true是false否")
    })
    public R gototal(String patientno, String flag) {
        String pdfUrl = "";
        //返回map
        HashMap result = new HashMap();
        String username = SecurityUtils.getUsername();
        result.put("username", username);
        String patientCode = patientno;
        //体检号补0
        if (StringUtils.isNotEmpty(flag) && flag.equals("true")) {
            patientCode = ToolUtil.patientCode(patientno, iSysBranchService.getBranchFlag(null));
        }
        // 获取当前体检号人员信息
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientCode));

        if (ObjectUtils.isEmpty(peispatient)) {
            throw new ServiceException("error@体检号不存在！");
        }

        String harms = "";
        if (peispatient.getJhgl() == null) {
            harms = "体检类别:" + getMedicalType(peispatient.getMedicaltype()) + ";" + "接害工龄：" + " " + "年;";
        } else if (peispatient.getJhgl() == 0) {
            harms = "体检类别:" + getMedicalType(peispatient.getMedicaltype()) + ";" + "接害工龄：" + 0 + "年;";
        } else {
            float num = (float) (peispatient.getJhgl()) / 12;
            double harmsyears = new BigDecimal(num).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            harms = "体检类别:" + getMedicalType(peispatient.getMedicaltype()) + ";" + "接害工龄：" + harmsyears + "年;";
        }

        //接害因素
        String jhys = peispatient.getJhys();
        if (StringUtils.isNotEmpty(jhys)) {
            List<Harm> harmList = harmService.list(new QueryWrapper<Harm>().in("id", jhys.split(",")));
            if (harmList.size() > 0) {
                harms = harms + "接害因素:";
                for (Harm harm : harmList) {
                    harms = harms + harm.getHarmName() + ",";
                }
                harms = harms.substring(0, harms.length() - 1);
            }
            if (harms.length() > 0) {
                result.put("harms", harms);
            }
        }

        //职业锁定完成时，职业锁定人不是当前用户时
        if (peispatient.getIdGuidenurse() != null && peispatient.getIdGuidenurse() == 1
                && peispatient.getParsedassignedsuiteandfi() != null
                && !peispatient.getParsedassignedsuiteandfi().equals(username)) {
            String islockman = "0";
            String msg = "该记录由" + peispatient.getParsedassignedsuiteandfi() + "医师锁定!";
            result.put("islockman", islockman);
            result.put("msg", msg);
        } else {
            if (peispatient.getZytjzt() == null) {
                peispatient.setZytjzt(0);// 总检状态开始
                peisStateService.setScbs(peispatient.getPatientcode(), 0);
            }
            peispatient.setIdGuidenurse(1.0);// 总检锁定: 1.锁定 0.解锁 暂定
            peispatient.setParsedassignedsuiteandfi(username);// 锁定人
            peispatientService.updateById(peispatient);
        }
        SectionTotal sectionTotal = null;
        synchronized (basMergeConclusionService) {
            sectionTotal = sectionTotalService.getOne(new QueryWrapper<SectionTotal>()
                    .eq("patientcode", patientCode).eq("disease_health", 1));

            if (ObjectUtils.isEmpty(sectionTotal)) {
                sectionTotal = totalHealthInspectionService.init(peispatient, 1);
            }


        }
        result.put("sectionTotal", sectionTotal);

        Report report = reportService.getOne(new QueryWrapper<Report>().eq("patientcode", patientCode)
                .eq("disease_health", 1).isNotNull("create_num"));
        if (report != null) {
            String fpdf = "1";
            result.put("fpdf", fpdf);
            pdfUrl = report.getUrlPdf();
        }

        String isVIP = getIdPatientClass(peispatient);
        result.put("isVIP", isVIP);
        String createUrl = Constants.CREATE_IP;
        result.put("createUrl", createUrl);


        Peispatient p = peispatientService.getByPatientCode(sectionTotal.getPatientcode());
        List<STHistoryVo> history = sectionTotalService.getHistory(sectionTotal.getId(), 1, p.getPatientarchiveno());
        result.put("history", history.size() > 0 ? 1 : 0);
        result.put("peispatient", peispatient);
        result.put("pdfUrl", pdfUrl);

        return R.ok(result);
    }


    public static String getMedicalType(Object obj) {
        if (obj == null) {
            return null;
        }
        switch (obj.toString()) {
            case "0":
                return "上岗前";
            case "1":
                return "在岗期间";
            case "2":
                return "离岗时";
            case "3":
                return "离岗后";
            case "4":
                return "应急";
            default:
                return null;
        }
    }


    /**
     * 职业总检结论词同步
     *
     * @param patientno
     * @return
     */
    @PutMapping("/synchronization")
    @ApiOperation(value = "职业总检结论词同步", notes = "职业总检结论词同步")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R synchronization(String patientno) {
        // TODO: 2022/12/23 同步未完成
//        Boolean b = diseaseTotalService.synchronization(patientno);
        return R.ok();
    }


    /**
     * 打开职业处理意见窗口
     *
     * @param patientno
     * @return
     */
    @GetMapping("/treatmentsuggestion")
    @ApiOperation(value = "打开职业处理意见窗口", notes = "打开职业处理意见窗口")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R treatmentsuggestion(String patientno) {
        //返回map
        HashMap result = new HashMap();
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientno));
        if (ObjectUtils.isNotEmpty(peispatient)) {
            // 获取综述内容,结论,处理意见
            Map<String, String> map = diseaseTotalService.getThreeData(patientno);
            result.put("conclusion", map.get("advice"));
            result.put("summary", map.get("summary"));
            result.put("occupationDiseast", diseaseTotalService.getOccupationDiseast(peispatient.getPatientarchiveno()));
            result.put("posistive", map.get("posistive"));
            result.put("peispatient", peispatient);
        }
        return R.ok(result);
    }


    /**
     * 打开职业处理意见增加窗口
     *
     * @param patientno
     * @return
     */
    @GetMapping("/treatmentsuggestionadd")
    @ApiOperation(value = "打开职业处理意见增加窗口", notes = "打开职业处理意见增加窗口")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R treatmentsuggestionadd(String patientno) {
        //返回map
        HashMap result = new HashMap();
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", patientno));
        if (ObjectUtils.isNotEmpty(peispatient)) {
            result.put("peispatient", peispatient);
            String jhyss = peispatient.getJhys();
            String jhys = "";
            if (StringUtils.isNotEmpty(jhyss)) {
                String[] str = jhyss.split(",");
                if (ObjectUtils.isNotEmpty(str) && str.length > 0) {
                    for (int i = 0; i < str.length; i++) {
                        Harm harm = harmService.getInfoById(str[i].trim());
                        if (ObjectUtils.isNotEmpty(harm)) {
                            if (StringUtils.isEmpty(jhys)) {
                                jhys = harm.getHarmName();
                            } else {
                                jhys = jhys + "," + harm.getHarmName();
                            }
                        }
                    }
                }
            }

            result.put("jhysId", jhyss);
            result.put("jhys", jhys);
            result.put("medicaltype", peispatient.getMedicaltype());
            result.put("patientno", patientno);
            return R.ok(result);
        }
        return R.fail("体检号不存在！");
    }


    /**
     * 展示危害因素
     *
     * @return
     */
    @GetMapping("/showJhys")
    @ApiOperation(value = "展示危害因素", notes = "展示危害因素")
    public R showJhys() {
        List<Harm> list = harmService.list();
        return R.ok(list);
    }


    /**
     * 显示所有的职业病检查结论
     *
     * @return
     */
    @GetMapping("/showZySummaryService")
    @ApiOperation(value = "显示所有的职业病检查结论", notes = "显示所有的职业病检查结论")
    public R showZySummaryService() {
        List<ZySummary> list = zySummaryService.list(new QueryWrapper<ZySummary>()
                .eq("is_delete", 0)
                .orderByAsc("sort"));
        return R.ok(list);
    }


    /**
     * 显示所有的禁忌疾病
     *
     * @return
     */
    @GetMapping("/showHealthEvaluation")
    @ApiOperation(value = "显示所有的禁忌疾病", notes = "显示所有的禁忌疾病")
    public R showHealthEvaluation() {
        List<DrugDisease> list = drugDiseaseService.list();
        return R.ok(list);
    }


    /**
     * 职业处理意见界面 条件搜索
     *
     * @param param
     * @return
     */
    @GetMapping("/searchTreatmentsuggestion")
    @ApiOperation(value = "职业处理意见界面 条件搜索", notes = "职业处理意见界面 条件搜索")
    public R searchTreatmentsuggestion(SearchTreatParam param) {
        List<HashMap> list = diseaseTotalService.searchTreatmentsuggestion(param);
        return R.ok(list);
    }


    /**
     * 科室小结 界面 数据
     *
     * @param patientno
     * @return
     */
    @GetMapping("/getVerdictData")
    @ApiOperation(value = "科室小结界面数据", notes = "科室小结 界面 数据")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R getVerdictData(String patientno) {
        List<VerdictDataVo> list = diseaseTotalService.getVerdictData(patientno);
        return R.ok(list);
    }


    /**
     * 职业意见 界面 数据
     *
     * @param patientno
     * @return
     */
    @GetMapping("/getTreatmentData")
    @ApiOperation(value = "职业意见界面数据", notes = "职业意见 界面 数据")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R<List<TreatmentDataVo>> getTreatmentData(String patientno) {
        List<TreatmentDataVo> list = diseaseTotalService.getTreatmentDataString(patientno);
        return R.ok(list);
    }


    /**
     * 删除某条职业处理意见
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/removeRows")
    @ApiOperation(value = "删除某条职业处理意见", notes = "删除某条职业处理意见")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "id的集合"),
            @ApiImplicitParam(name = "patientno", value = "体检号")
    })
    public R<Map<String, Object>> removeRows(@RequestParam List<String> ids, @RequestParam String patientno) {
        Map<String, Object> map = diseaseTotalService.removeRows(ids, patientno);
        return R.ok(map);
    }


    /**
     * 保存职业意见增加界面 数据
     *
     * @param param
     * @return
     */
    @PostMapping("/saveTreatment")
    @ApiOperation(value = "保存职业意见增加界面数据", notes = "保存职业意见增加界面 数据")
    public R saveTreatment(@RequestBody SaveTreatParam param) {
        Map<String, Object> map = diseaseTotalService.saveTreatment(param);
        return R.ok(map);
    }


    /**
     * 根据科室id和体检号 获取 体检项目和收费项目 和小结
     *
     * @param patientno
     * @param sectionId
     * @return
     */
    @GetMapping("/getInspectChargeListData")
    @ApiOperation(value = "根据科室id和体检号 获取 体检项目和收费项目 和小结", notes = "返回的ver2，ver4，ver5，ver6，ver7,就是c1，c2，c3，c4，CommonSummary")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "sectionId", value = "科室id")
    })
    public R getInspectChargeListData(String patientno, String sectionId) {
        List<VerdictDataVo> list = diseaseTotalService.getInspectChargeListData(patientno, sectionId);
        return R.ok(list);
    }


    /**
     * 保存职业意见增加界面 数据
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "保存职业意见增加界面数据", notes = "保存职业意见增加界面 数据")
    public R saOrUp(@RequestBody DiseaseSaOrUpParam param) {
        Boolean b = diseaseTotalService.saOrUp(param);
        return R.toResult(b);
    }


    /**
     * 跳总检界面需判断体检号是否存在,存在返回对应实体对象
     *
     * @param patientno
     * @param flag
     * @return
     */
    @GetMapping("/checkExamnumber")
    @ApiOperation(value = "跳总检界面需判断体检号是否存在,存在返回对应实体对象", notes = "跳总检界面需判断体检号是否存在,存在返回对应实体对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "flag", value = "体检号是否补0 ，true是false否")
    })
    public R checkExamnumber(String patientno, String flag) {
        String patientCode = patientno;
        //是否补0
        if (StringUtils.isNotEmpty(flag) && flag.equals("true")) {
            patientCode = ToolUtil.patientCode(patientno, iSysBranchService.getBranchFlag(null));
        }
        //体检者表
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientCode));
        if (ObjectUtils.isEmpty(peispatient)) {
            throw new ServiceException("体检号不存在！");
        }
        //分检完成
        if (ObjectUtils.isEmpty(peispatient.getFReadytofinal()) || peispatient.getFReadytofinal() != 1) {
            throw new ServiceException("分检未完成！");
        }
        return R.ok(peispatient);
    }


    /**
     * 根据体检号锁定 改变总检状态
     *
     * @param patientcode
     * @return
     */
    @PutMapping("/lock")
    @ApiOperation(value = "锁定", notes = "根据体检号锁定 改变总检状态")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R lock(String patientcode) {
        Boolean b = diseaseTotalService.lock(patientcode);
        return R.ok(b);
    }


    /**
     * 解锁
     *
     * @param patientcode
     * @return
     */
    @PutMapping("/unlock")
    @ApiOperation(value = "解锁", notes = "根据体检号锁定 改变总检状态")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R unlock(String patientcode) {
        Boolean b = diseaseTotalService.unlock(patientcode);
        return R.ok(b);
    }


    /**
     * 完成
     *
     * @param param
     * @return
     */
    @PostMapping("/finish")
    @ApiOperation(value = "完成", notes = "根据体检号 改变总检完成状态")
    public R finish(@RequestBody DiseaseSaOrUpParam param) {
        Boolean b = diseaseTotalService.finish(param);
        return R.toResult(b);

    }


    /**
     * 未完成
     *
     * @param patientno
     * @return
     */
    @PutMapping("/unfinish")
    @ApiOperation(value = "未完成", notes = "根据体检号 改变总检完成状态")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R unfinish(@RequestParam String patientno) {
        Boolean b = diseaseTotalService.unfinish(patientno);
        return R.toResult(b);
    }


    /**
     * 保存综述 结论 建议 职业和健康共用
     *
     * @param param
     * @return
     */
    @PostMapping("/saveSign")
    @ApiOperation(value = "保存综述 结论 建议 职业和健康共用", notes = "保存综述 结论 建议 职业和健康共用")
    public R saveSign(@RequestBody SaveSignAllParam param) {
        Boolean b = diseaseTotalService.saveSign(param);
        return R.toResult(b);
    }


    /**
     * 获取该体检者所有的体证词与所产生体证词的科室
     *
     * @param patientno
     * @return
     */
    @GetMapping("/getSign")
    @ApiOperation(value = "获取该体检者所有的体证词与所产生体证词的科室", notes = "获取该体检者所有的体证词与所产生体证词的科室")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R getSign(String patientno) {
        List<HashMap> list = diseaseTotalService.getSign(patientno);
        return R.ok(list);
    }


    /**
     * 查询出符合要求的所有未出现在总检中的结论词
     *
     * @param pageParamSimple
     * @param patientno
     * @param srm
     * @return
     */
    @GetMapping("/serchConclusion")
    @ApiOperation(value = "查询出符合要求的所有未出现在总检中的结论词", notes = "他是分页查询，size设置的20条")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientno", value = "体检号"),
            @ApiImplicitParam(name = "srm", value = "输入码，就是老系统的key")
    })
    public R serchConclusion(PageParamSimple pageParamSimple, String patientno, String srm) {
        PageParam<Basconclusion> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<Basconclusion> iPage = diseaseTotalService.getConclusion(srm, page, patientno);
        return R.ok(iPage);
    }


    /**
     * 分科肺功能,返回科室ID
     *
     * @param patientCode
     * @return
     */
    @GetMapping("/lung")
    @ApiOperation(value = "分科肺功能", notes = "分科肺功能")
    @ApiImplicitParam(name = "patientCode", value = "体检号")
    public R<Lung> lung(String patientCode) {
        Lung lung = lungService.getOne(new QueryWrapper<Lung>().eq("patientcode", patientCode));
        return R.ok(lung);
    }


    /**
     * 科电测听,返回科室ID及配置
     *
     * @param patientCode
     * @return
     */
    @GetMapping("/audio")
    @ApiOperation(value = "分科电测听", notes = "分科电测听,返回科室ID及配置")
    @ApiImplicitParam(name = "patientCode", value = "体检号")
    public R audio(String patientCode) {

        ElectroAudiometer audio = electroAudiometerService.getOne(new QueryWrapper<ElectroAudiometer>()
                .eq("patientcode", patientCode));

        Map<String, String> dct = Constants.DCT;

        if (audio != null) {
            String ksID = audio.getDepId();
            dct.put("ksID", ksID);
        }

        return R.ok(dct);
    }


    public final String getIdPatientClass(Peispatient patient) {
        String result = "";
        if (patient != null) {
            Patienttype pt = patienttypeService.getInfoById(patient.getIdPatientclass());
            if (ObjectUtils.isNotEmpty(pt)) {
                result = pt.getPatientName() == null ? "" : pt.getPatientName();
            }
        }
        return result;
    }


    /**
     * 分科电测听读卡
     *
     * @param autoFill
     * @param patientCode
     * @param ksId
     * @return
     */
    @GetMapping("/search")
    @ApiOperation(value = "分科电测听读卡", notes = "分科电测听读卡，返回基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "autoFill", value = "是否自动补0,true是,false否"),
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "ksId", value = "科室id")
    })
    public R search(String autoFill, String patientCode, String ksId) {
        //自动补0
        if ("true".equals(autoFill)) {
            patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        }
        HashMap<String, Object> data = diseaseTotalService.search(patientCode, ksId);
        return R.ok(data);
    }


    /**
     * 获取医生数据
     *
     * @param username
     * @param harmClass
     * @return
     */
    @GetMapping("/getDoctorData")
    @ApiOperation(value = "获取医生数据", notes = "获取医生数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名或输入码模糊查询"),
            @ApiImplicitParam(name = "harmClass", value = "危害因素种类ID集合")
    })
    public R getDoctorData(PageParamSimple pageParamSimple, @RequestParam(required = false) String username,
                           @RequestParam(required = false) List<String> harmClass) {
        PageParam<DoctorDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        String userNo = SecurityUtils.getUserNo();
        IPage<DoctorDataVo> list = sysUserService.getDoctorData(page, userNo, username, harmClass);
        return R.ok(list);
    }


    /**
     * 判断是否需要选择总检医生
     *
     * @param patientno
     * @return
     */
    @GetMapping("/checkHarm")
    @ApiOperation(value = "判断是否需要选择总检医生", notes = "判断是否需要选择总检医生")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R checkHarm(String patientno) {
        Map<String, Object> map = diseaseTotalService.checkHarm(patientno);
        return R.ok(map);
    }


    /**
     * 获取复查项目
     *
     * @param patientno
     * @return
     */
    @GetMapping("/getReviewProjects")
    @ApiOperation(value = "获取复查项目", notes = "获取复查项目")
    @ApiImplicitParam(name = "patientno", value = "体检号")
    public R getReviewProjects(String patientno) {
        Review r = reviewService.getOne(new QueryWrapper<Review>()
                .eq("patientcode", patientno).eq("is_delete", 0));
        if (r == null) {
            return R.ok();
        }
        Map map = new HashMap();
        List<ReviewProject> rps = reviewProjectService.list(new QueryWrapper<ReviewProject>().eq("review_id", r.getId()));
        List<String> itemNames = new ArrayList<String>();
        List<String> notes = new ArrayList<>();
        for (ReviewProject rp : rps) {
            Items item = itemsService.getInfoById(rp.getItemsId());
            if (item != null) {
                itemNames.add(item.getExamfeeitemNameprn());
            }
            if (item != null && StringUtils.isNotEmpty(item.getReviewMatters())) {
                notes.add(item.getReviewMatters());
            }
        }
        map.put("items", StringUtils.join(itemNames, "、"));
        map.put("notes", StringUtils.join(notes, ""));
        String reviewTips = StringUtils.isNotEmpty(ReportConstants.REVIEWTIPS) ? ReportConstants.REVIEWTIPS : "请您于收到此报告后一个月内进行复查。";
        map.put("reviewTips", reviewTips);
        return R.ok(map);
    }
}


