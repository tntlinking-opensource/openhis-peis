package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Branch;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.core.domain.vo.XsryByCodeVo;
import com.center.medical.common.core.domain.vo.XsryDataVo;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.sellcrm.bean.model.Orderandfzx;
import com.center.medical.sellcrm.bean.param.SellDateParam;
import com.center.medical.sellcrm.bean.vo.SellDateMonthVo;
import com.center.medical.sellcrm.bean.vo.SellDateQuarterVo;
import com.center.medical.sellcrm.bean.vo.SellDateVo;
import com.center.medical.sellcrm.service.OrderandfzxService;
import com.center.medical.sellcrm.service.SelltargetService;
import com.center.medical.system.service.BranchService;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 销售同期对比(Selltarget)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售同期对比")
@RequestMapping("sell/sellDate")
public class SellDateController extends BaseController {
    /**
     * 服务对象
     */
    private final SelltargetService selltargetService;
    private final MapperFacade mapperFacade;
    private final ISysUserService sysUserService;
    private final BranchService branchService;
    private final OrderandfzxService orderandfzxService;


    /**
     * 查询,根据条件获取相关数据进行柱状图展现
     *
     * @param sellDateParam 查询条件
     * @return 所有数据
     */
    @GetMapping("/getSearchChartsData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询,根据条件获取相关数据进行柱状图展现", notes = "查询,根据条件获取相关数据进行柱状图展现")
    public R<IPage<SellDateVo>> getPage(PageParamSimple pageParamSimple, SellDateParam sellDateParam) {
        PageParam<SellDateVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.selltargetService.getSellDatePage(page, sellDateParam));
    }


    /**
     * 获取本分中心下所有的销售人员数据
     *
     * @return
     */
    @GetMapping("/getXsryDataUser")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取本分中心下所有的销售人员数据", notes = "获取本分中心下所有的销售人员数据")
    public R<List<XsryDataVo>> getXsryData() {
        String cId = SecurityUtils.getCId();
        return R.ok(this.sysUserService.getXsryDataUser(cId));
    }


    /**
     * 根据用户id、年份、月份获取关联的数据
     *
     * @param sellDateParam
     * @return
     */
    @GetMapping("/getXsAndData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据用户id、年份、月份获取关联的数据", notes = "根据用户id、年份、月份获取关联的数据")
    public R<SellDateVo> getXsAndData(SellDateParam sellDateParam) {
        SellDateVo sellDateVo = selltargetService.getXsAndSellDate(sellDateParam);
        return R.ok(sellDateVo);
    }


    /**
     * 查询条件获取销售人员名称数据,用于显示
     *
     * @param pageParamSimple
     * @param fzxId
     * @param userName
     * @return
     */
    @GetMapping("/getXsryData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询条件获取销售人员名称数据,用于显示", notes = "查询条件获取销售人员名称数据,用于显示")
    public R<IPage<SysUser>> getXsryDateData(PageParamSimple pageParamSimple, String fzxId, String userName) {
        PageParam<SysUser> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<SysUser> sysUsers = sysUserService.getXsryData(page, fzxId, userName);
        return R.ok(sysUsers);
    }


    /**
     * 获取分中心数据
     *
     * @return
     */
    @GetMapping("/getBranchData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取分中心数据", notes = "获取分中心数据")
    public R<List<Branch>> getBranchData(String ddid) {
        QueryWrapper<Branch> queryWrapper = new QueryWrapper<Branch>().orderByAsc("branch_sort")
                .eq("is_delete", 0);
        if (StringUtils.isNotNull(ddid)){
            List<Orderandfzx> orderandfzxList = orderandfzxService.list(new LambdaQueryWrapper<Orderandfzx>().eq(Orderandfzx::getDdid, ddid));
            if (CollectionUtils.isNotEmpty(orderandfzxList)){
                List<String> fzxIds = orderandfzxList.stream().map(b -> b.getFzxid()).collect(Collectors.toList());
                queryWrapper.in("branch_id",fzxIds);
            }
        }
        List<Branch> list = branchService.list(queryWrapper);
        return R.ok(list);
    }


    /**
     * 通过输入码获取销售人员
     *
     * @param pageParamSimple
     * @param code
     * @return
     */
    @GetMapping("/getXsryByCode")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "通过输入码获取销售人员", notes = "通过输入码获取销售人员")
    public R<IPage<XsryByCodeVo>> getXsryByCode(PageParamSimple pageParamSimple, String code) {
        PageParam<XsryByCodeVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        List<String> cIds = null;
        if (ZhongkangConfig.isOnline()) {
            cIds = branchService.getUserCid(SecurityUtils.getUserNo());
        }
        code = code.trim();
        IPage<XsryByCodeVo> sysUsers = sysUserService.getXsryByCode(page, cIds, code);
        return R.ok(sysUsers);
    }


    /**
     * 查询,根据条件获取相关数据进行柱状图展现
     *
     * @param sellDateParam 查询条件
     * @return 所有数据
     */
    @GetMapping("/getSellDateYear")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "销售同期对比年", notes = "销售同期对比年")
    public R<IPage<SellDateVo>> getSellDateYear(PageParamSimple pageParamSimple, SellDateParam sellDateParam) {
        PageParam<SellDateVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.selltargetService.getSellDateYear(page, sellDateParam));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "导出同期对比年", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出同期对比年", notes = "导出同期对比年")
    @PostMapping("/exportSellDateYear")
    public void export(HttpServletResponse response, SellDateParam param) {
        PageParam<SellDateVo> page = new PageParam<>();
        page.setSize(9999);
        IPage<SellDateVo> iPage = selltargetService.getSellDateYear(page, param);
        List<SellDateVo> list = iPage.getRecords();
        for (int i = 0; i < list.size(); i++) {
            SellDateVo vo = list.get(i);
            vo.setRownum(i + 1);
        }
        ExcelUtil<SellDateVo> util = new ExcelUtil<SellDateVo>(SellDateVo.class);
        util.exportExcel(response, list, "销售同期对比年");
    }


    /**
     * 查询,根据条件获取相关数据进行柱状图展现
     *
     * @param sellDateParam 查询条件
     * @return 所有数据
     */
    @GetMapping("/getSellDateQuarter")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "销售同期对比季度", notes = "销售同期对比季度")
    public R<IPage<SellDateQuarterVo>> getSellDateQuarter(PageParamSimple pageParamSimple, SellDateParam sellDateParam) {
        PageParam<SellDateQuarterVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.selltargetService.getSellDateQuarter(page, sellDateParam));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "导出销售同期对比季度", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出销售同期对比季度", notes = "导出销售同期对比季度")
    @PostMapping("/exportSellDateQuarter")
    public void exportSellDateQuarter(HttpServletResponse response, SellDateParam param) {
        PageParam<SellDateQuarterVo> page = new PageParam<>();
        page.setSize(9999);
        IPage<SellDateQuarterVo> iPage = selltargetService.getSellDateQuarter(page, param);
        List<SellDateQuarterVo> list = iPage.getRecords();
        for (int i = 0; i < list.size(); i++) {
            SellDateQuarterVo vo = list.get(i);
            vo.setRownum(i + 1);
        }
        ExcelUtil<SellDateQuarterVo> util = new ExcelUtil<SellDateQuarterVo>(SellDateQuarterVo.class);
        util.exportExcel(response, list, "导出销售同期对比季度");
    }


    /**
     * 查询,根据条件获取相关数据进行柱状图展现
     *
     * @param sellDateParam 查询条件
     * @return 所有数据
     */
    @GetMapping("/getSellDateMonth")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "销售同期对比月", notes = "销售同期对比月")
    public R<IPage<SellDateMonthVo>> getSellDateMonth(PageParamSimple pageParamSimple, SellDateParam sellDateParam) {
        PageParam<SellDateMonthVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.selltargetService.getSellDateMonth(page, sellDateParam));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "导出销售同期对比月", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出销售同期对比月", notes = "导出销售同期对比月")
    @PostMapping("/exportSellDateMonth")
    public void exportSellDateMonth(HttpServletResponse response, SellDateParam param) {
        PageParam<SellDateMonthVo> page = new PageParam<>();
        page.setSize(9999);
        IPage<SellDateMonthVo> iPage = selltargetService.getSellDateMonth(page, param);
        List<SellDateMonthVo> list = iPage.getRecords();
        for (int i = 0; i < list.size(); i++) {
            SellDateMonthVo vo = list.get(i);
            vo.setRownum(i + 1);
        }
        ExcelUtil<SellDateMonthVo> util = new ExcelUtil<SellDateMonthVo>(SellDateMonthVo.class);
        util.exportExcel(response, list, "导出销售同期对比月");
    }
}

