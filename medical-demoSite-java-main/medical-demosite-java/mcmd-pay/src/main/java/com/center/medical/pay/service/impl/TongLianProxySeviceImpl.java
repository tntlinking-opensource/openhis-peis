package com.center.medical.pay.service.impl;


import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.bean.model.*;
import com.center.medical.common.utils.QrCodeUtils;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.dao.PeispatientChargeMainMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.pay.bean.dto.AllItemsDto;
import com.center.medical.pay.bean.dto.OrderInfoByPatientCodeDto;
import com.center.medical.pay.bean.model.PayProperties;
import com.center.medical.pay.bean.model.PayRequestBody;
import com.center.medical.pay.dao.TongLianProxyMapper;
import com.center.medical.pay.service.TongLianProxySevice;
import com.center.medical.service.PeispatientchargeService;
import com.center.medical.service.TempFeeitemService;
import com.center.medical.service.TradeRecordService;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自助登记机通联支付接口
 */
@Slf4j
@Service("payService")
@RequiredArgsConstructor
public class TongLianProxySeviceImpl implements TongLianProxySevice {

    private final TongLianProxyMapper tongLianProxyMapper;
    private final PeispatientchargeService peispatientchargeService;
    private final TempFeeitemService tempFeeitemService;
    private final TradeRecordService tradeRecordService;
    private final PayProperties properties;
    private final Snowflake snowflake;
    private final PeispatientMapper peispatientMapper;
    private final String CHARGE_ID = "syy2";
    private final PeispatientChargeMainMapper peispatientChargeMainMapper;


    public static final String ID_PAYWAY = "22";
    public static final String PAYWAY = "通联";
    public final Map<String, String> orderMap = new ConcurrentHashMap<>();

    /**
     * 自助登记机通联支付接口
     *
     * @param patientCode
     * @param tempIds
     * @param machineId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)

    public PayRequestBody createOrder(String patientCode, String tempIds, String machineId) throws Exception {
        if (checkNotPayRecentOrder(patientCode)) {
            //恢复订单
            return recoveryOrder(patientCode, tempIds, machineId);
        } else {
            return createNewAddOrder(patientCode, tempIds, machineId);
        }
    }


    /**
     * 创建新订单
     *
     * @param machine_id
     * @param patientCode 体检号
     * @param tempIds     加项ID
     * @param patientCode 体检号
     * @return 订单实体
     * @throws IOException
     * @throws WriterException
     */
    @Transactional
    public PayRequestBody createNewAddOrder(String patientCode, String tempIds, String machine_id) throws IOException, WriterException {
        if (StringUtils.isEmpty(patientCode)) {
            throw new RuntimeException("体检号不能为空");
        }
        if (StringUtils.isEmpty(machine_id)) {
            machine_id = SecurityUtils.getUserNo();
        }
        String tradeNo = "AD" + snowflake.nextId();
        System.out.println("tradeNo=" + tradeNo);
        BigDecimal decimal = new BigDecimal(0);
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        Integer numIndex = getChargeMaxNumIndex(patientCode);
        ArrayList<String> itemIds = new ArrayList<>();
        List<AllItemsDto> items = getAllItems(patientCode);
        for (AllItemsDto item : items) {
            decimal = decimal.add(item.getPrice());
            itemIds.add(item.getId());
        }
        numIndex = numIndex == null ? 0 : numIndex;
        //region 加项收费
        if (decimal.doubleValue() != 0) {
            Peispatientcharge newPeispatientCharge = createNewPeispatientCharge(patientCode, decimal.doubleValue(), peispatient.getShortCode(), numIndex, tradeNo, 0, machine_id);
            peispatientchargeService.save(newPeispatientCharge);
        }
//        updateOrderItemTradeNo(itemIds, tradeNo, newPeispatientCharge.getId());
        decimal = decimal.add(createTempOrder(tempIds, patientCode, peispatient.getShortCode(), tradeNo, numIndex, machine_id));
        updateChargeMain(decimal, peispatient.getPatientcode());
        peispatient.setMoneyamount((ObjectUtils.isNotEmpty(peispatient.getMoneyamount()) ? peispatient.getMoneyamount() : 0.00)
                + (decimal.doubleValue() != 0 ? decimal.doubleValue() : 0.00));
        peispatientMapper.updateById(peispatient);
        //endregion
        PayRequestBody requestBody = createOrderBody(tradeNo, decimal);
        orderMap.put(requestBody.getOid(), "0");
        return requestBody;
    }


