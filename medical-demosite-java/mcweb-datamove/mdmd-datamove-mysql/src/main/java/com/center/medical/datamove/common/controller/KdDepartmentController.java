package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.KdDepartment;
import com.center.medical.datamove.common.service.KdDepartmentService;
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
 * 金蝶中的部门信息（kingdeedepartment）(KdDepartment)接口
 *
 * @author ay
 * @since 2023-07-17 20:45:03
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "金蝶中的部门信息（kingdeedepartment）")
@RequestMapping("kdDepartment")
public class KdDepartmentController extends BaseController {
    /**
     * 服务对象
     */
    private final KdDepartmentService kdDepartmentService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param kdDepartment    查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询金蝶中的部门信息（kingdeedepartment）")
    public R<IPage<KdDepartment>> getPage(PageParamSimple pageParamSimple, KdDepartment kdDepartment) {
        PageParam<KdDepartment> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.kdDepartmentService.getPage(page, kdDepartment));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param accountNo 主键
     * @return 单条数据
     */
    @GetMapping("{accountNo}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据accountNo查金蝶中的部门信息（kingdeedepartment）详情")
    @ApiImplicitParam(name = "accountNo", value = "要查询的对象的主键{accountNo}")
    public R<KdDepartment> selectOne(@PathVariable String accountNo) {
        return R.ok(this.kdDepartmentService.getInfoById(accountNo));
    }

    /**
     * 新增数据
     *
     * @param kdDepartment 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加金蝶中的部门信息（kingdeedepartment）")
    @Log(title = "金蝶中的部门信息（kingdeedepartment）", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"kdDepartment.accountNo"})
    public R insert(@RequestBody KdDepartment kdDepartment) {
        return R.toResult(this.kdDepartmentService.save(kdDepartment));
    }

    /**
     * 修改数据
     *
     * @param kdDepartment 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新金蝶中的部门信息（kingdeedepartment）")
    @Log(title = "金蝶中的部门信息（kingdeedepartment）", businessType = BusinessType.UPDATE)
    public R update(@RequestBody KdDepartment kdDepartment) {
        return R.toResult(this.kdDepartmentService.updateById(kdDepartment));
    }

    /**
     * 删除数据
     *
     * @param accountNos 删除的对象主键{accountNo}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除金蝶中的部门信息（kingdeedepartment）")
    @Log(title = "金蝶中的部门信息（kingdeedepartment）", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{accountNo}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> accountNos) {
        return R.toResult(this.kdDepartmentService.removeByIds(accountNos));
    }
}

