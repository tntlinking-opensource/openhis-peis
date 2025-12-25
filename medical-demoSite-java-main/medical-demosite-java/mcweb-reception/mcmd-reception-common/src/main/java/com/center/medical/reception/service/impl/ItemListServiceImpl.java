package com.center.medical.reception.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.PeispatientChargeMain;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysRole;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientChargeMainMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.reception.bean.dto.GCSaOrUpDto;
import com.center.medical.reception.bean.dto.GIGriddataDto;
import com.center.medical.reception.bean.param.GCSaOrUpParam;
import com.center.medical.reception.bean.param.ItemListParam;
import com.center.medical.reception.bean.param.ListDataParam;
import com.center.medical.reception.bean.vo.ItemListVo;
import com.center.medical.reception.bean.vo.ListDataVo;
import com.center.medical.reception.dao.ItemListMapper;
import com.center.medical.reception.service.ItemListService;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.bean.model.Mealanditem;
import com.center.medical.sellcrm.bean.model.Peisorgreservation;
import com.center.medical.sellcrm.dao.*;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysRoleMapper;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-02-02 15:30:33
 */
@Slf4j
@Service("itemListService")
@RequiredArgsConstructor
public class ItemListServiceImpl extends ServiceImpl<ItemListMapper, Peispatient> implements ItemListService {

    private final ItemListMapper itemListMapper;
    private final ItemsMapper itemsMapper;
    private final CreateorderMapper createorderMapper;
    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final PeispatientMapper peispatientMapper;
    private final MapperFacade mapperFacade;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PeisStateService peisStateService;
    private final PeispatientChargeMainMapper peispatientChargeMainMapper;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final SysDeptMapper sysDeptMapper;
    private final PeisorgreservationMapper peisorgreservationMapper;
    private final CreatemealMapper createmealMapper;
    private final MealanditemMapper mealanditemMapper;


    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ItemListVo> getList(PageParam<ItemListVo> page, ItemListParam param) {
        //没传分中心就默认登录人的分中心
//        if (ObjectUtils.isEmpty(param.getBranchIds())){
//            param.setBranchIds(Arrays.asList(SecurityUtils.getCId()));
//        }

        //没有决策管理权限，看自己的
        if (!SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
            param.setUserNo(SecurityUtils.getUserNo());
        }

        //去空格大写
        if (ObjectUtils.isNotEmpty(param.getTcCode())) {
            param.setTcCode(param.getTcCode().trim().toUpperCase());
        }
        if (ObjectUtils.isNotEmpty(param.getInputCode())) {
            param.setInputCode(param.getInputCode().trim().toUpperCase());
        }

        return itemListMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return itemListMapper.getInfoById(id);
    }

    ;


    /**
     * 查询收费项目下拉
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<ListDataVo> getListData(PageParam<ListDataVo> page, ListDataParam param) {
        //只有登记页面，如果体检者所属订单适用市场价。
        //2025.11.26都只取原价
//        String priceKey = "unitprice";
//        if (ObjectUtils.isNotEmpty(param)) {
//            String numorgresv = param.getNumorgresv();
//            if (StringUtils.isNotEmpty(numorgresv)) {
//                // 订单表
//                Createorder co = createorderMapper.selectOne(new QueryWrapper<Createorder>()
//                        .eq("ddh", numorgresv));
//
//                if (co != null && co.getIsMarket() != null && co.getIsMarket().intValue() == 1) {
//                    priceKey = "coopprice";
//                }
//            }
//        }
//        param.setPricekey(priceKey);
        //去空格大写
        if (StringUtils.isNotBlank(param.getKey())) {
            param.setKey(param.getKey().trim().toUpperCase());
        }
        //权限判断
        SysUser user = sysUserMapper.selectUserByUserNo(SecurityUtils.getUserNo());
        if (!ownRole(user, Constants.SUPER_MANAGER)) {
            param.setCid(user.getCid());
        }

        return itemListMapper.getListData(page, param);
    }


    //判断有没有权限
    public static final boolean ownRole(SysUser user, String roleName) {
        if (user != null) {
            List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
            for (SysRole role : roles) {
                if (roleName.equals(role.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 团检退项匹配最小套餐
     *
     * @param gForm
     * @param idss
     */
    @Override
    public Boolean compareMinTcContent(List<GIGriddataDto> gForm, List<String> idss) {
        Map<String, String> info = new HashMap<String, String>();
        if (gForm.size() > 0) {
            if (idss.size() > 0) {
                String text = "";
                for (int i = 0, l = gForm.size(); i < l; i++) {
                    String idExamtype = gForm.get(i).getIdExamtype();
                    // 健康除外
                    if ("0".equals(idExamtype)) {
                        continue;
                    }
                    String result = itemListMapper.compareMinTcContent(
                            gForm.get(i).getJhys(), idss,
                            gForm.get(i).getMedicaltype());
                    if (!StringUtils.isBlank(result)) {
                        text += "第" + (i + 1) + "个体检者的" + result + "收费项目存在于最小套餐内";
                    }
                }
                if (StringUtils.isBlank(text)) {
                    return Boolean.TRUE;
                } else {
                    throw new ServiceException(text);
                }
            } else {
                throw new ServiceException("请选择一条收费项目");
            }
        } else {
            throw new ServiceException("请选择一条体检者");
        }
    }


