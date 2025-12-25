package com.center.medical.reception.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.common.utils.*;
import com.center.medical.data.bean.model.*;
import com.center.medical.appadmin.bean.param.GenerateCodeParam;
import com.center.medical.bean.dto.PriceAndFactPriceDto;
import com.center.medical.bean.dto.RecalculatePriceDto;
import com.center.medical.bean.enums.CulturalLevel;
import com.center.medical.bean.enums.ExamType;
import com.center.medical.bean.enums.MarriageType;
import com.center.medical.bean.enums.MedicalType;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.SmsConfig;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.dto.InvalidImagesDto;
import com.center.medical.common.core.domain.entity.SysConfig;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.poi.ExcelExp;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.common.utils.poi.ExcelUtilManySheet;
import com.center.medical.common.utils.sms.SDKTestSendTemplateSMS;
import com.center.medical.dao.*;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.data.dao.NotificationmethodMapper;
import com.center.medical.data.dao.ShortmessageMapper;
import com.center.medical.data.service.BaseWorktypeService;
import com.center.medical.reception.bean.dto.CheckListDto;
import com.center.medical.reception.bean.dto.ExportPatientDto;
import com.center.medical.reception.bean.dto.PeispatientImportDto;
import com.center.medical.reception.bean.dto.PullOnlineDataDto;
import com.center.medical.reception.bean.param.*;
import com.center.medical.reception.bean.vo.*;
import com.center.medical.reception.dao.OrderMapper;
import com.center.medical.reception.service.OrderService;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.model.ReservationSetting;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.reservation.service.ReservationSettingService;
import com.center.medical.sellcrm.bean.dto.GpFormdataDto;
import com.center.medical.sellcrm.bean.dto.GpGriddataDto;
import com.center.medical.sellcrm.bean.dto.PaGridDataDto;
import com.center.medical.sellcrm.bean.dto.VationAndGroupErrorDataDto;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.bean.param.DbOrderParam;
import com.center.medical.sellcrm.bean.param.SaOrUpGroupParam;
import com.center.medical.sellcrm.bean.param.SaOrUpPatientParam;
import com.center.medical.sellcrm.dao.*;
import com.center.medical.sellcrm.service.*;
import com.center.medical.service.*;
import com.center.medical.system.bean.model.SysUserBranch;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.*;
import com.center.medical.workflow.bean.enums.WorkflowType;
import com.center.medical.workflow.bean.model.Workflow;
import com.center.medical.workflow.bean.model.WorkflowCase;
import com.center.medical.workflow.bean.model.WorkflowReservationType;
import com.center.medical.workflow.service.WorkflowCaseService;
import com.center.medical.workflow.service.WorkflowReservationTypeService;
import com.center.medical.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 前台-备单服务实现层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:41
 */