    private void updateChargeMain(BigDecimal decimal, String peispatient) {
        PeispatientChargeMain peispatientChargeMain = peispatientChargeMainMapper.getByPatientCode(peispatient);
        if (peispatientChargeMain != null) {
            Double moneyamount = peispatientChargeMain.getMoneyamount();
            if (ObjectUtils.isEmpty(moneyamount)){
                moneyamount = 0.0;
            }
            peispatientChargeMain.setMoneyamount(moneyamount + (ObjectUtils.isNotEmpty(decimal) ? decimal.doubleValue() : 0.00));
            peispatientChargeMainMapper.updateById(peispatientChargeMain);
        }
    }

    /**
     * 创建新的订单主表实体
     *
     * @param patientCode 体检号
     * @param moneyamount 应付金额
     * @param shortCode   体检号短码
     * @param numIndex    支付序列
     * @param tradeNo     订单号
     * @param machine_id
     * @return 订单主表实体
     */
    @Transactional
    public Peispatientcharge createNewPeispatientCharge(String patientCode, Double moneyamount, Integer shortCode, Integer numIndex, String tradeNo, Integer isAdd, String machine_id) {
        Peispatientcharge charge = new Peispatientcharge();
        charge.setPatientcode(patientCode);
        charge.setTradeNo(tradeNo);
        charge.setIdPayway(ID_PAYWAY);
        charge.setPayway(PAYWAY);
        charge.setMoneyamount(moneyamount);
        charge.setMoneyamountpaid(moneyamount);
        charge.setFFeeconfirmed(0);
        charge.setFFeecharged(0);
        charge.setFFeechargedinttrans(0);
        charge.setIdFeecharger(CHARGE_ID);
        charge.setFeechargetime(new Date());
        charge.setNote("自助机扫码消费");
        charge.setFFeechargedbyinterface(0);
        charge.setIsTotalcharge(0);
        charge.setIsDelete(0);
        charge.setShortCode(shortCode);
        charge.setNumIndex(numIndex + 1);
        charge.setIsAdd(isAdd);
        charge.setIdFeecharger(machine_id);
        return charge;
    }


    /**
     * 检查最近是否有未支付订单
     *
     * @param patientCode
     * @return
     */
    private boolean checkNotPayRecentOrder(String patientCode) {
        Integer integer = tongLianProxyMapper.checkNotPayRecentOrder(patientCode);
        return ObjectUtils.isNotEmpty(integer) && integer != 1;
    }


    /**
     * 恢复订单（订单未支付时恢复订单）
     *
     * @param patientCode 体检号
     * @param tempIds     加项ID
     * @param machine_id
     * @return 订单实体
     */
    public PayRequestBody recoveryOrder(String patientCode, String tempIds, String machine_id) throws IOException, WriterException {
        if (StringUtils.isEmpty(machine_id)) {
            machine_id = "syy2";
        }
        OrderInfoByPatientCodeDto map = getOrderInfoByPatientCode(patientCode);
        String tradeNo = map.getTradeNo();
        log.info("tradeNo=" + tradeNo);
        BigDecimal decimal = map.getPrice();
        String id = map.getId();
        BigDecimal decimalNew = new BigDecimal(0);
        ArrayList<String> itemIds = new ArrayList<>();
//        获取未付费所有项目
        List<AllItemsDto> items = getAllItems(patientCode);
        for (AllItemsDto item : items) {
            itemIds.add(item.getId());
            decimalNew = decimalNew.add(item.getPrice());
        }
//        updateOrderItemTradeNo(itemIds, tradeNo, id);
        if (!decimalNew.equals(decimal)) {
//            如果订单金额不一致，更新主表
            decimal = decimalNew;
            updateChargeMoneyAmount(decimalNew, id);
        }
        //获取体检者表
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        //region 计算加项内容
        Integer numIndex = getChargeMaxNumIndex(patientCode);
        numIndex = numIndex == null ? 0 : numIndex;
        BigDecimal tempDecimal = createTempOrder(tempIds, patientCode, peispatient.getShortCode(), tradeNo, numIndex, machine_id);
        decimal = decimal.add(tempDecimal);
        //endregion
        PayRequestBody requestBody = createOrderBody(tradeNo, decimal);
        orderMap.put(requestBody.getOid(), "0");
        return requestBody;
    }


