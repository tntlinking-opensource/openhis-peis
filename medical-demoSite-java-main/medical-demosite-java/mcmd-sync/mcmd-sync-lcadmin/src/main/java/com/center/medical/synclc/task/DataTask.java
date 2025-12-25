package com.center.medical.synclc.task;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.bean.dto.SyncFileCheckDto;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.config.Domain;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.message.constant.MessageConstant;
import com.center.medical.message.service.LocalProducer;
import com.center.medical.service.AttachmentService;
import com.center.medical.sync.bean.dto.SyncDataDto;
import com.center.medical.sync.service.SyncOfflineService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: 路飞
 * @date: 2023-02-07 10:46
 * @description: 基础数据同步定时任务
 */
@Slf4j
@Component("syncDataTask")
@DisallowConcurrentExecution
public class DataTask {

    @Resource
    private SyncOfflineService syncOfflineService;
    @Resource
    private LocalProducer localProducer;
    @Resource
    private ISysBranchService iSysBranchService;
    @Resource
    private ISysConfigService iSysConfigService;
    @Resource
    private AttachmentService attachmentService;
//    private final ExecutorService executorService = Executors.newFixedThreadPool(8);


    public static boolean areFilesSameSize(String url1, String url2) {
        try {
            URLConnection connection1 = new URL(url1).openConnection();
            long size1 = connection1.getContentLengthLong();
            System.out.println("size1=" + size1);

            URLConnection connection2 = new URL(url2).openConnection();
            long size2 = connection2.getContentLengthLong();
            System.out.println("size2=" + size2);

            return size1 == size2;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        DateTime d = DateUtil.parse("2024-04-10 12:24:00");

        String url1 = "https://XXX.XXX.XXX.XXXm/newimage/files/material/01/20240408/6d48c19e17ae43118e58173ab55ac02b.pdf";
        String url2 = "http://XXX.XXX.XXX.XXX:8080/newimage/files/material/01/20240408/6d48c19e17ae43118e58173ab55ac02b.pdf";

        boolean sameSize = areFilesSameSize(url1, url2);
        System.out.println("是否大小相等： " + sameSize);
    }


    /**
     * 将SyncDataObjects缓存中的SyncData执行同步
     *
     * @return
     */
    public void syncDataToDB(Long endNum, Integer duringTime) {
        //log.info("syncDataToDB定时任务开始执行:endNum={}", endNum);
        Date start = new Date();
        DateTime offset = DateUtil.offset(start, DateField.SECOND, duringTime);
        //判断是否允许IP执行
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(4L, ips)) {
            Date startTime = new Date();
            if (Objects.isNull(endNum) || endNum == 0) {
                endNum = 99L;
            }
            int totalSize = 0;
            //循环执行10次操作
            do {
                Date now1 = new Date();
                if (now1.after(offset)) {
                    log.info("将缓存中的SyncData同步至数据库中定时任务，提前结束，超时了");
                    break;
                }
                Set<Object> set = RedisSetUtil.queryByRange(CacheConstants.SYNC_DATA_OBJECTS, null, endNum);
//            log.info("数据同步内容：{}、{}", set, set.size());
                if (set.size() == 0) {
                    break;
                }
                totalSize = totalSize + set.size();
                syncOfflineService.getSyncContent(set);
//                try {
//                    Thread.sleep(50);             // 等待指定的时间
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            } while (1 == 1);
            RedisUtil.set(CacheConstants.SYNC_DATA_TO_DB_TASK_RUNNING, 0, 0);
            Object count = RedisUtil.get("syncDataToDB:" + DateUtil.format(new Date(), "yyyy-MM-dd"));
            Integer todayTotal = 0;
            if (Objects.nonNull(count)) {
                todayTotal = (Integer) count + totalSize;
            } else {
                todayTotal = totalSize;
            }
            RedisUtil.set("syncDataToDB:" + DateUtil.format(new Date(), "yyyy-MM-dd"), todayTotal, 0);

            Date endTime = new Date();
            log.info("将SyncDataObjects缓存中的SyncData执行同步{}-{}：本次共执行了{}条，今天共执行了{}条,用时{}S", DateUtil.format(startTime, "yyyy-MM-dd HH:mm:ss"), DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"), totalSize, todayTotal, DateUtil.between(startTime, endTime, DateUnit.SECOND));
        }
    }

    /**
     * 检验文件是否成功同步
     *
     * @return
     */
    public void syncFileCheck(Long endNum, Integer duringTime) {
        log.info("同步-检验文件是否成功同步-定时任务：syncDataTask.syncFileCheck");
        Date start = new Date();
        DateTime offset = DateUtil.offset(start, DateField.MINUTE, duringTime);
        //判断是否允许IP执行
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(23L, ips)) {
            Set<Object> set = RedisSetUtil.queryByRange(CacheConstants.SYNC_FILE_CHECK, null, endNum == 0L ? null : endNum);
//            log.info("同步-检验文件是否成功同步-数据set：{}", set);
            if (set.size() == 0) {
                return;
            }
            Domain domain = iSysConfigService.getDomain();
            for (Object o : set) {
                Date now1 = new Date();
                if (now1.after(offset)) {
                    log.info("定时发送短信定时任务，提前结束，超时了");
                    break;
                }
                SyncFileCheckDto item = JSONUtil.toBean(JSONUtil.toJsonStr(o), SyncFileCheckDto.class);
                Integer flag = item.getToFlag();

                if (StringUtils.isNotBlank(item.getFileUrl())) {
                    //只判断5分钟前的记录
//                    if (DateUtil.betweenMs(item.getUpdateDate(), new Date()) > 300000) {
                        log.info("进入线程池：{}", item.getPatientcode());
                        //进入线程池
//                        executorService.submit(() -> {
                        //执行保存操作，线程池设置了一个核心线程和最大线程都为1的线程池，空闲时间为1200秒。如果在1200秒内没有任务提交，线程池会自动释放线程资源。当有新任务提交时，线程池会重新启动线程来处理任务。
                        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 8, 1200, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
                        // 提交任务到线程池
                        executor.submit(() -> {
                            //判断文件是否已经上传过
                            String host = "";
                            if (flag == 0) {
                                host = domain.getRsPfDomain();
                            } else {
                                host = domain.getRsLcDomain();
                            }
                            String path = item.getFileUrl();
                            path = path.startsWith("/") ? path : StrUtil.subAfter(path, "/", false);
                            if (!areFilesSameSize(domain.getRsPfDomain() + path, domain.getRsLcDomain() + path)) {
                                try {
                                    //执行文件上传
                                    attachmentService.uploadSyncFile(host, path, flag);
                                    log.info("同步-检验文件是否成功同步-上传成功：{}、{}", item);
                                } catch (IOException e) {
                                    log.error("同步-检验文件是否成功同步-上传失败：{}、{}", e, item);
                                    if (item.getCount() < 5) {
                                        //小于5次就再给一次机会
                                        item.setCount(item.getCount() + 1);
                                        RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_CHECK, item, DateUtil.currentSeconds());
                                    }
                                }
                            } else {
                                log.error("同步-检验文件是否成功同步-已同步成功了，无需再次上传：{}", item);
                            }

                        });
                        //删除redis记录
                        RedisSetUtil.remove(CacheConstants.SYNC_FILE_CHECK, o);

//                    }
                }

            }

        }


//        Date startTime = new Date();
//
//        Domain domain = iSysConfigService.getDomain();
//        // log.info("domain:{}", domain);
//        String host = domain.getRsLcDomain();
//        //获取待上传的文件
//        // List<SyncFile> list = syncFileService.list(new LambdaQueryWrapper<SyncFile>().in(SyncFile::getStatus, Arrays.asList(0, 3)).orderByAsc(SyncFile::getCreatedate));
//        int count = 0;
//        int totalSize = 0;
//        //循环执行10次上传操作
//        do {
//            // log.info("同步-上传文件-定时任务count:{}", count);
//            //获取数据
//            Set<Object> set = RedisSetUtil.queryByRange(CacheConstants.SYNC_FILE_ON_OFF_LINE, 0L, 19L);
//            // log.info("同步-上传文件-数据set：{}", set);
//            if (set.size() == 0) {
//                break;
//            }
//            totalSize = totalSize + set.size();
//
//            set.forEach(o -> {
//                SyncFile item = (SyncFile) o;
//                try {
//                    item.setStatus(1);
//                    syncFileService.updateById(item);
//                    if (attachmentService.uploadSyncFile(host, item.getImageUrl(), 1)) {
//                        item.setStatus(2);
//                        // syncFileService.updateById(item);
//                    } else {
//                        item.setStatus(3);
//                    }
//                } catch (IOException e) {
//                    item.setStatus(3);
//                    // syncFileService.updateById(item);
//                    // throw new RuntimeException(e);
//                }
//                item.setModifydate(new Date());
//                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_DONE_KEY, item, DateUtil.currentSeconds());
//                RedisUtil.del(CacheConstants.SYNC_FILE_UNDO_KEY + item.getId());
//                RedisSetUtil.removeRange(CacheConstants.SYNC_FILE_ON_OFF_LINE, 0L, 0L);
//            });
//            try {
//                Thread.sleep(1000);             // 等待指定的时间
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            count++;
//        } while (count < 5);
//        Date endTime = new Date();
//        log.info("同步-上传文件-执行了({}-{})：" + count + "次，共" + totalSize + "条，用时{}S", startTime, endTime, count, totalSize, DateUtil.between(startTime, endTime, DateUnit.SECOND));

    }

    /**
     * 即时从线上下载同步数据至线下定时任务
     *
     * @return
     */
