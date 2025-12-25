package com.center.medical.reception.controller;

/**
 * @author: 路飞
 * @date: 2023-03-16 15:16
 * @description: 前台-收费管理
 */

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.dto.PayResultDto;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.bean.model.Peispatientcharge;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.param.*;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.model.CardType;
import com.center.medical.finance.service.CardService;
import com.center.medical.finance.service.CardTypeService;
import com.center.medical.pay.service.SuiXingPayService;
import com.center.medical.pay.service.TongLianPayService;
import com.center.medical.reception.bean.param.*;
import com.center.medical.reception.bean.vo.RecheckVo;
import com.center.medical.reception.bean.vo.RefundManagementVo;
import com.center.medical.reception.service.RegisterService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientarchiveService;
import com.center.medical.service.PeispatientchargeService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysDeptService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 2)
@Api(tags = "前台-收费管理")
@RequestMapping("/reception/charge")
public class ChargeController extends BaseController {

    private final PeispatientchargeService peispatientchargeService;
    private final RegisterService registerService;
    private final ISysBranchService iSysBranchService;
    private final ISysDeptService iSysDeptService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final CardService cardService;
    private final PeispatientService peispatientService;
    private final PeispatientarchiveService peispatientarchiveService;
    private final CardTypeService cardTypeService;
    private final MapperFacade mapperFacade;
    private final TongLianPayService tongLianPayService;
    private final SuiXingPayService suiXingPayService;


    /**
     * 根据体检号查询体检者信息
     *
     * @param patientCode 体检号
     * @param autoFill    是否需要补全体检号：0.否 1.是
     * @return
     */
    @GetMapping("/getPatientData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "体检者信息 ", notes = "根据体检号查询体检者信息", position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "autoFill", value = "是否需要补全体检号：0.否 1.是")
    })
    public R<Peispatient> getPatientData(@RequestParam(value = "patientCode") String patientCode,
                                         @RequestParam(value = "autoFill") Integer autoFill) {
        if (StringUtils.isBlank(patientCode)) {
            throw new GlobalException("体检号不能为空！");
        }
        if (autoFill == 1) {
            patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        }

        Peispatient peispatient = registerService.getOne(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getPatientcode, patientCode));
        return R.ok(peispatient);
    }

    /**
     * 登记后收费
     *
     * @param param
     * @return
     */
    @PostMapping("/receiveRefund")
    @RepeatSubmit(interval = 3000, message = "正在处理，请稍等")
    @PreAuthorize("@ss.hasPermi('reception:registration:charge')")
    @ApiOperation(value = "收费", notes = "登记后收费", position = 2)
    @Log(title = "前台-收费管理-登记后收费", businessType = BusinessType.INSERT)
    public R<Boolean> receiveRefund(@RequestBody ChargeParam param) {
        //TODO wait 实现数据库锁，保证支付信息的一致性
        return R.ok(this.peispatientchargeService.receiveRefund(param));
    }


    /**
     * 支付：体检卡、会员卡、微信支付、支付宝支付等
     *
     * @param param 支付参数
     * @return
     */
    @PostMapping("/pay")
    @PreAuthorize("@ss.hasPermi('reception:registration:charge')")
    @ApiOperation(value = "支付", notes = "支付：体检卡、会员卡、微信支付、支付宝支付等", position = 2)
    @Log(title = "前台-收费管理-支付", businessType = BusinessType.INSERT)
    public R<PayResultDto> pay(@RequestBody PayParam param) {
        //TODO wait 实现数据库锁，保证支付信息的一致性
        log.info("前台-收费管理-支付：{}", param);
        return R.ok(this.peispatientchargeService.pay(param));
    }


    /**
     * 加载复查金额
     *
     * @param cardno 会员卡号
     * @return
     */
    @PostMapping("/loadCheckCard/{cardno}")
