package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.finance.bean.param.OCPageParam;
import com.center.medical.finance.bean.vo.OCPageVo;
import com.center.medical.finance.service.OrderDiscountService;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.sellcrm.service.SellcustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 团检订单折扣(Createorder)表控制层
 *
 * @author ay
 * @since 2023-04-04 16:53:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售提成核算-团检订单折扣")
@RequestMapping("finance/orderDiscount")
public class OrderDiscountController extends BaseController {
    /**
     * 服务对象
     */
    private final OrderDiscountService orderDiscountService;
    private final MapperFacade mapperFacade;
    private final CreateorderService createorderService;
    private final SellcustomerService sellcustomerService;


    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【团检订单折扣】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取分中心信息", "GET", "/sell/createorder/getBranchData", "财务管理->体检卡管理-卡充值->获取分中心信息", null)
        );
        return R.ok(new FunctionVo("09.财务管理", "团检订单折扣", interfaceVos, "09.财务管理"));
    }

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询订单表")
    public R<IPage<OCPageVo>> getPage(PageParamSimple pageParamSimple, OCPageParam param) {
        PageParam<OCPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.orderDiscountService.getList(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/view")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查订单表详情")
    @ApiImplicitParam(name = "id", value = "客户单位名称可能会返回空,这是因为数据库不一样，没有对应khdwmcid的名称")
    public R<Createorder> selectOne(String id) {
        Createorder createorder = createorderService.getInfoById(id);
        if (ObjectUtils.isNotEmpty(createorder)) {
            //客户单位名称
            Sellcustomer sellcustomer = sellcustomerService.getInfoById(createorder.getKhdwmcid());
            createorder.setKhdwmc(ObjectUtils.isNotEmpty(sellcustomer) ? sellcustomer.getKhdwmc() : "");
        }
        return R.ok(createorder);
    }

}