//    public void downloadData() {
////        log.info("即时同步-下载数据-定时任务：syncDataTask.downloadData");
////        Date startTime = new Date();
////        Domain domain = iSysConfigService.getDomain();
////        String pfDomain = domain.getPlatformDomain();
////        pfDomain = "http://127.0.0.1:8090";
////        SysBranch branch = iSysBranchService.getDefaultBranch();
////        //log.info("线上接口地址：{}", pfDomain + Constants.ONLINE_SYNC_DATA_DOWNLOAD + "/" + branch.getBranchId());
////        int count = 0;
////        int totalSize = 0;
////        //循环执行10次上传操作
////        do {
////            String result = HttpUtil.get(pfDomain + Constants.ONLINE_SYNC_DATA_DOWNLOAD + "/" + branch.getBranchId());
////            //log.info("同步-下载数据-内容result：{}", result);
////            R<JSONArray> r = JSONUtil.toBean(result, R.class);
//////            log.info("同步-下载数据-内容r：{}", r);
////            if (r.getCode() == 200 && CollectionUtil.isNotEmpty(r.getData())) {
////                List<JSONObject> list = JSONUtil.toList(r.getData(), JSONObject.class);
////                // log.info("同步-下载数据-内容list：{}、{}", result, list);
////                if (list.size() > 0) {
////                    List<SyncDataDto> syncDataDtoList = list.stream().map(item -> JSONUtil.toBean(item, SyncDataDto.class)).collect(Collectors.toList());
//////                    log.info("同步-下载数据-内容syncDataDtoList.size：{}", syncDataDtoList.size());
////                    if (syncDataDtoList.size() > 0) {
////                        totalSize = totalSize + list.size();
////                        //2. 执行数据更新
////                        for (SyncDataDto item : syncDataDtoList) {
////                            // log.info("同步-下载数据-内容item：{}", item);
////                            if (item.getOptType() == 2 || item.getOptType() == 3) {
////                                //新增或者更新操作
////                                Map<String, Object> sqlData = JSONUtil.toBean(item.getSyncRunSql(), Map.class);
////                                syncSqlRunService.updateOrInsert(item.getBizTable(), sqlData);
////                            } else {
////                                //删除操作
////                                syncSqlRunService.updateSql(item.getSyncRunSql());
////                            }
////
////                            //3. 返回执行的结果
////                            Map<String, Object> params = new HashMap<>();
////                            params.put("syncDataId", item.getSyncDataId());
////                            params.put("result", 1);
////                            String post = HttpUtil.post(pfDomain + Constants.ONLINE_SYNC_DATA_RESULT, params);
////                            // log.info("返回执行的结果:{}", post);
////                        }
////                    } else {
////                        break;
////                    }
////                } else {
////                    break;
////                }
////            } else if (CollectionUtil.isEmpty(r.getData())) {
////                break;
////            }
////
////            try {
////                Thread.sleep(1000);             // 等待指定的时间
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////            count++;
////        } while (count < 10);
////        Date endTime = new Date();
////        log.info("实时同步-下载数据-执行了({}-{})：" + count + "次，共" + totalSize + "条，用时{}S", startTime, endTime, count, totalSize, DateUtil.between(startTime, endTime, DateUnit.SECOND));
//    }

    /**
     * 闲时从线上下载同步数据至线下定时任务
     *
     * @return
     */
