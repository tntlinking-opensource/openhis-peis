package com.center.medical.machine.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.PayResultDto;
import com.center.medical.bean.event.TongLianPayEvent;
import com.center.medical.bean.model.PeispatientChargeOther;
import com.center.medical.bean.model.Report;
import com.center.medical.bean.model.ShReport;
import com.center.medical.bean.model.SmsRecord;
import com.center.medical.bean.param.TongLianPayParam;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.MachineReportPrintConfig;
import com.center.medical.common.config.SmsConfig;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.MachineConstants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.common.utils.sms.SDKTestSendTemplateSMS;
import com.center.medical.machine.bean.param.ReportPrintPaymentParam;
import com.center.medical.machine.bean.param.SendVerificationCodeParam;
import com.center.medical.machine.bean.param.SuccessCallbackParam;
import com.center.medical.machine.bean.param.VerificationCodeLoginParam;
import com.center.medical.machine.bean.vo.ReportPrintListVo;
import com.center.medical.machine.dao.ReportPrintMapper;
import com.center.medical.machine.service.ReportPrintService;
import com.center.medical.report.service.ReportService;
import com.center.medical.service.PeispatientChargeOtherService;
import com.center.medical.service.PeispatientarchiveService;
import com.center.medical.service.ShReportService;
import com.center.medical.service.SmsRecordService;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * BG报告主表(Report)表服务实现类
 *
 * @author ay
 * @since 2023-05-30 10:20:50
 */
@Slf4j
@Service("reportPrintService")
@RequiredArgsConstructor
public class ReportPrintServiceImpl extends ServiceImpl<ReportPrintMapper, Report> implements ReportPrintService {

    private final ReportPrintMapper reportPrintMapper;
    private final SysBranchMapper sysBranchMapper;
    private final PeispatientarchiveService peispatientarchiveService;
    private final SmsRecordService smsRecordService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ShReportService shReportService;
    private final ISysConfigService iSysConfigService;
    private final Snowflake snowflake;
    private final PeispatientChargeOtherService peispatientChargeOtherService;
    private final ReportService reportService;

    private final String RsaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVdd5bYJx9Z+3wNH+kdxErZxwoaa5L14EY6G4/ggnmxLqTpgK3e3FtXSbffU+yN2GYwK3dj5XZHf6tqN2vE9V3bsB6FlDXVdn4UYqDc3lCGWG95W7UkMfOZaJRTaBTx2ORRi/59n0vap+BUcj6y5eqcNVNu3+pkXD7uTkxVz1owQIDAQAB";
    private final String RsaPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJV13ltgnH1n7fA0f6R3EStnHChprkvXgRjobj+CCebEupOmArd7cW1dJt99T7I3YZjArd2Pldkd/q2o3a8T1XduwHoWUNdV2fhRioNzeUIZYb3lbtSQx85lolFNoFPHY5FGL/n2fS9qn4FRyPrLl6pw1U27f6mRcPu5OTFXPWjBAgMBAAECgYBTgAR4PKNxFI1EM7BULAk1nGeI1HICASYoykI9zOLHQIWUU50U8SxPk0lSdun9gR9PfeYk8EEhibZtQviHBaeuNGISsHFsHuCi3eMI1TeESkcASeKywcYNtLFOWaLiGS5PB88yynJFUoxG8yI3Y0SH/if7YiKkWdSGMaHfALGWmQJBANeaIGTDDKqI6SbRkEJLwVMab0N4Q+CEO7GwRn6LTFMn6rXkUNg383rwFBNyT8+6ej7qZ1RIfnr7FzlE022+zvcCQQCxdxlPFGi2DQQodTGdGgbzJK30GLymaeuJSb2ndSezNn06GSlEfpjTDKVO8WwPiih4npR/7LAHFhcFnEr8+EAHAkBcsi51RAshEwYzI2zDpDB1W2s09fMxB4lmxyQ36gohwoq+M3Cy1bOAStJG3iIEbOd8P2m3qgd/No9wlu/UQnJXAkEAqiSmpjPKBKjMEMHzdEzM1vaWtM22qNIqWTDS3ScsjdyzCS2wbBP4D3gzUVGTqf5quOJJO7DAP7sFDYMAPjMApwJBANMECTp8yBaGTm/nJ1mv9j58XH7YpFaFxa8/o+/kce+tdFbJ75OllOFJyK+OjidO7KTgBcImdoIuNPfl7xpey9Y=";