//    @PreAuthorize("@ss.hasPermi('reception:registration:charge')")
    @ApiOperation(value = "加载复查金额", notes = "加载复查金额", position = 2)
    @ApiImplicitParam(name = "cardno", value = "会员卡号")
    @Log(title = "前台-收费管理-加载复查金额", businessType = BusinessType.INSERT)
    public R<RecheckVo> loadCheckCard(@PathVariable String cardno) {
        if (StringUtils.isEmpty(cardno)) {
            throw new GlobalException("卡号不能为空");
        }
        Card card = cardService.getInfoByNo(cardno);
        if (Objects.isNull(card)) {
            throw new GlobalException("卡号不存在");
        }
        if (!Card.TEN.equals(card.getTypeId())) {
            throw new GlobalException("卡号不是十周年会员卡");
        }
        if (card.getRecheckMoney() == null) {
            throw new GlobalException("卡号没有维护复查额度");
        }
        RecheckVo result = mapperFacade.map(card, RecheckVo.class);
        return R.ok(result);
    }

    /**
     * 线下消费复查额度
     *
     * @param param 复查额度支付参数
     * @return
     */
    @PostMapping("/chargeRecheck")
    @PreAuthorize("@ss.hasPermi('reception:registration:charge')")
    @ApiOperation(value = "线下消费复查额度", notes = "线下消费复查额度", position = 2)
    @Log(title = "前台-收费管理-线下消费复查额度", businessType = BusinessType.INSERT)
    public R<PayResultDto> chargeRecheck(@RequestBody ChargeRecheckParam param) {
        log.info("前台-收费管理-线下消费复查额度：{}", param);
        return R.ok(this.peispatientchargeService.chargeRecheck(param));
    }


    /**
     * 退款：体检卡、会员卡、微信支付、支付宝支付等
     *
     * @param param 退款参数
     * @return
     */
    @PostMapping("/refund")
    @PreAuthorize("@ss.hasPermi('reception:registration:charge')")
    @ApiOperation(value = "退款", notes = "退款：体检卡、会员卡、微信支付、支付宝支付等", position = 2)
    @Log(title = "前台-收费管理-退款", businessType = BusinessType.INSERT)
    public R<Boolean> refund(@RequestBody RefundParam param) {
        //TODO wait 实现数据库锁，保证支付信息的一致性
        log.info("前台-收费管理-退款：{}", param);
        return R.ok(this.peispatientchargeService.refund(param));
    }


    /**
     * 费用预收
     *
     * @param param
     * @return
     */
    @PostMapping("/advanceRefund")
    @PreAuthorize("@ss.hasPermi('reception:registration:charge')")
    @ApiOperation(value = "费用预收", notes = "费用预收", position = 3)
    @Log(title = "前台-收费管理-费用预收", businessType = BusinessType.INSERT)
    public R<Boolean> advanceRefund(@RequestBody AdvanceRefundParam param) {
        if (StringUtils.isBlank(param.getPatientcode())) {
            throw new GlobalException("体检号不能为空！");
        }
        if (CollectionUtil.isEmpty(param.getChargeItems())) {
            throw new GlobalException("请添加预收记录！");
        }

        //TODO wait 实现数据库锁，保证支付信息的一致性
        return R.ok(this.peispatientchargeService.advanceRefund(param));
    }

    /**
     * 预收支付：体检卡、会员卡、微信支付、支付宝支付等
     *
     * @param param 支付参数
     * @return
     */
    @PostMapping("/advancePay")
    @PreAuthorize("@ss.hasPermi('reception:registration:charge')")
    @ApiOperation(value = "预收支付", notes = "预收支付：体检卡、会员卡、微信支付、支付宝支付等", position = 3)
    @Log(title = "前台-登记页面-预收支付", businessType = BusinessType.INSERT)
    public R<PayResultDto> advancePay(@RequestBody PayParam param) {
        //TODO wait 实现数据库锁，保证支付信息的一致性
        log.info("前台-登记页面-预收支付：{}", param);
        return R.ok(this.peispatientchargeService.advancePay(param));
    }


    /**
     * 获取收费记录
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getChargeData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取收费记录", notes = "获取收费记录", position = 3)
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<List<Peispatientcharge>> getChargeData(String patientcode) {
        List<Peispatientcharge> list = peispatientchargeService.getChargeData(patientcode);
        return R.ok(list);
    }


    /**
     * 判断是否存在已退项未退费的检验科项目
     *
     * @param key 体检号
     * @return
     */
    @GetMapping("/checkInspection")
