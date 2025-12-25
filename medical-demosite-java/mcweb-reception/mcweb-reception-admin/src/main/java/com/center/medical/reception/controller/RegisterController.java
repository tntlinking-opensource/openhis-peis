package com.center.medical.reception.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.*;
import com.center.medical.common.config.AppointmentSmsConfig;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.abteilung.service.SignInInspectionService;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.param.ConfirmOrderParam;
import com.center.medical.bean.param.ExportItemsParam;
import com.center.medical.bean.param.PoGroupParam;
import com.center.medical.bean.vo.ExportItemsVo;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.bean.model.Patienttype;
import com.center.medical.data.bean.param.SfxmDataParam;
import com.center.medical.data.bean.vo.ItemsByTcVo;
import com.center.medical.data.bean.vo.SfxmDataVo;
import com.center.medical.data.service.HarmService;
import com.center.medical.data.service.ItemsService;
import com.center.medical.data.service.PatienttypeService;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.service.CardService;
import com.center.medical.finance.service.PhysicalCheckoutService;
import com.center.medical.olddata.bean.model.OrPeispatient;
import com.center.medical.olddata.service.OrPeispatientService;
import com.center.medical.pacslis.service.MiddleDbInterfaceService;
import com.center.medical.reception.bean.dto.RefundFeeItemDto;
import com.center.medical.reception.bean.dto.RegisterDto;
import com.center.medical.reception.bean.param.*;
import com.center.medical.reception.bean.vo.*;
import com.center.medical.reception.service.ItemListService;
import com.center.medical.reception.service.OrderService;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.reception.service.RegisterService;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.service.PingAnService;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.bean.param.ApproveTjtcDataParam;
import com.center.medical.sellcrm.bean.param.CreatecomboParam;
import com.center.medical.sellcrm.bean.param.ZxtcsDataParam;
import com.center.medical.sellcrm.bean.vo.*;
import com.center.medical.sellcrm.service.*;
import com.center.medical.service.*;
import com.center.medical.system.bean.param.SysDeptParam;
import com.center.medical.system.bean.vo.AllKsVo;
import com.center.medical.system.bean.vo.DeptSimpleVo;
import com.center.medical.system.bean.vo.GetDoctorVo;
import com.center.medical.system.bean.vo.LqrDataVo;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.*;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 前台-登记管理
 *
 * @author 路飞船长
 * @since 2023-03-01 16:32:25
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 1)
@Api(tags = "前台-登记管理")
@RequestMapping("/reception/register")
public class RegisterController extends BaseController {
    /**
     * 服务对象
     */
    private final RegisterService registerService;
    private final MapperFacade mapperFacade;
    private final ISysBranchService iSysBranchService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final ISysUserService iSysUserService;
    private final PeisorgreservationService peisorgreservationService;
    private final ItemsService itemsService;
    private final ISysDeptService sysDeptService;
    private final CreatemealService createmealService;
    private final PeispatientService peispatientService;
    private final OutsideMainService outsideMainService;
    private final SignInInspectionService signInInspectionService;
    private final OrderandcomboService orderandcomboService;
    private final PhysicalCheckoutService physicalCheckoutService;
    private final DictpaywayService dictpaywayService;
    private final ISysUserService sysUserService;
    private final SellcustomerService sellcustomerService;
    private final CreatecomboService createcomboService;
    private final PatienttypeService patienttypeService;
    private final ItemListService itemListService;
    private final CardService cardService;
    private final CreateorderService createorderService;
    private final HarmService harmService;
    private final PeispatienthistoryService peispatienthistoryService;
    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;
    private final MiddleDbInterfaceService middleDbInterfaceService;
    private final PeispatientarchiveService peispatientarchiveService;
    private final CodeCorrespondingService codeCorrespondingService;
    private final OrPeispatientService orPeispatientService;
    private final PeispatientChargeMainService peispatientChargeMainService;
    private final PeispatientPhotoService peispatientPhotoService;
    private final ISysConfigService iSysConfigService;
    private final ReservationService reservationService;
    private final OrderandfzxService orderandfzxService;
    private final BranchService branchService;
    private final OrderService orderService;
    private final PingAnService pingAnService;
    @Autowired
    private LoadProperties loadProperties;




    /**
     * 【前台-登记管理】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【前台-登记管理】这个块业务所有接口及接口说明", position = 1)
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("数据重发", "POST", "/pacs_list/middleDbInterface/insert", "13.PACS系统->中间库接口对接->插入中间库", null),
                new InterfaceVo("重复：获取已登记的体检人员信息", "GET", "/reception/register/page", "08.前台-预约系统->前台-登记管理->分页查询", null),
                new InterfaceVo("推项：获取体检套餐列表", "GET", "/sell/createcombo/list", "06.客户-销售模块->基础数据-最小套餐->分页列表", null),
                new InterfaceVo("推项：获取收费项目列表", "GET", "/items/page", "03.基础数据模块->收费项目设置->分页查询", null),
                new InterfaceVo("体检者查询", "GET", "/SignInInspection/getRecheckItems", "科室->检完签到->体检者查询", null),
                new InterfaceVo("获取会员类型", "GET", "/total/RecordManage/getPatientTypeData", "10.总检-报告系统->总检管理-对比报告->获取会员类型", null),
                new InterfaceVo("婚姻下拉", "GET", "/total/RecordManage/getMarriageData", "11.pacs/lis->PACS-PACS登记信息查询-登记->婚姻下拉", null),
                new InterfaceVo("获取通知方式", "GET", "/SignInInspection/getIssueWayData", "科室->检完签到->获取通知方式", null),
                new InterfaceVo("获取民族数据", "GET", "/total/RecordManage/getNationData", "10.总检-报告系统->总检管理-对比报告->获取民族数据", null),
                new InterfaceVo("获取籍贯", "GET", "/dictpayway/getAreaData", "03.基础数据模块->收银收款方式->获取籍贯", null),
                new InterfaceVo("获取开单医师", "GET", "/member/previewingTrack/getKdys", "客服系统->客服管理-回访管理-个检预检回访->获取开单医师", null),
                new InterfaceVo("获取套餐对应的折后价格", "GET", "/SignInInspection/getTcZhPrice", "科室->检完签到->获取套餐对应的折后价格", null),
                new InterfaceVo("获取接害因素", "GET", "/abteilung/division/getJhysData", "科室->职业性问诊->获取接害因素", null)
        );

        return R.ok(new FunctionVo("08.前台-预约系统", "前台-登记管理", interfaceVos, "08.前台-预约系统"));
    }

    /**
     * 分页查询登记信息列表
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询登记信息列表", position = 2)
    public R<IPage<RegisterVo>> getPage(PageParamSimple pageParamSimple, RegisterParam param) {
        PageParam<Peispatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.registerService.getPage(page, param));
    }


    /**
     * 前台-登记管理-根据身份证号获取体检号
     */
    @PostMapping("/getPatientcodeByIdcard/{idCard}")
    @Log(title = "前台-登记管理-根据身份证号获取体检号", businessType = BusinessType.INSERT)
    @ApiOperation(value = "根据身份证号获取体检号", notes = "前台-登记管理-根据身份证号获取体检号", position = 2)
    public R<List<IdcarPatientVo>> getPatientcodeByIdcard(@PathVariable String idCard) {
        log.info("前台-登记管理-根据身份证号获取体检号：{}", idCard);
        if (StringUtils.isBlank(idCard)) {
            throw new GlobalException("身份证号不能为空！");
        }
        return R.ok(registerService.getPatientcodeByIdcard(idCard));
    }


