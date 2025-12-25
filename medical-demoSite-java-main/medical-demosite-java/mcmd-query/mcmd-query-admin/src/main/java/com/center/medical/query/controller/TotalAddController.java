package com.center.medical.query.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.query.bean.param.TotalAddParam;
import com.center.medical.query.bean.vo.TotalAddVo;
import com.center.medical.query.service.TotalAddService;
import com.center.medical.system.bean.vo.AllUserDataVo;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
 * 加项情况查询(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-08 09:31:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "加项情况查询")
@RequestMapping("query/totalAdd")
public class TotalAddController extends BaseController {
    /**
     * 服务对象
     */
    private final TotalAddService totalAddService;
    private final MapperFacade mapperFacade;
    private final ISysUserService sysUserService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<TotalAddVo>> getPage(PageParamSimple pageParamSimple, TotalAddParam param) {
        PageParam<TotalAddVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.totalAddService.getList(page, param));
    }


    /**
     * 所有用户下拉
     *
     * @param key
     * @return
     */
    @GetMapping("/getAllUserData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "所有用户下拉", notes = "所有用户下拉")
    @ApiImplicitParam(name = "key", value = "搜索的用户名或输入码")
    public R getAllUserData(String key) {
        List<AllUserDataVo> list = sysUserService.getAllUserData(key);
        return R.ok(list);
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出加项查询")
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "加项查询", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, TotalAddParam param) {
        List<TotalAddVo> list = totalAddService.getExportData(param);
        ExcelUtil<TotalAddVo> util = new ExcelUtil<TotalAddVo>(TotalAddVo.class);
        util.exportExcel(response, list, "加项查询");
    }




    /**
     * 获取分页合计
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/getPageTotal")
    @ApiOperation(value = "获取分页合计", notes = "获取分页合计")
    public R<Double> getPageTotal(TotalAddParam param) {
        Double total = totalAddService.getPageTotal(param);
        return R.ok(total);
    }
}

