package com.center.medical.olddata.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.CodeCorresponding;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientcharge;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.bean.param.MigrationOldDataParam;
import com.center.medical.bean.param.OfflineImportPeiParam;
import com.center.medical.bean.param.OnlineImportParam;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.olddata.bean.model.*;
import com.center.medical.olddata.service.*;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.olddata.bean.model.*;
import com.center.medical.olddata.bean.param.AddUserFzxParam;
import com.center.medical.olddata.bean.param.ImportItemsParam;
import com.center.medical.olddata.bean.param.ImportPeiParam;
import com.center.medical.olddata.bean.param.ImportTjPeopleParam;
import com.center.medical.olddata.service.*;
import com.center.medical.service.CodeCorrespondingService;
import com.center.medical.service.PeispatientchargeService;
import com.center.medical.system.bean.model.SysUserBranch;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysUserService;
import com.center.medical.system.service.SysUserBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: ay
 * @date: 2023-08-26 11:28
 * @description: 导入未完成的体检者
 */
@Slf4j
@Service("oldDatabaseQueryService")
@RequiredArgsConstructor
public class OldDatabaseQueryServiceImpl implements OldDatabaseQueryService {

    private final OrCreateorderService createorderService;
    private final MdCreateorderService mdCreateorderService;
    private final MapperFacade mapperFacade;
    private final OrSellcustomerService sellcustomerService;
    private final MdSellcustomerService mdSellcustomerService;
    private final OrOrderandfzxService orderandfzxService;
    private final MdOrderandfzxService mdOrderandfzxService;
    private final OrSellpactService sellpactService;
    private final MdSellpactService mdSellpactService;
    private final OrOrderandcomboService orderandcomboService;
    private final MdOrderandcomboService mdOrderandcomboService;
    private final OrCreatemealService createmealService;
    private final MdCreatemealService mdCreatemealService;
    private final OrCreatecomboService createcomboService;
    private final MdCreatecomboService mdCreatecomboService;
    private final OrComboandfzxService comboandfzxService;
    private final OrComboanditemService comboanditemService;
    private final MdComboandfzxService mdComboandfzxService;
    private final MdComboanditemService mdComboanditemService;
    private final OrMealanditemService mealanditemService;
    private final OrMealandfzxService mealandfzxService;
    private final MdMealanditemService mdMealanditemService;
    private final MdMealandfzxService mdMealandfzxService;
    private final OrPeisorgreservationService peisorgreservationService;
    private final MdPeisorgreservationService mdPeisorgreservationService;
    private final OrPeisorgreservationgroupService peisorgreservationgroupService;
    private final MdPeisorgreservationgroupService mdPeisorgreservationgroupService;
    private final OrPeispatientService peispatientService;
    private final MdPeispatientService mdPeispatientService;
    private final OrPeispatientfeeitemService peispatientfeeitemService;
    private final MdPeispatientfeeitemService mdPeispatientfeeitemService;
    private final OrPeispatientChargeMainService peispatientChargeMainService;
    private final MdPeispatientChargeMainService mdPeispatientChargeMainService;
    private final ISysBranchService iSysBranchService;
    private final PeispatientMapper peispatientMapper;
    private final CodeCorrespondingService codeCorrespondingService;
    private final MdPeispatientarchiveService mdPeispatientarchiveService;
    private final OrPeispatientarchiveService orPeispatientarchiveService;
    private final PeispatientchargeService peispatientchargeService;
    private final MdSectionResultMainService mdSectionResultMainService;
    private final MdSectionResultTwoService mdSectionResultTwoService;
    private final MdSectionTotalService mdSectionTotalService;
    private final OrPeispatientchargeService orPeispatientchargeService;
    private final MdVationAndFzxService mdVationAndFzxService;
    private final OrVationAndFzxService orVationAndFzxService;
    private final OrGroupAndFzxService orGroupAndFzxService;
    private final MdGroupAndFzxService mdGroupAndFzxService;
    private final OrPeispatientAndFzxService orPeispatientAndFzxService;
    private final MdPeispatientAndFzxService mdPeispatientAndFzxService;
    private final OrSectionResultMainService orSectionResultMainService;
    private final OrSectionResultTwoService orSectionResultTwoService;
    private final OrSectionTotalService orSectionTotalService;
    private final OrPeispatientConsultationService orPeispatientConsultationService;
    private final OrPeispatientexamitemService orPeispatientexamitemService;
    private final OrDescribeService orDescribeService;
    private final OrTjregService orTjregService;
    private final OrLungService orLungService;
    private final OrElectroAudiometerService orElectroAudiometerService;
    private final MdPeispatientConsultationService mdPeispatientConsultationService;
    private final MdPeispatientexamitemService mdPeispatientexamitemService;
    private final MdDescribeService mdDescribeService;
    private final MdTjregService mdTjregService;
    private final MdLungService mdLungService;
    private final MdElectroAudiometerService mdElectroAudiometerService;
    private final ISysConfigService iSysConfigService;


    private final OrItemsService orItemsService;
    private final OrItemsAndFzxService orItemsAndFzxService;
    private final OrInspectChargeService orInspectChargeService;
    private final OrBasexamltemService orBasexamltemService;
    private final OrBasexamltemSignService orBasexamltemSignService;


    private final MdItemsService mdItemsService;
    private final MdItemsAndFzxService mdItemsAndFzxService;
    private final MdInspectChargeService mdInspectChargeService;
    private final MdBasexamltemService mdBasexamltemService;
    private final MdBasexamltemSignService mdBasexamltemSignService;


    private final ISysUserService sysUserService;
    private final SysUserBranchService sysUserBranchService;




