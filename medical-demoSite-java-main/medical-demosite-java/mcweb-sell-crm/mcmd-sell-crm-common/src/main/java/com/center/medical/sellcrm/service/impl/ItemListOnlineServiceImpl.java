package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.*;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.*;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.sellcrm.bean.dto.GpFormdataDto;
import com.center.medical.sellcrm.bean.dto.GpGriddataDto;
import com.center.medical.sellcrm.bean.dto.PaGridDataDto;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.bean.param.*;
import com.center.medical.sellcrm.bean.vo.AllTcOrderVo;
import com.center.medical.sellcrm.bean.vo.ItemOnlineVo;
import com.center.medical.sellcrm.bean.vo.NeedNoticeVo;
import com.center.medical.sellcrm.bean.vo.PatientDataVo;
import com.center.medical.sellcrm.dao.*;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.sellcrm.service.ItemListOnlineService;
import com.center.medical.sellcrm.service.OrderandfzxService;
import com.center.medical.sellcrm.service.PeisorgreservationService;
import com.center.medical.service.PeispatientarchiveService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.BranchMapper;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.BranchService;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.center.medical.common.utils.ToolUtil.getDateForMonth;

/**
 * 订单表(Createorder)表服务实现类
 *
 * @author ay
 * @since 2023-03-11 18:01:03
 */
@Slf4j
@Service("itemListOnlineService")
@RequiredArgsConstructor
public class ItemListOnlineServiceImpl extends ServiceImpl<ItemListOnlineMapper, Createorder> implements ItemListOnlineService {

