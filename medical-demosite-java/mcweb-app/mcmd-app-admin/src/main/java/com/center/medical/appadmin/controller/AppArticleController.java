package com.center.medical.appadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.AppArticle;
import com.center.medical.appadmin.bean.param.AppArticlePageParam;
import com.center.medical.appadmin.bean.param.AppArticleParam;
import com.center.medical.appadmin.bean.param.GoLiveParam;
import com.center.medical.appadmin.bean.vo.AppArticlePageVo;
import com.center.medical.appadmin.bean.vo.GetTypeListVo;
import com.center.medical.appadmin.service.AppArticleService;
import com.center.medical.appadmin.service.AppArticleTypeService;
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
 * 小程序文章表(AppArticle)接口
 *
 * @author ay
 * @since 2024-06-15 09:08:15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "小程序文章")
@RequestMapping("app/appArticle")
public class AppArticleController extends BaseController {
    /**
     * 服务对象
     */
    private final AppArticleService appArticleService;
    private final AppArticleTypeService appArticleTypeService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param      查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询小程序文章表")
    public R<IPage<AppArticlePageVo>> getPage(PageParamSimple pageParamSimple, AppArticlePageParam param) {
        PageParam<AppArticlePageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.appArticleService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查小程序文章表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<AppArticle> selectOne(@PathVariable String id) {
        AppArticle appArticle = appArticleService.getInfoById(id);
        return R.ok(appArticle);
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    @ApiOperation(value = "添加或修改", notes = "添加小程序文章表")
    @Log(title = "小程序文章表", businessType = BusinessType.INSERT)
    public R saOrUp(@RequestBody AppArticleParam param) {
        return R.ok(this.appArticleService.saOrUp(param));
    }



    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除小程序文章表")
    @Log(title = "小程序文章表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.ok(this.appArticleService.deleteIds(ids));
    }



    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/goLive")
    @ApiOperation(value = "上线或下线", notes = "上线或下线")
    @Log(title = "小程序文章上线或下线", businessType = BusinessType.INSERT)
    public R goLive(@RequestBody GoLiveParam param) {
        return R.ok(this.appArticleService.goLive(param));
    }




    /**
     * 分页查询所有数据
     *
     * @param name      查询条件
     * @return 所有数据
     */
    @GetMapping("/getTypeList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取类型", notes = "获取类型")
    public R<List<GetTypeListVo>> getTypeList(String name) {
        return R.ok(this.appArticleTypeService.getTypeList(name));
    }
}
