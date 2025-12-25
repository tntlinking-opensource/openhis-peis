package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.ModifyStatusParam;
import com.center.medical.abteilung.bean.param.SignInInspectionParam;
import com.center.medical.abteilung.bean.param.UpdateItemsDealParam;
import com.center.medical.abteilung.bean.vo.PopDataVo;
import com.center.medical.abteilung.bean.vo.ViewThirdPartyImagesVo;
import com.center.medical.abteilung.service.SignInInspectionService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.bean.param.HarmParam;
import com.center.medical.data.bean.vo.NotificationmethodVo;
import com.center.medical.data.service.HarmService;
import com.center.medical.data.service.ItemsService;
import com.center.medical.data.service.NotificationmethodService;
import com.center.medical.sellcrm.bean.model.Createcombo;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.service.CreatecomboService;
import com.center.medical.sellcrm.service.CreatemealService;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.bean.param.SysDeptParam;
import com.center.medical.system.bean.vo.DeptSimpleVo;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 检完签到(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-01-15 16:06:42
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "检完签到")
@RequestMapping("/SignInInspection")
public class SignInInspectionController extends BaseController {
    /**
     * 服务对象
     */
    private final SignInInspectionService signInInspectionService;
    private final MapperFacade mapperFacade;
    private final CreatecomboService createcomboService;
    private final CreatemealService createmealService;
    private final ISysBranchService iSysBranchService;
    private final ISysDeptService sysDeptService;
    private final NotificationmethodService notificationmethodService;
    private final HarmService harmService;
    private final PeispatientService peispatientService;
    private final ItemsService itemsService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;
    private final ISysConfigService iSysConfigService;


