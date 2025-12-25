package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.dto.PriceAndFactPriceDto;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.Arith;
import com.center.medical.common.utils.MathUtil;
import com.center.medical.common.utils.Render;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.dto.ComboDataDto;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.dao.*;
import com.center.medical.data.bean.vo.ItemsVo;
import com.center.medical.data.service.ItemsAndFzxService;
import com.center.medical.sellcrm.bean.dto.CMSfxmDto;
import com.center.medical.sellcrm.bean.dto.CreatemealExportXyDto;
import com.center.medical.sellcrm.bean.dto.MealJsonSfxmDto;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.bean.param.*;
import com.center.medical.sellcrm.bean.vo.ZxtcsDataVo;
import com.center.medical.sellcrm.dao.*;
import com.center.medical.sellcrm.service.ComboandfzxService;
import com.center.medical.sellcrm.service.CreatemealService;
import com.center.medical.sellcrm.service.MealanditemService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 普通套餐表(Createmeal)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-14 19:21:19
 */
@Slf4j
@Service("createmealService")
@RequiredArgsConstructor
public class CreatemealServiceImpl extends ServiceImpl<CreatemealMapper, Createmeal> implements CreatemealService {

    private final CreatemealMapper createmealMapper;
    private final CreatecomboMapper createcomboMapper;
    private final SysUserMapper sysUserMapper;
    private final MealandfzxMapper mealandfzxMapper;
    private final HarmMapper harmMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final InspectChargeMapper inspectChargeMapper;
    private final ItemsMapper itemsMapper;
    private final BasexamltemMapper basexamltemMapper;
    private final PrinttypeMapper printtypeMapper;
    private final MapperFacade mapperFacade;
    private final MealanditemMapper mealanditemMapper;
    private final MealanditemService mealanditemService;
    private final ComboandfzxService comboandfzxService;
    private final SysBranchMapper sysBranchMapper;
    private final CreateorderMapper createorderMapper;
    private final OrderandcomboMapper orderandcomboMapper;
    private final ComboanditemMapper comboanditemMapper;
    private final ItemsAndFzxService itemsAndFzxService;
    private final BranchService branchService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    @Autowired
    private LoadProperties loadProperties;


