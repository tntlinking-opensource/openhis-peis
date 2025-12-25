package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.statistics.bean.param.SatisfactionRankParam;
import com.center.medical.statistics.bean.vo.SatisfactionRankVo;
import com.center.medical.statistics.service.SatisfactionRankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 科室满意度排名(Satisfaction)表控制层
 *
 * @author ay
 * @since 2023-04-20 10:01:49
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室满意度排名")
@RequestMapping("statistics/satisfactionRank")
public class SatisfactionRankController extends BaseController {
    /**
     * 服务对象
     */
    private final SatisfactionRankService satisfactionRankService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询KF满意度")
    public R<IPage<SatisfactionRankVo>> getPage(PageParamSimple pageParamSimple, SatisfactionRankParam param) {
        PageParam<SatisfactionRankVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.satisfactionRankService.getList(page, param));
    }


}

