package com.center.medical.reservation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.param.ReservationParam;
import com.center.medical.reservation.bean.vo.ReservationVo;
import com.center.medical.reservation.service.ReservationService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 体检预约-预约记录控制层
 *
 * @author ay
 * @since 2023-03-18 08:58:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 1)
@Api(tags = "体检预约记录")
@RequestMapping("/reservation/lc")
public class ReservationController extends BaseController {
    /**
     * 服务对象
     */
    private final ReservationService reservationService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询体检预约记录")
    public R<IPage<ReservationVo>> getPage(PageParamSimple pageParamSimple, ReservationParam param) {
        PageParam<Reservation> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reservationService.getList(page, param));
    }

    /**
     * 通过预约号查询单条数据
     *
     * @param reservationNo 预约号
     * @return 单条数据
     */
    @GetMapping("{reservationNo}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "预约号查看详情", notes = "根据预约号查体检预约记录详情")
    @ApiImplicitParam(name = "reservationNo", value = "要查询的对象的预约号")
    public R<ReservationVo> selectOne(@PathVariable String reservationNo) {
        return R.ok(this.reservationService.getInfoByNo(reservationNo));
    }

    /**
     * 新增预约
     *
     * @param reservation 实体对象
     * @return 新增结果
     */
    @PostMapping
    @PreAuthorize("@ss.hasPermi('subscribe:myAppointment:add')")
    @ApiOperation(value = "新增预约", notes = "新增预约记录")
    @Log(title = "体检预约记录-新增预约", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"reservation.id"})
    public R<String> insert(@RequestBody Reservation reservation) {
        return this.reservationService.saOrUp(reservation);
    }

    /**
     * 修改数据
     *
     * @param reservation 更新后的状态
     * @return 修改结果
     */
    @PutMapping
    @PreAuthorize("@ss.hasPermi('subscribe:myAppointment:edit')")
    @ApiOperation(value = "更新预约状态", notes = "更新体检预约记录（预约状态），只需要传预约号`reservationNo`和更新后的状态`status`即可")
    @Log(title = "体检预约记录-更新预约状态", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Reservation reservation) {
        return this.reservationService.saOrUp(reservation);
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除体检预约记录")
    @Log(title = "体检预约记录", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {

        return R.toResult(this.reservationService.rmByIds(ids));
    }
}