    /**
     * 前台-登记管理-拍照上传
     */
    @PostMapping("/upload")
    //@PreAuthorize("@ss.hasPermi('::upload')")
    @Log(title = "前台-登记管理-拍照上传", businessType = BusinessType.INSERT)
    @ApiOperation(value = "拍照上传", notes = "前台-登记管理-拍照上传", position = 2)
    public R<String> upload(MultipartFile file) throws Exception {
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.APP.value());
        //线上系统将文件存储至阿里云的OSS中
        Attachment attachment = new Attachment();
        String extName = FileUtil.extName(file.getOriginalFilename());
        attachment.setFileType("体检者头像");
        attachment.setType(AttachmentType.IMAGE.value());
        attachment.setBranchId(SecurityUtils.getCId());
        attachment.setCreatedate(new Date());
        attachmentService.uploadFile(file, attachment, baseDir, extName, null, true);
        log.info("上传结果：{}、{}", attachment.getId(), attachment);
        return R.ok(attachment.getFilePath());
    }

    /**
     * 前台-登记管理-完成登记
     *
     * @param registerDto 登记信息
     * @return 新增结果
     */
    @PostMapping("/insert")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    @PreAuthorize("@ss.hasPermi('registration:registration:add')")
    @ApiOperation(value = "完成登记", notes = "前台-登记管理-完成登记", position = 3)
    @Log(title = "前台-登记-完成登记", businessType = BusinessType.INSERT)
    public R<RegisterResultVo> insert(@RequestBody RegisterDto registerDto) {
//        log.info("前台-登记管理-新增登记:userNo={}、registerDto={}", SecurityUtils.getUserNo(), JSONUtil.toJsonStr(registerDto));
        //判断该分中心可以登记该体检类型的
        Boolean examtypeOpen = iSysConfigService.getExamtypeOpen(registerDto.getIdExamtype());
        if (!examtypeOpen){
            throw new GlobalException("该中心未开启登记该体检类型！");
        }

        if (StringUtils.isNotBlank(registerDto.getPatientcode())) {
            //没有预约能否登记
            AppointmentSmsConfig config = iSysConfigService.getSysConfigObject(Constants.APPOINTMENT_SMS_CONFIG, AppointmentSmsConfig.class);
            if (Objects.nonNull(config) && config.getIsOpen()) {
                Reservation reservation = reservationService.getOne(new LambdaQueryWrapper<Reservation>()
                        .eq(Reservation::getPatientcode, registerDto.getPatientcode())
                        .eq(Reservation::getStatus, 2)
                );
                if (config.getRegistration() && ObjectUtils.isEmpty(reservation)) {
                    throw new GlobalException("请先预约后再完成登记！");
                }
            }

            //判断是否老系统导入的体检号,如果老系统登记过的话就要提示一下，如果没登记需要在老系统禁检并备注
            //去重后体检号可能会变，用id查询吧
            if (StringUtils.isNotEmpty(registerDto.getId()) && !StringUtils.equals(loadProperties.name, "jindu")) {
                //是否开启老系统
                if (iSysConfigService.oldSystemOpen()){
                    OrPeispatient orPeispatient = orPeispatientService.getInfoById(registerDto.getId());
                    if (ObjectUtils.isNotEmpty(orPeispatient)) {
                        if ("1".equals(orPeispatient.getFRegistered())){
                            throw new GlobalException("该体检号老系统已登记,新系统不能操作!");
                        }
                        //老系统体检号设为禁检
                        orPeispatientService.setFPaused(orPeispatient);
                    }
                }
                //查询是否在别的区已经登记
                String s = "";
                try {
                    String url = iSysConfigService.getDomain().getPlatformDomain() + Constants.OTHER_IS_REGISTERED;
                    s = HttpUtils.sendGet(url,"id="+registerDto.getId()+ "&fzx="+ iSysBranchService.getDefaultBranch().getBranchId());
                    log.info("查询是否在别的区已经登记url:{},返回结果是：{}",url,s);
                }catch (Exception e){
                    log.error("查询是否在别的区已经登记失败！体检号：{},错误信息：{}",registerDto.getPatientcode(),e.getMessage());
                }
                if (StringUtils.isNotEmpty(s)){
                    R responseEntity = JSONUtil.toBean(s, R.class);
                    if (200 != responseEntity.getCode()) {
                        throw new ServiceException(responseEntity.getMsg());
                    }
                }

            }

            //验证是否有未退费的退项,如果有不能收费，必须先退费
            R<Peispatient> peispatientR = refundValidate(registerDto.getPatientcode(), 0);
            if (R.isError(peispatientR)) {
                throw new GlobalException("该体检者存在已退项未退费的收费项目，请先退费再收费！");
            }
        }
        //黑名单备注添加时间
        if (registerDto.getIsHmd() == 1 && StringUtils.isNotEmpty(registerDto.getIsHmdb())) {
            //最后两位是数字的话就视为已经插入时间了
            boolean b = Pattern.matches(".*\\d{2}$", registerDto.getIsHmdb());
            if (!b) {
                registerDto.setIsHmdb(registerDto.getIsHmdb() + " " + LocalDate.now());
            }
        }
        RegisterResultVo registerResultVo = this.registerService.saOrUp(registerDto, 1);

        //疫苗自动交单
        registerService.autoCompare(registerResultVo.getPatientcode());

        //插入中间库
        if (peispatientfeeitemService.unpaidItems(registerResultVo.getPatientcode()) == 0) {
            //拼接体检号
            middleDbInterfaceService.insert(registerResultVo.getPatientcode());
        }
        if (ZhongkangConfig.isOnline()){
            //
        }else {
            Runnable task = () -> {
                // 多中心备单的体检者登记后，删除其他非到检的中心的体检数据


            };
            Thread thread = new Thread(task);
            thread.start();
        }
        return R.ok(registerResultVo);
    }

    /**
     * 前台-登记-保存
     *
     * @param registerDto 登记信息
     * @return 新增结果
     */
    @PostMapping("sava")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    @PreAuthorize("@ss.hasPermi('registration:registration:save')")
    @ApiOperation(value = "保存登记", notes = "前台-登记-保存", position = 3)
    @Log(title = "前台-登记-保存", businessType = BusinessType.UPDATE)
    public R<RegisterResultVo> save(@RequestBody RegisterDto registerDto) {
//        log.info("前台-登记-保存:userNo={}、registerDto={}", SecurityUtils.getUserNo(), JSONUtil.toJsonStr(registerDto));
        //验证是否有未退费的退项,如果有不能收费，必须先退费
        if (StringUtils.isNotBlank(registerDto.getPatientcode())) {
            R<Peispatient> peispatientR = refundValidate(registerDto.getPatientcode(), 0);
            if (R.isError(peispatientR)) {
                throw new GlobalException("该体检者存在已退项未退费的收费项目，请先退费再收费！");
            }
            //查询是否在别的区已经登记
            String s = "";
            try {
                String url = iSysConfigService.getDomain().getPlatformDomain() + Constants.OTHER_IS_REGISTERED;
                s = HttpUtils.sendGet(url,"id="+registerDto.getId()+ "&fzx="+ iSysBranchService.getDefaultBranch().getBranchId());
                log.info("查询是否在别的区已经登记url:{},返回结果是：{}",url,s);
            }catch (Exception e){
                log.error("查询是否在别的区已经登记失败！体检号：{},错误信息：{}",registerDto.getPatientcode(),e.getMessage());
            }
            if (StringUtils.isNotEmpty(s)){
                R responseEntity = JSONUtil.toBean(s, R.class);
                if (200 != responseEntity.getCode()) {
                    throw new ServiceException(responseEntity.getMsg());
                }
            }
        }
        Boolean aBoolean = SecurityUtils.hasRole("reception:registration:superadd");
        //黑名单备注添加时间
        if (registerDto.getIsHmd() == 1 && StringUtils.isNotEmpty(registerDto.getIsHmdb())) {
            //最后两位是数字的话就视为已经插入时间了
            boolean b = Pattern.matches(".*\\d{2}$", registerDto.getIsHmdb());
            if (!b) {
                registerDto.setIsHmdb(registerDto.getIsHmdb() + " " + LocalDate.now());
            }
        }
        RegisterResultVo vo = this.registerService.saOrUp(registerDto, 2);


        return R.ok(vo);
    }

