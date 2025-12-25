package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.bean.param.MeSaOrUpParam;
import com.center.medical.bean.param.MemberParam;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.vo.MedicalCardVo;
import com.center.medical.finance.service.CardService;
import com.center.medical.member.bean.param.AddJfParam;
import com.center.medical.member.bean.param.SaveReportParam;
import com.center.medical.member.bean.vo.MemberListVo;
import com.center.medical.member.bean.vo.MerExportVo;
import com.center.medical.member.bean.vo.ReChargeVo;
import com.center.medical.member.service.MemberService;
import com.center.medical.sellcrm.bean.model.Createcombo;
import com.center.medical.sellcrm.bean.vo.CoSimpleVo;
import com.center.medical.sellcrm.service.CreatecomboService;
import com.center.medical.service.PeispatientarchiveService;
import com.center.medical.system.service.ISysUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员管理-中心会员(Peispatientarchive)控制层
 *
 * @author 路飞船长
 * @since 2022-11-24 10:04:19
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "会员管理-中心会员")
@RequestMapping("member/member")
public class MemberController extends BaseController {
    /**
     * 服务对象
     */
    private final PeispatientarchiveService peispatientarchiveService;
    private final MapperFacade mapperFacade;
    private final CreatecomboService createcomboService;
    private final CardService cardService;
    private final MemberService memberService;
    private final ISysUserService sysUserService;


