package com.center.medical.reception.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.*;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.message.service.PlatformProducer;
import com.center.medical.reception.service.SyncCommonService;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.service.*;
import com.center.medical.service.*;
import com.center.medical.sync.bean.dto.SyncDataDto;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.sync.service.SyncSqlRunService;
import com.center.medical.system.bean.model.SysTableConfig;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 前台-备单服务实现层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:41
 */
@Slf4j
@Service("syncCommonService")
@RequiredArgsConstructor
public class SyncCommonServiceImpl implements SyncCommonService {

    private final CreatemealService createmealService;
    private final SellcustomerService sellcustomerService;
    private final OrderandfzxService orderandfzxService;
    private final OrderandcomboService orderandcomboService;
    private final MealandfzxService mealandfzxService;
    private final MealanditemService mealanditemService;
    private final CreatecomboService createcomboService;
    private final ComboandfzxService comboandfzxService;
    private final ComboanditemService comboanditemService;
    private final CreateorderService createorderService;
    private final PeisorgreservationService peisorgreservationService;
    private final VationAndFzxService vationAndFzxService;
    private final PeisorgreservationgroupService peisorgreservationgroupService;
    private final GroupAndFzxService groupAndFzxService;
    private final PeispatientService peispatientService;
    private final PeispatientReservationChargeService peispatientReservationChargeService;
    private final PeispatientPhotoService peispatientPhotoService;
    private final PeispatientarchiveService peispatientarchiveService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final PeispatientChargeMainService peispatientChargeMainService;
    private final PeispatientChargeRecordService peispatientChargeRecordService;
    private final PeispatientchargeService peispatientchargeService;
    private final PeispatientChargeOtherService peispatientChargeOtherService;
    private final SyncSqlRunService syncSqlRunService;
    private final Snowflake snowflake;
    private final PlatformProducer platformProducer;
    private final ISysBranchService iSysBranchService;
    private final PeispatientAndFzxService peispatientAndFzxService;
    /**
     * 线上数据同步到分中心路由键，标识线上系统产生的当前分中心同步数据
     */
    @Value("${sync.amq.online.routingKey.data}")
    private String PF_SYNC_CD_ROUTING_KEY;
    /**
     * 线上数据同步到分中心交换机，接收线上系统产生的分中心的同步数据
     */
    @Value("${sync.amq.online.topicExchange.data}")
    private String PF_SYNC_CD_TOPIC_EXCHANGE;

    /**
     * 同步订单信息
     *
     * @param orderIds
     * @return
     */
    @Override
    public String syncOrderData(List<String> orderIds) {
        String result = "同步结果：";
        for (int i = 0; i < orderIds.size(); i++) {
            //查询订单信息
            Createorder dd = createorderService.getOne(new LambdaQueryWrapper<Createorder>().eq(Createorder::getDdh, orderIds.get(i)));
            result = result + "[";
            if (Objects.isNull(dd)) {
                result = result + orderIds.get(i) + "不存在！]";
                continue;
            }
            result = result + dd.getDdh() + "：";

            //判断订单分中心关联是否存在
            String fzxids = dd.getFzxid();
            if (StringUtils.isNotBlank(fzxids)) {
                String[] fzxidList = fzxids.split(",");
                for (String fzxid : fzxidList) {
                    if (orderandfzxService.count(new LambdaQueryWrapper<Orderandfzx>().eq(Orderandfzx::getFzxid, fzxid).eq(Orderandfzx::getDdid, dd.getId())) == 0) {
                        Orderandfzx orderandfzx = new Orderandfzx();
                        orderandfzx.setDdid(dd.getId());
                        orderandfzx.setFzxid(fzxid);
                        orderandfzxService.save(orderandfzx);
                    }
                }
            }


            //同步订单表
            SyncData syncData1 = new SyncData();
            syncData1.setOptType(3);
            syncData1.setBizDb("medical_prod");
            syncData1.setBizTable("md_createorder");
            syncData1.setBizModifydate(new Date());
            syncData1.setIsAll(0);
            syncData1.setStatus(0);
            syncData1.setBizId(dd.getId());
            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData1, DateUtil.currentSeconds());
            result = result + "订单同步成功、";

            //查询订单的客户信息
            Sellcustomer sc = sellcustomerService.getById(dd.getKhdwmcid());
            if (Objects.isNull(sc)) {
                result = result + "客户信息不存在、";
            } else {
                //同步订单表
                SyncData syncData2 = new SyncData();
                syncData2.setOptType(3);
                syncData2.setBizDb("medical_prod");
                syncData2.setBizTable("crm_sellcustomer");
                syncData2.setBizModifydate(new Date());
                syncData2.setIsAll(0);
                syncData2.setStatus(0);
                syncData2.setBizId(sc.getId());
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData2, DateUtil.currentSeconds());
                result = result + "客户信息同步成功、";
            }