//    @PreAuthorize("@ss.hasPermi('::send')")
    @ApiOperation(value = "判断是否存在已退项未退费的检验科项目", notes = "判断是否存在已退项未退费的检验科项目", position = 4)
    @ApiImplicitParam(name = "key", value = "体检号")
    public R<Boolean> checkInspection(@RequestParam(value = "key") String key) {

        LambdaQueryWrapper<Peispatientfeeitem> wrapper = new LambdaQueryWrapper<Peispatientfeeitem>()
                .eq(Peispatientfeeitem::getIdPatient, key)
                .eq(Peispatientfeeitem::getChangeItem, 1)
                .eq(Peispatientfeeitem::getIdKs, iSysDeptService.selectDeptByName("检验科").getDeptNo());
        wrapper.and(w -> {
            w.isNull(Peispatientfeeitem::getFMarkFeereturn).or().eq(Peispatientfeeitem::getFMarkFeereturn, 0);
        });
        long count = peispatientfeeitemService.count(wrapper);

        return R.ok(count > 0);
    }

    /**
     * 前台-登记-退费-反收费
     *
     * @param patientCode 体检号
     * @param version     版本号
     * @return
     */
    @PostMapping("/reCharge")
    @PreAuthorize("@ss.hasPermi('reception:proceeds:reCharge')")
    @ApiOperation(value = "退费-反收费", notes = "前台-登记-退费-反收费", position = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "version", value = "版本")
    })
    @Log(title = "前台-登记-退费-反收费", businessType = BusinessType.UPDATE)
    public R<Boolean> reCharge(@RequestParam(value = "patientCode") String patientCode, @RequestParam(value = "version") Long version) {
        //TODO wait 实现数据库锁，保证反收费信息的一致性
        return this.peispatientchargeService.reCharge(patientCode, version);
    }


    /**
     * 反登记
     *
     * @param patientCode
     * @return
     */
    @PutMapping("/setUnRegister")
    @Log(title = "反登记", businessType = BusinessType.INSERT)
    @ApiOperation(value = "反登记", notes = "反登记", position = 6)
    @ApiImplicitParam(name = "patientCode", value = "体检号")
    public R setUnRegister(@RequestParam(value = "patientCode") String patientCode) {
        Boolean b = registerService.setUnRegister(patientCode);
        return R.toResult(b);
    }


    /**
     * 查询当前体检者有没有绑定家庭卡
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/searchPatientcode")
    @ApiOperation(value = "查询当前体检者有没有绑定家庭卡", notes = "查询当前体检者有没有绑定家庭卡", position = 7)
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R searchPatientcode(@RequestParam String patientcode) {
        //体检者表
        Peispatient patient = peispatientService.getByPatientCode(patientcode);
        //档案表
        Peispatientarchive archive = peispatientarchiveService.getOne(new LambdaQueryWrapper<Peispatientarchive>()
                .eq(Peispatientarchive::getPatientarchiveno, patient.getPatientarchiveno()));
        //一卡通号
        if (ObjectUtils.isEmpty(archive) || StringUtils.isEmpty(archive.getPatientcardno())) {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("status", "failed");
            result.put("msg", "体检者未绑定家庭卡");
            return R.ok(result);
        }

        //线上
        //查询卡号
        Card card = cardService.getOne(new QueryWrapper<Card>()
                .eq("card_no", archive.getPatientcardno())
                .eq("is_delete", 0)
        );
        Map<String, Object> result = new HashMap<String, Object>();
        if (card == null) {
            result.put("status", "failed");
            result.put("msg", "卡号不存在");
            return R.ok(result);
        }
        //卡类型
        CardType type = cardTypeService.getInfoById(Card.JTK);
        if (!Card.JTK.equals(card.getTypeId())) {
            result.put("status", "failed");
            result.put("msg", "此卡号不是" + type.getTypeName());
            return R.ok(result);
        }
        //成功返回卡数据
        card.setTypeName(type.getTypeName());
        result.put("status", "success");
        result.put("msg", card);
        return R.ok(result);
    }


    /**
     * 缴费单打印
     *
     * @param id
     * @return
     */
    @GetMapping("/chargeDataPrint")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "缴费单打印数据", notes = "缴费单打印数据", position = 8)
    @ApiImplicitParam(name = "id", value = "体检者id")
    public R chargeDataPrint(String id) {
        List<Map<String, Object>> list = registerService.chargeDataPrint(id);
        return R.ok(list);
    }


    /**
     * 判断支付方式的金额是否可以退
     *
     * @param param
     * @return
     */
    @GetMapping("/checkRefund")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "判断支付方式的金额是否可以退", notes = "判断支付方式的金额是否可以退", position = 9)
    public R checkRefund(CheckRefundParam param) {
        Boolean b = registerService.checkRefund(param);
        return R.ok(b);
    }


    /**
     * 体检卡退费
     * @param param
     * @return
     */
    @PostMapping("/receiveTjkCard")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "体检卡退费", notes = "体检卡退费", position = 10)
    public R receiveTjkCard(@RequestBody ReceiveTjkCardParam param) {
        Map map = registerService.receiveTjkCard(param);
        return R.ok(map);
    }


    /**
     * 体检卡退费
     * @param param
     * @return
     */
    @PostMapping("/removeCard")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "体检卡误操作", notes = "体检卡误操作", position = 11)
    public R removeCard(@RequestBody RemoveCardParam param) {
        Map map = registerService.removeCard(param);
        return R.ok(map);
    }



    /**
     * 会员卡退费
     * @param param
     * @return
     */
    @PostMapping("/receiveMemberCard")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "会员卡退费", notes = "会员卡退费", position = 12)
    public R receiveMemberCard(@RequestBody ReceiveMemberParam param) {
        Map map = registerService.receiveMemberCard(param);
        return R.ok(map);
    }




    /**
     * 第三方支付退款
     * @param param
     * @return
     */
    @PostMapping("/refundThirdPayment")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    @Log(title = "第三方支付退款",businessType = BusinessType.INSERT)
    @ApiOperation(value = "第三方支付退款", notes = "第三方支付退款", position = 10)
    public R receiveTongLian(@RequestBody ReceiveTongLianParam param) {
        log.info("第三方支付退款参数:{}",param);
        Map map = registerService.refundThirdPayment(param);
        return R.ok(map);
    }




    /**
     * 退款管理
     *
     * @param param
     * @return
     */
    @GetMapping("/refundManagement")
    @ApiOperation(value = "通联支付退款管理", notes = "通联支付退款管理", position = 11)
    public R<IPage<RefundManagementVo>> refundManagement(PageParamSimple pageParamSimple, RefundManagementParam param) {
        PageParam<RefundManagementVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<RefundManagementVo> iPage = registerService.refundManagement(page,param);
        return R.ok(iPage);
    }




    /**
     * 导出Excel文件
     * @param response
     * @param param
     */
    @Log(title = "退款管理",businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasAnyPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出通联支付退款管理",notes = "导出通联支付退款管理")
    @PostMapping("/exportData")
    public void export(HttpServletResponse response, RefundManagementParam param){
        List<RefundManagementVo> list = registerService.exportData(param);
        ExcelUtil<RefundManagementVo> util = new ExcelUtil<>(RefundManagementVo.class);
        util.exportExcel(response,list,"通联支付管理","通联支付管理");
    }


    /**
     * 查询通联是否支付成功
     * @param trxid
     * @return
     */
    @PostMapping("/queryTongLian")
    @ApiOperation(value = "查询通联是否支付成功", notes = "查询通联是否支付成功")
    public R<Map> queryTongLian(String trxid,Integer type) {
        log.info("查询通联是否支付成功trxid:{}",trxid);
        Map<String, String> map = null;
        try {
            map = tongLianPayService.query("", trxid,type);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        log.info("查询通联是否支付成功结果:{}",map);
        return R.ok(map);
    }






    /**
     * 会员卡退费
     * @param param
     * @return
     */
    @PostMapping("/receivesFamilyCard")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @Log(title = "家庭卡退费",businessType = BusinessType.INSERT)
    @ApiOperation(value = "家庭卡退费", notes = "家庭卡退费")
    public R receivesFamilyCard(@RequestBody ReFamilyCardParam param) {
        Map map = registerService.receivesFamilyCard(param);
        return R.ok(map);
    }



//
//    /**
//     * 手动通联退款(测试使用)
//     * @param param
//     * @return
//     */
//    @PostMapping("/tongLianRefund")
//    @ApiOperation(value = "手动通联退款(测试使用)", notes = "手动通联退款(测试使用)")
//    public R tongLianRefund(TongLianRefundParam param) {
//        Boolean b = peispatientchargeService.tongLianRefund(param);
//        return R.ok(b);
//    }




    /**
     * 查询通联是否支付成功
     * @param param
     * @return
     */
    @PostMapping("/querySuiXing")
    @ApiOperation(value = "查询随行支付是否支付成功", notes = "查询随行支付是否支付成功")
    public R<Map> querySuiXing(@RequestBody SuiXingTradeQueryParam param) {
        Map<String, Object> map = suiXingPayService.tradeQuery(param);
        log.info("查询随行支付是否支付成功{}",map);
        return R.ok(map);
    }


    /**
     * 通联报备设备
     * @param param
     * @return
     */
    @PostMapping("/reporting")
    @ApiOperation(value = "通联报备设备", notes = "通联报备设备")
    public R<Map> reporting(@RequestBody ReportingParam param) throws Exception {
        Map<String, String> reporting = tongLianPayService.reporting(param.getTermno(),param.getDevicetype(),param.getOperation(),param.getTermstate(),param.getTermaddress());
        return R.ok(reporting);
    }



    /**
     * 登记后收费
     *
     * @param param
     * @return
     */
    @PostMapping("/changePaymentMethod")
    @RepeatSubmit(interval = 3000, message = "正在处理，请稍等")
    @ApiOperation(value = "更改收费方式", notes = "更改收费方式")
    @Log(title = "前台-收费管理-更改收费方式", businessType = BusinessType.INSERT)
    public R<Boolean> changePaymentMethod(@RequestBody @Valid ChangePaymentMethodParam param) {
        //TODO wait 实现数据库锁，保证支付信息的一致性
        return R.ok(this.peispatientchargeService.changePaymentMethod(param));
    }

}
