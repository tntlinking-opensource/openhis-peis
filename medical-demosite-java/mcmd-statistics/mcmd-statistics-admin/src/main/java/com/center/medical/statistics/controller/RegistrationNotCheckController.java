package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.RegistrationNotCheckParam;
import com.center.medical.statistics.bean.vo.RegistrationNotCheckVo;
import com.center.medical.statistics.service.RegistrationNotCheckService;
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
 * 登记未检客户统计(Peispatient)接口
 *
 * @author ay
 * @since 2023-12-18 15:52:57
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "登记未检客户统计")
@RequestMapping("statistics/registrationNotCheck")
public class RegistrationNotCheckController extends BaseController {
    /**
     * 服务对象
     */
    private final RegistrationNotCheckService registrationNotCheckService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<RegistrationNotCheckVo>> getPage(PageParamSimple pageParamSimple, RegistrationNotCheckParam param) {
        PageParam<RegistrationNotCheckVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.registrationNotCheckService.getPage(page, param));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "登记未检客户统计", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出登记未检客户统计")
    @PostMapping("/export")
    public void export(HttpServletResponse response, RegistrationNotCheckParam param) {
        List<RegistrationNotCheckVo> list = registrationNotCheckService.export(param);
        for (int i = 0; i < list.size(); i++) {
            RegistrationNotCheckVo vo = list.get(i);
            vo.setRownum(i+1);
        }
        ExcelUtil<RegistrationNotCheckVo> util = new ExcelUtil<RegistrationNotCheckVo>(RegistrationNotCheckVo.class);
        util.exportExcel(response, list, "登记未检客户统计");
    }
}
