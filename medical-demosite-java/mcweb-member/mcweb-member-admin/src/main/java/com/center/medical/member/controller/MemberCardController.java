package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.finance.bean.param.SendCardParam;
import com.center.medical.finance.bean.vo.SendCardVo;
import com.center.medical.finance.service.CardService;
import com.center.medical.member.service.MemberService;
import com.center.medical.service.PeispatientarchiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 会员管理-会员卡发放(Peispatientarchive)控制层
 *
 * @author 路飞船长
 * @since 2022-11-24 10:04:19
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "会员管理-会员卡发放")
@RequestMapping("member/card")
public class MemberCardController extends BaseController {
    /**
     * 服务对象
     */
    private final PeispatientarchiveService peispatientarchiveService;
    private final MapperFacade mapperFacade;
    private final MemberService memberService;
    private final CardService cardService;


    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【会员管理-中心会员】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取分中心数据", "GET", "/sell/monthtarget/getBranchData", "07.会员系统->会员管理-会员卡发放->获取分中心数据", null),
                new InterfaceVo("分中心下所有销售人员信息", "GET", "/sell/customerTransfer/getXsryData", "07.会员系统->会员管理-会员卡发放->获取本分中心下所有销售人员信息", null),
                new InterfaceVo("获取体检卡类型", "GET", "/finance/sendCard/getTypeData", "07.会员系统->会员管理-会员卡发放->获取体检卡类型", null),
                new InterfaceVo("卡类型改变后获取卡前缀等字段", "GET", "/finance/sendCard/getChangeData", "07.会员系统->会员管理-会员卡发放->卡类型改变后获取卡前缀等字段", null),
                new InterfaceVo("领取人处搜索", "GET", "/finance/sendCard/getLqrData", "07.会员系统->会员管理-会员卡发放->领取人处搜索", null),
                new InterfaceVo("新增发卡保存", "POST", "/finance/sendCard/saveOrUpdate", "07.会员系统->会员管理-会员卡发放->新增发卡保存", null),
                new InterfaceVo("删除", "Delete", "/finance/sendCard/remove", "07.会员系统->会员管理-会员卡发放->新增发卡保存", null),
                new InterfaceVo("导出", "POST", "/finance/sendCard/export", "07.会员系统->会员管理-会员卡发放->导出", null),
                new InterfaceVo("分页查询", "GET", "/member/card/page", "07.会员系统->会员管理-会员卡发放->分页查询", null)
        );
        return R.ok(new FunctionVo("07.会员系统", "会员管理-会员卡发放", interfaceVos, "07.会员系统"));
    }


    /**
     * 分页查询平台会员列表数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询平台会员列表数据")
    public R<IPage<SendCardVo>> getPage(PageParamSimple pageParamSimple, SendCardParam param) {
        PageParam<SendCardVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.cardService.getList(page, param));
    }


}

