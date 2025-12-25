package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.KdPayway;
import com.center.medical.datamove.common.service.KdPaywayService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 金蝶中的收银方式（kingdeepayway）(KdPayway)接口
 *
 * @author ay
 * @since 2023-07-17 20:45:04
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "金蝶中的收银方式（kingdeepayway）")
@RequestMapping("kdPayway")
public class KdPaywayController extends BaseController {
    /**
     * 服务对象
     */
    private final KdPaywayService kdPaywayService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param kdPayway        查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询金蝶中的收银方式（kingdeepayway）")
    public R<IPage<KdPayway>> getPage(PageParamSimple pageParamSimple, KdPayway kdPayway) {
        PageParam<KdPayway> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.kdPaywayService.getPage(page, kdPayway));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param accountNo 主键
     * @return 单条数据
     */
    @GetMapping("{accountNo}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据accountNo查金蝶中的收银方式（kingdeepayway）详情")
    @ApiImplicitParam(name = "accountNo", value = "要查询的对象的主键{accountNo}")
    public R<KdPayway> selectOne(@PathVariable String accountNo) {
        return R.ok(this.kdPaywayService.getInfoById(accountNo));
    }

    /**
     * 新增数据
     *
     * @param kdPayway 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加金蝶中的收银方式（kingdeepayway）")
    @Log(title = "金蝶中的收银方式（kingdeepayway）", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"kdPayway.accountNo"})
    public R insert(@RequestBody KdPayway kdPayway) {
        return R.toResult(this.kdPaywayService.save(kdPayway));
    }

    /**
     * 修改数据
     *
     * @param kdPayway 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新金蝶中的收银方式（kingdeepayway）")
    @Log(title = "金蝶中的收银方式（kingdeepayway）", businessType = BusinessType.UPDATE)
    public R update(@RequestBody KdPayway kdPayway) {
        return R.toResult(this.kdPaywayService.updateById(kdPayway));
    }

    /**
     * 删除数据
     *
     * @param accountNos 删除的对象主键{accountNo}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除金蝶中的收银方式（kingdeepayway）")
    @Log(title = "金蝶中的收银方式（kingdeepayway）", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{accountNo}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> accountNos) {
        return R.toResult(this.kdPaywayService.removeByIds(accountNos));
    }
}

