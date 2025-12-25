package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.param.OrderConflictDeallParam;
import com.center.medical.sellcrm.bean.param.OrderConflictParam;
import com.center.medical.sellcrm.bean.vo.OrderConflictVo;
import com.center.medical.sellcrm.service.OrderConflictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 撞单记录(OrderConflict)表控制层
 *
 * @author 路飞船长
 * @since 2023-04-27 18:18:31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售-撞单记录")
@RequestMapping("sell/orderConflict")
public class OrderConflictController extends BaseController {
    /**
     * 服务对象
     */
    private final OrderConflictService orderConflictService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询撞单记录")
    public R<IPage<OrderConflictVo>> getPage(PageParamSimple pageParamSimple, OrderConflictParam param) {
        PageParam<OrderConflictVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.orderConflictService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查撞单记录详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<OrderConflictVo> selectOne(@PathVariable String id) {
        return R.ok(this.orderConflictService.getInfoById(id));
    }


    /**
     * 撞单处理
     *
     * @param param 处理参数
     * @return 处理结果结果
     */
    @PutMapping("/deal")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "处理提交", notes = "处理提交")
    @Log(title = "销售-撞单记录-撞单处理提交", businessType = BusinessType.UPDATE)
    public R deal(@RequestBody OrderConflictDeallParam param) {
        return R.toResult(this.orderConflictService.deal(param));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除撞单记录")
    @Log(title = "撞单记录", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.orderConflictService.removeByIds(ids));
    }
}