    /**
     * 分页查询[BG报告主表]列表
     *
     * @param page  分页参数
     * @param param Report查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Report> getList(PageParam<Report> page, Report param) {
        return reportPrintMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Report getInfoById(String id) {
        return reportPrintMapper.getInfoById(id);
    }


    /**
     * 打印指南
     * @param value
     * @return
     */
    @Override
    public Map<String, Object> extracted(int value) {
        Map<String,Object> map = new HashMap<>();
        String docUrl = MachineConstants.doc_url;
        map.put("docUrl", docUrl);
        String dydStyle = MachineConstants.dydStyle;
        map.put("dydStyle", dydStyle);
        String cid = sysBranchMapper.getDefaultCid();
        map.put("cid", cid);
        String barcodecountconfig = MachineConstants.barcodeCount;
        String barcodecount = StringUtils.isEmpty(barcodecountconfig) ? "2" : barcodecountconfig;
        map.put("barcodecount", barcodecount);
        // TODO: ??? id怎么拿到的？
//        map.put("patientId", id);
        map.put("fastPrint", value);
        return map;
    }


    /**
     * 发送验证码
     * @param param
     */
    @Override
    public Boolean sendVerificationCode(SendVerificationCodeParam param) {
        //校验一下身份证和手机号是否合法
        String phone = param.getPhone();
        if (!IdcardUtil.isValidCard(param.getIdcardno())){
            throw new ServiceException("身份证不合法,请重新输入!");
        }
        if (phone.length() > 13) {
            throw new ServiceException("联系电话不合法，不能超过13位！");
        } else if (phone.indexOf("0") != 0 && phone.length() != 11) {
            throw new ServiceException("联系电话格式错误！");
        }

        //当天最多发送五条
        List<SmsRecord> list = smsRecordService.list(new LambdaQueryWrapper<SmsRecord>().eq(SmsRecord::getNotifyType, "99")
                .eq(SmsRecord::getPatientcode, param.getPhone()).orderByDesc(SmsRecord::getCreatedate));
        if (list.size() > 5){
            throw new ServiceException("发送失败！每天最多发送五条短信!");
        }
        if (list.size() > 0){
            long timeDb = DateUtil.offsetSecond(list.get(0).getCreatedate(), 60).getTime();
            if (System.currentTimeMillis() < timeDb) {
                throw new ServiceException("一分钟内只能发送一次验证码");
            }
        }

        //删除redis里面的验证码
        RedisUtil.del(CacheConstants.VERIFICATION_CODE_KEY + param.getPhone());

        //发送短信
        String code = RandomUtil.randomNumbers(6);
        String[] codes = new String[] { code, "5分钟" };
        //因为这个模板在指定的APPID里面,所以只能先写死
        SmsConfig smsConfig = new SmsConfig("8aaf07085a608ec2015a6a597d9b0520", "f9aabc7ee53a4dc9a6a70ecc1952fe54");
        String flag = SDKTestSendTemplateSMS.sendMsg(smsConfig, phone, "3043995", codes, "8a216da85a60ae8d015a6a6eaa7f058e");
        log.info("发送短信验证码手机号:{},返回结果：{}", phone,flag);
        if (!"success".equals(flag)) {
            // 发送短信失败，请稍后再试
            throw new ServiceException("发送短信失败，请稍后再试");
        }

        //保存短信记录
        SmsRecord sms = new SmsRecord();
        sms.setNotifyType("99");
        sms.setPatientcode(phone);
        sms.setCreater(SecurityUtils.getUserNo());
        sms.setIsImmediately(1);
        sms.setIdTemplate("3043995");
        sms.setNotifyTime(new Date());
        sms.setNotifyContent(code);
        sms.setNotifyResult(2);
        smsRecordService.save(sms);

        //存入redis
        RedisUtil.set(CacheConstants.VERIFICATION_CODE_KEY + param.getPhone(), code, 60 * 5);
        return Boolean.TRUE;
    }


    /**
     * 验证码登录
     * @param param
     * @return
     */
    @Override
    public String verificationCodeLogin(VerificationCodeLoginParam param) {
        List<SmsRecord> list = smsRecordService.list(new LambdaQueryWrapper<SmsRecord>().eq(SmsRecord::getNotifyType, "99")
                .eq(SmsRecord::getPatientcode, param.getPhone()).orderByDesc(SmsRecord::getCreatedate));
        if (CollectionUtil.isEmpty(list)){
            throw new ServiceException("请先发送验证码后登录！");
        }
        String code = (String) RedisUtil.get(CacheConstants.VERIFICATION_CODE_KEY + param.getPhone());
        if (StringUtils.isEmpty(code)){
            throw new ServiceException("验证码已过期！");
        }
        if (!code.equals(param.getCode())){
            throw new ServiceException("验证码错误！");
        }
        //验证通过删除redis里的记录
        RedisUtil.del(CacheConstants.VERIFICATION_CODE_KEY + param.getPhone());
        //加密手机号防止篡改，后续前端请求时要放在data里面解密
        return RSAUtil.publicEncrypt(JSONUtil.toJsonStr(param), RsaPublicKey);
    }