    private final ItemListOnlineMapper itemListOnlineMapper;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final CreatecomboMapper createcomboMapper;
    private final CreatemealMapper createmealMapper;
    private final HarmMapper harmMapper;
    private final GroupAndFzxMapper groupAndFzxMapper;
    private final BranchMapper branchMapper;
    private final MapperFacade mapperFacade;
    private final PeisorgreservationMapper peisorgreservationMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final VationAndFzxMapper vationAndFzxMapper;
    private final OrderandfzxMapper orderandfzxMapper;
    private final CreateorderMapper createorderMapper;
    private final OrderandfzxService orderandfzxService;
    private final SysBranchMapper sysBranchMapper;
    private final PeispatientMapper peispatientMapper;
    private final PeispatientAndFzxMapper peispatientAndFzxMapper;
    private final SysUserMapper sysUserMapper;
    private final CreateorderService createorderService;
    private final OrderandcomboMapper orderandcomboMapper;
    private final AreaMapper areaMapper;
    private final ISysBranchService iSysBranchService;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final ComboanditemMapper comboanditemMapper;
    private final ItemsMapper itemsMapper;
    private final MealanditemMapper mealanditemMapper;
    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final PeispatientChargeMainMapper peispatientChargeMainMapper;
    private final PeisOlMapper peisOlMapper;
    private final MealandfzxMapper mealandfzxMapper;
    private final ComboandfzxMapper comboandfzxMapper;
    private final PeisorgreservationService peisorgreservationService;
    private final PeispatientarchiveService peispatientarchiveService;
    private final PeispatientChargeRecordMapper peispatientChargeRecordMapper;
    private final BranchService branchService;



    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ItemOnlineVo> getList(PageParam<ItemOnlineVo> page, ItemOnlineParam param) {
        param.setIsOnline(1);
        param.setFzxId(SecurityUtils.getCId());
        param.setUserNo(SecurityUtils.getUserNo());
        IPage<ItemOnlineVo> iPage = itemListOnlineMapper.getList(page, param);
        List<ItemOnlineVo> list = iPage.getRecords();
        for (ItemOnlineVo vo : list) {
            String urls = vo.getUrls();
            int url = StringUtils.isEmpty(urls) ? 0 : urls.split(",").length;
            vo.setUrls(String.valueOf(url));
        }
        iPage.setRecords(list);
        return iPage;
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Map getInfoById(String id) {
        // 订单信息
        Createorder createOrder = createorderMapper.selectOne(new QueryWrapper<Createorder>()
                .eq("id", id).eq("spzt", 4).eq("is_delete", 0));
        // 订单存在
        if (null != createOrder) {
            Map map = new HashMap();
            //用户所在的分中心ID
            String fzxId = SecurityUtils.getCId();
            //体检者团体任务
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
                map.put("datereservation", dateFormat.format(createOrder.getJhjqc()));
                map.put("planenddate", dateFormat.format(createOrder.getJhjqd()));
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
            SysUser qxUser = sysUserMapper.getUserByNo(peisorgreservation.getIdSalesperson());
            //获取用户名
            String userName = qxUser == null ? "" : qxUser.getUserName();
            // 销售人员名称
            map.put("saleName", userName);
            map.put("note", peisorgreservation.getNote());
            map.put("fFinished", peisorgreservation.getFFinished());
            //我的客户表
            Sellcustomer sellCustomer = sellcustomerMapper.getInfoById(createOrder.getKhdwmcid());
            if (null != sellCustomer) {
                map.put("tjm", sellCustomer.getIntId());
            }
            return map;
        }

        return new HashMap();
    }

    ;


    /**
     * 导出Excel
     *
     * @param param
     * @return
     */
    @Override
    public List<ItemOnlineVo> getExportData(ItemOnlineParam param) {
        param.setIsOnline(1);
        param.setFzxId(SecurityUtils.getCId());
        param.setUserNo(SecurityUtils.getUserNo());
        return itemListOnlineMapper.getExportData(param);
    }


    /**
     * 获取体检团体分组信息
     *
     * @param idOrgreservation
     * @return
     */
    @Override
    public Map getGroupData(String idOrgreservation) {
        // 判断是否为空
        if (StringUtils.isBlank(idOrgreservation)) {
            return new HashMap();
        }
        //体检者任务分组
        List<Peisorgreservationgroup> list = peisorgreservationgroupMapper.selectList(new QueryWrapper<Peisorgreservationgroup>()
                .orderByAsc("createDate").eq("id_orgreservation", idOrgreservation).eq("is_delete", 0));
        Peisorgreservation peisorgreservation = peisorgreservationMapper.getInfoById(idOrgreservation);
        List newList = new ArrayList();
        // 拼接页面数据
        for (Peisorgreservationgroup group : list) {
            Orderandcombo orderandcombo = orderandcomboMapper.selectOne(new QueryWrapper<Orderandcombo>().eq("ddid",peisorgreservation.getDdh())
                    .eq("tcid",group.getIdExamsuite()));
            //人数多的时候查询很慢
            //Peispatient list2=get(Peispatient.class,Restrictions.eq("idOrgreservationgroup",group.getId()));
            Long size = peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getIdOrgreservationgroup, group.getId()));
            if(orderandcombo!=null&&(orderandcombo.getShow()==null||orderandcombo.getShow()==0))
            {
                HashMap map = new HashMap();
                map.put("id", group.getId());
                map.put("orgreservationgroupname", group.getOrgreservationgroupname());
                map.put("dateexamplanned", group.getDateexamplanned());
                map.put("dispOrder", group.getDispOrder());
                map.put("forMale", group.getFMale());
                map.put("forFemale", group.getFFemale());
                map.put("forHasmarried", group.getFHasmarried());
                map.put("forNevermarried", group.getFNevermarried());
                map.put("grouptype", group.getGrouptype());
                map.put("agemin", group.getAgemin());
                map.put("agemax", group.getAgemax());
                map.put("idExamtype", group.getIdExamtype());
                map.put("idExamsuite", group.getIdExamsuite());
                map.put("groupLimit", (group.getFGroupstarted()==1 && group.getFGrouppaused()==0) ? 0 : 1);// 组禁检
                String tcName = "";
                String harmGroup = "";
                String zhjg="";
                String tcSrm="";
                Createcombo combo = createcomboMapper.getInfoById(group.getIdExamsuite());
                if (null == combo) {
                    Createmeal createMeal = createmealMapper.getInfoById(group.getIdExamsuite());
                    if (null != createMeal) {
                        tcName = createMeal.getTjtcjc();
                        tcSrm=createMeal.getTjtcsrm();
                        harmGroup = createMeal.getJhys();
                        zhjg=createMeal.getZhjg().toString();
                        map.put("forbidden", createMeal.getForbidden());
                    } else {
                        tcName = "";
                        harmGroup = "";
                    }
                } else {
                    tcName = combo.getTjtcjc();
                    harmGroup = combo.getJhys();
                    tcSrm=combo.getTjtcsrm();
                    zhjg=combo.getZhjg().toString();
                }
                map.put("tcName", tcName);
                map.put("tcSrm", tcSrm);
                // 获取危害分组
                map.put("harmGroup", harmGroup);
                map.put("harmName", harmMapper.getHarmText(harmGroup.split(",")));
                map.put("idPayway", group.getIdPayway());
                map.put("zhjg", zhjg);
                map.put("idPatientclass2", group.getIdPatientclass2());
                newList.add(map);
            }else if(size>0){
                HashMap map = new HashMap();
                map.put("id", group.getId());
                map.put("orgreservationgroupname", group.getOrgreservationgroupname());
                map.put("dateexamplanned", group.getDateexamplanned());
                map.put("dispOrder", group.getDispOrder());
                map.put("forMale", group.getFMale());
                map.put("forFemale", group.getFFemale());
                map.put("forHasmarried", group.getFHasmarried());
                map.put("forNevermarried", group.getFNevermarried());
                map.put("grouptype", group.getGrouptype());
                map.put("agemin", group.getAgemin());
                map.put("agemax", group.getAgemax());
                map.put("idExamtype", group.getIdExamtype());
                map.put("idExamsuite", group.getIdExamsuite());
                map.put("groupLimit", (group.getFGroupstarted()==1 && group.getFGrouppaused()==0) ? 0 : 1);// 组禁检
                String tcName = "";
                String harmGroup = "";
                String zhjg="";
                String tcSrm="";
                Createcombo combo = createcomboMapper.getInfoById(group.getIdExamsuite());
                if (null == combo) {
                    Createmeal createMeal = createmealMapper.getInfoById(group.getIdExamsuite());
                    if (null != createMeal) {
                        tcName = createMeal.getTjtcjc();
                        tcSrm=createMeal.getTjtcsrm();
                        harmGroup = createMeal.getJhys();
                        zhjg=createMeal.getZhjg().toString();
                        map.put("forbidden", createMeal.getForbidden());
                    } else {
                        tcName = "";
                        harmGroup = "";
                    }
                } else {
                    tcName = combo.getTjtcjc();
                    harmGroup = combo.getJhys();
                    tcSrm=combo.getTjtcsrm();
                    zhjg=combo.getZhjg().toString();
                }
                map.put("tcName", tcName);
                map.put("tcSrm", tcSrm);
                // 获取危害分组
                map.put("harmGroup", harmGroup);
                map.put("harmName", harmMapper.getHarmText(harmGroup.split(",")));
                map.put("idPayway", group.getIdPayway());
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
     * 获取订单下的所有套餐
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<AllTcOrderVo> getAllTcForOrder(PageParam<AllTcOrderVo> page, AllTcOrderParam param) {
        //key去空格
        if (ObjectUtils.isNotEmpty(param.getKey())) {
            param.setKey(param.getKey().trim());
        }
        //设置分中心
        param.setBranchIds(branchService.getUserCid(SecurityUtils.getUserNo()));
        IPage<AllTcOrderVo> allTcForOrder = itemListOnlineMapper.getAllTcForOrder(page, param);
        return allTcForOrder;
    }

    /**
     * 获取套餐的分中心
     *
     * @param id
     * @return
     */
    @Override
    public List<Branch> getBranchData(String id) {
        return branchMapper.getBranchData(id);
    }


    /**
     * 体检团体分组保存
     *
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveOrUpdateGroup(SaOrUpGroupParam param) {
        //团体任务表
        Peisorgreservation vation = mapperFacade.map(param.getFormdata(), Peisorgreservation.class);

        String vation_id = vation.getId();
        if (StringUtils.isEmpty(vation_id)) {
            //体检者团体任务
            Peisorgreservation vationNew = peisorgreservationMapper.selectOne(new QueryWrapper<Peisorgreservation>()
                    .eq("ddh", vation.getDdh()));
            // 判断该订单是否已经备单
            if (ObjectUtils.isNotEmpty(vationNew))
                throw new ServiceException("保存失败：该订单已经备单，请重新选择");
            // 备单人员ID
            vation.setBdid(SecurityUtils.getUserNo());
            //客户单位名称
            String orgName = sellcustomerMapper.getInfoById(vation.getIdOrg()).getKhdwmc();
            vation.setOrgName(orgName);
            // 保存实体类
            peisorgreservationMapper.insert(vation);
            vation_id = vation.getId();
        } else {
            Peisorgreservation vationNew = peisorgreservationMapper.getInfoById(vation_id);
            // 判断是否为空
            if (ObjectUtils.isEmpty(vationNew))
                throw new ServiceException("保存失败：体检团体信息不存在，已经被删除");

            if (vation.getBdid() == null) {
                vation.setBdid(SecurityUtils.getUserNo());
            }
            peisorgreservationMapper.updateById(vation);
            //删除团体任务分中心
            vationAndFzxMapper.delete(new QueryWrapper<VationAndFzx>().eq("vation_id", vation_id));

        }
        //任务分组
        Date bdrq = new Date();
        //订单与分中心关联表
        List<Orderandfzx> oafs = orderandfzxMapper.selectList(new QueryWrapper<Orderandfzx>()
                .eq("ddid", vation.getDdh()));
        for (Orderandfzx oaf : oafs) {
            oaf.setTbzt(1);//线上已备单线下不显示未备单(生成备单表就是已备单)
            oaf.setBdrq(bdrq);
            oaf.setXzzt(0);//未下载
            //团体任务分中心
            vationAndFzxMapper.insert(new VationAndFzx(oaf.getFzxid(), vation_id, 0));
        }
        //批量更新
        orderandfzxService.updateBatchById(oafs);
        //订单表
        Createorder order = createorderMapper.getInfoById(vation.getDdh());
        //已线上备单
        order.setIsOnline(1);
        createorderMapper.updateById(order);
        //分组表
        List<GpGriddataDto> groupData = param.getGriddata();
        for (GpGriddataDto map : groupData) {
            //体检者任务分组
            Peisorgreservationgroup group = mapperFacade.map(map, Peisorgreservationgroup.class);
            String state = map.getState();
            String orgreservationgroupname = map.getOrgreservationgroupname();
            String group_id = map.getId();
            String fzxIds = map.getFzxId();
            if ("added".equals(state)) {
                //添加
                Peisorgreservationgroup groupNew = peisorgreservationgroupMapper.selectOne(new QueryWrapper<Peisorgreservationgroup>()
                        .eq("id_orgreservation", vation_id)
                        .eq("orgreservationgroupname", orgreservationgroupname)
                        .eq("is_delete", 0));
                if (null != groupNew) {
                    throw new ServiceException("保存失败：" + orgreservationgroupname + " 分组名称已经重复!");
                }
                group.setIdOrgreservation(vation_id);
                group.setFGroupstarted(1);
                group.setFGrouppaused(0);
                group.setIsDelete(0);
                group.setInputCode(ToolUtil.getHanziPinyinHeadChar(group.getOrgreservationgroupname()));
                group.setIdPatientclass3(0);
                peisorgreservationgroupMapper.insert(group);
                group_id = group.getId();

                //分组分中心
                if (StringUtils.isEmpty(fzxIds)) {
                    throw new ServiceException("保存失败：" + orgreservationgroupname + " 未选择分中心!");
                }
                String[] fzxArr = fzxIds.split(",");
                for (String fzxId : fzxArr) {
                    groupAndFzxMapper.insert(new GroupAndFzx(fzxId, group_id, 0));
                }
            } else if ("modified".equals(state)) {
                //修改
                Peisorgreservationgroup groupNew = peisorgreservationgroupMapper.getInfoById(map.getId());
                if (null != groupNew) {
                    Peisorgreservationgroup groupNews = peisorgreservationgroupMapper.selectOne(new QueryWrapper<Peisorgreservationgroup>()
                            .ne("id", group_id)
                            .eq("id_orgreservation", vation_id)
                            .eq("orgreservationgroupname", orgreservationgroupname)
                            .eq("is_delete", 0));
                    if (null == groupNews) {
                        //分组分中心
                        if (StringUtils.isEmpty(fzxIds)) {
                            throw new ServiceException("保存失败：" + orgreservationgroupname + " 未选择分中心!");
                        }
                        String[] fzxArr = fzxIds.split(",");
                        List<String> fzxList = Arrays.asList(fzxArr);
                        //分组分中心
                        List<GroupAndFzx> gafs = groupAndFzxMapper.selectList(new QueryWrapper<GroupAndFzx>()
                                .eq("group_id", group_id));
                        List<String> oldFzxList = new ArrayList<String>();
                        List<String> removeFzxList = new ArrayList<String>();
                        for (GroupAndFzx gaf : gafs) {
                            if (!fzxList.contains(gaf.getFzxId())) {
                                //下载状态：0.未下载 1.已下载
                                if (gaf.getXzzt() == 1) {
                                    throw new ServiceException("保存失败：" + orgreservationgroupname
                                            + " 分组已在" + sysBranchMapper.getInfoById(gaf.getFzxId()).getFzx()
                                            + "下载，不能删除该分中心!");
                                }
                                removeFzxList.add(gaf.getFzxId());
                            }
                            oldFzxList.add(gaf.getFzxId());
                        }
                        List<String> addFzxList = new ArrayList<String>();
                        for (String fzxId : fzxArr) {
                            if (!oldFzxList.contains(fzxId)) {
                                addFzxList.add(fzxId);
                            }
                        }
                        //删除分组分中心
                        groupAndFzxMapper.delete(new QueryWrapper<GroupAndFzx>()
                                .eq("group_id", group_id));

                        for (String fzxId : fzxArr) {
                            groupAndFzxMapper.insert(new GroupAndFzx(fzxId, group_id, 0));
                        }

                        //体检者分中心
                        List<Peispatient> patients = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                                .eq("id_orgreservationgroup", group_id));
                        List<String> patientIdList = new ArrayList<String>();
                        //循环
                        for (Peispatient patient : patients) {
                            String patient_id = patient.getId();
                            patientIdList.add(patient_id);
                            for (String fzxId : addFzxList) {
                                //插入分组分中心
                                peispatientAndFzxMapper.insert(new PeispatientAndFzx(fzxId, patient_id, 0));
                            }
                        }
                        if (removeFzxList.size() > 0 && patientIdList.size() > 0) {
                            //删除
                            peispatientAndFzxMapper.delete(new QueryWrapper<PeispatientAndFzx>()
                                    .in("patient_id", patientIdList)
                                    .in("fzx_id", removeFzxList));
                        }

                        //更新
                        group.setInputCode(ToolUtil.getHanziPinyinHeadChar(group.getOrgreservationgroupname()));
                        peisorgreservationgroupMapper.updateById(group);
                    } else {
                        throw new ServiceException("保存失败：" + orgreservationgroupname + " 分组名称已经重复!");
                    }
                } else {
                    throw new ServiceException("保存失败：" + orgreservationgroupname + " 分组不存在，已经被删除!");
                }
            } else if ("removed".equals(state)) {
                //删除
                Peisorgreservationgroup groupNew = peisorgreservationgroupMapper.getInfoById(group_id);
                // 判断是否为空
                if (null != groupNew) {
                    // 检查是否存在体检者
                    List<Peispatient> peispatients = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                            .eq("id_orgreservationgroup", map.getId()));
                    if (peispatients.size() > 0) {
                        throw new ServiceException("保存失败： " + orgreservationgroupname + " 分组下已经存在体检者，不可以删除!");
                    }
                    //分组分中心
                    List<GroupAndFzx> gafs = groupAndFzxMapper.selectList(new QueryWrapper<GroupAndFzx>()
                            .eq("xzzt", 1).eq("group_id", group_id));
                    if (gafs.size() > 0) {
                        throw new ServiceException("保存失败： " + orgreservationgroupname + " 分组已被下载，不可以删除!");
                    }
                    groupNew.setIsDelete(1);
                    peisorgreservationgroupMapper.updateById(groupNew);
                }
            }


        }
        return "success@" + vation_id;
    }


    /**
     * 备单确认
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean groupConfirm(List<String> ids) {
        for (String id : ids) {
            //体检者任务分组
            Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(id);
            if (ObjectUtils.isEmpty(group)) {
                throw new ServiceException("操作失败，分组已不存在，请刷新后重试。");
            }
            group.setIdPatientclass3(1);
            peisorgreservationgroupMapper.updateById(group);
        }
        return Boolean.TRUE;
    }


    /**
     * 获取分组下相应的人员信息
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<PatientDataVo> getPatientData(PageParam<PatientDataVo> page, PatientDataParam param) {
        //去空格
        if (ObjectUtils.isNotEmpty(param.getInputCode())) {
            param.setInputCode(param.getInputCode().trim());
        }
        return itemListOnlineMapper.getPatientData(page, param);
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
        String id = param.getId();

        List<PaGridDataDto> patientData = param.getGriddata();
        if (patientData.size() == 0) {
            throw new ServiceException("预登记失败：体检者数据不能为空");
        }
        // 体检者默认头像
        String registerR = SecurityUtils.getUserNo();
        GpFormdataDto mData = param.getFormdata();

        String vationId = mData.getId();
        //体检者团体任务
        Peisorgreservation peisorgreservation = peisorgreservationMapper.selectOne(new QueryWrapper<Peisorgreservation>().eq("id", vationId).eq("f_finished", 1));
        boolean isFinished = ObjectUtils.isNotEmpty(peisorgreservation);
        // 团体id
        String idOrg = mData.getIdOrg();
        // 开单医师ID
        Sellcustomer customer = sellcustomerMapper.getInfoById(idOrg);
        SysUser qxUsers = sysUserMapper.getUserByNo(customer.getXsjlid());
        String xsjlId = null == qxUsers ? "" : qxUsers.getUserNo();
        String xsjlName = null == qxUsers ? "" : qxUsers.getUserName();
        // 团体名称
        String orgName = sellcustomerMapper.getInfoById(idOrg).getKhdwmc();
        Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(id);
        List<GroupAndFzx> gafs = groupAndFzxMapper.selectList(new QueryWrapper<GroupAndFzx>().eq("group_id", id));
        String ddh = mData.getOrdernum();
        Createorder order = createorderMapper.selectOne(new QueryWrapper<Createorder>().eq("ddh", ddh));
        String idInformway = order == null ? null : order.getIdInforway();//通知方式
        // 体检类别
        String examsuiteName = "";
        String tjlb = "0";
        String jhys = "";
        //最小套餐
        Createcombo combo = createcomboMapper.getInfoById(group.getIdExamsuite());
        if (null != combo) {
            examsuiteName = combo.getTjtcmc();
            tjlb = combo.getZytjlb();
            jhys = combo.getJhys();
        } else {
            //普通套餐表
            Createmeal createMeal = createmealMapper.getInfoById(group.getIdExamsuite());
            if (null != createMeal) {
                examsuiteName = createMeal.getTjtcmc();
                tjlb = String.valueOf(createMeal.getZytjlb());
                jhys = createMeal.getJhys();
            }
        }
        //订单与套餐关联表
        Orderandcombo orderAndCombo = orderandcomboMapper.selectOne(new QueryWrapper<Orderandcombo>().eq("ddid", order.getId()).eq("tcid", group.getIdExamsuite()));
        //检查类型，0.健康类 1.职业类 2.综合类 5.入职类 6.疫苗类 7.其他类
        Integer idExamclass = orderAndCombo.getIdExamclass();
        Date date = new Date();
        String note = SecurityUtils.getUsername() + "线上备单于" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ";";
        for (PaGridDataDto map : patientData) {
            String wechatCode = NumUtil.createAuthCode();
            Peispatient peispatient = mapperFacade.map(map, Peispatient.class);
            if (ObjectUtils.isEmpty(peispatient)) {
                throw new ServiceException("预登记失败：系统发生异常，请联系管理员");
            }
            peispatient.setCountreportoccupationxml(1);//身份证
            //前台须知
            peispatient.setQtxz(null == mData.getQtxz() ? "" : mData.getQtxz().toString());
            peispatient.setIdInformway(idInformway);
            // 订单号
            peispatient.setNumorgresv(ddh);
            peispatient.setIdTjtc(group.getIdExamsuite());
            peispatient.setIdOrgreservationgroup(id);
            //个检/团检：0.个检 1.团检
            peispatient.setFUsecodehiden(1);
            peispatient.setIdExamclass(idExamclass);
            // 任务ID
            peispatient.setIdOrgreservation(vationId);
            // 体检时间
            if (null == group.getDateexamplanned()) {
                peispatient.setMedicaldate(new Date());
            } else {
                peispatient.setMedicaldate(new Date(group.getDateexamplanned().getTime()));
            }
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
                    peispatient.setBirthdate(new Date());
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    try {
                        peispatient.setBirthdate(format.parse(peispatient.getIdcardno().substring(6, 14)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 体检类型
            peispatient.setIdExamtype(group.getIdExamtype());
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
                peispatient.setIdcardno(peispatient.getIdcardno().toUpperCase());
                String card = peispatient.getIdcardno();
                if (!IdcardUtil.isValidCard(card)) {
                    throw new ServiceException("预登记失败：" + map.getPatientname() + " 体检者的身份证号不合法");
                }
                // 如果长度是15位
                if (card.length() == 15) {
                    card = card.substring(0, 6) + "19" + card.substring(6) + "x";
                }
                // 生日匹配
                peispatient.setBirthdate(IdcardUtil.getBirthDate(card));
                // 年龄匹配
                int age = IdcardUtil.getAgeByIdCard(card);
                if (null != peispatient.getAge() && peispatient.getAge() > 0 && peispatient.getAge() != age) {
                    throw new ServiceException("预登记失败：" + map.getPatientname() + " 体检者的身份证号与年龄不匹配");
                }
                peispatient.setAge(age);

                // 匹配性别
                card = card.substring(card.length() - 2, card.length() - 1);
                // 性别是否匹配
                Integer strSex = (Integer.valueOf(card) & 1) != 0 ? 0 : 1;
                if (ObjectUtils.isNotEmpty(peispatient.getIdSex()) && !strSex.equals(peispatient.getIdSex())) {
                    throw new ServiceException("预登记失败：" + map.getPatientname() + " 体检者的身份证号与性别不匹配");
                }
                peispatient.setIdSex(strSex);

            }
            // 根据总工龄计算参加工作时间
            if ((ObjectUtils.isNotEmpty(peispatient.getZgl())
                    && 0 != peispatient.getZgl())
                    || ObjectUtils.isEmpty(peispatient.getWorkDate())) {
                peispatient.setWorkDate(
                        subTime(getDateForMonth(peispatient.getZgl())));
            } else {
                peispatient.setZgl(getMonthSpace(new Date(), subTime(peispatient.getWorkDate())));
            }
            // 根据接害工龄计算从事该工种工作时间
            if ((null != peispatient.getJhgl() && 0 != peispatient.getJhgl()) || null == peispatient.getHarmDate()) {
                peispatient.setHarmDate(subTime(getDateForMonth(peispatient.getJhgl())));
            } else {
                peispatient.setJhgl(getMonthSpace(new Date(), subTime(peispatient.getHarmDate())));
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
                    area = areaMapper.selectOne(new QueryWrapper<Area>().eq("resarea", "山东省"));
                }
            } else {
                area = areaMapper.selectOne(new QueryWrapper<Area>().eq("resarea", "山东省"));
            }
            if (null != area) {
                peispatient.setIdResarea(area.getId());
                peispatient.setResarea(area.getResarea());
            }
            if ("removed".equals(map.getState())) {
                //删除
                if (null != map.getId()) {
                    Peispatient peispatientNew = peispatientMapper.getInfoById(map.getId());
                    // 判断是否为空
                    if (null != peispatientNew) {
                        if (peispatientAndFzxMapper.selectCount(new QueryWrapper<PeispatientAndFzx>()
                                .eq("patient_id", map.getId()).eq("xzzt", 1)) > 0) {
                            throw new ServiceException("体检者已被下载，不可以删除！");
                        }
                        peispatientAndFzxMapper.delete(new QueryWrapper<PeispatientAndFzx>().eq("patient_id", map.getId()));

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
                    String rlt = isExitsName(peispatient, mData.getOrdernum());
                    if (!"success".equals(rlt)) {
                        // 存在重复
                        throw new ServiceException(rlt);
                    }
                    boolean hasNotCode = StringUtils.isBlank(peispatientNew.getPatientcode());
                    if (hasNotCode) {
                        // 体检号
                        String patientCode = "";
                        do {
                            patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(null), "");
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
                    peispatient.setIdPayway(group.getIdPayway());
                    BeanUtils.copyProperties(peispatient, peispatientNew);
                    peispatientNew.setIdCis(bindArchive(peispatientNew));
                    if (null == peispatientNew.getFRegistered() || peispatientNew.getFRegistered() != 1) {
                        // 更新收费项目实体类
                        peispatientMapper.updateById(peispatientNew);
                        //更新下载状态
                        List<PeispatientAndFzx> pafs = peispatientAndFzxMapper.selectList(new QueryWrapper<PeispatientAndFzx>().eq("patient_id", map.getId()));
                        for (PeispatientAndFzx paf : pafs) {
                            paf.setXzzt(0);
                            peispatientAndFzxMapper.updateById(paf);
                        }
                        //体检者收费项目表
                        List<Peispatientfeeitem> pItems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>().eq("id_patient", peispatientNew.getPatientcode()));
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
                        // 折扣价格是否放在【个检报告工本费】上
                        Boolean isMakeGb = false;
                        for (int j = 0; j < size; j++) {
                            Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
                            peispatientfeeitem.setIdPatient(peispatientNew.getPatientcode());
                            peispatientfeeitem.setIdExamfeeitem(items.get(j).get("idExamfeeitem"));
                            peispatientfeeitem.setExamfeeitemName(items.get(j).get("examfeeitemName"));
                            peispatientfeeitem.setPrice(Double.valueOf(items.get(j).get("price")));
                            peispatientfeeitem.setIdKs(items.get(j).get("idKs"));

                            Object[] oa = peispatientfeeitemService.getFactPrice(items.get(j), size, j, isMakeGb);
                            if (!isMakeGb) {
                                isMakeGb = (Boolean) oa[1];
                            }
                            peispatientfeeitem.setFactprice(Double.parseDouble(oa[0].toString()));
                            peispatientfeeitem.setCount(1);
                            peispatientfeeitem.setIdPayway(group.getIdPayway());
                            peispatientfeeitem.setFRegistered(0);
                            peispatientfeeitem.setChangeItem(0);
                            peispatientfeeitem.setFMarkFeereturn(0);
                            peispatientfeeitem.setFFeecharged(0);
                            peispatientfeeitem.setFLabsendtolis(0);
                            peispatientfeeitem.setFExaminated(0);
                            peispatientfeeitem.setFGiveup(0);
                            peispatientfeeitem.setIsbx(Integer.valueOf(String.valueOf(items.get(j).get("isbx"))));
                            peispatientfeeitem.setBxcount(items.get(j).get("bxcount") == null ? null : Integer.valueOf(String.valueOf(items.get(j).get("bxcount"))));
                            peispatientfeeitem.setFDelayed(0);
                            peispatientfeeitem.setIsMintc(1);
                            peispatientfeeitem.setQty(items.get(j).get("qty") == null ? null : Integer.valueOf(String.valueOf(items.get(j).get("qty"))));
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

                    //沃德小程序
                    PeisOl peisOl = peisOlMapper.selectOne(new QueryWrapper<PeisOl>().eq("patientbizno", peispatientNew.getPatientbizno()));
                    if (peisOl == null) {
                        peisOl = new PeisOl();
                        peisOl.setPatientbizno(peispatient.getPatientbizno());
                        peisOl.setWechatCode(wechatCode);
                        peisOlMapper.insert(peisOl);
                    }

                } else {
                    // 不存在
                    throw new ServiceException("预登记失败：" + map.getPatientname() + " 体检者不存在，已经被删除");
                }
            } else if ("added".equals(map.getState())) {
                if (isFinished) {
                    throw new ServiceException("预登记失败：订单已结束，不能添加人员");
                }
                // 生成体检号
                String patientCode = "";
                do {
                    patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(null), "");
                    //判断体检号是否存在
                } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                        .eq(Peispatient::getPatientcode, patientCode)) > 0);
                peispatient.setPatientcode(patientCode);
                peispatient.setPatientbizno(patientCode);
                peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
                peispatient.setFRegistered(0);
                peispatient.setIdPayway(group.getIdPayway());

                //体检号生成人  生成时间
                peispatient.setTimingstartedat(date);
                peispatient.setGuidancenote2(registerR);

                // 判断人员信息是否重复添加
                String rlt = isExitsName(peispatient, mData.getOrdernum());
                if (!"success".equals(rlt)) {
                    // 存在重复
                    throw new ServiceException(rlt);
                }
                peispatient.setIdCis(bindArchive(peispatient));
                // 保存实体类
                peispatientMapper.insert(peispatient);
                String result = peispatient.getId();
                if (StringUtils.isBlank(result)) {
                    throw new ServiceException("" + map.getPatientname() + " 预登记失败");
                }
                //分中心关联表
                for (GroupAndFzx gaf : gafs) {
                    peispatientAndFzxMapper.insert(new PeispatientAndFzx(gaf.getFzxId(), result, 0));
                }
                // 绑定套餐的收费项目
                List<HashMap<String, String>> items = getExamfeeitem(peispatient.getIdTjtc());
                List<Peispatientfeeitem> peispatientfeeitems = new ArrayList<Peispatientfeeitem>();
                int size = items.size();
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
                    if (!isMakeGb) {
                        isMakeGb = (Boolean) oa[1];
                    }
                    peispatientfeeitem.setFactprice(Double.parseDouble(oa[0].toString()));
                    peispatientfeeitem.setCount(1);
                    peispatientfeeitem.setIdPayway(group.getIdPayway());
                    peispatientfeeitem.setFRegistered(0);
                    peispatientfeeitem.setChangeItem(0);
                    peispatientfeeitem.setFMarkFeereturn(0);
                    peispatientfeeitem.setFFeecharged(0);
                    peispatientfeeitem.setFLabsendtolis(0);
                    peispatientfeeitem.setFExaminated(0);
                    peispatientfeeitem.setFGiveup(0);
                    peispatientfeeitem.setIsbx(items.get(j).get("isbx") == null ? 0 : Integer.parseInt(String.valueOf(items.get(j).get("isbx"))));
                    peispatientfeeitem.setBxcount(items.get(j).get("bxcount") == null ? 0 : Integer.parseInt(String.valueOf(items.get(j).get("bxcount"))));
                    peispatientfeeitem.setFDelayed(0);
                    peispatientfeeitem.setIsMintc(1);
                    peispatientfeeitem.setQty(items.get(j).get("qty") == null ? null : Integer.valueOf(items.get(j).get("qty").toString()));
                    peispatientfeeitems.add(peispatientfeeitem);
                }
                peispatientfeeitemService.saveBatch(peispatientfeeitems);

                peispatientChargeMainMapper.insert(new PeispatientChargeMain(
                        note
                        , peispatient.getMoneyamount()
                        , peispatient.getMoneyamountpaid()
                        , peispatient.getPatientcode()));

                //沃德小程序体检码
                PeisOl peisOl = new PeisOl();
                peisOl.setPatientbizno(peispatient.getPatientbizno());
                peisOl.setWechatCode(wechatCode);
                peisOlMapper.insert(peisOl);
            }
        }
        return Boolean.TRUE;
    }


    private Date subTime(Date indate) {
        Date dat = null;
        if (indate != null) {
            Long time = indate.getTime() - 28800000;
            dat = new Date(time);
        }
        return dat;
    }

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
        Long i = peispatientMapper.selectCount(and.eq("numorgresv", ddh)
                .ne("id_examtype", "3"));
        // 判断是否存在已经导入
        if (i > 1) {
            return "人员: <font color='red'>" + peispatient.getPatientname() + "</font> 已经存在";
        }
        return "success";
    }


    /**
     * 线上备单-绑定档案
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
        archive.setIdMarriage(String.valueOf(patient.getIdMarriage()));
        archive.setIdNation(patient.getIdNation());
        archive.setCultural(patient.getCultural());
        //保存或修改
        peispatientarchiveService.saveOrUpdate(archive);
        String id = archive.getId();
        return id;
    }


    /**
     * 获取登记页面最小套餐收费项目明细
     *
     * @param tcid
     * @return
     */
    public List<HashMap<String, String>> getExamfeeitem(String tcid) {
        List list = new ArrayList();
        // 判断是否为空
        if (StringUtils.isBlank(tcid)) {
            return list;
        }
        // 根据套餐id获取最小套餐下的收费项目
        List<Comboanditem> minPackagesList = comboanditemMapper.selectList(new QueryWrapper<Comboanditem>()
                .orderByAsc("createdate").eq("tcid", tcid).eq("is_delete", 0));
        // 判断是否存在
        if (CollectionUtils.isNotEmpty(minPackagesList) && minPackagesList.size() > 0) {
            // 最小套餐
            Createcombo createCombo = createcomboMapper.getInfoById(tcid);
            int size = minPackagesList.size();
            for (int i = 0; i < size; i++) {
                Comboanditem cai = minPackagesList.get(i);
                Items items = itemsMapper.getInfoById(cai.getSfxmid());
                if (ObjectUtils.isNotEmpty(items)) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    // 收费项目id
                    map.put("idExamfeeitem", items.getId());
                    // 收费项目名称
                    map.put("examfeeitemName", items.getExamfeeitemName());
                    // 原始价格
                    map.put("price", String.valueOf(items.getUnitprice()));
                    map.put("factprice", (i + 1) == size ? (null == createCombo.getZhjg() ? 0 : createCombo.getZhjg()) : 0);
                    // 科室
                    map.put("idKs", items.getIdDepart());
                    // 是否备选
                    map.put("isbx", StringUtils.isBlank(minPackagesList.get(i).getSfbx()) ? "0" : minPackagesList.get(i).getSfbx());
                    // 备选数量
                    map.put("bxcount", createCombo.getSl());
                    // 折后价格
                    map.put("zhjg", createCombo.getZhjg());
                    map.put("idSex", items.getXb());
                    map.put("qty", i + 1 + "");
                    list.add(map);
                }
            }
        }

        // 普通套餐
        //按是否备选排序(是否备选不会为空)
        List<Mealanditem> mealItems = mealanditemMapper.selectList(new QueryWrapper<Mealanditem>()
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
            for (int i = 0; i < size; i++) {
                Mealanditem mai = mealItems.get(i);
                // 是否必选：0或null.否 1.是
                if ("1".equals(mai.getSfbx())) {
                    //分组类型：0.组内选 1.组间选 2.组任选
                    Integer groupType = mai.getGroupType();
                    if (groupType == null) {//兼容老套餐
                        bxjg += mai.getPrice();
                    } else if (groupType == 0) {
                        Integer group = mai.getItemGroup();//组内选，同一组所有项目价格相同
                        if (groups.contains(group)) {

                        } else {
                            groups.add(group);
                            bxjg += mai.getPrice();
                        }
                    }
                }
            }
            //折后价格 - 所有备选项目价格
            double fbxjg = createMeal.getZhjg() - bxjg;
            for (int i = 0; i < size; i++) {
                Items items = itemsMapper.getInfoById(mealItems.get(i).getSfxmid());

                if (ObjectUtils.isNotEmpty(items)) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    // 收费项目id
                    map.put("idExamfeeitem", items.getId());
                    // 收费项目名称
                    map.put("examfeeitemName", items.getExamfeeitemName());
                    // 原始价格
                    map.put("price", String.valueOf(items.getUnitprice()));
                    map.put("factprice", (i + 1) == size ? createMeal.getZhjg() : 0);
                    // 科室
                    map.put("idKs", items.getIdDepart());
                    // 是否备选
                    Integer isbx = Objects.isNull(mealItems.get(i).getSfbx()) ? 0 : mealItems.get(i).getSfbx();
                    map.put("isbx", isbx);
                    // 备选数量
                    map.put("bxcount", String.valueOf(createMeal.getKxsl()));
                    // 折后价格
                    if (isbx == 1) {//如果是备选
                        map.put("zhjg", mealItems.get(i).getPrice());
                    } else {
                        map.put("zhjg", fbxjg);
                    }
                    // map.put("zhjg", createMeal.getZhjg());
                    map.put("idSex", items.getXb());
                    map.put("qty", mealItems.get(i).getItemSort() == null ? null : mealItems.get(i).getItemSort().toString());
                    map.put("group", mealItems.get(i).getItemGroup() == null ? null : mealItems.get(i).getItemGroup().toString());
                    map.put("groupType", mealItems.get(i).getGroupType() == null ? null : mealItems.get(i).getGroupType().toString());
                    list.add(map);
                }
            }
        }

        return list;
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
        // 选中体检者
        if (ids.size() > 0 && !StringUtils.isBlank(ids.get(0))) {
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
                }
                // TODO: 不删除费用主表及收费记录,再次点击线上备单就会报错
                peispatientChargeMainMapper.delete(new QueryWrapper<PeispatientChargeMain>().eq("patientcode", peispatient.getPatientcode()));
                peispatientChargeRecordMapper.delete(new QueryWrapper<PeispatientChargeRecord>().eq("patientcode", peispatient.getPatientcode()));
                //删除收费项目表
                peispatientfeeitemMapper.delete(new QueryWrapper<Peispatientfeeitem>().eq("id_patient", peispatient.getPatientcode()));
                //删除线上体检者
                peisOlMapper.delete(new QueryWrapper<PeisOl>().eq("patientbizno", peispatient.getPatientcode()));
            }

