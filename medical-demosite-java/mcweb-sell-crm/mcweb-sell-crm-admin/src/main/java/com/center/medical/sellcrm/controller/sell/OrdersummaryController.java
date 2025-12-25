package com.center.medical.sellcrm.controller.sell;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.Ordersummary;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.bean.param.OrdersummaryParam;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.sellcrm.service.OrdersummaryService;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 订单总结表(Ordersummary)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-22 18:38:25
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售-订单总结")
@RequestMapping("sell/orderSummary")
public class OrdersummaryController extends BaseController {
    /**
     * 服务对象
     */
    private final OrdersummaryService ordersummaryService;
    private final MapperFacade mapperFacade;
    private final CreateorderService createorderService;
    private final SellcustomerService sellcustomerService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询订单总结表")
    public R<IPage<Ordersummary>> getPage(PageParamSimple pageParamSimple, OrdersummaryParam param) {
        PageParam<Ordersummary> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.ordersummaryService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查订单总结表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Ordersummary> selectOne(@PathVariable String id) {
        return R.ok(this.ordersummaryService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param ordersummary 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加订单总结表")
    @Log(title = "订单总结表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"ordersummary.id"})
    public R insert(@RequestBody Ordersummary ordersummary) {
        return R.toResult(this.ordersummaryService.saOrUp(ordersummary));
    }

    /**
     * 修改数据
     *
     * @param ordersummary 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新订单总结表")
    @Log(title = "订单总结表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Ordersummary ordersummary) {
        return R.toResult(this.ordersummaryService.saOrUp(ordersummary));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除订单总结表")
    @Log(title = "订单总结表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R<Boolean> delete(@PathVariable List<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请选择要删除的对象！");
        }
        Ordersummary ordersummary = new Ordersummary();
        ordersummary.setIsDelete(1);
        ordersummary.setModifydate(new Date());

        return R.toResult(this.ordersummaryService.update(ordersummary, new LambdaUpdateWrapper<Ordersummary>().in(Ordersummary::getId, ids)));
    }

    /**
     * 根据订单id获取关联的客户单位名称
     *
     * @param orderId 主键
     * @return 单条数据
     */
    @GetMapping("getKhdwmc/{orderId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "根据订单id获取关联的客户单位名称", notes = "根据订单id获取关联的客户单位名称")
    @ApiImplicitParam(name = "orderId", value = "要查询的订单id}")
    public R<String> getKhdwmc(@PathVariable String orderId) {
        Createorder createorder = createorderService.getInfoById(orderId);
        if (Objects.isNull(createorder)) {
            throw new ServiceException("获取失败，订单不存在或者已被删除！");
        }
        Sellcustomer sellcustomer = sellcustomerService.getInfoById(createorder.getKhdwmcid());
        if (Objects.isNull(sellcustomer)) {
            throw new ServiceException("获取失败，订单客户信息不存在！");
        }
        return R.ok(sellcustomer.getKhdwmc());
    }
}