@Slf4j
@Service("orderService")
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Createorder> implements OrderService {

    private final OrderMapper orderMapper;
    private final ISysConfigService iSysConfigService;
    private final PeispatientService peispatientService;
    private final OrderandfzxService orderandfzxService;
    private final OrderandcomboService orderandcomboService;
    private final MealandfzxService mealandfzxService;
    private final MealanditemService mealanditemService;
    private final CreatecomboService createcomboService;
    private final ComboandfzxService comboandfzxService;
    private final ComboanditemService comboanditemService;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final CreatecomboMapper createcomboMapper;
    private final CreatemealMapper createmealMapper;
    private final HarmMapper harmMapper;
    private final GroupAndFzxMapper groupAndFzxMapper;
    private final GroupAndFzxService groupAndFzxService;
    private final PeisorgreservationMapper peisorgreservationMapper;
    private final PeispatientMapper peispatientMapper;
    private final CreateorderMapper createorderMapper;
    private final CreateorderService createorderService;
    private final SellcustomerMapper sellcustomerMapper;
    private final SysUserMapper sysUserMapper;
    private final MapperFacade mapperFacade;
    private final AreaMapper areaMapper;
    private final ISysBranchService iSysBranchService;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final PeispatientChargeMainMapper peispatientChargeMainMapper;
    private final PeispatientChargeMainService peispatientChargeMainService;
    private final ItemsMapper itemsMapper;
    private final NotifySmsExamMapper notifySmsExamMapper;
    private final NotifySmsExamService notifySmsExamService;
    private final ISysConfigService sysConfigService;
    private final PeispatientAndFzxMapper peispatientAndFzxMapper;
    private final PeispatientchargeMapper peispatientchargeMapper;
    private final MealanditemMapper mealanditemMapper;
    private final ISysDeptService sysDeptService;
    private final BaseWorktypeService baseWorktypeService;
    private final NotificationmethodMapper notificationmethodMapper;
    private final MealandfzxMapper mealandfzxMapper;
    private final DictpaywayMapper dictpaywayMapper;
    private final SysUserBranchService sysUserBranchService;
    private final SmsRecordMapper smsRecordMapper;
    private final ShortmessageMapper shortmessageMapper;
    private final VationAndFzxMapper vationAndFzxMapper;
    private final WorkflowService workflowService;
    private final WorkflowReservationTypeService workflowReservationTypeService;
    private final WorkflowCaseService workflowCaseService;
    private final BranchService branchService;
    private final PeispatientAndFzxService peispatientAndFzxService;
    private final ReservationSettingService reservationSettingService;
    private final ReservationService reservationService;
    private final PeisorgreservationgroupService peisorgreservationgroupService;
    private final Snowflake snowflake;
//    private final ExecutorService executorService = Executors.newFixedThreadPool(1);



    /**
     * 分页查询备单订单列表
     *
     * @param page  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @Override
    public IPage<BdOrderVo> getPage(PageParam<Createorder> page, DbOrderParam param) {
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
            param.setCids(branchService.getUserCid(userNo));
            param.setUserName(SecurityUtils.getUsername());
        }

        IPage<BdOrderVo> pages = orderMapper.getPage(page, param);
        //设置序号
        int current = page.getCurrent() == 0 ? 0 : Math.toIntExact(page.getSize() * (page.getCurrent() - 1));
        List<BdOrderVo> list = pages.getRecords();
        int i = 1;
        for (BdOrderVo vo : list) {
            vo.setRownum(current + i);
            i++;
        }
        return pages;
    }

    /**
     * 查看套餐
     *
     * @param idOrgreservation
     * @return
     */
    @Override
    public Map getGroupData(String idOrgreservation, String tjtcmc) {
        // 判断是否为空
        if (StringUtils.isBlank(idOrgreservation)) {
            return new HashMap();
        }
        List<Peisorgreservationgroup> list = peisorgreservationgroupMapper.getGroupData(idOrgreservation, tjtcmc);
        //体检者团体任务
        Peisorgreservation peisorgreservation = peisorgreservationMapper.getInfoById(idOrgreservation);
        if (CollectionUtils.isEmpty(list) || peisorgreservation == null) {
            HashMap empty = new HashMap();
            empty.put("data", Collections.emptyList());
            empty.put("total", 0);
            return empty;
        }

        // 预加载所需数据，避免循环中大量单条查询
        String ddh = peisorgreservation.getDdh();
        Set<String> examSuiteIds = new HashSet<>();
        Set<String> payWayIds = new HashSet<>();
        Set<String> groupIds = new HashSet<>();
        for (Peisorgreservationgroup group : list) {
            if (group.getIdExamsuite() != null) {
                examSuiteIds.add(group.getIdExamsuite());
            }
            if (group.getIdPayway() != null) {
                payWayIds.add(group.getIdPayway());
            }
            if (group.getId() != null) {
                groupIds.add(group.getId());
            }
        }

        Map<String, Orderandcombo> orderComboMap = orderandcomboService.list(
                        new QueryWrapper<Orderandcombo>()
                                .eq("ddid", ddh)
                                .in(CollectionUtils.isNotEmpty(examSuiteIds), "tcid", examSuiteIds)
                ).stream()
                .collect(Collectors.toMap(Orderandcombo::getTcid, o -> o, (o1, o2) -> o1));

        Map<String, Createcombo> comboMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(examSuiteIds)) {
            createcomboService.listByIds(examSuiteIds)
                    .forEach(c -> comboMap.put(c.getId(), c));
        }
        Map<String, Createmeal> mealMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(examSuiteIds)) {
            createmealMapper.selectBatchIds(examSuiteIds)
                    .forEach(m -> mealMap.put(m.getId(), m));
        }

        Map<String, Dictpayway> paywayMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(payWayIds)) {
            dictpaywayMapper.selectBatchIds(payWayIds)
                    .forEach(p -> paywayMap.put(p.getId(), p));
        }

        Map<String, Long> patientCountMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(groupIds)) {
            peispatientService.list(
                            new QueryWrapper<Peispatient>()
                                    .in("id_orgreservationgroup", groupIds)
                                    .select("id_orgreservationgroup")
                    ).forEach(p -> patientCountMap.merge(p.getIdOrgreservationgroup(), 1L, Long::sum));
        }

        Map<String, String> harmGroupTextCache = new HashMap<>();
        List newList = new ArrayList();

        // 拼接页面数据
        for (Peisorgreservationgroup group : list) {
            //订单与套餐关联表
            Orderandcombo orderandcombo = orderComboMap.get(group.getIdExamsuite());
            if (orderandcombo != null && (orderandcombo.getShow() == null || orderandcombo.getShow() == 0)) {
                HashMap map = new HashMap();
                map.put("id", group.getId());
                map.put("orgreservationgroupname", group.getOrgreservationgroupname());
                map.put("dateexamplanned", group.getDateexamplanned());
                map.put("dispOrder", group.getDispOrder());
                map.put("fMale", group.getFMale());
                map.put("fFemale", group.getFFemale());
                map.put("fHasmarried", group.getFHasmarried());
                map.put("fNevermarried", group.getFNevermarried());
                map.put("grouptype", group.getGrouptype());
                map.put("agemin", group.getAgemin());
                map.put("agemax", group.getAgemax());
                map.put("idExamtype", group.getIdExamtype());
                map.put("idExamsuite", group.getIdExamsuite());
                map.put("groupLimit", (group.getFGroupstarted() == 1 && group.getFGrouppaused() == 0) ? 0 : 1);// 组禁检
                String tcName = "";
                String harmGroup = "";
                String zhjg = "";
                String tcSrm = "";
                //最小套餐
                Createcombo combo = comboMap.get(group.getIdExamsuite());
                if (ObjectUtils.isEmpty(combo)) {
                    //普通套餐表
                    Createmeal createMeal = mealMap.get(group.getIdExamsuite());
                    if (null != createMeal) {
                        tcName = createMeal.getTjtcjc();
                        tcSrm = createMeal.getTjtcsrm();
                        harmGroup = createMeal.getJhys();
                        zhjg = createMeal.getZhjg().toString();
                        map.put("forbidden", createMeal.getForbidden());
                        map.put("zytjlb", createMeal.getZytjlb());
                    } else {
                        tcName = "";
                        harmGroup = "";
                    }
                } else {
                    tcName = combo.getTjtcjc();
                    harmGroup = combo.getJhys();
                    tcSrm = combo.getTjtcsrm();
                    zhjg = combo.getZhjg().toString();
                }
                map.put("tcName", tcName);
                // 获取危害分组
                map.put("harmGroup", harmGroup);
                if (StringUtils.isNotEmpty(harmGroup)) {
                    String harmName = harmGroupTextCache.computeIfAbsent(harmGroup,
                            hg -> harmMapper.getHarmText(hg.split(",")));
                    map.put("harmName", harmName);
                } else {
                    map.put("harmName", "");
                }
                map.put("idPayway", group.getIdPayway());
                //支付方式名称
                Dictpayway pay = paywayMap.get(group.getIdPayway());
                map.put("payWayName", ObjectUtils.isNotEmpty(pay) ? pay.getPaywayName() : "");
                map.put("zhjg", zhjg);
                map.put("idPatientclass3", group.getIdPatientclass3());
                newList.add(map);
            } else if (patientCountMap.getOrDefault(group.getId(), 0L) > 0) {
                HashMap map = new HashMap();
                map.put("id", group.getId());
                map.put("orgreservationgroupname", group.getOrgreservationgroupname());
                map.put("dateexamplanned", group.getDateexamplanned());
                map.put("dispOrder", group.getDispOrder());
                map.put("fMale", group.getFMale());
                map.put("fFemale", group.getFFemale());
                map.put("fHasmarried", group.getFHasmarried());
                map.put("fNevermarried", group.getFNevermarried());
                map.put("grouptype", group.getGrouptype());
                map.put("agemin", group.getAgemin());
                map.put("agemax", group.getAgemax());
                map.put("idExamtype", group.getIdExamtype());
                map.put("idExamsuite", group.getIdExamsuite());
                map.put("groupLimit", (group.getFGroupstarted() == 1 && group.getFGrouppaused() == 0) ? 0 : 1);// 组禁检
                String tcName = "";
                String harmGroup = "";
                String zhjg = "";
                String tcSrm = "";
                //最小套餐
                Createcombo combo = comboMap.get(group.getIdExamsuite());
                if (ObjectUtils.isEmpty(combo)) {
                    //普通套餐表
                    Createmeal createMeal = mealMap.get(group.getIdExamsuite());
                    if (ObjectUtils.isNotEmpty(createMeal)) {
                        tcName = createMeal.getTjtcjc();
                        tcSrm = createMeal.getTjtcsrm();
                        harmGroup = createMeal.getJhys();
                        zhjg = createMeal.getZhjg().toString();
                        map.put("forbidden", createMeal.getForbidden());
                        map.put("zytjlb", createMeal.getZytjlb());
                    } else {
                        tcName = "";
                        harmGroup = "";
                    }
                } else {
                    tcName = combo.getTjtcjc();
                    harmGroup = combo.getJhys();
                    tcSrm = combo.getTjtcsrm();
                    zhjg = combo.getZhjg().toString();
                }
                map.put("tcName", tcName);
                map.put("tcSrm", tcSrm);
                // 获取危害分组
                map.put("harmGroup", harmGroup);
                if (StringUtils.isNotEmpty(harmGroup)) {
                    String harmName = harmGroupTextCache.computeIfAbsent(harmGroup,
                            hg -> harmMapper.getHarmText(hg.split(",")));
                    map.put("harmName", harmName);
                } else {
                    map.put("harmName", "");
                }
                map.put("idPayway", group.getIdPayway());
                //支付方式名称
                Dictpayway pay = paywayMap.get(group.getIdPayway());
                map.put("payWayName", ObjectUtils.isNotEmpty(pay) ? pay.getPaywayName() : "");
                map.put("zhjg", zhjg);
                map.put("idPatientclass2", group.getIdPatientclass2());
                newList.add(map);
            }
        }
        HashMap map = new HashMap();
        map.put("data", newList);
        map.put("total", newList.size());
        return map;
    }


    /**
     * 备单管理-上方数据
     *
     * @param id
     * @return
     */
    @Override
    public Map edit(String id) {
        // 订单信息
        Createorder createOrder = createorderMapper.selectOne(new QueryWrapper<Createorder>()
                .eq("id", id).eq("spzt", 4).eq("is_delete", 0));
        // 订单存在
        if (null != createOrder) {
            Map map = new HashMap();
            String fzxId = SecurityUtils.getCId();
            Peisorgreservation peisorgreservation = peisorgreservationMapper.selectOne(new QueryWrapper<Peisorgreservation>().eq("ddh", id));
            // 判断此订单是否存在
            if (null == peisorgreservation) {
                map.put("ddh", createOrder.getId());
                map.put("ordernum", createOrder.getDdh());
                map.put("idOrg", createOrder.getKhdwmcid());
                if (StringUtils.isNotEmpty(createOrder.getKhdwmcid())) {
                    //我的客户表
                    Sellcustomer sellCustomer = sellcustomerMapper.getInfoById(createOrder.getKhdwmcid());
                    if (sellCustomer != null) {
                        map.put("orgName", sellCustomer.getKhdwmc());
                        map.put("tjm", sellCustomer.getIntId());
                        map.put("orgName", sellCustomer.getKhdwmc());
                        map.put("address", StringUtils.isBlank(sellCustomer.getKhdwzcdz()) ? "" : sellCustomer.getKhdwzcdz());
                    }
                }
                map.put("isMarket", createOrder.getIsMarket());
                SysUser qxUser = sysUserMapper.getUserByNo(createOrder.getXsjlid());
                //获取用户名
                String userName = qxUser == null ? "" : qxUser.getUserName();
                map.put("idSalesperson", createOrder.getXsjlid());
                map.put("saleName", userName);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                map.put("datereservation", createOrder.getJhjqc());
                map.put("planenddate", createOrder.getJhjqd());
                int count = createOrder.getNxtjrs().intValue() + createOrder.getVxtjrs().intValue();
                map.put("countexaminee", count);
                map.put("phone", createOrder.getKhdwdh());
                map.put("qtxz", createOrder.getQtxz());
                map.put("idOrgclass", createorderService.getGroupLevel(id));

                map.put("fzxid", fzxId);//PEISORGRESERVATION分中心ID
                return map;
            }
            map.put("peisorgreservation", peisorgreservation);
            // 订单号
            map.put("ordernum", createOrder.getDdh());
            map.put("isMarket", createOrder.getIsMarket());
            // 体检团体任务id
            map.put("id", peisorgreservation.getId());
            //销售员ID
            SysUser qxUser = sysUserMapper.getUserByNo(peisorgreservation.getIdSalesperson());
            //获取用户名
            String userName = qxUser == null ? "" : qxUser.getUserName();
            // 销售人员名称
            map.put("saleName", userName);
            map.put("note", peisorgreservation.getNote());
            map.put("FFinished", peisorgreservation.getFFinished());
            Sellcustomer sellCustomer = sellcustomerMapper.getInfoById(createOrder.getKhdwmcid());
            if (null != sellCustomer) {
                //数字形式团体ID
                map.put("tjm", sellCustomer.getIntId());
            }
            //订单分中心
            List<String> branchName = orderandfzxService.getBranchNameByDdid(peisorgreservation.getDdh());
            map.put("branchName", branchName);

            return map;
        }

        return new HashMap();
    }


    /**
     * 设置工种
     *
     * @param id
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean setWorktype(String id, List<String> ids) {
        Peispatient peispatient = new Peispatient();
        //设置工种id
        peispatient.setWorktypeId(id);
        //更新
        int i = peispatientMapper.update(peispatient, new UpdateWrapper<Peispatient>().in("id_orgreservationgroup", ids));
        return i > 0;
    }

    /**
     * 体检团体分组保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdateGroup(SaOrUpGroupParam param) {
        // 团体任务表
        String resultVation = saveOrUpdateVation(param.getFormdata());
        String id = "";
        if (resultVation.indexOf("success") == 0) {
            id = resultVation.substring(8);
        } else {
            return resultVation;
        }

        List<GpGriddataDto> groupData = param.getGriddata();
        if (ObjectUtils.isEmpty(groupData)) {
            return "success:" + id;
        }

        // 预加载数据
        Map<String, Peisorgreservationgroup> existingGroups = preloadExistingGroups(id, groupData);
        Map<String, List<String>> comboFzxMap = preloadComboFzxData(groupData);
        Map<String, Long> groupPatientCounts = preloadGroupPatientCounts(groupData);

        // 分类处理
        List<Peisorgreservationgroup> addedGroups = new ArrayList<>();
        List<Peisorgreservationgroup> modifiedGroups = new ArrayList<>();
        List<Peisorgreservationgroup> deletedGroups = new ArrayList<>();
        List<GroupAndFzx> addedGroupFzxList = new ArrayList<>();

        // 处理数据
        processGroupData(groupData, id, existingGroups, comboFzxMap, groupPatientCounts,
                addedGroups, modifiedGroups, deletedGroups, addedGroupFzxList);

        // 批量操作数据库
        executeBatchOperations(addedGroups, modifiedGroups, deletedGroups, addedGroupFzxList);

        return "success:" + id;
    }

    /**
     * 预加载已存在的分组数据
     */
    private Map<String, Peisorgreservationgroup> preloadExistingGroups(String reservationId, List<GpGriddataDto> groupData) {
        Set<String> groupIds = groupData.stream()
                .map(GpGriddataDto::getId)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());

        if (groupIds.isEmpty()) {
            return new HashMap<>();
        }

        List<Peisorgreservationgroup> existingGroups = peisorgreservationgroupMapper.selectList(
                new QueryWrapper<Peisorgreservationgroup>()
                        .in("id", groupIds)
                        .eq("is_delete", 0));

        return existingGroups.stream()
                .collect(Collectors.toMap(Peisorgreservationgroup::getId, group -> group));
    }

    /**
     * 预加载套餐分中心数据
     */
    private Map<String, List<String>> preloadComboFzxData(List<GpGriddataDto> groupData) {
        Set<String> examSuiteIds = groupData.stream()
                .map(GpGriddataDto::getIdExamsuite)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());

        if (examSuiteIds.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, List<String>> comboFzxMap = new HashMap<>();
        for (String examSuiteId : examSuiteIds) {
            List<String> fzxList = peisorgreservationgroupMapper.getComboAndMealFzx(examSuiteId);
            comboFzxMap.put(examSuiteId, fzxList);
        }

        return comboFzxMap;
    }

    /**
     * 预加载分组体检者数量
     */
    private Map<String, Long> preloadGroupPatientCounts(List<GpGriddataDto> groupData) {
        Set<String> groupIds = groupData.stream()
                .map(GpGriddataDto::getId)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());

        if (groupIds.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, Long> patientCounts = new HashMap<>();
        for (String groupId : groupIds) {
            Long count = peispatientMapper.selectCount(new QueryWrapper<Peispatient>()
                    .eq("id_orgreservationgroup", groupId));
            patientCounts.put(groupId, count);
        }

        return patientCounts;
    }

    /**
     * 处理分组数据
     */
    private void processGroupData(List<GpGriddataDto> groupData, String reservationId,
                                  Map<String, Peisorgreservationgroup> existingGroups,
                                  Map<String, List<String>> comboFzxMap,
                                  Map<String, Long> groupPatientCounts,
                                  List<Peisorgreservationgroup> addedGroups,
                                  List<Peisorgreservationgroup> modifiedGroups,
                                  List<Peisorgreservationgroup> deletedGroups,
                                  List<GroupAndFzx> addedGroupFzxList) {

        // 检查新增分组的名称重复
        checkDuplicateGroupNames(groupData, reservationId);

        for (GpGriddataDto map : groupData) {
            String state = map.getState();
            String groupId = map.getId();
            String groupName = map.getOrgreservationgroupname();

            if (StringUtils.isBlank(groupName)) {
                throw new ServiceException("分组名称不能为空！");
            }

            if ("removed".equals(state)) {
                processRemovedGroup(map, existingGroups, groupPatientCounts, deletedGroups);
            } else if ("modified".equals(state)) {
                processModifiedGroup(map, existingGroups, comboFzxMap, modifiedGroups, addedGroupFzxList);
            } else if ("added".equals(state)) {
                processAddedGroup(map, reservationId, comboFzxMap, addedGroups, addedGroupFzxList);
            }
        }
    }

    /**
     * 检查新增分组的名称重复
     */
    private void checkDuplicateGroupNames(List<GpGriddataDto> groupData, String reservationId) {
        List<String> newGroupNames = groupData.stream()
                .filter(dto -> "added".equals(dto.getState()))
                .map(GpGriddataDto::getOrgreservationgroupname)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());

        if (!newGroupNames.isEmpty()) {
            List<Peisorgreservationgroup> duplicateGroups = peisorgreservationgroupMapper.selectList(
                    new QueryWrapper<Peisorgreservationgroup>()
                            .eq("id_orgreservation", reservationId)
                            .in("orgreservationgroupname", newGroupNames)
                            .eq("is_delete", 0));

            if (!duplicateGroups.isEmpty()) {
                String duplicateNames = duplicateGroups.stream()
                        .map(Peisorgreservationgroup::getOrgreservationgroupname)
                        .collect(Collectors.joining(","));
                throw new ServiceException("保存失败：分组名称 " + duplicateNames + " 已经重复!");
            }
        }
    }

    /**
     * 处理删除的分组
     */
    private void processRemovedGroup(GpGriddataDto map,
                                     Map<String, Peisorgreservationgroup> existingGroups,
                                     Map<String, Long> groupPatientCounts,
                                     List<Peisorgreservationgroup> deletedGroups) {
        String groupId = map.getId();
        if (StringUtils.isNotBlank(groupId)) {
            Peisorgreservationgroup group = existingGroups.get(groupId);
            if (group != null) {
                Long patientCount = groupPatientCounts.getOrDefault(groupId, 0L);
                if (patientCount > 0) {
                    throw new ServiceException("保存失败：该 " + map.getOrgreservationgroupname() + " 分组下已经存在体检者，不可以删除!");
                }
                group.setIsDelete(1);
                deletedGroups.add(group);
            }
        }
    }

    /**
     * 处理修改的分组
     */
    private void processModifiedGroup(GpGriddataDto map,
                                      Map<String, Peisorgreservationgroup> existingGroups,
                                      Map<String, List<String>> comboFzxMap,
                                      List<Peisorgreservationgroup> modifiedGroups,
                                      List<GroupAndFzx> addedGroupFzxList) {
        String groupId = map.getId();
        Peisorgreservationgroup existingGroup = existingGroups.get(groupId);
        if (existingGroup == null) {
            throw new ServiceException("保存失败：" + map.getOrgreservationgroupname() + " 分组不存在，已经被删除!");
        }

        // 更新分组信息
        Peisorgreservationgroup group = mapperFacade.map(map, Peisorgreservationgroup.class);
        group.setId(groupId);
        group.setInputCode(ToolUtil.getHanziPinyinHeadChar(group.getOrgreservationgroupname()));
        modifiedGroups.add(group);

        // 处理分组分中心
        processGroupFzx(map.getIdExamsuite(), groupId, comboFzxMap, addedGroupFzxList);
    }

    /**
     * 处理新增的分组
     */
    private void processAddedGroup(GpGriddataDto map, String reservationId,
                                   Map<String, List<String>> comboFzxMap,
                                   List<Peisorgreservationgroup> addedGroups,
                                   List<GroupAndFzx> addedGroupFzxList) {
        Peisorgreservationgroup group = mapperFacade.map(map, Peisorgreservationgroup.class);
        group.setIdOrgreservation(reservationId);
        group.setFGroupstarted(1);
        group.setFGrouppaused(0);
        group.setIsDelete(0);
        group.setInputCode(ToolUtil.getHanziPinyinHeadChar(group.getOrgreservationgroupname()));
        group.setId(String.valueOf(snowflake.nextId()));
        addedGroups.add(group);

        // 处理分组分中心
        processGroupFzx(map.getIdExamsuite(), group.getId(), comboFzxMap, addedGroupFzxList);
    }

    /**
     * 处理分组分中心关联
     */
    private void processGroupFzx(String examSuiteId, String groupId,
                                 Map<String, List<String>> comboFzxMap,
                                 List<GroupAndFzx> addedGroupFzxList) {
        // 添加空值检查
        if (examSuiteId == null) {
            return;
        }

        List<String> fzxList = comboFzxMap.get(examSuiteId);
        if (fzxList != null && !fzxList.isEmpty()) {
            // 批量查询已存在的记录，减少数据库访问次数
            List<GroupAndFzx> existingRelations = groupAndFzxService.list(
                    new LambdaQueryWrapper<GroupAndFzx>()
                            .eq(GroupAndFzx::getGroupId, groupId)
                            .in(GroupAndFzx::getFzxId, fzxList)
            );

            // 将已存在的关系存入Set便于快速查找
            Set<String> existingFzxIds = existingRelations.stream()
                    .map(GroupAndFzx::getFzxId)
                    .collect(Collectors.toSet());

            // 只添加不存在的关系
            for (String fzxId : fzxList) {
                if (!existingFzxIds.contains(fzxId)) {
                    addedGroupFzxList.add(new GroupAndFzx(fzxId, groupId, 0));
                }
            }
        }
    }

    /**
     * 执行批量数据库操作
     */
    private void executeBatchOperations(List<Peisorgreservationgroup> addedGroups,
                                        List<Peisorgreservationgroup> modifiedGroups,
                                        List<Peisorgreservationgroup> deletedGroups,
                                        List<GroupAndFzx> addedGroupFzxList) {
        // 批量新增分组
        if (!addedGroups.isEmpty()) {
            peisorgreservationgroupService.saveBatch(addedGroups);
        }

        // 批量更新分组
        if (!modifiedGroups.isEmpty()) {
            peisorgreservationgroupService.updateBatchById(modifiedGroups);
        }

        // 批量删除分组（逻辑删除）
        if (!deletedGroups.isEmpty()) {
            peisorgreservationgroupService.updateBatchById(deletedGroups);
        }

        // 批量新增分组分中心
        if (!addedGroupFzxList.isEmpty()) {
            groupAndFzxService.saveBatch(addedGroupFzxList);
        }
    }


    /**
     * 保存或更新体检者团体任务
     *
     * @param formdata
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdateVation(GpFormdataDto formdata) {
        //体检者团体任务
        Peisorgreservation vation = mapperFacade.map(formdata, Peisorgreservation.class);
        String vation_id = vation.getId();
        // 分中心ID
//        vation.setFzxid(SecurityUtils.getCId());
        // id存在，更新
        if (StringUtils.isNotEmpty(vation.getId())) {
            Peisorgreservation vationNew = peisorgreservationMapper.getInfoById(vation.getId());
            // 判断是否为空
            if (null == vationNew)
                throw new ServiceException("保存失败：体检团体信息不存在，已经被删除");
            BeanUtils.copyProperties(vationNew, vation);
            if (vationNew.getBdid() == null) {
                //备单人员ID
                vationNew.setBdid(SecurityUtils.getUserNo());
            }
            // 更新
            peisorgreservationMapper.updateById(vationNew);
            vation_id = vationNew.getId();

        } else {
            //体检者团体任务
            Peisorgreservation vationNew = peisorgreservationMapper.selectOne(new QueryWrapper<Peisorgreservation>().eq("ddh", vation.getDdh()));
            // 判断该订单是否已经备单
            if (null != vationNew)
                throw new ServiceException("保存失败：该订单已经备单，请重新选择");
            // 备单人员ID
            vation.setBdid(SecurityUtils.getUserNo());
            //客户单位名称
            String orgName = sellcustomerMapper.getInfoById(vation.getIdOrg()).getKhdwmc();
            vation.setOrgName(orgName);
            // 保存实体类
            peisorgreservationMapper.insert(vation);
            vation_id = vation.getId();
            if (StringUtils.isBlank(vation_id)) {
                throw new ServiceException("保存失败");
            }
            //删除团体任务分中心
            vationAndFzxMapper.delete(new QueryWrapper<VationAndFzx>().eq("vation_id", vation_id));
        }
        //团体任务与分中心关联表
        List<Orderandfzx> oafs = orderandfzxService.list(new QueryWrapper<Orderandfzx>()
                .eq("ddid", vation.getDdh()));
        for (Orderandfzx oaf : oafs) {
            //团体任务分中心
            vationAndFzxMapper.insert(new VationAndFzx(oaf.getFzxid(), vation_id, 0));
        }
        return "success:" + vation.getId();
    }

    /**
     * 备单状态更改
     *
     * @param ddId
     * @param curFzxId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateNotifyRemoteOrder(String ddId, String curFzxId) {
        if (StringUtils.isBlank(ddId) && StringUtils.isBlank(curFzxId)) {
            return "同步状态发生异常";
        }
        //订单与分中心关联表
        Orderandfzx oaf = orderandfzxService.getOne(new QueryWrapper<Orderandfzx>()
                .eq("ddid", ddId).eq("fzxid", curFzxId).in("tbzt", 0, 2));
        if (oaf != null) {
            oaf.setTbzt(1);
            oaf.setBdrq(new Date());// 同步日期
            orderandfzxService.updateById(oaf);
        }
        //订单与套餐关联表
        List<Orderandcombo> oacs = orderandcomboService.list(new QueryWrapper<Orderandcombo>().eq("ddid", ddId));
        for (Orderandcombo oac : oacs) {
            if ("0".equals(oac.getCombostate())) {
                //普通套餐
                Mealandfzx maf = mealandfzxService.getOne(new QueryWrapper<Mealandfzx>().eq("fzxid", curFzxId)
                        .eq("tcid", oac.getTcid()).in("tbzt", 0, 2));
                if (maf != null) {
                    maf.setTbzt(1);
                    mealandfzxService.updateById(maf);
                }
            } else {
                //最小套餐与分中心关联表
                Comboandfzx caf = comboandfzxService.getOne(new QueryWrapper<Comboandfzx>()
                        .eq("fzxid", curFzxId).eq("tcid", oac.getTcid()).in("tbzt", 0, 2));
                if (caf != null) {
                    caf.setTbzt(1);
                    comboandfzxService.updateById(caf);
                }
            }
        }
        // TODO: wait 发送线上请求
//        //老系统链接 receptionist/item_list!updateNotifyRemoteOrder.action
//        String result = notifyRemoteOrder(curFzxId, ddId);
//        if (null != result) {
//            if (!result.equals("success")) {
//                log.info("通知网上已备单result:" + result + "。订单ID：" + ddId);
//                throw new ServiceException(result);
//            }
//        } else {
//            throw new ServiceException("远程服务器发生异常");
//        }
        return "success";
    }


    /**
     * 备单状态更改(线上)
     *
     * @param curFzxId
     * @param ddId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String notifyRemoteOrder(String curFzxId, String ddId) {
        if (StringUtils.isBlank(curFzxId) && StringUtils.isBlank(ddId)) {
            return "同步状态发生异常";
        }

        //订单与分中心关联表
        Orderandfzx oaf = orderandfzxService.getOne(new QueryWrapper<Orderandfzx>()
                .eq("ddid", ddId).eq("fzxid", curFzxId).in("tbzt", 0, 2));
        if (oaf != null) {
            //同步
            oaf.setTbzt(1);
            oaf.setBdrq(new Date());// 同步日期
            orderandfzxService.updateById(oaf);
        }

        //订单与套餐关联表
        List<Orderandcombo> oacs = orderandcomboService.list(new QueryWrapper<Orderandcombo>().eq("ddid", ddId));
        for (Orderandcombo oac : oacs) {
            if ("0".equals(oac.getCombostate())) {
                //普通套餐
                Mealandfzx maf = mealandfzxService.getOne(new QueryWrapper<Mealandfzx>().eq("fzxid", curFzxId)
                        .eq("tcid", oac.getTcid()).in("tbzt", 0, 2));
                if (maf != null) {
                    //已同步
                    maf.setTbzt(1);
                    mealandfzxService.updateById(maf);
                }
            } else {
                Comboandfzx caf = comboandfzxService.getOne(new QueryWrapper<Comboandfzx>().eq("fzxid", curFzxId)
                        .eq("tcid", oac.getTcid()).in("tbzt", 0, 2));
                if (caf != null) {
                    //已同步
                    caf.setTbzt(1);
                    comboandfzxService.updateById(caf);
                }
            }
        }

        return "success";

    }

    /**
     * 获取分组下相应的人员信息
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<OrderPaDataVo> getPatientData(PageParam<OrderPaDataVo> page, OrderPaDataParam param) {
        //输入码去空格
        if (ObjectUtils.isNotEmpty(param.getInputCode())) {
            param.setInputCode(param.getInputCode().trim());
        }
        IPage<OrderPaDataVo> pages = orderMapper.getPatientData(page, param);
        //设置序号
        int current = page.getCurrent() == 0 ? 0 : Math.toIntExact(page.getSize() * (page.getCurrent() - 1));
        List<OrderPaDataVo> list = pages.getRecords();
        int i = 1;
        for (OrderPaDataVo vo : list) {
            vo.setRownum(current + i);
            i++;
        }
        return pages;
    }

    /**
     * 体检者基本信息保存（预登记）
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdatePatient(SaOrUpPatientParam param) {
        //取出属性
        List<PaGridDataDto> patientData = param.getGriddata();

        //已登记的不能修改
        List<String> patientCodes = patientData.stream()
                .filter(dto -> "modified".equals(dto.getState()))
                .map(PaGridDataDto::getPatientcode)
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(patientCodes)){
            long count = peispatientService.count(new LambdaQueryWrapper<Peispatient>()
                    .in(Peispatient::getPatientcode, patientCodes).eq(Peispatient::getFRegistered,1));
            if (count > 0){ throw new ServiceException("修改数据体检号已登记,请刷新页面!");}
        }

        GpFormdataDto mData = param.getFormdata();
        Date date = new Date();
        String id = param.getId();
        String registerR = SecurityUtils.getUserNo();
        // 团体id
        String idOrg = mData.getIdOrg();
        // 开单医师ID
        Sellcustomer customer = sellcustomerMapper.getInfoById(idOrg);
        SysUser qxUsers = sysUserMapper.getUserByNo(customer.getXsjlid());
        String xsjlId = null == qxUsers ? "" : qxUsers.getUserNo();
        String xsjlName = null == qxUsers ? "" : qxUsers.getUserName();
        // 团体名称
        String orgName = customer.getKhdwmc();
        Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(id);
        //订单号
        String ddhstr = mData.getOrdernum();
        Createorder order = createorderMapper.selectOne(new QueryWrapper<Createorder>().eq("ddh", ddhstr));
        //通知方式
        String idInformway = order == null ? null : order.getIdInforway();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //前台须知
        String qtxz = null == mData.getQtxz() ? "" : mData.getQtxz();
        String tjtcid = group.getIdExamsuite();
        String vationId = mData.getId();
        //体检者团体任务
        Long count = peisorgreservationMapper.selectCount(new QueryWrapper<Peisorgreservation>()
                .eq("id", vationId).eq("f_finished", 1));
        boolean isFinished = count > 0;
        Date medialdate = group.getDateexamplanned() == null ? date : group.getDateexamplanned();
        // 体检类别
        String examsuiteName = "";
        String tjlb = "0";
        String jhys = "";
        //折后价格
        Double zhjg = 0.0;
        //最小套餐
        Createcombo combo = createcomboService.getInfoById(group.getIdExamsuite());
        if (null != combo) {
            examsuiteName = combo.getTjtcmc();
            tjlb = combo.getZytjlb();
            jhys = combo.getJhys();
            zhjg = combo.getZhjg();
        } else {
            //普通套餐表
            Createmeal createMeal = createmealMapper.getInfoById(group.getIdExamsuite());
            if (null != createMeal) {
                examsuiteName = createMeal.getTjtcmc();
                tjlb = ObjectUtils.isNotEmpty(createMeal.getZytjlb()) ? String.valueOf(createMeal.getZytjlb()) : null;
                jhys = createMeal.getJhys();
                zhjg = createMeal.getZhjg();
            }
        }
        //订单与套餐关联表
        Orderandcombo orderAndCombo = orderandcomboService.getOne(new QueryWrapper<Orderandcombo>()
                .eq("ddid", order.getId())
                .eq("tcid", group.getIdExamsuite()));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        //体检类型ID
        String idExamType = group.getIdExamtype();
        String orderNum = mData.getOrdernum();
        //籍贯表
        Area defaultArea = areaMapper.selectOne(new QueryWrapper<Area>().eq("is_default", 1));
        Integer idExamclass = orderAndCombo.getIdExamclass();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 使用格式化对象将 Date 转换为字符串
        String dateString = dateFormat.format(new Date());
        String onlne = ZhongkangConfig.isOnline() ? "线上" : "线下";
        String note = SecurityUtils.getUsername() + onlne + "备单于" + dateString + ";";
        for (PaGridDataDto map : patientData) {
            //体检者表快速赋值
            Peispatient peispatient = mapperFacade.map(map, Peispatient.class);
            if (null == peispatient) {
                throw new ServiceException("预登记失败：系统发生异常，请联系管理员");
            }

            peispatient.setCountreportoccupationxml(1);//身份证
            peispatient.setQtxz(qtxz);
            peispatient.setIdInformway(idInformway);
            //个检/团检：0.个检 1.团检
            peispatient.setFUsecodehiden(1);
            peispatient.setIdExamclass(idExamclass);
            // 订单号
            peispatient.setNumorgresv(ddhstr);
            peispatient.setIdTjtc(tjtcid);
            peispatient.setIdOrgreservationgroup(id);
            // 任务ID
            peispatient.setIdOrgreservation(vationId);
            // 体检时间
            peispatient.setMedicaldate(medialdate);

            peispatient.setExamsuiteName(examsuiteName);
            if (StringUtils.isBlank(peispatient.getMedicaltype())) {
                peispatient.setMedicaltype(tjlb);
            }
            // 接害因素
            if (StringUtils.isBlank(peispatient.getJhys())) {
                peispatient.setJhys(jhys);
            }
            if (null == peispatient.getBirthdate()) {
                if (StringUtils.isBlank(peispatient.getIdcardno())) {
                    peispatient.setBirthdate(date);
                } else {
                    try {
                        peispatient.setBirthdate(format.parse(peispatient.getIdcardno().substring(6, 14)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 体检类型
            peispatient.setIdExamtype(idExamType);
            // 团体id
            peispatient.setIdOrg(idOrg);
            // 团体名称
            peispatient.setOrgName(orgName);
            // 输入码
            peispatient.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
            // 已备单
            peispatient.setFIsforprepare(1);
            // 开单医师ID
            peispatient.setIdOpendoctor(xsjlId);
            peispatient.setDoctorapply(xsjlName);
            // SABC等级验证，null的不会更新
            if (StringUtils.isNotEmpty(peispatient.getSabc())) {
                peispatient.setSabc(ToolUtil.validateSABC(peispatient.getSabc()));
            }

            // 15位或18位身份证进行校验,其余的不校验
            if (StringUtils.isNotBlank(peispatient.getIdcardno()) && (peispatient.getIdcardno().length() == 15 || peispatient.getIdcardno().length() == 18)) {
                peispatient.setIdcardno(peispatient.getIdcardno().toUpperCase());
                String card = peispatient.getIdcardno();
                if (!IdcardUtil.isValidCard(card)) {
                    throw new ServiceException("预登记失败：" + map.getPatientname() + " 体检者的身份证号不合法");
                }
                // 如果长度是15位
                if (card.length() == 15) {
                    card = card.substring(0, 6) + "19" + card.substring(6) + "x";
                }
                // 年龄匹配
                peispatient.setAge(IdcardUtil.getAgeByIdCard(card));
                // 生日匹配
                peispatient.setBirthdate(IdcardUtil.getBirthDate(card));
                // 匹配性别
                card = card.substring(card.length() - 2, card.length() - 1);
                // 性别是否匹配
                Integer strSex = (Integer.valueOf(card) & 1) != 0 ? 0 : 1;
                if (!ObjectUtils.isEmpty(peispatient.getIdSex()) && !strSex.equals(peispatient.getIdSex())) {
                    throw new ServiceException("预登记失败：" + map.getPatientname() + " 体检者的身份证号与性别不匹配");
                }
                //性别是否和分组的性别一致
                if (strSex == 0) {
                    if (group.getFMale() != 1) {
                        throw new ServiceException("预登记失败：" + map.getPatientname() + " 体检者的性别和分组性别不匹配");
                    }
                } else {
                    if (group.getFFemale() != 1) {
                        throw new ServiceException("预登记失败：" + map.getPatientname() + " 体检者的性别和分组性别不匹配");
                    }
                }
                peispatient.setIdSex(strSex);


                //根据身份证判断籍贯
                Area area = null;
                if (StringUtils.isNotBlank(peispatient.getIdcardno())) {
                    area = areaMapper.selectOne(new QueryWrapper<Area>().eq("area_code", peispatient.getIdcardno().substring(0, 2)));
                    if (null == area) {
                        area = defaultArea;
                    }
                } else {
                    area = defaultArea;
                }
                if (null != area) {
                    peispatient.setIdResarea(area.getId());
                    peispatient.setResarea(area.getResarea());
                }
            }
            // 根据总工龄计算参加工作时间
            if (null != peispatient.getZgl() && 0 != peispatient.getZgl()) {
                peispatient.setWorkDate(subTime(getDateForMonth(peispatient.getZgl())));
            } else if (null != peispatient.getWorkDate()) {
                peispatient.setZgl(getMonthSpace(date, subTime(peispatient.getWorkDate())));
            }
            // 根据接害工龄计算从事该工种工作时间
            if (null != peispatient.getJhgl() && 0 != peispatient.getJhgl()) {
                peispatient.setHarmDate(subTime(getDateForMonth(peispatient.getJhgl())));
            } else if (null != peispatient.getHarmDate()) {
                peispatient.setJhgl(getMonthSpace(date, subTime(peispatient.getHarmDate())));
            }
            //会员类型
            if (map.getIdPatientclass() == null) {
                peispatient.setIdPatientclass("1");
            } else {
                peispatient.setIdPatientclass(map.getIdPatientclass());
            }
            // 登记员
            peispatient.setIdDoctorreg(registerR);
            if ("removed".equals(map.getState())) {
                if (null != map.getId()) {
                    Peispatient peispatientNew = peispatientMapper.getInfoById(map.getId());
                    // 判断是否为空
                    if (null != peispatientNew) {
                        // 删除（真删）
                        peispatientMapper.deleteById(peispatientNew);
                    }
                }
            } else if ("modified".equals(map.getState())) {
                //修改
                Peispatient peispatientNew = peispatientMapper.getInfoById(map.getId());
                // 判断是否存在
                if (null != peispatientNew) {
                    // 判断人员信息是否重复添加
                    String rlt = isExitsName(peispatient, orderNum);
                    if (!"success".equals(rlt)) {
                        // 存在重复
                        throw new ServiceException(rlt);
                    }
                    boolean hasNotCode = StringUtils.isBlank(peispatientNew.getPatientcode());
                    if (hasNotCode) {
                        // 体检号
                        String patientCode = "";
                        do {
                            patientCode = CodeUtil.getPatientCode(Constants.ONLINE_PREFIX, iSysConfigService.selectConfigByKey(Constants.VERSION_NO));
                            //判断体检号是否存在
                        } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                                .eq(Peispatient::getPatientcode, patientCode)) > 0);
                        peispatient.setPatientcode(patientCode);
                        peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
                        //体检号生成人  生成时间
                        peispatient.setTimingstartedat(date);
                        peispatient.setGuidancenote2(registerR);
                    }
                    peispatient.setIdPayway(group.getIdPayway());
                    //拷贝
                    BeanUtils.copyBeanProp(peispatientNew, peispatient);
                    //是否登记：0.未登记 1.已登记
                    if (null == peispatientNew.getFRegistered() || peispatientNew.getFRegistered() != 1) {
                        // 更新收费项目实体类
                        peispatientMapper.updateById(peispatientNew);
                        //体检者收费项目表
                        List<Peispatientfeeitem> pItems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                                .eq("id_patient", peispatientNew.getPatientcode()));
                        if (pItems.size() > 0) {
                            for (Peispatientfeeitem peispatientfeeitem : pItems) {
                                //支付方式ID
                                peispatientfeeitem.setIdPayway(group.getIdPayway());
                            }
                            peispatientfeeitemService.updateBatchById(pItems);
                            continue;
                        }
                        // 绑定套餐的收费项目
                        List<HashMap<String, String>> items = getExamfeeitem(peispatient.getIdTjtc());
                        List<Peispatientfeeitem> peispatientfeeitems = new ArrayList<Peispatientfeeitem>();
                        int size = items.size();
                        for (int j = 0; j < size; j++) {
                            //查询当前体检者是否收该收费项目，有的话跳过
                            long count1 = peispatientfeeitemService.count(new LambdaQueryWrapper<Peispatientfeeitem>()
                                    .eq(Peispatientfeeitem::getIdExamfeeitem, items.get(j).get("idExamfeeitem"))
                                    .eq(Peispatientfeeitem::getIdPatient, peispatientNew.getPatientcode())
                            );
                            if (count1 > 0) {
                                continue;
                            }
                            Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
                            peispatientfeeitem.setIdPatient(peispatientNew.getPatientcode());
                            peispatientfeeitem.setIdExamfeeitem(items.get(j).get("idExamfeeitem"));
                            peispatientfeeitem.setExamfeeitemName(items.get(j).get("examfeeitemName"));
                            peispatientfeeitem.setPrice(Double.valueOf(items.get(j).get("price")));
                            peispatientfeeitem.setIdKs(items.get(j).get("idKs"));
                            peispatientfeeitem.setQty(items.get(j).get("qty") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("qty"))));
                            //拆分的价格
                            peispatientfeeitem.setFactprice(Double.parseDouble(String.valueOf(items.get(j).get("factprice"))));
                            peispatientfeeitem.setCount(1);
                            peispatientfeeitem.setIdPayway(group.getIdPayway());
                            peispatientfeeitem.setFRegistered(0);
                            peispatientfeeitem.setChangeItem(0);
                            peispatientfeeitem.setFMarkFeereturn(0);
                            peispatientfeeitem.setFFeecharged(0);
                            peispatientfeeitem.setFLabsendtolis(0);
                            peispatientfeeitem.setFExaminated(0);
                            peispatientfeeitem.setFGiveup(0);
                            peispatientfeeitem.setIsbx(Integer.parseInt(String.valueOf(items.get(j).get("isbx"))));
                            peispatientfeeitem.setBxcount(items.get(j).get("bxcount") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("bxcount"))));
                            peispatientfeeitem.setFDelayed(0);
                            peispatientfeeitem.setIsMintc(1);
                            peispatientfeeitem.setItemGroup(items.get(j).get("group") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("group"))));
                            peispatientfeeitems.add(peispatientfeeitem);
                        }
                        peispatientfeeitemService.saveBatch(peispatientfeeitems);

                    }
                    if (hasNotCode) {
                        peispatientChargeMainMapper.insert(new PeispatientChargeMain(
                                note
                                , peispatient.getMoneyamount()
                                , peispatient.getMoneyamountpaid()
                                , peispatient.getPatientcode()));
                    }
                    //添加体检者和分中心
                    savePeiAndFzx(Arrays.asList(peispatient));
                } else {
                    // 不存在
                    throw new ServiceException("预登记失败：" + map.getPatientname() + " 体检者不存在，已经被删除");
                }
            } else if ("added".equals(map.getState())) {
                if (isFinished) {
                    throw new ServiceException("预登记失败：订单已结束，不能添加人员");
                }
                // 体检号
                String patientCode = "";
                do {
                    patientCode = CodeUtil.getPatientCode(ZhongkangConfig.isOnline() ?
                            Constants.ONLINE_PREFIX : iSysBranchService.getBranchFlag(null),
                            ZhongkangConfig.isOnline() ? iSysConfigService.selectConfigByKey(Constants.VERSION_NO) : "");
                    //判断体检号是否存在
                } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                        .eq(Peispatient::getPatientcode, patientCode)) > 0);
                peispatient.setPatientcode(patientCode);
                peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
                peispatient.setFRegistered(0);
                peispatient.setIdPayway(group.getIdPayway());

                //体检号生成人  生成时间
                peispatient.setTimingstartedat(date);
                peispatient.setGuidancenote2(registerR);

                // 判断人员信息是否重复添加
                String rlt = isExitsName(peispatient, orderNum);
                if (!"success".equals(rlt)) {
                    // 存在重复
                    throw new ServiceException(rlt);
                }

                //应付价格和实付价格
                if (BigDecimal.valueOf(zhjg).compareTo(BigDecimal.ZERO) != 0) {
                    peispatient.setMoneyamount(zhjg);
                    peispatient.setMoneyamountpaid(0.0);
                }

                // 保存实体类
                peispatientMapper.insert(peispatient);
                String result = peispatient.getId();
                if (StringUtils.isBlank(result)) {
                    throw new ServiceException("" + map.getPatientname() + " 预登记失败");
                }
                // 绑定套餐的收费项目
                List<HashMap<String, String>> items = getExamfeeitem(peispatient.getIdTjtc());
                List<Peispatientfeeitem> peispatientfeeitems = new ArrayList<Peispatientfeeitem>();
                int size = items.size();
                // 折扣价格是否放在【个检报告工本费】上
                for (int j = 0; j < size; j++) {
                    Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
                    peispatientfeeitem.setIdPatient(patientCode);
                    peispatientfeeitem.setIdExamfeeitem(items.get(j).get("idExamfeeitem"));
                    peispatientfeeitem.setExamfeeitemName(items.get(j).get("examfeeitemName"));
                    peispatientfeeitem.setPrice(Double.valueOf(items.get(j).get("price")));
                    peispatientfeeitem.setIdKs(items.get(j).get("idKs"));

                    peispatientfeeitem.setFactprice(Double.parseDouble(String.valueOf(items.get(j).get("factprice"))));
                    peispatientfeeitem.setCount(1);
                    peispatientfeeitem.setIdPayway(group.getIdPayway());
                    peispatientfeeitem.setFRegistered(0);
                    peispatientfeeitem.setChangeItem(0);
                    peispatientfeeitem.setFMarkFeereturn(0);
                    peispatientfeeitem.setFFeecharged(0);
                    peispatientfeeitem.setFLabsendtolis(0);
                    peispatientfeeitem.setFExaminated(0);
                    peispatientfeeitem.setFGiveup(0);
                    peispatientfeeitem.setIsbx(Integer.parseInt(String.valueOf(items.get(j).get("isbx"))));
                    peispatientfeeitem.setBxcount(items.get(j).get("bxcount") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("bxcount"))));
                    peispatientfeeitem.setFDelayed(0);
                    peispatientfeeitem.setIsMintc(1);
                    peispatientfeeitem.setQty(items.get(j).get("qty") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("qty"))));
                    peispatientfeeitem.setItemGroup(items.get(j).get("group") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("group"))));
                    peispatientfeeitems.add(peispatientfeeitem);
                }
                peispatientfeeitemService.saveBatch(peispatientfeeitems);

                peispatientChargeMainMapper.insert(new PeispatientChargeMain(
                        note
                        , peispatient.getMoneyamount()
                        , peispatient.getMoneyamountpaid()
                        , peispatient.getPatientcode()));
                //添加体检者和分中心
                savePeiAndFzx(Arrays.asList(peispatient));
            }

            //todo 客户预约VIP、贵宾当次消费需达到800、1500；同天同单位预约VIP、贵宾人数不能超过5个人
            if (!"removed".equals(map.getState())) {
                //判断是否开启审批流
                Workflow workflow = workflowService.isOpen(SecurityUtils.getCId(), WorkflowType.OVER_RESERVATION.getCode());
                if (Objects.nonNull(workflow)) {
                    String idPatientclass = peispatient.getIdPatientclass();
                    PriceAndFactPriceDto priceAndFactprice = peispatientfeeitemService.getPriceAndFactprice(peispatient.getPatientcode());
                    Double moneyamount = priceAndFactprice.getFactprice();
                    //查询订单信息
                    Createorder createorder = createorderService.getOne(new LambdaQueryWrapper<Createorder>()
                            .eq(Createorder::getDdh, peispatient.getNumorgresv()));

                    //开启的话,需要判断是否达到会员额度（VIP800、贵宾1500）
                    if ((idPatientclass.equals("2") && moneyamount < 800.0)
                            || (idPatientclass.equals("3") && moneyamount < 1500.0)) {
                        //查询有没有重复提交
                        List<WorkflowReservationType> workflowReservationTypeList = workflowReservationTypeService.list(new LambdaQueryWrapper<WorkflowReservationType>()
                                .eq(WorkflowReservationType::getPatientcode, peispatient.getPatientcode())
                                .eq(WorkflowReservationType::getOrderId, createorder.getId())
                                .orderByDesc(WorkflowReservationType::getCreatedate)
                        );
                        if (CollectionUtil.isEmpty(workflowReservationTypeList)) {
                            WorkflowReservationType workflowReservationType = new WorkflowReservationType();
                            workflowReservationType.setPatientcode(peispatient.getPatientcode());
                            workflowReservationType.setIdPatientclass(peispatient.getIdPatientclass());
                            workflowReservationType.setCreatedate(new Date());
                            workflowReservationType.setCreator(SecurityUtils.getUserNo());
                            workflowReservationType.setOrderId(createorder.getId());
                            workflowReservationTypeService.save(workflowReservationType);

                            //审批时会员类型默认为普通会员
                            peispatient.setIdPatientclass("1");
                            peispatientService.updateById(peispatient);
                        } else {
                            //查询最后一次提交的记录是什么状态
                            WorkflowReservationType workflowReservationType = workflowReservationTypeList.get(0);
                            String caseId = workflowReservationType.getCaseId();
                            if (StringUtils.isEmpty(caseId)) {
                                //只是创建了，还没提交
                                peispatient.setIdPatientclass("1");
                                peispatientService.updateById(peispatient);
                            } else {
                                //有三种情况，一种是审批通过的就不修改了，一种是还在进行中，一种是被驳回或者失效的这个可以重新提交
                                WorkflowCase workflowCase = workflowCaseService.getInfoById(caseId);
                                if (workflowCase.getStatus() == 2) {
                                    //通过的不修改
                                } else if (workflowCase.getStatus() == 0 || workflowCase.getStatus() == 1) {
                                    //进行中的会员类型得改回普通会员
                                    peispatient.setIdPatientclass("1");
                                    peispatientService.updateById(peispatient);
                                } else {
                                    //被驳回或者失效的重新提交
                                    WorkflowReservationType workflowReservationType1 = new WorkflowReservationType();
                                    workflowReservationType1.setPatientcode(peispatient.getPatientcode());
                                    workflowReservationType1.setIdPatientclass(peispatient.getIdPatientclass());
                                    workflowReservationType1.setCreatedate(new Date());
                                    workflowReservationType1.setCreator(SecurityUtils.getUserNo());
                                    workflowReservationType1.setOrderId(createorder.getId());
                                    workflowReservationTypeService.save(workflowReservationType1);

                                    //审批时会员类型默认为普通会员
                                    peispatient.setIdPatientclass("1");
                                    peispatientService.updateById(peispatient);

                                }
                            }
                        }

                    }
                }
            }
        }


        return Boolean.TRUE;
    }


    /**
     * 判断当前时间是否是7-12点中
     * @return
     */
    public static boolean isInMorningTime() {
//        String timeString = "09:30";
//        LocalTime now = LocalTime.parse(timeString);
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(7, 0);
        LocalTime end = LocalTime.of(12, 0);

        return !now.isBefore(start) && now.isBefore(end);
    }



    /**
     * 导入名单
     *
     * @param param 导入参数
     * @return
     */
    @Override
    public R importPatientBatch(ImportPatientBatchParam param) {
        // TODO wait 需要完善和整理出导入名单的字段必填项和判断名单里的体检者信息是否可以导入（同个订单同一个人是否可以有多个条登记记录）
        Integer mt = param.getModelType();
        //验证信息
        List<Peisorgreservationgroup> groupList = null;
        Createorder order = null;
        // 团体任务
        Peisorgreservation vation = null;
        Sellcustomer customer = null;
        SysUser qxUsers = null;
        if (mt == 2) {
            order = orderMapper.selectById(param.getId());
            if (Objects.isNull(order)) {
                throw new ServiceException("名单导入失败：订单不存在或者已删除！");
            }
            customer = sellcustomerMapper.selectById(order.getKhdwmcid());
            qxUsers = sysUserMapper.getUserByNo(order.getXsjlid());
        } else {
            groupList = peisorgreservationgroupMapper.selectList(new LambdaQueryWrapper<Peisorgreservationgroup>()
                    .eq(Peisorgreservationgroup::getIdOrgreservation, param.getId()).eq(Peisorgreservationgroup::getIsDelete, 0));
            if (groupList.size() == 0) {
                throw new ServiceException("名单导入失败：该体检团体下分组信息不存在");
            }
            vation = peisorgreservationMapper.selectById(param.getId());
            if (Objects.isNull(vation)) throw new ServiceException("名单导入失败： 该体检团体任务不存在");
            if (vation.getFFinished() != null && vation.getFFinished() == 1)
                throw new ServiceException("订单已结束，不能导入人员信息");

            Peisorgreservation task = peisorgreservationMapper.selectById(param.getId());

            //排除分组下无体检者并且订单的体检套餐隐藏的分组
            List<Peisorgreservationgroup> newList = new ArrayList<>();
            for (int i = 0; i < groupList.size(); i++) {

                Orderandcombo orderandcombo = orderandcomboService.getOne(new LambdaQueryWrapper<Orderandcombo>()
                        .eq(Orderandcombo::getDdid, task.getDdh())
                        .eq(Orderandcombo::getTcid, groupList.get(i).getIdExamsuite()));
                //是否显示
                if (ObjectUtils.isNotEmpty(orderandcombo) && Objects.nonNull(orderandcombo.getShow()) && orderandcombo.getShow() == 1) {
                    //套餐隐藏了，再判断该分组下是否有体检者
                    Long hadpp = peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                            .eq(Peispatient::getIdOrgreservationgroup, groupList.get(i).getId()));
                    if (hadpp == 0) {
                        //该分组下无体检者
                        break;
                    }
                }
                newList.add(groupList.get(i));
            }
            if (CollectionUtil.isEmpty(newList)) {
                throw new ServiceException("名单导入失败： 该体检团体下分组信息不存在");
            }
            groupList = newList;
            order = orderMapper.selectById(vation.getDdh());
            if (Objects.isNull(order)) {
                throw new ServiceException("名单导入失败：订单不存在或者已删除！");
            }
            customer = sellcustomerMapper.selectById(vation.getIdOrg());
            qxUsers = sysUserMapper.getUserByNo(vation.getIdSalesperson());
        }

        //读取数据
        ExcelUtil<PeispatientImportDto> util = new ExcelUtil<PeispatientImportDto>(PeispatientImportDto.class);
        List<PeispatientImportDto> list = null;
        try {
            list = util.importExcel(param.getFile().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("文件内容解析失败！");
        }
        if (CollectionUtil.isEmpty(list)) {
            throw new ServiceException("名单导入失败：导入名单中不存在人员信息");
        }
        if (isInMorningTime() && list.size() > 100){
            throw new ServiceException("名单导入失败：由于早上同步数据较多,7点-12点间,导入名单单次最多导入100人！");
        }
        if (list.size() > 1000){
            throw new ServiceException("名单导入失败：导入名单中人员数量不能大于1000");
        }


        //导入的名单可能有空格，在这去掉,只去前后的空格
        for (int i = 0; i < list.size(); i++) {
            PeispatientImportDto dto = list.get(i);
            if (StringUtils.isNotEmpty(dto.getPatientname())) {
                dto.setPatientname(dto.getPatientname().trim());
            }
            if (StringUtils.isNotEmpty(dto.getIdcardno())) {
                dto.setIdcardno(dto.getIdcardno().trim());
            }
            if (StringUtils.isNotEmpty(dto.getSexMale())) {
                dto.setSexMale(dto.getSexMale().trim());
            }
            if (StringUtils.isNotEmpty(dto.getMarriage())) {
                dto.setMarriage(dto.getMarriage().trim());
            }
            if (StringUtils.isNotEmpty(dto.getOrgDepart())) {
                dto.setOrgDepart(dto.getOrgDepart().trim());
            }
            if (StringUtils.isNotEmpty(dto.getPhone())) {
                dto.setPhone(dto.getPhone().trim());
            }
            if (StringUtils.isNotEmpty(dto.getHospitalcode())) {
                dto.setHospitalcode(dto.getHospitalcode().trim());
            }
            //组类必填
            if (StringUtils.isNotEmpty(dto.getGrouptype())) {
                dto.setGrouptype(dto.getGrouptype().trim());
            } else {
                throw new ServiceException("第"+(i+2)+"行,组类不能为空！");
            }
            if (StringUtils.isNotEmpty(dto.getSabc())) {
                dto.setSabc(ToolUtil.validateSABC(dto.getSabc()));
            }
            if (StringUtils.isNotEmpty(dto.getWorkno())) {
                dto.setWorkno(dto.getWorkno().trim());
            }

            //分中心
//            if (StringUtils.isBlank(dto.getHospitalcode())) {
//                throw new ServiceException(dto.getPatientname() + "分中心不能为空!");
//            }
        }


        //禁检的分组 不能导入
        Set<String> groupTypeList = list.stream()
                .map(PeispatientImportDto::getGrouptype)
                .collect(Collectors.toSet());
        if (ObjectUtils.isNotEmpty(groupTypeList)) {
            List<Peisorgreservationgroup> peisorgreservationgroups = peisorgreservationgroupMapper.selectList(new LambdaQueryWrapper<Peisorgreservationgroup>()
                    .eq(Peisorgreservationgroup::getIdOrgreservation, param.getId())
                    .in(Peisorgreservationgroup::getGrouptype, groupTypeList)//组类
                    .eq(Peisorgreservationgroup::getFGrouppaused, 1)//禁用
                    .eq(Peisorgreservationgroup::getIsDelete, 0));
            if (ObjectUtils.isNotEmpty(peisorgreservationgroups)) {
                String collect = peisorgreservationgroups.stream().map(Peisorgreservationgroup::getGrouptype)
                        .collect(Collectors.joining(","));
                throw new ServiceException("组类:" + collect + "已禁用!");
            }
        }

        log.info("导入名单内容:{}、{}", param, list);
        //文件表名信息
//        List<Object[]> fields = util.getFields();
//        log.info("字段注解信息：{}", JSONUtil.toJsonStr(fields));

        //新建体检者接收列表
        List<Peispatient> importData = new ArrayList<>();

        // 匹配消息
        String msg = StringUtils.EMPTY;

        String userNo = SecurityUtils.getUserNo();
        Set<String> compare = new HashSet<String>();
        //健康
        if (mt == 1 || mt == 3) {
            // 健康模板
            Set<String> phones = new HashSet<String>();
            int i = 0;
            for (PeispatientImportDto item : list) {
                if (Objects.isNull(item)) continue;

                item.setPicture(""); //TODO 设置默认头像
                item.setOrgName(Objects.isNull(customer) ? "" : customer.getKhdwmc());
                item.setDoctorapply(Objects.isNull(qxUsers) ? "" : qxUsers.getUserName());
                item.setOrderNum(order.getDdh());
                item.setIdOrg(order.getKhdwmcid());
                item.setIdOpendoctor(order.getXsjlid());
                item.setIdInformway(order.getIdInforway());
                item.setIdDoctorreg(userNo);
//                //设置分组ID
//                item.setIdOrgreservationgroup(param.getId());

                // 分中心校验
                String hospitalcodeStr = item.getHospitalcode();
                if (StringUtils.isNotBlank(hospitalcodeStr)) {
                    String[] split = hospitalcodeStr.split("-");
                    if (split.length != 2) {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，该分中心不合法：" + hospitalcodeStr);
                    }
                    String hospitalcode = split[1];
                    if (StringUtils.isBlank(hospitalcode)) {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，该分中心不合法：" + hospitalcodeStr);
                    }
                    //判断分中心是否可用
                    String cId = SecurityUtils.getCId();
                    if (!cId.equals(hospitalcode)) {
                        List<SysUserBranch> userBranches = sysUserBranchService.list(new LambdaQueryWrapper<SysUserBranch>().eq(SysUserBranch::getUserId, SecurityUtils.getUserNo()));
                        List<String> branchIds = userBranches.stream().map(SysUserBranch::getBranchId).collect(toList());
                        if (!CollectionUtil.contains(branchIds, hospitalcode)) {
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，你当前无法备单该分中心：" + hospitalcodeStr);
                        }
                    }
                    log.info("分中心：hospitalcode={}, hospitalcodeStr={}", hospitalcode, hospitalcodeStr);
                    item.setHospitalcode(hospitalcode);

                }

                // 姓名验证
                if (StringUtils.isNotBlank(item.getPatientname())) {
                    String message = checkLength(item.getPatientname(), "姓名长度不能超过25位", 25);
                    if (StringUtils.isNotBlank(message))
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + message);
                } else throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，姓名不能为空！");

                // 性别校验
                if (StringUtils.isNotBlank(item.getSexMale())) {
                    if ("男".equals(item.getSexMale()) || "女".equals(item.getSexMale())) {
                        item.setIdSex("男".equals(item.getSexMale()) ? 0 : 1);
                    } else {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 性别不合法！");
                    }
                } else
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 性别不能为空！");

                // 年龄校验
                if (Objects.nonNull(item.getAge())) {
                    // 判断年龄范围
                    if (item.getAge() < 0)
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 年龄必须在0岁以上！");
                    else if (item.getAge() > 200)
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 年龄必须在200岁以下！");
                } else
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 年龄不能为空！");

                // 身份证校验
                if (StringUtils.isNotBlank(item.getIdcardno())) {
                    log.info(item.getPatientname() + "：" + item.getIdcardno());
                    if ("1".equals(item.getIdcardno())){
                        //填1不校验身份证
                        item.setIdcardno(null);
                    }else {
                        if (!IdcardUtil.isValidCard(item.getIdcardno()))
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 身份证号不合法！");
                        else {
                            item.setIdcardno(item.getIdcardno().toUpperCase());
                            String card = item.getIdcardno();
                            // 如果长度是15位
                            if (card.length() == 15) {
                                card = card.substring(0, 6) + "19" + card.substring(6) + "x";
                            }
                            // 生日匹配
                            item.setBirthdate(IdcardUtil.getBirthDate(card));
                            // 年龄匹配
                            item.setAge(IdcardUtil.getAgeByIdCard(card));
                            // 匹配性别
                            card = card.substring(card.length() - 2, card.length() - 1);
                            // 性别是否匹配
                            Integer sex = (Integer.valueOf(card) & 1) != 0 ? 0 : 1;
                            if (item.getIdSex() != sex) {
                                throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 身份证号与性别不匹配！");
                            }


                            //导入名单的时候或者完成登记,用areacode匹配区域
                            String areaCode = item.getIdcardno().substring(0, 2);
                            Area area = areaMapper.selectOne(new LambdaQueryWrapper<Area>().eq(Area::getAreaCode, areaCode));
                            if (Objects.nonNull(area)) {
                                item.setIdResarea(area.getId());
                                item.setResarea(area.getResarea());
                            }
                        }
                    }

                } else throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，身份证号不能为空！");

                // 婚姻校验
                if (StringUtils.isNotBlank(item.getMarriage())) {
                    Integer marriageType = MarriageType.getValueByName(item.getMarriage());
                    if (Objects.isNull(marriageType))
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 婚姻不存在！");
                    item.setIdMarriage(marriageType);
                }
                // 部门校验
                if (StringUtils.isNotBlank(item.getOrgDepart())) {
                    String message = checkLength(item.getOrgDepart(), "部门长度不能超过30位", 30);
                    if (StringUtils.isNotBlank(message))
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + message);
                }

                // 联系电话校验
                if (StringUtils.isNotBlank(item.getPhone())) {
                    if ("1".equals(item.getPhone())){
                        //填1不校验手机号
                        item.setPhone(null);
                    }else {
                        if (item.getPhone().length() > 13) {
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 联系电话不合法，不能超过13位！");
                        } else if (item.getPhone().indexOf("0") != 0 && item.getPhone().length() != 11) {
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 联系电话格式错误！");
                        } else {
                            phones.add(item.getPhone());
                        }
                    }
                } else throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，联系电话不能为空！");

                // 备注
                if (StringUtils.isNotBlank(item.getNote())) {
                    if (item.getNote().length() > 500)
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 备注过长，不能超过500字！");
                }

                // 统收限额
                if (Objects.nonNull(item.getTsLimit())) {
                    if (!StringUtil.isNum(item.getTsLimit())) {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 统收限额必须是数字！");
                    } else {
                        Double tsLimit = item.getTsLimit();
                        if (tsLimit < 0 || tsLimit > 999999.99) {
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 统收限额数值超出范围0~999999.99！");
                        }
                    }
                } else {
                    //throw new Exception("名单导入失败： 第"+ (i+1) +"行，" + item.getPatientname() + " 统收限额不能为空！");
                }

                // 模板数据匹配
                msg += bindInfo(param.getId(), groupList, item, importData, 0, compare);
                i++;
            }
        } else if (mt == 2) {
            //app导入
            String numorgresv = order.getDdh();
            // 循环遍历存储的Excel值
            Set<String> phones = new HashSet<String>();
            int i = 0;
            for (PeispatientImportDto item : list) {
                if (Objects.isNull(item)) continue;
                item.setPicture(""); //TODO 设置默认头像
                item.setOrgName(Objects.isNull(customer) ? "" : customer.getKhdwmc());
                item.setDoctorapply(Objects.isNull(qxUsers) ? "" : qxUsers.getUserName());
                item.setOrderNum(order.getDdh());
                item.setIdOrg(order.getKhdwmcid());
                item.setIdOpendoctor(order.getXsjlid());
                item.setIdInformway(order.getIdInforway());
                item.setIdDoctorreg(userNo);

                // 分中心校验
                String hospitalcodeStr = item.getHospitalcode();
                if (StringUtils.isNotBlank(hospitalcodeStr)) {
                    String[] split = hospitalcodeStr.split("-");
                    if (split.length != 2) {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，该分中心不合法：" + hospitalcodeStr);
                    }
                    String hospitalcode = split[1];
                    if (StringUtils.isBlank(hospitalcode)) {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，该分中心不合法：" + hospitalcodeStr);
                    }
                    //判断分中心是否可用
                    String cId = SecurityUtils.getCId();
                    if (!cId.equals(hospitalcode)) {
                        List<SysUserBranch> userBranches = sysUserBranchService.list(new LambdaQueryWrapper<SysUserBranch>().eq(SysUserBranch::getUserId, SecurityUtils.getUserNo()));
                        List<String> branchIds = userBranches.stream().map(SysUserBranch::getBranchId).collect(toList());
                        if (!CollectionUtil.contains(branchIds, hospitalcode)) {
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，你当前无法备单该分中心：" + hospitalcodeStr);
                        }
                    }
//                    log.info("分中心：hospitalcode={}, hospitalcodeStr={}", hospitalcode, hospitalcodeStr);
                    item.setHospitalcode(hospitalcode);

                }
                // 姓名验证
                if (StringUtils.isNotBlank(item.getPatientname())) {
                    String message = checkLength(item.getPatientname(), "姓名长度不能超过25位", 25);
                    if (StringUtils.isNotBlank(message))
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + message);
                } else throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，姓名不能为空！");

                // 性别校验
                if (StringUtils.isNotBlank(item.getSexMale())) {
                    if ("男".equals(item.getSexMale()) || "女".equals(item.getSexMale())) {
                        item.setIdSex("男".equals(item.getSexMale()) ? 0 : 1);
                    } else {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 性别不合法！");
                    }
                } else
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 性别不能为空！");

                // 年龄校验
                if (Objects.nonNull(item.getAge())) {
                    // 判断年龄范围
                    if (item.getAge() < 0)
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 年龄必须在0岁以上！");
                    else if (item.getAge() > 200)
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 年龄必须在200岁以下！");
                } else
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 年龄不能为空！");

                // 身份证校验
                if (StringUtils.isNotBlank(item.getIdcardno())) {
                    // 身份证号匹配
                    if ("1".equals(item.getIdcardno())){
                        //填1不校验身份证
                        item.setIdcardno(null);
                    }else{
                        if (!IdcardUtil.isValidCard(item.getIdcardno()))
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 身份证号不合法！");
                        else {
                            item.setIdcardno(item.getIdcardno().toUpperCase());
                            String card = item.getIdcardno();
                            // 如果长度是15位
                            if (card.length() == 15) {
                                card = card.substring(0, 6) + "19" + card.substring(6) + "x";
                            }
                            //生日匹配
                            item.setBirthdate(IdcardUtil.getBirthDate(card));
                            // 年龄匹配
                            item.setAge(IdcardUtil.getAgeByIdCard(card));
                            // 匹配性别
                            card = card.substring(card.length() - 2, card.length() - 1);
                            // 性别是否匹配
                            Integer sex = (Integer.valueOf(card) & 1) != 0 ? 0 : 1;
                            if (item.getIdSex() != sex) {
                                throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 身份证号与性别不匹配！");
                            }


                            //导入名单的时候或者完成登记,用areacode匹配区域
                            String areaCode = item.getIdcardno().substring(0, 2);
                            Area area = areaMapper.selectOne(new LambdaQueryWrapper<Area>().eq(Area::getAreaCode, areaCode));
                            if (Objects.nonNull(area)) {
                                item.setIdResarea(area.getId());
                                item.setResarea(area.getResarea());
                            }
                        }
                    }
                } else throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，身份证号不能为空！");

                // 婚姻校验
                if (StringUtils.isNotBlank(item.getMarriage())) {
                    Integer marriageType = MarriageType.getValueByName(item.getMarriage());
                    if (Objects.isNull(marriageType))
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 婚姻不存在！");
                    item.setIdMarriage(marriageType);
                }
                // 部门校验
                if (StringUtils.isNotBlank(item.getOrgDepart())) {
                    String message = checkLength(item.getOrgDepart(), "部门长度不能超过30位", 30);
                    if (StringUtils.isNotBlank(message))
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + message);
                }

                // 联系电话校验
                if (StringUtils.isNotBlank(item.getPhone())) {
                    if ("1".equals(item.getPhone())){
                        //填1不校验手机号
                        item.setPhone(null);
                    }else {
                        if (item.getPhone().length() > 13) {
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 联系电话不合法，不能超过13位！");
                        } else if (item.getPhone().indexOf("0") != 0 && item.getPhone().length() != 11) {
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 联系电话格式错误！");
                        } else {
//                            if (phones.contains(item.getPhone())) {
//                                throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 联系电话与模板中其他人员的电话重复！");
//                            }
//
//                            if (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
//                                    .eq(Peispatient::getNumorgresv, order.getDdh()).eq(Peispatient::getPhone, item.getPhone())) > 0) {
//                                throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 具有相同联系电话的人员已存在！");
//                            }
                            phones.add(item.getPhone());
                        }
                    }
                } else throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，联系电话不能为空！");

                // 备注
                if (StringUtils.isNotBlank(item.getNote())) {
                    if (item.getNote().length() > 500)
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 备注过长，不能超过500字！");
                }

                // 统收限额
                if (Objects.nonNull(item.getTsLimit())) {
                    if (!StringUtil.isNum(item.getTsLimit())) {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 统收限额必须是数字！");
                    } else {
                        Double tsLimit = item.getTsLimit();
                        if (tsLimit < 0 || tsLimit > 999999.99) {
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 统收限额数值超出范围0~999999.99！");
                        }
                    }
                } else {
                    //throw new Exception("名单导入失败： 第"+ (i+1) +"行，" + item.getPatientname() + " 统收限额不能为空！");
                }

                // 模板数据匹配
                msg += bindInfo(param.getId(), groupList, item, importData, 0, compare);
                i++;
            }
        } else {
            // 职业、综合模板
            // 循环遍历存储的Excel值
            Set<String> phones = new HashSet<String>();
            int i = 0;
            for (PeispatientImportDto item : list) {

                if (Objects.isNull(item)) continue;
                item.setPicture(""); //TODO 设置默认头像
                item.setIdOrder(vation.getDdh());
                item.setVation(vation);
                item.setOrgName(Objects.isNull(customer) ? "" : customer.getKhdwmc());
                item.setDoctorapply(Objects.isNull(qxUsers) ? "" : qxUsers.getUserName());
                item.setOrderNum(order.getDdh());
                item.setIdInformway(order.getIdInforway());
//                item.setIdOrg(order.getKhdwmcid());
//                item.setIdOpenDoctor(order.getXsjlid());
                item.setIdDoctorreg(userNo);

                // 分中心校验
                String hospitalcodeStr = item.getHospitalcode();
                if (StringUtils.isNotBlank(hospitalcodeStr)) {
                    String hospitalcode = StringUtils.substringAfter(hospitalcodeStr, "-");
                    if (StringUtils.isBlank(hospitalcode)) {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，该分中心不合法：" + hospitalcodeStr);
                    }
                    //判断分中心是否可用
                    String cId = SecurityUtils.getCId();
                    if (!cId.equals(hospitalcode)) {
                        List<SysUserBranch> userBranches = sysUserBranchService.list(new LambdaQueryWrapper<SysUserBranch>().eq(SysUserBranch::getUserId, SecurityUtils.getUserNo()));
                        List<String> branchIds = userBranches.stream().map(SysUserBranch::getBranchId).collect(toList());
                        if (!CollectionUtil.contains(branchIds, hospitalcode)) {
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，你当前无法备单该分中心：" + hospitalcodeStr);
                        }
                    }
                    log.info("分中心：hospitalcode={}, hospitalcodeStr={}", hospitalcode, hospitalcodeStr);
                    item.setHospitalcode(hospitalcode);

                }

                // 判断是职业体检还是健康体检
                if (StringUtils.isBlank(item.getExamtype())) {
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 体检类型不能为空！");
                } else {
                    // 职业体检、综合
                    Integer idExamType = ExamType.getValueByName(item.getExamtype());
                    log.info("体检类型：{}、{}", item.getExamtype(), idExamType);
                    if (ObjectUtils.isEmpty(idExamType) || (idExamType != 1 && idExamType != 2)) {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，体检类型格式不正确！");
                    }
                    item.setIdExamtype(idExamType);
                }

                // 姓名验证
                if (StringUtils.isNotBlank(item.getPatientname())) {
                    String message = checkLength(item.getPatientname(), "姓名长度不能超过25位", 25);
                    if (StringUtils.isNotBlank(message))
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + message);
                } else throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，姓名不能为空！");

                // 性别校验
                if (StringUtils.isNotBlank(item.getSexMale())) {
                    if ("男".equals(item.getSexMale()) || "女".equals(item.getSexMale())) {
                        item.setIdSex("男".equals(item.getSexMale()) ? 0 : 1);
                    } else {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 性别不合法！");
                    }
                } else
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 性别不能为空！");

                // 年龄校验
                if (Objects.nonNull(item.getAge())) {
                    // 判断年龄范围
                    if (item.getAge() < 0)
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 年龄必须在0岁以上！");
                    else if (item.getAge() > 200)
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 年龄必须在200岁以下！");
                }

                // 身份证校验
                if (StringUtils.isNotBlank(item.getIdcardno())) {
                    if ("1".equals(item.getIdcardno())){
                        //填1不校验身份证
                        item.setIdcardno(null);
                    }else{
                        // 身份证号匹配
                        if (!IdcardUtil.isValidCard(item.getIdcardno()))
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 身份证号不合法！");
                        else {
                            item.setIdcardno(item.getIdcardno().toUpperCase());
                            String card = item.getIdcardno();
                            // 如果长度是15位
                            if (card.length() == 15) {
                                card = card.substring(0, 6) + "19" + card.substring(6) + "x";
                            }
                            //生日匹配
                            item.setBirthdate(IdcardUtil.getBirthDate(card));
                            // 年龄匹配
                            item.setAge(IdcardUtil.getAgeByIdCard(card));
                            // 匹配性别
                            card = card.substring(card.length() - 2, card.length() - 1);
                            // 性别是否匹配
                            Integer sex = (Integer.valueOf(card) & 1) != 0 ? 0 : 1;
                            if (item.getIdSex() != sex) {
                                throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 身份证号与性别不匹配！");
                            }


                            //导入名单的时候或者完成登记,用areacode匹配区域
                            String areaCode = item.getIdcardno().substring(0, 2);
                            Area area = areaMapper.selectOne(new LambdaQueryWrapper<Area>().eq(Area::getAreaCode, areaCode));
                            if (Objects.nonNull(area)) {
                                item.setIdResarea(area.getId());
                                item.setResarea(area.getResarea());
                            }
                        }
                    }
                } else throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，身份证号不能为空！");

                // 婚姻校验
                if (StringUtils.isNotBlank(item.getMarriage())) {
                    Integer marriageType = MarriageType.getValueByName(item.getMarriage());
                    if (Objects.isNull(marriageType))
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 婚姻不存在！");
                    item.setIdMarriage(marriageType);
                }
                // 部门校验
                if (StringUtils.isNotBlank(item.getOrgDepart())) {
                    String message = checkLength(item.getOrgDepart(), "部门长度不能超过30位", 30);
                    if (StringUtils.isNotBlank(message))
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + message);
                }

                // 联系电话校验
                if (StringUtils.isNotBlank(item.getPhone())) {
                    if ("1".equals(item.getPhone())){
                        //填1不校验手机号
                        item.setPhone(null);
                    }else{
                        if (item.getPhone().length() > 13) {
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 联系电话不合法，不能超过13位！");
                        } else if (item.getPhone().indexOf("0") != 0 && item.getPhone().length() != 11) {
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 联系电话格式错误！");
                        } else {
//                        if (phones.contains(item.getPhone())) {
//                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 联系电话与模板中其他人员的电话重复！");
//                        }
//
//                        if (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
//                                .eq(Peispatient::getNumorgresv, order.getDdh()).eq(Peispatient::getPhone, item.getPhone())) > 0) {
//                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 具有相同联系电话的人员已存在！");
//                        }
                            phones.add(item.getPhone());
                        }
                    }
                } else throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，联系电话不能为空！");

                // 备注
                if (StringUtils.isNotBlank(item.getNote())) {
                    if (item.getNote().length() > 500)
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 备注过长，不能超过500字！");
                }

                // 统收限额
                if (Objects.nonNull(item.getTsLimit())) {
                    if (!StringUtil.isNum(item.getTsLimit())) {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 统收限额必须是数字！");
                    } else {
                        Double tsLimit = item.getTsLimit();
                        if (tsLimit < 0 || tsLimit > 999999.99) {
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 统收限额数值超出范围0~999999.99！");
                        }
                    }
                } else {
                    //throw new Exception("名单导入失败： 第"+ (i+1) +"行，" + item.getPatientname() + " 统收限额不能为空！");
                }

                // 文化程度校验
                if (StringUtils.isNotBlank(item.getCulturalStr())) {
                    Integer cultural = CulturalLevel.getValueByName(item.getCulturalStr());
                    if (Objects.isNull(cultural)) {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 文化程度不存在！");
                    }
                    item.setCultural(cultural);
                }
                // TODO: ??? 导入模板,没有原始工种
                //原始工种
