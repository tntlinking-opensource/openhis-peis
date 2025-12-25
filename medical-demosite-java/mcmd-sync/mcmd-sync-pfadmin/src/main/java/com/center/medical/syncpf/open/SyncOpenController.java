package com.center.medical.syncpf.open;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.PeispatientChargeMain;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.reception.bean.dto.PullOnlineDataDto;
import com.center.medical.service.PeispatientChargeMainService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.sync.bean.dto.SyncDataDto;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.sync.bean.model.SyncFile;
import com.center.medical.sync.bean.param.GenerateCodeParam;
import com.center.medical.sync.bean.param.SyncResult;
import com.center.medical.sync.service.SyncDataService;
import com.center.medical.sync.service.SyncFileService;
import com.center.medical.sync.service.SyncSqlRunService;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 同步数据操作(SyncData)表控制层（已弃用）
 *
 * @author 路飞船长
 * @since 2023-02-02 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "线上同步数据开放接口")
@RequestMapping("/open/api/syncData")
public class SyncOpenController extends BaseController {
    /**
     * 服务对象
     */
    private final SyncDataService syncDataService;
    private final ISysBranchService iSysBranchService;
    private final SyncSqlRunService syncSqlRunService;
    private final SyncFileService syncFileService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final PeispatientChargeMainService peispatientChargeMainService;
    private final PeispatientService peispatientService;

    /**
     * 即时下载数据
     *
     * @param cid 分中心ID
     */
    //@GetMapping("/download/{cid}")
    //@ApiOperation(value = "即时下载数据", notes = "即时下载数据")
    public R<Set<Object>> download(@PathVariable String cid) {
        SysBranch branch = iSysBranchService.getByBranchId(cid);
        if (Objects.isNull(branch)) {
            return R.fail("分中心【" + cid + "】不存在！");
        }
        //1. 获取需要同步的数据
//        log.info("分中心：{}", branch);
        Set<Object> set = RedisSetUtil.queryByRange(CacheConstants.SYNC_DATA_ON_OFF_LINE + "_" + branch.getJm(), 0L, 29L);
        log.info("即时数据同步下载数据set：{}", set.size());
        if (set.size() > 0) {
            //2.更新数据同步记录状态为1（同步中）
//            List<Long> syncDataIds = set.stream().map(item -> {
//                SyncDataDto dataDto = JSONUtil.toBean((String) item, SyncDataDto.class);
//                return dataDto.getSyncDataId();
//            }).collect(Collectors.toList());
//            SyncData syncData = new SyncData();
//            syncData.setStatus(1);
//            syncDataService.update(syncData, new LambdaUpdateWrapper<SyncData>().in(SyncData::getId, syncDataIds));
            //删除缓存中的数据
            RedisSetUtil.removeRange(CacheConstants.SYNC_DATA_ON_OFF_LINE + "_" + branch.getJm(), 0L, Long.valueOf((set.size() - 1) + ""));
        }
        //3.返回同步数据
        return R.ok(set);
    }


    /**
     * 闲时下载数据
     *
     * @param cid 分中心ID
     */
    //@GetMapping("/downloadDelay/{cid}")
    //@ApiOperation(value = "闲时下载数据", notes = "闲时下载数据")
    public R<Set<Object>> downloadDelay(@PathVariable String cid) {
        SysBranch branch = iSysBranchService.getByBranchId(cid);
        if (Objects.isNull(branch)) {
            return R.fail("分中心【" + cid + "】不存在！");
        }
        //1. 获取需要同步的数据
//        log.info("分中心：{}", branch);
        Set<Object> set = RedisSetUtil.queryByRange(CacheConstants.SYNC_DATA_ON_OFF_LINE_DELAY + "_" + branch.getJm(), null, 29L);
        log.info("闲时数据同步下载数据set：{}", set.size());
        if (set.size() > 0) {
            //2.更新数据同步记录状态为1（同步中）
//            List<Long> syncDataIds = set.stream().map(item -> {
//                SyncDataDto dataDto = JSONUtil.toBean((String) item, SyncDataDto.class);
//                return dataDto.getSyncDataId();
//            }).collect(Collectors.toList());
//            SyncData syncData = new SyncData();
//            syncData.setStatus(1);
//            syncDataService.update(syncData, new LambdaUpdateWrapper<SyncData>().in(SyncData::getId, syncDataIds));
            //删除缓存中的数据
            RedisSetUtil.removeRange(CacheConstants.SYNC_DATA_ON_OFF_LINE_DELAY + "_" + branch.getJm(), 0L, Long.valueOf((set.size() - 1) + ""));
        }


        //3.返回同步数据
        return R.ok(set);
    }

