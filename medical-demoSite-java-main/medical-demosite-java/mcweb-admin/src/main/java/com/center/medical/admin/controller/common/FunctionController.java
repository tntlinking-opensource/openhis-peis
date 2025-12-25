package com.center.medical.admin.controller.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.center.medical.bean.dto.SyncFileCheckDto;
import com.center.medical.bean.model.*;
import com.center.medical.common.config.Domain;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.file.FileUtils;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.reception.service.RegisterService;
import com.center.medical.report.bean.model.BallCheckReport;
import com.center.medical.report.service.BallCheckReportService;
import com.center.medical.report.service.ReportService;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.service.*;
import com.center.medical.service.*;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 特殊功能接口
 *
 * @author 路飞
 */
@Slf4j
@RestController
@Api(tags = "特殊功能接口")
@RequiredArgsConstructor
public class FunctionController {

    private final RegisterService registerService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final PeispatientChargeMainService peispatientChargeMainService;
    private final PeispatientChargeRecordService peispatientChargeRecordService;
    private final PeispatientchargeService peispatientchargeService;
    private final PeispatientChargeOtherService peispatientChargeOtherService;
    private final PeispatientPhotoService peispatientPhotoService;
    private final PeispatientReservationChargeService peispatientReservationChargeService;
    private final PeispatientarchiveService peispatientarchiveService;
    private final ReportService reportService;
    private final ReportContentService reportContentService;
    private final OtherReportService otherReportService;
    private final ISysConfigService iSysConfigService;
    private final AttachmentService attachmentService;
    private final CreateorderService createorderService;
    private final OrderandfzxService orderandfzxService;
    private final SellcustomerService sellcustomerService;
    private final OrderandcomboService orderandcomboService;
    private final CreatecomboService createcomboService;
    private final CreatemealService createmealService;
    private final ComboandfzxService comboandfzxService;
    private final MealandfzxService mealandfzxService;
    private final MealanditemService mealanditemService;
    private final ComboanditemService comboanditemService;
    private final PeisorgreservationService peisorgreservationService;
    private final VationAndFzxService vationAndFzxService;
    private final PeisorgreservationgroupService peisorgreservationgroupService;
    private final GroupAndFzxService groupAndFzxService;
    private final SystemConfig systemConfig;
    private final BallCheckReportService ballCheckReportService;


    /**
     * 同步订单相关数据
     *
     * @param orderIds
     * @return
     */
    @GetMapping("/function/syncOrder/{orderIds}")
    @ApiOperation(value = "同步订单信息", notes = "同步订单信息，包括：订单信息、订单套餐、套餐、套餐项目等")
    @ApiImplicitParam(name = "orderIds", value = "订单号集合,多个以英文逗号隔开")
    public R syncOrder(@PathVariable List<String> orderIds) {
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
                result = result + "订单的任务主表不存在、";
            } else {
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
            }

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

