package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.Arith;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.InspectCharge;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.BasexamltemMapper;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.data.dao.InspectChargeMapper;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.bean.param.CreatecomboParam;
import com.center.medical.sellcrm.bean.param.DataAddMealParam;
import com.center.medical.sellcrm.bean.param.TcCopyParam;
import com.center.medical.sellcrm.bean.vo.*;
import com.center.medical.sellcrm.dao.*;
import com.center.medical.sellcrm.service.ComboandfzxService;
import com.center.medical.sellcrm.service.ComboexamitemService;
import com.center.medical.sellcrm.service.CreatecomboService;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 最小套餐(Createcombo)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-14 19:21:18
 */
@Slf4j
@Service("createcomboService")
@RequiredArgsConstructor
public class CreatecomboServiceImpl extends ServiceImpl<CreatecomboMapper, Createcombo> implements CreatecomboService {

    private final CreatecomboMapper createcomboMapper;
    private final Snowflake snowflake;
    private final ComboexamitemMapper comboexamitemMapper;
    private final ComboandfzxMapper comboandfzxMapper;
    private final MapperFacade mapperFacade;
    private final ComboanditemMapper comboanditemMapper;
    private final ItemsMapper itemsMapper;
    private final InspectChargeMapper inspectChargeMapper;
    private final BasexamltemMapper basexamltemMapper;
    private final SysUserMapper sysUserMapper;
    private final ComboexamitemService comboexamitemService;
    private final ComboandfzxService comboandfzxService;
    private final ISysBranchService iSysBranchService;
    private final HarmMapper harmMapper;

    /**
     * 分页查询[最小套餐]列表
     *
     * @param page  分页参数
     * @param param Createcombo查询参数
     * @return 分页数据
     */
    @Override

