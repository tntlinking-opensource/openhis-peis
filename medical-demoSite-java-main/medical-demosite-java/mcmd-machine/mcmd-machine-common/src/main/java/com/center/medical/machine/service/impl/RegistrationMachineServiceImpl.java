package com.center.medical.machine.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.config.QueueConfig;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.ReportConstants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.dao.*;
import com.center.medical.dao.*;
import com.center.medical.machine.bean.dto.PeispatientDto;
import com.center.medical.machine.bean.dto.RegisterSelectDTO;
import com.center.medical.machine.bean.model.PackageItemEntity;
import com.center.medical.machine.bean.model.RegisteredEntity;
import com.center.medical.machine.bean.param.ModelParam;
import com.center.medical.machine.bean.vo.ResultVo;
import com.center.medical.machine.dao.RegistrationMachineMapper;
import com.center.medical.machine.service.RegistrationMachineService;
import com.center.medical.machine.service.TeamRegisterService;
import com.center.medical.olddata.service.OrPeispatientService;
import com.center.medical.pacslis.dao.PacsPeispatientMapper;
import com.center.medical.reception.service.OrderService;
import com.center.medical.sellcrm.bean.model.Createcombo;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.bean.model.Mealanditem;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.dao.*;
import com.center.medical.sellcrm.service.CreatemealService;
import com.center.medical.service.PeisDydCtSeqService;
import com.center.medical.service.PeispatientPhotoService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 自助登记机(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-05-23 09:30:33
 */
@Slf4j
@Service("registrationMachine")
@RequiredArgsConstructor
public class RegistrationMachineServiceImpl extends ServiceImpl<RegistrationMachineMapper, Peispatient> implements RegistrationMachineService {

    private final RegistrationMachineMapper registrationMachineMapper;
    private final TeamRegisterService teamRegisterService;
    private final PeispatientMapper peispatientMapper;
    private final PeispatientPhotoMapper peispatientPhotoMapper;
    private final CreatemealMapper createmealMapper;
    private final CreatemealService createmealService;
    private final CreatecomboMapper createcomboMapper;
    private final SysBranchMapper sysBranchMapper;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final SysUserMapper sysUserMapper;
    private final PacsPeispatientMapper pacsPeispatientMapper;
    private final NotificationmethodMapper notificationmethodMapper;
    private final PeispatientPhotoService peispatientPhotoService;
    private final PatienttypeMapper patienttypeMapper;
    private final PeispatientchargeMapper peispatientchargeMapper;
    private final MealanditemMapper mealanditemMapper;
    private final InspectChargeMapper inspectChargeMapper;
    private final ItemsMapper itemsMapper;
    private final PeispatientChargeMainMapper peispatientChargeMainMapper;
    private final BasexamltemMapper basexamltemMapper;
    private final PeisStateMapper peisStateMapper;
    private final SysDeptMapper sysDeptMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final ISysConfigService iSysConfigService;
    private final PeisDydCtSeqService peisDydCtSeqService;
    private final ISysBranchService iSysBranchService;
    private final OrPeispatientService orPeispatientService;
    private final OrderService orderService;
    @Autowired
    private LoadProperties loadProperties;


    public static Map<String, RegisteredEntity> map = new ConcurrentHashMap<>();

    /**
     * 自助登记
     *
     * @param registeredEntity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo register(RegisteredEntity registeredEntity) {
        ResultVo result = new ResultVo();
        List<PeispatientDto> peispatients = getPeispatient(registeredEntity);
        if (peispatients != null && peispatients.size() > 0) {
            if (peispatients.size() > 1) {
                result.setCode("0");
                result.setMessage("你存在多个体检登记信息，请选择");
                result.setSuccess(true);
                result.setTotal(peispatients.size());
                StringBuilder stringBuilder = new StringBuilder();
                for (PeispatientDto peispatient : peispatients) {
                    String patientcode = peispatient.getPatientcode();
                    stringBuilder.append(patientcode + ",");
                }
                String substring = stringBuilder.substring(0, stringBuilder.length() - 1);
                HashMap<Object, Object> data = new HashMap<>();
                data.put("patientcode", substring);
                result.setData(data);
                result.setTotal(peispatients.size());
                return result;
            } else {
                PeispatientDto peispatient = peispatients.get(0);
                registeredEntity.setPatientCode(peispatient.getPatientcode());
                if (checkMultipleSelection(registeredEntity)) {
                    result.setCode("3");
                    result.setMessage("当前操作不支持，请联系前台");
                    HashMap<Object, Object> data = new HashMap<>();
                    data.put("patientcode", peispatient.getPatientcode());
                    result.setData(data);
                    result.setSuccess(true);
                    return result;
                } else {
                    result.setCode("0");
                    result.setMessage("获取成功");
                    HashMap<Object, Object> data = new HashMap<>();
                    data.put("patientcode", peispatient.getPatientcode());
                    result.setData(data);
                    result.setSuccess(true);
                    return result;

                }
            }
        } else {
            result.setCode("2");
        }
        result.setMessage("当前没有登记或者预约记录，是否为入职体检");
        result.setSuccess(false);
        return result;
    }

    /**
     * 更新体检者表通过体检号
     *
     * @param registeredEntity
     * @param patientcode
     */
    @Transactional(rollbackFor = Exception.class)
    public String updatePatientInfoByPatientCode(RegisteredEntity registeredEntity, String patientcode) {
        String fzxjm = StringUtils.isBlank(ZhongkangConfig.getFzxjm()) ? iSysBranchService.getBranchPrefix() : ZhongkangConfig.getFzxjm();
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientcode);
        String sexFlag = peispatient.getIdSex() == 0 ? "男" : "女";
        if (StringUtils.isNotEmpty(registeredEntity.getName()) && !registeredEntity.getName().equals(peispatient.getPatientname())) {
            peispatient.setPatientname(registeredEntity.getName());
        }
        if (StringUtils.isNotEmpty(registeredEntity.getSex()) && !sexFlag.equals(registeredEntity.getSex())) {
            Integer idSex = peispatient.getIdSex();
            System.out.println("idSex = " + idSex);
            System.out.println("sex = " + registeredEntity.getSex());
            peispatient.setIdSex("男".equals(registeredEntity.getSex()) ? 0 : 1);
        }
        //设置年龄
        if (ObjectUtils.isEmpty(peispatient.getAge()) || peispatient.getAge() == 0){
            if (StringUtils.isNotEmpty(peispatient.getIdcardno()) && IdcardUtil.isValidCard(peispatient.getIdcardno())){
                peispatient.setAge(IdcardUtil.getAgeByIdCard(peispatient.getIdcardno().trim()));
            }
        }

        if (patientcode.indexOf(Constants.ONLINE_PREFIX) != -1 || !patientcode.startsWith(fzxjm)) {
            // 如果已app开头或者是不是以本分中心的简码开头，转换体检号并重新赋值
            String newPatientcode = CodeUtil.appConvert(iSysBranchService.getBranchFlag(null), "",patientcode);
            //收费主表
            PeispatientChargeMain peispatientChargeMain = peispatientChargeMainMapper.getByPatientCode(patientcode);
            if (ObjectUtils.isNotEmpty(peispatientChargeMain)){
                peispatientChargeMain.setPatientcode(newPatientcode);
                peispatientChargeMainMapper.updateById(peispatientChargeMain);
            }
            //收费项目
            List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemMapper.getByPatientCode(patientcode);
            if (ObjectUtils.isNotEmpty(peispatientfeeitems)){
                for (Peispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                    peispatientfeeitem.setIdPatient(newPatientcode);
                    peispatientfeeitemMapper.updateById(peispatientfeeitem);
                }
            }
            peispatient.setHospitalcode(SecurityUtils.getCId());
            peispatient.setFPaused(0);
            peispatient.setPatientcode(newPatientcode);
        }
        peispatientMapper.updateById(peispatient);
        savePicture(registeredEntity, peispatient);
        return peispatient.getPatientcode();
    }

    /**
     * 保存图片
     *
     * @param registeredEntity
     * @param peispatient
     */
    public void savePicture(RegisteredEntity registeredEntity, Peispatient peispatient) {
        //体检者头像
        PeispatientPhoto photo = peispatientPhotoMapper.selectOne(new QueryWrapper<PeispatientPhoto>()
                .eq("patientcode", peispatient.getPatientcode()));
        if (photo == null) {
            peispatientPhotoMapper.insert(new PeispatientPhoto(peispatient.getPatientcode(), registeredEntity.getPicture()));
        } else {
            photo.setPicture(registeredEntity.getPicture());
            peispatientPhotoMapper.updateById(photo);
        }
    }


    public List<PeispatientDto> getPeispatient(RegisteredEntity entity) {
        ArrayList<PeispatientDto> peispatients = new ArrayList<>();
        HashSet<String> strings = new HashSet<>();
        //团体
        List<PeispatientDto> teamPeispatients = teamRegisterService.getPeispatient(entity);
        for (PeispatientDto peispatientDto : teamPeispatients) {
            if (strings.add(peispatientDto.getPatientcode())) {
                peispatients.add(peispatientDto);
            }
        }
        //个人
        List<PeispatientDto> personPeispatient = teamRegisterService.getPeispatientPersonal(entity);
        for (PeispatientDto peispatientDto : personPeispatient) {
            if (strings.add(peispatientDto.getPatientcode())) {
                peispatients.add(peispatientDto);
            }
        }
        //复查
        List<PeispatientDto> servicePeispatient = teamRegisterService.getPeispatientRecheck(entity);
        for (PeispatientDto peispatientDto : servicePeispatient) {
            if (strings.add(peispatientDto.getPatientcode())) {
                peispatients.add(peispatientDto);
            }
        }
        //补检
        List<PeispatientDto> makeUpRegisterServicePeispatient = teamRegisterService.getPeispatientMakeUp(entity);
        for (PeispatientDto peispatientDto : makeUpRegisterServicePeispatient) {
            if (strings.add(peispatientDto.getPatientcode())) {
                peispatients.add(peispatientDto);
            }
        }
        return peispatients;
    }

    public boolean checkMultipleSelection(RegisteredEntity registeredEntity) {
        String patientCode = registeredEntity.getPatientCode();
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        if (StringUtils.isNotEmpty(peispatient.getIdTjtc())) {
            //体检套餐ID
            String idTjtc = peispatient.getIdTjtc();
            Createmeal createMeal = createmealMapper.getInfoById(idTjtc);
            if (createMeal != null) {
                Integer count = registrationMachineMapper.existBxxmByTcId(idTjtc);
                return count > 0;
            }
            return false;
        } else {
            return false;
        }
    }


    /**
     * 登记的体检信息
     *
     * @param patientCodes
     * @return
     */
    @Override
    public List<RegisterSelectDTO> registerSelect(List<String> patientCodes) {
        List<RegisterSelectDTO> registerSelectDTOS = new ArrayList<>();
        List<Peispatient> peispatients = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                .in("patientcode", patientCodes));
        for (Peispatient peispatient : peispatients) {
            //查询老系统是否已登记
            if (iSysConfigService.oldSystemOpen()){
                Boolean b = orPeispatientService.isRegistered(peispatient.getId());
                if (b){
                    continue;
                }
            }
            //套餐id
            String idTjtc = peispatient.getIdTjtc();
            String tcName = "";
            if (StringUtils.isNotBlank(idTjtc)){
                Createmeal createMeal = createmealMapper.getInfoById(idTjtc);
                if (createMeal != null) {
                    tcName = createMeal.getTjtcmc();
                } else {
                    Createcombo createCombo = createcomboMapper.getInfoById(idTjtc);
                    if(createCombo!=null){
                        tcName = createCombo.getTjtcmc();
                    }
                }
            }

            String type = "";
            Integer countreportoccupation = peispatient.getCountreportoccupation();
            if (countreportoccupation == null) {
                type = "普通";
            } else {
                switch (countreportoccupation) {
                    case 1:
                        type = "平安预约";
                        break;
                    case 2:
                        type = "微信商城";
                        break;
                    case 3:
                        type = "康淘公社";
                        break;
                }
            }
//            团队名称
            String orgName = peispatient.getOrgName();
            RegisterSelectDTO registerSelectDTO = new RegisterSelectDTO();
            registerSelectDTO.setFPaused(peispatient.getFPaused());
            registerSelectDTO.setPatientCode(peispatient.getPatientcode());
            registerSelectDTO.setPatientName(peispatient.getPatientname());
            registerSelectDTO.setTeamName(orgName);
            registerSelectDTO.setMealName(tcName);
            registerSelectDTO.setType(type);
            registerSelectDTO.setCreateDate(DateFormatUtils.format(peispatient.getCreatedate(), "yyyy-MM-dd"));
            double tsLimit = peispatient.getTsLimit() == null ? 0d : peispatient.getTsLimit();
            registerSelectDTO.setTsLimit(BigDecimal.valueOf(tsLimit));
            if (tsLimit > 0) {
                double val = ObjectUtils.isNotEmpty(peispatient.getMoneyamount())?peispatient.getMoneyamount():0.0 - tsLimit;
                if (val < 0) {
                    registerSelectDTO.setPayAmount(BigDecimal.ZERO);
                } else {
                    registerSelectDTO.setPayAmount(BigDecimal.valueOf(val).setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            } else {
                registerSelectDTO.setPayAmount(BigDecimal.ZERO);
            }
            //注册
            registerSelectDTO.setCanReg(
                    (peispatient.getTsLimit()!=null?peispatient.getTsLimit().doubleValue():0.0)
                            >= 0.0 ? 1 : 0);
            String types = getType(peispatient);
            System.out.println("type:" + types);
            //是否支付
            if ("团检".equals(types)) {
                String payWay = checkPayWayByCode(peispatient.getPatientcode());
                if ("1".equals(payWay)) {
                    //现金
                    Long count = peispatientfeeitemMapper.selectCount(new LambdaQueryWrapper<Peispatientfeeitem>()
                            .eq(Peispatientfeeitem::getIdPatient, peispatient.getPatientcode())
                            .eq(Peispatientfeeitem::getIdPayway,1));
                    if (count>0){
                        //现金
                        registerSelectDTO.setIsPay(1);
                    }else {
                        //统收
                        registerSelectDTO.setIsPay(0);
                    }
                } else {
                    //统收
                    BigDecimal decimal = teamRegisterService.getPriceAmount(peispatient.getPatientcode());
                    if (decimal.doubleValue() > 0) {
                        registerSelectDTO.setIsPay(1);
                    } else {
                        registerSelectDTO.setIsPay(0);
                    }
                }
            } else if ("个人".equals(types)) {
                registerSelectDTO.setIsPay(1);
            }

            //如果包含组选，也不能用自助登记机登记
            Long count = peispatientfeeitemMapper.selectCount(new LambdaQueryWrapper<Peispatientfeeitem>()
                    .eq(Peispatientfeeitem::getIdPatient, peispatient.getPatientcode())
                    .eq(Peispatientfeeitem::getIsbx, 1)
            );
            if (count >= 1){
                registerSelectDTO.setIsPay(1);
            }

            //如果是职业或者综合的，要校验一下工种是否为空
            if ("1".equals(peispatient.getIdExamtype()) || "2".equals(peispatient.getIdExamtype())){
                if (StringUtils.isBlank(peispatient.getWorktypeId())){
                    log.info(peispatient.getPatientcode() + "工种为空!");
                    registerSelectDTO.setIsPay(1);
                }
            }


            registerSelectDTOS.add(registerSelectDTO);
        }
        return registerSelectDTOS;
    }

    /**
     * 自助登记-确认登记
     *
     * @param registeredEntity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVo confirmRegister(RegisteredEntity registeredEntity) {
        if (map.get(registeredEntity.getPatientCode()) == null) {
            Peispatient peispatient1 = peispatientMapper.getByPatientCode(registeredEntity.getPatientCode());
            String type = getType(peispatient1);
            System.out.println("type:" + type);
            if ("团检".equals(type)) {
                registeredEntity = teamRegisterService.createPeispatient(registeredEntity);//在这里面收费的
                boolean selection = checkMultipleSelection(registeredEntity);
                if (selection) {
                    registeredEntity.setIsSelected(1);
                } else {
                    registeredEntity.setIsSelected(0);
                }
                String payWay = checkPayWay(registeredEntity);
                if ("1".equals(payWay)) {
                    //判断是否是有现金的收费记录
                    Long count = peispatientfeeitemMapper.selectCount(new LambdaQueryWrapper<Peispatientfeeitem>()
                            .eq(Peispatientfeeitem::getIdPatient, peispatient1.getPatientcode()).eq(Peispatientfeeitem::getIdPayway,1));
                    //有现金的就需要支付
                    if (count>0){
                        registeredEntity.setIsPay(1);
                    }else {
                        registeredEntity.setIsPay(0);
                    }
                } else {
                    //计算没支付的价格
                    BigDecimal decimal = teamRegisterService.getPriceAmount(registeredEntity.getPatientCode());
                    if (decimal.doubleValue() > 0) {
                        registeredEntity.setIsPay(1);
                    } else {
                        registeredEntity.setIsPay(0);
                    }
                }
            } else if ("个人".equals(type)) {
                //            registeredEntity = personalRegisterService.createPeispatient(registeredEntity);
                registeredEntity.setIsPay(1);
            }
            map.put(registeredEntity.getPatientCode(), registeredEntity);
            return new ResultVo("0", "成功", registeredEntity, true);
        } else {
            return new ResultVo("0", "成功", map.get(registeredEntity.getPatientCode()), true);
        }
    }


    public String checkPayWay(RegisteredEntity registeredEntity) {
        String patientCode = registeredEntity.getPatientCode();
        return registrationMachineMapper.getPayWayNameByPatientCode(patientCode);
    }


    public String checkPayWayByCode(String patientCode) {
        return registrationMachineMapper.getPayWayNameByPatientCode(patientCode);
    }

    private String getType(Peispatient peispatient) {
        String type = "";
        if (peispatient.getFUsecodehiden() == 0) {
//            个人
            type = "个人";
        } else {
//            团队
            type = "团检";
        }
        return type;
    }


    /**
     * 自助登记-打印
     *
     * @param registeredEntity
     * @return
     */
    @Override
    public Map<String, Object> print(RegisteredEntity registeredEntity) {
        Map<String, Object> map = new HashMap<>();
        String dydStyle = ReportConstants.DYDSTYLE;
        map.put("dydStyle", dydStyle);
        String cid = sysBranchMapper.getDefaultCid();
        map.put("cid", cid);
        String queueUrl = ReportConstants.QUEUE_URL;
        map.put("queueUrl", queueUrl);
        String barcodecountconfig = "3";
        String barcodecountConfig = ReportConstants.BARCODECOUNT;
        if (StringUtils.isNotEmpty(barcodecountConfig)) {
            barcodecountconfig = barcodecountConfig;
        }
        Integer barcode = getItemNumByPatientCode(registeredEntity.getPatientCode());
        if (barcode > Integer.parseInt(barcodecountconfig)) {
            barcodecountconfig = barcode.toString();
        }
        String barcodecount = barcodecountconfig;
        map.put("barcodecount", barcodecount);
        Peispatient patient = peispatientMapper.getByPatientCode(registeredEntity.getPatientCode());
        if (patient != null) {
            String patientname = patient.getPatientname();
            String orgName = patient.getOrgName();
            String sex = patient.getIdSex() == 0 ? "男" : "女";
            String phone = patient.getPhone();
            String idcardno = patient.getIdcardno();
            String id = patient.getId();
            map.put("patientname", patientname);
            map.put("orgName", orgName);
            map.put("sex", sex);
            map.put("phone", phone);
            map.put("idcardno", idcardno);
            map.put("id", id);
        }
        if (patient == null) {
            String errorMsg = "您输入的号码不存在！";
            map.put("errorMsg", errorMsg);
        } else if (patient.getFRegistered() != null && patient.getFRegistered() == 1) {
            String fcharged = "是";
            String errorMsg = "此号码已经登记，不可再次登记，请联系前台人员！";
            map.put("fcharged", fcharged);
            map.put("errorMsg", errorMsg);
        } else {
            //禁检
            if (null != patient.getFPaused() && 1 == patient.getFPaused()) {
                throw new ServiceException("此号码已被禁检！");
            }
            if (patient.getIdOrgreservationgroup() != null) {
                //体检者任务分组
                Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(patient.getIdOrgreservationgroup());
                if (group != null && group.getFGroupstarted() == 0 && group.getFGrouppaused() == 1) {
                    throw new ServiceException("此号码已被禁检！");
                }
            }

            //体检者收费项目表
            List<Peispatientfeeitem> unChargeds = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", patient.getPatientcode())
                    .eq("change_item", 0)
            );

            Double money = 0.0;//所有现金、未收费项目的钱
            if (unChargeds.size() > 0) {
                StringBuilder itemsStr = new StringBuilder();
                if ("true".equals(registeredEntity.getPaid())) {
                    for (int i = 0; i < 5; i++) {
                        for (Peispatientfeeitem feeitem : unChargeds) {
                            //统收不用判断（只有统收和现金两种）
                            if (!"1".equals(feeitem.getIdPayway())) {
                                if (feeitem.getFFeecharged() == null || feeitem.getFFeecharged() != 1) {
                                    if (i != 4) {
                                        unChargeds = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                                                .eq("idPatient", patient.getPatientcode()));
                                        money = 0.0;
                                        break;
                                    }
                                    itemsStr.append(feeitem.getExamfeeitemName() + "、");
                                    if (feeitem.getFactprice() != null) {
                                        money += feeitem.getFactprice();
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (Peispatientfeeitem feeitem : unChargeds) {
                        //统收不用判断
                        if (!"1".equals(feeitem.getIdPayway())) {
                            if (feeitem.getFFeecharged() == null || feeitem.getFFeecharged() != 1) {
                                itemsStr.append(feeitem.getExamfeeitemName() + "、");
                                if (feeitem.getFactprice() != null) {
                                    money += feeitem.getFactprice();
                                }
                            }
                        }
                    }
                }

                //微信商城不判断
                if (itemsStr.length() > 0
                        && (patient.getCountreportoccupation() == null
                        || patient.getCountreportoccupation() != 2)) {
                    String fcharged = "否";
                    String errorMsg = "存在没有缴费的项目：" + itemsStr.substring(0, itemsStr.length() - 1) + "。";
                    map.put("fcharged", fcharged);
                    map.put("errorMsg", errorMsg);
                } else {
                    dydStyle = ReportConstants.DYDSTYLE;
                    cid = sysBranchMapper.getDefaultCid();
                    String fcharged = "是";
                    map.put("dydStyle", dydStyle);
                    map.put("cid", cid);
                }
            } else {
                String fcharged = "否";
                String errorMsg = "此号码没有体检项目，请联系前台人员！";
                map.put("fcharged", fcharged);
                map.put("errorMsg", errorMsg);
            }
            map.put("money", money);
        }

        patient.setFRegistered(1);
        log.info("自助登记机设置体检者为已登记");
        patient.setDateregister(new Date());

        //目前自动登记机只能付统收的，现金的付款码暂时生成不了
        patient.setIdPayway("5");
        patient.setPayway("统收");
        SysUser user = null;

        //机器id
        String machineId = SecurityUtils.getUserNo();
        user = sysUserMapper.getUserByNo(machineId);
        patient.setDoctorreg(user == null ? "自助机" : user.getUserName());
        patient.setIdDoctorreg(machineId);
        log.info("自助登记机保存前的体检者:{}",patient);
        peispatientMapper.updateById(patient);


        return map;
    }


    public Integer getItemNumByPatientCode(String patientCode) {
        //查询收费项目数量
        Integer num = registrationMachineMapper.getItemNumByPatientCode(patientCode);
        while (num % 3 != 0) {
            ++num;
        }
        return (num / 3) + 1;
    }


    /**
     * 自助登记-完成
     *
     * @param patientcode
     * @return
     */
    @Override
    public Map<String, Object> complete(String patientcode) {
        Map<String, Object> map = new HashMap<>();
        String closeChongfa = ReportConstants.CLOSE_CHONGFA;
        String lisPacsUrl = ReportConstants.LIS_IP;
        map.put("lisPacsUrl", lisPacsUrl);
        map.put("userName", SecurityUtils.getUsername());
        map.put("closeChongfa", closeChongfa);
        map.put("patientcode", patientcode);
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientcode);
        if (peispatient != null) {
            map.put("shortcode", peispatient.getShortCode());
        } else {
            map.put("shortcode", "");
        }
        map.put("flag", 0);

        return map;
    }


    /**
     * 自助登记-打印导引单数据
     *
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> getBillModelData(ModelParam param) {
        List<String> ids = param.getIds();
        String cid = param.getCid();
        String model = param.getModel();
        List<Map<String, Object>> resultList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> errorMsg = new ArrayList<>();
        String[] tjlb = {"健康体检", "职业体检", "综合", "复查"};
        String[] zytjlb = {"上岗前", "在岗期间", "离岗时", "离岗后", "应急"};

        for (String patientId : ids) {
            Map<String, Object> map = new HashMap<>();
            Peispatient peispatient = peispatientMapper.getByPatientCode(patientId);
            if (peispatient == null) {
                continue;
            }
            // 1. 收费项目去重及价格校验
            handleFeeItemDeduplication(peispatient);

            // 2. 通知方式
            map.put("informWay", getInformWay(peispatient));

            // 3. 体检者基本信息
            fillPatientBasicInfo(map, peispatient, cid, dateFormat, tjlb, zytjlb);

            // 4. 收费项目分组
            List<List<Map<String, String>>> items = buildGroupedItems(peispatient, cid);
            map.put("items", items);
            if (CollectionUtil.isEmpty(items)) errorMsg.add("体检号" + peispatient.getPatientcode() + "收费项目为空!");

            // 5. 其他信息
            map.put("counts", getItemCounts(peispatient, cid));
            map.put("guidancenote", StringUtils.defaultIfBlank(peispatient.getGuidancenote(), ""));
            map.put("patientClass", getPatientClass(peispatient));
            fillQueueSystemInfo(map, peispatient, cid);

            resultList.add(map);

            // 6. 打印次数更新
            if ("1".equals(model)) {
                updateGuidePrintCount(peispatient);
            }
        }
        if (CollectionUtil.isNotEmpty(errorMsg)) {
            throw new ServiceException(String.join(",", errorMsg));
        }
        return resultList;
    }

    /** 收费项目去重及价格校验 */
    private void handleFeeItemDeduplication(Peispatient peispatient) {
        if (peispatientfeeitemService.isRepeat(peispatient.getPatientcode())) {
            log.info("收费项目重复，执行去重操作：{}", peispatient.getPatientcode());
            peispatientfeeitemService.deduplication(peispatient.getPatientcode());
            if (!createmealService.isConsistentPrice(peispatient.getPatientcode(), peispatient.getIdTjtc())) {
                orderService.recalculatePeiPrice(peispatient);
            }
        }
    }

    /** 获取通知方式 */
    private String getInformWay(Peispatient peispatient) {
        if (peispatient.getIdInformway() != null) {
            Notificationmethod method = notificationmethodMapper.getInfoById(peispatient.getIdInformway());
            if (method != null) {
                return method.getMethodName();
            }
        } else if (peispatient.getFUsecodehiden() != null && peispatient.getFUsecodehiden() == 0) {
            return "个检发";
        }
        return "";
    }

    /** 填充体检者基本信息 */
    private void fillPatientBasicInfo(Map<String, Object> map, Peispatient peispatient, String cid, SimpleDateFormat dateFormat, String[] tjlb, String[] zytjlb) {
        SysBranch sysBranch = sysBranchMapper.selectOne(new QueryWrapper<SysBranch>().eq("branch_id", cid));
        map.put("cid", ObjectUtils.isNotEmpty(sysBranch) ? sysBranch.getFzx() : cid);
        map.put("numorgresv", StringUtils.isNotEmpty(peispatient.getNumorgresv()) && !"-1".equals(peispatient.getNumorgresv()) ? peispatient.getNumorgresv() : "-1");
        map.put("patientCode", peispatient.getPatientcode());
        map.put("picture", peispatientPhotoService.getPicture(peispatient));
        map.put("depart", StringUtils.defaultIfBlank(peispatient.getOrgDepart(), ""));
        map.put("dydremark", peispatient.getGuidancenote());
        map.put("name", peispatient.getPatientname());
        map.put("sex", ObjectUtils.isEmpty(peispatient.getIdSex()) ? "" : peispatient.getIdSex() == 0 ? "男" : "女");
        map.put("age", peispatient.getAge() == null ? 0 : peispatient.getAge());
        map.put("idcardno", StringUtils.defaultIfBlank(peispatient.getIdcardno(), ""));
        map.put("medicaldate", peispatient.getDateregister() != null ? dateFormat.format(peispatient.getDateregister()) : dateFormat.format(new Date()));
        map.put("org", StringUtils.defaultIfBlank(peispatient.getOrgName(), ""));
        map.put("type", getExamType(peispatient, tjlb));
        map.put("orgDepart", peispatient.getOrgDepart());
        String medicalType = peispatient.getMedicaltype();
        if (medicalType != null && ToolUtil.isInteger(medicalType)) {
            int z = Integer.parseInt(medicalType);
            if (z >= 0 && z < zytjlb.length) {
                map.put("zytjlb", zytjlb[z]);
            }
        }
        // 获取领取名称
        List<String> tflist = registrationMachineMapper.getMethodName(peispatient.getIdInformway());
        if (CollectionUtil.isNotEmpty(tflist)) {
            map.put("delivery", tflist.get(0));
        }
        map.put("marriagec", peispatient.getIdMarriage() == null ? "" : peispatient.getIdMarriage());
        map.put("meal", peispatient.getExamsuiteName());
    }

    /** 获取体检类型描述 */
    private String getExamType(Peispatient peispatient, String[] tjlb) {
        if (peispatient.getIdExamtype() == null) return "";
        Integer idx = null;
        try { idx = Integer.valueOf(peispatient.getIdExamtype()); } catch (Exception ignored) {}
        if (idx == null || idx < 0 || idx >= tjlb.length) return "";
        if (peispatient.getCounterreportprinted() != null && peispatient.getCounterreportprinted() > 0) {
            return tjlb[idx] + "(" + peispatient.getCounterreportprinted() + ")";
        } else {
            return tjlb[idx];
        }
    }

    /** 构建分组后的收费项目 */
    private List<List<Map<String, String>>> buildGroupedItems(Peispatient peispatient, String cid) {
        String patientCode = peispatient.getPatientcode();
        String fieldName = "pitem.examfeeitem_name";
        List<Map<String, Object>> exams = registrationMachineMapper.getModelItems2(patientCode, cid, fieldName);
        // 如果项目不存在，就拉取线上数据或重新生成项目
        if (CollectionUtil.isEmpty(exams)) {
            orderService.pullOnlineData(patientCode,0);
            exams = registrationMachineMapper.getModelItems2(patientCode, cid, fieldName);
        }
        // 分组逻辑与原方法一致，略作结构优化
        LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>>> link1 = new LinkedHashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        boolean hasCcCq = false;
        for (Map<String, Object> exam : exams) {
            String cx = String.valueOf(exam.get("cx"));
            String ksId = exam.get("id_ks") == null ? "" : String.valueOf(exam.get("id_ks"));
            String yblxId = exam.get("yblxid") == null ? "" : String.valueOf(exam.get("yblxid"));
            String yblx = exam.get("yblx") == null ? "" : String.valueOf(exam.get("yblx"));
            String dydMemo = exam.get("dyd_memo") == null ? "" : String.valueOf(exam.get("dyd_memo"));
            String hisRoom = "";
            // CT序号逻辑
            if ("173".equals(ksId) && peispatient.getFRegistered() != null && peispatient.getFRegistered() == 1) {
                DateTime dateTime = DateUtil.beginOfDay(new Date());
                PeisDydCtSeq ctseq = peisDydCtSeqService.getByPatientcode(patientCode);
                Integer seq = (ctseq == null) ? (peisDydCtSeqService.getLastSeq(dateTime) + 1) : ctseq.getSeq();
                if (ctseq == null) peisDydCtSeqService.save(new PeisDydCtSeq(patientCode, seq));
                hisRoom = "<br/>" + seq;
            }
            // 彩超特殊处理
            if (ToolUtil.CC.equals(ksId)) {
                if ("0".equals(cx)) {
                    hasCcCq = true;
                } else if (hasCcCq) {
                    cx = "0";
                }
            }
            link1.computeIfAbsent(cx, k -> new LinkedHashMap<>());
            LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>> link2 = link1.get(cx);
            link2.computeIfAbsent(ksId, k -> new LinkedHashMap<>());
            LinkedHashMap<String, Map<String, String>> link3 = link2.get(ksId);
            Map<String, String> link4 = link3.computeIfAbsent(yblxId, k -> new HashMap<>());
            if (link4.isEmpty()) {
                counts.put(cx, counts.getOrDefault(cx, 0) + 1);
                link4.put("cx", "0".equals(cx) ? "餐前" : ("3".equals(cx) ? "" : "餐后"));
                link4.put("ks", (exam.get("ks") == null ? "" : String.valueOf(exam.get("ks"))) + hisRoom);
                link4.put("items", exam.get("itemname") == null ? "" : String.valueOf(exam.get("itemname")));
                link4.put("bz", exam.get("bz") == null ? "" : String.valueOf(exam.get("bz")));
                link4.put("yblx", yblx);
                link4.put("memo", dydMemo);
            } else {
                link4.put("items", link4.get("items") + "," + exam.get("itemname"));
                link4.put("bz", link4.get("bz") + (exam.get("bz") == null ? "" : ("".equals(link4.get("bz")) ? String.valueOf(exam.get("bz")) : ("," + exam.get("bz")))));
                link4.put("memo", dydMemo);
            }
        }
        // 转换为List<List<Map<String, String>>>结构
        List<List<Map<String, String>>> result = new ArrayList<>();
        for (LinkedHashMap<String, LinkedHashMap<String, Map<String, String>>> map2 : link1.values()) {
            for (LinkedHashMap<String, Map<String, String>> map3 : map2.values()) {
                result.add(new ArrayList<>(map3.values()));
            }
        }
        return result;
    }

    /** 获取分组项目数量 */
    private Map<String, Integer> getItemCounts(Peispatient peispatient, String cid) {
        // 复用分组逻辑，返回分组数量
        String patientCode = peispatient.getPatientcode();
        String fieldName = "pitem.examfeeitem_name";
        List<Map<String, Object>> exams = registrationMachineMapper.getModelItems2(patientCode, cid, fieldName);
        Map<String, Integer> counts = new HashMap<>();
        boolean hasCcCq = false;
        for (Map<String, Object> exam : exams) {
            String cx = String.valueOf(exam.get("cx"));
            String ksId = exam.get("id_ks") == null ? "" : String.valueOf(exam.get("id_ks"));
            if (ToolUtil.CC.equals(ksId)) {
                if ("0".equals(cx)) {
                    hasCcCq = true;
                } else if (hasCcCq) {
                    cx = "0";
                }
            }
            counts.put(cx, counts.getOrDefault(cx, 0) + 1);
        }
        return counts;
    }

    /** 获取会员等级 */
    private String getPatientClass(Peispatient peispatient) {
        String idPatientClass = peispatient.getIdPatientclass();
        if (StringUtils.isNotEmpty(idPatientClass)) {
            Patienttype ptype = patienttypeMapper.getInfoById(idPatientClass);
            if (ptype != null) {
                return ptype.getPatientName();
            }
        }
        return "普通会员";
    }

    /** 填充排队系统相关信息 */
    private void fillQueueSystemInfo(Map<String, Object> map, Peispatient peispatient, String cid) {
        QueueConfig queueConfig = iSysConfigService.getSysConfigObject(Constants.QUEUE_CONFIG, QueueConfig.class);
        String url = queueConfig.getQrCodeUrl();
        if (StringUtils.isNotEmpty(queueConfig.getLineUpOpen())) {
            map.put("lineUpOpen", queueConfig.getLineUpOpen());
            map.put("imagePath", queueConfig.getImagePath());
            if ("1".equals(queueConfig.getLineUpOpen())) {
                String oldSysPrefix = queueConfig.getOldSysPrefix();
                String patientCode = peispatient.getPatientcode();
                if (StrUtil.isNotEmpty(oldSysPrefix)) {
                    map.put("QUEUEING_SYSTEM_URL", url + oldSysPrefix + patientCode.substring(oldSysPrefix.length()));
                } else {
                    map.put("QUEUEING_SYSTEM_URL", url + patientCode);
                }
                map.put("lineUpRemarks", queueConfig.getLineUpRemarks());
            }
        }
    }

    /** 更新导引单打印次数 */
    private void updateGuidePrintCount(Peispatient peispatient) {
        peispatient.setGuideSignleCount(peispatient.getGuideSignleCount() == null ? 1 : (peispatient.getGuideSignleCount() + 1));
        peispatientMapper.updateById(peispatient);
    }


    /**
     * 引导单模板、条码展示页数据
     *
     * @param cid
     * @return
     */
    @Override
    public Map<String, Object> all(String cid) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String orientation = ReportConstants.BARCODECOUNT;
        map.put("ORIENTATION", orientation);
        map.put("TM", ReportConstants.TM);// 条码
        map.put("DYD", ReportConstants.DYD);// 导引单
        map.put("JFD", ReportConstants.JFD);// 缴费单
        SysBranch branch = sysBranchMapper.selectOne(new QueryWrapper<SysBranch>().eq("branch_id", cid));
        String address = null == branch ? "" : branch.getAddress();
        map.put("ADDRESS", address);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = format.format(date);
        map.put("TODAY", today);
        String barcount = ReportConstants.BARCODECOUNT;
        map.put("BARCOUNT", ObjectUtils.isEmpty(barcount) ? "1" : barcount);

        map.put("hideProcess", ReportConstants.hideProcess);
        map.put("qrCodePath", ReportConstants.qrCodePath);
        map.put("yxCodePath", ReportConstants.yxCodePath);
        map.put("dbCodePath", ReportConstants.dbCodePath);
        map.put("YTYYPHONE", ReportConstants.YTYYPHONE);
        map.put("YTBGPHONE", ReportConstants.YTBGPHONE);
        map.put("YTTSPHONE", ReportConstants.YTTSPHONE);

        String barcodeCountSelectData = ToolUtil.getBarcodeCountSelectData();
        map.put("barcodeCountSelectData", barcodeCountSelectData);
        if ("vertical".equals(orientation)) {
            map.put("return", "allc");
        } else if ("verticalYanTai".equals(orientation)) {
            map.put("return", "all3");
        } else {
            map.put("return", "all2");
        }
        return map;
    }


    /**
     * 检查金额
     *
     * @param patientCode
     * @return
     */
    @Override
    public ResultVo checkAmount(String patientCode) {
        ResultVo object = new ResultVo();
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        //判断该分中心可以登记该体检类型的
        Boolean examtypeOpen = iSysConfigService.getExamtypeOpen(peispatient.getIdExamtype());
        if (!examtypeOpen){
            throw new ServiceException("该中心未开启登记该体检类型！");
        }
        //查询是否在别的区已经登记
        String s = "";
        try {
            String url = iSysConfigService.getDomain().getPlatformDomain() + Constants.OTHER_IS_REGISTERED;
            s = HttpUtils.sendGet(url,"id="+peispatient.getId()+ "&fzx="+ iSysBranchService.getDefaultBranch().getBranchId());
            log.info("查询是否在别的区已经登记url:{},返回结果是：{}",url,s);
        }catch (Exception e){
            log.error("查询是否在别的区已经登记失败！体检号：{},错误信息：{}",peispatient.getPatientcode(),e.getMessage());
        }
        if (StringUtils.isNotEmpty(s)) {
            R responseEntity = JSONUtil.toBean(s, R.class);
            if (200 != responseEntity.getCode()) {
                throw new ServiceException(responseEntity.getMsg());
            }
        }

        //能进来的只有团检creatmeal的
        if (StringUtils.isNotEmpty(peispatient.getIdTjtc())){
            long pfCount = peispatientfeeitemService.count(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", peispatient.getPatientcode())
                    .isNull("f_transferedhl7").eq("sfjj", 0)
                    .eq("f_giveup", 0).eq("change_item", 0));
            Long miCount = mealanditemMapper.selectCount(new LambdaQueryWrapper<Mealanditem>().eq(Mealanditem::getTcid, peispatient.getIdTjtc()));
            if (pfCount!=miCount){
                throw new ServiceException("请去前台确认项目数量！");
            }
        }



        double v = peispatient.getTsLimit() == null ? 0 : peispatient.getTsLimit();
        double chargeAmount = 0d;
        //体检者缴费表
        List<Peispatientcharge> peispatientCharges = peispatientchargeMapper.selectList(new QueryWrapper<Peispatientcharge>().eq("patientcode", patientCode));
        if (peispatientCharges != null && peispatientCharges.size() > 0) {
            for (Peispatientcharge peispatientCharge : peispatientCharges) {
                Double moneyamountpaid = peispatientCharge.getMoneyamountpaid();
                chargeAmount += moneyamountpaid;
            }
        }
        if (chargeAmount > 0 && chargeAmount >= v) {
            object.setCode("0");
        } else {
            if (v > 0) {
                Double moneyamount = ObjectUtils.isNotEmpty(peispatient.getMoneyamount())?peispatient.getMoneyamount():0.0;
                double v1 = moneyamount - v;
                if (v1 > 0) {
                    object.setCode("1");
                    object.setMessage("您仍需现场支付" + BigDecimal.valueOf(v1).setScale(2, BigDecimal.ROUND_HALF_UP) + "元，请到导检台窗口人工办理缴费等业务。");
                } else {
                    object.setCode("0");
                }
            } else {
                object.setCode("0");
            }
        }
        return object;
    }


    /**
     * 分组确认项目
     *
     * @param registeredEntity
     * @return
     */
    @Override
    public ResultVo groupConfirmItems(RegisteredEntity registeredEntity) {
        String patientCode = registeredEntity.getPatientCode();
        //包id
        String packageId = registeredEntity.getPackageId();
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        Createmeal createMeal = createmealMapper.getInfoById(packageId);
        //普通套餐与收费项目关联表
        //是否必选
        List<Mealanditem> requiredItems = mealanditemMapper.selectList(new QueryWrapper<Mealanditem>()
                .eq("tcid", packageId).eq("sfbx", "0"));
        List<Mealanditem> optionalItems = mealanditemMapper.selectList(new QueryWrapper<Mealanditem>()
                .eq("tcid", packageId).eq("sfbx", "1")
                .in("sfxmid", registeredEntity.getItemIds().split(",")));
        //保存项目状态及价格
        saveOrUpdateItem(peispatient, createMeal, registeredEntity, optionalItems, requiredItems);

        registeredEntity.setPatientCode(patientCode);
        return new ResultVo("0", "成功", registeredEntity, true);
    }


    /**
     * 保存或修改项目
     *
     * @param pei
     * @param createMeal
     * @param registeredEntity
     * @param optionalItems
     * @param requiredItems
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdateItem(Peispatient pei, Createmeal createMeal, RegisteredEntity registeredEntity, List<Mealanditem> optionalItems, List<Mealanditem> requiredItems) {
        String patientCode = pei.getPatientcode();
        pei.setModifydate(new Date());
        ArrayList<String> strr = new ArrayList<String>();
        for (Mealanditem mealAndItem : optionalItems) {
            String itemsId = mealAndItem.getSfxmid();
            strr.add(itemsId);
        }
        String[] str = new String[strr.size()];
        str = strr.toArray(str);
        String text = compareItemsToExam(str);
        if (!StringUtils.isBlank(text)) {
            return "fail:" + text;
        }

        int isataus = -1;
        // 存在套餐
        Double tcPrice = 0d;
        Double yuanshi = 0d;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        double ys = 0.0;//应收（所有优惠价合计）
        double yj = 0.0;//原价（所有原价合计）
        Date checkInTime = new Date();
        for (Mealanditem mealAndItem : optionalItems) {
            Items items = itemsMapper.getInfoById(mealAndItem.getSfxmid());
            Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
            peispatientfeeitem.setIdPatient(pei.getPatientcode());
            peispatientfeeitem.setIdExamfeeitem(items.getId());
            peispatientfeeitem.setExamfeeitemName(items.getExamfeeitemName());
            peispatientfeeitem.setPrice(items.getUnitprice());
            peispatientfeeitem.setCount(1);
            peispatientfeeitem.setRegistertime(checkInTime);
            peispatientfeeitem.setSfjx(0);
            peispatientfeeitem.setFRegistered(1);
            peispatientfeeitem.setChangeItem(0);
            peispatientfeeitem.setFMarkFeereturn(0);

            // 性别
            peispatientfeeitem.setFFeechargedinttrans(null == pei.getIdSex() ? 0 : Integer.valueOf(pei.getIdSex()));
            // 弃检
            peispatientfeeitem.setFGiveup(0);
            // 迟检
            peispatientfeeitem.setFDelayed(0);
            // 体检号
            peispatientfeeitem.setIdPatient(patientCode);
            peispatientfeeitem.setShortCode(CodeUtil.getShortCodeByLong(pei.getPatientcode()));
            peispatientfeeitem.setBxcount(0);

            // 计算实际金额
            // 不是最小套餐，实际金额==优惠价格
            Double shiji = 0d;
            if (yuanshi != 0) {
                shiji = Double.valueOf(decimalFormat.format(tcPrice / yuanshi * peispatientfeeitem.getPrice()));
            }
            if (null == peispatientfeeitem.getIsMintc() || 0 == peispatientfeeitem.getIsMintc()) {
                shiji = peispatientfeeitem.getFactprice();
            }

            peispatientfeeitem.setActualprice(shiji);
            if (null != pei.getFRegistered() && pei.getFRegistered() == 1) {
                // 已登记，增项
                isataus = 1;
            }
            peispatientfeeitemMapper.insert(peispatientfeeitem);
        }
        //体检者费用主表
        PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientCode);
        if (pcm == null) {
            Double zhjg = createMeal.getZhjg();
            peispatientChargeMainMapper.insert(new PeispatientChargeMain(
                    SecurityUtils.getUsername() + "保存于" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ";"
                    , zhjg
                    , zhjg
                    , pei.getPatientcode()));
        } else {
            Double zhjg = createMeal.getZhjg();
            pcm.setMoneyamount(zhjg);
            pcm.setMoneyamountpaid(zhjg);
            peispatientChargeMainMapper.updateById(pcm);
        }


        // 检查剩余收费项目是否可以分拣完成
        checkFj(pei);


        return "success" + " sataus:" + isataus;
    }


    public String compareItemsToExam(String[] itemId) {
        if (itemId.length == 0) {
            return "";
        }
        StringBuffer text = new StringBuffer();
        Map<String, String> map = new HashMap<String, String>();
        //检查项目收费项目关联表
        List<InspectCharge> inspectCharges = inspectChargeMapper.selectList(new QueryWrapper<InspectCharge>()
                .in("charge_id", itemId).eq("is_delete", 0));
        for (InspectCharge inspectCharge : inspectCharges) {
            //查询重复项目
            List<String> items = registrationMachineMapper.getRepeatItems(inspectCharge.getInspectId(), itemId);
            if (items.size() <= 1) {
                continue;
            }
            StringBuffer str2 = new StringBuffer();
            for (int i = 0; i < items.size(); i++) {
                //检查项目
                Items items2 = itemsMapper.selectOne(new QueryWrapper<Items>()
                        .eq("id", StringUtils.isBlank(items.get(i).toString()) ? "" : items.get(i).toString())
                        .eq("is_delete", 0));
                if (null != items2) {
                    str2.append(items2.getExamfeeitemName() + "、");
                }
            }
            String res = str2.toString().substring(0, str2.toString().length() - 1);
            //检查项目表
            Basexamltem exanm = basexamltemMapper.selectOne(new QueryWrapper<Basexamltem>()
                    .eq("id", inspectCharge.getInspectId()).eq("is_delete", 0));
            String jcxmName = "";
            if (null != exanm) {
                jcxmName = exanm.getExamitemName();
            }
            if (!StringUtils.isBlank(map.get(res))) {
                jcxmName = map.get(res) + "、" + jcxmName;
            }
            map.put(res, jcxmName);
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            text.append("<font color='red'>★</font>收费项目:<font color='red'>" + entry.getKey() + "</font>存在相同的检查项目: <font color='red'>" + entry.getValue() + "</font><br/>");
        }

        // 不存在重复项
        if (StringUtils.isBlank(text.toString())) {
            return "";
        } else {
            return text.toString();
        }
    }


    //检查剩余收费项目是否可以分拣完成
    public void checkFj(Peispatient pei) {
        // 体检者不存在
        if (null == pei) {
            return;
        }
        List<String> depIds = sysDeptMapper.getFunctionKsIds(1);//功能科室
        // 查找未检、未弃检、未退项、存在科室的收费项目数量
        List<Peispatientfeeitem> peispatientfeeitems = depIds.size() == 0 ? new ArrayList<Peispatientfeeitem>()
                : peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .in("id_ks", depIds)
                .eq("id_patient", pei.getPatientcode())
                .eq("change_item", 0)
                .eq("f_giveup", 0)
                .eq("sfjj", 0)
                .isNotNull("id_ks")//不关联科室的（收费项目只能管理功能科室）
                .isNull("f_transferedhl7")// 不存在补检
                .eq("f_examinated", 0));
        if (peispatientfeeitems.size() == 0) {
            pei.setFReadytofinal(1);
            setScbs(pei, 0);
            pei.setReadytofinalDate(new Date());
            // 无关联科室已检
            List<Peispatientfeeitem> others = registrationMachineMapper.noRelatedDepartments(pei.getPatientcode());
            for (Peispatientfeeitem peispatientfeeitem : others) {
                peispatientfeeitem.setFExaminated(1);//设置未关联科室项目为已检,反审核时不改回去
                // 更新收费实体类
                peispatientfeeitemMapper.updateById(peispatientfeeitem);
            }
        } else {
            pei.setFReadytofinal(0);
            setScbs(pei, 0);
        }
        peispatientMapper.updateById(pei);
    }


    /**
     * 设置上传标志
     *
     * @param patient
     * @param scbs
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public PeisState setScbs(Peispatient patient, int scbs) {
        if (StringUtils.isEmpty(patient.getPatientcode())) {
            return null;
        }
        //体检者上传状态
        PeisState ps = peisStateMapper.selectOne(new QueryWrapper<PeisState>().eq("patientcode", patient.getPatientcode()));
        if (ps == null) {
            ps = new PeisState(patient.getPatientcode());
            ps.setScbs(scbs);
            peisStateMapper.insert(ps);
        } else {
            ps.setScbs(scbs);
            peisStateMapper.updateById(ps);
        }
        return ps;
    }


    /**
     * 获取项目
     *
     * @param registeredEntity
     * @return
     */
    @Override
    public PackageItemEntity getMedicalExaminationItem(RegisteredEntity registeredEntity) {
        PackageItemEntity packageItemEntity = new PackageItemEntity();
        String patientCode = registeredEntity.getPatientCode();
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        String idTjtc = peispatient.getIdTjtc();
        Createmeal createMeal = createmealMapper.getInfoById(idTjtc);
        List<Mealanditem> list = mealanditemMapper.selectList(new QueryWrapper<Mealanditem>().eq("tcid", idTjtc));
        setItems(packageItemEntity, list, createMeal);
        return packageItemEntity;
    }


    protected void setItems(PackageItemEntity packageItemEntity, List<Mealanditem> mealAndItems, Createmeal meal) {
        packageItemEntity.setPackageId(meal.getId());
        packageItemEntity.setPackageType(String.valueOf(meal.getSfzx()));
        packageItemEntity.setPackageName(meal.getTjtcmc());
        packageItemEntity.setBetweenSelectNum(meal.getKxsl());
        packageItemEntity.setGroupSelectNum(1);
        HashSet<String> groupItemIds = new HashSet<>();
        HashSet<String> betweenItemIds = new HashSet<>();
        for (Mealanditem mealAndItem : mealAndItems) {
            if (ObjectUtils.isNotEmpty(mealAndItem.getGroupType()) && 0 == mealAndItem.getGroupType()) {
                groupItemIds.add(String.valueOf(mealAndItem.getItemGroup()));
            } else if (ObjectUtils.isNotEmpty(mealAndItem.getGroupType()) && 1 == mealAndItem.getGroupType()) {
                betweenItemIds.add(String.valueOf(mealAndItem.getItemGroup()));
            } else {

            }
        }
        ArrayList<PackageItemEntity.GroupItems> groupItems = new ArrayList<>();
        for (String groupItemId : groupItemIds) {
            PackageItemEntity.GroupItems items = new PackageItemEntity.GroupItems();
            items.setGroupSelectNum(1);
            items.setGroup(groupItemId);
            ArrayList<PackageItemEntity.Item> items1 = new ArrayList<>();
            for (Mealanditem mealAndItem : mealAndItems) {
                if (mealAndItem.getItemGroup().toString().equals(groupItemId)) {
                    PackageItemEntity.Item item = new PackageItemEntity.Item();
                    item.setGroup(groupItemId);
                    item.setItemId(mealAndItem.getSfxmid());
                    item.setItemName(itemsMapper.getInfoById(mealAndItem.getSfxmid()).getExamfeeitemName());
                    item.setDescription("");
                    items1.add(item);
                }
            }
            items.setGroupDescription("以下项目请选择" + meal.getKxsl() + "个");
            items.setItems(items1);
            groupItems.add(items);
        }
        packageItemEntity.setGroupItems(groupItems);
        ArrayList<String> groupIds = new ArrayList<>();
        for (PackageItemEntity.GroupItems groupItem : packageItemEntity.getGroupItems()) {
            String group = groupItem.getGroup();
            groupIds.add(group);
        }
        packageItemEntity.setGroupIds(groupIds);
        ArrayList<PackageItemEntity.BetweenItems> betweenItems = new ArrayList<>();
        for (String betweenItemId : betweenItemIds) {
            PackageItemEntity.BetweenItems items = new PackageItemEntity.BetweenItems();
            items.setGroup(betweenItemId);
            ArrayList<PackageItemEntity.Item> list = new ArrayList<>();
            for (Mealanditem mealAndItem : mealAndItems) {
                if (mealAndItem.getItemGroup().toString().equals(betweenItemId)) {
                    PackageItemEntity.Item item = new PackageItemEntity.Item();
                    item.setGroup(betweenItemId);
                    item.setItemId(mealAndItem.getSfxmid());
                    item.setItemName(itemsMapper.getInfoById(mealAndItem.getSfxmid()).getExamfeeitemName());
                    item.setDescription("");
                    list.add(item);
                }
            }
            items.setItems(list);
            betweenItems.add(items);
        }
        packageItemEntity.setBetweenItems(betweenItems);
//        if (meal.getSfzx().equals(0)) {
        packageItemEntity.setBetweenItemsDescription("请从以下分组项目中选择" + meal.getKxsl() + "组");
//        }
    }


    /**
     * 自助登记-个人信息
     * @param registeredEntity
     * @return
     */
    @Override
    public Peispatient registerInfo(RegisteredEntity registeredEntity) {
        String patientCode = updatePatientInfoByPatientCode(registeredEntity, registeredEntity.getPatientCode());
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        //判断收费项目是否重复
        if (peispatientfeeitemService.isRepeat(peispatient.getPatientcode())){
            //执行去重
            log.info("收费项目重复，执行去重操作：{}", peispatient.getPatientcode());
            peispatientfeeitemService.deduplication(peispatient.getPatientcode());
            //判断套餐价格和收费项目的总金额是否一致
            if (!createmealService.isConsistentPrice(peispatient.getPatientcode(),peispatient.getIdTjtc())){
                //重新计算价格
                orderService.recalculatePeiPrice(peispatient);
            }
        }
        return peispatient;
    }
}

