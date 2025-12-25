package com.center.medical.reservation.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.ReservationStatus;
import com.center.medical.bean.model.*;
import com.center.medical.common.config.PingAnConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.MD5;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.AreaMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.data.bean.model.PaPeissortexam;
import com.center.medical.finance.service.PhysicalCheckoutService;
import com.center.medical.reservation.bean.dto.ApplyForOrderDto;
import com.center.medical.reservation.bean.dto.GetPingAnCodeDto;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.model.ReservationSetting;
import com.center.medical.reservation.bean.param.PingAnNumsParam;
import com.center.medical.reservation.bean.param.ReservationSettingCondition;
import com.center.medical.reservation.bean.vo.ApplyForOrderVo;
import com.center.medical.reservation.bean.vo.PingAnNumsVo;
import com.center.medical.reservation.dao.PingAnMapper;
import com.center.medical.reservation.dao.ReservationSettingMapper;
import com.center.medical.reservation.service.PingAnService;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.reservation.service.ReservationSettingService;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.dao.CreatemealMapper;
import com.center.medical.sellcrm.dao.CreateorderMapper;
import com.center.medical.sellcrm.dao.SellcustomerMapper;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.sellcrm.service.PeisorgreservationService;
import com.center.medical.sellcrm.service.PeisorgreservationgroupService;
import com.center.medical.service.*;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 平安软件-排检(PaPeissortexam)服务实现类
 *
 * @author ay
 * @since 2023-10-20 13:51:14
 */
@Slf4j
@Service("pingAnService")
@RequiredArgsConstructor
public class PingAnServiceImpl extends ServiceImpl<PingAnMapper, PaPeissortexam> implements PingAnService {

    private final PingAnMapper pingAnMapper;
    private final PeispatientMapper peispatientMapper;
    private final AreaMapper areaMapper;
    private final SysBranchMapper sysBranchMapper;
    private final CreatemealMapper createmealMapper;
    private final CreateorderMapper createorderMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final ISysConfigService iSysConfigService;
    private final PeisOlService peisOlService;
    private final CreateorderService createorderService;
    private final PeispatientChargeMainService peispatientChargeMainService;
    private final PeispatientAndFzxService peispatientAndFzxService;
    private final PhysicalCheckoutService physicalCheckoutService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final PeisStateService peisStateService;
    private final ReservationSettingService reservationSettingService;
    private final ReservationService reservationService;
    private final ISysBranchService iSysBranchService;
    private final ReservationSettingMapper reservationSettingMapper;
    private final PeisorgreservationService peisorgreservationService;
    private final PeisorgreservationgroupService peisorgreservationgroupService;



    /**
     * 分页查询[平安软件-排检]列表
     *
     * @param page  分页参数
     * @param param PaPeissortexam查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PaPeissortexam> getPage(PageParam<PaPeissortexam> page, PaPeissortexam param) {
        return pingAnMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PaPeissortexam getInfoById(String id) {
        return pingAnMapper.getInfoById(id);
    }

    /**
     * 体检预约可用人数
     * @param param
     * @return
     */
    @Override
    public List<PingAnNumsVo> getAvailableNums(PingAnNumsParam param) {
        return pingAnMapper.getAvailableNums(param);
    }


