package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.MemberParam;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.member.bean.vo.MemberListVo;
import com.center.medical.member.service.MemberService;
import com.center.medical.service.PeispatientarchiveService;
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
 * 会员管理-平台会员(Peispatientarchive)控制层
 *
 * @author 路飞船长
 * @since 2022-11-24 10:04:19
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "会员管理-平台会员")
@RequestMapping("member/online")
public class MemberOnlineController extends BaseController {
    /**
     * 服务对象
     */
    private final PeispatientarchiveService peispatientarchiveService;
    private final MapperFacade mapperFacade;
    private final MemberService memberService;


    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【会员管理-中心会员】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取分中心数据", "GET", "/sell/monthtarget/getBranchData", "06.客户销售系统->销售管理-销售目标-销售月度目标->获取分中心数据", null),
                new InterfaceVo("分中心下所有销售人员信息", "GET", "/sell/customerTransfer/getXsryData", "06.客户销售系统->销售管理-销售目标-销售月度目标->获取本分中心下所有销售人员信息", null),
                new InterfaceVo("分页查询平台会员列表数据", "GET", "/member/online/getMemberListData", "07.会员系统->客服管理-会员管理-中心会员->分页查询平台会员列表数据", null),
                new InterfaceVo("导出", "POST", "/member/online/export", "07.会员系统->客服管理-会员管理-中心会员->导出", null)
        );
        return R.ok(new FunctionVo("07.会员系统", "会员管理-中心会员", interfaceVos, "07.会员系统"));
    }


    /**
     * 分页查询平台会员列表数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/getMemberListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询平台会员列表数据", notes = "分页查询平台会员列表数据")
    public R<IPage<MemberListVo>> getPage(PageParamSimple pageParamSimple, MemberParam param) {
        PageParam<MemberListVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<MemberListVo> iPage = memberService.getMemberListData(page, param);
        return R.ok(iPage);
    }

    /**
     * 导出线上会员列表数据
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出会员列表数据")
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "会员维护", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, MemberParam param) {
        List<MemberListVo> list = memberService.getExportData(param);
        ExcelUtil<MemberListVo> util = new ExcelUtil<MemberListVo>(MemberListVo.class);
        util.exportExcel(response, list, "会员维护");
    }

}

