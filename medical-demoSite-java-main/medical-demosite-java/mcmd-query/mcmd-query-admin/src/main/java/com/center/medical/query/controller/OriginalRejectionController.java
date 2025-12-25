package com.center.medical.query.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.query.bean.param.OriginalParam;
import com.center.medical.query.bean.vo.OriginalVo;
import com.center.medical.query.service.ItemsStatusQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * QT体检者表(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-13 18:36:46
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室拒检查询")
@RequestMapping("query/originalRejection")
public class OriginalRejectionController extends BaseController {
    /**
     * 服务对象
     */
    private final ItemsStatusQueryService itemsStatusQueryService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询科室拒检查询", notes = "分页查询科室拒检查询")
    public R<IPage<OriginalVo>> getPage(PageParamSimple pageParamSimple, OriginalParam param) {
        PageParam<OriginalVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.itemsStatusQueryService.getOriginalRejection(page, param));
    }


}

