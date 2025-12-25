package com.center.medical.enterprise.controller.main;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.enterprise.bean.dto.PageParamSimple;
import com.center.medical.enterprise.bean.param.CreateOrderInfoDataParam;
import com.center.medical.enterprise.bean.param.CreateOrderInfoItemParam;
import com.center.medical.enterprise.bean.vo.CreateOrderInfoDataVo;
import com.center.medical.enterprise.bean.vo.CreateOrderInfoItemlVo;
import com.center.medical.enterprise.bean.vo.CreateOrderInfoMealVo;
import com.center.medical.enterprise.common.core.domain.R;
import com.center.medical.enterprise.common.util.PageParam;
import com.center.medical.enterprise.service.CreateorderService;
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
 * 订单表(MdCreateorder)接口
 *
 * @author ay
 * @since 2023-07-17 20:45:19
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "订单详情")
@RequestMapping("sell/createorder")
public class CreateorderController {
    /**
     * 服务对象
     */
    private final CreateorderService mdCreateorderService;
    private final MapperFacade mapperFacade;

    /**
     * 获取订单详情
     *
     * @return 所有数据
     */
    @GetMapping("/getInfoListData")
    @ApiOperation(value = "订单详情", notes = "获取订单详情")
    public R<IPage<CreateOrderInfoDataVo>> getInfoListData(PageParamSimple pageParamSimple, CreateOrderInfoDataParam param) {
        PageParam<CreateOrderInfoDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<CreateOrderInfoDataVo> iPage = mdCreateorderService.getInfoListData(page,param);
        return R.ok(iPage);
    }



    /**
     * 获取套餐详情
     *
     * @return 所有数据
     */
    @GetMapping("/getInfoMealData")
    @ApiOperation(value = "套餐列表", notes = "获取套餐列表")
    public R<IPage<CreateOrderInfoMealVo>> getInfoMealData(PageParamSimple pageParamSimple, String id) {
        PageParam<CreateOrderInfoMealVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<CreateOrderInfoMealVo> iPage = mdCreateorderService.getInfoMealData(page,id);
        return R.ok(iPage);
    }



    /**
     * 获取套餐详情
     *
     * @return 所有数据
     */
    @GetMapping("/getInfoItemData")
    @ApiOperation(value = "套餐详情", notes = "获取套餐详情")
    public R<IPage<CreateOrderInfoItemlVo>> getInfoItemData(PageParamSimple pageParamSimple, CreateOrderInfoItemParam param) {
        PageParam<CreateOrderInfoItemlVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<CreateOrderInfoItemlVo> iPage = mdCreateorderService.getInfoItemData(page,param);
        return R.ok(iPage);
    }
}