//                if (StringUtils.isNotBlank(item.getOriginalTrade())) {
//                    String message = checkLength(item.getOriginalTrade(), "原始工种长度不能超过50位", 50);
//                    if (StringUtils.isNotBlank(message))
//                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + message);
//                } else throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 原始工种不能为空！");

                // 规范工种校验
                if (StringUtils.isNotBlank(item.getTrades())) {
                    List<BaseWorktype> bwts = baseWorktypeService.list(new LambdaQueryWrapper<BaseWorktype>()
                            .eq(BaseWorktype::getTypeName, item.getTrades()).eq(BaseWorktype::getIsDelete, 0));
                    if (CollectionUtil.isEmpty(bwts)) {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，规范工种不存在，请从工种工作表中选择");
                    }
                    item.setWorktypeId(bwts.get(0).getId());
                } else
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 规范工种不能为空！");

                // 组类校验
                if (Objects.nonNull(item.getGrouptype())) {
                    String message = checkLength(item.getGrouptype(), "组类长度不能超过3位", 3);
                    if (StringUtils.isNotBlank(message))
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + message);
                }

                // 接触（拟接触）危害因素校验
                if (StringUtils.isNotBlank(item.getJhysStr())) {
                    String[] harmStrs = item.getJhysStr().split("、");
                    String newHarm = "";
                    String s = "";
                    for (int g = 0; g < harmStrs.length; g++) {
                        String harmStr = s + harmStrs[g];
                        //老套餐可能含有lv1的接害因素,也可能有已经删除的因素
                        Harm harm = harmMapper.selectOne(new LambdaQueryWrapper<Harm>()
                                .eq(Harm::getHarmName, harmStr).eq(Harm::getIsDelete, 0));
                        if (Objects.nonNull(harm)) {
                            newHarm += harm.getId() + ",";
                            s = "";
                        } else {
                            if (g == harmStrs.length - 1) {
                                throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " " + harmStr + " 危害因素不存在！");
                            }
                            s = harmStr + "、";
                        }
                    }
                    item.setJhys(newHarm.substring(0, newHarm.length() - 1));
                } else
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 接触（拟接触）危害因素不能为空！");

                Date date = new Date();
                int totalAge = 0;
                // 参加工作时间
                if (Objects.nonNull(item.getWorkDate())) {
                    totalAge = getMonthSpace(date, item.getWorkDate());
                }

                int harmAge = 0;
                // 从事该岗位工种时间
                if (Objects.nonNull(item.getHarmDate())) {
                    harmAge = getMonthSpace(date, item.getHarmDate());
                }


                // 体检类别校验
                if (StringUtils.isNotBlank(item.getMedicaltypeStr())) {

                    Integer medicaltype = MedicalType.getValueByName(item.getMedicaltypeStr());

                    if (Objects.isNull(medicaltype))
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 体检类别不存在！");
                    item.setMedicaltype(medicaltype);
                } else
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 体检类别不能为空！");


                // 总工龄校验
                if (Objects.nonNull(item.getZgln()) && Objects.nonNull(item.getZgly())) {
                    // 年转换为月份与月相加
                    int months = 12 * item.getZgln() + item.getZgly();
                    //岗中、离岗体检，总工龄不能小于0
                    if (item.getMedicaltype() == 1 || item.getMedicaltype() == 2 || item.getMedicaltype() == 3) {
                        if (months <= 0) {
                            throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 岗中、离岗体检，总工龄不能小于等于0！");
                        }
                    }
                    // 判断参加工作时间与总工龄是否相符
                    if (totalAge != 0 && totalAge != months) {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 参加工作时间与总工龄不匹配！");
                    }
                    item.setZgl(months);
                    item.setWorkDate(getDateForMonth(months));
                } else
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 总工龄不能为空！");

                // 接害工龄校验
                if (Objects.nonNull(item.getJhgln())) {
                    // 年转换为月份与月相加
                    int months = 12 * item.getJhgln() + item.getJhgly();
                    ;
                    // 判断从事时间与接害工龄是否相符
                    if (harmAge != 0 && harmAge != months) {
                        throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 从事该工种时间与接害工龄不匹配！");
                    }
                    item.setJhgl(months);
                    item.setHarmDate(getDateForMonth(months));
                } else
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 接害工龄不能为空！");
                // 总工龄、接害工龄验证
                if (item.getZgl() < item.getJhgl()) {
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 总工龄少于接害工龄！");
                }
                //大于100年给提示
                if (item.getZgl() > 12 * 100) {
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 总工龄不能大于100年！");
                }
                if (item.getJhgly() > 12 * 100) {
                    throw new ServiceException("名单导入失败： 第" + (i + 2) + "行，" + item.getPatientname() + " 接害工龄不能大于100年！");
                }


                // 模板数据匹配
                msg += bindInfo(param.getId(), groupList, item, importData, item.getIdExamtype(), compare);
                i++;
            }
        }

        // 没有导入任何人员名单
        if (importData.size() == 0) {
            throw new ServiceException("@导入失败：" + msg);
        }
        // 有未分组的人员信息返回页面
        if (StringUtils.isNotBlank(msg)) {
            return R.fail("@有以下人员信息没有找到对应的分组：" + msg);
        } else {
            //执行保存操作，线程池设置了一个核心线程和最大线程都为1的线程池，空闲时间为600秒。如果在600秒内没有任务提交，线程池会自动释放线程资源。当有新任务提交时，线程池会重新启动线程来处理任务。
            ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 600, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
            // 提交任务到线程池
            String username = SecurityUtils.getUsername();
            executor.submit(() -> {
                // 执行任务逻辑
                doSavePatientData(importData, username);
            });

//            executorService.submit(() -> {
//                doSavePatientData(importData);
//            });

        }


        return R.ok("success");
    }

    /**
     * 执行保存操作
     *
     * @param importData
     * @param username
     */
    @Transactional(rollbackFor = Exception.class)
    public void doSavePatientData(List<Peispatient> importData, String username) {
        log.warn("2开始执行写入名单信息：{}", importData);
        //保存用户和分中心
//        savePeiAndFzx(importData);
        //保存体检者
        peispatientService.saveBatch(importData);
        savePeispatientfeeitem(importData, username);
        //log.warn("52开始执行写入名单信息：结束");
    }


    /**
     * 保存体检者和分中心
     *
     * @param importData
     */
    private void savePeiAndFzx(List<Peispatient> importData) {
        List<PeispatientAndFzx> peispatientAndFzxList = new ArrayList<>();
        log.warn("3开始执行写入名单信息：{}", importData);
        for (Peispatient peispatient : importData) {
            if (ObjectUtils.isEmpty(peispatient.getId())) {
                continue;
            }
            peispatientAndFzxService.remove(new LambdaQueryWrapper<PeispatientAndFzx>().eq(PeispatientAndFzx::getPatientId, peispatient.getId()));
            //有分中心建一条,分中心为空的话，就用备单的分中心去新建
            if (com.center.medical.common.utils.StringUtils.isNotEmpty(peispatient.getHospitalcode())) {
                Long count = peispatientAndFzxService.count(new LambdaQueryWrapper<PeispatientAndFzx>()
                        .eq(PeispatientAndFzx::getPatientId, peispatient.getId())
                        .eq(PeispatientAndFzx::getFzxId, peispatient.getHospitalcode())
                );
                if (count == 0) {
                    PeispatientAndFzx peispatientAndFzx = new PeispatientAndFzx(peispatient.getHospitalcode(), peispatient.getId(), 0);
                    peispatientAndFzxList.add(peispatientAndFzx);
                }
            } else {
                List<String> fzxList = createorderService.getFzxList(peispatient.getNumorgresv());
                if (CollectionUtil.isNotEmpty(fzxList)) {
                    for (String fzx : fzxList) {
                        Long count = peispatientAndFzxService.count(new LambdaQueryWrapper<PeispatientAndFzx>()
                                .eq(PeispatientAndFzx::getPatientId, peispatient.getId())
                                .eq(PeispatientAndFzx::getFzxId, fzx)
                        );
                        if (count == 0) {
                            PeispatientAndFzx peispatientAndFzx = new PeispatientAndFzx(fzx, peispatient.getId(), 0);
                            peispatientAndFzxList.add(peispatientAndFzx);
                        }
                    }
                }
            }
        }
        //批量更新
        if (CollectionUtil.isNotEmpty(peispatientAndFzxList)) {
            peispatientAndFzxService.saveOrUpdateBatch(peispatientAndFzxList);
        }
        log.warn("31开始执行写入名单信息：{}", importData);

    }

    /**
     * 保存收费项目
     *
     * @param importData
     */
    private void savePeispatientfeeitem(List<Peispatient> importData, String userName) {
//        log.info(userName + "4导入名单保存收费项目等数据:{}" + importData);
        //收费主表的备注
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(new Date());
        String onlne = ZhongkangConfig.isOnline() ? "线上" : "线下";
        String note = userName + onlne + "备单于" + dateString + ";";

        for (Peispatient peispatient : importData) {
            //休眠5毫秒
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //查询项目信息
            List<HashMap<String, String>> items = getExamfeeitem(peispatient.getIdTjtc());
            //查询分组的支付方式
            Peisorgreservationgroup peisorgreservationgroup = peisorgreservationgroupMapper.getInfoById(peispatient.getIdOrgreservationgroup());
            //插入体检者分中心表
            List<GroupAndFzx> groups = groupAndFzxMapper.selectList(new LambdaQueryWrapper<GroupAndFzx>()
                    .eq(GroupAndFzx::getGroupId, peispatient.getIdOrgreservationgroup()));
            for (GroupAndFzx group : groups) {
                peispatientAndFzxMapper.insert(new PeispatientAndFzx(group.getFzxId(), peispatient.getId(), 0));
            }

            // 插入体检者费用主表
            peispatientChargeMainMapper.insert(new PeispatientChargeMain(
                    note
                    , peispatient.getMoneyamount()
                    , peispatient.getMoneyamountpaid()
                    , peispatient.getPatientcode()));

            // 插入体检者收费项目
            List<Peispatientfeeitem> peispatientfeeitems = new ArrayList<Peispatientfeeitem>();
            for (int j = 0; j < items.size(); j++) {
                Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
                peispatientfeeitem.setIdPatient(peispatient.getPatientcode());
                peispatientfeeitem.setIdExamfeeitem(items.get(j).get("idExamfeeitem"));
                peispatientfeeitem.setExamfeeitemName(items.get(j).get("examfeeitemName"));
                peispatientfeeitem.setPrice(Double.valueOf(items.get(j).get("price")));
                peispatientfeeitem.setIdKs(items.get(j).get("idKs"));
                peispatientfeeitem.setFactprice(Double.parseDouble(String.valueOf(items.get(j).get("factprice"))));
                peispatientfeeitem.setCount(1);
                peispatientfeeitem.setIdPayway(peisorgreservationgroup.getIdPayway());
                peispatientfeeitem.setFRegistered(0);
                peispatientfeeitem.setChangeItem(0);
                peispatientfeeitem.setFMarkFeereturn(0);
                peispatientfeeitem.setFFeecharged(0);
                peispatientfeeitem.setFLabsendtolis(0);
                peispatientfeeitem.setFExaminated(0);
                peispatientfeeitem.setFGiveup(0);
                peispatientfeeitem.setIsbx(Integer.parseInt(String.valueOf(items.get(j).get("isbx"))));
                peispatientfeeitem.setBxcount(items.get(j).get("bxcount") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("bxcount"))));
                peispatientfeeitem.setFDelayed(0);
                peispatientfeeitem.setIsMintc(1);
                peispatientfeeitem.setQty(items.get(j).get("qty") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("qty"))));
                peispatientfeeitem.setItemGroup(items.get(j).get("group") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("group"))));
                peispatientfeeitems.add(peispatientfeeitem);
            }
            peispatientfeeitemService.saveBatch(peispatientfeeitems);
            //log.info(peispatient.getPatientcode() + "体检者数据保存成功");
        }
        log.info("导入名单保存收费项目等数据成功！");
    }


    /**
     * @param idVation   团体任务ID
     * @param groupList  分组数据
     * @param map        模板列数据
     * @param importData 存储体检者容器
     * @param type       类型 0：职业 1：健康 2：综合
     * @throws Exception void
     * @Title: 模板数据匹配
     * @author zhanghj
     * @since 2016-10-19 V 1.0
     */
    private String bindInfo(String idVation, List<Peisorgreservationgroup> groupList, PeispatientImportDto map,
                            List<Peispatient> importData, Integer type, Set<String> compare) {
        // 匹配消息
        String msg = StringUtils.EMPTY;

        // 判断人员信息是否重复添加
        LambdaQueryWrapper<Peispatient> and = new LambdaQueryWrapper<>();
        String bind = "";
        // 身份证号
        if (StringUtils.isNotBlank(map.getIdcardno())) {
            and.eq(Peispatient::getIdcardno, map.getIdcardno());
            bind += map.getIdcardno();
        } else {
            // 性别
            if (Objects.nonNull(map.getIdSex())) {
                and.eq(Peispatient::getIdSex, map.getIdSex());
            } else {
                and.isNull(Peispatient::getIdSex);
            }
            bind += map.getIdSex() + "&";
            // 年龄
            if (Objects.nonNull(map.getAge())) {
                and.eq(Peispatient::getAge, map.getAge());
            } else {
                and.isNull(Peispatient::getAge);
            }
            bind += map.getAge() + "&";
            // 电话
            if (StringUtils.isNotBlank(map.getPhone())) {
                and.eq(Peispatient::getPhone, map.getPhone());
            } else {
                and.isNull(Peispatient::getPhone);
            }
            bind += map.getPhone() + "&";
            // 部门
            if (StringUtils.isNotBlank(map.getOrgDepart())) {
                and.eq(Peispatient::getOrgDepart, map.getOrgDepart());
            } else {
                and.isNull(Peispatient::getOrgDepart);
            }
            bind += map.getOrgDepart() + "&";
            // 工种
            if (StringUtils.isNotBlank(map.getTrades())) {
                and.eq(Peispatient::getTrades, map.getTrades());
            } else {
                and.isNull(Peispatient::getTrades);
            }
            bind += map.getTrades() + "&";
            // 姓名
            and.eq(Peispatient::getPatientname, map.getPatientname());
            bind += map.getPatientname();
        }
        if (compare.add(bind)) {
            //2025.8.05东区销售要求可以重复导入名单
//            and.eq(Peispatient::getNumorgresv, map.getOrderNum()).in(Peispatient::getIdExamtype, Arrays.asList(0, 1, 2));
//            List<Peispatient> peispatientNewlist = peispatientMapper.selectList(and);
//            // 判断是否存在已经导入
//            if (CollectionUtil.isNotEmpty(peispatientNewlist)) {
//                msg = "人员: 【" + map.getPatientname() + "】 已经导入过";
//                return msg;
//            }
//            compare.add(bind);
        } else {
            msg = "人员: 【" + map.getPatientname() + "】的模板中其他人员存在相同的信息";
            return msg;
        }
        // 根据分组信息进行匹配操作
        // 不存在体检类型
        if (CollectionUtil.isNotEmpty(groupList)) {
            Boolean noType = false;
            for (int j = 0; j < groupList.size(); j++) {
                // 判断健康、职业、综合
                //log.warn("体检类型：{}、{}、{}", Integer.parseInt(groupList.get(j).getIdExamtype()),type,type != Integer.parseInt(groupList.get(j).getIdExamtype()));
                if (ObjectUtils.isEmpty(type) || type != Integer.parseInt(groupList.get(j).getIdExamtype())) {
                    noType = true;
                    continue;
                } else noType = false;

                // 分组条件男
                int male = groupList.get(j).getFMale();
                // 分组条件女
                int female = groupList.get(j).getFFemale();
                // 已婚
                int forHasmarried = groupList.get(j).getFHasmarried();
                // 未婚
                int forNevermarried = groupList.get(j).getFNevermarried();
                // 年龄范围
                float minAge = groupList.get(j).getAgemin();
                float maxAge = groupList.get(j).getAgemax();
                Boolean isGouptype = StringUtils.isBlank(groupList.get(j).getGrouptype());
                // 存在组类
                if (!isGouptype) {
                    // 判断条件
                    if (Objects.nonNull(map.getGrouptype())) {
                        String grouptype = groupList.get(j).getGrouptype();
                        // 按照组类分组
                        if (grouptype.equals(map.getGrouptype().toString())) {
                            // 其他条件匹配
                            msg = match(minAge, maxAge, male, female, forHasmarried, forNevermarried, map, importData, idVation, groupList, j);
                        }
                    }
                } else {
                    // 判断条件
                    if (Objects.isNull(map.getGrouptype())) {
                        // 其他条件匹配
                        msg = match(minAge, maxAge, male, female, forHasmarried, forNevermarried, map, importData, idVation, groupList, j);
                    }
                }

                // 已经匹配到
                if ("1".equals(msg)) {
                    break;
                }
            }
            if (StringUtils.isBlank(msg)) {
                if (noType) {
                    return "<br/>人员: 【" + map.getPatientname() + "】 没有匹配到相对应的体检类型";
                }
                msg = "<br/>人员: 【" + map.getPatientname() + "】 没有匹配到";
            }
            if ("1".equals(msg)) {
                msg = "";
            }
        } else {
            addPatient(map, importData, idVation, null);
            msg = "";
        }
        return msg;
    }


    /**
     * @param minAge          最小年龄
     * @param maxAge          最大年龄
     * @param male            男
     * @param female          女
     * @param forHasmarried   已婚
     * @param forNevermarried 未婚
     * @param map             名单信息
     * @param importData      批量人员容器
     * @param idVation        团体任务ID
     * @param groupList       分组表信息
     * @param j               当前匹配行
     * @throws Exception void
     * @Title: 人员匹配
     * @author zhanghj
     * @since 2016-11-1 V 1.0
     */
    private String match(float minAge, float maxAge, int male, int female, int forHasmarried, int forNevermarried, PeispatientImportDto map, List<Peispatient> importData,
                         String idVation, List<Peisorgreservationgroup> groupList, int j) {
        Boolean agePartern = false;
        // 年龄不存在
        if (Objects.isNull(map.getAge())) {
            agePartern = true;
        }
        String msg = StringUtils.EMPTY;
        if (agePartern || (minAge <= map.getAge()) && maxAge >= map.getAge()) {
            // 性别不存在
            if (StringUtils.isNotBlank(map.getSexMale())) {
                // 男
                if ("男".equals(map.getSexMale()) && 1 == male) {
                    // 已婚
                    if (StringUtils.isNotBlank(map.getMarriage()) && "已婚".equals(map.getMarriage()) && 1 == forHasmarried) {
                        // 匹配危害因素
                        msg = compareHarm(map, groupList.get(j));
                        if (StringUtils.isNotBlank(msg)) {
                            return msg;
                        }
                        addPatient(map, importData, idVation, groupList.get(j));
                        msg = "1";
                    }
                    // 未婚
                    else if (StringUtils.isNotBlank(map.getMarriage()) && "未婚".equals(map.getMarriage()) && 1 == forNevermarried) {
                        // 匹配危害因素
                        msg = compareHarm(map, groupList.get(j));
                        if (StringUtils.isNotBlank(msg)) {
                            return msg;
                        }
                        addPatient(map, importData, idVation, groupList.get(j));
                        msg = "1";
                    } else {
                        // 匹配危害因素
                        msg = compareHarm(map, groupList.get(j));
                        if (StringUtils.isNotBlank(msg)) {
                            return msg;
                        }
                        addPatient(map, importData, idVation, groupList.get(j));
                        msg = "1";
                    }
                }
                // 女
                else if ("女".equals(map.getSexMale()) && 1 == female) {
                    // 已婚
                    if (StringUtils.isNotBlank(map.getMarriage()) && "已婚".equals(map.getMarriage()) && 1 == forHasmarried) {
                        // 匹配危害因素
                        msg = compareHarm(map, groupList.get(j));
                        if (StringUtils.isNotBlank(msg)) {
                            return msg;
                        }
                        addPatient(map, importData, idVation, groupList.get(j));
                        msg = "1";
                    }
                    // 未婚
                    else if (StringUtils.isNotBlank(map.getMarriage()) && "未婚".equals(map.getMarriage()) && 1 == forNevermarried) {
                        // 匹配危害因素
                        msg = compareHarm(map, groupList.get(j));
                        if (StringUtils.isNotBlank(msg)) {
                            return msg;
                        }
                        addPatient(map, importData, idVation, groupList.get(j));
                        msg = "1";
                    } else {
                        // 匹配危害因素
                        msg = compareHarm(map, groupList.get(j));
                        if (StringUtils.isNotBlank(msg)) {
                            return msg;
                        }
                        addPatient(map, importData, idVation, groupList.get(j));
                        msg = "1";
                    }
                }
            }
            // 通用
            else {
                // 已婚
                if (StringUtils.isNotBlank(map.getMarriage()) && "已婚".equals(map.getMarriage()) && 1 == forHasmarried) {
                    // 匹配危害因素
                    msg = compareHarm(map, groupList.get(j));
                    if (StringUtils.isNotBlank(msg)) {
                        return msg;
                    }
                    addPatient(map, importData, idVation, groupList.get(j));
                    msg = "1";
                }
                // 未婚
                else if (StringUtils.isNotBlank(map.getMarriage()) && "未婚".equals(map.getMarriage()) && 1 == forNevermarried) {
                    // 匹配危害因素
                    msg = compareHarm(map, groupList.get(j));
                    if (StringUtils.isNotBlank(msg)) {
                        return msg;
                    }
                    addPatient(map, importData, idVation, groupList.get(j));
                    msg = "1";
                } else {
                    // 匹配危害因素
                    msg = compareHarm(map, groupList.get(j));
                    if (StringUtils.isNotBlank(msg)) {
                        return msg;
                    }
                    addPatient(map, importData, idVation, groupList.get(j));
                    msg = "1";
                }
            }
        } else {
            msg = "<br/>人员: 【" + map.getPatientname() + "】 年龄不在所有分组的年龄段区间";
        }

        return msg;
    }


    /**
     * 接害因素匹配
     *
     * @param map   体检者信息
     * @param group 分组
     * @return
     */
    private String compareHarm(PeispatientImportDto map, Peisorgreservationgroup group) {
        // 接害因素存在
        if (StringUtils.isNotBlank(map.getJhys())) {
            String[] jhys = map.getJhys().split(",");
            Set<String> set = new HashSet<String>();
            Collections.addAll(set, jhys);
            if (set.size() != jhys.length) {
                return "<br/>人员: 【" + map.getJhys() + "】存在重复的接害因素";
            }
            String[] cJ = null;
            // 匹配套餐
            Createmeal createMeal = createmealMapper.selectById(group.getIdExamsuite());
            if (Objects.isNull(createMeal)) {
                Createcombo createCombo = createcomboMapper.selectById(group.getIdExamsuite());
                if (Objects.isNull(createCombo)) {
                    cJ = createCombo.getJhys().split(",");
                }
            } else {
                cJ = createMeal.getJhys().split(",");
            }
            if (null != cJ) {
                List<String> list = new ArrayList<String>(Arrays.asList(cJ));
//                list.retainAll(Arrays.asList(jhys));
                //可能id不同名字相同，因为有些没有自己职业数据的危害因素被批量删除并生成新的危害因素，老套餐中还使用着老的危害因素
                List<Harm> harmList = harmMapper.selectList(new LambdaQueryWrapper<Harm>().in(Harm::getId, list));
                List<String> nameList = new ArrayList<>();
                for (Harm harm : harmList) {
                    nameList.add(harm.getHarmName());
                }
                List<Harm> jhysList = harmMapper.selectList(new LambdaQueryWrapper<Harm>().in(Harm::getId, jhys));
                List<String> namejhysList = new ArrayList<>();
                for (Harm harm : jhysList) {
                    namejhysList.add(harm.getHarmName());
                }
                // 判断两个集合是否包含完全相同的元素
                boolean isEqual = CollectionUtils.isEqualCollection(nameList, namejhysList);
                if (!isEqual) {
                    return "<br/>人员: 【" + map.getPatientname() + "】 模板中的接害因素与分组【" + group.getOrgreservationgroupname() + "】 中的危害分组不匹配";
                } else {
                    return StringUtils.EMPTY;
                }
            } else {
                return StringUtils.EMPTY;
            }
        } else {
            return StringUtils.EMPTY;
        }
    }


    /**
     * 添加体检者数据
     *
     * @param map
     * @param importData
     * @param idVation
     * @param group
     */
    @Transactional(rollbackFor = Exception.class)
    public void addPatient(PeispatientImportDto map, List<Peispatient> importData, String idVation, Peisorgreservationgroup group) {
        Peispatient peispatient = mapperFacade.map(map, Peispatient.class);
        //log.info("备单导入名单构建的体检者信息1：{}、{}", JSONUtil.toJsonStr(peispatient), JSONUtil.toJsonStr(group));
        // 任务ID
        peispatient.setIdOrgreservation(idVation);
        Peisorgreservation vation = map.getVation();
        if (vation == null && StrUtil.isNotEmpty(idVation)) {
            vation = peisorgreservationMapper.selectById(idVation);
        }
//        if (null == vation) {
//            throw new Exception("名单导入失败： 备单团体不存在，已经被删除！");
//        }

        //生成体检号
        String patientCode = "";
        do {
            if (ZhongkangConfig.isOnline()) {
                patientCode = CodeUtil.getPatientCode(Constants.ONLINE_PREFIX, iSysConfigService.selectConfigByKey(Constants.VERSION_NO));
            } else {
                patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(null), "");
            }
            //判断体检号是否存在
        } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getPatientcode, patientCode)) > 0);

        peispatient.setPatientcode(patientCode);
        peispatient.setShortCode(CodeUtil.getShortCodeByLong(patientCode));
        if (Objects.nonNull(vation)) {
            peispatient.setQtxz(vation.getQtxz());
            // 团体ID
            peispatient.setIdOrg(vation.getIdOrg());
            // 开单医师ID
            peispatient.setIdOpendoctor(vation.getIdSalesperson());
        }
        if (Objects.nonNull(group)) {
            // 分组ID
            peispatient.setIdOrgreservationgroup(group.getId());
            // 体检日期
            peispatient.setMedicaldate(group.getDateexamplanned());
            // 体检类型
            peispatient.setIdExamtype(group.getIdExamtype());
            // 支付方式
            peispatient.setIdPayway(group.getIdPayway());
            // 体检套餐
            peispatient.setIdTjtc(group.getIdExamsuite());
            String tcName = "";
            Createcombo createCombo = createcomboMapper.selectById(group.getIdExamsuite());
            if (Objects.nonNull(createCombo)) {
                tcName = createCombo.getTjtcmc();
            } else {
                Createmeal createMeal = createmealMapper.selectById(group.getIdExamsuite());
                if (Objects.nonNull(createMeal)) {
                    tcName = createMeal.getTjtcmc();
                }
            }
            peispatient.setExamsuiteName(tcName);

            //备单分类 新系统导入名单后就有体检号，不需要点保存，点保存时不保存未修改的数据。不能在保存时关联备单分类，导入时就要关联备单分类。
            Orderandcombo orderAndCombo = orderandcomboService.getOne(
                    new LambdaQueryWrapper<Orderandcombo>()
                            .eq(Orderandcombo::getDdid, vation.getDdh())
                            .eq(Orderandcombo::getTcid, group.getIdExamsuite())
            );
            if (orderAndCombo != null) {
                Integer idExamclass = orderAndCombo.getIdExamclass();
                peispatient.setIdExamclass(idExamclass);
            }

        }
        // 拼音码
        peispatient.setInputCode(StringUtils.isNotEmpty(peispatient.getPatientname()) ? ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()) : null);

        // 订单号
        peispatient.setNumorgresv(map.getOrderNum());
        peispatient.setFRegistered(0);
        // 已备单
        peispatient.setFIsforprepare(1);
        // 体检者类型
        peispatient.setIdPatientclass("1");
        // 根据身份证判断籍贯
        Area area = null;
        if (StringUtils.isNotBlank(peispatient.getIdcardno())) {
            area = areaMapper.selectOne(new LambdaQueryWrapper<Area>().eq(Area::getAreaCode, peispatient.getIdcardno().substring(0, 3)));
            if (Objects.isNull(area)) {
                area = areaMapper.selectOne(new LambdaQueryWrapper<Area>().eq(Area::getIsDefault, 1));
            }
        } else {
            area = areaMapper.selectOne(new LambdaQueryWrapper<Area>().eq(Area::getIsDefault, 1));
        }
        if (Objects.nonNull(area)) {
            peispatient.setIdResarea(area.getId());
            peispatient.setResarea(area.getResarea());
        }
        // 体检类型 团检
        peispatient.setFUsecodehiden(1);
        // 备单体检号生成人
        peispatient.setGuidancenote2(SecurityUtils.getUserNo());
        //log.info("备单导入名单构建的体检者信息：{}", JSONUtil.toJsonStr(peispatient));