//    public void downloadDelayData() {
////        log.info("闲时同步-下载数据-定时任务：syncDataTask.downloadData");
////        Date startTime = new Date();
////        Domain domain = iSysConfigService.getDomain();
////        String pfDomain = domain.getPlatformDomain();
////        pfDomain = "http://127.0.0.1:8090";
////        SysBranch branch = iSysBranchService.getDefaultBranch();
////        //log.info("线上接口地址：{}", pfDomain + Constants.ONLINE_SYNC_DATA_DOWNLOAD + "/" + branch.getBranchId());
////        int count = 0;
////        int totalSize = 0;
////        //循环执行10次上传操作
////        do {
////            String result = HttpUtil.get(pfDomain + Constants.ONLINE_SYNC_DATA_DOWNLOAD_DELAY + "/" + branch.getBranchId());
////            //log.info("同步-下载数据-内容result：{}", result);
////            R<JSONArray> r = JSONUtil.toBean(result, R.class);
////            // log.info("同步-下载数据-内容r：{}", r);
////            if (r.getCode() == 200 && CollectionUtil.isNotEmpty(r.getData())) {
////                List<JSONObject> list = JSONUtil.toList(r.getData(), JSONObject.class);
////                // log.info("同步-下载数据-内容list：{}、{}", result, list);
////                if (list.size() > 0) {
////                    totalSize = totalSize + list.size();
////                    List<SyncDataDto> syncDataDtoList = list.stream().map(item -> JSONUtil.toBean(item, SyncDataDto.class)).collect(Collectors.toList());
////                    if (syncDataDtoList.size() > 0) {
////                        //2. 执行数据更新
////                        for (SyncDataDto item : syncDataDtoList) {
////                            // log.info("同步-下载数据-内容item：{}", item);
////                            if (item.getOptType() == 2 || item.getOptType() == 3) {
////                                //新增或者更新操作
////                                Map<String, Object> sqlData = JSONUtil.toBean(item.getSyncRunSql(), Map.class);
////                                syncSqlRunService.updateOrInsert(item.getBizTable(), sqlData);
////                            } else {
////                                //删除操作
////                                syncSqlRunService.updateSql(item.getSyncRunSql());
////                            }
////
////                            //3. 返回执行的结果
////                            Map<String, Object> params = new HashMap<>();
////                            params.put("syncDataId", item.getSyncDataId());
////                            params.put("result", 1);
////                            String post = HttpUtil.post(pfDomain + Constants.ONLINE_SYNC_DATA_RESULT, params);
////                            // log.info("返回执行的结果:{}", post);
////                        }
////                    } else {
////                        break;
////                    }
////                } else {
////                    break;
////                }
////            } else if (CollectionUtil.isEmpty(r.getData())) {
////                break;
////            }
////
////            try {
////                Thread.sleep(1000);             // 等待指定的时间
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////            count++;
////        } while (count < 10);
////        Date endTime = new Date();
////        log.info("闲时同步-下载数据-执行了({}-{})：" + count + "次，共" + totalSize + "条，用时{}S", startTime, endTime, count, totalSize, DateUtil.between(startTime, endTime, DateUnit.SECOND));
//    }


    /**
     * 即时从线下推送同步数据至线上定时任务
     *
     * @return
     */
