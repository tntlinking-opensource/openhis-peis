package com.center.medical.query.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.query.bean.param.FinishStatusParam;
import com.center.medical.query.bean.vo.FCChargeDataVo;
import com.center.medical.query.bean.vo.FinishStatusVo;
import com.center.medical.query.service.FinishStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 未检项目查询(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-13 16:32:12
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "未检项目查询")
@RequestMapping("query/finishStatus")
public class FinishStatusController extends BaseController {
    /**
     * 服务对象
     */
    private final FinishStatusService finishStatusService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<FinishStatusVo>> getPage(PageParamSimple pageParamSimple, FinishStatusParam param) {
        PageParam<FinishStatusVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.finishStatusService.getList(page, param));
    }

    /**
     * 点击获取收费项目信息
     * @param pageParamSimple
     * @param patientcode
     * @return
     */
    @GetMapping("/getChargeData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "点击获取收费项目信息", notes = "点击获取收费项目信息")
    public R<IPage<FCChargeDataVo>> getChargeData(PageParamSimple pageParamSimple, String patientcode) {
        PageParam<FCChargeDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.finishStatusService.getChargeData(page, patientcode));
    }
}

