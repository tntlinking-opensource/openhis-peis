package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.*;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.service.*;
import com.center.medical.abteilung.bean.dto.DivisionDto;
import com.center.medical.abteilung.bean.dto.InitDto;
import com.center.medical.abteilung.bean.model.SectionAndRemind;
import com.center.medical.abteilung.bean.model.SectionRemind;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.param.*;
import com.center.medical.abteilung.bean.vo.*;
import com.center.medical.abteilung.service.DivisionService;
import com.center.medical.abteilung.service.SectionAndRemindService;
import com.center.medical.abteilung.service.SectionRemindService;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.bean.vo.AddListDataVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.config.DeptDataConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysConfig;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.ReflectionUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.vo.SfxmVo;
import com.center.medical.report.bean.vo.CommonDataVo;
import com.center.medical.report.bean.vo.VerdictDataVo;
import com.center.medical.report.service.DiseaseTotalService;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.service.PeisorgreservationgroupService;
import com.center.medical.service.*;
import com.center.medical.system.bean.vo.AllUserDataVo;
import com.center.medical.system.dao.SysConfigMapper;
import com.center.medical.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 科室列表(SectionResultMain)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:53
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室列表")
@RequestMapping("/abteilung/division")
public class DivisionController extends BaseController {
    /**
     * 服务对象
     */
    private final DivisionService divisionService;
    private final MapperFacade mapperFacade;
    private final ISysDeptService sysDeptService;
    private final ISysUserService sysUserService;
    private final ISysBranchService sysBranchService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final SysConfigMapper sysConfigMapper;
    private final ISysRoleService sysRoleService;
    private final SectionResultMainService sectionResultMainService;
    private final ISysBranchService isysBranchService;
    private final PatienttypeService patienttypeService;
    private final SectionAndRemindService sectionAndRemindService;
    private final PeispatientService peispatientService;
    private final PeispatientPhotoService peispatientPhotoService;
    private final PeisorgreservationgroupService peisorgreservationgroupService;
    private final DiseaseTotalService diseaseTotalService;
    private final SectionRemindService sectionRemindService;
    private final ItemsService itemsService;
    private final DrinkingTypeService drinkingTypeService;
    private final WzJwbService wzJwbService;
    private final HarmService harmService;
    private final PeispatientConsultationService peispatientConsultationService;
    private final PeispatienthistoryService peispatienthistoryService;
    private final WzZysService wzZysService;
    private final PeispatientConsultationPicService peispatientConsultationPicService;
    private final AreaService areaService;
    private final WzZybsService wzZybsService;
    private final OccupationDiseastService occupationDiseastService;
    private final ISysBranchService iSysBranchService;
    private final BaseWorktypeService baseWorktypeService;
    private final WzJzbService wzJzbService;
    private final AttachmentService attachmentService;
    private final ISysConfigService iSysConfigService;

    /**
     * 查询
     *
     * @param param
     * @return
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询科室列表数据", notes = "查询科室列表数据")
    public R<AbteilungListVo> list(SectionResultMainParam param) {
        //返回对象
        AbteilungListVo vo = new AbteilungListVo();
        String lisPacsUrl = Constants.PACS_IP;
        String userName = SecurityUtils.getUsername();
        String pacsOpen = getDictionaryStatusByType("PACS") ? "1" : "0";
        vo.setLisPacsUrl(lisPacsUrl);
        vo.setUserName(userName);
        vo.setPacsOpen(pacsOpen);

        String msg = "";
        List<Peispatientfeeitem> peispatientfeeitemList = new ArrayList<>();
        //体检号补0
        String patientCode = param.getPatientCode();
        if (ObjectUtils.isNotEmpty(param.getAutoFill()) && "true".equals(param.getAutoFill())) {
            //需要补全
            patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        } else if (ObjectUtils.isNotEmpty(patientCode)) {
            patientCode = patientCode.trim().toUpperCase();
        }
        vo.setPatientCode(patientCode);

        //科室列表数据
        List<DivisionDto> listQD = divisionService.getListData(patientCode);
        vo.setListQD(listQD);

        if (StringUtils.isNotEmpty(patientCode)) {
            String userNo = SecurityUtils.getUserNo();
            peispatientfeeitemList = peispatientfeeitemService.selectUnfees(userNo, patientCode);
            if (peispatientfeeitemList.size() > 0) {
                msg = "此人有未缴费的项目，会影响进一步检查，请确认！";
                vo.setMsg(msg);
            }
        }
        return R.ok(vo);
    }


    public boolean getDictionaryStatusByType(String type) {
        SysConfig dic = sysConfigMapper.checkConfigKeyUnique(type);
        return (dic == null || dic.getConfigType() == null) ? false : "T".equals(dic.getConfigType()) ? true : false;
    }




    /**
     * 查看列队数据
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getRankData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查看列队数据", notes = "查看列队数据(职业性问诊单独用getpatientdata)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ksID", value = "科室id"),
            @ApiImplicitParam(name = "param", value = "参数")
    })
    public R<IPage<RankDataVo>> getRankData(PageParamSimple pageParamSimple, RankDataParam param) {
        PageParam<Peispatientexamitem> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<RankDataVo> list = divisionService.getRankData(page, param);
        return R.ok(list);
    }


    /**
     * 获取会员类型
     *
     * @return
     */
    @GetMapping("/getPatientTypeData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取会员类型", notes = "获取体检者会员类型")
    public R<List<Patienttype>> getPatientTypeData() {
        List<Patienttype> pattList = patienttypeService.list();
        return R.ok(pattList);
    }


