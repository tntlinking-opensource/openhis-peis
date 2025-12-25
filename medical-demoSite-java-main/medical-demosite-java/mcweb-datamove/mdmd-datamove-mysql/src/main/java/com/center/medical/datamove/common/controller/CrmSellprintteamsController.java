package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.CrmSellprintteams;
import com.center.medical.datamove.common.service.CrmSellprintteamsService;
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
 * 销售打印项目分类设置表(CrmSellprintteams)接口
 *
 * @author ay
 * @since 2023-07-17 20:45:01
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售打印项目分类设置表")
@RequestMapping("crmSellprintteams")
public class CrmSellprintteamsController extends BaseController {
    /**
     * 服务对象
     */
    private final CrmSellprintteamsService crmSellprintteamsService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple   分页参数
     * @param crmSellprintteams 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询销售打印项目分类设置表")
    public R<IPage<CrmSellprintteams>> getPage(PageParamSimple pageParamSimple, CrmSellprintteams crmSellprintteams) {
        PageParam<CrmSellprintteams> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.crmSellprintteamsService.getPage(page, crmSellprintteams));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查销售打印项目分类设置表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<CrmSellprintteams> selectOne(@PathVariable String id) {
        return R.ok(this.crmSellprintteamsService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param crmSellprintteams 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加销售打印项目分类设置表")
    @Log(title = "销售打印项目分类设置表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"crmSellprintteams.id"})
    public R insert(@RequestBody CrmSellprintteams crmSellprintteams) {
        return R.toResult(this.crmSellprintteamsService.save(crmSellprintteams));
    }

    /**
     * 修改数据
     *
     * @param crmSellprintteams 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新销售打印项目分类设置表")
    @Log(title = "销售打印项目分类设置表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody CrmSellprintteams crmSellprintteams) {
        return R.toResult(this.crmSellprintteamsService.updateById(crmSellprintteams));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除销售打印项目分类设置表")
    @Log(title = "销售打印项目分类设置表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.crmSellprintteamsService.removeByIds(ids));
    }
}

