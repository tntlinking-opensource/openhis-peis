package com.center.medical.system.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.system.bean.model.BusinessSource;
import com.center.medical.system.bean.param.BusinessSourceParam;
import com.center.medical.system.service.BusinessSourceService;
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
 * 合作第三方(BusinessSource)接口
 *
 * @author 路飞
 * @since 2023-07-05 15:27:24
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "合作第三方")
@RequestMapping("businessSourc")
public class BusinessSourceController extends BaseController {
    /**
     * 服务对象
     */
    private final BusinessSourceService businessSourceService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询数据表配置")
    public R<IPage<BusinessSource>> getPage(PageParamSimple pageParamSimple, BusinessSourceParam param) {
        PageParam<BusinessSource> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.businessSourceService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查数据表配置详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<BusinessSource> selectOne(@PathVariable Long id) {
        return R.ok(this.businessSourceService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param businessSource 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加数据表配置")
    @Log(title = "数据表配置", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"businessSource.id"})
    public R insert(@RequestBody BusinessSource businessSource) {
        boolean b = this.businessSourceService.save(businessSource);
        return R.toResult(b);
    }

    /**
     * 修改数据
     *
     * @param businessSource 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新数据表配置")
    @Log(title = "数据表配置", businessType = BusinessType.UPDATE)
    public R update(@RequestBody BusinessSource businessSource) {
        boolean b = this.businessSourceService.updateById(businessSource);
        return R.toResult(b);
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除数据表配置")
    @Log(title = "数据表配置", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Integer> ids) {
        return R.toResult(this.businessSourceService.removeByIds(ids));
    }
}