    /**
     * 导入未完成的体检者
     *
     * @return
     */
    @Override
    public Boolean importPeispatient(ImportPeiParam param) {
        log.info("开始导入未完成的体检者:{}",param);
        SysBranch defaultBranch = iSysBranchService.getDefaultBranch();
        List<String> patientcodes = param.getPatientcodes();

        List<MdPeispatientAndFzx> mdPeispatientAndFzxList = new ArrayList<>();
        List<MdPeispatient> mdPeispatientList = new ArrayList<>();
        List<MdPeispatientfeeitem> mdPeispatientfeeitemList = new ArrayList<>();
        List<Peispatientcharge> peispatientchargeList = new ArrayList<>();
        List<MdPeispatientChargeMain> mdPeispatientChargeMainList = new ArrayList<>();
        List<CodeCorresponding> codeCorrespondingList = new ArrayList<>();
        List<MdVationAndFzx> mdVationAndFzxList = new ArrayList<>();
        List<MdPeisorgreservation> mdPeisorgreservationList = new ArrayList<>();
        List<MdGroupAndFzx> mdGroupAndFzxList = new ArrayList<>();
        List<MdPeisorgreservationgroup> mdPeisorgreservationgroupList = new ArrayList<>();


        List<MdCreateorder> mdCreateorderList = new ArrayList<>();
        List<MdSellcustomer> mdSellcustomerList = new ArrayList<>();
        List<MdOrderandfzx> mdOrderandfzxList = new ArrayList<>();
        List<MdSellpact> mdSellpactList = new ArrayList<>();
        List<MdOrderandcombo> mdOrderandcomboList = new ArrayList<>();

        List<MdMealandfzx> mdMealandfzxList = new ArrayList<>();
        List<MdCreatemeal> mdCreatemealList = new ArrayList<>();
        List<MdMealanditem> mdMealanditemList = new ArrayList<>();
        List<MdComboandfzx> mdComboandfzxList = new ArrayList<>();
        List<MdCreatecombo> mdCreatecomboList = new ArrayList<>();
        List<MdComboanditem> mdComboanditemList = new ArrayList<>();

        for (String patientcode : patientcodes) {
            OrPeispatient peispatient = peispatientService.getInfoByPatientCode(patientcode);
            if (ObjectUtils.isEmpty(peispatient)) {
                log.info("体检号：{}不存在！",patientcode);
                continue;
            }
            if ("1".equals(peispatient.getFRegistered())) {
                log.info("老系统体检号：{}已完成登记！",patientcode);
                continue;
            }


            String oldPatientCode = patientcode;
            //判断是否重复,重复跳过
            Long count = codeCorrespondingService.count(new LambdaQueryWrapper<CodeCorresponding>()
                    .eq(CodeCorresponding::getOldCode, oldPatientCode));
            if (count > 0) {
                continue;
            }

            //判断新系统是否有这个id，有的话，如果是已登记的就跳过，未登记的返回给前端
            MdPeispatient peispatient1 = mdPeispatientService.getInfoById(peispatient.getId());
            if (ObjectUtils.isNotEmpty(peispatient1)) {
                if ("1".equals(peispatient1.getFRegistered())) {
                    continue;
                } else if ("0".equals(peispatient1.getFRegistered())) {
                    System.out.println("导入时id重复,且未登记:" + peispatient.getId());
                    continue;
                }
            }

            //不需要合并，执行导入逻辑
            if (StringUtils.isEmpty(peispatient.getHospitalcode())) {
                peispatient.setHospitalcode(defaultBranch.getBranchId());
            }

            //重新生成体检号
            String patientCode = "";
            do {
                patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(defaultBranch.getBranchId()), "");
            } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getPatientcode, patientCode)) > 0);
            if (isExistByPatientCode(patientCode)) {
                throw new ServiceException("保存失败：生成体检号失败！");
            }
            peispatient.setPatientcode(patientCode);
            //插入老系统及新系统的体检号的对应关系
            codeCorrespondingList.add(new CodeCorresponding(oldPatientCode, patientCode));
            //体检者检查项目 md_peispatientfeeitem
            List<OrPeispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.getByPatientCode(oldPatientCode);
            for (OrPeispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                peispatientfeeitem.setIdPatient(patientCode);
            }
            mdPeispatientfeeitemList.addAll(mapperFacade.mapAsList(peispatientfeeitems, MdPeispatientfeeitem.class));
            //体检者缴费表 md_peispatientcharge
            List<OrPeispatientcharge> orPeispatientcharges = orPeispatientchargeService.getByPatientCode(oldPatientCode);
            for (OrPeispatientcharge orPeispatientcharge : orPeispatientcharges) {
                orPeispatientcharge.setPatientcode(patientCode);
                //导过来的都要改备注，让收费日报那看不到
                String note = orPeispatientcharge.getNote();
                if (StringUtils.isNotEmpty(note)) {
                    if (note.contains("预收")) {
                        orPeispatientcharge.setNote("老系统体检号:" + oldPatientCode + "已预付款!");
                    } else {
                        orPeispatientcharge.setNote(note + " 老系统数据");
                    }
                } else {
                    orPeispatientcharge.setNote("老系统数据");
                }
            }
            peispatientchargeList.addAll(mapperFacade.mapAsList(orPeispatientcharges, Peispatientcharge.class));
            //体检者费用主表 md_peispatient_charge_main
            OrPeispatientChargeMain peispatientChargeMain = peispatientChargeMainService.getByPatientCode(oldPatientCode);
            if (ObjectUtils.isNotEmpty(peispatientChargeMain)) {
                peispatientChargeMain.setPatientcode(patientCode);
                mdPeispatientChargeMainList.add(mapperFacade.map(peispatientChargeMain, MdPeispatientChargeMain.class));
            }


            //体检者和分中心 md_peispatient_and_fzx
            MdPeispatientAndFzx mdPeispatientAndFzx = mdPeispatientAndFzxService.getByPidAndFzx(peispatient.getId(), defaultBranch.getBranchId());
            if (ObjectUtils.isEmpty(mdPeispatientAndFzx)) {
                mdPeispatientAndFzx = new MdPeispatientAndFzx();
                mdPeispatientAndFzx.setPatientId(peispatient.getId());
                mdPeispatientAndFzx.setFzxId(defaultBranch.getBranchId());
            }
            mdPeispatientAndFzxList.add(mdPeispatientAndFzx);

            //修改体检者表中的字段
            //修改婚姻
            if (ObjectUtil.isNotEmpty(peispatient.getIdMarriage())) {
                if ("402881a85417c6ec015417dfc285000e".equals(peispatient.getIdMarriage())) {
                    peispatient.setIdMarriage("2");
                } else if ("402881a85417c6ec015417df521f000d".equals(peispatient.getIdMarriage())) {
                    peispatient.setIdMarriage("1");
                }
            }

            //如果是团检的话，需要判断导入过来没有，导入过来的分组或者任务是否一致
            if (ObjectUtil.isNotEmpty(peispatient.getIdOrgreservation())) {
                MdCreateorder createorder = mdCreateorderService.getByDdh(peispatient.getNumorgresv());
                if (ObjectUtils.isEmpty(createorder)) {
                    String ddh = peispatient.getNumorgresv();
                    //导入订单相关数据 订单 createorder
                    OrCreateorder orCreateorder = createorderService.getInfoByDdh(ddh);
                    createorder = mapperFacade.map(orCreateorder, MdCreateorder.class);
                    mdCreateorderList.add(createorder);

                    // 客户 SellCustomer
                    MdSellcustomer mdSellcustomer = mdSellcustomerService.getInfoById(createorder.getKhdwmcid());
                    if (ObjectUtils.isEmpty(mdSellcustomer)) {
                        OrSellcustomer sellcustomer = sellcustomerService.getInfoById(createorder.getKhdwmcid());
                        mdSellcustomerList.add(mapperFacade.map(sellcustomer, MdSellcustomer.class));
                    }

                    // 订单分中心关联表 OrderAndFzx
                    //查询新系统有没有,有的话就更新，没有的话就用老系统的插入，老系统也没有的话，就插入一条新的
                    MdOrderandfzx mdOrderandfzx = mdOrderandfzxService.getByDdidAndFzxId(createorder.getId(), defaultBranch.getBranchId());
                    if (ObjectUtils.isEmpty(mdOrderandfzx)) {
                        OrOrderandfzx orOrderandfzx = orderandfzxService.getByDdidAndFzxId(createorder.getId(), defaultBranch.getBranchId());
                        if (ObjectUtils.isNotEmpty(orOrderandfzx)) {
                            mdOrderandfzxList.add(mapperFacade.map(orOrderandfzx, MdOrderandfzx.class));
                        } else {
                            MdOrderandfzx mdOrderandfzx1 = new MdOrderandfzx();
                            mdOrderandfzx1.setDdid(createorder.getId());
                            mdOrderandfzx1.setFzxid(defaultBranch.getBranchId());
                            mdOrderandfzx1.setCreatedate(new Date());
                            mdOrderandfzx1.setModifydate(new Date());
                            mdOrderandfzxList.add(mdOrderandfzx1);
                        }
                    }

                    // 合同 SellPact
                    if (ObjectUtil.isNotEmpty(createorder.getHtbh())) {
                        List<MdSellpact> mdSellpact = mdSellpactService.getByHtbh(createorder.getHtbh());
                        if (CollectionUtil.isEmpty(mdSellpact)) {
                            List<OrSellpact> sellpact = sellpactService.getByHtbh(createorder.getHtbh());
                            if (ObjectUtils.isNotEmpty(sellpact)) {
                                mdSellpactList.addAll(mapperFacade.mapAsList(sellpact, MdSellpact.class));
                            }
                        }
                    }

                    // 订单套餐关联表 OrderAndCombo
                    List<OrOrderandcombo> orderandcomboList = orderandcomboService.getByDdId(createorder.getId());
                    mdOrderandcomboList.addAll(mapperFacade.mapAsList(orderandcomboList, MdOrderandcombo.class));

                    for (OrOrderandcombo oac : orderandcomboList) {
                        if ("0".equals(oac.getCombostate())) {
                            // 普通套餐与分中心关联表 MealAndFzx
                            // 查询新系统有没有,有的话就更新，没有的话就用老系统的插入，老系统也没有的话，就插入一条新的
                            MdMealandfzx mdMealandfzx = mdMealandfzxService.getByTcidAndFzx(oac.getTcid(), defaultBranch.getBranchId());
                            if (ObjectUtils.isNotEmpty(mdMealandfzx)) {
                                mdMealandfzxList.add(mdMealandfzx);
                            } else {
                                OrMealandfzx orMealandfzx = mealandfzxService.getByTcidAndFzx(oac.getTcid(), defaultBranch.getBranchId());
                                if (ObjectUtils.isNotEmpty(orMealandfzx)) {
                                    mdMealandfzxList.add(mapperFacade.map(orMealandfzx, MdMealandfzx.class));
                                } else {
                                    MdMealandfzx mealandfzx = new MdMealandfzx();
                                    mealandfzx.setTcid(oac.getTcid());
                                    mealandfzx.setFzxid(defaultBranch.getBranchId());
                                    mealandfzx.setCreatedate(new Date());
                                    mdMealandfzxList.add(mealandfzx);
                                }
                            }

                            // 套餐 CreateMeal
                            OrCreatemeal createmeal = createmealService.getInfoById(oac.getTcid());
                            mdCreatemealList.add(mapperFacade.map(createmeal, MdCreatemeal.class));
                            // 普通套餐与收费项目 MealAndItem
                            List<OrMealanditem> mealanditemList = mealanditemService.getByTcid(oac.getTcid());
                            mdMealanditemList.addAll(mapperFacade.mapAsList(mealanditemList, MdMealanditem.class));

                        } else {
                            // 最小套餐与分中心 ComboAndFzx
                            // 查询新系统有没有,有的话就更新，没有的话就用老系统的插入，老系统也没有的话，就插入一条新的
                            MdComboandfzx mdComboandfzx = mdComboandfzxService.getByTcidAndFzx(oac.getTcid(), defaultBranch.getBranchId());
                            if (ObjectUtils.isNotEmpty(mdComboandfzx)) {
                                mdComboandfzxList.add(mdComboandfzx);
                            } else {
                                OrComboandfzx orComboandfzx = comboandfzxService.getByTcidAndFzx(oac.getTcid(), defaultBranch.getBranchId());
                                if (ObjectUtils.isNotEmpty(orComboandfzx)) {
                                    mdComboandfzxList.add(mapperFacade.map(orComboandfzx, MdComboandfzx.class));
                                } else {
                                    MdComboandfzx mdComboandfzx1 = new MdComboandfzx();
                                    mdComboandfzx1.setTcid(oac.getTcid());
                                    mdComboandfzx1.setFzxid(defaultBranch.getBranchId());
                                    mdComboandfzx1.setCreatedate(new Date());
                                    mdComboandfzxList.add(mdComboandfzx1);
                                }
                            }
                            // 最小套餐 CreateCombo
                            OrCreatecombo createcombo = createcomboService.getInfoById(oac.getTcid());
                            mdCreatecomboList.add(mapperFacade.map(createcombo, MdCreatecombo.class));
                            // 最小套餐与收费项目 ComboAndItem
                            List<OrComboanditem> comboanditemList = comboanditemService.getByTcid(oac.getTcid());
                            mdComboanditemList.addAll(mapperFacade.mapAsList(comboanditemList, MdComboanditem.class));
                        }
                    }
                }

                //修改任务
                MdPeisorgreservation mdPeisorgreservation = mdPeisorgreservationService.getByDdh(createorder.getId());
                if (ObjectUtils.isEmpty(mdPeisorgreservation)) {
                    OrPeisorgreservation orPeisorgreservation = peisorgreservationService.getByDdh(createorder.getId());
                    mdPeisorgreservation = mapperFacade.map(orPeisorgreservation, MdPeisorgreservation.class);
                    mdPeisorgreservationList.add(mdPeisorgreservation);

                    // 体检者团体任务和分中心 md_vation_and_fzx
                    MdVationAndFzx mdVationAndFzx = mdVationAndFzxService.getByVationIdAndFzx(mdPeisorgreservation.getId(), defaultBranch.getBranchId());
                    if (ObjectUtils.isEmpty(mdVationAndFzx)) {
                        OrVationAndFzx orVationAndFzx = orVationAndFzxService.getByVationIdAndFzx(mdPeisorgreservation.getId(), defaultBranch.getBranchId());
                        if (ObjectUtils.isNotEmpty(orVationAndFzx)) {
                            orVationAndFzx.setVationId(mdPeisorgreservation.getId());
                            mdVationAndFzxList.add(mapperFacade.map(orVationAndFzx, MdVationAndFzx.class));
                        } else {
                            MdVationAndFzx mdVationAndFzx1 = new MdVationAndFzx();
                            mdVationAndFzx1.setVationId(mdPeisorgreservation.getId());
                            mdVationAndFzx1.setFzxId(defaultBranch.getBranchId());
                            mdVationAndFzx1.setCreatedate(new Date());
                            mdVationAndFzxList.add(mdVationAndFzx1);
                        }
                    }
                }
                peispatient.setIdOrgreservation(mdPeisorgreservation.getId());


                //修改分组  有的数据没有分组在这做判断
                if (StringUtils.isNotEmpty(peispatient.getIdOrgreservationgroup())) {
                    MdPeisorgreservationgroup mdPeisorgreservationgroup = mdPeisorgreservationgroupService.getByVationIdAndTcId(mdPeisorgreservation.getId(), peispatient.getIdTjtc());
                    if (ObjectUtils.isEmpty(mdPeisorgreservationgroup)) {
                        //判断下新系统有没有这个分组
                        mdPeisorgreservationgroup = mdPeisorgreservationgroupService.getInfoById(peispatient.getIdOrgreservationgroup());
                        if (ObjectUtils.isEmpty(mdPeisorgreservationgroup)) {
                            OrPeisorgreservationgroup orPeisorgreservationgroup = peisorgreservationgroupService.getInfoById(peispatient.getIdOrgreservationgroup());
                            orPeisorgreservationgroup.setIdOrgreservation(mdPeisorgreservation.getId());
                            mdPeisorgreservationgroup = mapperFacade.map(orPeisorgreservationgroup, MdPeisorgreservationgroup.class);
                            mdPeisorgreservationgroupList.add(mdPeisorgreservationgroup);
                            //查询老系统的 分组和分中心 md_group_and_fzx
                            MdGroupAndFzx mdGroupAndFzx = mdGroupAndFzxService.getByGroupIdAndFzx(mdPeisorgreservationgroup.getId(), defaultBranch.getBranchId());
                            if (ObjectUtils.isEmpty(mdGroupAndFzx)) {
                                OrGroupAndFzx orGroupAndFzx = orGroupAndFzxService.getByGroupIdAndFzx(mdPeisorgreservationgroup.getId(), defaultBranch.getBranchId());
                                if (ObjectUtils.isEmpty(orGroupAndFzx)) {
                                    MdGroupAndFzx mdGroupAndFzx1 = new MdGroupAndFzx();
                                    mdGroupAndFzx1.setGroupId(mdPeisorgreservationgroup.getId());
                                    mdGroupAndFzx1.setFzxId(defaultBranch.getBranchId());
                                    mdGroupAndFzx1.setCreatedate(new Date());
                                    mdGroupAndFzxList.add(mdGroupAndFzx1);
                                } else {
                                    mdGroupAndFzxList.add(mapperFacade.map(orGroupAndFzx, MdGroupAndFzx.class));
                                }
                            }
                        }
                    }
                    peispatient.setIdOrgreservationgroup(mdPeisorgreservationgroup.getId());
                }

            }else if (StringUtils.isNotEmpty(peispatient.getIdTjtc())){
                // 个检也要把套餐或者最小套餐导入过来
                OrCreatecombo createcombo = createcomboService.getInfoById(peispatient.getIdTjtc());
                if (ObjectUtils.isNotEmpty(createcombo)){
                    mdCreatecomboList.add(mapperFacade.map(createcombo, MdCreatecombo.class));
                    List<OrComboanditem> comboanditemList = comboanditemService.getByTcid(peispatient.getIdTjtc());
                    mdComboanditemList.addAll(mapperFacade.mapAsList(comboanditemList, MdComboanditem.class));
                    // 查询新系统有没有,有的话就更新，没有的话就用老系统的插入，老系统也没有的话，就插入一条新的
                    MdComboandfzx mdComboandfzx = mdComboandfzxService.getByTcidAndFzx(peispatient.getIdTjtc(), defaultBranch.getBranchId());
                    if (ObjectUtils.isNotEmpty(mdComboandfzx)) {
                        mdComboandfzxList.add(mdComboandfzx);
                    } else {
                        OrComboandfzx orComboandfzx = comboandfzxService.getByTcidAndFzx(peispatient.getIdTjtc(), defaultBranch.getBranchId());
                        if (ObjectUtils.isNotEmpty(orComboandfzx)) {
                            mdComboandfzxList.add(mapperFacade.map(orComboandfzx, MdComboandfzx.class));
                        } else {
                            MdComboandfzx mdComboandfzx1 = new MdComboandfzx();
                            mdComboandfzx1.setTcid(peispatient.getIdTjtc());
                            mdComboandfzx1.setFzxid(defaultBranch.getBranchId());
                            mdComboandfzx1.setCreatedate(new Date());
                            mdComboandfzxList.add(mdComboandfzx1);
                        }
                    }
                }else {
                    // 套餐 CreateMeal
                    OrCreatemeal createmeal = createmealService.getInfoById(peispatient.getIdTjtc());
                    if (ObjectUtils.isNotEmpty(createmeal)){
                        mdCreatemealList.add(mapperFacade.map(createmeal, MdCreatemeal.class));
                        // 普通套餐与收费项目 MealAndItem
                        List<OrMealanditem> mealanditemList = mealanditemService.getByTcid(peispatient.getIdTjtc());
                        mdMealanditemList.addAll(mapperFacade.mapAsList(mealanditemList, MdMealanditem.class));
                        // 普通套餐与分中心关联表 MealAndFzx
                        // 查询新系统有没有,有的话就更新，没有的话就用老系统的插入，老系统也没有的话，就插入一条新的
                        MdMealandfzx mdMealandfzx = mdMealandfzxService.getByTcidAndFzx(peispatient.getIdTjtc(), defaultBranch.getBranchId());
                        if (ObjectUtils.isNotEmpty(mdMealandfzx)) {
                            mdMealandfzxList.add(mdMealandfzx);
                        } else {
                            OrMealandfzx orMealandfzx = mealandfzxService.getByTcidAndFzx(peispatient.getIdTjtc(), defaultBranch.getBranchId());
                            if (ObjectUtils.isNotEmpty(orMealandfzx)) {
                                mdMealandfzxList.add(mapperFacade.map(orMealandfzx, MdMealandfzx.class));
                            } else {
                                MdMealandfzx mealandfzx = new MdMealandfzx();
                                mealandfzx.setTcid(peispatient.getIdTjtc());
                                mealandfzx.setFzxid(defaultBranch.getBranchId());
                                mealandfzx.setCreatedate(new Date());
                                mdMealandfzxList.add(mealandfzx);
                            }
                        }
                    }
                }
            }

            mdPeispatientList.add(mapperFacade.map(peispatient, MdPeispatient.class));
        }


        //体检者相关数据
        mdVationAndFzxService.saOrUpList(mdVationAndFzxList);
        mdPeisorgreservationService.saOrUpList(mdPeisorgreservationList);
        mdGroupAndFzxService.saOrUpList(mdGroupAndFzxList);
        mdPeisorgreservationgroupService.saOrUpList(mdPeisorgreservationgroupList);
        mdPeispatientAndFzxService.saOrUpList(mdPeispatientAndFzxList);
        mdPeispatientService.saOrUpList(mdPeispatientList);
        mdPeispatientfeeitemService.saOrUpList(mdPeispatientfeeitemList);
        peispatientchargeService.saveOrUpdateBatch(peispatientchargeList);
        mdPeispatientChargeMainService.saOrUpList(mdPeispatientChargeMainList);
        codeCorrespondingService.saOrUpList(codeCorrespondingList);


        //订单相关数据
        mdOrderandfzxService.saOrUpList(mdOrderandfzxList);
        mdCreateorderService.saOrUpList(mdCreateorderList);
        mdSellcustomerService.saOrUpList(mdSellcustomerList);
        mdSellpactService.saOrUpList(mdSellpactList);
        mdOrderandcomboService.saOrUpList(mdOrderandcomboList);
        mdMealandfzxService.saOrUpList(mdMealandfzxList);
        mdCreatemealService.saOrUpList(mdCreatemealList);
        mdMealanditemService.saOrUpList(mdMealanditemList);
        mdComboandfzxService.saOrUpList(mdComboandfzxList);
        mdCreatecomboService.saOrUpList(mdCreatecomboList);
        mdComboanditemService.saOrUpList(mdComboanditemList);


        System.out.println("-------------------导入未完成的体检者成功！！！------------------------");


        return Boolean.TRUE;
    }


    /**
     * @param patientCode
     * @return boolean
     * @Title: 判断体检号是否存在
     * @author zhanghj
     * @since 2016-8-11 V 1.0
     */
    private boolean isExistByPatientCode(String patientCode) {
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        return null != peispatient;
    }

    /**
     * 根据订单号导入老数据到新系统中
     *
     * @param ddhs
     * @return
     */
    @Override
    public Boolean importData(List<String> ddhs) {
        List<MdCreateorder> mdCreateorderList = new ArrayList<>();
        List<MdSellcustomer> mdSellcustomerList = new ArrayList<>();
        List<MdOrderandfzx> mdOrderandfzxList = new ArrayList<>();
        List<MdSellpact> mdSellpactList = new ArrayList<>();
        List<MdOrderandcombo> mdOrderandcomboList = new ArrayList<>();
        List<MdMealandfzx> mdMealandfzxList = new ArrayList<>();
        List<MdCreatemeal> mdCreatemealList = new ArrayList<>();
        List<MdMealanditem> mdMealanditemList = new ArrayList<>();
        List<MdComboandfzx> mdComboandfzxList = new ArrayList<>();
        List<MdCreatecombo> mdCreatecomboList = new ArrayList<>();
        List<MdComboanditem> mdComboanditemList = new ArrayList<>();


        List<MdPeispatientAndFzx> mdPeispatientAndFzxList = new ArrayList<>();
        List<MdPeispatient> mdPeispatientList = new ArrayList<>();
        List<MdPeispatientfeeitem> mdPeispatientfeeitemList = new ArrayList<>();
        List<Peispatientcharge> peispatientchargeList = new ArrayList<>();
        List<MdPeispatientChargeMain> mdPeispatientChargeMainList = new ArrayList<>();
        List<CodeCorresponding> codeCorrespondingList = new ArrayList<>();
        List<MdVationAndFzx> mdVationAndFzxList = new ArrayList<>();
        List<MdPeisorgreservation> mdPeisorgreservationList = new ArrayList<>();
        List<MdGroupAndFzx> mdGroupAndFzxList = new ArrayList<>();
        List<MdPeisorgreservationgroup> mdPeisorgreservationgroupList = new ArrayList<>();


        for (String ddh : ddhs) {
            OrCreateorder createorder = createorderService.getInfoByDdh(ddh);
            log.info("查询订单，订单号是:" + ddh);
            if (ObjectUtils.isEmpty(createorder)) {
                log.info("订单号不存在:" + ddh);
                continue;
            }
            MdCreateorder mdCreateorder = mdCreateorderService.getByDdh(ddh);
            if (ObjectUtils.isEmpty(mdCreateorder)) {
                mdCreateorder = mapperFacade.map(createorder, MdCreateorder.class);
            }
            mdCreateorderList.add(mdCreateorder);

            // 客户 SellCustomer
            log.info("----查询客户----");
            OrSellcustomer sellcustomer = sellcustomerService.getInfoById(createorder.getKhdwmcid());
            MdSellcustomer mdSellcustomer = mdSellcustomerService.getInfoById(createorder.getKhdwmcid());
            if (ObjectUtils.isEmpty(mdSellcustomer)) {
                mdSellcustomerList.add(mapperFacade.map(sellcustomer, MdSellcustomer.class));
            }else {
                mdSellcustomerList.add(mdSellcustomer);
            }


            // 订单分中心关联表 OrderAndFzx
            log.info("----查询订单分中心关联表----");
            List<OrOrderandfzx> orOrderandfzxs = orderandfzxService.getByDdid(createorder.getId());
            //先查询新系统有没有，没有的话再插入老系统的
            for (OrOrderandfzx orOrderandfzx : orOrderandfzxs) {
                MdOrderandfzx mdOrderandfzx = mdOrderandfzxService.getByDdidAndFzxId(orOrderandfzx.getDdid(), orOrderandfzx.getFzxid());
                if (ObjectUtils.isEmpty(mdOrderandfzx)) {
                    mdOrderandfzxList.add(mapperFacade.map(orOrderandfzx, MdOrderandfzx.class));
                }
            }



            // 合同 SellPact
            log.info("----查询合同----");
            if (ObjectUtil.isNotEmpty(createorder.getHtbh())) {
                List<OrSellpact> sellpact = sellpactService.getByHtbh(createorder.getHtbh());
                if (ObjectUtils.isNotEmpty(sellpact)) {
                    mdSellpactList.addAll(mapperFacade.mapAsList(sellpact, MdSellpact.class));
                }
            }

            // 订单套餐关联表 OrderAndCombo
            log.info("----订单套餐关联表----");
            List<OrOrderandcombo> orderandcomboList = orderandcomboService.getByDdId(createorder.getId());
            mdOrderandcomboList.addAll(mapperFacade.mapAsList(orderandcomboList, MdOrderandcombo.class));

            for (OrOrderandcombo oac : orderandcomboList) {
                if ("0".equals(oac.getCombostate())) {
                    // 套餐 CreateMeal
                    MdCreatemeal mdCreatemeal = mdCreatemealService.getInfoById(oac.getTcid());
                    if (ObjectUtils.isEmpty(mdCreatemeal)) {
                        OrCreatemeal orCreatemeal = createmealService.getInfoById(oac.getTcid());
                        if (ObjectUtils.isEmpty(orCreatemeal)) {continue;}
                        mdCreatemealList.add(mapperFacade.map(orCreatemeal, MdCreatemeal.class));
                        // 普通套餐与收费项目 MealAndItem
                        List<OrMealanditem> mealanditemList = mealanditemService.getByTcid(oac.getTcid());
                        mdMealanditemList.addAll(mapperFacade.mapAsList(mealanditemList, MdMealanditem.class));
                    }
                    // 普通套餐与分中心关联表 MealAndFzx
                    List<OrMealandfzx> orMealandfzxs = mealandfzxService.getByTcid(oac.getTcid());
                    mdMealandfzxList.addAll(mapperFacade.mapAsList(orMealandfzxs, MdMealandfzx.class));
                } else {
                    // 最小套餐 CreateCombo
                    MdCreatecombo mdCreatecombo = mdCreatecomboService.getInfoById(oac.getTcid());
                    if (ObjectUtils.isEmpty(mdCreatecombo)){
                        OrCreatecombo orCreatecombo = createcomboService.getInfoById(oac.getTcid());
                        if (ObjectUtils.isEmpty(orCreatecombo)) {continue;}
                        mdCreatecomboList.add(mapperFacade.map(orCreatecombo, MdCreatecombo.class));
                        // 最小套餐与收费项目 ComboAndItem
                        List<OrComboanditem> comboanditemList = comboanditemService.getByTcid(oac.getTcid());
                        mdComboanditemList.addAll(mapperFacade.mapAsList(comboanditemList, MdComboanditem.class));
                    }
                    // 最小套餐与分中心 ComboAndFzx
                    List<OrComboandfzx> orComboandfzxs = comboandfzxService.getByTcid(oac.getTcid());
                    mdComboandfzxList.addAll(mapperFacade.mapAsList(orComboandfzxs, MdComboandfzx.class));
                }
            }

            log.info("-----------------开始查询体检者相关数据-------------------------------------");

            // 体检者团体任务 md_peisorgreservation
            OrPeisorgreservation orPeisorgreservation = peisorgreservationService.getByDdh(createorder.getId());
            //有的可能没备单，没备单的直接跳过
            if (ObjectUtils.isNotEmpty(orPeisorgreservation)) {
                //如果新系统没有这个任务就插入，有的话就跳过
                MdPeisorgreservation mdPeisorgreservation = mdPeisorgreservationService.getByDdh(mdCreateorder.getId());
                if (ObjectUtils.isEmpty(mdPeisorgreservation)) {
                    mdPeisorgreservation = mapperFacade.map(orPeisorgreservation, MdPeisorgreservation.class);
                    mdPeisorgreservationList.add(mdPeisorgreservation);
                    // 体检者团体任务和分中心 md_vation_and_fzx
                    List<OrVationAndFzx> orVationAndFzxs = orVationAndFzxService.getByVationId(mdPeisorgreservation.getId());
                    mdVationAndFzxList.addAll(mapperFacade.mapAsList(orVationAndFzxs, MdVationAndFzx.class));
                }else {
                    mdPeisorgreservationList.add(mdPeisorgreservation);
                    List<MdVationAndFzx> mdVationAndFzxes = mdVationAndFzxService.getByVationId(mdPeisorgreservation.getId());
                    mdVationAndFzxList.addAll(mdVationAndFzxes);
                }




                //体检者任务分组 md_peisorgreservationgroup
                List<OrPeisorgreservationgroup> peisorgreservationgroups = peisorgreservationgroupService.getGroupList(orPeisorgreservation.getId());
                for (OrPeisorgreservationgroup peisorgreservationgroup : peisorgreservationgroups) {
                    //已套餐作为查询的标识，没有就插入老系统分组
                    MdPeisorgreservationgroup mdPeisorgreservationgroup = mdPeisorgreservationgroupService.getByVationIdAndTcId(mdPeisorgreservation.getId(), peisorgreservationgroup.getIdExamsuite());
                    if (ObjectUtils.isEmpty(mdPeisorgreservationgroup)) {
                        peisorgreservationgroup.setIdOrgreservation(mdPeisorgreservation.getId());
                        mdPeisorgreservationgroup = mapperFacade.map(peisorgreservationgroup, MdPeisorgreservationgroup.class);
                        mdPeisorgreservationgroupList.add(mdPeisorgreservationgroup);
                        //查询老系统的 分组和分中心 md_group_and_fzx
                        List<OrGroupAndFzx> orGroupAndFzxs = orGroupAndFzxService.getByGroupId(peisorgreservationgroup.getId());
                        mdGroupAndFzxList.addAll(mapperFacade.mapAsList(orGroupAndFzxs, MdGroupAndFzx.class));
                    } else {
                        mdPeisorgreservationgroupList.add(mdPeisorgreservationgroup);
                        List<MdGroupAndFzx> mdGroupAndFzxs = mdGroupAndFzxService.getByGroupId(mdPeisorgreservationgroup.getId());
                        mdGroupAndFzxList.addAll(mdGroupAndFzxs);
                    }

                    //只查询未登记没被禁检的
                    List<OrPeispatient> peispatients = peispatientService.getByGroupId(peisorgreservationgroup.getId());
                    for (OrPeispatient peispatient : peispatients) {
                        String oldPatientCode = peispatient.getPatientcode();
                        //判断是否重复,重复跳过
                        Long count = codeCorrespondingService.count(new LambdaQueryWrapper<CodeCorresponding>()
                                .eq(CodeCorresponding::getOldCode, oldPatientCode));
                        if (count > 0) {
                            continue;
                        }

                        //判断新系统是否有这个id，有的话，如果是已登记的就跳过，未登记的返回给前端
                        MdPeispatient peispatient1 = mdPeispatientService.getInfoById(peispatient.getId());
                        if (ObjectUtils.isNotEmpty(peispatient1)) {
                            if ("1".equals(peispatient1.getFRegistered())) {
                                continue;
                            } else if ("0".equals(peispatient1.getFRegistered())) {
                                System.out.println("导入时id重复,且未登记:" + peispatient.getId());
                                continue;
                            }
                        }


                        //重新生成体检号
                        String patientCode = "";
                        do {
                            if (StringUtils.isEmpty(peispatient.getHospitalcode())) {
                                patientCode = CodeUtil.getPatientCode(Constants.ONLINE_PREFIX, iSysConfigService.selectConfigByKey(Constants.VERSION_NO));
                            }else {
                                patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(""), "");
                            }
                        } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                                .eq(Peispatient::getPatientcode, patientCode)) > 0);
                        if (isExistByPatientCode(patientCode)) {
                            throw new ServiceException("保存失败：生成体检号失败！");
                        }
                        peispatient.setPatientcode(patientCode);
                        //插入老系统及新系统的体检号的对应关系
                        codeCorrespondingList.add(new CodeCorresponding(oldPatientCode, patientCode));
                        //体检者检查项目 md_peispatientfeeitem
                        List<OrPeispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.getByPatientCode(oldPatientCode);
                        for (OrPeispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                            peispatientfeeitem.setIdPatient(patientCode);
                        }
                        mdPeispatientfeeitemList.addAll(mapperFacade.mapAsList(peispatientfeeitems, MdPeispatientfeeitem.class));
                        //体检者缴费表 md_peispatientcharge
                        List<OrPeispatientcharge> orPeispatientcharges = orPeispatientchargeService.getByPatientCode(oldPatientCode);
                        for (OrPeispatientcharge orPeispatientcharge : orPeispatientcharges) {
                            orPeispatientcharge.setPatientcode(patientCode);
                            //导过来的都要改备注，让收费日报那看不到
                            String note = orPeispatientcharge.getNote();
                            if (StringUtils.isNotEmpty(note)) {
                                if (note.contains("预收")) {
                                    orPeispatientcharge.setNote("老系统体检号:" + oldPatientCode + "已预付款!");
                                } else {
                                    orPeispatientcharge.setNote(note + " 老系统数据");
                                }
                            } else {
                                orPeispatientcharge.setNote("老系统数据");
                            }
                        }
                        peispatientchargeList.addAll(mapperFacade.mapAsList(orPeispatientcharges, Peispatientcharge.class));
                        //体检者费用主表 md_peispatient_charge_main
                        OrPeispatientChargeMain peispatientChargeMain = peispatientChargeMainService.getByPatientCode(oldPatientCode);
                        if (ObjectUtils.isNotEmpty(peispatientChargeMain)) {
                            peispatientChargeMain.setPatientcode(patientCode);
                            mdPeispatientChargeMainList.add(mapperFacade.map(peispatientChargeMain, MdPeispatientChargeMain.class));
                        }

                        //体检者和分中心 md_peispatient_and_fzx
                        List<OrPeispatientAndFzx> orPeispatients = orPeispatientAndFzxService.getByPid(peispatient.getId());
                        mdPeispatientAndFzxList.addAll(mapperFacade.mapAsList(orPeispatients, MdPeispatientAndFzx.class));

                        //修改体检者表中的字段
                        //修改婚姻
                        if (ObjectUtil.isNotEmpty(peispatient.getIdMarriage())) {
                            if ("402881a85417c6ec015417dfc285000e".equals(peispatient.getIdMarriage())) {
                                peispatient.setIdMarriage("2");
                            } else if ("402881a85417c6ec015417df521f000d".equals(peispatient.getIdMarriage())) {
                                peispatient.setIdMarriage("1");
                            }
                        }
                        //修改任务
                        peispatient.setIdOrgreservation(mdPeisorgreservation.getId());

                        //修改分组
                        peispatient.setIdOrgreservationgroup(mdPeisorgreservationgroup.getId());
                        mdPeispatientList.add(mapperFacade.map(peispatient, MdPeispatient.class));

                    }
                }
            }


            mdSellcustomerService.saOrUpList(mdSellcustomerList);
            mdSellcustomerList.clear();
            mdSellpactService.saOrUpList(mdSellpactList);
            mdSellpactList.clear();

            mdCreateorderService.saOrUpList(mdCreateorderList);
            mdCreateorderList.clear();
            mdOrderandfzxService.saOrUpList(mdOrderandfzxList);
            mdOrderandfzxList.clear();
            mdOrderandcomboService.saOrUpList(mdOrderandcomboList);
            mdOrderandcomboList.clear();

            mdCreatemealService.saOrUpList(mdCreatemealList);
            mdCreatemealList.clear();
            mdMealanditemService.saOrUpList(mdMealanditemList);
            mdMealanditemList.clear();
            mdMealandfzxService.saOrUpList(mdMealandfzxList);
            mdMealandfzxList.clear();

            mdCreatecomboService.saOrUpList(mdCreatecomboList);
            mdCreatecomboList.clear();
            mdComboanditemService.saOrUpList(mdComboanditemList);
            mdComboanditemList.clear();
            mdComboandfzxService.saOrUpList(mdComboandfzxList);
            mdComboandfzxList.clear();



            mdPeisorgreservationService.saOrUpList(mdPeisorgreservationList);
            mdPeisorgreservationList.clear();
            mdVationAndFzxService.saOrUpList(mdVationAndFzxList);
            mdVationAndFzxList.clear();
            mdPeisorgreservationgroupService.saOrUpList(mdPeisorgreservationgroupList);
            mdPeisorgreservationgroupList.clear();
            mdGroupAndFzxService.saOrUpList(mdGroupAndFzxList);
            mdGroupAndFzxList.clear();

            codeCorrespondingService.saOrUpList(codeCorrespondingList);
            codeCorrespondingList.clear();
            mdPeispatientService.saOrUpList(mdPeispatientList);
            mdPeispatientList.clear();
            mdPeispatientfeeitemService.saOrUpList(mdPeispatientfeeitemList);
            mdPeispatientfeeitemList.clear();
            peispatientchargeService.saveOrUpdateBatch(peispatientchargeList);
            peispatientchargeList.clear();
            mdPeispatientChargeMainService.saOrUpList(mdPeispatientChargeMainList);
            mdPeispatientChargeMainList.clear();
            mdPeispatientAndFzxService.saOrUpList(mdPeispatientAndFzxList);
            mdPeispatientAndFzxList.clear();

            log.info("-------------------订单号:" + ddh + "导入完成！！！------------------------");

        }


        return Boolean.TRUE;
    }


    /**
     * 删除测试体检者
     *
     * @return
     */
    @Override
    public Boolean deleteOtherPeispatient() {
        //获取要删除的体检号
        List<String> list = mdPeispatientService.deleteOtherPeispatient();
        //体检者表
        mdPeispatientService.remove(new LambdaQueryWrapper<MdPeispatient>().in(MdPeispatient::getPatientcode, list));
        //体检者收费项目表
        mdPeispatientfeeitemService.remove(new LambdaQueryWrapper<MdPeispatientfeeitem>().in(MdPeispatientfeeitem::getIdPatient, list));
        //体检者费用主表
        mdPeispatientChargeMainService.remove(new LambdaQueryWrapper<MdPeispatientChargeMain>().in(MdPeispatientChargeMain::getPatientcode, list));
        //体检者费用表
        peispatientchargeService.remove(new LambdaQueryWrapper<Peispatientcharge>().in(Peispatientcharge::getPatientcode, list));
        //科室检查结果主表
        mdSectionResultMainService.remove(new LambdaQueryWrapper<MdSectionResultMain>().in(MdSectionResultMain::getPatientcode, list));
        //科室检查结果副表
        mdSectionResultTwoService.remove(new LambdaQueryWrapper<MdSectionResultTwo>().in(MdSectionResultTwo::getPatientcode, list));
        //总检主表
        mdSectionTotalService.remove(new LambdaQueryWrapper<MdSectionTotal>().in(MdSectionTotal::getPatientcode, list));
        return Boolean.TRUE;
    }

    /**
     * 导入老系统个检人员
     *
     * @return
     */
    @Override
    public Boolean importPeople(String fzxId) {
        //查询未完成登记的体检者
        List<OrPeispatient> peispatients = peispatientService.getImportPeople();

        //容器
        List<MdPeispatient> patientList = new ArrayList<>();
        List<MdPeispatientAndFzx> mdPeispatientAndFzxList = new ArrayList<>();
        List<MdPeispatientfeeitem> peispatientfeeitemList = new ArrayList<>();
        List<MdPeispatientChargeMain> peispatientChargeMainList = new ArrayList<>();
        List<Peispatientcharge> peispatientchargeList = new ArrayList<>();

        int counter = 0; // 计数器变量
        for (OrPeispatient oldPeispatient : peispatients) {
            String oldPatientCode = oldPeispatient.getPatientcode();
            //判断是否重复,重复跳过
            Long count = codeCorrespondingService.count(new LambdaQueryWrapper<CodeCorresponding>()
                    .eq(CodeCorresponding::getOldCode, oldPatientCode));
            if (count > 0) {
                continue;
            }
            MdPeispatient peispatient = mapperFacade.map(oldPeispatient, MdPeispatient.class);
            //重新生成体检号
            String patientCode = "";
            do {
                patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(fzxId), "");
            } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getPatientcode, patientCode)) > 0);
            if (isExistByPatientCode(patientCode)) {
                throw new ServiceException("保存失败：生成体检号失败！");
            }
            peispatient.setPatientcode(patientCode);
            //插入老系统及新系统的体检号的对应关系
            codeCorrespondingService.save(new CodeCorresponding(oldPatientCode, patientCode));
            //体检者检查项目 md_peispatientfeeitem
            List<OrPeispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.getByPatientCode(oldPatientCode);
            for (OrPeispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                peispatientfeeitem.setIdPatient(patientCode);
                peispatientfeeitemList.add(mapperFacade.map(peispatientfeeitem, MdPeispatientfeeitem.class));
            }
            // md_peispatient_charge_main
            OrPeispatientChargeMain peispatientChargeMain = peispatientChargeMainService.getByPatientCode(oldPatientCode);
            peispatientChargeMain.setPatientcode(patientCode);
            peispatientChargeMainList.add(mapperFacade.map(peispatientChargeMain, MdPeispatientChargeMain.class));

            //体检者缴费表 md_peispatientcharge
            List<OrPeispatientcharge> orPeispatientcharges = orPeispatientchargeService.getByPatientCode(oldPatientCode);
            for (OrPeispatientcharge orPeispatientcharge : orPeispatientcharges) {
                orPeispatientcharge.setPatientcode(patientCode);
                //导过来的都要改备注，让收费日报那看不到
                String note = orPeispatientcharge.getNote();
                if (StringUtils.isNotEmpty(note)) {
                    if (note.contains("预收")) {
                        orPeispatientcharge.setNote("老系统体检号:" + oldPatientCode + "已预付款!");
                    } else {
                        orPeispatientcharge.setNote(note + " 老系统数据");
                    }
                } else {
                    orPeispatientcharge.setNote("老系统数据");
                }
                peispatientchargeList.add(mapperFacade.map(orPeispatientcharge, Peispatientcharge.class));
            }

            //修改婚姻状况
            if (ObjectUtil.isNotEmpty(peispatient.getIdMarriage())) {
                if ("402881a85417c6ec015417dfc285000e".equals(peispatient.getIdMarriage())) {
                    peispatient.setIdMarriage("2");
                } else if ("402881a85417c6ec015417df521f000d".equals(peispatient.getIdMarriage())) {
                    peispatient.setIdMarriage("1");
                }
            } else {
                peispatient.setIdMarriage(null);
            }
            patientList.add(peispatient);


            //体检者和分中心 md_peispatient_and_fzx
            MdPeispatientAndFzx mdPeispatientAndFzx = mdPeispatientAndFzxService.getByPidAndFzx(peispatient.getId(), fzxId);
            if (ObjectUtils.isEmpty(mdPeispatientAndFzx)) {
                mdPeispatientAndFzx = new MdPeispatientAndFzx(peispatient.getId(),fzxId);
            }
            mdPeispatientAndFzxList.add(mdPeispatientAndFzx);

            // 插入操作
            if (counter == 99) {
                // 插入体检者
                mdPeispatientService.saOrUpList(patientList);
                patientList.clear();
                // 体检者收费项目
                mdPeispatientfeeitemService.saOrUpList(peispatientfeeitemList);
                peispatientfeeitemList.clear();
                // 体检者费用主表
                mdPeispatientChargeMainService.saveOrUpdateBatch(peispatientChargeMainList);
                peispatientChargeMainList.clear();
                // 体检者缴费表
                peispatientchargeService.saveOrUpdateBatch(peispatientchargeList);
                peispatientchargeList.clear();
                //体检者和分中心表
                mdPeispatientAndFzxService.saOrUpList(mdPeispatientAndFzxList);
                mdPeispatientAndFzxList.clear();

                System.out.println("已成功导入100条数据！");
                counter = 0; // 重置计数器
            } else {
                counter++; // 计数器加1
            }
        }

        //最后再插入一次
        if (CollectionUtil.isNotEmpty(patientList)) {
            // 插入体检者
            mdPeispatientService.saOrUpList(patientList);
            // 体检者收费项目
            mdPeispatientfeeitemService.saOrUpList(peispatientfeeitemList);
            // 体检者费用主表
            mdPeispatientChargeMainService.saveOrUpdateBatch(peispatientChargeMainList);
            // 体检者缴费表
            peispatientchargeService.saveOrUpdateBatch(peispatientchargeList);
            //体检者和分中心表
            mdPeispatientAndFzxService.saOrUpList(mdPeispatientAndFzxList);
        }

        System.out.println("任务执行成功！");
        return Boolean.TRUE;
    }


    /**
     * 导入老系统团检人员
     *
     * @return
     */
    @Override
    public Boolean importTjPeople(ImportTjPeopleParam param) {
        List<MdCreateorder> mdCreateorderList = new ArrayList<>();
        List<MdSellcustomer> mdSellcustomerList = new ArrayList<>();
        List<MdOrderandfzx> mdOrderandfzxList = new ArrayList<>();
        List<MdSellpact> mdSellpactList = new ArrayList<>();
        List<MdOrderandcombo> mdOrderandcomboList = new ArrayList<>();
        List<MdPeisorgreservation> mdPeisorgreservationList = new ArrayList<>();
        List<MdPeisorgreservationgroup> mdPeisorgreservationgroupList = new ArrayList<>();
        List<MdPeispatient> mdPeispatientList = new ArrayList<>();
        List<MdPeispatientfeeitem> mdPeispatientfeeitemList = new ArrayList<>();
        List<Peispatientcharge> peispatientchargeList = new ArrayList<>();
        List<MdPeispatientChargeMain> mdPeispatientChargeMainList = new ArrayList<>();

        try {
            List<OrCreateorder> orCreateorderList = createorderService.getIncomplete(param);
            int counter = 0; // 计数器变量
            for (OrCreateorder createorder : orCreateorderList) {
                String ddh = createorder.getDdh();
                System.out.println("查询订单，订单号是:" + ddh);
                // 订单 createorder
                mdCreateorderList.add(mapperFacade.map(createorder, MdCreateorder.class));

                // 客户 SellCustomer
                System.out.println("----查询客户----");
                OrSellcustomer sellcustomer = sellcustomerService.getInfoById(createorder.getKhdwmcid());
                mdSellcustomerList.add(mapperFacade.map(sellcustomer, MdSellcustomer.class));

                // 订单分中心关联表 OrderAndFzx
                System.out.println("----查询订单分中心关联表----");
                List<OrOrderandfzx> orderandfzxList = orderandfzxService.getByDdid(createorder.getId());
                mdOrderandfzxList.addAll(mapperFacade.mapAsList(orderandfzxList, MdOrderandfzx.class));

                // 合同 SellPact
                System.out.println("----查询合同----");
                if (ObjectUtil.isNotEmpty(createorder.getHtbh())) {
                    List<OrSellpact> sellpact = sellpactService.getByHtbh(createorder.getHtbh());
                    if (ObjectUtils.isNotEmpty(sellpact)) {
                        mdSellpactList.addAll(mapperFacade.mapAsList(sellpact, MdSellpact.class));
                    }
                }

                // 订单套餐关联表 OrderAndCombo
                System.out.println("----订单套餐关联表----");
                List<OrOrderandcombo> orderandcomboList = orderandcomboService.getByDdId(createorder.getId());
                mdOrderandcomboList.addAll(mapperFacade.mapAsList(orderandcomboList, MdOrderandcombo.class));
                // TODO: 2023/11/29 明天单独导入
                for (OrOrderandcombo oac : orderandcomboList) {
                    if ("0".equals(oac.getCombostate())) {
                        // 套餐 CreateMeal
                        OrCreatemeal createmeal = createmealService.getInfoById(oac.getTcid());
                        mdCreatemealService.saOrUp(mapperFacade.map(createmeal, MdCreatemeal.class));
                        // 普通套餐与收费项目 MealAndItem
                        List<OrMealanditem> mealanditemList = mealanditemService.getByTcid(oac.getTcid());
                        mdMealanditemService.saOrUpList(mapperFacade.mapAsList(mealanditemList, MdMealanditem.class));
                        // 普通套餐与分中心关联表 MealAndFzx
                        List<OrMealandfzx> mealandfzxList = mealandfzxService.getByTcid(oac.getTcid());
                        mdMealandfzxService.saOrUpList(mapperFacade.mapAsList(mealandfzxList, MdMealandfzx.class));
                    } else {
                        // 最小套餐 CreateCombo
                        OrCreatecombo createcombo = createcomboService.getInfoById(oac.getTcid());
                        mdCreatecomboService.saOrUp(mapperFacade.map(createcombo, MdCreatecombo.class));
                        // 最小套餐与分中心关联表 ComboAndItem
                        List<OrComboanditem> comboanditemList = comboanditemService.getByTcid(oac.getTcid());
                        mdComboanditemService.saOrUpList(mapperFacade.mapAsList(comboanditemList, MdComboanditem.class));
                        // 最小套餐与收费项目 ComboAndFzx
                        List<OrComboandfzx> comboandfzxList = comboandfzxService.getByTcid(oac.getTcid());
                        mdComboandfzxService.saOrUpList(mapperFacade.mapAsList(comboandfzxList, MdComboandfzx.class));
                    }
                }

                // 体检者团体任务 md_peisorgreservation
                System.out.println("----体检者团体任务----");
                OrPeisorgreservation peisorgreservations = peisorgreservationService.getByDdh(createorder.getId());
                mdPeisorgreservationList.add(mapperFacade.map(peisorgreservations, MdPeisorgreservation.class));

                //体检者任务分组
                System.out.println("----体检者任务分组----");
                List<OrPeisorgreservationgroup> peisorgreservationgroups = peisorgreservationgroupService.getGroupList(peisorgreservations.getId());
                mdPeisorgreservationgroupList.addAll(mapperFacade.mapAsList(peisorgreservationgroups, MdPeisorgreservationgroup.class));


                for (OrPeisorgreservationgroup peisorgreservationgroup : peisorgreservationgroups) {
                    //体检者表 md_peispatient
                    String groupId = peisorgreservationgroup.getId();
                    //只查询未登记没被禁检的
                    System.out.println("----查询体检者----");
                    List<OrPeispatient> peispatients = peispatientService.getByGroupId(groupId);
                    for (OrPeispatient peispatient : peispatients) {
                        String oldPatientCode = peispatient.getPatientcode();
                        //重新生成体检号
                        String patientCode = "";
                        do {
                            patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(null), "");
                        } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                                .eq(Peispatient::getPatientcode, patientCode)) > 0);
                        if (isExistByPatientCode(patientCode)) {
                            throw new ServiceException("保存失败：生成体检号失败！");
                        }
                        peispatient.setPatientcode(patientCode);
                        //插入老系统及新系统的体检号的对应关系
                        System.out.println("----插入老系统及新系统的体检号的对应关系----");
                        codeCorrespondingService.save(new CodeCorresponding(oldPatientCode, patientCode));
                        //体检者检查项目 md_peispatientfeeitem
                        System.out.println("----体检者检查项目----");
                        List<OrPeispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.getByPatientCode(oldPatientCode);
                        for (OrPeispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                            peispatientfeeitem.setIdPatient(patientCode);
                        }
                        mdPeispatientfeeitemList.addAll(mapperFacade.mapAsList(peispatientfeeitems, MdPeispatientfeeitem.class));
                        //体检者缴费表 md_peispatientcharge
                        System.out.println("----体检者缴费表----");
                        List<OrPeispatientcharge> orPeispatientcharges = orPeispatientchargeService.getByPatientCode(oldPatientCode);
                        for (OrPeispatientcharge orPeispatientcharge : orPeispatientcharges) {
                            orPeispatientcharge.setPatientcode(patientCode);
                            //导过来的都要改备注，让收费日报那看不到
                            String note = orPeispatientcharge.getNote();
                            if (StringUtils.isNotEmpty(note)) {
                                if (note.contains("预收")) {
                                    orPeispatientcharge.setNote("老系统体检号:" + oldPatientCode + "已预付款!");
                                } else {
                                    orPeispatientcharge.setNote(note + " 老系统数据");
                                }
                            } else {
                                orPeispatientcharge.setNote("老系统数据");
                            }
                        }
                        peispatientchargeList.addAll(mapperFacade.mapAsList(orPeispatientcharges, Peispatientcharge.class));
                        //体检者费用主表 md_peispatient_charge_main
                        System.out.println("----体检者费用主表----");
                        OrPeispatientChargeMain peispatientChargeMain = peispatientChargeMainService.getByPatientCode(oldPatientCode);
                        if (ObjectUtils.isNotEmpty(peispatientChargeMain)) {
                            peispatientChargeMain.setPatientcode(patientCode);
                            mdPeispatientChargeMainList.add(mapperFacade.map(peispatientChargeMain, MdPeispatientChargeMain.class));
                        }
                        if (ObjectUtil.isNotEmpty(peispatient.getIdMarriage())) {
                            if ("402881a85417c6ec015417dfc285000e".equals(peispatient.getIdMarriage())) {
                                peispatient.setIdMarriage("2");
                            } else if ("402881a85417c6ec015417df521f000d".equals(peispatient.getIdMarriage())) {
                                peispatient.setIdMarriage("1");
                            }
                        }
                        //转换付款方式
                        mdPeispatientList.add(mapperFacade.map(peispatient, MdPeispatient.class));
                    }
                }

                // 插入操作
                if (counter == 10) {
                    // 订单 createorder
                    System.out.println("----插入订单----");
                    mdCreateorderService.saOrUpList(mdCreateorderList);
                    mdCreateorderList.clear();
                    System.out.println("----插入客户----");
                    mdSellcustomerService.saOrUpList(mdSellcustomerList);
                    mdSellcustomerList.clear();
                    System.out.println("----插入订单与分中心----");
                    mdOrderandfzxService.saOrUpList(mdOrderandfzxList);
                    mdOrderandfzxList.clear();
                    System.out.println("----插入销售合同----");
                    mdSellpactService.saOrUpList(mdSellpactList);
                    mdSellpactList.clear();
                    System.out.println("----插入订单与套餐----");
                    mdOrderandcomboService.saOrUpList(mdOrderandcomboList);
                    mdOrderandcomboList.clear();
                    System.out.println("----插入体检者任务----");
                    mdPeisorgreservationService.saOrUpList(mdPeisorgreservationList);
                    mdPeisorgreservationList.clear();
                    System.out.println("----插入体检者分组----");
                    mdPeisorgreservationgroupService.saOrUpList(mdPeisorgreservationgroupList);
                    mdPeisorgreservationgroupList.clear();
                    System.out.println("----插入体检者----");
                    mdPeispatientService.saOrUpList(mdPeispatientList);
                    mdPeispatientList.clear();
                    System.out.println("----插入体检者收费项目----");
                    mdPeispatientfeeitemService.saOrUpList(mdPeispatientfeeitemList);
                    mdPeispatientfeeitemList.clear();
                    System.out.println("----插入体检者收费记录表----");
                    peispatientchargeService.saveOrUpdateBatch(peispatientchargeList);
                    peispatientchargeList.clear();
                    System.out.println("----插入体检者收费记录主表----");
                    mdPeispatientChargeMainService.saOrUpList(mdPeispatientChargeMainList);
                    mdPeispatientChargeMainList.clear();
                    counter = 0; // 重置计数器
                } else {
                    counter++; // 计数器加1
                }
            }
            if (mdCreateorderList.size() > 0) {
                mdCreateorderService.saOrUpList(mdCreateorderList);
                mdSellcustomerService.saOrUpList(mdSellcustomerList);
                mdOrderandfzxService.saOrUpList(mdOrderandfzxList);
                mdSellpactService.saOrUpList(mdSellpactList);
                mdOrderandcomboService.saOrUpList(mdOrderandcomboList);
                mdPeisorgreservationService.saOrUpList(mdPeisorgreservationList);
                mdPeisorgreservationgroupService.saOrUpList(mdPeisorgreservationgroupList);
                mdPeispatientService.saOrUpList(mdPeispatientList);
                mdPeispatientfeeitemService.saOrUpList(mdPeispatientfeeitemList);
                peispatientchargeService.saveOrUpdateBatch(peispatientchargeList);
                mdPeispatientChargeMainService.saOrUpList(mdPeispatientChargeMainList);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("-------------------导入完成！！！------------------------");


        return Boolean.TRUE;
    }


    /**
     * 导入订单相关数据
     *
     * @param param
     */
    @Override
    public void importOrder(ImportTjPeopleParam param) {
        List<String> ddhs = param.getDdhs();
        List<MdCreateorder> mdCreateorderList = new ArrayList<>();
        List<MdSellcustomer> mdSellcustomerList = new ArrayList<>();
        List<MdOrderandfzx> mdOrderandfzxList = new ArrayList<>();
        List<MdSellpact> mdSellpactList = new ArrayList<>();
        List<MdOrderandcombo> mdOrderandcomboList = new ArrayList<>();
        List<MdMealandfzx> mdMealandfzxList = new ArrayList<>();
        List<MdCreatemeal> mdCreatemealList = new ArrayList<>();
        List<MdMealanditem> mdMealanditemList = new ArrayList<>();
        List<MdComboandfzx> mdComboandfzxList = new ArrayList<>();
        List<MdCreatecombo> mdCreatecomboList = new ArrayList<>();
        List<MdComboanditem> mdComboanditemList = new ArrayList<>();

        for (String ddh : ddhs) {
            OrCreateorder createorder = createorderService.getInfoByDdh(ddh);
            log.info("查询订单，订单号是:" + ddh);
            if (ObjectUtils.isEmpty(createorder)) {
                log.info("订单号不存在:" + ddh);
                continue;
            }
            MdCreateorder mdCreateorder = mdCreateorderService.getByDdh(ddh);
            if (ObjectUtils.isEmpty(mdCreateorder)) {
                mdCreateorder = mapperFacade.map(createorder, MdCreateorder.class);
            }
            mdCreateorderList.add(mdCreateorder);

            // 客户 SellCustomer
            System.out.println("----查询客户----");
            OrSellcustomer sellcustomer = sellcustomerService.getInfoById(createorder.getKhdwmcid());
            MdSellcustomer mdSellcustomer = mdSellcustomerService.getInfoById(createorder.getKhdwmcid());
            if (ObjectUtils.isEmpty(mdSellcustomer)) {
                mdSellcustomerList.add(mapperFacade.map(sellcustomer, MdSellcustomer.class));
            }else {
                mdSellcustomerList.add(mdSellcustomer);
            }


            // 订单分中心关联表 OrderAndFzx
            System.out.println("----查询订单分中心关联表----");
            List<OrOrderandfzx> orOrderandfzxs = orderandfzxService.getByDdid(createorder.getId());
            mdOrderandfzxList.addAll(mapperFacade.mapAsList(orOrderandfzxs, MdOrderandfzx.class));


            // 合同 SellPact
            System.out.println("----查询合同----");
            if (ObjectUtil.isNotEmpty(createorder.getHtbh())) {
                List<OrSellpact> sellpact = sellpactService.getByHtbh(createorder.getHtbh());
                if (ObjectUtils.isNotEmpty(sellpact)) {
                    mdSellpactList.addAll(mapperFacade.mapAsList(sellpact, MdSellpact.class));
                }
            }

            // 订单套餐关联表 OrderAndCombo
            System.out.println("----订单套餐关联表----");
            List<OrOrderandcombo> orderandcomboList = orderandcomboService.getByDdId(createorder.getId());
            mdOrderandcomboList.addAll(mapperFacade.mapAsList(orderandcomboList, MdOrderandcombo.class));

            for (OrOrderandcombo oac : orderandcomboList) {
                if ("0".equals(oac.getCombostate())) {
                    // 套餐 CreateMeal
                    MdCreatemeal mdCreatemeal = mdCreatemealService.getInfoById(oac.getTcid());
                    if (ObjectUtils.isEmpty(mdCreatemeal)) {
                        OrCreatemeal orCreatemeal = createmealService.getInfoById(oac.getTcid());
                        mdCreatemealList.add(mapperFacade.map(orCreatemeal, MdCreatemeal.class));
                        // 普通套餐与收费项目 MealAndItem
                        List<OrMealanditem> mealanditemList = mealanditemService.getByTcid(oac.getTcid());
                        mdMealanditemList.addAll(mapperFacade.mapAsList(mealanditemList, MdMealanditem.class));
                    }
                    // 普通套餐与分中心关联表 MealAndFzx
                    List<OrMealandfzx> orMealandfzxs = mealandfzxService.getByTcid(oac.getTcid());
                    mdMealandfzxList.addAll(mapperFacade.mapAsList(orMealandfzxs, MdMealandfzx.class));
                } else {
                    // 最小套餐 CreateCombo
                    MdCreatecombo mdCreatecombo = mdCreatecomboService.getInfoById(oac.getTcid());
                    if (ObjectUtils.isEmpty(mdCreatecombo)){
                        OrCreatecombo orCreatecombo = createcomboService.getInfoById(oac.getTcid());
                        mdCreatecomboList.add(mapperFacade.map(orCreatecombo, MdCreatecombo.class));
                        // 最小套餐与收费项目 ComboAndItem
                        List<OrComboanditem> comboanditemList = comboanditemService.getByTcid(oac.getTcid());
                        mdComboanditemList.addAll(mapperFacade.mapAsList(comboanditemList, MdComboanditem.class));
                    }
                    // 最小套餐与分中心 ComboAndFzx
                    List<OrComboandfzx> orComboandfzxs = comboandfzxService.getByTcid(oac.getTcid());
                    mdComboandfzxList.addAll(mapperFacade.mapAsList(orComboandfzxs, MdComboandfzx.class));
                }
            }




            mdSellcustomerService.saOrUpList(mdSellcustomerList);
            mdSellcustomerList.clear();
            mdSellpactService.saOrUpList(mdSellpactList);
            mdSellpactList.clear();

            mdCreateorderService.saOrUpList(mdCreateorderList);
            mdCreateorderList.clear();
            mdOrderandfzxService.saOrUpList(mdOrderandfzxList);
            mdOrderandfzxList.clear();
            mdOrderandcomboService.saOrUpList(mdOrderandcomboList);
            mdOrderandcomboList.clear();

            mdCreatemealService.saOrUpList(mdCreatemealList);
            mdCreatemealList.clear();
            mdMealanditemService.saOrUpList(mdMealanditemList);
            mdMealanditemList.clear();
            mdMealandfzxService.saOrUpList(mdMealandfzxList);
            mdMealandfzxList.clear();

            mdCreatecomboService.saOrUpList(mdCreatecomboList);
            mdCreatecomboList.clear();
            mdComboanditemService.saOrUpList(mdComboanditemList);
            mdComboanditemList.clear();
            mdComboandfzxService.saOrUpList(mdComboandfzxList);
            mdComboandfzxList.clear();



            System.out.println("-------------------订单号:" + ddh + "导入完成！！！------------------------");

        }
        log.info("订单相关数据导入成功");
    }


    /**
     * 修改收费备注老系统预收
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean oldSystemPrepayment(BaseParam param) {
        //查询之前的预收数据
        List<Peispatientcharge> list = peispatientchargeService.list(new LambdaQueryWrapper<Peispatientcharge>()
                .like(Peispatientcharge::getNote, "预收")
                .le(Peispatientcharge::getFeechargetime, param.getEndTime())
        );
        for (Peispatientcharge peispatientcharge : list) {
            //查询体检号关联的老系统体检号
            CodeCorresponding codeCorresponding = codeCorrespondingService.getOne(new LambdaQueryWrapper<CodeCorresponding>()
                    .eq(CodeCorresponding::getNewCode, peispatientcharge.getPatientcode()));
            //为空的话是新系统预收的，不为空的备注
            if (ObjectUtils.isNotEmpty(codeCorresponding)) {
                peispatientcharge.setNote("老系统体检号:" + codeCorresponding.getOldCode() + "已预付款!");
                peispatientchargeService.updateById(peispatientcharge);
            }
        }

//        List<Peispatientcharge> list = peispatientchargeService.list(new LambdaQueryWrapper<Peispatientcharge>()
//                .like(Peispatientcharge::getNote, "预收")
//                .le(Peispatientcharge::getFeechargetime,param.getEndTime())
//        );
//        Peispatientcharge peispatientcharge = new Peispatientcharge();
//        peispatientcharge.setNote("预收");
//        peispatientchargeService.update(peispatientcharge,new LambdaQueryWrapper<Peispatientcharge>()
//                .like(Peispatientcharge::getNote, "老系统"));
        return Boolean.TRUE;
    }


    /**
     * 根据订单号导入套餐相关
     *
     * @param ddh
     * @return
     */
    @Override
    public Boolean importOrderByDdh(String ddh) {
        OrCreateorder createorder = createorderService.getInfoByDdh(ddh);

        System.out.println("查询订单，订单号是:" + ddh);
        System.out.println("----订单套餐关联表----");
        List<OrOrderandcombo> orderandcomboList = orderandcomboService.getByDdId(createorder.getId());

        // TODO: 2023/11/29 明天单独导入
        for (OrOrderandcombo oac : orderandcomboList) {
            if ("0".equals(oac.getCombostate())) {
                // 套餐 CreateMeal
                OrCreatemeal createmeal = createmealService.getInfoById(oac.getTcid());
                mdCreatemealService.saOrUp(mapperFacade.map(createmeal, MdCreatemeal.class));
                // 普通套餐与收费项目 MealAndItem
                List<OrMealanditem> mealanditemList = mealanditemService.getByTcid(oac.getTcid());
                mdMealanditemService.saOrUpList(mapperFacade.mapAsList(mealanditemList, MdMealanditem.class));
                // 普通套餐与分中心关联表 MealAndFzx
                List<OrMealandfzx> mealandfzxList = mealandfzxService.getByTcid(oac.getTcid());
                mdMealandfzxService.saOrUpList(mapperFacade.mapAsList(mealandfzxList, MdMealandfzx.class));
            } else {
                // 最小套餐 CreateCombo
                OrCreatecombo createcombo = createcomboService.getInfoById(oac.getTcid());
                mdCreatecomboService.saOrUp(mapperFacade.map(createcombo, MdCreatecombo.class));
                // 最小套餐与分中心关联表 ComboAndItem
                List<OrComboanditem> comboanditemList = comboanditemService.getByTcid(oac.getTcid());
                mdComboanditemService.saOrUpList(mapperFacade.mapAsList(comboanditemList, MdComboanditem.class));
                // 最小套餐与收费项目 ComboAndFzx
                List<OrComboandfzx> comboandfzxList = comboandfzxService.getByTcid(oac.getTcid());
                mdComboandfzxService.saOrUpList(mapperFacade.mapAsList(comboandfzxList, MdComboandfzx.class));
            }
        }


        System.out.println("-------------------导入完成！！！------------------------");
        return Boolean.TRUE;
    }


    /**
     * 线上导入
     *
     * @param param
     * @return
     */
    @Override
    public Boolean onlineImportOrder(OnlineImportParam param) {
        List<MdCreateorder> mdCreateorderList = new ArrayList<>();
        List<MdSellcustomer> mdSellcustomerList = new ArrayList<>();
        List<MdOrderandfzx> mdOrderandfzxList = new ArrayList<>();
        List<MdSellpact> mdSellpactList = new ArrayList<>();
        List<MdOrderandcombo> mdOrderandcomboList = new ArrayList<>();

        List<MdMealandfzx> mdMealandfzxList = new ArrayList<>();
        List<MdCreatemeal> mdCreatemealList = new ArrayList<>();
        List<MdMealanditem> mdMealanditemList = new ArrayList<>();
        List<MdComboandfzx> mdComboandfzxList = new ArrayList<>();
        List<MdCreatecombo> mdCreatecomboList = new ArrayList<>();
        List<MdComboanditem> mdComboanditemList = new ArrayList<>();

        List<MdVationAndFzx> mdVationAndFzxList = new ArrayList<>();
        List<MdPeisorgreservation> mdPeisorgreservationList = new ArrayList<>();
        List<MdGroupAndFzx> mdGroupAndFzxList = new ArrayList<>();
        List<MdPeisorgreservationgroup> mdPeisorgreservationgroupList = new ArrayList<>();


//            List<OrCreateorder> orCreateorderList = createorderService.getOnlineImport(param);
        List<String> ddhs = param.getDdhs();
        int counter = 0; // 计数器变量
        for (String ddh : ddhs) {
            OrCreateorder createorder = createorderService.getInfoByDdh(ddh);
            System.out.println("查询订单，订单号是:" + ddh);
            MdCreateorder mdCreateorder = mdCreateorderService.getByDdh(ddh);
            if (ObjectUtils.isNotEmpty(mdCreateorder)) {
                //比较更新时间 如果老系统的更新时间晚于或等于新系统的时间
                if (createorder.getModifydate().after(mdCreateorder.getModifydate())) {
                    mdCreateorderList.add(mapperFacade.map(createorder, MdCreateorder.class));
                } else {
                    mdCreateorderList.add(mdCreateorder);
                }
            } else {
                mdCreateorderList.add(mapperFacade.map(createorder, MdCreateorder.class));
            }

            // 客户 SellCustomer
            System.out.println("----查询客户----");
            OrSellcustomer sellcustomer = sellcustomerService.getInfoById(createorder.getKhdwmcid());
            MdSellcustomer mdSellcustomer = mdSellcustomerService.getInfoById(createorder.getKhdwmcid());
            if (ObjectUtils.isNotEmpty(mdSellcustomer)) {
                //比较更新时间 如果老系统的更新时间晚于或等于新系统的时间
                if (sellcustomer.getModifydate().after(mdSellcustomer.getModifydate())) {
                    mdSellcustomerList.add(mapperFacade.map(sellcustomer, MdSellcustomer.class));
                } else {
                    mdSellcustomerList.add(mdSellcustomer);
                }
            } else {
                mdSellcustomerList.add(mapperFacade.map(sellcustomer, MdSellcustomer.class));
            }


            // 订单分中心关联表 OrderAndFzx
            System.out.println("----查询订单分中心关联表----");
            //查询新系统有没有,有的话就更新，没有的话就用老系统的插入，老系统也没有的话，就插入一条新的
            MdOrderandfzx mdOrderandfzx = mdOrderandfzxService.getByDdidAndFzxId(createorder.getId(), param.getFzxId());
            if (ObjectUtils.isNotEmpty(mdOrderandfzx)) {
                mdOrderandfzxList.add(mdOrderandfzx);
            } else {
                OrOrderandfzx orOrderandfzx = orderandfzxService.getByDdidAndFzxId(createorder.getId(), param.getFzxId());
                if (ObjectUtils.isNotEmpty(orOrderandfzx)) {
                    mdOrderandfzxList.add(mapperFacade.map(orOrderandfzx, MdOrderandfzx.class));
                } else {
                    MdOrderandfzx mdOrderandfzx1 = new MdOrderandfzx();
                    mdOrderandfzx1.setDdid(createorder.getId());
                    mdOrderandfzx1.setFzxid(param.getFzxId());
                    mdOrderandfzx1.setCreatedate(new Date());
                    mdOrderandfzx1.setModifydate(new Date());
                    mdOrderandfzxList.add(mdOrderandfzx1);
                }
            }


            // 合同 SellPact
            System.out.println("----查询合同----");
            if (ObjectUtil.isNotEmpty(createorder.getHtbh())) {
                List<OrSellpact> sellpact = sellpactService.getByHtbh(createorder.getHtbh());
                if (ObjectUtils.isNotEmpty(sellpact)) {
                    mdSellpactList.addAll(mapperFacade.mapAsList(sellpact, MdSellpact.class));
                }
            }

            // 订单套餐关联表 OrderAndCombo
            System.out.println("----订单套餐关联表----");
            List<OrOrderandcombo> orderandcomboList = orderandcomboService.getByDdId(createorder.getId());
            mdOrderandcomboList.addAll(mapperFacade.mapAsList(orderandcomboList, MdOrderandcombo.class));

            for (OrOrderandcombo oac : orderandcomboList) {
                if ("0".equals(oac.getCombostate())) {
                    // 普通套餐与分中心关联表 MealAndFzx
                    // 查询新系统有没有,有的话就更新，没有的话就用老系统的插入，老系统也没有的话，就插入一条新的
                    MdMealandfzx mdMealandfzx = mdMealandfzxService.getByTcidAndFzx(oac.getTcid(), param.getFzxId());
                    if (ObjectUtils.isNotEmpty(mdMealandfzx)) {
                        mdMealandfzxList.add(mdMealandfzx);
                    } else {
                        OrMealandfzx orMealandfzx = mealandfzxService.getByTcidAndFzx(oac.getTcid(), param.getFzxId());
                        if (ObjectUtils.isNotEmpty(orMealandfzx)) {
                            mdMealandfzxList.add(mapperFacade.map(orMealandfzx, MdMealandfzx.class));
                        } else {
                            MdMealandfzx mealandfzx = new MdMealandfzx();
                            mealandfzx.setTcid(oac.getTcid());
                            mealandfzx.setFzxid(param.getFzxId());
                            mealandfzx.setCreatedate(new Date());
                            mdMealandfzxList.add(mealandfzx);
                        }
                    }

                    // 套餐 CreateMeal
                    OrCreatemeal createmeal = createmealService.getInfoById(oac.getTcid());
                    mdCreatemealList.add(mapperFacade.map(createmeal, MdCreatemeal.class));
                    // 普通套餐与收费项目 MealAndItem
                    List<OrMealanditem> mealanditemList = mealanditemService.getByTcid(oac.getTcid());
                    mdMealanditemList.addAll(mapperFacade.mapAsList(mealanditemList, MdMealanditem.class));

                } else {
                    // 最小套餐与分中心 ComboAndFzx
                    // 查询新系统有没有,有的话就更新，没有的话就用老系统的插入，老系统也没有的话，就插入一条新的
                    MdComboandfzx mdComboandfzx = mdComboandfzxService.getByTcidAndFzx(oac.getTcid(), param.getFzxId());
                    if (ObjectUtils.isNotEmpty(mdComboandfzx)) {
                        mdComboandfzxList.add(mdComboandfzx);
                    } else {
                        OrComboandfzx orComboandfzx = comboandfzxService.getByTcidAndFzx(oac.getTcid(), param.getFzxId());
                        if (ObjectUtils.isNotEmpty(orComboandfzx)) {
                            mdComboandfzxList.add(mapperFacade.map(orComboandfzx, MdComboandfzx.class));
                        } else {
                            MdComboandfzx mdComboandfzx1 = new MdComboandfzx();
                            mdComboandfzx1.setTcid(oac.getTcid());
                            mdComboandfzx1.setFzxid(param.getFzxId());
                            mdComboandfzx1.setCreatedate(new Date());
                            mdComboandfzxList.add(mdComboandfzx1);
                        }
                    }
                    // 最小套餐 CreateCombo
                    OrCreatecombo createcombo = createcomboService.getInfoById(oac.getTcid());
                    mdCreatecomboList.add(mapperFacade.map(createcombo, MdCreatecombo.class));
                    // 最小套餐与收费项目 ComboAndItem
                    List<OrComboanditem> comboanditemList = comboanditemService.getByTcid(oac.getTcid());
                    mdComboanditemList.addAll(mapperFacade.mapAsList(comboanditemList, MdComboanditem.class));
                }
            }


            //如果线上有任务和分组，也要更新到线下
            if (ObjectUtils.isNotEmpty(mdCreateorder)) {
                MdPeisorgreservation mdPeisorgreservation = mdPeisorgreservationService.getByDdh(mdCreateorder.getId());
                if (ObjectUtils.isNotEmpty(mdPeisorgreservation)) {
                    mdPeisorgreservationList.add(mdPeisorgreservation);

                    // 体检者团体任务和分中心 md_vation_and_fzx
                    MdVationAndFzx mdVationAndFzx = mdVationAndFzxService.getByVationIdAndFzx(mdPeisorgreservation.getId(), param.getFzxId());
                    if (ObjectUtils.isNotEmpty(mdVationAndFzx)) {
                        mdVationAndFzxList.add(mdVationAndFzx);
                    } else {
                        OrVationAndFzx orVationAndFzx = orVationAndFzxService.getByVationIdAndFzx(mdPeisorgreservation.getId(), param.getFzxId());
                        if (ObjectUtils.isNotEmpty(orVationAndFzx)) {
                            orVationAndFzx.setVationId(mdPeisorgreservation.getId());
                            mdVationAndFzxList.add(mapperFacade.map(orVationAndFzx, MdVationAndFzx.class));
                        } else {
                            MdVationAndFzx mdVationAndFzx1 = new MdVationAndFzx();
                            mdVationAndFzx1.setVationId(mdPeisorgreservation.getId());
                            mdVationAndFzx1.setFzxId(param.getFzxId());
                            mdVationAndFzx1.setCreatedate(new Date());
                            mdVationAndFzxList.add(mdVationAndFzx1);
                        }
                    }

                    List<MdPeisorgreservationgroup> mdPeisorgreservationgroups = mdPeisorgreservationgroupService.getByVationId(mdPeisorgreservation.getId());
                    //查询新系统的 分组和分中心 md_group_and_fzx
                    for (MdPeisorgreservationgroup mdPeisorgreservationgroup : mdPeisorgreservationgroups) {
                        MdGroupAndFzx mdGroupAndFzx = mdGroupAndFzxService.getByGroupIdAndFzx(mdPeisorgreservationgroup.getId(), param.getFzxId());
                        if (ObjectUtils.isNotEmpty(mdGroupAndFzx)) {
                            mdGroupAndFzxList.add(mdGroupAndFzx);
                        } else {
                            MdGroupAndFzx mdGroupAndFzx1 = new MdGroupAndFzx();
                            mdGroupAndFzx1.setGroupId(mdPeisorgreservationgroup.getId());
                            mdGroupAndFzx1.setFzxId(param.getFzxId());
                            mdGroupAndFzx1.setCreatedate(new Date());
                            mdGroupAndFzxList.add(mdGroupAndFzx1);
                        }
                        mdPeisorgreservationgroupList.add(mdPeisorgreservationgroup);
                    }
                }
            }


            mdOrderandfzxService.saOrUpList(mdOrderandfzxList);
            mdOrderandfzxList.clear();
            mdCreateorderService.saOrUpList(mdCreateorderList);
            mdCreateorderList.clear();
            mdSellcustomerService.saOrUpList(mdSellcustomerList);
            mdSellcustomerList.clear();
            mdSellpactService.saOrUpList(mdSellpactList);
            mdSellpactList.clear();
            mdOrderandcomboService.saOrUpList(mdOrderandcomboList);
            mdOrderandcomboList.clear();
            mdMealandfzxService.saOrUpList(mdMealandfzxList);
            mdMealandfzxList.clear();
            mdCreatemealService.saOrUpList(mdCreatemealList);
            mdCreatemealList.clear();
            mdMealanditemService.saOrUpList(mdMealanditemList);
            mdMealanditemList.clear();
            mdComboandfzxService.saOrUpList(mdComboandfzxList);
            mdComboandfzxList.clear();
            mdCreatecomboService.saOrUpList(mdCreatecomboList);
            mdCreatecomboList.clear();
            mdComboanditemService.saOrUpList(mdComboanditemList);
            mdComboanditemList.clear();

            mdVationAndFzxService.saOrUpList(mdVationAndFzxList);
            mdVationAndFzxList.clear();
            mdPeisorgreservationService.saOrUpList(mdPeisorgreservationList);
            mdPeisorgreservationList.clear();
            mdGroupAndFzxService.saOrUpList(mdGroupAndFzxList);
            mdGroupAndFzxList.clear();
            mdPeisorgreservationgroupService.saOrUpList(mdPeisorgreservationgroupList);
            mdPeisorgreservationgroupList.clear();


            System.out.println("-------------------订单号:" + ddh + "导入完成！！！------------------------");
        }


        return Boolean.TRUE;
    }


    /**
     * 线下导入体检者
     *
     * @param param
     * @return
     */
    @Override
    public Boolean offlineImportPei(OfflineImportPeiParam param) {
        List<String> printCode = new ArrayList<>();
        List<MdPeispatientAndFzx> mdPeispatientAndFzxList = new ArrayList<>();
        List<MdPeispatient> mdPeispatientList = new ArrayList<>();
        List<MdPeispatientfeeitem> mdPeispatientfeeitemList = new ArrayList<>();
        List<Peispatientcharge> peispatientchargeList = new ArrayList<>();
        List<MdPeispatientChargeMain> mdPeispatientChargeMainList = new ArrayList<>();
        List<CodeCorresponding> codeCorrespondingList = new ArrayList<>();


        List<MdVationAndFzx> mdVationAndFzxList = new ArrayList<>();
        List<MdPeisorgreservation> mdPeisorgreservationList = new ArrayList<>();
        List<MdGroupAndFzx> mdGroupAndFzxList = new ArrayList<>();
        List<MdPeisorgreservationgroup> mdPeisorgreservationgroupList = new ArrayList<>();
        List<String> ddhs = param.getDdhs();
        for (String ddh : ddhs) {
            MdCreateorder mdCreateorder = mdCreateorderService.getByDdh(ddh);
            if (ObjectUtils.isEmpty(mdCreateorder)) {
//                throw new ServiceException("订单号"+ ddh +"不存在！");
                continue;
            }

            // 体检者团体任务 md_peisorgreservation
            OrPeisorgreservation orPeisorgreservation = peisorgreservationService.getByDdh(mdCreateorder.getId());
            //有的可能没备单，没备单的直接跳过
            if (ObjectUtils.isEmpty(orPeisorgreservation)) {
                continue;
            }
            //如果新系统没有这个任务就插入，有的话就跳过
            MdPeisorgreservation mdPeisorgreservation = mdPeisorgreservationService.getByDdh(mdCreateorder.getId());
            if (ObjectUtils.isEmpty(mdPeisorgreservation)) {
                mdPeisorgreservation = mapperFacade.map(orPeisorgreservation, MdPeisorgreservation.class);
            }
            mdPeisorgreservationList.add(mdPeisorgreservation);


            // 体检者团体任务和分中心 md_vation_and_fzx
            MdVationAndFzx mdVationAndFzx = mdVationAndFzxService.getByVationIdAndFzx(mdPeisorgreservation.getId(), param.getFzxId());
            if (ObjectUtils.isNotEmpty(mdVationAndFzx)) {
                mdVationAndFzxList.add(mdVationAndFzx);
            } else {
                OrVationAndFzx orVationAndFzx = orVationAndFzxService.getByVationIdAndFzx(mdPeisorgreservation.getId(), param.getFzxId());
                if (ObjectUtils.isNotEmpty(orVationAndFzx)) {
                    orVationAndFzx.setVationId(mdPeisorgreservation.getId());
                    mdVationAndFzxList.add(mapperFacade.map(orVationAndFzx, MdVationAndFzx.class));
                } else {
                    MdVationAndFzx mdVationAndFzx1 = new MdVationAndFzx();
                    mdVationAndFzx1.setVationId(mdPeisorgreservation.getId());
                    mdVationAndFzx1.setFzxId(param.getFzxId());
                    mdVationAndFzx1.setCreatedate(new Date());
                    mdVationAndFzxList.add(mdVationAndFzx1);
                }
            }

            //体检者任务分组 md_peisorgreservationgroup
            List<OrPeisorgreservationgroup> peisorgreservationgroups = peisorgreservationgroupService.getGroupList(orPeisorgreservation.getId());
            for (OrPeisorgreservationgroup peisorgreservationgroup : peisorgreservationgroups) {
                //已套餐作为查询的标识，如果有这个套餐就更新，没有就插入老系统分组
                MdPeisorgreservationgroup mdPeisorgreservationgroup = mdPeisorgreservationgroupService.getByVationIdAndTcId(mdPeisorgreservation.getId(), peisorgreservationgroup.getIdExamsuite());
                if (ObjectUtils.isNotEmpty(mdPeisorgreservationgroup)) {
                    mdPeisorgreservationgroupList.add(mdPeisorgreservationgroup);

                    //查询新系统的 分组和分中心 md_group_and_fzx
                    MdGroupAndFzx mdGroupAndFzx = mdGroupAndFzxService.getByGroupIdAndFzx(mdPeisorgreservationgroup.getId(), param.getFzxId());
                    if (ObjectUtils.isNotEmpty(mdGroupAndFzx)) {
                        mdGroupAndFzxList.add(mdGroupAndFzx);
                    } else {
                        MdGroupAndFzx mdGroupAndFzx1 = new MdGroupAndFzx();
                        mdGroupAndFzx1.setGroupId(mdPeisorgreservationgroup.getId());
                        mdGroupAndFzx1.setFzxId(param.getFzxId());
                        mdGroupAndFzx1.setCreatedate(new Date());
                        mdGroupAndFzxList.add(mdGroupAndFzx1);
                    }
                } else {
                    peisorgreservationgroup.setIdOrgreservation(mdPeisorgreservation.getId());
                    mdPeisorgreservationgroup = mapperFacade.map(peisorgreservationgroup, MdPeisorgreservationgroup.class);
                    mdPeisorgreservationgroupList.add(mdPeisorgreservationgroup);
                    //查询老系统的 分组和分中心 md_group_and_fzx
                    OrGroupAndFzx orGroupAndFzx = orGroupAndFzxService.getByGroupIdAndFzx(peisorgreservationgroup.getId(), param.getFzxId());
                    if (ObjectUtils.isNotEmpty(orGroupAndFzx)) {
                        mdGroupAndFzxList.add(mapperFacade.map(orGroupAndFzx, MdGroupAndFzx.class));
                    } else {
                        MdGroupAndFzx mdGroupAndFzx1 = new MdGroupAndFzx();
                        mdGroupAndFzx1.setGroupId(mdPeisorgreservationgroup.getId());
                        mdGroupAndFzx1.setFzxId(param.getFzxId());
                        mdGroupAndFzx1.setCreatedate(new Date());
                        mdGroupAndFzxList.add(mdGroupAndFzx1);
                    }
                }


                //通过分组id查询
                List<OrPeispatient> peispatients = peispatientService.getByGroupId(peisorgreservationgroup.getId());
                for (OrPeispatient peispatient : peispatients) {
                    String oldPatientCode = peispatient.getPatientcode();
                    //判断是否重复,重复跳过
                    Long count = codeCorrespondingService.count(new LambdaQueryWrapper<CodeCorresponding>()
                            .eq(CodeCorresponding::getOldCode, oldPatientCode));
                    if (count > 0) {
                        continue;
                    }

                    //判断新系统是否有这个id，有的话，如果是已登记的就跳过，未登记的返回给前端
                    MdPeispatient peispatient1 = mdPeispatientService.getInfoById(peispatient.getId());
                    if (ObjectUtils.isNotEmpty(peispatient1)) {
                        if ("1".equals(peispatient1.getFRegistered())) {
                            continue;
                        } else if ("0".equals(peispatient1.getFRegistered())) {
                            System.out.println("导入时id重复,且未登记:" + peispatient.getId());
                            printCode.add(peispatient.getId());
                            continue;
                        }
                    }


//                    //判断该分组下是否存在该体检者：姓名、手机号、身份证号，此外还得排除补检的和禁检
//                    List<MdPeispatient> newList = mdPeispatientService.list(new LambdaQueryWrapper<MdPeispatient>()
//                            .eq(MdPeispatient::getIdcardno, peispatient.getIdcardno())
//                            .eq(MdPeispatient::getPatientname, peispatient.getPatientname())
//                            .eq(MdPeispatient::getPhone, peispatient.getPhone())
//                            .eq(MdPeispatient::getIdTjtc, peispatient.getIdTjtc())
//                            .eq(MdPeispatient::getMoneyamountpaid,0)
//                            .ne(MdPeispatient::getFPaused, 1)
//                            .ne(MdPeispatient::getDoctorapply, "补检"));
//                    if (CollectionUtil.isNotEmpty(newList)) {
//                        //判断是否已检
//                        Boolean registered = Boolean.FALSE;
//                        for (MdPeispatient newitem : newList) {
//                            if ("1".equals(newitem.getFRegistered())) {
//                                registered = Boolean.TRUE;
//                                break;
//                            }
//                        }
//                        if (registered) {
//                            //已检跳过
//                            continue;
//                        } else {
//                            //合并
//                            hebing(newList);
//                        }
//                    }else {
                    //不需要合并，执行导入逻辑
                    if (StringUtils.isEmpty(peispatient.getHospitalcode())) {
                        peispatient.setHospitalcode(param.getFzxId());
                    }

                    //重新生成体检号
                    String patientCode = "";
                    do {
                        patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(param.getFzxId()), "");
                        //如果分中心ID为空应该将体检号改为APP开头的
//                            if (StringUtils.isEmpty(peispatient.getHospitalcode())) {
////                                如果分中心ID为空应该将体检号改为APP开头的
//
//                            } else {
////                                否则生成一个分中心开头的体检号
//
//                            }
                    } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                            .eq(Peispatient::getPatientcode, patientCode)) > 0);
                    if (isExistByPatientCode(patientCode)) {
                        throw new ServiceException("保存失败：生成体检号失败！");
                    }
                    peispatient.setPatientcode(patientCode);
                    //插入老系统及新系统的体检号的对应关系
                    codeCorrespondingList.add(new CodeCorresponding(oldPatientCode, patientCode));
                    //体检者检查项目 md_peispatientfeeitem
                    List<OrPeispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.getByPatientCode(oldPatientCode);
                    for (OrPeispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                        peispatientfeeitem.setIdPatient(patientCode);
                    }
                    mdPeispatientfeeitemList.addAll(mapperFacade.mapAsList(peispatientfeeitems, MdPeispatientfeeitem.class));
                    //体检者缴费表 md_peispatientcharge
                    List<OrPeispatientcharge> orPeispatientcharges = orPeispatientchargeService.getByPatientCode(oldPatientCode);
                    for (OrPeispatientcharge orPeispatientcharge : orPeispatientcharges) {
                        orPeispatientcharge.setPatientcode(patientCode);
                        //导过来的都要改备注，让收费日报那看不到
                        String note = orPeispatientcharge.getNote();
                        if (StringUtils.isNotEmpty(note)) {
                            if (note.contains("预收")) {
                                orPeispatientcharge.setNote("老系统体检号:" + oldPatientCode + "已预付款!");
                            } else {
                                orPeispatientcharge.setNote(note + " 老系统数据");
                            }
                        } else {
                            orPeispatientcharge.setNote("老系统数据");
                        }
                    }
                    peispatientchargeList.addAll(mapperFacade.mapAsList(orPeispatientcharges, Peispatientcharge.class));
                    //体检者费用主表 md_peispatient_charge_main
                    OrPeispatientChargeMain peispatientChargeMain = peispatientChargeMainService.getByPatientCode(oldPatientCode);
                    if (ObjectUtils.isNotEmpty(peispatientChargeMain)) {
                        peispatientChargeMain.setPatientcode(patientCode);
                        mdPeispatientChargeMainList.add(mapperFacade.map(peispatientChargeMain, MdPeispatientChargeMain.class));
                    }


                    //体检者和分中心 md_peispatient_and_fzx
                    MdPeispatientAndFzx mdPeispatientAndFzx = mdPeispatientAndFzxService.getByPidAndFzx(peispatient.getId(), param.getFzxId());
                    if (ObjectUtils.isEmpty(mdPeispatientAndFzx)) {
                        mdPeispatientAndFzx = new MdPeispatientAndFzx();
                        mdPeispatientAndFzx.setPatientId(peispatient.getId());
                        mdPeispatientAndFzx.setFzxId(param.getFzxId());
                    }
                    mdPeispatientAndFzxList.add(mdPeispatientAndFzx);

                    //修改体检者表中的字段
                    //修改婚姻
                    if (ObjectUtil.isNotEmpty(peispatient.getIdMarriage())) {
                        if ("402881a85417c6ec015417dfc285000e".equals(peispatient.getIdMarriage())) {
                            peispatient.setIdMarriage("2");
                        } else if ("402881a85417c6ec015417df521f000d".equals(peispatient.getIdMarriage())) {
                            peispatient.setIdMarriage("1");
                        }
                    }
                    //修改任务
                    peispatient.setIdOrgreservation(mdPeisorgreservation.getId());

                    //修改分组
                    peispatient.setIdOrgreservationgroup(mdPeisorgreservationgroup.getId());
                    mdPeispatientList.add(mapperFacade.map(peispatient, MdPeispatient.class));
//                    }


                }


            }


            mdVationAndFzxService.saOrUpList(mdVationAndFzxList);
            mdVationAndFzxList.clear();

            mdPeisorgreservationService.saOrUpList(mdPeisorgreservationList);
            mdPeisorgreservationList.clear();

            mdGroupAndFzxService.saOrUpList(mdGroupAndFzxList);
            mdGroupAndFzxList.clear();

            mdPeisorgreservationgroupService.saOrUpList(mdPeisorgreservationgroupList);
            mdPeisorgreservationgroupList.clear();


            codeCorrespondingService.saOrUpList(codeCorrespondingList);
            codeCorrespondingList.clear();

            mdPeispatientAndFzxService.saOrUpList(mdPeispatientAndFzxList);
            mdPeispatientAndFzxList.clear();

            mdPeispatientService.saOrUpList(mdPeispatientList);
            mdPeispatientList.clear();

            mdPeispatientfeeitemService.saOrUpList(mdPeispatientfeeitemList);
            mdPeispatientfeeitemList.clear();

            peispatientchargeService.saveOrUpdateBatch(peispatientchargeList);
            peispatientchargeList.clear();

            mdPeispatientChargeMainService.saOrUpList(mdPeispatientChargeMainList);
            mdPeispatientChargeMainList.clear();

            System.out.println("-------------------订单号:" + ddh + "导入完成！！！------------------------");

        }
        return Boolean.TRUE;
    }

    /**
     * 合并体检号
     *
     * @param newList
     */
    private void hebing(List<MdPeispatient> newList) {
        //未检，执行合并操作：将现有体检者体检号改为APP开头的，将Hospitalcode字段设置为""空字符串，修改收费项目和收费主表体检号为新体检号
        //合并需实现，如果newlist的数量大于1，则保留APP开头，如果没有APP开头的则保留其中一条（将体检号改为APP开头的，分中心设置为""空字符串），其他删除掉

        String AppCode = null;
        List<String> deleteCode = new ArrayList<>();

        for (MdPeispatient mdPeispatient : newList) {
            //如果是以app开头的
            if (mdPeispatient.getPatientcode().indexOf(Constants.ONLINE_PREFIX) != -1) {
                AppCode = mdPeispatient.getPatientcode();
            } else {
                deleteCode.add(mdPeispatient.getPatientcode());
            }
        }

        if (StringUtils.isNotEmpty(AppCode)) {
            //有app开头的体检号，删除多余的数据，只保留一条
            mdPeispatientService.remove(new LambdaQueryWrapper<MdPeispatient>().in(MdPeispatient::getPatientcode, deleteCode));
            mdPeispatientfeeitemService.remove(new LambdaQueryWrapper<MdPeispatientfeeitem>().in(MdPeispatientfeeitem::getIdPatient, deleteCode));
            peispatientchargeService.remove(new LambdaQueryWrapper<Peispatientcharge>().in(Peispatientcharge::getPatientcode, deleteCode));
            mdPeispatientChargeMainService.remove(new LambdaQueryWrapper<MdPeispatientChargeMain>().in(MdPeispatientChargeMain::getPatientcode, deleteCode));

            //插入体检者和分中心

        } else {
            //没有APP开头的则保留其中一条（将体检号改为APP开头的，分中心设置为""空字符串），其他删除掉

        }


    }

    /**
     * 导入对比报告数据
     * @param patientCodes
     * @return
     */
    @Override
    public Boolean importComparativeReport(List<String> patientCodes) {
        List<CodeCorresponding> codeCorrespondingList = new ArrayList<>();
        List<MdPeispatient> mdPeispatientList = new ArrayList<>();
        List<MdPeispatientfeeitem> mdPeispatientfeeitemList = new ArrayList<>();
        List<MdSectionResultMain> mdSectionResultMainList = new ArrayList<>();
        List<MdSectionResultTwo> mdSectionResultTwoList = new ArrayList<>();
        List<MdSectionTotal> mdSectionTotalList = new ArrayList<>();
        List<MdPeispatientConsultation> mdPeispatientConsultationList = new ArrayList<>();
        List<MdPeispatientexamitem> mdPeispatientexamitemList = new ArrayList<>();
        List<MdDescribe> mdDescribeList = new ArrayList<>();
        List<MdTjreg> mdTjregList = new ArrayList<>();
        List<MdLung> mdLungList = new ArrayList<>();
        List<MdElectroAudiometer> mdElectroAudiometerList = new ArrayList<>();
        for (String patientCode : patientCodes) {
            //判断是否重复,重复跳过
            Long count = codeCorrespondingService.count(new LambdaQueryWrapper<CodeCorresponding>()
                    .eq(CodeCorresponding::getOldCode, patientCode));
            if (count > 0) {
                log.info(patientCode+"已经导入过了！");
                continue;
            }
            codeCorrespondingList.add(new CodeCorresponding(patientCode, patientCode));

            //PEISPATIENT
            OrPeispatient peispatient = peispatientService.getInfoByPatientCode(patientCode);
            if (ObjectUtils.isEmpty(peispatient)){
                log.info(patientCode+"不存在！");
                continue;
            }


            //修改档案号
            List<MdPeispatientarchive> mdPeispatientarchives = mdPeispatientarchiveService.getInfoByIdCard(peispatient.getIdcardno());
            if (CollectionUtil.isNotEmpty(mdPeispatientarchives)){
                MdPeispatientarchive mdPeispatientarchive = mdPeispatientarchives.get(0);
                peispatient.setPatientarchiveno(mdPeispatientarchive.getPatientarchiveno());
            }

            //修改婚姻
            if ("402881a85417c6ec015417dfc285000e".equals(peispatient.getIdMarriage())) {
                peispatient.setIdMarriage("2");
            } else if ("402881a85417c6ec015417df521f000d".equals(peispatient.getIdMarriage())) {
                peispatient.setIdMarriage("1");
            }
            mdPeispatientList.add(mapperFacade.map(peispatient, MdPeispatient.class));

            //PEISPATIENTFEEITEM
            List<OrPeispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.getByPatientCode(patientCode);
            mdPeispatientfeeitemList.addAll(mapperFacade.mapAsList(peispatientfeeitems, MdPeispatientfeeitem.class));

            //SECTION_RESULT_MAIN
            List<OrSectionResultMain> orSectionResultMains = orSectionResultMainService.getListByPatientCode(patientCode);
            mdSectionResultMainList.addAll(mapperFacade.mapAsList(orSectionResultMains, MdSectionResultMain.class));

            //SECTION_RESULT_TWO
            List<OrSectionResultTwo> orSectionResultTwos = orSectionResultTwoService.getListByPatientCode(patientCode);
            mdSectionResultTwoList.addAll(mapperFacade.mapAsList(orSectionResultTwos, MdSectionResultTwo.class));

            //SECTION_TOTAL
            List<OrSectionTotal> orSectionTotals = orSectionTotalService.getListByPatientCode(patientCode);
            mdSectionTotalList.addAll(mapperFacade.mapAsList(orSectionTotals, MdSectionTotal.class));

            //PEISPATIENT_CONSULTATION
            List<OrPeispatientConsultation> orPeispatientConsultations = orPeispatientConsultationService.getListByPatientCode(patientCode);
            mdPeispatientConsultationList.addAll(mapperFacade.mapAsList(orPeispatientConsultations, MdPeispatientConsultation.class));

            //PEISPATIENTEXAMITEM
            List<OrPeispatientexamitem> orPeispatientexamitems = orPeispatientexamitemService.getListByPatientCode(patientCode);
            mdPeispatientexamitemList.addAll(mapperFacade.mapAsList(orPeispatientexamitems, MdPeispatientexamitem.class));

            //DESCRIBE
            List<OrDescribe> orDescribes= orDescribeService.getListByPatientCode(patientCode);
            mdDescribeList.addAll(mapperFacade.mapAsList(orDescribes, MdDescribe.class));

            //TJREG
            List<OrTjreg> orTjregs = orTjregService.getListByPatientCode(patientCode);
            mdTjregList.addAll(mapperFacade.mapAsList(orTjregs, MdTjreg.class));

            //LUNG
            List<OrLung> orLungs = orLungService.getListByPatientCode(patientCode);
            mdLungList.addAll(mapperFacade.mapAsList(orLungs, MdLung.class));

            //ELECTRO_AUDIOMETER
            List<OrElectroAudiometer> orElectroAudiometers = orElectroAudiometerService.getListByPatientCode(patientCode);
            mdElectroAudiometerList.addAll(mapperFacade.mapAsList(orElectroAudiometers, MdElectroAudiometer.class));
        }

        //保存
        codeCorrespondingService.saveBatch(codeCorrespondingList);
        mdPeispatientService.saveBatch(mdPeispatientList);
        mdPeispatientfeeitemService.saveBatch(mdPeispatientfeeitemList);
        mdSectionResultMainService.saveBatch(mdSectionResultMainList);
        mdSectionResultTwoService.saveBatch(mdSectionResultTwoList);
        mdSectionTotalService.saveBatch(mdSectionTotalList);
        mdPeispatientConsultationService.saveBatch(mdPeispatientConsultationList);
        mdPeispatientexamitemService.saveBatch(mdPeispatientexamitemList);
        mdDescribeService.saveBatch(mdDescribeList);
        mdTjregService.saveBatch(mdTjregList);
        mdLungService.saveBatch(mdLungList);
        mdElectroAudiometerService.saveBatch(mdElectroAudiometerList);


        return Boolean.TRUE;
    }

    /**
     * 导入收费项目
     * @param param
     * @return
     */
    @Override
    public Boolean importItems(ImportItemsParam param) {
        List<String> ids = param.getIds();
        List<MdItems> mdItemsList = new ArrayList<>();
        List<MdItemsAndFzx> mdItemsAndFzxList = new ArrayList<>();
        List<MdInspectCharge> mdInspectChargeList = new ArrayList<>();
        List<MdBasexamltem> mdBasexamltemList = new ArrayList<>();
        List<MdBasexamltemSign> mdBasexamltemSignList = new ArrayList<>();
        for (String id : ids) {
            //md_items
            MdItems mdItems = mdItemsService.getInfoById(id);
            if (ObjectUtils.isNotEmpty(mdItems)){
                log.info("重复导入收费项目:{}",id);
                continue;
            }
            OrItems orItems = orItemsService.getInfoById(id);
            if (ObjectUtils.isEmpty(orItems)){
                log.info("老系统收费项目id没找到:{}",id);
                continue;
            }

            //isban是空的话要设置为0
            if (ObjectUtils.isEmpty(orItems.getIsBan())){
                orItems.setIsBan(0);
            }
            mdItemsList.add(mapperFacade.map(orItems, MdItems.class));

            //md_items_and_fzx
            List<OrItemsAndFzx> orItemsAndFzxes = orItemsAndFzxService.getInfoByitemsId(id);
            mdItemsAndFzxList.addAll(mapperFacade.mapAsList(orItemsAndFzxes, MdItemsAndFzx.class));

            //md_inspect_charge
            List<OrInspectCharge> orInspectCharges = orInspectChargeService.getInfoBychargeId(id);
            mdInspectChargeList.addAll(mapperFacade.mapAsList(orInspectCharges, MdInspectCharge.class));

            //md_basexamltem
            for (OrInspectCharge orInspectCharge : orInspectCharges) {
                MdBasexamltem mdBasexamltem = mdBasexamltemService.getInfoById(orInspectCharge.getInspectId());
                if (ObjectUtils.isNotEmpty(mdBasexamltem)){
                    continue;
                }
                OrBasexamltem orBasexamltem = orBasexamltemService.getInfoById(orInspectCharge.getInspectId());
                mdBasexamltemList.add(mapperFacade.map(orBasexamltem, MdBasexamltem.class));


                //md_basexamltem_sign
                List<OrBasexamltemSign> orBasexamltemSigns = orBasexamltemSignService.getInfoByInspectId(orBasexamltem.getId());
                mdBasexamltemSignList.addAll(mapperFacade.mapAsList(orBasexamltemSigns, MdBasexamltemSign.class));
            }
        }
        mdItemsService.saveOrUpdateBatch(mdItemsList);
        mdItemsAndFzxService.saveOrUpdateBatch(mdItemsAndFzxList);
        mdInspectChargeService.saveOrUpdateBatch(mdInspectChargeList);
        mdBasexamltemService.saveOrUpdateBatch(mdBasexamltemList);
        mdBasexamltemSignService.saveOrUpdateBatch(mdBasexamltemSignList);
        log.info("收费项目ids:{}导入成功",ids);
        return Boolean.TRUE;
    }

    /**
     * 增加用户分中心
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addUserFzx(AddUserFzxParam param) {
        List<String> userNos = param.getUserNos();
        String fzxId = param.getFzxId();
        List<SysUser> sysUserList = new ArrayList<>();
        List<SysUserBranch> sysUserBranchList = new ArrayList<>();
        for (String userNo : userNos) {
            SysUser sysUser = sysUserService.getUserByNo(userNo);
            if (ObjectUtils.isEmpty(sysUser)){
                continue;
            }
            sysUserList.add(sysUser);
            SysUserBranch sysUserBranch = sysUserBranchService.getOne(new LambdaQueryWrapper<SysUserBranch>()
                    .eq(SysUserBranch::getUserId, userNo)
                    .eq(SysUserBranch::getBranchId, fzxId)
            );
            if (ObjectUtils.isEmpty(sysUserBranch)){
                sysUserBranch = new SysUserBranch();
                sysUserBranch.setUserId(userNo);
                sysUserBranch.setBranchId(fzxId);
            }
            sysUserBranchList.add(sysUserBranch);
        }
        sysUserService.updateBatchById(sysUserList);
        sysUserBranchService.saveOrUpdateBatch(sysUserBranchList);
        return Boolean.TRUE;
    }

    /**
     * 迁移老系统团检数据
     * @param param
     * @return
     */
    @Override
    public Boolean migrationOldData(MigrationOldDataParam param) {
        ImportTjPeopleParam importTjPeopleParam = new ImportTjPeopleParam(param.getStartTime(),param.getEndTime());
        List<OrCreateorder> orCreateorderList = createorderService.getIncomplete(importTjPeopleParam);
        log.info("需导入的订单共有{}个",orCreateorderList.size());

        List<MdCreateorder> mdCreateorderList = new ArrayList<>();
        List<MdSellcustomer> mdSellcustomerList = new ArrayList<>();
        List<MdOrderandfzx> mdOrderandfzxList = new ArrayList<>();
        List<MdSellpact> mdSellpactList = new ArrayList<>();
        List<MdOrderandcombo> mdOrderandcomboList = new ArrayList<>();

        List<MdMealandfzx> mdMealandfzxList = new ArrayList<>();
        List<MdCreatemeal> mdCreatemealList = new ArrayList<>();
        List<MdMealanditem> mdMealanditemList = new ArrayList<>();
        List<MdComboandfzx> mdComboandfzxList = new ArrayList<>();
        List<MdCreatecombo> mdCreatecomboList = new ArrayList<>();
        List<MdComboanditem> mdComboanditemList = new ArrayList<>();

        List<MdVationAndFzx> mdVationAndFzxList = new ArrayList<>();
        List<MdPeisorgreservation> mdPeisorgreservationList = new ArrayList<>();
        List<MdGroupAndFzx> mdGroupAndFzxList = new ArrayList<>();
        List<MdPeisorgreservationgroup> mdPeisorgreservationgroupList = new ArrayList<>();


        List<String> printCode = new ArrayList<>();
        List<MdPeispatientAndFzx> mdPeispatientAndFzxList = new ArrayList<>();
        List<MdPeispatient> mdPeispatientList = new ArrayList<>();
        List<MdPeispatientfeeitem> mdPeispatientfeeitemList = new ArrayList<>();
        List<Peispatientcharge> peispatientchargeList = new ArrayList<>();
        List<MdPeispatientChargeMain> mdPeispatientChargeMainList = new ArrayList<>();
        List<CodeCorresponding> codeCorrespondingList = new ArrayList<>();


        for (OrCreateorder createorder : orCreateorderList) {
            String ddh = createorder.getDdh();
            log.info("开始导入订单，订单号是:" + ddh);
            MdCreateorder mdCreateorder = mdCreateorderService.getByDdh(ddh);
            if (ObjectUtils.isNotEmpty(mdCreateorder)) {
                mdCreateorderList.add(mdCreateorder);
            } else {
                mdCreateorder = mapperFacade.map(createorder, MdCreateorder.class);
                mdCreateorderList.add(mdCreateorder);
            }

            // 客户 SellCustomer
            log.info("----查询客户----");
            OrSellcustomer sellcustomer = sellcustomerService.getInfoById(createorder.getKhdwmcid());
            MdSellcustomer mdSellcustomer = mdSellcustomerService.getInfoById(createorder.getKhdwmcid());
            if (ObjectUtils.isNotEmpty(mdSellcustomer)) {
                mdSellcustomerList.add(mdSellcustomer);
            } else {
                mdSellcustomerList.add(mapperFacade.map(sellcustomer, MdSellcustomer.class));
            }


            // 订单分中心关联表 OrderAndFzx
            log.info("----查询订单分中心关联表----");
            //查询新系统有没有,有的话就更新，没有的话就用老系统的插入，老系统也没有的话，就插入一条新的
            MdOrderandfzx mdOrderandfzx = mdOrderandfzxService.getByDdidAndFzxId(createorder.getId(), param.getFzxId());
            if (ObjectUtils.isNotEmpty(mdOrderandfzx)) {
                mdOrderandfzxList.add(mdOrderandfzx);
            } else {
                OrOrderandfzx orOrderandfzx = orderandfzxService.getByDdidAndFzxId(createorder.getId(), param.getFzxId());
                if (ObjectUtils.isNotEmpty(orOrderandfzx)) {
                    mdOrderandfzxList.add(mapperFacade.map(orOrderandfzx, MdOrderandfzx.class));
                } else {
                    MdOrderandfzx mdOrderandfzx1 = new MdOrderandfzx(createorder.getId(),param.getFzxId());
                    mdOrderandfzxList.add(mdOrderandfzx1);
                }
            }


            // 合同 SellPact
            log.info("----查询合同----");
            if (ObjectUtil.isNotEmpty(createorder.getHtbh())) {
                List<OrSellpact> sellpact = sellpactService.getByHtbh(createorder.getHtbh());
                if (ObjectUtils.isNotEmpty(sellpact)) {
                    mdSellpactList.addAll(mapperFacade.mapAsList(sellpact, MdSellpact.class));
                }
            }

            // 订单套餐关联表 OrderAndCombo
            log.info("----订单套餐关联表----");
            List<OrOrderandcombo> orderandcomboList = orderandcomboService.getByDdId(createorder.getId());
            mdOrderandcomboList.addAll(mapperFacade.mapAsList(orderandcomboList, MdOrderandcombo.class));

            for (OrOrderandcombo oac : orderandcomboList) {
                if ("0".equals(oac.getCombostate())) {
                    // 套餐 CreateMeal
                    MdCreatemeal mdCreatemeal = mdCreatemealService.getInfoById(oac.getTcid());
                    if (ObjectUtils.isEmpty(mdCreatemeal)) {
                        OrCreatemeal orCreatemeal = createmealService.getInfoById(oac.getTcid());
                        if (ObjectUtils.isNotEmpty(orCreatemeal)){
                            mdCreatemealList.add(mapperFacade.map(orCreatemeal, MdCreatemeal.class));
                            // 普通套餐与收费项目 MealAndItem
                            List<OrMealanditem> mealanditemList = mealanditemService.getByTcid(oac.getTcid());
                            mdMealanditemList.addAll(mapperFacade.mapAsList(mealanditemList, MdMealanditem.class));
                        }
                    }else{
                        mdCreatemealList.add(mdCreatemeal);
                        List<MdMealanditem> mealanditems = mdMealanditemService.getByTcid(oac.getTcid());
                        mdMealanditemList.addAll(mealanditems);
                    }
                    // 普通套餐与分中心关联表 MealAndFzx
                    MdMealandfzx mdMealandfzx = mdMealandfzxService.getByTcidAndFzx(oac.getTcid(), param.getFzxId());
                    if (ObjectUtils.isNotEmpty(mdMealandfzx)) {
                        mdMealandfzxList.add(mdMealandfzx);
                    } else {
                        OrMealandfzx orMealandfzx = mealandfzxService.getByTcidAndFzx(oac.getTcid(), param.getFzxId());
                        if (ObjectUtils.isNotEmpty(orMealandfzx)) {
                            mdMealandfzxList.add(mapperFacade.map(orMealandfzx, MdMealandfzx.class));
                        } else {
                            MdMealandfzx mealandfzx = new MdMealandfzx(oac.getTcid(),param.getFzxId());
                            mdMealandfzxList.add(mealandfzx);
                        }
                    }
                } else {
                    // 最小套餐 CreateCombo
                    MdCreatecombo mdCreatecombo = mdCreatecomboService.getInfoById(oac.getTcid());
                    if (ObjectUtils.isEmpty(mdCreatecombo)){
                        OrCreatecombo orCreatecombo = createcomboService.getInfoById(oac.getTcid());
                        if (ObjectUtils.isNotEmpty(orCreatecombo)){
                            mdCreatecomboList.add(mapperFacade.map(orCreatecombo, MdCreatecombo.class));
                            // 最小套餐与收费项目 ComboAndItem
                            List<OrComboanditem> comboanditemList = comboanditemService.getByTcid(oac.getTcid());
                            mdComboanditemList.addAll(mapperFacade.mapAsList(comboanditemList, MdComboanditem.class));
                        }
                    }else {
                        mdCreatecomboList.add(mdCreatecombo);
                        List<MdComboanditem> mdComboanditems = mdComboanditemService.getByTcid(oac.getTcid());
                        mdComboanditemList.addAll(mdComboanditems);
                    }
                    // 最小套餐与分中心 ComboAndFzx
                    MdComboandfzx mdComboandfzx = mdComboandfzxService.getByTcidAndFzx(oac.getTcid(), param.getFzxId());
                    if (ObjectUtils.isNotEmpty(mdComboandfzx)){
                        mdComboandfzxList.add(mdComboandfzx);
                    }else {
                        OrComboandfzx orComboandfzx = comboandfzxService.getByTcidAndFzx(oac.getTcid(), param.getFzxId());
                        if (ObjectUtils.isNotEmpty(orComboandfzx)) {
                            mdComboandfzxList.add(mapperFacade.map(orComboandfzx, MdComboandfzx.class));
                        } else {
                            MdComboandfzx comboandfzx = new MdComboandfzx(oac.getTcid(),param.getFzxId());
                            mdComboandfzxList.add(comboandfzx);
                        }
                    }

                }
            }


            // 体检者团体任务 md_peisorgreservation
            log.info("----体检者团体任务----");
            OrPeisorgreservation orPeisorgreservation = peisorgreservationService.getByDdh(mdCreateorder.getId());
            //有的可能没备单，没备单的直接跳过
            if (ObjectUtils.isNotEmpty(orPeisorgreservation)) {
                //如果新系统没有这个任务就插入，有的话就跳过
                MdPeisorgreservation mdPeisorgreservation = mdPeisorgreservationService.getByDdh(mdCreateorder.getId());
                if (ObjectUtils.isEmpty(mdPeisorgreservation)) {
                    mdPeisorgreservation = mapperFacade.map(orPeisorgreservation, MdPeisorgreservation.class);
                }
                mdPeisorgreservationList.add(mdPeisorgreservation);


                // 体检者团体任务和分中心 md_vation_and_fzx
                log.info("----体检者团体任务和分中心----");
                MdVationAndFzx mdVationAndFzx = mdVationAndFzxService.getByVationIdAndFzx(mdPeisorgreservation.getId(), param.getFzxId());
                if (ObjectUtils.isNotEmpty(mdVationAndFzx)) {
                    mdVationAndFzxList.add(mdVationAndFzx);
                } else {
                    OrVationAndFzx orVationAndFzx = orVationAndFzxService.getByVationIdAndFzx(mdPeisorgreservation.getId(), param.getFzxId());
                    if (ObjectUtils.isNotEmpty(orVationAndFzx)) {
                        orVationAndFzx.setVationId(mdPeisorgreservation.getId());
                        mdVationAndFzxList.add(mapperFacade.map(orVationAndFzx, MdVationAndFzx.class));
                    } else {
                        MdVationAndFzx mdVationAndFzx1 = new MdVationAndFzx(mdPeisorgreservation.getId(),param.getFzxId());
                        mdVationAndFzxList.add(mdVationAndFzx1);
                    }
                }

                //体检者任务分组 md_peisorgreservationgroup
                log.info("----体检者团体任务分组----");
                List<OrPeisorgreservationgroup> peisorgreservationgroups = peisorgreservationgroupService.getGroupList(orPeisorgreservation.getId());
                for (OrPeisorgreservationgroup peisorgreservationgroup : peisorgreservationgroups) {
                    //已套餐作为查询的标识，如果有这个套餐就更新，没有就插入老系统分组
                    MdPeisorgreservationgroup mdPeisorgreservationgroup = mdPeisorgreservationgroupService.getByVationIdAndTcId(mdPeisorgreservation.getId(), peisorgreservationgroup.getIdExamsuite());
                    if (ObjectUtils.isNotEmpty(mdPeisorgreservationgroup)) {
                        mdPeisorgreservationgroupList.add(mdPeisorgreservationgroup);
                    } else {
                        peisorgreservationgroup.setIdOrgreservation(mdPeisorgreservation.getId());
                        mdPeisorgreservationgroup = mapperFacade.map(peisorgreservationgroup, MdPeisorgreservationgroup.class);
                        mdPeisorgreservationgroupList.add(mdPeisorgreservationgroup);
                    }

                    //查询老系统的 分组和分中心 md_group_and_fzx
                    log.info("----体检者分组和分中心----");
                    MdGroupAndFzx mdGroupAndFzx = mdGroupAndFzxService.getByGroupIdAndFzx(mdPeisorgreservationgroup.getId(), param.getFzxId());
                    if (ObjectUtils.isNotEmpty(mdGroupAndFzx)) {
                        mdGroupAndFzxList.add(mdGroupAndFzx);
                    } else {
                        OrGroupAndFzx orGroupAndFzx = orGroupAndFzxService.getByGroupIdAndFzx(mdPeisorgreservationgroup.getId(), param.getFzxId());
                        if (ObjectUtils.isNotEmpty(orGroupAndFzx)) {
                            mdGroupAndFzxList.add(mapperFacade.map(orGroupAndFzx, MdGroupAndFzx.class));
                        }else {
                            MdGroupAndFzx mdGroupAndFzx1 = new MdGroupAndFzx(mdPeisorgreservationgroup.getId(),param.getFzxId());
                            mdGroupAndFzxList.add(mdGroupAndFzx1);
                        }
                    }


                    //通过分组id查询
                    log.info("----查询体检者----");
                    List<OrPeispatient> peispatients = peispatientService.getByGroupId(peisorgreservationgroup.getId());
                    for (OrPeispatient peispatient : peispatients) {
                        String oldPatientCode = peispatient.getPatientcode();
                        //判断是否重复,重复跳过
                        Long count = codeCorrespondingService.count(new LambdaQueryWrapper<CodeCorresponding>()
                                .eq(CodeCorresponding::getOldCode, oldPatientCode));
                        if (count > 0) {
                            continue;
                        }

                        //判断新系统是否有这个id，有的话，如果是已登记的就跳过，未登记的返回给前端
                        MdPeispatient peispatient1 = mdPeispatientService.getInfoById(peispatient.getId());
                        if (ObjectUtils.isNotEmpty(peispatient1)) {
                            if ("1".equals(peispatient1.getFRegistered())) {
                                continue;
                            } else if ("0".equals(peispatient1.getFRegistered())) {
                                log.info("导入时id重复,且未登记:" + peispatient.getId());
                                printCode.add(peispatient.getId());
                                continue;
                            }
                        }

                        //设置一个默认分中心
                        if (StringUtils.isEmpty(peispatient.getHospitalcode())) {
                            peispatient.setHospitalcode(param.getFzxId());
                        }

                        //重新生成体检号
                        String patientCode = "";
                        do {
                            patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(param.getFzxId()), "");
                        } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                                .eq(Peispatient::getPatientcode, patientCode)) > 0);
                        if (isExistByPatientCode(patientCode)) {
                            throw new ServiceException("保存失败：生成体检号失败！");
                        }
                        peispatient.setPatientcode(patientCode);
                        //插入老系统及新系统的体检号的对应关系
                        codeCorrespondingList.add(new CodeCorresponding(oldPatientCode, patientCode));
                        //体检者检查项目 md_peispatientfeeitem
                        List<OrPeispatientfeeitem> peispatientfeeitems = peispatientfeeitemService.getByPatientCode(oldPatientCode);
                        for (OrPeispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                            peispatientfeeitem.setIdPatient(patientCode);
                        }
                        mdPeispatientfeeitemList.addAll(mapperFacade.mapAsList(peispatientfeeitems, MdPeispatientfeeitem.class));
                        //体检者缴费表 md_peispatientcharge
                        List<OrPeispatientcharge> orPeispatientcharges = orPeispatientchargeService.getByPatientCode(oldPatientCode);
                        for (OrPeispatientcharge orPeispatientcharge : orPeispatientcharges) {
                            orPeispatientcharge.setPatientcode(patientCode);
                            //导过来的都要改备注，让收费日报那看不到
                            String note = orPeispatientcharge.getNote();
                            if (StringUtils.isNotEmpty(note)) {
                                if (note.contains("预收")) {
                                    orPeispatientcharge.setNote("老系统体检号:" + oldPatientCode + "已预付款!");
                                } else {
                                    orPeispatientcharge.setNote(note + " 老系统数据");
                                }
                            } else {
                                orPeispatientcharge.setNote("老系统数据");
                            }
                        }
                        peispatientchargeList.addAll(mapperFacade.mapAsList(orPeispatientcharges, Peispatientcharge.class));
                        //体检者费用主表 md_peispatient_charge_main
                        OrPeispatientChargeMain peispatientChargeMain = peispatientChargeMainService.getByPatientCode(oldPatientCode);
                        if (ObjectUtils.isNotEmpty(peispatientChargeMain)) {
                            peispatientChargeMain.setPatientcode(patientCode);
                            mdPeispatientChargeMainList.add(mapperFacade.map(peispatientChargeMain, MdPeispatientChargeMain.class));
                        }


                        //体检者和分中心 md_peispatient_and_fzx
                        MdPeispatientAndFzx mdPeispatientAndFzx = mdPeispatientAndFzxService.getByPidAndFzx(peispatient.getId(), param.getFzxId());
                        if (ObjectUtils.isEmpty(mdPeispatientAndFzx)) {
                            mdPeispatientAndFzx = new MdPeispatientAndFzx(peispatient.getId(),param.getFzxId());
                        }
                        mdPeispatientAndFzxList.add(mdPeispatientAndFzx);

                        //修改体检者表中的字段
                        //修改婚姻
                        if (ObjectUtil.isNotEmpty(peispatient.getIdMarriage())) {
                            if ("402881a85417c6ec015417dfc285000e".equals(peispatient.getIdMarriage())) {
                                peispatient.setIdMarriage("2");
                            } else if ("402881a85417c6ec015417df521f000d".equals(peispatient.getIdMarriage())) {
                                peispatient.setIdMarriage("1");
                            }
                        }
                        //修改任务
                        peispatient.setIdOrgreservation(mdPeisorgreservation.getId());

                        //修改分组
                        peispatient.setIdOrgreservationgroup(mdPeisorgreservationgroup.getId());
                        mdPeispatientList.add(mapperFacade.map(peispatient, MdPeispatient.class));
                    }
                }

            }
            log.info("开始插入客户及合同数据");
            //客户及合同数据
            mdSellcustomerService.saOrUpList(mdSellcustomerList);
            mdSellcustomerList.clear();
            mdSellpactService.saOrUpList(mdSellpactList);
            mdSellpactList.clear();

            log.info("开始插入订单数据");
            mdOrderandfzxService.saOrUpList(mdOrderandfzxList);
            mdOrderandfzxList.clear();
            mdOrderandcomboService.saOrUpList(mdOrderandcomboList);
            mdOrderandcomboList.clear();
            mdCreateorderService.saOrUpList(mdCreateorderList);
            mdCreateorderList.clear();

            log.info("开始插入套餐数据");
            mdMealandfzxService.saOrUpList(mdMealandfzxList);
            mdMealandfzxList.clear();
            mdMealanditemService.saOrUpList(mdMealanditemList);
            mdMealanditemList.clear();
            mdCreatemealService.saOrUpList(mdCreatemealList);
            mdCreatemealList.clear();

            mdComboandfzxService.saOrUpList(mdComboandfzxList);
            mdComboandfzxList.clear();
            mdComboanditemService.saOrUpList(mdComboanditemList);
            mdComboanditemList.clear();
            mdCreatecomboService.saOrUpList(mdCreatecomboList);
            mdCreatecomboList.clear();


            //任务及分组
            log.info("开始插入任务及分组");
            mdVationAndFzxService.saOrUpList(mdVationAndFzxList);
            mdVationAndFzxList.clear();
            mdPeisorgreservationService.saOrUpList(mdPeisorgreservationList);
            mdPeisorgreservationList.clear();
            mdGroupAndFzxService.saOrUpList(mdGroupAndFzxList);
            mdGroupAndFzxList.clear();
            mdPeisorgreservationgroupService.saOrUpList(mdPeisorgreservationgroupList);
            mdPeisorgreservationgroupList.clear();

            //体检者相关数据
            log.info("开始插入体检者相关数据");
            log.info("开始插入codeCorresponding");
            codeCorrespondingService.saOrUpList(codeCorrespondingList);
            codeCorrespondingList.clear();
            log.info("开始插入PeispatientAndFzx");
            mdPeispatientAndFzxService.saOrUpList(mdPeispatientAndFzxList);
            mdPeispatientAndFzxList.clear();
            log.info("开始插入Peispatient");
            mdPeispatientService.saOrUpList(mdPeispatientList);
            mdPeispatientList.clear();
            log.info("开始插入Peispatientfeeitem");
            mdPeispatientfeeitemService.saOrUpList(mdPeispatientfeeitemList);
            mdPeispatientfeeitemList.clear();
            log.info("开始插入peispatientcharge");
            peispatientchargeService.saveOrUpdateBatch(peispatientchargeList);
            peispatientchargeList.clear();
            log.info("开始插入mdPeispatientChargeMain");
            mdPeispatientChargeMainService.saOrUpList(mdPeispatientChargeMainList);
            mdPeispatientChargeMainList.clear();

            log.info("-------------------订单号:" + ddh + "导入完成！！！------------------------");
        }
        return Boolean.TRUE;
    }
}
