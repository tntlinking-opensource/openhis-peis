package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.MdAttachFile;
import com.center.medical.datamove.common.service.MdAttachFileService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 上传文件记录表(MdAttachFile)接口
 *
 * @author ay
 * @since 2023-07-17 20:45:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "上传文件记录表")
@RequestMapping("mdAttachFile")
public class MdAttachFileController extends BaseController {
    /**
     * 服务对象
     */
    private final MdAttachFileService mdAttachFileService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param mdAttachFile    查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询上传文件记录表")
    public R<IPage<MdAttachFile>> getPage(PageParamSimple pageParamSimple, MdAttachFile mdAttachFile) {
        PageParam<MdAttachFile> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdAttachFileService.getPage(page, mdAttachFile));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param fileId 主键
     * @return 单条数据
     */
    @GetMapping("{fileId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据fileId查上传文件记录表详情")
    @ApiImplicitParam(name = "fileId", value = "要查询的对象的主键{fileId}")
    public R<MdAttachFile> selectOne(@PathVariable String fileId) {
        return R.ok(this.mdAttachFileService.getInfoById(fileId));
    }

    /**
     * 新增数据
     *
     * @param mdAttachFile 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加上传文件记录表")
    @Log(title = "上传文件记录表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"mdAttachFile.fileId"})
    public R insert(@RequestBody MdAttachFile mdAttachFile) {
        return R.toResult(this.mdAttachFileService.save(mdAttachFile));
    }

    /**
     * 修改数据
     *
     * @param mdAttachFile 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新上传文件记录表")
    @Log(title = "上传文件记录表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody MdAttachFile mdAttachFile) {
        return R.toResult(this.mdAttachFileService.updateById(mdAttachFile));
    }

    /**
     * 删除数据
     *
     * @param fileIds 删除的对象主键{fileId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除上传文件记录表")
    @Log(title = "上传文件记录表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{fileId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> fileIds) {
        return R.toResult(this.mdAttachFileService.removeByIds(fileIds));
    }
}

