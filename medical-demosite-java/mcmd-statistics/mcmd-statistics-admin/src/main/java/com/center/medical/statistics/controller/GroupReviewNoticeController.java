package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.report.service.IPersonalReportService;
import com.center.medical.statistics.bean.param.CreateReviewGroupParam;
import com.center.medical.statistics.bean.param.GroupReviewNoticeParam;
import com.center.medical.statistics.bean.vo.GroupReviewNoticeVo;
import com.center.medical.statistics.service.GroupReviewNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 职业健康检查复查人员名单(GroupReviewNotice)接口
 *
 * @author ay
 * @since 2023-07-18 09:19:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业健康检查复查人员名单")
@RequestMapping("statistics/groupReviewNotice")
public class GroupReviewNoticeController extends BaseController {
    /**
     * 服务对象
     */
    private final GroupReviewNoticeService groupReviewNoticeService;
    private final IPersonalReportService iPersonalReportService;
    private final MapperFacade mapperFacade;


    /**
     * 【前台-备单】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【职业健康检查复查人员名单】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取客户单位下拉", "GET", "/sell/customer/getAllOrg", "06.客户销售系统->销售管理-线上备单->获取客户单位下拉", null),
                new InterfaceVo("获取订单号下拉", "GET", "/reception/register/getDdhData", "前台-登记管理->获取订单号下拉", null)
        );
        return R.ok(new FunctionVo("职业健康检查复查人员名单", "职业健康检查复查人员名单", interfaceVos, "职业健康检查复查人员名单"));
    }

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple   分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询批量职业健康检查复查通知书")
    public R<IPage<GroupReviewNoticeVo>> getPage(PageParamSimple pageParamSimple, GroupReviewNoticeParam param) {
        PageParam<GroupReviewNoticeVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.groupReviewNoticeService.getList(page, param));
    }




    /**
     * 创建名单
     *
     * @param param 支付参数
     * @return
     */
    @PostMapping("/createReviewGroup")
    @ApiOperation(value = "创建复查名单", notes = "创建复查名单")
    public R<Boolean> createReviewGroup(@RequestBody CreateReviewGroupParam param) {
        String id = groupReviewNoticeService.createReviewGroup(param);
//        iPersonalReportService.createPdf(id, SecurityUtils.getUsername());
        return R.ok(Boolean.TRUE);
    }





    /**
     * 生成
     *
     * @param param 支付参数
     * @return
     */
    @PostMapping("/createPdf")
    @ApiOperation(value = "生成pdf", notes = "生成pdf")
    public R<Boolean> createPdf(@RequestBody List<String> ids) {
        for (String id : ids) {
            iPersonalReportService.createPdf(id, SecurityUtils.getUsername());
        }
        return R.ok(Boolean.TRUE);
    }
}