    /**
     * 创建订单支付实体
     *
     * @param tradeNo 订单号
     * @param decimal 交易金额
     * @return 订单支付实体
     * @throws IOException
     * @throws WriterException
     */
    public PayRequestBody createOrderBody(String tradeNo, BigDecimal decimal) throws IOException, WriterException {
        PayRequestBody requestBody = new PayRequestBody();
        requestBody.setOid(tradeNo);
        requestBody.setAmt(decimal.toPlainString());
//        requestBody.setAmt(BigDecimal.valueOf(0.01).toPlainString());
        BufferedImage qrCode = createOrderQrCode(requestBody);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(qrCode, "jpg", byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String string = Base64.encodeBase64String(bytes);
//        String base64URLSafeString = Base64.encodeBase64URLSafeString(byteArrayOutputStream.toByteArray());
        requestBody.setImg(string);
        return requestBody;
    }


    /**
     * 创建订单信息二维码
     *
     * @param requestBody 订单支付实体
     * @return 二维码图片
     */
    public BufferedImage createOrderQrCode(PayRequestBody requestBody) throws IOException, WriterException {
        requestBody.setAppid(properties.getAppid());
        requestBody.setReturl(properties.getProxyUrl());
        requestBody.setC(properties.getC());
        requestBody.setKey(properties.getKey());
        String testUrl = requestBody.getPayProductiveUrl();
        System.out.println("testUrl = " + testUrl);
        return QrCodeUtils.createImage(testUrl);
    }

    /**
     * 创建或恢复加项订单
     *
     * @param tiids       加项ID
     * @param patientcode 体检号
     * @param shortCode
     * @param outTradeNo  订单号
     * @param numIndex
     * @param machineId
     * @return 加项金额
     */
    private BigDecimal createTempOrder(String tiids, String patientcode, Integer shortCode, String outTradeNo, Integer numIndex, String machineId) {
        //        删除未支付的加项订单
//        deleteAddOrderNotPayTradeRecordByPatientcode(patientcode);
        BigDecimal decimal = new BigDecimal(0);
        if (StringUtils.isNotEmpty(tiids) && StringUtils.isNotEmpty(tiids.replaceAll(",", ""))) {
            List<TempFeeitem> tfs = tempFeeitemService.list(new QueryWrapper<TempFeeitem>()
                    .in("id", tiids.split(","))
                    .eq("patientcode", patientcode)
                    .eq("is_delete", 0)
            );
            decimal = new BigDecimal("0");
            for (TempFeeitem tf : tfs) {
                decimal = decimal.add(BigDecimal.valueOf(tf.getPrice()));
//                numIndex = updateTempItemToFeeItem(patientcode, shortCode, numIndex, tf);
            }
            if (decimal.doubleValue() > 0) {
                TradeRecord tradeRecord = createNewTradeRecord(patientcode, outTradeNo, tiids, decimal, machineId, 1);
                tradeRecordService.save(tradeRecord);
//                PeispatientCharge newPeispatientCharge = createNewPeispatientCharge(patientcode, decimal.doubleValue(), shortCode, numIndex, outTradeNo, 1, machineId);
//                peiPatientService.executeSave(newPeispatientCharge);
            }
        }
        return decimal;
    }

    /**
     * 支付宝、微信生成二维码记录
     *
     * @param patientCode
     * @param tradeNo
     * @param tmpids
     * @param amount
     * @param machine_id
     * @param orderType
     * @return
     */
    private TradeRecord createNewTradeRecord(String patientCode, String tradeNo, String tmpids, BigDecimal amount, String machine_id, Integer orderType) {
        TradeRecord tradeRecord = new TradeRecord();
        tradeRecord.setPatientcode(patientCode);
        tradeRecord.setOutTradeNo(tradeNo);
        tradeRecord.setStatus(0);
        tradeRecord.setMoney(amount.doubleValue());
        tradeRecord.setTempItemIds(tmpids);
        tradeRecord.setRegisterId(machine_id);
        tradeRecord.setCreatedate(new Date());
        tradeRecord.setOrderType(orderType);
        return tradeRecord;
    }

    /**
     * 获取体检号的最大序列
     *
     * @param patientCode
     * @return
     */
    private Integer getChargeMaxNumIndex(String patientCode) {
        return tongLianProxyMapper.getChargeMaxNumIndex(patientCode);
    }

    /**
     * 更新订单应收金额
     *
     * @param decimalNew
     * @param id
     */
    private void updateChargeMoneyAmount(BigDecimal decimalNew, String id) {
        Peispatientcharge peispatientcharge = new Peispatientcharge();
        peispatientcharge.setMoneyamount(decimalNew.doubleValue());
        peispatientcharge.setMoneyamountpaid(decimalNew.doubleValue());
        peispatientcharge.setId(id);
        peispatientchargeService.updateById(peispatientcharge);
    }


    /**
     * 获取支付项目
     *
     * @param patientCode
     * @return
     */
    private List<AllItemsDto> getAllItems(String patientCode) {
        return tongLianProxyMapper.getAllItems(patientCode);
    }


    /**
     * 或缺订单部分详情
     *
     * @param patientCode
     * @return
     */
    private OrderInfoByPatientCodeDto getOrderInfoByPatientCode(String patientCode) {
        OrderInfoByPatientCodeDto dto = tongLianProxyMapper.getOrderInfoByPatientCode(patientCode);
        return dto;
    }
}
