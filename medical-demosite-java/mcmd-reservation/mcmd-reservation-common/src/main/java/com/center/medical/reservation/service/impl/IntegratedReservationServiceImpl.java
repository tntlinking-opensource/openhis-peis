package com.center.medical.reservation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.CusCardType;
import com.center.medical.bean.enums.ReservationStatus;
import com.center.medical.bean.model.*;
import com.center.medical.common.config.OldSystemConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.reservation.bean.dto.*;
import com.center.medical.reservation.bean.param.*;
import com.center.medical.dao.PeispatientAndFzxMapper;
import com.center.medical.dao.PeispatientChargeMainMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.finance.service.PhysicalCheckoutService;
import com.center.medical.reservation.bean.dto.*;
import com.center.medical.reservation.bean.model.MealExternalInfo;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.model.ReservationSetting;
import com.center.medical.reservation.bean.param.*;
import com.center.medical.reservation.constant.IntegratedReservationConstant;
import com.center.medical.reservation.dao.IntegratedReservationMapper;
import com.center.medical.reservation.dao.MealExternalInfoMapper;
import com.center.medical.reservation.dao.ReservationOpenApiMapper;
import com.center.medical.reservation.service.IntegratedReservationService;
import com.center.medical.reservation.service.ReservationOpenApiService;
import com.center.medical.reservation.service.ReservationSettingService;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.Mealandfzx;
import com.center.medical.sellcrm.bean.model.Orderandcombo;
import com.center.medical.sellcrm.dao.CreatemealMapper;
import com.center.medical.sellcrm.dao.MealandfzxMapper;
import com.center.medical.sellcrm.dao.OrderandcomboMapper;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.bean.model.BusinessSource;
import com.center.medical.system.dao.BranchMapper;
import com.center.medical.system.dao.BusinessSourceMapper;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * 新老系统集成预约
 *
 * @author xhp
 * @since 2024-01-03 8:04
 */
@Slf4j
@Service("integratedReservationService")
@RequiredArgsConstructor
public class IntegratedReservationServiceImpl extends ServiceImpl<IntegratedReservationMapper, Reservation> implements IntegratedReservationService {
    private final BranchMapper branchMapper;
    private final ReservationOpenApiService reservationOpenApiService;
    private final ISysConfigService iSysConfigService;
    private final ReservationOpenApiMapper reservationOpenApiMapper;
    private final CreatemealMapper createmealMapper;
    private final MealandfzxMapper mealandfzxMapper;
    private final ReservationSettingService reservationSettingService;
    private final ISysBranchService iSysBranchService;
    private final PeispatientMapper peispatientMapper;
    private final BusinessSourceMapper businessSourceMapper;
    private final PeispatientAndFzxMapper peispatientAndFzxMapper;
    private final Snowflake snowflake;
    private final PeispatientChargeMainMapper peispatientChargeMainMapper;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final CreateorderService createorderService;
    private final PhysicalCheckoutService physicalCheckoutService;
    private final MealExternalInfoMapper mealExternalInfoMapper;
    private final OrderandcomboMapper orderandcomboMapper;

    /**
     * 根据第三方套餐id获取可预约机构门店列表
     *
     * @param data
     * @return
     */
    @Override
    public List<IntegratedReservationBranchListDto> getBranchList(String data) {
        IntegratedReservationBranchListParam param = JSONUtil.toBean(data, IntegratedReservationBranchListParam.class);
        String bizComboId = param.getBizComboId();
        if (StrUtil.isBlank(bizComboId)) throw new ServiceException("第三方套餐ID不能为空");
        String systemId = param.getSystemId();
        if (StrUtil.isBlank(systemId)) throw new ServiceException("第三方系统ID不能为空");
        Createmeal createmeal = getMealByBizComboId(bizComboId, systemId);
        List<IntegratedReservationBranchListDto> result;
        //新系统
        if (createmeal != null) {
            result = new ArrayList<>();
            List<Mealandfzx> mealandfzxes = mealandfzxMapper.selectList(
                    new LambdaQueryWrapper<Mealandfzx>()
                            .eq(Mealandfzx::getTcid, createmeal.getId())
            );
            for (Mealandfzx mealandfzx : mealandfzxes) {
                IntegratedReservationBranchListDto dto = new IntegratedReservationBranchListDto();
                Branch branch = branchMapper.getInfoByBranchId(mealandfzx.getFzxid());
                dto.setHospitalSubId(branch.getHospitalSubId());
                dto.setHospitalSubName(branch.getFzx());
                result.add(dto);
            }
            //老系统
        } else {
            if (!isIntegratedReservationEnabled()) throw new ServiceException("第三方套餐ID不存在");
            JSONObject jo = callOldSystem(IntegratedReservationConstant.OLD_SYS_GET_BRANCH_LIST_PATH, data);
            return jo.getJSONArray("data").toList(IntegratedReservationBranchListDto.class);
        }
        return result;
    }

