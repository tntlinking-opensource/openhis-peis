package com.center.medical.datamove.oracle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.oracle.bean.model.TjbbXyjc;
import com.center.medical.datamove.oracle.service.TjbbXyjcService;
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
 * KS血压检测(TjbbXyjc)接口
 *
 * @author ay
 * @since 2023-07-18 09:25:27
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "KS血压检测")
@RequestMapping("tjbbXyjc")
public class TjbbXyjcController extends BaseController {
    /**
     * 服务对象
     */
    private final TjbbXyjcService tjbbXyjcService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param tjbbXyjc        查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询KS血压检测")
    public R<IPage<TjbbXyjc>> getPage(PageParamSimple pageParamSimple, TjbbXyjc tjbbXyjc) {
        PageParam<TjbbXyjc> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.tjbbXyjcService.getPage(page, tjbbXyjc));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查KS血压检测详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<TjbbXyjc> selectOne(@PathVariable String id) {
        return R.ok(this.tjbbXyjcService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param tjbbXyjc 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加KS血压检测")
    @Log(title = "KS血压检测", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"tjbbXyjc.id"})
    public R insert(@RequestBody TjbbXyjc tjbbXyjc) {
        return R.toResult(this.tjbbXyjcService.save(tjbbXyjc));
    }

    /**
     * 修改数据
     *
     * @param tjbbXyjc 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新KS血压检测")
    @Log(title = "KS血压检测", businessType = BusinessType.UPDATE)
    public R update(@RequestBody TjbbXyjc tjbbXyjc) {
        return R.toResult(this.tjbbXyjcService.updateById(tjbbXyjc));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除KS血压检测")
    @Log(title = "KS血压检测", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.tjbbXyjcService.removeByIds(ids));
    }
}

