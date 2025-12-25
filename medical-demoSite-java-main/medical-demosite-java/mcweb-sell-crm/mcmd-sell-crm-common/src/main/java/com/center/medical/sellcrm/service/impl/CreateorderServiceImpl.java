package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.SysBranchDto;
import com.center.medical.bean.enums.NoticeConfigId;
import com.center.medical.bean.model.*;
import com.center.medical.bean.param.AddNotificationParam;
import com.center.medical.bean.vo.CustomerOrderVo;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.Arith;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.*;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.sellcrm.bean.dto.*;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.bean.param.*;
import com.center.medical.sellcrm.bean.vo.*;
import com.center.medical.sellcrm.dao.*;
import com.center.medical.sellcrm.service.*;
import com.center.medical.service.PeispatientChargeMainService;
import com.center.medical.service.PeispatientarchiveService;
import com.center.medical.system.bean.vo.KdzlVo;
import com.center.medical.system.dao.BranchMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.BranchService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.SysNotificationService;
import com.center.medical.workflow.bean.enums.WorkflowType;
import com.center.medical.workflow.bean.model.Workflow;
import com.center.medical.workflow.bean.model.WorkflowCase;
import com.center.medical.workflow.bean.model.WorkflowItem;
import com.center.medical.workflow.service.WorkflowCaseService;
import com.center.medical.workflow.service.WorkflowService;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.rocketmq.shaded.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单表(Createorder)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:41
 */
@Slf4j
@Service("createorderService")
@RequiredArgsConstructor
public class CreateorderServiceImpl extends ServiceImpl<CreateorderMapper, Createorder> implements CreateorderService {

    private final CreateorderMapper createorderMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final OrderandfzxService orderandfzxService;
    private final CreatemealMapper createmealMapper;
    private final OrderandcomboMapper orderandcomboMapper;
    private final Snowflake snowflake;
    private final MealanditemMapper mealanditemMapper;
    private final ComboanditemMapper comboanditemMapper;
    private final CreatecomboMapper createcomboMapper;
    private final BranchMapper branchMapper;
    private final SysUserMapper sysUserMapper;
    private final ISysBranchService iSysBranchService;
    private final OrderandcomboService orderandcomboService;
    private final MapperFacade mapperFacade;
    private final OrdersummaryMapper ordersummaryMapper;
    private final CreateOrderQtxzMapper createOrderQtxzMapper;
    private final HarmMapper harmMapper;
    private final ItemsMapper itemsMapper;
    private final OrderPlanService orderPlanService;
    private final AreaMapper areaMapper;
    private final PeispatientMapper peispatientMapper;
    private final ISysConfigService iSysConfigService;
    private final PeispatientChargeMainService peispatientChargeMainService;
    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final PeispatientarchiveService peispatientarchiveService;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final PeisorgreservationMapper peisorgreservationMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PeispatientChargeMainMapper peispatientChargeMainMapper;
    private final OrderConflictMapper orderConflictMapper;
    private final WorkflowService workflowService;
    private final WorkflowCaseService workflowCaseService;
    private final MealandfzxService mealandfzxService;
    private final BranchService branchService;
    private final SysNotificationService sysNotificationService;

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    @Override

    public IPage<Createorder> getPage(PageParam<Createorder> page, CreateorderParam param) {
        String userNo = SecurityUtils.getUserNo();
        param.setUserCids(branchService.getUserCid(userNo));
        //没有决策管理的看自己的，有决策管理的看全部的
        Boolean greatLeader = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (greatLeader){
            //决策管理看全部
        }else if (SecurityUtils.isLeader()){
            // 领导查看下级的数据
            List<String> lowerLevelIds = sysUserMapper.getLowerLevelId(SecurityUtils.getUserNo());
            lowerLevelIds.add(SecurityUtils.getUserNo());
            param.setLowerLevelIds(lowerLevelIds);
        }else {
            //没权限看自己的
            param.setUserId(SecurityUtils.getUserNo());
            param.setKdzlName(SecurityUtils.getUsername());
        }

        IPage<Createorder> iPage = createorderMapper.getMyPage(page, param);

        List<Createorder> list = iPage.getRecords();
        for (Createorder createorder : list) {
            //拼接分中心的名字
            if (ObjectUtils.isNotEmpty(createorder.getFzxid())) {
                String[] cids = createorder.getFzxid().toString().split(",");
                List<Branch> branches = branchMapper.selectList(new QueryWrapper<Branch>().in("branch_id", cids));
                if (branches.size() > 0) {
                    StringBuilder c = new StringBuilder();
                    for (Branch branch : branches) {
                        c.append(branch.getFzx() + ",");
                    }
                    createorder.setFzx(c.substring(0, c.length() - 1));
                }
            }
            //计算变动成本率
            Double varCostRate = getVarCostRate(createorder.getId());
            createorder.setVarCostRate(varCostRate);
        }
        return iPage;
    }