//    public void uploadData(Long endNum) throws UnsupportedEncodingException {
////        Date startTime = new Date();
////        if (Objects.isNull(endNum) || endNum == 0) {
////            endNum = 19L;
////        }
//////        log.info("即时同步-上传数据-定时任务：syncDataTask.uploadData：{}", RedisSetUtil.count(CacheConstants.SYNC_DATA_ON_OFF_LINE));
////        Domain domain = iSysConfigService.getDomain();
////        String pfDomain = domain.getPlatformDomain();
////        pfDomain = "http://127.0.0.1:8090";
////        SysBranch branch = iSysBranchService.getDefaultBranch();
////
////        int count = 0;
////        int totalSize = 0;
////        //循环执行100次上传操作
////        do {
////            // log.info("同步-上传数据-定时任务count:{}", count);
////            //获取数据
////            Set<Object> set = RedisSetUtil.queryByRange(CacheConstants.SYNC_DATA_ON_OFF_LINE, 0L, endNum);
////            // log.info("同步-上传数据-数据set：{}", set);
////            if (set.size() == 0) {
////                break;
////            }
////            totalSize = totalSize + set.size();
////
////            //2.更新数据同步记录状态为1（同步中）
////            //List<Long> syncDataIds = new ArrayList<>();
////            List<SyncDataDto> syncDataList = set.stream().map(item -> {
////                SyncDataDto dataDto = JSONUtil.toBean((String) item, SyncDataDto.class);
////                //syncDataIds.add(dataDto.getSyncDataId());
////                dataDto.setSyncRunSql(JSONUtil.toJsonStr(dataDto.getSyncRunSql()));
////                return dataDto;
////            }).collect(Collectors.toList());
////
////            //SyncData syncData = new SyncData();
////            //syncData.setStatus(1);
////            //syncDataService.update(syncData, new LambdaUpdateWrapper<SyncData>().in(SyncData::getId, syncDataIds));
////
////            Map<String, Object> syncUpParam = new HashMap<>();
////            syncUpParam.put("cid", branch.getBranchId());
////            syncUpParam.put("syncDataList", URLEncoder.encode(JSONUtil.toJsonStr(syncDataList), "UTF-8"));
//////            log.info("3线上接口地址：{}、{}", pfDomain + Constants.ONLINE_SYNC_DATA_RECEIVE, syncUpParam);
////            String result = HttpUtil.post(pfDomain + Constants.ONLINE_SYNC_DATA_RECEIVE, syncUpParam);
////            //处理推送结果
//////            log.info("同步-上传数据-结果result：{}", result);
////            R<JSONObject> r = JSONUtil.toBean(result, R.class);
////            // log.info("同步-上传数据-结果r：{}", r);
////            if (r.getCode() == 200) {
////                JSONObject data = r.getData();
////                Map resultMap = JSONUtil.toBean(data, Map.class);
////                // log.info("6推送同步数据至线上结果：{}、{}", data, resultMap);
////                List<Long> success = JSONUtil.toList(JSONUtil.toJsonStr(resultMap.get("success")), Long.class);
////                List<Long> fail = JSONUtil.toList(JSONUtil.toJsonStr(resultMap.get("fail")), Long.class);
////                log.info("实时推送同步数据至线上({})结果：成功{}条、失败{}条", count, success.size(), fail.size());
////
////                //1.更新数据同步记录状态为1（同步中）
////                if (CollectionUtil.isNotEmpty(success)) {
////                    for (Long id : success) {
////                        SyncData successData = RedisUtil.get(CacheConstants.SYNC_DATA_UNDO_KEY + id);
////
////                        if (Objects.nonNull(successData)) {
////                            // log.warn("successData1:{}", JSONUtil.toJsonStr(successData));
////                            successData.setStatus(2);
////                            successData.setModifydate(new Date());
////                            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_DONE_SUCCESS_KEY, successData, DateUtil.currentSeconds());
////                            //log.warn("successData2:{}", (Object) RedisUtil.get(CacheConstants.SYNC_DATA_DONE_KEY + id));
////                            RedisUtil.delByKeys(Arrays.asList(CacheConstants.SYNC_DATA_UNDO_KEY + id));
////                        }
////
////                    }
////
////                    // SyncData successData = new SyncData();
////                    // successData.setStatus(2);
////                    // syncDataService.update(successData, new LambdaUpdateWrapper<SyncData>().in(SyncData::getId, success));
////                }
////
////                if (CollectionUtil.isNotEmpty(fail)) {
////                    for (Long id : fail) {
////                        SyncData failData = RedisUtil.get(CacheConstants.SYNC_DATA_UNDO_KEY + id);
////                        if (Objects.nonNull(failData)) {
////                            failData.setStatus(3);
////                            failData.setModifydate(new Date());
////                            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_DONE_FAIL_KEY, failData, DateUtil.currentSeconds());
////                            RedisUtil.delByKeys(Arrays.asList(CacheConstants.SYNC_DATA_UNDO_KEY + id));
////                        } else {
////                            for (SyncDataDto ii : syncDataList) {
////                                if (ii.getSyncDataId().equals(id)) {
////                                    log.error("同步失败对象{}", JSONUtil.toJsonStr(ii));
////                                }
////                            }
////                        }
////                    }
////                    // SyncData failData = new SyncData();
////                    // failData.setStatus(3);
////                    // syncDataService.update(failData, new LambdaUpdateWrapper<SyncData>().in(SyncData::getId, fail));
////                }
////
////                //2.删除缓存中的数据
////                RedisSetUtil.removeRange(CacheConstants.SYNC_DATA_ON_OFF_LINE, 0L, Long.valueOf((set.size() - 1) + ""));
////            }
////            try {
////                Thread.sleep(50);             // 等待指定的时间
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////            count++;
////        } while (count < 250);
////        Date endTime = new Date();
////        log.info("实时同步-上传数据-执行了({}-{})：" + count + "次，共" + totalSize + "条，用时{}S", startTime, endTime, count, totalSize, DateUtil.between(startTime, endTime, DateUnit.SECOND));
//
//    }


    /**
     * 闲时从线下推送同步数据至线上定时任务
     *
     * @return
     */
    public void uploadDataDelay(Long endNum, Integer duringTime) {
        log.info("闲时同步-上传数据-定时任务：syncDataTask.uploadData");
        Date start = new Date();
        DateTime offset = DateUtil.offset(start, DateField.SECOND, duringTime);
        //判断是否允许IP执行
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(18L, ips)) {

            Date startTime = new Date();
            if (Objects.isNull(endNum) || endNum == 0) {
                endNum = 19L;
            }
            SysBranch branch = iSysBranchService.getDefaultBranch();
            int count = 0;
            int totalSize = 0;
            //循环执行100次上传操作
            do {
                Date now1 = new Date();
                if (now1.after(offset)) {
                    log.info("闲时从线下推送同步数据至线上定时任务，提前结束，超时了");
                    break;
                }
                // log.info("同步-上传数据-定时任务count:{}", count);
                //获取数据
                Set<Object> set = RedisSetUtil.queryByRange(CacheConstants.SYNC_DATA_ON_OFF_LINE_DELAY, 0L, endNum);
//            log.info("同步-上传数据-数据set：{}", set);
                if (set.size() == 0) {
                    break;
                }
                totalSize = totalSize + set.size();
                set.forEach(item -> {
                    SyncDataDto dataDto = JSONUtil.toBean((String) item, SyncDataDto.class);
                    dataDto.setFxzId(branch.getBranchId());
                    localProducer.sendMessage(MessageConstant.LC_SYNC_DATA_DELAY_TOPIC_EXCHANGE, MessageConstant.LC_SYNC_DATA_DELAY_ROUTING_KEY, JSONUtil.toJsonStr(dataDto));
                });

                try {
                    Thread.sleep(10);             // 等待指定的时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //2.删除缓存中的数据
                RedisSetUtil.removeRange(CacheConstants.SYNC_DATA_ON_OFF_LINE_DELAY, 0L, Long.valueOf((set.size() - 1) + ""));
                log.info("闲时同步-上传数据，count={}，size={}", count++, set.size());
            } while (1 == 1);
//        RedisUtil.set(CacheConstants.UPLOAD_DATA_DELAY_TASK_RUNNING, 0, 0);

            Object all = RedisUtil.get("uploadDataDelayCount:" + DateUtil.format(new Date(), "yyyy-MM-dd"));
            Integer todayTotal = 0;
            if (Objects.nonNull(all)) {
                todayTotal = (Integer) all + totalSize;
            } else {
                todayTotal = totalSize;
            }
            RedisUtil.set("uploadDataDelayCount:" + DateUtil.format(new Date(), "yyyy-MM-dd"), todayTotal, 0);
            Date endTime = new Date();
            log.info("闲时同步-上传数据-({}-{})：共同步了" + totalSize + "条，用时{}S", DateUtil.format(startTime, "yyyy-MM-dd HH:mm:ss"), DateUtil.format(endTime, "yyyy-MM-dd HH:mm:ss"), totalSize, DateUtil.between(startTime, endTime, DateUnit.SECOND));
        }
    }

    /**
     * 从线上下载同步文件至线下定时任务
     *
     * @return
     */
