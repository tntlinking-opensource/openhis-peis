package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.member.bean.model.Memberintegral;
import com.center.medical.member.bean.param.MemberintegralParam;
import com.center.medical.member.service.MemberintegralService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 会员管理-会员积分(Memberintegral)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-24 10:00:24
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "会员管理-会员积分")
@RequestMapping("member/integral")
public class MemberintegralController extends BaseController {
    /**
     * 服务对象
     */
    private final MemberintegralService memberintegralService;
    private final MapperFacade mapperFacade;

    /**
     * 【会员管理-中心会员】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【会员管理-会员积分】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/member/integral/page", "07.会员系统->客服管理-会员管理-会员积分->分页查询", null),
                new InterfaceVo("导出", "POST", "/member/integral/export", "07.会员系统->客服管理-会员管理-会员积分->导出", null)
        );
        return R.ok(new FunctionVo("07.会员系统", "会员管理-会员积分", interfaceVos, "07.会员系统"));
    }



    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询会员卡积分明细表")
    public R<IPage<Memberintegral>> getPage(PageParamSimple pageParamSimple, MemberintegralParam param) {
        PageParam<Memberintegral> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.memberintegralService.getPage(page, param));
    }



    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出")
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "会员卡积分操作", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, MemberintegralParam param) {
        List<Memberintegral> list = this.memberintegralService.getList(param);
        ExcelUtil<Memberintegral> util = new ExcelUtil<Memberintegral>(Memberintegral.class);
        util.exportExcel(response, list, "会员积分明细");
    }
}