    /**
     * 下载数据同步结果
     */
    //@PostMapping("/result")
    //@ApiOperation(value = "下载数据同步结果", notes = "下载数据同步结果")
    public R<String> result(SyncResult result) {
        SyncData syncData = new SyncData();
        syncData.setId(result.getSyncDataId());
        syncData.setModifydate(new Date());
        if (result.getResult() == 1) {
            //同步成功
            syncData.setStatus(2);
//            syncDataService.updateById(syncData);
            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_DONE_SUCCESS_KEY, syncData, DateUtil.currentSeconds());
        } else {
            //同步失败，待同步
            syncData.setStatus(3);
//            syncDataService.updateById(syncData);
            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_DONE_FAIL_KEY, syncData, DateUtil.currentSeconds());
        }
        //log.warn("successData2:{}", (Object) RedisUtil.get(CacheConstants.SYNC_DATA_DONE_KEY + id));
        RedisUtil.delByKeys(Arrays.asList(CacheConstants.SYNC_DATA_UNDO_KEY + syncData.getId()));
        return R.ok("已收到！");
    }

    /**
     * 下载同步文件
     *
     * @param cid 分中心ID
     */
    //@GetMapping("/downloadFile/{cid}")
    //@ApiOperation(value = "下载同步文件", notes = "下载同步文件")
    public R<List<SyncFile>> downloadFile(@PathVariable String cid) {
        SysBranch branch = iSysBranchService.getByBranchId(cid);
        if (Objects.isNull(branch)) {
            return R.fail("分中心【" + cid + "】不存在！");
        }
        //获取待上传的文件
//        List<SyncFile> list = syncFileService.list(new LambdaQueryWrapper<SyncFile>()
//                .in(SyncFile::getStatus, Arrays.asList(0, 3))
//                .eq(SyncFile::getBranchIds, cid)
//                .orderByAsc(SyncFile::getCreatedate));
        Set<Object> set = RedisSetUtil.queryByRange(CacheConstants.SYNC_FILE_ON_OFF_LINE + "_" + branch.getJm(), 0L, 19L);
        if (set.size() == 0) {
            return R.ok();
        }
        List<SyncFile> list = new ArrayList<>();
        for (Object item : set) {
            list.add((SyncFile) item);
        }
        RedisSetUtil.removeRange(CacheConstants.SYNC_FILE_ON_OFF_LINE + "_" + branch.getJm(), 0L, Long.valueOf((set.size() - 1) + ""));
        //返回同步文件
        return R.ok(list);
    }

    /**
     * 下载同步文件结果
     */
    //@PostMapping("/downloadFile/result")
    //@ApiOperation(value = "下载同步文件结果", notes = "下载同步文件结果")
    public R<String> downloadFileResult(SyncResult result) {
        SyncFile syncFile = new SyncFile();
        syncFile.setId(result.getSyncDataId());
        syncFile.setModifydate(new Date());
        if (result.getResult() == 1) {
            //同步成功
            syncFile.setStatus(2);
//            syncFileService.updateById(syncFile);
        } else {
            //同步失败，待同步
            syncFile.setStatus(3);
//            syncFileService.updateById(syncFile);
        }
        RedisSetUtil.addToSortedSet(CacheConstants.SYNC_FILE_DONE_KEY, syncFile, DateUtil.currentSeconds());
        RedisUtil.del(CacheConstants.SYNC_FILE_UNDO_KEY + syncFile.getId());
        return R.ok("已收到！");
    }

    /**
     * 接收线下系统推送的同步数据
     *
     * @param cid              分中心ID
     * @param syncDataListJson 同步数据
     */
    //@PostMapping("/receive")
    //@ApiOperation(value = "接收同步数据", notes = "接收线下系统推送的同步数据")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "cid", value = ""),
