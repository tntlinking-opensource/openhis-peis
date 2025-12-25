package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.Whysqzfw;
import com.center.medical.data.service.BasexamltemService;
import com.center.medical.data.service.HarmService;
import com.center.medical.data.service.WhysqzfwService;
import com.center.medical.data.service.ZyHarmClassService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 职业检查设置-危害因素标准范围维护(Whysqzfw)表控制层
 *
 * @author ay
 * @since 2022-11-16 14:58:58
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业检查设置-危害因素标准范围维护")
@RequestMapping("whysqzfw")
public class WhysqzfwController extends BaseController {
    /**
     * 服务对象
     */
    private final WhysqzfwService whysqzfwService;
    private final MapperFacade mapperFacade;
    private final HarmService harmService;
    private final BasexamltemService basexamltemService;
    private final ZyHarmClassService zyHarmClassService;

    /**
     * 取值范围数据获取
     *
     * @param pageParamSimple
     * @param whysqzfw        查询实体
     * @return 所有数据
     */
    @GetMapping("/getQzfwData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "取值范围数据获取", notes = "分页查询JC危害因素取值范围")
    public R<IPage<Whysqzfw>> getQzfwData(PageParamSimple pageParamSimple, Whysqzfw whysqzfw) {
        PageParam<Whysqzfw> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.whysqzfwService.getList(page, whysqzfw));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查JC危害因素取值范围详情")
    public R<Whysqzfw> selectOne(@PathVariable String id) {
        return R.ok(this.whysqzfwService.getInfoById(id));
    }

    /**
     * 取值范围数据保存编辑
     *
     * @param whysqzfw 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "取值范围数据保存编辑", notes = "取值范围数据保存编辑")
    @Log(title = "JC危害因素取值范围", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"whysqzfw.id"})
    public R saveOrUpdate(@RequestBody Whysqzfw whysqzfw) {
        String s = whysqzfwService.saveOrUpdateWhysqzfw(whysqzfw);
        return R.ok(s);
    }

    /**
     * 返回编辑数据
     *
     * @param id 实体对象
     * @return 修改结果
     */
    @GetMapping("/edit")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "返回编辑数据", notes = "返回编辑数据")
    public R edit(String id) {
        Whysqzfw whysqzfw = whysqzfwService.getInfoById(id);
        Harm harm = harmService.getInfoById(whysqzfw.getHarmName());
        Basexamltem basExamLtem = basexamltemService.getInfoById(whysqzfw.getJcId());
        Map map = new HashMap();
        map.put("Whysqzfw", whysqzfw);
        map.put("Harm", harm);
        map.put("Basexamltem", basExamLtem);
        return R.ok(map);
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除JC危害因素取值范围")
    @Log(title = "JC危害因素取值范围", businessType = BusinessType.DELETE)
    public R delete(@PathVariable String ids) {
        String s = whysqzfwService.removeWhysqzfw(ids);
        return R.ok(s);
    }


    /**
     * 获取所有危害因素
     *
     * @return
     */
    @GetMapping("/getAllHarmname")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "获取所有危害因素", notes = "获取所有危害因素")
    public R getAllHarmname(PageParam<Harm> page, String inputCode) {
        return R.ok(this.harmService.getAllHarmname(page, inputCode));
    }

    /**
     * 获取检查项目名称
     *
     * @param pageParamSimple
     * @param inputCode
     * @return
     */
    @GetMapping("/getAllJcid")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "获取检查项目名称", notes = "获取检查项目名称")
    public R getAllJcid(PageParamSimple pageParamSimple, String inputCode) {
        PageParam<Basexamltem> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.basexamltemService.getAllJcid(page, inputCode));
    }


    /**
     * 通过项目名称获取接口编码
     *
     * @param data
     * @return
     */
    @GetMapping("/getXmData")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "通过项目名称获取接口编码", notes = "通过项目名称获取接口编码")
    public R getXmData(String data) {
        return R.ok(this.basexamltemService.getInfoById(data));
    }
}