    /**
     * 申请预约平安
     * @param data json数据
     * @param countreportoccupation 3平安好医生
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApplyForOrderVo applyForOrder(String data,Integer countreportoccupation) {
        ApplyForOrderVo vo = new ApplyForOrderVo();
        ApplyForOrderDto map = JSON.parseObject(data, ApplyForOrderDto.class);
        if(map == null){
            throw new ServiceException("data解析失败,data:"+data,400);
        }
        Date now = new Date();
        log.info("开始预约，data参数是:{}",map);
        String orderId = map.getOrderId();
        if(StringUtils.isEmpty(orderId)){throw new ServiceException("orderId不能为空",400);}
        //根据订单号做幂等  在编程中一个幂等操作的特点是其任意多次执行所产生的影响均与一次执行的影响相同。
        Peispatient oldp = peispatientMapper.selectOne(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getPatientnamereceipt,orderId));
        if(oldp!=null){
            vo.setStatus("200");
            vo.setHospitalOrderId(oldp.getPatientbizno());
            vo.setOrderState("01");
            return vo;
        }

        Peispatient patient = new Peispatient();
        patient.setPatientnamereceipt(orderId);

        String customerName = map.getCustomerName();
        if(StringUtils.isEmpty(customerName)){throw new ServiceException("customerName不能为空",400);}
        patient.setPatientname(customerName);

        String customerIdentityType = map.getCustomerIdentityType();
        if(StringUtils.isEmpty(customerIdentityType)){throw new ServiceException("customerIdentityType不能为空",400);}
        patient.setCountreportoccupationxml(Integer.parseInt(customerIdentityType));

        String customerIdentityNo=map.getCustomerIdentityNo();
        if(StringUtils.isEmpty(customerIdentityNo)){throw new ServiceException("customerIdentityNo不能为空",400);}
        patient.setIdcardno(customerIdentityNo);
        if(patient.getCountreportoccupationxml()==1){
            Area area = null;
            if (!StringUtils.isBlank(patient.getIdcardno())) {
                area = areaMapper.selectOne(new LambdaQueryWrapper<Area>().eq(Area::getAreaCode, patient.getIdcardno().substring(0, 2)));
                if (null == area) {
                    area = areaMapper.selectOne(new LambdaQueryWrapper<Area>().eq(Area::getResarea,"山东省"));
                }
            } else {
                area = areaMapper.selectOne(new LambdaQueryWrapper<Area>().eq(Area::getResarea, "山东省"));
            }
            if (null != area) {
                patient.setIdResarea(area.getId());
                patient.setResarea(area.getResarea());
            }
        }

        String customerGender = map.getCustomerGender();
        if(StringUtils.isEmpty(customerGender)){throw new ServiceException("customerGender不能为空",400);}
        patient.setIdSex("M".equals(customerGender)?0:"F".equals(customerGender)?1:null);

        String customerBirthday=map.getCustomerBirthday();
        if(StringUtils.isEmpty(customerBirthday)){throw new ServiceException("customerBirthday不能为空",400);}
        try {
            patient.setBirthdate(new SimpleDateFormat("yyyyMMdd").parse(customerBirthday));
            patient.setAge(DateUtil.ageOfNow(DateUtil.parse(customerBirthday)));
        } catch (ParseException e) {
            throw new ServiceException("customerBirthday格式不正确，应为yyyyMMdd,实际"+customerBirthday);
        }

        //生理状态：
//		男：01
//		未婚女：02
//		已婚女：03
        String medicalStatus=map.getMedicalStatus();
        //海康医院没有这个字段
//		if(StringUtils.isEmpty(medicalStatus)){throw new TbException("medicalStatus不能为空");}
//		patient.setIdSex("01".equals(medicalStatus)?"0":"1");

        //门店ID
        String hospitalSubId=map.getHospitalSubId();
        if(StringUtils.isEmpty(hospitalSubId)){throw new ServiceException("hospitalSubId不能为空",400);}
        SysBranch branch = sysBranchMapper.selectOne(new LambdaQueryWrapper<SysBranch>().eq(SysBranch::getHospitalSubId,hospitalSubId));
        if(branch==null){throw new ServiceException("hospitalSubId不存在："+hospitalSubId,400);}
        patient.setHospitalcode(branch.getBranchId());


        //平安
        if(countreportoccupation==3) {
            //套餐
            String medicalPackage = map.getMedicalPackage();
            if(StringUtils.isEmpty(medicalPackage)){throw new ServiceException("medicalPackage不能为空",400);}


            String sfyh = "";
            if(patient.getIdSex() != null && patient.getIdSex().equals(1)){
                if(StrUtil.isNotEmpty(medicalStatus)){
                    sfyh="03".equals(medicalStatus)?"0":"1";
                }
            }
            //按性别、已婚未婚、分中心匹配套餐
            log.info("查询套餐参数medicalPackage：{}、IdSex：{}、BranchId：{}、sfyh：{}",medicalPackage,patient.getIdSex(),branch.getBranchId(),sfyh);
            List<Createmeal> meals = createmealMapper.findPingAn(medicalPackage,patient.getIdSex(),branch.getBranchId(),sfyh);
            log.info("查询套餐结果：{}",meals);
            if(meals.size()==0){throw new ServiceException("套餐不存在，请检查套餐的平安id、性别、分中心、婚姻状态是否匹配",400);}
            Createmeal meal = meals.get(0);
            patient.setIdTjtc(meal.getId());
            patient.setExamsuiteName(meal.getTjtcmc());
            //套餐只会属于一个订单，开单销售是订单的开单销售，没有订单不能预约成功,团体是订单的团体
            List<Createorder> orders = createorderMapper.getPingAnById(meal.getId());

            if(orders.size()==0){throw new ServiceException("订单不存在，请检查订单是否已审核通过、变更通过，订单内是否已添加此套餐。",400);}
            Createorder order=orders.get(0);
            patient.setIdOrg(order.getKhdwmcid());
            Sellcustomer customer = sellcustomerMapper.getInfoById(order.getKhdwmcid());
            patient.setOrgName(customer.getKhdwmc());
            patient.setIdOpendoctor(customer.getXsjlid());
            patient.setDoctorapply(customer.getXsjl());
            patient.setNumorgresv(order.getDdh());
            //设置一下任务和分组
            Peisorgreservation peisorgreservation =  peisorgreservationService.getOne(new LambdaQueryWrapper<Peisorgreservation>().eq(Peisorgreservation::getDdh, order.getId()));
            if (ObjectUtils.isNotEmpty(peisorgreservation)){
                patient.setIdOrgreservation(peisorgreservation.getId());
                List<Peisorgreservationgroup> groupList = peisorgreservationgroupService.list(new LambdaQueryWrapper<Peisorgreservationgroup>()
                        .eq(Peisorgreservationgroup::getIdOrgreservation, peisorgreservation.getId())
                        .eq(Peisorgreservationgroup::getIdExamsuite, patient.getIdTjtc())
                );
                if (CollectionUtils.isNotEmpty(groupList)){
                    patient.setIdOrgreservationgroup(groupList.get(0).getId());
                }
            }
        }

        String appointmentTime = map.getAppointmentTime();
        if(StringUtils.isEmpty(appointmentTime)){throw new ServiceException("appointmentTime不能为空",400);}
        try {
            patient.setDateguidancereturned(new SimpleDateFormat("yyyyMMddHHmmss").parse(appointmentTime));
        } catch (ParseException e) {
            throw new ServiceException("appointmentTime格式不正确，应为yyyyMMddHHmmss，实际"+appointmentTime,400);
        }
        patient.setCountreportoccupation(countreportoccupation);
        patient.setFIsforreserve(1);

        String hasAuthorized = map.getHasAuthorized();
        if(StringUtils.isEmpty(hasAuthorized)){throw new ServiceException("hasAuthorized不能为空",400);}
        patient.setStatusofhm(hasAuthorized);

        String phone = map.getPhone();
        patient.setPhone(phone);

//		String packageDisplayName=map.get("packageDisplayName");
//		if(StringUtils.isEmpty(packageDisplayName)){throw new TbException("400");}

        String companyName=map.getCompanyName();
        patient.setOrgDepart(companyName);

        String isVip = map.getIsVip();
        if(StringUtils.isEmpty(isVip)){throw new ServiceException("isVip不能为空",400);}
        patient.setIdPatientclass("Y".equals(isVip)?"2":"1");

        // 体检者默认头像
//		String defaultPhoto = ToolUtil.getPropert("printer.properties", "patientImg");
//		patient.setPicture(defaultPhoto);
        patient.setFUsecodehiden(1);//平安好医生按团检来
        patient.setIdExamtype("0");
        patient.setInputCode(ToolUtil.getHanziPinyinHeadChar(patient.getPatientname()));

        String patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(branch.getBranchId()), "");
        patient.setPatientcode(patientCode);
        patient.setPatientbizno(patientCode);
        patient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
        patient.setFRegistered(0);
        patient.setTimingstartedat(now);

        PeisOl po=new PeisOl();
        po.setPatientbizno(patientCode);
        //微信商城核销码
        String chargedCode = map.getChargedCode();
        po.setOrgDepartsubd(chargedCode);

        //微信商城套餐价格
        Double ageofreal = map.getAgeofreal()==null?null:Double.parseDouble(map.getAgeofreal());
        po.setAgeofreal(ageofreal);
        peisOlService.save(po);

        patient.setFExamstarted(0);
        patient.setFReadytofinal(0);
        patient.setFFinallocked(0);
        patient.setFFinalexamed(0.0);

//        patient.setIdCis(createorderService.bindArchive(patient));//线上可能会有多个档案，多个区的，这绑定会出错
        peispatientMapper.insert(patient);

        peispatientChargeMainService.save(new PeispatientChargeMain("通用预约接口预约于"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+";", patient.getMoneyamount(), patient.getMoneyamountpaid(), patientCode));

        String time=appointmentTime.substring(0,8);


        //添加预约记录
        newCanOrder(time,isVip,orderId,patient);

        peispatientAndFzxService.save(new PeispatientAndFzx(branch.getBranchId(), patient.getId(), 0));

        long a=System.currentTimeMillis();
        List<HashMap<String, String>> items = physicalCheckoutService.getExamfeeitem(patient.getIdTjtc());
        if(ageofreal!=null) {
            for(HashMap<String, String> item:items) {
                item.put("zhjg", ageofreal+"");
            }
        }
        System.out.println("getExamfeeitem花费："+((System.currentTimeMillis()-a)/1000)+"秒");
        a=System.currentTimeMillis();
        List<Peispatientfeeitem> peispatientfeeitems = new ArrayList<Peispatientfeeitem>();
        int size= items.size();
        // 折扣价格是否放在【个检报告工本费】上
        Boolean isMakeGb = false;
        for (int j = 0; j < size; j++) {
            Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
            peispatientfeeitem.setIdPatient(patientCode);
            peispatientfeeitem.setIdExamfeeitem(items.get(j).get("idExamfeeitem"));
            peispatientfeeitem.setExamfeeitemName(items.get(j).get("examfeeitemName"));
            peispatientfeeitem.setPrice(Double.valueOf(items.get(j).get("price")));
            peispatientfeeitem.setIdKs(items.get(j).get("idKs"));

            Object[] oa = peispatientfeeitemService.getFactPrice(items.get(j), size, j, isMakeGb);
            if(!isMakeGb){isMakeGb=(Boolean) oa[1];}
            peispatientfeeitem.setFactprice(Double.parseDouble(oa[0].toString()));
            peispatientfeeitem.setCount(1);
            peispatientfeeitem.setIdPayway("1");
            peispatientfeeitem.setFRegistered(0);
            peispatientfeeitem.setChangeItem(0);
            peispatientfeeitem.setFMarkFeereturn(0);
            peispatientfeeitem.setFFeecharged(0);
            peispatientfeeitem.setFLabsendtolis(0);
            peispatientfeeitem.setFExaminated(0);
            peispatientfeeitem.setFGiveup(0);
            peispatientfeeitem.setIsbx(items.get(j).get("isbx")==null?0:Integer.valueOf(items.get(j).get("isbx")));
            peispatientfeeitem.setBxcount(items.get(j).get("bxcount")==null?0:Integer.valueOf(items.get(j).get("bxcount")));
            peispatientfeeitem.setFDelayed(0);
            peispatientfeeitem.setIsMintc(1);
            peispatientfeeitem.setQty(items.get(j).get("qty")==null?null:Integer.parseInt(items.get(j).get("qty")));
            peispatientfeeitems.add(peispatientfeeitem);
        }
        peispatientfeeitemService.saveOrUpdateBatch(peispatientfeeitems);
        log.info("savepeispatientfeeitem花费："+((System.currentTimeMillis()-a)/1000)+"秒");
        vo.setStatus("200");
        vo.setHospitalOrderId(patient.getPatientbizno());
        vo.setOrderState("01");
        log.info("预约成功：" + patient.getPatientbizno());
        return vo;
    }



    @SuppressWarnings("rawtypes")
    @Transactional(rollbackFor=Exception.class)
    public void canOrder(String time,String hospitalSubId,String isVip){
        PingAnNumsParam param = new PingAnNumsParam();
        param.setStartDate(time);
        param.setEndDate(time);
        param.setHospitalSubId(hospitalSubId);
        List<PingAnNumsVo> pes = getAvailableNums(param);
        if(pes.size() == 0){
            throw new ServiceException("时间段内不能预约，time："+time+",hospitalSubId:"+hospitalSubId);
        }
        PingAnNumsVo os = pes.get(0);
        Integer totalNum = os.getMaxNum() - os.getOrderNum();
        Integer vipNum = os.getVipMaxNum() - os.getVipOrderNum();
        if("Y".equals(isVip)){
            if(vipNum<=0){
                throw new ServiceException("预约人数已满");
            }
        }else{
            if(totalNum-vipNum<=0){
                throw new ServiceException("预约人数已满");
            }
        }
    }


    /**
     * 新系统完成预约
     * @param time 预约时间
     * @param isVip 是否vip
     * @param patient 体检者信息
     */
    public void newCanOrder(String time,String isVip,String bizOrderNo,Peispatient patient){
        String brandId = patient.getHospitalcode();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        DateTime dateTime = null;
        try {
            dateTime = DateUtil.beginOfDay(dateFormat.parse(time));
        } catch (ParseException e) {
            throw new ServiceException("时间日期转换错误！");
        }
        Integer levelId = "Y".equals(isVip) ? 2 : 1;

        //查询可预约的时间段
        ReservationSettingCondition reservationSettingCondition = new ReservationSettingCondition(dateTime,levelId, brandId, 1);
        List<ReservationSetting> timeIdList = reservationSettingMapper.selectList(new LambdaQueryWrapper<ReservationSetting>()
                .eq(ReservationSetting::getReservationDate, reservationSettingCondition.getReservationDate())
                .eq(ReservationSetting::getBranchId, reservationSettingCondition.getBranchId())
                .eq(ReservationSetting::getStatus, 1)
                .eq(ReservationSetting::getLevelId, reservationSettingCondition.getLevelId())
                .gt(ReservationSetting::getAbleNum, 0));
        if (CollectionUtil.isNotEmpty(timeIdList)) {
            String timeId = timeIdList.get(0).getId().toString();
            log.info("平安好医生的timeId是:{}",timeId);
            //插入或修改预约信息
            Reservation reservation = reservationService.getOne(new LambdaQueryWrapper<Reservation>()
                    .eq(Reservation::getPatientcode, patient.getPatientcode())
                    .eq(Reservation::getStatus, 2)
            );
            if (ObjectUtils.isEmpty(reservation)){
                reservation = new Reservation();
                reservation.setFUsecodehiden(0);
                reservation.setBranchId(brandId);
                reservation.setLevelId(levelId);
                reservation.setLevelName(levelId==1?"普通会员":"vip");
                reservation.setReservationDate(dateTime);
                reservation.setTimeId(timeId);
                reservation.setComboId(patient.getIdTjtc());
                reservation.setTjtcmc(patient.getExamsuiteName());
                reservation.setBizOrderNum(bizOrderNo);
                reservation.setRealName(patient.getPatientname());
                reservation.setSex(patient.getIdSex());
                reservation.setIdcard(patient.getIdcardno());
                reservation.setMobile(patient.getPhone());
                reservation.setSystemId("平安好医生");
                reservation.setPatientcode(patient.getPatientcode());
                reservation.setOrderNum(patient.getNumorgresv());
                reservation.setIdOrg(patient.getIdOrg());
                reservation.setRemark("平安好医生");
            }else {
                reservation.setReservationDate(dateTime);
                reservation.setLevelId(levelId);
                reservation.setLevelName(levelId==1?"普通会员":"vip");
                reservation.setTimeId(timeId);
            }
            reservationService.saOrUp(reservation);
        } else {
            throw new ServiceException("预约失败，" + dateTime + "当天可预约人数不足！");
        }
    }




