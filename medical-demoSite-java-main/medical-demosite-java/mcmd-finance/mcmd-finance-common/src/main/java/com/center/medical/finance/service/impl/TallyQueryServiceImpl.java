package com.center.medical.finance.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.PayResultDto;
import com.center.medical.bean.enums.Kkfs;
import com.center.medical.bean.event.*;
import com.center.medical.bean.param.*;
import com.center.medical.bean.model.PeisReserPayway;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.PeispatientReservationCharge;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.Arith;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeisReserPaywayMapper;
import com.center.medical.dao.PeispatientReservationChargeMapper;
import com.center.medical.finance.bean.dto.TQFormDataDto;
import com.center.medical.finance.bean.dto.TQGridDataDto;
import com.center.medical.finance.bean.param.TQPageParam;
import com.center.medical.finance.bean.param.TQSaOrUpParam;
import com.center.medical.finance.bean.param.TallyQueryPayParam;
import com.center.medical.finance.bean.vo.RemitterVo;
import com.center.medical.finance.bean.vo.TQPageVo;
import com.center.medical.finance.dao.TallyQueryMapper;
import com.center.medical.finance.service.TallyQueryService;
import com.center.medical.service.PeisReserPaywayService;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 记账管理-记账结算(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-03-31 14:27:31
 */
@Slf4j
@Service("tallyQueryService")
@RequiredArgsConstructor
public class TallyQueryServiceImpl extends ServiceImpl<TallyQueryMapper, Peispatient> implements TallyQueryService {

    private final TallyQueryMapper tallyQueryMapper;
    private final MapperFacade mapperFacade;
    private final PeisReserPaywayMapper peisReserPaywayMapper;
    private final PeispatientReservationChargeMapper peispatientReservationChargeMapper;
    private final PeisReserPaywayService peisReserPaywayService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final Snowflake snowflake;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TQPageVo> getList(PageParam<TQPageVo> page, TQPageParam param) {
        return tallyQueryMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return tallyQueryMapper.getInfoById(id);
    }


    /**
     * 记帐查询数据导出
     *
     * @param param
     * @return
     */
    @Override
    public List<TQPageVo> getExportData(TQPageParam param) {
        List<TQPageVo> list = tallyQueryMapper.getExportData(param);
        for (TQPageVo vo : list) {
            vo.setJszt(vo.getYpid() == null || vo.getJzje() == null ? "未结算"
                    : (Double.valueOf(vo.getYpid().toString()) >= Double.valueOf(vo.getJzje().toString()) ? "已结算" : "未结算"));
        }
        return tallyQueryMapper.getExportData(param);
    }

    /**
     * 卡扣款
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdateFee(PayParam param) {
        if (param.getLimit() > 0.01) {
            throw new ServiceException("消费款金额不能低于0.01元！");
        }
        log.info("结算-卡扣款：{}", param);
        //卡扣款操作
        CardConsumeParam cardConsumeParam = new CardConsumeParam(param.getCardId(), param.getLimit(), param.getLimit() > 0 ? 2 : 1, param.getMemotext(), SecurityUtils.getUserNo(), param.getIdPayway(), param.getBranchId(), param.getPatientcode(), param.getPatientname(), param.getConsumeType(), param.getKkfs(), SecurityUtils.getUsername(), null);
        applicationEventPublisher.publishEvent(new CardConsumeEvent(cardConsumeParam));
        //新增结算记录
        TQSaOrUpParam tqSaOrUpParam = new TQSaOrUpParam();
        //体检者数据
        TQFormDataDto formdata = new TQFormDataDto();
        formdata.setPatientcode(param.getPatientcode());
        tqSaOrUpParam.setFormdata(formdata);
        //支付记录
        TQGridDataDto tqGridDataDto = new TQGridDataDto();
        tqGridDataDto.setMoneyamountpaid(-param.getLimit());
        tqGridDataDto.setIdPayway(param.getIdPayway());
        tqGridDataDto.setIsCharged(1);
        tqGridDataDto.setNote(param.getMemotext());
        tqGridDataDto.setState("added");
        tqGridDataDto.setIsTotalcharge(0);
        tqGridDataDto.setCardno(param.getCardId());
        List<TQGridDataDto> griddata = Arrays.asList(tqGridDataDto);
        tqSaOrUpParam.setGriddata(griddata);
        return saOrUp(tqSaOrUpParam, false);
    }


    /**
     * 结算-保存
     *
     * @param param
     * @param flag  是否需要重新计算结算记录的总金额：false不需要，true需要
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(TQSaOrUpParam param, Boolean flag) {
        String patientcode = "";
        try {
            patientcode = param.getFormdata().getPatientcode();
        } catch (Exception e) {
            throw new ServiceException("操作失败，记账信息ID不能为空！");
        }
        // 更新结算表的结算金额
        PeispatientReservationCharge charge = peispatientReservationChargeMapper.selectOne(new QueryWrapper<PeispatientReservationCharge>()
                .eq("patientcode", patientcode));

        if (Objects.isNull(charge)) {
            throw new ServiceException("该条记账信息不存在");
        }
        String chargeId = charge.getId();
        // 结算金额
        Double jsMoney = 0d;
        List<TQGridDataDto> featureData = param.getGriddata();
        List<PeisReserPayway> list = new ArrayList<PeisReserPayway>();
        for (int i = 0, l = featureData.size(); i < l; i++) {
            TQGridDataDto map = featureData.get(i);
            //体检者结算方式表
            PeisReserPayway peisReserPayway = mapperFacade.map(map, PeisReserPayway.class);
            if ("removed".equals(map.getState())) {
                PeisReserPayway payway = peisReserPaywayMapper.getInfoById(map.getId());
                // 判断是否存在
                if (null != payway) {
                    peisReserPaywayMapper.deleteById(payway);
                }
            } else if ("modified".equals(map.getState())) {
                //修改
                PeisReserPayway payway = peisReserPaywayMapper.getInfoById(map.getId());
                // 判断是否存在
                if (null != payway) {
                    peisReserPaywayMapper.updateById(peisReserPayway);
                    jsMoney += payway.getMoneyamountpaid();
                } else throw new ServiceException("保存失败：第 <font color='red'>" + (i + 1) + " 条记录不存在，已经被删除");
            } else if ("added".equals(map.getState())) {
                //添加
                peisReserPayway.setMoneyamountpaiddate(new Date());
                peisReserPayway.setIdFeecharger(SecurityUtils.getUserNo());
                //体检者结算表ID
                peisReserPayway.setIdCharge(chargeId);
                list.add(peisReserPayway);
                jsMoney += peisReserPayway.getMoneyamountpaid();
            }
        }

        // 结算信息不存在则插入
        charge.setMoneyamountpaid(flag ? jsMoney : Arith.add(charge.getMoneyamountpaid(), jsMoney));
        peispatientReservationChargeMapper.updateById(charge);

        //批量插入
        if (list.size() > 0) {
            peisReserPaywayService.saveBatch(list);
        }

        return Boolean.TRUE;
    }

    /**
     * 退款：体检卡、会员卡等
     *
     * @param param 退款参数
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean refund(RefundParam param) {
        if (param.getLimit() < 0.01) {
            throw new ServiceException("退款金额不能低于0.01元！");
        }
        //获取支付记录，获取结算记录，判断退款金额是否小于或者等于支付金额，小于则支付记录减去退款金额，等于则直接删除支付记录，然后进行卡金额恢复，结算记录结算金额减去退款金额
        if (StringUtils.isBlank(param.getId())) {
            throw new ServiceException("支付记录ID不能为空！");
        }
        String id = param.getId();
        //获取支付记录
        PeisReserPayway payway = peisReserPaywayMapper.getInfoById(id);
        if (Objects.isNull(payway)) {
            throw new ServiceException("支付记录不存在或者已被删除！");
        }
        if (payway.getIsCharged() == 0) {
            throw new ServiceException("该条支付记录未收费，无法费！");
        }
        //获取结算记录
        PeispatientReservationCharge charge = peispatientReservationChargeMapper.getInfoById(payway.getIdCharge());
        if (Objects.isNull(charge)) {
            throw new ServiceException("该条记账信息不存在");
        }
        //判断退款金额是否小于或者等于支付金额，小于则支付记录减去退款金额，等于则直接删除支付记录
        if (Arith.sub(payway.getMoneyamountpaid(), param.getLimit()) < 0) {
            //不能超过支付金额
            throw new ServiceException("退款金额(" + param.getLimit() + ")不能超过支付金额(" + payway.getMoneyamountpaid() + ")！");
        }

        //退款操作
        // TODO wait 保存线上：线上扣费
        if (param.getKkfs() == Kkfs.TJK.value() || param.getKkfs() == Kkfs.HYKJF.value() || param.getKkfs() == Kkfs.HYKYE.value()) {
            //卡扣款操作
            CardConsumeParam cardConsumeParam = new CardConsumeParam(param.getCardId(), param.getLimit(), param.getLimit() > 0 ? 2 : 1, param.getMemotext(), SecurityUtils.getUserNo(), payway.getIdPayway(), param.getBranchId(), charge.getPatientcode(), param.getPatientname(), param.getConsumeType(), param.getKkfs(), SecurityUtils.getUsername(), null);
            applicationEventPublisher.publishEvent(new CardConsumeEvent(cardConsumeParam));
        } else if (param.getKkfs() == Kkfs.WXPAY.value()) {
            //TODO wait 微信退款
        } else if (param.getKkfs() == Kkfs.ALIPAY.value()) {
            //TODO wait 支付宝退款

        } else if (param.getKkfs() == Kkfs.TONGLIAN.value()) {
            //通联支付退款
            TongLianRefundParam payParam = new TongLianRefundParam();
            payParam.setTrxamt((long) (param.getLimit() * 100));//单位是分
            payParam.setReqsn(payway.getId());
            payParam.setOldtrxid(payway.getCardno());
            try {
                applicationEventPublisher.publishEvent(new TongLianRefundEvent(payParam));
                Map<String, String> payResult = payParam.getPayResult();
                log.info("通联支付退款成功：{}", payResult);
            } catch (Exception e) {
                log.error("通联支付退款失败：{}、{}", param, e);
                throw new ServiceException("通联支付退款失败!");
            }
        } else if (param.getKkfs() == Kkfs.JTKYE.value() || param.getKkfs() == Kkfs.JTKJF.value()) {
            //家庭卡退款
            //家庭卡扣款操作
            OldFamilyConParam oldFamilyConParam = new OldFamilyConParam();
            oldFamilyConParam.setIdcardno(param.getIdcardno());
            oldFamilyConParam.setCardNo(param.getCardId());
            oldFamilyConParam.setIdPayway(param.getIdPayway());
            oldFamilyConParam.setType("0");
            oldFamilyConParam.setMoney(param.getLimit());
            oldFamilyConParam.setChargeUsername(SecurityUtils.getUsername());
            oldFamilyConParam.setPatientcode(param.getPatientcode());
            oldFamilyConParam.setNote(param.getMemotext());
            applicationEventPublisher.publishEvent(new FamilyConsumptionEvent(oldFamilyConParam));
        } else if (param.getKkfs() == Kkfs.SUIXING.value()) {
            //随行支付退款
            SuiXingTradeRefundParam payParam = new SuiXingTradeRefundParam();
            payParam.setAmt(param.getLimit().toString());//单位是分
            payParam.setOrigOrderNo(payway.getId());
            applicationEventPublisher.publishEvent(new SuiXingRefundEvent(payParam));
        }


        if (Arith.sub(payway.getMoneyamountpaid(), param.getLimit()) < 0.01) {
            //直接删除支付记录
            peisReserPaywayMapper.deleteById(payway.getId());
        } else {
            //支付记录减去退款金额
            payway.setMoneyamountpaid(Arith.add(payway.getMoneyamountpaid(), param.getLimit()));
            peisReserPaywayMapper.updateById(payway);
        }
        charge.setMoneyamountpaid(Arith.sub(charge.getMoneyamountpaid(), param.getLimit()));
        peispatientReservationChargeMapper.updateById(charge);

        return Boolean.TRUE;
    }


    /**
     * 获取汇款单位名单
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<RemitterVo> getRemitter(PageParam<RemitterVo> page, String key) {
        return tallyQueryMapper.getRemitter(page,key);
    }

    /**
     * 支付
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PayResultDto pay(TallyQueryPayParam param) {
        log.info("支付参数：{}", param);
        // 支付记录ID
        String id = param.getChargeId();
        if (StringUtils.isEmpty(id)){
            id = String.valueOf(snowflake.nextId());
        }
        String patientcode = param.getPatientcode();

        // 更新结算表的结算金额
        PeispatientReservationCharge charge = peispatientReservationChargeMapper.selectOne(new QueryWrapper<PeispatientReservationCharge>()
                .eq("patientcode", patientcode));
        if (Objects.isNull(charge)) {
            throw new ServiceException("该条记账信息不存在");
        }
        String chargeId = charge.getId();



        // TODO wait 保存线上：线上扣费
        log.info("---------进行扣款操作中---------");
        if (param.getKkfs() == Kkfs.TJK.value() || param.getKkfs() == Kkfs.HYKJF.value() || param.getKkfs() == Kkfs.HYKYE.value()) {
            //卡扣款操作
            CardConsumeParam cardConsumeParam = new CardConsumeParam(param.getCardId(), param.getLimit(), param.getLimit() > 0 ? 2 : 1, param.getMemotext(), SecurityUtils.getUserNo(), param.getIdPayway(), param.getBranchId(), param.getPatientcode(), param.getPatientname(), param.getConsumeType(), param.getKkfs(), SecurityUtils.getUsername(), null);
            applicationEventPublisher.publishEvent(new CardConsumeEvent(cardConsumeParam));
        } else if (param.getKkfs() == Kkfs.WXPAY.value()) {
            //微信线上支付
            MicropayParam micropayParam = new MicropayParam();
            micropayParam.setDeviceInfo(param.getDeviceInfo());
            micropayParam.setAuthCode(param.getCardId());
            micropayParam.setBody("体检支付");
            micropayParam.setKkfs(param.getKkfs());
            micropayParam.setBodspbillCreateIpy("127.0 .0 .1");
            micropayParam.setTotalFee((int) (param.getLimit() * 100));
            micropayParam.setOutTradeNo(id);
            try {
                applicationEventPublisher.publishEvent(new WxPayMicropayEvent(micropayParam));
                WxPayMicropayResult micropay = micropayParam.getWxPayMicropayResult();
                log.info("微信支付成功：{}", micropay);
                param.setConsumeId(micropay.getTransactionId());
            } catch (Exception e) {
                log.error("支付失败：{}、{}", param, e);
                throw new ServiceException("支付失败!");
            }
        } else if (param.getKkfs() == Kkfs.ALIPAY.value()) {
            //TODO wait 支付宝线上支付

        } else if (param.getKkfs() == Kkfs.TONGLIAN.value()) {
            log.info("---------开始通联支付---------");
            //TODO wait 通联支付
            //有流水号就直接跳过支付
            if (StringUtils.isEmpty(param.getConsumeId())){
                TongLianScanPayParam payParam = new TongLianScanPayParam();
                payParam.setTrxamt((long) (param.getLimit() * 100));//单位是分
                payParam.setReqsn(id);
                payParam.setBody("体检支付");
                payParam.setRemark("");
//            payParam.setRemark("体检号:"+param.getPatientcode()+",支付于"+new Date()+",支付金额:"+payParam.getTrxamt());
                payParam.setAuthcode(param.getCardId());
                try {
                    log.info("---------通联支付扣款---------");
                    applicationEventPublisher.publishEvent(new TongLianScanPayEvent(payParam));
                    Map<String, String> payResult = payParam.getPayResult();
                    log.info("通联支付成功：{}", payResult);
                    if ("SUCCESS".equals(payResult.get("retcode"))){
                        //当结果码为2000时，商户系统可设置间隔时间(建议10秒)重新查询支付结果，直到支付成功或超时(建议50秒)
                        if ("2000".equals(payResult.get("trxstatus"))){
                            log.info("状态码2000,需要查询支付状态!");
                            PayResultDto dto = new PayResultDto(id,"fail",2000L,payResult.get("trxid"));
                            log.info("状态码2000返回的数据{}",dto);
                            return dto;
                        }
                        //0000是交易成功,其他都是失败
                        if (!"0000".equals(payResult.get("trxstatus").toString())){
                            log.info("收费状态不是0000:"+payResult.get("errmsg"));
                            throw new ServiceException(payResult.get("errmsg"));
                        }
                        param.setConsumeId(payResult.get("trxid"));//交易订单号
                    } else {
                        throw new ServiceException("请求支付失败!");
                    }
                } catch (Exception e) {
                    log.info("支付失败：{}、{}", param, e);
                    System.out.println("支付失败:" + e);
                    throw new ServiceException("支付失败!");
                }
            }
        } else if (param.getKkfs() == Kkfs.SUIXING.value()) {
            //随行支付扣款操作
            if (StringUtils.isEmpty(param.getConsumeId())) {
                SuiXingReverseScanParam suiXingReverseScanParam = new SuiXingReverseScanParam(id,param.getLimit().toString(),param.getCardId());
                log.info("---------随行支付扣款---------");
                applicationEventPublisher.publishEvent(new SuiXingScanPayEvent(suiXingReverseScanParam));
                Map<String, Object> map = suiXingReverseScanParam.getPayResult();
                //判断支付状态
                if ("SUCCESS".equals(map.get("tranSts"))){
                    //交易成功
                    param.setConsumeId(map.get("sxfUuid").toString());
                }else if ("PAYING".equals(map.get("tranSts"))){
                    //支付中
                    PayResultDto dto = new PayResultDto(id,"fail",2000L,map.get("sxfUuid").toString());
                    log.info("状态码2000返回的数据{}",dto);
                    return dto;
                }else {
                    //交易失败
                    throw new ServiceException("随行支付失败!");
                }
            }

        }
        log.info("记账结算支付成功！");

        //体检者结算方式表
        Double jsMoney = 0d;
        PeisReserPayway peisReserPayway = new PeisReserPayway();
        peisReserPayway.setId(id);
        peisReserPayway.setCardno(StringUtils.isNotEmpty(param.getConsumeId())?param.getConsumeId():param.getCardId());
        peisReserPayway.setIdCharge(chargeId);
        peisReserPayway.setMoneyamountpaid(param.getLimit());
        peisReserPayway.setMoneyamountpaiddate(new Date());
        peisReserPayway.setIdFeecharger(SecurityUtils.getUserNo());
        peisReserPayway.setIdPayway(param.getIdPayway());
        peisReserPayway.setIsCharged(1);
        peisReserPaywayService.save(peisReserPayway);
        jsMoney += peisReserPayway.getMoneyamountpaid();

        // 结算信息不存在则插入
        charge.setMoneyamountpaid(Arith.add(charge.getMoneyamountpaid(), jsMoney));
        peispatientReservationChargeMapper.updateById(charge);
        return new PayResultDto(null, "success", null, null);
    }
}