        return R.ok(result);
    }

    /**
     * 同步订单相关数据
     *
     * @param isRegistered
     * @param orderIds
     * @return
     */
    @GetMapping("/function/syncOrderData/{isRegistered}/{orderIds}")
    @ApiOperation(value = "同步订单相关数据", notes = "同步订单相关数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isRegistered", value = "登记标识：0未登记，1.已登记，2.全部"),
            @ApiImplicitParam(name = "orderIds", value = "订单ID集合,多个以英文逗号隔开")
    })
    public R syncOrderPatient(@PathVariable Integer isRegistered, @PathVariable List<String> orderIds) {
        List<List<Map<String, String>>> resultList = new ArrayList<>();
        String result = "同步结果：";
        for (int i = 0; i < orderIds.size(); i++) {
            //同步订单表
//            SyncData syncData1 = new SyncData();
//            syncData1.setOptType(3);
//            syncData1.setBizDb("medical_prod");
//            syncData1.setBizTable("md_peispatient");
//            syncData1.setBizModifydate(new Date());
//            syncData1.setIsAll(0);
//            syncData1.setStatus(0);
//            syncData1.setBizId(list.get(i).getId());
//            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData1, DateUtil.currentSeconds());

            LambdaQueryWrapper<Peispatient> wrapper = new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getNumorgresv, orderIds.get(i));
            if (isRegistered == 0) {
                wrapper.eq(Peispatient::getFRegistered, 0).or().isNull(Peispatient::getFRegistered);
            } else if (isRegistered == 1) {
                wrapper.eq(Peispatient::getFRegistered, 1);
            }
            List<Peispatient> list = registerService.list(wrapper);
            List<String> patientCodes = list.stream().map(Peispatient::getPatientcode).collect(Collectors.toList());
//            log.info("体检号数量：{}、{}", patientCodes.size(), patientCodes);
            result = result + "【订单" + orderIds.get(i) + "共" + patientCodes.size() + "个体检好号】";
            if (patientCodes.size() > 0)
                resultList.add(dosSyncPatient(patientCodes));
        }

        return R.ok(result);
    }


    /**
     * 同步体检者信息
     *
     * @param patientCodes
     * @return
     */
    @GetMapping("/function/syncPatient/{patientCodes}")
    @ApiOperation(value = "同步体检者信息", notes = "同步体检者信息，多个以英文逗号隔开")
    @ApiImplicitParam(name = "patientCodes", value = "体检号列表")
    public R syncPatient(@PathVariable List<String> patientCodes) {
        dosSyncPatient(patientCodes);
        return R.ok("");
    }


    /**
     * 同步体检者头像
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/function/syncPatientPhoto")
    @ApiOperation(value = "同步体检者头像", notes = "同步体检者头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "截至时间")
    })
    public R syncPatient(String startTime, String endTime) {
        //同步体检者头像
        List<PeispatientPhoto> photos = peispatientPhotoService.list(new LambdaQueryWrapper<PeispatientPhoto>()
                .ge(PeispatientPhoto::getCreatedate, startTime).lt(PeispatientPhoto::getCreatedate, endTime));
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
        return R.ok(photos.size());
    }


    /**
     * 将体检者头像设置为默认头像
     *
     * @param patientCodes
     * @return
     */
    @GetMapping("/function/setPatientPhoto/{patientCodes}")
    @ApiOperation(value = "将体检者头像设置为默认头像", notes = "将体检者头像设置为默认头像")
    @ApiImplicitParam(name = "patientCodes", value = "要同步的体检号，多个以英文逗号隔开")
    public R setPatientPhoto(@PathVariable List<String> patientCodes) {
        PeispatientPhoto peispatientPhoto = new PeispatientPhoto();
        peispatientPhoto.setPicture("/newimage/images/avator/default.jpg");
        peispatientPhotoService.update(peispatientPhoto, new LambdaUpdateWrapper<PeispatientPhoto>().in(PeispatientPhoto::getPatientcode, patientCodes));
        return R.ok("设置成功");
    }


    /**
     * 同步体检者报告
     *
     * @param patientCodes
     * @return
     */
    @GetMapping("/function/syncReport/{patientCodes}")
    @ApiOperation(value = "同步体检者报告", notes = "同步体检者报告")
    @ApiImplicitParam(name = "patientCodes", value = "要同步的体检号，多个以英文逗号隔开")
    public R syncReport(@PathVariable List<String> patientCodes) {
        // 同步报告数据
        dosSyncReport(patientCodes);
        return R.ok("");
    }

    /**
     * 同步体检者信息
     *
     * @param patientCodes
     * @return
     */
    private List<Map<String, String>> dosSyncPatient(List<String> patientCodes) {
        List<Map<String, String>> result = new ArrayList<>();
        List<Peispatient> list = registerService.list(new LambdaQueryWrapper<Peispatient>().in(Peispatient::getPatientcode, patientCodes));
        for (int i = 0; i < list.size(); i++) {
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

            result.add(item);

        }
        // 同步报告数据
        dosSyncReport(patientCodes);
        //log.warn("同步体检者信息:{}", JSONUtil.toJsonStr(result));
        return result;
    }

    /**
     * 同步体检报告信息
     *
     * @param patientCodes
     * @return
     */
    private void dosSyncReport(List<String> patientCodes) {
        List<Report> reports = reportService.list(new LambdaQueryWrapper<Report>().in(Report::getPatientcode, patientCodes));
        for (Report report : reports) {
            SyncData syncData1 = new SyncData();
            syncData1.setOptType(3);
            syncData1.setBizDb("medical_prod");
            syncData1.setBizTable("md_report");
            syncData1.setBizModifydate(new Date());
            syncData1.setIsAll(0);
            syncData1.setStatus(0);
            syncData1.setBizId(report.getId());
            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData1, DateUtil.currentSeconds());
        }
        List<ReportContent> reportContents = reportContentService.list(new LambdaQueryWrapper<ReportContent>().in(ReportContent::getPatientcode, patientCodes));
        for (ReportContent rc : reportContents) {
            SyncData syncData1 = new SyncData();
            syncData1.setOptType(3);
            syncData1.setBizDb("medical_prod");
            syncData1.setBizTable("md_report_content");
            syncData1.setBizModifydate(new Date());
            syncData1.setIsAll(0);
            syncData1.setStatus(0);
            syncData1.setBizId(rc.getId());
            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData1, DateUtil.currentSeconds());
        }
        List<OtherReport> otherReports = otherReportService.list(new LambdaQueryWrapper<OtherReport>().in(OtherReport::getPatientcode, patientCodes));
        for (OtherReport otherReport : otherReports) {
            SyncData syncData1 = new SyncData();
            syncData1.setOptType(3);
            syncData1.setBizDb("medical_prod");
            syncData1.setBizTable("md_other_report");
            syncData1.setBizModifydate(new Date());
            syncData1.setIsAll(0);
            syncData1.setStatus(0);
            syncData1.setBizId(otherReport.getId());
            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData1, DateUtil.currentSeconds());
        }

        log.info("同步报告执行成功：report={}条、reportContents={}条、otherReports={}条", reports.size(), reportContents.size(), otherReports.size());

    }

    /**
     *
     */
    @GetMapping("/function/countFeeItems/{orderId}")
    @ApiOperation(value = "查询订单的收费项目", notes = "查询订单的收费项目")
    public R countFeeItems(@PathVariable String orderId) {
        List<Peispatient> list = registerService.list(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getNumorgresv, orderId));
        int l2 = 0;
        for (int i = 0; i < list.size(); i++) {
            String patientcode = list.get(i).getPatientcode();
            List<Peispatientfeeitem> list1 = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, patientcode));
            l2 = l2 + list1.size();
