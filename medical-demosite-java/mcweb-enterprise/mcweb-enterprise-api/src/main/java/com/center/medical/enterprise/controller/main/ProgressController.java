package com.center.medical.enterprise.controller.main;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.enterprise.bean.dto.PageParamSimple;
import com.center.medical.enterprise.bean.param.CreateOrderInfoDataParam;
import com.center.medical.enterprise.bean.param.CreateOrderInfoItemParam;
import com.center.medical.enterprise.bean.param.GetOrderListParam;
import com.center.medical.enterprise.bean.param.PeipatientDataParam;
import com.center.medical.enterprise.bean.vo.*;
import com.center.medical.enterprise.common.core.domain.R;
import com.center.medical.enterprise.common.util.PageParam;
import com.center.medical.enterprise.service.CreateorderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 订单表(MdCreateorder)接口
 *
 * @author ay
 * @since 2023-07-17 20:45:19
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检进度")
@RequestMapping("sell/progress")
public class ProgressController {
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
    @ApiOperation(value = "获取订单列表", notes = "获取订单列表")
    public R<IPage<GetOrderListVo>> getOrderList(PageParamSimple pageParamSimple, GetOrderListParam param) {
        PageParam<GetOrderListVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<GetOrderListVo> iPage = mdCreateorderService.getOrderList(page,param);
        return R.ok(iPage);
    }


    /**
     * 获取订单详情
     *
     * @return 所有数据
     */
    @GetMapping("/getPeipatientDataList")
    @ApiOperation(value = "获取体检者数据", notes = "获取体检者数据")
    public R<IPage<PeipatientDataVo>> getPeipatientDataList(PageParamSimple pageParamSimple, PeipatientDataParam param) {
        PageParam<PeipatientDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<PeipatientDataVo> iPage = mdCreateorderService.getPeipatientDataList(page,param);
        return R.ok(iPage);
    }

}

