package com.center.medical.platform.controller.common;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.*;
import com.center.medical.bean.param.AddMealAndFzxParam;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.file.FileUtils;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.data.bean.model.ItemsAndFzx;
import com.center.medical.data.service.ItemsAndFzxService;
import com.center.medical.reception.service.OrderService;
import com.center.medical.reception.service.RegisterService;
import com.center.medical.reception.service.SyncCommonService;
import com.center.medical.report.service.ReportService;
import com.center.medical.sellcrm.bean.model.Comboandfzx;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.Mealandfzx;
import com.center.medical.sellcrm.service.ComboandfzxService;
import com.center.medical.sellcrm.service.MealandfzxService;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.service.*;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.system.bean.model.HelpDocument;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.HelpDocumentService;
import com.center.medical.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
    private final PeispatientPhotoService peispatientPhotoService;
    private final ReportService reportService;
    private final ReportContentService reportContentService;
    private final OtherReportService otherReportService;
    private final ISysConfigService iSysConfigService;
    private final AttachmentService attachmentService;
    private final SyncCommonService syncCommonService;
    private final OrderService orderService;
    private final PeispatientService peispatientService;
    private final PeispatientChargeMainService peispatientChargeMainService;
    private final HelpDocumentService helpDocumentService;
    private final OrderHistoryStatisticsService orderHistoryStatisticsService;
    private final MealandfzxService mealandfzxService;
    private final ComboandfzxService comboandfzxService;
    private final ItemsAndFzxService itemsAndFzxService;
    private final SystemConfig systemConfig;
    private final SellcustomerService sellcustomerService;

    /**
     * 同步订单相关数据
     *
     * @param orderIds
     * @return
     */
    @GetMapping("/function/syncOrder/{orderIds}")
    @Log(title = "特殊功能接口-根据订单号同步订单信息", businessType = BusinessType.OTHER)
    @ApiOperation(value = "根据订单号同步订单信息", notes = "根据订单号同步订单信息，包括：订单信息、订单套餐、套餐、套餐项目等")
    @ApiImplicitParam(name = "orderIds", value = "订单号集合,多个以英文逗号隔开")
    public R syncOrder(@PathVariable List<String> orderIds) {
        return R.ok(syncCommonService.syncOrderData(orderIds));
    }

    /**
     * 同步订单相关数据
     *
     * @param isRegistered
     * @param orderIds
     * @return
     */
    @GetMapping("/function/syncOrderData/{isRegistered}/{orderIds}")
    @Log(title = "特殊功能接口-同步订单下的体检者相关数据", businessType = BusinessType.OTHER)
    @ApiOperation(value = "同步订单下的体检者相关数据", notes = "同步订单下的体检者相关数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isRegistered", value = "登记标识：0未登记，1.已登记，2.全部"),
            @ApiImplicitParam(name = "orderIds", value = "订单ID集合,多个以英文逗号隔开")
    })
    public R syncOrderPatient(@PathVariable Integer isRegistered, @PathVariable List<String> orderIds) {
        List<List<Map<String, String>>> resultList = new ArrayList<>();
        String result = "同步结果：";
        for (int i = 0; i < orderIds.size(); i++) {

            LambdaQueryWrapper<Peispatient> wrapper = new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getNumorgresv, orderIds.get(i));
            if (isRegistered == 0) {
                wrapper.eq(Peispatient::getFRegistered, 0).or().isNull(Peispatient::getFRegistered);
            } else if (isRegistered == 1) {
                wrapper.eq(Peispatient::getFRegistered, 1);
            }
            List<Peispatient> list = registerService.list(wrapper);
            List<String> patientCodes = list.stream().map(Peispatient::getPatientcode).collect(Collectors.toList());
//            log.info("体检号数量：{}、{}", patientCodes.size(), patientCodes);
            result = result + "【订单" + orderIds.get(i) + "共" + patientCodes.size() + "个体检号】";
            if (patientCodes.size() > 0){
                //执行保存操作，线程池设置了一个核心线程和最大线程都为1的线程池，空闲时间为600秒。如果在600秒内没有任务提交，线程池会自动释放线程资源。当有新任务提交时，线程池会重新启动线程来处理任务。
                ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 6, 600, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
                // 提交任务到线程池
                executor.submit(() -> {
                    // 执行任务逻辑
                    resultList.add(dosSyncPatient(patientCodes));
                });
            }

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
    @Log(title = "特殊功能接口-根据体检号同步体检者信息", businessType = BusinessType.OTHER)
    @ApiOperation(value = "根据体检号同步体检者信息", notes = "根据体检号同步体检者信息，多个以英文逗号隔开")
    @ApiImplicitParam(name = "patientCodes", value = "体检号列表")
    public R syncPatient(@PathVariable List<String> patientCodes) {
        dosSyncPatient(patientCodes);
        return R.ok("");
    }


    /**
     * 同步体检者收费项目
     *
     * @param file
     * @return
     */
    @GetMapping("/function/syncPatientItem")
    @Log(title = "特殊功能接口-同步体检者收费项目", businessType = BusinessType.OTHER)
    @ApiOperation(value = "同步体检者收费项目", notes = "同步体检者收费项目，多个以英文逗号隔开")
    @ApiImplicitParam(name = "file", value = "体检号列表文件")
    public R syncPatientItem(MultipartFile file) throws Exception {
        if (Objects.isNull(file)) {
            throw new GlobalException("请选择上传文件！");
        }
        String extName = FileUtil.extName(file.getOriginalFilename());
        if (!extName.toLowerCase().endsWith("xls") && !extName.toLowerCase().endsWith("xlsx")) {
            throw new GlobalException("请选择正确的文件类型，必须是以.xls或.xlsx结尾！");
        }
        ExcelUtil<SyncPatient> util = new ExcelUtil<>(SyncPatient.class);
        List<SyncPatient> list = util.importExcel(file.getInputStream());
        if (CollectionUtil.isEmpty(list)) {
            throw new GlobalException("文件导入失败：该文件没数据，不能进行导入！");
        }
        for (SyncPatient item : list) {
            if (StringUtils.isBlank(item.getPatientCode())) {
                dosSyncPatient(Arrays.asList(item.getPatientCode().trim()));
            }

        }
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
    @ApiOperation(value = "特殊功能接口-同步体检者头像", notes = "同步体检者头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "截至时间")
    })
    public R syncPatientPhoto(String startTime, String endTime) {
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
     * 同步体检者报告
     *
     * @param patientCodes
     * @return
     */
    @GetMapping("/function/syncReport/{patientCodes}")
    @Log(title = "特殊功能接口-同步体检者报告", businessType = BusinessType.OTHER)
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
        return syncCommonService.syncPatientData(patientCodes);
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
        String host = domain.getRsPfDomain();
        //同步至线上
        host = host.endsWith("/") ? host : host + "/";
        String success = "success：";
        String fail = "fail：";
        for (Report report : reports) {
            String filePath = report.getUrlPdf();
            if (StringUtils.isNotBlank(filePath)) {
                filePath = filePath.startsWith("/") ? StrUtil.subAfter(filePath, "/", false) : filePath;
                String localFilePath = host + filePath;
                try {
                    URL url = new URL(localFilePath);
                    URLConnection connection = url.openConnection();
                    connection.getInputStream();
                } catch (IOException e) {
                    //图片不存在，需重新上传
                    try {
                        attachmentService.uploadSyncFile(domain.getRsLcDomain(), filePath, 1);
                        success = success + ",'" + report.getId() + "'";
                    } catch (IOException ioException) {
                        //上传失败
                        log.error("手动上传PDF报告文件失败：{}", report);
                        fail = fail + ",'" + report.getId() + "'";
                    }
//                    System.out.println("没有pdf:" + report.getUrlPdf());
//                    fail = fail + ",'" + report.getId() + "'";
                }

            }

        }
        return R.ok(success + "====" + fail);
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
    @Log(title = "特殊功能接口-图片同步", businessType = BusinessType.DELETE)
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
     * 按时间段同步变化的订单
     */
    @PostMapping("/function/syncOrderWithDuringTime")
    @Log(title = "特殊功能接口-按时间段同步变化的订单", businessType = BusinessType.OTHER)
    @ApiOperation(value = "按时间段同步变化的订单", notes = "按时间段同步变化的订单，按修改时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "截至时间")
    })
    public R syncTodayOrderData(String startTime, String endTime) {
        log.info("--------------按时间段同步变化的订单--------------");
        //获取当天变化的订单
        LambdaQueryWrapper<Createorder> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(Createorder::getModifydate, startTime);
        wrapper.le(Createorder::getModifydate, endTime);
        List<Createorder> list = orderService.list(wrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            List<String> orderIds = list.stream().map(Createorder::getDdh).collect(Collectors.toList());
            return R.ok(syncCommonService.syncOrderData(orderIds));
        }
        return R.ok("没有需要同步的数据");
    }


    /**
     * 按时间段同步体检者信息，按创建时间
     */
    @PostMapping("/function/syncPatientWithDuringTime")
    @Log(title = "特殊功能接口-按创建时间段同步体检者信息", businessType = BusinessType.OTHER)
    @ApiOperation(value = "按时间段同步体检者信息", notes = "按时间段同步体检者信息，按创建时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "截至时间")
    })
    public R syncPatientData(String startTime, String endTime) {
        log.info("--------------按时间段同步体检者信息--------------");
        //获取当天需要同步的体检者
        List<Peispatient> list;
        LambdaQueryWrapper<Peispatient> wrapper = new LambdaQueryWrapper<>();
        if (ZhongkangConfig.isOnline()) {
            //线上同步至线下，获取未登记的
            wrapper.ge(Peispatient::getCreatedate, startTime);
            wrapper.le(Peispatient::getCreatedate, endTime);
            wrapper.eq(Peispatient::getFRegistered, 0);
            list = registerService.list(wrapper);
        } else {
            //线下同步至线上，获取已登记的
            wrapper.ge(Peispatient::getDateregister, startTime);
            wrapper.le(Peispatient::getDateregister, endTime);
            wrapper.eq(Peispatient::getFRegistered, 1);
            list = registerService.list(wrapper);
        }
//        log.info("同步当天体检者信息list{}", list);
        if (CollectionUtil.isNotEmpty(list)) {
            List<String> patientCodes = list.stream().map(Peispatient::getPatientcode).collect(Collectors.toList());
            return R.ok(syncCommonService.syncPatientData(patientCodes));

        }
        return R.ok("没有需要同步的数据");
    }




    /**
     * 按时间段同步体检者信息，按创建时间
     */
    @PostMapping("/function/syncAllPatientWithDuringTime")
    @Log(title = "特殊功能接口-按创建时间段同步体检者信息", businessType = BusinessType.OTHER)
    @ApiOperation(value = "按时间段同步体检者信息", notes = "按时间段同步体检者信息，按创建时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "截至时间"),
            @ApiImplicitParam(name = "isRegistered", value = "登记标识：0未登记，1.已登记，2.全部")
    })
    public R syncAllPatientData(String startTime, String endTime, Integer isRegistered) {
        log.info("--------------按时间段同步体检者信息--------------");
        //获取当天需要同步的体检者
        LambdaQueryWrapper<Peispatient> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(Peispatient::getModifydate, startTime);
        wrapper.le(Peispatient::getModifydate, endTime);
        if (isRegistered == 1) {
            wrapper.eq(Peispatient::getFRegistered, 1);
        } else if (isRegistered == 0){
            wrapper.eq(Peispatient::getFRegistered, 0);
        }
        List<Peispatient> list = registerService.list(wrapper);
//        log.info("同步当天体检者信息list{}", list);
        if (CollectionUtil.isNotEmpty(list)) {
            List<String> patientCodes = list.stream().map(Peispatient::getPatientcode).collect(Collectors.toList());
            return R.ok(syncCommonService.syncPatientData(patientCodes));

        }
        return R.ok("没有需要同步的数据");
    }


    /**
     * 删除已登记体检者在其他分中心数据
     */
    @PostMapping("/function/delOtherPatientWithDuringTime")
    @Log(title = "特殊功能接口-按时间段删除已登记体检者在其他分中心数据", businessType = BusinessType.DELETE)
    @ApiOperation(value = "删除已登记体检者在其他分中心数据", notes = "按登记时间段删除已登记体检者在其他分中心数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "截至时间")
    })
    public R delOtherPatient(String startTime, String endTime) {
        log.info("--------------删除已登记体检者在其他分中心数据--------------");
        //获取当天需要同步的体检者
        LambdaQueryWrapper<Peispatient> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(Peispatient::getDateregister, startTime);
        wrapper.le(Peispatient::getDateregister, endTime);
        wrapper.eq(Peispatient::getFRegistered, 1);
        List<Peispatient> list;
        if (ZhongkangConfig.isOnline()) {
            //线上，获取当天登记的体检者
            list = registerService.list(wrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                List<String> patientCodes = list.stream().map(Peispatient::getPatientcode).collect(Collectors.toList());
                return R.ok(syncCommonService.delOtherPatient(patientCodes));
            }
        }
        return R.ok("没有需要删除的数据");
    }

    /**
     * 删除已登记体检者在其他分中心数据
     */
    @PostMapping("/function/delOtherPatientByDdh")
    @Log(title = "特殊功能接口-按订单号删除已登记体检者在其他分中心数据", businessType = BusinessType.DELETE)
    @ApiOperation(value = "根据订单号删除已登记体检者在其他分中心数据", notes = "根据订单号删除已登记体检者在其他分中心数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddhs", value = "订单号,多个以逗号隔开")
    })
    public R delOtherPatientByDdh(@RequestParam List<String> ddhs) {
        log.info("--------------删除已登记体检者在其他分中心数据--------------");
        //获取当天需要同步的体检者
        LambdaQueryWrapper<Peispatient> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Peispatient::getNumorgresv, ddhs);
        wrapper.eq(Peispatient::getFRegistered, 1);
        List<Peispatient> list;
        if (ZhongkangConfig.isOnline()) {
            //线上，获取当天登记的体检者
            list = registerService.list(wrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                List<String> patientCodes = list.stream().map(Peispatient::getPatientcode).collect(Collectors.toList());
                return R.ok(syncCommonService.delOtherPatient(patientCodes));
            }
        }
        return R.ok("没有需要删除的数据");
    }

    /**
     * 生成体检者的消费主表
     */
    @PostMapping("/function/gen/chargeMain")
    @Log(title = "特殊功能接口-生成体检者的消费主表", businessType = BusinessType.INSERT)
    @ApiOperation(value = "生成体检者的消费主表", notes = "生成体检者的消费主表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcodes", value = "体检号,多个以逗号隔开")
    })
    public R genChargeMain(@RequestParam List<String> patientcodes) {
        log.info("--------------生成体检者的消费主表--------------");
        for (String patientcode : patientcodes) {
            List<PeispatientChargeMain> list = peispatientChargeMainService.list(new LambdaQueryWrapper<PeispatientChargeMain>().eq(PeispatientChargeMain::getPatientcode, patientcode));
            if (CollectionUtil.isEmpty(list)){
                PeispatientChargeMain peispatientChargeMain = new PeispatientChargeMain("", 0.00, 0.00, patientcode);
                peispatientChargeMainService.save(peispatientChargeMain);
            }
        }
        return R.ok("已生成完！");
    }



    /**
     * 同步体检者信息
     *
     * @param
     * @return
     */
    @GetMapping("/function/syncHelpDocument")
    @ApiOperation(value = "同步操作说明", notes = "同步操作说明")
    public R syncHelpDocument() {
        HelpDocument helpDocument = new HelpDocument();
        helpDocument.setModifydate(new Date());
        helpDocumentService.update(helpDocument,new LambdaQueryWrapper<HelpDocument>().eq(HelpDocument::getIsDelete,0));
        return R.ok("更新成功");
    }





    /**
     * 同步体检者信息
     *
     * @param
     * @return
     */
    @PostMapping("/function/recalculatePrice")
    @ApiOperation(value = "重新计算价格(未收费)", notes = "先搜索出价格不对的未登记的体检号,再修改这些收费项目的价格")
    @ApiImplicitParam(name = "ddhs", value = "订单号,多个以逗号隔开")
    public R recalculatePrice(@RequestParam List<String> ddhs) {
        String string = orderService.recalculatePrice(ddhs);
        return R.ok(string);
    }





    /**
     * 插入订单历史折扣成本率等
     */
    @PostMapping("/function/insertOrderHistoryStatistics")
    @ApiOperation(value = "插入订单历史折扣成本率等", notes = "插入订单历史折扣成本率等(一年一次就行了),老系统sql在下面")
    public R insertOrderHistoryStatistics(@RequestBody BaseParam param) {
        Boolean b = orderHistoryStatisticsService.insertOrderHistoryStatistics(param);
        return R.ok(b);
    }




    /**
     * 插入订单历史折扣成本率等
     */
    @PostMapping("/function/bindingVationAndGroupAgain")
    @ApiOperation(value = "线上重新绑定体检者任务和分组", notes = "线上重新绑定任务和分组,返回一个任务id，需要从线上把对应的任务和分组复制到线下")
    @ApiImplicitParam(name = "ddhs", value = "订单号,多个以逗号隔开")
    public R bindingVationAndGroupAgain(@RequestParam List<String> ddhs) {
        /**
         * 查询sql语句，注意，有一些线上有，有一些线下有
         * 先把线上缺的的同步上来，再去操作
         * SELECT
         * 	p.numorgresv,
         * 	p.id_orgreservation,
         * 	pv2.id
         * FROM
         * 	md_peispatient p
         * 	LEFT JOIN md_peisorgreservation pv ON p.id_orgreservation = pv.id
         * 	LEFT JOIN md_createorder co ON co.ddh = p.numorgresv
         * 	LEFT JOIN md_peisorgreservation pv2 ON co.id = pv2.ddh
         * WHERE
         * 	pv.id IS NULL
         * 	AND ( p.numorgresv IS NOT NULL AND p.numorgresv != '' )
         * 	AND p.id_orgreservation IS NOT NULL
         * GROUP BY
         * 	numorgresv
         */
        String str = orderService.bindingVationAndGroupAgain(ddhs);
        return R.ok(str);
    }




    /**
     * 同步体检者信息
     *
     * @param
     * @return
     */
    @PostMapping("/function/cleanUpInvalidImages")
    @ApiOperation(value = "查询清理失效图片", notes = "清理失效图片")
    public R cleanUpInvalidImages() {
        String b = orderService.cleanUpInvalidImages();
        return R.ok(b);
    }





    /**
     * 添加套餐和分中心接口
     *
     * @return
     */
    @PostMapping("/function/addMealAndFzx")
    @Log(title = "添加套餐和分中心接口", businessType = BusinessType.INSERT)
    @ApiOperation(value = "添加套餐和分中心接口", notes = "添加套餐和分中心接口")
    public R addMealAndFzx(@RequestBody AddMealAndFzxParam param) {
        List<Mealandfzx> list = new ArrayList<>();
        List<String> ids = param.getIds();
        List<String> fzxs = param.getFzxs();
        for (String id : ids) {
            for (String fzx : fzxs) {
                long count = mealandfzxService.count(new LambdaQueryWrapper<Mealandfzx>()
                        .eq(Mealandfzx::getTcid, id).eq(Mealandfzx::getFzxid, fzx));
                if (count==0){
                    Mealandfzx mealandfzx = new Mealandfzx();
                    mealandfzx.setFzxid(fzx);
                    mealandfzx.setTcid(id);
                    list.add(mealandfzx);
                }
            }
        }
        if (CollectionUtil.isNotEmpty(list)){
            mealandfzxService.saveBatch(list);
        }
        return R.ok();
    }




    /**
     * 添加套餐和分中心接口
     *
     * @return
     */
    @PostMapping("/function/addComboAndFzx")
    @Log(title = "添加最小套餐和分中心接口", businessType = BusinessType.INSERT)
    @ApiOperation(value = "添加最小套餐和分中心接口", notes = "添加最小套餐和分中心接口")
    public R addComboAndFzx(@RequestBody AddMealAndFzxParam param) {
        List<Comboandfzx> list = new ArrayList<>();
        List<String> ids = param.getIds();
        List<String> fzxs = param.getFzxs();
        for (String id : ids) {
            for (String fzx : fzxs) {
                long count = comboandfzxService.count(new LambdaQueryWrapper<Comboandfzx>()
                        .eq(Comboandfzx::getTcid, id).eq(Comboandfzx::getFzxid, fzx));
                if (count==0){
                    Comboandfzx comboandfzx = new Comboandfzx();
                    comboandfzx.setFzxid(fzx);
                    comboandfzx.setTcid(id);
                    list.add(comboandfzx);
                }
            }
        }
        if (CollectionUtil.isNotEmpty(list)){
            comboandfzxService.saveBatch(list);
        }
        return R.ok();
    }




    /**
     * 添加套餐和分中心接口
     *
     * @return
     */
    @PostMapping("/function/addItemsAndFzx")
    @Log(title = "添加收费项目和分中心接口", businessType = BusinessType.INSERT)
    @ApiOperation(value = "添加收费项目和分中心接口", notes = "添加收费项目和分中心接口")
    public R addItemsAndFzx(@RequestBody AddMealAndFzxParam param) {
        List<ItemsAndFzx> list = new ArrayList<>();
        List<String> ids = param.getIds();
        List<String> fzxs = param.getFzxs();
        for (String id : ids) {
            for (String fzx : fzxs) {
                long count = itemsAndFzxService.count(new LambdaQueryWrapper<ItemsAndFzx>()
                        .eq(ItemsAndFzx::getItemsId, id).eq(ItemsAndFzx::getFzxId, fzx));
                if (count==0){
                    ItemsAndFzx itemsAndFzx = new ItemsAndFzx();
                    itemsAndFzx.setFzxId(fzx);
                    itemsAndFzx.setItemsId(id);
                    list.add(itemsAndFzx);
                }
            }
        }
        if (CollectionUtil.isNotEmpty(list)){
            itemsAndFzxService.saveBatch(list);
        }
        return R.ok();
    }




    /**
     * 重新计算已登记体检号的价格
     *
     * @param
     * @return
     */
    @PostMapping("/function/recalculateRegistrationPrice")
    @ApiOperation(value = "重新计算已登记体检号的价格", notes = "重新计算已登记体检号的价格")
    @ApiImplicitParam(name = "patientCodes", value = "体检号")
    public R recalculateRegistrationPrice(@RequestParam List<String> patientCodes) {
        Boolean b = orderService.recalculateRegistrationPrice(patientCodes);
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
     * 重新计算已登记体检号的价格
     *
     * @param
     * @return
     */
    @PostMapping("/function/intIdRepeated")
    @ApiOperation(value = "处理重复intId的", notes = "在这里更新似乎并不会同步下去，所以要提前查出来id，然后手动更新到线下")
    @ApiImplicitParam(name = "IndIds", value = "IndIds")
    public R intIdRepeated(@RequestBody List<String> IndIds) {
        Boolean b = sellcustomerService.intIdRepeated(IndIds);
        return R.ok(b);
    }





    /**
     * 重新计算已登记体检号的价格
     *
     * @param
     * @return
     */
    @PostMapping("/function/addAgainFeeItem")
    @ApiOperation(value = "重新添加收费项目", notes = "重新添加收费项目")
    public R addAgainFeeItem(@RequestBody List<String> patientCodes) {
        Boolean b = orderService.addAgainFeeItem(patientCodes);
        return R.ok(b);
    }



    /**
     * 根据数据更新体检者
     *
     * @param
     * @return
     */
    @PostMapping("/function/upPei")
    @ApiOperation(value = "根据数据更新体检者", notes = "根据数据更新体检者")
    public R upPei(@RequestBody Peispatient peispatient) {
        return R.ok(peispatientService.updateById(peispatient));
    }
}