    public static void main(String[] args) {
        String data = "ejC4iJtnEQpOwvbAdEpOZRfCepxIqZw7EeLGTy/DE1zszJWMgbvzJ/Wxqwi386+n0JI2bpErIjYvQ4mtjM96kds6FznTxdPCFLdlY/C4uKWImBs9XAn7dMZijou9DGBKNzqNybLwfJXxmZTJVJ85jKCOf6kBuyGQbpzH1vghQVM=";
        String RsaPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJV13ltgnH1n7fA0f6R3EStnHChprkvXgRjobj+CCebEupOmArd7cW1dJt99T7I3YZjArd2Pldkd/q2o3a8T1XduwHoWUNdV2fhRioNzeUIZYb3lbtSQx85lolFNoFPHY5FGL/n2fS9qn4FRyPrLl6pw1U27f6mRcPu5OTFXPWjBAgMBAAECgYBTgAR4PKNxFI1EM7BULAk1nGeI1HICASYoykI9zOLHQIWUU50U8SxPk0lSdun9gR9PfeYk8EEhibZtQviHBaeuNGISsHFsHuCi3eMI1TeESkcASeKywcYNtLFOWaLiGS5PB88yynJFUoxG8yI3Y0SH/if7YiKkWdSGMaHfALGWmQJBANeaIGTDDKqI6SbRkEJLwVMab0N4Q+CEO7GwRn6LTFMn6rXkUNg383rwFBNyT8+6ej7qZ1RIfnr7FzlE022+zvcCQQCxdxlPFGi2DQQodTGdGgbzJK30GLymaeuJSb2ndSezNn06GSlEfpjTDKVO8WwPiih4npR/7LAHFhcFnEr8+EAHAkBcsi51RAshEwYzI2zDpDB1W2s09fMxB4lmxyQ36gohwoq+M3Cy1bOAStJG3iIEbOd8P2m3qgd/No9wlu/UQnJXAkEAqiSmpjPKBKjMEMHzdEzM1vaWtM22qNIqWTDS3ScsjdyzCS2wbBP4D3gzUVGTqf5quOJJO7DAP7sFDYMAPjMApwJBANMECTp8yBaGTm/nJ1mv9j58XH7YpFaFxa8/o+/kce+tdFbJ75OllOFJyK+OjidO7KTgBcImdoIuNPfl7xpey9Y=";
        String string = RSAUtil.privateDecrypt(data, RsaPrivateKey);
        System.out.println(string);


    }


    /**
     * 报告打印列表
     * @param userToken
     * @return
     */
    @Override
    public List<ReportPrintListVo> reportPrintList(String userToken) {
        String string = RSAUtil.privateDecrypt(userToken, RsaPrivateKey);
        VerificationCodeLoginParam param = JSON.parseObject(string, VerificationCodeLoginParam.class);
        return reportPrintMapper.reportPrintList(param);
    }


    /**
     * 加密身份证号
     * @param idcardno
     * @return
     */
    @Override
    public String patientComplete(String idcardno) {
        VerificationCodeLoginParam param = new VerificationCodeLoginParam();
        param.setIdcardno(idcardno);
        return RSAUtil.publicEncrypt(JSONUtil.toJsonStr(param), RsaPublicKey);
    }