//    public static void main(String[] args) {
//        System.out.println(DateUtil.beginOfDay(new Date()));
//        System.out.println(DateUtil.endOfDay(new Date()));
//    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查QT体检者表详情", position = 4)
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Peispatient> selectOne(@PathVariable String id) {
        return R.ok(this.registerService.getInfoById(id));
    }

    /**
     * 验证是否有未退费的退项,如果有不能收费，必须先退费
     *
     * @param patientCode 体检号
     * @param autoFill    是否需要补全体检号：0.否 1.是
     * @return
     */
    @GetMapping("/refundValidate")
//    @PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "验证是否有未退费的退项", notes = "验证是否有未退费的退项,如果有不能收费，必须先退费", position = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "autoFill", value = "是否需要补全体检号：0.否 1.是")
    })
    public R<Peispatient> refundValidate(@RequestParam(value = "patientCode") String patientCode,
                                         @RequestParam(value = "autoFill") Integer autoFill) {
        if (autoFill == 1) {
            patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        }
        LambdaQueryWrapper<Peispatientfeeitem> wrapper = new LambdaQueryWrapper<Peispatientfeeitem>()
                .eq(Peispatientfeeitem::getIdPatient, patientCode)
                .eq(Peispatientfeeitem::getChangeItem, 1);//已退项
        wrapper.and(w -> {
            w.isNull(Peispatientfeeitem::getFMarkFeereturn).or().eq(Peispatientfeeitem::getFMarkFeereturn, 0);//未退费
        });
        long count = peispatientfeeitemService.count(wrapper);
        if (count > 0) {
            return R.fail("该体检者存在已退项未退费的收费项目，请先退费再收费！");
        } else {
            return R.ok();
        }
    }

    /**
     * 获取加项医师列表
     *
     * @param key 加项医生名字首字母
     * @return
     */
    @GetMapping("/getJxys")