    /**
     * 判断是否存在提醒
     *
     * @return
     */
    @GetMapping("/getRemindStr")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "判断是否存在提醒", notes = "判断是否存在提醒,true是,false否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "ksID", value = "科室id"),
            @ApiImplicitParam(name = "autoFill", value = "是否补全，true是false否")
    })
    public R getRemindStr(String patientcode, String ksID,String autoFill) {
        //科室提醒和科室关联表
        QueryWrapper<SectionAndRemind> con = new QueryWrapper<>();

        if (StringUtils.isNotEmpty(autoFill) && "true".equals(autoFill)) {
            patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
        }
        con.eq("patientcode", patientcode);
        if (StringUtils.isNotEmpty(ksID)) {
            con.eq("dep_id", ksID);
        }
        //true是,false否
        long count = sectionAndRemindService.count(con);
        if (count > 0) {
            return R.ok("true");
        }
        return R.ok("false");
    }


    /**
     * 获取审核之后的数据
     *
     * @return
     */
    @GetMapping("/getNkCheckedData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取审核之后的数据", notes = "获取审核之后的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "ksID", value = "科室id"),
            @ApiImplicitParam(name = "autoFill", value = "是否补全，true是false否")
    })
    public R<NkCheckedVo> getNkCheckedData(String patientcode, String ksID , String autoFill) {
        //补全体检号
        if (StringUtils.isNotEmpty(autoFill) && "true".equals(autoFill)) {
            patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
        }
        NkCheckedVo list = divisionService.getNkCheckedData(patientcode, ksID);
        return R.ok(list);
    }


    /**
     * 结伦词列表数据
     *
     * @return
     */
    @GetMapping("/jlcData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "结伦词列表数据", notes = "结伦词列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "ksID", value = "科室id")
    })
    public R jlcData(String patientcode, String ksID) {
        List<HashMap<String, String>> result = divisionService.jlcData(patientcode, ksID);
        return R.ok(result);
    }


    /**
     * 获取c13、c14数据
     *
     * @param patientcode
     * @param gridSeclect
     * @param autoFill
     * @param ksID
     * @return
     */
    @GetMapping("/c13")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "科室详情（c13、c14）", notes = "获取c13、c14数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "gridSeclect", value = "是否是选择体检者列表（内科）,true是false否"),
            @ApiImplicitParam(name = "autoFill", value = "体检号是否补0,true是false否"),
            @ApiImplicitParam(name = "ksID", value = "科室id")
    })
    public R c13(String patientcode, String gridSeclect, String autoFill, String ksID) {
        Map<String, Object> data = new HashMap<String, Object>();
        InitDto init = init();
        data.put("init", init);
        String flag = "";

        List<HashMap<String, String>> griddata = new ArrayList<>();
        if (StringUtils.isNotBlank(patientcode)) {
            // 是否是选择体检者列表
            if (gridSeclect == null || "false".equals(gridSeclect)) {
                //体检号补0
                if (autoFill != null && "true".equals(autoFill)) {
                    patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
                } else {
                    patientcode = patientcode.trim().toUpperCase();
                }
            }
            data.put("patientcode", patientcode);
            //体检者收费项目表
            List<Peispatientfeeitem> list1 = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", patientcode).eq("id_ks", ksID)
                    .isNull("f_transferedhl7").eq("sfjj", 0)
                    .eq("f_giveup", 0).eq("change_item", 0));

            if (list1 != null && list1.size() > 0) {
                boolean charged = false;// 判断是否有已缴费的费用项目
                boolean paid = true;// 是否全部缴费
                StringBuilder unpaid = new StringBuilder();

                List<Peispatientfeeitem> tjzsfxm = new ArrayList<Peispatientfeeitem>();
                for (Peispatientfeeitem feeitem : list1) {
                    if (feeitem.getFFeecharged() != null && feeitem.getFFeecharged() == 1) {
                        charged = true;
                        tjzsfxm.add(feeitem);
                    } else {
                        paid = false;
                        if (unpaid.length() == 0) {
                            unpaid.append("该体检号存在未缴费的费用项目：" + feeitem.getExamfeeitemName());
                        } else {
                            unpaid.append("," + feeitem.getExamfeeitemName());
                        }
                    }
                }
                if (charged) {
                    if (!paid) {
                        flag = unpaid.toString();
                    }
                    SectionResultMain main = sectionResultMainService.getOne(new QueryWrapper<SectionResultMain>()
                            .eq("patientcode", patientcode).eq("dep_id", ksID));
                    if (main != null && main.getIsAudit() != null && main.getIsAudit() == 1 ? true : false) {
                        // 已审核 不能修改
                        flag = "audit";
                        //检查时间
                    }
                    data.put("flag", flag);
                    if (ObjectUtils.isNotEmpty(main)) {
                        SysUser sysUser = sysUserService.selectUserByUserNo(main.getWriteId());
                        if (ObjectUtils.isNotEmpty(sysUser)) {
                            //录入人姓名
                            main.setWriteName(sysUser.getUserName());
                        }
                        data.put("sectionResultMain", main);
                    }

                    Peispatient user = peispatientService.getOne(new QueryWrapper<Peispatient>()
                            .eq("patientcode", patientcode).eq("f_registered", 1));
                    if (ObjectUtils.isEmpty(user)) {
                        throw new ServiceException("error@该体检号尚未登记!");
                    }
                    String picture = peispatientPhotoService.getPicture(user);
                    data.put("picture", picture);
                    if (user.getFPaused() != null && user.getFPaused().intValue() == 1) {
                        throw new ServiceException("error@该体检号已被禁检!");
                    }
                    data.put("peispatient", user);
                    // 任务分组ID
                    String idOrgreservationgroup = user.getIdOrgreservationgroup();
                    if (idOrgreservationgroup != null) {
                        Peisorgreservationgroup org = peisorgreservationgroupService.getInfoById(idOrgreservationgroup);
                        if (org != null && org.getFGrouppaused() != null && org.getFGrouppaused().intValue() == 1) {
                            throw new ServiceException("error@该体检号已被禁检!");
                        }
                    }
                    String isVIP = patienttypeService.getIdPatientClass(user);
                    data.put("isVIP", isVIP);
                    String tjlx = user.getIdExamtype();// 体检类型
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ksId", ksID);// 科室Id
                    map.put("tjh", patientcode);// 体检号
                    map.put("tjlx", tjlx);// 体检类型
                    List<HashMap<String, List<HashMap<String, String>>>> nkdata = divisionService.getC13data(map, tjzsfxm);
                    griddata = divisionService.getJlcData(patientcode, ksID);
                    data.put("nkdata", nkdata);
                    data.put("griddata", griddata);
                } else {
                    throw new ServiceException("error@该体检号尚未缴费!");
                }
            } else {
                throw new ServiceException("error@该体检号没有本科室收费项目!");
            }
        }
        return R.ok(data);
    }


    /**
     * 科室数据初始化
     *
     * @return
     */
    private InitDto init() {
        String create_url = Constants.CREATE_IP;
        String lrrId = SecurityUtils.getUserNo();
        String lrr = SecurityUtils.getUsername();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String lrsj = sdf.format(new Date());
        InitDto initDto = new InitDto(create_url, lrrId, lrr, lrsj);
        return initDto;
    }


    /**
     * 分科普通界面数据
     *
     * @param patientno
     * @return
     */
    @GetMapping("/getCommonData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "科室-左侧数据", notes = "查看科室左侧数据")
    @ApiImplicitParam(name = "patientno", value = "体检码")
    public R<List<CommonDataVo>> getCommonData(String patientno) {
        List<CommonDataVo> list = diseaseTotalService.getCommonListData(patientno);
        return R.ok(list);
    }


    /**
     * 根据科室id和体检号获取体检项目和收费项目和小结
     *
     * @param patientcode
     * @param sectionId
     * @return
     */
    @GetMapping("/getInspectChargeListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "科室-科室小结", notes = "查看科室-科室小结,返回的ver2，ver4，ver5，ver6，ver7,就是c1，c2，c3，c4，CommonSummary")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "sectionId", value = "科室id")
    })
    public R<List<VerdictDataVo>> getInspectChargeListData(String patientcode, String sectionId) {
        List<VerdictDataVo> list = diseaseTotalService.getInspectChargeListData(patientcode, sectionId);
        return R.ok(list);
    }


    /**
     * 查看档案 体检者列表数据
     *
     * @param pageParamSimple
     * @param patientcode
     * @return
     */
    @GetMapping("/getArchiveData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "档案-查看档案", notes = "查看档案 体检者列表数据")
    @ApiImplicitParam(name = "patientcode", value = "体检码")
    public R<IPage<ArchiveDataVo>> getArchiveData(PageParamSimple pageParamSimple, String patientcode) {
        PageParam<ArchiveDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<ArchiveDataVo> page1 = divisionService.getArchiveData(page, patientcode);
        return R.ok(page1);
    }


    /**
     * 查看档案 体检者列表数据
     *
     * @param pageParamSimple
     * @param patientcode
     * @return
     */
    @GetMapping("/getResultData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "档案-查看档案右侧数据", notes = "查看档案右侧数据,科室医师小结等")
    @ApiImplicitParam(name = "patientcode", value = "体检码")
    public R<IPage<ResultDataVo>> getResultData(PageParamSimple pageParamSimple, String patientcode) {
        PageParam<ResultDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<ResultDataVo> page1 = divisionService.getResultData(page, patientcode);
        return R.ok(page1);
    }


    /**
     * 我要提醒-获取科室
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getRemindKs")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "我要提醒-获取科室", notes = "我要提醒-获取科室")
    @ApiImplicitParam(name = "patientcode", value = "体检码")
    public R<List<RemindKsVo>> getRemindKs(String patientcode) {
        List<RemindKsVo> list = divisionService.getRemindKs(patientcode);
        return R.ok(list);
    }


    /**
     * 我要提醒-保存科室提醒
     *
     * @param param
     * @return
     */
    @PostMapping("/saveRemind")
    @ApiOperation(value = "我要提醒-保存科室提醒", notes = "我要提醒-保存科室提醒")
    @ApiImplicitParam(name = "patientcode", value = "体检码")
    public R saveRemind(SaveRemindParam param) {
        Boolean b = divisionService.saveRemind(param);
        return R.toResult(b);
    }


    /**
     * 我要提醒-获取数据
     *
     * @param patientcode
     * @param ksID
     * @param key
     * @return
     */
    @GetMapping("/remind")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "我要提醒-获取数据", notes = "我要提醒-获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "ksID", value = "科室id"),
            @ApiImplicitParam(name = "key", value = "提醒内容")
    })
    public R<SectionRemind> remind(String patientcode, String ksID, String key) {
        SectionRemind sectionRemind = sectionRemindService.getOne(new QueryWrapper<SectionRemind>()
                .eq("patientcode", patientcode).eq("dep_id", ksID));// 一个科室只提醒一次，如果重复提醒 就是修改。
        if (sectionRemind == null && StringUtils.isNotEmpty(key)) {
            sectionRemind = new SectionRemind();
            sectionRemind.setRemindContent(key);
        }
        return R.ok(sectionRemind);
    }


    /**
     * 查看提醒-获取数据
     *
     * @param patientcode
     * @param ksID
     * @return
     */
    @GetMapping("/showRemind")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查看提醒-获取数据", notes = "查看提醒-获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "ksID", value = "科室id")
    })
    public R showRemind(String patientcode, String ksID) {
        Map<String, String> map = new HashMap<>();
        Peispatient peispatient = peispatientService.getByPatientCode(patientcode);
        map.put("patientcode", peispatient.getPatientcode());
        map.put("patientname", peispatient.getPatientname());
        String key = divisionService.getContent(patientcode, ksID);
        map.put("key", key);
        return R.ok(map);
    }


    /**
     * 查看提醒-获取数据
     *
     * @param patientcode
     * @param ksID
     * @return
     */
    @GetMapping("/case1")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "科室详情（无图像检查、图像检查）", notes = "无图像检查、图像检查")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "ksID", value = "科室id"),
            @ApiImplicitParam(name = "gridSeclect", value = "是否是选择体检者列表（内科）"),
            @ApiImplicitParam(name = "autoFill", value = "是否自动补全")
    })
    public R case1(String patientcode, String ksID, String gridSeclect, String autoFill) {
        Map map = divisionService.case1(patientcode, ksID, gridSeclect, autoFill);
        return R.ok(map);
    }


    /**
     * 科室加项左侧数据，创建套餐获取基础数据收费项目
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getSfxm")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "科室加项左侧数据", notes = "科室加项左侧数据，创建套餐获取基础数据收费项目")
    public R<IPage<SfxmVo>> getSfxm(PageParamSimple pageParamSimple, SfxmParam param) {
        PageParam<SfxmVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<SfxmVo> list = divisionService.getSfxm(page, param);
        return R.ok(list);
    }


    /**
     * 科室加项右侧数据
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getAddListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "科室加项右侧数据", notes = "科室加项右侧数据，项目名称等")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<List<AddListDataVo>> getAddListData(String patientcode) {
        List<AddListDataVo> list = peispatientfeeitemService.getAddListData(patientcode);
        return R.ok(list);
    }


    /**
     * 科室加项保存数据
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "科室加项保存数据", notes = "科室加项保存数据")
    public R saveOrUpdate(@RequestBody DivAddParam param) {
        Boolean b = divisionService.divAddSaOrUp(param);
        return R.toResult(b);
    }


    /**
     * 获取饮酒种类下拉数据
     *
     * @return
     */
    @GetMapping("/getDrinkTypeShowData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "职业性问诊-获取饮酒种类下拉数据", notes = "获取饮酒种类下拉数据")
    public R<List<DrinkingType>> getDrinkTypeShowData() {
        List<DrinkingType> list = drinkingTypeService.list();
        return R.ok(list);
    }


    /**
     * 职业性问诊体检者列表数据
     *
     * @return
     */
    @GetMapping("/getPatientData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "职业性问诊-体检者列表数据", notes = "职业性问诊体检者列表数据")
    public R<IPage<PatientDataVo>> getPatientData(PageParamSimple pageParamSimple, PatientDataParam param) {
        PageParam<PatientDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<PatientDataVo> list = divisionService.getPatientData(page, param);
        return R.ok(list);
    }


    /**
     * 职业性问诊体检者列表数据
     *
     * @param pageParamSimple
     * @param inputCode
     * @return
     */
    @GetMapping("/getJwbData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "职业性问诊-既往病数据", notes = "职业性问诊-既往病数据")
    @ApiImplicitParam(name = "inputCode", value = "输入码")
    public R<IPage<WzJwb>> getJwbData(PageParamSimple pageParamSimple, String inputCode) {
        PageParam<WzJwb> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //去空格
        if (ObjectUtils.isNotEmpty(inputCode)){
            inputCode = inputCode.trim();
        }
        //条件构造器
        QueryWrapper<WzJwb> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(inputCode)) {
            queryWrapper.like("input_code", inputCode.toUpperCase());
        }
        PageParam<WzJwb> iPage = wzJwbService.page(page, queryWrapper.orderByAsc("input_code"));
        return R.ok(iPage);
    }


    /**
     * 职业性问诊-获取接害因素
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getJhysData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "职业性问诊-获取接害因素", notes = "职业性问诊-职业性问诊体检者列表数据")
    @ApiImplicitParam(name = "key", value = "输入码")
    public R<IPage<Harm>> getJhysData(PageParamSimple pageParamSimple, String key) {
        PageParam<Harm> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //获取接害因素
        if (StringUtils.isNotEmpty(key)) {
            key = key.trim().toUpperCase();
            QueryWrapper<Harm> con = new QueryWrapper<>();
            con.like("input_code", key);
            PageParam<Harm> page1 = harmService.page(page, con.orderByDesc("createdate")
                    .eq("is_delete", 0).eq("lv", 2));
            return R.ok(page1);
        } else {
            PageParam<Harm> page1 = harmService.page(page, new QueryWrapper<Harm>()
                    .orderByDesc("createdate").eq("is_delete", 0).eq("lv", 2));
            //不搜索
            return R.ok(page1);
        }
    }


    private String sjnk = "125";
    private String nk = "6";

    /**
     * 职业性问诊-右边选中列表数据
     *
     * @param id
     * @param ksID
     * @return
     */
    @GetMapping("/commonjobinquiry")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "职业性问诊-读卡", notes = "职业性问诊-读卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id"),
            @ApiImplicitParam(name = "ksID", value = "科室id"),
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "autoFill", value = "是否补全体检号")
    })
    @RepeatSubmit(interval = 3000, message = "请等待3秒后重试!")
    public R commonjobinquiry(String id, String ksID, String patientcode, String autoFill) {
        Map<String, Object> data = new HashMap<>();
        String create_url = FileTypePath.CREATE_IP;
        data.put("create_url", create_url);
        String flag = "";
        SysDept sjnk_dep = sysDeptService.getByDeptNo(sjnk);
        if (sjnk_dep != null) {
            String sjnk_title = sjnk_dep.getDeptName();
            data.put("sjnk_title", sjnk_title);
        }
        SysDept nk_dep = sysDeptService.getByDeptNo(nk);
        if (nk_dep != null) {
            String nk_title = nk_dep.getDeptName();
            data.put("nk_title", nk_title);
        }
        Peispatient bean = null;
        if (StringUtils.isNotBlank(id)) {// 刷新页面
            bean = peispatientService.getOne(new QueryWrapper<Peispatient>()
                    .eq("id", id).eq("f_registered", 1));// 已登记
            patientcode = bean.getPatientcode();
        } else if (StringUtils.isNotBlank(patientcode)) {// 读卡
            if (autoFill != null && "true".equals(autoFill)) {
                patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
            } else {
                patientcode = patientcode.trim().toUpperCase();
            }

            bean = peispatientService.getOne(new QueryWrapper<Peispatient>()
                    .eq("patientcode", patientcode).eq("f_registered", 1));// 已登记
        }
        data.put("peispatient", bean);
        PeispatientConsultation pc = null;
        SectionResultMain main = null;
        Peispatient peispatient = null;
        PeispatientConsultationPic pcp = null;
        if (bean != null) {
            pc = peispatientConsultationService.getOne(new QueryWrapper<PeispatientConsultation>().eq("patientcode", patientcode));
            pcp = peispatientConsultationPicService.getOne(new QueryWrapper<PeispatientConsultationPic>()
                    .eq("patientcode", patientcode));
            //拼接既往病名
            if (ObjectUtils.isNotEmpty(pc) && StringUtils.isNotEmpty(pc.getEverOfDisease())){
                List<WzJwb> list = wzJwbService.list(new LambdaQueryWrapper<WzJwb>().in(WzJwb::getId, pc.getEverOfDisease().split(",")));
                if (ObjectUtils.isNotEmpty(list)){
                    List<String> names = list.stream()
                            .map(WzJwb::getOccupationDiseast)
                            .collect(Collectors.toList());
                    pc.setEverOfDiseaseName(String.join(",", names));
                }
            }
            data.put("peispatientConsultation", pc);
            data.put("peispatientConsultationPic", pcp);
            //不是禁检
            if (bean.getFPaused() == null || bean.getFPaused().intValue() == 0) {
                //任务分组ID
                String idOrgreservationgroup = bean.getIdOrgreservationgroup();
                Peisorgreservationgroup org = null;
                if (idOrgreservationgroup != null) {
                    org = peisorgreservationgroupService.getInfoById(idOrgreservationgroup);
                }
                if (org == null || org.getFGrouppaused() == null || org.getFGrouppaused().intValue() == 0) {
                    String isVIP = patienttypeService.getIdPatientClass(bean);
                    data.put("isVIP", isVIP);
                    main = sectionResultMainService.getOne(new QueryWrapper<SectionResultMain>().eq("dep_id", ksID)
                            .eq("patientcode", bean.getPatientcode()));
                    if (ObjectUtils.isNotEmpty(main)) {
                        SysUser sysUser = sysUserService.selectUserByUserNo(main.getWriteId());
                        if (ObjectUtils.isNotEmpty(sysUser)) {
                            //录入人姓名
                            main.setWriteName(sysUser.getUserName());
                        }
                    }
                    data.put("sectionResultMain", main);
                    List<Peispatientfeeitem> feeitems = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
                            .eq("id_patient", bean.getPatientcode()).eq("id_ks", ksID)
                            .eq("sfjj", 0).eq("f_giveup", 0)
                            .isNull("f_transferedhl7").eq("change_item", 0));
                    if (feeitems.size() == 0) {
                        flag = "该体检号没有本科室体检项目！";
                    } else {
                        boolean charge_flag = false;
                        for (Peispatientfeeitem feeitem : feeitems) {
                            if (feeitem.getFFeecharged() != null && feeitem.getFFeecharged() == 1) {
                                charge_flag = true;
                                break;
                            }
                        }
                        if (charge_flag) {
//                            Peispatient peispatient = new Peispatient();
                            peispatient = bean;
                            peispatient.setId(bean.getId());
                            if (pc == null || pc.getIsAudit() == null || (pc.getIsAudit() != 1
                                    // && peispatient.getIsAudit()!=2
                            )) {// 如果未审核，查询体检者历史;已审核、反审核，查询体检者表
                                String idPatientarchive = peispatient.getPatientarchiveno();
                                List<Peispatienthistory> phs = peispatienthistoryService.list(new QueryWrapper<Peispatienthistory>()
                                        .orderByDesc("createdate").eq("id_patientarchive", idPatientarchive));// 按档案ID查创建时间最近的体检者历史
                                if (phs.size() > 0) {
                                    Peispatienthistory ph = phs.get(0);
                                    copyPro(ph, peispatient, new String[]{"everOfDisease", "everOfDiseaseRemark",
                                            "ccnl", "jq", "zq", "tjnl", "familyNumber", "lc", "zc", "sc", "ywrc",
                                            "jt", "everydaySmokeN", "smokeYear", "abstainSmokeNote", "noKissTheCup",
                                            "betweenKissTheCup", "evermoreKiss", "abstainLostKiss", "kissYearN",
                                            "kissAmount", "kissType", "familyOfDisease", "symptom"});
                                }
                                data.put("peispatienthistory", phs);
                                /** 生成问诊职业史 */
                                String patientCode = bean.getPatientcode();
                                long i = wzZysService.count(new QueryWrapper<WzZys>().eq("djls", patientCode));
                                if (i == 0) {
                                    Date stopDate = new Date();// 止日期
                                    String dept = bean.getOrgName();
                                    String deptId = bean.getIdOrg();
                                    String branch = bean.getOrgDepart();
                                    String worktypeId = bean.getWorktypeId();
                                    String typeOfWork = null;
                                    if (worktypeId != null) {
                                        //工种
                                        BaseWorktype bw = baseWorktypeService.getInfoById(worktypeId);
                                        if (bw != null) {
                                            //工种名称
                                            typeOfWork = bw.getTypeName();
                                        }
                                    } else if (bean.getTrades() != null) {
                                        //工种
                                        typeOfWork = bean.getTrades();
                                    }
                                    if (typeOfWork != null) {
                                        String occupationHarm = bean.getJhys();
                                        String userId = SecurityUtils.getUserNo();
                                        WzZys new_zys = new WzZys(idPatientarchive, stopDate, dept, deptId, branch,
                                                typeOfWork, occupationHarm, userId, patientCode);

                                        Integer jhgl = bean.getJhgl() == null ? 0 : bean.getJhgl();// 接害工龄（月）
                                        Calendar startC = Calendar.getInstance();
                                        startC.add(Calendar.MONTH, 0 - jhgl.intValue());
                                        new_zys.setStartDate(startC.getTime());
                                        wzZysService.save(new_zys);
                                    }else {
                                        throw new ServiceException("工种不存在！");
                                    }
                                }
                            }
                        } else {
                            flag = "该体检号尚未缴费！";// 职业性问诊有一个固定的收费项目
                        }
                    }
                } else {
                    flag = "该体检号已被禁检!";
                }
            } else {
                flag = "该体检号已被禁检!";
            }
        }
        data.put("flag", flag);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String lrr = "";
        Date lrsj = new Date();
        Date jcsj = new Date();
        String lrrId = "";
        String jcr = "";
        String jcrxm = "";
        if (main == null) {
            lrr = SecurityUtils.getUsername();
            data.put("lrr", null);
            data.put("lrsj", lrsj);
            data.put("jcsj", jcsj);
        } else {
            lrrId = main.getWriteId();
            lrsj = main.getWriteTime();
            jcr = main.getRummagerId();
            data.put("lrrId", lrrId);
            data.put("lrsj", lrsj);
            data.put("jcr", jcr);

            if (jcr != null) {
                SysUser jcr_user = sysUserService.selectUserByUserNo(jcr);
                if (jcr_user != null) {
                    jcrxm = jcr_user.getUserName();
                    data.put("jcrxm", jcrxm);
                }
            }
            if (lrrId != null) {
                SysUser jcr_user = sysUserService.selectUserByUserNo(lrrId);
                if (jcr_user != null) {
                    lrr = jcr_user.getUserName();
                    data.put("lrr", lrr);
                }
            }
            jcsj = main.getRummagerTime();
            data.put("jcsj", jcsj);
        }
        if (pc != null && StringUtils.isNotBlank(pc.getEverOfDisease())) {
            // 拼接既往病名
            List<WzJwb> wjs = wzJwbService.list(new QueryWrapper<WzJwb>()
                    .in("id", pc.getEverOfDisease().split(",")));
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < wjs.size(); i++) {
                if (i != 0) {
                    str.append(",");
                }
                str.append(wjs.get(i).getOccupationDiseast());
            }
            String everOfDiseaseName = str.toString();
            data.put("everOfDiseaseName", everOfDiseaseName);
        }

        if (peispatient != null && StringUtils.isNotBlank(peispatient.getJhys())) {
            //危害因素
            List<Harm> harms = harmService.list(new QueryWrapper<Harm>().in("id", peispatient.getJhys().split(",")));
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < harms.size(); i++) {
                if (i != 0) {
                    str.append("、");// 使用顿号连接
                }
                str.append(harms.get(i).getHarmName());
            }
            String jhys = str.toString();
            data.put("jhys", jhys);
        }

        if (peispatient != null && StringUtils.isNotBlank(peispatient.getIdResarea())) {
            Area area = areaService.getInfoById(peispatient.getIdResarea());
            if (area != null) {
                //省份名称
                String idResarea = area.getResarea();
                data.put("idResarea", idResarea);
            }
        }
        return R.ok(data);
    }


    /**
     * 复制
     *
     * @param source
     * @param target
     * @param proNames
     * @return
     */
    private Peispatient copyPro(Peispatienthistory source, Peispatient target, String[] proNames) {
        for (String fieldName : proNames) {
            Object value = ReflectionUtil.invokeGetterMethod(source, fieldName);
            if (value != null) {
                ReflectionUtil.invokeSetterMethod(target, fieldName, value);
            }
        }
        return target;
    }


    /**
     * 职业性问诊-职业史列表数据
     *
     * @param key
     * @return
     */
    @GetMapping("/getZysData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "职业性问诊-职业史列表数据", notes = "职业性问诊-职业史列表数据")
    @ApiImplicitParam(name = "key", value = "登记流水(体检号)")
    public R<List<WzZys>> getZysData(String key) {
        // 2019-6-26 职业史不关联历史，以当此为主
        //职业史
        List<WzZys> wzs = wzZysService.list(new QueryWrapper<WzZys>()
                .orderByAsc("start_date").eq("djls", key));
        for (WzZys map : wzs) {
            //有无有害因素
            String occupationHarm = map.getOccupationHarm();
            if (StringUtils.isNotBlank(occupationHarm)) {
                //查询危害因素
                List<Harm> harms = harmService.list(new QueryWrapper<Harm>().in("id", occupationHarm.split(",")));
                StringBuilder harmName = new StringBuilder();
                for (int i = 0; i < harms.size(); i++) {
                    if (i != 0) {
                        harmName.append(",");
                    }
                    harmName.append(harms.get(i).getHarmName());
                }
                map.setHarmName(harmName.toString());
            }
        }
        return R.ok(wzs);
    }


    /**
     * 职业性问诊-职业病史列表数据
     *
     * @param key
     * @return
     */
    @GetMapping("/getZybsData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "职业性问诊-职业病史列表数据", notes = "职业性问诊-职业病史列表数据")
    @ApiImplicitParam(name = "key", value = "档案ID")
    public R<List<WzZybs>> getZybsData(String key) {
        //职业病史
        List<WzZybs> wzs = wzZybsService.list(new QueryWrapper<WzZybs>()
                .orderByAsc("diagnosis_date").eq("id_patientarchive", key));
        for (WzZybs map : wzs) {
            //疾病名称
            String occupationDiseast = map.getOccupationDiseast();
            if (StringUtils.isNotBlank(occupationDiseast)) {
                //职业病名称
                OccupationDiseast od = occupationDiseastService.getInfoById(occupationDiseast);
                if (ObjectUtils.isNotEmpty(od)) {
                    map.setOccupationDiseastName(od.getOccupationDiseast());
                }
            }
        }
        return R.ok(wzs);
    }


    /**
     * 职业性问诊-职业病史列表数据
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getRemindPatient")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "职业性问诊-职业病史列表数据", notes = "职业性问诊-职业病史列表数据")
    public R<IPage<RemindPatientVo>> getRemindPatient(PageParamSimple pageParamSimple, RemindPatientParam param) {
        PageParam<RemindPatientVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<RemindPatientVo> iPage = divisionService.getRemindPatient(page, param);
        return R.ok(iPage);
    }


    /**
     * 职业病名称
     *
     * @param key
     * @return
     */
    @GetMapping("/getAutoCompleteData")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "获取职业病名称", notes = "获取职业病名称")
    @ApiImplicitParam(name = "key", value = "输入码")
    public R<List<OccupationDiseast>> getAutoCompleteData(String key) {
        //职业病名称
        List<OccupationDiseast> list = occupationDiseastService.list(new QueryWrapper<OccupationDiseast>()
                .and(wrapper ->
                        wrapper.like("input_code", key.trim().toUpperCase())
                        .or().like("occupation_diseast", key.trim().toUpperCase()))
                .eq("is_delete", 0)
                .orderByAsc("input_code"));
        return R.ok(list);
    }


    /**
     * 职业性问诊-审核
     *
     * @param param
     * @return
     */
    @PostMapping("/commonjobinquirySave")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "职业性问诊-审核", notes = "职业性问诊-审核")
    public R commonjobinquirySave(@RequestBody ComSaveParam param) {
        Boolean b = divisionService.commonjobinquirySave(param);
        return R.toResult(b);
    }


    /**
     * 职业性问诊-反审核
     *
     * @param id
     * @param ksID
     * @return
     */
    @PutMapping("/commonjobinquiryReverse")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "职业性问诊-反审核", notes = "职业性问诊-反审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id"),
            @ApiImplicitParam(name = "ksID", value = "科室id")
    })
    public R commonjobinquiryReverse(String id, String ksID) {
        Boolean b = divisionService.commonjobinquiryReverse(id, ksID);
        return R.toResult(b);
    }


    /**
     * 当前分中心所有医生
     *
     * @param ksID
     * @param key
     * @return
     */
    @GetMapping("/allDoctors")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "当前分中心当前科室的所有医生", notes = "当前分中心当前科室的所有医生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ksID", value = "科室id"),
            @ApiImplicitParam(name = "key", value = "key")
    })
    public R<List<AllUserDataVo>> allDoctors(String ksID, String key) {
        //职业病名称
        String cId = SecurityUtils.getCId();
        List<AllUserDataVo> list = divisionService.allDoctors(cId, ksID, key);
        return R.ok(list);
    }


    /**
     * 结论词搜索
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getConclusion")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "结论词搜索(图像检查)", notes = "总检结论词搜索")
    @ApiImplicitParam(name = "key", value = "输入码input_code")
    public R<IPage<Basconclusion>> getConclusion(PageParamSimple pageParamSimple, String key) {
        PageParam<Basconclusion> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<Basconclusion> iPage = divisionService.getConclusion(page, key);
        return R.ok(iPage);
    }


    /**
     * 结论词保存(图像检查)
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdateJlc")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "结论词保存", notes = "结论词保存")
    @ApiImplicitParam(name = "key", value = "输入码input_code")
    public R saveOrUpdateJlc(@RequestBody saOrUpJlcParam param) {
        Boolean b = divisionService.saveOrUpdateJlc(param);
        return R.toResult(b);
    }


    /**
     * 小结(图像检查)
     *
     * @param param
     * @return
     */
    @PostMapping("/autosave")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "小结(图像检查)", notes = "小结(图像检查)")
    public R autosave(@RequestBody AutoSaveParam param) {
        Boolean b = divisionService.autosave(param);
        return R.toResult(b);
    }


    /**
     * 审核(图像检查,视力检查)
     *
     * @param param
     * @return
     */
    @PostMapping("/shenhe")
    @Log(title = "审核(图像检查)", businessType = BusinessType.INSERT)
    @ApiOperation(value = "审核(图像检查)", notes = "审核(图像检查)")
    public R shenhe(@RequestBody AutoSaveParam param) {
        Boolean b = divisionService.shenhe(param);
        return R.toResult(b);
    }


    /**
     * 反审核
     *
     * @param patientcode
     * @param ksID
     * @return
     */
    @PutMapping("/caseReverse")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "反审核(图像检查)", notes = "反审核(图像检查)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "ksID", value = "科室id")
    })
    public R caseReverse(String patientcode, String ksID) {
        Boolean b = divisionService.caseReverse(patientcode, ksID);
        return R.toResult(b);
    }


    /**
     * 职业性问诊-家族病弹窗数据
     *
     * @param pageParamSimple
     * @param inputCode
     * @return
     */
    @GetMapping("/getJzbData")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "职业性问诊-家族病弹窗数据", notes = "职业性问诊-家族病弹窗数据")
    @ApiImplicitParam(name = "inputCode", value = "输入码")
    public R<PageParam<WzJzb>> getJzbData(PageParamSimple pageParamSimple, String inputCode) {
        PageParam<WzJzb> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //条件构造器
        QueryWrapper<WzJzb> queryWrapper = new QueryWrapper<>();
        if (ObjectUtils.isNotEmpty(inputCode)) {
            queryWrapper.like("input_code", inputCode);
        }
        PageParam<WzJzb> PageParam = wzJzbService.page(page, queryWrapper.orderByAsc("input_code"));
        return R.ok(PageParam);
    }


    /**
     * 普通预览科室报告
     * @param param
     * @return
     */
    @GetMapping("/diagnosticReport")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "普通预览科室报告", notes = "普通预览科室报告，条形码是根据体检号生成的，前端处理")
    public R<DiagnosticVo> diagnosticReport(DiagnosticParam param) {
        param.setShowHeaders(1);
        DiagnosticVo vo = divisionService.diagnosticReport(param);
        return R.ok(vo);
    }


    /**
     * 图片科室预览报告
     * @param param
     * @return
     */
    @GetMapping("/picReport")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "图片科室预览报告", notes = "图片科室预览报告，条形码是根据体检号生成的，前端处理")
    public R<PicReportVo> picReport(DiagnosticParam param) {
        param.setShowHeaders(1);
        PicReportVo vo = divisionService.picReport(param);
        return R.ok(vo);
    }


    /**
     * 检验科科室报告
     * @param param
     * @return
     */
    @GetMapping("/inspectReport")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "检验科科室报告", notes = "检验科科室报告，条形码是根据体检号生成的，前端处理")
    public R<InspectReportVo> inspectReport(DiagnosticParam param) {
        param.setShowHeaders(1);
        InspectReportVo vo = divisionService.inspectReport(param);
        return R.ok(vo);
    }




    /**
     * 检验科科室报告
     * @param patientcode
     * @return
     */
    @GetMapping("/getAdiconPictures")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "获取艾迪康图片", notes = "获取艾迪康图片")
    public R<List<Attachment>> getAdiconPictures(String patientcode) {
        //艾迪康自动上传的图片
        List<Attachment> attachments = attachmentService.list(new LambdaQueryWrapper<Attachment>()
                .eq(Attachment::getPatientcode, patientcode)
                .eq(Attachment::getType, 1)
                .eq(Attachment::getFileType, "艾迪康报告")
                .eq(Attachment::getInReport, 1)
        );
        return R.ok(attachments);
    }




    /**
     * 检验科科室报告
     * @param id
     * @return
     */
    @DeleteMapping("/deleteAdiconPictures")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "删除艾迪康图片", notes = "删除艾迪康图片")
    public R deleteAdiconPictures(String id) {
        attachmentService.remove(new LambdaQueryWrapper<Attachment>().in(Attachment::getId,id.split(",")));
        return R.ok(Boolean.TRUE);
    }




    /**
     * 获取科室数据范围配置
     * @return
     */
    @GetMapping("/getDeptDataConfig")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "获取科室数据范围配置", notes = "获取科室数据范围配置")
    public R<DeptDataConfig> getDeptDataConfig() {
        DeptDataConfig deptDataConfig = iSysConfigService.getSysConfigObject(Constants.DEPT_DATA_CONFIG, DeptDataConfig.class);
        return R.ok(deptDataConfig);
    }
}

