package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.data.bean.model.ZySummary;
import com.center.medical.data.service.ZySummaryService;
import com.center.medical.report.bean.param.ZySaveParam;
import com.center.medical.report.bean.param.ZyVsSummaryListParam;
import com.center.medical.report.bean.vo.ZyChooseVo;
import com.center.medical.report.bean.vo.ZyGridDataVo;
import com.center.medical.report.bean.vo.ZyVsSummaryListVo;
import com.center.medical.report.service.ZyFinalSummaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 总检管理-总检结论词(TotalVerdict)控制层
 *
 * @author 路飞船长
 * @since 2022-12-08 09:31:30
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业最终结论")
@RequestMapping("/total/ZyFinalSummary")
public class ZyFinalSummaryController extends BaseController {
    /**
     * 服务对象
     */
    private final ZyFinalSummaryService zyFinalSummaryService;
    private final ZySummaryService zySummaryService;
    private final MapperFacade mapperFacade;


    /**
     * 【检完签到】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【检完签到】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取接害因素", "GET", "/SignInInspection/getJhysData", "获取接害因素", null)
        );
        return R.ok(new FunctionVo("职业最终结论", "职业最终结论", interfaceVos, "职业最终结论"));
    }

    /**
     * 获取总检结论词列表数据
     *
     * @param param 查询参数
     * @return 所有数据
     */
    @GetMapping("/getGridData")
    @ApiOperation(value = "获取职业最终结论", notes = "获取职业最终结论")
    public R<List<ZyGridDataVo>> getGridData(String patientcode) {
        List<ZyGridDataVo> list = zyFinalSummaryService.getGridData(patientcode);
        return R.ok(list);
    }


    /**
     * 选择危害因素
     *
     * @param patientcode 查询参数
     * @return 所有数据
     */
    @GetMapping("/choose")
    @ApiOperation(value = "选择危害因素", notes = "选择职业处理意见")
    public R<List<ZyChooseVo>> choose(String patientcode) {
        List<ZyChooseVo> list = zyFinalSummaryService.getPendingHarmsList(patientcode);
        return R.ok(list);
    }



    /**
     * 体检结论类型的数据只有五条，其中复查不能作为最终结论，只能选择目前未见异常、疑似职业病、其他疾病或异常、职业禁忌证
     *
     * @return 所有数据
     */
    @GetMapping("/getZySummaryList")
    @ApiOperation(value = "获取职业病检查结论列表", notes = "获取职业病检查结论列表")
    public R<List<ZySummary>> getZySummaryList() {
        List<ZySummary> list = zySummaryService.list(new QueryWrapper<ZySummary>()
                .eq("is_delete", 0)
                .in("id", Arrays.asList("1","4","5","3"))
        );
        return R.ok(list);
    }


    /**
     * 体检结论类型的数据只有五条，其中复查不能作为最终结论，只能选择目前未见异常、疑似职业病、其他疾病或异常、职业禁忌证
     *
     * @return 所有数据
     */
    @GetMapping("/getZyVsSummaryList")
    @ApiOperation(value = "获取职业最终结论", notes = "获取职业最终结论")
    public R<List<ZyVsSummaryListVo>> getZyVsSummaryList(ZyVsSummaryListParam param) {
        List<ZyVsSummaryListVo> list = zyFinalSummaryService.getZyVsSummaryList(param);
        return R.ok(list);
    }



    /**
     * 添加
     *
     * @return 所有数据
     */
    @PostMapping("/save")
    @ApiOperation(value = "添加", notes = "添加")
    public R<String> save(ZySaveParam param) {
        String msg = zyFinalSummaryService.saOrUp(param);
        return R.ok(msg);
    }



    /**
     * 删除
     *
     * @return 所有数据
     */
    @PostMapping("/remove")
    @ApiOperation(value = "删除", notes = "删除")
    public R<Boolean> remove(@RequestBody List<String> ids) {
        Boolean b = zyFinalSummaryService.delete(ids);
        return R.ok(b);
    }

}

