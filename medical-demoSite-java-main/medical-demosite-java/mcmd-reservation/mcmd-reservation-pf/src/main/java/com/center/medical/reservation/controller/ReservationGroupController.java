package com.center.medical.reservation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.reservation.bean.model.ReservationGroup;
import com.center.medical.reservation.bean.param.GetReVipNumberParam;
import com.center.medical.reservation.bean.param.ReservationGroupParam;
import com.center.medical.reservation.bean.param.RgListParam;
import com.center.medical.reservation.bean.vo.ReVipNumberVo;
import com.center.medical.reservation.service.ReservationGroupService;
import com.center.medical.system.service.ISysConfigService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 团体预约记录(ReservationGroup)接口
 *
 * @author makejava
 * @since 2023-08-31 16:48:07
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "团体预约记录")
@RequestMapping("/reservation/group")
public class ReservationGroupController extends BaseController {
    /**
     * 服务对象
     */
    private final ReservationGroupService reservationGroupService;
    private final MapperFacade mapperFacade;
    private final ISysConfigService iSysConfigService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询团体预约记录")
    public R<IPage<ReservationGroup>> getPage(PageParamSimple pageParamSimple, RgListParam param) {
        PageParam<ReservationGroup> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reservationGroupService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查团体预约记录详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<ReservationGroup> selectOne(@PathVariable String id) {
        return R.ok(this.reservationGroupService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加团体预约记录")
    @Log(title = "团体预约记录", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"reservationGroup.id"})
    public R insert(@Validated @RequestBody ReservationGroupParam param) {
        return R.ok(this.reservationGroupService.saOrUp(param));
    }

    /**
     * 修改数据
     *
     * @param param 更新参数
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新团体预约记录")
    @Log(title = "团体预约记录", businessType = BusinessType.UPDATE)
    public R update(@RequestBody ReservationGroupParam param) {
        return R.toResult(this.reservationGroupService.saOrUp(param));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除团体预约记录")
    @Log(title = "团体预约记录", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.reservationGroupService.rmByIds(ids));
    }


    /**
     * 获取可预约vip人数
     * @return
     */
    @GetMapping("/getReVipNumber")
    @ApiOperation(value = "获取可预约vip人数", notes = "获取可预约vip及贵宾的人数")
    public R<List<ReVipNumberVo>> getReVipNumber(GetReVipNumberParam param) {
        List<ReVipNumberVo> vo = reservationGroupService.getReVipNumber(param);
        return R.ok(vo);
    }
}


