package com.center.medical.appadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.BsSettlement;
import com.center.medical.appadmin.bean.param.BsSettlementParam;
import com.center.medical.appadmin.bean.vo.BSGetTotalVo;
import com.center.medical.appadmin.bean.vo.BsSettlementVo;
import com.center.medical.appadmin.service.BsSettlementService;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 业务结算表(BsSettlement)接口
 *
 * @author ay
 * @since 2024-06-17 16:01:57
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "业务结算")
@RequestMapping("app/bsSettlement")
public class BsSettlementController extends BaseController {
    /**
     * 服务对象
     */
    private final BsSettlementService bsSettlementService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param    查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询业务结算表")
    public R<IPage<BsSettlementVo>> getPage(PageParamSimple pageParamSimple, BsSettlementParam param) {
        PageParam<BsSettlementVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.bsSettlementService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param settlementId 主键
     * @return 单条数据
     */
    @GetMapping("{settlementId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据settlementId查业务结算表详情")
    @ApiImplicitParam(name = "settlementId", value = "要查询的对象的主键{settlementId}")
    public R<BsSettlement> selectOne(@PathVariable String settlementId) {
        return R.ok(this.bsSettlementService.getInfoById(settlementId));
    }



    /**
     * 获取总额
     *
     * @param param    查询条件
     * @return 所有数据
     */
    @GetMapping("/getTotal")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取总额", notes = "获取总额")
    public R<BSGetTotalVo> getTotal(BsSettlementParam param) {
        return R.ok(this.bsSettlementService.getTotal(param));
    }

}
