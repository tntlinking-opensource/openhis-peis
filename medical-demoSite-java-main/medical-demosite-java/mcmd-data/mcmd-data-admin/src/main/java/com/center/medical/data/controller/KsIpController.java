package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.KsIp;
import com.center.medical.data.bean.param.KsIpPageParam;
import com.center.medical.data.service.KsIpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 科室IP(KsIp)表控制层
 *
 * @author 路飞船长
 * @since 2023-04-20 20:02:22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室IP")
@RequestMapping("base/ksip")
public class KsIpController extends BaseController {
    /**
     * 服务对象
     */
    private final KsIpService ksIpService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param            查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询科室IP")
    public R<IPage<KsIp>> getPage(PageParamSimple pageParamSimple, KsIpPageParam param) {
        PageParam<KsIp> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.ksIpService.getPage(page, param));
    }

    /**
     * 获取科室列表
     *
     * @param ksId 绑定的科室ID
     * @return 所有数据
     */
    @GetMapping("/list/{ksId}")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取科室列表", notes = "获取科室列表")
    @ApiImplicitParam(name = "ksId", value = "绑定的科室ID")
    public R<List<KsIp>> getList(@PathVariable("ksId") String ksId) {
        String branchId = SecurityUtils.getCId();
        return R.ok(this.ksIpService.getList(ksId, branchId));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查科室IP详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<KsIp> selectOne(@PathVariable String id) {
        return R.ok(this.ksIpService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param ksIp 实体对象
     * @return 新增结果
     */
    @PostMapping("saOrUp")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加或更新", notes = "添加科室IP")
    @Log(title = "科室IP", businessType = BusinessType.INSERT)
    public R insert(@RequestBody KsIp ksIp) {
        return R.toResult(this.ksIpService.saveOrUpdate(ksIp));
    }



    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除科室IP")
    @Log(title = "科室IP", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.ksIpService.removeByIds(ids));
    }
}

