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
import com.center.medical.member.bean.param.IFPageParam;
import com.center.medical.member.bean.vo.InterflowVo;
import com.center.medical.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 会员管理-沟通记录控制层
 *
 * @author 路飞船长
 * @since 2022-11-24 10:00:24
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "会员管理-沟通记录")
@RequestMapping("member/interflow")
public class MemberInterflowController extends BaseController {

    /**
     * 服务对象
     */
    private final MemberService memberService;
    private final MapperFacade mapperFacade;


    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【会员管理-中心会员】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/member/interflow/page", "07.会员系统->会员管理-沟通记录->分页查询", null),
                new InterfaceVo("导出", "POST", "/member/interflow/export", "07.会员系统->会员管理-沟通记录->导出", null)
        );
        return R.ok(new FunctionVo("07.会员系统", "会员管理-沟通记录", interfaceVos, "07.会员系统"));
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
    @ApiOperation(value = "分页查询", notes = "会员管理沟通记录分页查询")
    public R<IPage<InterflowVo>> getPage(PageParamSimple pageParamSimple, IFPageParam param) {
        PageParam<InterflowVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.memberService.getIFPage(page, param));
    }


    /**
     * 沟通记录导出
     *
     * @param response
     * @param param
     */
    @Log(title = "沟通记录", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "沟通记录导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, IFPageParam param) {
        List<InterflowVo> list = memberService.getIFExportData(param);
        ExcelUtil<InterflowVo> util = new ExcelUtil<InterflowVo>(InterflowVo.class);
        util.exportExcel(response, list, "沟通记录导出");
    }

}

