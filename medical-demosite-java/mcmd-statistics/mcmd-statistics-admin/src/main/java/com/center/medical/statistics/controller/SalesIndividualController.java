package com.center.medical.statistics.controller;

import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 个检销售统计及团检费用统计(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-14 16:40:23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检费用统计-个检销售统计及团检费用统计")
@RequestMapping("statistics/salesIndividual")
public class SalesIndividualController extends BaseController {
    /**
     * 【回访管理-迟补检回访】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【体检费用统计-个检销售统计及团检费用统计】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/finance/individualStatistics/page", "14.财务系统->销售提成核算-个检销售统计->分页查询", null),
                new InterfaceVo("获取关联的数据", "GET", "/finance/individualStatistics/getListData", "14.财务系统->销售提成核算-个检销售统计->获取关联的数据", null),
                new InterfaceVo("按天导出", "POST", "/finance/individualStatistics/exportByDay", "14.财务系统->销售提成核算-个检销售统计->按天导出", null),
                new InterfaceVo("导出", "POST", "/finance/individualStatistics/export", "14.财务系统->销售提成核算-个检销售统计->导出", null)
        );
        return R.ok(new FunctionVo("04.统计模块", "体检费用统计-个检销售统计及团检费用统计", interfaceVos, "04.统计模块"));
    }
}