    /**
     * 获取可预约时间段列表
     *
     * @param data
     * @return
     */
    @Override
    public List<IntegratedReservationAvailableDto> getAvailableNums(String data) {
        IntegratedReservationAvailableParam param = JSONUtil.toBean(data, IntegratedReservationAvailableParam.class);
        String hospitalSubId = param.getHospitalSubId();
        if (StrUtil.isBlank(hospitalSubId)) throw new ServiceException("机构门店ID不能为空");
        String startDateStr = param.getStartDate();
        if (StrUtil.isEmpty(startDateStr)) throw new ServiceException("开始日期不能为空");
        String endDateStr = param.getEndDate();
        if (StrUtil.isEmpty(endDateStr)) throw new ServiceException("结束日期不能为空");
        Branch branch = branchMapper.getInfoByHospitalSubId(hospitalSubId);
        //新系统
        if (isNewSystem(branch)) {
            List<IntegratedReservationAvailableDto> result = new ArrayList<>();
            LocalDate startDate = LocalDateTimeUtil.parseDate(startDateStr);
            LocalDate endDate = LocalDateTimeUtil.parseDate(endDateStr);
            LocalDate now = LocalDate.now();
            if (!startDate.isAfter(now)) startDate = now;
            while (!startDate.isAfter(endDate)) {
                Date reservationDate = Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                AppointmentAvailableParam appointmentAvailableParam = new AppointmentAvailableParam();
                //判断是否是同一天，是则查询当前时间到今天结束，否则查询一整天
                if (DateUtil.isSameDay(reservationDate, new Date())) {
                    appointmentAvailableParam.setReservationTime(DateUtil.formatTime(DateUtil.date()));
                } else {
                    appointmentAvailableParam.setReservationTime("00:00:00");
                }
                appointmentAvailableParam.setReservationDate(reservationDate);
                appointmentAvailableParam.setBranchId(branch.getBranchId());
                //只支持普通会员预约
                appointmentAvailableParam.setLevelId(1);
                List<AppointmentAvailableList> timeList = reservationOpenApiService.getAvailableNums(appointmentAvailableParam);
                IntegratedReservationAvailableDto dto = new IntegratedReservationAvailableDto();
                dto.setTimeList(timeList);
                dto.setDateStr(startDate.toString());
                result.add(dto);
                startDate = startDate.plusDays(1);
            }
            return result;
        //老系统
        } else {
            if (!isIntegratedReservationEnabled()) {
                if(branch==null)throw new ServiceException("机构门店ID不存在");
                throw new ServiceException("机构门店设置预约模式为老系统预约，但老系统未启用");
            }
            JSONObject jo = callOldSystem(IntegratedReservationConstant.OLD_SYS_GET_AVAILABLE_NUM_PATH, data);
            return jo.getJSONArray("data").toList(IntegratedReservationAvailableDto.class);
        }
    }

