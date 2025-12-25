package com.center.medical.query.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.query.bean.param.StatusQueryParam;
import com.center.medical.query.bean.vo.StatusQueryVo;
import com.center.medical.query.service.ItemsStatusQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 项目检况查询(Peispatientfeeitem)表控制层
 *
 * @author ay
 * @since 2023-04-13 17:32:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "项目检况查询")
@RequestMapping("query/itemsStatusQuery")
public class ItemsStatusQueryController extends BaseController {
    /**
     * 服务对象
     */
    private final ItemsStatusQueryService itemsStatusQueryService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple    分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询体检者收费项目表")
    public R<IPage<StatusQueryVo>> getPage(PageParamSimple pageParamSimple, StatusQueryParam param) {
        PageParam<StatusQueryVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.itemsStatusQueryService.getList(page, param));
    }

    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出迟检、拒检、弃检、补检统计")
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "迟检、拒检、弃检、补检统计", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, StatusQueryParam param) {
        List<StatusQueryVo> list = itemsStatusQueryService.getExportData(param);
        ExcelUtil<StatusQueryVo> util = new ExcelUtil<StatusQueryVo>(StatusQueryVo.class);
        util.exportExcel(response, list, "迟检、拒检、弃检、补检统计");
    }


}

