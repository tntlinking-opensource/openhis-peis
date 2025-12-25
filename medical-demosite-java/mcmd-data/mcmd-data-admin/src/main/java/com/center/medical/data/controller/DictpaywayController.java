package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Area;
import com.center.medical.bean.model.Dictpayway;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.service.AreaService;
import com.center.medical.service.DictpaywayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 收银收款方式(Dictpayway)表控制层
 *
 * @author ay
 * @since 2022-11-18 11:52:55
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "收银收款方式")
@RequestMapping("dictpayway")
public class DictpaywayController extends BaseController {
    /**
     * 服务对象
     */
    private final DictpaywayService dictpaywayService;
    private final MapperFacade mapperFacade;
    private final AreaService areaService;

    /**
     * 获取全部支付方式
     *
     * @param pageParamSimple 分页参数
     * @param key             inputCode
     * @return 所有数据
     */
    @GetMapping("/getPayWayData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询JC支付方式表")
    public R<IPage<Dictpayway>> getPayWayData(PageParamSimple pageParamSimple, String key, String type) {
        PageParam<Dictpayway> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //设置条件
        QueryWrapper<Dictpayway> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 0).orderByAsc("seq");
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.like("input_code", key.trim().toUpperCase());
        }
        //预收不能选择记账
        if ("1".equals(type)) {
            queryWrapper.ne("id", "4");
        }
        PageParam<Dictpayway> pageParam = dictpaywayService.page(page, queryWrapper);
        return R.ok(pageParam);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查JC支付方式表详情")
    public R<Dictpayway> selectOne(@PathVariable String id) {
        return R.ok(this.dictpaywayService.getInfoById(id));
    }

    /**
     * 新增或修改数据
     *
     * @param dictpayway 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "新增或修改数据", notes = "新增或修改JC支付方式表")
    @Log(title = "JC支付方式表", businessType = BusinessType.INSERT)
    public R saveOrUpdate(@RequestBody Dictpayway dictpayway) {
        String s = dictpaywayService.saveOrUpdateDictpayway(dictpayway);
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
    @ApiOperation(value = "删除", notes = "删除JC支付方式表")
    @Log(title = "JC支付方式表", businessType = BusinessType.DELETE)
    public R delete(@PathVariable String ids) {
        String s = dictpaywayService.removeDictpayway(ids);
        return R.ok(s);
    }


    /**
     * sell/customer
     * 获取籍贯
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getAreaData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取籍贯", notes = "获取籍贯")
    public R getAreaData(PageParamSimple pageParamSimple, String key) {
        PageParam<Area> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //设置条件
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.like("input_code", key.trim().toUpperCase());
        }
        PageParam<Area> pageParam = areaService.page(page, queryWrapper);
        return R.ok(pageParam);
    }


    /**
     * 客户升级
     *
     * @return
     */
    @GetMapping("/upgrade")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "客户升级", notes = "客户升级")
    public R upgrade() {
        String s = dictpaywayService.upgrade();
        return R.ok(s);
    }


}

