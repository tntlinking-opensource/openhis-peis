package com.center.medical.appadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.IndexImg;
import com.center.medical.appadmin.bean.param.IndexImgParam;
import com.center.medical.appadmin.service.IndexImgService;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 主页轮播图(IndexImg)接口
 *
 * @author ay
 * @since 2024-03-19 12:00:05
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "主页轮播图")
@RequestMapping("app/indexImg")
public class IndexImgController extends BaseController {
    /**
     * 服务对象
     */
    private final IndexImgService indexImgService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param        查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询主页轮播图")
    public R<IPage<IndexImg>> getPage(PageParamSimple pageParamSimple, IndexImgParam param) {
        PageParam<IndexImg> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.indexImgService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param imgId 主键
     * @return 单条数据
     */
    @GetMapping("{imgId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据imgId查主页轮播图详情")
    @ApiImplicitParam(name = "imgId", value = "要查询的对象的主键{imgId}")
    public R<IndexImg> selectOne(@PathVariable String imgId) {
        return R.ok(this.indexImgService.getInfoById(imgId));
    }

    /**
     * 新增数据
     *
     * @param indexImg 实体对象
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    @ApiOperation(value = "添加或修改", notes = "添加主页轮播图")
    @Log(title = "主页轮播图", businessType = BusinessType.INSERT)
    public R saOrUP(@RequestBody IndexImg indexImg) {
        return R.toResult(this.indexImgService.saOrUp(indexImg));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{imgId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除主页轮播图")
    @Log(title = "主页轮播图", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{imgId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.indexImgService.deleteImg(ids));
    }

}
