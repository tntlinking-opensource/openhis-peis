package com.center.medical.reservation.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.reservation.bean.dto.ReservationSettingGroupDto;
import com.center.medical.reservation.bean.model.ReservationSetting;
import com.center.medical.reservation.bean.param.*;
import com.center.medical.reservation.bean.param.*;
import com.center.medical.reservation.bean.vo.QueryReservationTimeVo;
import com.center.medical.reservation.bean.vo.QueryReservationVo;
import com.center.medical.reservation.service.ReservationSettingService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 预约各检区设置(ReservationSetting)表控制层
 *
 * @author ay
 * @since 2023-03-18 08:58:17
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 4)
@Api(tags = "预约各检区设置")
@RequestMapping("/reservation/Setting")
public class ReservationSettingController extends BaseController {
    /**
     * 服务对象
     */
    private final ReservationSettingService reservationSettingService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询预约各检区设置")
    public R<IPage<ReservationSetting>> getPage(PageParamSimple pageParamSimple, ReservationPageParam param) {
        PageParam<ReservationSetting> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reservationSettingService.getPage(page, param));
    }

    /**
     * 获取预约时段列表
     *
     * @param param 请求参数
     * @return 所有数据
     */
    @PostMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取预约时段列表", notes = "获取预约时段列表")
    public R<List<ReservationSetting>> getList(@Validated @RequestBody ReservationSettingParam param) {
        if (StringUtils.isBlank(param.getBranchId())) {
            param.setBranchId(SecurityUtils.getCId());
        }
        return R.ok(this.reservationSettingService.getList(param));
    }

    /**
     * 获取团体可预约列表
     *
     * @param param 请求参数
     * @return 所有数据
     */
    @PostMapping("/groupList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取团体可预约列表", notes = "获取团体可预约列表")
    public R<List<ReservationSettingGroupDto>> groupList(@Validated @RequestBody ReservationSettingGroupParam param) {
        if (StringUtils.isBlank(param.getBranchId())) {
            param.setBranchId(SecurityUtils.getCId());
        }
        param.setStatusList(Arrays.asList(1));
        return R.ok(this.reservationSettingService.groupList(param));
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
        //体检系统内部预约
        String cId = SecurityUtils.getCId();
        if (StringUtils.isBlank(cId)) {
            throw new ServiceException("你不是分中心管理人员，不能添加！");
        }
        reservationSetting.setBranchId(cId);
        reservationSetting.setStatus(1);
        reservationSetting.setCreator(SecurityUtils.getUserNo());
        //判断是否重复添加
        long count = reservationSettingService.count(new LambdaQueryWrapper<ReservationSetting>()
                .eq(ReservationSetting::getReservationDate, DateUtil.beginOfDay(reservationSetting.getReservationDate()))
                .eq(ReservationSetting::getLevelId, reservationSetting.getLevelId())
                .eq(ReservationSetting::getBranchId, reservationSetting.getBranchId())
                .eq(ReservationSetting::getStatus, 1)
        );
        if (count > 0) {
            throw new ServiceException("已有相同类型的数据,请前去修改！");
        }
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
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.reservationSettingService.removeByIds(ids));
    }




    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/queryReservationDate")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询预约日期", notes = "分页查询预约日期")
    public R<IPage<QueryReservationVo>> queryReservationDate(PageParamSimple pageParamSimple, QueryReservationParam param) {
        PageParam<QueryReservationVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reservationSettingService.queryReservationDate(page, param));
    }




    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/queryReservationTime")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询预约时间", notes = "分页查询预约时间")
    public R<IPage<QueryReservationTimeVo>> queryReservationTime(PageParamSimple pageParamSimple, QueryReservationParam param) {
        PageParam<QueryReservationTimeVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reservationSettingService.queryReservationTime(page, param));
    }


    /**
     * 批量设置
     *
     * @param param 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @PostMapping("/batchSettings")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "批量设置", notes = "批量设置")
    public R<Boolean> batchSettings(@RequestBody BatchSettingDataParam param) {
        Boolean b = reservationSettingService.batchSettings(param);
        return R.ok(b);
    }




    /**
     * 批量设置
     *
     * @param ids           查询条件
     * @return 所有数据
     */
    @PostMapping("/batchClose")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "批量关闭", notes = "批量关闭,把开放状态改成未开放预约")
    public R<Boolean> batchClose(@RequestBody List<String> ids) {
        Boolean b = reservationSettingService.batchClose(ids);
        return R.ok(b);
    }
}