    /**
     * 根据订单代码查询简单列表数据
     *
     * @param key 订单代码
     */
    @Override
    public List<CreateorderVo> getListByKey(String key) {
        CreateorderParam createorderParam = new CreateorderParam();
        createorderParam.setBranchIds(Arrays.asList(SecurityUtils.getCId()));
        //去空格
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim();
        }
        createorderParam.setDdmc(key);
        if (!SecurityUtils.isLeader()) {
            createorderParam.setXsjlid(SecurityUtils.getUserNo());
        }
        return createorderMapper.getListByKey(createorderParam);
    }

    /**
     * 根据订单名称输入码
     *
     * @param page             分页参数
     * @param createorderParam Createorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CreateorderVo> getList(PageParam<CreateorderVo> page, CreateorderParam createorderParam) {
        return createorderMapper.getList(page, createorderParam);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Createorder getInfoById(String id) {
        return createorderMapper.getInfoById(id);
    }

    /**
     * 新增/编辑操作
     *
     * @param createorder
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean saOrUp(Createorder createorder) {
        //校验一下订单分中心和套餐分中心是否一致
//        String[] fzxIds = createorder.getFzxid().split(",");
//        int fzxCount = fzxIds.length;
//        List<TjtcIdParam> tjtcIds = createorder.getTjtcId();
//        for (TjtcIdParam param : tjtcIds) {
//            int itemsCount = mealandfzxService.countByIdAndFzx(param.getId(),fzxIds);
//            if (itemsCount!=fzxCount){
//                throw new ServiceException("套餐:"+param.getTjtcmc()+"分中心与订单的分中心不匹配!");
//            }
//        }


        //如果体检类型是职业或者综合的，就要校验一下上传云平台的数据
        if (ObjectUtils.isNotEmpty(createorder.getTjlx()) && createorder.getTjlx() > 0){
            checkSellData(createorder);
        }
        Boolean b = false;
        //变更标志
        String fchange = createorder.getFchange();
        if (ObjectUtils.isNotEmpty(fchange) && "1".equals(fchange)) {//变更
            b = change(createorder);
        } else {//编辑
            b = saOrUps(createorder);
        }
        return b;
    }

    /**
     * 检查疾控客户数据是否正确
     * @param createorder
     */
    private void checkSellData(Createorder createorder) {
        String khdwmcid = createorder.getKhdwmcid();
        if (StringUtils.isEmpty(khdwmcid)){
            throw new ServiceException("客户id不能为空!");
        }
        Sellcustomer sellcustomer = sellcustomerMapper.getInfoById(khdwmcid);
        if (ObjectUtils.isEmpty(sellcustomer)){
            throw new ServiceException("客户不存在!");
        }
        //校验职业上传必传数据
        if (StringUtils.isEmpty(sellcustomer.getLicenseName())) {
            throw new ServiceException("创建职业订单时，客户的用人单位名称不能为空！");
        }
        if (StringUtils.isEmpty(sellcustomer.getSocialCreditCode())) {
            throw new ServiceException("创建职业订单时，客户的社会信用代码不能为空！");
        }
        if (StringUtils.isEmpty(sellcustomer.getIndusTypeCode())) {
            throw new ServiceException("创建职业订单时，客户的用人单位行业类别不能为空！");
        }
        if (StringUtils.isEmpty(sellcustomer.getStreet())) {
            throw new ServiceException("创建职业订单时，省/市/区/街道不能为空！");
        }
        if (StringUtils.isEmpty(sellcustomer.getKhdwzcdz())) {
            throw new ServiceException("创建职业订单时，客户的用单位注册地址不能为空！");
        }
        if (StringUtils.isEmpty(sellcustomer.getKhdwlxr())) {
            throw new ServiceException("创建职业订单时，客户的用人单位联系人不能为空！");
        }
        if (StringUtils.isEmpty(sellcustomer.getKhdh())) {
            throw new ServiceException("创建职业订单时，客户的用人单位联系电话错误不能为空！");
        }
        if (StringUtils.isEmpty(sellcustomer.getUnitarea())) {
            throw new ServiceException("创建职业订单时，客户的用人单位所属区名称不能为空！");
        }

        //社会信用代码和用工单位统一社会信用代码不一致时，可能是劳务公司
        if (StringUtils.isNotEmpty(sellcustomer.getRauSocialCreditCode())
                && !sellcustomer.getSocialCreditCode().equals(sellcustomer.getRauSocialCreditCode())) {
            if (StringUtils.isEmpty(sellcustomer.getRauKhdwmc())) {
                throw new ServiceException("创建职业订单时，社会信用代码和用工单位统一社会信用代码不一致时,用工客户单位名称不能为空！");
            }
            if (StringUtils.isEmpty(sellcustomer.getRauStreet())) {
                throw new ServiceException("创建职业订单时，社会信用代码和用工单位统一社会信用代码不一致时,用工单位街道不能为空！");
            }
            if (StringUtils.isEmpty(sellcustomer.getRauEconomyCode())) {
                throw new ServiceException("创建职业订单时，社会信用代码和用工单位统一社会信用代码不一致时,用工单位经济类型不能为空！");
            }
        }

    }


    //编辑
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean saOrUps(Createorder createOrder) {

        checkJinanStatus(createOrder);
        //获取订单id
        String ddId = "";
        //判断id是否为空值
        if (ObjectUtils.isEmpty(createOrder.getId())) {
            //执行保存操作
            //获取订单号(获取序列的数值)
            String ddh = CodeUtil.getOrderNum(iSysBranchService.getBranchFlag(null), "");
            createOrder.setDdh(ddh);
            //订单创建日期
            createOrder.setCjddrq(new Date());
            //订单输入码
            createOrder.setDddm(ToolUtil.getHanziPinyinHeadChar(createOrder.getDdmc()));
            //订单状态,初始为草稿
            createOrder.setSpzt(2);
            createOrder.setIsDelete(0);
            createOrder.setXsjlid(SecurityUtils.getUserNo());
            createOrder.setXsjl(SecurityUtils.getUsername());
            createOrder.setBgzt(5);
            this.save(createOrder);
            ddId = createOrder.getId();
            //操作订单与分中心中间表,添加数据
            String[] fzxId = createOrder.getFzxid().split(",");
            //操作中间表,存储订单关联的分中心
            batchSaveOrderAndFzx(ddId, fzxId);

        } else {
            //执行更新操作
            Createorder createOrder1 = createorderMapper.getInfoById(createOrder.getId());
            Integer spzt = createOrder1.getSpzt();
            if (ObjectUtils.isNotEmpty(spzt) && spzt.intValue() > 2) {
                throw new ServiceException("订单已提交或审核通过，不能修改！");
            }
            if (ObjectUtils.isNotEmpty(createOrder1)) {
                //根据订单id删除分中心关联表
                orderandfzxService.remove(new QueryWrapper<Orderandfzx>().eq("ddid", createOrder1.getId()));
                //更新
                createOrder.setSpzt(2);
                createOrder.setBgzt(5);
                createOrder.setModifydate(new Date());
                this.updateById(createOrder);
                ddId = createOrder1.getId();
                //重新保存订单与分中心的数据
                String[] fzxId = createOrder.getFzxid().split(",");
                batchSaveOrderAndFzx(ddId, fzxId);
            }
        }

        // 批量处理套餐数据
        processCombos(createOrder, ddId);

        return Boolean.TRUE;
    }


    /**
     * 批量处理套餐数据
     */
    private void processCombos(Createorder createOrder, String ddId) {
        List<TjtcIdParam> griddata = createOrder.getTjtcId();
        if (ObjectUtils.isEmpty(griddata)) return;

        // 预检查所有套餐
        preCheckCombos(griddata);

        // 分类处理
        List<Orderandcombo> addedList = new ArrayList<>();
        List<Orderandcombo> modifiedList = new ArrayList<>();
        List<String> deletedIds = new ArrayList<>();
        List<Integer> bdlxList = new ArrayList<>();

        String userNo = SecurityUtils.getUserNo();
        Date now = new Date();

        for (TjtcIdParam map : griddata) {
            String status = map.getState();
            String combostate = map.getCombostate();
            String tcid = map.getId();
            String idExamclassStr = map.getIdExamclass();
            Integer idExamclass = StringUtils.isEmpty(idExamclassStr) ? null : Integer.parseInt(idExamclassStr);

            if ("added".equals(status)) {
                Orderandcombo orderAndCombo = createOrderCombo(ddId, tcid, combostate, idExamclass, userNo, now);
                addedList.add(orderAndCombo);
                addBdlx(bdlxList, idExamclass);

            } else if ("modified".equals(status)) {
                Orderandcombo orderAndCombo = orderandcomboMapper.getInfoById(map.getOacid());
                if (ObjectUtils.isEmpty(orderAndCombo)) {
                    orderAndCombo = createOrderCombo(ddId, tcid, combostate, idExamclass, userNo, now);
                    addedList.add(orderAndCombo);
                } else {
                    updateOrderCombo(orderAndCombo, tcid, combostate, idExamclass, userNo, now);
                    modifiedList.add(orderAndCombo);
                }
                addBdlx(bdlxList, idExamclass);

            } else {
                deletedIds.add(map.getOacid());
            }
        }

        // 批量操作数据库
        if (!addedList.isEmpty()) {
            orderandcomboService.saveBatch(addedList);
        }
        if (!modifiedList.isEmpty()) {
            orderandcomboService.updateBatchById(modifiedList);
        }
        if (!deletedIds.isEmpty()) {
            orderandcomboMapper.deleteBatchIds(deletedIds);
        }

        orderPlanService.saOrUp(createOrder, bdlxList);
    }



    /**
     * 预检查所有套餐
     */
    private void preCheckCombos(List<TjtcIdParam> griddata) {
        Double seasonZk = getSeasonZk();
        if (ObjectUtils.isEmpty(seasonZk)) return;

        // 收集需要检查的套餐ID
        Set<String> needCheckMealIds = new HashSet<>();
        for (TjtcIdParam map : griddata) {
            if (("added".equals(map.getState()) || "modified".equals(map.getState()))
                    && "0".equals(map.getCombostate())) {
                needCheckMealIds.add(map.getId());
            }
        }

        if (!needCheckMealIds.isEmpty()) {
            // 批量查询套餐信息
            List<Createmeal> meals = createmealMapper.selectBatchIds(needCheckMealIds);
            Map<String, Createmeal> mealMap = meals.stream()
                    .collect(Collectors.toMap(Createmeal::getId, meal -> meal));

            for (TjtcIdParam map : griddata) {
                if (("added".equals(map.getState()) || "modified".equals(map.getState()))
                        && "0".equals(map.getCombostate())) {
                    Createmeal meal = mealMap.get(map.getId());
                    if (ObjectUtils.isEmpty(meal)) {
                        throw new ServiceException("保存失败，套餐已被删除！");
                    }
                    if (meal.getForbidden() != null && meal.getForbidden() == 1) {
                        throw new ServiceException("套餐" + meal.getTjtcmc() + "已被禁用，无法加入该订单，请修改");
                    }
                    if (ObjectUtils.isEmpty(meal.getTczk())) {
                        throw new ServiceException("保存失败，" + meal.getTjtcmc() + "套餐折扣为空！");
                    }
                    if (meal.getTczk().doubleValue() < seasonZk.doubleValue()) {
                        throw new ServiceException("套餐" + meal.getTjtcmc() + "折扣低于本季度最低折扣，无法加入该订单，请修改");
                    }
                }
            }
        }
    }

    /**
     * 批量处理变更场景的套餐数据
     */
    private void processChangeCombos(Createorder createOrder, String ddId) {
        List<TjtcIdParam> griddata = createOrder.getTjtcId();
        if (ObjectUtils.isEmpty(griddata)) return;

        // 预检查所有套餐
        preCheckChangeCombos(griddata);

        // 分类处理
        List<Orderandcombo> addedList = new ArrayList<>();
        List<Orderandcombo> modifiedList = new ArrayList<>();
        List<String> deletedIds = new ArrayList<>();

        String userNo = SecurityUtils.getUserNo();
        Date now = new Date();

        // 批量查询需要修改的套餐关联记录
        Set<String> needQueryOacIds = new HashSet<>();
        for (TjtcIdParam map : griddata) {
            if (!"added".equals(map.getState()) && ObjectUtils.isNotEmpty(map.getOacid())) {
                needQueryOacIds.add(map.getOacid());
            }
        }
        Map<String, Orderandcombo> existingComboMap = new HashMap<>();
        if (!needQueryOacIds.isEmpty()) {
            List<Orderandcombo> existingCombos = orderandcomboMapper.selectBatchIds(new ArrayList<>(needQueryOacIds));
            existingComboMap = existingCombos.stream()
                    .collect(Collectors.toMap(Orderandcombo::getId, combo -> combo));
        }

        for (TjtcIdParam map : griddata) {
            String status = map.getState();
            String combostate = map.getCombostate();
            String tcid = map.getId();
            String idExamclassStr = map.getIdExamclass();
            Integer idExamclass = StringUtils.isEmpty(idExamclassStr) ? null : Integer.parseInt(idExamclassStr);

            if ("added".equals(status)) {
                Orderandcombo orderAndCombo = createChangeOrderCombo(ddId, tcid, combostate, idExamclass, userNo, now);
                addedList.add(orderAndCombo);

            } else {
                String oacid = map.getOacid();
                Orderandcombo orderAndCombo = existingComboMap.get(oacid);
                if (ObjectUtils.isEmpty(orderAndCombo)) {
                    throw new ServiceException("变更失败，套餐已被修改，请刷新重试！");
                }

                if ("modified".equals(status)) {
                    updateChangeOrderCombo(orderAndCombo, tcid, combostate, idExamclass, userNo, now);
                    modifiedList.add(orderAndCombo);
                } else {
                    deletedIds.add(oacid);
                }
            }
        }

        // 批量操作数据库
        if (!addedList.isEmpty()) {
            orderandcomboService.saveBatch(addedList);
        }
        if (!modifiedList.isEmpty()) {
            orderandcomboService.updateBatchById(modifiedList);
        }
        if (!deletedIds.isEmpty()) {
            orderandcomboMapper.deleteBatchIds(deletedIds);
        }
    }

    /**
     * 预检查变更场景的所有套餐
     */
    private void preCheckChangeCombos(List<TjtcIdParam> griddata) {
        Double seasonZk = getSeasonZk();

        // 收集需要检查的套餐ID
        Set<String> needCheckMealIds = new HashSet<>();
        for (TjtcIdParam map : griddata) {
            if (("added".equals(map.getState()) || "modified".equals(map.getState()))
                    && "0".equals(map.getCombostate())) {
                needCheckMealIds.add(map.getId());
            }
        }

        if (!needCheckMealIds.isEmpty()) {
            // 批量查询套餐信息
            List<Createmeal> meals = createmealMapper.selectBatchIds(needCheckMealIds);
            Map<String, Createmeal> mealMap = meals.stream()
                    .collect(Collectors.toMap(Createmeal::getId, meal -> meal));

            for (TjtcIdParam map : griddata) {
                if (("added".equals(map.getState()) || "modified".equals(map.getState()))
                        && "0".equals(map.getCombostate())) {
                    Createmeal meal = mealMap.get(map.getId());
                    if (ObjectUtils.isEmpty(meal)) {
                        throw new ServiceException("保存失败，套餐已被删除！");
                    }
                    //2025.11.28 禁用的套餐不提示，可以添加或修改
//                    if (ObjectUtils.isNotEmpty(meal.getForbidden()) && meal.getForbidden() == 1) {
//                        throw new ServiceException("套餐" + meal.getTjtcmc() + "已被禁用，无法加入该订单，请修改");
//                    }
                    if ("added".equals(map.getState())) {
                        if (ObjectUtils.isNotEmpty(seasonZk) && ObjectUtils.isNotEmpty(meal.getTczk())) {
                            if (meal.getTczk().doubleValue() < seasonZk.doubleValue()) {
                                throw new ServiceException("套餐" + meal.getTjtcmc() + "折扣低于本季度最低折扣，无法加入该订单，请修改");
                            }
                        }
                    } else if ("modified".equals(map.getState())) {
                        if (ObjectUtils.isNotEmpty(seasonZk) && ObjectUtils.isNotEmpty(meal.getTczk())) {
                            if (meal.getTczk().doubleValue() < seasonZk.doubleValue()) {
                                throw new ServiceException("套餐" + meal.getTjtcmc() + "折扣低于本季度最低折扣，无法加入该订单，请修改");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 创建变更场景的订单套餐对象
     */
    private Orderandcombo createChangeOrderCombo(String ddId, String tcid, String combostate,
                                                 Integer idExamclass, String userNo, Date now) {
        Orderandcombo orderAndCombo = new Orderandcombo();
        orderAndCombo.setDdid(ddId);
        orderAndCombo.setTcid(tcid);
        orderAndCombo.setXsjlid(userNo);
        orderAndCombo.setCombostate(combostate);
        orderAndCombo.setSpzt(0);
        orderAndCombo.setIsbg(1); // 变更场景：本次变更
        orderAndCombo.setIdExamclass(idExamclass);
        orderAndCombo.setId(String.valueOf(snowflake.nextId()));
        orderAndCombo.setCreatedate(now);
        return orderAndCombo;
    }

    /**
     * 更新变更场景的订单套餐对象
     */
    private void updateChangeOrderCombo(Orderandcombo orderAndCombo, String tcid, String combostate,
                                        Integer idExamclass, String userNo, Date now) {
        orderAndCombo.setIdExamclass(idExamclass);
        // 如果已审批，则不变更
        if (ObjectUtils.isNotEmpty(orderAndCombo.getSpzt()) && orderAndCombo.getSpzt().intValue() == 1) {
            orderAndCombo.setIsbg(0);
        } else {
            orderAndCombo.setTcid(tcid);
            orderAndCombo.setXsjlid(userNo);
            orderAndCombo.setCombostate(combostate);
            orderAndCombo.setIsbg(1); // 本次变更
        }
        orderAndCombo.setModifydate(now);
    }


    /**
     * 创建订单套餐对象
     */
    private Orderandcombo createOrderCombo(String ddId, String tcid, String combostate,
                                           Integer idExamclass, String userNo, Date now) {
        Orderandcombo orderAndCombo = new Orderandcombo();
        orderAndCombo.setDdid(ddId);
        orderAndCombo.setTcid(tcid);
        orderAndCombo.setXsjlid(userNo);
        orderAndCombo.setCombostate(combostate);
        orderAndCombo.setSpzt(0);
        orderAndCombo.setIsbg(0);
        orderAndCombo.setCreatedate(now);
        orderAndCombo.setIdExamclass(idExamclass);
        orderAndCombo.setId(String.valueOf(snowflake.nextId()));
        return orderAndCombo;
    }

    /**
     * 更新订单套餐对象
     */
    private void updateOrderCombo(Orderandcombo orderAndCombo, String tcid, String combostate,
                                  Integer idExamclass, String userNo, Date now) {
        orderAndCombo.setTcid(tcid);
        orderAndCombo.setXsjlid(userNo);
        orderAndCombo.setCombostate(combostate);
        orderAndCombo.setSpzt(0);
        orderAndCombo.setIsbg(0);
        orderAndCombo.setIdExamclass(idExamclass);
        orderAndCombo.setModifydate(now);
    }

    /**
     * 添加报表类型
     */
    private void addBdlx(List<Integer> bdlxList, Integer idExamclass) {
        if (idExamclass != null && !bdlxList.contains(idExamclass)) {
            bdlxList.add(idExamclass);
        }
    }




    /**
     * 批量保存订单与分中心关联
     */
    private void batchSaveOrderAndFzx(String ddId, String[] fzxIds) {
        if (ObjectUtils.isEmpty(fzxIds)) return;

        List<Orderandfzx> orderAndFzxList = new ArrayList<>();
        Date now = new Date();
        for (String fzxId : fzxIds) {
            Orderandfzx orderAndFzx = new Orderandfzx();
            orderAndFzx.setDdid(ddId);
            orderAndFzx.setFzxid(fzxId);
            orderAndFzx.setTbzt(0);
            orderAndFzx.setCreatedate(now);
            orderAndFzx.setId(String.valueOf(snowflake.nextId()));
            orderAndFzxList.add(orderAndFzx);
        }

        // 批量插入
        orderandfzxService.saveBatch(orderAndFzxList);
    }

//    public static void main(String[] args) {
//        System.out.println(DateUtil.year(new Date()));
//    }

    /**
     * 应国家卫健委要求在创建职业/综合订单时，必须选择信息完善的单位，否则不能创建订单
     *
     * @param createOrder
     */
    @Transactional
    public void checkJinanStatus(Createorder createOrder) {
        Integer tjlx = createOrder.getTjlx();
        if (ObjectUtils.isNotEmpty(tjlx) && (tjlx.intValue() == 1 || tjlx.intValue() == 2)) {
            String khdwid = createOrder.getKhdwmcid();
            if (StringUtils.isNotEmpty(khdwid)) {
                Sellcustomer sellCustomer = sellcustomerMapper.getInfoById(khdwid);
                if (sellCustomer != null) {
                    Integer jinanStatus = sellCustomer.getJinanStatus();
                    if (jinanStatus == null || (jinanStatus.intValue() != 0 && jinanStatus.intValue() != 1)) {
                        throw new ServiceException("若要创建职业/综合订单，请先到【客户管理】页面完善客户信息。客户当前上传状态：" + getJinanStatus(jinanStatus));
                    }
                }
            }
        }
    }

    //根据数字判断上传状态
    public static String getJinanStatus(Object obj) {
        if (obj == null) {
            return null;
        }
        switch (obj.toString()) {
            case "-1":
                return "未录入必填字段";
            case "0":
                return "待上传";
            case "1":
                return "已上传";
            case "2":
                return "上传失败";
            default:
                return null;
        }
    }


    /**
     * 变更
     *
     * @param createOrder
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean change(Createorder createOrder) {
        //获取订单id
        String ddId = "";
        //执行更新操作
        Createorder createOrder1 = createorderMapper.getInfoById(createOrder.getId());
        if (ObjectUtils.isEmpty(createOrder1)) {
            throw new ServiceException("变更失败，订单已不存在!");
        }

        //获取审批状态：0.审核未通过  1.已撤回 2.草稿 3.已提交 4.审核通过
        Integer spzt = createOrder1.getSpzt();
        if (ObjectUtils.isEmpty(spzt) || spzt.intValue() != 4) {
            throw new ServiceException("订单未审核通过，不能变更！");
        }

        //变更状态，订单变更状态：0.审核未通过  2.已变更 3.变更已提交 4.变更审核通过 5.未变更
        Integer bgzt = createOrder1.getBgzt();
        if (ObjectUtils.isNotEmpty(bgzt) && bgzt.intValue() == 3) {
            throw new ServiceException("变更失败，订单已提交变更，不能修改!");
        }
        ddId = createOrder1.getId();
        //根据订单id和分中心id拼串确定套餐与分中心关联表中的数据
        orderandfzxService.remove(new QueryWrapper<Orderandfzx>().eq("ddid", ddId));

        //更新
        createOrder.setSpzt(4);
        createOrder.setBgzt(2);
        createOrder.setModifydate(new Date());
        this.updateById(createOrder);
        //重新保存订单与分中心的数据
        String[] fzxOrderId = createOrder.getFzxid().split(",");
        batchSaveOrderAndFzx(ddId, fzxOrderId);

        // 批量处理套餐数据
        processChangeCombos(createOrder, ddId);

        //备单那取的是体检者任务中的前台须知，把这个表也更新下
        Peisorgreservation peisorgreservation = peisorgreservationMapper.selectOne(new QueryWrapper<Peisorgreservation>().eq("ddh", createOrder.getId()));
        if (ObjectUtils.isNotEmpty(peisorgreservation)){
            peisorgreservation.setQtxz(createOrder.getQtxz());
            peisorgreservationMapper.updateById(peisorgreservation);
        }
        return Boolean.TRUE;
    }


    /**
     * 获取当前季度最低折扣
     * 来判断套餐的折扣如果在规定时间内低于相应折扣，则该套餐不允许加入到订单中
     *
     * @return
     */
    @Override
    public Double getSeasonZk() {
        Double discount = null;
        try {
            Calendar now = Calendar.getInstance();
            int month = now.get(Calendar.MONTH) + 1;
            Map<Object, Object> conf = new HashMap<>();
            conf.put("1-3", 0);
            conf.put("4-6", 0);
            conf.put("7-9", 0);
            conf.put("10-12", 0);
            for (Map.Entry<Object, Object> entry : conf.entrySet()) {
                String[] range = entry.getKey().toString().split("-");
                int start = Integer.parseInt(range[0]);
                int end = Integer.parseInt(range[1]);
                if ((month >= start && month <= end) || (start >= end && (month >= start || month <= end))) {
                    discount = Double.parseDouble(entry.getValue().toString());
                    break;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return discount;
    }

    /**
     * 订单里面为套餐增加收费项目
     *
     * @param addItemsParam
     * @return
     */
    @Override
    public Boolean addItems(AddItemsParam addItemsParam) {
        List<String> itemIds = addItemsParam.getItemIds();
        if ("0".equals(addItemsParam.getCombostate())) {
            //为普通套餐增加收费项目
            for (int i = 0; i < itemIds.size(); i++) {
                Mealanditem mealAndItem = new Mealanditem();
                mealAndItem.setTcid(addItemsParam.getTcid());
                mealAndItem.setSfxmid(itemIds.get(i));
                mealAndItem.setSfbx(0);
                mealAndItem.setCreatedate(new Date());
                mealanditemMapper.insert(mealAndItem);
            }
        } else {
            //为最小套餐增加收费项目
            for (int i = 0; i < itemIds.size(); i++) {
                Comboanditem comboAndItem = new Comboanditem();
                comboAndItem.setTcid(addItemsParam.getTcid());
                comboAndItem.setSfxmid(itemIds.get(i));
                comboAndItem.setIsDelete(0);
                comboAndItem.setXsjlid(SecurityUtils.getUserNo());
                comboAndItem.setCreatedate(new Date());
                comboanditemMapper.insert(comboAndItem);
            }

        }
        return Boolean.TRUE;
    }


    /**
     * 根据套餐id删除关联的收费项目
     *
     * @param tcrowId
     * @param sfxmId
     * @return
     */
    @Override
    public Boolean removeItemsData(String tcrowId, List<String> sfxmId) {
        //直接删除
        int i = mealanditemMapper.delete(new QueryWrapper<Mealanditem>().eq("tcid", tcrowId)
                .in("sfxmid", sfxmId));
        return i > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 提交订单
     *
     * @param ddcomId
     * @param flag    是否需要进行撞单排查：true需要、false不需要
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public R commit(List<String> ddcomId, Boolean flag , List<String> approverIds) {
        log.info("提交订单参数：ddcomId{},approverIds:{}",ddcomId,approverIds);
        for (String id : ddcomId) {
            Createorder createOrder = createorderMapper.getInfoById(id);

            if (flag) {
                //判断是否撞单，判断依据：订单名称、客户ID、客户名称、体检类型、备单类型
                R<String> conflictResult = orderPlanService.doConflict(createOrder.getDdh(), createOrder.getDdmc(),createOrder.getXsjlid());
                if (R.isError(conflictResult)) {
                    return conflictResult;
                }
            }

            //修改订单的状态
            Date submitTime = new Date();
            if (ObjectUtils.isNotEmpty(createOrder)) {
                //修改订单状态为提交
                createOrder.setSpzt(3);
                createOrder.setSubmitTime(submitTime);
                createOrder.setModifydate(new Date());
                this.updateById(createOrder);

                //判断是否开启订单审核流
                Workflow workflow = null;
                //用销售的默认分中心，找到了就走审批流，没找到就提示
                if (createOrder.getTjlx() == 0){
                    workflow = workflowService.isOpen(SecurityUtils.getCId(), WorkflowType.ORDER_FLOW.getCode());
                }else {
                    workflow = workflowService.isOpen(SecurityUtils.getCId(), WorkflowType.ORDER_FLOW_OCCUPATION.getCode());
                }


                if (Objects.nonNull(workflow)) {
                    //走订单审核流
                    WorkflowCase workflowCase = new WorkflowCase();
                    workflowCase.setCaseName(createOrder.getDdmc() + "订单审核");
                    workflowCase.setBizId(createOrder.getId());
                    workflowCase.setFlowId(workflow.getId());
                    workflowCase.setFzxid(workflow.getFzxid());
                    workflowCase.setRemark("");
                    workflowCase.setTypeFlag(workflow.getTypeFlag());
                    workflowCase.setApproverIds(approverIds);
                    workflowCaseService.saOrUp(workflowCase);
                } else {
                    throw new ServiceException("尚未建立审批流，请联系运维人员创建后再提交审批!");
                }
            }
        }
        return R.ok("提交成功！");
    }


    /**
     * @param phoneNum
     * @param code
     * @throws Exception void
     * @Title: sendMsg
     * @author mbx
     * @since 2016年9月29日 V 1.0
     */
    @SuppressWarnings("unused")
    public void sendMsg(String phoneNum, String[] code) throws Exception {
        HashMap<String, Object> result = null;
        //初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

        //******************************注释*********************************************
        //*初始化服务器地址和端口                                                       *
        //*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
        //*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
        //*******************************************************************************
        restAPI.init("app.cloopen.com", "8883");

        //******************************注释*********************************************
        //*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
        //*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
        //*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
        //*******************************************************************************
//        restAPI.setAccount("8a48b5515388ec1501539c68d0d51c7c", "abe719756b4b413f86ec9d3a4b776547");
        restAPI.setAccount("8aaf07085a608ec2015a6a597d9b0520", "f9aabc7ee53a4dc9a6a70ecc1952fe54");

        //******************************注释*********************************************
        //*初始化应用ID                                                                 *
        //*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
        //*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
        //*******************************************************************************
//        restAPI.setAppId("8aaf0708575066050157509c4a2d00f6");
        restAPI.setAppId("8aaf07085a6ec238015a890f83be0e1e");

        //**************************************举例说明***********************************************************************
        //*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
        //*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});                                                                         *
        //*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
        //*********************************************************************************************************************
        result = restAPI.sendTemplateSMS(phoneNum, "157004", code);
    }

    /**
     * 变更审核
     *
     * @param checkOrderParam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean checkChange(CheckOrderParam checkOrderParam) {
        //根据订单id获取订单
        Createorder createOrder = createorderMapper.getInfoById(checkOrderParam.getOrderCheckId());

        if (ObjectUtils.isEmpty(createOrder)) {
            throw new ServiceException("审核失败，订单已不存在!");
        }
        if (ObjectUtils.isNotEmpty(createOrder.getBgzt()) && createOrder.getBgzt().intValue() != 3) {
            throw new ServiceException("订单变更状态不是已提交，请确认后重试！");
        }
        //改变订单状态
        if ("0".equals(checkOrderParam.getSpjgValue())) {
            Integer tjlx = createOrder.getTjlx();
            if (ObjectUtils.isNotEmpty(tjlx) && (tjlx == 1 || tjlx == 2)) {
                Integer clspzt = createOrder.getClspzt();
                if (ObjectUtils.isEmpty(clspzt) || clspzt != 1) {
                    throw new ServiceException("该订单未通过材料审核，不能审核");
                }
            }
            //审批通过
            createOrder.setBgzt(4);
            createOrder.setModifydate(new Date());
            this.updateById(createOrder);
            //根据订单id获取关联的套餐,将编辑状态置为不可编辑
            List<Orderandcombo> orderAndComboData = orderandcomboMapper.selectList(new QueryWrapper<Orderandcombo>().eq("ddid", createOrder.getId())
                    .eq("isbg", 1));
            for (Orderandcombo orderAndCombo : orderAndComboData) {
                if ("0".equals(orderAndCombo.getCombostate())) {
                    //普通套餐
                    Createmeal createMeal = createmealMapper.selectOne(new QueryWrapper<Createmeal>().eq("id", orderAndCombo.getTcid()).eq("is_delete", 0));
                    if (ObjectUtils.isNotEmpty(createMeal)) {
                        createMeal.setBjzt(1);
                        createMeal.setModifydate(new Date());
                        createmealMapper.updateById(createMeal);
                    }
                } else {
                    //最小套餐
                    Createcombo createCombo = createcomboMapper.selectOne(new QueryWrapper<Createcombo>().eq("id", orderAndCombo.getTcid()).eq("is_delete", 0));
                    if (ObjectUtils.isNotEmpty(createCombo)) {
                        createCombo.setBjzt(1);
                        createCombo.setCreatedate(new Date());
                        createcomboMapper.updateById(createCombo);
                    }
                }
                orderAndCombo.setSpzt(1);
                orderAndCombo.setModifydate(new Date());
                orderandcomboMapper.updateById(orderAndCombo);
            }
            //修改订单与分中心关联表的同步状态为2
            List<Orderandfzx> orderAndFzxData = orderandfzxService.list(new QueryWrapper<Orderandfzx>().eq("ddid", createOrder.getId()));
            if (ObjectUtils.isNotEmpty(orderAndFzxData.size())) {
                //修改同步状态为2
                for (Orderandfzx orderAndFzx : orderAndFzxData) {
                    orderAndFzx.setTbzt(2);
                    orderAndFzx.setModifydate(new Date());
                    orderandfzxService.updateById(orderAndFzx);
                }
            }
        } else {
            //审批未通过
            createOrder.setBgzt(0);
            createOrder.setModifydate(new Date());
            //数据更新
            this.updateById(createOrder);
        }

        createOrder.setSpyj(checkOrderParam.getSpyjValue());
        createOrder.setModifydate(new Date());
        createorderMapper.updateById(createOrder);

        //添加消息通知
        AddNotificationParam param = new AddNotificationParam(createOrder.getXsjlid(),
                "0".equals(checkOrderParam.getSpjgValue()) ? NoticeConfigId.ORDER_APPROVED.value() : NoticeConfigId.ORDER_REJECTION.value()
                ,createOrder.getDdh());
        sysNotificationService.addNotice(param);

        //审批流失效
        WorkflowCase workflowCase = new   WorkflowCase();
        workflowCase.setStatus(-1);
        workflowCase.setFailText("订单变更审批通过，审批流失效");
        workflowCaseService.update(workflowCase,new LambdaQueryWrapper<WorkflowCase>()
                .eq(WorkflowCase::getBizId,createOrder.getId())
                .eq(WorkflowCase::getTypeFlag,createOrder.getTjlx() == 0 ? WorkflowType.ORDER_FLOW_CHANGE.getCode() :  WorkflowType.ORDER_FLOW_CHANGE_OCCUPATIONAL.getCode())
                .eq(WorkflowCase::getStatus,1));

        return Boolean.TRUE;
    }


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean checkOrder(CheckOrderParam checkOrderParam) {
        //根据订单id获取订单
        Createorder createOrder = createorderMapper.getInfoById(checkOrderParam.getOrderCheckId());
        if (ObjectUtils.isNotEmpty(createOrder)) {
            //改变订单状态
            if ("0".equals(checkOrderParam.getSpjgValue())) {
                Integer tjlx = createOrder.getTjlx();
                //体检类型
                if (ObjectUtils.isNotEmpty(tjlx) && (tjlx == 1 || tjlx == 2)) {
                    //材料审批状态：null未审核 1.通过 2.驳回
                    Integer clspzt = createOrder.getClspzt();
                    if (ObjectUtils.isEmpty(clspzt) || clspzt != 1) {
                        throw new ServiceException("该订单未通过材料审核，不能审核");
                    }
                }
                //审批通过
                createOrder.setSpzt(4);
                createOrder.setModifydate(new Date());
                createOrder.setSpr(SecurityUtils.getUsername());

                //根据订单id获取关联的套餐,将编辑状态置为不可编辑
                List<Orderandcombo> orderAndComboData = orderandcomboMapper.selectList(new QueryWrapper<Orderandcombo>().eq("ddid", createOrder.getId()));
                if (ObjectUtils.isNotEmpty(orderAndComboData)) {
                    List<String> commonMealIds = new ArrayList<>();
                    List<String> comboIds = new ArrayList<>();
                    List<String> orderComboIds = new ArrayList<>();

                    //获取id方便后续更新
                    for (Orderandcombo orderAndCombo : orderAndComboData) {
                        if ("0".equals(orderAndCombo.getCombostate())) {
                            commonMealIds.add(orderAndCombo.getTcid());
                        } else {
                            comboIds.add(orderAndCombo.getTcid());
                        }
                        orderComboIds.add(orderAndCombo.getId());
                    }

                    // 更新
                    Date now = new Date();
                    updateBatchCreatemeal(commonMealIds, now);
                    updateBatchCreatecombo(comboIds, now);
                    updateBatchOrderandcombo(orderComboIds, now);
                }
                //修改订单与分中心关联表的同步状态为2
                Orderandfzx orderAndFzx = new Orderandfzx();
                orderAndFzx.setTbzt(2);
                orderAndFzx.setModifydate(new Date());
                orderandfzxService.update(orderAndFzx, new QueryWrapper<Orderandfzx>()
                                .eq("ddid", createOrder.getId()));
            } else {
                //审批未通过
                createOrder.setSpzt(0);
                createOrder.setModifydate(new Date());
                createOrder.setSpr(SecurityUtils.getUsername());
            }

            createOrder.setSpyj(checkOrderParam.getSpyjValue());
            createOrder.setModifydate(new Date());
            createorderMapper.updateById(createOrder);
        }
        //添加消息通知
        AddNotificationParam param = new AddNotificationParam(createOrder.getXsjlid(),
                "0".equals(checkOrderParam.getSpjgValue()) ? NoticeConfigId.ORDER_APPROVED.value() : NoticeConfigId.ORDER_REJECTION.value()
                ,createOrder.getDdh());
        sysNotificationService.addNotice(param);
        //审批流失效
        WorkflowCase workflowCase = new   WorkflowCase();
        workflowCase.setStatus(-1);
        workflowCase.setFailText("订单审批通过，审批流失效");
        workflowCaseService.update(workflowCase,new LambdaQueryWrapper<WorkflowCase>()
                .eq(WorkflowCase::getBizId,createOrder.getId())
                .eq(WorkflowCase::getTypeFlag,createOrder.getTjlx() == 0 ? WorkflowType.ORDER_FLOW.getCode() :  WorkflowType.ORDER_FLOW_OCCUPATION.getCode())
                .in(WorkflowCase::getStatus,0,1));

        return Boolean.TRUE;
    }



    private void updateBatchCreatemeal(List<String> ids, Date now) {
        if (ObjectUtils.isEmpty(ids)) return;

        Createmeal updateMeal = new Createmeal();
        updateMeal.setBjzt(1);
        updateMeal.setModifydate(now);

        createmealMapper.update(updateMeal,
                new QueryWrapper<Createmeal>()
                        .in("id", ids)
                        .eq("is_delete", 0));
    }

    private void updateBatchCreatecombo(List<String> ids, Date now) {
        if (ObjectUtils.isEmpty(ids)) return;

        Createcombo updateCombo = new Createcombo();
        updateCombo.setBjzt(1);
        updateCombo.setModifydate(now);

        createcomboMapper.update(updateCombo,
                new QueryWrapper<Createcombo>()
                        .in("id", ids)
                        .eq("is_delete", 0));
    }

    private void updateBatchOrderandcombo(List<String> ids, Date now) {
        if (ObjectUtils.isEmpty(ids)) return;

        Orderandcombo updateOrderCombo = new Orderandcombo();
        updateOrderCombo.setSpzt(1);
        updateOrderCombo.setIsbg(0);
        updateOrderCombo.setModifydate(now);

        orderandcomboMapper.update(updateOrderCombo,
                new QueryWrapper<Orderandcombo>().in("id", ids));
    }






    /**
     * 判断要编辑的订单是否为"提交"或"审核通过"状态
     *
     * @param isTjOrShtgId
     * @return
     */
    @Override
    public Boolean isTjOrShtg(String isTjOrShtgId) {
        Boolean b = true;
        //获取实体
        Createorder createOrder = createorderMapper.getInfoById(isTjOrShtgId);
        if (ObjectUtils.isNotEmpty(createOrder)) {
            //判断要编辑的订单是否为"提交"或"审核通过"状态
            if (3 == createOrder.getSpzt() || 4 == createOrder.getSpzt()) {
                b = false;
            }
        }
        return b;
    }


    /**
     * 判断对于已提交与审核通过的订单不能删除
     *
     * @param isRemoveId
     * @return
     */
    @Override
    public String isRemove(List<String> isRemoveId) {
        String text = "";
        for (String s : isRemoveId) {
            Createorder createOrder = createorderMapper.getInfoById(s);
            if (ObjectUtils.isNotEmpty(createOrder)) {
                if (createOrder.getSpzt() == 3 || createOrder.getSpzt() == 4) {
                    String spzt = createOrder.getSpzt() == 3 ? "已提交" : "审核通过";
                    text += "<font color='red'>★</font>订单号【<font color='red'><b>" + createOrder.getDdh() + "</b></font>】的订单,状态为【<font color='red'><b>" + spzt + "</b></font>】,不能进行删除！请重新勾选！<br/>";
                }
            }
        }
        return text;
    }

    /**
     * 判断选择的记录是否是【提交】和【审核通过】的,这两种状态不能再提交
     *
     * @param isCommitAndSptgId
     * @return
     */
    @Override
    public String isCommitAndSptg(List<String> isCommitAndSptgId) {
        String text = "";
        for (String s : isCommitAndSptgId) {
            Createorder createOrder = createorderMapper.getInfoById(s);
            if (ObjectUtils.isNotEmpty(createOrder)) {
                if (createOrder.getSpzt() == 4 || createOrder.getSpzt() == 3) {
                    String spzt = createOrder.getSpzt() == 3 ? "已提交" : "审核通过";
                    text += "<font color='red'>★</font>订单号为【<font color='red'><b>" + createOrder.getDdh() + "</b></font>】的【<font color='red'><b>" + createOrder.getDdmc() + "</b></font>】订单是【<font color='red'><b>" + spzt + "</b></font>】状态,不能再提交,请重新选择！<br/>";
                }
            }
        }
        return text;
    }


    /**
     * 判断对于订单状态为：草稿、已撤回、审核通过、审核未通过--状态的订单不能再进行撤回
     *
     * @param isChId
     * @return
     */
    @Override
    public String isCh(List<String> isChId) {
        String text = "";
        for (String s : isChId) {
            Createorder createOrder = createorderMapper.getInfoById(s);
            if (ObjectUtils.isNotEmpty(createOrder)) {
                if (createOrder.getSpzt() == 4 || createOrder.getSpzt() == 2 || createOrder.getSpzt() == 1 || createOrder.getSpzt() == 0) {
                    String spzt = "";
                    if (4 == createOrder.getSpzt()) {
                        spzt = "审核通过";
                    } else if (2 == createOrder.getSpzt()) {
                        spzt = "草稿";
                    } else if (1 == createOrder.getSpzt()) {
                        spzt = "已撤回";
                    } else if (0 == createOrder.getSpzt()) {
                        spzt = "审核未通过";
                    }
                    text += "<font color='red'>★</font>订单号为【<font color='red'><b>" + createOrder.getDdh() + "</b></font>】的订单【<font color='red'><b>" + createOrder.getDdmc() + "</b></font>】当前状态为【<font color='red'><b>" + spzt + "</b></font>】,不能进行撤回！<br/>";
                }
            }
        }
        return text;
    }

    /**
     * 根据订单号订单名称获取订单下拉
     *
     * @param page
     * @param ddh
     * @param ddmc
     * @return
     */
    @Override
    public IPage<Createorder> getDdhData(PageParam<Createorder> page, String ddh, String ddmc) {
        return createorderMapper.getDdhData(page, ddh, ddmc);
    }

    /**
     * 根据客户单位名称ID获取订单
     *
     * @param groupId
     * @return
     */
    @Override
    public List<Createorder> getDateByKhdwmcid(String groupId) {
        return createorderMapper.getDateByKhdwmcid(groupId);
    }

    /**
     * 根据key模糊查询获取所有订单数据
     *
     * @param key
     * @return
     */
    @Override
    public List<AllOrderDataVo> getAllOrderData(String key) {
        //去空格
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim();
        }
        return createorderMapper.getAllOrderData(key);
    }

    /**
     * 获取开单助理
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<KdzlVo> getKdzl(PageParam<KdzlVo> page, String key) {
        //去空格大写
        if (ObjectUtils.isNotEmpty(key)) {
            key.trim().toUpperCase();
        }
        return sysUserMapper.getKdzl(page, key);
    }

    /**
     * 获取检查数据
     *
     * @param param
     * @return
     */
    @Override
    public List<CheckDateVo> checkDate(CheckDateParam param) {
        return createorderMapper.checkDate(param);
    }

    /**
     * 隐藏展示操作
     *
     * @param paused
     * @param ids
     * @param ddId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean showOrHide(Integer paused, List<String> ids, String ddId) {
        /**展示隐藏**/
        //订单与套餐关联表
        List<Orderandcombo> orderandcombos = orderandcomboMapper.selectList(new QueryWrapper<Orderandcombo>().eq("ddid", ddId));
        //订单与分中心关联表
        List<Orderandfzx> orderandfzxs = orderandfzxService.list(new QueryWrapper<Orderandfzx>().eq("ddid", ddId));
        for (Orderandcombo orderandcombo : orderandcombos) {
            //套餐ID
            if (orderandcombo.getTcid().equals(ids.get(0))) {
                //是否显示 0 展示 1 隐藏
                orderandcombo.setShow(paused);
                orderandcomboService.updateById(orderandcombo);
                if (orderandfzxs.size() != 0) {
                    for (Orderandfzx orderandfzx : orderandfzxs) {
                        //隐藏展示下载状态
                        orderandfzx.setYcxzzt(0);
                        orderandfzxService.updateById(orderandfzx);
                    }
                }
            }

        }
        return Boolean.TRUE;
    }

    /**
     * 订单撤回
     *
     * @param ddid 订单ID列表
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean undo(List<String> ddid) {
        Createorder createorder = new Createorder();
        //修改订单状态为撤回状态
        createorder.setSpzt(1);
        createorder.setModifydate(new Date());
        int update = createorderMapper.update(createorder, new UpdateWrapper<Createorder>().eq("is_delete", 0)
                .in("id", ddid));
        //更新审批流状态为失效状态（-1）
        for (String id : ddid) {
            Createorder createorder1 = createorderMapper.getInfoById(id);
            if (createorder1.getTjlx() == 0){
                workflowCaseService.loseByBizId(ddid, WorkflowType.ORDER_FLOW.getCode(), -1, "订单手动撤回");
            }else {
                workflowCaseService.loseByBizId(ddid, WorkflowType.ORDER_FLOW_OCCUPATION.getCode(), -1, "订单手动撤回");
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 订单反审核
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean undoOrder(String id) {
        String state = "success";
        //获取实体
        Createorder createOrder = createorderMapper.getInfoById(id);
        if (ObjectUtils.isNotEmpty(createOrder)) {
            //审批状态
            if (createOrder.getSpzt() != 4) {
                throw new ServiceException("订单未审核，无需反审核!");
            } else if (createOrder.getBgzt().intValue() == 3) {
                throw new ServiceException("订单变更已提交，不能反审核!");
            } else {
//                @SuppressWarnings("rawtypes")
                //订单与分中心关联表
                List<Orderandfzx> list = orderandfzxService.list(new QueryWrapper<Orderandfzx>()
                        .eq("ddid", id).eq("tbzt", 1));
                if (list.size() > 0) {
                    throw new ServiceException("订单已同步，反审核失败!");
                } else {
                    createOrder.setSpzt(2);
                    createOrder.setBgzt(5);
                    //更新订单状态
                    this.updateById(createOrder);
                    //订单与套餐关联表
                    List<Orderandcombo> oacs = orderandcomboMapper.selectList(new QueryWrapper<Orderandcombo>().eq("ddid", id));
                    for (Orderandcombo oac : oacs) {
                        //是否变更
                        oac.setIsbg(0);
                        //审批状态
                        oac.setSpzt(0);
                    }
                    //更新订单与套餐关联记录
                    orderandcomboService.updateBatchById(oacs);
                    //更新审批流状态为失效状态（-1）
                    workflowCaseService.loseByBizId(Arrays.asList(createOrder.getId()), WorkflowType.ORDER_FLOW.getCode(), -1, "订单反审核");
                }
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 总结-保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUpSummary(SaOrUpSumParam param) {
        //将json序列化为对象
        Ordersummary orderSummary = mapperFacade.map(param, Ordersummary.class);
        if (ObjectUtils.isNotEmpty(orderSummary)) {
            if (StringUtils.isEmpty(orderSummary.getId())) {
                //数据保存
                Createorder createOrder = createorderMapper.getInfoById(orderSummary.getDdid());
                if (null != createOrder) {
                    //单位名称
                    orderSummary.setDdmc(createOrder.getDdmc());
                    //销售经理id
                    orderSummary.setXsjlid(SecurityUtils.getUserNo());
                    //分中心id
                    orderSummary.setFzxid(SecurityUtils.getCId());
                    //假删状态
                    orderSummary.setIsDelete(0);
                    //数据保存
                    ordersummaryMapper.insert(orderSummary);
                }
            } else {
                //数据编辑
                Ordersummary orderSu = ordersummaryMapper.getInfoById(orderSummary.getId());
                if (ObjectUtils.isNotEmpty(orderSu)) {
                    //订单名称
                    String ddmc = createorderMapper.getInfoById(orderSummary.getDdid()).getDdmc();
                    orderSummary.setDdmc(ddmc);
                    orderSummary.setModifydate(new Date());
                    ordersummaryMapper.updateById(orderSu);
                } else {
                    throw new ServiceException("该id不存在");
                }
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 变更提交
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean commitChange(List<String> ids,List<String> approverIds) {
        for (int i = 0; i < ids.size(); i++) {
            log.info("变更提交id:"+ids.get(i));
            //订单表
            Createorder createOrder = createorderMapper.getInfoById(ids.get(i));
            if (ObjectUtils.isNotEmpty(createOrder)) {
                log.info("变更提交已查询到id:"+ids.get(i));
                //变更状态
                if (createOrder.getBgzt() != null && createOrder.getBgzt().intValue() != 2) {
                    throw new ServiceException("error@请选择<font color='red'>已变更</font>的数据！");
                }
                //变更状态，订单变更状态：0.审核未通过  2.已变更 3.变更已提交 4.变更审核通过 5.未变更 null未变更
                createOrder.setBgzt(3);
                //更新
                this.updateById(createOrder);
                //判断是否开启订单变更审核流
                //用销售的默认分中心，找到了就走审批流,没找到就提示
                Workflow workflow = workflowService.isOpen(SecurityUtils.getCId(),
                            createOrder.getTjlx() == 0 ? WorkflowType.ORDER_FLOW_CHANGE.getCode() : WorkflowType.ORDER_FLOW_CHANGE_OCCUPATIONAL.getCode());
                if (Objects.nonNull(workflow)) {
                    //走订单审核流
                    WorkflowCase workflowCase = new WorkflowCase();
                    workflowCase.setCaseName(createOrder.getDdmc() + "订单变更审核");
                    workflowCase.setBizId(createOrder.getId());
                    workflowCase.setFlowId(workflow.getId());
                    workflowCase.setFzxid(workflow.getFzxid());
                    workflowCase.setRemark("");
                    workflowCase.setTypeFlag(workflow.getTypeFlag());
                    workflowCase.setApproverIds(approverIds);
                    workflowCaseService.saOrUp(workflowCase);
                } else {
                    throw new ServiceException("该分中心未开启改审批流，请先开启！");
                }
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 变更撤回
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean undoChange(List<String> ids) {
        //根据订单id依次进行撤回
        for (int i = 0; i < ids.size(); i++) {
            Createorder createOrder = createorderMapper.getInfoById(ids.get(i));
            if (ObjectUtils.isNotEmpty(createOrder)) {
                //变更状态，订单变更状态：0.审核未通过  2.已变更 3.变更已提交 4.变更审核通过 5.未变更 null未变更
                if (createOrder.getBgzt() != null && createOrder.getBgzt().intValue() != 3) {
                    throw new RuntimeException("请选择已提交变更的数据！");
                }
                //修改订单状态为撤回状态
                createOrder.setBgzt(2);
                this.updateById(createOrder);
                //更新变更审批流状态为失效状态（-1）
                workflowCaseService.loseByBizId(Arrays.asList(createOrder.getId()),
                        createOrder.getTjlx() == 0 ? WorkflowType.ORDER_FLOW_CHANGE.getCode() : WorkflowType.ORDER_FLOW_CHANGE_OCCUPATIONAL.getCode(),
                        -1, "变更撤回");
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 变更反审
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean unauditChange(String id) {
        //获取实体
        Createorder createOrder = createorderMapper.getInfoById(id);
        if (ObjectUtil.isNotEmpty(createOrder)) {
            //变更状态，订单变更状态：0.审核未通过  2.已变更 3.变更已提交 4.变更审核通过 5.未变更 null未变更
            if (createOrder.getBgzt() != 4) {
                throw new ServiceException("变更未审核，无需反审核!");
            } else {
//                @SuppressWarnings("rawtypes")
                //订单与分中心关联表
                List<Orderandfzx> list = orderandfzxService.list(new QueryWrapper<Orderandfzx>()
                        .eq("ddid", id).eq("tbzt", 1));
                if (list.size() > 0) {
                    throw new ServiceException("订单已同步，反审核失败!");
                } else {
                    //变更状态
                    createOrder.setBgzt(2);
                    this.updateById(createOrder);
                    //更新变更审批流状态为失效状态（-1）
                    workflowCaseService.loseByBizId(Arrays.asList(createOrder.getId()), createOrder.getTjlx() == 0 ?
                            WorkflowType.ORDER_FLOW_CHANGE.getCode() : WorkflowType.ORDER_FLOW_CHANGE_OCCUPATIONAL.getCode(),
                            -1, "变更反审");
                    //订单与套餐关联表
                    List<Orderandcombo> oacs = orderandcomboMapper.selectList(new QueryWrapper<Orderandcombo>().eq("ddid", id).eq("isbg", 1));
                    for (Orderandcombo oac : oacs) {
                        //审批状态：0.未审批 1.已审批
                        oac.setSpzt(0);
                        orderandcomboMapper.updateById(oac);
                    }
                }
            }
        } else {
            throw new ServiceException("反审核失败，订单已不存在！");
        }
        return Boolean.TRUE;
    }

    /**
     * 材料通过
     *
     * @param ids
     * @param clspzt
     * @param clspyj
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean clpassOrUmpass(List<String> ids, int clspzt, String clspyj) {
        for (String id : ids) {
            //订单表
            Createorder order = createorderMapper.getInfoById(id);
            if (ObjectUtils.isEmpty(order)) {
                throw new ServiceException("审核失败，所选订单已不存在，请刷新后重试。");
            }
            //审批状态：0.审核未通过  1.已撤回 2.草稿 3.已提交 4.审核通过
            if (order.getSpzt().intValue() != 3
                    && (order.getBgzt() == null || order.getBgzt().intValue() != 3)) {
                throw new ServiceException("审核失败，所选订单未提交或已审核通过，请刷新后重试。");
            }
            order.setClspyj(clspyj);
            order.setClspzt(clspzt);
            //材料审批人
            order.setClspr(SecurityUtils.getUsername());
            updateById(order);
        }
        return Boolean.TRUE;
    }

    /**
     * 修改发放方式-保存
     *
     * @param ids
     * @param idInformway
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveInfo(List<String> ids, String idInformway) {
        //订单表
        List<Createorder> cos = createorderMapper.selectList(new QueryWrapper<Createorder>().in("id", ids));
        for (Createorder co : cos) {
            //通知方式ID
            co.setIdInforway(idInformway);
        }

        //订单与分中心关联表
        List<Orderandfzx> oafs = orderandfzxService.list(new QueryWrapper<Orderandfzx>().in("ddid", ids));
        for (Orderandfzx oaf : oafs) {
            //下载状态；0未下载、1已下载、null不需要下载
            oaf.setDdxzzt(0);
        }
        //更新
        this.updateBatchById(cos);
        orderandfzxService.updateBatchById(oafs);

        return Boolean.TRUE;
    }

    /**
     * 变更前台须知-保存
     *
     * @param orderId
     * @param qtxz
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveQtxz(String orderId, String qtxz) {
        //前台须知记录
        List<CreateOrderQtxz> coqs = createOrderQtxzMapper.selectList(new QueryWrapper<CreateOrderQtxz>()
                .orderByDesc("idx").eq("order_Id", orderId));
        if (coqs.size() >= 2) {
            throw new ServiceException("此订单变更前台须知已达两次，不能再变更前台须知，请变更订单。");
        }
        //订单表
        Createorder o = createorderMapper.getInfoById(orderId);
        //前台须知
        String oldQtxz = o.getQtxz();
        //更新
        o.setQtxz(qtxz);
        createorderMapper.updateById(o);

        //插入
        createOrderQtxzMapper.insert(
                new CreateOrderQtxz(
                        coqs.size() == 0 ? 1 : (coqs.get(0).getIdx() + 1)
                        , qtxz
                        , oldQtxz
                        , SecurityUtils.getUsername()
                        , orderId
                )
        );
        List<Orderandfzx> oafs = orderandfzxService.list(new QueryWrapper<Orderandfzx>().eq("ddid", orderId));
        for (Orderandfzx oaf : oafs) {
            //线上变更前台须知后的下载状态。0未下载、1已下载、null不需要下载
            oaf.setXzxzzt(0);
        }
        //更新
        orderandfzxService.updateBatchById(oafs);


        //备单那取的是体检者任务中的前台须知，把这个表也更新下
        Peisorgreservation peisorgreservation = peisorgreservationMapper.selectOne(new QueryWrapper<Peisorgreservation>().eq("ddh", orderId));
        if (ObjectUtils.isNotEmpty(peisorgreservation)){
            peisorgreservation.setQtxz(qtxz);
            peisorgreservationMapper.updateById(peisorgreservation);
        }

        //修改未登记体检者的前台须知
        Peispatient peispatient = new Peispatient();
        peispatient.setQtxz(qtxz);
        peispatientMapper.update(peispatient, new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getNumorgresv, o.getDdh()).eq(Peispatient::getFRegistered,0));

        return Boolean.TRUE;
    }

    /**
     * 编辑开单助理保存
     *
     * @param id
     * @param kdzlName
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveKdzl(String id, String kdzlName) {
        //订单表
        Createorder createorder = createorderMapper.getInfoById(id);
        //订单与分中心关联表
        List<Orderandfzx> ddList = orderandfzxService.list(new QueryWrapper<Orderandfzx>().eq("ddid", id));
        if (CollectionUtil.isEmpty(ddList)) {
            throw new ServiceException("无此订单详情");
        } else {
            ddList.forEach(item -> {
                if (StringUtils.isBlank(kdzlName)) {
                    createorder.setKdzlName("");
                    item.setKdzlzt(0);
                } else {
                    createorder.setKdzlName(kdzlName);
                    item.setKdzlzt(0);
                }
            });
        }

        createorderMapper.updateById(createorder);
        orderandfzxService.updateBatchById(ddList);
        return Boolean.TRUE;
    }

    /**
     * 查看套餐
     *
     * @param createOrder
     * @return
     */
    @Override
    public Map getDataForRequest2(Createorder createOrder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> result = new HashMap<>();
        boolean hasFinanceRole = SecurityUtils.hasRole(RoleAuthName.CAIWU);

        // 客户信息
        Sellcustomer customer = sellcustomerMapper.getInfoById(createOrder.getKhdwmcid());
        String companyName = customer.getKhdwmc();
        String year = sdf.format(createOrder.getCjddrq()).substring(0, 4);
        result.put("jhjq", sdf.format(createOrder.getJhjqc()) + "至" + sdf.format(createOrder.getJhjqd()));
        result.put("tcbt", companyName + year + "体检套餐");
        result.put("jhrs", createOrder.getNxtjrs() + createOrder.getVxtjrs());
        result.put("zk", createOrder.getDdzk());
        SysUser salesUser = sysUserMapper.getUserByNo(createOrder.getXsjlid());
        result.put("xsjl", salesUser.getUserName());
        result.put("xsjldh", salesUser.getPhonenumber());
        result.put("khdwmc", companyName);
        result.put("dwlxr", customer.getKhdwlxr());
        result.put("lxrdh", customer.getKhdh());

        // 1. 批量获取套餐、危害因素、套餐状态
        List<Orderandcombo> combos = orderandcomboMapper.selectList(new QueryWrapper<Orderandcombo>().eq("ddid", createOrder.getId()));
        List<String> tcidList = new ArrayList<>();
        List<String> tcstateList = new ArrayList<>();
        List<String> comboIsbgList = new ArrayList<>();
        for (Orderandcombo combo : combos) {
            tcidList.add(combo.getTcid());
            tcstateList.add(combo.getCombostate());
            comboIsbgList.add(combo.getIsbg() == null ? "0" : combo.getIsbg().toString());
        }
        // 批量查套餐
        Map<String, Createmeal> createmealMap = new HashMap<>();
        Map<String, Createcombo> createcomboMap = new HashMap<>();
        if (!tcidList.isEmpty()) {
            List<Createmeal> createmeals = createmealMapper.selectBatchIds(tcidList);
            for (Createmeal m : createmeals) createmealMap.put(m.getId(), m);
            List<Createcombo> createcombos = createcomboMapper.selectBatchIds(tcidList);
            for (Createcombo c : createcombos) createcomboMap.put(c.getId(), c);
        }
        // 批量查危害因素
        Set<String> harmIdSet = new HashSet<>();
        for (Createmeal m : createmealMap.values()) {
            if (m.getJhys() != null) Collections.addAll(harmIdSet, m.getJhys().split(","));
        }
        for (Createcombo c : createcomboMap.values()) {
            if (c.getJhys() != null) harmIdSet.add(c.getJhys());
        }
        Map<String, Harm> harmMap = new HashMap<>();
        if (!harmIdSet.isEmpty()) {
            List<Harm> harms = harmMapper.selectBatchIds(harmIdSet);
            for (Harm h : harms) harmMap.put(h.getId(), h);
        }
        // 2. 组装套餐与危害因素
        List<Object> packageList = new ArrayList<>();
        List<String> harmList = new ArrayList<>();
        for (int i = 0; i < combos.size(); i++) {
            String tcid = tcidList.get(i);
            String tcstate = tcstateList.get(i);
            String isbg = comboIsbgList.get(i);
            if ("0".equals(tcstate)) {
                Createmeal meal = createmealMap.get(tcid);
                if (meal == null || (ObjectUtils.isNotEmpty(meal.getForbidden()) && meal.getForbidden() == 1)) continue;
                meal.setTjtcjc(meal.getTjtcjc() + ("1".equals(isbg) ? "(变更)" : ""));
                packageList.add(meal);
                if (meal.getJhys() != null) {
                    String[] harmIds = meal.getJhys().split(",");
                    StringBuilder sb = new StringBuilder();
                    for (String hid : harmIds) {
                        Harm h = harmMap.get(hid);
                        if (h != null) sb.append(h.getHarmName()).append("、");
                    }
                    harmList.add(sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "");
                } else {
                    harmList.add("");
                }
            } else {
                Createcombo minCombo = createcomboMap.get(tcid);
                if (minCombo == null) continue;
                minCombo.setTjtcjc(minCombo.getTjtcjc() + ("1".equals(isbg) ? "(变更)" : ""));
                packageList.add(minCombo);
                if (minCombo.getJhys() != null) {
                    Harm h = harmMap.get(minCombo.getJhys());
                    harmList.add(h != null ? h.getHarmName() : "");
                } else {
                    harmList.add("");
                }
            }
        }
        result.put("tcjcList", packageList);
        result.put("jhysList", harmList);

        // 3. 批量获取收费项目
        List<ItemDataDto> itemData = createorderMapper.getItemData(createOrder.getId());
        Set<String> allItemIds = new HashSet<>();
        for (ItemDataDto dto : itemData) {
            String[] itemIds = dto.getItemids().split("@");
            Collections.addAll(allItemIds, itemIds);
        }
        Map<String, Items> itemsMap = new HashMap<>();
        if (!allItemIds.isEmpty()) {
            List<Items> itemsList = itemsMapper.selectBatchIds(allItemIds);
            for (Items item : itemsList) itemsMap.put(item.getId(), item);
        }

        // 3.1 批量预加载套餐-项目关系
        // Createmeal
        Map<String, Set<String>> mealItemMap = new HashMap<>();
        if (!createmealMap.isEmpty()) {
            List<String> mealIds = new ArrayList<>(createmealMap.keySet());
            List<Mealanditem> allMealItems = mealanditemMapper.selectList(new QueryWrapper<Mealanditem>().in("tcid", mealIds));
            for (Mealanditem mi : allMealItems) {
                mealItemMap.computeIfAbsent(mi.getTcid(), k -> new HashSet<>()).add(mi.getSfxmid());
            }
        }
        // Createcombo
        Map<String, Set<String>> comboItemMap = new HashMap<>();
        if (!createcomboMap.isEmpty()) {
            List<String> comboIds = new ArrayList<>(createcomboMap.keySet());
            List<Comboanditem> allComboItems = comboanditemMapper.selectList(new QueryWrapper<Comboanditem>().in("tcid", comboIds).eq("is_delete", 0));
            for (Comboanditem ci : allComboItems) {
                comboItemMap.computeIfAbsent(ci.getTcid(), k -> new HashSet<>()).add(ci.getSfxmid());
            }
        }

        // 4. 收费项目分组
        List<Map<Object, List>> groupedItems = new ArrayList<>();
        for (ItemDataDto dto : itemData) {
            Map<Object, List> group = new HashMap<>();
            String[] itemIds = dto.getItemids().split("@");
            List<Map<String, Object>> items = new ArrayList<>();
            Set<String> unique = new HashSet<>();
            for (String itemId : itemIds) {
                if (unique.add(itemId)) {
                    Items item = itemsMap.get(itemId);
                    if (item == null) throw new ServiceException(itemId + "收费项目id不存在！");
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("itemName", item.getExamfeeitemNameprn());
                    itemMap.put("checkYy", item.getJcyy());
                    itemMap.put("itemid", itemId);
                    itemMap.put("mou", getMou((List) result.get("tcjcList"), itemId, mealItemMap, comboItemMap));
                    itemMap.put("price", item.getUnitprice());
                    if (hasFinanceRole) itemMap.put("costprice", item.getCostprice());
                    items.add(itemMap);
                }
            }
            group.put(dto.getFz(), items);
            groupedItems.add(group);
        }
        result.put("sfxmData", groupedItems);

        // 5. 禁止打折项目批量处理
        Set<String> forbiddenIds = new HashSet<>();
        for (int i = 0; i < tcidList.size(); i++) {
            String tcid = tcidList.get(i);
            String tcstate = tcstateList.get(i);
            if ("0".equals(tcstate)) {
                Set<String> mealItems = mealItemMap.getOrDefault(tcid, Collections.emptySet());
                for (String sfxmid : mealItems) {
                    Items item = itemsMap.get(sfxmid);
                    if (item != null && item.getFDiscountdisabled() != null && item.getFDiscountdisabled() == 1) forbiddenIds.add(item.getId());
                }
            } else {
                Set<String> comboItems = comboItemMap.getOrDefault(tcid, Collections.emptySet());
                for (String sfxmid : comboItems) {
                    Items item = itemsMap.get(sfxmid);
                    if (item != null && item.getFDiscountdisabled() != null && item.getFDiscountdisabled() == 1) forbiddenIds.add(item.getId());
                }
            }
        }
        // 排序
        List<String> forbiddenSorted = forbiddenIds.stream().sorted((a, b) -> {
            Items i1 = itemsMap.get(a);
            Items i2 = itemsMap.get(b);
            String xh1 = i1 != null ? i1.getXh() : null;
            String xh2 = i2 != null ? i2.getXh() : null;
            if (xh1 == null) return 1;
            if (xh2 == null) return -1;
            return Integer.compare(Integer.parseInt(xh1), Integer.parseInt(xh2));
        }).collect(Collectors.toList());
        result.put("qcData", forbiddenSorted);
        // 详细禁止打折项目信息
        List<Object> forbiddenDetail = new ArrayList<>();
        for (String id : forbiddenSorted) {
            Items item = itemsMap.get(id);
            if (item == null) continue;
            Map<String, Object> map = new HashMap<>();
            map.put("sfid", id);
            map.put("sfxmmc", item.getExamfeeitemNameprn());
            map.put("jg", item.getUnitprice());
            map.put("jcyy", item.getJcyy() == null ? "" : item.getJcyy());
            map.put("mou", getMou((List) result.get("tcjcList"), id, mealItemMap, comboItemMap));
            forbiddenDetail.add(map);
        }
        result.put("qcDataList", forbiddenDetail);
        // 打印机配置
        result.put("tcPrinter", Constants.TCDY);
        return result;
    }

    // 优化后的 getMou 方法，纯内存判断
    private List<Integer> getMou(List<Object> list, String itemid, Map<String, Set<String>> mealItemMap, Map<String, Set<String>> comboItemMap) {
        List<Integer> result = new ArrayList<>();
        for (Object obj : list) {
            if (obj instanceof Createmeal) {
                result.add(mealItemMap.getOrDefault(((Createmeal)obj).getId(), Collections.emptySet()).contains(itemid) ? 1 : 0);
            } else if (obj instanceof Createcombo) {
                result.add(comboItemMap.getOrDefault(((Createcombo)obj).getId(), Collections.emptySet()).contains(itemid) ? 1 : 0);
            } else {
                result.add(0);
            }
        }
        return result;
    }

    /**
     * 获取客户单位类型
     *
     * @param id
     * @return
     */
    @Override
    public Integer getGroupLevel(String id) {
        List<Integer> list = createorderMapper.getGroupLevel(id);
        if (list.size() > 0) {
            Integer obj = list.get(0);
            return obj == null ? 0 : obj;
        }
        return 0;
    }

    /**
     * 获取订单号下拉
     *
     * @param key
     * @param customerId
     * @return
     */
    @Override
    public List<DdhDataVo> getDdhDatas(String key, String customerId) {
        return createorderMapper.getDdhDatas(key, customerId);
    }

    /**
     * 材料路径保存
     *
     * @param id   订单号
     * @param urls 材料路径：多个以英文
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveClUrl(String id, List<String> urls) {
        Createorder order = createorderMapper.selectById(id);
        if (Objects.isNull(order)) {
            throw new ServiceException("所选订单已不存在，请检查后重试！");
        }
        if (Objects.nonNull(order.getClspzt()) && order.getClspzt() == 1) {
            throw new ServiceException("所选订单已通过材料审核，不能修改上传材料。");
        }
        if (!(order.getSpzt().intValue() == 0 || order.getSpzt().intValue() == 1 || order.getSpzt().intValue() == 2
                || (Objects.nonNull(order.getBgzt()) && (order.getBgzt().intValue() == 0 || order.getBgzt().intValue() == 2)))) {
            throw new ServiceException("所选订单已提交或审核通过，不能修改上传材料。");
        }
        String urlsStr = StringUtils.join(urls, "|");
        update(new LambdaUpdateWrapper<Createorder>()
                .set(Createorder::getClurls, urlsStr)
                .set(Createorder::getClspzt, null)
                .set(Createorder::getModifydate, new Date())
                .eq(Createorder::getId, id));
        //TODO wait 实现删除旧材料文件
        return Boolean.TRUE;
    }


    /**
     * 上传名单-保存
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saveUpload(SaveUploadParam param) {
        Createorder order = createorderMapper.getInfoById(param.getId());
        if ("ok".equals(param.getFchange())) {
            order.setUrls(param.getPath());
        } else {
            order.setUrls(order.getUrls() == null ? param.getPath() : (order.getUrls() + ";" + param.getPath()));
        }

        int i = createorderMapper.updateById(order);
        return i > 0;
    }


    /**
     * 列表数据  显示订单下所有人员不含复查,判断重复时不判断复查
     *
     * @param ddh
     * @return
     */
    @Override
    public List<COListDataVo> getListData(String ddh) {
        return createorderMapper.getListData(ddh);
    }


    /**
     * 导入名单-保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUpNameList(SaOrUpNameListParam param) {
        //保存  一个订单下手机号不能重复
        // TODO: wait  体检者默认头像
        String defaultPhoto = "";
        String registerR = SecurityUtils.getUserNo();
        Date date = new Date();
        COFormDataDto coFormData = param.getCoFormData();
        List<COGridDataDto> coGridDataDtos = param.getCoGridDataDtos();

        Createorder order = createorderMapper.getInfoById(coFormData.getOrderId());
        // 团体id
        String idOrg = order.getKhdwmcid();
        // 开单医师ID
        Sellcustomer customer = sellcustomerMapper.getInfoById(idOrg);
        SysUser qxUsers = sysUserMapper.getUserByNo(customer.getXsjlid());
        String xsjlId = null == qxUsers ? "" : qxUsers.getUserNo();
        String xsjlName = null == qxUsers ? "" : qxUsers.getUserName();
        // 团体名称
        String orgName = customer.getKhdwmc();
        String ddh = order.getDdh();
        String idInformway = order == null ? null : order.getIdInforway();//通知方式
        String qtxz = order.getQtxz();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String idExamType = "0";
        Area defaultArea = areaMapper.selectOne(new QueryWrapper<Area>().eq("resarea", "山东省"));
        Set<String> phones = new HashSet<String>();
        String note = SecurityUtils.getUsername() + "线上导入订单名单于" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ";";

        for (COGridDataDto map : coGridDataDtos) {
            if (phones.contains(map.getPhone())) {
                throw new ServiceException("联系电话: <font color='red'>" + map.getPhone() + "</font>重复");
            }
            phones.add(map.getPhone());
            Peispatient peispatient = mapperFacade.map(map, Peispatient.class);
            if (null == peispatient) {
                throw new ServiceException("预登记失败：系统发生异常，请联系管理员");
            }
            peispatient.setCountreportoccupationxml(1);//身份证
            peispatient.setQtxz(qtxz);
            peispatient.setIdInformway(idInformway);
            peispatient.setFUsecodehiden(1);
            // 订单号
            peispatient.setNumorgresv(ddh);
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
            if (!StringUtils.isBlank(peispatient.getIdcardno())) {
                String card = peispatient.getIdcardno();
                // 如果长度是15位
                if (card.length() == 15) {
                    card = card.substring(0, 6) + "19" + card.substring(6) + "x";
                }
                // 生日匹配
                peispatient.setBirthdate(IdcardUtil.getBirthDate(card));
                // 年龄匹配
                int age = IdcardUtil.getAgeByIdCard(card);
                if (null != peispatient.getAge() && peispatient.getAge() > 0 && peispatient.getAge() != age) {
                    throw new ServiceException("保存失败：" + map.getPatientname() + " 体检者的身份证号与年龄不匹配");
                }
                peispatient.setAge(age);
                // 匹配性别
                card = card.substring(card.length() - 2, card.length() - 1);
                // 性别是否匹配，身份证倒数第二位,单数男，双数女
                String strSex = Integer.valueOf(card) % 2 != 0 ? "0" : "1";
                if (ObjectUtils.isNotEmpty(peispatient.getIdSex()) && !strSex.equals(String.valueOf(peispatient.getIdSex()))) {
                    throw new ServiceException("保存失败：" + map.getPatientname() + " 体检者的身份证号与性别不匹配");
                }

            }
            // 体检者类型
            peispatient.setIdPatientclass("1");
            // 登记员
            peispatient.setIdDoctorreg(registerR);
            // 根据身份证判断籍贯
            Area area = null;
            if (!StringUtils.isBlank(peispatient.getIdcardno())) {
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
            if ("modified".equals(map.getState())) {
                long count = peispatientMapper.selectCount(new QueryWrapper<Peispatient>()
                        .eq("phone", peispatient.getPhone())
                        .ne("id_examtype", "3")
                        .eq("numorgresv", ddh)
                        .ne("id", peispatient.getId()));
                if (count > 0) {
                    throw new ServiceException("联系电话: <font color='red'>" + map.getPhone() + "</font>重复");
                }
                Peispatient peispatientNew = peispatientMapper.getInfoById(map.getId());
                // 判断是否存在
                if (null != peispatientNew) {
                    // 判断人员信息是否重复添加
                    String rlt = isExitsName(peispatient, ddh);
                    if (!"success".equals(rlt)) {
                        // 存在重复
                        throw new ServiceException(rlt);
                    }
                    boolean hasNotCode = StringUtils.isBlank(peispatientNew.getPatientcode());
                    if (hasNotCode) {
                        // 生成体检号
                        String patientCode = "";
                        do {
                            patientCode = CodeUtil.getPatientCode(Constants.ONLINE_PREFIX, iSysConfigService.selectConfigByKey(Constants.VERSION_NO));
                            //判断体检号是否存在
                        } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                                .eq(Peispatient::getPatientcode, patientCode)) > 0);
                        peispatient.setPatientcode(patientCode);
                        peispatient.setPatientbizno(patientCode);
                        peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
                        //体检号生成人  生成时间
                        peispatient.setTimingstartedat(date);
                        peispatient.setGuidancenote2(registerR);
                    }
                    if (StringUtils.isEmpty(peispatient.getIdCis())) {
                        peispatient.setIdCis(bindArchive(peispatient));
                    }
                    if (hasNotCode) {
                        peispatientChargeMainService.save(new PeispatientChargeMain(
                                note
                                , peispatient.getMoneyamount()
                                , peispatient.getMoneyamountpaid()
                                , peispatient.getPatientcode()));
                    }
                } else {
                    // 不存在
                    throw new ServiceException("预登记失败：" + map.getPatientname() + " 体检者不存在，已经被删除");
                }
            } else if ("added".equals(map.getState())) {
                long count = peispatientMapper.selectCount(new QueryWrapper<Peispatient>()
                        .ne("id_examtype", "3")
                        .eq("phone", peispatient.getPhone())
                        .eq("numorgresv", ddh));
                if (count > 0) {
                    throw new ServiceException("联系电话: <font color='red'>" + map.getPhone() + "</font>重复");
                }
                // 生成体检号
                String patientCode = "";
                do {
                    patientCode = CodeUtil.getPatientCode(Constants.ONLINE_PREFIX, iSysConfigService.selectConfigByKey(Constants.VERSION_NO));
                    //判断体检号是否存在
                } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                        .eq(Peispatient::getPatientcode, patientCode)) > 0);
                peispatient.setPatientcode(patientCode);
                peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
                peispatient.setFRegistered(0);
                peispatient.setPatientbizno(patientCode);

                // 判断人员信息是否重复添加
                String rlt = isExitsName(peispatient, ddh);
                if (!"success".equals(rlt)) {
                    // 存在重复
                    throw new ServiceException(rlt);
                }
                if (StringUtils.isEmpty(peispatient.getIdCis())) {
                    peispatient.setIdCis(bindArchive(peispatient));
                }
                // 保存实体类
                peispatientMapper.insert(peispatient);
                peispatientChargeMainService.save(new PeispatientChargeMain(
                        note
                        , peispatient.getMoneyamount()
                        , peispatient.getMoneyamountpaid()
                        , peispatient.getPatientcode()));
            }
        }
        return Boolean.TRUE;
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
        if (!StringUtils.isBlank(peispatient.getIdcardno())) {
            and.eq("idcardno", peispatient.getIdcardno());
        } else {
            // 性别
            if (!StringUtils.isBlank(peispatient.getIdSex().toString())) {
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
            if (!StringUtils.isBlank(peispatient.getPhone())) {
                and.eq("phone", peispatient.getPhone());
            } else {
                and.isNull("phone");
            }
            // 部门
            if (!StringUtils.isBlank(peispatient.getOrgDepart())) {
                and.eq("org_depart", peispatient.getOrgDepart());
            } else {
                and.isNull("org_depart");
            }
            // 工种
            if (!StringUtils.isBlank(peispatient.getTrades())) {
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
        long i = peispatientMapper.selectCount(and.eq("numorgresv", ddh)
                .ne("id_examtype", "3"));
        // 判断是否存在已经导入
        if (i > 0) {
            return "人员: <font color='red'>" + peispatient.getPatientname() + "</font> 已经存在";
        }
        return "success";
    }


    /**
     * 绑定档案
     *
     * @param patient
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String bindArchive(Peispatient patient) {
        //体检者档案表
        Peispatientarchive archive = null;
        if (patient.getIdCis() != null) {
            archive = peispatientarchiveMapper.getInfoById(patient.getIdCis());
        }
        //身份证号
        String idcardno = patient.getIdcardno();
        if (ObjectUtils.isEmpty(archive)) {
            if (idcardno != null) {
                //对比身份证
                archive = peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>()
                        .eq("idcardno", idcardno)
                        .eq("is_delete", 0)
                );
            }
        }
        String phone = patient.getPhone();
        String patientname = patient.getPatientname();
        if (archive == null) {
            if (phone != null) {
                //对比手机号+姓名
                archive = peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>()
                        .eq("phone", phone)
                        .eq("patientname", patientname)
                        .eq("is_delete", 0)
                );
            }
        }
        if (archive == null) {
            archive = new Peispatientarchive();
            //生成档案号
            String recordNo = peispatientarchiveService.getArchiveCode();
            archive.setPatientarchiveno(recordNo);
            archive.setJf(0D);
            archive.setFzx(SecurityUtils.getCId());
        }
        archive.setInputCode(patient.getInputCode());
        archive.setPatientname(patientname);
        archive.setIdcardno(idcardno);
        archive.setCountreportoccupationxml(1);
        archive.setAge(patient.getAge());
        archive.setBirthdate(patient.getBirthdate());
        archive.setPhone(phone);
        archive.setIdMarriage(patient.getIdMarriage() == null ? null : patient.getIdMarriage().toString());
        archive.setIdNation(patient.getIdNation());
        archive.setCultural(patient.getCultural());
        //保存或修改
        peispatientarchiveService.saveOrUpdate(archive);
        String id = archive.getId();
        return id;
    }


    /**
     * 导入名单-删除
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeAll(RemoveAllParam param) {
        List<String> ids = param.getIds();
        String id = param.getId();
        // 选中体检者
        if (CollectionUtil.isNotEmpty(ids)) {
            for (String string : ids) {
                Peispatient peispatient = peispatientMapper.getInfoById(string);
                if (ObjectUtils.isNotEmpty(peispatient)) {
                    //有收费项目的  不能删除
                    long count = peispatientfeeitemMapper.selectCount(new QueryWrapper<Peispatientfeeitem>().eq("id_patient", peispatient.getPatientcode()));
                    if (count > 0) {
                        throw new ServiceException("删除失败：<font color='red'>" + peispatient.getPatientname() + "</font>订单已确认，不可删除");
                    }
                    if (peispatient.getFRegistered() == 1) {
                        throw new ServiceException("体检号:" + peispatient.getPatientcode() + "已登记,不能清除!");
                    }
                    peispatientChargeMainMapper.delete(new QueryWrapper<PeispatientChargeMain>().eq("patientcode", peispatient.getPatientcode()));
                }
            }
            peispatientMapper.delete(new QueryWrapper<Peispatient>().in("id", ids));

        } else {
            Createorder order = createorderMapper.getInfoById(id);
            List<Peispatient> peispatients = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                    .eq("numorgresv", order.getDdh()));
            List<String> pIds = new ArrayList<>();
            for (Peispatient peispatient : peispatients) {
                //有收费项目的  不能删除
                long count = peispatientfeeitemMapper.selectCount(new QueryWrapper<Peispatientfeeitem>().eq("id_patient", peispatient.getPatientcode()));
                if (count > 0) {
                    throw new ServiceException("删除失败：<font color='red'>" + peispatient.getPatientname() + "</font>订单已确认，不可删除");
                }
                if (peispatient.getFRegistered() == 1) {
                    throw new ServiceException("体检号:" + peispatient.getPatientcode() + "已登记,不能清除!");
                }
                peispatientChargeMainMapper.delete(new QueryWrapper<PeispatientChargeMain>().eq("patientcode", peispatient.getPatientcode()));
                pIds.add(peispatient.getId());
            }
            peispatientMapper.deleteBatchIds(pIds);
        }
        return Boolean.TRUE;
    }


    /**
     * 获取体检者任务分组
     *
     * @param id
     * @return
     */
    @Override
    public List<Peisorgreservationgroup> getGroup(String id) {
        List<Peisorgreservationgroup> group = new ArrayList<>();
        //体检者团体任务
        Peisorgreservation vation = peisorgreservationMapper.selectOne(new QueryWrapper<Peisorgreservation>().eq("ddh", id));
        if (ObjectUtils.isNotEmpty(vation)) {
            //体检者任务分组
            group = peisorgreservationgroupMapper.selectList(new QueryWrapper<Peisorgreservationgroup>()
                    .eq("id_orgreservation", vation.getId()).eq("is_delete", 0));
        }
        return group;
    }

    /**
     * 删除订单
     *
     * @param ids 订单id集合
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeOrders(List<String> ids) {
        //假删
        Createorder createorder = new Createorder();
        createorder.setIsDelete(1);
        createorder.setModifydate(new Date());
        createorderMapper.update(createorder, new UpdateWrapper<Createorder>().in("id", ids));
        //删除撞单记录和签单计划
        orderPlanService.remove(new LambdaQueryWrapper<OrderPlan>().in(OrderPlan::getDdId, ids));
        orderConflictMapper.delete(new LambdaQueryWrapper<OrderConflict>().in(OrderConflict::getDdId, ids));
        return Boolean.TRUE;
    }

    /**
     * 通过参数获取订单信息
     *
     * @param param 查询参数
     * @return 单条数据
     */
    @Override
    public IPage<CreateorderDto> getOrderInfo(PageParam<CreateorderDto> page, OrderParam param) {
        IPage<CreateorderDto> iPage = createorderMapper.getOrderInfo(page,param);
        List<CreateorderDto> orderList = iPage.getRecords();
        for (CreateorderDto item : orderList) {
            if (StringUtils.isNotBlank(item.getFzxid())) {
                //获取分中心列表
                String[] branchList = item.getFzxid().split(",");
                List<Branch> list = branchMapper.selectList(new LambdaQueryWrapper<Branch>().select(Branch::getBranchId, Branch::getFzx, Branch::getId, Branch::getJm).in(Branch::getBranchId, branchList).eq(Branch::getIsDelete, 0));
                if (CollectionUtil.isNotEmpty(list)) {
                    item.setBranchList(mapperFacade.mapAsList(list, SysBranchDto.class));
                }
            }
        }
        iPage.setRecords(orderList);
        return iPage;

    }


    /**
     * 获取套餐
     *
     * @param createorder
     * @return
     */
    @Override
    public List<GetPackageVo> getPackage(Createorder createorder) {
        Boolean caiWu = SecurityUtils.hasRole(RoleAuthName.CAIWU);
        List<GetPackageVo> list = new ArrayList<>();
        List<Orderandcombo> oacs = orderandcomboMapper.selectList(new QueryWrapper<Orderandcombo>().eq("ddid", createorder.getId()));
        PageParam<ItemDataVo> page = new PageParam<>();
        page.setSize(999);
        for (Orderandcombo oac : oacs) {
            GetPackageVo vo = new GetPackageVo();
            String combostate = oac.getCombostate();
            if ("0".equals(combostate)) {
                //从普通套餐表获取数据
                Createmeal createMeal = createmealMapper.getInfoById(oac.getTcid());
                vo.setTjtcmc(createMeal.getTjtcmc());
                //接害因素
                if(StringUtils.isNotEmpty(createMeal.getJhys())){
                    String[] jhys = createMeal.getJhys().split(",");
                    String harmText = harmMapper.getHarmText(jhys);
                    vo.setJhys(harmText);
                }
                IPage<ItemDataVo> ipage = mealanditemMapper.getItemData(page, createMeal.getId());
                List<ItemDataVo> records = ipage.getRecords();

                Double totalCost = 0.0;
                Double totalPrice = 0.0;
                Double tcysjg = createMeal.getTcysjg();
                double percentage = (tcysjg == null || tcysjg == 0) ? 0 : Arith.div(createMeal.getZhjg(), tcysjg);
                for (ItemDataVo record : records) {
                    totalCost = Arith.add(totalCost, record.getCostprice());
                    totalPrice = Arith.add(totalPrice, record.getJg());
                    record.setZhjg(Arith.mul(percentage, record.getJg()));
                    //没财务权限就设置为空
                    if (!caiWu){
                        record.setCostprice(null);
                    }
                }
                vo.setItemData(records);
                vo.setTotalCost(totalCost);
                vo.setTotalPrice(totalPrice);
                vo.setPercentage(Arith.div(totalCost, totalPrice));
                vo.setTotalDiscountedPrice(createMeal.getZhjg());
            } else {
                //从最小套餐表获取数据
                Createcombo createCombo = createcomboMapper.getInfoById(oac.getTcid());
                vo.setTjtcmc(createCombo.getTjtcmc());
                //接害因素
                if(StringUtils.isNotEmpty(createCombo.getJhys())){
                    String[] jhys = createCombo.getJhys().split(",");
                    String harmText = harmMapper.getHarmText(jhys);
                    vo.setJhys(harmText);
                }
                IPage<ItemDataVo> ipage = comboanditemMapper.getItemData(page, createCombo.getId());
                List<ItemDataVo> records = ipage.getRecords();

                Double totalCost = 0.0;
                Double totalPrice = 0.0;
                double percentage = Arith.div(createCombo.getZhjg(), createCombo.getTcysjg());
                for (ItemDataVo record : records) {
                    totalCost = Arith.add(totalCost, record.getCostprice());
                    totalPrice = Arith.add(totalPrice, record.getJg());
                    record.setZhjg(Arith.mul(percentage, record.getJg()));
                    //没财务权限就设置为空
                    if (!caiWu){
                        record.setCostprice(null);
                    }
                }
                vo.setItemData(records);
                vo.setTotalCost(totalCost);
                vo.setTotalPrice(totalPrice);
                vo.setPercentage(Arith.div(totalCost, totalPrice));
                vo.setTotalDiscountedPrice(createCombo.getZhjg());
            }
            //变动成本率
            Double varCostRate = createorderMapper.getVarCostRate(oac.getDdid());
            vo.setVariableCostRate(varCostRate);
            list.add(vo);
        }

        return list;
    }

    /**
     * 获取变动成本率
     * @param id
     * @return
     */
    @Override
    public Double getVarCostRate(String id) {
        return createorderMapper.getVarCostRate(id);
    }

    /**
     * 获取订单的分中心
     * @param numorgresv
     * @return
     */
    @Override
    public List<String> getFzxList(String numorgresv) {
        return createorderMapper.getFzxList(numorgresv);
    }

    /**
     * 查询订单和任务错误的分组
     * @param ddh
     * @return
     */
    @Override
    public VationAndGroupErrorDataDto getVationAndGroupErrorData(String ddh) {
        return createorderMapper.getVationAndGroupErrorData(ddh);
    }

    /**
     * 根据客户ID查询订单列表
     * @param customerIds
     * @return
     */
    @Override
    public List<CustomerOrderVo> getOrderByKHIds(List<String> customerIds) {
        return createorderMapper.getOrderByKHIds(customerIds);
    }

    /**
     * 是否需要选择
     * @param param
     * @return
     */
    @Override
    public List<WorkflowItem> needChoose(NeedChooseParam param) {
        Createorder createorder = createorderMapper.getInfoById(param.getId());
        if (ObjectUtils.isEmpty(createorder)){
            throw new ServiceException("订单id不正确!");
        }
        String typeFlag = getTypeFlag(createorder.getTjlx(),param.getType());
        List<WorkflowItem> workflowItems = createorderMapper.needChoose(SecurityUtils.getCId(),typeFlag);
        if (CollectionUtil.isEmpty(workflowItems)){
            throw new ServiceException("尚未建立审批流，请联系运维人员创建后再提交审批!");
        }
        //判断是否有相同的层级,有相同的层级就返回信息让他选，没有的话就返回null
        Set<Integer> levels = workflowItems.stream()
                .map(WorkflowItem::getLevel)
                .collect(Collectors.toSet());
        if (workflowItems.size() != levels.size()){
            return workflowItems;
        }else {
            return null;
        }
    }

    /**
     * 获取类型标识
     * @param tjlx
     * @param type
     * @return
     */
    private String getTypeFlag(Integer tjlx, Integer type) {
        switch (type) {
            case 0:
                return tjlx == 0 ? WorkflowType.ORDER_FLOW.getCode() : WorkflowType.ORDER_FLOW_OCCUPATION.getCode() ;
            case 1:
                return tjlx == 0 ? WorkflowType.ORDER_FLOW_CHANGE.getCode() : WorkflowType.ORDER_FLOW_CHANGE_OCCUPATIONAL.getCode();
            default:
                throw new ServiceException("该类型没有对应的类型标识!");
        }
    }
}


