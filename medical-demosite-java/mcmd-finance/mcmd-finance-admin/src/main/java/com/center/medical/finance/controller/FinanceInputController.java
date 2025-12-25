package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Financeinput;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.finance.bean.param.FIPageParam;
import com.center.medical.finance.bean.vo.FIPageVo;
import com.center.medical.finance.service.FinanceInputService;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售员回款(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-06 16:22:37
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售提成核算-销售员回款")
@RequestMapping("finance/financeInput")
public class FinanceInputController extends BaseController {
    /**
     * 服务对象
     */
    private final FinanceInputService financeInputService;
    private final MapperFacade mapperFacade;
    private final SellcustomerService sellcustomerService;

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
    public R<IPage<FIPageVo>> getPage(PageParamSimple pageParamSimple, FIPageParam param) {
        PageParam<FIPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.financeInputService.getList(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "编辑-详情", notes = "编辑-详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Financeinput> selectOne(@PathVariable String id) {
        return R.ok(this.financeInputService.getInfoById(id));
    }

    /**
     * 数据保存或修改
     *
     * @param financeinput 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "数据保存或修改", notes = "数据保存或修改")
    @Log(title = "QT体检者表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peispatient.id"})
    public R insert(@RequestBody Financeinput financeinput) {
        return R.toResult(this.financeInputService.saOrUp(financeinput));
    }


    /**
     * 返回年份
     *
     * @return
     */
    @GetMapping("/getAllYear")
    @ApiOperation(value = "返回年份", notes = "返回年份")
    public R getAllYear() {
        List list = sellcustomerService.getAllYear();
        return R.ok(list);
    }


    /**
     * 是否允许编辑
     *
     * @param euserId
     * @param eyear
     * @return
     */
    @GetMapping("/isEdit")
    @ApiOperation(value = "是否允许编辑", notes = "是否允许编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "euserId", value = "用户id,用于判断是否允许编辑"),
            @ApiImplicitParam(name = "eyear", value = "年份")
    })
    public R isEdit(String euserId, String eyear) {
        String b = financeInputService.isEdit(euserId, eyear);
        return R.ok(b);
    }


    /**
     * 是否允许查看
     *
     * @param viewUserId
     * @param viewYear
     * @return
     */
    @GetMapping("/isView")
    @ApiOperation(value = "是否允许查看", notes = "是否允许查看")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "viewUserId", value = "用户id,用于判断是否允许编辑"),
            @ApiImplicitParam(name = "viewYear", value = "年份")
    })
    public R isView(String viewUserId, String viewYear) {
        String b = financeInputService.isView(viewUserId, viewYear);
        return R.ok(b);
    }


    /**
     * 财务录入-是否已填写
     *
     * @param fuserId
     * @param fyear
     * @return
     */
    @GetMapping("/isFinanceInput")
    @ApiOperation(value = "财务录入-是否已填写", notes = "财务录入-是否已填写")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fuserId", value = "用户id,用于判断是否允许编辑"),
            @ApiImplicitParam(name = "fyear", value = "年份")
    })
    public R isFinanceInput(String fuserId, String fyear) {
        String b = financeInputService.isFinanceInput(fuserId, fyear);
        return R.ok(b);
    }


}

