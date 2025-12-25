package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.MdAttachmentGroup;
import com.center.medical.datamove.common.service.MdAttachmentGroupService;
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
 * 文件分组表(MdAttachmentGroup)接口
 *
 * @author ay
 * @since 2023-07-17 20:45:07
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "文件分组表")
@RequestMapping("mdAttachmentGroup")
public class MdAttachmentGroupController extends BaseController {
    /**
     * 服务对象
     */
    private final MdAttachmentGroupService mdAttachmentGroupService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple   分页参数
     * @param mdAttachmentGroup 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询文件分组表")
    public R<IPage<MdAttachmentGroup>> getPage(PageParamSimple pageParamSimple, MdAttachmentGroup mdAttachmentGroup) {
        PageParam<MdAttachmentGroup> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdAttachmentGroupService.getPage(page, mdAttachmentGroup));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param fileGroupId 主键
     * @return 单条数据
     */
    @GetMapping("{fileGroupId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据fileGroupId查文件分组表详情")
    @ApiImplicitParam(name = "fileGroupId", value = "要查询的对象的主键{fileGroupId}")
    public R<MdAttachmentGroup> selectOne(@PathVariable String fileGroupId) {
        return R.ok(this.mdAttachmentGroupService.getInfoById(fileGroupId));
    }

    /**
     * 新增数据
     *
     * @param mdAttachmentGroup 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加文件分组表")
    @Log(title = "文件分组表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"mdAttachmentGroup.fileGroupId"})
    public R insert(@RequestBody MdAttachmentGroup mdAttachmentGroup) {
        return R.toResult(this.mdAttachmentGroupService.save(mdAttachmentGroup));
    }

    /**
     * 修改数据
     *
     * @param mdAttachmentGroup 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新文件分组表")
    @Log(title = "文件分组表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody MdAttachmentGroup mdAttachmentGroup) {
        return R.toResult(this.mdAttachmentGroupService.updateById(mdAttachmentGroup));
    }

    /**
     * 删除数据
     *
     * @param fileGroupIds 删除的对象主键{fileGroupId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除文件分组表")
    @Log(title = "文件分组表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{fileGroupId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> fileGroupIds) {
        return R.toResult(this.mdAttachmentGroupService.removeByIds(fileGroupIds));
    }
}