    public IPage<Createcombo> getList(PageParam<Createcombo> page, CreatecomboParam param) {
        log.info("分页查询[最小套餐]列表参数param：{}", JSONUtil.toJsonStr(param));
        IPage<Createcombo> iPage = createcomboMapper.getList(page, param);
        List<Createcombo> records = iPage.getRecords();
        for (Createcombo createcombo : records) {
            if (ObjectUtils.isEmpty(createcombo.getFzx())){
                String[] ids = createcombo.getFzxid().split(Constants.COMMA);
                List<String> list = Arrays.stream(ids).collect(Collectors.toList());
                List<SysBranch> list1 = iSysBranchService.list(new LambdaQueryWrapper<SysBranch>().in(SysBranch::getBranchId, list));
                Set<String> set = list1.stream().map(SysBranch::getFzx).collect(Collectors.toSet());
                if (CollectionUtil.isNotEmpty(set)) {
                    createcombo.setFzx(StringUtils.join(set, Constants.COMMA));
                }
            }
        }
        iPage.setRecords(records);
        return iPage;
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Createcombo getInfoById(String id) {
        return createcomboMapper.getInfoById(id);
    }

    /**
     * 新增或者更新最小套餐信息
     *
     * @param createcombo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean saOrUp(Createcombo createcombo) {
        Date now = new Date();
        //接收最小套餐id
        String id="";
        //接收多个分中心id
        String[] fzxId;
        int tjlx = createcombo.getTjlx();
        if (StringUtils.isBlank(createcombo.getId())) {
            //新增
            createcombo.setId(String.valueOf(snowflake.nextId()));
            //保存套餐数据
            createcombo.setBjzt(0);
            createcombo.setTbzt(tjlx == 0 ? 1 : 0);
            createcombo.setCreatedate(now);
            createcombo.setModifydate(now);
            createcomboMapper.insert(createcombo);
            id = createcombo.getId();

            fzxId = createcombo.getFzxid().split(",");
            //进行循环保存
            for(int i=0;i<fzxId.length;i++){
                Comboandfzx comboAndFzx = new Comboandfzx();
                comboAndFzx.setTcid(id);
                comboAndFzx.setFzxid(fzxId[i]);
                comboAndFzx.setTbzt(0);
                comboandfzxMapper.insert(comboAndFzx);
            }
        } else {
            //编辑
            //判断套餐是否存在
            Createcombo createCom = createcomboMapper.selectOne(new LambdaQueryWrapper<Createcombo>()
                    .eq(Createcombo::getId, createcombo.getId())
                    .eq(Createcombo::getIsDelete, 0));
            if (createCom != null) {
                Integer tbzt = null;
                if (tjlx != 0) {
                    tbzt = 0;
                } else {
                    if (createCom.getTjlx() != null && createCom.getTjlx().intValue() != 0) {
                        //删除套餐检查项
                        comboexamitemMapper.delete(new LambdaQueryWrapper<Comboexamitem>().eq(Comboexamitem::getComboId, createCom.getId()));
                    }
                    tbzt = 1;
                }
                //根据套餐id和分中心id拼串确定套餐与分中心关联表中的数据
                comboandfzxMapper.delete(new LambdaQueryWrapper<Comboandfzx>().eq(Comboandfzx::getTcid, createCom.getId()));

                log.info("更新前的最小套餐信息：{}、{}", createCom, createcombo);//执行更新操作
                mapperFacade.map(createcombo, createCom);
                log.info("更新后的最小套餐信息：{}、{}", createCom, createcombo);
                createCom.setModifydate(new Date());
                createCom.setTbzt(tbzt);
                createCom.setCreatedate(now);
                createCom.setModifydate(now);
                createcomboMapper.updateById(createCom);
                createcombo.setId(createCom.getId());
                //重新保存套餐与分中心的数据
                String[] fzxComboId = createcombo.getFzxid().split(",");
                for (int i = 0; i < fzxComboId.length; i++) {
                    Comboandfzx comboAndFzx = new Comboandfzx();
                    comboAndFzx.setTcid(createCom.getId());
                    comboAndFzx.setFzxid(fzxComboId[i]);
                    comboAndFzx.setTbzt(0);
                    comboAndFzx.setId(String.valueOf(snowflake.nextId()));
                    comboAndFzx.setCreatedate(now);
                    comboAndFzx.setModifydate(now);
                    comboandfzxMapper.insert(comboAndFzx);
                }
            } else {
                //失败
                throw new ServiceException("操作失败！该体检套餐已被删除！");
            }
        }

        //操作收费项目
        List<ComboAndItemVo> comboAndItemVoList = createcombo.getComboAndItemVoList();
        for (ComboAndItemVo item : comboAndItemVoList) {
            Comboanditem comItem = mapperFacade.map(item, Comboanditem.class);
            log.info("最小套餐的收费项目：{}", comItem);
            if (StringUtils.equals("removed", item.get_state())) {
                if (StringUtils.isNotBlank(item.getId())) {
                    Comboanditem comboAndItem = comboanditemMapper.selectOne(new LambdaQueryWrapper<Comboanditem>()
                            .eq(Comboanditem::getId, item.getId())
                            .eq(Comboanditem::getIsDelete, 0));
                    //判断是否存在
                    if (Objects.nonNull(comboAndItem)) {
                        comboAndItem.setIsDelete(1);
                        comboAndItem.setModifydate(now);
                        comboanditemMapper.updateById(comboAndItem);
                    }
                }
            } else if (StringUtils.equals("modified", item.get_state())) {
                if (StringUtils.isNotBlank(item.getId())) {
                    Comboanditem comboAndItem = comboanditemMapper.selectOne(new LambdaQueryWrapper<Comboanditem>()
                            .eq(Comboanditem::getId, item.getId())
                            .eq(Comboanditem::getIsDelete, 0));
                    //判断是否存在
                    if (Objects.nonNull(comboAndItem)) {
                        log.info("最小套餐的收费项目转换前：{}、{}", comItem, comboAndItem);
                        mapperFacade.map(comItem, comboAndItem);
                        log.info("最小套餐的收费项目转换后：{}、{}", comItem, comboAndItem);
                        comboAndItem.setModifydate(now);
                        comboanditemMapper.updateById(comboAndItem);
                    } else {
                        //保存
                        comboAndItem = new Comboanditem();
                        comboAndItem.setSfxmid(item.getSfxmid());
                        comboAndItem.setSort(item.getSort());
                        comboAndItem.setTcid(createcombo.getId());
                        comboAndItem.setIsDelete(0);
                        comboAndItem.setXsjlid(createcombo.getXsjlid());
                        comboAndItem.setSfbj(item.getSfbj());
                        comboAndItem.setId(String.valueOf(snowflake.nextId()));
                        comboAndItem.setCreatedate(now);
                        comboAndItem.setModifydate(now);
                        comboanditemMapper.insert(comboAndItem);
                    }
                } else {
                    //新增子表数据
                    Comboanditem comboAndItem = new Comboanditem();
                    comboAndItem.setSfxmid(item.getSfxmid());
                    comboAndItem.setSort(item.getSort());
                    comboAndItem.setTcid(createcombo.getId());
                    comboAndItem.setIsDelete(0);
                    comboAndItem.setXsjlid(createcombo.getXsjlid());
                    comboAndItem.setSfbj(item.getSfbj());
                    comboAndItem.setId(String.valueOf(snowflake.nextId()));
                    comboAndItem.setCreatedate(now);
                    comboAndItem.setModifydate(now);
                    comboanditemMapper.insert(comboAndItem);
                }

            } else if (StringUtils.equals("added", item.get_state())) {
                Comboanditem comboAndItem = new Comboanditem();
                comboAndItem.setSfxmid(item.getSfxmid());
                comboAndItem.setSort(item.getSort());
                comboAndItem.setTcid(createcombo.getId());
                comboAndItem.setIsDelete(0);
                comboAndItem.setXsjlid(createcombo.getXsjlid());
                comboAndItem.setSfbj(item.getSfbj());
                comboAndItem.setId(String.valueOf(snowflake.nextId()));
                comboAndItem.setCreatedate(now);
                comboAndItem.setModifydate(now);
                comboanditemMapper.insert(comboAndItem);
            }
        };

        List<Comboanditem> csis = comboanditemMapper.selectList(new LambdaQueryWrapper<Comboanditem>()
                .eq(Comboanditem::getTcid, createcombo.getId())
                .eq(Comboanditem::getIsDelete, 0));
        Map<String, String> map = new HashMap<String, String>();
        for (Comboanditem csi : csis) {
            //设置项目为APP可见
            Items item = itemsMapper.selectById(csi.getSfxmid());
            if (Objects.isNull(item)) {
                throw new ServiceException("保存失败，收费项目不存在。");
            }
            if (StringUtils.isEmpty(item.getExamfeeitemCodex()) || !StringUtils.equals(item.getExamfeeitemCodex(), "1")) {
                item.setExamfeeitemCodex("1");
                item.setModifydate(now);
                itemsMapper.updateById(item);
            }

            List<InspectCharge> ics = inspectChargeMapper.selectList(new LambdaQueryWrapper<InspectCharge>()
                    .eq(InspectCharge::getChargeId, csi.getSfxmid())
                    .eq(InspectCharge::getIsDelete, 0));
            for (InspectCharge ic : ics) {
                String inspectId = ic.getInspectId();
                if (map.get(inspectId) == null) {
                    map.put(inspectId, csi.getSfxmid());
                } else {
                    Basexamltem e = basexamltemMapper.selectById(inspectId);
                    if (Objects.nonNull(e.getFCanDup()) && Objects.equals(e.getFCanDup(), 1)) {
                        continue;
                    }
                    Items i2 = itemsMapper.selectById(map.get(inspectId));
                    throw new ServiceException("保存失败！收费项目"
                            + ((item != null && i2 != null) ? (item.getExamfeeitemName() + "、" + i2.getExamfeeitemName()) : "")
                            + "下存在相同的检查项目" + (e != null ? e.getExamitemName() : ""));
                }
            }
        }
        //设置成本价
        createcombo.setTotalCostprice(createcomboMapper.getCostpriceByTcid(createcombo.getId()));
        createcomboMapper.updateById(createcombo);

        return Boolean.TRUE;
    }

    /**
     * 复制套餐
     *
     * @param param  新的属性参数
     * @param userNo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean copy(TcCopyParam param, String userNo) {
        Date now = new Date();
        Createcombo coDb = createcomboMapper.selectById(param.getTcId());
        if (Objects.isNull(coDb)) {
            throw new ServiceException("仿制失败，套餐已被删除！");
        }
        Createcombo coNew = new Createcombo();
        //设置数据
        mapperFacade.map(coDb, coNew);
        coNew.setBjzt(0);//编辑状态
        //2017-3-29if(coNew.getTjlx().intValue()==1){
        if (coNew.getTjlx().intValue() == 0) {//体检类型
            coNew.setTbzt(1);//同步状态
        } else {
            coNew.setTbzt(0);
        }
        coNew.setSyxb(param.getSyxb());//适用性别
        coNew.setJhys(param.getJhId());//接害因素
        coNew.setZytjlb(StringUtils.isEmpty(param.getZyValue()) ? null : (param.getZyValue()));//职业体检类别
        coNew.setTcysjg(null);//套餐原始价格
        coNew.setTczk(null);//套餐折扣
        coNew.setZhjg(null);//折后价格
        coNew.setCreatedate(now);
        coNew.setModifydate(now);
        coNew.setModifier(userNo);
        coNew.setXsjlid(userNo);

        List<Createcombo> comboData = createcomboMapper.selectList(new LambdaQueryWrapper<Createcombo>()
                .eq(Createcombo::getJhys, param.getJhId())
                .eq(Createcombo::getZytjlb, param.getZyValue()));
        if (CollectionUtil.isNotEmpty(comboData)) {
            throw new ServiceException("所选接害因素和职业体检类别类型的最小套餐已存在,不能重复添加,请重新选择！");
        }

        //数据保存
        coNew.setId(String.valueOf(snowflake.nextId()));
        createcomboMapper.insert(coNew);

        //操作套餐与分中心关联表,进行数据的保存
        String[] fzxId = coNew.getFzxid().split(",");
        //进行循环保存
        for (int i = 0; i < fzxId.length; i++) {
            Comboandfzx comboAndFzx = new Comboandfzx();
            comboAndFzx.setTcid(coNew.getId());
            comboAndFzx.setFzxid(fzxId[i]);
            comboAndFzx.setTbzt(0);
            comboAndFzx.setId(String.valueOf(snowflake.nextId()));
            comboAndFzx.setCreatedate(now);
            comboAndFzx.setModifydate(now);
            comboandfzxMapper.insert(comboAndFzx);
        }

        List<Comboanditem> list = comboanditemMapper.selectList(new LambdaQueryWrapper<Comboanditem>()
                .eq(Comboanditem::getTcid, param.getTcId())
                .eq(Comboanditem::getIsDelete, 0));
        //直接计算，不用减   折扣也不用那样算  直接优惠价就行
        Double money = 0.0;
        for (Comboanditem item : list) {
            Items items1 = itemsMapper.selectOne(new LambdaQueryWrapper<Items>()
                    .eq(Items::getId, item.getSfxmid())
                    .eq(Items::getIsDelete, 0));
            if (Objects.nonNull(items1) && Objects.equals(param.getSyxb(), items1.getXb()) || Objects.equals(2, items1.getXb())) {
                //保存
                Comboanditem comboAndItem = new Comboanditem();
                comboAndItem.setSfxmid(item.getSfxmid());
                comboAndItem.setSort(item.getSort());
                comboAndItem.setTcid(coNew.getId());
                comboAndItem.setIsDelete(0);
                comboAndItem.setXsjlid(coNew.getXsjlid());
                comboAndItem.setSfbj(item.getSfbj());
                comboAndItem.setId(String.valueOf(snowflake.nextId()));
                comboAndItem.setCreatedate(now);
                comboAndItem.setModifydate(now);
                comboanditemMapper.insert(comboAndItem);

                money = Arith.add(items1.getUnitprice(), money);//计算原价
            }
        }
        ;

        coNew.setTcysjg(money);
        createcomboMapper.updateById(coNew);

        return Boolean.TRUE;
    }

    /**
     * 根据接害因素和职业体检类别判断是否存在重复,存在重复不能进行保存
     *
     * @param param 判断的属性参数
     * @return
     */
    @Override
    public Boolean isRepeat(TcCopyParam param) {

        LambdaQueryWrapper<Createcombo> wrapper = new LambdaQueryWrapper<Createcombo>().eq(Createcombo::getJhys, param.getJhId())
                .eq(Createcombo::getZytjlb, param.getZyValue())
                .eq(Createcombo::getIsDelete, 0);
        if (StringUtils.isNotBlank(param.getTcId())) {
            wrapper.eq(Createcombo::getId, param.getTcId());
        }
        List<Createcombo> comboData = createcomboMapper.selectList(wrapper);

        if (CollectionUtil.isEmpty(comboData)) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    /**
     * 同步套餐
     *
     * @param comboIds 要同步的套餐id集合
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean synchronous(List<String> comboIds) {
        comboexamitemMapper.delete(new LambdaQueryWrapper<Comboexamitem>().in(Comboexamitem::getComboId, comboIds));
        //只同步职业、综合类型最小套餐
        //TODO (同一检查项目同一危害因素只维护一个，实际可能不同职业体检类型有多个)
        List<Comboexamitem> list = createcomboMapper.getSynchronous(comboIds);
        list.forEach(item -> {
            item.setId(String.valueOf(snowflake.nextId()));
            item.setCreatedate(new Date());
//            comboexamitemMapper.insert(item);
        });
        comboexamitemService.saveBatch(list);
        Createcombo createcombo = new Createcombo();
        createcombo.setBjzt(1);
        createcomboMapper.update(createcombo, new LambdaUpdateWrapper<Createcombo>().in(Createcombo::getId, comboIds));
        return Boolean.TRUE;
    }

    /**
     * 设置/取消活动套餐
     *
     * @param comboIds 要操作的套餐id集合
     * @param state    操作标识：1设置 0取消
     * @return
     */
    @Override
    public Boolean setActive(List<String> comboIds, Integer state) {
        List<Createcombo> combos = createcomboMapper.selectList(new LambdaQueryWrapper<Createcombo>()
                .in(Createcombo::getId, comboIds));
        for (Createcombo combo : combos) {
            if (state == 1 && StringUtils.isEmpty(combo.getAppTypeId())) {
                throw new ServiceException("套餐" + combo.getTjtcmc() + "没有设置小程序套餐分类，不能设为活动套餐。");
            }
            combo.setIsActive(state);
        }
        updateBatchById(combos);
        return Boolean.TRUE;
    }

    /**
     * 设置/取消推荐套餐
     *
     * @param comboIds 要操作的套餐id集合
     * @param state    操作标识：1设置 0取消
     * @return
     */
    @Override
    public Boolean setRecommend(List<String> comboIds, Integer state) {
        List<Createcombo> combos = createcomboMapper.selectList(new LambdaQueryWrapper<Createcombo>()
                .in(Createcombo::getId, comboIds));
        for (Createcombo combo : combos) {
            combo.setIsRecommended(state);
        }
        updateBatchById(combos);
        return Boolean.TRUE;
    }

    /**
     * 禁用/反禁用套餐
     *
     * @param comboIds 要操作的套餐id集合
     * @param state    操作标识：0反禁用 1禁用
     * @return
     */
    @Override
    public Boolean setBan(List<String> comboIds, Integer state) {
        List<Createcombo> combos = createcomboMapper.selectList(new LambdaQueryWrapper<Createcombo>()
                .in(Createcombo::getId, comboIds));
        for (Createcombo combo : combos) {
            combo.setIsBan(state);
        }
        updateBatchById(combos);
        return Boolean.TRUE;
    }

    /**
     * 根据输入套餐名称或拼音码分页查询
     *
     * @param page 分页参数
     * @param key  输入值
     * @return 所有数据
     */
    @Override
    public IPage<CoSimpleVo> getAutoCompleteData(PageParam<Createcombo> page, String key, Long userId) {
        //获取当前用户分中心ID
        SysUser sysUser = sysUserMapper.selectUserById(userId);
        IPage<CoSimpleVo> list = createcomboMapper.getAutoCompleteData(page, key, sysUser.getCid());
        return list;
    }

    /**
     * 获取存在于最小套餐内的收费项目
     *
     * @param jhys
     * @param idss
     * @param medicaltype
     * @return
     */
    @Override
    public String compareMinTcContent(String jhys, List<String> idss, String medicaltype) {
        return createcomboMapper.compareMinTcContent(jhys, idss, medicaltype);
    }

    /**
     * 新增套餐-体检套餐列表
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<DataAddMealVo> getListDataAddMeal(PageParam<DataAddMealVo> page, DataAddMealParam param) {
        //是否禁用
        param.setIsBan("0");
        //去空格大写
        if (ObjectUtils.isNotEmpty(param.getTjtcsrm())) {
            param.setTjtcsrm(param.getTjtcsrm().trim().toUpperCase());
        }
        IPage<DataAddMealVo> iPage = createcomboMapper.getListDataAddMeal(page, param);
        List<DataAddMealVo> list = iPage.getRecords();
        for (DataAddMealVo vo : list) {
            if (StringUtils.isNotBlank(vo.getJhys())){
                String harm = harmMapper.getHarmText(vo.getJhys().split(","));
                vo.setJhys(harm);
            }
        }
        return iPage;
    }


    /**
     * 获取和套餐关联的收费项目
     *
     * @param page
     * @param test
     * @return
     */
    @Override
    public IPage<SmallItemsVo> getSmallItems(PageParam<SmallItemsVo> page, String test) {
        return createcomboMapper.getSmallItems(page, test);
    }


    /**
     * 获取所有套餐
     *
     * @param key
     * @return
     */
    @Override
    public List<AllComboMealVo> getAllComboAndMealData(String key) {
        return createcomboMapper.getAllComboAndMealData(key);
    }


    /**
     * 获取所有基础套餐
     *
     * @param key
     * @return
     */
    @Override
    public List<BasicDataVo> getBasicData(String key) {
        return createcomboMapper.getBasicData(key);
    }


    /**
     * 批量添加分中心
     * @param fzxId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addFzx(String fzxId) {
        //新增分中心后，自动选择上体检基础套餐维护的分中心
        List<Createcombo> updateList = new ArrayList<>();
        List<Comboandfzx> comboandfzxList = new ArrayList<>();
        List<Createcombo> list = createcomboMapper.selectList(new LambdaQueryWrapper<Createcombo>()
                .eq(Createcombo::getIsDelete, 0));
        for (Createcombo createcombo : list) {
            String fzxid = createcombo.getFzxid();
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(fzxid.split(",")));
            boolean a = false;
            if (arrayList.contains(fzxId)){
                //包含跳过
            }else {
                //不包含添加
                arrayList.add(fzxId);
                a = true;
            }
            if (a){
                //添加到更新集合里面
                String joinedString = String.join(",", arrayList);
                createcombo.setFzxid(joinedString);
                updateList.add(createcombo);
                //套餐和分中心表
                Comboandfzx comboAndFzx = new Comboandfzx();
                comboAndFzx.setTcid(createcombo.getId());
                comboAndFzx.setFzxid(fzxId);
                comboAndFzx.setTbzt(0);
                comboandfzxList.add(comboAndFzx);
            }
        }
        //批量插入及更新
        comboandfzxService.saveBatch(comboandfzxList);
        return updateBatchById(updateList);
    }


    /**
     * 批量删除分中心
     * @param fzxId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteFzx(String fzxId) {
        //删除选择上体检基础套餐维护的分中心
        List<Createcombo> updateList = new ArrayList<>();
        List<String> tcIds = new ArrayList<>();
        List<Createcombo> list = createcomboMapper.selectList(new LambdaQueryWrapper<Createcombo>()
                .eq(Createcombo::getIsDelete, 0));
        for (Createcombo createcombo : list) {
            String fzxid = createcombo.getFzxid();
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(fzxid.split(",")));
            boolean a = false;
            //是否包含对应的分中心
            if (arrayList.contains(fzxId)){
                //包含删除
                arrayList.remove(fzxId);
                a = true;
            }

            if (a){
                //添加到更新集合里面
                String joinedString = String.join(",", arrayList);
                createcombo.setFzxid(joinedString);
                updateList.add(createcombo);
                tcIds.add(createcombo.getId());
            }
        }
        //删除套餐和分中心表
        comboandfzxService.remove(new LambdaQueryWrapper<Comboandfzx>()
                .in(Comboandfzx::getTcid,tcIds).eq(Comboandfzx::getFzxid,fzxId));
        return updateBatchById(updateList);
    }

    /**
     * 添加项目成本价合计
     * @return
     */
    @Override
    public Boolean addTotalCostprice() {
        List<Createcombo> list = createcomboMapper.selectList(new LambdaQueryWrapper<Createcombo>()
                .eq(Createcombo::getTotalCostprice, "")
                .or().isNull(Createcombo::getTotalCostprice));
        for (Createcombo createcombo : list) {
            Double costprice = createcomboMapper.getCostpriceByTcid(createcombo.getId());
            createcombo.setTotalCostprice(costprice);
            createcomboMapper.updateById(createcombo);
        }
        return Boolean.TRUE;
    }
}