    /**
     * 【检完签到】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【检完签到】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取会员类型", "GET", "/total/RecordManage/getPatientTypeData", "10.总检-报告系统->总检管理-对比报告->获取会员类型", null),
                new InterfaceVo("婚姻下拉", "GET", "/total/RecordManage/getMarriageData", "11.pacs/lis->PACS-PACS登记信息查询-登记->婚姻下拉", null),
                new InterfaceVo("支付方式", "GET", "/dictpayway/getPayWayData", "支付方式", null),
                new InterfaceVo("获取民族数据", "GET", "/total/RecordManage/getNationData", "10.总检-报告系统->总检管理-对比报告->获取民族数据", null)
        );
        return R.ok(new FunctionVo("09.科室系统", "检完签到", interfaceVos, "09.科室系统"));
    }

    /**
     * 体检者查询
     *
     * @param patientCode
     * @param autoFill
     * @param key
     * @return
     */
    @GetMapping("/getRecheckItems")
    @ApiOperation(value = "体检者查询", notes = "体检者查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "autoFill", value = "是否补0 ，true是false否"),
            @ApiImplicitParam(name = "key", value = "key")
    })
    public R getRecheckItems(String patientCode, String autoFill, String key) {
        return R.ok(this.signInInspectionService.getRecheckItems(patientCode, autoFill, key));
    }


    /**
     * 得到备单人员的信息
     *
     * @param patientCode
     * @param id
     * @return
     */
    @GetMapping("/getPatientData")
    @ApiOperation(value = "得到备单人员的信息", notes = "得到备单人员的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "id", value = "id")
    })
    public R getPatientData(String patientCode, String id) {
        return R.ok(this.signInInspectionService.getPatientData(patientCode, id));
    }


    /**
     * 获取套餐对应的折后价格
     *
     * @param tcid
     * @return
     */
    @GetMapping("/getTcZhPrice")
    @ApiOperation(value = "获取套餐对应的折后价格", notes = "根据套餐id，查询最小套餐或普通套餐，返回")
    @ApiImplicitParam(name = "tcid", value = "套餐id")
    public R getTcZhPrice(String tcid) {
        // 获取该套餐的折后价格
        Createcombo combo = createcomboService.getInfoById(tcid);
        Createmeal createMeal = createmealService.getInfoById(tcid);
        if (null != combo && null == createMeal) {
            return R.ok(combo);
        } else if (null == combo && null != createMeal) {
            return R.ok(createMeal);
        }
        return R.ok("{'zhjg':0}");
    }


    /**
     * 检查危急值
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/checkDanger")
    @ApiOperation(value = "检查危急值", notes = "检查危急值,返回0或1")
    @ApiImplicitParam(name = "patientcode", value = "体检码")
    public R checkDanger(String patientcode) {
        // 体检号补0
        patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
        return R.ok(signInInspectionService.checkDanger(patientcode));
    }


    /**
     * 根据输入码获取科室
     *
     * @param key
     * @return
     */
    @GetMapping("/getAllks")
    @ApiOperation(value = "根据条件获取科室", notes = "根据条件获取科室")
    @ApiImplicitParam(name = "key", value = "key，输入码")
    public R<List<DeptSimpleVo>> getAllks(String key) {
        SysDeptParam param = new SysDeptParam();
        if (ObjectUtils.isNotEmpty(key)) {
            param.setInputCode(key.trim());
        }
        param.setKsh("不为空");
        param.setCjsjpx("不为空");
        List<DeptSimpleVo> list = sysDeptService.getAllDepartment(param);
        return R.ok(list);
    }


    /**
     * 获取通知方式列表
     *
     * @return
     */
    @GetMapping("/getIssueWayData")
    @ApiOperation(value = "获取通知方式列表", notes = "获取通知方式列表")
    public R<List<NotificationmethodVo>> getIssueWayData() {
        List<NotificationmethodVo> list = notificationmethodService.getIssueWayData();
        return R.ok(list);
    }

    /**
     * 获取接害因素
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getJhysData")
    @ApiOperation(value = "获取接害因素", notes = "获取接害因素")
    @ApiImplicitParam(name = "key", value = "key，输入码")
    public R<IPage<Harm>> getJhysData(PageParamSimple pageParamSimple, String key) {
        PageParam<Harm> page = mapperFacade.map(pageParamSimple, PageParam.class);
        HarmParam param = new HarmParam();
        if (ObjectUtils.isNotEmpty(key)) {
            param.setInputCode(key.trim());
        }
        param.setIsDelete(0);
        param.setLv(2);
        IPage<Harm> harmDataSimple = harmService.getHarmDataSimple(page, param);
        return R.ok(harmDataSimple);
    }


    /**
     * 交单
     *
     * @param patientCode
     * @return
     */
    @PutMapping("/surrender")
    @ApiOperation(value = "交单", notes = "交单")
    @ApiImplicitParam(name = "patientCode", value = "体检号")
    @Log(title = "检完签到-交单", businessType = BusinessType.INSERT)
    public R surrender(@RequestParam List<String> patientCode) {
        Boolean b = signInInspectionService.surrender(patientCode);
        return R.toResult(b);
    }


    /**
     * 预约、登记、保存右侧列表
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdateItem")
    @ApiOperation(value = "弃检、反弃检,迟检、反迟检", notes = "弃检、反弃检,迟检、反迟检")
    @Log(title = "检完签到-弃检迟检(反)", businessType = BusinessType.INSERT)
    public R saveOrUpdateItem(@RequestBody SignInInspectionParam param) throws Exception {
        Peispatient pei = peispatientService.getByPatientCode(param.getPatientCode());
        if (ObjectUtils.isEmpty(pei)) {
            throw new ServiceException("该体检者体检号不存在，不能保存收费项目！");
        }
        if (pei.getJktjzt() != null && pei.getJktjzt() == 1) {
            throw new ServiceException("操作失败，本体检者已被" + (pei.getDoctorfinalNameR() == null ? "" : pei.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (pei.getZytjzt() != null && pei.getZytjzt() == 1) {
            throw new ServiceException("操作失败，本体检者已被" + (pei.getPatientnameencoded() == null ? "" : pei.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (pei.getFFinallocked() != null && pei.getFFinallocked() == 1) {
            throw new ServiceException("操作失败，本体检者已被" + (pei.getIdDoctorapply() == null ? "" : pei.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (pei.getIdGuidenurse() != null && pei.getIdGuidenurse() == 1) {
            throw new ServiceException("操作失败，本体检者已被" + (pei.getParsedassignedsuiteandfi() == null ? "" : pei.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        String s = signInInspectionService.saveOrUpdateItem(pei, param.getGriddata(), param.getIntReservation(), param.getFormdata(), param.getNoticeJyk(),param.getPath());
        return R.ok(s);
    }


    /**
     * 预约、登记、保存右侧列表
     *
     * @param param
     * @return
     */
    @PostMapping("/updateItemsDeal")
    @ApiOperation(value = "补检，反补检", notes = "补检，反补检")
    @Log(title = "检完签到-补检", businessType = BusinessType.INSERT)
    public R updateItemsDeal(@RequestBody UpdateItemsDealParam param) {
        Boolean b = signInInspectionService.updateItemsDeal(param);
        return R.toResult(b);
    }


    /**
     * 拒检
     *
     * @param ids
     * @param data
     * @return
     */
    @PutMapping("/jujiann")
    @ApiOperation(value = "拒检", notes = "拒检")
    @Log(title = "检完签到-拒检", businessType = BusinessType.INSERT)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "id集合"),
            @ApiImplicitParam(name = "data", value = "签名图片地址")
    })
    public R jujian(@RequestParam List<String> ids, @RequestParam String data) throws Exception {
        Boolean b = signInInspectionService.jujian(ids, data);
        return R.toResult(b);
    }


    /**
     * 反拒检
     *
     * @param ids
     * @return
     */
    @PutMapping("/fanjujian/{ids}")
    @ApiOperation(value = "反拒检", notes = "反拒检")
    @ApiImplicitParam(name = "ids", value = "id集合")
    @Log(title = "检完签到-反拒检", businessType = BusinessType.INSERT)
    public R fanjujian(@PathVariable List<String> ids) {
        Boolean b = signInInspectionService.fanjujian(ids);
        return R.toResult(b);
    }


    /**
     * 查看图片弹窗
     *
     * @param viewPhotoId
     * @return
     */
    @GetMapping("/viewItemPhoto")
    @ApiOperation(value = "查看图片弹窗", notes = "查看图片弹窗,有图片的返回地址（你得拼接前面的路径），没图片的返回空")
    @ApiImplicitParam(name = "viewPhotoId", value = "收费项目id")
    public R viewItemPhoto(String viewPhotoId) {
        String viewPath = "";
        //根据收费项目id获取收费项目实体,再获取图片路径,进行图片加载
        Items items = itemsService.getInfoById(viewPhotoId);
        if (ObjectUtils.isNotEmpty(items) && ObjectUtils.isNotEmpty(items.getInputcodee())) {
            viewPath = items.getInputcodee();
        }
        return R.ok(viewPath);
    }


    /**
     * 获取自助项目弹窗数据,某些需要客户自己测的项目需要登记完成检测或弃检
     * @param patientCode
     * @return
     */
    @GetMapping("/getPopData")
    @ApiOperation(value = "获取自助项目弹窗数据", notes = "获取自助项目弹窗数据,某些需要客户自己测的项目需要登记完成检测或弃检")
    @ApiImplicitParam(name = "patientCode", value = "体检号")
    public R getPopData(String patientCode) {
        List<PopDataVo> list = signInInspectionService.getPopData(patientCode);
        return R.ok(list);
    }




    /**
     * 获取自助项目弹窗数据,某些需要客户自己测的项目需要登记完成检测或弃检
     * @param ids
     * @return
     */
    @PutMapping("/setPopData")
    @ApiOperation(value = "完成检测", notes = "设置获取自助项目弹窗数据，设为已检")
    @ApiImplicitParam(name = "ids", value = "id集合")
    @Log(title = "护理登记完成检测", businessType = BusinessType.UPDATE)
    public R setPopData(@RequestParam List<String> ids) {
        Boolean b = signInInspectionService.setPopData(ids);
        return R.ok(b);
    }


    /**
     * 自助项目弃检
     * @param ids
     * @return
     */
    @PutMapping("/popGiveUp")
    @ApiOperation(value = "自助项目弃检", notes = "自助项目弃检")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "id集合"),
            @ApiImplicitParam(name = "path", value = "签名地址")
    })
    @Log(title = "检完签到-自助项目弃检", businessType = BusinessType.INSERT)
    public R popGiveUp(@RequestParam List<String> ids , @RequestParam String path) {
        Boolean b = signInInspectionService.popGiveUp(ids,path);
        return R.ok(b);
    }


    /**
     * 查询第三方系统报告
     *
     * @param patientcode
     * @param feeItemId
     * @return
     */
    @GetMapping("/queryThirdReports")
    @ApiOperation(value = "查询第三方系统报告", notes = "查询第三方系统报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "feeItemId", value = "收费项目id")
    })
    public R<List<String>> queryThirdReports(String patientcode, String feeItemId) {
        List<String> list  = attachmentService.queryThirdReports(patientcode, feeItemId);
        return R.ok(list);
    }



    /**
     * 查询第三方系统报告
     *
     * @param patientcode
     * @param feeItemId
     * @return
     */
    @PostMapping("/uploadThirdReports")
    @ApiOperation(value = "上传第三方系统报告", notes = "上传第三方系统报告")
    public R uploadThirdReports(MultipartFile file, String feeItemId ,String patientcode) {
        String cId = SecurityUtils.getCId();
        Boolean b = signInInspectionService.uploadThirdReports(file, feeItemId, patientcode, cId);
        return R.ok(b);
    }




    /**
     * 查询第三方系统报告
     *
     * @param patientcode
     * @param feeItemId
     * @return
     */
    @PostMapping("/deleteThirdReports")
    @ApiOperation(value = "删除第三方系统报告", notes = "删除第三方系统报告")
    public R deleteThirdReports(String feeItemId ,String patientcode,String filePath) {
        Boolean b = signInInspectionService.deleteThirdReports(feeItemId,patientcode,filePath);
        return R.ok(b);
    }



    /**
     * 修改项目状态-查询
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/modifyProjectStatus")
    @ApiOperation(value = "检完签到-已检未检", notes = "检完签到-已检未检")
    @Log(title = "检完签到已检未检", businessType = BusinessType.INSERT)
    public R modifyProjectStatus(@Valid @RequestBody ModifyStatusParam param) {
        Boolean b = signInInspectionService.modifyProjectStatus(param);
        return R.ok(b);
    }




    /**
     * 修改项目状态-查询
     *
     * @param param
     * @return
     */
    @GetMapping(value = "/viewThirdPartyImages")
    @ApiOperation(value = "查看第三方图片", notes = "查看第三方图片")
    public R modifyProjectStatus(String patientcode) {
        List<ViewThirdPartyImagesVo> list = signInInspectionService.viewThirdPartyImages(patientcode);
        return R.ok(list);
    }
}

