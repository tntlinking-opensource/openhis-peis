package com.center.medical.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.GetTempFeeitemDto;
import com.center.medical.bean.dto.PayResultDto;
import com.center.medical.bean.event.TongLianPayEvent;
import com.center.medical.bean.event.TongLianScanPayEvent;
import com.center.medical.bean.model.*;
import com.center.medical.bean.param.GetTempFeeitemParam;
import com.center.medical.bean.param.PaymentParam;
import com.center.medical.bean.param.TongLianPayParam;
import com.center.medical.bean.param.TongLianScanPayParam;
import com.center.medical.bean.vo.GetTempFeeitemVo;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.*;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.*;
import com.center.medical.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 科室临时加项表(TempFeeitem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:45
 */
@Slf4j
@Service("tempFeeitemService")
@RequiredArgsConstructor
public class TempFeeitemServiceImpl extends ServiceImpl<TempFeeitemMapper, TempFeeitem> implements TempFeeitemService {

    private final TempFeeitemMapper tempFeeitemMapper;
    private final PeispatientService peispatientService;
    private final MapperFacade mapperFacade;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final Snowflake snowflake;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final PeispatientChargeMainMapper peispatientChargeMainMapper;
    private final PeispatientChargeRecordMapper peispatientChargeRecordMapper;
    private final DictpaywayMapper dictpaywayMapper;
    private final PeispatientchargeMapper peispatientchargeMapper;
    private final PeisStateService peisStateService;
    private final DictpaywayService dictpaywayService;

    /**
     * 分页查询[科室临时加项表]列表
     *
     * @param page  分页参数
     * @param param TempFeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TempFeeitem> getList(PageParam<TempFeeitem> page, TempFeeitem param) {
        return tempFeeitemMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public TempFeeitem getInfoById(String id) {
        return tempFeeitemMapper.getInfoById(id);
    }

    /**
     * 查询科室加项数据
     * @param param
     * @return
     */
    @Override
    public GetTempFeeitemVo getTempFeeitem(GetTempFeeitemParam param) {
        if (StringUtils.isEmpty(param.getPatientcode())){
            throw new ServiceException("请输入体检号!");
        }
        if (ObjectUtils.isNotEmpty(param.getShowAll()) && param.getShowAll() == 1){
            param.setUserName(SecurityUtils.getUsername());
        }
        Peispatient peispatient = peispatientService.getByPatientCode(param.getPatientcode());
        GetTempFeeitemVo vo = mapperFacade.map(peispatient, GetTempFeeitemVo.class);
        List<GetTempFeeitemDto> list = tempFeeitemMapper.getTempFeeitem(param);
        vo.setList(list);
        Double total = 0.0;
        for (GetTempFeeitemDto dto : list) {
            total = Arith.add(total,dto.getPrice());
        }
        vo.setTotalPrice(total);
        return vo;
    }



    /**
     * 付款
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayResultDto payment(PaymentParam param) {
        if (StringUtils.isEmpty(param.getPatientcode()) || StringUtils.isEmpty(param.getIdPayway()) || ObjectUtils.isEmpty(param.getTotalPrice())){
            throw new ServiceException("请填写完整数据!");
        }
        Dictpayway dictpayway = dictpaywayService.getInfoById(param.getIdPayway());
        if (ObjectUtils.isEmpty(dictpayway)){
            throw new ServiceException("支付方式不存在!");
        }
        PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new LambdaQueryWrapper<PeispatientChargeMain>()
                .eq(PeispatientChargeMain::getPatientcode, param.getPatientcode()));
        if (Objects.isNull(pcm)) {
            throw new ServiceException("未找到收费信息主表！");
        }


        //是加项，并且加项人是当前登录用户，并且金额实付是0的,就是当前人生成的
        Peispatientcharge charge = peispatientchargeMapper.selectOne(new LambdaQueryWrapper<Peispatientcharge>()
                .eq(Peispatientcharge::getIsAdd, 1)
                .eq(Peispatientcharge::getIdFeecharger, SecurityUtils.getUserNo())
                .ne(Peispatientcharge::getMoneyamount,0)
                .eq(Peispatientcharge::getMoneyamountpaid, 0)
                .eq(Peispatientcharge::getIsDelete, 0)
                .eq(Peispatientcharge::getFFeecharged, 0)
        );

        //删除，并重新生成记录
        if (ObjectUtils.isNotEmpty(charge)){
            peispatientchargeMapper.deleteById(charge);
        }
        charge = mapperFacade.map(param, Peispatientcharge.class);
        charge.setPayway(dictpayway.getPaywayName());
        charge.setIsAdd(1);
        charge.setIdFeecharger(SecurityUtils.getUserNo());
        charge.setMoneyamount(param.getTotalPrice());
        charge.setMoneyamountpaid(0.0);
        charge.setIsDelete(0);
        charge.setFFeecharged(0);
        charge.setShortCode(CodeUtil.getShortCodeByLong(param.getPatientcode()));
        charge.setId(String.valueOf(snowflake.nextId()));
        peispatientchargeMapper.insert(charge);


        PayResultDto payResultDto = new PayResultDto();
        String id = charge.getId();
        Long newVersion = new Date().getTime();
        payResultDto.setId(id);
        //执行扣款操作
        if ("22".equals(param.getIdPayway())){
            //扫码枪扫码
            TongLianScanPayParam payParam = new TongLianScanPayParam();
            payParam.setTrxamt((long) (param.getTotalPrice() * 100));//单位是分
            payParam.setReqsn(id);
            payParam.setBody("体检支付");
            payParam.setRemark("");
            payParam.setAuthcode(param.getCardId());
            try {
                log.info("---------通联支付扣款---------");
                applicationEventPublisher.publishEvent(new TongLianScanPayEvent(payParam));
                Map<String, String> payResult = payParam.getPayResult();
                print(payResult);
                if ("SUCCESS".equals(payResult.get("retcode"))){
                    //当结果码为2000时，商户系统可设置间隔时间(建议10秒)重新查询支付结果，直到支付成功或超时(建议50秒)
                    //除了0000 其他都是支付失败
                    if ("2000".equals(payResult.get("trxstatus"))){
                        payResultDto.setStatus("fail");
                        payResultDto.setVersion(2000L);
                        payResultDto.setPayNo(payResult.get("trxid"));
                    }else if (!"0000".equals(payResult.get("trxstatus"))){
                        throw new ServiceException(payResult.get("errmsg"));
                    }else {
                        payResultDto.setStatus("success");
                        payResultDto.setVersion(newVersion);
                        payResultDto.setPayNo(payResult.get("trxid"));
                    }
                } else {
                    throw new ServiceException("请求支付失败!");
                }
            } catch (Exception e) {
                log.info("支付失败：{}、{}", param, e);
                System.out.println("支付失败:" + e);
                throw new ServiceException("支付失败!");
            }
        }else if (("101".equals(param.getIdPayway()))){
            //生成二维码，用户扫码
            TongLianPayParam payParam = new TongLianPayParam();
            payParam.setTrxamt((long) (param.getTotalPrice() * 100));//单位是分
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
        }
        //把支付流水号，放到收费记录里面，如果页面刷新，还能再次查询
        charge.setTxTradeNo(payResultDto.getPayNo());
        charge.setCardno(payResultDto.getPayNo());
        peispatientchargeMapper.updateById(charge);

        if ("fail".equals(payResultDto.getStatus())){
            return payResultDto;
        }else if ("success".equals(payResultDto.getStatus())){
            //生成收费项目
            List<GetTempFeeitemDto> list = param.getList();
            for (GetTempFeeitemDto dto : list) {
                Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
                peispatientfeeitem.setIdPatient(dto.getPatientcode());
                peispatientfeeitem.setIdExamfeeitem(dto.getItemId());
                peispatientfeeitem.setExamfeeitemName(dto.getExamfeeitemName());
                peispatientfeeitem.setPrice(dto.getPrice());
                peispatientfeeitem.setFactprice(dto.getPrice());
                peispatientfeeitem.setIdPayway("1");//现金
                peispatientfeeitem.setFRegistered(1);
                peispatientfeeitem.setIdDoctorreg(SecurityUtils.getUserNo());
                peispatientfeeitem.setDoctorregR(SecurityUtils.getUsername());
                peispatientfeeitem.setRegistertime(new Date());
                peispatientfeeitem.setFFeecharged(1);
                peispatientfeeitem.setIdFeecharger(SecurityUtils.getUserNo());
                peispatientfeeitem.setFeechargetime(new Date());
                peispatientfeeitem.setSfjj(0);
                peispatientfeeitem.setSfjx(1);
                peispatientfeeitem.setJxys(SecurityUtils.getUserNo());
                peispatientfeeitem.setIdKs(dto.getIdDepart());
                peispatientfeeitem.setEndtuiprice(-dto.getPrice());
                peispatientfeeitem.setActualprice(dto.getPrice());
                peispatientfeeitem.setShortCode(ToolUtil.getShortCodeByLong(dto.getPatientcode()));
                peispatientfeeitem.setChangeItem(0);
                peispatientfeeitem.setFMarkFeereturn(0);
                peispatientfeeitem.setFLabsendtolis(0);
                peispatientfeeitem.setFExaminated(0);
                peispatientfeeitem.setFGiveup(0);
                peispatientfeeitemService.save(peispatientfeeitem);

                //把科室加项设为删除状态
                TempFeeitem tempFeeitem = new TempFeeitem();
                tempFeeitem.setId(dto.getId());
                tempFeeitem.setIsDelete(1);
                tempFeeitem.setFeeitemId(peispatientfeeitem.getId());
                tempFeeitemMapper.updateById(tempFeeitem);
            }
            //修改体检者主表 体检者状态
            Peispatient patient = peispatientService.getByPatientCode(param.getPatientcode());
            patient.setMoneyamount(Arith.add(patient.getMoneyamount(),param.getTotalPrice()));
            patient.setMoneyamountpaid(Arith.add(patient.getMoneyamountpaid(),param.getTotalPrice()));
            patient.setPersonpricelimit(Arith.add(patient.getPersonpricelimit(),param.getTotalPrice()));
            peisStateService.saOrUp(patient, 0);
            peispatientService.updateById(patient);

            //更新收费主表信息
            pcm.setVersion(newVersion);
            pcm.setMoneyamount(patient.getMoneyamount());
            pcm.setMoneyamountpaid(patient.getMoneyamountpaid());
            peispatientChargeMainMapper.updateById(pcm);

            //生成收费记录
            PeispatientChargeRecord pcr = new PeispatientChargeRecord();
            pcr.setPatientcode(param.getPatientcode());
            pcr.setVersion(newVersion);
            pcr.setMethod("pay");
            pcr.setMoneyamount(pcm.getMoneyamount());
            pcr.setMoneyamountpaid(pcm.getMoneyamountpaid());
            pcr.setMoneyamountOld(pcm.getMoneyamount());
            pcr.setMoneyamountpaidOld(pcm.getMoneyamountpaid());
            pcr.setUsername(SecurityUtils.getUsername());
            pcr.setNote("pay");
            peispatientChargeRecordMapper.insert(pcr);


            //修改金额
            charge.setMoneyamountpaid(param.getTotalPrice());
            charge.setFeechargetime(new Date());
            charge.setFFeecharged(1);
            charge.setIdFeecharger(SecurityUtils.getUserNo());
            peispatientchargeMapper.updateById(charge);



        }

        return payResultDto;
    }

    /**
     * 支付完调用
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean callBack(PaymentParam param) {
        if (StringUtils.isEmpty(param.getConsumeId())){
            throw new ServiceException("请输入交易流水号!");
        }
        //判断金额是否一致
        Peispatientcharge peispatientcharge = peispatientchargeMapper.selectOne(new LambdaQueryWrapper<Peispatientcharge>().eq(Peispatientcharge::getTxTradeNo, param.getConsumeId()).eq(Peispatientcharge::getIdFeecharger,SecurityUtils.getUserNo()));
        if (ObjectUtils.isEmpty(peispatientcharge)){
            throw new ServiceException("未查询到交易流水号!");
        }
        boolean b = Arith.isEquals(peispatientcharge.getMoneyamount(), param.getTotalPrice());
        if (!b){
            throw new ServiceException("总价和流水号金额不一致!");
        }
        PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new LambdaQueryWrapper<PeispatientChargeMain>()
                .eq(PeispatientChargeMain::getPatientcode, param.getPatientcode()));
        if (Objects.isNull(pcm)) {
            throw new ServiceException("未找到收费信息主表！");
        }
        Long newVersion = new Date().getTime();

        //进行支付成功的逻辑
        List<GetTempFeeitemDto> list = param.getList();
        for (GetTempFeeitemDto dto : list) {
            Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
            peispatientfeeitem.setIdPatient(dto.getPatientcode());
            peispatientfeeitem.setIdExamfeeitem(dto.getItemId());
            peispatientfeeitem.setExamfeeitemName(dto.getExamfeeitemName());
            peispatientfeeitem.setPrice(dto.getPrice());
            peispatientfeeitem.setFactprice(dto.getPrice());
            peispatientfeeitem.setIdPayway("1");
            peispatientfeeitem.setFRegistered(1);
            peispatientfeeitem.setIdDoctorreg(SecurityUtils.getUserNo());
            peispatientfeeitem.setDoctorregR(SecurityUtils.getUsername());
            peispatientfeeitem.setRegistertime(new Date());
            peispatientfeeitem.setFFeecharged(1);
            peispatientfeeitem.setIdFeecharger(SecurityUtils.getUserNo());
            peispatientfeeitem.setFeechargetime(new Date());
            peispatientfeeitem.setSfjj(0);
            peispatientfeeitem.setSfjx(1);
            peispatientfeeitem.setJxys(SecurityUtils.getUserNo());
            peispatientfeeitem.setIdKs(dto.getIdDepart());
            peispatientfeeitem.setEndtuiprice(-dto.getPrice());
            peispatientfeeitem.setActualprice(dto.getPrice());
            peispatientfeeitem.setShortCode(ToolUtil.getShortCodeByLong(dto.getPatientcode()));
            peispatientfeeitem.setChangeItem(0);
            peispatientfeeitem.setFMarkFeereturn(0);
            peispatientfeeitem.setFLabsendtolis(0);
            peispatientfeeitem.setFExaminated(0);
            peispatientfeeitem.setFGiveup(0);
            peispatientfeeitemService.save(peispatientfeeitem);

            //把科室加项设为删除状态
            TempFeeitem tempFeeitem = new TempFeeitem();
            tempFeeitem.setId(dto.getId());
            tempFeeitem.setIsDelete(1);
            tempFeeitem.setFeeitemId(peispatientfeeitem.getId());
            tempFeeitemMapper.updateById(tempFeeitem);
        }
        //修改体检者主表
        Peispatient patient = peispatientService.getByPatientCode(param.getPatientcode());
        patient.setMoneyamount(Arith.add(patient.getMoneyamount(),param.getTotalPrice()));
        patient.setMoneyamountpaid(Arith.add(patient.getMoneyamountpaid(),param.getTotalPrice()));
        patient.setPersonpricelimit(Arith.add(patient.getPersonpricelimit(),param.getTotalPrice()));
        //体检者状态
        peispatientService.updateById(patient);
        peisStateService.saOrUp(patient, 0);


        //更新收费主表信息
        pcm.setMoneyamount(patient.getMoneyamount());
        pcm.setMoneyamountpaid(patient.getMoneyamountpaid());
        pcm.setVersion(newVersion);
        peispatientChargeMainMapper.updateById(pcm);

        //生成收费记录
        PeispatientChargeRecord pcr = new PeispatientChargeRecord();
        pcr.setPatientcode(param.getPatientcode());
        pcr.setVersion(newVersion);
        pcr.setMethod("pay");
        pcr.setMoneyamount(pcm.getMoneyamount());
        pcr.setMoneyamountpaid(pcm.getMoneyamountpaid());
        pcr.setMoneyamountOld(pcm.getMoneyamount());
        pcr.setMoneyamountpaidOld(pcm.getMoneyamountpaid());
        pcr.setUsername(SecurityUtils.getUsername());
        pcr.setNote("pay");
        peispatientChargeRecordMapper.insert(pcr);


        //修改金额
        peispatientcharge.setMoneyamountpaid(param.getTotalPrice());
        peispatientcharge.setFeechargetime(new Date());
        peispatientcharge.setFFeecharged(1);
        peispatientcharge.setIdFeecharger(SecurityUtils.getUserNo());
        peispatientchargeMapper.updateById(peispatientcharge);



        return Boolean.TRUE;
    }


    public static void print(Map<String, String> map){
        System.out.println("返回数据如下:");
        if(map!=null){
            for(String key:map.keySet()){
                System.out.println(key+";"+map.get(key));
            }
        }
    }
}

