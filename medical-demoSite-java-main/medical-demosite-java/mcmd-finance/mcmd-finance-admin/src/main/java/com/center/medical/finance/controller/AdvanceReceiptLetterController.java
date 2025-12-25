package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.param.ReceiptLetterParam;
import com.center.medical.finance.bean.vo.ReceiptLetterVo;
import com.center.medical.finance.service.AdvanceReceiptLetterService;
import com.center.medical.system.service.ISysBranchService;
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
import java.util.List;

/**
 * 应收预收函证接口
 *
 * @author ay
 * @since 2024-02-19 15:50:20
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "应收预收函证")
@RequestMapping("finance/advanceReceiptLetter")
public class AdvanceReceiptLetterController extends BaseController {
    /**
     * 服务对象
     */
    private final AdvanceReceiptLetterService advanceReceiptLetterService;
    private final ISysBranchService iSysBranchService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询体检者团体任务")
    public R<IPage<ReceiptLetterVo>> getPage(PageParamSimple pageParamSimple, ReceiptLetterParam param) {
        PageParam<ReceiptLetterVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.advanceReceiptLetterService.getPage(page, param));
    }




    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "导出应收预收函证", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出应收预收函证", notes = "导出应收预收函证")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReceiptLetterParam param) {
        SysBranch sysBranch = iSysBranchService.getDefaultBranch();
        List<ReceiptLetterVo> list = advanceReceiptLetterService.getExportData(param);
        ExcelUtil<ReceiptLetterVo> util = new ExcelUtil<ReceiptLetterVo>(ReceiptLetterVo.class);
        util.exportExcel(response, list, sysBranch.getFzx()+"中心应收账款询证函",sysBranch.getFzx()+"中心应收账款询证函");
    }


}
