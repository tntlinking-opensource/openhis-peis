package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.OccupationDiseastClass;
import com.center.medical.data.service.OccupationDiseastClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 *职业检查设置-职业病名称分类(OccupationDiseastClass)表控制层
 *
 * @author ay
 * @since 2022-11-16 19:41:28
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业检查设置-职业病名称分类")
@RequestMapping("occupationDiseastClass")
public class OccupationDiseastClassController extends BaseController {
    /**
     * 服务对象
     */
    private final OccupationDiseastClassService occupationDiseastClassService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple        分页参数
     * @param occupationDiseastClass 查询实体
     * @return 所有数据
     */
    @GetMapping("/getListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询JC职业病种分类")
    public R<IPage<OccupationDiseastClass>> getListData(PageParamSimple pageParamSimple, OccupationDiseastClass occupationDiseastClass) {
        PageParam<OccupationDiseastClass> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.occupationDiseastClassService.getList(page, occupationDiseastClass));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查JC职业病种分类详情")
    public R<OccupationDiseastClass> selectOne(@PathVariable String id) {
        return R.ok(this.occupationDiseastClassService.getInfoById(id));
    }

    /**
     * 保存或更新
     *
     * @param occupationDiseastClass 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "保存或更新", notes = "保存或更新JC职业病种分类")
    @Log(title = "JC职业病种分类", businessType = BusinessType.INSERT)
    public R saveOrUpdate(@RequestBody OccupationDiseastClass occupationDiseastClass) {
        String s = occupationDiseastClassService.saveOrUpdateOccu(occupationDiseastClass);
        return R.ok(s);
    }


    /**
     * 跳转编辑页返回数据
     *
     * @param id 实体对象
     * @return 修改结果
     */
    @GetMapping("/edit/{id}")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "跳转编辑页返回数据", notes = "跳转编辑页返回数据")
    public R update(@PathVariable String id) {
        return R.ok(occupationDiseastClassService.getInfoById(id));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/remove/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除JC职业病种分类")
    @Log(title = "JC职业病种分类", businessType = BusinessType.DELETE)
    public R delete(@PathVariable String ids) {
        String s = occupationDiseastClassService.removeOccupa(ids);
        return R.ok(s);
    }
}

