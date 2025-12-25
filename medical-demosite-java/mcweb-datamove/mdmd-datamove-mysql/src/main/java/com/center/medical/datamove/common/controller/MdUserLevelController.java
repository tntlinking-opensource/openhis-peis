package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.MdUserLevel;
import com.center.medical.datamove.common.service.MdUserLevelService;
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
 * 会员等级表(MdUserLevel)接口
 *
 * @author ay
 * @since 2023-07-17 20:49:17
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "会员等级表")
@RequestMapping("mdUserLevel")
public class MdUserLevelController extends BaseController {
    /**
     * 服务对象
     */
    private final MdUserLevelService mdUserLevelService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param mdUserLevel     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询会员等级表")
    public R<IPage<MdUserLevel>> getPage(PageParamSimple pageParamSimple, MdUserLevel mdUserLevel) {
        PageParam<MdUserLevel> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdUserLevelService.getPage(page, mdUserLevel));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param levelId 主键
     * @return 单条数据
     */
    @GetMapping("{levelId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据levelId查会员等级表详情")
    @ApiImplicitParam(name = "levelId", value = "要查询的对象的主键{levelId}")
    public R<MdUserLevel> selectOne(@PathVariable Integer levelId) {
        return R.ok(this.mdUserLevelService.getInfoById(levelId));
    }

    /**
     * 新增数据
     *
     * @param mdUserLevel 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加会员等级表")
    @Log(title = "会员等级表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"mdUserLevel.levelId"})
    public R insert(@RequestBody MdUserLevel mdUserLevel) {
        return R.toResult(this.mdUserLevelService.save(mdUserLevel));
    }

    /**
     * 修改数据
     *
     * @param mdUserLevel 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新会员等级表")
    @Log(title = "会员等级表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody MdUserLevel mdUserLevel) {
        return R.toResult(this.mdUserLevelService.updateById(mdUserLevel));
    }

    /**
     * 删除数据
     *
     * @param levelIds 删除的对象主键{levelId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除会员等级表")
    @Log(title = "会员等级表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{levelId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Integer> levelIds) {
        return R.toResult(this.mdUserLevelService.removeByIds(levelIds));
    }
}