//    @PreAuthorize("@ss.hasPermi('system:user:getJxys')")
    @ApiOperation(value = "获取加项医师列表", notes = "获取加项医师列表", position = 6)
    @ApiImplicitParam(name = "key", value = "加项医生名字首字母")
    public R<List<GetDoctorVo>> getJxys(String key) {
        String cId = SecurityUtils.getCId();
        //去空格
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim();
        }
        return R.ok(iSysUserService.getDoctor(cId, key));
    }

    /**
     * 得到备单人员的信息
     *
     * @param patientCode
     * @param id
     * @return
     */
    @GetMapping("/getPatientData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "基本信息 ", notes = "得到备单人员的基本信息 ", position = 7)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "id", value = "体检者id")
    })
    public R getPatientData(String patientCode, String id) {
        HashMap map = registerService.getPatientData(id, patientCode);
        return R.ok(map);
    }


    /**
     * 获取体检者与收费项目信息
     *
     * @param patientCode
     * @param type
     * @param autoFill
     * @return
     */
    @GetMapping("/getCustomerData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取体检者与收费项目信息", notes = "获取体检者与收费项目信息", position = 8)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "type", value = "类型"),
            @ApiImplicitParam(name = "autoFill", value = "是否需要补全体检号：0.否 1.是")
    })
    public R<CustomerDataVo> getCustomerData(@RequestParam(value = "patientCode") String patientCode,
                                             @RequestParam(value = "type") Integer type,
                                             @RequestParam(value = "autoFill") Integer autoFill) {
        if (org.apache.commons.lang3.StringUtils.isBlank(patientCode)) {
            throw new GlobalException("体检号不能为空！");
        }
        if (autoFill == 1) {
            patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        }
        return R.ok(this.registerService.getCustomerData(patientCode, type, autoFill));
    }


    /**
     * 前台-登记管理-退项
     *
     * @param refundFeeItemDto 退项信息
     * @return 操作结果
     */
    @PostMapping("/updateTui")
    @PreAuthorize("@ss.hasPermi('reception:registration:return')")
    @ApiOperation(value = "退项", notes = "前台-登记管理-退项", position = 12)
    @Log(title = "前台-登记-退项", businessType = BusinessType.UPDATE)
    public R<Integer> updateTui(@RequestBody RefundFeeItemDto refundFeeItemDto) {
//        log.info("前台-登记管理-退项:userNo={}、refundFeeItemDto={}", SecurityUtils.getUserNo(), JSONUtil.toJsonStr(refundFeeItemDto));

        return this.registerService.updateTui(refundFeeItemDto);
    }


    /**
     * 前台-登记-退项-退项恢复
     *
     * @param id 要操作的体检者收费项目ID
     * @return
     */
    @PutMapping("/returnItem")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "退项恢复", notes = "前台-登记-退项-退项恢复")
    @ApiImplicitParam(name = "id", value = "体检者收费项目ID")
    @Log(title = "前台-登记-退项-退项恢复", businessType = BusinessType.UPDATE)
    public R returnItem(@RequestParam String id) {
        return R.toResult(registerService.returnItem(id));
    }


    /**
     * 收费项目导出Excel
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出Excel", notes = "登记列表导出Excel")
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "登记列表导出Excel", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, RegisterParam param) {
        List<RCExportVo> list = registerService.getExportData(param);
        ExcelUtil<RCExportVo> util = new ExcelUtil<RCExportVo>(RCExportVo.class);
        util.exportExcel(response, list, "登记列表导出Excel");
    }


    /**
     * 取得已预约客户
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getReservationUser")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "取得已预约客户", notes = "取得已预约客户")
    public R<IPage<ReservationUserVo>> getReservationUser(PageParamSimple pageParamSimple, ReservationUserParam param) {
        PageParam<ReservationUserVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(registerService.getReservationUser(page, param));
    }


    /**
     * 获取所有科室
     *
     * @param key
     * @return
     */
    @GetMapping("/getAllks")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取所有科室", notes = "获取所有科室")
    @ApiImplicitParam(name = "key", value = "搜索的输入码")
    public R<List<AllKsVo>> getAllks(String key) {
        List<AllKsVo> list = sysDeptService.getAllks(key);
        return R.ok(list);
    }


    /**
     * 加载所有最小套餐--按照分中心
     *
     * @param param
     * @return
     */
    @GetMapping("/getZxtcsData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "加载所有最小套餐--按照分中心", notes = "加载所有最小套餐--按照分中心")
    @ApiImplicitParam(name = "key", value = "key")
    public R<IPage<ZxtcsDataVo>> getZxtcsData(PageParamSimple pageParamSimple, ZxtcsDataParam param) {
        PageParam<ZxtcsDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<ZxtcsDataVo> iPage = createmealService.getZxtcsData(page, param);
        return R.ok(iPage);
    }


    /**
     * 通过身份证号获取最近一次人员信息
     *
     * @param idcardno
     * @param patientcode
     * @return
     */
    @GetMapping("/getDataByIdcard")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "通过身份证号获取最近一次人员信息", notes = "通过身份证号获取最近一次人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "idcardno", value = "身份证号")
    })
    public R<Map> getDataByIdcard(String idcardno, String patientcode) {
        Map<String, Object> result = new HashMap<>();
        QueryWrapper<Peispatient> and = new QueryWrapper<>();
        and.eq("idcardno", idcardno);
        and.eq("f_registered", 1);
        //体检号
        if (StringUtils.isNotEmpty(patientcode)) {
            and.ne("patientcode", patientcode);
        }
        //查询
        List<Peispatient> patients = peispatientService.list(and.orderByDesc("dateregister"));
        if (patients.size() == 0) {
            result.put("isExists", 0);
        } else {
            Peispatient p = patients.get(0);
            result.put("isExists", 1);
            result.put("idSex", p.getIdSex());
            result.put("patientname", p.getPatientname());
            result.put("phone", p.getPhone());
            //黑名单等
            Peispatientarchive peispatientarchive = peispatientarchiveService.getInfoByNo(p.getPatientarchiveno());
            result.put("ishmd", peispatientarchive.getIshmd());
            result.put("hmdbz", peispatientarchive.getHmdbz());
            //体检者头像
            for (Peispatient patient : patients) {
                String picture = peispatientPhotoService.getPictureByCode(patient.getPatientcode());
                if (StringUtils.isNotEmpty(picture)) {
                    result.put("picture", picture);
                    break;
                }
            }
        }
        return R.ok(result);
    }


    /**
     * 获取档案记录
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getRecordListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取档案记录", notes = "获取档案记录")
    @ApiImplicitParam(name = "key", value = "key")
    public R<IPage<RecordListVo>> getRecordListData(PageParamSimple pageParamSimple, RecordListParam param) {
        PageParam<RecordListVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<RecordListVo> iPage = registerService.getRecordListData(page, param);
        return R.ok(iPage);
    }


    /**
     * 根据姓名等查询体检者
     *
     * @param param
     * @return
     */
    @GetMapping("/recordMatch")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据姓名等查询体检者", notes = "根据姓名等查询体检者,返回-1表示不存在，返回单条数据表示只有一个，返回0表示有多个")
    public R recordMatch(RecordMatchParam param) {
        HashMap<String, Object> map = outsideMainService.recordMatch(param);
        return R.ok(map);
    }


    /**
     * 获取体检者收费项目
     *
     * @param tcid
     * @param idOrder
     * @param idGroup
     * @return
     */
    @GetMapping("/getExamfeeitemData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取体检者收费项目", notes = "获取体检者收费项目")
    public R getExamfeeitemData(String tcid, String idOrder, String idGroup) {
        List list = signInInspectionService.getExamfeeitemData(tcid, idOrder, idGroup);
        return R.ok(list);
    }


    /**
     * 获取审核订单下关联的套餐
     *
     * @param pageParamSimple
     * @param approveTjtcDataParam
     * @return
     */
    @GetMapping("/getApproveTjtcData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取审核订单下关联的套餐", notes = "获取审核订单下关联的套餐")
    public R<Map> getApproveTjtcData(PageParamSimple pageParamSimple, ApproveTjtcDataParam approveTjtcDataParam) {
        PageParam<Orderandcombo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        Map map = orderandcomboService.getApproveTjtcData(page, approveTjtcDataParam);
        return R.ok(map);
    }


    /**
     * 预付方式数据
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getChargeData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "预付方式数据", notes = "预付方式数据")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R getChargeData(String patientcode) {
        List<Map<String, Object>> list = physicalCheckoutService.getChargeData(patientcode);
        return R.ok(list);
    }


    /**
     * 获取全部支付方式
     *
     * @param pageParamSimple 分页参数
     * @param key             inputCode
     * @return 所有数据
     */
    @GetMapping("/getPayWayData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取全部支付方式", notes = "获取全部支付方式")
    public R<IPage<Dictpayway>> getPayWayData(PageParamSimple pageParamSimple, String key, String type) {
        PageParam<Dictpayway> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //设置条件
        QueryWrapper<Dictpayway> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 0);
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.like("input_code", key.trim().toUpperCase());
        }
        //预收不能选择记账
        if ("1".equals(type)) {
            queryWrapper.ne("id", "4");
        }
        PageParam<Dictpayway> pageParam = dictpaywayService.page(page, queryWrapper.orderByAsc("seq"));
        return R.ok(pageParam);
    }


    /**
     * 领取人处搜索
     *
     * @param pageParamSimple
     * @param srm
     * @return
     */
    @GetMapping("/getLqrData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "领取人处搜索", notes = "领取人处搜索")
    @ApiImplicitParam(name = "srm", value = "输入码")
    public R<IPage<LqrDataVo>> getLqrData(PageParamSimple pageParamSimple, String srm) {
        PageParam<LqrDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<LqrDataVo> iPage = sysUserService.getLqrData(page, srm);
        return R.ok(iPage);
    }


    /**
     * 获取记账单位
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getJzOrg")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取记账单位", notes = "获取记账单位")
    @ApiImplicitParam(name = "key", value = "名称或输入码")
    public R<IPage<JzOrgVo>> getJzOrg(PageParamSimple pageParamSimple, String key) {
        PageParam<JzOrgVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<JzOrgVo> iPage = sellcustomerService.getJzOrg(page, key);
        return R.ok(iPage);
    }


    /**
     * 从基础数据获取收费项目
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getSfxmData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "推荐收费项目-收费项目", notes = "推荐收费项目-收费项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tjValue", value = "体检类型数值"),
            @ApiImplicitParam(name = "syxbValue", value = "适用性别数值--用于调取不同类别的收费项目")
    })
    public R<IPage<SfxmDataVo>> getSfxmData(PageParamSimple pageParamSimple, SfxmDataParam param) {
        PageParam<Items> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<SfxmDataVo> iPage = itemsService.getSfxmData(page, param);
        return R.ok(iPage);
    }


    /**
     * 分页查询最小套餐
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getListData")
    @PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "推荐收费项目-查看套餐", notes = "推荐收费项目-查看套餐")
    public R<IPage<Createcombo>> selectAll(PageParamSimple pageParamSimple, CreatecomboParam param) {
        PageParam<Createcombo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.createcomboService.getList(page, param));
    }


    /**
     * 根据条件查询全部收费项目数据
     *
     * @param tcId 查询条件
     * @return 所有数据
     */
    @GetMapping("/getItemsData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "推荐收费项目-查看套餐下的检查项目", notes = "推荐收费项目-查看套餐下的检查项目")
    public R<List<ItemsByTcVo>> getItemsData(String tcId) {
        List<ItemsByTcVo> list = itemsService.getItemsDataByTcId(tcId);
        return R.ok(list);
    }


    /**
     * 问卷-数据
     *
     * @return
     */
    @GetMapping("/add")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "问卷-数据", notes = "问卷-数据")
    @ApiImplicitParam(name = "id", value = "体检者id")
    public R<List<QuestionsVo>> add(String id) {
        List<QuestionsVo> result = registerService.addQuestions(id);
        return R.ok(result);
    }


    /**
     * 返回设置数据
     *
     * @return
     */
    @GetMapping("/returnList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "返回设置数据", notes = "返回设置数据")
    public R<ReturnListVo> returnList() {
        Integer closeChongfa = Constants.CLOSE_CHONGFA;
        String lisPacsUrl = Constants.PACS_IP;
        String userName = SecurityUtils.getUsername();
        String cid = SecurityUtils.getCId();
        String dydStyle = Constants.DYDSTYLE;
        if (dydStyle == null || "".equals(dydStyle)) {
            dydStyle = "1";
        } else {
            dydStyle = dydStyle.trim();
        }
        //返回数据
        ReturnListVo returnListVo = new ReturnListVo(closeChongfa, lisPacsUrl, userName, cid, dydStyle);
        return R.ok(returnListVo);
    }


    /**
     * 完成登记时，当前是第几次来本体检中心
     * getDataByIdcard
     *
     * @param id
     * @param patientId
     * @return
     */
    @GetMapping("/getTjTime")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "完成登记时，当前是第几次来本体检中心", notes = "完成登记时，当前是第几次来本体检中心")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "档案号"),
            @ApiImplicitParam(name = "patientId", value = "体检号")
    })
    public R<Map> getTjTime(String id, String patientId) {
        Map<String, String> text = new HashMap<String, String>();
        //个数
        long count = peispatientService.count(new QueryWrapper<Peispatient>().eq("patientarchiveno", id));
        text.put("count", Long.toString(count));
        Peispatient peispatient = peispatientService.getByPatientCode(patientId);
        //体检者类型
        Patienttype patienType = patienttypeService.getInfoById(peispatient.getIdPatientclass());
        text.put("patientClass", patienType.getPatientName());
        return R.ok(text);
    }


    /**
     * 问卷-保存
     *
     * @return
     */
    @PostMapping("/getAnswer")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "问卷-保存", notes = "问卷-保存")
    public R getAnswer(@RequestBody String params) {
        Map map = registerService.getAnswer(params);
        return R.ok(map);
    }


    /**
     * 团体列表-搜索
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getGroupForOrgData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "团体列表-搜索", notes = "团体列表-搜索")
    public R<IPage<POSimpleVo>> getGroupForOrgData(PageParamSimple pageParamSimple, PoGroupParam param) {
        PageParam<Peisorgreservation> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<POSimpleVo> iPage = peisorgreservationService.getGroupForOrgData(page, param);
        return R.ok(iPage);
    }


    /**
     * 最近体检人员列表
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getPatientForRegister")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "最近体检人员列表", notes = "获取已登记的体检人员信息")
    public R<IPage<PaForReVo>> getPatientForRegister(PageParamSimple pageParamSimple, PaForReParam param) {
        PageParam<PaForReVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<PaForReVo> iPage = registerService.getPatientForRegister(page, param);
        return R.ok(iPage);
    }


    /**
     * 根据体检号查询体检者信息
     *
     * @param patientCode
     * @param autoFill
     * @return
     */
    @GetMapping("/getPeispatient")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据体检号查询体检者信息", notes = "根据体检号查询体检者信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "autoFill", value = "是否补0 ，true是false否")
    })
    public R<GetPeispatientVo> getPeispatient(String patientCode, String autoFill) {
        GetPeispatientVo vo = registerService.getPeispatient(patientCode, autoFill);
        return R.ok(vo);
    }


    /**
     * 获取收费项目数据
     *
     * @param patientCode
     * @param type
     * @return
     */
    @GetMapping("/getExamfeeByPatientCode")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取右侧收费项目", notes = "获取右侧收费项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "type", value = "类型，0全部显示，1显示除去退项的，2显示退项的")
    })
    public R<List<Map<String, Object>>> getExamfeeByPatientCode(String patientCode, String type) {
        return R.ok(this.itemListService.getExamfeeByPatientCode(patientCode, type));
    }


    /**
     * 分頁获取所有功能部门
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getKsData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分頁获取所有功能部门（科室）", notes = "分頁获取所有功能部门（科室）")
    public R<IPage<DeptSimpleVo>> getKsData(PageParamSimple pageParamSimple, SysDeptParam param) {
        PageParam<DeptSimpleVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(sysDeptService.findAllDepartment(page, param));
    }


    /**
     * 根据体检号查询不同状态的收费项目
     *
     * @param patientCode
     * @param type
     * @param inpatientno
     * @return
     */
    @GetMapping("/getKindItems")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据体检号查询不同状态的收费项目", notes = "根据体检号查询不同状态的收费项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "type", value = "类型,复查0,补查1，复查+补查2"),
            @ApiImplicitParam(name = "inpatientno", value = "复查体检号")
    })
    public R<Map> getKindItems(String patientCode, String type, String inpatientno) {
        Map map = registerService.getKindItems(patientCode, type, inpatientno);
        return R.ok(map);
    }


    /**
     * 套餐卡
     *
     * @param cardno
     * @return
     */
    @GetMapping("/getCardTc")
