package com.center.medical.data.controller;

import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.data.bean.model.Yblx;
import com.center.medical.data.bean.param.YblxParam;
import com.center.medical.data.service.YblxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 标本类型(Yblx)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-29 11:23:15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "标本类型")
@RequestMapping("data/yblx")
public class YblxController extends BaseController {
    /**
     * 服务对象
     */
    private final YblxService yblxService;
    private final MapperFacade mapperFacade;

//    /**
//     * 分页查询所有数据
//     *
//     * @param pageParamSimple 分页参数
//     * @param yblx            查询条件
//     * @return 所有数据
//     */
//    @GetMapping("/page")
//    //@PreAuthorize("@ss.hasPermi('::list')")
//    @ApiOperation(value = "分页查询", notes = "分页查询样本类型")
//    public R<IPage<Yblx>> getPage(PageParamSimple pageParamSimple, Yblx yblx) {
//        PageParam<Yblx> page = mapperFacade.map(pageParamSimple, PageParam.class);
//        return R.ok(this.yblxService.getPage(page, yblx));
//    }

    /**
     * 根据条件查询列表数据
     *
     * @param param 查询参数
     * @return 列表数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据输入码查询列表数据", notes = "根据条件查询列表数据")
    public R<List<Yblx>> getList(YblxParam param) {
        return R.ok(this.yblxService.getList(param));
    }

//    /**
//     * 通过主键查询单条数据
//     *
//     * @param id 主键
//     * @return 单条数据
//     */
//    @GetMapping("{id}")
//    //@PreAuthorize("@ss.hasPermi('::info')")
//    @ApiOperation(value = "详情", notes = "根据id查样本类型详情")
//    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
//    public R<Yblx> selectOne(@PathVariable String id) {
//        return R.ok(this.yblxService.getInfoById(id));
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param yblx 实体对象
//     * @return 新增结果
//     */
//    @PostMapping
//    //@PreAuthorize("@ss.hasPermi('::add')")
//    @ApiOperation(value = "添加", notes = "添加样本类型")
//    @Log(title = "样本类型", businessType = BusinessType.INSERT)
//    @ApiOperationSupport(ignoreParameters = {"yblx.id"})
//    public R insert(@RequestBody Yblx yblx) {
//        return R.toResult(this.yblxService.save(yblx));
//    }
//
//    /**
//     * 修改数据
//     *
//     * @param yblx 实体对象
//     * @return 修改结果
//     */
//    @PutMapping
//    //@PreAuthorize("@ss.hasPermi('::edit')")
//    @ApiOperation(value = "更新", notes = "更新样本类型")
//    @Log(title = "样本类型", businessType = BusinessType.UPDATE)
//    public R update(@RequestBody Yblx yblx) {
//        return R.toResult(this.yblxService.updateById(yblx));
//    }
//
//    /**
//     * 删除数据
//     *
//     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
//     * @return 删除结果
//     */
//    @DeleteMapping("/{ids}")
//    //@PreAuthorize("@ss.hasPermi('::remove')")
//    @ApiOperation(value = "删除", notes = "删除样本类型")
//    @Log(title = "样本类型", businessType = BusinessType.DELETE)
//    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
//    public R delete(@PathVariable List<String> ids) {
//        return R.toResult(this.yblxService.removeByIds(ids));
//    }
}