            //查询订单分中心关联信息
            List<Orderandfzx> oafList = orderandfzxService.list(new LambdaQueryWrapper<Orderandfzx>().eq(Orderandfzx::getDdid, dd.getId()));
            if (oafList.size() == 0) {
                result = result + "订单分中心关联信息不存在、";
            } else {
                for (Orderandfzx item : oafList) {
                    //同步数据
                    SyncData syncData = new SyncData();
                    syncData.setOptType(3);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_orderandfzx");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(item.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                }
                result = result + "订单分中心关联信息同步成功" + oafList.size() + "条、";
            }

            //查询订单套餐关联信息
            List<Orderandcombo> oacList = orderandcomboService.list(new LambdaQueryWrapper<Orderandcombo>().eq(Orderandcombo::getDdid, dd.getId()));
            if (oacList.size() == 0) {
                result = result + "订单套餐关联信息不存在、";
            } else {
                for (Orderandcombo item : oacList) {
                    //同步数据
                    SyncData syncData = new SyncData();
                    syncData.setOptType(3);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_orderandcombo");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(item.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                }
                result = result + "订单套餐关联信息同步成功" + oacList.size() + "条、";
            }

            List<String> tcIds = oacList.stream().map(Orderandcombo::getTcid).collect(Collectors.toList());
            log.info("套餐ID:{}", tcIds);
            //查询最小套餐信息
            List<Createcombo> ccList = createcomboService.list(new LambdaQueryWrapper<Createcombo>().in(Createcombo::getId, tcIds));
            if (ccList.size() == 0) {
                result = result + "最小套餐信息不存在、";
            } else {
                for (Createcombo item : ccList) {
                    //判断最小套餐分中心关联是否存在
                    String f1 = item.getFzxid();
                    if (StringUtils.isNotBlank(f1)) {
                        String[] f1List = f1.split(",");
                        for (String fzxid : f1List) {
                            if (comboandfzxService.count(new LambdaQueryWrapper<Comboandfzx>().eq(Comboandfzx::getFzxid, fzxid).eq(Comboandfzx::getTcid, item.getId())) == 0) {
                                Comboandfzx comboandfzx = new Comboandfzx();
                                comboandfzx.setTcid(item.getId());
                                comboandfzx.setFzxid(fzxid);
                                comboandfzxService.save(comboandfzx);
                            }
                        }
                    }

                    //同步数据
                    SyncData syncData = new SyncData();
                    syncData.setOptType(3);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_createcombo");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(item.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                }
                result = result + "最小套餐信息同步成功" + ccList.size() + "条、";
            }

            //查询普通套餐信息
            List<Createmeal> cmList = createmealService.list(new LambdaQueryWrapper<Createmeal>().in(Createmeal::getId, tcIds));
            if (cmList.size() == 0) {
                result = result + "普通套餐信息不存在、";
            } else {
                for (Createmeal item : cmList) {
                    //判断普通套餐分中心关联是否存在
                    String f1 = item.getFzxid();
                    if (StringUtils.isNotBlank(f1)) {
                        String[] f1List = f1.split(",");
                        for (String fzxid : f1List) {
                            if (mealandfzxService.count(new LambdaQueryWrapper<Mealandfzx>().eq(Mealandfzx::getFzxid, fzxid).eq(Mealandfzx::getTcid, item.getId())) == 0) {
                                Mealandfzx mealandfzx = new Mealandfzx();
                                mealandfzx.setTcid(item.getId());
                                mealandfzx.setFzxid(fzxid);
                                mealandfzxService.save(mealandfzx);
                            }
                        }
                    }
                    //同步数据
                    SyncData syncData = new SyncData();
                    syncData.setOptType(3);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_createmeal");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(item.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                }
                result = result + "普通套餐信息同步成功" + cmList.size() + "条、";
            }

            //查询普通套餐分中心关联信息
            List<Mealandfzx> mafList = mealandfzxService.list(new LambdaQueryWrapper<Mealandfzx>().in(Mealandfzx::getTcid, tcIds));
            if (mafList.size() == 0) {
                result = result + "普通套餐分中心关联信息不存在、";
            } else {
                for (Mealandfzx item : mafList) {
                    //同步数据
                    SyncData syncData = new SyncData();
                    syncData.setOptType(3);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_mealandfzx");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(item.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                }
                result = result + "普通套餐分中心关联信息同步成功" + mafList.size() + "条、";
            }

            //查询最小套餐分中心关联信息
            List<Comboandfzx> cafList = comboandfzxService.list(new LambdaQueryWrapper<Comboandfzx>().in(Comboandfzx::getTcid, tcIds));
            if (cafList.size() == 0) {
                result = result + "最小套餐分中心关联信息不存在、";
            } else {
                for (Comboandfzx item : cafList) {
                    //同步数据
                    SyncData syncData = new SyncData();
                    syncData.setOptType(3);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_comboandfzx");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(item.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                }
                result = result + "最小套餐分中心关联信息同步成功" + cafList.size() + "条、";
            }

            //查询最小套餐项目关联信息
            List<Comboanditem> caiList = comboanditemService.list(new LambdaQueryWrapper<Comboanditem>().in(Comboanditem::getTcid, tcIds));
            if (caiList.size() == 0) {
                result = result + "最小套餐项目关联信息不存在、";
            } else {
                for (Comboanditem item : caiList) {
                    //同步数据
                    SyncData syncData = new SyncData();
                    syncData.setOptType(3);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_comboanditem");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(item.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                }
                result = result + "最小套餐项目关联信息同步成功" + caiList.size() + "条、";
            }

            //查询普通套餐项目关联信息
            List<Mealanditem> maiList = mealanditemService.list(new LambdaQueryWrapper<Mealanditem>().in(Mealanditem::getTcid, tcIds));
            if (maiList.size() == 0) {
                result = result + "普通套餐项目关联信息不存在、";
            } else {
                for (Mealanditem item : maiList) {
                    //同步数据
                    SyncData syncData = new SyncData();
                    syncData.setOptType(3);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("md_mealanditem");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(item.getId());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                }
                result = result + "普通套餐项目关联信息同步成功" + maiList.size() + "条、";
            }

            //查询订单的任务主表
            Peisorgreservation vt = peisorgreservationService.getOne(new LambdaQueryWrapper<Peisorgreservation>().eq(Peisorgreservation::getDdh, dd.getId()));
            if (Objects.isNull(vt)) {
                log.warn("订单的任务主表不存在:{}", dd.getDdh());
                result = result + "订单的任务主表不存在、";
            } else {
                log.warn("订单的任务主表存在:{}", vt);
                //判断任务主表分中心关联是否存在
                for (Orderandfzx oaf : oafList) {
                    if (vationAndFzxService.count(new LambdaQueryWrapper<VationAndFzx>().eq(VationAndFzx::getFzxId, oaf.getFzxid()).eq(VationAndFzx::getVationId, vt.getId())) == 0) {
                        VationAndFzx vationAndFzx = new VationAndFzx();
                        vationAndFzx.setVationId(vt.getId());
                        vationAndFzx.setFzxId(oaf.getFzxid());
                        vationAndFzxService.save(vationAndFzx);
                    }
                }
                //同步订单任务表
                SyncData syncData3 = new SyncData();
                syncData3.setOptType(3);
                syncData3.setBizDb("medical_prod");
                syncData3.setBizTable("md_peisorgreservation");
                syncData3.setBizModifydate(new Date());
                syncData3.setIsAll(0);
                syncData3.setStatus(0);
                syncData3.setBizId(vt.getId());
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData3, DateUtil.currentSeconds());
                result = result + "订单的任务主表同步成功、";

                //查询任务分中心关联信息
                List<VationAndFzx> vafList = vationAndFzxService.list(new LambdaQueryWrapper<VationAndFzx>().eq(VationAndFzx::getVationId, vt.getId()));
                if (vafList.size() == 0) {
                    result = result + "查询任务分中心关联信息不存在、";
                } else {
                    for (VationAndFzx item : vafList) {
                        //同步数据
                        SyncData syncData = new SyncData();
                        syncData.setOptType(3);
                        syncData.setBizDb("medical_prod");
                        syncData.setBizTable("md_vation_and_fzx");
                        syncData.setBizModifydate(new Date());
                        syncData.setIsAll(0);
                        syncData.setStatus(0);
                        syncData.setBizId(item.getId());
                        RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                    }
                    result = result + "查询任务分中心关联信息同步成功" + vafList.size() + "条、";
                }

                //查询体检者分组信息
                List<Peisorgreservationgroup> groupList = peisorgreservationgroupService.list(new LambdaQueryWrapper<Peisorgreservationgroup>().eq(Peisorgreservationgroup::getIdOrgreservation, vt.getId()));
                if (groupList.size() == 0) {
                    result = result + "体检者分组信息不存在、";
                } else {
                    for (Peisorgreservationgroup item : groupList) {
                        //判断分组分中心关联记录是否存在
                        for (VationAndFzx vaf : vafList) {
                            if (groupAndFzxService.count(new LambdaQueryWrapper<GroupAndFzx>().eq(GroupAndFzx::getFzxId, vaf.getFzxId()).eq(GroupAndFzx::getGroupId, item.getId())) == 0) {
                                GroupAndFzx groupAndFzx = new GroupAndFzx();
                                groupAndFzx.setGroupId(item.getId());
                                groupAndFzx.setFzxId(vaf.getFzxId());
                                groupAndFzxService.save(groupAndFzx);
                            }
                        }
                        //同步数据
                        SyncData syncData = new SyncData();
                        syncData.setOptType(3);
                        syncData.setBizDb("medical_prod");
                        syncData.setBizTable("md_peisorgreservationgroup");
                        syncData.setBizModifydate(new Date());
                        syncData.setIsAll(0);
                        syncData.setStatus(0);
                        syncData.setBizId(item.getId());
                        RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                    }
                    result = result + "体检者分组信息同步成功" + groupList.size() + "条、";
                }

                //查询分组分中心关联信息
                List<String> groupIds = groupList.stream().map(Peisorgreservationgroup::getId).collect(Collectors.toList());
                List<GroupAndFzx> gafList = groupAndFzxService.list(new LambdaQueryWrapper<GroupAndFzx>().in(GroupAndFzx::getGroupId, groupIds));
                if (gafList.size() == 0) {
                    result = result + "查询分组分中心关联信息不存在、";
                } else {
                    for (GroupAndFzx item : gafList) {
                        //同步数据
                        SyncData syncData = new SyncData();
                        syncData.setOptType(3);
                        syncData.setBizDb("medical_prod");
                        syncData.setBizTable("md_group_and_fzx");
                        syncData.setBizModifydate(new Date());
                        syncData.setIsAll(0);
                        syncData.setStatus(0);
                        syncData.setBizId(item.getId());
                        RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                    }
                    result = result + "查询分组分中心关联信息同步成功" + gafList.size() + "条、";
                }
            }



        }
        log.info("同步订单信息结果：{}", result);
        return result;
    }


    /**
     * 同步体检者信息
     *
     * @param patientCodes
     * @return
     */
    @Override
    public List<Map<String, String>> syncPatientData(List<String> patientCodes) {
        log.info("同步体检者信息patientCodes{}", patientCodes);
        List<Map<String, String>> result = new ArrayList<>();
        List<Peispatient> list = peispatientService.list(new LambdaQueryWrapper<Peispatient>().in(Peispatient::getPatientcode, patientCodes));
        log.info("同步体检者信息list{}", list.size());
        for (int i = 0; i < list.size(); i++) {
            log.info("同步体检者信息结果i：{}", i);
            Map<String, String> item = new HashMap<>();
            //同步体检者表
            SyncData syncData1 = new SyncData();
            syncData1.setOptType(3);
            syncData1.setBizDb("medical_prod");
            syncData1.setBizTable("md_peispatient");
            syncData1.setBizModifydate(new Date());
            syncData1.setIsAll(0);
            syncData1.setStatus(0);
            syncData1.setBizId(list.get(i).getId());
            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData1, DateUtil.currentSeconds());
            Peispatient patient = list.get(i);
            String patientcode = patient.getPatientcode();
            item.put("Peispatient", patientcode + "--" + patient.getId());

            //同步体检者头像
            List<PeispatientPhoto> photos = peispatientPhotoService.list(new LambdaQueryWrapper<PeispatientPhoto>().eq(PeispatientPhoto::getPatientcode, patientcode));
            item.put("PeispatientPhoto", "数量:" + photos.size() + "--" + photos.stream().map(PeispatientPhoto::getId).collect(Collectors.joining(",")));
            for (int j = 0; j < photos.size(); j++) {
                SyncData syncData2 = new SyncData();
                syncData2.setOptType(3);
                syncData2.setBizDb("medical_prod");
                syncData2.setBizTable("md_peispatient_photo");
                syncData2.setBizModifydate(new Date());
                syncData2.setIsAll(0);
                syncData2.setStatus(0);
                syncData2.setBizId(photos.get(j).getId());
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData2, DateUtil.currentSeconds());
            }

            //同步体检者档案
            List<Peispatientarchive> patientarchives = peispatientarchiveService.list(new LambdaQueryWrapper<Peispatientarchive>().eq(Peispatientarchive::getPatientarchiveno, patient.getPatientarchiveno()));
            item.put("Peispatientarchive", "数量:" + patientarchives.size() + "--" + patientarchives.stream().map(Peispatientarchive::getId).collect(Collectors.joining(",")));
            for (int j = 0; j < patientarchives.size(); j++) {
                SyncData syncData2 = new SyncData();
                syncData2.setOptType(3);
                syncData2.setBizDb("medical_prod");
                syncData2.setBizTable("md_peispatientarchive");
                syncData2.setBizModifydate(new Date());
                syncData2.setIsAll(0);
                syncData2.setStatus(0);
                syncData2.setBizId(patientarchives.get(j).getId());
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData2, DateUtil.currentSeconds());
            }

            //同步体检者收费项目表
            List<Peispatientfeeitem> feeitems = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, patientcode));
            item.put("Peispatientfeeitem", "数量:" + feeitems.size() + "--" + feeitems.stream().map(Peispatientfeeitem::getId).collect(Collectors.joining(",")));
            for (int j = 0; j < feeitems.size(); j++) {
                SyncData syncData2 = new SyncData();
                syncData2.setOptType(3);
                syncData2.setBizDb("medical_prod");
                syncData2.setBizTable("md_peispatientfeeitem");
                syncData2.setBizModifydate(new Date());
                syncData2.setIsAll(0);
                syncData2.setStatus(0);
                syncData2.setBizId(feeitems.get(j).getId());
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData2, DateUtil.currentSeconds());
            }

