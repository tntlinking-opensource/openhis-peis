package com.center.medical.reservation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.reservation.bean.model.ReservationSetting;
import com.center.medical.reservation.bean.param.ReservationPageParam;
import com.center.medical.reservation.service.ReservationSettingService;
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
 * 预约各检区设置(ReservationSetting)表控制层
 *
 * @author 路飞船长
 * @since 2023-02-09 17:26:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "预约各检区设置")
@RequestMapping("reservationSetting")
public class ReservationSettingController extends BaseController {
    /**
     * 服务对象
     */
    private final ReservationSettingService reservationSettingService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple    分页参数
     * @param reservationSetting 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询预约各检区设置")
    public R<IPage<ReservationSetting>> getPage(PageParamSimple pageParamSimple, ReservationPageParam param) {
        PageParam<ReservationSetting> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reservationSettingService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查预约各检区设置详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<ReservationSetting> selectOne(@PathVariable String id) {
        return R.ok(this.reservationSettingService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param reservationSetting 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加预约各检区设置")
    @Log(title = "预约各检区设置", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"reservationSetting.id"})
    public R insert(@RequestBody ReservationSetting reservationSetting) {
        return R.toResult(this.reservationSettingService.save(reservationSetting));
    }

    /**
     * 修改数据
     *
     * @param reservationSetting 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新预约各检区设置")
    @Log(title = "预约各检区设置", businessType = BusinessType.UPDATE)
    public R update(@RequestBody ReservationSetting reservationSetting) {
        return R.toResult(this.reservationSettingService.updateById(reservationSetting));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除预约各检区设置")
    @Log(title = "预约各检区设置", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> ids) {
        return R.toResult(this.reservationSettingService.removeByIds(ids));
    }
}

