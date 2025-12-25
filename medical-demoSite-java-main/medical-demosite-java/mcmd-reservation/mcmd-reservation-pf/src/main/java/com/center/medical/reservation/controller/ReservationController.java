package com.center.medical.reservation.controller;

import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.DateUtils;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.param.BatchModifyParam;
import com.center.medical.reservation.bean.param.ReservationParam;
import com.center.medical.reservation.bean.param.VipExportParam;
import com.center.medical.reservation.bean.vo.ReservationVo;
import com.center.medical.reservation.bean.vo.VipExportVo;
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

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@RequestMapping("/reservation/pf")
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
        IPage<ReservationVo> iPage = reservationService.getList(page, param);
        List<ReservationVo> list = iPage.getRecords();
        for (ReservationVo vo : list) {
            if (StringUtils.isNotEmpty(vo.getIdcard()) && IdcardUtil.isValidCard(vo.getIdcard())){
                vo.setAge(IdcardUtil.getAgeByIdCard(vo.getIdcard().trim()));
            }
        }
        return R.ok(iPage);
    }




    /**
     * 通过预约号查询单条数据
     *
     * @param id 预约号
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查体检预约记录详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的id")
    public R<ReservationVo> selectOne(@PathVariable String id) {
        return R.ok(this.reservationService.getInfoById(id));
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
        String systemId = reservation.getSystemId();
        if (StringUtils.isBlank(systemId)) {
            //体检系统内部预约
            String cId = SecurityUtils.getCId();
            if (StringUtils.isBlank(cId)) {
                throw new ServiceException("你不是分中心管理人员，无法进行预约！");
            }
            reservation.setBranchId(cId);
        } else {
            //外部系统预约

        }
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
    @ApiOperation(value = "更新预约状态", notes = "更新体检预约记录（预约状态），只需要传：id、reservationNo、failReason、status即可")
    @Log(title = "体检预约记录-更新预约状态", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Reservation reservation) {
        return R.ok(this.reservationService.saOrUp(reservation));
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


    /**
     * 批量修改日期及人员类型
     *
     * @param param
     * @return
     */
    @PutMapping("/batchModify")
    @ApiOperation(value = "批量修改日期及人员类型", notes = "批量修改日期及人员类型")
    @Log(title = "体检预约记录-更新预约状态", businessType = BusinessType.UPDATE)
    public R batchModify(@RequestBody BatchModifyParam param) {
        return R.ok(this.reservationService.batchModify(param));
    }



    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "vip及贵宾导出", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出", notes = "vip及贵宾导出")
    @PostMapping("/vipExport")
    public void export(HttpServletResponse response, VipExportParam param) throws ParseException {
        String[] chineseDayOfWeek = {"", "一", "二", "三", "四", "五", "六", "日"};
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date reservationDate = param.getReservationDate();
        int dayOfWeek = DateUtils.toCalendar(reservationDate).get(java.util.Calendar.DAY_OF_WEEK);
        int correctedDayOfWeek = (dayOfWeek == 1) ? 7 : dayOfWeek - 1;
        String chineseWeekDay = chineseDayOfWeek[correctedDayOfWeek];


        String date = formatter.format(reservationDate);
        List<VipExportVo> list = reservationService.vipExport(param);
        ExcelUtil<VipExportVo> util = new ExcelUtil<VipExportVo>(VipExportVo.class);
        util.exportExcel(response, list, date+"人员预约信息",date +" 星期"+chineseWeekDay+" 贵宾预约表");
    }
}