//            for (int j = 0; j < list1.size(); j++) {
//                peispatientfeeitemService.updateById(list1.get(j));
//            }

        }
        return R.ok(list.size() + "----" + l2);
    }


    /**
     * 查询上线PDF报告是否存在
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws IOException
     */
    @GetMapping("/function/reportExist")
    @ApiOperation(value = "查询上线PDF报告是否存在", notes = "查询报告是否存在，如果存在则跳过，否则重新上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "截至时间"),
            @ApiImplicitParam(name = "flag", value = "时间查询标识：1.创建时间，2.更新时间，3.生成时间，4.登记时间，5.总检时间")
    })
    public R reportExist(String startTime, String endTime, Integer flag) throws IOException {
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();

        switch (flag) {
            case 1:
                wrapper.ge(Report::getCreatedate, startTime).le(Report::getCreatedate, endTime);
                break;
            case 3:
                wrapper.ge(Report::getGenerateDate, startTime).le(Report::getGenerateDate, endTime);
                break;
            case 4:
                wrapper.ge(Report::getDateregister, startTime).le(Report::getDateregister, endTime);
                break;
            case 5:
                wrapper.ge(Report::getDatefinalexamed, startTime).le(Report::getDatefinalexamed, endTime);
                break;
            default:
                wrapper.ge(Report::getModifydate, startTime).le(Report::getModifydate, endTime);
        }
        List<Report> reports = reportService.list(wrapper);
        Domain domain = iSysConfigService.getDomain();
//        String host = domain.getRsPfDomain();
        //同步至线上
//        host = host.endsWith("/") ? host : host + "/";
        String success = "success：";
//        String fail = "fail：";
        for (Report report : reports) {
            String filePath = report.getUrlPdf();
            if (StringUtils.isNotBlank(filePath)) {
                filePath = filePath.startsWith("/") ? filePath : StrUtil.subAfter(filePath, "/", false);
//                log.info("判断的文件：{}、{}", domain.getRsPfDomain() + filePath, domain.getRsLcDomain() + filePath);
                if (!FileUtils.areFilesSameSize(domain.getRsPfDomain() + filePath, domain.getRsLcDomain() + filePath)) {
//                String localFilePath = host + filePath;
//                InputStream inputStream = null;
//                try {
//                    URL url = new URL(localFilePath);
//                    URLConnection connection = url.openConnection();
//                    connection.getInputStream();
//                } catch (IOException e) {
                    //图片不存在，需重新上传
//                    try {
//                        attachmentService.uploadSyncFile(domain.getRsLcDomain(), filePath, 1);

                    SyncFileCheckDto param = new SyncFileCheckDto();
                    param.setUpdateDate(new Date());
                    param.setBizId(report.getId());
                    param.setCount(0);
                    param.setPatientcode(report.getPatientcode());
                    param.setFileUrl(report.getUrlPdf());
                    param.setToFlag(1);
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_CHECK, param, DateUtil.currentSeconds());
                    log.info("报告同步失败记录：{}", report.getId());
                    success = success + ",'" + report.getId() + "'";
//                    } catch (IOException ioException) {
//                        //上传失败
//                        log.error("手动上传PDF报告文件失败：{}", report);
//                        fail = fail + ",'" + report.getId() + "'";
//                    }
//                    System.out.println("没有pdf:" + report.getUrlPdf());
//                    fail = fail + ",'" + report.getId() + "'";
//                }
                }

            }

        }
        return R.ok(success);
    }


    /**
     * 判断文件同步是否成功，如果已成功则跳过，否则重新上传
     *
     * @param param 文件参数
     * @return
     */
    @GetMapping("/function/syncFile")
    @ApiOperation(value = "判断文件同步是否成功", notes = "判断文件同步是否成功，如果已成功则跳过，否则重新上传")
    public R reportExist(SyncFileCheckDto param) {
        param.setUpdateDate(new Date());
        RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_CHECK, param, DateUtil.currentSeconds());
        return R.ok("添加成功，正在执行上传！");
    }

    /**
     * 图片同步
     *
     * @param host
     * @param paths
     * @param flag
     * @return
     */
    @PostMapping("/function/syncFiles")
    @ApiOperation(value = "图片同步", notes = "图片同步")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "host", value = "图片来源host地址"),
            @ApiImplicitParam(name = "paths", value = "图片路径"),
            @ApiImplicitParam(name = "flag", value = "目的地标识：0线上同步到线下，1线下同步到线上")
    })
    public R syncFiles(String host, List<String> paths, Integer flag) {
        String success = "success：";
        String fail = "fail：";
        for (String path : paths) {
            try {
                attachmentService.uploadSyncFile(host, path, flag);
                success = success + "," + path;
            } catch (IOException e) {
                log.error("上传失败test：{}", e);
                fail = fail + "," + path;
            }
        }
        return R.ok(success + "------" + fail);
    }



    /**
     * 清除附件表中的特殊符号
     */
    @PostMapping("/function/clearSpecialSymbols")
    @ApiOperation(value = "清除附件表中的特殊符号", notes = "清除附件表中的特殊符号")
    @ApiImplicitParam(name = "symbol", value = "特殊符号")
    public R clearSpecialSymbols(@RequestParam String symbol) {
        /**
         * 有时候还把目录地址和\\存进去了，直接用语句修改吧
         * UPDATE md_attachment
         * SET file_path = REPLACE(REPLACE(file_path, 'G:\\newimages\\', '/'), '\\', '/')
         * WHERE file_path LIKE 'G:%';
         */
        Boolean b = attachmentService.clearSpecialSymbols(symbol);
        return R.ok(b);
    }








    /**
     * 查询上线PDF报告是否存在
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws IOException
     */
    @GetMapping("/function/uploadHuaWeiReport")
    @ApiOperation(value = "上传华为云", notes = "上传华为云，如果存在则跳过，否则重新上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "截至时间"),
    })
    public R uploadHuaWeiReport(String startTime, String endTime) throws IOException {
        log.info("开始执行上传华为云");
        long time = System.currentTimeMillis();
        List<Report> reports = reportService.list(new LambdaQueryWrapper<Report>()
                .ge(Report::getGenerateDate, startTime)
                .le(Report::getGenerateDate, endTime)
                .isNotNull(Report::getUrlPdf)
                .orderByAsc(Report::getGenerateDate));
        Domain domain = iSysConfigService.getDomain();
        for (Report report : reports) {
            String filePath = report.getUrlPdf();
            if (StringUtils.isNotBlank(filePath)) {
                try{
                    if (!FileUtils.areFilesSameSize(domain.getRsPfDomain() + filePath, domain.getRsLcDomain() + filePath)){
                        log.info("开始执行上传华为云,:体检号：{}",report.getPatientcode());
                        attachmentService.uploadHuaWeiReport(domain.getRsLcDomain(), filePath);
                        log.info("上传华为云成功,:体检号：{}",report.getPatientcode());
                    }else {
                        log.info("文件大小相同，跳过上传,:体检号：{}",report.getPatientcode());
                    }
                } catch (Exception e) {
                    log.info("上传华为云失败,:体检号：{}",report.getPatientcode());
                }
            }
        }
        log.info("开始执行上传华为云结束");
        log.info("本共耗时："+((System.currentTimeMillis()-time)/1000)+"秒");
        return R.ok("本共耗时："+((System.currentTimeMillis()-time)/1000)+"秒");
    }


    /**
     * 查询上线PDF报告是否存在
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws IOException
     */
    @GetMapping("/function/uploadBallCheckReport")
    @ApiOperation(value = "上传团检报告华为云", notes = "上传华为云，如果存在则跳过，否则重新上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "截至时间"),
    })
    public R uploadBallCheckReport(String startTime, String endTime) throws IOException {
        log.info("团检报告开始执行上传华为云");
        long time = System.currentTimeMillis();
        List<BallCheckReport> reports = ballCheckReportService.list(new LambdaQueryWrapper<BallCheckReport>()
                .ge(BallCheckReport::getCreatedate, startTime)
                .le(BallCheckReport::getCreatedate, endTime)
                .isNotNull(BallCheckReport::getPdfUrl)
                .orderByAsc(BallCheckReport::getCreatedate));
        Domain domain = iSysConfigService.getDomain();
        for (BallCheckReport report : reports) {
            //上传pdf
            String filePath = report.getPdfUrl();
            if (StringUtils.isNotBlank(filePath)) {
                try{
                    if (!FileUtils.areFilesSameSize(domain.getRsPfDomain() + filePath, domain.getRsLcDomain() + filePath)){
                        log.info("团检报告开始执行上传华为云,:订单号：{}",report.getOrderId());
                        attachmentService.uploadHuaWeiReport(domain.getRsLcDomain(), filePath);
                        log.info("团检报告上传华为云成功,:订单号：{}",report.getOrderId());
                    }else {
                        log.info("团检报告文件大小相同，跳过上传,:订单号：{}",report.getOrderId());
                    }
                } catch (Exception e) {
                    log.info("团检报告上传华为云失败,:订单号：{}",report.getOrderId());
                }
            }
            //上传word
            String wordUrl = report.getWordUrl();
            if (StringUtils.isNotBlank(wordUrl)) {
                try{
                    if (!FileUtils.areFilesSameSize(domain.getRsPfDomain() + wordUrl, domain.getRsLcDomain() + wordUrl)){
                        log.info("团检报告开始执行上传华为云word,:订单号：{}",report.getOrderId());
                        attachmentService.uploadHuaWeiReport(domain.getRsLcDomain(), wordUrl);
                        log.info("团检报告上传华为云成功word,:订单号：{}",report.getOrderId());
                    }else {
                        log.info("团检报告文件大小相同word，跳过上传,:订单号：{}",report.getOrderId());
                    }
                } catch (Exception e) {
                    log.info("团检报告上传华为云失败word,:订单号：{}",report.getOrderId());
                }
            }

        }
        log.info("团检报告开始执行上传华为云结束");
        log.info("本共耗时："+((System.currentTimeMillis()-time)/1000)+"秒");
        return R.ok("本共耗时："+((System.currentTimeMillis()-time)/1000)+"秒");
    }
}
