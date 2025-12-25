package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.vo.XsryDataVo;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.sellcrm.bean.model.Leadertarget;
import com.center.medical.sellcrm.bean.model.Selltarget;
import com.center.medical.sellcrm.bean.param.SelltargetParam;
import com.center.medical.sellcrm.bean.vo.GetXsAndDataVo;
import com.center.medical.sellcrm.bean.vo.SelltargetVo;
import com.center.medical.sellcrm.service.LeadertargetService;
import com.center.medical.sellcrm.service.SelltargetService;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 销售季度目标(Selltarget)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售季度目标")
@RequestMapping("sell/target")
public class SelltargetController extends BaseController {
    /**
     * 服务对象
     */
    private final SelltargetService selltargetService;
    private final MapperFacade mapperFacade;
    private final LeadertargetService leadertargetService;
    private final ISysUserService sysUserService;


    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param selltargetParam 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询XS销售目标")
    public R<IPage<SelltargetVo>> getPage(PageParamSimple pageParamSimple, SelltargetParam selltargetParam) {
        PageParam<SelltargetVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.selltargetService.getPage(page, selltargetParam));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查XS销售目标详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Selltarget> selectOne(@PathVariable String id) {
        return R.ok(this.selltargetService.getInfoById(id));
    }

    /**
     * 数据保存或编辑
     *
     * @param selltarget 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "数据保存或编辑", notes = "数据保存或编辑")
    public R insert(@RequestBody Selltarget selltarget) {
        return R.toResult(selltargetService.saOrUp(selltarget));
    }


    /**
     * 返回年份
     *
     * @return
     */
    @GetMapping("/getAllYear")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "返回年份", notes = "返回年份")
    public R getAllYear() {
        List list = leadertargetService.getAllYear();
        return R.ok(list);
    }


    /**
     * 不分页查询列表
     *
     * @return
     */
    @GetMapping("/getSummaryData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取总结数据", notes = "获取总结数据")
    public R<List<SelltargetVo>> getSummaryData(SelltargetParam selltargetParam) {
        return R.ok(this.selltargetService.getSummaryData(selltargetParam));
    }


    /**
     * 判断之前是否制定了销售季度目标
     *
     * @param isYear
     * @param isUserid
     * @return
     */
    @GetMapping("/isXsjdmb")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "判断之前是否制定了销售季度目标", notes = "判断之前是否制定了销售季度目标")
    public R isXsjdmb(@RequestParam("isYear") String isYear, @RequestParam("isUserid") String isUserid) {
        Boolean state = true;
        //获取当前登录者分中心id
        String fzxId = SecurityUtils.getCId();
        Selltarget sellTarget = selltargetService.getOne(new QueryWrapper<Selltarget>()
                .eq("xsjlid", isUserid).eq("year", isYear).eq("fzxid", fzxId));
        if (ObjectUtils.isNotEmpty(sellTarget)) {
            //之前已经制定过了季度目标
            state = false;
        }
        return R.ok(state);
    }


    /**
     * 判断是否允许编辑
     *
     * @param selltyear
     * @param selltuser
     * @return
     */
    @GetMapping("/isEdit")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "判断是否允许编辑", notes = "判断是否允许编辑,true允许，false不允许")
    public R isEdit(@RequestParam("selltyear") String selltyear, @RequestParam("selltuser") String selltuser) {
        Boolean b = true;
        //获取当前登录者用户的分中心id
        String fzxId = SecurityUtils.getCId();
        Selltarget sellTarget = selltargetService.getOne(new QueryWrapper<Selltarget>().eq("year", selltyear)
                .eq("xsjlid", selltuser).eq("fzxid", fzxId));
        if (ObjectUtils.isEmpty(sellTarget)) {
            //不能进行编辑
            b = false;
        }
        return R.ok(b);
    }


    /**
     * 判断是否允许查看
     *
     * @param isviewyear
     * @param isviewuserid
     * @return
     */
    @GetMapping("/isView")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "判断是否允许查看", notes = "判断是否允许查看,true允许，false不允许")
    public R isView(@RequestParam("isviewyear") String isviewyear, @RequestParam("isviewuserid") String isviewuserid) {
        boolean b = true;
        //获取当前登录者分中心id
        String fzxId = SecurityUtils.getCId();
        Selltarget sellTarget = selltargetService.getOne(new QueryWrapper<Selltarget>().eq("xsjlid", isviewuserid)
                .eq("year", isviewyear).eq("fzxid", fzxId));
        if (ObjectUtils.isEmpty(sellTarget)) {
            b = false;
        }
        return R.ok(b);
    }

    /**
     * 获取当前登录人分中心下的销售部下的非领导的用户
     *
     * @return
     */
    @GetMapping("/getXsryData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取当前登录人分中心下的销售部下的非领导的用户", notes = "获取当前登录人分中心下的销售部下的非领导的用户")
    public R<List<XsryDataVo>> getXsryData() {
        String fzxId = SecurityUtils.getCId();
        List<XsryDataVo> xsryData = sysUserService.getXsryDataUser(fzxId);
        return R.ok(xsryData);
    }


    /**
     * 获取销售人员关联的数据
     *
     * @param selldatayear
     * @param selldatauserid
     * @return
     */
    @GetMapping("/getXsAndData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取销售人员关联的数据", notes = "获取销售人员关联的数据")
    public R getXsAndData(@RequestParam("selldatayear") String selldatayear, @RequestParam("selldatauserid") String selldatauserid) {
        GetXsAndDataVo getXsAndDataVo = selltargetService.getXsAndData(selldatayear, selldatauserid);
        return R.ok(getXsAndDataVo);
    }


    /**
     * 导出
     *
     * @param response
     * @param selltargetParam
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出销售季度目标")
    public void export(HttpServletResponse response, SelltargetParam selltargetParam) {
        List<SelltargetVo> list = selltargetService.getAllList(selltargetParam);
        for (int i = 0; i < list.size(); i++) {
            SelltargetVo vo = list.get(i);
            vo.setRownum(i+1);
        }
        ExcelUtil<SelltargetVo> util = new ExcelUtil<SelltargetVo>(SelltargetVo.class);
        util.exportExcel(response, list, "销售季度目标");
    }




    /**
     * 获取领导制定的年度目标
     *
     * @param year 分页参数
     * @return 所有数据
     */
    @GetMapping("/getYearTarget")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取年度目标", notes = "获取年度目标，返回空就是没有")
    @ApiImplicitParam(name = "year", value = "年份")
    public R<Double> getYearTarget(String year,String userid) {
        Leadertarget leadertarget = leadertargetService.getOne(new LambdaQueryWrapper<Leadertarget>()
                .eq(Leadertarget::getYear, year).eq(Leadertarget::getXsjlid, userid));
        if (ObjectUtils.isNotEmpty(leadertarget)){
            return R.ok(leadertarget.getNdmb());
        }else {
            return R.ok(null);
        }
    }

}