    /**
     * 预约申请
     *
     * @param data
     * @return 预约号
     */
    @Override
    @Transactional
    public String apply(String data) {
        //参数校验
        IntegratedReservationApplyParam param = JSONUtil.toBean(data, IntegratedReservationApplyParam.class);
        String hospitalSubId = param.getHospitalSubId();
        if (StrUtil.isBlank(hospitalSubId)) throw new ServiceException("机构门店ID不能为空");
        String systemId = param.getSystemId();
        if (StrUtil.isBlank(systemId)) throw new ServiceException("第三方系统ID不能为空");
        BusinessSource businessSource = businessSourceMapper.selectOne(
                new LambdaQueryWrapper<BusinessSource>()
                        .eq(BusinessSource::getSourceId, systemId)
                        .eq(BusinessSource::getStatus, 1)
        );
        if (businessSource == null) throw new ServiceException("第三方系统ID不存在");
        String bizOrderNum = param.getBizOrderNum();
        if (StrUtil.isBlank(bizOrderNum)) throw new ServiceException("第三方订单ID不能为空");
        String idcard = param.getIdcard();
        if (StrUtil.isBlank(idcard)) throw new ServiceException("身份证号不能为空");
        idcard = idcard.toUpperCase();
        param.setIdcard(idcard);
        if (!IdcardUtil.isValidCard(idcard)) throw new ServiceException("身份证号不合法");
        Integer fUsecodehiden = param.getFUsecodehiden();
        if (Objects.isNull(fUsecodehiden)) throw new ServiceException("订单类型不能为空");
        if (fUsecodehiden.intValue() != 0 && fUsecodehiden.intValue() != 1)
            throw new ServiceException("订单类型不正确");
        String bizComboId = param.getBizComboId();
        if (StrUtil.isBlank(bizComboId)) throw new ServiceException("第三方套餐ID不能为空");
        String realName = param.getRealName();
        if (StrUtil.isBlank(realName)) throw new ServiceException("姓名不能为空");
        if (param.getReservationDate() == null) throw new ServiceException("预约日期不能为空");
        Branch branch = branchMapper.getInfoByHospitalSubId(hospitalSubId);
        String phone=param.getMobile();
        if(StrUtil.isEmpty(phone))throw new ServiceException("手机号不能为空");

        //新系统
        if (isNewSystem(branch)) {

            //判断是否新系统预约成功
            Reservation reservation = getReservedReservation(systemId, bizOrderNum);
            if (reservation != null) {
                return reservation.getReservationNo();
            }

            //参数校验
            String timeId = param.getTimeId();
            if (StrUtil.isBlank(timeId)) throw new ServiceException("预约时间段ID不能为空");
            Createmeal createmeal = getMealByBizComboId(bizComboId, systemId);
            if (createmeal == null) throw new ServiceException("第三方套餐ID不存在");
            String comboId = createmeal.getId();
            String comboName = createmeal.getTjtcmc();
            int sex = IdcardUtil.getGenderByIdCard(idcard) == 1 ? 0 : 1;
            Integer syxb = createmeal.getSyxb();
            if (syxb == null || (syxb.intValue() != 2 && syxb.intValue() != sex))
                throw new ServiceException("套餐性别不匹配");
            Integer idMarriage = param.getIdMarriage();
            if (idMarriage != null && sex == 1) {
                String sfyh;
                if (idMarriage.intValue() == 0) {
                    sfyh = "1";
                } else if (idMarriage.intValue() == 1) {
                    sfyh = "0";
                } else {
                    throw new ServiceException("婚姻状态不正确");
                }
                String sfyhtc = createmeal.getSfyhtc();
                if (StrUtil.isBlank(sfyhtc) || (!"2".equals(sfyhtc) && !sfyh.equals(sfyhtc)))
                    throw new ServiceException("套餐婚姻状态不匹配");
            }
            String branchId = branch.getBranchId();
            Mealandfzx mealandfzx = mealandfzxMapper.selectOne(
                    new LambdaQueryWrapper<Mealandfzx>()
                            .eq(Mealandfzx::getTcid, comboId)
                            .eq(Mealandfzx::getFzxid, branchId)
            );
            if (mealandfzx == null) throw new ServiceException("套餐与机构门店ID不匹配");
            String numorgresv = null;
            String idOrg = null;
            String orgName = null;
            String idOpendoctor = null;
            String doctorapply = null;
            Integer idExamclass=null;
            String idPayway="1";//收费项目付款方式 1现金 5统收
            if (fUsecodehiden.intValue() == 1) {
                List<Createorder> createorders = baseMapper.getOrderListByMealId(comboId);
                if (createorders.size() > 1) throw new ServiceException("体检系统存在多个匹配的订单");
                if (createorders.size() == 0) throw new ServiceException("没有匹配的订单");
                Createorder createorder = createorders.get(0);
                idOrg = createorder.getKhdwmcid();
                numorgresv = createorder.getDdh();
                orgName = createorder.getKhdwmc();
                idOpendoctor = createorder.getXsjlid();
                doctorapply = createorder.getXsjl();
                Orderandcombo orderandcombo=orderandcomboMapper.selectOne(
                        new LambdaQueryWrapper<Orderandcombo>()
                            .eq(Orderandcombo::getDdid,createorder.getId())
                            .eq(Orderandcombo::getTcid,comboId)
                );
                idExamclass=orderandcombo.getIdExamclass();
                idPayway=createmeal.getFkfs()!=null&&"0".equals(createmeal.getFkfs())?"5":"1";
            }

            //生成体检号
            String patientcode;
            do {
                patientcode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(branchId), "");
                //判断体检号是否存在
            } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getPatientcode, patientcode)) > 0);

            //保存预约信息
            AppointmentDto appointmentDto = BeanUtil.toBean(param, AppointmentDto.class);
            appointmentDto.setBranchId(branchId);
            ReservationSetting setting = reservationSettingService.getInfoById(timeId);
            String startTime=StrUtil.isEmpty(setting.getStartTime())?"00:00:00":setting.getStartTime();
            Date reservationDate = DateUtil.parse(DateUtil.format(setting.getReservationDate(),"yyyy-MM-dd")+" "+startTime);
            appointmentDto.setReservationDate(reservationDate);
            appointmentDto.setLevelId(setting.getLevelId());
            appointmentDto.setLevelName(setting.getLevelName());
            appointmentDto.setSex(sex);
            appointmentDto.setBranchName(branch.getFzx());
            appointmentDto.setNumorgresv(numorgresv);
            appointmentDto.setComboId(comboId);
            appointmentDto.setComboName(comboName);
            appointmentDto.setPatientcode(patientcode);
            appointmentDto.setIdOrg(idOrg);
            //这里存第三方订单id
            appointmentDto.setNumorgresv(bizOrderNum);
            String reservationNo = reservationOpenApiService.apply(appointmentDto);

            //保存体检者信息
            Peispatient patient = new Peispatient();
            patient.setPatientnamereceipt(bizOrderNum);
            patient.setPatientname(realName);
            patient.setCountreportoccupationxml(CusCardType.ID_CARD.value());
            patient.setIdcardno(idcard);
            patient.setIdResarea(IdcardUtil.getProvinceCodeByIdCard(idcard));
            patient.setResarea(IdcardUtil.getProvinceByIdCard(idcard));
            patient.setIdSex(sex);
            patient.setAge(IdcardUtil.getAgeByIdCard(idcard));
            patient.setBirthdate(IdcardUtil.getBirthDate(idcard));
            patient.setIdTjtc(comboId);
            patient.setExamsuiteName(comboName);
            patient.setIdOrg(idOrg);
            patient.setOrgName(orgName);
            patient.setIdOpendoctor(idOpendoctor);
            patient.setDoctorapply(doctorapply);
            patient.setNumorgresv(numorgresv);
            patient.setDateguidancereturned(reservationDate);
            patient.setCountreportoccupation(businessSource.getId().intValue());
            patient.setFIsforreserve(1);
            patient.setPhone(phone);
            patient.setOrgDepart(param.getOrgDepart());
            patient.setIdPatientclass("1");
            patient.setFUsecodehiden(fUsecodehiden);
            patient.setInputCode(ToolUtil.getHanziPinyinHeadChar(realName));
            patient.setPatientcode(patientcode);
            patient.setPatientbizno(patientcode);
            patient.setShortCode(CodeUtil.getShortCodeByLong(patientcode));
            patient.setFRegistered(0);
            patient.setTimingstartedat(new Date());
            patient.setFExamstarted(0);
            patient.setFReadytofinal(0);
            patient.setFFinallocked(0);
            patient.setFFinalexamed(0.0);
            patient.setFPaused(0);
            patient.setIdCis(createorderService.bindArchive(patient));
            patient.setId(String.valueOf(snowflake.nextId()));
            patient.setIdExamtype("0");
            patient.setIdExamclass(idExamclass);
            patient.setHospitalcode(branchId);
            peispatientMapper.insert(patient);

            //保存体检者分中心
            PeispatientAndFzx peispatientAndFzx = new PeispatientAndFzx();
            peispatientAndFzx.setPatientId(patient.getId());
            peispatientAndFzx.setFzxId(branchId);
            peispatientAndFzx.setXzzt(0);
            peispatientAndFzx.setId(String.valueOf(snowflake.nextId()));
            peispatientAndFzxMapper.insert(peispatientAndFzx);

            //保存体检者收费项目
            List<HashMap<String, String>> items = physicalCheckoutService.getExamfeeitem(patient.getIdTjtc());
            List<Peispatientfeeitem> peispatientfeeitems = new ArrayList<Peispatientfeeitem>();
            int size = items.size();
            Boolean isMakeGb = false;
            Double moneyamount = 0.0;
            for (int j = 0; j < size; j++) {
                Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
                peispatientfeeitem.setIdPatient(patientcode);
                peispatientfeeitem.setIdExamfeeitem(items.get(j).get("idExamfeeitem"));
                peispatientfeeitem.setExamfeeitemName(items.get(j).get("examfeeitemName"));
                peispatientfeeitem.setPrice(Double.valueOf(items.get(j).get("price")));
                peispatientfeeitem.setIdKs(items.get(j).get("idKs"));

                Object[] oa = peispatientfeeitemService.getFactPrice(items.get(j), size, j, isMakeGb);
                if (!isMakeGb) {
                    isMakeGb = (Boolean) oa[1];
                }
                Double factprice = Double.parseDouble(oa[0].toString());
                moneyamount = NumberUtil.add(moneyamount, factprice);
                peispatientfeeitem.setFactprice(factprice);
                peispatientfeeitem.setCount(1);
                peispatientfeeitem.setIdPayway(idPayway);
                peispatientfeeitem.setFRegistered(0);
                peispatientfeeitem.setChangeItem(0);
                peispatientfeeitem.setFMarkFeereturn(0);
                peispatientfeeitem.setFFeecharged(0);
                peispatientfeeitem.setFLabsendtolis(0);
                peispatientfeeitem.setFExaminated(0);
                peispatientfeeitem.setFGiveup(0);
                peispatientfeeitem.setIsbx(items.get(j).get("isbx") == null ? 0 : Integer.valueOf(items.get(j).get("isbx")));
                peispatientfeeitem.setBxcount(items.get(j).get("bxcount") == null ? 0 : Integer.valueOf(items.get(j).get("bxcount")));
                peispatientfeeitem.setFDelayed(0);
                peispatientfeeitem.setIsMintc(1);
                peispatientfeeitem.setQty(items.get(j).get("qty") == null ? null : Integer.parseInt(items.get(j).get("qty")));
                peispatientfeeitems.add(peispatientfeeitem);
            }
            peispatientfeeitemService.saveOrUpdateBatch(peispatientfeeitems);

            //保存体检者应收金额
            PeispatientChargeMain peispatientChargeMain = new PeispatientChargeMain();
            peispatientChargeMain.setId(String.valueOf(snowflake.nextId()));
            peispatientChargeMain.setNote("新老系统集成预约生成");
            peispatientChargeMain.setMoneyamount(moneyamount);
            peispatientChargeMain.setMoneyamountpaid(0.0);
            peispatientChargeMain.setPatientcode(patientcode);
            peispatientChargeMain.setVersion(new Date().getTime());
            peispatientChargeMainMapper.insert(peispatientChargeMain);
            patient.setMoneyamount(moneyamount);
            patient.setMoneyamountpaid(0.0);
            peispatientMapper.updateById(patient);

            return reservationNo;
            //老系统
        } else {
            if (!isIntegratedReservationEnabled()) throw new ServiceException("老系统预约尚未开放");
            JSONObject jo = callOldSystem(IntegratedReservationConstant.OLD_SYS_APPLY_PATH, data);
            return jo.getStr("data");
        }
    }

    /**
     * 取消预约
     *
     * @param data
     */
    @Override
    @Transactional
    public void cancel(String data) {
        IntegratedReservationCancelParam param = JSONUtil.toBean(data, IntegratedReservationCancelParam.class);
        String hospitalSubId = param.getHospitalSubId();
        if (StrUtil.isBlank(hospitalSubId)) throw new ServiceException("机构门店ID不能为空");
        Branch branch = branchMapper.getInfoByHospitalSubId(hospitalSubId);
        String systemId = param.getSystemId();
        if (StrUtil.isBlank(systemId)) throw new ServiceException("第三方系统ID不能为空");
        String bizOrderNum = param.getBizOrderNum();
        if (StrUtil.isBlank(bizOrderNum)) throw new ServiceException("第三方订单ID不能为空");
        //新系统
        if (isNewSystem(branch)) {
            String reservationNo = param.getReservationNo();
            if (StrUtil.isBlank(reservationNo)) throw new ServiceException("预约号不能为空");
            Integer fUsecodehiden = param.getFUsecodehiden();
            if (Objects.isNull(fUsecodehiden)) throw new ServiceException("订单类型不能为空");
            LambdaQueryWrapper<Reservation> wrapper = new LambdaQueryWrapper<Reservation>()
                    .eq(Reservation::getReservationNo, reservationNo)
                    .eq(Reservation::getFUsecodehiden, fUsecodehiden)
                    .eq(Reservation::getStatus, ReservationStatus.SUCCESS.value())
                    .eq(Reservation::getSystemId, systemId)
                    .eq(Reservation::getBranchId, branch.getBranchId())
                    .eq(Reservation::getBizOrderNum, bizOrderNum);
            Reservation reservation = reservationOpenApiMapper.selectOne(wrapper);
            if (Objects.isNull(reservation)) {
                //没有符合条件的预约记录
                throw new ServiceException("体检系统中没有符合条件的预约记录");
            }
            ReservationCancelParam rcp = BeanUtil.toBean(param, ReservationCancelParam.class);
            if (fUsecodehiden.intValue() == 1) {
                rcp.setPcodeOrOrderId(reservation.getPatientcode());
            } else {
                rcp.setPcodeOrOrderId(bizOrderNum);
            }
            reservationOpenApiService.cancel(rcp);

            //禁检体检者（存在统收收费，不禁检可以免费体检）
            Peispatient peispatient=peispatientMapper.getByPatientCode(reservation.getPatientcode());
            log.info("取消预约，禁检体检号:{}",peispatient.getPatientcode());
            peispatient.setFPaused(1);
            peispatientMapper.updateById(peispatient);
        //老系统
        } else {
            if (!isIntegratedReservationEnabled()) throw new ServiceException("机构门店设置预约模式为老系统预约，但老系统未启用");
            callOldSystem(IntegratedReservationConstant.OLD_STS_CANCEL_PATH, data);
        }
    }

    /**
     * 根据第三方订单ID获取预约状态
     *
     * @param data
     * @return
     */
    @Override
    public IntegratedReservationStatusDto getStatus(String data) {
        IntegratedReservationStatusParam param = JSONUtil.toBean(data, IntegratedReservationStatusParam.class);
        String bizOrderNum = param.getBizOrderNum();
        if (StrUtil.isBlank(bizOrderNum)) throw new ServiceException("第三方订单ID不能为空");
        String systemId = param.getSystemId();
        if (StrUtil.isBlank(systemId)) throw new ServiceException("第三方系统ID不能为空");

        //新系统已预约  成功状态只会有一条记录
        Reservation reservation = getReservedReservation(systemId, bizOrderNum);
        IntegratedReservationStatusDto dto = new IntegratedReservationStatusDto();
        if (reservation != null) {
            dto.setRemark(reservation.getRemark());
            dto.setStatus(reservation.getStatus());
            dto.setHospitalSubName(branchMapper.getInfoByBranchId(reservation.getBranchId()).getFzx());
            dto.setFinishedTime(reservation.getFinishedTime());
            return dto;
        }

        //新系统预约失败  失败记录可能多条
        LambdaQueryWrapper<Reservation> wrapper = new LambdaQueryWrapper<Reservation>()
                .eq(Reservation::getStatus, ReservationStatus.fail.value())
                .eq(Reservation::getSystemId, systemId)
                .eq(Reservation::getBizOrderNum, bizOrderNum)
                .orderByDesc(Reservation::getCreatedate);
        List<Reservation> reservations = reservationOpenApiMapper.selectList(wrapper);
        if (reservations.size() > 0) {
            reservation = reservations.get(0);
            dto.setRemark(reservation.getFailReason());
            dto.setStatus(reservation.getStatus());
            dto.setHospitalSubName(branchMapper.getInfoByBranchId(reservation.getBranchId()).getFzx());
            dto.setFinishedTime(reservation.getFinishedTime());
            return dto;
        }

        //新系统无预约记录，查询老系统
        if (!isIntegratedReservationEnabled()) {
            dto.setRemark("未预约");
            dto.setStatus(0);
            return dto;
        }
        JSONObject jo = callOldSystem(IntegratedReservationConstant.OLD_SYS_STATUS_PATH, data);
        return jo.getBean("data", IntegratedReservationStatusDto.class);

    }

    /**
     * 调用老系统接口
     *
     * @param methodUrl
     * @param data
     * @return
     */
    JSONObject callOldSystem(String methodUrl, String data) {
        String oldSystemPfDomain = iSysConfigService.getDomain().getOldSystemPfDomain();
        String url = oldSystemPfDomain + methodUrl;
        String resBody = HttpRequest.post(url)
                .timeout(5 * 1000)
                .contentType(ContentType.JSON.getValue())
                .body(data)
                .execute()
                .body();
        JSONObject jo = JSONUtil.parseObj(resBody);
        if (jo.getInt("code") == null || R.SUCCESS != jo.getInt("code").intValue())
            throw new ServiceException(jo.getStr("msg"));
        return jo;
    }

    /**
     * isShow=1的分中心在新系统预约；否则调用老系统预约接口，在老系统预约。
     *
     * @param branch
     * @return
     */
    boolean isNewSystem(Branch branch) {
        return branch != null
                && branch.getIsShow() != null
                && branch.getIsShow().intValue() == 1;
    }

    /**
     * 是否开启老系统预约
     *
     * @return
     */
    boolean isIntegratedReservationEnabled() {
        OldSystemConfig config = iSysConfigService.getSysConfigObject(Constants.OLD_SYSTEM_CONFIG, OldSystemConfig.class);
        return config != null && config.getIsIntegratedReservationEnabled() != null && config.getIsIntegratedReservationEnabled().intValue() == 1;
    }

    /**
     * 查询已预约的信息，如果未预约返回Null
     *
     * @param systemId
     * @param bizOrderNum
     * @return
     */
    @Override
    @Transactional
    public Reservation getReservedReservation(String systemId, String bizOrderNum) {
        //2 3 4状态的只会有唯一一条,-1状态可能有多条
        LambdaQueryWrapper<Reservation> wrapper = new LambdaQueryWrapper<Reservation>()
                .in(Reservation::getStatus, ReservationStatus.SUCCESS.value(), ReservationStatus.PENDING.value(), ReservationStatus.FINISHED.value())
                .eq(Reservation::getSystemId, systemId)
                .eq(Reservation::getBizOrderNum, bizOrderNum)
                .eq(Reservation::getIsDelete, 0);
        Reservation reservation = reservationOpenApiMapper.selectOne(wrapper);
        return reservation;
    }

    /**
     * 使用第三方套餐id获取套餐
     *
     * @param bizComboId
     * @return
     */
    @Override
    public Createmeal getMealByBizComboId(String bizComboId, String systemId) {
        MealExternalInfo mealExternalInfo = mealExternalInfoMapper.selectOne(
                new LambdaQueryWrapper<MealExternalInfo>()
                        .eq(MealExternalInfo::getSourceId, systemId)
                        .eq(MealExternalInfo::getBizComboId, bizComboId)
        );
        if (mealExternalInfo == null) return null;
        return createmealMapper.selectOne(
                new LambdaQueryWrapper<Createmeal>()
                        .eq(Createmeal::getId, mealExternalInfo.getMealId())
                        .and(createmealLambdaQueryWrapper -> createmealLambdaQueryWrapper.isNull(Createmeal::getIsDelete).or().eq(Createmeal::getIsDelete, 0))
                        .and(createmealLambdaQueryWrapper -> createmealLambdaQueryWrapper.isNull(Createmeal::getForbidden).or().eq(Createmeal::getForbidden, 0))
        );
    }
}
