package com.center.medical.reception.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.PeispatientchargeDto;
import com.center.medical.bean.enums.*;
import com.center.medical.bean.event.*;
import com.center.medical.bean.model.*;
import com.center.medical.bean.param.CardConsumeParam;
import com.center.medical.bean.param.OldFamilyConParam;
import com.center.medical.bean.param.SuiXingTradeRefundParam;
import com.center.medical.bean.param.TongLianRefundParam;
import com.center.medical.common.config.SmsConfig;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysConfig;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.*;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.sms.SDKTestSendTemplateSMS;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.dao.*;
import com.center.medical.dao.*;
import com.center.medical.data.service.HarmService;
import com.center.medical.data.service.InspectChargeService;
import com.center.medical.data.service.ItemsService;
import com.center.medical.finance.service.PhysicalCheckoutService;
import com.center.medical.member.bean.model.VisitMain;
import com.center.medical.member.service.VisitMainService;
import com.center.medical.reception.bean.dto.*;
import com.center.medical.reception.bean.param.*;
import com.center.medical.reception.bean.vo.*;
import com.center.medical.reception.dao.RegisterMapper;
import com.center.medical.reception.dao.ReviewMapper;
import com.center.medical.reception.service.ItemListService;
import com.center.medical.reception.service.OrderService;
import com.center.medical.reception.service.RegisterService;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.dao.*;
import com.center.medical.sellcrm.service.*;
import com.center.medical.service.*;
import com.center.medical.sync.bean.dto.SyncDataDto;
import com.center.medical.sync.service.SyncOnlineService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 前台-登记管理业务实现
 *
 * @author 路飞船长
 * @since 2022-11-22 20:52:26
 */
@Slf4j
@Service("registerService")
@RequiredArgsConstructor
public class RegisterServiceImpl extends ServiceImpl<RegisterMapper, Peispatient> implements RegisterService {

    private final RegisterMapper registerMapper;
    private final SysBranchMapper sysBranchMapper;
    private final MapperFacade mapperFacade;
    private final PeispatientChargeMainMapper peispatientChargeMainMapper;
    private final PeispatientChargeMainService peispatientChargeMainService;
    private final PeispatientPhotoMapper peispatientPhotoMapper;
    private final ISysBranchService iSysBranchService;
    private final CreatemealMapper createmealMapper;
    private final CreatemealService createmealService;
    private final CreatecomboMapper createcomboMapper;
    private final ISysUserService iSysUserService;
    private final CreateorderMapper createorderMapper;
    private final OrderService orderService;
    private final PeisorgreservationMapper peisorgreservationMapper;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final PeispatientarchiveService peispatientarchiveService;
    private final PeisStateService peisStateService;
    private final AreaMapper areaMapper;
    private final PeispatientChargeRecordMapper peispatientChargeRecordMapper;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final ISysConfigService iSysConfigService;
    private final SystemConfig systemConfig;
    private final ReservationService reservationService;
    private final VisitMainService visitMainService;
    private final FailTotalVisitService failTotalVisitService;
    private final InspectChargeService inspectChargeService;
    private final SysDeptMapper sysDeptMapper;
    private final HandleNewProjectsService handleNewProjectsService;
    private final TempFeeitemService tempFeeitemService;
    private final PeispatientchargeService peispatientchargeService;
    private final HarmMapper harmMapper;
    private final ItemsService itemsService;
    private final MealanditemService mealanditemService;
    private final ComboanditemService comboanditemService;
    private final PeispatientMapper peispatientMapper;
    private final PeispatientchargeMapper peispatientchargeMapper;
    private final BaseGuideMealMapper baseGuideMealMapper;
    private final PeispatienthistoryMapper peispatienthistoryMapper;
    private final PeispatientPhotoService peispatientPhotoService;
    private final PhysicalCheckoutService physicalCheckoutService;
    private final ComboexamitemMapper comboexamitemMapper;
    private final BaseWorktypeMapper baseWorktypeMapper;
    private final ItemListService itemListService;
    private final TempFeeitemMapper tempFeeitemMapper;
    private final ISysConfigService sysConfigService;
    private final ReviewMapper reviewMapper;
    private final DictpaywayMapper dictpaywayMapper;
    private final PeispatientService peispatientService;
    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final ItemsMapper itemsMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ShortmessageMapper shortmessageMapper;
    private final SmsRecordMapper smsRecordMapper;
    private final SmsRecordService smsRecordService;
    private final  Snowflake snowflake;
    private final SyncOnlineService syncOnlineService;
    private final CodeCorrespondingService codeCorrespondingService;
    private String ptcodeFrom;
    private final HarmService harmService;
    private final HarmAndFzxService harmAndFzxService;
    private final MealandfzxService mealandfzxService;
    private final ComboandfzxService comboandfzxService;
    private final SysUserMapper sysUserMapper;
    private final WzZysService wzZysService;