    /**
     * 取消申请
     * @param hospitalOrderId
     * @param orderId
     * @param countreportoccupation 3平安好医生
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(String hospitalOrderId, String orderId, int countreportoccupation) {
        log.info("取消预约：hospitalOrderId:"+hospitalOrderId+",orderId:"+orderId);
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientbizno", hospitalOrderId)
                .eq("countreportoccupation",countreportoccupation));
        if(patient==null){
            return;
        }
        if(patient.getFRegistered()!=null&&patient.getFRegistered()==1){
            throw new ServiceException("已登记不能取消预约");
        }

        //取消预约记录
        Reservation reservation = reservationService.getOne(new LambdaQueryWrapper<Reservation>()
                .eq(Reservation::getPatientcode, patient.getPatientcode())
                .in(Reservation::getStatus, 0, 1, 2)
        );
        if (Objects.isNull(reservation)) {
            //没有符合条件的预约记录
            throw new ServiceException("体检系统中没有符合条件的预约记录");
        }
        reservation.setStatus(ReservationStatus.fail.value());
        reservation.setFailReason("取消预约");
        reservation.setModifydate(new Date());
        reservationService.updateById(reservation);
        // 恢复可预约人数
        ReservationSetting setting = reservationSettingService.getInfoById(reservation.getTimeId());
        if (Objects.nonNull(setting)) {
            reservationSettingService.updateAbleNumWithRetry(reservation.getTimeId(), 1);
        }
        // 删除体检者数据
        peispatientAndFzxService.remove(new QueryWrapper<PeispatientAndFzx>().eq("patient_id",patient.getId()));
        peispatientfeeitemService.remove(new QueryWrapper<Peispatientfeeitem>().eq("id_patient",patient.getId()));
        peispatientMapper.deleteById(patient);
    }


    /**
     *
     * 修改预约
     * @param hospitalOrderId
     * @param orderId
     * @param appointmentTime
     * @param countreportoccupation
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeDate(String hospitalOrderId, String orderId, String appointmentTime, int countreportoccupation) {
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientbizno", hospitalOrderId)
                .eq("countreportoccupation",countreportoccupation)
        );
        if(patient==null){
            throw new ServiceException("体检者不存在，hospitalOrderId："+hospitalOrderId+",countreportoccupation:"+countreportoccupation);
        }

        //改期根据orderId和改期时间做幂等
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        if(patient.getDateguidancereturned()!=null){
            if(sdf.format(patient.getDateguidancereturned()).equals(appointmentTime)){
                return;
            }
        }
        if(patient.getFRegistered()!=null&&patient.getFRegistered()==1){
            throw new ServiceException("已登记不能修改预约时间");
        }
        PeispatientAndFzx paf = peispatientAndFzxService.getOne(new QueryWrapper<PeispatientAndFzx>().eq("patient_id", patient.getId()));
        if(paf==null){
            throw new ServiceException("没有预约分中心，无法修改预约时间");
        }
        SysBranch branch = sysBranchMapper.getByBranchId(paf.getFzxId());
        if(branch==null||branch.getHospitalSubId()==null){
            throw new ServiceException("预约分中心不存在，无法修改预约时间");
        }

        String isVip = "2".equals(patient.getIdPatientclass())?"Y":"N";
        newCanOrder(appointmentTime.substring(0,8),isVip,orderId,patient);
        try {
            Date dateguidancereturned=sdf.parse(appointmentTime);
            patient.setDateguidancereturned(dateguidancereturned);
            peispatientMapper.updateById(patient);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServiceException("预约时间appointmentTime格式不正确，应为yyyyMMddHHmmss，实际"+appointmentTime);
        }

    }


    /**
     * 客户授权查看影像报告
     * @param hospitalOrderId
     * @param orderId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void authorize(String hospitalOrderId, String orderId) {
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientbizno", hospitalOrderId));
        if(patient==null){
            throw new ServiceException("500");
        }
        if(patient.getCountreportoccupation()==null||patient.getCountreportoccupation()!=3){
            throw new ServiceException("401");
        }
        patient.setStatusofhm("Y");
        peispatientMapper.updateById(patient);
    }


    /**
     * 到检确认
     * @param id
     * @param captcha
     * @return
     */
    @Override
    public void confirmOrder(String id, String captcha) {
        Peispatient patient = peispatientMapper.getInfoById(id);
        Integer countreportoccupation = patient.getCountreportoccupation();
        if(ObjectUtils.isEmpty(countreportoccupation)){
            throw new ServiceException("不是第三方平台预约体检者，不能导检确认。");
        }


        StringBuilder data=new StringBuilder();
        String timestamp=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        PingAnConfig pingAnConfig = iSysConfigService.getSysConfigObject(Constants.PINGAN_CONFIG, PingAnConfig.class);
        data.append("pajkKey=" + pingAnConfig.getPajkKey());
        data.append("&sign="+ MD5.encode(pingAnConfig.getPajkKey() + pingAnConfig.getPajkPwd() + timestamp));
        data.append("&timestamp="+timestamp);
        data.append("&hospitalOrderId="+patient.getPatientbizno());
        data.append("&orderId="+patient.getPatientnamereceipt());
        data.append("&orderState=03");
        data.append("&captcha="+captcha);


        String routerUrl = pingAnConfig.getConfirmUrl();
        String result="";
        String str = HttpUtils.sendGet(routerUrl, data.toString());
        JSONObject map = JSON.parseObject(str);
        log.info("平安到检确认返回数据:{}",map);
        if (ObjectUtils.isEmpty(map)) throw new ServiceException("平安核销失败！请检查网络！");

        if("200".equals(map.get("status"))){
            patient.setFOutpatient(1);
            patient.setInstancetag(captcha);
            peispatientMapper.updateById(patient);
            peisStateService.setScbs(patient.getPatientcode(),0);
        }else{
            String errorCode = String.valueOf(map.get("errorCode"));
            System.out.println("核销失败:"+result);
            throw new ServiceException("01".equals(errorCode)?"重复核销":"02".equals(errorCode)?"订单状态异常":"zk01".equals(errorCode)?"沃德外网服务器异常":"其他");
        }
    }

    /**
     * 获取平安好医生需要上传pdf的体检号
     * @return
     */
    @Override
    public List<GetPingAnCodeDto> getPingAnCode() {
        return pingAnMapper.getPingAnCode();
    }

}

