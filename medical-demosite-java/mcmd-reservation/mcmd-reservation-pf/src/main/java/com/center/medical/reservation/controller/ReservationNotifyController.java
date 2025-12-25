package com.center.medical.reservation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.reservation.bean.model.ReservationNotify;
import com.center.medical.reservation.service.ReservationNotifyService;
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
 * 预约信息变更通知记录(ReservationNotify)接口
 *
 * @author makejava
 * @since 2023-09-21 10:58:15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "预约信息变更通知记录")
@RequestMapping("reservationNotify/pf")
public class ReservationNotifyController extends BaseController {
    /**
     * 服务对象
     */
    private final ReservationNotifyService reservationNotifyService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple   分页参数
     * @param reservationNotify 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询预约信息变更通知记录")
    public R<IPage<ReservationNotify>> getPage(PageParamSimple pageParamSimple, ReservationNotify reservationNotify) {
        PageParam<ReservationNotify> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reservationNotifyService.getPage(page, reservationNotify));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查预约信息变更通知记录详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<ReservationNotify> selectOne(@PathVariable String id) {
        return R.ok(this.reservationNotifyService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param reservationNotify 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加预约信息变更通知记录")
    @Log(title = "预约信息变更通知记录", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"reservationNotify.id"})
    public R insert(@RequestBody ReservationNotify reservationNotify) {
        return R.toResult(this.reservationNotifyService.save(reservationNotify));
    }

    /**
     * 修改数据
     *
     * @param reservationNotify 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新预约信息变更通知记录")
    @Log(title = "预约信息变更通知记录", businessType = BusinessType.UPDATE)
    public R update(@RequestBody ReservationNotify reservationNotify) {
        return R.toResult(this.reservationNotifyService.updateById(reservationNotify));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除预约信息变更通知记录")
    @Log(title = "预约信息变更通知记录", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.reservationNotifyService.removeByIds(ids));
    }
}


