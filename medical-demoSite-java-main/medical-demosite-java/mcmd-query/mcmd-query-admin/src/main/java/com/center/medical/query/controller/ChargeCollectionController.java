package com.center.medical.query.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.query.bean.param.CCPageParam;
import com.center.medical.query.bean.vo.CCPageVo;
import com.center.medical.query.service.ChargeCollectionService;
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

/**
 * 自费收费汇总(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-08 09:31:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "自费收费汇总")
@RequestMapping("query/chargeCollection")
public class ChargeCollectionController extends BaseController {
    /**
     * 服务对象
     */
    private final ChargeCollectionService chargeCollectionService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<CCPageVo>> getPage(PageParamSimple pageParamSimple, CCPageParam param) {
        PageParam<CCPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.chargeCollectionService.getList(page, param));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "自费收费汇总", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出自费收费汇总")
    @PostMapping("/export")
    public void export(HttpServletResponse response, CCPageParam param) {
        chargeCollectionService.getExportData(response,param);
    }
}