//    public void downloadImages() {
////        log.info("同步-下载文件-定时任务：syncDataTask.downloadImages");
////        Domain domain = iSysConfigService.getDomain();
//////        String pfDomain = domain.getPlatformDomain();
////        String pfDomain = "http://127.0.0.1:8090";
////        SysBranch branch = iSysBranchService.getDefaultBranch();
////        // log.info("线上接口地址：{}", pfDomain + Constants.ONLINE_SYNC_FILE_DOWNLOAD + "/" + branch.getBranchId());
////        String result = HttpUtil.get(pfDomain + Constants.ONLINE_SYNC_FILE_DOWNLOAD + "/" + branch.getBranchId());
////        // log.info("同步-下载文件-内容result：{}", result);
////        R<JSONArray> r = JSONUtil.toBean(result, R.class);
////        // log.info("同步-下载文件-内容r：{}", r);
////        if (r.getCode() == 200 && CollectionUtil.isNotEmpty(r.getData())) {
////            List<SyncFile> list = JSONUtil.toList(r.getData(), SyncFile.class);
////            // log.info("同步-下载文件-内容list：{}、{}", result, list);
////            if (list.size() > 0) {
////                String host = domain.getRsPfDomain();
////                list.forEach(item -> {
////                    try {
////                        if (attachmentService.uploadSyncFile(host, item.getImageUrl(), 0)) {
////                            //3. 返回执行的结果
////                            Map<String, Object> params = new HashMap<>();
////                            params.put("syncDataId", item.getId());
////                            params.put("result", 1);
////                            String post = HttpUtil.post(pfDomain + Constants.ONLINE_SYNC_FILE_RESULT, params);
////                            log.info("返回执行的结果:{}", post);
////                        } else {
////
////                        }
////                    } catch (IOException e) {
////                        //3. 返回执行的结果
////                        Map<String, Object> params = new HashMap<>();
////                        params.put("syncDataId", item.getId());
////                        params.put("result", 0);
////                        params.put("result", 0);
////                        params.put("result", branch.getBranchId());
////                        String post = HttpUtil.post(pfDomain + Constants.ONLINE_SYNC_FILE_RESULT, params);
////                        log.info("返回执行的结果:{}", post);
////                        throw new RuntimeException(e);
////                    }
////
////                });
////
////            }
////
////        }
//
//    }


    /**
     * 从线下推送同步文件至线上定时任务
     *
     * @return
     */
