package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.MdPeispatientChargeOther;
import com.center.medical.datamove.common.service.MdPeispatientChargeOtherService;
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
 * 体检者其他缴费(MdPeispatientChargeOther)接口
 *
 * @author ay
 * @since 2023-07-17 20:48:11
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检者其他缴费")
@RequestMapping("mdPeispatientChargeOther")
public class MdPeispatientChargeOtherController extends BaseController {
    /**
     * 服务对象
     */
    private final MdPeispatientChargeOtherService mdPeispatientChargeOtherService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple          分页参数
     * @param mdPeispatientChargeOther 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询体检者其他缴费")
    public R<IPage<MdPeispatientChargeOther>> getPage(PageParamSimple pageParamSimple, MdPeispatientChargeOther mdPeispatientChargeOther) {
        PageParam<MdPeispatientChargeOther> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdPeispatientChargeOtherService.getPage(page, mdPeispatientChargeOther));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查体检者其他缴费详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<MdPeispatientChargeOther> selectOne(@PathVariable String id) {
        return R.ok(this.mdPeispatientChargeOtherService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param mdPeispatientChargeOther 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加体检者其他缴费")
    @Log(title = "体检者其他缴费", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"mdPeispatientChargeOther.id"})
    public R insert(@RequestBody MdPeispatientChargeOther mdPeispatientChargeOther) {
        return R.toResult(this.mdPeispatientChargeOtherService.save(mdPeispatientChargeOther));
    }

    /**
     * 修改数据
     *
     * @param mdPeispatientChargeOther 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新体检者其他缴费")
    @Log(title = "体检者其他缴费", businessType = BusinessType.UPDATE)
    public R update(@RequestBody MdPeispatientChargeOther mdPeispatientChargeOther) {
        return R.toResult(this.mdPeispatientChargeOtherService.updateById(mdPeispatientChargeOther));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除体检者其他缴费")
    @Log(title = "体检者其他缴费", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.mdPeispatientChargeOtherService.removeByIds(ids));
    }
}

