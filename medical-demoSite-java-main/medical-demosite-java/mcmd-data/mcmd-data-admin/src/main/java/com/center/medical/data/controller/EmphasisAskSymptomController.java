package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.EmphasisAskSymptom;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.service.EmphasisAskSymptomService;
import com.center.medical.data.service.HarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 职业检查设置-危害因素重点询问症状(EmphasisAskSymptom)表控制层
 *
 * @author ay
 * @since 2022-11-16 11:19:47
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业检查设置-危害因素重点询问症状")
@RequestMapping("emphasisAskSymptom")
public class EmphasisAskSymptomController extends BaseController {
    /**
     * 服务对象
     */
    private final EmphasisAskSymptomService emphasisAskSymptomService;
    private final HarmService harmService;

    /**
     * 展示重点询问症状列表数据
     *
     * @param page               分页对象
     * @param emphasisAskSymptom 查询实体
     * @return 所有数据
     */
    @GetMapping("/getAskData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "展示重点询问症状列表数据", notes = "分页查询JC重点询问症状表")
    public R<IPage<EmphasisAskSymptom>> getAskData(PageParam<EmphasisAskSymptom> page, EmphasisAskSymptom emphasisAskSymptom) {
        return R.ok(this.emphasisAskSymptomService.getList(page, emphasisAskSymptom));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查JC重点询问症状表详情")
    public R<EmphasisAskSymptom> selectOne(@PathVariable String id) {
        return R.ok(this.emphasisAskSymptomService.getInfoById(id));
    }

    /**
     * 新增或修改数据
     *
     * @param emp 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "批量新增", notes = "批量新增新增JC重点询问症状表")
    @Log(title = "JC重点询问症状表", businessType = BusinessType.INSERT)
    public R saveOrUpdate(@RequestBody List<EmphasisAskSymptom> emp) {
        String s = emphasisAskSymptomService.saveOrUpdateEmphasis(emp);
        return R.ok(s);
    }


    @PostMapping("/saveEdit")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "保存", notes = "保存单个")
    @Log(title = "JC重点询问症状表", businessType = BusinessType.INSERT)
    public R saveEdit(@RequestBody EmphasisAskSymptom emp) {
        String s = emphasisAskSymptomService.saveEdit(emp);
        return R.ok(s);
    }

    /**
     * 返回编辑数据
     *
     * @return 修改结果
     */
    @PutMapping("/edit")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "返回编辑数据", notes = "返回编辑数据")
    @Log(title = "JC重点询问症状表", businessType = BusinessType.UPDATE)
    public R edit(String id) {
        EmphasisAskSymptom emphasisAskSymptom = emphasisAskSymptomService.getInfoById(id);
        Harm harm = harmService.getOne(new QueryWrapper<Harm>().eq("harm_name", emphasisAskSymptom.getHarmname()));
        Map map = new HashMap();
        map.put("EmphasisAskSymptom",emphasisAskSymptom);
        map.put("harm",harm);
        return R.ok(map);
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/remove/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除JC重点询问症状表")
    @Log(title = "JC重点询问症状表", businessType = BusinessType.DELETE)
    public R remove(@PathVariable String ids) {
        String s = emphasisAskSymptomService.removeEmp(ids);
        return R.ok(s);
    }


    @PutMapping("/synchronize")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "同步", notes = "同步")
    @Log(title = "JC重点询问症状表", businessType = BusinessType.UPDATE)
    public R synchronize(String id) {
        String result = "";
        try {
            result = emphasisAskSymptomService.synchonize();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return R.fail(null, "同步失败！");
        }
        return R.ok(null, result);
    }


}

