package com.center.medical.finance.controller;

import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.finance.bean.vo.StatisticalManageVo;
import com.center.medical.finance.service.StatisticalManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 财务报表-统计报表(Createorder)表控制层
 *
 * @author ay
 * @since 2023-05-15 09:37:34
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "财务报表-统计报表")
@RequestMapping("finance/statisticalManage")
public class StatisticalManageController extends BaseController {
    /**
     * 服务对象
     */
    private final StatisticalManageService statisticalManageService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param date 分页参数
     * @return 所有数据
     */
    @GetMapping("/getList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询统计数据", notes = "查询统计数据")
    @ApiImplicitParam(name = "date", value = "时间筛选参数,月份后面拼接 -01 00:00:00")
    public R<StatisticalManageVo> getList(Date date) {
        StatisticalManageVo vo = statisticalManageService.getData(date);
        return R.ok(vo);
    }












}

