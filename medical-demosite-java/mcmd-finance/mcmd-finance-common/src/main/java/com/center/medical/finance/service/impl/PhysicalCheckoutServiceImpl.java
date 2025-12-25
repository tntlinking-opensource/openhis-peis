package com.center.medical.finance.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.MarriageType;
import com.center.medical.bean.model.Dictpayway;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientcharge;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.*;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.DictpaywayMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientchargeMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.data.bean.model.InspectCharge;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.InspectChargeMapper;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.finance.bean.dto.AccountsTotalDto;
import com.center.medical.finance.bean.param.AccountsInfoParam;
import com.center.medical.finance.bean.param.AnalyseParam;
import com.center.medical.finance.bean.vo.AccountsInfoVo;
import com.center.medical.finance.bean.vo.AnalyseVo;
import com.center.medical.finance.bean.vo.ExportItemsVo;
import com.center.medical.finance.bean.vo.PCItemListVo;
import com.center.medical.finance.dao.PhysicalCheckoutMapper;
import com.center.medical.finance.service.PhysicalCheckoutService;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.dao.*;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单表(Createorder)表服务实现类
 *
 * @author ay
 * @since 2023-03-11 18:01:03
 */
@Slf4j
@Service("physicalCheckoutService")
@RequiredArgsConstructor
public class PhysicalCheckoutServiceImpl extends ServiceImpl<PhysicalCheckoutMapper, Createorder> implements PhysicalCheckoutService {

    private final PhysicalCheckoutMapper physicalCheckoutMapper;
    private final ISysBranchService iSysBranchService;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final PeisorgreservationMapper peisorgreservationMapper;
    private final PeispatientMapper peispatientMapper;
    private final PeispatientService peispatientService;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final CreatemealMapper createmealMapper;
    private final SysUserMapper sysUserMapper;
    private final ItemsMapper itemsMapper;
    private final MealanditemMapper mealanditemMapper;
    private final DictpaywayMapper dictpaywayMapper;
    private final ComboanditemMapper comboanditemMapper;
    private final CreatecomboMapper createcomboMapper;
    private final SysDeptMapper sysDeptMapper;
    private final ComboexamitemMapper comboexamitemMapper;
    private final InspectChargeMapper inspectChargeMapper;
    private final PeispatientchargeMapper peispatientchargeMapper;