    /**
     * 报告打印支付
     * @param param
     * @return
     */
    @Override
    public PayResultDto reportPrintPayment(ReportPrintPaymentParam param) {
        //是否开启扫码支付，不开启就去前台打印
        MachineReportPrintConfig config = iSysConfigService.getSysConfigObject(Constants.MACHINE_REPORT_PRINT_CONFIG, MachineReportPrintConfig.class);
        if (ObjectUtils.isEmpty(config) || !config.getIsOpen()){
            throw new ServiceException("自助登记机暂不支持打印,请去前台打印！");
        }
        PayResultDto payResultDto = new PayResultDto();
        //判断是否打印过，没打印过直接打印
        long count = shReportService.count(new LambdaQueryWrapper<ShReport>()
                .eq(ShReport::getPatientcode, param.getPatientcode())
                .eq(ShReport::getReportType, 1).eq(ShReport::getIsPrinted, 1)
        );
        if (count == 0){
            //生成报告打印操作记录
            ShReport shReport = new ShReport();
            shReport.setPatientcode(param.getPatientcode());
            shReport.setReportType(1);
            shReport.setIsCharged(0);
            shReport.setIsPrinted(1);
            shReport.setPrintTime(new Date());
            shReportService.save(shReport);
            Report report = reportService.getOne(new LambdaQueryWrapper<Report>()
                    .eq(Report::getPatientcode, param.getPatientcode()).eq(Report::getDiseaseHealth, 0));
            //拼接url前缀
            Domain domain = iSysConfigService.getSysConfigObject(Constants.DOMAIN_CONFIG, Domain.class);
            String prefix = ZhongkangConfig.isOnline()? domain.getRsPfDomain() : domain.getRsLcDomain();
            payResultDto.setPdfUrl(prefix + report.getUrlPdf());
            return payResultDto;
        }


        //生成体检者其他缴费
        String tradeNo = "OT" + snowflake.nextId();
        PeispatientChargeOther entity = new PeispatientChargeOther();
        entity.setOrderNo(tradeNo);
        entity.setOrderType("打印报告");
        entity.setFeeName("报告打印费");
        entity.setPrice(config.getPreviousReportsPrice());
        entity.setPayStatus(0);
        entity.setPatientcode(param.getPatientcode());
        peispatientChargeOtherService.save(entity);

        //生成二维码，用户扫码
        String id = entity.getId();
        payResultDto.setId(id);
        TongLianPayParam payParam = new TongLianPayParam();
        payParam.setTrxamt((long) (config.getPreviousReportsPrice() * 100));//单位是分
        payParam.setPatientcode(param.getPatientcode());
        payParam.setReqsn(id);
        payParam.setBody("体检号"+param.getPatientcode()+"二维码支付");
        payParam.setRemark(param.getPatientcode()+"二维码支付");
        payParam.setPaytype(param.getPaytype());
        applicationEventPublisher.publishEvent(new TongLianPayEvent(payParam));
        Map<String, String> payResult = payParam.getPayResult();
        print(payResult);
        if ("SUCCESS".equals(payResult.get("retcode"))){
            //生成二维码都要支付完
            String payinfo = payResult.get("payinfo");
            payResultDto.setStatus("fail");
            payResultDto.setVersion(3000L);
            payResultDto.setPayNo(payResult.get("trxid"));
            payResultDto.setRemarks(payinfo);
        } else {
            throw new ServiceException("生成支付二维码失败!");
        }
        entity.setTransactionNo(payResult.get("trxid"));
        peispatientChargeOtherService.updateById(entity);
        return payResultDto;
    }




    public static void print(Map<String, String> map){
        System.out.println("返回数据如下:");
        if(map!=null){
            for(String key:map.keySet()){
                System.out.println(key+";"+map.get(key));
            }
        }
    }


    /**
     * 支付成功回调方法
     * @param param
     * @return
     */
    @Override
    public String successCallback(SuccessCallbackParam param) {
        String trxid = param.getTrxid();
        PeispatientChargeOther entity = peispatientChargeOtherService.getOne(new QueryWrapper<PeispatientChargeOther>().eq("transaction_no", trxid));
        if (ObjectUtils.isEmpty(entity)){
            throw new ServiceException("流水号不正确!");
        }
        Report report = reportService.getInfoById(param.getId());
        if (ObjectUtils.isEmpty(report)){
            throw new ServiceException("报告id不正确!");
        }
        entity.setPayStatus(1);
        entity.setPaidPrice(entity.getPrice());
        peispatientChargeOtherService.updateById(entity);
        //生成报告打印操作记录
        ShReport shReport = new ShReport();
        shReport.setPatientcode(report.getPatientcode());
        shReport.setReportType(1);
        shReport.setIsCharged(1);
        shReport.setIsPrinted(1);
        shReport.setPrintTime(new Date());
        shReport.setErrorMsg(entity.getId());
        shReportService.save(shReport);

        //拼接url前缀
        Domain domain = iSysConfigService.getSysConfigObject(Constants.DOMAIN_CONFIG, Domain.class);
        String prefix = ZhongkangConfig.isOnline()? domain.getRsPfDomain() : domain.getRsLcDomain();
        return prefix + report.getUrlPdf();
    }
}

