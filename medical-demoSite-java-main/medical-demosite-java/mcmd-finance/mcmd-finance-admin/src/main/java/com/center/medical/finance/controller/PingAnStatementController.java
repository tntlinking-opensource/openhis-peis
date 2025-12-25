package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelExp;
import com.center.medical.common.utils.poi.ExcelUtilManySheet;
import com.center.medical.finance.bean.dto.AccountsTotalDto;
import com.center.medical.finance.bean.param.PatientListParam;
import com.center.medical.finance.bean.param.PingAnPageParam;
import com.center.medical.finance.bean.vo.AccountsInfoVo;
import com.center.medical.finance.bean.vo.PatientListVo;
import com.center.medical.finance.bean.vo.PingAnPageVo;
import com.center.medical.finance.service.PingAnStatementService;
import com.center.medical.sellcrm.service.SellcustomerService;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 平安对账单(Createorder)表控制层
 *
 * @author ay
 * @since 2023-04-03 14:19:36
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "平安对账单")
@RequestMapping("finance/pingAnStatement")
public class PingAnStatementController extends BaseController {
    /**
     * 服务对象
     */
    private final PingAnStatementService pingAnStatementService;
    private final MapperFacade mapperFacade;
    private final ISysBranchService sysBranchService;
    private final SellcustomerService sellcustomerService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/getOrderListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询订单表")
    public R<IPage<PingAnPageVo>> getPage(PageParamSimple pageParamSimple, PingAnPageParam param) {
        PageParam<PingAnPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.pingAnStatementService.getList(page, param));
    }


    /**
     * 获取所有分中心
     *
     * @return
     */
    @GetMapping("/fzx")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取所有分中心", notes = "获取所有分中心")
    public R<List<SysBranch>> fzx() {
        List<SysBranch> list = sysBranchService.list(new QueryWrapper<SysBranch>
                ().eq("is_delete", 0));
        return R.ok(list);
    }


    /**
     * 获取体检者数据
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getPatientListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取体检者数据", notes = "获取体检者数据")
    public R<IPage<PatientListVo>> getPatientListData(PageParamSimple pageParamSimple, PatientListParam param) {
        PageParam<PatientListVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.pingAnStatementService.getPatientListData(page, param));
    }


    /**
     * 导出收费项目
     *
     * @param response
     * @param param
     */
    @Log(title = "体检人员", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出体检人员", notes = "导出体检人员")
    @PostMapping("/exportOrderPatient")
    public void exportItems(HttpServletResponse response, PatientListParam param) throws IOException {
        List<PatientListVo> list = pingAnStatementService.exportOrderPatient(param);
        //下方数据
        AccountsTotalDto maleAvg = new AccountsTotalDto("男平均");
        AccountsTotalDto femealAvg = new AccountsTotalDto("女平均");
        AccountsTotalDto maleTotal = new AccountsTotalDto("男合计");
        AccountsTotalDto femaleTotal = new AccountsTotalDto("女合计");
        AccountsTotalDto allTotal = new AccountsTotalDto("总合计");
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                AccountsInfoVo item = mapperFacade.map(list.get(i), AccountsInfoVo.class);
                if (item.getSex() == 0) {
                    //男
                    maleTotal.addSelf(item);
                } else if (item.getSex() == 1) {
                    //女
                    femaleTotal.addSelf(item);
                }
                allTotal.addSelf(item);
            }
            maleAvg.avg(maleTotal);
            femealAvg.avg(femaleTotal);
        }
        List<AccountsTotalDto> totalGrid = new ArrayList<>();
        totalGrid.add(femealAvg);
        totalGrid.add(maleAvg);
        totalGrid.add(femaleTotal);
        totalGrid.add(maleTotal);
        totalGrid.add(allTotal);


        //导出
        ExcelExp e1 = new ExcelExp("体检人员", list, PatientListVo.class);
        ExcelExp e2 = new ExcelExp("合计数据", totalGrid, AccountsTotalDto.class);
        List<ExcelExp> mysheet = new ArrayList<ExcelExp>();
        mysheet.add(e1);
        mysheet.add(e2);
        ExcelUtilManySheet<List<ExcelExp>> util2 = new ExcelUtilManySheet<List<ExcelExp>>(mysheet);
        util2.exportExcelManySheet(response, mysheet);
    }
}

