package com.center.medical.machine.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.bean.dto.PayResultDto;
import com.center.medical.bean.enums.Kkfs;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.machine.bean.param.ReportPrintPaymentParam;
import com.center.medical.machine.bean.param.SendVerificationCodeParam;
import com.center.medical.machine.bean.param.SuccessCallbackParam;
import com.center.medical.machine.bean.param.VerificationCodeLoginParam;
import com.center.medical.machine.bean.vo.ReportPrintListVo;
import com.center.medical.machine.service.ReportPrintService;
import com.center.medical.machine.service.impl.ReadCardServiceImpl;
import com.center.medical.pay.service.TongLianPayService;
import com.center.medical.service.PeispatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * BG报告主表(Report)表控制层
 *
 * @author ay
 * @since 2023-05-30 10:20:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "打印服务")
@RequestMapping("machine/reportPrint")
public class ReportPrintController extends BaseController {
    /**
     * 服务对象
     */
    private final ReportPrintService reportPrintService;
    private final MapperFacade mapperFacade;
    private final PeispatientService peispatientService;
    private final TongLianPayService tongLianPayService;


    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/printGuide")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "打印指南", notes = "打印指南")
    public R<Map<String, Object>> printGuide() {
        Map<String, Object> map = reportPrintService.extracted(2);
        return R.ok(map);
    }


    /**
     * 打印指南查询
     *
     * @param patientIds
     * @return
     */
    @GetMapping("/printGuideSelect")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "打印指南查询", notes = "打印指南查询")
    public R<List<Peispatient>> printGuideSelect(@RequestParam List<String> patientIds) {
        List<Peispatient> data = peispatientService.list(new QueryWrapper<Peispatient>().in("id", patientIds));
        return R.ok(data);
    }


    /**
     * 打印条形码
     *
     * @return
     */
    @GetMapping("/printBarcode")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "打印条形码", notes = "打印条形码")
    public R<Map<String, Object>> printBarcode() {
        Map<String, Object> map = reportPrintService.extracted(3);
        return R.ok(map);
    }


    /**
     * 打印条形码查询
     *
     * @param patientIds
     * @return
     */
    @GetMapping("/printBarcodeSelect")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "打印条形码查询", notes = "打印条形码查询")
    public R<List<Peispatient>> printBarcodeSelect(@RequestParam List<String> patientIds) {
        List<Peispatient> data = peispatientService.list(new QueryWrapper<Peispatient>().in("id", patientIds));
        return R.ok(data);
    }


    /**
     * 科室报告-打印结束
     *
     * @return
     */
    @GetMapping("/depComplete")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "科室报告-打印结束", notes = "科室报告-打印结束")
    public R depComplete() {
        ReadCardServiceImpl.nameList.clear();
        ReadCardServiceImpl.photo.clear();
        return R.ok();
    }


    /**
     * 既往报告-打印结束
     *
     * @return
     */
    @GetMapping("/patientComplete")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "既往报告-打印结束", notes = "既往报告-打印结束")
    public R patientComplete() {
        ReadCardServiceImpl.nameList.clear();
        ReadCardServiceImpl.photo.clear();
        return R.ok();
    }


    /**
     * 发送验证码
     * @param param
     * @return
     */
    @PostMapping("/sendVerificationCode")
    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    public R<Boolean> sendLoginCode(@Valid @RequestBody SendVerificationCodeParam param) {
        Boolean b = reportPrintService.sendVerificationCode(param);
        return R.ok(b);
    }




    /**
     * 验证码登录
     * @param param
     * @return
     */
    @PostMapping("/verificationCodeLogin")
    @ApiOperation(value = "验证码登录", notes = "验证码登录,返回一个加密字符串，请求后续页面的时候都要带着")
    public R<String> VerificationCodeLogin(@Valid @RequestBody VerificationCodeLoginParam param) {
        String str = reportPrintService.verificationCodeLogin(param);
        return R.ok(str);
    }


    /**
     * 加密身份证号
     *
     * @return
     */
    @GetMapping("/encryptionIdCardNo")
    @ApiOperation(value = "加密身份证号", notes = "加密身份证号")
    @ApiImplicitParam(name = "idcardno", value = "身份证号")
    public R<String> encryptionIdCardNo(String idcardno) {
        String str = reportPrintService.patientComplete(idcardno);
        return R.ok(str);
    }



    /**
     * 获取报告列表
     * @param userToken
     * @return
     */
    @GetMapping("/reportPrintList")
    @ApiOperation(value = "报告打印列表", notes = "报告打印列表")
    @ApiImplicitParam(name = "userToken", value = "登录或加密返回的数据")
    public R<List<ReportPrintListVo>> reportPrintList(String userToken) {
        List<ReportPrintListVo> list = reportPrintService.reportPrintList(userToken);
        return R.ok(list);
    }


    /**
     * 报告打印支付
     *
     * @param param 分页参数
     * @return 所有数据
     */
    @PostMapping("/reportPrintPayment")
    @ApiOperation(value = "付款", notes = "先付款，付钱成功后调查询接口，成功后调用回调接口")
    public R<PayResultDto> reportPrintPayment(@Valid @RequestBody ReportPrintPaymentParam param) {
        PayResultDto dto = reportPrintService.reportPrintPayment(param);
        return R.ok(dto);
    }


    /**
     * 查询通联是否支付成功
     * @param trxid
     * @return
     */
    @GetMapping("/queryTongLian")
    @ApiOperation(value = "查询通联是否支付成功", notes = "查询通联是否支付成功")
    public R<Map> queryTongLian(String trxid) {
        log.info("查询通联是否支付成功trxid:{}",trxid);
        Map<String, String> map = null;
        try {
            map = tongLianPayService.query("", trxid , Kkfs.TONGLIAN.value());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        log.info("查询通联是否支付成功结果:{}",map);
        return R.ok(map);
    }



    /**
     * 报告打印支付
     *
     * @param param 分页参数
     * @return 所有数据
     */
    @PostMapping("/callback")
    @ApiOperation(value = "支付后回调方法", notes = "支付后回调方法")
    public R<String> successCallback(@Valid @RequestBody SuccessCallbackParam param) {
        String trxid = param.getTrxid();
        log.info("查询通联是否支付成功trxid:{}",trxid);
        Map<String, String> map = null;
        try {
            map = tongLianPayService.query("", trxid, Kkfs.TONGLIAN.value());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        log.info("查询通联是否支付成功结果:{}",map);
        //0000是交易成功,其他都是失败
        if (!"0000".equals(map.get("trxstatus").toString())){
            throw new ServiceException("支付未成功！请成功后再尝试！");
        }
        String str = reportPrintService.successCallback(param);
        return R.ok(str);
    }

}

