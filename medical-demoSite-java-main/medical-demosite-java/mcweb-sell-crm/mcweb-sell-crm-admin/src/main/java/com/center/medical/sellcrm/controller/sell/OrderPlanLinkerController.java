package com.center.medical.sellcrm.controller.sell;

import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.sellcrm.bean.model.OrderPlanLinker;
import com.center.medical.sellcrm.service.OrderPlanLinkerService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 签单计划联系人(OrderPlanLinker)表控制层
 *
 * @author 路飞船长
 * @since 2023-04-28 11:37:55
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售-签单计划联系人")
@RequestMapping("sell/orderPlanLinker")
public class OrderPlanLinkerController extends BaseController {
    /**
     * 服务对象
     */
    private final OrderPlanLinkerService orderPlanLinkerService;

    /**
     * 获取列表
     *
     * @param planId 签单计划ID
     * @return 所有数据
     */
    @GetMapping("/list/{planId}")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取列表", notes = "获取列表")
    @ApiImplicitParam(name = "planId", value = "签单计划ID")
    public R<List<OrderPlanLinker>> getList(String planId) {
        return R.ok(this.orderPlanLinkerService.getList(planId));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查签单计划联系人详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<OrderPlanLinker> selectOne(@PathVariable String id) {
        return R.ok(this.orderPlanLinkerService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param orderPlanLinker 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加签单计划联系人")
    @Log(title = "签单计划联系人", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"orderPlanLinker.id"})
    public R insert(@RequestBody OrderPlanLinker orderPlanLinker) {
        return R.toResult(this.orderPlanLinkerService.save(orderPlanLinker));
    }

    /**
     * 修改数据
     *
     * @param orderPlanLinker 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新签单计划联系人")
    @Log(title = "签单计划联系人", businessType = BusinessType.UPDATE)
    public R update(@RequestBody OrderPlanLinker orderPlanLinker) {
        return R.toResult(this.orderPlanLinkerService.updateById(orderPlanLinker));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除签单计划联系人")
    @Log(title = "签单计划联系人", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.orderPlanLinkerService.removeByIds(ids));
    }
}