    /**
     * 右侧保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(GCSaOrUpParam param) {
        // 判断是否为空
        if (StringUtils.isNotEmpty(param.getGroupid())) {
            // 获取支付方式id
            String payId = peisorgreservationgroupMapper.getInfoById(param.getGroupid()).getIdPayway();
            // 分组下的未登记人员信息
            List<Peispatient> peispatients = peispatientMapper.selectList(new QueryWrapper<Peispatient>().in("id", param.getIds()));
            if (peispatients.size() == 0) {
                throw new ServiceException("保存失败：该分组套餐下没有体检者");
            }
            //收费项目表
            List<Peispatientfeeitem> peispatientfeeitems = new ArrayList<Peispatientfeeitem>();
            List<GCSaOrUpDto> list = param.getGriddata();

            for (int i = 0; i < peispatients.size(); i++) {
                Peispatient peispatient = peispatients.get(i);
                //总检锁定,总检状态,职业锁定完成,职业体检状态
                if (((null != peispatient.getFFinallocked() && peispatient.getFFinallocked() == 1)
                        || (null != peispatient.getJktjzt() && peispatient.getJktjzt() > 0))
                        || ((null != peispatient.getIdGuidenurse() && peispatient.getIdGuidenurse() == 1)
                        || (null != peispatient.getZytjzt() && peispatient.getZytjzt() > 0))) {
                    throw new ServiceException("保存失败：该体检者 " + peispatient.getPatientname() + " 总检已经锁定或者开始，不可以操作！");
                }
                Double amount = 0d;
                for (int j = 0; j < list.size(); j++) {
                    // 未检、未退项（换项）、未弃项
                    Peispatientfeeitem pf = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                            .eq("id_examfeeitem", list.get(j).getIdExamfeeitem())
                            .eq("id_patient", peispatient.getPatientcode())
                            .eq("change_item", 0)
                            .eq("f_giveup", 0)
                            .eq("sfjj", 0)
                            .eq("f_examinated", 0));
                    if ("removed".equals(list.get(j).getState())) {
                        //删除
                        if (!StringUtils.isBlank(list.get(j).getId())) {
                            if (null != pf) {
                                peispatientfeeitemMapper.deleteById(pf);
                            }
                        }
                    } else if ("modified".equals(list.get(j).getState())) {
                        //修改
                        if (!StringUtils.isBlank(list.get(j).getId())) {
                            if (null != pf) {
                                //优惠价格
                                pf.setFactprice(Double.valueOf(list.get(j).getFactprice()));
                                peispatientfeeitemMapper.updateById(pf);
                                // 应收
                                amount += pf.getFactprice();
                            }
                        }
                    } else if ("added".equals(list.get(j).getState())) {
                        //添加
                        if (null != pf) {
                            // 重复
                            throw new ServiceException("保存失败：第" + (j + 1) + "个收费项目已经重复！");
                        } else {
                            Peispatientfeeitem peispatientfeeitem = new Peispatientfeeitem();
                            peispatientfeeitem.setIdPatient(peispatient.getPatientcode());
                            peispatientfeeitem.setIdExamfeeitem(list.get(j).getIdExamfeeitem());
                            peispatientfeeitem.setExamfeeitemName(list.get(j).getExamfeeitemName());
                            peispatientfeeitem.setPrice(Double.valueOf(list.get(j).getPrice()));
                            peispatientfeeitem.setFactprice(Double.valueOf(list.get(j).getFactprice()));
                            peispatientfeeitem.setCount(1);
                            peispatientfeeitem.setIdPayway(payId);
                            peispatientfeeitem.setFRegistered(0);
                            peispatientfeeitem.setChangeItem(0);
                            peispatientfeeitem.setFMarkFeereturn(0);
                            peispatientfeeitem.setFFeecharged(0);
                            peispatientfeeitem.setFLabsendtolis(0);
                            peispatientfeeitem.setFExaminated(0);
                            peispatientfeeitem.setFGiveup(0);
                            peispatientfeeitem.setFDelayed(0);
                            peispatientfeeitem.setIdKs(list.get(j).getIdKs());
                            peispatientfeeitems.add(peispatientfeeitem);
                            // 应收
                            amount += peispatientfeeitem.getFactprice();
                        }
                        // 已分检完成修改未分检完成
                        peispatient.setFReadytofinal(0);
                        peisStateService.setScbs(peispatient.getPatientcode(), 0);
                    }
                }
                // 应付金额
                peispatient.setMoneyamount(amount);
                // 体检者费用主表
                PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new QueryWrapper<PeispatientChargeMain>()
                        .eq("patientcode", peispatient.getPatientcode()));
                String newNote = SecurityUtils.getUsername() + "团检加项弃项于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ";";
                if (ObjectUtils.isEmpty(pcm)) {
                    throw new ServiceException("未找到体检者收费主表");
                } else {
                    //更新
                    pcm.setMoneyamount(peispatient.getMoneyamount());
                    pcm.setNote(newNote);
                    peispatientChargeMainMapper.updateById(pcm);
                }
                //查找剩余的未检收费项目是否是弃检、迟检、退项状态判断是否需要分检完成，无科室的检查完
                checkFj(peispatient);
            }

            // 保存收费项目
            if (peispatientfeeitems.size() > 0) {
                peispatientfeeitemService.saveBatch(peispatientfeeitems);
            }
        } else {
            throw new ServiceException("请选择一条体检人员记录！");
        }

        return Boolean.TRUE;
    }


    /**
     * 查找剩余的未检收费项目是否是弃检、迟检、退项状态判断是否需要分检完成，无科室的检查完
     *
     * @param pei
     */
    @Transactional(rollbackFor = Exception.class)
    public void checkFj(Peispatient pei) {
        // 体检者不存在
        if (null == pei) {
            return;
        }
        // 功能科室
        List<String> depIds = sysDeptMapper.getFunctionKsIds(1);
        // 查找未检、未弃检、未退项、存在科室的收费项目数量
        List<Peispatientfeeitem> peispatientfeeitems = depIds.size() == 0 ? new ArrayList<Peispatientfeeitem>()
                : peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .in("id_ks", depIds)
                .eq("id_patient", pei.getPatientcode())
                .eq("change_item", 0)
                .eq("f_giveup", 0)
                .eq("sfjj", 0)
                .isNotNull("id_ks")
                .isNull("f_transferedhl7")
                .eq("f_examinated", 0));
        if (peispatientfeeitems.size() == 0) {
            pei.setFReadytofinal(1);
            peisStateService.setScbs(pei.getPatientcode(), 0);
            pei.setReadytofinalDate(new Date());
            // 无关联科室已检
            List<Peispatientfeeitem> others = peispatientfeeitemMapper.irrelevantInspect(pei.getPatientcode());

            for (Peispatientfeeitem peispatientfeeitem : others) {
                peispatientfeeitem.setFExaminated(1);//设置未关联科室项目为已检,反审核时不改回去
                // 更新收费实体类
                peispatientfeeitemService.updateById(peispatientfeeitem);
            }
        } else {
            pei.setFReadytofinal(0);
            peisStateService.setScbs(pei.getPatientcode(), 0);
        }
        peispatientMapper.updateById(pei);
    }


    /**
     * 禁检、反禁检操作
     *
     * @param paused
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean savePausedStatus(Integer paused, List<String> ids) {
        List<Peispatient> list = new ArrayList<Peispatient>();

        for (int i = 0; i < ids.size(); i++) {
            Peispatient peispatient = peispatientMapper.getInfoById(ids.get(i));
            if (ObjectUtils.isEmpty(peispatient)) {
                throw new ServiceException("操作失败：第" + (i + 1) + "条记录已归档，请召回后再操作！");
            }
            //0 反禁检 1 禁检 ,任务分组ID
            if (paused == 0 && peispatient.getIdOrgreservationgroup() != null) {
                //体检者团体任务
                Peisorgreservation peisorgreservation = peisorgreservationMapper.selectOne(new QueryWrapper<Peisorgreservation>()
                        .eq("id", peispatient.getIdOrgreservation())
                        .eq("f_finished", 1));
                if (peisorgreservation != null) {
                    throw new ServiceException("操作失败：第" + (i + 1) + "条记录订单已结束，不能反禁检！");
                }
            }
            //禁检
            log.info("禁检、反禁检操作，操作人：{},禁检体检号:{}",SecurityUtils.getUsername(),peispatient.getPatientcode());
            peispatient.setFPaused(paused);
            list.add(peispatient);
        }
        this.updateBatchById(list);

        return Boolean.TRUE;
    }


    /**
     * 获取右侧收费项目
     *
     * @param patientCode
     * @param type
     * @return
     */
    @Override
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
        //收费项目表
        List<Peispatientfeeitem> list = peispatientfeeitemMapper.selectList(and.orderByAsc("qty")
                .eq("id_patient", patientCode));
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        //体检者表
        Peispatient patient = peispatientMapper.getByPatientCode(patientCode);
        //体检套餐ID
        String idtjtc = patient.getIdTjtc();
        //普通套餐表
        Createmeal cm = null;
        Integer bxcount = null;
        if (idtjtc != null) {
            cm = createmealMapper.getInfoById(idtjtc);
            //可选数量
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
            //查询医师
            SysUser qxUsers = sysUserMapper.selectUserByUserNo(peispatientfeeitem.getJxys());
            map.put("name", null == qxUsers ? "" : qxUsers.getUserName());
            map.put("FRegistered", peispatientfeeitem.getFRegistered());
            map.put("changeItem", peispatientfeeitem.getChangeItem());
            map.put("FMarkFeereturn", peispatientfeeitem.getFMarkFeereturn());
            map.put("FFeecharged", peispatientfeeitem.getFFeecharged());
            map.put("FLabsendtolis", peispatientfeeitem.getFLabsendtolis());
            map.put("FExaminated", peispatientfeeitem.getFExaminated());
            map.put("FGiveup", peispatientfeeitem.getFGiveup());
            map.put("FDelayed", peispatientfeeitem.getFDelayed());
            map.put("FTransferedhl7", peispatientfeeitem.getFTransferedhl7());
            map.put("isMintc", peispatientfeeitem.getIsMintc());
            map.put("idKs", peispatientfeeitem.getIdKs());
            //科室名称
            SysDept sysDept = sysDeptMapper.getByDeptNo(peispatientfeeitem.getIdKs());
            map.put("ksName", ObjectUtils.isNotEmpty(sysDept) ? sysDept.getDeptName() : "");
            map.put("feechargetime", peispatientfeeitem.getFeechargetime());
//            map.put("isbx", peispatientfeeitem.getIsbx());
            map.put("bxcount", bxcount);
            map.put("idDoctorreg", peispatientfeeitem.getIdDoctorreg());
            map.put("doctorregR", peispatientfeeitem.getDoctorregR());
            Items it = itemsMapper.getInfoById(peispatientfeeitem.getIdExamfeeitem());
//            map.put("FFeechargedinttrans", peispatientfeeitem.getFFeechargedinttrans());// 性别
            map.put("FFeechargedinttrans", ObjectUtils.isNotEmpty(it) ? it.getXb() : "");// 性别
            map.put("createdate", ObjectUtils.isNotEmpty(peispatientfeeitem.getCreatedate())?
                    peispatientfeeitem.getCreatedate():peispatientfeeitem.getCreateDate());
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
            map.put("isAdd", peispatientfeeitem.getSfjx());
            //备选
            if (peispatientfeeitem.getIsbx() != null
                    && peispatientfeeitem.getIsbx() == 1
            ) {
                if (cm != null) {
                    //普通套餐与收费项目关联表
                    Mealanditem mai = mealanditemMapper.selectOne(new QueryWrapper<Mealanditem>()
                            .eq("tcid", idtjtc).eq("sfxmid", peispatientfeeitem.getIdExamfeeitem())
                    );
                    if (mai != null) {
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
}

