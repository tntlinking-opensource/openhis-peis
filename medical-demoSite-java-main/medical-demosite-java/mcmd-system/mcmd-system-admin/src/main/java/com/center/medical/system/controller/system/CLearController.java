package com.center.medical.system.controller.system;

import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.sync.bean.model.SyncData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 同步数据操作(SyncData)表控制层
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:58
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "清除操作")
@RequestMapping("system/clear")
public class CLearController extends BaseController {

    /**
     * 删除SYNC_DATA_OBJECTS异常数据
     *
     * @param count 数量
     * @return 单条数据
     */
    @GetMapping("/judge/{count}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "判断SYNC_DATA_OBJECTS", notes = "删除SYNC_DATA_OBJECTS异常数据")
    @ApiImplicitParam(name = "count", value = "数量")
    public R<String> judge(@PathVariable Long count) {
        Integer success = 0;
        Integer fail = 0;
        for (int i = 0; i < count; i++) {
            try {
                Set<Object> set = RedisSetUtil.queryByRange(CacheConstants.SYNC_DATA_OBJECTS, null, 29L);
                for (Object o : set) {
                    SyncData o1 = (SyncData) o;
                    log.info("o1{}", o1);
                }
                success++;
            } catch (Exception e) {
                log.error("删除SYNC_DATA_OBJECTS异常数据:{}", e);
                fail++;
                //RedisSetUtil.removeRange(CacheConstants.SYNC_DATA_OBJECTS, null, count);
            }
        }
        log.warn("删除SYNC_DATA_OBJECTS异常数据，success:{}、fail:{}、", success, fail);
        return R.ok("success=" + success + "，fail=" + fail);
    }

    /**
     * 删除SYNC_DATA_OBJECTS异常数据
     *
     * @param count 数量
     * @return 单条数据
     */
    @GetMapping("/del1/{count}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "删除SYNC_DATA_OBJECTS", notes = "删除SYNC_DATA_OBJECTS异常数据")
    @ApiImplicitParam(name = "count", value = "数量")
    public R<Long> del1(@PathVariable Long count) {
        return R.ok(RedisSetUtil.removeRange(CacheConstants.SYNC_DATA_OBJECTS, 0L, count));
    }


    /**
     * 统计同步数据
     */
    @GetMapping("/count")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "统计同步数据", notes = "统计同步数据")
    public R<Map<String, Object>> count() {
        Long objects = RedisSetUtil.count(CacheConstants.SYNC_DATA_OBJECTS);
        Long on_off_line = RedisSetUtil.count(CacheConstants.SYNC_DATA_ON_OFF_LINE);
        Long on_off_line_delay = RedisSetUtil.count(CacheConstants.SYNC_DATA_ON_OFF_LINE_DELAY);
        Integer done_undo_key = RedisUtil.keys(CacheConstants.SYNC_DATA_UNDO_KEY + "*").size();
        Long done_success_key = RedisSetUtil.count(CacheConstants.SYNC_DATA_DONE_SUCCESS_KEY);
        Long done_fail_key = RedisSetUtil.count(CacheConstants.SYNC_DATA_DONE_FAIL_KEY);
        Long file_on_off_line = RedisSetUtil.count(CacheConstants.SYNC_FILE_ON_OFF_LINE);
        Long file_done_key = RedisSetUtil.count(CacheConstants.SYNC_FILE_DONE_KEY);
        Integer send_middle_db_fail = RedisUtil.keys(CacheConstants.SEND_MIDDLE_DB_FAIL_KEY + "*").size();

        HashMap<String, Object> map = new HashMap<>();
        map.put("objects", "待处理的数量：" + objects);
        map.put("on_off_line", "待实时上传的数量：" + on_off_line);
        map.put("on_off_line_delay", "待闲时上传的数量：" + on_off_line_delay);
        map.put("done_undo_key", "done_undo_key的数量：" + done_undo_key);
        map.put("done_success_key", "已上传成功的数量：" + done_success_key);
        map.put("done_fail_key", "已上传失败的数量：" + done_fail_key);
        map.put("file_on_off_line", "待上传的文件的数量：" + file_on_off_line);
        map.put("file_done_key", "上传成功的文件的数量：" + file_done_key);
        map.put("send_middle_db_fail", "Lis数据重发失败的数量：" + send_middle_db_fail);

        return R.ok(map);
    }


    /**
     * 删除UNDO
     */
    @GetMapping("/del/{key}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "删除UNDO", notes = "删除UNDO")
    public R<Map<String, Object>> del(@PathVariable String key) {
        Set<String> keys = RedisUtil.keys(CacheConstants.SYNC_DATA_UNDO_KEY + key + "*");
        System.out.println("SYNC_DATA_UNDO_KEYkeys=" + keys.size());
        RedisUtil.delByKeys(keys);
        Set<String> keys1 = RedisUtil.keys(CacheConstants.SYNC_DATA_UNDO_KEY + key + "*");
        System.out.println("SYNC_DATA_UNDO_KEYkeys1=" + keys1.size());
        return R.ok();
    }


    /**
     * 删除sync_data_done_success
     */
    @GetMapping("/delDataDone/{count}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "删除sync_data_done_success", notes = "删除sync_data_done_success")
    public R<Map<String, Object>> delDone(@PathVariable Long count) {
        RedisSetUtil.removeRange(CacheConstants.SYNC_DATA_DONE_SUCCESS_KEY, 0L, count);
        return R.ok();
    }
}

