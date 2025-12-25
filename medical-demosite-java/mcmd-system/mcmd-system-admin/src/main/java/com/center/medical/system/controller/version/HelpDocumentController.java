package com.center.medical.system.controller.version;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.system.bean.model.HelpDocument;
import com.center.medical.system.service.HelpDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 帮助文档(HelpDocument)接口
 *
 * @author ay
 * @since 2024-04-24 13:56:58
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "帮助文档")
@RequestMapping("/system/helpDocument")
public class HelpDocumentController extends BaseController {
    /**
     * 服务对象
     */
    private final HelpDocumentService helpDocumentService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param helpDocument    查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询帮助文档")
    public R<IPage<HelpDocument>> getPage(PageParamSimple pageParamSimple, HelpDocument helpDocument) {
        PageParam<HelpDocument> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.helpDocumentService.getPage(page, helpDocument));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查帮助文档详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<HelpDocument> selectOne(@PathVariable String id) {
        return R.ok(this.helpDocumentService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param helpDocument 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation(value = "添加", notes = "添加帮助文档")
    public R insert(@RequestBody HelpDocument helpDocument) {
        return R.toResult(this.helpDocumentService.save(helpDocument));
    }

    /**
     * 修改数据
     *
     * @param helpDocument 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiOperation(value = "更新", notes = "更新帮助文档")
    public R update(@RequestBody HelpDocument helpDocument) {
        return R.toResult(this.helpDocumentService.updateById(helpDocument));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除帮助文档")
    @Log(title = "帮助文档", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        HelpDocument helpDocument = new HelpDocument();
        helpDocument.setIsDelete(1);
        helpDocument.setModifydate(new Date());
        helpDocumentService.update(helpDocument,new LambdaQueryWrapper<HelpDocument>().in(HelpDocument::getId,ids));
        return R.ok(Boolean.TRUE);
    }

}