            //同步体检者收费主表
            List<PeispatientChargeMain> chargeMains = peispatientChargeMainService.list(new LambdaQueryWrapper<PeispatientChargeMain>().eq(PeispatientChargeMain::getPatientcode, patientcode));
            item.put("PeispatientChargeMain", "数量:" + chargeMains.size() + "--" + chargeMains.stream().map(PeispatientChargeMain::getId).collect(Collectors.joining(",")));
            for (int j = 0; j < chargeMains.size(); j++) {
                SyncData syncData2 = new SyncData();
                syncData2.setOptType(3);
                syncData2.setBizDb("medical_prod");
                syncData2.setBizTable("md_peispatient_charge_main");
                syncData2.setBizModifydate(new Date());
                syncData2.setIsAll(0);
                syncData2.setStatus(0);
                syncData2.setBizId(chargeMains.get(j).getId());
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData2, DateUtil.currentSeconds());
            }

            //同步体检者结算表
            List<PeispatientReservationCharge> reservationCharges = peispatientReservationChargeService.list(new LambdaQueryWrapper<PeispatientReservationCharge>().eq(PeispatientReservationCharge::getPatientcode, patientcode));
            item.put("PeispatientReservationCharge", "数量:" + reservationCharges.size() + "--" + reservationCharges.stream().map(PeispatientReservationCharge::getId).collect(Collectors.joining(",")));
            for (int j = 0; j < reservationCharges.size(); j++) {
                SyncData syncData2 = new SyncData();
                syncData2.setOptType(3);
                syncData2.setBizDb("medical_prod");
                syncData2.setBizTable("md_peispatient_reservation_charge");
                syncData2.setBizModifydate(new Date());
                syncData2.setIsAll(0);
                syncData2.setStatus(0);
                syncData2.setBizId(reservationCharges.get(j).getId());
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData2, DateUtil.currentSeconds());
            }

            //同步体检者收费表
            List<Peispatientcharge> charges = peispatientchargeService.list(new LambdaQueryWrapper<Peispatientcharge>().eq(Peispatientcharge::getPatientcode, patientcode));
            item.put("Peispatientcharge", "数量:" + charges.size() + "--" + charges.stream().map(Peispatientcharge::getId).collect(Collectors.joining(",")));
            for (int j = 0; j < charges.size(); j++) {
                SyncData syncData2 = new SyncData();
                syncData2.setOptType(3);
                syncData2.setBizDb("medical_prod");
                syncData2.setBizTable("md_peispatientcharge");
                syncData2.setBizModifydate(new Date());
                syncData2.setIsAll(0);
                syncData2.setStatus(0);
                syncData2.setBizId(charges.get(j).getId());
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData2, DateUtil.currentSeconds());
            }

            //同步体检者体检者其他缴费表
            List<PeispatientChargeOther> chargeOthers = peispatientChargeOtherService.list(new LambdaQueryWrapper<PeispatientChargeOther>().eq(PeispatientChargeOther::getPatientcode, patientcode));
            item.put("PeispatientChargeOther", "数量:" + chargeOthers.size() + "--" + chargeOthers.stream().map(PeispatientChargeOther::getId).collect(Collectors.joining(",")));
            for (int j = 0; j < chargeOthers.size(); j++) {
                SyncData syncData2 = new SyncData();
                syncData2.setOptType(3);
                syncData2.setBizDb("medical_prod");
                syncData2.setBizTable("md_peispatient_charge_other");
                syncData2.setBizModifydate(new Date());
                syncData2.setIsAll(0);
                syncData2.setStatus(0);
                syncData2.setBizId(chargeOthers.get(j).getId());
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData2, DateUtil.currentSeconds());
            }

            //同步体检者体检者其他缴费表
            List<PeispatientChargeRecord> chargeRecords = peispatientChargeRecordService.list(new LambdaQueryWrapper<PeispatientChargeRecord>().eq(PeispatientChargeRecord::getPatientcode, patientcode));
            item.put("PeispatientChargeRecord", "数量:" + chargeRecords.size() + "--" + chargeRecords.stream().map(PeispatientChargeRecord::getId).collect(Collectors.joining(",")));
            for (int j = 0; j < chargeRecords.size(); j++) {
                SyncData syncData2 = new SyncData();
                syncData2.setOptType(3);
                syncData2.setBizDb("medical_prod");
                syncData2.setBizTable("md_peispatient_charge_record");
                syncData2.setBizModifydate(new Date());
                syncData2.setIsAll(0);
                syncData2.setStatus(0);
                syncData2.setBizId(chargeRecords.get(j).getId());
                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData2, DateUtil.currentSeconds());
            }
