package com.center.medical.outreach.pf.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.AppArticle;
import com.center.medical.appadmin.bean.model.AppArticleType;
import com.center.medical.appadmin.bean.param.GetArticleListParam;
import com.center.medical.appadmin.bean.vo.GetArticleListVo;
import com.center.medical.appadmin.service.AppArticleService;
import com.center.medical.appadmin.service.AppArticleTypeService;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 查询手机报告列表
 * 该拦截层之作权限数据认证和数据消毒处理，不对任何业务处理
 *
 * @author 路飞船长
 * @since 2023-02-02 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "新版小程序文章接口")
@RequestMapping("/open/api/v2/appArticle")
public class appArticleController extends BaseController {

    private final AppArticleService appArticleService;
    private final AppArticleTypeService appArticleTypeService;

    /**
     * 查询手机报告列表
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/getArticleType")
    @ApiOperation(value = "获取文章分类", notes = "获取文章分类")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R getArticleType(HttpServletRequest request) {
        List<AppArticleType> list = appArticleTypeService.list(new LambdaQueryWrapper<AppArticleType>()
                .eq(AppArticleType::getIsDelete, 0)
                .orderByAsc(AppArticleType::getSeq));
        return R.ok(JSONUtil.toJsonStr(list));
    }




    /**
     * 查询手机报告列表
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/getArticleList")
    @ApiOperation(value = "获取文章列表", notes = "获取文章列表")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R getArticleList(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        GetArticleListParam param = JSONUtil.toBean(dataStr, GetArticleListParam.class);
        log.info("请求参数：{}", param);
        PageParam<GetArticleListVo> page = new PageParam<>();
        page.setSize(param.getSize());
        page.setCurrent(param.getCurrent());
        IPage<GetArticleListVo> iPage = appArticleService.getArticleList(page,param);
        return R.ok(JSONUtil.toJsonStr(iPage));
    }




    /**
     * 查询手机报告列表
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/getArticleDetails")
    @ApiOperation(value = "获取文章详情", notes = "获取文章详情")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R getMealDetails(HttpServletRequest request) {
        String id = (String) request.getAttribute("dataStr");
        log.info("请求参数：{}", id);
        AppArticle appArticle = appArticleService.getInfoById(id);
        return R.ok(JSONUtil.toJsonStr(appArticle));
    }
}

