package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.model.SellOutside;
import com.center.medical.sellcrm.service.SellOutsideService;
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
 * 外出沟通(SellOutside)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "外出沟通")
@RequestMapping("sell/outside")
public class SellOutsideController extends BaseController {
    /**
     * 服务对象
     */
    private final SellOutsideService sellOutsideService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sellOutside     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询外出沟通")
    public R<IPage<SellOutside>> getPage(PageParamSimple pageParamSimple, SellOutside sellOutside) {
        PageParam<SellOutside> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sellOutsideService.getPage(page, sellOutside));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查外出沟通详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SellOutside> selectOne(@PathVariable String id) {
        return R.ok(this.sellOutsideService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param sellOutside 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加外出沟通")
    @Log(title = "外出沟通", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sellOutside.id"})
    public R insert(@RequestBody SellOutside sellOutside) {
        return R.toResult(this.sellOutsideService.save(sellOutside));
    }

    /**
     * 修改数据
     *
     * @param sellOutside 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新外出沟通")
    @Log(title = "外出沟通", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SellOutside sellOutside) {
        return R.toResult(this.sellOutsideService.updateById(sellOutside));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除外出沟通")
    @Log(title = "外出沟通", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.sellOutsideService.removeByIds(ids));
    }
}