    /**
     * 分页查询[普通套餐表]列表
     *
     * @param page  分页参数
     * @param param Createmeal查询参数
     * @return 分页数据
     */
    @Override
//    @PostScope(alias = "c.xsjlid")
    public IPage<Createmeal> getPage(PageParam<Createmeal> page, CreatemealParam param) {

        //获取当前登录用户分中心id
        Boolean isLeader = SecurityUtils.isLeader();
        boolean greatLeader = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        String userNo = SecurityUtils.getUserNo();
        if (greatLeader) {
            //决策管理可以看所有中心所有套餐
        } else if (isLeader) {
            //领导查他的下级数据
            List<String> lowerLevelIds = sysUserMapper.getLowerLevelId(SecurityUtils.getUserNo());
            lowerLevelIds.add(SecurityUtils.getUserNo());
            param.setLowerLevelIds(lowerLevelIds);
        } else {
            //非领导看自己的
            param.setUserNo(userNo);
        }

        IPage<Createmeal> iPage = createmealMapper.getPage(page, param);
        List<Createmeal> records = iPage.getRecords();
        for (Createmeal record : records) {
            if (ObjectUtils.isNotEmpty(record.getJhys())){
                String newHarm ="";
                //拼接危害因素
                List<Harm> harm = harmMapper.selectList(new QueryWrapper<Harm>().in("id", record.getJhys().split(",")));
                for (Harm harm1 : harm) {
                    //拼接危害因素名字
                    newHarm += harm1 == null ? "" : (harm1.getHarmName() + ",");

                }
                record.setJhysName(newHarm.substring(0, newHarm.length()-1));
            }
            //变动成本率 = 成本价 / 折后价格 保留两位小数
            if(ObjectUtils.isNotEmpty(record.getZhjg()) && record.getZhjg() != 0
                    && ObjectUtils.isNotEmpty(record.getTotalCostprice())){
                double costRate = Arith.div(record.getTotalCostprice(), record.getZhjg(), 4);
                double mul = Arith.mul(costRate, 100);
                record.setVarCostRate(mul + "%");
            }

            //只有财务权限才能看到成本价
            if (SecurityUtils.hasRole(RoleAuthName.CAIWU)){
                record.setCostprice(record.getTotalCostprice());
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
    public Createmeal getInfoById(String id) {
        return createmealMapper.getInfoById(id);
    }

    /**
     * 新增/编辑操作
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(MealSaOrUpParam param) {
        //快速赋值
        Createmeal createMeal = mapperFacade.map(param.getJson(), Createmeal.class);

        //创建套餐的折扣不可以低于用户设置的折扣权限
        //现在只有潍坊的校验折扣，其他的由财务校验变动成本率
        if (StringUtils.equals(loadProperties.name, "weifang")){
            Double tczk = createMeal.getTczk();
            Double zk = 0.0;
            SysUser user = sysUserMapper.getUserByNo(SecurityUtils.getUserNo());
            if (SecurityUtils.isLeader()){
                if (ObjectUtils.isEmpty(user.getLdiscount())){
                    throw new ServiceException("请联系管理员设置领导折扣！");
                }
                zk = user.getLdiscount();
            }else {
                if (ObjectUtils.isEmpty(user.getSdiscount())){
                    throw new ServiceException("请联系管理员设置折扣！");
                }
                zk = user.getSdiscount();
            }
            if (tczk < zk){
                throw new ServiceException("套餐折扣不能低于用户折扣！");
            }
        }

        //接收套餐返回id
        String tcId = "";
        //接收多个分中心id
        String[] fzxId = createMeal.getFzxid().split(",");
        if (StringUtils.isBlank(createMeal.getId())) {
            //套餐数据保存
            createMeal.setIsDelete(0);
            createMeal.setCombostate("0");
            createMeal.setXsjlid(SecurityUtils.getUserNo());
            createMeal.setBjzt(0);
            createMeal.setZkztw(0);
            //直接将名称设置为简称，因为一般操作都是直接复制名称到简称
            createMeal.setTjtcjc(createMeal.getTjtcmc());
            //保存
            save(createMeal);
            tcId = createMeal.getId();
            //进行循环保存
            for (int i = 0; i < fzxId.length; i++) {
                //套餐与分中心关联表
                Mealandfzx mealAndFzx = new Mealandfzx();
                mealAndFzx.setTcid(tcId);
                mealAndFzx.setFzxid(fzxId[i]);
                mealAndFzx.setTbzt(0);
                mealandfzxMapper.insert(mealAndFzx);
            }
            //打印日志
            log.info(new Date() + "," + SecurityUtils.getUsername() + "," + SecurityUtils.getCId() + "," + createMeal.getTjtcmc() + ",执行中");
        } else {
            //编辑
            //直接将名称设置为简称，因为一般操作都是直接复制名称到简称
            createMeal.setTjtcjc(createMeal.getTjtcmc());
            //套餐数据更新
            Createmeal createMea = createmealMapper.getInfoById(createMeal.getId());
            if (null != createMea) {
                //根据套餐id和分中心id拼串确定套餐与分中心关联表中的数据
                String[] fzxid = createMea.getFzxid().split(",");
                //删除关联表
                mealandfzxMapper.delete(new QueryWrapper<Mealandfzx>().eq("tcid", createMea.getId()).in("fzxid", fzxid));
                //更新
                createMeal.setModifydate(new Date());
                this.updateById(createMeal);
                tcId = createMea.getId();

                //重新保存套餐与分中心的数据
                String[] fzxMealId = createMeal.getFzxid().split(",");
                for (int i = 0; i < fzxMealId.length; i++) {
                    Mealandfzx mealAndFzx = new Mealandfzx();
                    mealAndFzx.setTcid(createMea.getId());
                    mealAndFzx.setFzxid(fzxMealId[i]);
                    mealAndFzx.setTbzt(2);
                    mealandfzxMapper.insert(mealAndFzx);
                }
                //打印日志
                log.info(new Date() + "," + SecurityUtils.getUsername() + "," + SecurityUtils.getCId() + "," + createMeal.getTjtcmc() + ",执行中");
            }
        }
        int fzxCount = fzxId.length;
        //操作套餐与收费项目关联表
        if (StringUtils.isNotEmpty(tcId)) {
            //收费项目中间表操作
            List<MealJsonSfxmDto> map = param.getJsonSfxm();
            //根据套餐id删除掉中间表中的记录
            long count = mealanditemMapper.selectCount(new QueryWrapper<Mealanditem>().eq("tcid", tcId));
            if (count > 0) {
                //执行删除操作
                mealanditemMapper.delete(new QueryWrapper<Mealanditem>().eq("tcid", tcId));
            }
            for (int i = 0; i < map.size(); i++) {
                MealJsonSfxmDto result = map.get(i);
                //执行添加操作
                Mealanditem meal = new Mealanditem();
                meal.setTcid(tcId);
                meal.setItemGroup(StringUtils.isEmpty(result.getGroup()) ? null
                        : Integer.parseInt(result.getGroup()));
                meal.setGroupType(StringUtils.isEmpty(result.getGroupType()) ? null
                        : Integer.parseInt(result.getGroupType()));
                meal.setSfxmid(result.getId());
                //优惠价
                meal.setPrice(StringUtils.isEmpty(result.getYhj()) ? null : Double.parseDouble((result.getYhj())));
                //设置项目为APP可见
                Items item = itemsMapper.getInfoById(result.getId());
                if (item == null) {
                    throw new ServiceException("保存失败，收费项目不存在。");
                }
                //判断收费项目的分中心是否满足套餐的分中心
                int itemsCount = itemsMapper.countByIdAndFzx(item.getId(),fzxId);
                if (itemsCount!=fzxCount){
                    throw new ServiceException("保存失败，收费项目:"+item.getExamfeeitemName()+"分中心与套餐的分中心不匹配!");
                }
                //是否在APP出现：1.是  0或null.否
                if (item.getExamfeeitemCodex() == null || !"1".equals(item.getExamfeeitemCodex())) {
                    item.setExamfeeitemCodex("1");
                    itemsMapper.updateById(item);
                }
                meal.setIsSystem(0);
                //是否备选
                if (Objects.nonNull(result.getSfbx())) {
                    meal.setSfbx(result.getSfbx());
                } else {
                    meal.setSfbx(0);
                }
                meal.setItemSort((result.getSort()) == null ? null : Integer.parseInt((result.getSort())));
                mealanditemMapper.insert(meal);
            }
        }
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
    @Transactional(rollbackFor = Exception.class)
    public Boolean repeat(TcCopyParam param, String userNo) {
        //取出属性
        String tcId = param.getTcId();
        Integer syxb = param.getSyxb();
        String jhys = param.getJhId();
        String zytjlb = param.getZyValue();

        String cftcId = "";
        //接收多个分中心id
        String[] fzxId;
        //普通套餐表
        Createmeal createMeal1 = createmealMapper.getInfoById(tcId);
        if (ObjectUtils.isNotEmpty(createMeal1)) {
            Createmeal createMeal2 = new Createmeal();
            createMeal2 = createMeal1;
            //销售经理换成复制的人，要不然在创建订单那里搜不到
            createMeal2.setXsjlid(userNo);
            createMeal2.setBjzt(0);
            //根据适用性别将性别进行修改
            createMeal2.setSyxb(syxb);
            createMeal2.setJhys(jhys);
            createMeal2.setZytjlb(StringUtils.isEmpty(zytjlb) ? null : Integer.parseInt(zytjlb));

            createMeal2.setTcysjg(null);
            createMeal2.setTczk(null);
            createMeal2.setZhjg(null);

            /**年龄设置为0-100*/
            createMeal2.setNlz("0");
            createMeal2.setNld("100");

            createMeal2.setZkztw(0);
            //插入
            createMeal2.setId(null);
            createmealMapper.insert(createMeal2);
            cftcId = createMeal2.getId();
            //操作套餐与分中心关联表,进行数据的保存
            fzxId = createMeal1.getFzxid().split(",");
            //进行循环保存
            for (int i = 0; i < fzxId.length; i++) {
                Mealandfzx mealAndFzx = new Mealandfzx();
                mealAndFzx.setTcid(cftcId);
                mealAndFzx.setFzxid(fzxId[i]);
                mealAndFzx.setTbzt(0);
                mealandfzxMapper.insert(mealAndFzx);
            }
            if (StringUtils.isNotEmpty(cftcId)) {
                //通过套餐id获取关联的收费项目
                List<Mealanditem> data = mealanditemMapper.selectList(new QueryWrapper<Mealanditem>().eq("tcid", tcId));
                Map<String, Map<String, Object>> priceData = new HashMap<String, Map<String, Object>>();//key:itemId,value:unitprice 新套餐所有收费项目 及价格
                List<ComboDataDto> zxtcData = null;//最小套餐
                //判断要重复什么类型的数据
                int sort = 1;
                if (syxb == 0) {
                    //重复类型为男性、通用的收费项目,把女性的去掉
                    for (Mealanditem mealAndItem : data) {
                        //根据中间表获取关联的收费项目信息,用于判断是否适合重复
                        Items items1 = itemsMapper.getInfoById(mealAndItem.getSfxmid());
                        if (ObjectUtils.isEmpty(items1) || (ObjectUtils.isNotEmpty(items1.getIsBan()) && items1.getIsBan() != 0)){
                            //这个项目被删掉了，或者被禁用了,就跳过
                            continue;
                        }
                        if (syxb.equals(items1.getXb()) || items1.getXb() == 2) {
                            //允许重复
                            Mealanditem mealAndItem1 = new Mealanditem();
                            mealAndItem1 = mealAndItem;
                            mealAndItem1.setTcid(cftcId);
                            mealAndItem1.setId(null);
                            //进行数据保存
                            mealanditemService.save(mealAndItem1);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("jg", items1.getUnitprice());
                            //禁止打折字段，0否 1禁止
                            map.put("flag", items1.getFDiscountdisabled());
                            if (mealAndItem1.getItemSort() != null && mealAndItem1.getItemSort() >= sort) {
                                sort = mealAndItem1.getItemSort() + 1;
                            }
                            priceData.put(items1.getId(), map);
                        }
                    }
                } else if (syxb == 1) {
                    //重复类型为女性、通用的收费项目,把男性的去掉
                    for (Mealanditem mealAndItem : data) {
                        //根据中间表获取关联的收费项目信息,用于判断是否适合重复
                        Items items1 = itemsMapper.getInfoById(mealAndItem.getSfxmid());
                        if (ObjectUtils.isEmpty(items1) || (ObjectUtils.isNotEmpty(items1.getIsBan()) && items1.getIsBan() != 0)){
                            //这个项目被删掉了，或者被禁用了,就跳过
                            continue;
                        }
                        if (syxb.equals(items1.getXb()) || items1.getXb() == 2) {
                            //允许重复
                            Mealanditem mealAndItem1 = new Mealanditem();
                            mealAndItem1 = mealAndItem;
                            mealAndItem1.setTcid(cftcId);
                            mealAndItem1.setId(null);
                            //进行数据保存
                            mealanditemService.save(mealAndItem1);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("jg", items1.getUnitprice());
                            map.put("flag", items1.getFDiscountdisabled());
                            priceData.put(items1.getId(), map);
                            if (mealAndItem1.getItemSort() != null && mealAndItem1.getItemSort() >= sort) {
                                sort = mealAndItem1.getItemSort() + 1;
                            }
                        }
                    }

                } else if (syxb == 2) {
                    //重复类型为通用,直接重复数据
                    for (Mealanditem mealAndItem : data) {
                        Items items1 = itemsMapper.getInfoById(mealAndItem.getSfxmid());
                        if (ObjectUtils.isEmpty(items1) || (ObjectUtils.isNotEmpty(items1.getIsBan()) && items1.getIsBan() != 0)){
                            //这个项目被删掉了，或者被禁用了,就跳过
                            continue;
                        }
                        if (syxb.equals(items1.getXb())) {
                            //允许重复
                            Mealanditem mealAndItem1 = new Mealanditem();
                            mealAndItem1 = mealAndItem;
                            mealAndItem1.setTcid(cftcId);
                            mealAndItem1.setId(null);
                            //进行数据保存
                            mealanditemService.save(mealAndItem1);
                            if (ObjectUtils.isNotEmpty(items1)) {
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("jg", items1.getUnitprice());
                                map.put("flag", items1.getFDiscountdisabled());
                                priceData.put(items1.getId(), map);
                                if (mealAndItem1.getItemSort() != null && mealAndItem1.getItemSort() >= sort) {
                                    sort = mealAndItem1.getItemSort() + 1;
                                }
                            }
                        }
                    }
                }
                if (StringUtils.isNotEmpty(zytjlb)) {
                    zxtcData = itemsMapper.getComboData(syxb, zytjlb, jhys);
                    for (ComboDataDto os : zxtcData) {
                        String itemId = os.getId();
                        if (priceData.get(itemId) == null) {
                            Double unitPrice = os.getUnitprice() == null ? 0 : os.getUnitprice();
                            Mealanditem mealAndItem1 = new Mealanditem();
                            mealAndItem1.setTcid(cftcId);
                            mealAndItem1.setIsSystem(1);
                            mealAndItem1.setSfbx(0);
                            mealAndItem1.setSfxmid(itemId);
                            mealAndItem1.setPrice(unitPrice);
                            mealAndItem1.setItemSort(sort++);
                            mealanditemService.save(mealAndItem1);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("jg", unitPrice);
                            map.put("flag", os.getFDiscountdisabled());
                            priceData.put(itemId, map);
                        }
                    }
                }
                double money = 0.0;
                double discount = 0.0;
                for (Map<String, Object> map : priceData.values()) {
                    Double unitPrice = map.get("jg") == null ? 0.0 : Double.parseDouble(map.get("jg").toString());
                    money = MathUtil.add(unitPrice, money);
                    if (map.get("flag") != null
                            && Double.parseDouble(map.get("flag").toString()) == 1) {
                        discount = MathUtil.add(discount, unitPrice);
                    }
                }
                createMeal2.setTcysjg(money);
                createmealMapper.updateById(createMeal2);
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 判断套餐能否编辑
     *
     * @param tcId
     * @return
     */
    @Override
    public String isEdit(String tcId) {
        //订单已提交或变更已提交或订单关联的套餐已审核时不能编辑
        String obj = createmealMapper.isEdit(tcId);
        String state = StringUtils.isEmpty(obj) ? "0" : obj.toString();
        return state;
    }

    /**
     * 判断套餐能否删除
     *
     * @param tcId
     * @return
     */
    @Override
    public String isRemove(List<String> tcId) {
        String state = "success";
        //被订单引用了就不能编辑
        for (String id : tcId) {
            String str = createmealMapper.isRemove(id);
            if (StringUtils.isNotEmpty(str)) {
                Createmeal createMeal = createmealMapper.getInfoById(id);
                state = "套餐<font color='red'>" + createMeal.getTjtcmc() + "</font>被订单<font color='red'>" + str + "</font>引用，不能删除！";
                break;
            }
        }
        return state;
    }

    /**
     * 根据接害因素和职业体检类别获取关联的收费项目,将数据返回
     *
     * @return
     */
    @Override
    public List<ItemsVo> getPpZxtcData(CreatecomboParam1 param) {
        return createcomboMapper.getPpZxtcData(param);
    }

    /**
     * 根据状态位判断套餐折扣
     *
     * @param discount 当前折扣
     * @param tczkId   套餐ID
     * @return
     */
    @Override
    public R<Boolean> onZk(Double discount, String tczkId) {
        //根据套餐id获取套餐后,修改套餐状态位
        Createmeal createMeal = createmealMapper.selectOne(new LambdaQueryWrapper<Createmeal>()
                .eq(Createmeal::getId, tczkId).eq(Createmeal::getIsDelete, 0));
        int zkzt = createMeal.getZkztw();
        if (0 == zkzt) {
            //与自己的折扣进行比较
            SysUser sysUser = sysUserMapper.selectUserById(SecurityUtils.getUserId());
            if (Objects.nonNull(sysUser)) {
                Double sdiscount = sysUser.getSdiscount();
                if (discount < sdiscount) {
                    return R.ok(false, "您能打的折扣范围为【" + sdiscount + "-10折】");
                }
            } else {
                throw new ServiceException("获取当前登录用户信息异常，请联系技术人员");
            }

        } else if (1 == zkzt) {
            //与领导折扣进行比较
            SysUser sysUser = sysUserMapper.selectUserById(SecurityUtils.getUserId());
            if (Objects.nonNull(sysUser)) {
                Double lDiscount = sysUser.getLdiscount();
                if (discount < lDiscount) {
                    return R.ok(false, "您能打的折扣范围为【" + lDiscount + "-10折】");
                }
            } else {
                throw new ServiceException("获取当前登录用户信息异常，请联系技术人员");
            }
        }
        return R.ok(true);
    }

    /**
     * 判断是否必检
     *
     * @return
     */
    @Override
    public List<Integer> getSfbj(CreatecomboParam1 param) {
        List<String> itemsIds = param.getItemsIds();
        List<Integer> list = new ArrayList<>();
        for (String itemsId : itemsIds) {
            Integer sfbj = createcomboMapper.getSfbj(itemsId, param.getJhId(), param.getZyValue());
            list.add(ObjectUtils.isEmpty(sfbj) ? 0 : sfbj);
        }
        return list;
    }

    /**
     * 禁用/反禁用套餐
     *
     * @param ids   操作的对象主键id集合
     * @param state 操作标识：0反禁用 1禁用
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean setBan(List<String> ids, Integer state) {
        //更新套餐
        LambdaUpdateWrapper<Createmeal> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.in(Createmeal::getId, ids)
                .set(Createmeal::getForbidden,state == 1 ? 1 : null)
                .set(Createmeal::getModifydate,new Date());
        createmealMapper.update(null, lambdaUpdateWrapper);


        //更新套餐关联项目
        // TOTO ？套餐反禁用后关联分中心需要设置编辑状态为0还是1
        Mealandfzx mealandfzx = new Mealandfzx();
        mealandfzx.setTbzt(state == 1 ? 0 : 1);
        mealandfzx.setModifydate(new Date());
        mealandfzxMapper.update(mealandfzx, new LambdaUpdateWrapper<Mealandfzx>().in(Mealandfzx::getTcid, ids));
        return Boolean.TRUE;
    }

    /**
     * 设置平安ID
     *
     * @param id       操作的套餐id
     * @param pinganId 平安ID
     * @return
     */
    @Override
    public Boolean setPinganId(String id, String pinganId) {
        Createmeal createmeal = createmealMapper.selectById(id);
        if (Objects.nonNull(createmeal)) {
            createmeal.setPinganId(pinganId);
            createmeal.setModifydate(new Date());
            createmealMapper.updateById(createmeal);
            return Boolean.TRUE;
        } else {
            throw new ServiceException("设置失败，套餐不存在!");
        }

    }

    /**
     * 获取普通套餐与最小套餐的数据
     *
     * @param param
     * @return
     */
    @Override
    public IPage<Createmeal> getTcData(PageParam<Createmeal> page, CreateorderParam param) {
        param.setUserId(SecurityUtils.getUserNo());
        //分页查询
        if (ObjectUtils.isNotEmpty(param.getKey())) {
            param.setKey(param.getKey().trim());
        }
        IPage<Createmeal> iPage = createmealMapper.getTcData(page, param);
        List<Createmeal> records = iPage.getRecords();
        //拼接接害因素名称
        String jhName = "";
        //遍历分页对象
        for (Createmeal record : records) {
            //接害因素不为空
            if (StringUtils.isNotEmpty(record.getJhys())) {
                String[] jhysData = record.getJhys().split(",");
                for (int j = 0; j < jhysData.length; j++) {
                    Harm harm = harmMapper.getInfoById(jhysData[j]);
                    //拼接接害因素名称
                    if (harm != null) {
                        jhName += harm.getHarmName() + ",";
                    }
                }
                record.setJhysName("".equals(jhName) ? "" : jhName.substring(0, jhName.length() - 1));
                jhName = "";
            }
        }
        iPage.setRecords(records);
        return iPage;
    }

    /**
     * 返回客户从未使用过的套餐和客户单位电话
     *
     * @param khdwdhId
     * @param ids
     * @return
     */
    @Override
    public List<Createmeal> getKhdwdhAndTcs(String khdwdhId, List<String> ids) {
        Sellcustomer sellCustomer = sellcustomerMapper.getInfoById(khdwdhId);
//        Double seasonZk = getSeasonZk();
        String jhName = "";
        List<Createmeal> data = new ArrayList();
        List<Createmeal> cms = createmealMapper.getKhdwdhAndTcs(khdwdhId, ids, null);
        for (Createmeal createMeal : cms) {
            createMeal.setKhdwmc(sellCustomer.getKhdwmc());
            createMeal.setPhone(sellCustomer.getKhdh());
            data.add(createMeal);
        }
        return data;
    }


    /**
     * 获取当前季度最低折扣
     * 来判断套餐的折扣如果在规定时间内低于相应折扣，则该套餐不允许加入到订单中
     *
     * @return
     */
    public static Double getSeasonZk() {
        Double discount = null;
        try {
            Calendar now = Calendar.getInstance();
            int month = now.get(Calendar.MONTH) + 1;
            Map<Object, Object> conf = new HashMap<>();
            conf.put("1-3", 3);
            conf.put("4-6", 4);
            conf.put("7-9", 5);
            conf.put("10-12", 6);
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
     * 检查添加的收费项目下是否有检查项目重复,给予提示
     *
     * @param itemId
     * @return
     */
    @Override
    public String compareItemsToExam(List<String> itemId) {
        if (itemId.size() == 0) return "";
        StringBuffer text = new StringBuffer();
        Map<String, String> map = new HashMap<String, String>();
        //JC检查项目收费项目关联表
        List<InspectCharge> inspectCharges = inspectChargeMapper.selectList(new QueryWrapper<InspectCharge>()
                .in("charge_id", itemId).eq("is_delete", 0));
        //集合转数组
        //遍历
        for (InspectCharge inspectCharge : inspectCharges) {
            List<String> items = inspectChargeMapper.getRepeatItems(inspectCharge.getInspectId(), itemId);
            if (items.size() <= 1) {
                continue;
            }
            StringBuffer str2 = new StringBuffer();
            for (int i = 0; i < items.size(); i++) {
                Items items2 = itemsMapper.getInfoById(items.get(i));
                if (null != items2) {
                    //收费项目名称
                    str2.append(items2.getExamfeeitemName() + "、");
                }
            }
            String res = str2.toString().substring(0, str2.toString().length() - 1);
            //JC检查项目表
            Basexamltem exanm = basexamltemMapper.getInfoById(inspectCharge.getInspectId());
            String jcxmName = "";
            if (null != exanm) {
                //检查项目名称
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

    /**
     * 获取普通套餐关联的收费项目
     *
     * @param list
     * @return
     */
    @Override
    public List<Map> getDatas(List<Comboanditem> list) {
        List data = new ArrayList();
        Set<String> check = new HashSet<String>();
        for (int i = 0; i < list.size(); i++) {
            Map result = new HashMap();
            //收费项目ID
            String sfxmid = list.get(i).getSfxmid();
            if (check.contains(sfxmid)) {
                continue;
            } else {
                check.add(sfxmid);
            }
            Items items = itemsMapper.getInfoById(sfxmid);
            if (items != null) {
                result.put("costprice", items.getCostprice());
                result.put("id", items.getId());
                result.put("sfxmid", items.getId());
                result.put("sfxmmc", items.getExamfeeitemName());
                result.put("sfxmsrm", items.getSfxmsrm());
                result.put("xb", items.getXb());
                result.put("jcyy", items.getJcyy());
                result.put("jg", items.getUnitprice());
                result.put("bz", items.getBz());
                result.put("tjlx", items.getTjlx());
                result.put("ssks", items.getDepartName());
                result.put("idKs", items.getIdDepart());
                result.put("sycstj", null == items.getJccs() ? 0 : items.getJccs());
                result.put("fDiscountdisabled", items.getFDiscountdisabled());
                String xsdyfl = items.getXsdyfl();
                if (xsdyfl != null) {
                    //销售打印分类设置
                    Printtype pt = printtypeMapper.getInfoById(xsdyfl);
                    if (pt != null) {
                        result.put("xsdyfl", pt.getPrintName());
                        result.put("dysx", pt.getShunxu());
                    }
                }
                data.add(result);
            }
        }
        return data;
    }

    /**
     * 加载所有最小套餐按照分中心
     *
     * @param param
     * @return
     */
    @Override
    public IPage<ZxtcsDataVo> getZxtcsData(PageParam<ZxtcsDataVo> page, ZxtcsDataParam param) {
        //设置分中心
        if (StringUtils.isBlank(param.getFzxid())){
            param.setFzxid(SecurityUtils.getCId());
        }
        IPage<ZxtcsDataVo> iPage = createcomboMapper.getZxtcsData(page, param);
        return iPage;
    }


    /**
     * 保存前判断是否重复
     *
     * @param jhysValue
     * @param zytjlbValue
     * @param tcid
     * @return
     */
    @Override
    public String isReport(String jhysValue, String zytjlbValue, String tcid) {
        String state = "success";
        long i = 0;
        if (StringUtils.isNotBlank(tcid)) {
            i = createcomboMapper.selectCount(new QueryWrapper<Createcombo>()
                    .eq("jhys", jhysValue)
                    .eq("zytjlb", zytjlbValue)
                    .ne("id", tcid)
                    .eq("is_delete", 0));
        } else {
            i = createcomboMapper.selectCount(new QueryWrapper<Createcombo>()
                    .eq("jhys", jhysValue)
                    .eq("zytjlb", zytjlbValue)
                    .eq("is_delete", 0));
        }
        if (i > 0) {
            //即将要保存的数据在数据库中已经存在,不能再填加
            state = "error";
        }
        return state;
    }

    /**
     * 套餐导出
     *
     * @param tcIds 选择导出的套餐ID集合
     */
    @SneakyThrows
    @Override
    public void getExportData(HttpServletResponse response,List<String> tcIds) {
        //查询数据
        Map<String ,Object> ac = new HashMap<String, Object>();
        //套餐
        List<Createmeal> cms = createmealMapper.selectList(new QueryWrapper<Createmeal>().in("id", tcIds));
        String khdwmc="";//客户单位名称   （随便取一个）
        List<Map<String,Object>> list = new ArrayList();//所有套餐
        Map<String,List<String>> qtdata = new HashMap<String, List<String>>();//其他（以下项目不参与折扣统计） item_id:list<tcid>
        for(Createmeal meal:cms){
            if("".equals(khdwmc)&&meal.getKhdwmcid()!=null){
                Sellcustomer sc = sellcustomerMapper.getInfoById(meal.getKhdwmcid());
                if(sc!=null){
                    khdwmc = sc.getKhdwmc();
                }
            }
            Map<String,Object> map = BeanUtil.beanToMap(meal);
            String tcid = meal.getId();
            //普通套餐与收费项目关联表
            List<Mealanditem> mdata = mealanditemMapper.selectList(new QueryWrapper<Mealanditem>().eq("tcid", tcid));
            double hj=0.0;
            double zkyhj = ObjectUtils.isNotEmpty(meal.getZhjg())?meal.getZhjg():0.0;
            for(Mealanditem mealAndItem:mdata){
                String item_id = mealAndItem.getSfxmid();
                Items items = itemsMapper.getInfoById(item_id);
                if(null!=items){
                    //禁止打折
                    double dis = items.getFDiscountdisabled()==null?0:items.getFDiscountdisabled();
                    if(dis==1){
                        zkyhj-=items.getUnitprice();
                        if(qtdata.get(item_id)==null){
                            List<String> tcids = new ArrayList<String>();
                            tcids.add(tcid);
                            qtdata.put(item_id, tcids);
                        }else{
                            qtdata.get(item_id).add(tcid);
                        }
                    }else{
                        hj+=items.getUnitprice();
                    }
                }
            }
            map.put("hj", hj+"");
            map.put("zkyhj", zkyhj+"");
            list.add(map);
        }
        ac.put("tcjcList", list);
        StringBuilder mealIds=new StringBuilder();
        for(String tcid:tcIds){
            if(mealIds.length()!=0){
                mealIds.append(",");
            }
            mealIds.append("'"+tcid+"'");
        }

        //获取相关的收费项目
        List<CMSfxmDto> itemData = createmealMapper.getSfxm(String.valueOf(mealIds));
        List<Map<Object,List>> data = new ArrayList<Map<Object,List>>();
        for(int i=0;i<itemData.size();i++){
            //将打印分类进行存储
            CMSfxmDto obj = itemData.get(i);
            Map<Object,List> map = new HashMap<Object, List>();
            //获取收费项目id
            String[] itemid = obj.getItemId().split(",");
            //获取收费项目
            String[] sfxm = obj.getSfxm().split(",");
            List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
            //解决去重
            List qc = new ArrayList();
            int b=0;
            for(int j=0;j<sfxm.length;j++){
                if(qc.size()!=0){
                    //判断是否存在相同的收费项目id
                    for(int m=0;m<qc.size();m++){
                        b=0;
                        if(itemid[j].equals(qc.get(m))){
                            //存在相同的收费项目id
                            b=1;
                            break;
                        }
                    }
                    if(b==0){
                        //根据收费项目id获取收费实体
                        Items item = itemsMapper.getInfoById(itemid[j]);
                        if(item!=null){
                            Map<String,Object> m2=new HashMap<String, Object>();
                            m2.put("itemName", item.getExamfeeitemNameprn());//收费项目名称
                            m2.put("checkYy", item.getJcyy());//检查意义
                            m2.put("itemid", itemid[j]);//收费项目id
                            m2.put("price", item.getUnitprice());//收费项目单价
                            m2.put("tjlx", Render.getTjlx(item.getTjlx()));//体检类型
                            items.add(m2);
                        }
                        qc.add(itemid[j]);
                        b = 0;
                    }else{
                        continue;
                    }
                }else{
                    Map<String,Object> m2=new HashMap<String, Object>();
                    Items item = itemsMapper.getInfoById(itemid[j]);
                    m2.put("itemName", item.getExamfeeitemNameprn());//收费项目名称
                    m2.put("checkYy", item.getJcyy());//检查意义
                    m2.put("itemid", itemid[j]);//收费项目id
                    m2.put("price", item.getUnitprice());//收费项目单价
                    m2.put("tjlx", Render.getTjlx(item.getTjlx()));//体检类型
                    items.add(m2);
                    qc.add(itemid[j]);
                }
            }
            map.put(obj.getFz(), items);
            data.add(map);
        }
        ac.put("sfxmData", data);

        /*************导出************************************/
        int cellLenght=list.size()+4;//项目 体检类型 检查目的 所有套餐 单位
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("sheet1");
        sheet.setDefaultColumnWidth(8);//默认行宽
        sheet.setDefaultRowHeight((short)746);//默认行高

        HSSFFont font_title = hwb.createFont();//默认字体1(大标题 加粗  14号)
        font_title.setFontName("宋体"); //字体
        font_title.setBold(true);
//       font_title.setBoldweight((short)1);//加粗
        font_title.setFontHeightInPoints((short) 14);// 设置字体大小

        HSSFFont font_bold = hwb.createFont();//默认字体3(收费项目标题 加粗  12号)
        font_bold.setFontName("宋体"); //字体
        font_title.setBold(true);
//       font_bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
        font_bold.setFontHeightInPoints((short) 12);// 设置字体大小

        HSSFFont font_normal_11 = hwb.createFont();//默认字体2 (11号)
        font_normal_11.setFontName("宋体"); //字体
        font_normal_11.setFontHeightInPoints((short) 11);// 设置字体大小

        HSSFFont font_normal_14 = hwb.createFont();//默认字体4（14号）
        font_normal_14.setFontName("宋体"); //字体
        font_normal_14.setFontHeightInPoints((short) 14);// 设置字体大小

        HSSFCellStyle cellStyle_bold = hwb.createCellStyle();//单元格格式1
        cellStyle_bold.setFont(font_bold);
        cellStyle_bold.setAlignment(HorizontalAlignment.CENTER); //水平布局：居中
        cellStyle_bold.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle_bold.setBorderBottom(BorderStyle.THIN);// 设置边框
        cellStyle_bold.setBorderLeft(BorderStyle.THIN);
        cellStyle_bold.setBorderRight(BorderStyle.THIN);
        cellStyle_bold.setBorderTop(BorderStyle.THIN);
        cellStyle_bold.setWrapText(true);  // 自动换行

        HSSFCellStyle cellStyle_title = hwb.createCellStyle();//单元格格式2
        cellStyle_title.cloneStyleFrom(cellStyle_bold);
        cellStyle_title.setFont(font_title);

        HSSFCellStyle cellStyle_normal_14 = hwb.createCellStyle();//单元格格式3
        cellStyle_normal_14.cloneStyleFrom(cellStyle_bold);
        cellStyle_normal_14.setFont(font_normal_14);

        HSSFCellStyle cellStyle_normal_11 = hwb.createCellStyle();//单元格格式4
        cellStyle_normal_11.cloneStyleFrom(cellStyle_bold);
        cellStyle_normal_11.setFont(font_normal_11);

        HSSFCellStyle cellStyle_background = hwb.createCellStyle();//单元格格式5
        cellStyle_background.cloneStyleFrom(cellStyle_bold);
        cellStyle_background.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//背景色
        cellStyle_background.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        HSSFCellStyle cellStyle_other=hwb.createCellStyle();//单元格格式6
        cellStyle_other.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//背景色
        cellStyle_other.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        for(int i=0;i<23+cellLenght;i++){
            sheet.setDefaultColumnStyle(i, cellStyle_other);
        }

        int row_num=0;

        HSSFRow row = sheet.createRow(row_num);
        mergeCell(sheet, row, cellStyle_bold, row_num, cellLenght);
        HSSFCell cell =row.getCell(0);
//        BufferedImage bufferImg = null;
//        String full_path = "https://img2.baidu.com/it/u=920514027,2384439154&fm=253&fmt=auto&app=138&f=JPEG?w=490&h=490";
//        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//        bufferImg = ImageIO.read(new File(full_path));
//        ImageIO.write(bufferImg, "jpg", byteArrayOut);
        //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
//        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//        //anchor主要用于设置图片的属性
//        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1000, 255,(short) 0, 0, (short) 0, 0);
//        anchor.setAnchorType(ClientAnchor.AnchorType.byId(3));
//        //插入图片
//        patriarch.createPicture(anchor, hwb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        cell.setCellValue(khdwmc);
        row_num++;

        row = sheet.createRow(row_num);
        cell =row.createCell(0);
        cell.setCellValue("项目");
        cell.setCellStyle(cellStyle_bold);

        cell =row.createCell(1);
        cell.setCellValue("体检类型");
        cell.setCellStyle(cellStyle_bold);

        cell =row.createCell(2);
        cell.setCellValue("检查目的");
        cell.setCellStyle(cellStyle_bold);

        for(int i=3;i<cellLenght-1;i++){
            cell =row.createCell(i);
            Map<String,Object> map=list.get(i-3);
            String cellValue=String.valueOf(map.get("tjtcjc"));
            cell.setCellValue(cellValue);
            cell.setCellStyle(cellStyle_bold);
        }
        cell =row.createCell(cellLenght-1);
        cell.setCellValue("单价(元)");
        cell.setCellStyle(cellStyle_bold);
        row_num++;

        for(Map<Object ,List> map:data){
            for(Map.Entry<Object,List> entry:map.entrySet()){
                row = sheet.createRow(row_num);
                mergeCell(sheet, row, cellStyle_background, row_num, cellLenght);
                cell =row.getCell(0);
                cell.setCellValue(entry.getKey().toString());
                row_num++;
                List<Map<String,Object>> list_in=entry.getValue();
                for(Map<String,Object> map_in:list_in){
                    row = sheet.createRow(row_num);
                    cell =row.createCell(0);
                    cell.setCellValue(map_in.get("itemName").toString());
                    cell.setCellStyle(cellStyle_normal_11);

                    cell =row.createCell(1);
                    cell.setCellValue(map_in.get("tjlx").toString());
                    cell.setCellStyle(cellStyle_normal_11);

                    cell =row.createCell(2);
                    String cellValue=map_in.get("checkYy")==null?"":map_in.get("checkYy").toString();
                    cell.setCellValue(cellValue);
                    cell.setCellStyle(cellStyle_normal_11);

                    int row_in_num=(int)Math.ceil(cellValue.getBytes("gbk").length/60.0);
                    if(row_in_num>2){
                        row.setHeight((short)(row_in_num*373));
                    }

                    String itemid=map_in.get("itemid").toString();
                    for(int i=0,l=list.size();i<l;i++){//套餐
                        Map<String,Object> map_create=list.get(i);
                        int mou=0;
                        //根据套餐id获取该套餐下含有的收费项目
                        List<Mealanditem> mealData = mealanditemMapper.selectList(new QueryWrapper<Mealanditem>().eq("tcid", map_create.get("id")));
                        for(Mealanditem meal:mealData){
                            //判断是否含有该收费项目
                            if(meal.getSfxmid().equals(itemid)){
                                mou=1;
                                cell =row.createCell(3+i);
                                cell.setCellValue("★");
                                cell.setCellStyle(cellStyle_bold);
                                break;
                            }
                        }
                        if(mou==0){
                            cell =row.createCell(3+i);
                            cell.setCellStyle(cellStyle_bold);
                        }
                    }

                    cell =row.createCell(3+list.size());
                    cell.setCellValue(map_in.get("price").toString());
                    cell.setCellStyle(cellStyle_bold);
                    row_num++;
                }
            }
        }

        row = sheet.createRow(row_num);
        mergeCell(sheet, row, cellStyle_background, row_num, cellLenght);
        cell =row.getCell(0);
        cell.setCellValue("其他(以下项目不参与折扣统计)");
        row_num++;

        for(String sfid:qtdata.keySet()){
            Items items = itemsMapper.getInfoById(sfid);
            String sfxmmc = items.getExamfeeitemNameprn();
            String jcyy = items.getJcyy();
            double jg = items.getUnitprice();

            row = sheet.createRow(row_num);
            cell =row.createCell(0);
            cell.setCellValue(sfxmmc);
            cell.setCellStyle(cellStyle_normal_11);
            // 体检类型列
            cell =row.createCell(1);
            cell.setCellValue(Render.getTjlx(items.getTjlx()));
            cell.setCellStyle(cellStyle_normal_11);
            // 检查目的列
            cell =row.createCell(2);
            cell.setCellValue(jcyy);
            cell.setCellStyle(cellStyle_normal_11);
            for(int i=0,l=list.size();i<l;i++){
                String tcId=String.valueOf(list.get(i).get("id"));
                cell =row.createCell(3+i);
                if(qtdata.get(sfid).contains(tcId)){
                    cell.setCellValue("★");
                }
                cell.setCellStyle(cellStyle_bold);
            }
            cell =row.createCell(cellLenght-1);
            cell.setCellValue(jg);
            cell.setCellStyle(cellStyle_bold);
            row_num++;
        }

        row = sheet.createRow(row_num);
        cell =row.createCell(0);
        cell.setCellValue("原价");
        cell.setCellStyle(cellStyle_normal_11);
        cell =row.createCell(1);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle_normal_11);
        cell =row.createCell(2);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle_normal_11);
        for(int i=0,l=list.size();i<l;i++){
            cell =row.createCell(3+i);
            cell.setCellValue(null == (list.get(i).get("tcysjg"))?0
                    :Double.parseDouble(String.valueOf(list.get(i).get("tcysjg"))));
            cell.setCellStyle(cellStyle_normal_11);
        }
        cell =row.createCell(cellLenght-1);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle_bold);
        row_num++;

        row = sheet.createRow(row_num);
        cell =row.createCell(0);
        cell.setCellValue("折扣");
        cell.setCellStyle(cellStyle_normal_11);
        cell =row.createCell(1);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle_normal_11);
        cell =row.createCell(2);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle_normal_11);
        for(int i=0,l=list.size();i<l;i++){
            cell =row.createCell(3+i);
            cell.setCellValue(null == (list.get(i).get("tczk"))?0
                    :Double.parseDouble(String.valueOf(list.get(i).get("tczk"))));
            cell.setCellStyle(cellStyle_normal_11);
        }
        cell =row.createCell(cellLenght-1);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle_bold);
        row_num++;

        row = sheet.createRow(row_num);
        cell =row.createCell(0);
        cell.setCellValue("最终优惠价格");
        cell.setCellStyle(cellStyle_normal_11);
        cell =row.createCell(1);
        cell.setCellValue("折扣价+合计");
        cell.setCellStyle(cellStyle_normal_11);
        cell =row.createCell(2);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle_normal_11);
        for(int i=0,l=list.size();i<l;i++){
            cell =row.createCell(3+i);
            cell.setCellValue("null".equals(list.get(i).get("zhjg"))?0:Double.parseDouble(String.valueOf(list.get(i).get("zhjg"))));
            cell.setCellStyle(cellStyle_normal_11);
        }
        cell =row.createCell(cellLenght-1);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle_bold);

        sheet.setColumnWidth(0,4287);
        sheet.setColumnWidth(1,4287); // 体检类型列
        sheet.setColumnWidth(2,17000); // 检查目的列
        for(int i=3;i<cellLenght;i++){
            sheet.setColumnWidth(i,1842);
            sheet.autoSizeColumn((short)i);//自动调整列宽
        }


        OutputStream ouputStream =null;
        try {
            ouputStream=response.getOutputStream();
            hwb.write(ouputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(ouputStream!=null){
                ouputStream.flush();
                ouputStream.close();
            }
            if(hwb!=null) {
                hwb.close();
            }
        }
        /*************导出结束************************************/
    }

    private void mergeCell(HSSFSheet sheet,HSSFRow row,HSSFCellStyle style,int rowNum,int cellNum){
        for(int i=0;i<cellNum;i++){
            HSSFCell cell=row.createCell(i);
            cell.setCellStyle(style);
        }
        sheet.addMergedRegion(new CellRangeAddress(rowNum,rowNum,(short)0,(short)(cellNum-1)));//合并单元格
    }


    /**
     * 导出协议套餐
     *
     * @param tcId 选择的套餐ID
     */
    @Override
    public List<CreatemealExportXyDto> getExportXyData(List<String> tcId,String orderId) {
        List<CreatemealExportXyDto> list = createmealMapper.getExportXyData(tcId, orderId);
        for (int i = 0; i < list.size(); i++) {
            CreatemealExportXyDto dto = list.get(i);
            //序号
            dto.setRownum(i + 1);

            //接害因素名称
            String jhysIds = dto.getJhys();
            if (StringUtils.isNotEmpty(jhysIds)) {
                List<Harm> harms = harmMapper.selectList(new QueryWrapper<Harm>().in("id", jhysIds.split(",")));
                List<String> harmNameList = new ArrayList<>();
                for (Harm harm : harms) {
                    harmNameList.add(harm.getHarmName());
                }
                String jhysName = StringUtils.join(harmNameList, "、");
                dto.setJhysName(jhysName);
            }

            //体检类型名称
            Integer medicaltype = StringUtils.isNotEmpty(dto.getZytjlb()) ? Integer.valueOf(dto.getZytjlb()) : null;
            String tjlxName = Render.getMedicalType(medicaltype);
            dto.setTjlxName(tjlxName);

            //职业体检项目
            if (StringUtils.isNotEmpty(jhysIds) && ObjectUtils.isNotEmpty(medicaltype)) {
                /**改成本套餐内的职业项目*/
                List<String> medicalItemsList = null;
                String[] harmIdsSql = jhysIds.split(",");
                if ("0".equals(dto.getNum())) {
                    //普通套餐表查询职业项目
                    medicalItemsList = createmealMapper.findMedicalItems(dto.getId(), harmIdsSql, medicaltype);
                } else {
                    //最小套餐查询职业项目
                    medicalItemsList = createmealMapper.findMedicalItems2(dto.getId(), harmIdsSql, medicaltype);
                }
                //set去重
                Set<String> zyitemNames = new HashSet<>();
                for (int n = 0; n < medicalItemsList.size(); n++) {
                    zyitemNames.add(medicalItemsList.get(n));
                }
                String zyItems = StringUtils.join(zyitemNames, "、");
                dto.setZyItems(zyItems);
            }

            //增加健康项目
            String jkItems = "";
            String idExamtype = dto.getTjlx() == null ? "" : dto.getTjlx().toString();
            String combostate = dto.getNum().toString();
            String[] jhysStr = StringUtils.isNotEmpty(jhysIds) ? jhysIds.split(",") : null;
            if ("1".equals(idExamtype)) {
                jkItems = "无";
            } else if ("2".equals(idExamtype)) {
                if ("0".equals(combostate)) {
                    jkItems = createmealMapper.getJxItemsStr1(jhysStr, dto.getZytjlb(), dto.getId());
                } else {
                    jkItems = createmealMapper.getJxItemsStr2(jhysStr, dto.getZytjlb(), dto.getId());
                }
            } else if ("0".equals(idExamtype)) {
                if ("0".equals(combostate)) {
                    jkItems = createmealMapper.getJxItemsStrById1(dto.getId());
                } else {
                    jkItems = createmealMapper.getJxItemsStrById2(dto.getId());
                }
            }
            dto.setJkItems(jkItems);
        }

        return list;
    }


    /**
     * 导出订单的套餐
     * @param response
     * @param orderId
     */
    @Override
    public void getExportTc(HttpServletResponse response, String orderId) throws IOException {
        //查询数据
        Createorder createOrder = createorderMapper.getInfoById(orderId);
        Map<String ,Object> ac = new HashMap<String, Object>();
        //获取客户实体
        Sellcustomer sc = sellcustomerMapper.getInfoById(createOrder.getKhdwmcid());
        String khdwmc = sc.getKhdwmc();
        List<Map<String,String>> list = new ArrayList();//所有套餐
        Map<String,List<String>> qtdata = new HashMap<String, List<String>>();//其他（以下项目不参与折扣统计） item_id:list<tcid>
        List<Orderandcombo> oacs = orderandcomboMapper.selectList(new QueryWrapper<Orderandcombo>().eq("ddid",orderId));
        for(Orderandcombo oac:oacs){
            String tcid=oac.getTcid();
            if("0".equals(oac.getCombostate())){
                Createmeal meal = createmealMapper.getInfoById(tcid);
                if(meal!=null){
                    Map<String,String> map= JSON.parseObject(JSON.toJSONString(meal), new TypeReference<Map<String, String>>() {});
                    map.put("class", "CreateMeal");

                    List<Mealanditem> mdata = mealanditemMapper.selectList(new QueryWrapper<Mealanditem>().eq("tcid", tcid));
                    double hj=0.0;//合计-不参与折扣
                    double zkyhj=meal.getZhjg();//优惠价-不参与折扣
                    for(Mealanditem mealAndItem:mdata){
                        String item_id=mealAndItem.getSfxmid();
                        Items items = itemsMapper.getInfoById(item_id);
                        if(null!=items){
                            double dis=items.getFDiscountdisabled()==null?0:items.getFDiscountdisabled();
                            if(dis==1){
                                if(qtdata.get(item_id)==null){
                                    List<String> tcids=new ArrayList<String>();
                                    tcids.add(tcid);
                                    qtdata.put(item_id, tcids);
                                }else{
                                    qtdata.get(item_id).add(tcid);
                                }
                                zkyhj-=items.getUnitprice();
                            }else{
                                hj+=items.getUnitprice();
                            }
                        }
                    }
                    map.put("hj", hj+"");
                    map.put("zkyhj", zkyhj+"");
                    list.add(map);
                }
            }else{
                Createcombo combo = createcomboMapper.getInfoById(oac.getTcid());
                if(combo!=null){
                    Map<String,String> map = JSON.parseObject(JSON.toJSONString(combo), new TypeReference<Map<String, String>>() {});
                    map.put("class", "CreateCombo");

                    //从最小套餐与收费项目关联表获取数据
                    List<Comboanditem> mdata = comboanditemMapper.selectList(new QueryWrapper<Comboanditem>().eq("tcid", tcid));
                    double hj=0.0;
                    for(Comboanditem comboAndItem:mdata){
                        String item_id = comboAndItem.getSfxmid();
                        Items items = itemsMapper.getInfoById(item_id);
//                         String item_name=items.getExamfeeitemName();
                        if(null!=items){
                            if(items.getFDiscountdisabled()!=null&&items.getFDiscountdisabled().intValue()==1
//                            		 item_name.equals("静脉采血")
//                            		 ||item_name.equals("早餐(以上项目还未全部完成的顾客，请勿饮水用餐)")
//                            		 ||item_name.equals("个检报告工本费")
                            ){
                                if(qtdata.get(item_id)==null){
                                    List<String> tcids=new ArrayList<String>();
                                    tcids.add(tcid);
                                    qtdata.put(item_id, tcids);
                                }else{
                                    qtdata.get(item_id).add(tcid);
                                }
                            }else{
                                hj+=items.getUnitprice();
                            }
                        }
                    }
                    map.put("hj", hj+"");

                    list.add(map);
                }
            }
        }
        ac.put("tcjcList", list);
        //通过sql获取相关的收费项目(按打印分类，收费项目打印顺序排序)
        //获取相关的收费项目
        List<CMSfxmDto> itemData = createmealMapper.getSfxmByOrder(createOrder.getId());
        List<Map<Object,List>> data = new ArrayList<Map<Object,List>>();
        for(int i=0;i<itemData.size();i++){
            //将打印分类进行存储
            CMSfxmDto obj = itemData.get(i);
            Map<Object,List> map = new HashMap<Object, List>();
            //获取收费项目id
            String[] itemid = obj.getItemId().split(",");
            //获取收费项目
            String[] sfxm = obj.getSfxm().split(",");
            List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
            //解决去重
            List qc = new ArrayList();
            int b=0;
            for(int j=0;j<sfxm.length;j++){
                if(qc.size()!=0){
                    //判断是否存在相同的收费项目id
                    for(int m=0;m<qc.size();m++){
                        b=0;
                        if(itemid[j].equals(qc.get(m))){
                            //存在相同的收费项目id
                            b=1;
                            break;
                        }
                    }
                    if(b==0){
                        //根据收费项目id获取收费实体
                        Items item = itemsMapper.getInfoById(itemid[j]);
                        if(item!=null){
                            Map<String,Object> m2=new HashMap<String, Object>();
                            m2.put("itemName", item.getExamfeeitemNameprn());//收费项目名称
                            m2.put("checkYy", item.getJcyy());//检查意义
                            m2.put("itemid", itemid[j]);//收费项目id
                            m2.put("price", item.getUnitprice());//收费项目单价
                            items.add(m2);
                        }
                        qc.add(itemid[j]);
                        b = 0;
                    }else{
                        continue;
                    }
                }else{
                    Map<String,Object> m2=new HashMap<String, Object>();
                    Items item = itemsMapper.getInfoById(itemid[j]);
                    m2.put("itemName", item.getExamfeeitemNameprn());//收费项目名称
                    m2.put("checkYy", item.getJcyy());//检查意义
                    m2.put("itemid", itemid[j]);//收费项目id
                    m2.put("price", item.getUnitprice());//收费项目单价
                    items.add(m2);
                    qc.add(itemid[j]);
                }
            }
            map.put(obj.getFz(), items);
            data.add(map);
        }
        ac.put("sfxmData", data);
        /************获取数据结束*******************************/

        /*************导出************************************/
        int cellLenght=list.size()+3;//项目 检查目的  所有套餐 单位
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("sheet1");
        sheet.setDefaultColumnWidth(8);//默认行宽
        sheet.setDefaultRowHeight((short)746);//默认行高

        HSSFFont font_title = hwb.createFont();//默认字体1(大标题 加粗  14号)
        font_title.setFontName("宋体"); //字体
        font_title.setBold(true);
//       font_title.setBoldweight((short)1);//加粗
        font_title.setFontHeightInPoints((short) 14);// 设置字体大小

        HSSFFont font_bold = hwb.createFont();//默认字体3(收费项目标题 加粗  12号)
        font_bold.setFontName("宋体"); //字体
        font_title.setBold(true);
//       font_bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
        font_bold.setFontHeightInPoints((short) 12);// 设置字体大小

        HSSFFont font_normal_11 = hwb.createFont();//默认字体2 (11号)
        font_normal_11.setFontName("宋体"); //字体
        font_normal_11.setFontHeightInPoints((short) 11);// 设置字体大小

        HSSFFont font_normal_14 = hwb.createFont();//默认字体4（14号）
        font_normal_14.setFontName("宋体"); //字体
        font_normal_14.setFontHeightInPoints((short) 14);// 设置字体大小

        HSSFCellStyle cellStyle_bold = hwb.createCellStyle();//单元格格式1
        cellStyle_bold.setFont(font_bold);
        cellStyle_bold.setAlignment(HorizontalAlignment.CENTER); //水平布局：居中
        cellStyle_bold.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle_bold.setBorderBottom(BorderStyle.THIN);// 设置边框
        cellStyle_bold.setBorderLeft(BorderStyle.THIN);
        cellStyle_bold.setBorderRight(BorderStyle.THIN);
        cellStyle_bold.setBorderTop(BorderStyle.THIN);
        cellStyle_bold.setWrapText(true);  // 自动换行

        HSSFCellStyle cellStyle_title = hwb.createCellStyle();//单元格格式2
        cellStyle_title.cloneStyleFrom(cellStyle_bold);
        cellStyle_title.setFont(font_title);

        HSSFCellStyle cellStyle_normal_14 = hwb.createCellStyle();//单元格格式3
        cellStyle_normal_14.cloneStyleFrom(cellStyle_bold);
        cellStyle_normal_14.setFont(font_normal_14);

        HSSFCellStyle cellStyle_normal_11 = hwb.createCellStyle();//单元格格式4
        cellStyle_normal_11.cloneStyleFrom(cellStyle_bold);
        cellStyle_normal_11.setFont(font_normal_11);

        HSSFCellStyle cellStyle_background = hwb.createCellStyle();//单元格格式5
        cellStyle_background.cloneStyleFrom(cellStyle_bold);
        cellStyle_background.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//背景色
        cellStyle_background.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        HSSFCellStyle cellStyle_other=hwb.createCellStyle();//单元格格式6
        cellStyle_other.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());//背景色
        cellStyle_other.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        for(int i=0;i<23+cellLenght;i++){
            sheet.setDefaultColumnStyle(i, cellStyle_other);
        }

        int row_num=0;

        HSSFRow row = sheet.createRow(row_num);
        mergeCell(sheet, row, cellStyle_bold, row_num, cellLenght);
        HSSFCell cell =row.getCell(0);
//        BufferedImage bufferImg = null;
//        String full_path = "https://img2.baidu.com/it/u=920514027,2384439154&fm=253&fmt=auto&app=138&f=JPEG?w=490&h=490";
//        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//        bufferImg = ImageIO.read(new File(full_path));
//        ImageIO.write(bufferImg, "jpg", byteArrayOut);
        //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
//        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
//        //anchor主要用于设置图片的属性
//        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1000, 255,(short) 0, 0, (short) 0, 0);
//        anchor.setAnchorType(ClientAnchor.AnchorType.byId(3));
//        //插入图片
//        patriarch.createPicture(anchor, hwb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        cell.setCellValue(khdwmc);
        row_num++;

        row = sheet.createRow(row_num);
        cell =row.createCell(0);
        cell.setCellValue("项目");
        cell.setCellStyle(cellStyle_bold);
        cell =row.createCell(1);
        cell.setCellValue("检查目的");
        cell.setCellStyle(cellStyle_bold);
        for(int i=2;i<cellLenght-1;i++){
            cell =row.createCell(i);
            Map<String,String> map=list.get(i-2);
            String cellValue=String.valueOf(map.get("tjtcjc"));
            cell.setCellValue(cellValue);
            cell.setCellStyle(cellStyle_bold);
        }
        cell =row.createCell(cellLenght-1);
        cell.setCellValue("单价(元)");
        cell.setCellStyle(cellStyle_bold);
        row_num++;

        for(Map<Object ,List> map:data){
            for(Map.Entry<Object,List> entry:map.entrySet()){
                row = sheet.createRow(row_num);
                mergeCell(sheet, row, cellStyle_background, row_num, cellLenght);
                cell =row.getCell(0);
                cell.setCellValue(entry.getKey().toString());
                row_num++;
                List<Map<String,Object>> list_in=entry.getValue();
                for(Map<String,Object> map_in:list_in){
                    row = sheet.createRow(row_num);
                    cell =row.createCell(0);
                    cell.setCellValue(map_in.get("itemName").toString());
                    cell.setCellStyle(cellStyle_normal_11);
                    cell =row.createCell(1);
                    String cellValue=map_in.get("checkYy")==null?"":map_in.get("checkYy").toString();
                    cell.setCellValue(cellValue);
                    cell.setCellStyle(cellStyle_normal_11);
                    int row_in_num= 0;
                    try {
                        row_in_num = (int)Math.ceil(cellValue.getBytes("gbk").length/60.0);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    if(row_in_num>2){
                        row.setHeight((short)(row_in_num*373));
                    }

                    String itemid=map_in.get("itemid").toString();
                    for(int i=0,l=list.size();i<l;i++){//套餐
                        Map<String,String> map_create=list.get(i);
                        int mou=0;
                        //根据套餐id获取该套餐下含有的收费项目
                        List<Mealanditem> mealData = mealanditemMapper.selectList(new QueryWrapper<Mealanditem>().eq("tcid", map_create.get("id")));
                        for(Mealanditem meal:mealData){
                            //判断是否含有该收费项目
                            if(meal.getSfxmid().equals(itemid)){
                                mou=1;
                                cell =row.createCell(2+i);
                                cell.setCellValue("★");
                                cell.setCellStyle(cellStyle_bold);
                                break;
                            }
                        }
                        if(mou==0){
                            cell =row.createCell(2+i);
                            cell.setCellStyle(cellStyle_bold);
                        }
                    }

                    cell =row.createCell(2+list.size());
                    cell.setCellValue(map_in.get("price").toString());
                    cell.setCellStyle(cellStyle_bold);
                    row_num++;
                }
            }
        }

        row = sheet.createRow(row_num);
        mergeCell(sheet, row, cellStyle_background, row_num, cellLenght);
        cell =row.getCell(0);
        cell.setCellValue("其他(以下项目不参与折扣统计)");
        row_num++;

        for(String sfid:qtdata.keySet()){
            Items items = itemsMapper.getInfoById(sfid);
            String sfxmmc = items.getExamfeeitemNameprn();
            String jcyy = items.getJcyy();
            double jg = items.getUnitprice();

            row = sheet.createRow(row_num);
            cell =row.createCell(0);
            cell.setCellValue(sfxmmc);
            cell.setCellStyle(cellStyle_normal_11);
            cell =row.createCell(1);
            cell.setCellValue(jcyy);
            cell.setCellStyle(cellStyle_normal_11);
            for(int i=0,l=list.size();i<l;i++){
                String tcId=list.get(i).get("id");
                cell =row.createCell(2+i);
                if(qtdata.get(sfid).contains(tcId)){
                    cell.setCellValue("★");
                }
                cell.setCellStyle(cellStyle_bold);
            }
            cell =row.createCell(cellLenght-1);
            cell.setCellValue(jg);
            cell.setCellStyle(cellStyle_bold);
            row_num++;
        }

        row = sheet.createRow(row_num);
        cell =row.createCell(0);
        cell.setCellValue("原价");
        cell.setCellStyle(cellStyle_normal_11);
        cell =row.createCell(1);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle_normal_11);
        for(int i=0,l=list.size();i<l;i++){
            cell =row.createCell(2+i);
            cell.setCellValue("null".equals(list.get(i).get("tcysjg"))?0
                    :Double.parseDouble(String.valueOf(list.get(i).get("tcysjg"))));
            cell.setCellStyle(cellStyle_normal_11);
        }
        cell =row.createCell(cellLenght-1);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle_bold);
        row_num++;

        row = sheet.createRow(row_num);
        cell =row.createCell(0);
        cell.setCellValue("折扣");
        cell.setCellStyle(cellStyle_normal_11);
        cell =row.createCell(1);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle_normal_11);
        for(int i=0,l=list.size();i<l;i++){
            cell =row.createCell(2+i);
            cell.setCellValue("null".equals(list.get(i).get("tczk"))?0
                    :Double.parseDouble(String.valueOf(list.get(i).get("tczk"))));
            cell.setCellStyle(cellStyle_normal_11);
        }
        cell =row.createCell(cellLenght-1);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle_bold);
        row_num++;

        row = sheet.createRow(row_num);
        cell =row.createCell(0);
        cell.setCellValue("最终优惠价格");
        cell.setCellStyle(cellStyle_normal_11);
        cell =row.createCell(1);
        cell.setCellValue("折扣价+合计");
        cell.setCellStyle(cellStyle_normal_11);
        for(int i=0,l=list.size();i<l;i++){
            cell =row.createCell(2+i);
            cell.setCellValue("null".equals(list.get(i).get("zhjg"))?0:Double.parseDouble(String.valueOf(list.get(i).get("zhjg"))));
            cell.setCellStyle(cellStyle_normal_11);
        }
        cell =row.createCell(cellLenght-1);
        cell.setCellValue("");
        cell.setCellStyle(cellStyle_bold);

        sheet.setColumnWidth(1, 17000);
        sheet.setColumnWidth(0,4287);
        for(int i=2;i<cellLenght;i++){
            sheet.setColumnWidth(i,1842);
            sheet.autoSizeColumn((short)i);//自动调整列宽
        }


        OutputStream ouputStream =null;
        try {
            ouputStream=response.getOutputStream();
            hwb.write(ouputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(ouputStream!=null){
                ouputStream.flush();
                ouputStream.close();
            }
            if(hwb!=null) {
                hwb.close();
            }
        }
        /*************导出结束************************************/
    }


    /**
     * 添加项目的分中心
     * @param fzx
     * @return
     */
    @Override
    public Boolean addItemsFzx(String fzx) {
        List<Items> items = itemsMapper.selectList(new LambdaQueryWrapper<Items>().eq(Items::getIsDelete, 0));
        for (Items item : items) {
            long count = itemsAndFzxService.count(new LambdaQueryWrapper<ItemsAndFzx>()
                    .eq(ItemsAndFzx::getItemsId, item.getId())
                    .eq(ItemsAndFzx::getFzxId, fzx)
            );
            if (count == 0){
                //添加
                ItemsAndFzx itemsAndFzx = new ItemsAndFzx();
                itemsAndFzx.setItemsId(item.getId());
                itemsAndFzx.setFzxId(fzx);
                itemsAndFzx.setTbzt(0);
                itemsAndFzxService.save(itemsAndFzx);
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 判断套餐价格和收费项目的总金额是否一致
     * @param patientcode
     * @param tcId
     * @return
     */
    @Override
    public boolean isConsistentPrice(String patientcode,String tcId) {
        //获取体检者收费项目的总价格
        PriceAndFactPriceDto priceAndFactprice = peispatientfeeitemService.getPriceAndFactprice(patientcode);
        //获取套餐的折后价格
        Double tcPrice = 0.0;
        Createmeal meal = createmealMapper.getInfoById(tcId);
        if (meal == null) {
            Createcombo combo = createcomboMapper.getInfoById(tcId);
            tcPrice = combo.getZhjg();
        } else {
            tcPrice = meal.getZhjg();
        }
        return Double.compare(tcPrice, priceAndFactprice.getFactprice()) == 0;
    }

    /**
     * 添加项目成本价合计
     * @return
     */
    @Override
    public Boolean addTotalCostprice() {
        List<Createmeal> list = createmealMapper.selectList(new LambdaQueryWrapper<Createmeal>()
                .eq(Createmeal::getTotalCostprice, "")
                .or().isNull(Createmeal::getTotalCostprice));
        for (Createmeal createmeal : list) {
            Double costprice = createmealMapper.getCostpriceByTcid(createmeal.getId());
            createmeal.setTotalCostprice(costprice);
            createmealMapper.updateById(createmeal);
        }
        return Boolean.TRUE;
    }
}

