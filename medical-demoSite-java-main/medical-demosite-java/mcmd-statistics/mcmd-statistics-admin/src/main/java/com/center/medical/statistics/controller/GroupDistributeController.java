package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.statistics.bean.vo.GroupDistributeVo;
import com.center.medical.statistics.service.GroupDistributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 体检团体分布情况(Createorder)表控制层
 *
 * @author ay
 * @since 2023-04-18 19:31:32
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检团体分布情况")
@RequestMapping("statistics/groupDistribute")
public class GroupDistributeController extends BaseController {
    /**
     * 服务对象
     */
    private final GroupDistributeService groupDistributeService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询订单表")
    public R<IPage<GroupDistributeVo>> getPage(PageParamSimple pageParamSimple, BaseParam param) {
        PageParam<GroupDistributeVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.groupDistributeService.getList(page, param));
    }


}

