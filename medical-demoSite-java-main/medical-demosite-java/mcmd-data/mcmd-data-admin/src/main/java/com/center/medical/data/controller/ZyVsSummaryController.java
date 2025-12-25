package com.center.medical.data.controller;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.DrugDisease;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.service.*;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.service.*;
import com.center.medical.service.DrugDiseaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职业检查设置-职业体检处理意见(ZyVsSummary)表控制层
 *
 * @author ay
 * @since 2022-11-17 11:39:40
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "职业检查设置-职业体检处理意见")
@RequestMapping("/zyVsSummary")
public class ZyVsSummaryController extends BaseController {
    /**
     * 服务对象
     */
    private final ZyVsSummaryService zyVsSummaryService;
    private final MapperFacade mapperFacade;
    private final Snowflake snowflake;
    private final HarmService harmService;
    private final DrugDiseaseService drugDiseaseService;
    private final OccupationDiseastService occupationDiseastService;
    private final ZyHarmClassService zyHarmClassService;
    private final ZySummaryService zySummaryService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param zyVsSummary     查询实体
     * @return 所有数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询JC职业病处理意见")
    public R<IPage<ZyVsSummary>> selectAll(PageParamSimple pageParamSimple, ZyVsSummary zyVsSummary) {
        PageParam<ZyVsSummary> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.zyVsSummaryService.getPage(page, zyVsSummary));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查JC职业病处理意见详情")
    public R<ZyVsSummary> selectOne(@PathVariable String id) {
        return R.ok(this.zyVsSummaryService.getInfoById(id));
    }

    /**
     * 新增或修改数据
     *
     * @param zyVsSummary 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "新增或修改数据", notes = "新增或修改JC职业病处理意见")
    @Log(title = "JC职业病处理意见", businessType = BusinessType.INSERT)
    public R saveOrUpdate(@RequestBody ZyVsSummary zyVsSummary) {
        String s = zyVsSummaryService.saveOrUpdateZyVsSummary(zyVsSummary);
        return R.ok(s);
    }

    /**
     * 批量保存
     *
     * @param zyVsSummary 实体对象
     * @return 修改结果
     */
    @PostMapping("/saveBatch")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "批量保存", notes = "批量保存JC职业病处理意见")
    @Log(title = "JC职业病处理意见", businessType = BusinessType.INSERT)
    public R update(@RequestBody List<ZyVsSummary> zyVsSummary) {
        for (ZyVsSummary vsSummary : zyVsSummary) {
            String[] jhysArr = vsSummary.getOccupationDiagnosis().split(",");
            //设置属性
            vsSummary.setDbUser(SecurityUtils.getUsername());
            vsSummary.setIsDelete(0);
            for(String jhysId:jhysArr){
                vsSummary.setId(null);
                Harm harm = harmService.getInfoById(jhysId);
                vsSummary.setHealthEvaluationClass(harm.getHarmClass());
                vsSummary.setInputCode(harm.getInputCode());
                vsSummary.setForPersonInfluence(harm.getAffect());
                vsSummary.setOccupationDiagnosis(jhysId);
                zyVsSummaryService.save(vsSummary);
            }
        }
        return R.ok();
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/remove/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除JC职业病处理意见")
    @Log(title = "JC职业病处理意见", businessType = BusinessType.DELETE)
    public R delete(@PathVariable String ids) {
        String s = zyVsSummaryService.removeZyVsSummary(ids);
        return R.ok(s);
    }


    /**
     * 获取结论下拉列表
     *
     * @param pageParamSimple
     * @param inputCode
     * @return
     */
    @GetMapping("/getJcjlData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取结论下拉列表", notes = "获取结论下拉列表")
    public R getJcjlData(PageParamSimple pageParamSimple, String inputCode) {
        PageParam<ZySummary> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<ZySummary> jcjlData = zySummaryService.getJcjlData(page, inputCode);
        return R.ok(jcjlData);
    }


    /**
     * 获取禁忌疾病
     *
     * @param pageParamSimple
     * @param inputCode
     * @return
     */
    @GetMapping("/getJjjbData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取禁忌疾病", notes = "获取禁忌疾病")
    public R getJjjbData(PageParamSimple pageParamSimple, String inputCode) {
        PageParam<DrugDisease> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<DrugDisease> data = drugDiseaseService.getJjjbData(page, inputCode);
        return R.ok(data);
    }

    /**
     * 根据输入码获取职业病
     *
     * @param pageParamSimple
     * @param inputCode
     * @return
     */
    @GetMapping("/getZybData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "根据输入码获取职业病", notes = "根据输入码获取职业病")
    public R getZybData(PageParamSimple pageParamSimple, String inputCode) {
        PageParam<OccupationDiseast> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<OccupationDiseast> data = occupationDiseastService.getZybData(page, inputCode);
        return R.ok(data);
    }


    /**
     * 获取危害因素分类下拉
     *
     * @param pageParamSimple
     * @return
     */
    @GetMapping("/getWhflData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取危害因素分类下拉", notes = "获取危害因素分类下拉")
    public R getWhflData(PageParamSimple pageParamSimple) {
        PageParam<ZyHarmClass> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<ZyHarmClass> data = zyHarmClassService.page(page, new QueryWrapper<ZyHarmClass>().eq("is_delete", 0));
        return R.ok(data);
    }


    /**
     * 获取危害因素下拉
     *
     * @param inputCode
     * @return
     */
    @GetMapping("/getWhysData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取危害因素下拉", notes = "获取危害因素下拉")
    public R getWhysData(String inputCode) {
        QueryWrapper<Harm> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(inputCode)) {
            queryWrapper.like("input_code", inputCode.trim().toUpperCase());
        }
        queryWrapper.eq("is_delete", 0);
        List<Harm> data = harmService.list(queryWrapper);
        return R.ok(data);
    }


    /**
     * 根据危害因素获取其输入码
     *
     * @param occupationDiagnosis
     * @return
     */
    @GetMapping("/getInputCode")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "根据危害因素获取其输入码", notes = "根据危害因素获取其输入码")
    public R<Harm> getInputCode(String occupationDiagnosis) {
        Harm data = harmService.getOne(new QueryWrapper<Harm>().eq("id", occupationDiagnosis)
                .eq("is_delete", 0));
        return R.ok(data);
    }


    /**
     * 同步
     * @return
     */
    @GetMapping("/synchronize")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "同步", notes = "同步")
    public R synchronize() {
        String result = "";
        try {
            result = zyVsSummaryService.synchonize();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return R.fail("同步失败！");
        }
        return R.ok(result);
    }


    /**
     * 获取危害因素下拉
     * @param inputCode
     * @return
     */
    @GetMapping("/getWhysDataPage")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取危害因素下拉分页", notes = "获取危害因素下拉分页")
    public R getWhysData(PageParamSimple pageParamSimple,String inputCode) {
        PageParam<Harm> page = mapperFacade.map(pageParamSimple, PageParam.class);
        QueryWrapper<Harm> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(inputCode)) {
            queryWrapper.like("input_code", inputCode.trim().toUpperCase());
        }
        queryWrapper.eq("is_delete", 0);
        PageParam<Harm> iPage = harmService.page(page, queryWrapper);
        return R.ok(iPage);
    }


}