//        peispatientService.save(peispatient);

        importData.add(peispatient);
    }


    /**
     * @param column
     * @param msg
     * @param length
     * @return String
     * @Title: 检测列的长度
     * @author zhanghj
     * @since 2016年12月6日 V 1.0
     */
    private String checkLength(Object column, String msg, int length) {
        if (null != column) {
            if (column.toString().length() > length) return msg;
            else return "";
        } else return "";
    }

    /**
     * @param months
     * @return Date
     * @Title: 根据月份差值获取日期
     * @author zhanghj
     * @since 2016-10-27 V 1.0
     */
    private Date getDateForMonth(int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -months);

        return calendar.getTime();
    }


    /**
     * @param indate
     * @return Date
     * @Title: 日期-8小时
     * @author zhanghj
     * @since 2016-11-10 V 1.0
     */
    private Date subTime(Date indate) {
        Date dat = null;
        if (indate != null) {
            Long time = indate.getTime() - 28800000;
            dat = new Date(time);
        }

        return dat;
    }


    /**
     * @param date1
     * @param date2
     * @return
     * @throws ParseException int
     * @Title: 获取两个日期之间月份的差值
     * @author zhanghj
     * @since 2016-10-19 V 1.0
     */
    private int getMonthSpace(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        if (c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) yearInterval--;
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) monthInterval--;
        monthInterval %= 12;

        return yearInterval * 12 + monthInterval;
    }


    /**
     * 判断是否存在重复的名字
     *
     * @param peispatient
     * @param ddh
     * @return
     */
    private String isExitsName(Peispatient peispatient, String ddh) {
        if (peispatient.getIdOrgreservationgroup() != null) {
            Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(peispatient.getIdOrgreservationgroup());
            if (ObjectUtils.isNotEmpty(group) && ObjectUtils.isNotEmpty(group.getIdPatientclass2()) && group.getIdPatientclass2() == 1) {
                return "success";
            }
        }
        // 判断人员信息是否重复添加
        QueryWrapper<Peispatient> and = new QueryWrapper<>();

        // 身份证号
        if (!com.center.medical.common.utils.StringUtils.isBlank(peispatient.getIdcardno())) {
            and.eq("idcardno", peispatient.getIdcardno());
        } else {
            // 性别
            if (!com.center.medical.common.utils.StringUtils.isBlank(peispatient.getIdSex().toString())) {
                and.eq("id_sex", peispatient.getIdSex());
            } else {
                and.isNull("id_sex");
            }
            // 年龄
            if (null != peispatient.getAge()) {
                and.eq("age", peispatient.getAge());
            } else {
                and.isNull("age");
            }
            // 电话
            if (!com.center.medical.common.utils.StringUtils.isBlank(peispatient.getPhone())) {
                and.eq("phone", peispatient.getPhone());
            } else {
                and.isNull("phone");
            }
            // 部门
            if (!com.center.medical.common.utils.StringUtils.isBlank(peispatient.getOrgDepart())) {
                and.eq("org_depart", peispatient.getOrgDepart());
            } else {
                and.isNull("org_depart");
            }
            // 工种
            if (!com.center.medical.common.utils.StringUtils.isBlank(peispatient.getTrades())) {
                and.eq("trades", peispatient.getTrades());
            } else {
                and.isNull("trades");
            }
            // 姓名
            and.eq("patientname", peispatient.getPatientname());
        }
        if (ObjectUtils.isNotEmpty(peispatient.getId())) {
            //id
            and.ne("id", peispatient.getId());
        }
        Long i = peispatientMapper.selectCount(and.eq("numorgresv", ddh)
                .ne("id_examtype", "3")
                .eq("id_orgreservationgroup", peispatient.getIdOrgreservationgroup()));//校验相同分组下的
        // 判断是否存在已经导入
        if (i > 0) {
            return "人员: <font color='red'>" + peispatient.getPatientname() + "</font> 已经存在";
        }
        return "success";
    }


    /**
     * 获取登记页面最小套餐收费项目明细
     *
     * @param tcid
     * @return
     */
    public List<HashMap<String, String>> getExamfeeitem(String tcid) {
        List<HashMap<String, String>> list = new ArrayList();
        // 判断是否为空
        if (com.center.medical.common.utils.StringUtils.isBlank(tcid)) {
            return list;
        }
        double factPrices = 0.00;//算出来的总价
        double remainder = 0.00;//余数
        // 根据套餐id获取最小套餐下的收费项目
        List<Comboanditem> minPackagesList = comboanditemService.list(new QueryWrapper<Comboanditem>()
                .orderByAsc("createdate").eq("tcid", tcid).eq("is_delete", 0));
        // 判断是否存在
        if (CollectionUtils.isNotEmpty(minPackagesList) && minPackagesList.size() > 0) {
            // 最小套餐
            Createcombo createCombo = createcomboMapper.getInfoById(tcid);
            //算出折扣率
            double zkl = Arith.div(createCombo.getZhjg(), createCombo.getTcysjg());
            int size = minPackagesList.size();
            for (int i = 0; i < size; i++) {
                Comboanditem cai = minPackagesList.get(i);
                Items items = itemsMapper.getInfoById(cai.getSfxmid());
                if (ObjectUtils.isNotEmpty(items)) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    // 收费项目id
                    map.put("idExamfeeitem", items.getId());
                    // 收费项目名称
                    map.put("examfeeitemName", items.getExamfeeitemName());
                    // 原始价格
                    map.put("price", String.valueOf(items.getUnitprice()));
                    //套餐价格 = 原价 * 折扣率
//                    map.put("factprice", (i + 1) == size ? (null == createCombo.getZhjg() ? 0 : createCombo.getZhjg()) : 0);
                    double factPrice = Arith.round(Arith.mul(items.getUnitprice(), zkl), 1);
//                    map.put("factprice", (i + 1) == size ? Arith.sub(createCombo.getZhjg(), factPrices) : factPrice);
                    map.put("factprice", String.valueOf(factPrice));
                    factPrices = Arith.add(factPrices, factPrice);
                    // 科室
                    map.put("idKs", items.getIdDepart());
                    // 是否备选
                    map.put("isbx", StringUtils.isBlank(minPackagesList.get(i).getSfbx()) ? "0" : minPackagesList.get(i).getSfbx());
                    // 备选数量
                    map.put("bxcount", Objects.isNull(createCombo.getSl()) ? "0" : createCombo.getSl().toString());
                    // 折后价格
                    map.put("zhjg", Objects.isNull(createCombo.getZhjg()) ? "0" : createCombo.getZhjg().toString());
                    map.put("idSex", Objects.isNull(items.getXb()) ? "0" : items.getXb().toString());
                    map.put("qty", i + 1 + "");
                    list.add(map);
                }
            }
            remainder = Arith.sub(createCombo.getZhjg(), factPrices);
        }

        // 普通套餐
        //按是否备选排序(是否备选不会为空)
        List<Mealanditem> mealItems = mealanditemService.list(new QueryWrapper<Mealanditem>()
                .orderByAsc("item_sort").eq("tcid", tcid));
        // 判断是否存在
        if (ObjectUtils.isNotEmpty(mealItems) && mealItems.size() > 0) {
            Createmeal createMeal = createmealMapper.getInfoById(tcid);
            int size = mealItems.size();
            // 可选数量
            Long kxsl = Long.valueOf(createMeal.getKxsl());
            // 分组金额
            Double groupPrice = createMeal.getGroupPrice();
            double bxjg = 0.0;//所有备选项目价格
            if (ObjectUtils.isNotEmpty(kxsl) && ObjectUtils.isNotEmpty(groupPrice)) {
                bxjg += kxsl * groupPrice;//所有组间选价格（更新前的老套餐没有）
            }
            Set<Integer> groups = new HashSet<Integer>();//组内选组去重
            double totalPrice = 0.00;
            for (int i = 0; i < size; i++) {
                Mealanditem mai = mealItems.get(i);
                Items items = itemsMapper.selectOne(new LambdaQueryWrapper<Items>().eq(Items::getId, mealItems.get(i).getSfxmid()));
                //老系统导过来的数据有可能是空的直接跳过
                if (ObjectUtils.isEmpty(items)) {
                    continue;
                }
                // 是否备选：0或null.否 1.是
                if (mai.getSfbx() == 1) {
                    //分组类型：0.组内选 1.组间选 2.组任选
                    Integer groupType = mai.getGroupType();
                    if (groupType == null) {//兼容老套餐
                        bxjg += mai.getPrice();
                    } else if (groupType == 0) {
                        //0组内选，或1组间选，同一组所有项目价格相同
                        Integer group = mai.getItemGroup();
                        if (groups.contains(group)) {

                        } else {
                            groups.add(group);
                            bxjg += mai.getPrice();
//                            totalPrice = Arith.add(totalPrice, mai.getPrice());
                        }
                    }
                } else {
                    //普通项目加上总价
                    Double unitprice = items.getUnitprice();
                    totalPrice = Arith.add(totalPrice, unitprice);
                }
            }
            //总价 = 普通项目价格 + 所有备选项目价格
//            double fbxjg = totalPrice - bxjg;

            //折后价格-备选价格
            double total = Arith.sub(createMeal.getZhjg(), bxjg);
            //算出折扣率 = 优惠价/原价
            double zkl = totalPrice == 0 ? 0 : Arith.div(total, totalPrice);
            for (int i = 0; i < size; i++) {
                Mealanditem mai = mealItems.get(i);
                Items items = itemsMapper.getInfoById(mealItems.get(i).getSfxmid());

                if (ObjectUtils.isNotEmpty(items)) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    // 收费项目id
                    map.put("idExamfeeitem", items.getId());
                    // 收费项目名称
                    map.put("examfeeitemName", items.getExamfeeitemName());
                    // 原始价格
                    map.put("price", String.valueOf(items.getUnitprice()));
                    //优惠价格
                    if (mai.getSfbx() == 1) {
                        //组间选之类的已经设置好优惠价了
                        map.put("factprice", mai.getPrice().toString());
                    } else {
                        //单项 = 原价*折扣 差价加在最后一项上
                        double factPrice = Arith.round(Arith.mul(items.getUnitprice(), zkl), 1);
                        map.put("factprice", String.valueOf(factPrice));
                        factPrices = Arith.add(factPrices, factPrice);
                    }
                    // 科室
                    map.put("idKs", items.getIdDepart());
                    // 是否备选
                    Integer isbx = Objects.isNull(mealItems.get(i).getSfbx()) ? 0 : mealItems.get(i).getSfbx();
                    map.put("isbx", isbx.toString());
                    // 备选数量
                    map.put("bxcount", String.valueOf(createMeal.getKxsl()));
                    // 折后价格
//                    if (isbx == 1) {//如果是必选
//                        map.put("zhjg", mealItems.get(i).getPrice());
//                    } else {
//                        map.put("zhjg", fbxjg);
//                    }
                    map.put("idSex", items.getXb().toString());
                    map.put("qty", mealItems.get(i).getItemSort() == null ? null : mealItems.get(i).getItemSort().toString());
                    map.put("group", mealItems.get(i).getItemGroup() == null ? null : mealItems.get(i).getItemGroup().toString());
                    map.put("groupType", mealItems.get(i).getGroupType() == null ? null : mealItems.get(i).getGroupType().toString());
                    list.add(map);
                }
            }
            remainder = Arith.sub(total, factPrices);
        }

        //处理余数，找个不为0的放进去
        if (Double.compare(remainder, 0.0) != 0) {
            for (Map<String, String> map : list) {
                String isbx = map.get("isbx");
                double factprice = Double.parseDouble(map.get("factprice"));
                //不是备选的,并且价格大于0
                if (!"1".equals(isbx) && factprice > 0 && Arith.add(factprice, remainder) > 0) {
                    map.put("factprice", String.valueOf(Arith.add(factprice, remainder)));
                    break;
                }
            }
        }


        return list;
    }

    /**
     * Object转map
     *
     * @param object
     * @param <T>
     * @return
     */
    private static <T> Map<String, Object> convertToMap(T object) {
        HashMap<String, Object> resultMap = new HashMap<>();

        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();

            // 设置访问权限，使得private字段也能被获取到值
            field.setAccessible(true);

            // 根据字段名称存入map中
            try {
                resultMap.put(fieldName, field.get(object));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return resultMap;
    }


    /**
     * 来检短信提醒-保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveSmsToExam(SmsToExamParam param) {
        // 当前分中心
        String curFzxId = SecurityUtils.getCId();
        // 当前登录用户
        String curUser = SecurityUtils.getUserNo();
        //订单id
        String orderId = param.getOrderId();
        Createorder order = createorderMapper.getInfoById(orderId);
        if (order == null) {
            throw new ServiceException("该订单不存在");
        }
        //存放来检短信提醒表
        List<NotifySmsExam> list = new ArrayList<NotifySmsExam>();
        List<String> idsArray = param.getIds();
        for (int i = 0, l = idsArray.size(); i < l; i++) {
            //来检短信提醒表
            NotifySmsExam exam = notifySmsExamMapper.selectOne(new QueryWrapper<NotifySmsExam>().eq("patientcode", idsArray.get(i)));
            if (null != exam) {
                exam.setIdContact(param.getIdContact());
                exam.setPhone(param.getPhone());
                //操作人
                exam.setCreateer(curUser);
                notifySmsExamMapper.updateById(exam);
                continue;
            }
            NotifySmsExam notity = new NotifySmsExam();
            notity.setIdOrder(orderId);
            notity.setDdh(order.getDdh());
            notity.setIdContact(param.getIdContact());
            notity.setPhone(param.getPhone());
            notity.setPatientcode(idsArray.get(i));
            notity.setCreateer(curUser);
            notity.setFzxid(curFzxId);
            list.add(notity);
        }
        if (list.size() > 0) {
            //批量插入
            notifySmsExamService.saveBatch(list);
        }
        return Boolean.TRUE;
    }


    /**
     * 批量设置
     *
     * @param data
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatehy(List<String> data, String id) {
        Peispatient peispatient = new Peispatient();
        peispatient.setIdPatientclass(id);
        //更新
        peispatientMapper.update(peispatient, new UpdateWrapper<Peispatient>().in("patientcode", data));
        return Boolean.TRUE;
    }

    /**
     * 获取分页统计数据
     *
     * @return
     */
    @Override
    public StatisticsVo getStatistics(DbOrderParam param) {
        return orderMapper.getStatistics(param);
    }


    /**
     * 清除
     *
     * @param id
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeAll(String id, List<String> ids) {
        SysConfig qd = sysConfigService.getConfigByKey("HIS");
        boolean isHis = qd != null && "T".equals(qd.getConfigType());
        // 选中体检者
        if (ids.size() > 0 && StringUtils.isNotBlank(ids.get(0))) {
            for (String string : ids) {
                Peispatient peispatient = peispatientMapper.getInfoById(string);
                if (peispatient.getFRegistered() == 1) {
                    throw new ServiceException("体检号:" + peispatient.getPatientcode() + "已登记,不能清除!");
                }
                if (ObjectUtils.isNotEmpty(peispatient)) {
                    Long count = peispatientAndFzxMapper.selectCount(new QueryWrapper<PeispatientAndFzx>()
                            .eq("patient_id", peispatient.getId()).eq("xzzt", 1));
                    if (count > 0) {
                        throw new ServiceException("清除失败：<font color='red'>" + peispatient.getPatientname() + "</font> 已经被下载，不可删除");
                    }
                    if (StringUtils.isNotBlank(peispatient.getPatientcode())) {
                        //删除费用主表
                        peispatientChargeMainMapper.delete(new QueryWrapper<PeispatientChargeMain>().eq("patientcode", peispatient.getPatientcode()));
                        //删除收费项目表
                        peispatientfeeitemMapper.delete(new QueryWrapper<Peispatientfeeitem>().eq("id_patient", peispatient.getPatientcode()));
                    }
                }
            }
            //删除体检者表
            peispatientMapper.delete(new QueryWrapper<Peispatient>().in("id", ids));
            // TODO: ??? 删除历史体检者 
            if (isHis) {
//                executeUpdateSql("delete from peispatient_his where id in('"+ids.replaceAll(",","','")+"') ");
            }
        } else {
            List<Peispatient> peispatients = peispatientMapper.selectList(new QueryWrapper<Peispatient>().eq("id_orgreservationgroup", id));
            for (Peispatient peispatient : peispatients) {
                if (peispatient.getFRegistered() == 1) {
                    throw new ServiceException("体检号:" + peispatient.getPatientcode() + "已登记,不能清除!");
                }
                Long count = peispatientAndFzxMapper.selectCount(new QueryWrapper<PeispatientAndFzx>()
                        .eq("patient_id", peispatient.getId()).eq("xzzt", 1));
                if (count > 0) {
                    throw new ServiceException("清除失败：<font color='red'>" + peispatient.getPatientname() + "</font> 已经被下载，不可删除");
                }
                if (StringUtils.isNotBlank(peispatient.getPatientcode())) {
                    //删除费用主表
                    peispatientChargeMainMapper.delete(new QueryWrapper<PeispatientChargeMain>().eq("patientcode", peispatient.getPatientcode()));
                    //删除收费项目表
                    peispatientfeeitemMapper.delete(new QueryWrapper<Peispatientfeeitem>().eq("id_patient", peispatient.getPatientcode()));
                }
            }
            //删除体检者表
            peispatientMapper.delete(new QueryWrapper<Peispatient>().eq("id_orgreservationgroup", id));
            // TODO: ??? 删除历史体检者
            if (isHis) {
//                executeUpdateSql("delete from peispatient_his where id in('"+ids.replaceAll(",","','")+"') ");
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 获取当前选中的已预约用户信息
     *
     * @param patientCode
     * @param type
     * @param autoFill
     * @return
     */
    @Override
    public HashMap getCustomerData(String patientCode, String type, String autoFill) {
        HashMap map = new HashMap();
        Peispatient p = getPeispatient(patientCode, autoFill);
        map.put("patientData", p);
        if (null == p) {
            return map;
        }
        map.put("jhysn", StringUtils.isNotEmpty(p.getJhys()) ? harmMapper.getHarmText(p.getJhys().split(",")) : "");// 接害因素
        // 获取右侧收费项目
        map.put("examfeeitemData", getExamfeeByPatientCode(p.getPatientcode(), type));
        // 重复
        Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(p.getIdOrgreservationgroup());
        map.put("groupName", null == group ? "" : group.getOrgreservationgroupname());
        // 查找收费信息退费金额
        List<Peispatientcharge> charges = peispatientchargeMapper.selectList(new QueryWrapper<Peispatientcharge>()
                .eq("patientcode", p.getPatientcode()).eq("is_delete", 0));
        Double tMoney = 0.00;
        for (Peispatientcharge peispatientCharge : charges) {
            if (peispatientCharge.getMoneyamountpaid() != null && peispatientCharge.getMoneyamountpaid() < 0) {
                //金额实付相加
                tMoney += peispatientCharge.getMoneyamountpaid();
            }
        }
        map.put("tMoney", tMoney);
        //体检者费用主表
        PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new QueryWrapper<PeispatientChargeMain>().eq("patientcode", patientCode));
        map.put("version", pcm == null ? null : pcm.getVersion());
        return map;
    }


    /**
     * 根据体检号查询体检者信息
     *
     * @param patientCode
     * @param isChecked
     * @return
     */
    public Peispatient getPeispatient(String patientCode, String isChecked) {
        if (StringUtils.isBlank(patientCode)) {
            return new Peispatient();
        }
        // 匹配体检号
        patientCode = "true".equals(isChecked) ? ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null)) : patientCode;
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        return peispatient;
    }


    /**
     * 获取体检者收费项目
     *
     * @param patientCode
     * @param type
     * @return
     */
    public List<Map<String, Object>> getExamfeeByPatientCode(String patientCode, String type) {
        QueryWrapper<Peispatientfeeitem> and = new QueryWrapper<>();

        if ("0".equals(type)) {
            // 全部显示
        } else if ("1".equals(type)) {
            // 显示除去退项的
            and.eq("change_item", 0);
        } else if ("2".equals(type)) {
            // 显示退项的
            and.eq("change_item", 1);
        }
        //体检者收费项目表
        List<Peispatientfeeitem> list = peispatientfeeitemMapper.selectList(and.orderByAsc("qty").eq("id_patient", patientCode));
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode));
        //体检套餐ID
        String idtjtc = patient.getIdTjtc();

        Createmeal cm = null;
        Integer bxcount = null;
        if (com.center.medical.common.utils.StringUtils.isNotEmpty(idtjtc)) {
            cm = createmealMapper.getInfoById(idtjtc);
            bxcount = cm == null || cm.getKxsl() == null ? 0 : cm.getKxsl();
        }

        for (Peispatientfeeitem peispatientfeeitem : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", peispatientfeeitem.getId());
            map.put("idExamfeeitem", peispatientfeeitem.getIdExamfeeitem());
            map.put("examfeeitemName", peispatientfeeitem.getExamfeeitemName());
            map.put("price", peispatientfeeitem.getPrice());
            map.put("factprice", peispatientfeeitem.getFactprice());
            Double endPrice = peispatientfeeitem.getFactprice();
            endPrice = (endPrice == null ? 0 : -endPrice);
            if (null != peispatientfeeitem.getChangeItem() && 1 == peispatientfeeitem.getChangeItem()) {
                endPrice = peispatientfeeitem.getEndtuiprice() == null ? 0d : peispatientfeeitem.getEndtuiprice();
            }
            map.put("endtuiprice", endPrice);
            map.put("actualprice", peispatientfeeitem.getActualprice() == null ? 0d : peispatientfeeitem.getActualprice());
            map.put("count", peispatientfeeitem.getCount());
            map.put("idPayway", peispatientfeeitem.getIdPayway());
            map.put("sfjx", peispatientfeeitem.getSfjx());
            map.put("jxys", peispatientfeeitem.getJxys());
            SysUser qxUsers = sysUserMapper.selectUserByUserNo(peispatientfeeitem.getJxys());
            map.put("name", null == qxUsers ? "" : qxUsers.getUserName());
            map.put("fRegistered", peispatientfeeitem.getFRegistered());
            map.put("changeItem", peispatientfeeitem.getChangeItem());
            map.put("fMarkFeereturn", peispatientfeeitem.getFMarkFeereturn());
            //已收费
            map.put("fFeecharged", peispatientfeeitem.getFFeecharged());
            map.put("fLabsendtolis", peispatientfeeitem.getFLabsendtolis());
            map.put("fExaminated", peispatientfeeitem.getFExaminated());
            map.put("fGiveup", peispatientfeeitem.getFGiveup());
            map.put("fDelayed", peispatientfeeitem.getFDelayed());
            map.put("fTransferedhl7", peispatientfeeitem.getFTransferedhl7());
            map.put("isMintc", peispatientfeeitem.getIsMintc());
            map.put("idKs", peispatientfeeitem.getIdKs());
            //科室名称
            SysDept sysDept = sysDeptService.getByDeptNo(peispatientfeeitem.getIdKs());
            map.put("ksmc", ObjectUtils.isNotEmpty(sysDept) ? sysDept.getDeptName() : "");
            map.put("feechargetime", peispatientfeeitem.getFeechargetime());
            map.put("bxcount", bxcount);
            map.put("idDoctorreg", peispatientfeeitem.getIdDoctorreg());
            map.put("doctorregR", peispatientfeeitem.getDoctorregR());
            //体检者收费项目ID
            Items it = itemsMapper.getInfoById(peispatientfeeitem.getIdExamfeeitem());

            map.put("fFeechargedinttrans", ObjectUtils.isNotEmpty(it) ? it.getXb() : "");// 性别

            map.put("createdate", peispatientfeeitem.getCreatedate());
            map.put("qjr", peispatientfeeitem.getQjr());
            map.put("cjr", peispatientfeeitem.getCjr());
            map.put("bjr", peispatientfeeitem.getBjr());
            map.put("qjsj", peispatientfeeitem.getQjsj());
            map.put("cjsj", peispatientfeeitem.getCjsj());
            map.put("bjsj", peispatientfeeitem.getBjsj());
            map.put("qty", peispatientfeeitem.getQty());
            map.put("jjr", peispatientfeeitem.getJjr());
            map.put("jjrqm", peispatientfeeitem.getJjrqm());
            map.put("jjsj", peispatientfeeitem.getJjsj());
            map.put("sfjj", peispatientfeeitem.getSfjj());
            map.put("feeitemdesc", peispatientfeeitem.getFeeitemdesc());
            map.put("samplemsgfromlis", peispatientfeeitem.getSamplemsgfromlis());
            map.put("giveupnotelet", peispatientfeeitem.getGiveupnotelet());
            //备选
            if (peispatientfeeitem.getIsbx() != null
                    && peispatientfeeitem.getIsbx() == 1
            ) {
                if (ObjectUtils.isNotEmpty(cm)) {
                    //普通套餐与收费项目关联表
                    Mealanditem mai = mealanditemMapper.selectOne(new QueryWrapper<Mealanditem>()
                            .eq("tcid", idtjtc).eq("sfxmid", peispatientfeeitem.getIdExamfeeitem()));
                    if (ObjectUtils.isNotEmpty(mai)) {
                        map.put("isbx", mai.getSfbx());
                        map.put("group", mai.getItemGroup());
                        map.put("groupType", mai.getGroupType());
                    }
                }
            }
            items.add(map);
        }
        return items;
    }

    /**
     * 反收费
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean returnItem(String id) {
        Peispatientfeeitem item = peispatientfeeitemService.getInfoById(id);
        // 判断这条收费项目是否存在
        if (null != item) {
            Peispatient peispatient = peispatientMapper.getByPatientCode(item.getIdPatient());
            if (null == peispatient) {
                throw new ServiceException("删除失败：体检者不存在");
            }
            peispatient.setModifydate(new Date());

            item.setFFeecharged(0);
            item.setFLabsendtolis(0);
            // 判断是否以退费
            if (null != item.getFMarkFeereturn() && 1 == item.getFMarkFeereturn()) {
                throw new ServiceException("删除失败：该条收费项目已经退费");
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
            throw new ServiceException("删除失败：该条收费项目不存在");
        }
        return Boolean.TRUE;
    }

    /**
     * 获取订单导出数据
     *
     * @param param
     * @return
     */
    @Override
    public void getExportData(HttpServletResponse response, DbOrderParam param) throws IOException {
        //是否线上系统
        if (ZhongkangConfig.isOnline()) {
            //决策管理看全部，否则自己看自己的
            if (!SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
                String userNo = SecurityUtils.getUserNo();
                param.setUserNo(userNo);
                param.setCids(branchService.getUserCid(userNo));
                param.setUserName(SecurityUtils.getUsername());
            }
        }
        List<BdOrderVo> list = orderMapper.getExportData(param);
        //存放容器
        List<ExportPatientDto> dtos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //序号
            BdOrderVo vo = list.get(i);
            vo.setRownum(i + 1);
            //预计人数
            vo.setYjrs(vo.getNxtjrs() + vo.getNxtjrs());

            //体检者团体任务
            Peisorgreservation vation = peisorgreservationMapper.selectOne(new QueryWrapper<Peisorgreservation>().eq("ddh", vo.getId()));
            if (null != vation) {
                //体检者任务分组
                List<Peisorgreservationgroup> peisorgreservationgroup = peisorgreservationgroupMapper.selectList(new QueryWrapper<Peisorgreservationgroup>().eq("id_orgreservation", vation.getId()));
                // 判断是否为空
                for (Peisorgreservationgroup group : peisorgreservationgroup) {
                    if (ObjectUtils.isNotEmpty(group)) {
                        List<Peispatient> peispatients = peispatientMapper.selectList(new QueryWrapper<Peispatient>().eq("id_orgreservationgroup", group.getId()));

                        for (int j = 0; j < peispatients.size(); j++) {
                            ExportPatientDto rowTwoList = new ExportPatientDto();
                            rowTwoList.setRownum(j + 1);//序号
                            rowTwoList.setOrgreservationgroupname(group.getOrgreservationgroupname());// 任务名称
                            rowTwoList.setPatientcode(peispatients.get(j).getPatientcode());// 体检号
                            rowTwoList.setPatientname(peispatients.get(j).getPatientname());// 姓名
                            rowTwoList.setIdSex(peispatients.get(j).getIdSex() == 0 ? "男" : "女");// 性别
                            rowTwoList.setAge(peispatients.get(j).getAge());// 年龄
                            rowTwoList.setPhone(peispatients.get(j).getPhone());// 手机号码
                            rowTwoList.setBirthdate(peispatients.get(j).getBirthdate());// 出生日期
                            rowTwoList.setIdcardno(peispatients.get(j).getIdcardno());// 身份证号
                            rowTwoList.setIdMarriage(peispatients.get(j).getIdMarriage());// 婚姻
                            rowTwoList.setNation(peispatients.get(j).getNation());// 民族
                            String[] str = {"小学", "初中", "技校", "职高", "高中", "中专", "大专", "大学", "研究生以上"};
                            rowTwoList.setCultural(null == peispatients.get(j).getCultural() ? "" : str[peispatients.get(j).getCultural()]);// 文化程度
                            rowTwoList.setOrgDepart(peispatients.get(j).getOrgDepart());// 部门
                            rowTwoList.setWorkno(peispatients.get(j).getWorkno());// 工号
                            rowTwoList.setTrades(peispatients.get(j).getTrades());// 工种
                            rowTwoList.setWorkDate(peispatients.get(j).getWorkDate());// 参加工作时间
                            rowTwoList.setZgl(peispatients.get(j).getZgl());// 总工龄(月)
                            rowTwoList.setHarmDate(peispatients.get(j).getHarmDate());// 从事工种时间
                            rowTwoList.setJhgl(peispatients.get(j).getJhgl());// 接害工龄(月)
                            String[] mt = {"上岗前", "在岗期间", "离岗时", "离岗后", "应急"};
                            rowTwoList.setMedicaltype((!StringUtils.isBlank(peispatients.get(j).getMedicaltype())
                                    && !(peispatients.get(j).getMedicaltype().contains("null"))) ?
                                    mt[Integer.valueOf(peispatients.get(j).getMedicaltype())] : "");// 体检类别
                            rowTwoList.setNote(peispatients.get(j).getNote());// 备注
                            dtos.add(rowTwoList);
                        }
                    }
                }
            }
        }


        //导出
        ExcelExp e1 = new ExcelExp("团体体检任务预定", list, BdOrderVo.class);
        ExcelExp e2 = new ExcelExp("团体体检预定任务名单", dtos, ExportPatientDto.class);
        List<ExcelExp> mysheet = new ArrayList<ExcelExp>();
        mysheet.add(e1);
        mysheet.add(e2);
        ExcelUtilManySheet<List<ExcelExp>> util2 = new ExcelUtilManySheet<List<ExcelExp>>(mysheet);
        util2.exportExcelManySheet(response, mysheet);
    }


    /**
     * 导出应急导引单
     *
     * @param ddid
     * @return
     */
    @Override
    public void exportGuidanceList(HttpServletResponse response, String ddid) throws Exception {
        String realPath = FileTypePath.REAL_PATH;
        //体检者团体任务
        Peisorgreservation vation = peisorgreservationMapper.selectOne(new QueryWrapper<Peisorgreservation>().eq("ddh", ddid));
        if (ObjectUtils.isEmpty(vation)) {
            throw new ServiceException("订单未备单，导出失败！");
        }
        //订单表
        Createorder order = createorderMapper.getInfoById(ddid);
        String orgName = vation.getOrgName();
        String informWay = "";
        //通知方式
        String idInformway = order.getIdInforway();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        if (idInformway != null) {
            //通知方式
            Notificationmethod method = notificationmethodMapper.getInfoById(idInformway);
            if (method != null) {
                informWay = method.getMethodName();
            }
        }
        String canReplace = (order.getCantReplace() == null || order.getCantReplace() == 0)
                ? "可替" : "不可替";
        //获取应急导引单数据
        List<ExportGuidanceListVo> list = orderMapper.exportGuidanceList(vation.getId());
        if (list.size() == 0) {
            throw new ServiceException("没有可以导出的信息！");
        }
        Map<String, Workbook> files = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> param = new HashMap<String, Object>();
            ExportGuidanceListVo os = list.get(i);
            boolean isHealth = "0".equals(os.getIdExamtype());
            String template = isHealth ? Constants.GUIDANCE_LIST_HEALTH : Constants.GUIDANCE_LIST_DISEASE;

            String patientname = os.getPatientname();
            param.put("patientname", patientname);
            String sex = Render.getSex(os.getIdSex());
            param.put("sex", sex);
            param.put("phone", EasyPoiUtil.getString(os.getPhone()));
            String age = EasyPoiUtil.getString(os.getAge());
            param.put("age", age);
            param.put("idcardno", EasyPoiUtil.getString(os.getIdcardno()));
            param.put("examsuiteName", EasyPoiUtil.getString(os.getExamsuiteName()));
            param.put("doctorreg", EasyPoiUtil.getString(os.getDoctorreg()));
            param.put("trades", EasyPoiUtil.getString(os.getTrades()));
            param.put("dateinorganization", EasyPoiUtil.getString(os.getDateinorganization()));
            param.put("orgName", orgName);

            if (!isHealth) {
                String jhysIds = os.getJhys();
                List<Harm> harms = harmMapper.selectList(new QueryWrapper<Harm>().in("id", jhysIds.split(",")));
                String[] harmNames = new String[harms.size()];
                for (int j = 0; j < harms.size(); j++) {
                    Harm harm = harms.get(j);
                    harmNames[j] = harm.getHarmName();
                }
                param.put("harms", StringUtils.join(harmNames, "、"));
            }
            String payway = os.getPaywayName();
            List<String> pws = new ArrayList<String>();
            if (StringUtils.isNotEmpty(payway)) {
                pws.add(payway);
            }
            if (StringUtils.isNotEmpty(informWay)) {
                pws.add(informWay);
            }
            if (StringUtils.isNotEmpty(canReplace)) {
                pws.add(canReplace);
            }
            param.put("empty", " ");
            param.put("payway", StringUtils.join(pws, "，"));
            List<Map<String, String>> fields = new ArrayList<Map<String, String>>();
            List<Peispatientfeeitem> feeitems = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", os.getPatientcode()));
            for (Peispatientfeeitem feeitem : feeitems) {
                //收费项目名称
                Map<String, String> m = new HashMap<String, String>();
                m.put("examfeeitemName", feeitem.getExamfeeitemName());
                fields.add(m);
            }
            Workbook workbook = EasyPoiUtil.exportTemplateLocal(template
                    , param
                    , fields
                    , patientname + "-" + sex + "-" + age
                    , realPath
                    , timeStamp);
            files.put(patientname + "-" + sex + "-" + age + ".xls", workbook);
        }
        try {
            ExportExcelZip.downloadExcelForZip(response, files, "应急导引单");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 重置所选订单及其套餐在网上的下载状态
     *
     * @param orderIds
     * @param cid
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean returnToZero(List<String> orderIds, String cid) {
        if (CollectionUtil.isEmpty(orderIds)) {
            throw new ServiceException("请选择一条或多条订单!");
        }
        if (StringUtils.isEmpty(cid)) {
            throw new ServiceException("分中心获取失败！");
        }
        //订单与分中心关联表
        List<Orderandfzx> oafs = orderandfzxService.list(new QueryWrapper<Orderandfzx>()
                .in("ddid", orderIds).eq("fzxid", cid));
        for (Orderandfzx oaf : oafs) {
            oaf.setTbzt(2);
            String orderId = oaf.getDdid();
            //普通套餐与分中心关联表
            List<Mealandfzx> cafs = mealandfzxMapper.findMealAndFzx(orderId, cid);
            for (Mealandfzx maf : cafs) {
                maf.setTbzt(2);
            }
            mealandfzxService.updateBatchById(cafs);
        }
        orderandfzxService.updateBatchById(oafs);
        return Boolean.TRUE;
    }


    /**
     * 分组-禁检或反禁检
     *
     * @param type
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateGroupLimit(Integer type, List<String> ids) {
        if (ObjectUtils.isEmpty(type)) {
            throw new ServiceException("请选择类型！");
        }
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请选择分组！");
        }
        if (type == 0) {
            //体检者任务分组
            Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(ids.get(0));
            if (ObjectUtils.isEmpty(group)) {
                throw new ServiceException("无法找到分组信息，请刷新重试。");
            }
            //体检者团体任务
            Long count = peisorgreservationMapper.selectCount(new QueryWrapper<Peisorgreservation>()
                    .eq("id", group.getIdOrgreservation())
                    .eq("f_finished", 1));
            if (count > 0) {
                throw new ServiceException("订单已结束，不能反禁检。");
            }
        }


        /**禁检分组下**/
        for (int i = 0; i < ids.size(); i++) {
            Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(ids.get(i));
            if (null != group) {
                if (type == 0) {// 启用
                    // 如果禁用
                    if (group.getFGroupstarted() == 0 && group.getFGrouppaused() == 1) {
                        group.setFGroupstarted(1);
                        group.setFGrouppaused(0);
                        peisorgreservationgroupMapper.updateById(group);
                    }
                } else if (type == 1) {// 禁用
                    // 如果启用
                    if (group.getFGroupstarted() == 1 && group.getFGrouppaused() == 0) {
                        group.setFGroupstarted(0);
                        group.setFGrouppaused(1);
                        peisorgreservationgroupMapper.updateById(group);
                    }
                }
            } else {
                throw new ServiceException("第 " + (i + 1) + "个组类不存在");
            }
        }


        /**禁检分组下所有人**/
        List<Peispatient> patients = peispatientMapper.selectList(new QueryWrapper<Peispatient>().in("id_orgreservationgroup", ids));
        for (Peispatient patient : patients) {
            //禁检
            if (type == 1) {
                //已经登记开检的不进行禁检处理
                if (patient.getFRegistered() == null || patient.getFRegistered().intValue() == 0) {
                    log.info("分组-禁检或反禁检，操作人：{},禁检体检号:{}",SecurityUtils.getUsername(),patient.getPatientcode());
                    patient.setFPaused(type);
                }
            } else {
                //反禁检
                patient.setFPaused(type);
            }
        }
        //批量更新
        peispatientService.updateBatchById(patients);
        return Boolean.TRUE;
    }

    /**
     * 计算总工龄和接害工龄
     *
     * @param patientCodes
     * @return
     */
    @Override
    public Boolean calculateZglAndJhgl(List<String> patientCodes) {
        for (String patientCode : patientCodes) {
            Peispatient peispatient = peispatientService.getByPatientCode(patientCode);
            if (ObjectUtils.isEmpty(peispatient)) {
                throw new ServiceException("体检号:" + patientCode + "不存在！");
            }
            if ("0".equals(peispatient.getMedicaltype())) {
                peispatient.setZgl(0);
                peispatient.setJhgl(0);
                //参加工作时间
                peispatient.setWorkDate(peispatient.getDateregister());
                //从事该工种时间
                peispatient.setHarmDate(peispatient.getDateregister());
            } else {
                //登记时间
                Date date = peispatient.getDateregister();
                if (ObjectUtils.isNotEmpty(peispatient.getWorkDate())) {
                    peispatient.setZgl(getMonthSpace(date, subTime(peispatient.getWorkDate())));
                }
                // 根据接害工龄计算从事该工种工作时间
                if (ObjectUtils.isNotEmpty(peispatient.getHarmDate())) {
                    peispatient.setJhgl(getMonthSpace(date, subTime(peispatient.getHarmDate())));
                }
            }
            peispatientService.updateById(peispatient);
        }
        return Boolean.TRUE;
    }

    /**
     * 发送预约短信
     *
     * @param ddh
     * @return
     */
    @Override
    public Boolean appointmentSMSByDddh(String ddh) {
        List<Peispatient> list = orderMapper.getSMSPeiByByDddh(ddh);
        for (Peispatient peispatient : list) {
            //判断手机号是否合法,不合法直接跳过
            if (StringUtils.isEmpty(peispatient.getPhone()) || !isValid(peispatient.getPhone())) {
                continue;
            }
            //vip直接跳过单独发
            if (StringUtils.isEmpty(peispatient.getIdPatientclass()) || !"1".equals(peispatient.getIdPatientclass())) {
                continue;
            }

            String patientcode = peispatient.getPatientcode();
            //1.查询是否发送过预约短信,发送过就跳过
            List<SmsRecord> records = smsRecordMapper.selectList(new QueryWrapper<SmsRecord>()
                    .eq("patientcode", patientcode)
                    .eq("notify_type", 0));

            if (CollectionUtil.isEmpty(records)) {
                //3.生成预约小程序链接
                String url = generateUrllink();
                System.out.println("打印一下生成跳小程序的连接:" + url);
                //2.判断会员类型，选择模板立即发送
                String idTem = "";
                List<String> data = new ArrayList<>();
                if (StringUtils.isNotEmpty(peispatient.getIdPatientclass())) {
                    //普通会员
                    idTem = Constants.ADVANCE_NOTICE_ORDINARY_TEMPLATE;
                    data.add(url + " ");

                    String[] strings = data.toArray(new String[0]);

                    //4.发送短信
                    Shortmessage message = shortmessageMapper.getInfoById(idTem);
                    String appId = message.getAppid();//appid
                    String idTemplate = message.getTemplateId();//模板id
                    String messageType = message.getMessageType();
                    String messageText = message.getMessageText();
                    String notifyContent = ToolUtil.getSmsContent(messageText, strings);

                    //生成发送记录
                    SmsRecord record = new SmsRecord(messageType, patientcode);
                    record.setCreater("自动发送预约短信");
                    record.setIsImmediately(1);
                    record.setIdTemplate(idTem);
                    record.setNotifyTime(new Date());
                    record.setNotifyContent(notifyContent);
                    Boolean isSuccess = false;
                    try {
                        SmsConfig smsConfig = iSysConfigService.getSysConfigObject(Constants.SMS_CONFIG, SmsConfig.class);
                        String result_one = SDKTestSendTemplateSMS.sendMsg(smsConfig,peispatient.getPhone(), idTemplate, strings, appId);
                        if (!"success".equals(result_one)) {
                            System.out.println("体检号" + patientcode + "发送失败！," + result_one);
                        } else {
                            System.out.println("体检号" + patientcode + "发送成功！");
                            isSuccess = true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("体检号" + patientcode + "发送失败 ," + e.getMessage());
                    }
                    if (isSuccess) {
                        record.setNotifyResult(3);
                    } else {
                        record.setNotifyResult(0);
                    }
                    smsRecordMapper.insert(record);
                }


            }

            log.info("体检号" + patientcode + "发送预约短信成功!");
        }

        return Boolean.TRUE;
    }


    /**
     * 判断手机号是否合法
     *
     * @param phone
     * @return
     */
    public static boolean isValid(String phone) {
        String regex = "^1[34578]\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }


    /**
     * 微信订阅短链接消息
     *
     * @param
     * @param
     * @return
     */
    public String generateUrllink() {
        Domain domain = iSysConfigService.getDomain();
        String url = domain.getAppDomain()+"/appArticle/getJumpUrl";
//        String url = "http://localhost:8094"+"/appArticle/getJumpUrl";
        String str = HttpUtils.sendPost(url, "");
        log.info("generateUrllink获取的url是:{}",str);
        return str;
    }


    /**
     * 获取分组下相应的人员信息(不分页)
     *
     * @param param
     * @return
     */
    @Override
    public List<OrderPaDataVo> getPatientDataList(OrderPaDataParam param) {
        //输入码去空格
        if (ObjectUtils.isNotEmpty(param.getInputCode())) {
            param.setInputCode(param.getInputCode().trim());
        }
        return orderMapper.getPatientDataList(param);
    }


    /**
     * 检查名单
     *
     * @param param
     * @return
     */
    @Override
    public List<CheckListDto> checkList(CheckListParam param) {
        Set<String> compare = new HashSet<String>();
        List<CheckListDto> checkListDtos = new ArrayList<>();
        List<Peispatient> importData = new ArrayList<>();
        int mt = param.getModelType();
        //读取数据
        ExcelUtil<PeispatientImportDto> util = new ExcelUtil<PeispatientImportDto>(PeispatientImportDto.class);
        List<PeispatientImportDto> list = null;
        try {
            list = util.importExcel(param.getFile().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("文件内容解析失败！" + JSONUtil.toJsonStr(e));
        }
        if (CollectionUtil.isEmpty(list)) {
            throw new ServiceException("名单导入失败：导入名单中不存在人员信息");
        }
        if (isInMorningTime() && list.size() > 100){
            throw new ServiceException("名单导入失败：由于早上同步数据较多,7点-12点间,导入名单单次最多导入100人！");
        }
        if (list.size() > 1000){
            throw new ServiceException("名单导入失败：导入名单中人员数量不能大于1000");
        }
        //导入的名单可能有空格，在这去掉,只去前后的空格
        for (int i = 0; i < list.size(); i++) {
            PeispatientImportDto dto = list.get(i);
            if (StringUtils.isNotEmpty(dto.getPatientname())) {
                dto.setPatientname(dto.getPatientname().trim());
            }
            if (StringUtils.isNotEmpty(dto.getIdcardno())) {
                dto.setIdcardno(dto.getIdcardno().trim());
            }
            if (StringUtils.isNotEmpty(dto.getSexMale())) {
                dto.setSexMale(dto.getSexMale().trim());
            }
            if (StringUtils.isNotEmpty(dto.getMarriage())) {
                dto.setMarriage(dto.getMarriage().trim());
            }
            if (StringUtils.isNotEmpty(dto.getOrgDepart())) {
                dto.setOrgDepart(dto.getOrgDepart().trim());
            }
            if (StringUtils.isNotEmpty(dto.getPhone())) {
                dto.setPhone(dto.getPhone().trim());
            }
            if (StringUtils.isNotEmpty(dto.getHospitalcode())) {
                dto.setHospitalcode(dto.getHospitalcode().trim());
            }
            //组类必填
            if (StringUtils.isNotEmpty(dto.getGrouptype())) {
                dto.setGrouptype(dto.getGrouptype().trim());
            } else {
                throw new ServiceException("第"+(i+2)+"行,组类不能为空！");
            }
            if (StringUtils.isNotEmpty(dto.getSabc())) {
                dto.setSabc(ToolUtil.validateSABC(dto.getSabc()));
            }
            //分中心
//            if (StringUtils.isBlank(dto.getHospitalcode())) {
//                throw new ServiceException(dto.getPatientname() + "分中心不能为空!");
//            }
        }

        //校验前的准备数据
        List<Peisorgreservationgroup> groupList = peisorgreservationgroupMapper.selectList(new LambdaQueryWrapper<Peisorgreservationgroup>()
                .eq(Peisorgreservationgroup::getIdOrgreservation, param.getId()).eq(Peisorgreservationgroup::getIsDelete, 0));


        // 健康模板
        if (mt == 1 || mt == 3) {
            // 循环遍历存储的Excel值
            for (int i = 0, z = list.size(); i < z; i++) {
                String msg = "";
                CheckListDto checkListDto = new CheckListDto();
                PeispatientImportDto dto = list.get(i);
                if (Objects.isNull(dto)) continue;
                checkListDto.setOrderNum(String.valueOf(i + 1));

                // 分中心校验
                String hospitalcodeStr = dto.getHospitalcode();
                if (StringUtils.isNotBlank(hospitalcodeStr)) {
                    String[] split = hospitalcodeStr.split("-");
                    if (split.length != 2) {
                        msg += "名单导入失败： 第" + (i + 2) + "行，该分中心不合法：" + hospitalcodeStr;
                    }
                    String hospitalcode = split[1];
                    if (StringUtils.isBlank(hospitalcode)) {
                        msg += "名单导入失败： 第" + (i + 2) + "行，该分中心不合法：" + hospitalcodeStr;
                    }
                    //判断分中心是否可用
                    String cId = SecurityUtils.getCId();
                    if (!cId.equals(hospitalcode)) {
                        List<SysUserBranch> userBranches = sysUserBranchService.list(new LambdaQueryWrapper<SysUserBranch>().eq(SysUserBranch::getUserId, SecurityUtils.getUserNo()));
                        List<String> branchIds = userBranches.stream().map(SysUserBranch::getBranchId).collect(toList());
                        if (!CollectionUtil.contains(branchIds, hospitalcode)) {
                            msg += "名单导入失败： 第" + (i + 2) + "行，你当前无法备单该分中心：" + hospitalcodeStr;
                        }
                    }
                }



                // 姓名验证
                if (!StringUtils.isBlank(dto.getPatientname())) {
                    String message = checkLength(dto.getPatientname(), "姓名长度不能超过25位;", 25);
                    if (!StringUtils.isBlank(message)) {
                        msg += message;
                    } else {
                        checkListDto.setPatientname(dto.getPatientname());
                    }
                } else {
                    msg += "姓名不能为空;";
                }

                // 性别校验
                if (!StringUtils.isBlank(dto.getSexMale())) {
                    if ("男".equals(dto.getSexMale()) || "女".equals(dto.getSexMale())) {
                        checkListDto.setSex(dto.getSexMale());
                    } else {
                        msg += "性别不合法;";
                    }
                } else {
                    msg += "性别不能为空;";
                }

                // 年龄校验
                if (!ObjectUtils.isEmpty(dto.getAge())) {
                    int r = dto.getAge();
                    // 判断年龄范围
                    if (r < 0) {
                        msg += "年龄必须在0岁以上;";
                    } else if (r > 200) {
                        msg += "年龄必须在200岁以下;";
                    } else {
                        checkListDto.setAge(dto.getAge());
                    }
                } else {
                    msg += "年龄不能为空;";
                }

                // 身份证校验
                if (!StringUtils.isBlank(dto.getIdcardno())) {
                    if ("1".equals(dto.getIdcardno())){
                        // 填1不校验身份证
                        dto.setIdcardno(null);
                    }else{
                        // 身份证号匹配
                        if (!IdcardUtil.isValidCard(dto.getIdcardno())) {
                            msg += "身份证号不合法;";
                        } else {
                            try {
                                String card = dto.getIdcardno();
                                // 如果长度是15位
                                if (card.length() == 15) {
                                    card = card.substring(0, 6) + "19" + card.substring(6) + "x";
                                }
                                Calendar cal = Calendar.getInstance();
                                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                                cal.setTime(format.parse(card.substring(6, 14)));
                                // 匹配性别
                                card = card.substring(card.length() - 2, card.length() - 1);
                                // 性别是否匹配
                                String strSex = (Integer.valueOf(card) & 1) != 0 ? "男" : "女";
                                if (StringUtils.isNotEmpty(dto.getSexMale()) && !dto.getSexMale().equals(strSex)) {
                                    msg += "身份证号与性别不匹配;";
                                }
                                checkListDto.setIdcardno(dto.getIdcardno());
                                // 年龄匹配
//                            int age = new Date().getYear() - cal.getTime().getYear();
//                            map.put("age", age);
                            } catch (Exception e) {
                                msg += "身份证号不合法;";
                            }
                        }
                    }
                }else {
                    msg += "身份证号不能为空;";
                }

                // 婚姻校验
                if (!StringUtils.isBlank(dto.getMarriage())) {
                    Integer marriageType = MarriageType.getValueByName(dto.getMarriage());
                    if (Objects.isNull(marriageType)) {
                        msg += "婚姻不存在;";
                    }
                    checkListDto.setMarriage(dto.getMarriage());
                }

                // 部门校验
                if (!StringUtils.isBlank(dto.getOrgDepart())) {
                    String message = checkLength(dto.getOrgDepart(), "部门长度不能超过30位;", 30);
                    if (!StringUtils.isBlank(message)) {
                        msg += message;
                    } else {
                        checkListDto.setOrgDepart(dto.getOrgDepart());
                    }
                }

//                // 组类校验
//                if (!StringUtils.isBlank(dto.getGrouptype())) {
//                    String message = checkLength(dto.getGrouptype(), "组类长度不能超过3位;", 3);
//                    if (!StringUtils.isBlank(message)) {
//                        msg += message;
//                    } else {
//                        checkListDto.setGrouptype(dto.getGrouptype());
//                    }
//                }

                // 联系电话校验
                if (!StringUtils.isBlank(dto.getPhone())) {
                    if ("1".equals(dto.getPhone())){
                        //填1不校验手机号
                        dto.setPhone(null);
                    }else{
                        if (dto.getPhone().length() > 13) {
                            msg += "联系电话格式错误;";
                        } else if (dto.getPhone().indexOf("0") != 0 && dto.getPhone().length() != 11) {
                            msg += "联系电话格式错误;";
                        } else {
                            checkListDto.setPhone(dto.getPhone());
                        }
                    }
                }else {
                    msg += "联系电话不能为空;";
                }

                // 备注
                if (!StringUtils.isBlank(dto.getNote())) {
                    if (dto.getNote().length() > 500) {
                        msg += "备注过长，不能超过500字;";
                    }
                }

                // 统收限额
                if (!ObjectUtils.isEmpty(dto.getTsLimit())) {
                    if (!StringUtil.isNum(dto.getTsLimit())) {
                        msg += "统收限额必须是数字;";
                    } else {
                        Double tsLimit = dto.getTsLimit();
                        if (tsLimit < 0 || tsLimit > 999999.99) {
                            msg += "统收限额数值超出范围0~999999.99;";
                        } else {
                            checkListDto.setTsLimit(dto.getTsLimit());
                        }
                    }
                }

                // 模板数据匹配
                msg += bindInfo(param.getId(), groupList, dto, importData, 0, compare);
                checkListDto.setCheckNote(msg);
                checkListDtos.add(checkListDto);
            }
        } else if (mt == 2) {
            Set<String> phones = new HashSet<String>();
            Integer numorgresv = Integer.parseInt(param.getDdh());
            // 循环遍历存储的Excel值
            for (int i = 0, z = list.size(); i < z; i++) {
                String msg = "";
                CheckListDto checkListDto = new CheckListDto();
                PeispatientImportDto dto = list.get(i);
                if (Objects.isNull(dto)) continue;

                // 订单号
                checkListDto.setOrderNum(param.getDdh());
                // 姓名验证
                if (!StringUtils.isBlank(dto.getPatientname())) {
                    String message = checkLength(dto.getPatientname(), "姓名长度不能超过25位;", 25);
                    if (!StringUtils.isBlank(message)) {
                        msg += message;
                    } else {
                        checkListDto.setPatientname(dto.getPatientname());
                    }
                } else {
                    msg += "姓名不能为空;";
                }

                // 性别校验
                if (!StringUtils.isBlank(dto.getSexMale())) {
                    if ("男".equals(dto.getSexMale()) || "女".equals(dto.getSexMale())) {
                        checkListDto.setSex(dto.getSexMale());
                    } else {
                        msg += "性别不合法;";
                    }
                } else {
                    msg += "性别不能为空;";
                }

                // 年龄校验
                if (!ObjectUtils.isEmpty(dto.getAge())) {
                    int r = dto.getAge();
                    // 判断年龄范围
                    if (r < 0) {
                        msg += "年龄必须在0岁以上;";
                    } else if (r > 200) {
                        msg += "年龄必须在200岁以下;";
                    } else {
                        checkListDto.setAge(dto.getAge());
                    }
                } else {
                    msg += "年龄不能为空;";
                }

                // 身份证校验
                if (!StringUtils.isBlank(dto.getIdcardno())) {
                    if ("1".equals(dto.getIdcardno())){
                        // 填1不校验身份证
                        dto.setIdcardno(null);
                    }else{
                        // 身份证号匹配
                        if (!IdcardUtil.isValidCard(dto.getIdcardno())) {
                            msg += "身份证号不合法;";
                        } else {
                            try {
                                String card = dto.getIdcardno();
                                // 如果长度是15位
                                if (card.length() == 15) {
                                    card = card.substring(0, 6) + "19" + card.substring(6) + "x";
                                }
                                Calendar cal = Calendar.getInstance();
                                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                                cal.setTime(format.parse(card.substring(6, 14)));
                                // 匹配性别
                                card = card.substring(card.length() - 2, card.length() - 1);
                                // 性别是否匹配
                                String strSex = (Integer.valueOf(card) & 1) != 0 ? "男" : "女";
                                if (ObjectUtils.isNotEmpty(dto.getSexMale()) && !dto.getSexMale().equals(strSex)) {
                                    msg += "身份证号与性别不匹配;";
                                }
                                checkListDto.setIdcardno(dto.getIdcardno());
                                // 年龄匹配
//                            int age = new Date().getYear() - cal.getTime().getYear();
//                            map.put("age", age);
                            } catch (Exception e) {
                                msg += "身份证号不合法;";
                            }
                        }
                    }
                }

                // 婚姻校验
                if (!StringUtils.isBlank(dto.getMarriage())) {
                    Integer marriageType = MarriageType.getValueByName(dto.getMarriage());
                    if (Objects.isNull(marriageType)) {
                        msg += "婚姻不存在;";
                    }
                    checkListDto.setMarriage(dto.getMarriage());
                }

                // 部门校验
                if (!StringUtils.isBlank(dto.getOrgDepart())) {
                    String message = checkLength(dto.getOrgDepart(), "部门长度不能超过30位;", 30);
                    if (!StringUtils.isBlank(message)) {
                        msg += message;
                    } else {
                        checkListDto.setOrgDepart(dto.getOrgDepart());
                    }
                }

                // 联系电话校验
                if (!StringUtils.isBlank(dto.getPhone())) {
                    if ("1".equals(dto.getPhone())){
                        //填1不校验手机号
                        dto.setPhone(null);
                    }else{
                        if (dto.getPhone().length() > 13) {
                            msg += "联系电话格式错误;";
                        } else if (dto.getPhone().indexOf("0") != 0 && dto.getPhone().length() != 11) {
                            msg += "联系电话格式错误;";
                        } else {
                            if (phones.contains(dto.getPhone())) {
                                msg += "联系电话与模板中其他人员重复;";
                            } else if (peispatientService.count(new LambdaQueryWrapper<Peispatient>()
                                    .eq(Peispatient::getNumorgresv, numorgresv).eq(Peispatient::getPhone, dto.getPhone())) > 0) {
                                msg += "具有相同联系电话的人员已存在;";
                            } else {
                                phones.add(dto.getPhone());
                                checkListDto.setPhone(dto.getPhone());
                            }
                        }
                    }
                } else {
                    msg += "联系电话不能为空;";
                }

                // 备注
                if (!StringUtils.isBlank(dto.getNote())) {
                    if (dto.getNote().length() > 500) {
                        msg += "备注过长，不能超过500字;";
                    }
                }

                // 统收限额
                if (!ObjectUtils.isEmpty(dto.getTsLimit())) {
                    if (!StringUtil.isNum(dto.getTsLimit())) {
                        msg += "统收限额必须是数字!;";
                    } else {
                        Double tsLimit = dto.getTsLimit();
                        if (tsLimit < 0 || tsLimit > 999999.99) {
                            msg += "统收限额数值超出范围0~999999.99;";
                        } else {
                            checkListDto.setTsLimit(dto.getTsLimit());
                        }
                    }
                }

                // 模板数据匹配
                msg += bindInfo(param.getId(), groupList, dto, importData, 0, compare);
                checkListDto.setCheckNote(msg);
                checkListDtos.add(checkListDto);
            }
        } else {
            Date date = new Date();
            // 职业、综合模板
            // 循环遍历存储的Excel值
            for (int i = 0, z = list.size(); i < z; i++) {
                String msg = "";
                CheckListDto checkListDto = new CheckListDto();
                PeispatientImportDto dto = list.get(i);
                if (Objects.isNull(dto)) continue;
                checkListDto.setOrderNum(String.valueOf(i + 1));

                // 分中心校验
                String hospitalcodeStr = dto.getHospitalcode();
                if (StringUtils.isNotBlank(hospitalcodeStr)) {
                    String hospitalcode = StringUtils.substringAfter(hospitalcodeStr, "-");
                    if (StringUtils.isBlank(hospitalcode)) {
                        msg += "名单导入失败： 第" + (i + 2) + "行，该分中心不合法：" + hospitalcodeStr;
                    }
                    //判断分中心是否可用
                    String cId = SecurityUtils.getCId();
                    if (!cId.equals(hospitalcode)) {
                        List<SysUserBranch> userBranches = sysUserBranchService.list(new LambdaQueryWrapper<SysUserBranch>().eq(SysUserBranch::getUserId, SecurityUtils.getUserNo()));
                        List<String> branchIds = userBranches.stream().map(SysUserBranch::getBranchId).collect(toList());
                        if (!CollectionUtil.contains(branchIds, hospitalcode)) {
                            msg += "名单导入失败： 第" + (i + 2) + "行，你当前无法备单该分中心：" + hospitalcodeStr;
                        }
                    }
                }

                // 判断是职业体检还是健康体检
                if (StringUtils.isBlank(dto.getExamtype())) {
                    msg += "体检类型不能为空;";
                } else {
                    // 职业体检、综合
                    Integer idExamType = ExamType.getValueByName(dto.getExamtype());
                    log.info("体检类型：{}、{}", dto.getExamtype(), idExamType);
                    if (ObjectUtils.isEmpty(idExamType) || (idExamType != 1 && idExamType != 2)) {
                        msg += "体检类型格式不正确！";
                    }
                    dto.setIdExamtype(idExamType);
                }
                // 姓名验证
                if (!StringUtils.isBlank(dto.getPatientname())) {
                    String message = checkLength(dto.getPatientname(), "姓名长度不能超过25位;", 25);
                    if (!StringUtils.isBlank(message)) {
                        msg += message;
                    } else {
                        checkListDto.setPatientname(dto.getPatientname());
                    }
                } else {
                    msg += "姓名不能为空;";
                }

                // 性别校验
                if (!StringUtils.isBlank(dto.getSexMale())) {
                    if ("男".equals(dto.getSexMale()) || "女".equals(dto.getSexMale())) {
                        checkListDto.setSex(dto.getSexMale());
                    } else {
                        msg += "性别不合法;";
                    }
                }else msg += "性别不能为空;";

                // 年龄校验
                if (!ObjectUtils.isEmpty(dto.getAge())) {
                    int r = dto.getAge();
                    // 判断年龄范围
                    if (r < 0) {
                        msg += "年龄必须在0岁以上;";
                    } else if (r > 200) {
                        msg += "年龄必须在200岁以下;";
                    } else {
                        checkListDto.setAge(dto.getAge());
                    }
                }

//                // 婚姻校验
//                if (!StringUtils.isBlank(dto.getMarriage())) {
//                    Integer marriageType = MarriageType.getValueByName(dto.getMarriage());
//                    if (Objects.isNull(marriageType)) {
//                        msg += "婚姻不存在;";
//                    }
//                    checkListDto.setMarriage(dto.getMarriage());
//                }
//
//                // 出生日期校验
//                if (!ObjectUtils.isEmpty(dto.getBirthdate())) {
//                    try {
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                        String format1 = format.format(dto.getBirthdate());
//                        Pattern pattern = Pattern.compile("\\d{4}+[-]\\d{1,2}+[-]\\d{1,2}+");
//                        Matcher matcher = pattern.matcher(format1);
//                        if (!matcher.matches()) {
//                            msg += "出生日期格式不正确;";
//                        }
//                    } catch (Exception e) {
//                        msg += "出生日期格式不正确;";
//                    }
//                }

                // 身份证校验
                if (!StringUtils.isBlank(dto.getIdcardno())) {
                    if ("1".equals(dto.getIdcardno())){
                        // 填1不校验身份证
                        dto.setIdcardno(null);
                    }else{
                        // 身份证号匹配
                        if (!IdcardUtil.isValidCard(dto.getIdcardno())) {
                            msg += "身份证号不合法;";
                        } else {
                            try {
                                String card = dto.getIdcardno();
                                // 如果长度是15位
                                if (card.length() == 15) {
                                    card = card.substring(0, 6) + "19" + card.substring(6) + "x";
                                }
                                // 匹配性别
                                card = card.substring(card.length() - 2, card.length() - 1);
                                // 性别是否匹配
                                String strSex = (Integer.valueOf(card) & 1) != 0 ? "男" : "女";
                                if (StringUtils.isNotEmpty(dto.getSexMale()) && !dto.getSexMale().equals(strSex)) {
                                    msg += "身份证号与性别不匹配;";
                                }
                                checkListDto.setIdcardno(dto.getIdcardno());
                            } catch (Exception e) {
                                msg += "身份证号不合法;";
                            }
                        }
                    }
                }else  msg += "身份证号不能为空;";


                // 婚姻校验
                if (StringUtils.isNotBlank(dto.getMarriage())) {
                    Integer marriageType = MarriageType.getValueByName(dto.getMarriage());
                    if (Objects.isNull(marriageType))
                        msg += "婚姻不存在;";
                }

                // 部门校验
                if (!StringUtils.isBlank(dto.getOrgDepart())) {
                    String message = checkLength(dto.getOrgDepart(), "部门长度不能超过30位;", 30);
                    if (!StringUtils.isBlank(message)) {
                        msg += message;
                    } else {
                        checkListDto.setOrgDepart(dto.getOrgDepart());
                    }
                }


                // 联系电话校验
                if (StringUtils.isNotBlank(dto.getPhone())) {
                    if ("1".equals(dto.getPhone())){
                        //填1不校验手机号
                        dto.setPhone(null);
                    }else{
                        if (dto.getPhone().length() > 13) {
                            msg += "联系电话不合法，不能超过13位;";
                        } else if (dto.getPhone().indexOf("0") != 0 && dto.getPhone().length() != 11) {
                            msg += "联系电话格式错误;";
                        } else {
                            checkListDto.setPhone(dto.getPhone());
                        }
                    }
                } else msg += "联系电话不能为空;";

                // 备注
                if (StringUtils.isNotBlank(dto.getNote())) {
                    if (dto.getNote().length() > 500)
                        msg += "备注过长，不能超过500字;";
                }

                // 统收限额
                if (Objects.nonNull(dto.getTsLimit())) {
                    if (!StringUtil.isNum(dto.getTsLimit())) {
                        msg += "统收限额必须是数字;";
                    } else {
                        Double tsLimit = dto.getTsLimit();
                        if (tsLimit < 0 || tsLimit > 999999.99) {
                            msg += "统收限额数值超出范围0~999999.99;";
                        }
                    }
                }
                // 文化程度校验
                if (StringUtils.isNotBlank(dto.getCulturalStr())) {
                    Integer cultural = CulturalLevel.getValueByName(dto.getCulturalStr());
                    if (Objects.isNull(cultural)) {
                        msg += "文化程度不存在;";
                    }
                }



                // 工种校验
                if (!StringUtils.isBlank(dto.getTrades())) {
                    List<BaseWorktype> bwts = baseWorktypeService.list(new LambdaQueryWrapper<BaseWorktype>()
                            .eq(BaseWorktype::getTypeName, dto.getTrades()).eq(BaseWorktype::getIsDelete, 0));
                    if (CollectionUtil.isEmpty(bwts)) {
                        String message = "规范工种不存在，请从工种工作表中选择;";
                        msg += message;
                    } else {
                        checkListDto.setTrades(dto.getTrades());
                    }
                } else {
                    msg += "规范工种不能为空;";
                }

                // 组类校验
                if (!StringUtils.isBlank(dto.getGrouptype())) {
                    String message = checkLength(dto.getGrouptype(), "组类长度不能超过3位", 3);
                    if (!StringUtils.isBlank(message)) {
                        msg += message;
                    } else {
                        checkListDto.setGrouptype(dto.getGrouptype());
                    }
                }

                // 接触（拟接触）危害因素校验
                if (!StringUtils.isBlank(dto.getJhysStr())) {
                    String[] harmStrs = dto.getJhysStr().split("、");
                    String newHarm = "";
                    String s = "";
                    for (int g = 0; g < harmStrs.length; g++) {
                        String harmStr = s + harmStrs[g];
                        //老套餐可能含有lv1的接害因素,也可能有已经删除的因素
                        Harm harm = harmMapper.selectOne(new LambdaQueryWrapper<Harm>().eq(Harm::getHarmName, harmStr).eq(Harm::getIsDelete, 0));
                        if (harm != null) {
                            newHarm += harm.getId() + ",";
                            s = "";
                        } else {
                            if (g == harmStrs.length - 1) {
                                msg += "危害因素【"+harmStr+"】不存在;";
                            }
                            s = harmStr + "、";
                        }
                    }
                    if (StringUtils.isNotEmpty(newHarm)) {
                        checkListDto.setJhys(newHarm.substring(0, newHarm.length() - 1));
                        dto.setJhys(checkListDto.getJhys());
                    }
                } else {
                    msg += "接触（拟接触）危害因素不能为空;";
                }

                // 体检类别校验
                if (StringUtils.isNotBlank(dto.getMedicaltypeStr())) {
                    Integer medicaltype = MedicalType.getValueByName(dto.getMedicaltypeStr());
                    if (Objects.isNull(medicaltype))
                        msg += "体检类别不存在;";
                    dto.setMedicaltype(medicaltype);
                } else
                    msg += "体检类别不能为空;";


                // 总工龄校验
                if (Objects.nonNull(dto.getZgln()) && Objects.nonNull(dto.getZgly())) {
                    // 年转换为月份与月相加
                    int months = 12 * dto.getZgln() + dto.getZgly();
                    //岗中、离岗体检，总工龄不能小于0
                    if (dto.getMedicaltype() == 1 || dto.getMedicaltype() == 2 || dto.getMedicaltype() == 3) {
                        if (months <= 0) {
                            msg += "岗中、离岗体检，总工龄不能小于等于0;";
                        }
                    }
                    int totalAge = 0;
                    // 参加工作时间
                    if (Objects.nonNull(dto.getWorkDate())) {
                        totalAge = getMonthSpace(new Date(), dto.getWorkDate());
                    }
                    // 判断参加工作时间与总工龄是否相符
                    if (totalAge != 0 && totalAge != months) {
                        msg += "参加工作时间与总工龄不匹配;";
                    }
                    dto.setZgl(months);

                } else
                    msg += "总工龄不能为空;";


                // 接害工龄校验
                if (Objects.nonNull(dto.getJhgln())) {
                    // 年转换为月份与月相加
                    int months = 12 * dto.getJhgln() + dto.getJhgly();
                    int harmAge = 0;
                    // 从事该岗位工种时间
                    if (Objects.nonNull(dto.getHarmDate())) {
                        harmAge = getMonthSpace(date, dto.getHarmDate());
                    }

                    // 判断从事时间与接害工龄是否相符
                    if (harmAge != 0 && harmAge != months) {
                        msg += "从事该工种时间与接害工龄不匹配;";
                    }
                    dto.setJhgl(months);
                } else
                    msg += "接害工龄不能为空;";
                // 总工龄、接害工龄验证
                if (ObjectUtils.isNotEmpty(dto.getZgl()) && ObjectUtils.isNotEmpty(dto.getJhgl())){
                    if (dto.getZgl() < dto.getJhgl()) {
                        msg += "总工龄少于接害工龄;";
                    }
                    //大于100年给提示
                    if (dto.getZgl() > 12 * 100) {
                        msg += "总工龄不能大于100年;";
                    }
                    if (dto.getJhgly() > 12 * 100) {
                        msg += "接害工龄不能大于100年;";
                    }
                }


                // 模板数据匹配
                msg += bindInfo(param.getId(), groupList, dto, importData, dto.getIdExamtype(), compare);
                checkListDto.setCheckNote(msg);
                checkListDtos.add(checkListDto);
            }
        }

        return checkListDtos;
    }

    /**
     * 校正体检者
     *
     * @param idPayway
     * @return
     */
    @Override
    public Boolean checkPeispatient(String idPayway) {
        List<CheckPeispatientVo> list = orderMapper.checkPeispatient(idPayway);
        List<String> idList = list.stream()
                .map(CheckPeispatientVo::getId) // 提取 id 字段
                .collect(Collectors.toList());

        int batchSize = 2000; // 设置每批处理的记录数
        int totalSize = idList.size();
        int batchCount = (int) Math.ceil((double) totalSize / batchSize);

        for (int i = 0; i < batchCount; i++) {
            int startIndex = i * batchSize;
            int endIndex = Math.min((i + 1) * batchSize, totalSize);

            List<String> subIdList = idList.subList(startIndex, endIndex);

            Peispatient peispatient = new Peispatient();
            peispatient.setIdPayway(idPayway);

            peispatientService.update(peispatient, new LambdaQueryWrapper<Peispatient>().in(Peispatient::getId, subIdList));
            System.out.println("已处理两千条数据！");
        }

        return Boolean.TRUE;

    }

    /**
     * 单位预约提交
     *
     * @param param
     * @return
     */
    @Override
    public String unitReservation(UnitReservationParam param) {
        //同一个手机号,同一天只能生成1次
        long count = peispatientService.count(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getGuidancenote2, "小程序团检码用户生成")
                .eq(Peispatient::getPhone, param.getPhone())
        );
        if (count > 1) {
            throw new ServiceException("今日预约次数已达上限！");
        }


        //查询
        Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(param.getGroupId());
        Peisorgreservation peisorgreservation = peisorgreservationMapper.getInfoById(group.getIdOrgreservation());
        Createorder createorder = createorderService.getInfoById(peisorgreservation.getDdh());
        Sellcustomer customer = sellcustomerMapper.getInfoById(param.getIdOrg());

        // 开单医师ID
        SysUser qxUsers = sysUserMapper.getUserByNo(customer.getXsjlid());
        String xsjlId = null == qxUsers ? "" : qxUsers.getUserNo();
        String xsjlName = null == qxUsers ? "" : qxUsers.getUserName();
        // 团体名称
        String orgName = customer.getKhdwmc();
        Peispatient peispatient = new Peispatient();
        peispatient.setQtxz(createorder.getQtxz());
        peispatient.setPhone(param.getPhone());
        peispatient.setPatientname(param.getPatientname());
        peispatient.setIdPatientclass("1");//默认普通会员
        peispatient.setGuidancenote2("小程序团检码用户生成");
        peispatient.setCreatedate(new Date());
        peispatient.setHospitalcode("2");//先默认东区分中心
        //个检/团检：0.个检 1.团检
        peispatient.setFUsecodehiden(1);
        Orderandcombo orderAndCombo = orderandcomboService.getOne(new QueryWrapper<Orderandcombo>()
                .eq("ddid", createorder.getId())
                .eq("tcid", group.getIdExamsuite()));
        Integer idExamclass = orderAndCombo.getIdExamclass();
        peispatient.setIdExamclass(idExamclass);
        // 订单号
        peispatient.setNumorgresv(createorder.getDdh());
        peispatient.setIdTjtc(createorder.getId());
        peispatient.setIdOrgreservationgroup(group.getId());
        // 任务ID
        peispatient.setIdOrgreservation(peisorgreservation.getId());
        String examsuiteName = "";
        String tjlb = "0";
        String jhys = "";
        //折后价格
        Double zhjg = 0.0;
        //最小套餐
        Createcombo combo = createcomboService.getInfoById(group.getIdExamsuite());
        if (null != combo) {
            examsuiteName = combo.getTjtcmc();
            tjlb = combo.getZytjlb();
            jhys = combo.getJhys();
            zhjg = combo.getZhjg();
        } else {
            //普通套餐表
            Createmeal createMeal = createmealMapper.getInfoById(group.getIdExamsuite());
            if (null != createMeal) {
                examsuiteName = createMeal.getTjtcmc();
                tjlb = String.valueOf(createMeal.getZytjlb());
                jhys = createMeal.getJhys();
                zhjg = createMeal.getZhjg();
            }
        }
        peispatient.setIdTjtc(group.getIdExamsuite());
        peispatient.setExamsuiteName(examsuiteName);
        if (StringUtils.isBlank(peispatient.getMedicaltype())) {
            peispatient.setMedicaltype(tjlb);
        }
        // 接害因素
        if (StringUtils.isBlank(peispatient.getJhys())) {
            peispatient.setJhys(jhys);
        }

        // 体检类型
        peispatient.setIdExamtype(group.getIdExamtype());
        // 团体id
        peispatient.setIdOrg(peisorgreservation.getIdOrg());
        // 团体名称
        peispatient.setOrgName(orgName);
        // 输入码
        peispatient.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
        // 已备单
        peispatient.setFIsforprepare(1);
        // 开单医师ID
        peispatient.setIdOpendoctor(xsjlId);
        peispatient.setDoctorapply(xsjlName);

        // 体检号
        String patientCode = "";
        do {
            patientCode = CodeUtil.getPatientCode(Constants.ONLINE_PREFIX, iSysConfigService.selectConfigByKey(Constants.VERSION_NO));
            //判断体检号是否存在
        } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getPatientcode, patientCode)) > 0);
        peispatient.setPatientcode(patientCode);
        peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
        peispatient.setFRegistered(0);
        //支付方式
        peispatient.setIdPayway(group.getIdPayway());
        Dictpayway dictPayWay = dictpaywayMapper.getInfoById(group.getIdPayway());
        peispatient.setPayway(dictPayWay.getPaywayName());

        //体检号生成人  生成时间
        peispatient.setTimingstartedat(new Date());
        peispatient.setNote("小程序团检码用户生成");

        //应付价格和实付价格
        if (BigDecimal.valueOf(zhjg).compareTo(BigDecimal.ZERO) != 0) {
            peispatient.setMoneyamount(zhjg);
            peispatient.setMoneyamountpaid(0.0);
        }

        // 保存实体类
        peispatientMapper.insert(peispatient);

        // 绑定套餐的收费项目
        List<HashMap<String, String>> items = getExamfeeitem(peispatient.getIdTjtc());
        List<Peispatientfeeitem> peispatientfeeitems = new ArrayList<Peispatientfeeitem>();
        int size = items.size();
        // 折扣价格是否放在【个检报告工本费】上
        for (int j = 0; j < size; j++) {
            Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
            peispatientfeeitem.setIdPatient(patientCode);
            peispatientfeeitem.setIdExamfeeitem(items.get(j).get("idExamfeeitem"));
            peispatientfeeitem.setExamfeeitemName(items.get(j).get("examfeeitemName"));
            peispatientfeeitem.setPrice(Double.valueOf(items.get(j).get("price")));
            peispatientfeeitem.setIdKs(items.get(j).get("idKs"));

            peispatientfeeitem.setFactprice(Double.parseDouble(String.valueOf(items.get(j).get("factprice"))));
            peispatientfeeitem.setCount(1);
            peispatientfeeitem.setIdPayway(group.getIdPayway());
            peispatientfeeitem.setFRegistered(0);
            peispatientfeeitem.setChangeItem(0);
            peispatientfeeitem.setFMarkFeereturn(0);
            peispatientfeeitem.setFFeecharged(0);
            peispatientfeeitem.setFLabsendtolis(0);
            peispatientfeeitem.setFExaminated(0);
            peispatientfeeitem.setFGiveup(0);
            peispatientfeeitem.setIsbx(Integer.parseInt(String.valueOf(items.get(j).get("isbx"))));
            peispatientfeeitem.setBxcount(items.get(j).get("bxcount") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("bxcount"))));
            peispatientfeeitem.setFDelayed(0);
            peispatientfeeitem.setIsMintc(1);
            peispatientfeeitem.setQty(items.get(j).get("qty") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("qty"))));
            peispatientfeeitem.setItemGroup(items.get(j).get("group") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("group"))));
            peispatientfeeitems.add(peispatientfeeitem);
        }
        peispatientfeeitemService.saveBatch(peispatientfeeitems);

        peispatientChargeMainMapper.insert(new PeispatientChargeMain(
                "小程序团检码用户生成"
                , peispatient.getMoneyamount()
                , peispatient.getMoneyamountpaid()
                , peispatient.getPatientcode()));

        return peispatient.getPatientcode();
    }

    /**
     * 生成体检者主表
     *
     * @param patientCodes
     * @return
     */
    @Override
    public Boolean generatePatientChangeMain(List<String> patientCodes) {
        // TODO: 未完成未测试
        for (String patientCode : patientCodes) {
            Peispatient peispatient = peispatientService.getByPatientCode(patientCode);
            Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(peispatient.getIdOrgreservationgroup());
            // 绑定套餐的收费项目
            List<HashMap<String, String>> items = getExamfeeitem(peispatient.getIdTjtc());
            List<Peispatientfeeitem> peispatientfeeitems = new ArrayList<Peispatientfeeitem>();
            int size = items.size();
            // 折扣价格是否放在【个检报告工本费】上
            for (int j = 0; j < size; j++) {
                //查询有没有对应的收费项目
                long count = peispatientfeeitemService.count(new LambdaQueryWrapper<Peispatientfeeitem>()
                        .eq(Peispatientfeeitem::getIdPatient, patientCode)
                        .eq(Peispatientfeeitem::getIdExamfeeitem, items.get(j).get("idExamfeeitem"))
                );
                if (count > 1) {
                    continue;
                }

                Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
                peispatientfeeitem.setIdPatient(patientCode);
                peispatientfeeitem.setIdExamfeeitem(items.get(j).get("idExamfeeitem"));
                peispatientfeeitem.setExamfeeitemName(items.get(j).get("examfeeitemName"));
                peispatientfeeitem.setPrice(Double.valueOf(items.get(j).get("price")));
                peispatientfeeitem.setIdKs(items.get(j).get("idKs"));

                peispatientfeeitem.setFactprice(Double.parseDouble(String.valueOf(items.get(j).get("factprice"))));
                peispatientfeeitem.setCount(1);
                peispatientfeeitem.setIdPayway(group.getIdPayway());
                peispatientfeeitem.setFRegistered(0);
                peispatientfeeitem.setChangeItem(0);
                peispatientfeeitem.setFMarkFeereturn(0);
                peispatientfeeitem.setFFeecharged(0);
                peispatientfeeitem.setFLabsendtolis(0);
                peispatientfeeitem.setFExaminated(0);
                peispatientfeeitem.setFGiveup(0);
                peispatientfeeitem.setIsbx(Integer.parseInt(String.valueOf(items.get(j).get("isbx"))));
                peispatientfeeitem.setBxcount(items.get(j).get("bxcount") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("bxcount"))));
                peispatientfeeitem.setFDelayed(0);
                peispatientfeeitem.setIsMintc(1);
                peispatientfeeitem.setQty(items.get(j).get("qty") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("qty"))));
                peispatientfeeitem.setItemGroup(items.get(j).get("group") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("group"))));
                peispatientfeeitems.add(peispatientfeeitem);
            }
            peispatientfeeitemService.saveBatch(peispatientfeeitems);

            peispatientChargeMainMapper.insert(new PeispatientChargeMain(
                    "手动生成"
                    , peispatient.getMoneyamount()
                    , peispatient.getMoneyamountpaid()
                    , peispatient.getPatientcode())
            );
        }

        return Boolean.TRUE;
    }


    /**
     * 重新计算年龄
     *
     * @param ddhs
     * @return
     */
    @Override
    public Boolean calculateAge(List<String> ddhs) {
        List<Peispatient> peispatients = new ArrayList<>();
        for (String ddh : ddhs) {
            List<Peispatient> list = peispatientService.list(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getNumorgresv, ddh).eq(Peispatient::getFRegistered, 0));
            for (Peispatient peispatient : list) {
                if (StringUtils.isNotEmpty(peispatient.getIdcardno())) {
                    //身份证合法才计算年龄
                    if (IdcardUtil.isValidCard(peispatient.getIdcardno())) {
                        peispatient.setAge(IdcardUtil.getAgeByIdCard(peispatient.getIdcardno()));
                        peispatients.add(peispatient);
                    }
                }
            }
        }
        peispatientService.updateBatchById(peispatients);
        return Boolean.TRUE;
    }


    /**
     * 重新计算价格
     *
     * @param ddhs
     * @return
     */
    @Override
    public Boolean recalculatePrices(List<String> ddhs) {
        for (String ddh : ddhs) {
            //未登记的体检者
            List<Peispatient> list = peispatientService.list(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getNumorgresv, ddh).eq(Peispatient::getFRegistered, 0));

            for (Peispatient peispatient : list) {
                Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(peispatient.getIdOrgreservationgroup());
                List<HashMap<String, String>> items = getExamfeeitem(peispatient.getIdTjtc());

                for (int j = 0; j < items.size(); j++) {
                    //查询当前体检者是否收该收费项目,有的话更新，没有的话插入
                    Peispatientfeeitem peispatientfeeitem = peispatientfeeitemService.getOne(new LambdaQueryWrapper<Peispatientfeeitem>()
                            .eq(Peispatientfeeitem::getIdExamfeeitem, items.get(j).get("idExamfeeitem"))
                            .eq(Peispatientfeeitem::getIdPatient, peispatient.getPatientcode())
                    );

                    if (ObjectUtils.isNotEmpty(peispatientfeeitem)) {
                        peispatientfeeitem.setPrice(Double.valueOf(items.get(j).get("price")));
                        peispatientfeeitem.setFactprice(Double.parseDouble(String.valueOf(items.get(j).get("factprice"))));
                        peispatientfeeitemService.updateById(peispatientfeeitem);
                    } else {
                        peispatientfeeitem = new Peispatientfeeitem();
                        peispatientfeeitem.setIdPatient(peispatient.getPatientcode());
                        peispatientfeeitem.setIdExamfeeitem(items.get(j).get("idExamfeeitem"));
                        peispatientfeeitem.setExamfeeitemName(items.get(j).get("examfeeitemName"));
                        peispatientfeeitem.setPrice(Double.valueOf(items.get(j).get("price")));
                        peispatientfeeitem.setIdKs(items.get(j).get("idKs"));
                        peispatientfeeitem.setQty(items.get(j).get("qty") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("qty"))));
                        //拆分的价格
                        peispatientfeeitem.setFactprice(Double.parseDouble(String.valueOf(items.get(j).get("factprice"))));
                        peispatientfeeitem.setCount(1);
                        peispatientfeeitem.setIdPayway(group.getIdPayway());
                        peispatientfeeitem.setFRegistered(0);
                        peispatientfeeitem.setChangeItem(0);
                        peispatientfeeitem.setFMarkFeereturn(0);
                        peispatientfeeitem.setFFeecharged(0);
                        peispatientfeeitem.setFLabsendtolis(0);
                        peispatientfeeitem.setFExaminated(0);
                        peispatientfeeitem.setFGiveup(0);
                        peispatientfeeitem.setIsbx(Integer.parseInt(String.valueOf(items.get(j).get("isbx"))));
                        peispatientfeeitem.setBxcount(items.get(j).get("bxcount") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("bxcount"))));
                        peispatientfeeitem.setFDelayed(0);
                        peispatientfeeitem.setIsMintc(1);
                        peispatientfeeitem.setItemGroup(items.get(j).get("group") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("group"))));
                        peispatientfeeitemService.save(peispatientfeeitem);
                    }
                }


                //折后价格
                Double zhjg = 0.0;
                //最小套餐
                Createcombo combo = createcomboService.getInfoById(group.getIdExamsuite());
                if (null != combo) {
                    zhjg = combo.getZhjg();
                } else {
                    //普通套餐表
                    Createmeal createMeal = createmealMapper.getInfoById(group.getIdExamsuite());
                    if (null != createMeal) {
                        zhjg = createMeal.getZhjg();
                    }
                }
                //更新体检者表
                peispatient.setMoneyamount(zhjg);
                peispatientService.updateById(peispatient);


                //更新收费主表
                PeispatientChargeMain peispatientChargeMain = peispatientChargeMainMapper.getByPatientCode(peispatient.getPatientcode());
                peispatientChargeMain.setMoneyamount(zhjg);
                peispatientChargeMainMapper.updateById(peispatientChargeMain);
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 团检套餐加项
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addItem(OrderAddItemParam param) {

        //套餐增项
        Items item = itemsMapper.selectById(param.getItemId());

        if (mealanditemService.count(new LambdaQueryWrapper<Mealanditem>().eq(Mealanditem::getSfxmid, item.getId()).eq(Mealanditem::getTcid, param.getTcId())) == 0) {
            Mealanditem mealanditem = new Mealanditem();
            mealanditem.setSfbx(0);
            mealanditem.setSfxmid(item.getId());
            mealanditem.setTcid(param.getTcId());
            mealanditemService.save(mealanditem);
        }


        List<Peispatientfeeitem> peispatientfeeitems = new ArrayList<>();
        LambdaUpdateWrapper<Peispatient> wrapper = new LambdaUpdateWrapper<Peispatient>().eq(Peispatient::getNumorgresv, param.getDdh()).eq(Peispatient::getIdTjtc, param.getTcId());
        if (param.getFRegistered() == 0 || param.getFRegistered() == 1) {
            wrapper.eq(Peispatient::getFRegistered, param.getFRegistered());
        }
        List<Peispatient> list = peispatientService.list(wrapper);
        for (Peispatient peispatient : list) {
            if (peispatientfeeitemService.count(new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, peispatient.getPatientcode()).eq(Peispatientfeeitem::getIdExamfeeitem, item.getId())) > 0) {
                continue;
            }

            //体检者增项
            Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
            peispatientfeeitem.setIdPatient(peispatient.getPatientcode());
            peispatientfeeitem.setIdExamfeeitem(item.getId());
            peispatientfeeitem.setExamfeeitemName(item.getExamfeeitemName());
            peispatientfeeitem.setPrice(param.getPrice());
            peispatientfeeitem.setIdKs(item.getIdDepart());
            peispatientfeeitem.setFactprice(item.getUnitprice());
            peispatientfeeitem.setCount(1);
            peispatientfeeitem.setIdPayway(param.getIdPayway());
            peispatientfeeitem.setFRegistered(0);
            peispatientfeeitem.setChangeItem(0);
            peispatientfeeitem.setFMarkFeereturn(0);
            peispatientfeeitem.setFFeecharged(0);
            peispatientfeeitem.setFLabsendtolis(0);
            peispatientfeeitem.setFExaminated(0);
            peispatientfeeitem.setFGiveup(0);
            peispatientfeeitem.setIsbx(0);
            peispatientfeeitem.setBxcount(0);
            peispatientfeeitem.setFDelayed(0);
            peispatientfeeitem.setIsMintc(1);
            long count = peispatientfeeitemService.count(new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, peispatient.getPatientcode()));
            peispatientfeeitem.setQty((int) (count + 1));
            peispatientfeeitems.add(peispatientfeeitem);
        }
        peispatientfeeitemService.saveBatch(peispatientfeeitems);

        return Boolean.TRUE;
    }

    /**
     * 添加收费主表
     *
     * @return
     */
    @Override
    public Boolean addChangMain(List<String> patientcodes) {
//        List<String> patientcodes = peispatientChargeMainMapper.getAddChangMain();
        List<PeispatientChargeMain> peispatientChargeMainList = new ArrayList<>();

        //添加
        for (String patientcode : patientcodes) {
            PeispatientChargeMain peispatientChargeMain = new PeispatientChargeMain();
            peispatientChargeMain.setPatientcode(patientcode);
            peispatientChargeMain.setNote("执行接口添加");
            peispatientChargeMain.setVersion(null);
            peispatientChargeMain.setCreatedate(new Date());
            peispatientChargeMainList.add(peispatientChargeMain);
        }
        peispatientChargeMainService.saveBatch(peispatientChargeMainList);
        return Boolean.TRUE;
    }

    /**
     * 购买套餐生成体检号
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generateCode(GenerateCodeParam param) {

        Peispatient peispatient = new Peispatient();
        peispatient.setPatientbizno(param.getSettlementId());
        peispatient.setIdExamtype("0");
        peispatient.setIdExamclass(0);
        peispatient.setQtxz(null);
        peispatient.setPhone(param.getPhone());
        peispatient.setPatientname(null);
        peispatient.setIdPatientclass("1");//默认普通会员
        peispatient.setGuidancenote2("小程序购买套餐");
        peispatient.setCreatedate(new Date());
        peispatient.setHospitalcode(param.getFzxId());//分中心
        //个检/团检：0.个检 1.团检
        peispatient.setFUsecodehiden(0);
        // 订单号
        peispatient.setNumorgresv(null);
        peispatient.setIdOrgreservationgroup(null);
        // 任务ID
        peispatient.setIdOrgreservation(null);

        //普通套餐表
        Createcombo createcombo = createcomboService.getInfoById(param.getTcId());
        peispatient.setExamsuiteName(createcombo.getTjtcmc());
        peispatient.setIdTjtc(param.getTcId());
        // 已备单
        peispatient.setFIsforprepare(1);
        // 开单医师ID
        peispatient.setIdOpendoctor("1788757788712371823");
        peispatient.setDoctorapply("小程序收款");

        // 体检号
        String patientCode = "";
        do {
            //生成带区号的体检号
            patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(param.getFzxId()), "");
//            patientCode = CodeUtil.getPatientCode(Constants.ONLINE_PREFIX, iSysConfigService.selectConfigByKey(Constants.VERSION_NO));
            //判断体检号是否存在
        } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getPatientcode, patientCode)) > 0);
        peispatient.setPatientcode(patientCode);
        peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
        peispatient.setFRegistered(0);
        //支付方式
        peispatient.setIdPayway("99");
        peispatient.setPayway("随行支付");
//        peispatient.setPrepayment(param.getMoney());
        //体检号生成人  生成时间
        peispatient.setTimingstartedat(new Date());
        peispatient.setNote("小程序购买套餐生成");
        //应付价格和实付价格
        peispatient.setMoneyamount(param.getMoney());
        peispatient.setMoneyamountpaid(param.getMoney());

        // 保存实体类
        peispatientMapper.insert(peispatient);

        // 绑定套餐的收费项目
        List<HashMap<String, String>> items = getExamfeeitem(peispatient.getIdTjtc());
        List<Peispatientfeeitem> peispatientfeeitems = new ArrayList<Peispatientfeeitem>();
        int size = items.size();
        // 折扣价格是否放在【个检报告工本费】上
        for (int j = 0; j < size; j++) {
            Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
            peispatientfeeitem.setIdPatient(patientCode);
            peispatientfeeitem.setIdExamfeeitem(items.get(j).get("idExamfeeitem"));
            peispatientfeeitem.setExamfeeitemName(items.get(j).get("examfeeitemName"));
            peispatientfeeitem.setPrice(Double.valueOf(items.get(j).get("price")));
            peispatientfeeitem.setIdKs(items.get(j).get("idKs"));

            peispatientfeeitem.setFactprice(Double.parseDouble(String.valueOf(items.get(j).get("factprice"))));
            peispatientfeeitem.setCount(1);
            peispatientfeeitem.setIdPayway("1");
            peispatientfeeitem.setFRegistered(0);
            peispatientfeeitem.setChangeItem(0);
            peispatientfeeitem.setFMarkFeereturn(0);
            peispatientfeeitem.setFFeecharged(0);
            peispatientfeeitem.setFLabsendtolis(0);
            peispatientfeeitem.setFExaminated(0);
            peispatientfeeitem.setFGiveup(0);
            peispatientfeeitem.setIsbx(Integer.parseInt(String.valueOf(items.get(j).get("isbx"))));
            peispatientfeeitem.setBxcount(items.get(j).get("bxcount") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("bxcount"))));
            peispatientfeeitem.setFDelayed(0);
            peispatientfeeitem.setIsMintc(1);
            peispatientfeeitem.setQty(items.get(j).get("qty") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("qty"))));
            peispatientfeeitem.setItemGroup(items.get(j).get("group") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("group"))));
            peispatientfeeitems.add(peispatientfeeitem);
        }
        peispatientfeeitemService.saveBatch(peispatientfeeitems);

        //收费记录
        Peispatientcharge peispatientcharge = new Peispatientcharge();
        peispatientcharge.setPatientcode(peispatient.getPatientcode());
//        peispatientcharge.setIdPayway("14");
//        peispatientcharge.setPayway("微信");
        peispatientcharge.setIdPayway("99");
        peispatientcharge.setPayway("随行支付");
        peispatientcharge.setMoneyamount(param.getMoney());
        peispatientcharge.setMoneyamountpaid(param.getMoney());
        peispatientcharge.setFFeecharged(1);
        peispatientcharge.setFeechargetime(new Date());
        peispatientcharge.setIdFeecharger("1788757788712371823");
        peispatientcharge.setIsDelete(0);
        peispatientcharge.setShortCode(CodeUtil.getShortCodeByLong(peispatient.getPatientcode()));
        peispatientcharge.setFFeeconfirmed(1);
        peispatientcharge.setTradeNo(param.getBizPayNo());
        peispatientcharge.setCardno(param.getBizPayNo());
//        peispatientcharge.setNote("预收");
        peispatientchargeMapper.insert(peispatientcharge);


        peispatientChargeMainMapper.insert(new PeispatientChargeMain(
                "小程序购买套餐生成"
                , peispatient.getMoneyamount()
                , peispatient.getMoneyamountpaid()
                , peispatient.getPatientcode()));

        return peispatient.getPatientcode();
    }

    /**
     * 退款删除体检号
     *
     * @param code
     * @return
     */
    @Override
    public Boolean deleteCode(String code) {
        Reservation reservation = reservationService.getOne(new LambdaQueryWrapper<Reservation>()
                .eq(Reservation::getPatientcode, code)
                .in(Reservation::getStatus, 0, 1, 2, 3)
        );
        if (ObjectUtils.isNotEmpty(reservation) && StringUtils.isNotEmpty(reservation.getTimeId())) {
            // 恢复可预约人数
            ReservationSetting setting = reservationSettingService.getInfoById(reservation.getTimeId());
            if (Objects.nonNull(setting)) {
                reservationSettingService.updateAbleNum(reservation.getTimeId(), 1);
            }
            //删除预约信息
            reservationService.remove(new LambdaQueryWrapper<Reservation>()
                    .eq(Reservation::getPatientcode, code));
        }

        peispatientMapper.delete(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, code));
        peispatientfeeitemMapper.delete(new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, code));
        peispatientchargeMapper.delete(new LambdaQueryWrapper<Peispatientcharge>().eq(Peispatientcharge::getPatientcode, code));
        peispatientChargeMainMapper.delete(new LambdaQueryWrapper<PeispatientChargeMain>().eq(PeispatientChargeMain::getPatientcode, code));
        return Boolean.TRUE;
    }


    /**
     * 校验体检号状态的体检号是
     *
     * @param code
     * @return
     */
    @Override
    public String checkPeiStatus(String code) {
        if (StringUtils.isEmpty(code)) {
            return "请输入体检号！";
        }

        Peispatient peispatient = peispatientService.getByPatientCode(code);
        if (ObjectUtils.isEmpty(peispatient)) {
            return "体检信息不正确！";
        }
        if (peispatient.getFRegistered() == 1) {
            return "已登记不能退款！";
        }
        return Boolean.TRUE.toString();
    }

    /**
     * 收费项目不对的重新计算价格
     * @param ddhs
     * @return
     */
    @Override
    public String recalculatePrice(List<String> ddhs) {
        //查出收费项目不对的体检号
        List<RecalculatePriceDto> recalculatePriceDtoList = peispatientfeeitemMapper.recalculatePrice(ddhs);
        log.info("查出收费项目不对的数据:{}",recalculatePriceDtoList);
        List<String> idList = recalculatePriceDtoList.stream()
                .map(RecalculatePriceDto::getId)
                .collect(Collectors.toList());
        List<String> patientCodes = recalculatePriceDtoList.stream()
                .map(RecalculatePriceDto::getPatientcode)
                .collect(Collectors.toList());


        //删除之前的数据
        peispatientfeeitemMapper.delete(new LambdaQueryWrapper<Peispatientfeeitem>().in(Peispatientfeeitem::getIdPatient, patientCodes));
        peispatientAndFzxMapper.delete(new LambdaQueryWrapper<PeispatientAndFzx>().in(PeispatientAndFzx::getPatientId,idList));
        peispatientChargeMainMapper.delete(new LambdaQueryWrapper<PeispatientChargeMain>().in(PeispatientChargeMain::getPatientcode, patientCodes));

        //重新添加
        List<Peispatient> importData = peispatientService.list(new LambdaQueryWrapper<Peispatient>().in(Peispatient::getPatientcode, patientCodes));
        savePeispatientfeeitem(importData, "admin重新计算价格");

        return patientCodes.toString();
    }


    /**
     * 线上重新绑定体检者任务和分组
     * @param ddhs
     * @return
     */
    @Override
    public String bindingVationAndGroupAgain(List<String> ddhs) {
        //未完成未测试
        StringBuilder stringBuilder = new StringBuilder();
        //根据订单号查询
        for (String ddh : ddhs) {
            VationAndGroupErrorDataDto dto = createorderService.getVationAndGroupErrorData(ddh);
            if (ObjectUtils.isEmpty(dto)){
                stringBuilder.append("订单号:"+ ddh + "，里面没有需要修改的数据!");
                stringBuilder.append(System.lineSeparator());
                break;
            }
            Createorder createorder = createorderService.getOne(new LambdaQueryWrapper<Createorder>().eq(Createorder::getDdh, ddh));
            if (ObjectUtils.isEmpty(createorder)){
                stringBuilder.append("订单号:"+ ddh + "，订单为空");
                stringBuilder.append(System.lineSeparator());
                break;
            }
            Peisorgreservation peisorgreservation = peisorgreservationMapper.selectOne(new LambdaQueryWrapper<Peisorgreservation>()
                    .eq(Peisorgreservation::getDdh, createorder.getId()));
            if (ObjectUtils.isEmpty(peisorgreservation)){
                stringBuilder.append("订单号:"+ ddh + "，任务为空");
                stringBuilder.append(System.lineSeparator());
                break;
            }
            List<Peispatient> list = peispatientService.list(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getIdOrgreservation, dto.getIdOrgreservation()));
            for (Peispatient peispatient : list) {
                peispatient.setIdOrgreservation(peisorgreservation.getId());
                List<Peisorgreservationgroup> peisorgreservationgroups = peisorgreservationgroupMapper.selectList(new LambdaQueryWrapper<Peisorgreservationgroup>()
                        .eq(Peisorgreservationgroup::getIdOrgreservation, peisorgreservation.getId())
                        .eq(Peisorgreservationgroup::getIdExamsuite, peispatient.getIdTjtc())
                );
                if (CollectionUtils.isNotEmpty(peisorgreservationgroups)){
                    peispatient.setIdOrgreservationgroup(peisorgreservationgroups.get(0).getId());
                }else {
                    stringBuilder.append("体检号:"+ peispatient.getPatientcode() + "，体检者分组没找到");
                    stringBuilder.append(System.lineSeparator());
                }
            }
            peispatientService.updateBatchById(list);
            stringBuilder.append("订单号:"+ ddh + "，修改成功!");
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    /**
     * 设为可重复
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean setRepeated(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)){
            throw new ServiceException("请选择一条数据！");
        }
        List<Peisorgreservationgroup> list = new ArrayList<>();
        for (String id : ids) {
            Peisorgreservationgroup peisorgreservationgroup = peisorgreservationgroupMapper.getInfoById(id);
            if (ObjectUtils.isEmpty(peisorgreservationgroup)){
                throw new ServiceException(id + "id不存在!");
            }
            //设为可重复
            peisorgreservationgroup.setIdPatientclass2(1);
            peisorgreservationgroup.setIdPatientclass3(1);
            list.add(peisorgreservationgroup);
        }
        peisorgreservationgroupService.updateBatchById(list);
        return Boolean.TRUE;
    }

    /**
     * 重新计算价格
     * @param peispatientNew
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recalculatePeiPrice(Peispatient peispatientNew) {
        // 绑定套餐的收费项目
        List<HashMap<String, String>> items = getExamfeeitem(peispatientNew.getIdTjtc());
        List<Peispatientfeeitem> peispatientfeeitems = new ArrayList<Peispatientfeeitem>();
        int size = items.size();
        Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(peispatientNew.getIdOrgreservationgroup());
        //查询当前体检者是否收该收费项目，有的话跳过
        List<Peispatientfeeitem> list = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, peispatientNew.getPatientcode()));
        for (int j = 0; j < size; j++) {
            Peispatientfeeitem oldPeispatientfeeitem = null;
            for (Peispatientfeeitem peispatientfeeitem : list) {
                String idExamfeeitem = items.get(j).get("idExamfeeitem");
                if (idExamfeeitem.equals(peispatientfeeitem.getIdExamfeeitem())){
                    oldPeispatientfeeitem = peispatientfeeitem;
                    break;
                }
            }
            if (ObjectUtils.isEmpty(oldPeispatientfeeitem)) {
                //少收费项目,重新添加
                Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
                peispatientfeeitem.setIdPatient(peispatientNew.getPatientcode());
                peispatientfeeitem.setIdExamfeeitem(items.get(j).get("idExamfeeitem"));
                peispatientfeeitem.setExamfeeitemName(items.get(j).get("examfeeitemName"));
                peispatientfeeitem.setPrice(Double.valueOf(items.get(j).get("price")));
                peispatientfeeitem.setIdKs(items.get(j).get("idKs"));
                peispatientfeeitem.setQty(items.get(j).get("qty") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("qty"))));
                //拆分的价格
                peispatientfeeitem.setFactprice(Double.parseDouble(String.valueOf(items.get(j).get("factprice"))));
                peispatientfeeitem.setCount(1);
                peispatientfeeitem.setIdPayway(group.getIdPayway());
                peispatientfeeitem.setFRegistered(0);
                peispatientfeeitem.setChangeItem(0);
                peispatientfeeitem.setFMarkFeereturn(0);
                peispatientfeeitem.setFFeecharged(0);
                peispatientfeeitem.setFLabsendtolis(0);
                peispatientfeeitem.setFExaminated(0);
                peispatientfeeitem.setFGiveup(0);
                peispatientfeeitem.setIsbx(Integer.parseInt(String.valueOf(items.get(j).get("isbx"))));
                peispatientfeeitem.setBxcount(items.get(j).get("bxcount") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("bxcount"))));
                peispatientfeeitem.setFDelayed(0);
                peispatientfeeitem.setIsMintc(1);
                peispatientfeeitem.setItemGroup(items.get(j).get("group") == null ? null : Integer.parseInt(String.valueOf(items.get(j).get("group"))));
                peispatientfeeitems.add(peispatientfeeitem);
            }else if (!String.valueOf(oldPeispatientfeeitem.getFactprice()).equals(String.valueOf(items.get(j).get("factprice")))){
                //价格不一致，重新设置价格
                oldPeispatientfeeitem.setFactprice(Double.parseDouble(String.valueOf(items.get(j).get("factprice"))));
                peispatientfeeitems.add(oldPeispatientfeeitem);
            }
        }
        peispatientfeeitemService.saveOrUpdateBatch(peispatientfeeitems);

    }

    /**
     * 补全套餐项目
     * @param patientcode
     * @return
     */
    @Override
    public Boolean completeTheProject(String patientcode) {
        Peispatient peispatient = peispatientService.getByPatientCode(patientcode);
        if (ObjectUtils.isEmpty(peispatient)){
            throw new ServiceException("体检号不存在！");
        }
        if (StringUtils.isEmpty(peispatient.getIdOrgreservationgroup())){
            throw new ServiceException("体检者分组不能为空！");
        }
        recalculatePeiPrice(peispatient);
        return Boolean.TRUE;
    }

    /**
     * 拉取线上体检者数据
     * @param patientcode 体检号
     * @param type 插入类型 0全部 1收费项目 2收费主表
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean pullOnlineData(String patientcode , Integer type) {
        Peispatient peispatient = peispatientService.getByPatientCode(patientcode);
        if (ObjectUtils.isEmpty(peispatient)){
            throw new ServiceException("体检号不存在！");
        }
        if (ObjectUtils.isNotEmpty(peispatient.getFRegistered()) && peispatient.getFRegistered() == 1){
            throw new ServiceException("该体检号已登记，不能操作！");
        }
        //请求线上获取体检者数据
        Domain domain = iSysConfigService.getDomain();
        String pfDomain = domain.getPlatformDomain();
//        String pfDomain = "http://localhost:8090";
        String url = pfDomain + Constants.ONLINE_SYNC_DATA_PULLONLINEDATA;
        try {
            String s = HttpUtils.sendGet(url, "patientcode="+patientcode);
            R responseEntity = JSONUtil.toBean(s, R.class);
            if (200 != responseEntity.getCode()) {
                throw new ServiceException("线上体检者数据拉取失败！");
            }
            //更新或插入
            PullOnlineDataDto dto = JSONUtil.toBean(responseEntity.getData().toString(), PullOnlineDataDto.class);
            switch (type) {
                case 0://全部
                    if (CollectionUtil.isNotEmpty(dto.getPeispatientfeeitemList())){
                        peispatientfeeitemService.saveOrUpdateBatch(dto.getPeispatientfeeitemList());
                    }else {
                        savePeispatientfeeitem(Arrays.asList(peispatient),"admin");
                    }
                    if (ObjectUtils.isNotEmpty(dto.getPeispatientChargeMain())){
                        peispatientChargeMainService.saveOrUpdate(dto.getPeispatientChargeMain());
                    }else {
                        PeispatientChargeMain peispatientChargeMain = new PeispatientChargeMain();
                        peispatientChargeMain.setPatientcode(patientcode);
                        peispatientChargeMainService.save(peispatientChargeMain);
                    }
                    break;
                case 1://收费项目
                    if (CollectionUtil.isNotEmpty(dto.getPeispatientfeeitemList())){
                        peispatientfeeitemService.saveOrUpdateBatch(dto.getPeispatientfeeitemList());
                    }else {
                        savePeispatientfeeitem(Arrays.asList(peispatient),"admin");
                    }
                    break;
                case 2://收费主表
                    if (ObjectUtils.isNotEmpty(dto.getPeispatientChargeMain())){
                        peispatientChargeMainService.saveOrUpdate(dto.getPeispatientChargeMain());
                    }else {
                        PeispatientChargeMain peispatientChargeMain = new PeispatientChargeMain();
                        peispatientChargeMain.setPatientcode(patientcode);
                        peispatientChargeMainService.save(peispatientChargeMain);
                    }
                    break;
            }
        }catch (Exception e){
            log.info("数据拉取失败！");
        }

        return Boolean.TRUE;
    }

    /**
     * 清理失效图片
     * @return
     */
    @Override
    public String cleanUpInvalidImages() {
        StringBuilder stringBuilder = new StringBuilder();
        List<InvalidImagesDto> list = sysUserMapper.getInvalidImages();
        //线上线下地址
        Domain domain = iSysConfigService.getDomain();
        String prefix = ZhongkangConfig.isOnline() ? domain.getRsPfDomain() : domain.getRsLcDomain();
        for (InvalidImagesDto dto : list) {
            String image = prefix + dto.getSignPic();
            //校验图片是否存在
            if (!isImageUrlExists(image)){
                stringBuilder.append(dto.getUserId()+",");
            }
        }
        return stringBuilder.toString();
    }


    public static boolean isImageUrlExists(String imageUrl) {
        try {
            // 创建一个 URL 对象
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法为 HEAD，以减少数据传输
            connection.setRequestMethod("HEAD");
            // 设置连接超时和读取超时
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            // 发送请求
            connection.connect();

            // 获取响应码
            int responseCode = connection.getResponseCode();
            // 检查响应码是否为 200 (HTTP_OK) 或 206 (HTTP_PARTIAL)
            return (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_PARTIAL);
        } catch (IOException e) {
            // 处理异常，返回 false 表示图片不存在
            return false;
        }
    }

    /**
     * 重新计算已登记体检号的价格
     * @param patientCodes
     * @return
     */
    @Override
    public Boolean recalculateRegistrationPrice(List<String> patientCodes) {
        for (String patientCode : patientCodes) {
            Peispatient peispatient = peispatientService.getByPatientCode(patientCode);
            if (ObjectUtils.isEmpty(peispatient)){
                continue;
            }

            Createmeal createmeal = createmealMapper.getInfoById(peispatient.getIdTjtc());
            Double zhjg = createmeal.getZhjg();
            if (!peispatient.getMoneyamountpaid().equals(zhjg)){
                //重新计算价格
                recalculateRegistration(peispatient);
            }

        }
        return Boolean.TRUE;
    }

    /**
     * 重新计算价格
     * @param peispatient
     */
    private void recalculateRegistration(Peispatient peispatient) {
        log.info("重新计算价格,体检号:{}",peispatient.getPatientcode());
        // 绑定套餐的收费项目
        List<HashMap<String, String>> items = getExamfeeitem(peispatient.getIdTjtc());
        List<Peispatientfeeitem> peispatientfeeitems = new ArrayList<Peispatientfeeitem>();
        int size = items.size();
        //查询当前体检者是否收该收费项目，有的话跳过
        List<Peispatientfeeitem> list = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, peispatient.getPatientcode()));
        for (int j = 0; j < size; j++) {
            Peispatientfeeitem oldPeispatientfeeitem = null;
            for (Peispatientfeeitem peispatientfeeitem : list) {
                String idExamfeeitem = items.get(j).get("idExamfeeitem");
                if (idExamfeeitem.equals(peispatientfeeitem.getIdExamfeeitem())){
                    oldPeispatientfeeitem = peispatientfeeitem;
                    break;
                }
            }
            if (ObjectUtils.isEmpty(oldPeispatientfeeitem)) {
                //少收费项目,暂时不考虑
            }else if (!String.valueOf(oldPeispatientfeeitem.getFactprice()).equals(String.valueOf(items.get(j).get("factprice")))){
                //价格不一致，重新设置价格
                oldPeispatientfeeitem.setFactprice(Double.parseDouble(String.valueOf(items.get(j).get("factprice"))));
                oldPeispatientfeeitem.setEndtuiprice(-Double.parseDouble(String.valueOf(items.get(j).get("factprice"))));
                peispatientfeeitems.add(oldPeispatientfeeitem);
            }
        }
        peispatientfeeitemService.saveOrUpdateBatch(peispatientfeeitems);

        //修改价格
        //体检者表
        Createmeal createmeal = createmealMapper.getInfoById(peispatient.getIdTjtc());
        Double zhjg = createmeal.getZhjg();
        peispatient.setMoneyamount(zhjg);
        peispatient.setMoneyamountpaid(zhjg);
        peispatientService.updateById(peispatient);

        //体检者收费表主表
        PeispatientChargeMain peispatientChargeMain = peispatientChargeMainService.getOne(new LambdaQueryWrapper<PeispatientChargeMain>()
                .eq(PeispatientChargeMain::getPatientcode, peispatient.getPatientcode()));
        peispatientChargeMain.setMoneyamount(zhjg);
        peispatientChargeMain.setMoneyamountpaid(zhjg);
        peispatientChargeMainService.updateById(peispatientChargeMain);

        //体检者收费表
        List<Peispatientcharge> peispatientcharges = peispatientchargeMapper.selectList(new LambdaQueryWrapper<Peispatientcharge>()
                .eq(Peispatientcharge::getPatientcode, peispatient.getPatientcode()));
        if (CollectionUtils.isNotEmpty(peispatientcharges)){
            Peispatientcharge peispatientcharge = peispatientcharges.get(0);
            peispatientcharge.setMoneyamount(zhjg);
            peispatientcharge.setMoneyamountpaid(zhjg);
            peispatientchargeMapper.updateById(peispatientcharge);
        }
        log.info("体检号重新计算价格成功:{}",peispatient.getPatientcode());

    }

    /**
     * 批量加项
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchAddItems(BatchAddItemsParam param) {
        String ddh = param.getDdh();
        String itemsId = param.getItemsId();
        Double price = param.getPrice();
        List<Peispatient> peispatientList = peispatientService.list(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getNumorgresv, ddh)
                .eq(Peispatient::getFRegistered, 0));
        Items items = itemsMapper.getInfoById(itemsId);

        List<Peispatientfeeitem> peispatientfeeitemList = new ArrayList<>();
        for (Peispatient peispatient : peispatientList) {
            Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
            peispatientfeeitem.setIdPatient(peispatient.getPatientcode());
            peispatientfeeitem.setIdExamfeeitem(items.getId());
            peispatientfeeitem.setExamfeeitemName(items.getExamfeeitemName());
            peispatientfeeitem.setPrice(items.getUnitprice());
            peispatientfeeitem.setIdKs(items.getIdDepart());

            //拆分的价格
            peispatientfeeitem.setFactprice(price);
            peispatientfeeitem.setCount(1);
            peispatientfeeitem.setIdPayway(param.getIdPayway());
            peispatientfeeitem.setFRegistered(0);
            peispatientfeeitem.setChangeItem(0);
            peispatientfeeitem.setFMarkFeereturn(0);
            peispatientfeeitem.setFFeecharged(0);
            peispatientfeeitem.setFLabsendtolis(0);
            peispatientfeeitem.setFExaminated(0);
            peispatientfeeitem.setFGiveup(0);
            peispatientfeeitem.setIsbx(0);
            peispatientfeeitem.setBxcount(0);
            peispatientfeeitem.setFDelayed(0);
            peispatientfeeitem.setIsMintc(1);
            peispatientfeeitem.setItemGroup(0);
            peispatientfeeitemList.add(peispatientfeeitem);
        }
        peispatientfeeitemService.saveOrUpdateBatch(peispatientfeeitemList);
        return Boolean.TRUE;
    }

    /**
     * 重新添加收费项目
     * @param patientCodes
     * @return
     */
    @Override
    public Boolean addAgainFeeItem(List<String> patientCodes) {
        //删除旧数据
        peispatientfeeitemService.remove(new LambdaQueryWrapper<Peispatientfeeitem>()
                .in(Peispatientfeeitem::getIdPatient, patientCodes));
        peispatientChargeMainService.remove(new LambdaQueryWrapper<PeispatientChargeMain>()
                .in(PeispatientChargeMain::getPatientcode, patientCodes));
        //重新添加收费项目
        List<Peispatient> list = peispatientService.list(new LambdaQueryWrapper<Peispatient>().in(Peispatient::getPatientcode, patientCodes));
        List<String> ids = list.stream().map(Peispatient::getId).collect(Collectors.toList());
        peispatientAndFzxMapper.delete(new LambdaQueryWrapper<PeispatientAndFzx>().in(PeispatientAndFzx::getPatientId,ids));
        savePeispatientfeeitem(list,"admin");
        return Boolean.TRUE;
    }
}

