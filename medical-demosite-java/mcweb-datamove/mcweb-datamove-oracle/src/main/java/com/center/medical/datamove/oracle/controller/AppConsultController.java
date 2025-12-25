package com.center.medical.datamove.oracle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.oracle.bean.model.AppConsult;
import com.center.medical.datamove.oracle.service.AppConsultService;
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
 * 微信小程序医生咨询(AppConsult)接口
 *
 * @author ay
 * @since 2023-07-18 09:11:24
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "微信小程序医生咨询")
@RequestMapping("appConsult")
public class AppConsultController extends BaseController {
    /**
     * 服务对象
     */
    private final AppConsultService appConsultService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param appConsult      查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询微信小程序医生咨询")
    public R<IPage<AppConsult>> getPage(PageParamSimple pageParamSimple, AppConsult appConsult) {
        PageParam<AppConsult> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.appConsultService.getPage(page, appConsult));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查微信小程序医生咨询详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<AppConsult> selectOne(@PathVariable String id) {
        return R.ok(this.appConsultService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param appConsult 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加微信小程序医生咨询")
    @Log(title = "微信小程序医生咨询", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"appConsult.id"})
    public R insert(@RequestBody AppConsult appConsult) {
        return R.toResult(this.appConsultService.save(appConsult));
    }

    /**
     * 修改数据
     *
     * @param appConsult 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新微信小程序医生咨询")
    @Log(title = "微信小程序医生咨询", businessType = BusinessType.UPDATE)
    public R update(@RequestBody AppConsult appConsult) {
        return R.toResult(this.appConsultService.updateById(appConsult));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除微信小程序医生咨询")
    @Log(title = "微信小程序医生咨询", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.appConsultService.removeByIds(ids));
    }
}

