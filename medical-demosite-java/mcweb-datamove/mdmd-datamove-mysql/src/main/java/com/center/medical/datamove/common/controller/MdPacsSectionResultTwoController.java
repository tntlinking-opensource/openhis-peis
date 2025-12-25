package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.MdPacsSectionResultTwo;
import com.center.medical.datamove.common.service.MdPacsSectionResultTwoService;
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
 * PACS-科室结果表(MdPacsSectionResultTwo)接口
 *
 * @author ay
 * @since 2023-07-17 20:46:35
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "PACS-科室结果表")
@RequestMapping("mdPacsSectionResultTwo")
public class MdPacsSectionResultTwoController extends BaseController {
    /**
     * 服务对象
     */
    private final MdPacsSectionResultTwoService mdPacsSectionResultTwoService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple        分页参数
     * @param mdPacsSectionResultTwo 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询PACS-科室结果表")
    public R<IPage<MdPacsSectionResultTwo>> getPage(PageParamSimple pageParamSimple, MdPacsSectionResultTwo mdPacsSectionResultTwo) {
        PageParam<MdPacsSectionResultTwo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdPacsSectionResultTwoService.getPage(page, mdPacsSectionResultTwo));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查PACS-科室结果表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<MdPacsSectionResultTwo> selectOne(@PathVariable String id) {
        return R.ok(this.mdPacsSectionResultTwoService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param mdPacsSectionResultTwo 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加PACS-科室结果表")
    @Log(title = "PACS-科室结果表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"mdPacsSectionResultTwo.id"})
    public R insert(@RequestBody MdPacsSectionResultTwo mdPacsSectionResultTwo) {
        return R.toResult(this.mdPacsSectionResultTwoService.save(mdPacsSectionResultTwo));
    }

    /**
     * 修改数据
     *
     * @param mdPacsSectionResultTwo 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新PACS-科室结果表")
    @Log(title = "PACS-科室结果表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody MdPacsSectionResultTwo mdPacsSectionResultTwo) {
        return R.toResult(this.mdPacsSectionResultTwoService.updateById(mdPacsSectionResultTwo));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除PACS-科室结果表")
    @Log(title = "PACS-科室结果表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.mdPacsSectionResultTwoService.removeByIds(ids));
    }
}

