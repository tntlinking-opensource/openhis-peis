package com.center.medical.member.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.bean.param.MeSaOrUpParam;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.config.SmsConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.sms.SDKTestSendTemplateSMS;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.service.CardService;
import com.center.medical.member.bean.param.FamilyMemberParam;
import com.center.medical.member.bean.param.SaveChargeParam;
import com.center.medical.member.bean.vo.ChargeInfoVo;
import com.center.medical.member.bean.vo.FMSaOrUpParam;
import com.center.medical.member.bean.vo.FamilyMemberVo;
import com.center.medical.member.bean.vo.InfoListDataVo;
import com.center.medical.member.service.FamilyMemberService;
import com.center.medical.member.service.MemberService;
import com.center.medical.sellcrm.bean.model.Createcombo;
import com.center.medical.sellcrm.bean.vo.CoSimpleVo;
import com.center.medical.sellcrm.service.CreatecomboService;
import com.center.medical.service.PeispatientarchiveService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 家庭会员-家庭会员(Peispatientarchive)表控制层
 *
 * @author ay
 * @since 2023-03-01 10:34:46
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "家庭会员-家庭会员")
@RequestMapping("/member/familyMember")
public class FamilyMemberController extends BaseController {
    /**
     * 服务对象
     */
    private final FamilyMemberService familyMemberService;
    private final MapperFacade mapperFacade;
    private final CardService cardService;
    private final PeispatientarchiveService peispatientarchiveService;
    private final ISysUserService sysUserService;
    private final CreatecomboService createcomboService;
    private final MemberService memberService;
    private final ISysConfigService iSysConfigService;


    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【家庭会员-家庭卡消费】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取全部支付方式", "GET", "/dictpayway/getPayWayData", "07.会员系统->客服管理-家庭会员-家庭卡消费->获取全部支付方式", null),
                new InterfaceVo("获取分中心数据", "GET", "/sell/monthtarget/getBranchData", "06.客户销售系统->销售管理-销售目标-销售月度目标->获取分中心数据", null),
                new InterfaceVo("客户经理下拉", "GET", "/report/healthGetReport/getAllUserData", "07.会员系统->客服管理-家庭会员-家庭卡消费->客户经理下拉", null),
                new InterfaceVo("体检卡搜索", "GET", "/member/member/getMedicalCardAutoComData", "07.会员系统->客服管理-家庭会员-家庭卡消费->体检卡搜索", null),
                new InterfaceVo("分中心下所有销售人员信息", "GET", "/sell/customerTransfer/getXsryData", "07.会员系统->会员管理-会员卡发放->获取本分中心下所有销售人员信息", null)

        );
        return R.ok(new FunctionVo("07.会员系统", "咨询与随访", interfaceVos, "07.会员系统"));
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
    public R<IPage<FamilyMemberVo>> getPage(PageParamSimple pageParamSimple, FamilyMemberParam param) {
        PageParam<FamilyMemberVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<FamilyMemberVo> iPage = familyMemberService.getList(page, param);
        return R.ok(iPage);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查体检者档案表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R selectOne(@PathVariable String id) {
        //返回数据
        Map<String, Object> data = new HashMap<>();
        //体检者档案表
        Peispatientarchive peisPatientArchive = peispatientarchiveService.getInfoById(id);
        if (peisPatientArchive == null) {
            peisPatientArchive = new Peispatientarchive();
        }
        data.put("peispatientarchive", peisPatientArchive);
        String khjlId = peisPatientArchive.getKhjl();
        if (khjlId != null) {
            SysUser user = sysUserService.getUserByNo(khjlId);
            if (ObjectUtils.isNotEmpty(user)) {
                String khjl = user.getUserName();
                data.put("khjl", khjl);
            }
        }
        if (peisPatientArchive.getPatientcardno() != null) {
            Map<String, Object> map = familyMemberService.getCardData(peisPatientArchive.getPatientcardno());
            data.put("cardData", map);
        }
        return R.ok(data);
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveCharge")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "充值-保存", notes = "充值-保存")
    @Log(title = "家庭会员", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peispatientarchive.id"})
    public R saveCharge(@RequestBody SaveChargeParam param) {
        SmsConfig smsConfig = iSysConfigService.getSysConfigObject(Constants.SMS_CONFIG,SmsConfig.class);
        Map<String, Object> result = familyMemberService.saveCharge(param);
        JSONObject obj = JSONUtil.parseObj(result);
        if("success".equals(obj.getStr("status"))) {
            String phone=obj.getStr("phone");
            if(StringUtils.isNotEmpty(phone)) {
                try {
                    SDKTestSendTemplateSMS.sendFamilyCardMsg(smsConfig,phone
                            , obj.getJSONArray("data").toList(String.class).toArray(new String[4])
                            , true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            return R.fail(obj.getStr("msg"));
        }
        return R.ok();
    }


    /**
     * 家庭卡号-加载
     *
     * @param cardno
     * @return
     */
    @GetMapping("/chargeLoadCard")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "家庭卡号-加载", notes = "充值-家庭卡号-加载")
    @ApiImplicitParam(name = "cardno", value = "卡号")
    public R chargeLoadCard(String cardno) {
        //体检卡
        Card card = cardService.getOne(new QueryWrapper<Card>().eq("card_no", cardno));
        Map<String, Object> result = new HashMap<String, Object>();
        if (card == null) {
            throw new ServiceException("卡号:" + cardno + "不存在。");
        }
        //不是家庭卡卡类型id
        if (!Card.JTK.equals(card.getTypeId())) {
            throw new ServiceException("卡号:" + cardno + "不是家庭卡不能在此处充值。");
        }
        //体检者档案表
        Peispatientarchive main = peispatientarchiveService.getOne(new QueryWrapper<Peispatientarchive>()
                .eq("patientcardno", cardno).eq("is_main", 1));
        Map<String, Object> data = new HashMap<String, Object>();
        if (main != null) {
            data.put("patientname", main.getPatientname());
        }
        data.put("startJf", card.getBalanceJf());
        data.put("startMoney", card.getBalanceMoney());
        data.put("cardState", card.getCardState());
        result.put("status", "success");
        result.put("msg", data);
        return R.ok(result);
    }


    /**
     * 编辑-套餐下拉
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getAutoCompleteData")
//    @PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "编辑-套餐下拉", notes = "编辑-套餐下拉", position = 2)
    public R<IPage<CoSimpleVo>> getAutoCompleteData(PageParamSimple pageParamSimple, @RequestParam(value = "key", required = false) String key) {
        PageParam<Createcombo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        Long userId = SecurityUtils.getUserId();
        return R.ok(this.createcomboService.getAutoCompleteData(page, key, userId));
    }


    /**
     * 编辑-保存
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "编辑-保存", notes = "编辑-保存")
    @Log(title = "会员中心", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peispatientarchive.id"})
    public R insert(@RequestBody MeSaOrUpParam param) {
        return R.toResult(this.memberService.SaOrUp(param));
    }


    /**
     * 新增家庭卡办理-其他家庭成员信息-加载
     *
     * @param cardno
     * @return
     */
    @GetMapping("/searchIdcardno")
//    @PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "新增家庭卡办理-其他家庭成员信息-加载", notes = "新增家庭卡办理-其他家庭成员信息-加载")
    @ApiImplicitParam(name = "cardno", value = "身份证号")
    public R<Peispatientarchive> searchIdcardno(String cardno) {
        //体检者档案表
        Peispatientarchive archive = peispatientarchiveService.getOne(new QueryWrapper<Peispatientarchive>().eq("idcardno", cardno));
        Map<String, Object> result = new HashMap<String, Object>();
        if (ObjectUtils.isEmpty(archive)) {
            throw new ServiceException("匹配此身份证号的档案不存在");
        }
        //是否主持卡人
        if (archive.getPatientcardno() != null && archive.getIsMain() != null && archive.getIsMain() == 1) {
            throw new ServiceException("匹配此身份证号的档案是卡号：" + archive.getPatientcardno() + "的主持卡人");
        }

        String khjlid = archive.getKhjl();
        if (StringUtils.isNotEmpty(khjlid)) {
            SysUser user = sysUserService.getUserByNo(khjlid);
            if (user != null) {
                //客户经理名称
                archive.setKhjl(user.getUserName());
            }
        }
        return R.ok(archive);
    }


    /**
     * 检查是否有重复的身份证号
     *
     * @param cardno
     * @return
     */
    @GetMapping("/checkIdcardno")
//    @PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "检查是否有重复的身份证号", notes = "检查是否有重复的身份证号")
    @ApiImplicitParam(name = "cardno", value = "卡号")
    public R<Peispatientarchive> checkIdcardno(String cardno) {
        long i = peispatientarchiveService.count(new QueryWrapper<Peispatientarchive>().eq("idcardno", cardno));
        if (i > 0) {
            throw new ServiceException("已存在有相同身份证号的档案，请直接在家庭卡办理页面输入身份证号。");
        }
        return R.ok();
    }


    /**
     * 新增家庭卡办理-保存
     *
     * @param param
     * @return
     */
    @PostMapping("/saOrUp")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "新增家庭卡办理-保存", notes = "新增家庭卡办理-保存")
    @Log(title = "会员中心", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peispatientarchive.id"})
    public R SaOrUp(@RequestBody FMSaOrUpParam param) {
        Boolean b = familyMemberService.saveOrUpdateOl(param);
        return R.toResult(b);
    }


    /**
     * 新增家庭卡办理-上方数据
     *
     * @param cardno
     * @return
     */
    @GetMapping("/add")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "新增家庭卡办理-上方数据", notes = "新增家庭卡办理-上方数据,userId售卡人id，username售卡人姓名。不传cardno，返回登录人的")
    @ApiImplicitParam(name = "cardno", value = "卡号")
    public R add(String cardno) {
        Map<String, Object> map = familyMemberService.getAddData(cardno);
        return R.ok(map);
    }


    /**
     * 新增家庭卡办理-左下数据
     *
     * @param cardno
     * @return
     */
    @GetMapping("/getChargeInfoData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "新增家庭卡办理-左下数据", notes = "新增家庭卡办理-左下数据")
    @ApiImplicitParam(name = "cardno", value = "卡号")
    public R<IPage<ChargeInfoVo>> getChargeInfoData(PageParamSimple pageParamSimple, String cardno) {
        PageParam<ChargeInfoVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<ChargeInfoVo> iPage = familyMemberService.getChargeInfoData(page, cardno);
        return R.ok(iPage);
    }


    /**
     * 获取其他家庭成员信息
     *
     * @param cardno
     * @return
     */
    @GetMapping("/getInfoListData")
//    @PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取其他家庭成员信息", notes = "获取其他家庭成员信息")
    @ApiImplicitParam(name = "cardno", value = "卡号")
    public R<List<InfoListDataVo>> getInfoListData(String cardno) {
        List<InfoListDataVo> list = familyMemberService.getInfoListData(cardno);
        return R.ok(list);
    }
}

