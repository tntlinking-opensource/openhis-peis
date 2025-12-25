package com.center.medical.appadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.MdUser;
import com.center.medical.appadmin.bean.param.AppUserParam;
import com.center.medical.appadmin.bean.vo.AppUserVo;
import com.center.medical.appadmin.service.MdUserService;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户表(MdUser)接口
 *
 * @author ay
 * @since 2024-07-23 17:03:25
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "小程序用户")
@RequestMapping("app/appUser")
public class MdUserController extends BaseController {
    /**
     * 服务对象
     */
    private final MdUserService mdUserService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param mdUser          查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询用户表")
    public R<IPage<AppUserVo>> getPage(PageParamSimple pageParamSimple, AppUserParam mdUser) {
        PageParam<AppUserVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdUserService.getPage(page, mdUser));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param userId 主键
     * @return 单条数据
     */
    @GetMapping("{userId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据userId查用户表详情")
    @ApiImplicitParam(name = "userId", value = "要查询的对象的主键{userId}")
    public R<MdUser> selectOne(@PathVariable String userId) {
        return R.ok(this.mdUserService.getInfoById(userId));
    }





}
