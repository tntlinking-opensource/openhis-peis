package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.model.Customerfollow;
import com.center.medical.sellcrm.bean.param.CustomerfollowPram;
import com.center.medical.sellcrm.service.CustomerfollowService;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户关系-阶段跟踪
 *
 * @author 路飞船长
 * @since 2022-11-21 09:06:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 3)
@Api(tags = "客户关系-阶段跟踪")
@RequestMapping("sell/customerfollow")
public class CustomerfollowController extends BaseController {
    /**
     * 服务对象
     */
    private final CustomerfollowService customerfollowService;
    private final MapperFacade mapperFacade;
    private final SellcustomerService sellcustomerService;

    //TODO 权限设置

    /**
     * 分页查询阶段跟踪列表
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取有跟踪记录的客户列表", notes = "分页查询阶段跟踪列表", position = 1)
    public R<IPage<Customerfollow>> getPage(PageParamSimple pageParamSimple, CustomerfollowPram param) {
        PageParam<Customerfollow> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.customerfollowService.getPage(page, param));
    }

    /**
     * 获取指定客户跟踪记录
     *
     * @param cumId 客户ID
     * @return 所有数据
     */
    @GetMapping("/getListByCumId/{cumId}")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取指定客户跟踪记录", notes = "获取指定客户跟踪记录", position = 2)
    @ApiImplicitParam(name = "cumId", value = "客户ID")
    public R<List<Customerfollow>> getListByCumId(@PathVariable(value = "cumId") String cumId) {
        return R.ok(this.customerfollowService.getListByCumId(cumId));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查客户跟踪表详情", position = 3)
    @ApiImplicitParam(name = "id", value = "查询的对象ID")
    public R<Customerfollow> selectOne(@PathVariable String id) {
        return R.ok(this.customerfollowService.getInfoById(id));
    }

    /**
     * 新增客户跟进记录
     *
     * @param customerfollow 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "新增", notes = "新增客户跟进记录", position = 4)
    @Log(title = "客户关系-阶段跟踪-新增", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"customerfollow.id"})
    public R insert(@RequestBody Customerfollow customerfollow) {
        return R.toResult(this.customerfollowService.saOrUp(customerfollow));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键集合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除客户跟踪记录", position = 5)
    @Log(title = "客户关系-阶段跟踪-删除", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象id集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.customerfollowService.removeByIds(ids));
    }


}

