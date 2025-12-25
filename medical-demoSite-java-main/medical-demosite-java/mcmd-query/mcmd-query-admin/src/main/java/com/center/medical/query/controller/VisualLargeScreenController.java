package com.center.medical.query.controller;

import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.query.bean.vo.GetHomePageDataVo;
import com.center.medical.query.service.VisualLargeScreenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 可视化大屏(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-05-20 11:00:31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "可视化大屏")
@RequestMapping("query/visualLargeScreen")
public class VisualLargeScreenController extends BaseController {

    /**
     * 服务对象
     */
    private final VisualLargeScreenService visualLargeScreenService;
    private final MapperFacade mapperFacade;

    /**
     * 查询首页数据
     *
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/getHomePageData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询首页数据", notes = "查询首页数据")
    public R<GetHomePageDataVo> getHomePageData(BaseParam param) {
        GetHomePageDataVo vo = visualLargeScreenService.getHomePageData(param);
        return R.ok(vo);
    }


}

