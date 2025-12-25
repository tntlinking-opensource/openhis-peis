package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Patienttype;
import com.center.medical.data.service.PatienttypeService;
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
 * 体检者类型(Patienttype)表控制层
 *
 * @author 路飞船长
 * @since 2022-12-07 19:28:11
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检者类型")
@RequestMapping("patienttype")
public class PatienttypeController extends BaseController {
    /**
     * 服务对象
     */
    private final PatienttypeService patienttypeService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param patienttype     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询体检者类型")
    public R<IPage<Patienttype>> getPage(PageParamSimple pageParamSimple, Patienttype patienttype) {
        PageParam<Patienttype> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.patienttypeService.getPage(page, patienttype));
    }

    /**
     * 查询全部
     *
     * @param srm 输入码
     * @return 所有数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询全部", notes = "查询全部体检者类型")
    @ApiImplicitParam(name = "srm", value = "输入码")
    public R<List<Patienttype>> getPage(@RequestParam("srm") String srm) {
        LambdaUpdateWrapper<Patienttype> wrapper = new LambdaUpdateWrapper<>();
        if (StringUtils.isNotBlank(srm)) {
            wrapper.like(Patienttype::getInputCode, srm);
        }
        return R.ok(this.patienttypeService.list(wrapper));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查体检者类型详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Patienttype> selectOne(@PathVariable String id) {
        return R.ok(this.patienttypeService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param patienttype 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加体检者类型")
    @Log(title = "体检者类型", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"patienttype.id"})
    public R insert(@RequestBody Patienttype patienttype) {
        return R.toResult(this.patienttypeService.save(patienttype));
    }

    /**
     * 修改数据
     *
     * @param patienttype 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新体检者类型")
    @Log(title = "体检者类型", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Patienttype patienttype) {
        return R.toResult(this.patienttypeService.updateById(patienttype));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除体检者类型")
    @Log(title = "体检者类型", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        // TODO ?需讨论是否删除
        return R.toResult(this.patienttypeService.removeByIds(ids));
    }
}