    /**
     * 【会员管理-中心会员】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【会员管理-中心会员】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取分中心数据", "GET", "/sell/monthtarget/getBranchData", "06.客户销售系统->销售管理-销售目标-销售月度目标->获取分中心数据", null),
                new InterfaceVo("分中心下所有销售人员信息", "GET", "/sell/customerTransfer/getXsryData", "06.客户销售系统->销售管理-销售目标-销售月度目标->获取本分中心下所有销售人员信息", null),
                new InterfaceVo("单位名称下拉", "GET", "/data/dwHarm/getAllCompany", "06.客户销售系统->销售管理-销售目标-销售月度目标->获取所有的公司客户名称", null),
                new InterfaceVo("客户经理下拉", "GET", "/report/healthGetReport/getAllUserData", "06.客户销售系统->销售管理-销售目标-销售月度目标->获取所有的公司客户名称", null),
                new InterfaceVo("套餐下拉", "GET", "/member/member/getAutoCompleteData", "07.会员系统->客服管理-会员管理-中心会员->套餐下拉", null),
                new InterfaceVo("分页查询", "GET", "/member/member/page", "07.会员系统->客服管理-会员管理-中心会员->分页查询", null),
                new InterfaceVo("详情", "GET", "/member/member/edit", "07.会员系统->客服管理-会员管理-中心会员->详情", null),
                new InterfaceVo("添加", "POST", "/member/member/saveOrUpdate", "07.会员系统->客服管理-会员管理-中心会员->添加", null),
                new InterfaceVo("会员升级", "PUT", "/member/member/up", "07.会员系统->客服管理-会员管理-中心会员->会员升级", null),
                new InterfaceVo("导出", "POST", "/member/member/export", "07.会员系统->客服管理-会员管理-中心会员->导出", null),
                new InterfaceVo("体检卡搜索", "GET", "/member/member/getMedicalCardAutoComData", "07.会员系统->客服管理-会员管理-中心会员->体检卡搜索", null),
                new InterfaceVo("积分充值详情", "GET", "/member/member/recharge", "07.会员系统->客服管理-会员管理-中心会员->积分充值详情", null),
                new InterfaceVo("积分充值-搜索会员卡号", "GET", "/member/member/getMemberData", "07.会员系统->客服管理-会员管理-中心会员->积分充值-搜索会员卡号", null),
                new InterfaceVo("积分充值-保存", "POST", "/member/member/addJf", "07.会员系统->客服管理-会员管理-中心会员->积分充值-保存", null),
                new InterfaceVo("挂失保存数据", "POST", "/member/member/saveReport", "07.会员系统->客服管理-会员管理-中心会员->挂失保存数据", null)
        );
        return R.ok(new FunctionVo("07.会员系统", "会员管理-中心会员", interfaceVos, "07.会员系统"));
    }


    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询体检者档案表")
    public R<IPage<MemberListVo>> getPage(PageParamSimple pageParamSimple, MemberParam param) {
        PageParam<MemberListVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<MemberListVo> iPage = memberService.getMemberListData(page, param);
        return R.ok(iPage);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/edit")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查体检者档案表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R edit(String id) {
        Map<String, Object> data = new HashMap<>();
        //体检者档案表
        Peispatientarchive peisPatientArchive = peispatientarchiveService.getInfoById(id);
        //客户经理
        String khjlId = ObjectUtils.isNotEmpty(peisPatientArchive) ? peisPatientArchive.getKhjl() : "";
        if (khjlId != null) {
            SysUser user = sysUserService.selectUserByUserNo(khjlId);
            if (user != null) {
                String khjl = user.getUserName();
                peisPatientArchive.setKhjl(khjl);
            }
        }
        data.put("peisPatientArchive", peisPatientArchive);
        if (peisPatientArchive.getPatientcardno() != null) {
            //获取卡数据
            Map<String, Object> map = memberService.getCardData(peisPatientArchive.getPatientcardno());
            data.put("map", map);
        }
        return R.ok(data);
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "会员管理中心会员添加")
    @Log(title = "会员中心", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peispatientarchive.id"})
    public R insert(@RequestBody MeSaOrUpParam param) {
        return R.toResult(this.memberService.SaOrUp(param));
    }


    /**
     * 修改数据
     *
     * @param ids 实体对象
     * @return 修改结果
     */
    @PutMapping("/up")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "会员升级", notes = "会员升级")
    @Log(title = "会员中心", businessType = BusinessType.UPDATE)
    public R up(@RequestParam List<String> ids) {
        return R.toResult(this.peispatientarchiveService.up(ids));
    }


    /**
     * 导出会员列表数据
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "中心会员导出数据")
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "会员中心", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, MemberParam param) {
        List<MerExportVo> list = memberService.getExport(param);
        ExcelUtil<MerExportVo> util = new ExcelUtil<MerExportVo>(MerExportVo.class);
        util.exportExcel(response, list, "会员列表数据");
    }


    /**
     * 套餐下拉
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getAutoCompleteData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "套餐下拉", notes = "套餐下拉")
    @ApiImplicitParam(name = "key", value = "查询条件")
    public R<IPage<CoSimpleVo>> getAutoCompleteData(PageParamSimple pageParamSimple, String key) {
        PageParam<Createcombo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        Long userId = SecurityUtils.getUserId();
        IPage<CoSimpleVo> iPage = createcomboService.getAutoCompleteData(page, key, userId);
        return R.ok(iPage);
    }


    /**
     * 体检卡搜索
     *
     * @param key
     * @return
     */
    @GetMapping("/getMedicalCardAutoComData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "体检卡搜索", notes = "体检卡搜索")
    public R<List<MedicalCardVo>> getMedicalCardAutoComData(String key) {
        List<MedicalCardVo> list = cardService.getMedicalCardAutoComData(key);
        return R.ok(list);
    }


    /**
     * 积分充值详情
     *
     * @param id
     * @return
     */
    @GetMapping("/recharge")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "积分充值详情", notes = "积分充值详情")
    @ApiImplicitParam(name = "id", value = "档案id")
    public R<ReChargeVo> recharge(String id) {
        ReChargeVo vo = new ReChargeVo();
        if (StringUtils.isNotEmpty(id)) {
            Peispatientarchive peisPatientArchive = peispatientarchiveService.getInfoById(id);
            Card card = cardService.getOne(new QueryWrapper<Card>().eq("card_no", peisPatientArchive.getPatientcardno()));
            vo.setPeispatientarchive(peisPatientArchive);
            vo.setCard(card);
        }
        String cid = SecurityUtils.getCId();
        vo.setCid(cid);
        String userName = SecurityUtils.getUsername();
        vo.setUserName(userName);
        return R.ok(vo);
    }


    /**
     * 积分充值-搜索会员卡号
     *
     * @param patientcardno
     * @param phone
     * @return
     */
    @GetMapping("/getMemberData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "积分充值-搜索会员卡号", notes = "返回success:false,前端自行处理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcardno", value = "会员卡号"),
            @ApiImplicitParam(name = "phone", value = "手机号")
    })
    public R getMemberData(String patientcardno, String phone) {
        Map<String, Object> map = memberService.getMemberData(patientcardno, phone);
        return R.ok(map);
    }

    /**
     * 积分充值-积分充值
     *
     * @param param
     * @return
     */
    @PostMapping("/addJf")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "积分充值-积分充值", notes = "积分充值-积分充值")
    public R addJf(@RequestBody AddJfParam param) {
        Boolean b = memberService.addJf(param);
        return R.toResult(b);
    }


    /**
     * 挂失保存数据
     *
     * @param param
     * @return
     */
    @PostMapping("/saveReport")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "挂失保存数据", notes = "挂失保存数据")
    public R saveReport(@RequestBody SaveReportParam param) {
        Boolean b = memberService.saveReport(param);
        return R.toResult(b);
    }


    /**
     * 会员卡挂失页面数据
     * @param id
     * @return
     */
    @GetMapping("/report")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "会员卡挂失页面数据", notes = "会员卡挂失页面数据")
    @ApiImplicitParam(name = "id", value = "档案id")
    public R report(String id) {
        //体检者档案表
        Map<String,Object> map = new HashMap<>();
        Peispatientarchive peisPatientArchive = peispatientarchiveService.getInfoById(id);
        map.put("peisPatientArchive",peisPatientArchive);
        if (ObjectUtils.isNotEmpty(peisPatientArchive)){
            //体检卡
            Card card = cardService.getInfoByNo(peisPatientArchive.getPatientcardno());
            map.put("card",card);
        }
        return R.ok(map);
    }
}