//    @PreAuthorize("@ss.hasPermi('::send')")
    @ApiOperation(value = "套餐卡", notes = "套餐卡", position = 4)
    @ApiImplicitParam(name = "cardno", value = "卡号")
    public R getCardTc(String cardno) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(cardno)) {
            return R.ok("请输入会员卡号！");
        }
        //查询卡
        Card card = cardService.getOne(new QueryWrapper<Card>()
                .eq("card_no", cardno).eq("is_delete", 0)
                .in("type_id", new String[]{Card.TEN, Card.ACT, Card.GROUP})//2021.01.18添加团检专属卡,逻辑与其他两种卡一样
        );
        if (card == null) {
            return R.ok("卡号不存在！");
        }
        //赠送套餐是否已用：0或null.否 1.是
        if (card.getTcChecked() != null && card.getTcChecked() == 1) {
            return R.ok("该卡号赠送的套餐已被体检号：" + card.getTcPatientcode()
                    + "使用！");
        }
        //团检专属卡类型id
        if (Card.GROUP.equals(card.getTypeId())) {
            Map<String, Object> tcdata = new HashMap<>();
            //订单表
            Createorder order = createorderService.getInfoById(card.getOrderId());
            tcdata.put("idInformway", order.getIdInforway());
            tcdata.put("idOrg", order.getKhdwmcid());
            tcdata.put("ddid", order.getId());
            //客户表
            Sellcustomer customer = sellcustomerService.getInfoById(order.getKhdwmcid());
            tcdata.put("orgName", customer.getKhdwmc());
            tcdata.put("idOpendoctor", order.getXsjlid());
            tcdata.put("idOpendoctorName", order.getXsjl());
            tcdata.put("numorgresv", order.getDdh());
            //普通套餐表
            Createmeal meal = createmealService.getInfoById(card.getTcId());
            tcdata.put("idExamtype", meal.getTjlx());
            tcdata.put("jhys", meal.getJhys());
            String jhysn = "";
            //接害因素
            if (meal.getJhys() != null) {
                List<Harm> harms = harmService.list(new QueryWrapper<Harm>().in("id", meal.getJhys().split(",")));
                Set<String> harmNames = new HashSet<>();
                for (Harm harm : harms) {
                    harmNames.add(harm.getHarmName());
                }
                //拼接接害因素
                jhysn = org.apache.commons.lang3.StringUtils.join(harmNames.toArray(), ",");
            }
            tcdata.put("jhysn", jhysn);
            tcdata.put("zytjlb", meal.getZytjlb());
            return R.ok("success@" + card.getTcId() + "@@1@@" + JSONUtil.toJsonStr(tcdata));
        } else {
            //团检专属卡不需要售卡即可使用
            if (card.getSellStatus() == null || card.getSellStatus().intValue() != 1) {
                return R.ok("该卡号的状态是未售，不能使用。");
            }
            return R.ok("success@" + card.getTcId() + "@@0@@{}");
        }
    }


    /**
     * 批量登记
     *
     * @param ids
     * @return
     */
    @PostMapping("/saveBatchRegister")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "批量登记", notes = "批量登记")
    @ApiImplicitParam(name = "ids", value = "id集合")
    @Log(title = "批量登记", businessType = BusinessType.INSERT)
    public R saveBatchRegister(@RequestParam List<String> ids) {
        String result = registerService.saveBatchRegister(ids);

        //插入中间库
        List<Peispatient> patients = peispatientService.list(new QueryWrapper<Peispatient>().in("id", ids));
        List<String> patientCodes = new ArrayList<>();
        for (Peispatient patient : patients) {
            if (peispatientfeeitemService.unpaidItems(patient.getPatientcode()) == 0) {
                //拼接体检号
                patientCodes.add(patient.getPatientcode());
            }
        }
        if (patientCodes.size() > 0) {
            String join = String.join(",", patientCodes);
            middleDbInterfaceService.insert(join);
        }

        return R.ok(result);
    }


    /**
     * 备单人员登记操作
     *
     * @param id
     * @return
     */
    @GetMapping("/getBdRegisterData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "备单人员登记操作", notes = "备单人员登记操作")
    @ApiImplicitParam(name = "id", value = "id")
    public R getBdRegisterData(String id) {
        // 判断是否为空
        if (!StringUtils.isBlank(id)) {
            Peispatient peispatient = peispatientService.getInfoById(id);
            // 判断选中的体检者是否存在
            if (null != peispatient) {
                Peispatient peispatientNew = peispatientService.getOne(new QueryWrapper<Peispatient>()
                        .eq("id", id).eq("f_registered", 1));
                // 判断选中的体检者是否已登记
                if (null != peispatientNew) {
                    // 该体检者已经登记！
                    return R.ok("-1");
                } else {
                    return R.ok("success");
                }
            } else {
                // 历史体检者
                Peispatienthistory peispatientHistory = peispatienthistoryService.getById(id);
                // 判断选中的体检者是否存在
                if (null != peispatientHistory) {
                    Peispatienthistory peispatientHistoryNew = peispatienthistoryService.getOne(new QueryWrapper<Peispatienthistory>().eq("id", id).eq("f_registered", 1));
                    // 判断选中的体检者是否已登记
                    if (null != peispatientHistoryNew) {
                        // 该体检者已经登记！
                        return R.ok("-1");
                    } else {
                        return R.ok("success");
                    }
                } else {
                    // 该体检者信息不存在！
                    return R.ok("-2");
                }
            }
        } else
            // 系统发生异常，请联系管理员！
            return R.ok("-3");
    }


    /**
     * 左中-禁检或反禁检
     *
     * @param paused
     * @param ids
     * @return
     */
    @PutMapping("/savePausedStatus")
    @ApiOperation(value = "禁检或反禁检", notes = "禁检或反禁检")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paused", value = "禁检1,反禁检0"),
            @ApiImplicitParam(name = "ids", value = "id集合")
    })
    @Log(title = "禁检或反禁检", businessType = BusinessType.INSERT)
    public R savePausedStatus(@RequestParam Integer paused, @RequestParam List<String> ids) {
        Boolean b = physicalCheckoutService.savePausedStatus(paused, ids);
        return R.toResult(b);
    }


    /**
     * 批量设置统收限额
     *
     * @param param
     * @return
     */
    @PostMapping("/saveTsLimitEdit")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "批量设置统收限额", notes = "批量设置统收限额")
    public R saveTsLimitEdit(@RequestBody TsLimitEditParam param) {
        Boolean b = registerService.saveTsLimitEdit(param);
        return R.toResult(b);
    }


    /**
     * 判断收费项目是否禁止打折
     *
     * @param ids
     * @return
     */
    @GetMapping("/getReadItems")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "判断收费项目是否禁止打折", notes = "判断收费项目是否禁止打折,返回的是可以打折的")
    @ApiImplicitParam(name = "ids", value = "接口中的idExamfeeitem 集合")
    public R getReadItems(@RequestParam List<String> ids) {
        List<Items> items = itemsService.list(new QueryWrapper<Items>().in("id", ids));
        HashMap<String, Object> msgId = new HashMap<String, Object>();
        for (Items item : items) {
            //禁止打折字段，0否 1禁止
            if (null != item.getFDiscountdisabled() && (item.getFDiscountdisabled() != 1)) {
                msgId.put(item.getId(), item.getId());
            }
        }
        return R.ok(msgId);
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "收费项目列表", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出体检者收费项目列表", notes = "导出体检者收费项目列表")
    @PostMapping("/exportItems")
    public void export(HttpServletResponse response, ExportItemsParam param) {
        List<ExportItemsVo> list = peispatientfeeitemService.exportItems(param);
        for (int i = 0; i < list.size(); i++) {
            ExportItemsVo vo = list.get(i);
            vo.setRownum(i + 1);
        }
        ExcelUtil<ExportItemsVo> util = new ExcelUtil<ExportItemsVo>(ExportItemsVo.class);
        util.exportExcel(response, list,"导出体检者收费项目列表","收费项目列表-顾客姓名：" + param.getPatientname() + " 体检号：" + param.getPatientCode());
    }


    /**
     * 修改体检者开单医师和备注
     *
     * @param param
     * @return
     */
    @PostMapping("/saveEdit")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "修改体检者开单医师和备注", notes = "修改体检者开单医师和备注")
    public R saveEdit(@RequestBody RCSaveEditParam param) {
        Boolean b = registerService.saveEdit(param);
        return R.toResult(b);
    }


    /**
     * 批量登记查询
     *
     * @param patientcode
     * @param autoFill
     * @return
     */
    @GetMapping("/getPatientForCode")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "批量登记查询", notes = "批量登记查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "autoFill", value = "是否补0 ，true是false否")
    })
    public R<PatientForOrderIdVo> getPatientForCode(String patientcode, String autoFill) {
        if (StringUtils.isNotEmpty(patientcode)) {
            //体检号补0
            if ("true".equals(autoFill)) {
                patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
                long count = peispatientService.count(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, patientcode));
                if (count == 0){
                    //查不到可能是app开头的体检号
                    patientcode = ToolUtil.patientCodeApp(patientcode);
                }
            }
            return R.ok(registerService.getPatientForCode(patientcode));
        } else {
            throw new ServiceException("error@体检号不能为空");
        }
    }


    /**
     * 获取订单号下拉
     *
     * @param key
     * @param customerId
     * @return
     */
    @GetMapping("/getDdhData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取订单号下拉", notes = "获取订单号下拉")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "订单号"),
            @ApiImplicitParam(name = "customerId", value = "客户id")
    })
    public R<List<DdhDataVo>> getDdhData(String key, String customerId) {
        List<DdhDataVo> list = createorderService.getDdhDatas(key, customerId);
        return R.ok(list);
    }


    /**
     * 获取所有套餐
     *
     * @param key
     * @return
     */
    @GetMapping("/getAllComboAndMealData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取所有套餐", notes = "获取所有套餐")
    @ApiImplicitParam(name = "key", value = "输入码或名称 模糊查询")
    public R<List<AllComboMealVo>> getAllComboAndMealData(String key) {
        List<AllComboMealVo> list = createcomboService.getAllComboAndMealData(key);
        return R.ok(list);
    }


    /**
     * 保存发送短信
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdateMsg")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "保存发送短信", notes = "保存发送短信")
    @Log(title = "保存发送短信", businessType = BusinessType.UPDATE)
    public R<String> saveOrUpdateMsg(@RequestBody SaveOrUpdateMsgParam param) {
        return R.ok(this.registerService.saveOrUpdateMsg(param));
    }


    /**
     * 取消发送短信
     *
     * @param patientcodes
     * @return
     */
    @PutMapping("/cancelSmsPredetection")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "取消发送短信", notes = "取消发送短信")
    public R<Boolean> cancelSmsPredetection(@RequestParam List<String> patientcodes) {
        return R.ok(this.registerService.cancelSmsPredetection(patientcodes));
    }


    /**
     * 绑定档案
     *
     * @param patientcode
     * @return
     */
    @PutMapping("/bingIArchive")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "绑定档案", notes = "绑定档案,返回档案号，自己再手动去体检者表绑定档案号")
    public R<String> bingIArchive(@RequestParam String patientcode) {
        Peispatient peispatient = peispatientService.getByPatientCode(patientcode);
        return R.ok(this.peispatientarchiveService.bingIArchive(peispatient, false,SecurityUtils.getUserNo()));
    }


    /**
     * 根据体检号获取版本号
     *
     * @param patientcode 体检号
     * @return
     */
    @GetMapping("/getVersion")
    @ApiOperation(value = "根据体检号获取版本号", notes = "根据体检号获取版本号")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<Long> getVersion(String patientcode) {
        PeispatientChargeMain peispatientChargeMain = peispatientChargeMainService.getOne(
                new LambdaQueryWrapper<PeispatientChargeMain>().eq(PeispatientChargeMain::getPatientcode, patientcode));
        if (ObjectUtils.isNotEmpty(peispatientChargeMain)) {
            return R.ok(peispatientChargeMain.getVersion());
        }
        return R.ok();
    }


    /**
     * 疫苗自动交单
     *
     * @param patientcodes 体检号集合
     * @return
     */
    @PostMapping("/autoCompare")
    @ApiOperation(value = "疫苗自动交单", notes = "疫苗自动交单")
    @ApiImplicitParam(name = "patientcodes", value = "体检号")
    public R<Boolean> autoCompare(@RequestBody List<String> patientcodes) {
        for (String patientcode : patientcodes) {
            registerService.autoCompare(patientcode);
        }
        return R.ok(Boolean.TRUE);
    }


    /**
     * 疫苗自动交单
     *
     * @param patientcodes 体检号集合
     * @return
     */
    @PostMapping("/setDateregister")
    @ApiOperation(value = "设置没有登记的体检号", notes = "设置已登记没有登记的时间")
    @ApiImplicitParam(name = "patientcodes", value = "体检号")
    public R<Boolean> setDateregister(@RequestBody List<String> patientcodes) {
        Boolean b = registerService.setDateregister(patientcodes);
        return R.ok(b);
    }


    /**
     * 重新设置错误的档案
     *
     * @return
     */
    @PostMapping("/setUpProfile")
    @ApiOperation(value = "重新设置错误的档案", notes = "重新设置错误的档案")
    @ApiImplicitParam(name = "patientcodes", value = "体检号")
    public R<Boolean> setUpProfile(@RequestBody List<String> patientcodes) {
        Boolean b = registerService.setUpProfile(patientcodes);
        return R.ok(b);
    }


    /**
     * 获取最近人员列表
     *
     * @param param
     * @return
     */
    @GetMapping("/getRecentPersonnelList")
    @ApiOperation(value = "获取最近人员列表", notes = "获取最近人员列表")
    public R<IPage<RecentPersonnelListVo>> getRecentPersonnelList(PageParamSimple pageParamSimple, RecentPersonnelListParam param) {
        PageParam<RecentPersonnelListVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<RecentPersonnelListVo> iPage = registerService.getRecentPersonnelList(page, param);
        return R.ok(iPage);
    }


    /**
     * 一键体检者收费项目去重
     *
     * @param patientcode 体检号
     * @return
     */
    @GetMapping("/deduplication/{patientcode}")
    @ApiOperation(value = "体检者收费项目去重", notes = "一键体检者收费项目去重")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R deduplication(@PathVariable String patientcode) {
        return R.ok(peispatientfeeitemService.deduplication(patientcode));
    }





    /**
     * 校正档案号
     *
     * @return
     */
    @PostMapping("/checkPatientarchiveno")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "校正档案号", notes = "校正档案号")
    public R checkPatientarchiveno() {
        String result = registerService.checkPatientarchiveno();
        return R.ok(result);
    }



    /**
     * 校正档案号
     *
     * @return
     */
    @PostMapping("/againRegenCode")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "重新生成体检号", notes = "重新生成体检号")
    public R againRegenCode(@RequestBody List<String> codes) {
        Boolean b = registerService.againRegenCode(codes);
        return R.ok(b);
    }




    /**
     * 插入该分中心的所有危害因素
     *
     * @return
     */
    @PostMapping("/addHarmAndFzx")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "插入分中心的危害因素", notes = "插入该分中心的所有危害因素")
    public R addHarmAndFzx(@RequestBody List<String> fzxId) {
        Boolean b = registerService.addHarmAndFzx(fzxId);
        return R.ok(b);
    }




    /**
     * 插入该分中心的所有危害因素
     *
     * @return
     */
    @GetMapping("/getUserCid")
    @ApiOperation(value = "获取用户分中心", notes = "获取用户分中心及兼职分中心")
    public R<List<Branch>> getUserCid() {
        List<String> cIds = branchService.getUserCid(SecurityUtils.getUserNo());
        List<Branch> list = branchService.list(new QueryWrapper<Branch>().orderByAsc("branch_sort")
                .eq("is_delete", 0).in("branch_id",cIds));
        return R.ok(list);
    }




    /**
     * 修改体检者开单医师和备注
     *
     * @param ddhs
     * @return
     */
    @PostMapping("/recalculatePrices")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "重新计算价格", notes = "重新计算订单下，体检者收费项目的价格")
    public R recalculatePrices(@RequestBody List<String> ddhs) {
        Boolean b = orderService.recalculatePrices(ddhs);
        return R.ok(b);
    }




    /**
     * 获取默认分中心
     *
     * @return
     */
    @GetMapping("/getDefaultBranch")
    @ApiOperation(value = "获取默认分中心", notes = "获取默认分中心")
    public R getDefaultBranch() {
        return R.ok(iSysBranchService.getDefaultBranch());
    }





    /**
     * 修改体检者开单医师和备注
     *
     * @param patientcodes
     * @return
     */
    @PostMapping("/addChangMain")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "添加收费主表", notes = "添加收费主表")
    public R addChangMain(@RequestBody List<String> patientcodes) {
        Boolean b = orderService.addChangMain(patientcodes);
        return R.ok(b);
    }




    /**
     * 到检核销
     * @param param
     * @return
     */
    @PostMapping("/confirmOrder")
    @ApiOperation(value = "到检核销", notes = "到检核销")
    @Log(title = "到检核销", businessType = BusinessType.INSERT)
    public R getOffer(ConfirmOrderParam param) {
        // TODO: 2024/6/28 待测试
        pingAnService.confirmOrder(param.getId(), param.getCaptcha());
        return R.ok(Boolean.TRUE);
    }





    /**
     * 重新绑定档案
     * @param codes
     * @return
     */
    @PostMapping("/reBingArchive")
    @ApiOperation(value = "重新绑定档案", notes = "重新绑定档案")
    public R getOffer(@RequestBody List<String> codes) {
        return R.ok(peispatientarchiveService.reBingArchive(codes));
    }


    /**
     * 补全套餐项目
     *
     * @param patientcode
     * @return
     */
    @PostMapping("/completeTheProject")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    @ApiOperation(value = "补全套餐项目", notes = "补全套餐项目")
    @Log(title = "补全套餐项目", businessType = BusinessType.INSERT)
    public R completeTheProject(@RequestParam String patientcode) {
        Boolean b = orderService.completeTheProject(patientcode);
        return R.ok(b);
    }



    /**
     * 拉取线上体检者数据
     *
     * @param patientcode
     * @return
     */
    @PostMapping("/pullOnlineData")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    @ApiOperation(value = "拉取线上体检者数据", notes = "拉取线上体检者数据")
    @Log(title = "拉取线上体检者数据", businessType = BusinessType.INSERT)
    public R pullOnlineData(@RequestParam String patientcode) {
        Boolean b = orderService.pullOnlineData(patientcode,0);
        return R.ok(b);
    }




    /**
     * 补全套餐项目
     *
     * @param patientcodes
     * @return
     */
    @PostMapping("/batchCompletionProject")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    @ApiOperation(value = "批量补全套餐项目", notes = "批量补全套餐项目")
    @Log(title = "批量补全套餐项目", businessType = BusinessType.INSERT)
    public R batchCompletionProject(@RequestBody List<String> patientcodes) {
        for (String patientcode : patientcodes) {
            orderService.completeTheProject(patientcode);
        }
        return R.ok(Boolean.TRUE);
    }
}

