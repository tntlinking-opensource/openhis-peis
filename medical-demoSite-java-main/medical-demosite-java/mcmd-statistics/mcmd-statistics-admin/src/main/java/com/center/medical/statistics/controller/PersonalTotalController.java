package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.Arith;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.PTTotalListParam;
import com.center.medical.statistics.bean.param.PersonalTotalParam;
import com.center.medical.statistics.bean.vo.PTTotalListVo;
import com.center.medical.statistics.bean.vo.PersonalTotalVo;
import com.center.medical.statistics.service.PersonalTotalService;
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
 * QT体检者表(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-14 16:40:23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检费用统计-团检费用统计")
@RequestMapping("statistics/personalTotal")
public class PersonalTotalController extends BaseController {
    /**
     * 服务对象
     */
    private final PersonalTotalService personalTotalService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页团检费用统计")
    public R<IPage<PersonalTotalVo>> getPage(PageParamSimple pageParamSimple, PersonalTotalParam param) {
        PageParam<PersonalTotalVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.personalTotalService.getList(page, param));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "单位职业健康检查结果附表", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出销售团检统计")
    @PostMapping("/export")
    public void export(HttpServletResponse response, PersonalTotalParam param) {
        List<PersonalTotalVo> list = personalTotalService.getExportData(param);
        ExcelUtil<PersonalTotalVo> util = new ExcelUtil<PersonalTotalVo>(PersonalTotalVo.class);
        int i = 1;
        //合计数据 原价合计、实收合计、人数
        Double yj = 0.0;
        Double ss = 0.0;
        Double jxyjhj = 0.0;
        Double ksjxje = 0.0;
        Double rs = 0.0;
        for (PersonalTotalVo vo : list) {
            yj = Arith.add(yj,vo.getPrice());
            ss = Arith.add(ss,vo.getFastprice());
            jxyjhj = Arith.add(jxyjhj,vo.getAddorgprice());
            ksjxje = Arith.add(ksjxje,vo.getAddprice());
            rs = Arith.add(rs,vo.getCounts());

            vo.setRownum(i);
            i++;
        }
        PersonalTotalVo personalTotalVo = new PersonalTotalVo();
        personalTotalVo.setSellName("合计");
        personalTotalVo.setPrice(yj);
        personalTotalVo.setFastprice(ss);
        personalTotalVo.setAddorgprice(jxyjhj);
        personalTotalVo.setAddprice(ksjxje);
        personalTotalVo.setCounts(rs);
        list.add(personalTotalVo);

        util.exportExcel(response, list, "销售团检统计","销售团检统计");
    }


    /**
     * 查询右边列表
     * @param param
     * @return
     */
    @GetMapping("/getTotalList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询右边列表", notes = "查询右边列表")
    public R<List<PTTotalListVo>> getTotalList(PTTotalListParam param) {
        List<PTTotalListVo> list = personalTotalService.getTotalList(param);
        return R.ok(list);
    }


}

