package com.center.medical.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.PayResultDto;
import com.center.medical.bean.dto.PeispatientchargeDto;
import com.center.medical.bean.enums.CardConsumeType;
import com.center.medical.bean.enums.Kkfs;
import com.center.medical.bean.enums.NewProjectHandleType;
import com.center.medical.bean.event.*;
import com.center.medical.bean.model.*;
import com.center.medical.bean.param.*;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.*;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.*;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientchargeService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysUserMapper;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * 体检者缴费表(Peispatientcharge)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:06
 */
@Slf4j
@Service("peispatientchargeService")
@RequiredArgsConstructor
public class PeispatientchargeServiceImpl extends ServiceImpl<PeispatientchargeMapper, Peispatientcharge> implements PeispatientchargeService {

    private final PeispatientchargeMapper peispatientchargeMapper;
    private final DictpaywayMapper dictpaywayMapper;
    private final PeispatientMapper peispatientMapper;
    private final PeispatientChargeMainMapper peispatientChargeMainMapper;
    private final MapperFacade mapperFacade;
    private final PeispatientReservationChargeMapper peispatientReservationChargeMapper;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final HandleNewProjectsMapper handleNewProjectsMapper;
    private final PeisStateService peisStateService;
    private final PeispatientChargeRecordMapper peispatientChargeRecordMapper;
    private final SysUserMapper sysUserMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final Snowflake snowflake;

