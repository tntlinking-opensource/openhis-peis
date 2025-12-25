package com.center.medical.reception.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.reception.bean.model.ReviewProject;
import com.center.medical.reception.service.ReviewProjectService;
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
 * ZJ复查项目表(ReviewProject)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-22 20:52:48
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "ZJ复查项目表")
@RequestMapping("reviewProject")
public class ReviewProjectController extends BaseController {
    /**
     * 服务对象
     */
    private final ReviewProjectService reviewProjectService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param reviewProject   查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询ZJ复查项目表")
    public R<IPage<ReviewProject>> getPage(PageParamSimple pageParamSimple, ReviewProject reviewProject) {
        PageParam<ReviewProject> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reviewProjectService.getPage(page, reviewProject));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查ZJ复查项目表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<ReviewProject> selectOne(@PathVariable String id) {
        return R.ok(this.reviewProjectService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param reviewProject 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加ZJ复查项目表")
    @Log(title = "ZJ复查项目表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"reviewProject.id"})
    public R insert(@RequestBody ReviewProject reviewProject) {
        return R.toResult(this.reviewProjectService.save(reviewProject));
    }

    /**
     * 修改数据
     *
     * @param reviewProject 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新ZJ复查项目表")
    @Log(title = "ZJ复查项目表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody ReviewProject reviewProject) {
        return R.toResult(this.reviewProjectService.updateById(reviewProject));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除ZJ复查项目表")
    @Log(title = "ZJ复查项目表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.reviewProjectService.removeByIds(ids));
    }
}

