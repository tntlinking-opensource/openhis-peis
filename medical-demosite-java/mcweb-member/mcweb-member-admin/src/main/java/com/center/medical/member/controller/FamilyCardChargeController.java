package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.member.bean.param.FamilyChargeParam;
import com.center.medical.member.bean.vo.FamilyChargeVo;
import com.center.medical.member.service.FamilyMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 家庭会员-家庭卡消费记录(Peispatientarchive)表控制层
 *
 * @author ay
 * @since 2023-03-01 10:34:46
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "家庭会员-家庭卡消费记录")
@RequestMapping("/member/familyCardCharge")
public class FamilyCardChargeController extends BaseController {
    /**
     * 服务对象
     */
    private final FamilyMemberService familyMemberService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple    分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页家庭卡消费记录")
    public R<IPage<FamilyChargeVo>> getPage(PageParamSimple pageParamSimple, FamilyChargeParam param) {
        PageParam<FamilyChargeVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<FamilyChargeVo>  iPage = familyMemberService.familyChargeData(page,param);
        return R.ok(iPage);
    }


    /**
     * 导出
     * @param response
     * @param param
     */
    @Log(title = "家庭卡消费记录", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出家庭卡消费记录数据")
    @PostMapping("/export")
    public void export(HttpServletResponse response, FamilyChargeParam param) {
        List<FamilyChargeVo> list = familyMemberService.chargeExportData(param);
        ExcelUtil<FamilyChargeVo> util = new ExcelUtil<FamilyChargeVo>(FamilyChargeVo.class);
        util.exportExcel(response, list, "家庭卡收费明细");
    }

}

