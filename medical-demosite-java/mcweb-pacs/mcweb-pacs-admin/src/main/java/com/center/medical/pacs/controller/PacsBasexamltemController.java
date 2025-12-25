package com.center.medical.pacs.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.pacs.bean.param.PacsBasexamltemListParam;
import com.center.medical.pacs.bean.param.PacsBasexamltemSaveParam;
import com.center.medical.pacs.bean.vo.PacsBasexamltemSignVo;
import com.center.medical.pacs.bean.vo.PacsBasexamltemVo;
import com.center.medical.pacs.bean.vo.PacsConclusionVo;
import com.center.medical.pacs.service.PacsBasexamltemService;
import com.center.medical.system.bean.vo.DeptSimpleVo;
import com.center.medical.system.bean.param.SysDeptParam;
import com.center.medical.system.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xhp
 * @since 2023-03-29 8:01
 */
@RestController
@RequestMapping("/pacs/exam")
@Api(tags = "pacs检查项目")
@RequiredArgsConstructor
@Validated
public class PacsBasexamltemController extends BaseController {
    private final PacsBasexamltemService pacsBasexamltemService;
    private final MapperFacade mapperFacade;
    private final ISysDeptService deptService;

    @GetMapping("/page")
    @ApiOperation(value = "分页查询pacs检查项目", notes = "分页查询PACS检查项目")
    public R<IPage<PacsBasexamltemVo>> getPage(PageParamSimple pageParamSimple, PacsBasexamltemListParam pacsBasexamltemListParam){
        PageParam pageParam=mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(pacsBasexamltemService.getPage(pageParam, pacsBasexamltemListParam));
    }

    @GetMapping("/sign/page")
    @ApiOperation(value = "分页查询pacs体征词", notes = "分页查询体征词")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "pacs检查项目id", required = true),
    })
    public R<IPage<PacsBasexamltemSignVo>> getSignPage(PageParamSimple pageParamSimple,@RequestParam  String id){
        PageParam pageParam=mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(pacsBasexamltemService.getSignPage(pageParam,id));
    }

    @GetMapping("/sign/list")
    @ApiOperation(value = "查询全部pacs体征词", notes = "查询全部体征词")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "pacs检查项目id", required = true),
    })
    public R<List<PacsBasexamltemSignVo>> getSignList(@RequestParam  String id){
        return R.ok(pacsBasexamltemService.getSignList(id));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除pacs检查项目", notes = "删除pacs检查项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "pacs检查项目ids", required = true),
    })
    public R delete(@PathVariable List<String> ids) {
        pacsBasexamltemService.delete(ids);
        return R.ok();
    }

    @GetMapping("{id}")
    @ApiOperation(value = "pacs检查项目详情", notes = "根据id查pacs检查项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "pacs检查项目id", required = true),
    })
    public R<PacsBasexamltemVo> selectOne(@PathVariable String id){
        return R.ok(pacsBasexamltemService.getInfoById(id));
    }

    @PostMapping("/save")
    @ApiOperation(value = "添加", notes = "添加pacs检查项目表")
    public R insert(@Validated @RequestBody PacsBasexamltemSaveParam pacsBasexamltemSaveParam) {
        pacsBasexamltemSaveParam.setId(null);
        pacsBasexamltemService.saOrUp(pacsBasexamltemSaveParam);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新", notes = "更新pacs检查项目表")
    public R update(@Validated @RequestBody PacsBasexamltemSaveParam pacsBasexamltemSaveParam) {
        if(StrUtil.isEmpty(pacsBasexamltemSaveParam.getId()))return R.fail("id不能为空");
        pacsBasexamltemService.saOrUp(pacsBasexamltemSaveParam);
        return R.ok();
    }

    @GetMapping("/conclusion/list")
    @ApiOperation(value = "结论词分页查询", notes = "结论词分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "inputCode", value = "拼音码", required = false),
    })
    public R<IPage<PacsConclusionVo>> getConclusionList(PageParamSimple pageParamSimple, String inputCode){
        PageParam pageParam=mapperFacade.map(pageParamSimple,PageParam.class);
        return R.ok(pacsBasexamltemService.getConclusionList(pageParam,inputCode));
    }

    @GetMapping("/dept/list")
    @ApiOperation(value = "查询全部功能科室", notes = "查询全部功能科室")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "inputCode", value = "输入码"),
    })
    public R<List<DeptSimpleVo>> getDeptList(String inputCode){
        if(StrUtil.isNotEmpty(inputCode))inputCode=inputCode.toUpperCase();
        SysDeptParam param=new SysDeptParam();
        param.setInputCode(inputCode);
        return R.ok(deptService.getAllDepartment(param));
    }
}
