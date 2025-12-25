package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.member.bean.model.VisitMain;
import com.center.medical.member.bean.param.VisitMainParam;
import com.center.medical.member.bean.param.VmFormdata;
import com.center.medical.member.bean.vo.VisitMainVo;
import com.center.medical.member.service.VisitMainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 检验样本-不合格样本(VisitMain)表控制层
 *
 * @author ay
 * @since 2023-01-11 17:39:59
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "检验样本-不合格样本")
@RequestMapping("/handle/visitMain")
public class VisitMainController extends BaseController {
    /**
     * 服务对象
     */
    private final VisitMainService visitMainService;
    private final MapperFacade mapperFacade;


    /**
     * 【外送管理-外送登记】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【外送管理-外送登记】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取体检者信息", "GET", "/addSatisficing/getTjzData", "09.科室系统->科室管理-检验样本-不合格样本->获取体检者信息", null),
                new InterfaceVo("分页查询", "GET", "/handle/visitMain/page", "09.科室系统->科室管理-检验样本-不合格样本->分页查询", null),
                new InterfaceVo("新增数据", "POST", "/handle/visitMain/saveOrUpdate", "09.科室系统->科室管理-检验样本-不合格样本->新增数据", null),
                new InterfaceVo("删除", "DELETE", "/handle/visitMain/{ids}", "09.科室系统->科室管理-检验样本-不合格样本->删除", null),
                new InterfaceVo("导出", "DELETE", "/handle/visitMain/export", "09.科室系统->科室管理-检验样本-不合格样本->导出", null)

        );
        return R.ok(new FunctionVo("09.科室系统", "外送管理-外送登记", interfaceVos, "09.科室系统"));
    }


    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询与迟检、阳性、不合格样本回访表一对多关联，")
    public R<IPage<VisitMainVo>> getPage(PageParamSimple pageParamSimple, VisitMainParam param) {
        PageParam<VisitMainVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.visitMainService.getList(page, param));
    }


    /**
     * 新增数据
     *
     * @param vmFormdata 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "新增数据", notes = "新增数据")
    public R insert(@RequestBody VmFormdata vmFormdata) {
        return R.toResult(this.visitMainService.saOrUp(vmFormdata));
    }


    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除与迟检、阳性、不合格样本回访表一对多关联，")
    @Log(title = "与迟检、阳性、不合格样本回访表一对多关联，", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        VisitMain visitMain = new VisitMain();
        //将isDelete设置为1，为删除
        visitMain.setIsDelete(1);
        boolean b = visitMainService.update(visitMain, new UpdateWrapper<VisitMain>().in("id", ids));
        return R.toResult(b);
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "不合格样本", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, VisitMainParam param) {
        List<VisitMainVo> list = visitMainService.getExportDate(param);
        ExcelUtil<VisitMainVo> util = new ExcelUtil<VisitMainVo>(VisitMainVo.class);
        util.exportExcel(response, list, "外送登记");
    }
}

