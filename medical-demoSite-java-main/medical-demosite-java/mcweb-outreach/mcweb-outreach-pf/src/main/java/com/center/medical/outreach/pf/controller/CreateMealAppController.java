package com.center.medical.outreach.pf.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.CreatemealAppType;
import com.center.medical.appadmin.bean.param.GenerateCodeParam;
import com.center.medical.appadmin.bean.param.GetMealDetailsParam;
import com.center.medical.appadmin.bean.param.GetMealListParam;
import com.center.medical.appadmin.bean.vo.GetMealDetailsVo;
import com.center.medical.appadmin.bean.vo.GetMealListVo;
import com.center.medical.appadmin.service.CreatemealAppService;
import com.center.medical.appadmin.service.CreatemealAppTypeService;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.service.NewReservationService;
import com.center.medical.reception.service.OrderService;
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
@Api(tags = "新版小程序套餐接口")
@RequestMapping("/open/api/v2/createMealApp")
public class CreateMealAppController extends BaseController {

    private final CreatemealAppTypeService createmealAppTypeService;
    private final CreatemealAppService createmealAppService;
    private final NewReservationService newReservationService;
    private final OrderService orderService;





    /**
     * 查询手机报告列表
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/getMealList")
    @ApiOperation(value = "获取套餐列表", notes = "获取套餐列表")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R getMealList(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        GetMealListParam param = JSONUtil.toBean(dataStr, GetMealListParam.class);
        log.info("请求参数：{}", param);
        PageParam<GetMealListVo> page = new PageParam<>();
        page.setSize(param.getSize());
        page.setCurrent(param.getCurrent());
        IPage<GetMealListVo> iPage = createmealAppService.getMealList(page,param);
        return R.ok(JSONUtil.toJsonStr(iPage));
    }



    /**
     * 查询手机报告列表
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/getMealDetails")
    @ApiOperation(value = "获取套餐详情", notes = "获取套餐详情")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R getMealDetails(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        GetMealDetailsParam param = JSONUtil.toBean(dataStr, GetMealDetailsParam.class);
        log.info("请求参数：{}", param);
        GetMealDetailsVo vo = createmealAppService.getMealDetails(param);
        return R.ok(JSONUtil.toJsonStr(vo));
    }




    /**
     * 查询手机报告列表
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/getMealType")
    @ApiOperation(value = "获取套餐分类", notes = "获取套餐分类")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R getMealType(HttpServletRequest request) {
        List<CreatemealAppType> list = createmealAppTypeService.list(new LambdaQueryWrapper<CreatemealAppType>()
                .eq(CreatemealAppType::getIsDelete, 0)
                .orderByAsc(CreatemealAppType::getSeq));
        return R.ok(JSONUtil.toJsonStr(list));
    }



    /**
     * 购买套餐生成体检号
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/generateCode")
    @ApiOperation(value = "购买套餐生成体检号", notes = "购买套餐生成体检号")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R generateCode(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        GenerateCodeParam param = JSONUtil.toBean(dataStr, GenerateCodeParam.class);
        log.info("请求参数：{}", param);
        String patientcode = orderService.generateCode(param);
        return R.ok(patientcode);
    }



    /**
     * 校验体检号状态
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/checkPeiStatus")
    @ApiOperation(value = "校验体检号状态", notes = "校验体检号状态")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R checkPeiStatus(HttpServletRequest request) {
        String code = (String) request.getAttribute("dataStr");
        log.info("校验体检号状态的体检号是：{}", code);
        String str = orderService.checkPeiStatus(code);
        return R.ok(str);
    }



    /**
     * 购买套餐生成体检号
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/deleteCode")
    @ApiOperation(value = "退款删除体检号", notes = "退款删除体检号")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R deleteCode(HttpServletRequest request) {
        String code = (String) request.getAttribute("dataStr");
        log.info("退款删除的体检号是：{}", code);
        Boolean b = orderService.deleteCode(code);
        return R.ok(b);
    }
}

