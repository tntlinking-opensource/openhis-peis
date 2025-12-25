package com.center.medical.pacs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.pacslis.bean.model.PacsBasePart;
import com.center.medical.pacslis.service.PacsBasePartService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * PACS基础配置-部位维护(PacsBasePart)表控制层
 *
 * @author ay
 * @since 2022-12-29 09:23:20
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "PACS基础配置-部位维护")
@RequestMapping("/pacs/pacsBasePart")
public class PacsBasePartController extends BaseController {
    /**
     * 服务对象
     */
    private final PacsBasePartService pacsBasePartService;
    private final MapperFacade mapperFacade;


    /**
     * 【PACS基础配置-部位维护】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【PACS基础配置-部位维护】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(new InterfaceVo("分页查询", "GET", "/pacs/pacsBasePart/page", "11.pacs/lis->PACS基础配置-部位维护->分页查询", null), new InterfaceVo("详情", "GET", "/pacs/pacsBasePart/{id}", "11.pacs/lis->PACS基础配置-部位维护->详情", null), new InterfaceVo("添加或修改", "POST", "/pacs/pacsBasePart/saveOrUpdate", "11.pacs/lis->PACS基础配置-部位维护->添加或修改", null), new InterfaceVo("删除", "DELETE", "/pacs/pacsBasePart/{ids}", "11.pacs/lis->PACS基础配置-部位维护->删除", null), new InterfaceVo("不分页查全部", "GET", "/pacs/pacsBasePart/getAutoCompleteData", "11.pacs/lis->PACS基础配置-部位维护->不分页查全部", null));
        return R.ok(new FunctionVo("11.pacs/lis", "PACS基础配置-部位维护", interfaceVos, "11.pacs/lis"));
    }

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询PACS-部位")
    public R<IPage<PacsBasePart>> getPage(PageParamSimple pageParamSimple) {
        PageParam<PacsBasePart> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.pacsBasePartService.getPage(page));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查PACS-部位详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<PacsBasePart> selectOne(@PathVariable String id) {
        return R.ok(this.pacsBasePartService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param pacsBasePart 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加或修改", notes = "添加或修改")
    @Log(title = "PACS-部位", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"pacsBasePart.id"})
    public R insert(@RequestBody PacsBasePart pacsBasePart) {
        return R.toResult(this.pacsBasePartService.saOrUp(pacsBasePart));
    }


    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除PACS-部位")
    @Log(title = "PACS-部位", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.pacsBasePartService.removeByIds(ids));
    }


    /**
     * 不分页查全部
     *
     * @return
     */
    @GetMapping("/getAutoCompleteData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "不分页查全部", notes = "不分页查全部")
    public R<List<PacsBasePart>> getAutoCompleteData(String name) {
        QueryWrapper<PacsBasePart> queryWrapper = new QueryWrapper<PacsBasePart>()
                .orderByAsc("part_sort");
        if (StringUtils.isNotEmpty(name)){
            queryWrapper.like("part_name",name).or().like("en_name", name);
        }
        List<PacsBasePart> list = pacsBasePartService.list(queryWrapper);
        return R.ok(list);
    }
}

