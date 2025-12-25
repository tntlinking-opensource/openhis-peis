package com.center.medical.pacs.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.pacs.bean.param.PacsItemsListParam;
import com.center.medical.pacs.bean.param.PacsItemsSaveParam;
import com.center.medical.pacs.bean.vo.PacsItemExamListVo;
import com.center.medical.pacs.bean.vo.PacsItemExamPageVo;
import com.center.medical.pacs.bean.vo.PacsItemsListVo;
import com.center.medical.pacs.bean.vo.PacsItemsVo;
import com.center.medical.pacs.service.PacsItemsBasicService;
import com.center.medical.system.bean.param.SysDeptParam;
import com.center.medical.system.bean.vo.DeptSimpleVo;
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
 * @since 2023-03-31 14:14
 */
@RestController
@RequestMapping("/pacs/items")
@Api(tags = "pacs收费项目")
@RequiredArgsConstructor
@Validated
public class PacsItemsController extends BaseController {
    private final MapperFacade mapperFacade;
    private final PacsItemsBasicService pacsItemsBasicService;
    private final ISysDeptService deptService;

    @GetMapping("/page")
    @ApiOperation(value = "分页查询pacs收费项目", notes = "分页查询pacs收费项目")
    public R<IPage<PacsItemsListVo>> getPage(PageParamSimple pageParamSimple, PacsItemsListParam pacsItemsListParam) {
        PageParam pageParam = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(pacsItemsBasicService.getPage(pageParam, pacsItemsListParam));
    }

    @GetMapping("/exam/page")
    @ApiOperation(value = "分页查询pacs检查项目", notes = "按pacs收费项目id分页查询pacs检查项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "pacs收费项目id", required = true),
    })
    public R<IPage<PacsItemExamPageVo>> getExamPage(PageParamSimple pageParamSimple, @RequestParam String id) {
        PageParam pageParam = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(pacsItemsBasicService.getExamPage(pageParam, id));
    }

    @GetMapping("/exam/list")
    @ApiOperation(value = "查询全部pacs检查项目", notes = "按pacs收费项目id查询全部pacs检查项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "pacs收费项目id", required = true),
    })
    public R<List<PacsItemExamListVo>> getExamList(@RequestParam String id) {
        return R.ok(pacsItemsBasicService.getExamList(id));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除pacs收费项目", notes = "删除pacs收费项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "pacs收费项目ids", required = true),
    })
    public R delete(@PathVariable List<String> ids) {
        pacsItemsBasicService.delete(ids);
        return R.ok();
    }

    @GetMapping("{id}")
    @ApiOperation(value = "pacs收费项目详情", notes = "根据id查pacs收费项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "pacs收费项目id", required = true),
    })
    public R<PacsItemsVo> selectOne(@PathVariable String id) {
        return R.ok(pacsItemsBasicService.selectOne(id));
    }

    @PostMapping("/save")
    @ApiOperation(value = "添加", notes = "添加pacs收费项目")
    public R insert(@Validated @RequestBody PacsItemsSaveParam pacsItemsSaveParam) {
        pacsItemsSaveParam.setId(null);
        pacsItemsBasicService.saOrUp(pacsItemsSaveParam);
        return R.ok();
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新", notes = "更新pacs收费项目")
    public R update(@Validated @RequestBody PacsItemsSaveParam pacsItemsSaveParam) {
        if (StrUtil.isEmpty(pacsItemsSaveParam.getId())) return R.fail("id不能为空");
        pacsItemsBasicService.saOrUp(pacsItemsSaveParam);
        return R.ok();
    }

    @GetMapping("/dept/list")
    @ApiOperation(value = "查询全部功能科室", notes = "查询全部功能科室")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "inputCode", value = "输入码"),
    })
    public R<List<DeptSimpleVo>> getDeptList(String inputCode) {
        if (StrUtil.isNotEmpty(inputCode)) inputCode = inputCode.toUpperCase();
        SysDeptParam param = new SysDeptParam();
        param.setInputCode(inputCode);
        return R.ok(deptService.getAllDepartment(param));
    }
}