            //删除体检者表
            peispatientMapper.delete(new QueryWrapper<Peispatient>().in("id", ids));

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
                if (!StringUtils.isBlank(peispatient.getPatientcode())) {
                    //删除收费项目表
                    peispatientfeeitemMapper.delete(new QueryWrapper<Peispatientfeeitem>().eq("id_patient", peispatient.getPatientcode()));
                }
                peispatientChargeMainMapper.delete(new QueryWrapper<PeispatientChargeMain>().eq("patientcode", peispatient.getPatientcode()));
                peispatientChargeRecordMapper.delete(new QueryWrapper<PeispatientChargeRecord>().eq("patientcode", peispatient.getPatientcode()));
                //删除收费项目表
                peispatientfeeitemMapper.delete(new QueryWrapper<Peispatientfeeitem>().eq("id_patient", peispatient.getPatientcode()));
                //删除线上体检者
                peisOlMapper.delete(new QueryWrapper<PeisOl>().eq("patientbizno", peispatient.getPatientcode()));
            }
            //删除体检者表
            peispatientMapper.delete(new QueryWrapper<Peispatient>().eq("id_orgreservationgroup", id));
        }
        return Boolean.TRUE;
    }

    /**
     * 已备单
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean markTbzt(List<String> ids) {
        // TODO: ??? 已备单同步就光改个状态?
        List<String> cids = branchService.getUserCid(SecurityUtils.getUserNo());
        List<Orderandfzx> oafs = new ArrayList<Orderandfzx>();
        Date bdrq = new Date();
        for (String id : ids) {
            for (String fzxId : cids) {
                //订单与分中心关联表
                Orderandfzx oaf = orderandfzxMapper.selectOne(new QueryWrapper<Orderandfzx>().eq("ddid", id).eq("fzxid", fzxId)
                        .in("tbzt", 0, 2));
                if (oaf != null) {
                    //同步
                    oaf.setTbzt(1);
                    //备单日期
                    oaf.setBdrq(bdrq);
                    oafs.add(oaf);
                }
                //订单与套餐关联表
                List<Orderandcombo> oacs = orderandcomboMapper.selectList(new QueryWrapper<Orderandcombo>().eq("ddid", id));
                for (Orderandcombo oac : oacs) {
                    if ("0".equals(oac.getCombostate())) {
                        //普通套餐
                        Mealandfzx maf = mealandfzxMapper.selectOne(new QueryWrapper<Mealandfzx>().eq("fzxid", fzxId)
                                .eq("tcid", oac.getTcid()).in("tbzt", 0, 2));
                        if (maf != null) {
                            //同步
                            maf.setTbzt(1);
                            mealandfzxMapper.updateById(maf);
                        }
                    } else {
                        //最小套餐与分中心关联表
                        Comboandfzx caf = comboandfzxMapper.selectOne(new QueryWrapper<Comboandfzx>().eq("fzxid", fzxId)
                                .eq("tcid", oac.getTcid()).in("tbzt", 0, 2));
                        if (caf != null) {
                            //同步
                            caf.setTbzt(1);
                            comboandfzxMapper.updateById(caf);
                        }
                    }
                }
            }

        }
        //更新
        orderandfzxService.updateBatchById(oafs);
        return Boolean.TRUE;
    }


    /**
     * 订单结束/反结束
     *
     * @param ids
     * @param type
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean finishOrder(List<String> ids, int type) {
        //体检者团体任务
        List<Peisorgreservation> vations = peisorgreservationMapper.selectList(new QueryWrapper<Peisorgreservation>().in("ddh", ids));
        for (Peisorgreservation vation : vations) {
            //任务已完成
            vation.setFFinished(type);
        }
        peisorgreservationService.updateBatchById(vations);
        return Boolean.TRUE;
    }

    /**
     * 线上备单发送短信 查询数据
     *
     * @return
     */
    @Override
    public List<NeedNoticeVo> selectNeedNoticeWechatCodeList() {
        return itemListOnlineMapper.selectNeedNoticeWechatCodeList();
    }
}

