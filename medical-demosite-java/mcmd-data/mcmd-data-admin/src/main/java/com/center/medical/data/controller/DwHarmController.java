package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.DwHarm;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.service.DwHarmService;
import com.center.medical.data.service.HarmService;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.service.SellcustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 单位危害因素(DwHarm)表控制层
 *
 * @author ay
 * @since 2022-11-16 09:19:17
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "单位危害因素")
@RequestMapping("data/dwHarm")
public class DwHarmController extends BaseController {
    /**
     * 服务对象
     */
    private final DwHarmService dwHarmService;
    private final SellcustomerService sellcustomerService;
    private final HarmService harmService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param page   分页对象
     * @param dwHarm 查询实体
     * @return 所有数据
     */
    @GetMapping("/getDwData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取单位职业危害因素数据", notes = "分页查询单位危害因素")
    public R<IPage<DwHarm>> selectAll(PageParam<DwHarm> page, DwHarm dwHarm) {
        return R.ok(this.dwHarmService.getList(page, dwHarm));
    }


    /**
     * 返回编辑数据
     *
     * @param id
     * @return
     */
    @GetMapping("/edit")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "返回编辑数据", notes = "返回编辑数据")
    public R edit(String id) {
        DwHarm dwHarm = dwHarmService.getInfoById(id);
        Sellcustomer sellcustomer = sellcustomerService.getOne(new QueryWrapper<Sellcustomer>().eq("khdwmc", dwHarm.getCompanyName()));
        Harm harm = harmService.getOne(new QueryWrapper<Harm>().eq("harm_name", dwHarm.getHarmName()));
        Map map = new HashMap();
        map.put("DwHarm", dwHarm);
        map.put("Sellcustomer", sellcustomer);
        ;
        map.put("Harm", harm);
        return R.ok(map);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查单位危害因素详情")
    public R<DwHarm> selectOne(@PathVariable String id) {
        return R.ok(this.dwHarmService.getInfoById(id));
    }

    /**
     * 新增或修改
     *
     * @param dwHarm 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "新增或修改", notes = "新增或修改")
    @Log(title = "单位危害因素", businessType = BusinessType.INSERT)
    public R saveOrUpdate(@RequestBody DwHarm dwHarm) {
        String s = dwHarmService.saveOrUpdateDwHarm(dwHarm);
        return R.ok(s);
    }


    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/remove/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "假删单位职业危害因素数据", notes = "假删单位职业危害因素数据")
    @Log(title = "单位危害因素", businessType = BusinessType.DELETE)
    public R delete(@PathVariable String ids) {
        String s = dwHarmService.removeDwHarm(ids);
        return R.toResult(s == "success" ? true : false);

    }

    /**
     * 获取所有的公司客户名称
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getAllCompany")
    @ApiOperation(value = "获取所有的公司客户名称", notes = "获取所有的公司客户名称")
    public R<IPage<Sellcustomer>> getAllCompany(PageParamSimple pageParamSimple, String key) {
        PageParam<Sellcustomer> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<Sellcustomer> allCompany = sellcustomerService.getAllCompany(page, key);
        return R.ok(allCompany);
    }


}