//            @ApiImplicitParam(name = "syncDataList", value = "syncDataList")
//    })
    public R receive(@RequestParam("cid") String cid, @RequestParam("syncDataList") String syncDataListJson) throws UnsupportedEncodingException {
        log.info("接收线下系统推送的同步数据:{}", cid);
        String decode = URLDecoder.decode(syncDataListJson, "UTF-8");
//        log.info("接收线下系统推送的同步数据decode:{}", decode);
        JSONArray jsonArray = JSONUtil.parseArray(decode);
        List<SyncDataDto> syncDataList = jsonArray.toList(SyncDataDto.class);
//        return R.fail(syncDataList);

        if (CollectionUtil.isEmpty(syncDataList)) {
            R.fail("同步失败，数据不能为空！");
        }
        SysBranch branch = iSysBranchService.getByBranchId(cid);
        if (Objects.isNull(branch)) {
            R.fail("同步失败，该分中心不存在或者已经禁用！");
        }
        List<Long> success = new ArrayList<>();
        List<Long> fail = new ArrayList<>();
        if (syncDataList.size() > 0) {
            // 2.执行数据更新
            for (SyncDataDto item : syncDataList) {
                // log.info("接收线下系统推送的同步数据item：{}", item);
                try {
                    if (item.getOptType() == 2 || item.getOptType() == 3) {
                        // 新增或者更新操作
                        Map<String, Object> sqlData = JSONUtil.toBean(item.getSyncRunSql(), Map.class);
                        syncSqlRunService.updateOrInsert(item.getBizTable(), sqlData);
                    } else {
                        // 删除操作
                        syncSqlRunService.updateSql(item.getSyncRunSql());
                    }
                    success.add(item.getSyncDataId());
                } catch (Exception e) {
                    log.error("数据同步失败SyncOpenController.receive:{}、{}", item, e);
                    fail.add(item.getSyncDataId());
                }
            }
        }

        Map<String, List<Long>> result = new HashMap<>();
        result.put("success", success);
        result.put("fail", fail);
        //3.返回同步数据
        return R.ok(result, "同步成功！");
    }


    /**
     * 定时任务生成体检号
     *
     * @param param
     * @return
     */
    @PostMapping("/generateCode")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "定时任务生成体检号", notes = "定时任务生成体检号")
    public List<String> generateCode(@RequestBody GenerateCodeParam param) {
        return syncDataService.generateCode(param);
    }




    /**
     * 定时任务生成体检号
     *
     * @param param
     * @return
     */
    @PostMapping("/generateArchiveCode")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "定时任务生成档案号", notes = "定时任务生成档案号")
    public List<String> generateArchiveCode(@RequestBody GenerateCodeParam param) {
        return syncDataService.generateArchiveCode(param);
    }


    /**
     * 定时任务生成体检号
     *
     * @param param
     * @return
     */
    @GetMapping("/pullOnlineData")
    @ApiOperation(value = "线上获取体检者数据", notes = "线上获取体检者数据")
    public R<PullOnlineDataDto> pullOnlineData(String patientcode) {
        PullOnlineDataDto dto = new PullOnlineDataDto();
        List<Peispatientfeeitem> peispatientfeeitemList = peispatientfeeitemService.list(new LambdaQueryWrapper<Peispatientfeeitem>().eq(Peispatientfeeitem::getIdPatient, patientcode));
        dto.setPeispatientfeeitemList(peispatientfeeitemList);
        PeispatientChargeMain peispatientChargeMain = peispatientChargeMainService.getOne(new LambdaQueryWrapper<PeispatientChargeMain>().eq(PeispatientChargeMain::getPatientcode, patientcode));
        dto.setPeispatientChargeMain(peispatientChargeMain);
        return R.ok(dto);
    }


    /**
     * 定时任务生成体检号
     *
     * @param param
     * @return
     */
    @GetMapping("/otherIsRegistered")
    @ApiOperation(value = "查询别的区是否登记", notes = "查询别的区是否登记")
    public R<Boolean> otherIsRegistered(String id,String fzx) {
        Peispatient peispatient = peispatientService.getInfoById(id);
        //不存在可能是直接线下加的或者没同步到线上
        if (ObjectUtils.isNotEmpty(peispatient)){
            if (ObjectUtils.isNotEmpty(peispatient.getFRegistered()) && peispatient.getFRegistered() == 1){
                if (StringUtils.isNotEmpty(fzx) && fzx.equals(peispatient.getHospitalcode())){
                    return R.ok(Boolean.TRUE);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                throw new ServiceException("体检者于"+sdf.format(peispatient.getDateregister()) +"在"
                        + iSysBranchService.getByBranchId(peispatient.getHospitalcode()).getFzx() +"已登记");
            }
        }
        return R.ok(Boolean.TRUE);
    }
}