    /**
     * 分页查询[体检者缴费表]列表
     *
     * @param page  分页参数
     * @param param Peispatientcharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatientcharge> getList(PageParam<Peispatientcharge> page, Peispatientcharge param) {
        return peispatientchargeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Peispatientcharge getInfoById(String id) {
        return peispatientchargeMapper.getInfoById(id);
    }

    /**
     * 团检客户没有增加项目自动保存收费信息
     *
     * @param chargeDto
     * @return
     */
    @Override
    public Boolean autoSaveOrUpdate(PeispatientchargeDto chargeDto) {
        chargeDto.setIsTotalcharge(1);
        chargeDto.setTong(1);
        chargeDto.setFFeecharged(1);
        chargeDto.setFeechargetime(new Date());
        chargeDto.setCardno(null);
        //支付方式
        Dictpayway pay = dictpaywayMapper.selectOne(new LambdaQueryWrapper<Dictpayway>().eq(Dictpayway::getPaywayName, "统收"));
        chargeDto.setIdPayway(pay.getId());
        chargeDto.setPayway(pay.getPaywayName());

        int numIndex = 1;
        List<Peispatientcharge> chargeList = peispatientchargeMapper.selectList(new LambdaQueryWrapper<Peispatientcharge>()
                .eq(Peispatientcharge::getPatientcode, chargeDto.getPatientcode())
                .isNotNull(Peispatientcharge::getNumIndex)
                .orderByDesc(Peispatientcharge::getNumIndex));
        if (CollectionUtil.isNotEmpty(chargeList)) {
            if (Objects.nonNull(chargeList.get(0).getNumIndex())) {
                numIndex = chargeList.get(0).getNumIndex() + 1;
            }
        }
        chargeDto.setNumIndex(numIndex);
        chargeDto.setState("added");
        List<PeispatientchargeDto> list = Arrays.asList(chargeDto);

        return saveOrUpdates(chargeDto.getPatientcode(), list, "", null, false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdates(String patientCode, List<PeispatientchargeDto> griddata, String removeIds
            , String noticeJyk, boolean check) {
        // 体检者
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        if (ObjectUtils.isEmpty(peispatient)) {
            throw new ServiceException("收退款失败:该体检号" + patientCode + "不存在，已经被删除");
        }
        /**
         * 进入收费页面时，所有收费记录都已收或已退不可编辑,完成登记后记账也不能修改删除，数据库中的收费记录只可能增加
         * spring事务隔离级别应该是可以读已提交数据
         * flush 同一个体检号的操作必须等上一次提交事务才能继续
         */
        peispatient.setModifydate(new Date());
        if (check) {
            List<Peispatientcharge> charges = peispatientchargeMapper.selectList(new QueryWrapper<Peispatientcharge>()
                    .eq("patientcode", patientCode).eq("is_delete", 0));
            Set<String> newIds = new HashSet<String>();
            for (PeispatientchargeDto map : griddata) {
                if (ObjectUtils.isNotEmpty(map.getId())) {
                    newIds.add(map.getId());
                }
            }
            Set<String> newIds2 = new HashSet<String>();
            for (Peispatientcharge ch : charges) {
                newIds2.add(ch.getId());
            }
            if (!ToolUtil.setEquals(newIds, newIds2)) {
                throw new ServiceException("收费信息已发生改变，请刷新重试！");
            }
        }

        // 原先实收实退
        PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new QueryWrapper<PeispatientChargeMain>()
                .eq("patientcode", peispatient.getPatientcode()));
        Double oldPaid = (pcm == null || pcm.getMoneyamountpaid() == null ? 0d : pcm.getMoneyamountpaid());

        // 每种收费方式的实收合计
        Map<String, Double> yingMap = new HashMap<String, Double>();
        // 每种收费方式的实退合计
        Map<String, Double> tYingMap = new HashMap<String, Double>();
        Double tMoney = 0d;
        // 是否存在统收自动收费情况
        int tong = 0;
        // 第一次收费还是多次收费 0:第一次收费 1：加项收费
        int oneTime = 0;
        // 是否存在记账
        Boolean isJz = false;
        // 记账金额
        Double jzMoney = 0d;
        Dictpayway pay = dictpaywayMapper.selectOne(new QueryWrapper<Dictpayway>().eq("payway_name", "记账"));
        if (StringUtils.isNotEmpty(removeIds)) {
            for (String id : removeIds.split(",")) {
                Peispatientcharge chargeNew = peispatientchargeMapper.selectOne(new QueryWrapper<Peispatientcharge>()
                        .eq("id", id).eq("is_delete", 0).eq("patientcode", patientCode));
                // 判断是否存在
                if (null != chargeNew) {
                    chargeNew.setIsDelete(1);
                    // 更新实体类
                    peispatientchargeMapper.updateById(chargeNew);
                }
            }
        }


        for (PeispatientchargeDto map : griddata) {
            //map转成对象
            Peispatientcharge charge = mapperFacade.map(map, Peispatientcharge.class);
            //删除
            if ("removed".equals(map.getState())) {
                if (StringUtils.isNotBlank(map.getId())) {
                    Peispatientcharge chargeNew = peispatientchargeMapper.selectOne(new QueryWrapper<Peispatientcharge>()
                            .eq("id", map.getId()).eq("is_delete", 0).eq("patientcode", patientCode));
                    // 判断是否存在
                    if (ObjectUtils.isNotEmpty(chargeNew)) {
                        chargeNew.setIsDelete(1);
                        // 更新实体类
                        peispatientchargeMapper.updateById(chargeNew);
                    }
                }
            } else if ("modified".equals(map.getState())) {
                //更新
                Integer fFeecharged = map.getFFeecharged();
                if (Objects.isNull(fFeecharged) || "0".equals(fFeecharged)) {
                    // 体检者缴费表
                    Peispatientcharge pc = peispatientchargeMapper.selectOne(new QueryWrapper<Peispatientcharge>().eq("id", map.getId()).eq("is_delete", 0));
                    if (ObjectUtils.isEmpty(pc)) {
                        throw new ServiceException("收退款失败:该体检号" + patientCode + "的收费信息已被删除！");
                    }
                    BeanUtils.copyProperties(charge, pc, new String[]{"id", "isDelete", "patientcode", "shortCode"});
                    pc.setFFeecharged(1);
                    pc.setFeechargetime(new Date());
                    pc.setIdFeecharger(SecurityUtils.getUserNo());
                    pc.setFFeeconfirmed(1);
                    peispatientchargeMapper.updateById(pc);
                    log.error(pc.getPatientcode() + "-" + pc.getMoneyamount() + "-" + pc.getMoneyamountpaid());
                }
                // 实收合计
                if (charge.getMoneyamountpaid() > 0) {
                    if (null != yingMap.get(charge.getIdPayway())) {
                        yingMap.put(charge.getIdPayway(), yingMap.get(charge.getIdPayway()) + charge.getMoneyamountpaid());
                    } else {
                        yingMap.put(charge.getIdPayway(), charge.getMoneyamountpaid());
                    }
                } else {
                    if (null != tYingMap.get(charge.getIdPayway())) {
                        tYingMap.put(charge.getIdPayway(), tYingMap.get(charge.getIdPayway()) + charge.getMoneyamountpaid());
                    } else {
                        tYingMap.put(charge.getIdPayway(), charge.getMoneyamountpaid());
                    }
                }
                // 判断是否存在记账收费方式
                if (charge.getIdPayway().equals(pay.getId())) {
                    // 是否存在记账人(必填)
                    if (StringUtils.isBlank(peispatient.getJzdwr())) {
                        throw new ServiceException("收退款失败:该体检号【" + patientCode + "】存在记账信息，记账人必须填写");
                    }
                    isJz = true;
                    jzMoney += charge.getMoneyamountpaid();
                }
                oneTime = 1;
            } else if ("added".equals(map.getState())) {
                // 存在统收自动收费项目
                if (null != map.getTong()) {
                    tong = 1;
                }
                // 已收费
                charge.setFFeecharged(1);
                charge.setFeechargetime(new Date());
                charge.setIdFeecharger(SecurityUtils.getUserNo());
                charge.setIsDelete(0);
                charge.setPatientcode(patientCode);
                charge.setShortCode(peispatient.getShortCode());
                charge.setFFeeconfirmed(1);

                // 退费金额
                if (charge.getMoneyamountpaid() < 0) {
                    tMoney = Arith.add(tMoney, charge.getMoneyamountpaid());
                    charge.setFIsreturn(1);
                    oneTime = 2;
                }
                // 保存实体类
                peispatientchargeMapper.insert(charge);
                String result = charge.getId();

                if (StringUtils.isBlank(result)) {
                    throw new ServiceException("收退款失败:该体检号【" + patientCode + "】的收费信息保存失败");
                }

                // 判断是否存在记账收费方式
                if (charge.getIdPayway().equals(pay.getId())) {
                    // 是否存在记账人(必填)
                    if (StringUtils.isBlank(peispatient.getJzdwr())) {
                        throw new ServiceException("收退款失败:该体检号【" + patientCode + "】存在记账信息，记账人必须填写");
                    }
                    isJz = true;
                    jzMoney = Arith.add(jzMoney, charge.getMoneyamountpaid());
                }

                if (charge.getMoneyamountpaid() > 0) {
                    // 实收合计
                    if (null != yingMap.get(charge.getIdPayway())) {
                        yingMap.put(charge.getIdPayway(), yingMap.get(charge.getIdPayway()) + charge.getMoneyamountpaid());
                    } else {
                        yingMap.put(charge.getIdPayway(), charge.getMoneyamountpaid());
                    }
                } else {
                    // 应退合计
                    if (null != tYingMap.get(charge.getIdPayway())) {
                        tYingMap.put(charge.getIdPayway(), tYingMap.get(charge.getIdPayway()) + charge.getMoneyamountpaid());
                    } else {
                        tYingMap.put(charge.getIdPayway(), charge.getMoneyamountpaid());
                    }
                }
            }
        }

        // 判断是否存在每种收费方式的退费金额总和不能大于收费金额总和
        for (Map.Entry<String, Double> m : tYingMap.entrySet()) {
            if (null == yingMap.get(m.getKey()) && !(0.0 == m.getValue().doubleValue())) {
                // 此种收费方式不存在
                Dictpayway way = dictpaywayMapper.getInfoById(m.getKey());
                throw new ServiceException("收退款失败:收费方式【" + way.getPaywayName() + "】所退款项总额不能超过本次登记该种收费方式的原缴款总额");
            } else {
                Double s = yingMap.get(m.getKey()) == null ? 0.0 : Double.valueOf(yingMap.get(m.getKey()));
                s = new BigDecimal(s).setScale(1, RoundingMode.HALF_UP).doubleValue();
                Double t = -Double.valueOf(tYingMap.get(m.getKey()));
                t = new BigDecimal(t).setScale(1, RoundingMode.HALF_UP).doubleValue();
                if (s < t) {
                    Dictpayway way = dictpaywayMapper.getInfoById(m.getKey());
                    throw new ServiceException("收退款失败:退款方式为【" + way.getPaywayName() + "】的退款总额"
                            + t + "已经超过该种收费方式的收款总额：" + s);
                }
            }
        }

        if (isJz) {
            log.info("开始生成体检者结算表：{}", patientCode);
            // 体检者结算表
            PeispatientReservationCharge charge2 = peispatientReservationChargeMapper.selectOne(new QueryWrapper<PeispatientReservationCharge>()
                    .eq("patientcode", patientCode));
            // 更新个检记账结算表
            if (null != charge2) {
                charge2.setJzje(jzMoney);
                peispatientReservationChargeMapper.updateById(charge2);
            } else {
                PeispatientReservationCharge charge = new PeispatientReservationCharge();
                charge.setPatientcode(patientCode);
                charge.setMoneyamountpaid(0.0);
                charge.setJzje(jzMoney);
                peispatientReservationChargeMapper.insert(charge);
            }
        }

        // 更新收费项目的收费时间
        List<Peispatientfeeitem> items = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>().eq("id_patient", patientCode));
        if (items.size() == 0) {
            throw new ServiceException("收退款失败:该体检号【" + patientCode + "】的收费项目不存在！");
        }
        Boolean isT = false;
        if (tMoney > 0 && peispatient.getMoneyamount() - oldPaid != tMoney) {
            throw new ServiceException("收退款失败:该体检号【" + patientCode + "】的退费金额不足！");
        } else isT = true;
        for (Peispatientfeeitem peispatientfeeitem : items) {
            // 退费
            if (peispatientfeeitem.getChangeItem() == 1 && isT) {
                if ("1".equals(noticeJyk) && (
                        peispatientfeeitem.getFMarkFeereturn() == null || peispatientfeeitem.getFMarkFeereturn() == 0)) {
                    HandleNewProjects handleNewProjects = handleNewProjectsMapper.selectOne(new QueryWrapper<HandleNewProjects>()
                            .eq("patientcode", patientCode)
                            .eq("projectsId", peispatientfeeitem.getId())
                            .eq("is_delete", 0)
                            .eq("handle_type", 4));
                    // 不存在
                    if (null == handleNewProjects) {
                        handleNewProjects = new HandleNewProjects();
                        handleNewProjects.setPatientcode(patientCode);
                        handleNewProjects.setCreateId(SecurityUtils.getUserNo());
                        handleNewProjects.setModifyId(SecurityUtils.getUserNo());
                        handleNewProjects.setProjectsId(peispatientfeeitem.getId());// 项目ID
                        handleNewProjects.setIsDelete(0);
                        handleNewProjects.setStatus(0);
                        handleNewProjects.setHandleType(4);
                        // 保存实体类
                        handleNewProjectsMapper.insert(handleNewProjects);
                    } else {
                        handleNewProjects.setModifyId(SecurityUtils.getUserNo());
                        handleNewProjects.setProjectsId(peispatientfeeitem.getId());// 项目ID
                        // 更新实体类
                        handleNewProjectsMapper.updateById(handleNewProjects);
                    }
                }
                peispatientfeeitem.setFMarkFeereturn(1);
//                peispatientfeeitem.setFeechargetime(new Date());
                //防止修改之前已经退掉的项目的退项时间
                if (peispatientfeeitem.getDtDelayedtill() == null) {
                    peispatientfeeitem.setDtDelayedtill(new Date());
                }
            }

            // 没有收费
            if (null == peispatientfeeitem.getFFeecharged() || peispatientfeeitem.getFFeecharged() != 1) {
                // 存在统收自动收费项目,只更新统收为已收费
                if (1 == tong) {
//                    if (peispatientfeeitem.getIdPayway().equals("5")) {
                    if ("5".equals(peispatientfeeitem.getIdPayway())) {
                        peispatientfeeitem.setFFeecharged(1);
                        peispatientfeeitem.setIdFeecharger(SecurityUtils.getUserNo());
                        peispatientfeeitem.setFeechargetime(new Date());
                        continue;
                    }
                } else {
                    peispatientfeeitem.setFFeecharged(1);
                    peispatientfeeitem.setIdFeecharger(SecurityUtils.getUserNo());
                    peispatientfeeitem.setFeechargetime(new Date());
                }
            }
        }

        // 批量保存
        peispatientfeeitemService.saveOrUpdateBatch(items);

        // 第一次收费
        if (oneTime == 0) {
            peispatient.setFSettlenone(0);
        } else if (oneTime == 1) {
            peispatient.setFSettlenone(2);
        } else {
            peispatient.setFSettlenone(null);
        }
        // 查找未检、未弃检、未退项、的收费项目数量
        peispatientfeeitemService.checkFj(peispatient);

        return Boolean.TRUE;
    }