    /**
     * 分页查询登记信息列表
     *
     * @param page  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @Override
    public IPage<RegisterVo> getPage(PageParam<Peispatient> page, RegisterParam param) {
        // TODO: wait 区分线上线下时,把xml中的关于jm的注释解开,p.patientbizno IS NOT NULL线上，p.patientbizno IS NULL 线下
        String jm = "";
        if (Objects.nonNull(param.getAutoFill()) && param.getAutoFill()) {
            if (StringUtils.isNotBlank(param.getJm())) {
                //线上登记
                jm = param.getJm();
            } else {
                //线下登记
                SysBranch branch = sysBranchMapper.selectOne(new LambdaQueryWrapper<SysBranch>().eq(SysBranch::getIsDefault, 1));
                jm = branch.getJm();
            }
        }
        if (StringUtils.isNotBlank(param.getPtcodeFrom()) && StringUtils.isNotBlank(param.getPtcodeTo())) {
            ptcodeFrom = "";
            String ptcodeTo = "";
            if (StringUtils.isNotBlank(param.getPtcodeFrom())) {
                ptcodeFrom = param.getPtcodeFrom().trim();
                if (StringUtils.isNotBlank(jm) && ptcodeFrom.length() != 12) {
                    ptcodeFrom = jm + String.format("%10s", ptcodeFrom).replace(" ", "0");
                }
            }
            if (StringUtils.isNotBlank(param.getPtcodeTo())) {
                ptcodeTo = param.getPtcodeTo().trim();
                if (StringUtils.isNotBlank(jm) && ptcodeTo.length() != 12) {
                    ptcodeTo = jm + String.format("%10s", ptcodeTo).replace(" ", "0");
                }
            }
            param.setPtcodeFrom(ptcodeFrom);
            param.setPtcodeTo(ptcodeTo);
        }
        Boolean greatLeader = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (greatLeader){
            //决策管理看全部
        }else if (SecurityUtils.isLeader()){
            // 领导查看下级的数据
            List<String> lowerLevelIds = sysUserMapper.getLowerLevelId(SecurityUtils.getUserNo());
            lowerLevelIds.add(SecurityUtils.getUserNo());
            param.setLowerLevelIds(lowerLevelIds);
        }else {
            String userNo = SecurityUtils.getUserNo();
            param.setUserNo(userNo);
            param.setUserName(SecurityUtils.getUsername());
        }
        //设置属性
        IPage<RegisterVo> iPage = registerMapper.getPage(page, param);
        List<RegisterVo> records = iPage.getRecords();
        int i = 1;
        for (RegisterVo record : records) {
            int groupLimit = (record.getFGroupstarted() == null || record.getFGrouppaused() == null) ? 0 : (record.getFGroupstarted() == 1
                    && record.getFGrouppaused() == 0) ? 0 : 1;
            int pLimit = null == record.getFPaused() ? 0 : record.getFPaused();
            // 0:反禁检1:禁检
            record.setIspaused((groupLimit == 1 || pLimit == 1) ? 1 : 0);
            if (ObjectUtils.isEmpty(record.getOrderNum())) {
                record.setOrderNum("-1");
            }
            //设置序号
            int current = page.getCurrent() == 0 ? 0 : Math.toIntExact(page.getSize() * (page.getCurrent() - 1));
            record.setRownum(current + i);
            i++;
        }
        iPage.setRecords(records);
        return iPage;
    }

    /**
     * 前台-登记管理-新增登记
     *
     * @param registerDto    登记信息
     * @param intReservation 0 预约 1登记 2 保存
     * @return 新增结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RegisterResultVo saOrUp(RegisterDto registerDto, Integer intReservation) {
        log.info("前台-登记管理-新增登记.saOrUp.registerDto={}、intReservation={}", JSONUtil.toJsonStr(registerDto), intReservation);
        //去除名字中的所有空格和制表符
        registerDto.setPatientname(registerDto.getPatientname().replaceAll("[\\s\\t]", ""));
        registerDto.setIdcardno(registerDto.getIdcardno().replaceAll("[\\s\\t]", ""));
        Date now = new Date();
        SysUser admin = SecurityUtils.getLoginUser().getUser();
        //数据转换
        Peispatient peispatient = mapperFacade.map(registerDto, Peispatient.class);
        //分中心简码
        String fzxjm = StringUtils.isBlank(ZhongkangConfig.getFzxjm()) ? iSysBranchService.getBranchPrefix() : ZhongkangConfig.getFzxjm();

        //登记后体检号会变,如果销售线上没刷新，还是用app开头的体检号查询，会造成覆盖，所以在此处加校验
        Peispatient oldPei = peispatientService.getInfoById(peispatient.getId());
        if (ObjectUtils.isNotEmpty(oldPei) && !oldPei.getPatientcode().equals(peispatient.getPatientcode())){
            throw new ServiceException("信息有变动，请刷新!");
        }

        //套餐及分中心校验
        if (StringUtils.isNotEmpty(peispatient.getIdTjtc()) && StringUtils.isNotEmpty(peispatient.getHospitalcode())){
            long mealAndFzxCount = mealandfzxService.count(new LambdaQueryWrapper<Mealandfzx>()
                    .eq(Mealandfzx::getTcid, peispatient.getIdTjtc()).eq(Mealandfzx::getFzxid, peispatient.getHospitalcode()));
            if (mealAndFzxCount == 0){
                long comboAndFzxCount = comboandfzxService.count(new LambdaQueryWrapper<Comboandfzx>()
                        .eq(Comboandfzx::getTcid, peispatient.getIdTjtc()).eq(Comboandfzx::getFzxid, peispatient.getHospitalcode()));
                if (comboAndFzxCount == 0){
                    throw new ServiceException("该套餐未关联该分中心!");
                }
            }
        }

        //如果已登记,就不能更改登记人
        if (registerDto.getFRegistered() == 1) {
            peispatient.setIdDoctorreg(null);
            peispatient.setDoctorreg(null);
        }



        //将身份证号转为大写
        if (StringUtils.isNotEmpty(peispatient.getIdcardno())) {
            // TODO wait 等待完善其他证件号的校验
            switch (peispatient.getCountreportoccupationxml()) {
                case 1:
                    if (!IdcardUtil.isValidCard(peispatient.getIdcardno())) {
                        throw new ServiceException("身份证号输入不合法");
                    }
                    break;
                case 2:
                    if (peispatient.getIdcardno().length()<7) {
                        throw new ServiceException("护照号长度应大于7位!");
                    }
                    break;
                default:

            }

            peispatient.setIdcardno(peispatient.getIdcardno().toUpperCase());
        }

        // SABC等级验证,null的不会更新
        if (StringUtils.isNotEmpty(peispatient.getSabc())) {
            peispatient.setSabc(ToolUtil.validateSABC(peispatient.getSabc()));
        }


        String patientcode = peispatient.getPatientcode();
        String oldPatientcode = peispatient.getPatientcode();
        PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new LambdaQueryWrapper<PeispatientChargeMain>().eq(PeispatientChargeMain::getPatientcode, patientcode));
        Long newVersion = null;
        PeispatientPhoto photo = null;
        if (StringUtils.isNotEmpty(patientcode)) {
            photo = peispatientPhotoMapper.getByPatientCode(patientcode);
            log.info("保存或登记查询图片,体检号:{},结果:{}",patientcode,ObjectUtils.isNotEmpty(photo)?"存在":"不存在！");
            Long version = registerDto.getVersion();
//            log.info("version:{}、{}、{}、{}、{}", version, Objects.nonNull(pcm)?pcm.getVersion():"", !Objects.equals(version, pcm.getVersion()), (Objects.isNull(version) && Objects.nonNull(pcm) && Objects.nonNull(pcm.getVersion())),
//                    (Objects.nonNull(version) && Objects.nonNull(pcm) && Objects.nonNull(pcm.getVersion()) && version != pcm.getVersion()));
            if ((Objects.isNull(version) && Objects.nonNull(pcm) && Objects.nonNull(pcm.getVersion())) ||
                    (Objects.nonNull(version) && Objects.nonNull(pcm) && Objects.nonNull(pcm.getVersion()) && !Objects.equals(version, pcm.getVersion()))) {
                throw new ServiceException("信息有变动，请刷新!");
            }
        }
        SysBranch baranch = iSysBranchService.getDefaultBranch();
        if (StringUtils.isBlank(peispatient.getHospitalcode())) {
            peispatient.setHospitalcode(baranch.getBranchId());
        }
        peispatient.setFPaused(0);
        // 登记员名称
        peispatient.setDoctorreg(admin.getUserName());

        // 套餐名称
        if (StringUtils.isNotBlank(registerDto.getIdTjtc())) {
            Createmeal createMeal = createmealMapper.getInfoById(peispatient.getIdTjtc());
            String tcName = "";
            if (Objects.nonNull(createMeal)) {
                tcName = createMeal.getTjtcmc();
            } else {
                Createcombo createCombo = createcomboMapper.getInfoById(peispatient.getIdTjtc());
                if (Objects.nonNull(createCombo))
                    tcName = createCombo.getTjtcmc();
            }
            peispatient.setExamsuiteName(tcName);
        }

        // 开单医师
        SysUser doctor = null;
        if (StringUtils.isBlank(peispatient.getIdOpendoctor())) {
            peispatient.setIdOpendoctor(admin.getUserNo());
            doctor = admin;
        } else {
            doctor = iSysUserService.selectUserByUserNo(peispatient.getIdOpendoctor());
        }
        peispatient.setDoctorapply(Objects.isNull(doctor) ? "" : doctor.getUserName());

        Peisorgreservation vation = null;
        // 订单号
        String orderId = registerDto.getDdh();
        if (StringUtils.isNotBlank(orderId)) {
            Createorder createOrder = createorderMapper.getInfoById(orderId);
            peispatient.setNumorgresv(Objects.isNull(createOrder) ? "-1" : createOrder.getDdh());
            vation = peisorgreservationMapper.selectOne(new LambdaQueryWrapper<Peisorgreservation>().eq(Peisorgreservation::getDdh, orderId));
            // 任务ID
            peispatient.setIdOrgreservation(Objects.isNull(vation) ? "" : vation.getId());
            // 备单(不再备单列表的团体客户)
            peispatient.setFIsforprepare(Objects.isNull(vation) ? 0 : 1);
        }
        // 日期多8小时处理
        // 体检日期
        peispatient.setMedicaldate(ToolUtil.subTime(peispatient.getMedicaldate()));
        peispatient.setBirthdate(ToolUtil.subTime(peispatient.getBirthdate()));
        if (registerDto.getFUsecodehiden() == 1) {
            // 根据总工龄计算参加工作时间
            if (ObjectUtils.isNotEmpty(peispatient.getZgl()) && 0 != peispatient.getZgl()) {
                peispatient.setWorkDate(ToolUtil.getDateForMonth(peispatient.getZgl()));
            } else if (ObjectUtils.isNotEmpty(peispatient.getWorkDate())) {
                peispatient.setZgl(ToolUtil.getMonthSpace(now, ToolUtil.subTime(peispatient.getWorkDate())));
            }
            // 根据接害工龄计算从事该工种工作时间
            if (ObjectUtils.isNotEmpty(peispatient.getZgl()) && 0 != peispatient.getJhgl()) {
                peispatient.setHarmDate(ToolUtil.getDateForMonth(peispatient.getJhgl()));
            } else if (ObjectUtils.isNotEmpty(peispatient.getHarmDate())) {
                peispatient.setJhgl(ToolUtil.getMonthSpace(now, ToolUtil.subTime(peispatient.getHarmDate())));
            }
        }
        // 时间
        // 判断身份证、性别、年龄是否相符
        if (!StringUtils.isBlank(peispatient.getIdcardno()) && peispatient.getCountreportoccupationxml() != null
                && peispatient.getCountreportoccupationxml() == 1) {
            String card = peispatient.getIdcardno();
            // 如果长度是15位
            if (card.length() == 15) {
                card = card.substring(0, 6) + "19" + card.substring(6) + "x";
            }

            try {
                //生日匹配
                peispatient.setBirthdate(IdcardUtil.getBirthDate(card));
                // 年龄匹配
                peispatient.setAge(IdcardUtil.getAgeByIdCard(card));
                // 匹配性别
                card = card.substring(card.length() - 2, card.length() - 1);
                // 性别是否匹配
                int idSex = (Integer.valueOf(card) & 1) != 0 ? 0 : 1;
                if (peispatient.getIdSex() != idSex) {
                    throw new ServiceException("保存失败：性别不匹配");
                }
                peispatient.setIdSex(idSex);

                // 导入名单的时候或者完成登记,用areacode匹配区域
                String areaCode = peispatient.getIdcardno().substring(0, 2);
                Area area = areaMapper.selectOne(new LambdaQueryWrapper<Area>().eq(Area::getAreaCode, areaCode));
                if (Objects.nonNull(area)) {
                    peispatient.setIdResarea(area.getId());
                    peispatient.setResarea(area.getResarea());
                }
            } catch (Exception e) {
                log.error("判断身份证、性别、年龄是否相符操作异常：{}", e);
                throw new ServiceException("身份证、性别、年龄不相符,请修改后重试！");
            }

        }

        // 判断是否存在团检任务中
        // 判断人员信息是否重复添加
        // 现在同一个订单因为可以重复导入名单的情况，可以重复登记，所以先去掉校验
//        if (StringUtils.isNotBlank(peispatient.getIdOrg()) && !"3".equals(peispatient.getIdExamtype())
//                && StringUtils.isNotEmpty(peispatient.getIdOrgreservationgroup())) {
//            // 复查
//            Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(peispatient.getIdOrgreservationgroup());
//            //可重复
//            if (!(group != null && group.getIdPatientclass2() != null && group.getIdPatientclass2().intValue() == 1)) {
//                // 没分组的不判断
//                LambdaQueryWrapper<Peispatient> pWrapper = new LambdaQueryWrapper<>();
//
//                // 身份证号
//                if (StringUtils.isNotBlank(peispatient.getIdcardno())) {
//                    pWrapper.eq(Peispatient::getIdcardno, peispatient.getIdcardno());
//                } else {
//                    // 性别
//                    pWrapper.eq(Peispatient::getIdSex, peispatient.getIdSex());
//                    // 年龄
//                    pWrapper.eq(Peispatient::getAge, peispatient.getAge());
//                    // 电话
//                    if (StringUtils.isNotBlank(peispatient.getPhone())) {
//                        pWrapper.eq(Peispatient::getPhone, peispatient.getPhone());
//                    } else {
//                        pWrapper.isNull(true, Peispatient::getPhone);
//                    }
//                    // 部门
//                    if (StringUtils.isNotBlank(peispatient.getOrgDepart())) {
//                        pWrapper.eq(Peispatient::getOrgDepart, peispatient.getOrgDepart());
//                    } else {
//                        pWrapper.isNull(true, Peispatient::getOrgDepart);
//                    }
//                    // 工种
//                    if (StringUtils.isNotBlank(peispatient.getTrades())) {
//                        pWrapper.eq(Peispatient::getTrades, peispatient.getTrades());
//                    } else {
//                        pWrapper.isNull(true, Peispatient::getTrades);
//                    }
//                    // 姓名
//                    pWrapper.eq(Peispatient::getPatientname, peispatient.getPatientname());
//                }
//                // 不是复查
//                if (!"3".equals(peispatient.getIdExamtype())) {
//                    pWrapper.eq(Peispatient::getIdExamtype, peispatient.getIdExamtype());
//                }
//                // 不与自身体检号比较
//                if (!StringUtils.isBlank(peispatient.getPatientcode())) {
//                    pWrapper.ne(Peispatient::getPatientcode, peispatient.getPatientcode());
//                } else if (!StringUtils.isBlank(peispatient.getId())) {
//                    pWrapper.ne(Peispatient::getId, peispatient.getId());
//                }
//                if (Objects.nonNull(vation)) {
//                    Createorder order = createorderMapper.getInfoById(vation.getDdh());
//                    if (Objects.nonNull(order)) {
//                        pWrapper.eq(Peispatient::getNumorgresv, order.getDdh()).eq(Peispatient::getIdTjtc, peispatient.getIdTjtc());
//                        Peispatient peispatientNew = registerMapper.selectOne(pWrapper);
//                        if (Objects.nonNull(peispatientNew)) {
//                            throw new ServiceException("该体检者 " + peispatient.getPatientname() + " 在备单中已经存在，不能再进行操作！");
//                        }
//                    }
//                }
//            }
//        }

        // 中间库状态 -1:保存 0：第一次登记 1：重新登记 2:增项
        int sataus = -1;

        Peispatient pOld = registerMapper.selectOne(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, peispatient.getPatientcode()));

        if (intReservation == 1) {
            //完成登记操作
            // 登记后不可以再次登记
            if (Objects.nonNull(pOld) && Objects.nonNull(pOld.getFRegistered()) && 1 == pOld.getFRegistered()) {
                throw new ServiceException("完成登记失败：该体检者【" + peispatient.getPatientname() + "】已经登记不可以再次登记！");
            }
            if (Objects.nonNull(vation) && Objects.nonNull(vation.getFFinished()) && vation.getFFinished().intValue() == 1) {
                throw new ServiceException("完成登记失败：该体检者的订单已结束，不可以登记！");
            }
            // 判断是否已登记过
            if (!StringUtils.isBlank(peispatient.getPatientcode())) {
                // 已登记过
                sataus = 1;
            } else if (Objects.isNull(peispatient.getDateregister()) && Objects.isNull(peispatient.getFRegistered())) {
                // 第一次登记
                sataus = 0;
            }
            // 登记日期
            peispatient.setDateregister(now);
            // 体检时间 完成登记时，体检时间等于登记时间
            peispatient.setMedicaldate(peispatient.getDateregister());

            // 记录当前复查次数
            Long counterreportprinted = 0L;
            if (StringUtils.isNotEmpty(peispatient.getInpatientno())) {
                // 完成登记操作，不用判断时间,登记前肯定是未登记状态，不用判断不是当前号码
                counterreportprinted = registerMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                        .eq(Peispatient::getInpatientno, peispatient.getInpatientno())
                        .eq(Peispatient::getFRegistered, 1)) + 1;

                //第一次的体检号 可能是老系统导入过来的，如果搜不到就先默认是职业的
                Peispatient peispatient1 = registerMapper.selectOne(new LambdaQueryWrapper<Peispatient>()
                        .eq(Peispatient::getPatientcode, peispatient.getInpatientno()));
                String idExamtype = ObjectUtils.isNotEmpty(peispatient1) ? peispatient1.getIdExamtype() : "1";
                counterreportprinted = counterreportprinted + (idExamtype.equals(String.valueOf(ExamType.FUCHA.value())) ? 1 : 0);

            } else if (peispatient.getIdExamtype().equals(String.valueOf(ExamType.FUCHA.value()))) {
                // 如果手动调成复查，算作第一次
                counterreportprinted = 1L;
            }
            peispatient.setCounterreportprinted(Math.toIntExact(counterreportprinted));
            peispatient.setFRegistered(1);
            peispatient.setStatus(PeispatientStatus.dzf.value());



        } else {
            if (Objects.nonNull(pOld)) {
                if (Objects.nonNull(pOld.getFRegistered()) && 1 == pOld.getFRegistered()) {
                    sataus = 2;
                }
            }
        }

        if (intReservation == 2) {
            // 保存操作
        }

        // 总检锁定、体检状态开始，项目不可操作
        if (Objects.nonNull(pOld) && (((Objects.nonNull(pOld.getFFinallocked()) && pOld.getFFinallocked() == 1)
                || (Objects.nonNull(pOld.getJktjzt()) && pOld.getJktjzt() > 0))
                || ((Objects.nonNull(pOld.getIdGuidenurse()) && pOld.getIdGuidenurse() == 1)
                || (Objects.nonNull(pOld.getZytjzt()) && pOld.getZytjzt() > 0)))) {
            throw new ServiceException("保存失败：该体检者【" + peispatient.getPatientname() + "】总检已经锁定或者开始，不可以操作！");
        }

        // 绑定档案
        String patientarchiveno = peispatientarchiveService.bingIArchive(peispatient, false,SecurityUtils.getUserNo());

        // 判断是否为空
        if (StringUtils.isBlank(peispatient.getId()) && StringUtils.isBlank(peispatient.getPatientcode())) {
            // 生成体检号
            do {
                //线上生成app开头的，线下生成本中心的体检号
                if (ZhongkangConfig.isOnline()) {
                    patientcode = CodeUtil.getPatientCode(Constants.ONLINE_PREFIX, iSysConfigService.selectConfigByKey(Constants.VERSION_NO));
                } else {
                    patientcode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(null), "");
                }
                //判断体检号是否存在
            } while (registerMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getPatientcode, patientcode)) > 0);

            peispatient.setPatientcode(patientcode);
            peisStateService.setScbs(patientcode, 0);
            peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientcode));
            peispatient.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
            peispatient.setPatientarchiveno(patientarchiveno);
            peispatient.setIdDoctorreg(admin.getUserNo());

            // 登记后预付不修改数据库的值
            registerMapper.insert(peispatient);

            pcm = new PeispatientChargeMain(admin.getUserName(), peispatient.getMoneyamount(), peispatient.getMoneyamountpaid(),
                    peispatient.getPatientcode());
            peispatientChargeMainMapper.insert(pcm);
            newVersion = pcm.getVersion();
            pOld = peispatient;
        } else {
            //存在
            if (Objects.isNull(pOld)) {
                pOld = registerMapper.selectById(peispatient.getId());
                if (Objects.isNull(pOld) || StringUtils.isBlank(peispatient.getId())) {
                    throw new ServiceException("保存失败：体检号不存在，不能保存体检者信息！");
                } else {
                    // 备单人员未提前生成体检号，在此生成
                    // 生成体检号
                    do {
                        //线上生成app开头的，线下生成本中心的体检号
                        if (ZhongkangConfig.isOnline()) {
                            patientcode = CodeUtil.getPatientCode(Constants.ONLINE_PREFIX, iSysConfigService.selectConfigByKey(Constants.VERSION_NO));
                        } else {
                            patientcode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(null), "");
                        }
                        //判断体检号是否存在
                    } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                            .eq(Peispatient::getPatientcode, patientcode)) > 0);

                }
            } else {
                patientcode = peispatient.getPatientcode();
                // 如果以线上体检号前缀开头，或者不是本分中心的简码，重新生成本地体检号
                // 只有线下的时候才变
                boolean online = ZhongkangConfig.isOnline();
                if (!online) {
                    if (patientcode.indexOf(Constants.ONLINE_PREFIX) != -1 || !patientcode.startsWith(fzxjm) ) {
                        patientcode = CodeUtil.appConvert(iSysBranchService.getBranchFlag(null), "", patientcode);
                        // 更新体检者收费表（收费项目表在下面更新）
                        List<Peispatientcharge> charges = peispatientchargeService.list(
                                new LambdaQueryWrapper<Peispatientcharge>().eq(Peispatientcharge::getPatientcode, peispatient.getPatientcode()));
                        Integer shortCode = CodeUtil.getShortCodeByLong(patientcode);
                        for (Peispatientcharge charge : charges) {
                            charge.setPatientcode(patientcode);
                            charge.setShortCode(shortCode);
                            peispatientchargeService.updateById(charge);
                        }
                        if (pcm == null) {
                            //不抛异常了,先模糊查询，如果没有再从线上拉取
                            pcm = peispatientChargeMainMapper.selectOne(new LambdaQueryWrapper<PeispatientChargeMain>()
                                    .likeLeft(PeispatientChargeMain::getPatientcode, patientcode.substring(patientcode.length() - 8)));
                            if (ObjectUtils.isEmpty(pcm)){
                                orderService.pullOnlineData(oldPatientcode,2);
                                pcm = peispatientChargeMainMapper.selectOne(new LambdaQueryWrapper<PeispatientChargeMain>()
                                        .eq(PeispatientChargeMain::getPatientcode, oldPatientcode));
                            }
                        } else {
                            pcm.setPatientcode(patientcode);
                        }
                    }
                }
            }
            peispatient.setPatientarchiveno(patientarchiveno);
            peispatient.setPatientcode(patientcode);
            peisStateService.setScbs(peispatient.getPatientcode(), 0);
            peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientcode));
            peispatient.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
            peispatient.setIdDoctorreg(admin.getUserNo());
            registerMapper.updateById(peispatient);
            log.info("复制前对象操作：pOld={}、peispatient={}", pOld, peispatient);
            BeanUtils.copyBeanProp(pOld, peispatient);
            log.info("复制后对象操作：pOld={}、peispatient={}", pOld, peispatient);
        }
        //设置婚姻，可以为空
        LambdaUpdateWrapper<Peispatient> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Peispatient::getId, peispatient.getId())
                .set(Peispatient::getIdMarriage,registerDto.getIdMarriage())
                .set(Peispatient::getMarriage, MarriageType.getName(registerDto.getIdMarriage()));
        registerMapper.update(null, lambdaUpdateWrapper);


        // 如果是补检 登记
        if (intReservation == 1 && StringUtils.isNotBlank(pOld.getInsuranceno())) {
            List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>()
                    .eq(Peispatientfeeitem::getFRegistered, 1).eq(Peispatientfeeitem::getFFeecharged, 1)
                    .eq(Peispatientfeeitem::getIdPatient, pOld.getInsuranceno()).eq(Peispatientfeeitem::getFTransferedhl7, 0));
            for (Peispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                peispatientfeeitem.setFTransferedhl7(1);
                // 设置已来检
                LambdaQueryWrapper<VisitMain> wrapper = new LambdaQueryWrapper<VisitMain>()
                        .eq(VisitMain::getPatientcode, pOld.getInsuranceno())
                        .eq(VisitMain::getIdExamfeeitem, peispatientfeeitem.getId());
                wrapper.and(w -> {
                    w.isNotNull(VisitMain::getIsDelete).or().eq(VisitMain::getIsDelete, 0);
                });
                List<VisitMain> main = visitMainService.list(wrapper);
                if (CollectionUtil.isNotEmpty(main)) {
                    for (VisitMain visitMain : main) {
                        visitMain.setIsInspect(1);
                        visitMain.setInspectTime(now);
                        visitMainService.updateById(visitMain);
                    }
                }
            }
            peispatientfeeitemService.updateBatchById(peispatientfeeitems);
        }

        if (Objects.nonNull(pcm)) {
            pcm.setVersion(now.getTime());
            newVersion = pcm.getVersion();
            PeispatientChargeRecord pcr = new PeispatientChargeRecord();
            pcr.setPatientcode(patientcode);
            pcr.setVersion(newVersion);
            pcr.setMethod("saveOrUpdate");
            pcr.setMoneyamount(peispatient.getMoneyamount());
            pcr.setMoneyamountpaid(peispatient.getMoneyamountpaid());
            pcr.setUsername(admin.getUserName());
            if (Objects.isNull(newVersion)) {
                pcm.setVersion(now.getTime());

                pcr.setMoneyamountOld(pcm.getMoneyamount());
                pcr.setMoneyamountpaidOld(peispatient.getMoneyamountpaid());
                pcr.setNote("saveOrUpdate");
            } else {
                // 新增pcm
                pcr.setNote("saveOrUpdate 插入新的peisptient_charge_main");
            }
            //保存收费记录
            peispatientChargeRecordMapper.insert(pcr);

            pcm.setMoneyamount(peispatient.getMoneyamount());
            pcm.setMoneyamountpaid(peispatient.getMoneyamountpaid());
            //更新收费信息
            peispatientChargeMainMapper.updateById(pcm);
        } else {
            pcm = new PeispatientChargeMain(admin.getUserName(), peispatient.getMoneyamount(), peispatient.getMoneyamountpaid(),
                    peispatient.getPatientcode());
            //新增收费信息
            peispatientChargeMainMapper.insert(pcm);
            newVersion = pcm.getVersion();
        }

        //更新体检者头像图片
        if (Objects.isNull(photo)) {
            if (StringUtils.isNotEmpty(registerDto.getPicture())) {
                //这步老是报错，也没找到在哪里重复插入的，就在这再判断一下
                PeispatientPhoto peispatientPhoto = peispatientPhotoMapper.getByPatientCode(patientcode);
                log.info("保存或登记更新图片,体检号:{},结果:{}",patientcode,ObjectUtils.isNotEmpty(peispatientPhoto)?"存在":"不存在");
                if (ObjectUtils.isEmpty(peispatientPhoto)){
                    peispatientPhoto = new PeispatientPhoto();
                    peispatientPhoto.setPatientcode(patientcode);
                    peispatientPhoto.setPicture(registerDto.getPicture());
                    peispatientPhotoMapper.insert(peispatientPhoto);
                }else {
                    peispatientPhoto.setPicture(registerDto.getPicture());
                    peispatientPhotoMapper.updateById(peispatientPhoto);
                }
            }
        } else {
            photo.setPatientcode(patientcode);
            photo.setPicture(registerDto.getPicture());
            peispatientPhotoMapper.updateById(photo);
        }

        // 右侧收费项目没有数据，只是预约状态
        if (CollectionUtil.isNotEmpty(registerDto.getItemList())) {
            // 保存右侧收费项目
            // 性别可能会变,导致性别校验过不了，提起设置一下
            pOld.setIdSex(peispatient.getIdSex());
            R<String> stringR = saveOrUpdateItem(pOld, registerDto.getItemList(), intReservation, registerDto);
            if (StringUtils.isNotEmpty(registerDto.getHospitalcode()) &&
                    ("2".equals(registerDto.getHospitalcode()) || "3".equals(registerDto.getHospitalcode()))
                    && (ObjectUtils.isNotEmpty(registerDto.getFUsecodehiden()) && registerDto.getFUsecodehiden() == 0)
                    && StringUtils.isEmpty(registerDto.getIdTjtc())
                    && StringUtils.isEmpty(registerDto.getInsuranceno())
            ) {
                //东西区要检验最低折扣,个检，并且没有选套餐，还不是补检的
                SysUser user = sysUserMapper.getUserByNo(SecurityUtils.getUserNo());
                Double sdiscount = "1".equals(user.getIsleader())?user.getLdiscount():user.getSdiscount();
                if (ObjectUtils.isEmpty(sdiscount)) throw new ServiceException("请先去用户管理处维护折扣！");
                double zkl = Arith.div(pOld.getMoneyamount(), pOld.getPersonpricelimit(),2) * 10;
                if (zkl < sdiscount) {
                    throw new ServiceException("您的最低折扣为：" + sdiscount + "，当前体检者的折扣为：" + zkl + "，无法保存！");
                }
            }

            if (R.isError(stringR)) {
                throw new ServiceException(StringUtils.isBlank(stringR.getMsg()) ? "保存失败：保存收费项目发生问题！" : stringR.getMsg());
            } else {
                int isataus = Integer.valueOf(stringR.getData());
                // 不是第一次登记和重新登记
                if (sataus != 0 && sataus != 1 && sataus != -1) {
                    if (isataus == 1) {
                        sataus = 2;
                    } else {
                        sataus = isataus;
                    }
                }
            }

        }
        //完成登记修改预约信息
        if (intReservation == 1) {
            //修改预约信息(根据后8位模糊查询)
            List<Reservation> reservations = reservationService.list(new LambdaQueryWrapper<Reservation>()
                    .likeLeft(Reservation::getPatientcode, patientcode.substring(patientcode.length() - 8))
                    .eq(Reservation::getStatus, ReservationStatus.SUCCESS.value())
                    .orderByDesc(Reservation::getCreatedate)
            );
            if (CollectionUtil.isNotEmpty(reservations)) {
                //需要更新体检者信息和状态
                Reservation reservation1 = reservations.get(0);
                reservation1.setPatientcode(patientcode);
                reservation1.setStatus(ReservationStatus.FINISHED.value());
                reservation1.setRealName(peispatient.getPatientname());
                reservation1.setMobile(peispatient.getPhone());
                reservation1.setIdcard(peispatient.getIdcardno());
                reservation1.setFinishedTime(new Date());
                reservation1.setFailReason("预约结束，已完成预约！");
                reservationService.updateById(reservation1);

                //修改体检者表的信息
                peispatient.setFIsforreserve(1);
                peispatient.setDateguidancereturned(reservation1.getReservationDate());
                peispatientMapper.updateById(peispatient);
            }
        }

        // 如果已登记，是职业或者综合的，而且点的是保存，如果存在职业史(只有一条的话，就更新一下)
        if (peispatient.getFRegistered() == 1 && intReservation == 2
                && ("1".equals(peispatient.getIdExamtype()) || "2".equals(peispatient.getIdExamtype()))
                && (ObjectUtils.isNotEmpty(peispatient.getJhgl()) && peispatient.getJhgl() > 0)){
            List<WzZys> list = wzZysService.list(new LambdaQueryWrapper<WzZys>().eq(WzZys::getDjls, peispatient.getPatientcode()));
            if (list.size() == 1){
                WzZys wzZys = list.get(0);
                Calendar startC = Calendar.getInstance();
                startC.setTime(DateUtil.beginOfDay(peispatient.getDateregister()));
                startC.add(Calendar.MONTH, 0 - peispatient.getJhgl());
                wzZys.setStartDate(startC.getTime());
                wzZysService.updateById(wzZys);
            }
        }



        RegisterResultVo registerResultVo = new RegisterResultVo();
        registerResultVo.setPatientcode(patientcode);
        registerResultVo.setPatientarchiveno(peispatient.getPatientarchiveno());
        registerResultVo.setSataus(sataus);
        return registerResultVo;
    }


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public R<String> saveOrUpdateItem(Peispatient pei, List<ItemsDto> itemList, Integer intReservation, RegisterDto registerDto) {

        SysUser admin = SecurityUtils.getLoginUser().getUser();
        String patientCode = pei.getPatientcode();
        Integer shortCode = pei.getShortCode();
        pei.setModifydate(new Date());

        // 已经登记
        if (Objects.nonNull(pei.getFRegistered()) && 1 == pei.getFRegistered()) {
            if (0 == intReservation) {
                throw new ServiceException("该体检者已经完成登记，不可再次预约！");
            } else if (intReservation == 2) {
                // 如果已经登记过，说明收费项目是存在的，页面点击“保存”按钮，登记未收费项的项目
                intReservation = 1;
            }
        }

        //校验项目性别
        Integer idSex = pei.getIdSex();
        for (ItemsDto itemsDto : itemList) {
            Integer fFeechargedinttrans = itemsDto.getFFeechargedinttrans();
            //删除的、空的、和通用、的不做判断
            if (("removed").equals(itemsDto.get_state()) || fFeechargedinttrans == null || fFeechargedinttrans == 2) {
                continue;
            }
            if (idSex != fFeechargedinttrans) {
                throw new ServiceException("收费项目:" + itemsDto.getExamfeeitemName() + "和体检者性别不匹配!");
            }
        }


        //如果是补检，自动将所有没有处理的处理成补检来检
        if (intReservation == 1 && pei.getInsuranceno() != null) {
            List<VisitMain> vms = visitMainService.list(new LambdaQueryWrapper<VisitMain>()
                    .eq(VisitMain::getPatientcode, pei.getInsuranceno())
                    .in(VisitMain::getType, new Integer[]{0, 4}));
            String visitId = admin.getUserNo();
            Date visitTime = new Date();
            for (VisitMain vm : vms) {
                FailTotalVisit ftv = failTotalVisitService.getOne(new LambdaQueryWrapper<FailTotalVisit>()
                        .eq(FailTotalVisit::getVisitMainId, vm.getId()));

                if (Objects.isNull(ftv)) {
                    ftv = new FailTotalVisit();
                    ftv.setVisitMainId(vm.getId());
                    ftv.setVisitType(0);
                    ftv.setPersoncode(vm.getPatientcode());
                    ftv.setVisitId(visitId);
                    ftv.setSflj(5);
                    ftv.setVisitTime(visitTime);
                    failTotalVisitService.save(ftv);
                }
            }
        }

        if (CollectionUtil.isEmpty(itemList)) {
            return R.ok("-1", "");
        }

        // 不同的收费项目之间的检查项目也不能重复
        List<String> strr = itemList.stream().filter(itemsDto -> {
            if ("removed".equals(itemsDto.get_state())) return false;
            if (!((Objects.nonNull(itemsDto.getChangeItem()) && itemsDto.getChangeItem() == 1
                    && Objects.nonNull(itemsDto.getFMarkFeereturn()) && itemsDto.getFMarkFeereturn() == 1)
                    || Objects.nonNull(itemsDto.getFGiveup()) && itemsDto.getFGiveup() == 1
            )) return true;
            return false;

        }).map(ItemsDto::getIdExamfeeitem).collect(Collectors.toList());
        String text = inspectChargeService.compareItemsToExam(strr);
        if (StringUtils.isNotBlank(text)) {
            return R.fail(text);
        }

        //收费方式是统收，并且还是未收状态，不是要删除的
        long count = registerDto.getItemList().stream()
                .filter(item -> "5".equals(item.getIdPayway())
                        && (Objects.isNull(item.getFFeecharged()) || item.getFFeecharged() == 0)
                        && !item.get_state().equals("removed")
                ).count();
        // 团检是否存在现金收费(1:现金 0：统收)
        int isTuanToX = 0;
        // 团检
        if (registerDto.getFUsecodehiden() == 1) {
            isTuanToX = count > 0 ? 0 : 1;
        } else {
            isTuanToX = 1;
        }

        // 备选数量
        int bxcount = Objects.isNull(registerDto.getBxcount()) ? 0 : registerDto.getBxcount();

        int isataus = -1;

        // 存在套餐
        Double tcPrice = 0d;
        Double yuanshi = 0d;
        if (!StringUtils.isBlank(pei.getIdTjtc())) {
            Createcombo combo = createcomboMapper.getInfoById(pei.getIdTjtc());
            Createmeal createMeal = createmealMapper.getInfoById(pei.getIdTjtc());
            if (null != combo && null == createMeal) {
                yuanshi = combo.getTcysjg();
                tcPrice = combo.getZhjg();
            } else if (null == combo && null != createMeal) {
                yuanshi = createMeal.getTcysjg();
                tcPrice = createMeal.getZhjg();
            }
            yuanshi = null == yuanshi ? 0 : yuanshi;
            tcPrice = null == tcPrice ? 0 : tcPrice;
        }

        // 退项价格
        Double tuiPrice = 0d;
        Double tuiYsPrice = 0d;
        // 未收费项目是否需要变为已收（未收项总和=0，自动收费）
        Double noCharge = 0d;
        String jykId = sysDeptMapper.selectDeptByName("检验科").getDeptNo();

        List<Peispatientfeeitem> pfItemList = mapperFacade.mapAsList(itemList, Peispatientfeeitem.class);

        double ys = 0.0;// 应收（所有优惠价合计）
        double yj = 0.0;// 原价（所有原价合计）
        int i = 0;
        for (Peispatientfeeitem item : pfItemList) {
            ItemsDto itemsDto = itemList.get(i++);
            item.setIdPatient(patientCode);
            item.setShortCode(shortCode);
            item.setBxcount(bxcount);

            // 计算实际金额
            // 不是最小套餐，实际金额==优惠价格
            Double shiji = 0d;
            if (yuanshi != 0) {
                shiji = Arith.mul(Arith.div(tcPrice, yuanshi), item.getPrice());
            }
            if (null == item.getIsMintc() || 0 == item.getIsMintc()) {
                shiji = item.getFactprice();
            }
            item.setActualprice(shiji);

            if ("removed".equals(itemsDto.get_state())) {
                if (StringUtils.isNotBlank(item.getId())) {
                    Peispatientfeeitem old = peispatientfeeitemService.getInfoById(item.getId());
                    // 判断是否存在
                    if (Objects.nonNull(old)) {
                        // 弃检
                        if (old.getFGiveup() == 1) {
                            throw new ServiceException("删除失败：【" + item.getExamfeeitemName() + "】收费项目已经弃检，不能被删除！");
                        }
                        // 已检
                        if (Objects.nonNull(old.getFExaminated()) && old.getFExaminated() == 1) {
                            throw new ServiceException("删除失败：【" + item.getExamfeeitemName() + "】收费项目已经检查，不能被删除！");
                        }
                        // 已收费
                        if (Objects.nonNull(old.getFFeecharged()) && old.getFFeecharged() == 1) {
                            throw new ServiceException("删除失败：【" + item.getExamfeeitemName() + "】收费项目已经收费，不能被删除,只能换项或者弃项！");
                        }
                        // 删除实体类
                        peispatientfeeitemService.removeById(old);
                    }
                }
            } else if ("modified".equals(itemsDto.get_state())) {
                Peispatientfeeitem old = peispatientfeeitemService.getInfoById(item.getId());
                // 判断是否存在
                if (Objects.nonNull(old)) {
                    // 退项
                    if (Objects.nonNull(item.getChangeItem()) && item.getChangeItem() == 1) {
                        // 弃检
                        if (item.getFGiveup() == 1) {
                            throw new ServiceException("保存失败：【" + item.getExamfeeitemName() + "】收费项目已经弃检，不能退项！");
                        }
                        if (Objects.nonNull(item.getEndtuiprice())
                                && (Objects.isNull(item.getFMarkFeereturn()) || 0 == item.getFMarkFeereturn())) {
                            tuiPrice += item.getEndtuiprice();
                            tuiYsPrice -= item.getPrice();
                        }
                    } else {
                        Double fp = item.getFactprice();
                        if (fp != null) {
                            ys += fp;
                        }
                        Double p = item.getPrice();
                        if (p != null) {
                            yj += p;
                        }
                    }

                    // 弃检
                    if (Objects.nonNull(item.getFGiveup()) && item.getFGiveup() == 1) {
                        // 退项
                        if (Objects.nonNull(old.getChangeItem()) && old.getChangeItem() == 1) {
                            throw new ServiceException("保存失败：【" + item.getExamfeeitemName() + "】收费项目已经退项，不能弃检！");
                        }
                    }

                    // 已检
                    if (Objects.nonNull(old.getFExaminated()) && old.getFExaminated() == 1) {
                        // 退项
                        if (Objects.nonNull(item.getChangeItem()) && item.getChangeItem() == 1) {
                            throw new ServiceException("保存失败：【" + item.getExamfeeitemName() + "】收费项目已经检查，不能退项！");
                        }
                        // 弃检
                        if (Objects.nonNull(item.getFGiveup()) && item.getFGiveup() == 1) {
                            throw new ServiceException("保存失败：【" + item.getExamfeeitemName() + "】收费项目已经检查，不能弃检！");
                        }
                    }

                    // 迟检(已经迟检的不再保存)
                    if (Objects.nonNull(item.getFDelayed()) && item.getFDelayed() == 1
                            && (Objects.isNull(old.getFDelayed()) || old.getFDelayed() == 0)) {
                        item.setCjr(admin.getUserName());
                        item.setCjsj(new Date());

                        // 更新迟捡、阳性、不合格样本人员表
                        VisitMain vm = new VisitMain();
                        vm.setPatientcode(patientCode);
                        vm.setIdExamfeeitem(old.getId());
                        vm.setType(0);
                        vm.setExamfeeitemName(item.getExamfeeitemName());
                        visitMainService.saveChijian(vm);

                        /** 迟检检验科项目时 默认插入不用提示 */
                        if (jykId.equals(old.getIdKs())) {
                            HandleNewProjects handleNewProjects = handleNewProjectsService.getOne(new LambdaQueryWrapper<HandleNewProjects>()
                                    .eq(HandleNewProjects::getPatientcode, patientCode)
                                    .eq(HandleNewProjects::getProjectsId, item.getId())
                                    .eq(HandleNewProjects::getIsDelete, 0)
                                    .eq(HandleNewProjects::getHandleType, NewProjectHandleType.CJ.value()));
                            // 不存在
                            if (Objects.isNull(handleNewProjects)) {
                                handleNewProjects = new HandleNewProjects();
                                handleNewProjects.setPatientcode(patientCode);
                                handleNewProjects.setCreateId(admin.getUserNo());
                                handleNewProjects.setModifyId(admin.getUserNo());
                                handleNewProjects.setProjectsId(old.getId());// 项目ID
                                handleNewProjects.setIsDelete(0);
                                handleNewProjects.setStatus(0);
                                handleNewProjects.setHandleType(NewProjectHandleType.CJ.value());
                                // 保存实体类
                                handleNewProjectsService.save(handleNewProjects);
                            } else {
                                handleNewProjects.setModifyId(admin.getUserNo());
                                handleNewProjects.setProjectsId(old.getId());// 项目ID
                                handleNewProjectsService.updateById(handleNewProjects);
                            }
                        }
                    }

                    // 反迟检
                    if (Objects.nonNull(old.getFDelayed()) && old.getFDelayed() == 1
                            && (item.getFDelayed()==null || item.getFDelayed() == 0)) {
                        LambdaUpdateWrapper<Peispatientfeeitem> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(Peispatientfeeitem::getId, item.getId())
                                .set(Peispatientfeeitem::getCjr, null)
                                .set(Peispatientfeeitem::getCjsj, null);
                        peispatientfeeitemService.update(updateWrapper);

                        VisitMain visitMain = visitMainService.getOne(new LambdaQueryWrapper<VisitMain>()
                                .eq(VisitMain::getPatientcode, patientCode)
                                .eq(VisitMain::getIdExamfeeitem, old.getId())
                                .eq(VisitMain::getType, 0)
                                .eq(VisitMain::getIsDelete, 0)
                        );
                        if (Objects.nonNull(visitMain)) {
                            visitMain.setIsDelete(1);
                            visitMainService.updateById(visitMain);
                        }

                        HandleNewProjects handleNewProjects = handleNewProjectsService.getOne(new LambdaQueryWrapper<HandleNewProjects>()
                                .eq(HandleNewProjects::getPatientcode, patientCode)
                                .eq(HandleNewProjects::getProjectsId, item.getId())
                                .eq(HandleNewProjects::getIsDelete, 0)
                                .eq(HandleNewProjects::getHandleType, 3)
                        );
                        if (Objects.nonNull(handleNewProjects)) {
                            if (Objects.nonNull(handleNewProjects.getIsHandle()) && handleNewProjects.getIsHandle() == 1) {
                                throw new ServiceException("保存失败：【" + item.getExamfeeitemName() + "】收费项目的弃检已被检验科处理，请反处理后再反迟检！");
                            }
                            handleNewProjectsService.removeById(handleNewProjects);
                        }
                    }

                    // 收费项目登记
                    if (intReservation == 1) {
                        item.setFRegistered(1);
                        // 登记日期
                        item.setRegistertime(new Date());
                    }
                    // 判断是否存在登记员ID
                    if (StringUtils.isBlank(item.getIdDoctorreg())) {
                        item.setIdDoctorreg(admin.getUserNo());
                        item.setDoctorregR(admin.getUserName());
                    }

                    /** HandleNewProjects */
                    // 加项医师是检化验科表中插入数据--> 检验科加项处理表
                    if (null != item.getSfjx() && 1 == item.getSfjx()) {
                        HandleNewProjects handleNewProjects = handleNewProjectsService.getOne(new LambdaQueryWrapper<HandleNewProjects>()
                                .eq(HandleNewProjects::getPatientcode, patientCode)
                                .eq(HandleNewProjects::getProjectsId, item.getId())
                                .eq(HandleNewProjects::getIsDelete, 0)
                                .eq(HandleNewProjects::getHandleType, NewProjectHandleType.JX.value()));
                        // 不存在
                        if (Objects.isNull(handleNewProjects)) {
                            handleNewProjects = new HandleNewProjects();
                            handleNewProjects.setPatientcode(patientCode);
                            handleNewProjects.setCreateId(admin.getUserNo());
                            handleNewProjects.setModifyId(admin.getUserNo());
                            handleNewProjects.setProjectsId(old.getId());// 项目ID
                            handleNewProjects.setAddDoctorId(old.getJxys());
                            handleNewProjects.setIsDelete(0);
                            handleNewProjects.setStatus(0);
                            handleNewProjects.setHandleType(NewProjectHandleType.JX.value());
                            // 保存实体类
                            handleNewProjectsService.save(handleNewProjects);
                        } else {
                            handleNewProjects.setModifyId(admin.getUserNo());
                            handleNewProjects.setProjectsId(old.getId());// 项目ID
                            handleNewProjects.setAddDoctorId(old.getJxys());
                            handleNewProjectsService.updateById(handleNewProjects);
                        }
                    }

                    // 弃检
                    if (Objects.nonNull(item.getFGiveup()) && item.getFGiveup() == 1) {
                        // 本次操作弃检的
                        if (Objects.isNull(old.getFGiveup()) || old.getFGiveup() != 1) {
                            item.setQjr(admin.getUserNo());
                            item.setQjsj(new Date());
                        }
                        HandleNewProjects handleNewProjects = handleNewProjectsService.getOne(new LambdaQueryWrapper<HandleNewProjects>()
                                .eq(HandleNewProjects::getPatientcode, patientCode)
                                .eq(HandleNewProjects::getProjectsId, item.getId())
                                .eq(HandleNewProjects::getIsDelete, 0)
                                .eq(HandleNewProjects::getHandleType, NewProjectHandleType.QJ.value()));
                        // 不存在
                        if (Objects.isNull(handleNewProjects)) {
                            handleNewProjects = new HandleNewProjects();
                            handleNewProjects.setPatientcode(patientCode);
                            handleNewProjects.setCreateId(admin.getUserNo());
                            handleNewProjects.setModifyId(admin.getUserNo());
                            handleNewProjects.setProjectsId(old.getId());// 项目ID
                            handleNewProjects.setIsDelete(0);
                            handleNewProjects.setStatus(0);
                            handleNewProjects.setHandleType(NewProjectHandleType.QJ.value());
                            // 保存实体类
                            handleNewProjectsService.save(handleNewProjects);
                        } else {
                            handleNewProjects.setModifyId(admin.getUserNo());
                            handleNewProjects.setProjectsId(old.getId());// 项目ID
                            handleNewProjectsService.updateById(handleNewProjects);
                        }
                    }
                    // 反弃检
                    if (Objects.nonNull(old.getFGiveup()) && old.getFGiveup() == 1
                            && (Objects.isNull(item.getFGiveup()) || item.getFGiveup() != 1)) {
                        LambdaUpdateWrapper<Peispatientfeeitem> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(Peispatientfeeitem::getId, item.getId())
                                .set(Peispatientfeeitem::getQjr, null)
                                .set(Peispatientfeeitem::getQjsj, null);
                        peispatientfeeitemService.update(updateWrapper);
                        HandleNewProjects handleNewProjects = handleNewProjectsService.getOne(new LambdaQueryWrapper<HandleNewProjects>()
                                .eq(HandleNewProjects::getPatientcode, patientCode)
                                .eq(HandleNewProjects::getProjectsId, item.getId())
                                .eq(HandleNewProjects::getIsDelete, 0)
                                .eq(HandleNewProjects::getHandleType, NewProjectHandleType.QJ.value()));
                        if (Objects.nonNull(handleNewProjects)) {
                            if (Objects.nonNull(handleNewProjects.getIsHandle()) && handleNewProjects.getIsHandle() == 1) {
                                throw new ServiceException("保存失败：【" + item.getExamfeeitemName() + "】收费项目的弃检已被检验科处理，请反处理后再反弃检！");
                            }
                            handleNewProjectsService.removeById(handleNewProjects);
                        }
                    }

                    //TODO ??? 10周年卡不要了，可以考虑删掉
                    if (intReservation == 1 && Objects.nonNull(item.getSamplemsgfromlis()) && "1".equals(item.getSamplemsgfromlis())) {
                        item.setFFeecharged(1);// 已收费
                        item.setIdFeecharger(admin.getUserNo());
                        item.setFeechargetime(new Date());
                    }
                    /** HandleNewProjects结束 */

                    // 是否自动收费
                    if (Objects.isNull(old.getFFeecharged()) || 0 == old.getFFeecharged()) {
                        noCharge = Arith.add(noCharge, item.getFactprice());
                    }

                } else {
                    // 不存在
                    throw new ServiceException("保存失败：【" + item.getExamfeeitemName() + "】收费项目不存在，已经被删除！");
                }
                log.info("保存前收费项目信息：{}", item.getFFeecharged());
                peispatientfeeitemService.updateById(item);
            } else if ("added".equals(itemsDto.get_state())) {
                Peispatientfeeitem old = peispatientfeeitemService.getOne(new LambdaQueryWrapper<Peispatientfeeitem>()
                        .eq(Peispatientfeeitem::getIdPatient, item.getIdPatient())
                        .eq(Peispatientfeeitem::getIdExamfeeitem, item.getIdExamfeeitem())
                        .eq(Peispatientfeeitem::getChangeItem, 0));
                if (Objects.nonNull(old)) {
                    throw new ServiceException("体检者已拥有收费项目【" + old.getExamfeeitemName() + "】,不能再添加此收费项目,请点击【刷新】查看。");
                }
                Double fp = item.getFactprice();
                if (fp != null) {
                    ys += fp;
                }
                Double p = item.getPrice();
                if (p != null) {
                    yj += p;
                }
                // 已登记
                if (Objects.nonNull(pei.getFRegistered()) && pei.getFRegistered() == 1) {
                    // 已登记，增项
                    isataus = 1;
                }
                // 收费项目登记
                if (intReservation == 1) {
                    item.setFRegistered(1);
                    // 登记日期
                    item.setRegistertime(new Date());
                }
                // 判断是否存在登记员ID
                if (StringUtils.isBlank(item.getIdDoctorreg())) {
                    item.setIdDoctorreg(admin.getUserNo());
                    item.setDoctorregR(admin.getUserName());
                }

                // 团检是否存在现金收费
                if (intReservation == 1 && isTuanToX == 0 && item.getIdPayway().equals("5")) {
                    // 统收不存在加项收费信息保存
                    item.setFFeecharged(1);// 已收费
                    item.setIdFeecharger(admin.getUserNo());
                    item.setFeechargetime(new Date());
                }

                //TODO ??? 10周年卡不要了，可以考虑删掉
                if (intReservation == 1 && Objects.nonNull(item.getSamplemsgfromlis())
                        && "1".equals(item.getSamplemsgfromlis())) {
                    item.setFFeecharged(1);// 已收费
                    item.setIdFeecharger(admin.getUserNo());
                    item.setFeechargetime(new Date());
                }
                // 保存实体类
                boolean save = peispatientfeeitemService.save(item);
                if (!save) {
                    throw new ServiceException("【" + item.getExamfeeitemName() + "】收费项目保存失败！");
                }
                if (StringUtils.isNotEmpty(itemsDto.getTempId())) {
                    TempFeeitem tf = tempFeeitemService.getInfoById(itemsDto.getTempId());
                    tf.setFeeitemId(item.getId());
                    tf.setIsDelete(1);
                    tempFeeitemService.updateById(tf);
                }
                // 是否自动收费
                noCharge = Arith.add(noCharge, item.getFactprice());

                // 总检开始，不可以加项
                if (Objects.nonNull(item.getSfjx()) && 1 == item.getSfjx()) {
                    if (((Objects.nonNull(pei.getFFinallocked()) && pei.getFFinallocked() == 1)
                            || (Objects.nonNull(pei.getJktjzt()) && pei.getJktjzt() > 0))
                            || ((Objects.nonNull(pei.getIdGuidenurse()) && pei.getIdGuidenurse() == 1)
                            || (Objects.nonNull(pei.getZytjzt()) && pei.getZytjzt() > 0))) {
                        throw new ServiceException("保存失败：该体检者总检已经锁定或者开始，不能加项！");
                    } else {
                        // 检验科加项医师处理
                        HandleNewProjects handleNewProjects = new HandleNewProjects();
                        handleNewProjects.setPatientcode(patientCode);
                        handleNewProjects.setCreateId(admin.getUserNo());
                        handleNewProjects.setModifyId(admin.getUserNo());
                        handleNewProjects.setProjectsId(item.getId());// 项目ID
                        handleNewProjects.setAddDoctorId(item.getJxys());
                        handleNewProjects.setIsDelete(0);
                        handleNewProjects.setStatus(0);
                        handleNewProjects.setHandleType(NewProjectHandleType.JX.value());
                        // 保存实体类
                        handleNewProjectsService.save(handleNewProjects);
                    }


                } else if (ObjectUtils.isNotEmpty(registerDto.getNoticeJyk()) && registerDto.getNoticeJyk() == 1) {
                    // 检验科加项医师处理
                    HandleNewProjects handleNewProjects = new HandleNewProjects();
                    handleNewProjects.setPatientcode(patientCode);
                    handleNewProjects.setCreateId(admin.getUserNo());
                    handleNewProjects.setModifyId(admin.getUserNo());
                    handleNewProjects.setProjectsId(item.getId());// 项目ID
                    handleNewProjects.setAddDoctorId(item.getJxys());
                    handleNewProjects.setIsDelete(0);
                    handleNewProjects.setStatus(0);
                    handleNewProjects.setHandleType(NewProjectHandleType.JX.value());
                    // 保存实体类
                    handleNewProjectsService.save(handleNewProjects);
                }
                // TODO: 加项科室反审核(检验科除外)
                applicationEventPublisher.publishEvent(new DeApprovalEvent(item));
            }
        }

        PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new LambdaQueryWrapper<PeispatientChargeMain>()
                .eq(PeispatientChargeMain::getPatientcode, patientCode));
        if (Objects.isNull(pcm)) {
            //不抛异常了,先模糊查询，如果没有再从线上拉取
            pcm = peispatientChargeMainMapper.selectOne(new LambdaQueryWrapper<PeispatientChargeMain>()
                    .likeLeft(PeispatientChargeMain::getPatientcode, patientCode.substring(patientCode.length() - 8)));
            if (ObjectUtils.isEmpty(pcm)){
                orderService.pullOnlineData(patientCode,2);
                pcm = peispatientChargeMainMapper.selectOne(new LambdaQueryWrapper<PeispatientChargeMain>()
                        .eq(PeispatientChargeMain::getPatientcode, patientCode));
            } else {
                pcm.setPatientcode(patientCode);
            }
        }
        // 团检未加项客户增加收费信息
        if (isTuanToX == 0 && pei.getFRegistered() == 1) {
            Double moneyamountpaid = Arith.add((Objects.isNull(pei.getMoneyamountpaid()) ? 0 : pei.getMoneyamountpaid()), registerDto.getTongFei());
            PeispatientChargeRecord pcr = new PeispatientChargeRecord();
            pcr.setPatientcode(patientCode);
            pcr.setVersion(pcm.getVersion());
            pcr.setMethod("saveOrUpdateItem");
            pcr.setMoneyamount(pcm.getMoneyamount());
            pcr.setMoneyamountpaid(pcm.getMoneyamountpaid());
            pcr.setMoneyamountOld(ys);
            pcr.setMoneyamountpaidOld(moneyamountpaid);
            pcr.setUsername(admin.getUserNo());
            pcr.setNote("saveOrUpdateItem 团检自动收费");
            peispatientChargeRecordMapper.insert(pcr);

            pei.setMoneyamountpaid(moneyamountpaid);
            pcm.setMoneyamountpaid(pei.getMoneyamountpaid());
            pcm.setNote(admin.getUserNo() + "团检自动收费于" + DateUtil.format(new Date(), "yyyyMMddHHmmssSSS") + ";");
            //TODO ??? 是否需要实现自动设置为vip
//            preregistrationService.setVip(pei);

            PeispatientchargeDto peispatientchargeDto = new PeispatientchargeDto();
            peispatientchargeDto.setMoneyamount(registerDto.getTongFei());
            peispatientchargeDto.setMoneyamountpaid(registerDto.getTongFei());
            peispatientchargeDto.setPatientcode(patientCode);
            if (!peispatientchargeService.autoSaveOrUpdate(peispatientchargeDto)) {
                throw new ServiceException("收费信息保存失败！");
            }
        }
        pei.setMoneyamount(ys);
        pei.setPersonpricelimit(yj);
        String newNote = admin.getUserName() + "保存应付金额于" + DateUtil.format(new Date(), "yyyyMMddHHmmssSSS") + ";";
        pcm.setMoneyamount(pei.getMoneyamount());
        pcm.setNote(newNote);
        registerMapper.updateById(pei);
        peispatientChargeMainMapper.updateById(pcm);

        // 检查剩余收费项目是否可以分拣完成
        peispatientfeeitemService.checkFj(pei);

        // TODO syncData 如果是完成登记则同步登记信息(登记项目信息)

        return R.ok(String.valueOf(isataus), "");
    }


    @Override
    @Cacheable(cacheNames = "Peispatient", key = "#id")
    public Peispatient getInfoById(String id) {
        Peispatient peispatient = registerMapper.selectById(id);
        return peispatient;
    }


    /**
     * 得到备单人员的信息
     *
     * @param id
     * @param patientCode
     * @return
     */
    @Override
    public HashMap getPatientData(String id, String patientCode) {
        // 保存页面所需要的值
        HashMap map = new HashMap();
        QueryWrapper<Peispatient> queryWrapper = new QueryWrapper<>();
        //判断是否是老系统导过来的数据
        long count = codeCorrespondingService.count(new LambdaQueryWrapper<CodeCorresponding>()
                .eq(CodeCorresponding::getNewCode, patientCode));
        map.put("oldCode", count > 0 ? 1 : 0);
        // id存在说明是备单人员(预约)
        if (!StringUtils.isBlank(patientCode)) {
            // 从登记列表跳转的数据
            queryWrapper.eq("patientcode", patientCode);
            if ("-1".equals(patientCode)) {
                queryWrapper.eq("id", id);
            }
            // 已登记的信息
            Peispatient peispatient = registerMapper.selectOne(queryWrapper);
            map.put("location", 0);
            // 体检者表不存在
            if (ObjectUtils.isEmpty(peispatient)) {
                QueryWrapper<Peispatienthistory> c = new QueryWrapper<>();
                c.eq("patientcode", patientCode);
                if ("-1".equals(patientCode)) {
                    c.eq("id", id);
                }
                // 历史体检者
                Peispatienthistory peispatientHistory = peispatienthistoryMapper.selectOne(c);
                if (null == peispatientHistory) {
                    // 错误信息
                    map.put("success", false);
                    map.put("error", "体检者不存在！");
                    return map;
                } else {
                    peispatient = mapperFacade.map(peispatientHistory, Peispatient.class);
                    map.put("location", 1);
                }
            }
            Peisorgreservationgroup group = null;
            Peisorgreservation vation = null;
            if (null != peispatient) {
                //体检者费用主表
                PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new QueryWrapper<PeispatientChargeMain>().eq("patientcode", peispatient.getPatientcode()));
                if (pcm != null) {
                    peispatient.setMoneyamount(pcm.getMoneyamount());
                    peispatient.setMoneyamountpaid(pcm.getMoneyamountpaid());
                    map.put("version", pcm.getVersion());
                }
                map.put("picture", peispatientPhotoService.getPicture(peispatient));
                String ddId = "";
                if (!StringUtils.isBlank(peispatient.getIdOrgreservation())) {
                    //体检者团体任务
                    vation = peisorgreservationMapper.getInfoById(peispatient.getIdOrgreservation());
                    // 体检套餐
                    if (null != vation) {
                        group = peisorgreservationgroupMapper.getInfoById(peispatient.getIdOrgreservationgroup());
                        //组禁用
                        if (null != group && group.getFGroupstarted() == 0 && group.getFGrouppaused() == 1) {
                            map.put("limit", 1);
                        }
                    }
                    // 备单没有保存相对应的收费项目
                    ddId = null == vation ? "" : vation.getDdh();//订单ID
                }
                if (StringUtils.isEmpty(ddId)) {
                    //订单号
                    String numorgresv = peispatient.getNumorgresv();
                    if (numorgresv != null) {
                        Createorder order = createorderMapper.getInfoByDdh(numorgresv);
                        if (order != null) {
                            ddId = order.getId();
                        }
                    }
                }
                String idTjtc = peispatient.getIdTjtc();
                if (StringUtils.isNotEmpty(idTjtc)) {
                    Createmeal meal = createmealMapper.getInfoById(idTjtc);
                    if (ObjectUtils.isEmpty(meal)) {
                        Createcombo combo = createcomboMapper.selectOne(new LambdaQueryWrapper<Createcombo>().eq(Createcombo::getId, idTjtc));
                        if (ObjectUtils.isEmpty(combo))throw new ServiceException("套餐不存在！");
                        map.put("tcjg", combo.getZhjg());
                        long tczs = comboanditemService.count(new QueryWrapper<Comboanditem>().eq("tcid", idTjtc));
                        map.put("tczs", tczs);
                    } else {
                        map.put("tcjg", meal.getZhjg());
                        long tczs = mealanditemService.count(new QueryWrapper<Mealanditem>().eq("tcid", idTjtc));
                        map.put("tczs", tczs);
                    }
                }




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

                List<Map<String, Object>> itemData = itemListService.getExamfeeByPatientCode(peispatient.getPatientcode(), "1");
                if (CollectionUtil.isEmpty(itemData)) {
                    // 关联备单分组的体检套餐下的收费项目
                    if (null != group) {
                        //获取右侧收费项目
                        itemData = physicalCheckoutService.getExamfeeitemData(peispatient.getIdTjtc(), ddId, group.getId());
                    }
                }
                //增加职业必检列
                if (CollectionUtil.isNotEmpty(itemData)) {

                    if ("1".equals(peispatient.getIdExamtype()) || "3".equals(peispatient.getIdExamtype())) {
                        for (Map<String, Object> im : itemData) {
                            im.put("zybj", "1");
                        }
                    } else if ("2".equals(peispatient.getIdExamtype())) {
                        //用于判断职业小结
                        List<Comboexamitem> eis = comboexamitemMapper.selectList(new QueryWrapper<Comboexamitem>()
                                .in("harm_id", peispatient.getJhys().split(",")).eq("medical_type", peispatient.getMedicaltype()));
                        HashMap<String, Comboexamitem> ceis = new HashMap<String, Comboexamitem>();
                        for (Comboexamitem cei : eis) {
                            ceis.put(cei.getExamId(), cei);
                        }
                        for (Map<String, Object> im : itemData) {
                            String idExamfeeitem = String.valueOf(im.get("idExamfeeitem"));
                            //检查项目收费项目关联表
                            List<InspectCharge> ics = inspectChargeService.list(new QueryWrapper<InspectCharge>().eq("charge_id", idExamfeeitem));
                            String zybj = "0";
                            for (InspectCharge ic : ics) {
                                if (ceis.get(ic.getInspectId()) != null) {
                                    zybj = "1";
                                    break;
                                }
                            }
                            im.put("zybj", zybj);
                        }
                        //idExamfeeitem
                    } else {
                        for (Map<String, Object> im : itemData) {
                            im.put("zybj", "0");
                        }
                    }

                }

                map.put("examfeeitemData", itemData);// 收费项目
                map.put("addItemData", getRegAddItemData(patientCode));//加项项目
                map.put("group", group);// 分组

                if (StringUtils.isNotBlank(peispatient.getJhys())) {
                    String[] jhyss = peispatient.getJhys().split(",");
                    log.info("打印一下接害因素数组{}" + jhyss.toString());
                    String harmText = harmMapper.getHarmText(jhyss);
                    log.info("打印一下接害因素{}" + harmText.toString());
                    map.put("jhysn", harmText);// 接害因素
                }

                map.put("idOrder", ddId);//订单ID
                if (!StringUtils.isEmpty(peispatient.getWorktypeId())) {
                    //工种
                    BaseWorktype worktype = baseWorktypeMapper.getInfoById(peispatient.getWorktypeId());
                    if (worktype != null) {
                        map.put("workType", worktype.getTypeName());
                        peispatient.setTrades(worktype.getTypeName());
                    }
                }
                map.put("patientData", peispatient);
                //开单医生
                SysUser qxUsers = iSysUserService.getUserByNo(peispatient.getIdOpendoctor());
                map.put("sellname", null == qxUsers ? "" : qxUsers.getUserName());// 开单医师名称
                // 查找收费信息退费金额
                List<Peispatientcharge> charges = peispatientchargeService.list(new QueryWrapper<Peispatientcharge>()
                        .eq("patientcode", peispatient.getPatientcode()).eq("is_delete", 0));
                Double tMoney = 0.00;
                for (Peispatientcharge peispatientCharge : charges) {
                    //==null按0.0处理
                    if (peispatientCharge.getMoneyamountpaid() != null && peispatientCharge.getMoneyamountpaid() < 0) {
                        tMoney += peispatientCharge.getMoneyamountpaid();
                    }
                }
                map.put("tMoney", tMoney);
                map.put("success", true);
            } else {
                map.put("success", false);
                map.put("error", "体检者不存在！");
            }

            return map;
        } else {
            if (!StringUtils.isBlank(id)) {
                return getPatientData(id, "-1");
            }
            // 错误信息
            map.put("success", false);
            map.put("error", "系统发生异常，请联系管理员！");
            return map;
        }
    }


    /**
     * 获取检查项目数据
     *
     * @param patientCode
     * @return
     */
    public List<Map<String, Object>> getRegAddItemData(String patientCode) {
        //科室临时加项表
        List<TempFeeitem> tfs = tempFeeitemMapper.selectList(new QueryWrapper<TempFeeitem>()
                .eq("patientcode", patientCode).eq("is_delete", 0)
        );
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (TempFeeitem tf : tfs) {
            map = new HashMap<String, Object>();
            map.put("tempId", tf.getId());
            map.put("idExamfeeitem", tf.getItemId());
            //收费项目表
            Items it = itemsService.getInfoById(tf.getItemId());
            map.put("examfeeitemName", it.getExamfeeitemName());
            map.put("price", it.getUnitprice());
            map.put("factprice", tf.getPrice());
            map.put("idPayway", "1");
            map.put("sfjx", "1");
            //加项医生
            SysUser qxUsers = iSysUserService.selectUserByUserName(tf.getDoctorUsername());
            map.put("jxys", qxUsers.getUserNo());
            map.put("name", qxUsers.getUserName());
            map.put("FRegistered", 0);
            map.put("changeItem", 0);
            map.put("FMarkFeereturn", 0);
            map.put("FFeecharged", 0);
            map.put("FLabsendtolis", 0);
            map.put("FExaminated", 0);
            map.put("FGiveup", 0);
            map.put("FDelayed", 0);
            map.put("isMintc", 0);
            map.put("idKs", it.getIdDepart());
            map.put("FFeechargedinttrans", it.getXb());// 性别
            map.put("createdate", tf.getCreatedate());
            map.put("feeitemdesc", tf.getRemarks());
            map.put("count", 1);
            items.add(map);
        }
        return items;
    }


    /**
     * 获取体检者与收费项目信息
     *
     * @param patientCode
     * @param type
     * @param autoFill
     * @return
     */
    @Override
    public CustomerDataVo getCustomerData(String patientCode, Integer type, Integer autoFill) {
        Peispatient peispatient = peispatientMapper.selectOne(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getPatientcode, patientCode));
        if (Objects.isNull(peispatient)) {
            throw new ServiceException("体检号不存在或者已被删除");
        }
        CustomerDataVo customerDataVo = new CustomerDataVo();
        customerDataVo.setPeispatient(peispatient);
        //接害因素
        String harmText = StringUtils.isNotEmpty(peispatient.getJhys()) ? harmMapper.getHarmText(peispatient.getJhys().split(",")) : "";
        //获取右侧收费项目
        customerDataVo.setExamfeeitemData(getExamfeeByPatientCode(patientCode, type));
        // 重复
        Peisorgreservationgroup group = peisorgreservationgroupMapper.selectById(peispatient.getIdOrgreservationgroup());
        customerDataVo.setGroupName(null == group ? "" : group.getOrgreservationgroupname());
        // 查找收费信息退费金额
        List<Peispatientcharge> charges = peispatientchargeService.list(new LambdaQueryWrapper<Peispatientcharge>()
                .eq(Peispatientcharge::getPatientcode, peispatient.getPatientcode())
                .eq(Peispatientcharge::getIsDelete, 0));
        Double tMoney = 0.00;
        for (Peispatientcharge peispatientCharge : charges) {
            if (peispatientCharge.getMoneyamountpaid() != null && peispatientCharge.getMoneyamountpaid() < 0) {
                tMoney = Arith.add(tMoney, peispatientCharge.getMoneyamountpaid());
            }
        }
        customerDataVo.setTMoney(tMoney);

        PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientCode);
        customerDataVo.setVersion(pcm == null ? null : pcm.getVersion());
        return customerDataVo;
    }

    /**
     * 获取右侧收费项目
     *
     * @param patientCode
     * @param type
     * @return
     */
    @Override
    public List<PeispatientfeeitemVo> getExamfeeByPatientCode(String patientCode, Integer type) {
        QueryWrapper<Peispatientfeeitem> queryWrapper = new QueryWrapper<>();

        if (type == 0) {
            // 全部显示
            queryWrapper.orderByAsc("createdate");
        } else if (type == 1) {
            // 显示除去退项的
            queryWrapper.eq("change_item", 0);
            queryWrapper.orderByAsc("createdate");
        } else if (type == 2) {
            // 显示退项的
            queryWrapper.eq("change_item", 1);
            queryWrapper.orderByAsc("modifydate");
        }
        //体检者收费项目列表
        List<Peispatientfeeitem> items = peispatientfeeitemService.list(queryWrapper.eq("id_patient", patientCode));
        log.info("体检者收费项目列表{}", items);

        Peispatient patient = registerMapper.selectOne(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getPatientcode, patientCode));
        //体检套餐ID
        String idtjtc = patient.getIdTjtc();
        Createmeal cm = null;
        Integer bxcount = null;
        if (StringUtils.isNotEmpty(idtjtc)) {
            cm = createmealMapper.getInfoById(idtjtc);
            bxcount = cm == null || cm.getKxsl() == null ? 0 : cm.getKxsl();
        }
        List<PeispatientfeeitemVo> itemVos = mapperFacade.mapAsList(items, PeispatientfeeitemVo.class);
        for (PeispatientfeeitemVo itemVo : itemVos) {
            log.info("体检者收费项目列表{}", items);
            Double endPrice = itemVo.getFactprice();
            endPrice = (endPrice == null ? 0 : -endPrice);
            if (null != itemVo.getChangeItem() && 1 == itemVo.getChangeItem()) {
                endPrice = itemVo.getEndtuiprice() == null ? 0d : itemVo.getEndtuiprice();
            }
            itemVo.setEndtuiprice(endPrice);
            //加项医师名称
            if (StringUtils.isNotBlank(itemVo.getJxys())) {
                SysUser sysUser = iSysUserService.selectUserByUserNo(itemVo.getJxys());
                itemVo.setName(Objects.nonNull(sysUser) ? sysUser.getUserName() : "");
            }

            //科室名称
            if (StringUtils.isNotBlank(itemVo.getIdKs())) {
                SysDept sysDepartment = sysDeptMapper.getByDeptNo(itemVo.getIdKs());
                itemVo.setKsmc(Objects.nonNull(sysDepartment) ? sysDepartment.getDeptName() : "");
            }
            itemVo.setBxcount(bxcount);
            Items it = itemsService.getById(itemVo.getIdExamfeeitem());
            itemVo.setFFeechargedinttrans(it.getXb());// 性别
            //备选
            if (itemVo.getIsbx() != null && itemVo.getIsbx() == 1) {
                if (cm != null) {
                    Mealanditem mai = mealanditemService.getOne(new LambdaQueryWrapper<Mealanditem>()
                            .eq(Mealanditem::getTcid, idtjtc).eq(Mealanditem::getSfxmid, itemVo.getIdExamfeeitem()));
                    if (mai != null) {
                        itemVo.setIsbx(mai.getSfbx());
                        mai.setItemGroup(mai.getItemGroup());
                        itemVo.setGroupType(mai.getGroupType());
                    }
                }
            }
        }
        log.info("获取体检者右侧收费项目：{}、{}", items, itemVos);

        return itemVos;
    }

    /**
     * 反登记：原来是直接删除收费记录，但会造成当日提交财务的数据和后来的数据不一致
     * 预售的费用不能删除，也不能退掉，因为反登记后再来登记还要用那些预收
     * 也不能系统自动退费，分不同收费方式、是否加项，很麻烦，只能手动退费
     * 现修改为，存在预收的体检号不能反登记
     * 必须手动退掉所有钱，再反登记
     *
     * @param patientCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean setUnRegister(String patientCode) {

        // 是否存在
        Peispatient peispatientNew = registerMapper.selectOne(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, patientCode));
        if (Objects.isNull(peispatientNew)) {
            throw new ServiceException("反登记失败：体检号不存在，不能进行反登记！");
        } else if (Objects.isNull(peispatientNew.getFRegistered()) || peispatientNew.getFRegistered() == 0) {
            throw new ServiceException("反登记失败：体检号没有登记！");
        }

        //存在预收费用，不能反登记！
        Peispatientcharge yushou = peispatientchargeService.getOne(new LambdaQueryWrapper<Peispatientcharge>()
                .eq(Peispatientcharge::getPatientcode, patientCode)
                .eq(Peispatientcharge::getNote, "预收").eq(Peispatientcharge::getIsDelete, 0));
        if (Objects.nonNull(yushou)) {
            throw new ServiceException("反登记失败：存在预收费用，不能反登记！");
        }

        //费用没有退完！
        PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientCode);
        if (Objects.nonNull(pcm) && Objects.nonNull(pcm.getMoneyamountpaid()) && pcm.getMoneyamountpaid() > 0) {
            throw new ServiceException("反登记失败：费用没有退完！");
        }

        // 关联的收费项目
        List<Peispatientfeeitem> list = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>()
                .eq(Peispatientfeeitem::getIdPatient, patientCode));

        if (StringUtils.isBlank(peispatientNew.getIdOrg())) {
            // 个检，如果没有完成收退款，可直接反登记，然后删除收费记录。
            for (Peispatientfeeitem peispatientfeeitem : list) {
                // 已收费、未退费
                if (Objects.nonNull(peispatientfeeitem.getFFeecharged()) && peispatientfeeitem.getFFeecharged() == 1
                        && (Objects.isNull(peispatientfeeitem.getChangeItem()) || peispatientfeeitem.getChangeItem() == 0)) {
                    throw new ServiceException("反登记失败：【" + peispatientNew.getPatientname() + "】体检者收费项目部分已收费，不能进行反登记操作！");
                }
                // 反登记收费项目
                peispatientfeeitem.setFRegistered(0);
                peispatientfeeitemService.updateById(peispatientfeeitem);
            }
        } else {
            //团检
            for (Peispatientfeeitem peispatientfeeitem : list) {
                // 统收中现金收费
                if ("1".equals(peispatientfeeitem.getIdPayway())
                        && (Objects.nonNull(peispatientfeeitem.getFFeecharged()) && peispatientfeeitem.getFFeecharged() == 1)
                        && (Objects.isNull(peispatientfeeitem.getChangeItem()) || peispatientfeeitem.getChangeItem() == 0)) {
                    throw new ServiceException("反登记失败：" + peispatientNew.getPatientname() + " 体检者的收费项目 " + peispatientfeeitem.getExamfeeitemName() + " 付款方式为现金已经收费，不能进行反登记操作！");
                }
                // 统收项目
                if (null != peispatientfeeitem.getFExaminated() && peispatientfeeitem.getFExaminated() == 1) {
                    throw new ServiceException("反登记失败：" + peispatientNew.getPatientname() + " 体检者的收费项目 " + peispatientfeeitem.getExamfeeitemName() + " 已经检查，不能进行反登记操作！");
                }
                // 反登记收费项目
                peispatientfeeitem.setFRegistered(0);
                // 未收费
                peispatientfeeitem.setFFeecharged(0);
                peispatientfeeitemService.updateById(peispatientfeeitem);
            }
        }

        //如果有预约结束的信息，修改为已预约
        Reservation reservation = reservationService.getOne(new LambdaQueryWrapper<Reservation>()
                .eq(Reservation::getPatientcode, patientCode)
                .in(Reservation::getStatus, 3)
        );
        if (ObjectUtils.isNotEmpty(reservation)){
            reservation.setStatus(2);
            reservationService.updateById(reservation);
        }

        // 反登记
        peispatientNew.setFRegistered(0);
        peispatientNew.setMoneyamount(0d);
        peispatientNew.setFSettlenone(null);
        peispatientNew.setMoneyamountpaid(0d);//xuhp
        //记录体检状态
        peisStateService.saOrUp(peispatientNew, 0);
        registerMapper.updateById(peispatientNew);
        //TODO syncData 反登记后同步线上状态

        //登记时间更新为空
        LambdaUpdateWrapper<Peispatient> wrapper = new LambdaUpdateWrapper<Peispatient>();
        wrapper.eq(Peispatient::getPatientcode, patientCode);
        wrapper.set(Peispatient::getDateregister, null);
        peispatientService.update(wrapper);

        return Boolean.TRUE;
    }

    /**
     * 前台-登记管理-退项
     *
     * @param refundFeeItemDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public R<Integer> updateTui(RefundFeeItemDto refundFeeItemDto) {
        String patientCode = refundFeeItemDto.getPatientcode();
        Date now = new Date();
        SysUser admin = SecurityUtils.getLoginUser().getUser();
        Peispatient pei = registerMapper.selectOne(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getPatientcode, patientCode));
        // 体检者不存在
        if (Objects.isNull(pei)) {
            throw new ServiceException("该体检者体检号不存在，不能退项！");
        }
        pei.setModifydate(now);

        PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientCode);
        if (Objects.isNull(pcm)) {
            throw new ServiceException(patientCode + "未找到收费信息主表！");
        }
        Long version = refundFeeItemDto.getVersion();
        // version==null兼容老数据
        if ((version == null && pcm.getVersion() != null) || (version != null && pcm.getVersion() != null
                && version.longValue() != pcm.getVersion().longValue())) {
            throw new ServiceException("信息有变动，请刷新!");
        }
        Long newVersion = new Date().getTime();
        pcm.setVersion(newVersion);

        /**
         * 表格中显示的是未退项的项目
         * 如果要应收改变，只能加项、退项、未收费项目改变价格,未收费的不能退项
         * 页面加载出数据后，保存前数据可能已经改变，先不考虑两个人操作同一体检者
         * 一个人操作，只判断同接口重复调用，只判断项目是否与数据库一致
         */
        List<ItemsDto> itemList = refundFeeItemDto.getItemList();
        if (CollectionUtil.isEmpty(itemList)) {
            throw new ServiceException("退项信息为空，请选择后再提交！");
        }

        List<String> itemIds1 = itemList.stream().map(ItemsDto::getId).collect(Collectors.toList());
        List<Peispatientfeeitem> pis = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>()
                .eq(Peispatientfeeitem::getIdPatient, patientCode)
                .eq(Peispatientfeeitem::getChangeItem, 0));
        List<String> itemIds2 = pis.stream().map(Peispatientfeeitem::getId).collect(Collectors.toList());

        if (!itemIds2.containsAll(itemIds1)) {
            throw new ServiceException("收费信息已改变，请刷新页面重试！");
        }

        int bxcount = Objects.nonNull(refundFeeItemDto.getBxcount()) ? refundFeeItemDto.getBxcount() : 0;

        int isataus = -1;
        // 存在套餐
        Double tcPrice = 0d;
        Double yuanshi = 0d;
        if (!StringUtils.isBlank(pei.getIdTjtc())) {
            Createcombo combo = createcomboMapper.selectById(pei.getIdTjtc());
            Createmeal createMeal = createmealMapper.selectById(pei.getIdTjtc());
            if (Objects.nonNull(combo) && Objects.nonNull(createMeal)) {
                yuanshi = combo.getTcysjg();
                tcPrice = combo.getZhjg();
            } else if (Objects.isNull(combo) && Objects.nonNull(createMeal)) {
                yuanshi = createMeal.getTcysjg();
                tcPrice = createMeal.getZhjg();
            }
            yuanshi = Objects.isNull(yuanshi) ? 0 : yuanshi;
            tcPrice = Objects.isNull(tcPrice) ? 0 : tcPrice;
        }

        // 退项价格
        Double tuiPrice = 0d;
        Double tuiYsPrice = 0d;
        // 未收费项目是否需要变为已收（未收项总和=0，自动收费）
        Double noCharge = 0d;

        List<Peispatientfeeitem> peispatientfeeitems = mapperFacade.mapAsList(itemList, Peispatientfeeitem.class);
        log.info("收费项目列表：{}、{}", itemList, peispatientfeeitems);
        Integer i = 0;
        for (Peispatientfeeitem item : peispatientfeeitems) {
            ItemsDto itemsDto = itemList.get(i++);
            item.setIdPatient(patientCode);
            item.setBxcount(bxcount);
            // 计算实际金额
            // 不是最小套餐，实际金额==优惠价格
            Double shiji = 0d;
            if (yuanshi != 0) {
                shiji = Arith.mul(Arith.div(tcPrice, yuanshi), item.getPrice());
            }
            if (Objects.isNull(item.getIsMintc()) || 0 == item.getIsMintc()) {
                shiji = item.getFactprice();
            }
            item.setActualprice(shiji);
            if ("modified".equals(itemsDto.get_state())) {
                Peispatientfeeitem feeItem = peispatientfeeitemService.getInfoById(item.getId());
                // 判断是否存在
                if (Objects.isNull(feeItem)) {
                    // 不存在
                    throw new ServiceException("保存失败：【" + item.getExamfeeitemName() + "】收费项目不存在，已经被删除！");
                }
                // 退项
                if (Objects.nonNull(item.getChangeItem()) && item.getChangeItem() == 1) {
                    // 弃检
                    if (feeItem.getFGiveup() == 1) {
                        throw new ServiceException("保存失败：【" + item.getExamfeeitemName() + "】收费项目已经弃检，不能退项！");
                    }
//                    log.error("tuiPrice0:{}、{}、{}、{}", item.getEndtuiprice(), feeItem.getFMarkFeereturn(),
//                            Objects.nonNull(item.getEndtuiprice()), (Objects.isNull(feeItem.getFMarkFeereturn()) || 0 == feeItem.getFMarkFeereturn()));
                    if (Objects.nonNull(item.getEndtuiprice()) && (Objects.isNull(feeItem.getFMarkFeereturn()) || 0 == feeItem.getFMarkFeereturn())) {
//                        log.error("tuiPrice1:" + tuiPrice);
                        tuiPrice = Arith.add(tuiPrice, item.getEndtuiprice());
                        tuiYsPrice = Arith.sub(tuiYsPrice, item.getPrice());
                    }
                    //如果是检验科加项的话，得把加项也处理了
                    if (StringUtils.isNotEmpty(item.getIdKs()) && "19".equals(item.getIdKs())){
                        handleNewProjectsService.remove(new LambdaQueryWrapper<HandleNewProjects>()
                                .eq(HandleNewProjects::getProjectsId,item.getId())
                                .eq(HandleNewProjects::getPatientcode,item.getIdPatient()));
                    }

                }
                // 已检
                if (Objects.nonNull(feeItem.getFExaminated()) && feeItem.getFExaminated() == 1) {
                    // 退项
                    if (Objects.nonNull(item.getChangeItem()) && item.getChangeItem() == 1) {
                        throw new ServiceException("保存失败：【" + item.getExamfeeitemName() + "】收费项目已经检查，不能退项！");
                    }
                }
                // 判断是否存在登记员ID
                if (StringUtils.isBlank(item.getIdDoctorreg())) {
                    item.setIdDoctorreg(admin.getUserNo());
//                    SysUser user = iSysUserService.selectUserByUserNo(admin.getUserNo());
                    item.setDoctorregR(admin.getUserName());
                }
                BeanUtils.copyBeanProp(feeItem, item);
                log.info("体检项目信息：{}、{}", feeItem, item);
                // 更新实体类
                peispatientfeeitemService.updateById(feeItem);
                // 是否自动收费
                if (Objects.isNull(feeItem.getFFeecharged()) || 0 == feeItem.getFFeecharged()) {
                    noCharge = Arith.add(noCharge, feeItem.getFactprice());
                }
            }
        }

        PeispatientChargeRecord pcr = new PeispatientChargeRecord();
        pcr.setPatientcode(patientCode);
        pcr.setVersion(pcm.getVersion());
        pcr.setMethod("updateTui");
        pcr.setMoneyamount(Arith.add(pei.getMoneyamount(), tuiPrice));
        pcr.setMoneyamountpaid(pcm.getMoneyamountpaid());
        pcr.setMoneyamountOld(pcm.getMoneyamount());
        pcr.setMoneyamountpaidOld(pcm.getMoneyamountpaid());
        pcr.setUsername(admin.getUserName());
        pcr.setNote("updateTui");
        peispatientChargeRecordMapper.insert(pcr);

        if (tuiPrice < 0) {
            // 更新体检者的应付金额
            pei.setMoneyamount(Arith.add(pei.getMoneyamount(), tuiPrice));
            pei.setPersonpricelimit(Objects.isNull(pei.getPersonpricelimit()) ? 0 : pei.getPersonpricelimit() + tuiYsPrice);
            String newNote = admin.getUserName() + ".updatetui于" + DateUtil.format(new Date(), "yyyyMMddHHmmssSSS") + ";";
            pcm.setMoneyamount(pei.getMoneyamount());
            pcm.setNote(newNote);
            peispatientChargeMainMapper.updateById(pcm);
        }
        // 检查剩余收费项目是否可以分拣完成
        peispatientfeeitemService.checkFj(pei);

        // 插入PACS
        log.info("是否开启PACS配置：{}", iSysConfigService.selectConfigByKey("PACS"));
        if (Boolean.parseBoolean(iSysConfigService.selectConfigByKey("PACS"))) {
            //TODO wait 这块逻辑需要重新梳理
            //String msg = savePacs(patientCode);
//            if (!"success".equals(msg)) {
//                throw new Exception("保存失败,插入PACS数据异常！");
//            }
        }

        // TODO syncData 退项成功后是否需要同步线上数据
        return R.ok(isataus, "");
    }

    /**
     * 前台-登记-退项-退项恢复
     *
     * @param id 体检者收费项目ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean returnItem(String id) {
        Peispatientfeeitem item = peispatientfeeitemService.getInfoById(id);
        // 判断这条收费项目是否存在
        if (Objects.nonNull(item)) {
            Peispatient peispatient = peispatientMapper.getByPatientCode(item.getIdPatient());
            if (Objects.isNull(peispatient)) {
                throw new ServiceException("恢复失败：体检者不存在");
            }
            peispatient.setModifydate(new Date());

            item.setFFeecharged(1);
            item.setFLabsendtolis(0);
            // 判断是否以退费
            if (Objects.nonNull(item.getFMarkFeereturn()) && 1 == item.getFMarkFeereturn()) {
                throw new ServiceException("恢复失败：该条收费项目已经退费");
            }
            item.setChangeItem(0);
            // 更新实体类

            // 设置【应付金额】
            peispatient.setMoneyamount(Arith.sub(peispatient.getMoneyamount(), item.getEndtuiprice()));
            //体检者费用主表
            PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(item.getIdPatient());
            String newNote = SecurityUtils.getUsername() + "returnitem于" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ";";
            if (Objects.isNull(pcm)) {
                throw new ServiceException("未找到体检者收费主表。");
            } else {
                pcm.setMoneyamount(peispatient.getMoneyamount());
                pcm.setNote(newNote);
            }
            Double endtuiprice = item.getEndtuiprice() == null ? 0.0 : item.getEndtuiprice();
            peispatient.setPersonpricelimit(peispatient.getPersonpricelimit() - endtuiprice);
            // 查找未检、未弃检、未退项、的收费项目数量
            peispatientfeeitemService.checkFj(peispatient);
            peispatientfeeitemService.updateById(item);

            // TODO: wait 插入独立pacs
//            //插入PACS
//            String patientCode = item.getIdPatient();
//            if (publicUtil.getDictionaryStatusByType("PACS")
//            ) {
//                String msg = preregistrationService.savePacs(patientCode);
//                if (!"success".equals(msg)) {
//                    throw new ServiceException("插入PACS数据异常！");
//                }
//            }
//            //插入独立PACS
//            if ("1".equals(ToolUtil.getXmlPro("pacs", "isOpen"))) {
//                String msg = preregistrationService.savePacs2(patientCode);
//                if (!"success".equals(msg)) {
//                    throw new ServiceException("保存失败,插入PACS数据异常！");
//                }
//            }
        } else {
            throw new ServiceException("恢复失败：该条收费项目不存在");
        }
        return Boolean.TRUE;
    }

    public static void main(String[] args) {
        List<String> itemIds1 = Arrays.asList("1", "2", "333");
        List<String> itemIds2 = Arrays.asList("1", "333", "2");
        System.out.println(itemIds1.stream().sorted().collect(Collectors.joining()).equals(itemIds2.stream().sorted().collect(Collectors.joining())));
    }

    /**
     * 取得已预约客户
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<ReservationUserVo> getReservationUser(PageParam<ReservationUserVo> page, ReservationUserParam param) {
        if (ObjectUtils.isEmpty(param.getIsReg())) {
            //不传默认为0
            param.setIsReg(0);
        }
        param.setType(0);
        param.setUserNo(SecurityUtils.getUserNo());
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        //今天的开始和结束时间
        param.setDayStart(DateUtil.beginOfDay(new Date()));
        param.setDayEnd(DateUtil.endOfDay(new Date()));
        return registerMapper.getReservationUser(page, param);
    }

    /**
     * 获取档案记录
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<RecordListVo> getRecordListData(PageParam<RecordListVo> page, RecordListParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        //去空格
        if (ObjectUtils.isNotEmpty(param.getPatientname())) {
            param.setPatientname(param.getPatientname().trim());
        }
        if (ObjectUtils.isNotEmpty(param.getInputCode())) {
            param.setInputCode(param.getInputCode().trim());
        }
        return registerMapper.getRecordListData(page, param);
    }


    /**
     * 问卷-保存
     *
     * @param formdata
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map getAnswer(String formdata) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String fzx = SecurityUtils.getCId();
            //基础推荐套餐
            BaseGuideMeal meal = baseGuideMealMapper.selectOne(new QueryWrapper<BaseGuideMeal>().eq("type", "1")
                    .eq("fzx", fzx).or().like("fzx", fzx));
            if (ObjectUtils.isEmpty(meal)) {
                throw new ServiceException("获取项目失败，没有维护个性套餐。");
            }
            Map<String, String> maps = Constants.question;
            Set<String> itemIds = new HashSet<String>();
            JSONObject obj = JSONUtil.parseObj(formdata);
            //循环
            for (String key : obj.keySet()) {
                //跳过id开头的
                if ("id".equals(key)) continue;

                //value
                Object fieldVal = obj.get(key);
                String val = null;
                //value是集合
                if (fieldVal instanceof JSONArray) {
                    //转换成集合遍历
                    JSONArray jarr = (JSONArray) fieldVal;
                    for (int i = 0; i < jarr.size(); i++) {
                        //key:value(i)
                        log.info("看一下value:{}" + key + ":" + jarr.getStr(i));
                        val = maps.get(key + ":" + jarr.getStr(i));
                        if (StringUtils.isNotEmpty(val)) {
                            itemIds.addAll(Arrays.asList(val.split(",")));
                        }
                    }
                } else {
                    log.info("看一下value:{}" + key + ":" + fieldVal);
                    val = maps.get(key + ":" + fieldVal);
                    if (StringUtils.isNotEmpty(val)) {
                        itemIds.addAll(Arrays.asList(val.split(",")));
                    }
                }
            }
            //通过sql查询答案
            List<AnswerDto> result = registerMapper.getAnswer(itemIds);
            resultMap.put("AnswerDto", result);
            //保存答案至体检者表

            String id = obj.getStr("id");
            if (StringUtils.isNotEmpty(id)) {
                //获取问卷数据
                List<QuestionsVo> questionsVos = getQusetions();
                JSONArray array = JSONUtil.parseArray(questionsVos);
                Map<String, String> descriptions = new HashMap<String, String>();
                for (int i = 0; i < array.size(); i++) {
                    descriptions.put(array.getJSONObject(i).getStr("questionId"), array.getJSONObject(i).getStr("description"));
                }
                //体检者表
                Peispatient p = peispatientMapper.getInfoById(id);

                StringBuilder builder = new StringBuilder();
                List<String> keys = new ArrayList<String>(obj.keySet());
                keys.remove("id");
                Collections.sort(keys, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        Integer i1 = Integer.parseInt(o1);
                        Integer i2 = Integer.parseInt(o2);
                        return i1.compareTo(i2);
                    }
                });
                for (String key : keys) {
                    String des = descriptions.get(key);
                    Object fieldVal = obj.get(key);
                    if (fieldVal instanceof JSONArray) {
                        builder.append(des + StringUtils.join(((JSONArray) fieldVal).toArray(), "、") + "@@");
                    } else {
                        builder.append(des + fieldVal + "@@");
                    }
                }
                p.setKnowledge(String.valueOf(builder));
                peispatientMapper.updateById(p);
            }
            resultMap.put("data", result);
            resultMap.put("status", "success");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("status", "error");
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    /**
     * 问卷-数据
     *
     * @param id
     * @return
     */
    @Override
    public List<QuestionsVo> addQuestions(String id) {
        Peispatient patient = peispatientMapper.getInfoById(id);
        //性别ID
        String sex = patient.getIdSex() == 0 ? "男" : "女";
        //获取问卷数据
        List<QuestionsVo> array = getQusetions();
        List<QuestionsVo> result = new ArrayList<>();
        //返回数据
        for (QuestionsVo questionsVo : array) {
            //性别不为空,性别不相符
            if (StringUtils.isNotEmpty(questionsVo.getSex()) && !questionsVo.getSex().equals(sex)) {
                continue;
            }
            result.add(questionsVo);
        }
        return result;
    }


    /**
     * 获取问卷数据
     *
     * @return
     */
    public List<QuestionsVo> getQusetions() {
        List<QuestionsVo> array = new ArrayList<>();
        QuestionsVo questionsVo1 = new QuestionsVo(1, 1, 1, 1, "现在和过去如有以下疾病，请在对应的框内打钩。", new String[]{"基本正常", "1.高血压", "2.糖尿病（或疑似）", "3.血脂异常"
                , "4.高尿酸症（包括痛风）", "5.肾,脏病（肾炎  肾结石等）", "6.心脏病（心肌梗塞  心律不齐等）", "7.呼吸系统疾病（喘息   肺结核等）", "8.癌症", "9.肝脏疾病（肝炎   脂肪肝等）", "10.食道 胃  十二指肠疾病（溃疡  胃炎等）"
                , "11.胆囊 脾脏疾病（胆结石  脾炎等）", "12.大肠疾病（息肉等）", "13.贫血症或其他的血液疾病", "14.甲状腺疾病（甲状腺机能亢进   甲状腺机能低下等）", "15.胶原病和关节风湿", "16.脑神经疾病（脑中风等）"
                , "17.眼科疾病（白内障  青光眼等）", "18.听力障碍", "19.前列腺疾病（前列腺肥大等）", "20.妇科疾病（子宫肌瘤等）", "21.乳腺疾病"});
        QuestionsVo questionsVo2 = new QuestionsVo(2, 1, 1, 1, "有血缘关系的家庭成员（父母 兄弟姐妹 祖父母）中，是否有人患有以下疾病，请在对应框中标记。",
                new String[]{"基本正常", "高血压", "糖尿病", "血脂异常", "缺血性心脏疾病（心肌梗塞  心绞痛）", "肺癌", "胃癌", "乳腺癌", "其他癌症"});
        QuestionsVo questionsVo3 = new QuestionsVo(3, 0, 1, "女", 1, "现在是在生理期吗？",
                new String[]{"是", "否"});
        QuestionsVo questionsVo4 = new QuestionsVo(4, 0, 1, "女", 1, "现在是在孕期吗？",
                new String[]{"有", "无"});
        QuestionsVo questionsVo5 = new QuestionsVo(5, 1, 1, 1, "自感症状请在下方进行选择",
                new String[]{"腹痛", "胃灼热", "食欲不振", "食物卡住的感觉", "易腹泻", "易便秘", "大便带血", "麻木感", "有痔疮", "眩晕", "心悸 脉搏乱", "胸痛   压迫感", "耳鸣", "咳嗽  痰多", "气喘", "尿频", "关节痛", "腰痛", "眼干", "飞蚊症"});
        QuestionsVo questionsVo6 = new QuestionsVo(6, 0, 1, 1, "现在正在服用降血压药吗？",
                new String[]{"是", "否"});
        QuestionsVo questionsVo7 = new QuestionsVo(7, 0, 1, 1, "现在正在服用降血糖药或者注射胰岛素吗？",
                new String[]{"是", "否"});
        QuestionsVo questionsVo8 = new QuestionsVo(8, 0, 1, 0, "现在正在服用降血脂的药物吗？",
                new String[]{"是", "否"});
        QuestionsVo questionsVo9 = new QuestionsVo(9, 0, 1, 0, "有脑出血、脑梗塞的病史吗？",
                new String[]{"是", "否"});
        QuestionsVo questionsVo10 = new QuestionsVo(10, 0, 1, 0, "有心脏病（心梗、心绞痛）病史吗？",
                new String[]{"是", "否"});
        QuestionsVo questionsVo11 = new QuestionsVo(11, 0, 1, 0, "有慢性肾病或者肾不全的病史吗？",
                new String[]{"是", "否"});
        QuestionsVo questionsVo12 = new QuestionsVo(12, 0, 1, 0, "有贫血病是吗？",
                new String[]{"是", "否"});
        QuestionsVo questionsVo13 = new QuestionsVo(13, 0, 1, 0, "有没有打算尝试改善生活习惯和饮食习惯？",
                new String[]{"不打算改善", "打算改善（6个月以内）", "最近（一个月以内）打算改善，逐渐改善", "已经开始改善（不到六个月）", "已经开始改善（六个月以上）"});
        QuestionsVo questionsVo14 = new QuestionsVo(14, 0, 1, 0, "关于改善生活习惯，如有接受保健指导的机会，会接受吗？",
                new String[]{"是", "否"});
        QuestionsVo questionsVo15 = new QuestionsVo(15, 0, 1, 0, "吃饭时间",
                new String[]{"规律", "有时候不规律", "不规律"});
        QuestionsVo questionsVo16 = new QuestionsVo(16, 0, 1, 0, "甜食",
                new String[]{"几乎不吃", "普通", "喜欢"});
        QuestionsVo questionsVo17 = new QuestionsVo(17, 0, 1, 0, "肉类",
                new String[]{"几乎不吃", "普通", "喜欢"});
        QuestionsVo questionsVo18 = new QuestionsVo(18, 0, 1, 0, "平时睡眠时间（平均）",
                new String[]{"7小时以上", "普通  4-6小时", "3小时以内"});
        QuestionsVo questionsVo19 = new QuestionsVo(19, 0, 1, 0, "入睡",
                new String[]{"良好", "普通", "困难"});
        QuestionsVo questionsVo20 = new QuestionsVo(20, 0, 1, 0, "是否进行一次30分钟以上轻微出汗的运动？",
                new String[]{"是", "否"});
        QuestionsVo questionsVo21 = new QuestionsVo(21, 0, 1, 0, "运动频率",
                new String[]{"6天以上/周", "3-5天以上/周", "1-2天以上/周", "1天/月"});
        QuestionsVo questionsVo22 = new QuestionsVo(22, 0, 1, 0, "运动项目",
                new String[]{"步行", "慢跑或马拉松", "游泳", "上班时步行"});
        QuestionsVo questionsVo23 = new QuestionsVo(23, 0, 1, 0, "是否饮酒？",
                new String[]{"是", "否"});
        QuestionsVo questionsVo24 = new QuestionsVo(24, 0, 1, 0, "是否抽烟？",
                new String[]{"是", "否"});
        Collections.addAll(array, questionsVo1, questionsVo2, questionsVo3, questionsVo4, questionsVo5, questionsVo6, questionsVo7, questionsVo8, questionsVo9, questionsVo10, questionsVo11,
                questionsVo12, questionsVo13, questionsVo14, questionsVo15, questionsVo16, questionsVo17, questionsVo18, questionsVo19, questionsVo20, questionsVo21, questionsVo22, questionsVo23, questionsVo24);
        return array;
    }

    /**
     * 最近体检人员列表
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<PaForReVo> getPatientForRegister(PageParam<PaForReVo> page, PaForReParam param) {
        //去空格大写
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        //开始时间结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        if (ObjectUtils.isNotEmpty(param.getDbtype())) {
            if ("0".equals(param.getDbtype())) {
                param.setP(0);
                param.setType(0);
            } else {
                param.setP(1);
                param.setType(1);
            }
        } else {
            param.setFlag(0);
            param.setP(0);
            param.setType(0);
        }
        IPage<PaForReVo> pages = registerMapper.getPatientForRegister(page, param);

        //设置序号
        int current = page.getCurrent() == 0 ? 0 : Math.toIntExact(page.getSize() * (page.getCurrent() - 1));
        List<PaForReVo> list = pages.getRecords();
        int i = 1;
        for (PaForReVo vo : list) {
            vo.setRownum(current + i);
            i++;
        }
        return pages;
    }

    /**
     * 根据体检号查询体检者信息
     *
     * @param patientCode
     * @param autoFill
     * @return
     */
    @Override
    public GetPeispatientVo getPeispatient(String patientCode, String autoFill) {
        Peispatient peispatient = getPeispatients(patientCode, autoFill);
        Double jxws = 0.0;//加项未收  用于收费页面
        Double txwt = 0.0;//加项退项未退 用于退费页面
        if (null == peispatient) {
            peispatient = new Peispatient();
        } else {
            // 是否禁检
            if (!StringUtils.isBlank(peispatient.getIdOrg())) {
                //体检者任务分组
                Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(peispatient.getIdOrgreservationgroup());
                if (null != group) {
                    //组启用
                    if (1 == group.getFGroupstarted()) {
                        peispatient.setFPaused(0);
                        //组禁用
                    } else if (1 == group.getFGrouppaused()) {
                        peispatient.setFPaused(1);
                    }
                }
            }
            //登记员
            SysUser user = iSysUserService.getUserByNo(peispatient.getIdDoctorreg());
            peispatient.setDoctorreg(null == user ? "" : user.getUserName());

            //体检者费用
            PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(peispatient.getPatientcode());
            if (pcm != null) {
                peispatient.setMoneyamount(pcm.getMoneyamount());
                peispatient.setMoneyamountpaid(pcm.getMoneyamountpaid());
            }
            //所有加项未收项目
            List<Peispatientfeeitem> pis = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", patientCode)
                    .or(i -> i.isNull("f_feecharged").eq("f_feecharged", 0))
                    .eq("sfjx", 1)
                    .eq("changeItem", 0)
            );
            for (Peispatientfeeitem pi : pis) {
                if (pi.getFactprice() != null) {
                    //优惠价格相加
                    jxws += pi.getFactprice();
                }
            }
            //所有加项项目退项未退
            List<Peispatientfeeitem> pis2 = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
                    .eq("idPatient", patientCode)
                    .or(i -> i.isNull("f_mark_feereturn").eq("f_mark_feereturn", 0))
                    .eq("sfjx", 1)
                    .eq("changeItem", 1)
            );
            for (Peispatientfeeitem pi : pis2) {
                if (pi.getEndtuiprice() != null) {
                    //退费价格相加
                    txwt += pi.getEndtuiprice();
                }
            }
        }
        //封装返回对象
        GetPeispatientVo vo = new GetPeispatientVo(peispatient, 1, jxws, txwt);
        return vo;
    }


    /**
     * 获取体检者信息
     *
     * @param patientCode
     * @param isChecked
     * @return
     */
    public Peispatient getPeispatients(String patientCode, String isChecked) {
        if (StringUtils.isBlank(patientCode)) {
            return new Peispatient();
        }
        // 匹配体检号
        if ("true".equals(isChecked)) {
            patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        }
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        return peispatient;
    }

    /**
     * 根据体检号查询不同状态的收费项目
     *
     * @param patientCode
     * @param type
     * @param inpatientno
     * @return
     */
    @Override
    public Map getKindItems(String patientCode, String type, String inpatientno) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Peispatient pei = peispatientMapper.getByPatientCode(patientCode);
        map.put("patient", pei);
        //体检者头像
        PeispatientPhoto peispatientPhoto = peispatientPhotoMapper.getByPatientCode(patientCode);
        map.put("picture", ObjectUtils.isNotEmpty(peispatientPhoto) ? peispatientPhoto.getPicture() : "");
        int qty = 1;
        List examfeeitemData = new ArrayList();
        map.put("examfeeitemData", examfeeitemData);
        //复查 或复查+补查
        if ("0".equals(type) || "2".equals(type)) {
            //现金
            String idPayway = Dictpayway.XJ;
            String numorgresv = null;
            //获取配置
            SysConfig qd = sysConfigService.getConfigByKey("HIS");

            if (qd != null && "Y".equals(qd.getConfigType())) {
                //体检者历史表
                Peispatienthistory ddhObj = peispatienthistoryMapper.getByPatientcode(inpatientno);
                numorgresv = ddhObj == null ? null : ddhObj.getNumorgresv();
            } else {
                //体检者表
                Peispatient zero = peispatientMapper.getByPatientCode(inpatientno);
                numorgresv = ObjectUtils.isNotEmpty(zero) && ObjectUtils.isNotEmpty(zero.getNumorgresv()) ? zero.getNumorgresv() : null;
            }

            Double zk = null;
            if (numorgresv != null) {
                //订单表
                Createorder order = createorderMapper.getInfoByDdh(numorgresv);
                if (order != null) {
                    if (order.getReviewPayway() != null) {
                        //复查收费方式
                        idPayway = order.getReviewPayway();
                    }
                    if (order.getReviewZk() != null) {
                        //复查优惠折扣
                        zk = order.getReviewZk();
                    }
                }
            }
            // 查询复查项目
            examfeeitemData.addAll(getRecheckItems(patientCode, idPayway, zk));
            qty += examfeeitemData.size();
            // 设置已复查
            Review review = reviewMapper.selectOne(new QueryWrapper<Review>().eq("patientcode", patientCode));
            String note = "第" + getFcTime(patientCode) + "次复查。";
            if (null != review) {
                // 已复查
                review.setCallbackStation(1);
                reviewMapper.updateById(review);
                if (review.getNoticeOfProceedingText() != null) {
                    //注意事项
                    note += review.getNoticeOfProceedingText();
                }
            }
            map.put("note", note);
        }
        if ("1".equals(type) || "2".equals(type)) {//补检  或  复查+补检
            // 补检项目
            List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
                    .eq("f_registered", 1)
                    .eq("f_feecharged", 1)
                    .eq("id_patient", patientCode)
                    .eq("f_transferedhl7", 0));
            List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
            for (Peispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                Map<String, Object> itemMap = new HashMap<String, Object>();
                itemMap.put("idExamfeeitem", peispatientfeeitem.getIdExamfeeitem());
                itemMap.put("examfeeitemName", peispatientfeeitem.getExamfeeitemName());
                itemMap.put("price", peispatientfeeitem.getPrice());
                itemMap.put("factprice", 0.0);//补检不收费
//                itemMap.put("factprice", peispatientfeeitem.getPrice());
                itemMap.put("count", 1);
                itemMap.put("idPayway", 1);
                itemMap.put("FRegistered", 0);
                itemMap.put("changeItem", 0);
                itemMap.put("FMarkFeereturn", 0);
                itemMap.put("FFeecharged", 0);
                itemMap.put("FLabsendtolis", 0);
                itemMap.put("FExaminated", 0);
                itemMap.put("FGiveup", 0);
                itemMap.put("FDelayed", 0);
                itemMap.put("isMintc", 0);
                itemMap.put("idKs", peispatientfeeitem.getIdKs());
                itemMap.put("isbx", 0);
                itemMap.put("bxcount", 0);
                itemMap.put("qty", qty++);
                items.add(itemMap);

            }
            examfeeitemData.addAll(items);
            //获取补检账户
            SysUser user = iSysUserService.getBjzh();
            //有补检账号就是补检账号，没补检账号就是上一次的开单医生
            if (user == null && pei.getIdOpendoctor() != null) {
                user = iSysUserService.getUserByNo(pei.getIdOpendoctor());
            }
            map.put("bjid", user == null ? "" : user.getUserNo());
            map.put("bjname", user == null ? "" : user.getUserName());
            if (null == pei) {
                map.put("noman", 0);
                throw new ServiceException(map.toString(), 200);
            } else map.put("noman", 1);
            // 检查剩余收费项目是否可以分拣完成
            //checkFj(pei);
        }
        return map;
    }


    /**
     * 获取复查项目
     *
     * @param patientCode
     * @param idPayway
     * @param zk
     * @return
     */
    private Collection getRecheckItems(String patientCode, String idPayway, Double zk) {
        List<RecheckItemsDto> list = reviewMapper.getRecheckItem(patientCode);
        List dataList = new ArrayList();
        // 转换成页面所需字段
        for (int j = 0; j < list.size(); j++) {
            RecheckItemsDto obj = list.get(j);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("idExamfeeitem", obj.getIdExamfeeitem());
            map.put("examfeeitemName", obj.getExamfeeitemName());
            map.put("price", obj.getPrice());
            // 优惠价格
            if (zk != null) {
                Double price = new Double(obj.getPrice().toString());
                map.put("factprice", NumUtil.keepDigit(price * zk / 10.0, 1));
            } else {
                map.put("factprice", obj.getPrice());
            }
            // 科室
            map.put("idKs", obj.getIdKs());
            // 数量
            map.put("count", 1);
            // 收费方式
            map.put("idPayway", idPayway);
            map.put("sfjx", 0);
            map.put("changeItem", 0);
            // 弃检
            map.put("FGiveup", 0);
            // 迟检
            map.put("FDelayed", 0);
            map.put("isMintc", 0);
            // 是否备选
            map.put("isbx", 0);
            // 备选数量
            map.put("bxcount", 0);
            map.put("qty", j + 1);
            dataList.add(map);
        }

        return dataList;
    }


    /**
     * 复查次数
     *
     * @param patientcode
     * @return
     */
    public int getFcTime(String patientcode) {

        Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
        //复查体检号
        String inpatientno = patient.getInpatientno();
        if (inpatientno == null) {
            return 1;
        }
        return peispatientMapper.selectList(new QueryWrapper<Peispatient>().eq("inpatientno", inpatientno)).size() + 1;
    }


    /**
     * 批量插入
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveBatchRegister(List<String> ids) {
        if (ZhongkangConfig.isOnline()) throw new ServiceException("线上不允许批量登记!");
        String fzxjm = StringUtils.isBlank(ZhongkangConfig.getFzxjm()) ? iSysBranchService.getBranchPrefix() : ZhongkangConfig.getFzxjm();
        List<Peispatient> list = new ArrayList<Peispatient>();

        Set<String> patientCodes = new HashSet<String>();
        for (int i = 0; i < ids.size(); i++) {
            Peispatient peispatient = peispatientMapper.getInfoById(ids.get(i));
            if (null == peispatient) {
                throw new ServiceException("操作失败：第" + (i + 1) + "条记录不存在，已经被删除！");
            } else if (null != peispatient.getFPaused() && peispatient.getFPaused() == 1) {
                throw new ServiceException("操作失败：第" + (i + 1) + "条记录 <font color='red'>" + peispatient.getPatientname() + "</font> 已经禁检，不能登记，请删除或者反禁检！");
            } else if (null != peispatient.getFRegistered() && peispatient.getFRegistered() == 1) {
                //throw new Exception("操作失败：第" + (i+1) +"条记录 <font color='red'>"+peispatient.getPatientname()+"</font> 已经登记，不能再次登记！");
                continue;
            } else if (StringUtils.isBlank(peispatient.getPatientcode())) {
                throw new ServiceException("操作失败：第" + (i + 1) + "条记录 <font color='red'>" + peispatient.getPatientname() + "</font> 体检号不存在，不能批量登记！");
            }
            if (peispatient.getIdOrgreservation() != null
                    && peisorgreservationMapper.selectCount(new QueryWrapper<Peisorgreservation>().eq("id", peispatient.getIdOrgreservation()).eq("f_finished", 1)) != 0) {
                throw new ServiceException("操作失败：第" + (i + 1) + "条记录的订单已结束，不能登记！");
            }

            if (StringUtils.isEmpty(peispatient.getPhone()) ) {
                throw new ServiceException("操作失败：第" + (i + 1) + "条手机号不能为空！");
            }
            if (StringUtils.isEmpty(peispatient.getIdcardno()) ) {
                throw new ServiceException("操作失败：第" + (i + 1) + "条证件号不能为空！");
            }
            //职业和综合的工种不能为空
            if ("1".equals(peispatient.getIdExamtype()) || "2".equals(peispatient.getIdExamtype())){
                if (StringUtils.isEmpty(peispatient.getWorktypeId()) ) {
                    throw new ServiceException("操作失败：第" + (i + 1) + "条工种不能为空！");
                }
            }
            //获取未退费的项目数量
            int count = peispatientfeeitemService.getUnreimbursedProjects(peispatient.getPatientcode());
            if (count == 0){
                throw new ServiceException("操作失败：第" + (i + 1) + "条收费项目不能为空！");
            }
            log.info("批量登记体检者信息：{}",peispatient);
            //@sqlorder
            peispatient.setModifydate(new Date());


            patientCodes.add(peispatient.getPatientcode());
            boolean isApp = false;
            // 登记
            peispatient.setFRegistered(1);
            if (StringUtils.isBlank(peispatient.getHospitalcode())) {
                //获取默认的cid
                peispatient.setHospitalcode(sysBranchMapper.getDefaultCid());
            }
            peispatient.setDateregister(new Date());
            peispatient.setIdDoctorreg(SecurityUtils.getUserNo());
            peispatient.setDoctorreg(SecurityUtils.getUsername());
            // 体检时间
            peispatient.setMedicaldate(new Date());
            setIdPatientclass2(peispatient, 0);
            //记录是批量登记的
            PeisState ps = peisStateService.getByPatientcode(peispatient.getPatientcode());
            ps.setIsBatchRegistered(1);
            peisStateService.updateById(ps);
            // 体检号不存在
            String patientCode = peispatient.getPatientcode();
            if (StringUtils.isBlank(patientCode)) {
                do {
                    //线上生成app开头的，线下生成本中心的体检号
                    if (ZhongkangConfig.isOnline()) {
                        patientCode = CodeUtil.getPatientCode(Constants.ONLINE_PREFIX, iSysConfigService.selectConfigByKey(Constants.VERSION_NO));
                    } else {
                        patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(null), "");
                    }
                    //判断体检号是否存在
                } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                        .eq(Peispatient::getPatientcode, patientCode)) > 0);
                peispatient.setPatientcode(patientCode);
                peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
                peispatientMapper.updateById(peispatient);
            } else if (patientCode.indexOf(Constants.ONLINE_PREFIX) != -1 || !patientCode.startsWith(fzxjm)) {
                isApp = true;
                Reservation reservation1 = reservationService.getOne(new LambdaQueryWrapper<Reservation>()
                        .eq(Reservation::getPatientcode, patientCode)
                        .ne(Reservation::getStatus,-1)
                );

                String newPatientCode = CodeUtil.appConvert(iSysBranchService.getBranchFlag(null), "", patientCode);
                //更新预约信息
                if (ObjectUtils.isNotEmpty(reservation1)) {
                    reservation1.setPatientcode(newPatientCode);
                    reservationService.updateById(reservation1);
                }
                peispatient.setPatientcode(newPatientCode);
                peispatient.setShortCode(ToolUtil.getShortCodeByLong(newPatientCode));
                peispatientMapper.updateById(peispatient);
                //体检者收费项目
                List<Peispatientfeeitem> items = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>().eq("id_patient", patientCode));
                for (Peispatientfeeitem item : items) {
                    item.setIdPatient(newPatientCode);
                }
                peispatientfeeitemService.updateBatchById(items);
                //体检者缴费表
                List<Peispatientcharge> charges = peispatientchargeService.list(new QueryWrapper<Peispatientcharge>()
                        .eq("patientcode", patientCode));
                for (Peispatientcharge charge : charges) {
                    charge.setPatientcode(newPatientCode);
                    charge.setShortCode(peispatient.getShortCode());
                    peispatientchargeService.updateById(charge);
                }
                //体检者费用主表
                PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientCode);
                if (pcm != null) {
                    pcm.setPatientcode(newPatientCode);
                    peispatientChargeMainMapper.updateById(pcm);
                }
                //更新体检者上传状态
                ps.setPatientcode(newPatientCode);
                peisStateService.updateById(ps);
                patientCode = newPatientCode;
            }
            //体检者头像
            PeispatientPhoto photo = peispatientPhotoMapper.getByPatientCode(patientCode);
            if (photo == null) {
                photo = new PeispatientPhoto();
                photo.setPatientcode(patientCode);
                peispatientPhotoMapper.insert(photo);
            } else {
                photo.setPatientcode(patientCode);
            }

            // 获取体检者收费项目,未退费的
            List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>()
                    .eq(Peispatientfeeitem::getIdPatient, peispatient.getPatientcode())
                    .and(j -> j.isNull(Peispatientfeeitem::getFMarkFeereturn).or().eq(Peispatientfeeitem::getFMarkFeereturn, 0))
            );

            Double charge = 0d;
            Double yj = 0d;
            SysUser user = iSysUserService.getUserByNo(SecurityUtils.getUserNo());
            for (int j = 0; j < peispatientfeeitems.size(); j++) {
                Peispatientfeeitem peispatientfeeitem = peispatientfeeitems.get(j);
                //校验收费方式
                if (StringUtils.isNotEmpty(peispatientfeeitem.getIdPayway()) && "1".equals(peispatientfeeitem.getIdPayway())) {
                    throw new ServiceException("体检号："+patientCode+",收费项目:" + peispatientfeeitem.getExamfeeitemName() + "的收费方式是现金，不能批量登记!");
                }
                charge += peispatientfeeitem.getFactprice();
                yj += peispatientfeeitem.getPrice();
                peispatientfeeitem.setFRegistered(1);// 登记
                peispatientfeeitem.setIdPatient(patientCode);
                peispatientfeeitem.setShortCode(peispatient.getShortCode());
                peispatientfeeitem.setFGiveup(0);// 弃检
                peispatientfeeitem.setFDelayed(0);// 迟检
                peispatientfeeitem.setSfjx(0);// 是否加项
                peispatientfeeitem.setFExaminated(0);// 未检
                peispatientfeeitem.setIdDoctorreg(SecurityUtils.getUserNo());
                peispatientfeeitem.setDoctorregR(user.getUserName());
                peispatientfeeitem.setRegistertime(new Date());
            }
            //原价
            peispatient.setPersonpricelimit(yj);
            // 批量保存收费项目
            peispatientfeeitemService.updateBatchById(peispatientfeeitems);
            // 添加收费信息
            PeispatientchargeDto peispatientchargeDto = new PeispatientchargeDto();
            peispatientchargeDto.setMoneyamount(charge);
            peispatientchargeDto.setMoneyamountpaid(charge);
            peispatientchargeDto.setPatientcode(peispatient.getPatientcode());
            Boolean chargeData = peispatientchargeService.autoSaveOrUpdate(peispatientchargeDto);
            if (!chargeData) {
                throw new ServiceException("操作失败：第" + (i + 1) + "条记录保存收费信息发生异常！");
            }
            // 绑定档案
            Map<String, Object> mData = new HashMap<String, Object>();
            Peispatient p = new Peispatient();
            p.setPatientarchiveno(peispatient.getPatientarchiveno());
            mData.put("patientarchiveno", peispatient.getPatientarchiveno());
            mData.put("idcardno", peispatient.getIdcardno());
            mData.put("countreportoccupationxml", peispatient.getCountreportoccupationxml() == null ? null : peispatient.getCountreportoccupationxml());
            mData.put("patientname", peispatient.getPatientname());
            mData.put("inputCode", ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
            mData.put("idSex", peispatient.getIdSex());
            mData.put("age", null == peispatient.getAge() ? 0 : peispatient.getAge());
            mData.put("birthdate", peispatient.getBirthdate());
            mData.put("phone", peispatient.getPhone());
            mData.put("idMarriage", String.valueOf(peispatient.getIdMarriage()));
            mData.put("idNation", peispatient.getIdNation());
            mData.put("address", peispatient.getAddress());
            mData.put("isHmd", null == peispatient.getIsHmd() ? 0 : peispatient.getIsHmd());
            mData.put("isHmdb", peispatient.getIsHmdb());
            mData.put("cultural", 0);
            mData.put("idPatientarchive", peispatient.getPatientarchiveno());
            mData.put("memberlevel", peispatient.getIdPatientclass());
            String bindResult = bingIArchive(peispatient, mData, true);
            if (bindResult.indexOf("success") == 0) {
//                String idPatientarchive = bindResult.substring(8, 40);
                String patientarchiveno = bindResult.substring(8);
//                peispatient.setPatientarchiveno(idPatientarchive);
                peispatient.setPatientarchiveno(patientarchiveno);
                peispatient.setMoneyamount(charge);
                peispatient.setMoneyamountpaid(charge);

                //体检者费用主表
                PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientCode);
                String newNote = SecurityUtils.getUsername() + "批量登记于" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ";";
                if (pcm == null) {
                    throw new ServiceException("保存失败：该体检者 " + peispatient.getPatientname() + " 收费主表不存在！");
                } else {
                    pcm.setMoneyamount(charge);
                    pcm.setMoneyamountpaid(charge);
                    pcm.setNote(newNote);
                }
                peispatientChargeMainMapper.updateById(pcm);
            } else {
                throw new ServiceException("保存失败：该体检者 " + peispatient.getPatientname() + " 档案绑定失败！");
            }
//            2019-7-15关闭自动升级VIP
//            setVip(peispatient);
            list.add(peispatient);
        }
        //添加或更新
        peispatientService.saveOrUpdateBatch(list);

        //插入PACS
        // TODO: wait 插入pacs未完成
//        if (publicUtil.getDictionaryStatusByType("PACS")
//        ) {
//            for (String patientCode : patientCodes) {
//                String msg = savePacs(patientCode);
//                if (!"success".equals(msg)) {
//                    throw new ServiceException("插入PACS数据异常！");
//                }
//            }
//        }
//        //插入独立PACS
//        if ("1".equals(ToolUtil.getXmlPro("pacs", "isOpen"))) {
//            for (String patientCode : patientCodes) {
//                String msg = savePacs2(patientCode);
//                if (!"success".equals(msg)) {
//                    throw new ServiceException("保存失败,插入PACS数据异常！");
//                }
//            }
//        }
        return "success";
    }

    @Transactional(rollbackFor = Exception.class)
    public void setIdPatientclass2(Peispatient patient, int value) {
        //体检者上传状态
        PeisState ps = peisStateService.getByPatientcode(patient.getPatientcode());
        if (ps == null) {
            ps = new PeisState(patient.getPatientcode());
            ps.setIdPatientclass2(value);
            peisStateService.save(ps);
        } else {
            ps.setIdPatientclass2(value);
            peisStateService.updateById(ps);
        }
    }


    /**
     * @param peispatient
     * @param map
     * @return 将archive的会员级别覆盖至peispatient
     * @throws Exception String
     * @Title: 绑定档案
     * @author zhanghj
     * @since 2016-9-24 V 1.0
     */
    private String bingIArchive(Peispatient peispatient, Map map, boolean isBatch) {
        String patientarchiveno = peispatient.getPatientarchiveno();

        // 判断档案是否存在或者人为取消（重新创建档案）
        if (StringUtils.isBlank(patientarchiveno)
                || "0".equals(patientarchiveno)
                || "1".equals(patientarchiveno)) {
            String name = peispatient.getPatientname();
            String phone = peispatient.getPhone();
            String idcardno = peispatient.getIdcardno();

            map.put("dateregister", new Date());
            map.put("memberlevel", map.get("idPatientclass"));
            map.put("ishmd", "null".equals(map.get("isHmd")) ? 0 : Integer.parseInt(map.get("isHmd").toString()));
            map.put("hmdbz", map.get("isHmdb"));
            //创建新档案
            if ("1".equals(patientarchiveno)) {
                // 生成档案号
                patientarchiveno = peispatientarchiveService.getArchiveCode();
                map.put("patientarchiveno", patientarchiveno);
                map.put("membercreate", SecurityUtils.getUserNo());
                map.put("inputCode", ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
                // 保存档案
                saveRecord(map);
                if (StringUtils.isBlank(patientarchiveno)) {
                    throw new ServiceException("保存失败：创建档案失败!");
                }

                return "success:" + patientarchiveno;
            }
            QueryWrapper<Peispatientarchive> and = new QueryWrapper<>();
            and.or(i -> i.eq("is_delete", 0).isNull("is_delete"));
            // 判断是否为空
            if (!StringUtils.isBlank(idcardno)) {
                // 匹配档案
                and.eq("idcardno", idcardno);
                List<Peispatientarchive> archives = peispatientarchiveService.list(and);
                if (archives.size() > 1) {
                    // 前台弹出档案列表进行选择
                    throw new ServiceException("-1");
                } else if (archives.size() == 1) {//匹配到唯一档案
                    Peispatientarchive archive = archives.get(0);
                    // 档案号
                    patientarchiveno = archive.getPatientarchiveno();
                    // 更新档案黑名单
                    archive.setIshmd(peispatient.getIsHmd());
                    // 最后一次体检时间（登记日期）
                    archive.setDateregister(new Date());
                    // 档案是否团检（0：个人 1：团检）
                    archive.setIsOrg(StringUtils.isBlank(peispatient.getIdOrg()) ? 0 : 1);
                    //保存、登记时要修改电话
                    archive.setPhone(peispatient.getPhone());

                    String archiveClass = archive.getMemberlevel();
                    String patientClass = peispatient.getIdPatientclass();//必填字段
                    if (archiveClass == null) {
                        archive.setMemberlevel(patientClass);
                    } else {
                        int i = archiveClass.compareTo(patientClass);
                        if (i < 0) {////体检者的等级如果修改大于档案中的登记，就修改档案中的等级
                            archive.setMemberlevel(patientClass);
                        } else if (i > 0) {//如果小于档案中的等级，就直接调取档案中的登记，覆盖体检者的等级。
                            //2019.7.4 需要能编辑VIP等级，去掉
                            //peispatient.setIdPatientclass(archiveClass);
                        }
                    }
                    peispatientarchiveService.updateById(archive);
                } else if (archives.size() == 0) {//未匹配到  创建新档案
                    // 生成档案号
                    patientarchiveno = peispatientarchiveService.getArchiveCode();
                    map.put("patientarchiveno", patientarchiveno);
                    map.put("membercreate", SecurityUtils.getUserNo());
                    map.put("inputCode", ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
                    // 保存档案
                    saveRecord(map);
                    if (StringUtils.isBlank(patientarchiveno)) {
                        throw new ServiceException("保存失败：创建档案失败!");
                    }
                }
            } else {
                // 判断是否为空
                if (!StringUtils.isBlank(name)) {
                    and.eq("patientname", name);
                }
                if (!StringUtils.isBlank(phone)) {
                    and.eq("phone", phone);
                }
                List<Peispatientarchive> archives = peispatientarchiveMapper.selectList(and);
                if (!isBatch//如果是批量登记，只匹配有身份证的，没有身份证的生成新档案。如果要匹配，需要到前台登记匹配
                        && archives.size() > 0) {
                    // 前台弹出档案列表进行选择
                    throw new ServiceException("-1");
                } else {
                    // 生成档案号
                    patientarchiveno = peispatientarchiveService.getArchiveCode();
                    map.put("patientarchiveno", patientarchiveno);
                    map.put("membercreate", SecurityUtils.getUserNo());
                    map.put("inputCode", ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
                    // 保存档案
                    saveRecord(map);
                    if (StringUtils.isBlank(patientarchiveno)) {
                        throw new ServiceException("保存失败：创建档案失败！");
                    }
                }
            }
        } else {
            // 已经选择档案号
            Peispatientarchive archive = peispatientarchiveMapper.getInfoByNo(patientarchiveno);
            if (null == archive) {
                throw new ServiceException("保存失败：选择的档案不存在，已经被删除!");
            }
            patientarchiveno = archive.getPatientarchiveno();
            // 更新档案黑名单
            archive.setIshmd(peispatient.getIsHmd());
            archive.setHmdbz(peispatient.getIsHmdb());
            // 最后一次体检时间（登记日期）
            archive.setDateregister(new Date());
            // 档案是否团检（0：个人 1：团检）
            archive.setIsOrg(StringUtils.isBlank(peispatient.getIdOrg()) ? 0 : 1);
            //保存、登记时要修改电话
            archive.setPhone(peispatient.getPhone());

            String archiveClass = archive.getMemberlevel();
            String patientClass = peispatient.getIdPatientclass();//必填字段
            if (archiveClass == null) {
                archive.setMemberlevel(patientClass);
            } else {
                int i = archiveClass.compareTo(patientClass);
                if (i < 0) {//体检者的等级如果修改大于档案中的登记，就修改档案中的等级
                    archive.setMemberlevel(patientClass);
                } else if (i > 0) {//如果小于档案中的等级，就直接调取档案中的登记，覆盖体检者的等级。
                    //peispatient.setIdPatientclass(archiveClass);
                }
            }
            peispatientarchiveMapper.updateById(archive);
        }

        return "success:" + patientarchiveno;
    }


    /**
     * @Title: 保存档案
     */
    private String saveRecord(Map map) {
        // 最后一次体检时间（登记日期）
        map.put("dateregister", new Date());
        // 档案是否团检（0：个人 1：团检）
        map.put("isOrg", null == map.get("idOrg") ? 0 : 1);
        map.put("note", null);
        // 转化实体类
        Peispatientarchive patientArchive = mapperFacade.map(map, Peispatientarchive.class);
        // 会员级别
        patientArchive.setMemberlevel(null == map.get("idPatientclass") ? "0" : map.get("idPatientclass").toString());
        // 积分
        patientArchive.setJf(0D);
        patientArchive.setFzx(SecurityUtils.getCId());

        // 保存实体，成功返回id，失败返回null
        boolean b = peispatientarchiveService.save(patientArchive);
        return b ? patientArchive.getId() : null;
    }

    /**
     * 批量设置统收限额
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saveTsLimitEdit(TsLimitEditParam param) {
        if (ObjectUtils.isNotEmpty(param)) {
            //取出参数
            String order = param.getOrder();
            Integer ageStart = param.getAgeStart();
            Integer ageEnd = param.getAgeEnd();
            String gender = param.getGender();
            String money = param.getMoney();
            String jctcId = param.getJctcId();
            QueryWrapper<Peispatient> con = new QueryWrapper<>();

            if (order != null) {
                con.eq("numorgresv", order);
            }
            if (ageStart != null) {
                con.ge("age", ageStart);
            }
            if (ageEnd != null) {
                con.le("age", ageEnd);
            }
            if (StringUtils.isNotEmpty(gender)) {
                con.eq("id_sex", gender);
            }
            if (StringUtils.isNotEmpty(jctcId)) {
                con.eq("idTjtc", jctcId);
            }
            List<Peispatient> list = peispatientMapper.selectList(con);
            for (Peispatient peispatient : list) {
                peispatient.setTsLimit(Double.valueOf(money));
            }
            peispatientService.updateBatchById(list);
        }
        return Boolean.TRUE;
    }


    /**
     * 修改体检者开单医师和备注
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saveEdit(RCSaveEditParam param) {
        //体检者表
        Peispatient patient = peispatientMapper.getInfoById(param.getId());
        patient.setDoctorapply(param.getDoctorapply());
        patient.setIdOpendoctor(param.getIdOpendoctor());
        patient.setNote(param.getNote());
        patient.setPhone(param.getPhone());
        peispatientMapper.updateById(patient);

        //修改档案
        Peispatientarchive peispatientarchive = peispatientarchiveMapper.getInfoByNo(patient.getPatientarchiveno());
        if (ObjectUtils.isNotEmpty(peispatientarchive)){
            peispatientarchive.setPhone(param.getPhone());
            peispatientarchiveMapper.updateById(peispatientarchive);
        }

        return Boolean.TRUE;
    }


    /**
     * 批量登记查询
     *
     * @param patientcode
     * @return
     */
    @Override
    public PatientForOrderIdVo getPatientForCode(String patientcode) {
        PatientForOrderIdVo vo = registerMapper.getPatientForOrderIdSql(patientcode);
        if (ObjectUtils.isEmpty(vo)) {
            throw new ServiceException("error@体检号不存在");
        }
        String patientcode1 = vo.getPatientcode();
        List<Peispatientfeeitem> pis = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_payway", "1").eq("change_item", 0)
                .eq("id_patient", patientcode1));
        if (pis.size() > 0) {
            throw new ServiceException(patientcode + "为现金收费，不可使用批量登记，请用登记页面登记。");
        }
        return vo;
    }


    /**
     * 导出excel数据
     *
     * @param param
     * @return
     */
    @Override
    public List<RCExportVo> getExportData(RegisterParam param) {
        String jm = "";
        if (Objects.nonNull(param.getAutoFill()) && param.getAutoFill()) {
            if (StringUtils.isNotBlank(param.getJm())) {
                //线上登记
                jm = param.getJm();
            } else {
                //线下登记
                SysBranch branch = sysBranchMapper.selectOne(new LambdaQueryWrapper<SysBranch>().eq(SysBranch::getIsDefault, 1));
                jm = branch.getJm();
            }
        }
        if (StringUtils.isNotBlank(param.getPtcodeFrom()) && StringUtils.isNotBlank(param.getPtcodeTo())) {
            ptcodeFrom = "";
            String ptcodeTo = "";
            if (StringUtils.isNotBlank(param.getPtcodeFrom())) {
                ptcodeFrom = param.getPtcodeFrom().trim();
                if (StringUtils.isNotBlank(jm) && ptcodeFrom.length() != 12) {
                    ptcodeFrom = jm + String.format("%10s", ptcodeFrom).replace(" ", "0");
                }
            }
            if (StringUtils.isNotBlank(param.getPtcodeTo())) {
                ptcodeTo = param.getPtcodeTo().trim();
                if (StringUtils.isNotBlank(jm) && ptcodeTo.length() != 12) {
                    ptcodeTo = jm + String.format("%10s", ptcodeTo).replace(" ", "0");
                }
            }
            param.setPtcodeFrom(ptcodeFrom);
            param.setPtcodeTo(ptcodeTo);
        }

        Boolean greatLeader = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (greatLeader){
            //决策管理看全部
        }else if (SecurityUtils.isLeader()){
            // 领导查看下级的数据
            List<String> lowerLevelIds = sysUserMapper.getLowerLevelId(SecurityUtils.getUserNo());
            lowerLevelIds.add(SecurityUtils.getUserNo());
            param.setLowerLevelIds(lowerLevelIds);
        }else {
            String userNo = SecurityUtils.getUserNo();
            param.setUserNo(userNo);
            param.setUserName(SecurityUtils.getUsername());
        }


        List<RCExportVo> list = registerMapper.getExportData(param);
        int i = 1;
        for (RCExportVo record : list) {
            int groupLimit = (record.getFGroupstarted() == null || record.getFGrouppaused() == null) ? 0 : (record.getFGroupstarted() == 1
                    && record.getFGrouppaused() == 0) ? 0 : 1;
            int pLimit = null == record.getFPaused() ? 0 : record.getFPaused();
            // 0:反禁检1:禁检
            record.setIspaused((groupLimit == 1 || pLimit == 1) ? 1 : 0);
            record.setRownum(i);
            i++;

        }

        return list;
    }


    /**
     * 导出登记信息列表
     *
     * @param param
     * @return
     */
    @Override
    public List<PaForReVo> getPaExportData(PaForReParam param) {
        //去空格大写
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        //开始时间结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        if (ObjectUtils.isNotEmpty(param.getDbtype())) {
            if ("0".equals(param.getDbtype())) {
                param.setP(0);
                param.setType(0);
            } else {
                param.setP(1);
                param.setType(1);
            }
        } else {
            param.setFlag(0);
            param.setP(0);
            param.setType(0);
        }
        List<PaForReVo> list = registerMapper.getPaExportData(param);
        int i = 1;
        for (PaForReVo vo : list) {
            vo.setRownum(i);
            i++;
        }

        return list;
    }


    /**
     * 缴费单打印数据
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> chargeDataPrint(String id) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // 存储缴费单信息
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(id)) {
            map.put("success", false);
            map.put("msg", "缴费单打印失败：系统发生异常，请联系管理员！");
            list.add(map);
            return list;
        }
        //体检者表
        Peispatient peispatient = peispatientMapper.getInfoById(id);
        // 判断该体检者是否存在
        if (null == peispatient) {
            map.put("success", false);
            map.put("msg", "导引单打印失败：该体检者不存在，已经被删除！");
            list.add(map);
            return list;
        }

        // 获取收费项目
        List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
                .orderByAsc("id_ks")
                .eq("id_patient", peispatient.getPatientcode())
                .eq("change_item", 0)
                .eq("sfjj", 0)
                .eq("f_giveup", 0));
        // 相对应的收费信息不存在
        if (peispatientfeeitems.size() == 0) {
            map.put("success", false);
            map.put("msg", "导引单打印失败：<font color='red'>" + peispatient.getPatientname() + "</font> 体检者相对应的收费项目信息不存在！");
            list.add(map);
            return list;
        }
        map.put("patientCode", peispatient.getPatientcode());
        map.put("name", peispatient.getPatientname());
        map.put("sex", ObjectUtils.isEmpty(peispatient.getIdSex()) ? "" : (peispatient.getIdSex() == 0 ? "男" : "女"));
        map.put("age", peispatient.getAge());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        map.put("dateregister", ObjectUtils.isEmpty(peispatient.getDateregister()) ? "" : format.format(peispatient.getDateregister()));
        List<Map<String, Object>> eaxms = new ArrayList<Map<String, Object>>();
        for (int j = 0; j < peispatientfeeitems.size(); j++) {
            // 获取基础收费项目
            Items items = itemsMapper.getInfoById(peispatientfeeitems.get(j).getIdExamfeeitem());
            // 相对应的基础收费项目不存在
            if (null == items) {
                map.put("success", false);
                map.put("msg", "导引单打印失败：该体检者的第 <font color='red'>" + (j + 1) + "</font> 个收费项目所对应的基础收费项目信息不存在！");
                list.add(map);

                return list;
            }
            Map<String, Object> eaxmsMap = new HashMap<String, Object>();
            eaxmsMap.put("itemname", items.getExamfeeitemName());
            eaxmsMap.put("cx", null == items.getCx() ? "" : items.getCx());
            eaxmsMap.put("FFeecharged", (null == peispatientfeeitems.get(j).getFFeecharged() || 0 == peispatientfeeitems.get(j).getFFeecharged()) ? "未收" : "已收");
            eaxmsMap.put("price", peispatientfeeitems.get(j).getFactprice());
            eaxms.add(eaxmsMap);
        }
        // 按照餐序排序
        sort(eaxms, "cx");
        map.put("success", true);
        map.put("items", eaxms);
        // 收费信息
        List charges = getChargeData(peispatient.getPatientcode());
        List<HashMap<String, Object>> newCharge = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < charges.size(); i++) {
            HashMap<String, String> charge = (HashMap<String, String>) charges.get(i);
            HashMap<String, Object> m = new HashMap<String, Object>();
            m.put("feechargetime", ObjectUtils.isEmpty(charge.get("feechargetime")) ? "" : charge.get("feechargetime"));
            Dictpayway payway = dictpaywayMapper.getInfoById(charge.get("idPayway"));
            m.put("payway", null == payway ? "" : payway.getPaywayName());
            m.put("FFeecharged", (ObjectUtils.isEmpty(charge.get("fFeecharged")) || "0".equals(String.valueOf(charge.get("fFeecharged")))) ? "未结" : "已结");
            m.put("moneyamountpaid", charge.get("moneyamountpaid"));
            m.put("idFeecharger", charge.get("idFeecharger"));
            newCharge.add(m);
        }
        map.put("charges", newCharge);
        list.add(map);
        return list;
    }

    /**
     * 通过身份证号获取体检号
     *
     * @param idCard
     * @return
     */
    @Override
    public List<IdcarPatientVo> getPatientcodeByIdcard(String idCard) {
        return registerMapper.getPatientcodeByIdcard(idCard);
    }


    /**
     * @param targetList 目标list
     * @param sortField  排序字段
     *                   void
     * @Title: 根据字段排序
     * @author zhanghj
     * @since 2016-9-17 V 1.0
     */
    public void sort(List<Map<String, Object>> targetList, final String sortField) {
        Collections.sort(targetList, new Comparator() {
            public int compare(Object obj1, Object obj2) {
                Map<String, Object> map1 = (Map<String, Object>) obj1;
                Map<String, Object> map2 = (Map<String, Object>) obj2;

                String m1 = null == map1.get(sortField) ? "" : map1.get(sortField).toString();
                String m2 = null == map2.get(sortField) ? "" : map2.get(sortField).toString();
                return m1.compareTo(m2);
            }
        });
    }


    public List getChargeData(String patientCode) {
        // 判断是否为空
        if (StringUtils.isBlank(patientCode)) {
            return new ArrayList();
        }

        //体检者缴费表
        List<Peispatientcharge> list = peispatientchargeMapper.selectList(new QueryWrapper<Peispatientcharge>().orderByAsc("num_index")
                .eq("patientcode", patientCode).eq("is_delete", 0));
        //转map
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> dataMap = BeanUtil.beanToMap(list.get(i));
            //支付方式表
            Dictpayway dictPayWay = dictpaywayMapper.getInfoById(list.get(i).getIdPayway());
            dataMap.put("isChange", ObjectUtils.isNotEmpty(dictPayWay) ? dictPayWay.getIsChange() : "");
            listMap.add(dataMap);
        }
        return listMap;
    }


    /**
     * 保存发送短信
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdateMsg(SaveOrUpdateMsgParam param) {
        List<String> patientCodes = param.getPatientcodes();
        String idTem = param.getIdTemplate();
        Shortmessage message = shortmessageMapper.getInfoById(idTem);
        String appId = message.getAppid();//appid
        String idTemplate = message.getTemplateId();//模板id
        String messageType = message.getMessageType();
        Integer isImmediately = "true".equals(param.getImmediately()) ? 1 : 0;//是否立即发送
        Date sendTimeStr = param.getSendTime();
        Date notifyTime = (ObjectUtils.isEmpty(sendTimeStr) || isImmediately.intValue() == 1) ? new Date() : sendTimeStr;//通知时间
        String result = "success";
        String creater = SecurityUtils.getUsername();//操作人
        String messageText = message.getMessageText();
        String notifyContent = ToolUtil.getSmsContent(messageText, null);
        //立即发送
        if (isImmediately.intValue() == 1) {
            StringBuilder builder = new StringBuilder();
            boolean isSuccess = false;
            for (String patientCode : patientCodes) {
                List<SmsRecord> records = smsRecordMapper.selectList(new QueryWrapper<SmsRecord>()
                        .orderByDesc("createDate").eq("patientcode", patientCode)
                        .eq("notify_type", messageType).eq("notify_result", 2));//等待通知 可以修改
                SmsRecord record = null;
                if (records.size() == 0) {
                    record = new SmsRecord(messageType, patientCode);
                } else {
                    record = records.get(0);
                }
                record.setCreater(creater);
                record.setIsImmediately(isImmediately);
                record.setIdTemplate(idTem);
                record.setNotifyTime(notifyTime);
                record.setNotifyContent(notifyContent);
                Peispatient patient = peispatientMapper.getByPatientCode(patientCode);
                try {
                    SmsConfig smsConfig = iSysConfigService.getSysConfigObject(Constants.SMS_CONFIG,SmsConfig.class);
                    String result_one = SDKTestSendTemplateSMS.sendMsg(smsConfig,patient.getPhone(), idTemplate, null, appId);
                    if (!"success".equals(result_one)) {
                        builder.append("体检号" + patientCode + "<font color='red'>发送失败！</font>," + result_one);
                    } else {
                        builder.append("体检号" + patientCode + "<font color='green'>发送成功！</font>");
                        isSuccess = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    builder.append("体检号" + patientCode + "<font color='red'>发送失败！</font>," + e.getMessage());
                }
                builder.append("</br>");
                if (isSuccess) {
                    record.setNotifyResult(3);
                } else {
                    record.setNotifyResult(0);
                }
                smsRecordMapper.insert(record);
            }
            result = builder.toString();
        } else {//定时发送
            for (String patientCode : patientCodes) {
                List<SmsRecord> records = smsRecordMapper.selectList(new QueryWrapper<SmsRecord>()
                        .orderByDesc("createDate").eq("patientcode", patientCode)
                        .eq("notify_type", messageType).eq("notify_result", 2));//等待通知 可以修改
                SmsRecord record = null;
                if (records.size() == 0) {
                    record = new SmsRecord(messageType, patientCode);
                } else {
                    record = records.get(0);
                }
                record.setCreater(creater);
                record.setNotifyContent(notifyContent);
                record.setIsImmediately(isImmediately);
                record.setIdTemplate(idTem);
                record.setNotifyTime(notifyTime);
                record.setNotifyResult(2);
                smsRecordService.saveOrUpdate(record);
            }
        }
        return result;
    }


    /**
     * 取消发送短信
     *
     * @param patientcodes
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelSmsPredetection(List<String> patientcodes) {
        for (String patientcode : patientcodes) {
            SmsRecord record = smsRecordMapper.selectOne(new QueryWrapper<SmsRecord>()
                    .eq("notify_type", ShortMessageType.PREDETECTION)
                    .eq("patientcode", patientcode)
                    .eq("notify_result", 2));
            if (record == null) {
                continue;
            }
            record.setNotifyResult(1);
            smsRecordMapper.updateById(record);
        }
        return Boolean.TRUE;
    }


    /**
     * 判断支付方式的金额是否可以退
     *
     * @param param
     * @return
     */
    @Override
    public Boolean checkRefund(CheckRefundParam param) {
        //查询对应收费方式的收费记录
        List<Peispatientcharge> list = peispatientchargeService.list(new LambdaQueryWrapper<Peispatientcharge>()
                .eq(Peispatientcharge::getPatientcode, param.getPatientcode())
                .eq(Peispatientcharge::getIdPayway, param.getIdPayway())
                .eq(Peispatientcharge::getFFeecharged, 1)
                .ne(Peispatientcharge::getFIsreturn, 1)
        );
        double sum = list.stream().mapToDouble(Peispatientcharge::getMoneyamountpaid).sum();
        log.info("算出来的金额是:{}", sum);
        return sum >= param.getMoneyamountpaid() ? true : false;
    }


    /**
     * 体检卡退费
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map receiveTjkCard(ReceiveTjkCardParam param) {
        Long version = param.getVersion();
        String patientcode = param.getPatientcode();
        TJKFormDataParam formdata = param.getFormdata();
        TJKDataParam data = param.getData();
        // 判断版本
        PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientcode);
        if (pcm == null) {
            throw new ServiceException(patientcode + "未找到收费信息主表！");
        }
        // version==null兼容老数据
        if ((version == null && pcm.getVersion() != null) || (version != null && pcm.getVersion() != null
                && version.longValue() != pcm.getVersion().longValue())) {
            throw new ServiceException("信息已过期，请刷新重试！");
        }
        Long newVersion = new Date().getTime();
        pcm.setVersion(newVersion);
        peispatientChargeRecordMapper.insert(new PeispatientChargeRecord(patientcode, newVersion, "receiveTjkCard", pcm.getMoneyamount(),
                pcm.getMoneyamountpaid(), pcm.getMoneyamount(), pcm.getMoneyamountpaid(), SecurityUtils.getUsername(),
                "receiveTjkCard"));
        //体检卡扣款
        CardConsumeParam cardConsumeParam = new CardConsumeParam(formdata.getCardId(), formdata.getLimit(), formdata.getLimit() > 0 ? 2 : 1,
                formdata.getMemotext(), SecurityUtils.getUserNo(), data.getIdPayway(), formdata.getBranchCenter(), param.getPatientcode(),
                param.getPatientname(), formdata.getConsumeType(), data.getKkfs(), SecurityUtils.getUsername(), null);
        applicationEventPublisher.publishEvent(new CardConsumeEvent(cardConsumeParam));
        // 保存本地
        String id = receiveCard(patientcode, param.getData());
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", "success");
        result.put("id", id);
        result.put("version", newVersion);
        return result;
    }

    /**
     * 保存线上
     *
     * @param formdata
     */
    private void saveCardOnline(TJKFormDataParam formdata) {
//        Double lim = formdata.getLimit();
//        LimitOperation odis = mapperFacade.map(formdata, LimitOperation.class);
//        //获取当前用户名
//        //String userName=toolUtil.userName();
//        String name = "";
//        // 判断是否是从内网获取数据，操作人
//        if (null != formdata.getUserName()) {
//            name = formdata.getUserName();
//        } else {
//            //获取当前登录用户名
//            name = SecurityUtils.getUsername();
//        }
//        // 判断是否为空
//        String cardId = "";
//        if(StringUtils.isBlank(odis.getId())) {
//            Card card = cardService.getOne(new LambdaQueryWrapper<Card>().eq(Card::getCardNo , odis.getCardId()));
//            if (null == card) {
//                throw new ServiceException("该卡号 "+odis.getCardId()+" 不存在");
//            }
//            if(card.getBalanceLimit()==null){
//                card.setBalanceLimit(0.0);// 如果是null值，赋值0.0可以充值
//            }
//            Double a = card.getBalanceLimit()+lim;
//            card.setBalanceLimit(a);
//            odis.setHandleMoney(a);
//            //当前登录用户
//            odis.setOperationId(name);
//            //设置isDelete字段为0
//            odis.setIsDelete(0);
//            odis.setIsAdd(0);
//            Date now=new Date();
//            odis.setOperationTime(now);
//            String id = this.save(odis);
//            if (StringUtils.isBlank(id)) {
//                throw new ServiceException("保存失败");
//            }
//            cardService.update(card);
//            cardId = odis.getId();
//        }


    }

    /**
     * 保存本地
     *
     * @param patientcode
     * @param data
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String receiveCard(String patientcode, TJKDataParam data) {
        Peispatientcharge charge = mapperFacade.map(data, Peispatientcharge.class);
        Dictpayway dictpayway = dictpaywayMapper.getInfoById(charge.getIdPayway());
        if (ObjectUtils.isEmpty(dictpayway))  throw new ServiceException("收费方式不存在！");
        charge.setPayway(dictpayway.getPaywayName());
        // 已收费
        charge.setIsDelete(0);
        charge.setPatientcode(patientcode);
        charge.setShortCode(CodeUtil.getShortCodeByLong(patientcode));
        // 退费金额
        if (charge.getMoneyamountpaid() < 0) {
            charge.setFIsreturn(1);
        }
        charge.setIdFeecharger(SecurityUtils.getUserNo());
        charge.setFeechargetime(new Date());
        // 保存实体类
        peispatientchargeService.save(charge);
        String id = charge.getId();
        Peispatient patient = peispatientMapper.getByPatientCode(charge.getPatientcode());
        setIdPatientclass2(patient, 0);
        return id;
    }


    /**
     * 体检卡误操作
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map removeCard(RemoveCardParam param) {
        Map<String, Object> result = new HashMap<String, Object>();
        String id = param.getId();
        Long version = param.getVersion();
        TJKFormDataParam formdata = param.getFormdata();
        if (StringUtils.isNotEmpty(id)) {
            Peispatientcharge charge = peispatientchargeService.getInfoById(id);
            String patientcode = charge.getPatientcode();
            PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientcode);
            if (pcm == null) {
                throw new ServiceException(patientcode + "未找到收费信息主表！");
            }
            // version==null兼容老数据
            if ((version == null && pcm.getVersion() != null) || (version != null && pcm.getVersion() != null
                    && version.longValue() != pcm.getVersion().longValue())) {
                throw new ServiceException("信息已过期，请刷新重试！");
            }

            //体检卡扣款
            CardConsumeParam cardConsumeParam = new CardConsumeParam(formdata.getCardId(), formdata.getLimit(), formdata.getLimit() > 0 ? 2 : 1,
                    formdata.getMemotext(), SecurityUtils.getUserNo(), formdata.getIdPayway(), formdata.getBranchCenter(), formdata.getPatientcode(),
                    param.getPatientname(), formdata.getConsumeType(), formdata.getKkfs(), SecurityUtils.getUsername(), null);
            applicationEventPublisher.publishEvent(new CardConsumeEvent(cardConsumeParam));

            Long newVersion = new Date().getTime();
            pcm.setVersion(newVersion);
            peispatientChargeRecordMapper.insert(new PeispatientChargeRecord(patientcode, newVersion, "removeCard", pcm.getMoneyamount(),
                    pcm.getMoneyamountpaid(), pcm.getMoneyamount(), pcm.getMoneyamountpaid(), SecurityUtils.getUsername(),
                    "removeCard"));
            Peispatient patient = peispatientMapper.getByPatientCode(charge.getPatientcode());
            setIdPatientclass2(patient, 0);
            peispatientchargeService.removeById(charge);
            result.put("version", newVersion);
        } else {
            //体检卡扣款
            CardConsumeParam cardConsumeParam = new CardConsumeParam(formdata.getCardId(), formdata.getLimit(), formdata.getLimit() > 0 ? 2 : 1,
                    formdata.getMemotext(), SecurityUtils.getUserNo(), formdata.getIdPayway(), formdata.getBranchCenter(), formdata.getPatientcode(),
                    param.getPatientname(), formdata.getConsumeType(), formdata.getKkfs(), SecurityUtils.getUsername(), null);
            applicationEventPublisher.publishEvent(new CardConsumeEvent(cardConsumeParam));
        }
        result.put("status", "success");
        return result;
    }


    /**
     * 会员卡退费
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map receiveMemberCard(ReceiveMemberParam param) {
        String patientcode = param.getPatientcode();
        Long version = param.getVersion();
        String type = param.getType();
        TJKDataParam chargedata = param.getData();
        // 判断版本过期
        PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientcode);
        if (pcm == null) {
            throw new ServiceException(patientcode + "未找到收费信息主表！");
        }
        // version==null兼容老数据
        if ((version == null && pcm.getVersion() != null) || (version != null && pcm.getVersion() != null
                && version.longValue() != pcm.getVersion().longValue())) {
            throw new ServiceException("信息已过期，请刷新重试！");
        }
        Long newVersion = new Date().getTime();
        pcm.setVersion(newVersion);
        peispatientChargeRecordMapper.insert(new PeispatientChargeRecord(patientcode, newVersion, "receiveMemberCard", pcm.getMoneyamount(),
                pcm.getMoneyamountpaid(), pcm.getMoneyamount(), pcm.getMoneyamountpaid(), SecurityUtils.getUsername(),
                "receiveMemberCard"));
        RMFormDataParam formdata = param.getFormdata();
        //体检卡扣款
        CardConsumeParam cardConsumeParam = new CardConsumeParam(formdata.getPatientcardno(), formdata.getLimit(), formdata.getLimit() > 0 ? 2 : 1,
                formdata.getMemotext(), SecurityUtils.getUserNo(), chargedata.getIdPayway(), formdata.getBranchCenter(), param.getPatientcode(),
                param.getPatientname(), formdata.getConsumeType(), chargedata.getKkfs(), SecurityUtils.getUsername(), null);
        applicationEventPublisher.publishEvent(new CardConsumeEvent(cardConsumeParam));

        // 保存卡信息
        String id = null;
        if ("saveCard".equals(type)) {
            id = receiveCard(patientcode, chargedata);
        }
//        if ("saveCards".equals(type)) {
//            id = receiveCards(patientcode, chargedata);
//        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", "success");
        result.put("id", id);
        result.put("version", newVersion);
        return result;
    }

    /**
     * 第三方支付退款
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map refundThirdPayment(ReceiveTongLianParam param) {
        Long version = param.getVersion();
        String patientcode = param.getPatientcode();
        String pcId = param.getId();
        // 判断版本
        Peispatientcharge pc = peispatientchargeService.getInfoById(pcId);
        PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientcode);
        if (pcm == null) {
            throw new ServiceException(patientcode + "未找到收费信息主表！");
        }
        // version==null兼容老数据
        if ((version == null && pcm.getVersion() != null) || (version != null && pcm.getVersion() != null
                && version.longValue() != pcm.getVersion().longValue())) {
            throw new ServiceException("信息已过期，请刷新重试！");
        }

        Long newVersion = new Date().getTime();
        pcm.setVersion(newVersion);

        // 保存本地
        String id = receiveCard(patientcode, param.getData());

        //退款操作
        if (param.getKkfs() == Kkfs.TONGLIAN.value() || param.getKkfs() == Kkfs.TONGLIAN2.value()) {
            //通联支付退款
            TongLianRefundParam payParam = new TongLianRefundParam();
            Double moneyamountpaid = param.getData().getMoneyamountpaid();
            payParam.setTrxamt((long) (Math.abs(moneyamountpaid) * 100));//取绝对值
            payParam.setReqsn(id);
            payParam.setOldtrxid(param.getData().getTxTradeNo());
            payParam.setType(param.getKkfs());
            try {
                applicationEventPublisher.publishEvent(new TongLianRefundEvent(payParam));
                Map<String, String> payResult = payParam.getPayResult();
                log.info("通联支付退款成功：{}", payResult);
            } catch (Exception e) {
                log.error("通联支付退款失败：{}、{}", param, e);
                throw new ServiceException("通联支付退款失败!");
            }
        }else if (param.getKkfs() == Kkfs.SUIXING.value()) {
            //随行支付退款
            SuiXingTradeRefundParam payParam = new SuiXingTradeRefundParam();
            //不能退负数，取绝对值
            payParam.setAmt(String.valueOf(Math.abs(param.getData().getMoneyamountpaid())));//单位是分
            payParam.setOrigOrderNo(pc.getId());
            try {
                applicationEventPublisher.publishEvent(new SuiXingRefundEvent(payParam));
                log.info("随行支付退款成功：{}", param);
            } catch (Exception e) {
                log.error("随行支付退款失败：{}、{}", param, e);
                throw new ServiceException("随行支付退款失败!");
            }
        }else {
            throw new ServiceException("请输入扣款方式！");
        }

        //收费记录实体类
        peispatientChargeRecordMapper.insert(new PeispatientChargeRecord(patientcode, newVersion, "receiveTongLian", pc.getMoneyamount(),
                pc.getMoneyamountpaid(), pc.getMoneyamount(), pc.getMoneyamountpaid(), SecurityUtils.getUsername(),
                "receiveTongLian"));

        //返回数据
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", "success");
        result.put("id", id);
        result.put("version", newVersion);
        return result;
    }

    /**
     * 退款管理
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<RefundManagementVo> refundManagement(PageParam<RefundManagementVo> page, RefundManagementParam param) {
        return registerMapper.refundManagement(page, param);
    }

    /**
     * 导出退款管理
     *
     * @param param
     * @return
     */
    @Override
    public List<RefundManagementVo> exportData(RefundManagementParam param) {
        List<RefundManagementVo> refundManagementVos = registerMapper.exportData(param);
        for (int i = 0; i < refundManagementVos.size(); i++) {
            RefundManagementVo vo = refundManagementVos.get(i);
            vo.setRownum(i + 1);
        }
        return refundManagementVos;
    }


    /**
     * 疫苗自动交单
     *
     * @param patientcode
     */
    @Override
    public void autoCompare(String patientcode) {
        //疫苗自动交单
        List<Peispatientfeeitem> jzList = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientcode)
                .eq("f_giveup", 0)
                .eq("change_item", 0)
                .isNull("f_transferedhl7")
                .eq("sfjj", 0)
                .eq("id_ks", "ff8080817bc96399017bd2c73a0c5e96"));
        if (jzList.size() > 0) {
            Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
            //是否交单：0.否 1.是
            patient.setCountreportcompare(1);
            peispatientMapper.updateById(patient);
        }
    }


    /**
     * 设置没有登记的体检号
     *
     * @param patientcodes
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean setDateregister(List<String> patientcodes) {
        for (String patientcode : patientcodes) {
            //查询体检者
            Peispatient peispatient = peispatientMapper.getByPatientCode(patientcode);
            if (peispatient.getFRegistered() != 1) {
                throw new ServiceException(patientcode + "还未登记");
            }
            List<Peispatientcharge> list = peispatientchargeService.list(new LambdaQueryWrapper<Peispatientcharge>()
                    .eq(Peispatientcharge::getPatientcode, patientcode).orderByAsc(Peispatientcharge::getCreatedate));
            if (CollectionUtil.isEmpty(list)) {
                throw new ServiceException(patientcode + "没有收费记录");
            }
            peispatient.setDateregister(list.get(0).getCreatedate());
            peispatientMapper.updateById(peispatient);
        }
        return Boolean.TRUE;
    }


    /**
     * 重新设置错误的档案
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean setUpProfile(List<String> patientcodes) {
        for (String patientcode : patientcodes) {
            Peispatient peispatient = peispatientMapper.getByPatientCode(patientcode);
            //重新生成身份证号
            peispatient.setIdcardno(generateRandomNumber(15));
            String patientarchiveno = peispatientarchiveService.bingIArchive(peispatient, false,SecurityUtils.getUserNo());
            peispatient.setPatientarchiveno(patientarchiveno);
            peispatientMapper.updateById(peispatient);
        }
        return Boolean.TRUE;
    }


    // 生成指定长度的随机数字字符串
    public static String generateRandomNumber(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // 生成 0 到 9 之间的随机数字
        }
        return sb.toString();
    }

    /**
     * 获取最近人员列表
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<RecentPersonnelListVo> getRecentPersonnelList(PageParam<RecentPersonnelListVo> page, RecentPersonnelListParam param) {
        return registerMapper.getRecentPersonnelList(page, param);
    }


    /**
     * 校正档案号
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String checkPatientarchiveno() {
        List<CheckPaNoDto> list = registerMapper.checkPatientarchiveno();
        List<Peispatient> peispatients = new ArrayList<>();
        for (CheckPaNoDto dto : list) {
            Peispatient peispatient = new Peispatient();
            peispatient.setId(dto.getId());
            peispatient.setPatientarchiveno(dto.getPatientarchiveno());
            peispatients.add(peispatient);
        }
        peispatientService.updateBatchById(peispatients);
        return "success";
    }


    /**
     * 家庭卡退费
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map receivesFamilyCard(ReFamilyCardParam param) {
        String patientcode = param.getPatientcode();
        Long version = param.getVersion();
        String type = param.getType();
        TJKDataParam chargedata = param.getData();
        // 判断版本过期
        PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(patientcode);
        if (pcm == null) {
            throw new ServiceException(patientcode + "未找到收费信息主表！");
        }
        // version==null兼容老数据
        if ((version == null && pcm.getVersion() != null) || (version != null && pcm.getVersion() != null
                && version.longValue() != pcm.getVersion().longValue())) {
            throw new ServiceException("信息已过期，请刷新重试！");
        }
        Long newVersion = new Date().getTime();
        pcm.setVersion(newVersion);
        peispatientChargeRecordMapper.insert(new PeispatientChargeRecord(patientcode, newVersion, "receiveMemberCard", pcm.getMoneyamount(),
                pcm.getMoneyamountpaid(), pcm.getMoneyamount(), pcm.getMoneyamountpaid(), SecurityUtils.getUsername(),
                "receiveMemberCard"));
        //退费
        OldFamilyConParam formdata = param.getFormdata();
        formdata.setType("0");
        formdata.setMoney(Math.abs(formdata.getMoney()));
        formdata.setChargeUsername(SecurityUtils.getUsername());
        applicationEventPublisher.publishEvent(new FamilyConsumptionEvent(formdata));

        // 保存卡信息
        String id = null;
        if ("saveCard".equals(type)) {
            id = receiveCard(patientcode, chargedata);
        }
//        if ("saveCards".equals(type)) {
//            id = receiveCards(patientcode, chargedata);
//        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", "success");
        result.put("id", id);
        result.put("version", newVersion);
        return result;
    }


    /**
     * 重新生成体检号
     *
     * @param codes
     * @return
     */
    @Override
    public Boolean againRegenCode(List<String> codes) {
        for (String code : codes) {
            Peispatient peispatient = peispatientService.getByPatientCode(code);
            String oldPatientCode = peispatient.getPatientcode();
            //重新生成体检号
            String patientCode = "";
            do {
                patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(null), "");
            } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getPatientcode, patientCode)) > 0);
            peispatient.setPatientcode(patientCode);

            //体检者收费项目
            List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, oldPatientCode));
            for (Peispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                peispatientfeeitem.setIdPatient(patientCode);
            }

            //体检者收费主表
            List<PeispatientChargeMain> peispatientChargeMainList = peispatientChargeMainService.list(new LambdaQueryWrapper<PeispatientChargeMain>().eq(PeispatientChargeMain::getPatientcode, oldPatientCode));
            for (PeispatientChargeMain peispatientChargeMain : peispatientChargeMainList) {
                peispatientChargeMain.setPatientcode(patientCode);
            }

            //更新
            peispatientService.updateById(peispatient);
            peispatientfeeitemService.updateBatchById(peispatientfeeitems);
            peispatientChargeMainService.updateBatchById(peispatientChargeMainList);

        }
        return Boolean.TRUE;
    }


    /**
     * 插入分中心的危害因素
     *
     * @param fzxId
     * @return
     */
    @Override
    public Boolean addHarmAndFzx(List<String> fzxId) {
        List<HarmAndFzx> harmAndFzxs = new ArrayList<>();
        List<Harm> list = harmService.list(new LambdaQueryWrapper<Harm>()
                .eq(Harm::getIsDelete, 0));
        for (String fzx : fzxId) {
            for (Harm harm : list) {
                long count = harmAndFzxService.count(new LambdaQueryWrapper<HarmAndFzx>()
                        .eq(HarmAndFzx::getFzxId, fzx)
                        .eq(HarmAndFzx::getHarmId, harm.getId()));
                if (count == 0) {
                    HarmAndFzx harmAndFzx = new HarmAndFzx();
                    harmAndFzx.setFzxId(fzx);
                    harmAndFzx.setHarmId(harm.getId());
                    harmAndFzx.setTbzt(0);
                    harmAndFzxs.add(harmAndFzx);
                }
            }
        }
        //批量插入
        harmAndFzxService.saveBatch(harmAndFzxs);
        return Boolean.TRUE;
    }

    /**
     * 多中心备单的体检者登记后，删除其他非到检的中心的体检数据
     * @param patientCode
     */
    @Override
    public void delOtherBranchPatient(String patientCode, List<String> branchList) {
        Peispatient patient = peispatientMapper.getByPatientCode(patientCode);
        if (Objects.nonNull(patient)){

            // 获取当前体检者已同步的分中心列表
            for (String branchNo : branchList) {
                List<SyncDataDto> syncDataDtos = new ArrayList<>();
                SysBranch branch = iSysBranchService.getByBranchId(branchNo);
//                SyncDataDto syncDataDto = new SyncDataDto(snowflake.nextIdStr(), patient.getId(), 4, "", syncRunSql, tc.getNeedSync(), cid);


                //执行同步
                syncOnlineService.execSync(syncDataDtos, branch);
            }



        }
    }
}