    @Autowired
    private LoadProperties loadProperties;

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AnalyseVo> getList(PageParam<AnalyseVo> page, AnalyseParam param) {
        //判断有无财务管理权限
        Boolean greatLeader = SecurityUtils.hasRole(RoleAuthName.CAIWU);
        Boolean isLeader = SecurityUtils.isLeader();
        //财务权限和领导查所有的,领导查下级,其他查自己的
        if (greatLeader && isLeader) {
         //财务权限和领导查所有的
        }else if (isLeader){
            List<String> lowerLevelIds = sysUserMapper.getLowerLevelId(SecurityUtils.getUserNo());
            lowerLevelIds.add(SecurityUtils.getUserNo());
            param.setLowerLevelIds(lowerLevelIds);
        }else {
            param.setUserNo(SecurityUtils.getUserNo());
            param.setUserName(SecurityUtils.getUsername());
        }

        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return physicalCheckoutMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Createorder getInfoById(String id) {
        return physicalCheckoutMapper.getInfoById(id);
    }

    ;

    /**
     * 查看左中体检人数据
     *
     * @param param
     * @return
     */
    @Override
    public IPage<AccountsInfoVo> getAccountsInfoData(PageParam<AccountsInfoVo> page, AccountsInfoParam param) {
        //设置支付方式康淘
        param.setIdPayway(Dictpayway.KT);
        //补全体检号 补0
        if (ObjectUtils.isNotEmpty(param.getDdinputCode())) {
            String patientCode = ToolUtil.patientCode(param.getDdinputCode(), iSysBranchService.getBranchFlag(null));
            param.setDdinputCode(patientCode);
        }
        IPage<AccountsInfoVo> iPage = new PageParam<>();
        if (ObjectUtils.isNotEmpty(param.getContainOldSystem()) && 1 == param.getContainOldSystem()){

            // TODO: wait 需要补充查询老系统报告的逻辑
            if (StringUtils.equals(loadProperties.name, "jindu")){
                //锦都没有老系统
                iPage = physicalCheckoutMapper.getAccountsInfoData(page, param);
            }else {
                //查询新系统+老系统数据,查询出来所有的数据再进行排序
                List<AccountsInfoVo> newSysList = physicalCheckoutMapper.getAccountsInfoList(param);
                List<AccountsInfoVo> oldSysList = physicalCheckoutMapper.getAccountsOldList(param);
                newSysList.addAll(oldSysList);
                //排序
                Comparator<AccountsInfoVo> comparator = new Comparator<AccountsInfoVo>() {
                    @Override
                    public int compare(AccountsInfoVo obj1, AccountsInfoVo obj2) {
                        // 处理空值情况
                        if (obj1.getMedicaldate() == null && obj2.getMedicaldate() == null) {
                            return 0;
                        } else if (obj1.getMedicaldate() == null) {
                            return 1;
                        } else if (obj2.getMedicaldate() == null) {
                            return -1;
                        }
                        // 默认比较规则
                        return obj2.getMedicaldate().compareTo(obj1.getMedicaldate());
                    }
                };
                Collections.sort(newSysList, comparator);
                //设置分页返回数据
                iPage = ToolUtil.getPages((int) page.getCurrent(), (int) page.getSize(), newSysList);
            }

        }else {
            iPage = physicalCheckoutMapper.getAccountsInfoData(page, param);
        }

        List<AccountsInfoVo> list = iPage.getRecords();


        boolean containUnchecked = "1".equals(String.valueOf(param.getContainUnchecked()));
        for (AccountsInfoVo vo : list) {
            Integer fRegistered = vo.getFRegistered();
            Double tcyhj = vo.getTcyhj();
            //如果含统收且未登记
            if (containUnchecked && fRegistered != 1) {
                vo.setSsts(tcyhj);//统收实收=套餐优惠价
                vo.setSsqt(0.0);//其他实收=0.0
                vo.setSskt(0.0);//康淘实收=0.0
                vo.setSshj(tcyhj);//合计实收=套餐优惠价
            } else {
                double ssts = vo.getSsts();
                double sshj = vo.getSshj();
                double sskt = vo.getSskt();
                //相减
                vo.setSsqt(MathUtil.sub(MathUtil.sub(sshj, ssts), sskt));
            }
            String line = getLine(vo.getFRegistered(), vo.getFReadytofinal());
            vo.setLine(line);
            vo.setLinenum("未检".equals(line) ? 0 : "部分".equals(line) ? 1 : "完成".equals(line) ? 2 : 3);
        }
        iPage.setRecords(list);
        return iPage;
    }


    private String getLine(Object start, Object finish) {
        boolean begin = start == null ? false : "1".equals(start.toString()) ? true : false;
        boolean end = finish == null ? false : "1".equals(finish.toString()) ? true : false;
        if (begin) {
            if (end) {
                return "完成";
            } else {
                return "部分";
            }
        } else {
            return "未检";
        }
    }

    /**
     * 右上禁检或反禁检
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
     * 左中-禁检或反禁检
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
            //体检者表
            Peispatient peispatient = peispatientMapper.getInfoById(ids.get(i));
            //已登记不能禁检
            if (peispatient.getFRegistered() == 1) {
                throw new ServiceException("操作失败：第" + (i + 1) + "条记录已登记，不能禁检！");
            }
            if (null == peispatient) {
                throw new ServiceException("操作失败：第" + (i + 1) + "条记录已归档，请召回后再操作！");
            }
            // 禁检 和 任务分组ID 不为空
            if (paused == 0 && peispatient.getIdOrgreservationgroup() != null) {
                Long count = peisorgreservationMapper.selectCount(new QueryWrapper<Peisorgreservation>()
                        .eq("id", peispatient.getIdOrgreservation())
                        .eq("f_finished", 1));
                if (count > 0) {
                    throw new ServiceException("操作失败：第" + (i + 1) + "条记录订单已结束，不能反禁检！");
                }
            }
            //禁检
            log.info("左中-禁检或反禁检,操作人：{},禁检体检号:{}",SecurityUtils.getUsername(),peispatient.getPatientcode());
            peispatient.setFPaused(paused);
            list.add(peispatient);
        }
        //批量更新
        peispatientService.updateBatchById(list);
        return Boolean.TRUE;
    }

    /**
     * 左中-已结账
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean finishStatus(List<String> ids) {
        //之前那个太慢了,现在有大量要结账的,直接更新吧
        //只有checkoutStatus为0、checkoutDate为空时才更新
        LambdaUpdateWrapper<Peispatient> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Peispatient::getId, ids)
                .and(wrapper -> wrapper.eq(Peispatient::getCheckoutStatus,0).or().isNull(Peispatient::getCheckoutStatus))
                .set(Peispatient::getCheckoutStatus, 1).set(Peispatient::getCheckoutDate, new Date());
        peispatientService.update(null, updateWrapper);
        return Boolean.TRUE;
    }

    /**
     * 左中-反结账
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean unfinishStatus(List<String> ids) {
        List<Peispatient> list = new ArrayList<Peispatient>();
        for (int i = 0; i < ids.size(); i++) {
            Peispatient peispatient = peispatientMapper.getInfoById(ids.get(i));
            if (null == peispatient) {
                throw new ServiceException("操作失败：第" + (i + 1) + "条记录已归档，请召回后再操作！");
            }
            if (peispatient.getCheckoutStatus() == null || peispatient.getCheckoutStatus() == 0) {
                throw new ServiceException("操作失败：第" + (i + 1) + "条记录为未结账，不需要反结账");
            }
        }
        //批量更新,点击已结账的结账日期可以为空
        LambdaUpdateWrapper<Peispatient> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.in(Peispatient::getId, ids)
                .set(Peispatient::getCheckoutStatus, 0)
                .set(Peispatient::getCheckoutDate,null);
        peispatientService.update(null, lambdaUpdateWrapper);

        return Boolean.TRUE;
    }

    /**
     * 左中-未检禁检
     *
     * @param paused
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean unSavePausedStatus(Integer paused, List<String> ids) {
        List<Peispatient> list = new ArrayList<Peispatient>();
        for (int i = 0; i < ids.size(); i++) {
            Peispatient peispatient = peispatientMapper.getInfoById(ids.get(i));
            if (null == peispatient) {
                throw new ServiceException("操作失败：第" + (i + 1) + "条记录已归档，请召回后再操作！");
            }
            if (paused == 0 && peispatient.getIdOrgreservationgroup() != null) {
                Long count = peisorgreservationMapper.selectCount(new QueryWrapper<Peisorgreservation>()
                        .eq("id", peispatient.getIdOrgreservation())
                        .eq("f_finished", 1));
                if (count > 0) {
                    throw new ServiceException("操作失败：第" + (i + 1) + "条记录订单已结束，不能未检反禁检!");
                }
            }
            //禁检
            log.info("左中-未检禁检,操作人：{},禁检体检号:{}",SecurityUtils.getUsername(),peispatient.getPatientcode());
            peispatient.setFPaused(paused);
            list.add(peispatient);
        }
        //批量更新
        peispatientService.updateBatchById(list);
        return Boolean.TRUE;
    }

    /**
     * 右中-项目列表数据
     *
     * @param patientcode
     * @return
     */
    @Override
    public List<Map<String, Object>> getItemListData(String patientcode) {

        List<PCItemListVo> list = physicalCheckoutMapper.getItemListData(patientcode);

        if (list.size() == 0) {
            throw new ServiceException("体检号为【" + patientcode + "】的体检者不存在");
        }
        PCItemListVo vo = list.get(0);
        String idTjtc = vo.getIdTjtc();
        String ddId = vo.getDdId();
        String groupId = vo.getGroupId();
        String idExamtype = vo.getIdExamtype();
        String jhys = vo.getJhys();
        String medicaltype = vo.getMedicaltype();

        List<Map<String, Object>> itemData = getExamfeeByPatientCode(patientcode, "1", idTjtc);
        if (CollectionUtils.isEmpty(itemData)) {
            // 关联备单分组的体检套餐下的收费项目
            if (StringUtils.isNotEmpty(groupId)) {
                itemData = getExamfeeitemData(idTjtc, ddId, groupId);
            }
        }
        //增加职业必检列
        if (itemData != null) {
            List<Map<String, Object>> idata = itemData;
            if ("1".equals(idExamtype) || "3".equals(idExamtype)) {
                for (Map<String, Object> im : idata) {
                    im.put("zybj", "1");
                }
            } else if ("2".equals(idExamtype)) {
                List<Comboexamitem> eis = comboexamitemMapper.selectList(new QueryWrapper<Comboexamitem>()
                        .in("harm_id", jhys.split(",")).eq("medical_type", medicaltype));
                HashMap<String, Comboexamitem> ceis = new HashMap<String, Comboexamitem>();
                for (Comboexamitem cei : eis) {
                    ceis.put(cei.getExamId(), cei);
                }
                for (Map<String, Object> im : idata) {
                    String idExamfeeitem = (String) im.get("idExamfeeitem");
                    //检查项目收费项目关联表
                    List<InspectCharge> ics = inspectChargeMapper.selectList(new QueryWrapper<InspectCharge>()
                            .eq("charge_id", idExamfeeitem));
                    String zybj = "0";
                    for (InspectCharge ic : ics) {
                        if (ceis.get(ic.getInspectId()) != null) {
                            zybj = "1";
                            break;
                        }
                    }
                    im.put("zybj", zybj);
                }
            } else {
                for (Map<String, Object> im : idata) {
                    im.put("zybj", "0");
                }
            }
            itemData = idata;
        }
        return itemData;
    }


    /**
     * 获取右侧收费项目
     *
     * @param patientCode
     * @param type
     * @return
     */
    @Override
    public List<Map<String, Object>> getExamfeeByPatientCode(String patientCode, String type, String idtjtc) {
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
            map.put("feechargetime", peispatientfeeitem.getFeechargetime());
//            map.put("isbx", peispatientfeeitem.getIsbx());
            map.put("bxcount", bxcount);
            map.put("idDoctorreg", peispatientfeeitem.getIdDoctorreg());
            map.put("doctorregR", peispatientfeeitem.getDoctorregR());
            Items it = itemsMapper.selectOne(new LambdaQueryWrapper<Items>().eq(Items::getId,peispatientfeeitem.getIdExamfeeitem()));
//            map.put("FFeechargedinttrans", peispatientfeeitem.getFFeechargedinttrans());// 性别
            if (ObjectUtils.isNotEmpty(it)){
                map.put("FFeechargedinttrans", it.getXb());// 性别
            }
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


    /**
     * 获取体检者收费项目
     *
     * @param tcid
     * @param idOrder
     * @param idGroup
     * @return
     */
    public List getExamfeeitemData(String tcid, String idOrder, String idGroup) {
        // 获取套餐下的收费项目
        List<HashMap<String, String>> items = getExamfeeitem(tcid);
        Dictpayway payWay = null;
        String idOrgreservationgroup = "";
        String orgreservationgroupname = "";
        if (StringUtils.isBlank(idOrder)) {
            payWay = dictpaywayMapper.selectOne(new QueryWrapper<Dictpayway>().eq("payway_name", "现金"));
        } else {
            //体检者团体任务
            Peisorgreservation vation = peisorgreservationMapper.selectOne(new QueryWrapper<Peisorgreservation>().eq("ddh", idOrder));
            if (ObjectUtils.isNotEmpty(vation)) {
                //体检者任务分组
                Peisorgreservationgroup group = peisorgreservationgroupMapper.selectOne(new QueryWrapper<Peisorgreservationgroup>()
                        .eq("id_examsuite", tcid).eq("id_orgreservation", vation.getId()).eq("is_delete", 0)
                        .orderByDesc("createdate").last("limit 1")
                );
                payWay = group == null ? null : dictpaywayMapper.getInfoById(group.getIdPayway());
                // 获取套餐对应的分组
                idOrgreservationgroup = group == null ? null : group.getId();
                orgreservationgroupname = group == null ? null : group.getOrgreservationgroupname();
            }
        }
        List list = new ArrayList();
        Boolean isMakeGb = false;
        int size = items.size();
        for (int j = 0; j < size; j++) {
            HashMap<String, String> mm = items.get(j);
            HashMap map = new HashMap();
            // 收费项目id
            map.put("idExamfeeitem", mm.get("idExamfeeitem"));
            // 收费项目名称
            map.put("examfeeitemName", mm.get("examfeeitemName"));
            // 原始价格
            map.put("price", mm.get("price"));

            Object[] oa = getFactPrice(mm, size, j, isMakeGb);
            if (!isMakeGb) {
                isMakeGb = (Boolean) oa[1];
            }
            map.put("factprice", oa[0]);

            // 优惠价格
            // map.put("factprice", mm.get("factprice"));
            // 数量
            map.put("count", 1);
            // 收费方式
            map.put("idPayway", payWay == null ? null : payWay.getId());
            // 科室
            map.put("idKs", mm.get("idKs"));
            map.put("sfjx", 0);
            map.put("changeItem", 0);
            // 弃检
            map.put("fGiveup", 0);
            // 迟检
            map.put("fDelayed", 0);
            map.put("isMintc", 1);
            // 是否备选
            map.put("isbx", mm.get("isbx"));
            // 备选数量
            map.put("bxcount", mm.get("bxcount"));
            // 登记人ID
            map.put("idDoctorreg", SecurityUtils.getUserNo());
            SysUser user = sysUserMapper.selectUserByUserNo(SecurityUtils.getUserNo());
            map.put("doctorregR", null == user ? "" : user.getUserName());
            map.put("idOrgreservationgroup", idOrgreservationgroup);
            map.put("orgreservationgroupname", orgreservationgroupname);
            map.put("fFeechargedinttrans", mm.get("idSex"));
            map.put("qty", mm.get("qty"));
            map.put("group", mm.get("group"));
            map.put("groupType", mm.get("groupType"));
            list.add(map);
        }

        return list;
    }


    /**
     * 获取登记页面最小套餐收费项目明细
     *
     * @param tcid
     * @return
     */
    @Override
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
                    map.put("isbx", isbx+"");
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


    public Object[] getFactPrice(HashMap<String, String> map, int size, int index, boolean isMakeGb) {
        Object[] result = {null, false};
        if ("1".equals(map.get("isbx"))) {
            result[0] = map.get("zhjg");
        } else {
            boolean isFunction = false;
            String idKs = map.get("idKs");
            if (StringUtils.isNotEmpty(idKs)) {
                SysDept dept = sysDeptMapper.getByDeptNo(idKs);
                //是否为功能部门0为非功能科室1为功能科室
                if (dept != null && dept.getIsFunction() != null && "1".equals(dept.getIsFunction())) {
                    isFunction = true;
                }
            }

            // 【个检报告工本费】
//             if (map.get("examfeeitemName").equals("个检报告工本费")) {
            if (!isFunction && !isMakeGb) {//不是功能科室
                result[0] = map.get("zhjg");
                result[1] = true;
            } else {
                // 套餐中不存在【个检报告工本费】就把折扣价放在最后一个收费项目
                if ((index + 1) == size && !isMakeGb) {
                    result[0] = map.get("zhjg");
                } else {
                    result[0] = "0";
                }
            }
        }
        return result;
    }

    /**
     * 右下-收费信息
     *
     * @param patientCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getChargeData(String patientCode) {
        // 判断是否为空
        if (StringUtils.isBlank(patientCode)) {
            return new ArrayList();
        }
        //支付方式
        Dictpayway dictPayWay = new Dictpayway();
        List<Peispatientcharge> list = peispatientchargeMapper.selectList(new QueryWrapper<Peispatientcharge>().orderByAsc("num_index")
                .eq("patientcode", patientCode).eq("is_delete", 0));

        List<Map<String, Object>> listMap = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> dataMap = BeanUtil.beanToMap(list.get(i));
            //支付方式
            dictPayWay = dictpaywayMapper.getInfoById(list.get(i).getIdPayway());
            //收费员姓名
            if (ObjectUtils.isNotEmpty(list.get(i).getIdFeecharger())) {
                SysUser user = sysUserMapper.getUserByNo(list.get(i).getIdFeecharger());
                dataMap.put("userName", ObjectUtils.isNotEmpty(user) ? user.getUserName() : "");
            }
            //是否可以编辑卡号
            if (ObjectUtils.isNotEmpty(dictPayWay)) {
                dataMap.put("payWayName", dictPayWay.getPaywayName());
                if (ObjectUtils.isNotEmpty(dictPayWay.getIsChange())) {
                    Integer num = dictPayWay.getIsChange();
                    dataMap.put("isChange", num);
                }
            }
            listMap.add(dataMap);
        }
        return listMap;
    }

    /**
     * 导出收费项目
     *
     * @param patientCode
     * @return
     */
    @Override
    public List<ExportItemsVo> exportItems(String patientCode) {
        return physicalCheckoutMapper.exportItems(patientCode);
    }

    /**
     * 导出体检人员上方数据
     *
     * @param param
     * @return
     */
    @Override
    public void exportAccountsInfoData(AccountsInfoParam param) {
        //设置支付方式康淘
        param.setIdPayway(Dictpayway.KT);

        //补全体检号 补0
        if (ObjectUtils.isNotEmpty(param.getDdinputCode())) {
            String patientCode = ToolUtil.patientCode(param.getDdinputCode(), iSysBranchService.getBranchFlag(null));
            param.setDdinputCode(patientCode);
        }
        //取出集合
        List<AccountsInfoVo> data = physicalCheckoutMapper.exportAccountsInfoData(param);
        //是否包含老系统
        if (ObjectUtils.isNotEmpty(param.getContainOldSystem()) && param.getContainOldSystem() == 1){
            List<AccountsInfoVo> oldSysList = physicalCheckoutMapper.getAccountsOldList(param);
            data.addAll(oldSysList);
            //排序
            Comparator<AccountsInfoVo> comparator = new Comparator<AccountsInfoVo>() {
                @Override
                public int compare(AccountsInfoVo obj1, AccountsInfoVo obj2) {
                    // 处理空值情况
                    if (obj1.getMedicaldate() == null && obj2.getMedicaldate() == null) {
                        return 0;
                    } else if (obj1.getMedicaldate() == null) {
                        return 1;
                    } else if (obj2.getMedicaldate() == null) {
                        return -1;
                    }
                    // 默认比较规则
                    return obj2.getMedicaldate().compareTo(obj1.getMedicaldate());
                }
            };
            Collections.sort(data, comparator);
        }


        boolean containUnchecked = "1".equals(String.valueOf(param.getContainUnchecked()));

        ArrayList<ArrayList> tableData = new ArrayList<ArrayList>();
        for (int i = 0, s = data.size(); i < s; i++) {
            AccountsInfoVo os =  data.get(i);
            //未登记的要清除 登记时间
            if(ObjectUtils.isNotEmpty(os.getFRegistered()) && os.getFRegistered()==0){
                os.setMedicaldate(null);
                os.setDoctorreg(null);
                os.setChargePayway(null);
            }
            ArrayList<Object> list = new ArrayList<Object>();
            list.add(i + 1);
            list.add(os.getPatientcode());
            String fRegistered = os.getFRegistered() == null ? "0" : os.getFRegistered().toString();
            list.add(getLine(os.getFRegistered(), os.getFReadytofinal()));
            os.setLine(getLine(os.getFRegistered(), os.getFReadytofinal()));
            list.add(Render.getIsNot(os.getFPaused()));
            list.add(os.getGroupName());
            list.add(os.getPatientname());
            list.add(Render.getIsNot(os.getFRegistered()));
            list.add(Render.getSex(os.getSex()));
            list.add(MarriageType.getName(os.getIdMarriage()));
            list.add(os.getAge());

            list.add(Render.getIsNot(os.getCountreportxml()));
            list.add(os.getTjr());

            list.add(Render.getTjlx(os.getIdExamtype()));
            list.add(Render.getTjzt(os.getJktjzt()));
            list.add(Render.getTjzt(os.getZytjzt()));

            list.add(os.getExamName());
            list.add(Render.getDouble(os.getPrice()));
            list.add(Render.getDouble(os.getTcyj()));
            double tcyhj = Render.getDouble(os.getTcyhj());//15
            list.add(tcyhj);
            if (containUnchecked && !"1".equals(fRegistered)) {//如果含统收且未登记
                list.add(tcyhj);//统收实收=套餐优惠价
                list.add(0.0);//康淘0.0
                list.add(0.0);//工费0.0
                list.add(0.0);//其他实收=0.0
                list.add(tcyhj);//合计实收=套餐优惠价
            } else {
                double ssts = Render.getDouble(os.getSsts());
                double sshj = Render.getDouble(os.getSshj());
                double sskt = Render.getDouble(os.getSskt());
                double ssgf=Render.getDouble(os.getSsqt());
                list.add(ssts);
                list.add(sskt);
                list.add(ssgf);
                list.add(MathUtil.sub(MathUtil.sub(MathUtil.sub(sshj, ssts), sskt),ssgf));
                list.add(sshj);
            }
            list.add(Render.getDouble(os.getSsAdd()));
            list.add(os.getOrgDepart());
            list.add(os.getMedicaldate());
            list.add(os.getDoctorreg());
            list.add(Render.getClob(os.getChargePayway()));
            list.add(os.getInputCode());
            list.add(os.getChiveNo());
            list.add(os.getIdcardno());
            list.add(os.getJz());//26
            list.add(os.getJzr());
            list.add(os.getNote());
            list.add(os.getGuidancenote2());
            list.add(os.getPhone());
            String checkzt = "";
            if (os.getCheckoutStatus() == null || Render.getInt(os.getCheckoutStatus()) == 0) {
                checkzt = "未结账";
            } else if (Render.getInt(os.getCheckoutStatus() ) == 1) {
                checkzt = "已结账";
            }
            list.add(checkzt);
            list.add(os.getCheckoutDate());
            list.add(os.getFzx());
            tableData.add(list);
        }
        String[] tableCaption = {"序号", "体检号", "体检状态", "禁检",
                "分组名称", "姓名", "登记", "性别", "婚姻", "年龄", "是否替检", "原体检者", "体检类型", "健康报告状态", "职业报告状态", "套餐", "原价",
                "套餐原价", "套餐优惠价", "统收实收", "康淘实收","工费实收", "其他实收", "合计实收", "加项实收", "部门", "体检时间", "登记员",
                "付款方式", "输入码", "档案号", "身份证号", "记账", "记账人", "备注", "体检号生成人", "电话", "结账状态", "结账日期","分中心"};


        AccountsTotalDto maleAvg = new AccountsTotalDto("男平均");
        AccountsTotalDto femealAvg = new AccountsTotalDto("女平均");
        AccountsTotalDto maleTotal = new AccountsTotalDto("男合计");
        AccountsTotalDto femaleTotal = new AccountsTotalDto("女合计");
        AccountsTotalDto allTotal = new AccountsTotalDto("总合计");
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                AccountsInfoVo item = data.get(i);
                if (item.getSex() == 0) {
                    //男
                    maleTotal.addSelf(item);
                } else if (item.getSex() == 1) {
                    //女
                    femaleTotal.addSelf(item);
                }
                allTotal.addSelf(item);
            }
            maleAvg.avg(maleTotal);
            femealAvg.avg(femaleTotal);
        }
        ArrayList<ArrayList> totalGrid = new ArrayList<ArrayList>();
        totalGrid.add(femealAvg.toArrayList());
        totalGrid.add(maleAvg.toArrayList());
        totalGrid.add(femaleTotal.toArrayList());
        totalGrid.add(maleTotal.toArrayList());
        totalGrid.add(allTotal.toArrayList());


        ArrayList<String[]> tableCaptions = new ArrayList<String[]>();
        tableCaptions.add(tableCaption);
        tableCaptions.add(new String[]{"统计值", "体检者人数", "体检者年龄", "未检人数", "部分完成人数", "全部完成人数"
                , "原价", "套餐原价", "套餐优惠价", "统收实收", "康淘实收", "其他实收", "合计实收", "加项实收", "记账"});
        ArrayList[] myList = new ArrayList[2];
        myList[0] = tableData;
        myList[1] = totalGrid;
        ExcelUtil.exportMore("体检人员", ExcelUtil.SheetType.Single, new String[]{"体检人员"}
                , new String[]{"体检人员", "合计"}
                , myList
                , tableCaptions
                , new int[]{0, 1}
                , null
                , null);

    }

