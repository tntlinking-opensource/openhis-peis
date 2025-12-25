package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.bean.param.GetKhdwmcDataParam;
import com.center.medical.sellcrm.bean.param.SellpactLoseCustParam;
import com.center.medical.sellcrm.bean.vo.SellpactVo;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.sellcrm.service.SellpactService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 客户关系-流失客户
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:57
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 5)
@Api(tags = "客户关系-流失客户")
@RequestMapping("sell/customer/lose")
public class CustomerLoseController extends BaseController {
    /**
     * 服务对象
     */
    private final SellpactService sellpactService;
    private final MapperFacade mapperFacade;
    private final SellcustomerService sellcustomerService;

    /**
     * 流失客户-分页查询
     *
     * @param pageParamSimple
     * @param sellpactLoseCustParam
     * @return
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页列表", notes = "分页查询流失客户列表", position = 1)
    public R<IPage<SellpactVo>> getLoseCustPage(PageParamSimple pageParamSimple, SellpactLoseCustParam sellpactLoseCustParam) {
        PageParam<SellpactVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sellpactService.getLoseCustPage(page, sellpactLoseCustParam));
    }


    /**
     * 流失客户-通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/loseCust/{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查销售合同维护表详情", position = 2)
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SellpactVo> selectloseCustOne(@PathVariable String id) {
        return R.ok(this.sellpactService.getloseCustInfoById(id));
    }


    /**
     * 根据分中心获取关联的客户信息
     *
     * @param sellpactLoseCustParam
     * @return
     */
    @GetMapping("/loseCust/getKhdwmcData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据分中心获取关联的客户信息", notes = "流失客户-根据分中心获取关联的客户信息", position = 3)
    public R getKhdwmcData(GetKhdwmcDataParam sellpactLoseCustParam) {
        String fzxId = SecurityUtils.getCId();
        //条件构造器
        QueryWrapper<Sellcustomer> queryWrapper = new QueryWrapper<Sellcustomer>().orderByDesc("createdate")
                .eq("khzt", "1")
                .eq("fzxid", fzxId).eq("is_delete", 0);
        //有搜索条件
        if (ObjectUtils.isNotEmpty(sellpactLoseCustParam.getKey())) {
            queryWrapper.like("khdwsrm", sellpactLoseCustParam.getKey().trim().toUpperCase());
        }
        List<Sellcustomer> list = sellcustomerService.list(queryWrapper);
        return R.ok(list);
    }


}

