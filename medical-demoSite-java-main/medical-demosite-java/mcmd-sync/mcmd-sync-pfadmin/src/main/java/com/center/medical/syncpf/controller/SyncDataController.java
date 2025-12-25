package com.center.medical.syncpf.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.sync.service.SyncDataService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 同步数据操作(SyncData)表控制层
 *
 * @author 路飞船长
 * @since 2023-02-02 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "同步数据操作")
@RequestMapping("syncData")
public class SyncDataController extends BaseController {
    /**
     * 服务对象
     */
    private final SyncDataService syncDataService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param syncData        查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询同步数据操作")
    public R<IPage<SyncData>> getPage(PageParamSimple pageParamSimple, SyncData syncData) {
        PageParam<SyncData> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.syncDataService.getPage(page, syncData));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查同步数据操作详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SyncData> selectOne(@PathVariable Long id) {
        return R.ok(this.syncDataService.getInfoById(id));
    }

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
     * 删除DONE_SUCCESS
     */
    @GetMapping("/delDone/{count}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "删除DONE_SUCCESS", notes = "删除DONE_SUCCESS")
    public R<Map<String, Object>> delDone(@PathVariable Long count) {
        RedisSetUtil.removeRange(CacheConstants.SYNC_DATA_DONE_SUCCESS_KEY, 0L, count);
        return R.ok();
    }

    /**
     * 新增数据
     *
     * @param syncData 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加同步数据操作")
    @Log(title = "同步数据操作", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"syncData.id"})
    public R insert(@RequestBody SyncData syncData) {
        return R.toResult(this.syncDataService.save(syncData));
    }

    /**
     * 修改数据
     *
     * @param syncData 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新同步数据操作")
    @Log(title = "同步数据操作", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SyncData syncData) {
        return R.toResult(this.syncDataService.updateById(syncData));
    }

    /**
     * 修改数据
     *
     * @return 修改结果
     */
    @PutMapping("/mupdate")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "批量更新", notes = "更新同步数据操作")
    @Log(title = "同步数据操作", businessType = BusinessType.UPDATE)
    public R mupdate() {
        SyncData syncData = new SyncData();
        syncData.setModifydate(new Date());
        return R.toResult(this.syncDataService.update(syncData, new LambdaUpdateWrapper<SyncData>().eq(SyncData::getBizId, "1")));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除同步数据操作")
    @Log(title = "同步数据操作-删除", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> ids) {
        return R.toResult(this.syncDataService.removeByIds(ids));
    }


}

