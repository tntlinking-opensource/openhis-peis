package com.center.medical.synclc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.sync.bean.model.SyncFile;
import com.center.medical.sync.service.SyncFileService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 同步文件操作(SyncImage)接口
 *
 * @author makejava
 * @since 2023-09-12 10:26:41
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "同步文件操作")
@RequestMapping("syncFile/lc")
public class SyncFileController extends BaseController {
    /**
     * 服务对象
     */
    private final SyncFileService syncFileService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param syncFile        查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询同步文件操作")
    public R<IPage<SyncFile>> getPage(PageParamSimple pageParamSimple, SyncFile syncFile) {
        PageParam<SyncFile> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.syncFileService.getPage(page, syncFile));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查同步文件操作详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SyncFile> selectOne(@PathVariable Long id) {
        return R.ok(this.syncFileService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param syncFile 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加同步文件操作")
    @Log(title = "同步文件操作", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"syncImage.id"})
    public R insert(@RequestBody SyncFile syncFile) {
        return R.toResult(this.syncFileService.save(syncFile));
    }

    /**
     * 修改数据
     *
     * @param syncFile 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新同步文件操作")
    @Log(title = "同步文件操作", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SyncFile syncFile) {
        return R.toResult(this.syncFileService.updateById(syncFile));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除同步文件操作")
    @Log(title = "同步文件操作", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> ids) {
        return R.toResult(this.syncFileService.removeByIds(ids));
    }


    /**
     * 删除sync_file_up_done
     */
    @GetMapping("/delFileUpDone/{count}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "删除sync_file_up_done", notes = "删除sync_file_up_done")
    public R<Map<String, Object>> delFileUpDone(@PathVariable Long count) {
        RedisSetUtil.removeRange(CacheConstants.SYNC_FILE_UP_DONE_KEY, 0L, count);
        return R.ok();
    }


    /**
     * 删除sync_file_done
     */
    @GetMapping("/delFileDone/{count}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "删除sync_file_done", notes = "删除sync_file_done")
    public R<Map<String, Object>> delFileDone(@PathVariable Long count) {
        RedisSetUtil.removeRange(CacheConstants.SYNC_FILE_DONE_KEY, 0L, count);
        return R.ok();
    }
}