//            log.info("同步体检者信息结果item：{}", item);
            result.add(item);

        }
        log.info("同步体检者信息结果：{}", result);
        return result;
    }

    /**
     * 删除已登记体检者在其他分中心数据（每天晚上10点半以后），只在线上系统执行
     *
     * @param patientCodes
     * @return
     */
    @Override
    public List<Map<String, Object>> delOtherPatient(List<String> patientCodes) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Peispatient> list = peispatientService.list(new LambdaQueryWrapper<Peispatient>().in(Peispatient::getPatientcode, patientCodes));
        log.warn("删除已登记体检者在其他分中心数据（每天晚上10点半以后），只在线上系统执行开始：{}", patientCodes);
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> item = new HashMap<>();

            Peispatient curItem = list.get(i);
            if (Objects.isNull(curItem)){
                continue;
            }
            String curFzxId = curItem.getHospitalcode();
            String patientcode = curItem.getPatientcode();

            //需要删除数据的分中心
            List<String> cidList = new ArrayList<>();
            if (curItem.getFUsecodehiden()==0){
                //个检，从体检者分中心关联表里查
                List<PeispatientAndFzx> ppfs = peispatientAndFzxService.list(new LambdaQueryWrapper<PeispatientAndFzx>().eq(PeispatientAndFzx::getPatientId, curItem.getId()));
                if (CollectionUtil.isEmpty(ppfs)){
                    continue;
                }else {
                    for (PeispatientAndFzx ppf : ppfs) {
                        if (!ppf.getFzxId().equals(curFzxId)){
                            cidList.add(ppf.getFzxId());
                        }
                    }

                }
            }else {
                //团检，从订单分中心关联表查
                if (StringUtils.isBlank(curItem.getNumorgresv())){
                    continue;
                }
                Createorder createorder = createorderService.getOne(new LambdaQueryWrapper<Createorder>().eq(Createorder::getDdh, curItem.getNumorgresv()));
                List<Orderandfzx> ofs = orderandfzxService.list(new LambdaQueryWrapper<Orderandfzx>().eq(Orderandfzx::getDdid, createorder.getId()));
                if (CollectionUtil.isEmpty(ofs)){
                    continue;
                }else {
                    for (Orderandfzx oaf : ofs) {
                        log.warn("分中心ID{},{},{}", oaf.getFzxid(), curFzxId, !oaf.getFzxid().equals(curFzxId));
                        if (!oaf.getFzxid().equals(curFzxId)){
                            cidList.add(oaf.getFzxid());
                        }
                    }
                }
            }
            if (CollectionUtil.isEmpty(cidList)){
                item.put("patientcode:"+patientcode, "无其他分中心");
                continue;
            }
            List<SysBranch> branches = iSysBranchService.list(new LambdaQueryWrapper<SysBranch>().in(SysBranch::getBranchId, cidList).eq(SysBranch::getIsShow, 1).eq(SysBranch::getIsDelete, 0));
            item.put("patientcode:"+patientcode, cidList);
            result.add(item);

            //体检者表对象
            String pkValue1 = curItem.getId();
            SysTableConfig tc1 = RedisUtil.get(Constants.SYS_TABLE_CONFIG + "md_peispatient");

            //体检者头像
            List<PeispatientPhoto> photos = peispatientPhotoService.list(new LambdaQueryWrapper<PeispatientPhoto>().eq(PeispatientPhoto::getPatientcode, patientcode));
            SysTableConfig tc2 = RedisUtil.get(Constants.SYS_TABLE_CONFIG + "md_peispatient_photo");

            //体检者收费项目
            List<Peispatientfeeitem> feeitems = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, patientcode));
            SysTableConfig tc3 = RedisUtil.get(Constants.SYS_TABLE_CONFIG + "md_peispatientfeeitem");

            //体检者收费主表
            List<PeispatientChargeMain> chargeMains = peispatientChargeMainService.list(new LambdaQueryWrapper<PeispatientChargeMain>().eq(PeispatientChargeMain::getPatientcode, patientcode));
            SysTableConfig tc4 = RedisUtil.get(Constants.SYS_TABLE_CONFIG + "md_peispatient_charge_main");

            //体检者结算表
            List<PeispatientReservationCharge> reservationCharges = peispatientReservationChargeService.list(new LambdaQueryWrapper<PeispatientReservationCharge>().eq(PeispatientReservationCharge::getPatientcode, patientcode));
            SysTableConfig tc5 = RedisUtil.get(Constants.SYS_TABLE_CONFIG + "md_peispatient_reservation_charge");

            //体检者收费表
            List<Peispatientcharge> charges = peispatientchargeService.list(new LambdaQueryWrapper<Peispatientcharge>().eq(Peispatientcharge::getPatientcode, patientcode));
            SysTableConfig tc6 = RedisUtil.get(Constants.SYS_TABLE_CONFIG + "md_peispatientcharge");

            //体检者其他缴费表
            List<PeispatientChargeOther> chargeOthers = peispatientChargeOtherService.list(new LambdaQueryWrapper<PeispatientChargeOther>().eq(PeispatientChargeOther::getPatientcode, patientcode));
            SysTableConfig tc7 = RedisUtil.get(Constants.SYS_TABLE_CONFIG + "md_peispatient_charge_other");

            //体检者其他缴费表
            List<PeispatientChargeRecord> chargeRecords = peispatientChargeRecordService.list(new LambdaQueryWrapper<PeispatientChargeRecord>().eq(PeispatientChargeRecord::getPatientcode, patientcode));
            SysTableConfig tc8 = RedisUtil.get(Constants.SYS_TABLE_CONFIG + "md_peispatient_charge_record");

            for (SysBranch branch : branches) {
                String cid = branch.getBranchId();
                String jm = branch.getJm();
                if (Objects.nonNull(branch) && !branch.getBranchId().equals(curFzxId)) {
                    //删除体检者表
                    SyncDataDto syncDataDto1 = new SyncDataDto(snowflake.nextId(), pkValue1, 4, "medical_prod",
                            syncSqlRunService.generateDeleteSql(tc1.getTableName(), tc1.getKeyName(), pkValue1), tc1.getNeedSync(), cid);
                    log.warn("删除体检者表:{}", JSONUtil.toJsonStr(syncDataDto1));
                    try {
                        platformProducer.sendMessage(PF_SYNC_CD_TOPIC_EXCHANGE + "." + jm, PF_SYNC_CD_ROUTING_KEY + jm, JSONUtil.toJsonStr(syncDataDto1));
                    } catch (Exception e) {
                        log.error("消息队列异常，生产数据失败：jm={}、syncDataDto={}", jm, syncDataDto1);
                    }

                    //删除体检者头像
                    for (PeispatientPhoto photo : photos) {
                        SyncDataDto syncDataDto2 = new SyncDataDto(snowflake.nextId(), photo.getId(), 4, "medical_prod",
                                syncSqlRunService.generateDeleteSql(tc2.getTableName(), tc2.getKeyName(), photo.getId()), tc2.getNeedSync(), cid);
                        log.warn("删除体检者头像:{}", JSONUtil.toJsonStr(syncDataDto2));
                        try {
                            platformProducer.sendMessage(PF_SYNC_CD_TOPIC_EXCHANGE + "." + jm, PF_SYNC_CD_ROUTING_KEY + jm, JSONUtil.toJsonStr(syncDataDto2));
                        } catch (Exception e) {
                            log.error("消息队列异常，生产数据失败：jm={}、syncDataDto={}", jm, syncDataDto2);
                        }
                    }

                    //删除体检者收费项目
                    for (Peispatientfeeitem feeitem : feeitems) {
                        SyncDataDto syncDataDto3 = new SyncDataDto(snowflake.nextId(), feeitem.getId(), 4, "medical_prod",
                                syncSqlRunService.generateDeleteSql(tc3.getTableName(), tc3.getKeyName(), feeitem.getId()), tc3.getNeedSync(), cid);
                        log.warn("删除体检者收费项目:{}", JSONUtil.toJsonStr(syncDataDto3));
                        try {
                            platformProducer.sendMessage(PF_SYNC_CD_TOPIC_EXCHANGE + "." + jm, PF_SYNC_CD_ROUTING_KEY + jm, JSONUtil.toJsonStr(syncDataDto3));
                        } catch (Exception e) {
                            log.error("消息队列异常，生产数据失败：jm={}、syncDataDto={}", jm, syncDataDto3);
                        }
                    }

                    //删除体检者收费主表
                    for (PeispatientChargeMain chargeMain : chargeMains) {
                        SyncDataDto syncDataDto4 = new SyncDataDto(snowflake.nextId(), chargeMain.getId(), 4, "medical_prod",
                                syncSqlRunService.generateDeleteSql(tc4.getTableName(), tc4.getKeyName(), chargeMain.getId()), tc4.getNeedSync(), cid);
                        log.warn("删除体检者收费主表:{}", JSONUtil.toJsonStr(syncDataDto4));
                        try {
                            platformProducer.sendMessage(PF_SYNC_CD_TOPIC_EXCHANGE + "." + jm, PF_SYNC_CD_ROUTING_KEY + jm, JSONUtil.toJsonStr(syncDataDto4));
                        } catch (Exception e) {
                            log.error("消息队列异常，生产数据失败：jm={}、syncDataDto={}", jm, syncDataDto4);
                        }
                    }

                    //删除体检者结算表
                    for (PeispatientReservationCharge reservationCharge : reservationCharges) {
                        SyncDataDto syncDataDto5 = new SyncDataDto(snowflake.nextId(), reservationCharge.getId(), 4, "medical_prod",
                                syncSqlRunService.generateDeleteSql(tc5.getTableName(), tc5.getKeyName(), reservationCharge.getId()), tc5.getNeedSync(), cid);
                        log.warn("删除体检者结算表:{}", JSONUtil.toJsonStr(syncDataDto5));
                        try {
                            platformProducer.sendMessage(PF_SYNC_CD_TOPIC_EXCHANGE + "." + jm, PF_SYNC_CD_ROUTING_KEY + jm, JSONUtil.toJsonStr(syncDataDto5));
                        } catch (Exception e) {
                            log.error("消息队列异常，生产数据失败：jm={}、syncDataDto={}", jm, syncDataDto5);
                        }
                    }

                    //删除体检者收费表
                    for (Peispatientcharge charge : charges) {
                        SyncDataDto syncDataDto6 = new SyncDataDto(snowflake.nextId(), charge.getId(), 4, "medical_prod",
                                syncSqlRunService.generateDeleteSql(tc6.getTableName(), tc6.getKeyName(), charge.getId()), tc6.getNeedSync(), cid);
                        log.warn("删除体检者收费表:{}", JSONUtil.toJsonStr(syncDataDto6));
                        try {
                            platformProducer.sendMessage(PF_SYNC_CD_TOPIC_EXCHANGE + "." + jm, PF_SYNC_CD_ROUTING_KEY + jm, JSONUtil.toJsonStr(syncDataDto6));
                        } catch (Exception e) {
                            log.error("消息队列异常，生产数据失败：jm={}、syncDataDto={}", jm, syncDataDto6);
                        }
                    }

                    //删除体检者其他缴费表
                    for (PeispatientChargeOther chargeOther : chargeOthers) {
                        SyncDataDto syncDataDto7 = new SyncDataDto(snowflake.nextId(), chargeOther.getId(), 4, "medical_prod",
                                syncSqlRunService.generateDeleteSql(tc7.getTableName(), tc7.getKeyName(), chargeOther.getId()), tc7.getNeedSync(), cid);
                        log.warn("删除体检者其他缴费表:{}", JSONUtil.toJsonStr(syncDataDto7));
                        try {
                            platformProducer.sendMessage(PF_SYNC_CD_TOPIC_EXCHANGE + "." + jm, PF_SYNC_CD_ROUTING_KEY + jm, JSONUtil.toJsonStr(syncDataDto7));
                        } catch (Exception e) {
                            log.error("消息队列异常，生产数据失败：jm={}、syncDataDto={}", jm, syncDataDto7);
                        }
                    }

                    //删除体检者其他缴费记录表
                    for (PeispatientChargeRecord chargeRecord : chargeRecords) {
                        SyncDataDto syncDataDto8 = new SyncDataDto(snowflake.nextId(), chargeRecord.getId(), 4, "medical_prod",
                                syncSqlRunService.generateDeleteSql(tc8.getTableName(), tc8.getKeyName(), chargeRecord.getId()), tc8.getNeedSync(), cid);
                        log.warn("删除体检者其他缴费记录表:{}", JSONUtil.toJsonStr(syncDataDto8));
                        try {
                            platformProducer.sendMessage(PF_SYNC_CD_TOPIC_EXCHANGE + "." + jm, PF_SYNC_CD_ROUTING_KEY + jm, JSONUtil.toJsonStr(syncDataDto8));
                        } catch (Exception e) {
                            log.error("消息队列异常，生产数据失败：jm={}、syncDataDto={}", jm, syncDataDto8);
                        }
                    }
                }


            }
        }
        log.warn("删除已登记体检者在其他分中心数据（每天晚上10点半以后），只在线上系统执行结束：{}", result);
        return result;
    }

}