    /**
     * 获取体检者收费信息
     *
     * @param patientCode
     * @return
     */
    @Override
    public List<Peispatientcharge> getChargeData(String patientCode) {
        // 判断是否为空
        if (StringUtils.isBlank(patientCode)) {
            return new ArrayList();
        }
        List<Peispatientcharge> list = peispatientchargeMapper.selectList(new QueryWrapper<Peispatientcharge>().orderByAsc("createdate")
                .eq("patientcode", patientCode).eq("is_delete", 0));
        for (Peispatientcharge item : list) {
            //支付方式
            Dictpayway dictPayWay = dictpaywayMapper.getInfoById(item.getIdPayway());
            item.setIsChange(dictPayWay.getIsChange());
            item.setPayway(dictPayWay.getPaywayName());

            //收费员
            SysUser sysUser = sysUserMapper.getUserByNo(item.getIdFeecharger());
            item.setFeechargerName(ObjectUtils.isNotEmpty(sysUser) ? sysUser.getUserName() : "");
        }

        return list;
    }

    /**
     * 登记后收费
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean receiveRefund(ChargeParam param) {
        String patientCode = param.getPatientcode();
        SysUser user = SecurityUtils.getLoginUser().getUser();
        Date now = new Date();
        // 判断收费项目是否登记
        LambdaQueryWrapper<Peispatientfeeitem> wrapper = new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, patientCode).ne(Peispatientfeeitem::getFMarkFeereturn, 1);
        wrapper.and(w -> {
            w.isNull(Peispatientfeeitem::getFRegistered).or().eq(Peispatientfeeitem::getFRegistered, 0);
        });
        List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.list(wrapper);

        if (peispatientfeeitems.size() > 0) {
            throw new ServiceException("收退款失败：【" + peispatientfeeitems.get(0).getExamfeeitemName() + "】收费项目没有登记，请先完成登记");
        }

        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        if (null == peispatient) {
            throw new ServiceException("该【" + patientCode + "】体检号不存在，已经被删除！");
        }
        // 已缴费
        peispatient.setModifydate(now);

        //记录体检者状态
        peisStateService.saOrUp(peispatient, 0);

        // 实收实退
        peispatient.setMoneyamountpaid(param.getMoneyamountpaid());
        peispatientMapper.updateById(peispatient);

        PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientCode);
        if (pcm == null) {
            throw new ServiceException(patientCode + "未找到收费信息主表！");
        }

        // version==null兼容老数据
        Long version = param.getVersion();
        if ((version == null && pcm.getVersion() != null) || (version != null && pcm.getVersion() != null
                && version.longValue() != pcm.getVersion().longValue())) {
            throw new ServiceException("信息有变动，请刷新!");
        }

        String newNote = user.getUserName();
        Long v = new Date().getTime();

        PeispatientChargeRecord pcr = new PeispatientChargeRecord();
        pcr.setPatientcode(patientCode);
        pcr.setVersion(v);
        pcr.setMethod("receiveRefund");
        pcr.setMoneyamount(pcm.getMoneyamount());
        pcr.setMoneyamountpaid(param.getMoneyamountpaid());
        pcr.setMoneyamountOld(pcm.getMoneyamount());
        pcr.setMoneyamountpaidOld(pcm.getMoneyamountpaid());
        pcr.setUsername(newNote);
        pcr.setNote("receiveRefund");
        peispatientChargeRecordMapper.insert(pcr);

        pcm.setMoneyamountpaid(param.getMoneyamountpaid());
        pcm.setNote(newNote);
        pcm.setVersion(v);
        peispatientChargeMainMapper.updateById(pcm);

        peispatientMapper.updateById(peispatient);

        //TODO ??? 自动设置为VIP操作，是否需要实现
        //preregistrationService.setVip(peispatient);

        // 保存收费信息
        return saveOrUpdateCharge(param);
    }

    /**
     * 支付：体检卡、会员卡、微信支付、支付宝支付等
     *
     * @param param 支付参数
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PayResultDto pay(PayParam param) {
        log.info("支付参数：{}", param);
        if (Objects.isNull(param.getChargeParam())) {
            throw new ServiceException("缴费信息不能为空！");
        }
        String username = SecurityUtils.getUsername();
        // 判断版本
        PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new LambdaQueryWrapper<PeispatientChargeMain>()
                .eq(PeispatientChargeMain::getPatientcode, param.getPatientcode()));
        if (Objects.isNull(pcm)) {
            throw new ServiceException("未找到收费信息主表！");
        }
        // version==null兼容老数据
        if ((Objects.isNull(param.getVersion()) && Objects.nonNull(pcm.getVersion())) || (Objects.nonNull(param.getVersion())
                && Objects.nonNull(pcm.getVersion()) && param.getVersion().longValue() != pcm.getVersion().longValue())) {
            throw new ServiceException("信息有变动，请刷新!");
        }

        // 支付记录ID
        String id = param.getChargeId();
        if (StringUtils.isEmpty(id)){
            id = String.valueOf(snowflake.nextId());
        }

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

        } else if (param.getKkfs() == Kkfs.TONGLIAN.value() || param.getKkfs() == Kkfs.TONGLIAN2.value()) {
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
                payParam.setType(param.getKkfs());
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
        } else if (param.getKkfs() == Kkfs.JTKYE.value() || param.getKkfs() == Kkfs.JTKJF.value()) {
            //家庭卡扣款操作
            OldFamilyConParam oldFamilyConParam = new OldFamilyConParam();
            oldFamilyConParam.setIdcardno(param.getIdcardno());
            oldFamilyConParam.setCardNo(param.getCardId());
            oldFamilyConParam.setIdPayway(param.getIdPayway());
            oldFamilyConParam.setType("1");
            oldFamilyConParam.setMoney(Math.abs(param.getLimit()));//只能传正数，取绝对值
            oldFamilyConParam.setChargeUsername(SecurityUtils.getUsername());
            oldFamilyConParam.setPatientcode(param.getPatientcode());
            oldFamilyConParam.setNote(param.getMemotext());
            applicationEventPublisher.publishEvent(new FamilyConsumptionEvent(oldFamilyConParam));
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

        //家庭卡支付要修改销售经理
//        if (StringUtils.isNotEmpty(param.getGetter()) && StringUtils.isNotEmpty(param.getGetterId())){
//            Peispatient peispatient = peispatientMapper.getByPatientCode(param.getPatientcode());
//            peispatient.setIdOpendoctor(param.getGetterId());//开单医生ID
//            peispatient.setDoctorapply(param.getGetter());//开单医生
//            peispatientMapper.updateById(peispatient);
//        }


        Long newVersion = new Date().getTime();
        pcm.setVersion(newVersion);
        PeispatientChargeRecord pcr = new PeispatientChargeRecord();
        pcr.setPatientcode(param.getPatientcode());
        pcr.setVersion(param.getVersion());
        pcr.setMethod("pay");
        pcr.setMoneyamount(pcm.getMoneyamount());
        pcr.setMoneyamountpaid(pcm.getMoneyamountpaid());
        pcr.setMoneyamountOld(pcm.getMoneyamount());
        pcr.setMoneyamountpaidOld(pcm.getMoneyamountpaid());
        pcr.setUsername(username);
        pcr.setNote("pay");
        peispatientChargeRecordMapper.insert(pcr);
        // 保存本地缴费表
        Peispatientcharge charge = mapperFacade.map(param.getChargeParam(), Peispatientcharge.class);
        charge.setIsDelete(0);
        charge.setPatientcode(param.getPatientcode());
        charge.setShortCode(CodeUtil.getShortCodeByLong(param.getPatientcode()));
        Dictpayway dictpayway = dictpaywayMapper.selectById(charge.getIdPayway());
        if (Objects.isNull(dictpayway)) {
            throw new ServiceException("不支持该支付方式付款！");
        }
        charge.setPayway(dictpayway.getPaywayName());
        // 退费金额
        if (charge.getMoneyamountpaid() < 0) {
            charge.setFIsreturn(1);
        }


        charge.setId(id);
        charge.setTxTradeNo(param.getConsumeId());
        //通联支付 把流水号也设置到卡号里面
        if (StringUtils.isNotNull(param.getConsumeId())){
            charge.setCardno(param.getConsumeId());
        }else {
            charge.setCardno(param.getCardId());
        }

        peispatientchargeMapper.insert(charge);

        //更新收费主表信息
        peispatientChargeMainMapper.updateById(pcm);
        Peispatient patient = peispatientMapper.getByPatientCode(charge.getPatientcode());
        //记录体检者状态
        peisStateService.saOrUp(patient, 0);
        return new PayResultDto(charge.getId(), "success", newVersion, param.getConsumeId());
//        String url = systemConfig.getDomain().getOutreachLcDomain() + Constants.LC_OUT_DO_PAY_PATH;
//        Map<String, Object> paramMap = mapperFacade.map(param, HashMap.class);
//        log.info("支付连接分中心外联系统参数：{}、{}", url, paramMap);
//        String resultStr = HttpUtil.post(url, paramMap);
//        log.info("支付连接分中心外联系统结果resultStr：{}", resultStr);
//        if (StringUtils.isNotBlank(resultStr)) {
//            R response = JSONUtil.toBean(resultStr, R.class);
//            log.info("支付连接分中心外联系统结果response：{}、{}", response, response.getData());
//            if (R.isSuccess(response)) {
//                //支付成功

//                String id = charge.getId();
//                Peispatient patient = peispatientMapper.getByPatientCode(charge.getPatientcode());
//                //记录体检者状态
//                peisStateService.saOrUp(patient, 0);
//                return new PayResultDto(id, "success", newVersion, response.getData() + "");
//            } else {
//                //支付失败
//                throw new ServiceException(StringUtils.isNotBlank(response.getMsg()) ? response.getMsg() : "支付失败，请稍后再重试！");
//            }
//        } else {
//            throw new ServiceException("获取失败，请稍后再重试！");
//        }

    }

    /**
     * 复查额度消费
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PayResultDto chargeRecheck(ChargeRecheckParam param) {
        log.info("支付参数：{}", param);
        if (Objects.isNull(param.getChargeParam())) {
            throw new ServiceException("缴费信息不能为空！");
        }
        String username = SecurityUtils.getUsername();
        // 判断版本
        PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new LambdaQueryWrapper<PeispatientChargeMain>()
                .eq(PeispatientChargeMain::getPatientcode, param.getPatientcode()));
        if (Objects.isNull(pcm)) {
            throw new ServiceException("未找到收费信息主表！");
        }
        // version==null兼容老数据
        if ((Objects.isNull(param.getVersion()) && Objects.nonNull(pcm.getVersion())) || (Objects.nonNull(param.getVersion())
                && Objects.nonNull(pcm.getVersion()) && param.getVersion().longValue() != pcm.getVersion().longValue())) {
            throw new ServiceException("信息有变动，请刷新!");
        }
        Long newVersion = new Date().getTime();
        pcm.setVersion(newVersion);
        PeispatientChargeRecord pcr = new PeispatientChargeRecord();
        pcr.setPatientcode(param.getPatientcode());
        pcr.setVersion(param.getVersion());
        pcr.setMethod("pay");
        pcr.setMoneyamount(pcm.getMoneyamount());
        pcr.setMoneyamountpaid(pcm.getMoneyamountpaid());
        pcr.setMoneyamountOld(pcm.getMoneyamount());
        pcr.setMoneyamountpaidOld(pcm.getMoneyamountpaid());
        pcr.setUsername(username);
        pcr.setNote("pay");
        peispatientChargeRecordMapper.insert(pcr);
        // 保存本地缴费表
        Peispatientcharge charge = mapperFacade.map(param.getChargeParam(), Peispatientcharge.class);
        charge.setIsDelete(0);
        charge.setPatientcode(param.getPatientcode());
        charge.setShortCode(CodeUtil.getShortCodeByLong(param.getPatientcode()));
        Dictpayway dictpayway = dictpaywayMapper.selectById(charge.getIdPayway());
        if (Objects.isNull(dictpayway)) {
            throw new ServiceException("不支持该支付方式付款！");
        }
        charge.setPayway(dictpayway.getPaywayName());
        // 退费金额
        if (charge.getMoneyamountpaid() < 0) {
            charge.setFIsreturn(1);
        }
        // 支付记录ID
        String id = String.valueOf(snowflake.nextId());

        // TODO wait 保存线上：线上扣费
        //卡扣款操作
        CardConsumeParam cardConsumeParam = new CardConsumeParam(param.getCardId(), param.getLimit(), param.getLimit() > 0 ? 2 : 1, param.getMemotext(), SecurityUtils.getUserNo(), param.getIdPayway(), param.getBranchId(), param.getPatientcode(), param.getPatientname(), String.valueOf(CardConsumeType.TIJIAN.value()), Kkfs.FCED.value(), SecurityUtils.getUsername(), null);
        applicationEventPublisher.publishEvent(new CardConsumeEvent(cardConsumeParam));
        charge.setId(id);
        charge.setTxTradeNo(param.getConsumeId());
        peispatientchargeMapper.insert(charge);

        //更新收费主表信息
        peispatientChargeMainMapper.updateById(pcm);
        Peispatient patient = peispatientMapper.getByPatientCode(charge.getPatientcode());
        //记录体检者状态
        peisStateService.saOrUp(patient, 0);
        return new PayResultDto(charge.getId(), "success", newVersion, param.getConsumeId());

    }

    /**
     * 退款：体检卡、会员卡、微信支付、支付宝支付等
     *
     * @param param 退款参数
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean refund(RefundParam param) {
        if (StringUtils.isNotEmpty(param.getId())) {
            Peispatientcharge charge = peispatientchargeMapper.selectById(param.getId());
            if (Objects.isNull(charge)) {
                throw new ServiceException("支付记录不存在！");
            }
            String patientcode = charge.getPatientcode();
            PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientcode);
            if (Objects.isNull(pcm)) {
                throw new ServiceException(patientcode + "未找到收费信息主表！");
            }
            // version==null兼容老数据
            if ((Objects.isNull(param.getVersion()) && Objects.nonNull(pcm.getVersion())) || (Objects.nonNull(param.getVersion())
                    && Objects.nonNull(pcm.getVersion()) && param.getVersion().longValue() != pcm.getVersion().longValue())) {
                throw new ServiceException("信息有变动，请刷新!");
            }

            // TODO wait 保存线上：线上扣费
            if (param.getKkfs() == Kkfs.TJK.value() || param.getKkfs() == Kkfs.HYKJF.value() || param.getKkfs() == Kkfs.HYKYE.value()) {
                //卡扣款操作
                CardConsumeParam cardConsumeParam = new CardConsumeParam(param.getCardId(), param.getLimit(), param.getLimit() > 0 ? 2 : 1, param.getMemotext(), SecurityUtils.getUserNo(), charge.getIdPayway(), param.getBranchId(), patientcode, param.getPatientname(), param.getConsumeType(), param.getKkfs(), SecurityUtils.getUsername(), null);
                applicationEventPublisher.publishEvent(new CardConsumeEvent(cardConsumeParam));
            } else if (param.getKkfs() == Kkfs.WXPAY.value()) {
                //TODO wait 微信退款
            } else if (param.getKkfs() == Kkfs.ALIPAY.value()) {
                //TODO wait 支付宝退款

            } else if (param.getKkfs() == Kkfs.TONGLIAN.value() || param.getKkfs() == Kkfs.TONGLIAN2.value()) {
                //通联支付退款
                TongLianRefundParam payParam = new TongLianRefundParam();
                payParam.setTrxamt((long) (param.getLimit() * 100));//单位是分
                payParam.setReqsn(charge.getId());
                payParam.setOldtrxid(charge.getTxTradeNo());
                payParam.setType(param.getKkfs());
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
                payParam.setOrigOrderNo(charge.getId());
                applicationEventPublisher.publishEvent(new SuiXingRefundEvent(payParam));
            }

            Long newVersion = new Date().getTime();
            pcm.setVersion(newVersion);
            PeispatientChargeRecord pcr = new PeispatientChargeRecord();
            pcr.setPatientcode(patientcode);
            pcr.setVersion(newVersion);
            pcr.setMethod("refund");
            pcr.setMoneyamount(pcm.getMoneyamount());
            pcr.setMoneyamountpaid(pcm.getMoneyamountpaid());
            pcr.setMoneyamountOld(pcm.getMoneyamount());
            pcr.setMoneyamountpaidOld(pcm.getMoneyamountpaid());
            pcr.setUsername(SecurityUtils.getUsername());
            pcr.setNote("refund");
            peispatientChargeRecordMapper.insert(pcr);

            //更新收费主表信息
            peispatientChargeMainMapper.updateById(pcm);
            Peispatient patient = peispatientMapper.getByPatientCode(charge.getPatientcode());
            //记录体检者状态
            peisStateService.saOrUp(patient, 0);

            //添加一条负的收费记录
            Peispatientcharge refundCharge = new Peispatientcharge();
            refundCharge.setIsDelete(0);
            refundCharge.setPatientcode(charge.getPatientcode());
            refundCharge.setShortCode(CodeUtil.getShortCodeByLong(param.getPatientcode()));
            refundCharge.setIdPayway(charge.getIdPayway());
            refundCharge.setPayway(charge.getPayway());
            refundCharge.setMoneyamount(-param.getLimit());
            refundCharge.setMoneyamountpaid(-param.getLimit());
            refundCharge.setTxTradeNo(charge.getTxTradeNo());
            refundCharge.setIdFeecharger(SecurityUtils.getUserNo());
            refundCharge.setFFeeconfirmed(1);
            refundCharge.setFFeecharged(1);
            refundCharge.setFIsreturn(1);
            refundCharge.setFeechargetime(new Date());
            refundCharge.setNote("refund退款");
            refundCharge.setCardno(charge.getCardno());
            refundCharge.setIsAdd(charge.getIsAdd());
            peispatientchargeMapper.insert(refundCharge);

            //预收需要修改体检者表
            Double paid = 0d;
            String idPay = "";
            String payName = "";
            if (ObjectUtils.isNotEmpty(param.getSfys()) && param.getSfys() == 1){
                List<Peispatientcharge> yushou = peispatientchargeMapper.selectList(new LambdaQueryWrapper<Peispatientcharge>()
                        .eq(Peispatientcharge::getPatientcode, patientcode)
                        .eq(Peispatientcharge::getNote, "预收")
                        .eq(Peispatientcharge::getIsDelete, 0)
                );

                for (Peispatientcharge peispatientcharge : yushou) {
                    paid = Arith.add(paid, peispatientcharge.getMoneyamountpaid());
                    idPay += peispatientcharge.getIdPayway() + ",";
                    payName += peispatientcharge.getPayway() + ",";
                }
                // 更新体检者预付金额
                patient.setIdPayway(idPay.length() > 0 ? idPay.substring(0, idPay.lastIndexOf(",")) : "");
                patient.setPayway(payName.length() > 0 ? payName.substring(0, payName.lastIndexOf(",")) : "");
                patient.setPrepayment(paid);
                peispatientMapper.updateById(patient);

            }
        } else {
            throw new ServiceException("收费记录ID不能为空！");
        }
        return Boolean.TRUE;
    }

    /**
     * 保存收费信息
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean saveOrUpdateCharge(ChargeParam param) {
        String patientCode = param.getPatientcode();
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 体检者
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        if (Objects.isNull(peispatient)) {
            throw new ServiceException("收退款失败:该体检号【" + patientCode + "】不存在，已经被删除");
        }
        peispatient.setModifydate(new Date());

        // 原先实收实退
        PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientCode);
        Double oldPaid = (Objects.isNull(pcm) || Objects.isNull(pcm.getMoneyamountpaid()) ? 0d : pcm.getMoneyamountpaid());
        // 每种收费方式的实收合计
        Map<String, Double> yingMap = new HashMap<String, Double>();
        // 每种收费方式的实退合计
        Map<String, Double> tYingMap = new HashMap<String, Double>();
        Double tMoney = 0d;
        // 是否存在统收自动收费情况
        int tong = 0;
        // 第一次收费还是多次收费 0:第一次收费 1：加项收费
        int oneTime = 0;
        // 是否存在记账
        Boolean isJz = false;
        // 记账金额
        Double jzMoney = 0d;
        Dictpayway pay = dictpaywayMapper.selectOne(new LambdaQueryWrapper<Dictpayway>().eq(Dictpayway::getPaywayName, "记账"));
        if (StringUtils.isNotEmpty(param.getIds())) {
            for (String id : param.getIds().split(",")) {
                Peispatientcharge chargeNew = peispatientchargeMapper.selectOne(new LambdaQueryWrapper<Peispatientcharge>()
                        .eq(Peispatientcharge::getId, id).eq(Peispatientcharge::getIsDelete, 0)
                        .eq(Peispatientcharge::getPatientcode, patientCode));
                // 判断是否存在
                if (Objects.nonNull(chargeNew)) {
                    chargeNew.setIsDelete(1);
                    peispatientchargeMapper.updateById(chargeNew);
                }
            }
        }

        for (Peispatientcharge charge : param.getChargeItems()) {
            if ("removed".equals(charge.get_state())) {
                if (StringUtils.isNotBlank(charge.getId())) {
                    Peispatientcharge chargeNew = peispatientchargeMapper.selectOne(new LambdaQueryWrapper<Peispatientcharge>()
                            .eq(Peispatientcharge::getId, charge.getId()).eq(Peispatientcharge::getIsDelete, 0)
                            .eq(Peispatientcharge::getPatientcode, patientCode));
                    // 判断是否存在
                    if (Objects.nonNull(chargeNew)) {
                        chargeNew.setIsDelete(1);
                        peispatientchargeMapper.updateById(chargeNew);
                    }
                }
            } else if ("modified".equals(charge.get_state())) {
                Integer fFeecharged = charge.getFFeecharged();
                if (Objects.isNull(fFeecharged) || fFeecharged == 0) {
                    Peispatientcharge pc = peispatientchargeMapper.selectOne(new LambdaQueryWrapper<Peispatientcharge>()
                            .eq(Peispatientcharge::getId, charge.getId()).eq(Peispatientcharge::getIsDelete, 0));
                    if (Objects.isNull(pc)) {
                        throw new ServiceException("收退款失败:该体检号【" + patientCode + "】的收费信息已被删除！");
                    }
                    BeanUtils.copyProperties(charge, pc, new String[]{"id", "isDelete", "patientcode", "shortCode"});
                    pc.setFFeecharged(1);
                    pc.setFFeeconfirmed(1);
                    //记账的不修改收费时间和收费员
                    if (!"4".equals(pc.getIdPayway())){
                        pc.setFeechargetime(new Date());
                        pc.setIdFeecharger(user.getUserNo());
                    }
                    peispatientchargeMapper.updateById(pc);
                }
                // 实收合计
                if (charge.getMoneyamountpaid() > 0) {
                    if (null != yingMap.get(charge.getIdPayway())) {
                        yingMap.put(charge.getIdPayway(),
                                yingMap.get(charge.getIdPayway()) + charge.getMoneyamountpaid());
                    } else {
                        yingMap.put(charge.getIdPayway(), charge.getMoneyamountpaid());
                    }
                } else {
                    if (null != tYingMap.get(charge.getIdPayway())) {
                        tYingMap.put(charge.getIdPayway(),
                                tYingMap.get(charge.getIdPayway()) + charge.getMoneyamountpaid());
                    } else {
                        tYingMap.put(charge.getIdPayway(), charge.getMoneyamountpaid());
                    }
                }
                // 判断是否存在记账收费方式
                if (charge.getIdPayway().equals(pay.getId())) {
                    // 是否存在记账人(必填)
                    if (StringUtils.isBlank(peispatient.getJzdwr())) {
                        throw new ServiceException("收退款失败:该体检号【" + patientCode + "】存在记账信息，记账人必须填写");
                    }
                    isJz = true;
                    jzMoney += charge.getMoneyamountpaid();
                }
                oneTime = 1;
            } else if ("added".equals(charge.get_state())) {
                //通联支付和随行支付不能直接退款
                if ("22".equals(charge.getIdPayway()) || "99".equals(charge.getIdPayway())){
                    throw new ServiceException("退款失败:该体检号【" + patientCode + "】存在通联支付或随行支付信息，不能直接退款");
                }
                // 存在统收自动收费项目
                if (Objects.nonNull(charge.getTong())) {
                    tong = 1;
                }
                // 已收费
                charge.setFFeecharged(1);
                charge.setFeechargetime(new Date());
                charge.setIdFeecharger(user.getUserNo());
                charge.setIsDelete(0);
                charge.setPatientcode(patientCode);
                charge.setShortCode(peispatient.getShortCode());
                charge.setFFeeconfirmed(1);

                // 退费金额
                if (charge.getMoneyamountpaid() < 0) {
                    tMoney += charge.getMoneyamountpaid();
                    charge.setFIsreturn(1);
                    oneTime = 2;
                }
                // 保存实体类
                peispatientchargeMapper.insert(charge);
                //TODO wait ???发送康淘
                //kangTaoPostService.postRegister(charge);

                // 判断是否存在记账收费方式
                if (charge.getIdPayway().equals(pay.getId())) {
                    // 是否存在记账人(必填)
                    if (StringUtils.isBlank(peispatient.getJzdwr())) {
                        throw new ServiceException("收退款失败:该体检号【" + patientCode + "】存在记账信息，记账人必须填写");
                    }
                    isJz = true;
                    jzMoney += charge.getMoneyamountpaid();
                }

                if (charge.getMoneyamountpaid() > 0) {
                    // 实收合计
                    if (null != yingMap.get(charge.getIdPayway())) {
                        yingMap.put(charge.getIdPayway(),
                                yingMap.get(charge.getIdPayway()) + charge.getMoneyamountpaid());
                    } else {
                        yingMap.put(charge.getIdPayway(), charge.getMoneyamountpaid());
                    }
                } else {
                    // 应退合计
                    if (null != tYingMap.get(charge.getIdPayway())) {
                        tYingMap.put(charge.getIdPayway(),
                                tYingMap.get(charge.getIdPayway()) + charge.getMoneyamountpaid());
                    } else {
                        tYingMap.put(charge.getIdPayway(), charge.getMoneyamountpaid());
                    }
                }
            }
        }

        // 判断是否存在每种收费方式的退费金额总和不能大于收费金额总和
        for (Map.Entry<String, Double> m : tYingMap.entrySet()) {
            if (null == yingMap.get(m.getKey()) && !(0.0 == m.getValue().doubleValue())) {
                // 此种收费方式不存在
                Dictpayway way = dictpaywayMapper.selectById(m.getKey());
                throw new ServiceException("收退款失败:收费方式【" + way.getPaywayName() + "】所退款项总额不能超过本次登记该种收费方式的原缴款总额");
            } else {
                Double s = yingMap.get(m.getKey()) == null ? 0.0 : Double.valueOf(yingMap.get(m.getKey()));
                s = new BigDecimal(s).setScale(1, RoundingMode.HALF_UP).doubleValue();
                Double t = -Double.valueOf(tYingMap.get(m.getKey()));
                t = new BigDecimal(t).setScale(1, RoundingMode.HALF_UP).doubleValue();
                if (s < t) {
                    Dictpayway way = dictpaywayMapper.selectById(m.getKey());
                    throw new ServiceException("收退款失败:退款方式为【" + way.getPaywayName() + "】的退款总额【"
                            + t + "】已经超过该种收费方式的收款总额【" + s + "】");
                }
            }
        }

        if (isJz) {
            log.info("开始生成体检者结算表：{}", patientCode);
            PeispatientReservationCharge charge2 = peispatientReservationChargeMapper.selectOne(new LambdaQueryWrapper<PeispatientReservationCharge>()
                    .eq(PeispatientReservationCharge::getPatientcode, patientCode));
            // 更新个检记账结算表
            if (null != charge2) {
                charge2.setJzje(jzMoney);
                peispatientReservationChargeMapper.updateById(charge2);
            } else {
                PeispatientReservationCharge charge = new PeispatientReservationCharge();
                charge.setPatientcode(patientCode);
                charge.setMoneyamountpaid(0.0);
                charge.setJzje(jzMoney);
                peispatientReservationChargeMapper.insert(charge);
            }
        }

        // 更新收费项目的收费时间
        List<Peispatientfeeitem> items = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>()
                .eq(Peispatientfeeitem::getIdPatient, patientCode));
        if (items.size() == 0) {
            throw new ServiceException("收退款失败:该体检号【" + patientCode + "】 的收费项目不存在！");
        }
        Boolean isT = false;
        if (tMoney > 0 && peispatient.getMoneyamount() - oldPaid != tMoney) {
            throw new ServiceException("收退款失败:该体检号【" + patientCode + "】的退费金额不足！");
        } else {
            isT = true;
        }

        for (Peispatientfeeitem peispatientfeeitem : items) {
            // 退费
            if (peispatientfeeitem.getChangeItem() == 1 && isT) {
                if ("1".equals(param.getKey()) && (peispatientfeeitem.getFMarkFeereturn() == null
                        || peispatientfeeitem.getFMarkFeereturn() == 0)) {
                    HandleNewProjects handleNewProjects = handleNewProjectsMapper.selectOne(new LambdaQueryWrapper<HandleNewProjects>()
                            .eq(HandleNewProjects::getPatientcode, patientCode).eq(HandleNewProjects::getHandleType, NewProjectHandleType.TX.value())
                            .eq(HandleNewProjects::getProjectsId, peispatientfeeitem.getId()));
                    // 不存在
                    if (null == handleNewProjects) {
                        handleNewProjects = new HandleNewProjects();
                        handleNewProjects.setPatientcode(patientCode);
                        handleNewProjects.setCreateId(user.getUserNo());
                        handleNewProjects.setModifyId(user.getUserNo());
                        handleNewProjects.setProjectsId(peispatientfeeitem.getId());// 项目ID
                        handleNewProjects.setIsDelete(0);
                        handleNewProjects.setStatus(0);
                        handleNewProjects.setHandleType(NewProjectHandleType.TX.value());
                        // 保存实体类
                        handleNewProjectsMapper.insert(handleNewProjects);
                    } else {
                        handleNewProjects.setModifyId(user.getUserNo());
                        handleNewProjects.setProjectsId(peispatientfeeitem.getId());// 项目ID
                        handleNewProjectsMapper.updateById(handleNewProjects);
                    }
                }
                peispatientfeeitem.setFMarkFeereturn(1);
//                peispatientfeeitem.setFeechargetime(new Date());
                //防止修改之前已经退掉的项目的退项时间
                if (peispatientfeeitem.getDtDelayedtill() == null) {
                    peispatientfeeitem.setDtDelayedtill(new Date());
                }
            }

            // 没有收费
            if (null == peispatientfeeitem.getFFeecharged() || peispatientfeeitem.getFFeecharged() != 1) {
                // 存在统收自动收费项目,只更新统收为已收费
                if (1 == tong) {
                    if ("5".equals(peispatientfeeitem.getIdPayway())) {
                        peispatientfeeitem.setFFeecharged(1);
                        peispatientfeeitem.setIdFeecharger(user.getUserNo());
                        peispatientfeeitem.setFeechargetime(new Date());
                        continue;
                    }
                } else {
                    peispatientfeeitem.setFFeecharged(1);
                    peispatientfeeitem.setIdFeecharger(user.getUserNo());
                    peispatientfeeitem.setFeechargetime(new Date());
                }
            }
        }

        // 批量保存
        peispatientfeeitemService.saveOrUpdateBatch(items);
        // 第一次收费
        if (oneTime == 0) {
            peispatient.setFSettlenone(0);
        } else if (oneTime == 1) {
            peispatient.setFSettlenone(2);
        } else {
            peispatient.setFSettlenone(null);
        }
        // 查找未检、未弃检、未退项、的收费项目数量
        peispatientfeeitemService.checkFj(peispatient);
        return Boolean.TRUE;

    }

    /**
     * 前台-登记-退费-反收费
     *
     * @param patientCode 体检号
     * @param version     版本号
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public R reCharge(String patientCode, Long version) {

        SysUser admin = SecurityUtils.getLoginUser().getUser();

        PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientCode);
        if (Objects.isNull(pcm)) {
            throw new ServiceException("【" + patientCode + "】未找到收费信息主表！");
        }
        // version==null兼容老数据
        if ((version == null && pcm.getVersion() != null) || (version != null && pcm.getVersion() != null
                && version.longValue() != pcm.getVersion().longValue())) {
            throw new ServiceException("信息有变动，请刷新!");
        }

        Long newVersion = new Date().getTime();
        pcm.setVersion(newVersion);

        PeispatientChargeRecord pcr = new PeispatientChargeRecord();
        pcr.setPatientcode(patientCode);
        pcr.setVersion(newVersion);
        pcr.setMethod("reCharge");
        pcr.setMoneyamount(pcm.getMoneyamount());
        pcr.setMoneyamountpaid(pcm.getMoneyamount());
        pcr.setMoneyamountOld(pcm.getMoneyamountpaid());
        pcr.setMoneyamountpaidOld(pcm.getMoneyamountpaid());
        pcr.setUsername(admin.getUserName());
        pcr.setNote("reCharge");
        peispatientChargeRecordMapper.insert(pcr);

        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        if (Objects.isNull(peispatient)) {
            throw new ServiceException("该体检号不存在");
        }

        // 统收项目已收费-->未收费
        Boolean isCharge = false;
        LambdaQueryWrapper<Peispatientfeeitem> wrapper = new LambdaQueryWrapper<Peispatientfeeitem>()
                .eq(Peispatientfeeitem::getIdPatient, patientCode)
                .eq(Peispatientfeeitem::getIdPayway, "5")
                .eq(Peispatientfeeitem::getFFeecharged, 1);
        List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.list(wrapper);
        for (Peispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
            if (Objects.nonNull(peispatientfeeitem.getFExaminated()) && peispatientfeeitem.getFExaminated() == 1) {
                throw new ServiceException("【" + peispatientfeeitem.getExamfeeitemName() + "】已经检查，不可以反收费");
            }
            isCharge = true;
            peispatientfeeitem.setFFeecharged(0);
            peispatientfeeitemService.updateById(peispatientfeeitem);
        }
        if (!isCharge) {
            throw new ServiceException("只有存在统收的收费项目才可以反收费");
        }

        // 统收项目删除
        List<Peispatientcharge> peispatientCharges = peispatientchargeMapper.selectList(new LambdaQueryWrapper<Peispatientcharge>()
                .eq(Peispatientcharge::getPatientcode, patientCode)
                .eq(Peispatientcharge::getIdPayway, "5")
                .eq(Peispatientcharge::getIsDelete, 0));
        Double paid = peispatient.getMoneyamountpaid();
        for (Peispatientcharge charge : peispatientCharges) {
            paid = Arith.sub(paid, charge.getMoneyamountpaid());
            peispatientchargeMapper.deleteById(charge);
        }
        // 更新体检者相对应的费用
        peispatient.setMoneyamountpaid(null);// 置为null不在每日体检者构成显示已收费
        //记录体检者状态
        peisStateService.saOrUp(peispatient, 0);
        peispatientMapper.updateById(peispatient);

        pcm.setMoneyamountpaid(null);
        pcm.setNote(admin.getUserName());
        peispatientChargeMainMapper.updateById(pcm);

        return R.ok(newVersion, "");
    }

    /**
     * 费用预收保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean advanceRefund(AdvanceRefundParam param) {
        // 体检者
        Peispatient peispatient = peispatientMapper.getByPatientCode(param.getPatientcode());

        if (Objects.isNull(peispatient)) {
            throw new ServiceException("收退款失败:该体检号【" + param.getPatientcode() + " 】不存在，已经被删除");
        }
        // 解析json
        List<Peispatientcharge> examData = param.getChargeItems();
        Double paid = 0d;
        String idPay = "";
        String payName = "";
        for (int i = 0; i < examData.size(); i++) {
            Peispatientcharge charge = examData.get(i);
            charge.setMoneyamount(charge.getMoneyamountpaid());
            if ("removed".equals(charge.get_state())) {
                if (StringUtils.isNotBlank(charge.getId())) {
                    Peispatientcharge chargeNew = peispatientchargeMapper.selectOne(new LambdaQueryWrapper<Peispatientcharge>()
                            .eq(Peispatientcharge::getId, charge.getId()).eq(Peispatientcharge::getIsDelete, 0).eq(Peispatientcharge::getPatientcode, param.getPatientcode()));
                    // 判断是否存在
                    if (Objects.nonNull(chargeNew)) {
                        chargeNew.setIsDelete(1);
                        // 更新实体类
                        peispatientchargeMapper.updateById(chargeNew);
                    }
                }
            } else if ("modified".equals(charge.get_state())) {
                Peispatientcharge chargeNew = peispatientchargeMapper.selectOne(new LambdaQueryWrapper<Peispatientcharge>()
                        .eq(Peispatientcharge::getId, charge.getId()).eq(Peispatientcharge::getIsDelete, 0).eq(Peispatientcharge::getPatientcode, param.getPatientcode()));
                // 判断是否存在
                if (Objects.nonNull(chargeNew)) {
//                    charge.setFeechargetime(new Date());
                    charge.setNumIndex(charge.get_uid());
                    charge.setId(chargeNew.getId());
                    charge.setFFeecharged(1);
                    peispatientchargeMapper.updateById(charge);
                    paid = Arith.add(paid, charge.getMoneyamountpaid());
                    idPay += charge.getIdPayway() + ",";
                    payName += charge.getPayway() + ",";
                } else throw new ServiceException("预收款失败:第" + (i + 1) + "条预收信息不存在，已经被删除");
            } else if ("added".equals(charge.get_state())) {
                // 已收费
                if ("4".equals(charge.getIdPayway())) {
                    charge.setFFeecharged(0);//记账的不直接是已收状态
                } else {
                    charge.setFFeecharged(1);
                }
                charge.setFeechargetime(new Date());
                charge.setIdFeecharger(SecurityUtils.getUserNo());
                charge.setIsDelete(0);
                charge.setPatientcode(param.getPatientcode());
                charge.setShortCode(CodeUtil.getShortCodeByLong(param.getPatientcode()));
                charge.setFFeeconfirmed(1);
                charge.setNumIndex(charge.get_uid());
                // 保存实体类
                try {
                    peispatientchargeMapper.insert(charge);
                } catch (Exception e) {
                    log.error("预收款失败:第" + (i + 1) + "条预收信息保存失败：{},{}", param, e);
                    throw new ServiceException("预收款失败:第" + (i + 1) + "条预收信息保存失败");
                }
                paid = Arith.add(paid, charge.getMoneyamountpaid());
                idPay += charge.getIdPayway() + ",";
                payName += charge.getPayway() + ",";
            }
        }
        // 更新体检者预付金额
        peispatient.setIdPayway(idPay.length() > 0 ? idPay.substring(0, idPay.lastIndexOf(",")) : null);
        peispatient.setPayway(payName.length() > 0 ? payName.substring(0, payName.lastIndexOf(",")) : null);
        peispatient.setPrepayment(paid);
        peispatientMapper.updateById(peispatient);
        PeisState ps = peisStateService.getByPatientcode(peispatient.getPatientcode());
        if (Objects.isNull(ps)) {
            ps = new PeisState(peispatient.getPatientcode());
            ps.setIdPatientclass2(0);
            peisStateService.save(ps);
        } else {
            ps.setIdPatientclass2(0);
            peisStateService.updateById(ps);
        }

        return Boolean.TRUE;
    }

    /**
     * 预收支付：体检卡、会员卡、微信支付、支付宝支付等
     *
     * @param param 支付参数
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PayResultDto advancePay(PayParam param) {
        // 体检者
        Peispatient peispatient = peispatientMapper.getByPatientCode(param.getPatientcode());
        if (Objects.isNull(peispatient)) {
            throw new ServiceException("预收退款失败:该体检号【" + param.getPatientcode() + " 】不存在，已经被删除");
        }
        Dictpayway dictpayway = dictpaywayMapper.selectById(param.getIdPayway());
        if (Objects.isNull(dictpayway)) {
            throw new ServiceException("不支持该支付方式付款！");
        }

        // 支付记录ID
        String id = param.getChargeId();
        if (StringUtils.isEmpty(id)){
            id = String.valueOf(snowflake.nextId());
        }

        // TODO wait 保存线上：线上扣费
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

        } else if (param.getKkfs() == Kkfs.TONGLIAN.value() || param.getKkfs() == Kkfs.TONGLIAN2.value()) {
            //TODO wait 通联支付
            if (StringUtils.isEmpty(param.getConsumeId())) {
                TongLianScanPayParam payParam = new TongLianScanPayParam();
                payParam.setTrxamt((long) (param.getLimit() * 100));//单位是分
                payParam.setReqsn(id);
                payParam.setBody("体检预支付");
                payParam.setRemark("");
                //            payParam.setRemark("体检号:"+param.getPatientcode()+",支付于"+new Date()+",支付金额:"+payParam.getTrxamt());
                payParam.setAuthcode(param.getCardId());
                payParam.setType(param.getKkfs());
                try {
                    applicationEventPublisher.publishEvent(new TongLianScanPayEvent(payParam));
                    Map<String, String> payResult = payParam.getPayResult();
                    log.info("通联支付成功：{}", payResult);
                    if ("SUCCESS".equals(payResult.get("retcode"))) {
                        //当结果码为2000时，商户系统可设置间隔时间(建议10秒)重新查询支付结果，直到支付成功或超时(建议50秒)
                        if ("2000".equals(payResult.get("trxstatus"))) {
                            PayResultDto dto = new PayResultDto(id, "fail", 2000L, payResult.get("trxid"));
                            return dto;
                        }
                        //0000是交易成功,其他都是失败
                        if (!"0000".equals(payResult.get("trxstatus"))) {
                            throw new ServiceException(payResult.get("errmsg"));
                        }
                        param.setConsumeId(payResult.get("trxid"));//交易订单号
                    } else {
                        throw new ServiceException("请求支付失败!");
                    }
                } catch (Exception e) {
                    log.error("支付失败：{}、{}", param, e);
                    throw new ServiceException("支付失败!");
                }
            }

        } else if ( param.getKkfs() == Kkfs.JTKYE.value() || param.getKkfs() == Kkfs.JTKJF.value()) {
            //家庭卡扣款操作
            OldFamilyConParam oldFamilyConParam = new OldFamilyConParam();
            oldFamilyConParam.setIdcardno(param.getIdcardno());
            oldFamilyConParam.setCardNo(param.getCardId());
            oldFamilyConParam.setIdPayway(param.getIdPayway());
            oldFamilyConParam.setType("1");
            oldFamilyConParam.setMoney(param.getLimit());
            oldFamilyConParam.setChargeUsername(SecurityUtils.getUsername());
            oldFamilyConParam.setPatientcode(param.getPatientcode());
            oldFamilyConParam.setNote(param.getMemotext());
            applicationEventPublisher.publishEvent(new FamilyConsumptionEvent(oldFamilyConParam));
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
                    throw new ServiceException("随行支付失败!");
                }
            }
        }

        // 保存本地缴费表
        Peispatientcharge charge = mapperFacade.map(param.getChargeParam(), Peispatientcharge.class);
        charge.setIsDelete(0);
        charge.setPatientcode(param.getPatientcode());
        charge.setShortCode(CodeUtil.getShortCodeByLong(param.getPatientcode()));
        // 退费金额
        if (charge.getMoneyamountpaid() < 0) {
            charge.setFIsreturn(1);
        }

        charge.setId(id);
        charge.setPayway(dictpayway.getPaywayName());
        charge.setTxTradeNo(param.getConsumeId());
        charge.setIdFeecharger(SecurityUtils.getUserNo());
        charge.setIsDelete(0);
        charge.setPatientcode(param.getPatientcode());
        charge.setShortCode(CodeUtil.getShortCodeByLong(param.getPatientcode()));
        charge.setFFeeconfirmed(1);
        charge.setNumIndex(charge.get_uid());
        // 已收费
        charge.setFFeecharged(1);
        charge.setMoneyamount(charge.getMoneyamountpaid());
        charge.setFeechargetime(new Date());
        charge.setNote(StringUtils.isBlank(charge.getNote()) ? "预收" : charge.getNote());
        log.info("预收支付，新生成支付记录信息：{}", charge);
        peispatientchargeMapper.insert(charge);

        // 更新体检者预付金额

        String idPayway = StringUtils.isBlank(peispatient.getIdPayway()) ? dictpayway.getId() : (peispatient.getIdPayway() + "," + dictpayway.getId());
        peispatient.setIdPayway(idPayway);
        String payway = StringUtils.isBlank(peispatient.getPayway()) ? dictpayway.getPaywayName() : (peispatient.getPayway() + "," + dictpayway.getPaywayName());
        peispatient.setPayway(payway);
        peispatient.setPrepayment(Arith.add(ObjectUtils.isEmpty(peispatient.getPrepayment())?
                0.0 : peispatient.getPrepayment(), charge.getMoneyamountpaid()));
        peispatientMapper.updateById(peispatient);
        PeisState ps = peisStateService.getByPatientcode(peispatient.getPatientcode());
        if (Objects.isNull(ps)) {
            ps = new PeisState(peispatient.getPatientcode());
            ps.setIdPatientclass2(0);
            peisStateService.save(ps);
        } else {
            ps.setIdPatientclass2(0);
            peisStateService.updateById(ps);
        }
        return new PayResultDto(charge.getId(), "success", null, param.getConsumeId());
//        String url = systemConfig.getDomain().getOutreachLcDomain() + Constants.LC_OUT_DO_PAY_PATH;
//        Map<String, Object> paramMap = mapperFacade.map(param, HashMap.class);
//        log.info("支付连接分中心外联系统参数：{}、{}", url, paramMap);
//        String resultStr = HttpUtil.post(url, paramMap);
//        log.info("支付连接分中心外联系统结果resultStr：{}", resultStr);
//        if (StringUtils.isNotBlank(resultStr)) {
//            R response = JSONUtil.toBean(resultStr, R.class);
//            log.info("支付连接分中心外联系统结果response：{}、{}", response, response.getData());
//            if (R.isSuccess(response)) {
//                //支付成功

//                String id = charge.getId();
//                Peispatient patient = peispatientMapper.getByPatientCode(charge.getPatientcode());
//                //记录体检者状态
//                peisStateService.saOrUp(patient, 0);
//                return new PayResultDto(id, "success", newVersion, response.getData() + "");
//            } else {
//                //支付失败
//                throw new ServiceException(StringUtils.isNotBlank(response.getMsg()) ? response.getMsg() : "支付失败，请稍后再重试！");
//            }
//        } else {
//            throw new ServiceException("获取失败，请稍后再重试！");
//        }

    }


    /**
     * 手动通联退款(测试使用)
     * @param payParam
     * @return
     */
    @Override
    public Boolean tongLianRefund(TongLianRefundParam payParam) {
        payParam.setTrxamt(payParam.getTrxamt() * 100);//单位是分
        payParam.setReqsn(String.valueOf(System.currentTimeMillis()));
        try {
            applicationEventPublisher.publishEvent(new TongLianRefundEvent(payParam));
            Map<String, String> payResult = payParam.getPayResult();
            log.info("通联支付退款成功：{}", payResult);
        } catch (Exception e) {
            log.error("通联支付退款失败：{}、{}", payParam, e);
            throw new ServiceException("通联支付退款失败!");
        }
        return Boolean.TRUE;
    }

