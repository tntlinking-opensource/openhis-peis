package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.MdUserLevelBind;
import com.center.medical.datamove.common.service.MdUserLevelBindService;
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
 * 会员当前的等级(MdUserLevelBind)接口
 *
 * @author ay
 * @since 2023-07-17 20:49:19
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "会员当前的等级")
@RequestMapping("mdUserLevelBind")
public class MdUserLevelBindController extends BaseController {
    /**
     * 服务对象
     */
    private final MdUserLevelBindService mdUserLevelBindService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param mdUserLevelBind 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询会员当前的等级")
    public R<IPage<MdUserLevelBind>> getPage(PageParamSimple pageParamSimple, MdUserLevelBind mdUserLevelBind) {
        PageParam<MdUserLevelBind> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdUserLevelBindService.getPage(page, mdUserLevelBind));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param userLevelId 主键
     * @return 单条数据
     */
    @GetMapping("{userLevelId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据userLevelId查会员当前的等级详情")
    @ApiImplicitParam(name = "userLevelId", value = "要查询的对象的主键{userLevelId}")
    public R<MdUserLevelBind> selectOne(@PathVariable Long userLevelId) {
        return R.ok(this.mdUserLevelBindService.getInfoById(userLevelId));
    }

    /**
     * 新增数据
     *
     * @param mdUserLevelBind 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加会员当前的等级")
    @Log(title = "会员当前的等级", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"mdUserLevelBind.userLevelId"})
    public R insert(@RequestBody MdUserLevelBind mdUserLevelBind) {
        return R.toResult(this.mdUserLevelBindService.save(mdUserLevelBind));
    }

    /**
     * 修改数据
     *
     * @param mdUserLevelBind 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新会员当前的等级")
    @Log(title = "会员当前的等级", businessType = BusinessType.UPDATE)
    public R update(@RequestBody MdUserLevelBind mdUserLevelBind) {
        return R.toResult(this.mdUserLevelBindService.updateById(mdUserLevelBind));
    }

    /**
     * 删除数据
     *
     * @param userLevelIds 删除的对象主键{userLevelId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除会员当前的等级")
    @Log(title = "会员当前的等级", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{userLevelId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> userLevelIds) {
        return R.toResult(this.mdUserLevelBindService.removeByIds(userLevelIds));
    }
}