//    public void uploadImages() {
//        log.info("同步-上传文件-定时任务：syncDataTask.uploadImages");
//        Date startTime = new Date();
//
//        Domain domain = iSysConfigService.getDomain();
//        // log.info("domain:{}", domain);
//        String host = domain.getRsLcDomain();
//        //获取待上传的文件
//        // List<SyncFile> list = syncFileService.list(new LambdaQueryWrapper<SyncFile>().in(SyncFile::getStatus, Arrays.asList(0, 3)).orderByAsc(SyncFile::getCreatedate));
//        int count = 0;
//        int totalSize = 0;
//        //循环执行10次上传操作
//        do {
//            // log.info("同步-上传文件-定时任务count:{}", count);
//            //获取数据
//            Set<Object> set = RedisSetUtil.queryByRange(CacheConstants.SYNC_FILE_ON_OFF_LINE, 0L, 19L);
//            // log.info("同步-上传文件-数据set：{}", set);
//            if (set.size() == 0) {
//                break;
//            }
//            totalSize = totalSize + set.size();
//
//            set.forEach(o -> {
//                SyncFile item = (SyncFile) o;
//                try {
//                    item.setStatus(1);
//                    syncFileService.updateById(item);
//                    if (attachmentService.uploadSyncFile(host, item.getImageUrl(), 1)) {
//                        item.setStatus(2);
//                        // syncFileService.updateById(item);
//                    } else {
//                        item.setStatus(3);
//                    }
//                } catch (IOException e) {
//                    item.setStatus(3);
//                    // syncFileService.updateById(item);
//                    // throw new RuntimeException(e);
//                }
//                item.setModifydate(new Date());
//                RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_DONE_KEY, item, DateUtil.currentSeconds());
//                RedisUtil.del(CacheConstants.SYNC_FILE_UNDO_KEY + item.getId());
//                RedisSetUtil.removeRange(CacheConstants.SYNC_FILE_ON_OFF_LINE, 0L, 0L);
//            });
//            try {
//                Thread.sleep(1000);             // 等待指定的时间
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            count++;
//        } while (count < 5);
//        Date endTime = new Date();
//        log.info("同步-上传文件-执行了({}-{})：" + count + "次，共" + totalSize + "条，用时{}S", startTime, endTime, count, totalSize, DateUtil.between(startTime, endTime, DateUnit.SECOND));

//    }
}
