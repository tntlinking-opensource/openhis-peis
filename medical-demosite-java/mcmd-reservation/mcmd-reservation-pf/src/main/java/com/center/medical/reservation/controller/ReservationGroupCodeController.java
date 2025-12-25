package com.center.medical.reservation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.reservation.bean.model.ReservationGroupCode;
import com.center.medical.reservation.bean.param.GenerateGroupCodeParam;
import com.center.medical.reservation.bean.param.ModifyGroupCodeParam;
import com.center.medical.reservation.bean.param.ReGroupCodePageParam;
import com.center.medical.reservation.bean.vo.GenerateGroupCodeVo;
import com.center.medical.reservation.service.ReservationGroupCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 团体预约分享码(ReservationGroupCode)接口
 *
 * @author ay
 * @since 2024-03-08 16:38:39
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "团体预约分享码")
@RequestMapping("/reservation/reservationGroupCode")
public class ReservationGroupCodeController extends BaseController {

    /**
     * 服务对象
     */
    private final ReservationGroupCodeService reservationGroupCodeService;
    private final MapperFacade mapperFacade;

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/generateGroupCode")
    @ApiOperation(value = "生成团检分享码", notes = "生成团检分享码，没生成就生成，生成的话就返回数据")
    @Log(title = "生成团检分享码", businessType = BusinessType.INSERT)
    public R<GenerateGroupCodeVo> generateGroupCode(@RequestBody GenerateGroupCodeParam param) {
        GenerateGroupCodeVo vo = reservationGroupCodeService.generateGroupCode(param);
        return R.ok(vo);
    }



    /**
     * 修改
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/modifyGroupCode")
    @ApiOperation(value = "修改团检分享码", notes = "目前只能修改有效期或状态,两个传一个")
    public R<Boolean> modifyGroupCode(@RequestBody ModifyGroupCodeParam param) {
        Boolean b = reservationGroupCodeService.modifyGroupCode(param);
        return R.ok(b);
    }




    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple      分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询团体预约分享码")
    public R<IPage<ReservationGroupCode>> getPage(PageParamSimple pageParamSimple, ReGroupCodePageParam param) {
        PageParam<ReservationGroupCode> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reservationGroupCodeService.getPage(page, param));
    }
}