    /**
     * 更改收费方式
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean changePaymentMethod(ChangePaymentMethodParam param) {
        Peispatientcharge peispatientcharge = peispatientchargeMapper.getInfoById(param.getId());
        if (ObjectUtils.isEmpty(peispatientcharge)){
            throw new ServiceException("请确认id是否正确!");
        }
        Date feechargetime = peispatientcharge.getFeechargetime();
        LocalDate localDateToCheck = feechargetime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        if (!localDateToCheck.equals(today)){
            throw new ServiceException("只能修改收费时间是当天的数据!");
        }
        Dictpayway dictpayway = dictpaywayMapper.getInfoById(param.getIdPayway());
        if (ObjectUtils.isEmpty(dictpayway)){
            throw new ServiceException("请确认支付方式id是否正确!");
        }
        //随行支付、通联支付（扫码支付）、体检卡、会员积分、家庭卡积分、二维码、记账
        List<String> ids = Arrays.asList("99","22","7","6","18","24","4");
        if (ids.contains(param.getIdPayway())){
            throw new ServiceException("该支付方式不支持修改，请去退款!");
        }
        if (ids.contains(peispatientcharge.getIdPayway())){
            throw new ServiceException("之前收费的支付方式不支持修改，请去退款!");
        }


        //修改数据
        SysUser sysUser = sysUserMapper.getUserByNo(peispatientcharge.getIdFeecharger());
        String oldNote = StringUtils.isEmpty(peispatientcharge.getNote()) ? "" : peispatientcharge.getNote();
        peispatientcharge.setNote(oldNote + " 已更改收费方式，上一次的收费信息是:"+sysUser.getUserName()+",收费方式:"+peispatientcharge.getPayway());
        peispatientcharge.setIdPayway(dictpayway.getId());
        peispatientcharge.setPayway(dictpayway.getPaywayName());
        peispatientcharge.setIdFeecharger(SecurityUtils.getUserNo());
        peispatientcharge.setFeechargetime(new Date());
        peispatientchargeMapper.updateById(peispatientcharge);
        return Boolean.TRUE;
    }

    /**
     * 修改收费方式(管理员)
     * @param param
     * @return
     */
    @Override
    public Boolean changePaymentMethodByAdmin(ChangePaymentMethodParam param) {
        //校验是不是管理员
        Boolean admin = SecurityUtils.hasRole(RoleAuthName.ADMIN) || SecurityUtils.hasRole(RoleAuthName.FZX_ADMIN);
        if (!admin){
            throw new ServiceException("只有管理员才能修改收费方式!");
        }
        Dictpayway dictpayway = dictpaywayMapper.getInfoById(param.getIdPayway());
        if (ObjectUtils.isEmpty(dictpayway)){
            throw new ServiceException("请确认支付方式id是否正确!");
        }
        Peispatientcharge peispatientcharge = peispatientchargeMapper.getInfoById(param.getId());
        //修改数据
        SysUser sysUser = sysUserMapper.getUserByNo(peispatientcharge.getIdFeecharger());
        String oldNote = StringUtils.isEmpty(peispatientcharge.getNote()) ? "" : peispatientcharge.getNote();
        peispatientcharge.setNote(oldNote + " 已更改收费方式，上一次的收费信息是:"+sysUser.getUserName()+",收费方式:"+peispatientcharge.getPayway());
        peispatientcharge.setIdPayway(dictpayway.getId());
        peispatientcharge.setPayway(dictpayway.getPaywayName());
        peispatientcharge.setIdFeecharger(SecurityUtils.getUserNo());
        peispatientcharge.setFeechargetime(new Date());
        peispatientchargeMapper.updateById(peispatientcharge);
        return Boolean.TRUE;
    }
}