    /**
     * 导出体检人员下方合计数据
     *
     * @param param
     * @return
     */
    @Override
    public List<AccountsTotalDto> exportAccountsTotalDto(AccountsInfoParam param) {

        //设置支付方式康淘
        param.setIdPayway(Dictpayway.KT);

        //补全体检号 补0
        if (ObjectUtils.isNotEmpty(param.getDdinputCode())) {
            String patientCode = ToolUtil.patientCode(param.getDdinputCode(), iSysBranchService.getBranchFlag(null));
            param.setDdinputCode(patientCode);
        }
        //取出集合
        List<AccountsInfoVo> list = physicalCheckoutMapper.exportAccountsInfoData(param);
        //包含老系统
        if (ObjectUtils.isNotEmpty(param.getContainOldSystem()) && param.getContainOldSystem() == 1){
            List<AccountsInfoVo> oldSysList = physicalCheckoutMapper.getAccountsOldList(param);
            list.addAll(oldSysList);
        }

        boolean containUnchecked = "1".equals(String.valueOf(param.getContainUnchecked()));
        for (AccountsInfoVo vo : list) {
            Integer fRegistered = vo.getFRegistered();
            Double tcyhj = vo.getTcyhj();

            //如果含未检且未登记
            if (containUnchecked && fRegistered != 1) {
                vo.setSsts(tcyhj);//统收实收=套餐优惠价
                vo.setSsqt(0.0);//其他实收=0.0
                vo.setSskt(0.0);//康淘实收=0.0
                vo.setSshj(tcyhj);//合计实收=套餐优惠价
            } else {
                double ssts = vo.getSsts();
                double sshj = vo.getSshj();
                double sskt = vo.getSskt();
                //其他实收=合计实收-统收实收-康淘实收
                vo.setSsqt(MathUtil.sub(MathUtil.sub(sshj, ssts), sskt));
            }
            String line = getLine(vo.getFRegistered(), vo.getFReadytofinal());
            vo.setLine(line);
            vo.setLinenum("未检".equals(line) ? 0 : "部分".equals(line) ? 1 : "完成".equals(line) ? 2 : 3);
        }

        AccountsTotalDto maleAvg = new AccountsTotalDto("男平均");
        AccountsTotalDto femealAvg = new AccountsTotalDto("女平均");
        AccountsTotalDto maleTotal = new AccountsTotalDto("男合计");
        AccountsTotalDto femaleTotal = new AccountsTotalDto("女合计");
        AccountsTotalDto allTotal = new AccountsTotalDto("总合计");
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                AccountsInfoVo item = list.get(i);
                if (item.getSex() == 0) {
                    //男
                    maleTotal.addSelf(item);
                } else if (item.getSex() == 1) {
                    //女
                    femaleTotal.addSelf(item);
                }
                allTotal.addSelf(item);
            }
            maleAvg.avg(maleTotal);
            femealAvg.avg(femaleTotal);
        }
        List<AccountsTotalDto> totalGrid = new ArrayList<>();
        totalGrid.add(femealAvg);
        totalGrid.add(maleAvg);
        totalGrid.add(femaleTotal);
        totalGrid.add(maleTotal);
        totalGrid.add(allTotal);
        return totalGrid;
    }


    /**
     * 把老系统登记的体检号，在新系统变成禁检
     * @param ddh
     * @return
     */
    @Override
    public Boolean synchronizedChecked(String ddh) {
        //把老系统登记的体检号，在新系统变成禁检
        List<String> ids = physicalCheckoutMapper.synchronizedChecked(ddh);
        if (CollectionUtils.isNotEmpty(ids)){
            //只修改倒过来的数据，id是一样的
            Peispatient peispatient = new Peispatient();
            log.info("在新系统变成禁检，操作人：{},禁检体检号:{}",SecurityUtils.getUsername(),peispatient.getPatientcode());
            peispatient.setFPaused(1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 使用格式化对象将 Date 转换为字符串
            String dateString = dateFormat.format(new Date());
            peispatient.setNote(dateString + "老系统系统已登记,该体检号已被禁检");
            peispatient.setModifydate(new Date());
            peispatientService.update(peispatient,new LambdaQueryWrapper<Peispatient>().in(Peispatient::getId,ids));
        }
        return Boolean.TRUE;
    }
}

