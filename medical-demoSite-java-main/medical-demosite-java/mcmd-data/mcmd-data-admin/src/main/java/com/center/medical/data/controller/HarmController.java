package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.ZyHarmClass;
import com.center.medical.data.bean.param.HarmParam;
import com.center.medical.data.service.HarmService;
import com.center.medical.data.service.ZyHarmClassService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 职业检查设置-职业健康危害因素(Harm)表控制层
 *
 * @author ay
 * @since 2022-11-15 11:56:23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业检查设置-职业健康危害因素")
@RequestMapping("harm")
public class HarmController extends BaseController {
    /**
     * 服务对象
     */
    private final HarmService harmService;
    private final ZyHarmClassService zyHarmClassService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple  分页对象
     * @param param 查询实体
     * @return 所有数据
     */
    @GetMapping("/getHarmData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询JC危害因素")
    public R<IPage<Harm>> getHarmData(PageParamSimple pageParamSimple, HarmParam param) {
        PageParam<Harm> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.harmService.getHarmData(page, param));
    }

    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param param 查询实体
     * @return 所有数据
     */
    @GetMapping("/getHarmDataSimple")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询JC危害因素")
    public R<IPage<Harm>> getHarmDataSimple(PageParam<Harm> page, HarmParam param) {
        return R.ok(this.harmService.getHarmDataSimple(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查JC危害因素详情")
    public R<Harm> selectOne(@PathVariable String id) {
        return R.ok(this.harmService.getInfoById(id));
    }

    /**
     * 新增或修改职业危害因素
     *
     * @param harm 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "新增或修改", notes = "新增或修改职业危害因素")
    @Log(title = "JC危害因素", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"harm.id"})
    public R saveOrUpdate(@RequestBody Harm harm) {
        String s = harmService.saveOrUpdateHarm(harm);
        return R.ok(s);
    }

    /**
     * 修改数据
     *
     * @param harm 实体对象
     * @return 修改结果
     */
    @PutMapping("/edit")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新JC危害因素")
    @Log(title = "JC危害因素", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Harm harm) {
        return R.toResult(this.harmService.updateById(harm));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/remove/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除JC危害因素")
    @Log(title = "JC危害因素", businessType = BusinessType.DELETE)
    public R delete(@PathVariable String ids) {
        String s = harmService.removeHarm(ids);
        return R.ok(s);
    }

    /**
     * 获得所有的职业危害因素
     * @param pageParamSimple
     * @param inputCode
     * @return
     */
    @GetMapping("/findAllHarmclass")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获得所有的职业危害因素", notes = "获得所有的职业危害因素")
    public R<IPage<ZyHarmClass>> findAllHarmclass(PageParamSimple pageParamSimple, String inputCode) {
        PageParam<ZyHarmClass> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.zyHarmClassService.findAllHarmclass(page, inputCode));
    }


}

