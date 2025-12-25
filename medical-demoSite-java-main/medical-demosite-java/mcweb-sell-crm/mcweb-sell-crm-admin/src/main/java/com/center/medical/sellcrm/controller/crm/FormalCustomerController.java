package com.center.medical.sellcrm.controller.crm;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.sellcrm.bean.param.FormalPageParam;
import com.center.medical.sellcrm.bean.vo.FormalPageVo;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 客户管理
 *
 * @author ay
 * @since 2022-11-10 18:48:15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort
@Api(tags = "客户关系-正式客户")
@RequestMapping("sell/formalCustomer")
public class FormalCustomerController extends BaseController {
    /**
     * 服务对象
     */
    private final SellcustomerService sellcustomerService;
    private final MapperFacade mapperFacade;


    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页对象
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
//    @PreAuthorize("@ss.hasPermi('sell:sellcustomer:list')")
    @ApiOperation(value = "分页列表", notes = "分页查询正式客户", position = 1)
    public R<IPage<FormalPageVo>> getPage(PageParamSimple pageParamSimple, FormalPageParam param) {
        PageParam<FormalPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<FormalPageVo> iPage = sellcustomerService.getFormalPage(page, param);
        return R.ok(iPage);
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "卡发放", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出正式客户", notes = "导出正式客户")
    @PostMapping("/export")
    public void export(HttpServletResponse response, FormalPageParam param) {
        List<FormalPageVo> list = sellcustomerService.getExportData(param);
        ExcelUtil<FormalPageVo> util = new ExcelUtil<FormalPageVo>(FormalPageVo.class);
        util.exportExcel(response, list, "正式客户");
    }

}

