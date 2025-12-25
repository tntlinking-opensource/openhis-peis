package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.vo.XsryDataVo;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.sellcrm.bean.model.Leadertarget;
import com.center.medical.sellcrm.bean.param.ImportTargetParam;
import com.center.medical.sellcrm.bean.param.LTSaOrUpParam;
import com.center.medical.sellcrm.bean.param.LeadertargetParam;
import com.center.medical.sellcrm.bean.vo.GetXsAndDataVo;
import com.center.medical.sellcrm.bean.vo.LTSummaryVo;
import com.center.medical.sellcrm.service.LeadertargetService;
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
import java.util.Objects;

/**
 * 销售年度目标(Leadertarget)表控制层
 *
 * @author ay
 * @since 2022-12-02 18:31:30
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售年度目标")
@RequestMapping("sell/leadertarget")
public class LeadertargetController extends BaseController {
    /**
     * 服务对象
     */
    private final LeadertargetService leadertargetService;
    private final MapperFacade mapperFacade;
    private final ISysUserService sysUserService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple   分页参数
     * @param leadertargetParam 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询领导目标表")
    public R<IPage<Leadertarget>> getPage(PageParamSimple pageParamSimple, LeadertargetParam leadertargetParam) {
        PageParam<Leadertarget> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.leadertargetService.getList(page, leadertargetParam));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查领导目标表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Leadertarget> selectOne(@PathVariable String id) {
        return R.ok(this.leadertargetService.getInfoById(id));
    }

    /**
     * 新增或修改数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "新增或修改数据", notes = "新增或修改数据")
    public R saOrUp(@RequestBody LTSaOrUpParam param) {
        Boolean b = leadertargetService.saOrUp(param);
        return R.toResult(b);
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
     * 获取总结数据
     *
     * @param leadertargetParam
     * @return
     */
    @GetMapping("/getSummaryData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取总结数据", notes = "获取总结数据")
    public R<List<LTSummaryVo>> getSummaryData(LeadertargetParam leadertargetParam) {
        List<LTSummaryVo> leadertargetList = leadertargetService.getSummaryData(leadertargetParam);
        return R.ok(leadertargetList);
    }

    /**
     * 判断是否已经制定了目标
     *
     * @param lyear
     * @param luserId
     * @return
     */
    @GetMapping("/isLeaderYearTarget")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "判断领导之前是否为该销售制定过目标", notes = "判断领导之前是否为该销售制定过目标，true指定，false未制定")
    public R isLeaderYearTarget(@RequestParam("lyear") String lyear, @RequestParam("luserId") String luserId) {
        Boolean b = leadertargetService.isLeaderYearTarget(lyear, luserId);
        return R.ok(b);
    }


    /**
     * 判断当前登录用户是否为领导
     *
     * @return
     */
    @GetMapping("/isLeader")
    @ApiOperation(value = "判断当前登录用户是否为领导", notes = "判断当前登录用户是否为领导,true是，false否")
    public R<Boolean> isLeader() {
        Boolean b = SecurityUtils.isLeader();
        return R.ok(b);
    }


    /**
     * 判断该记录是否已经制定了目标,未制定目标不能进行编辑
     *
     * @param zdmbId
     * @param zdmbYear
     * @return
     */
    @GetMapping("/isZdmb")
    @ApiOperation(value = "编辑判定", notes = "判断该记录是否已经制定了目标,未制定目标不能进行编辑,true能编辑，false不能编辑")
    public R<Boolean> isZdmb(@RequestParam("zdmbId") String zdmbId, @RequestParam("zdmbYear") String zdmbYear) {
        boolean b = true;
        //根据用户id和相关年份获取实体
        Leadertarget leaderTarget = leadertargetService.getOne(new QueryWrapper<Leadertarget>().eq("xsjlid", zdmbId).eq("year", zdmbYear));
        if (ObjectUtils.isEmpty(leaderTarget)) {
            //该实体为null,说明该实体还未制定相关目标,暂时不能编辑
            b = false;
        }
        return R.ok(b);
    }


    /**
     * 判断当前记录是否允许查看,对于未制定目标的不能进行查看
     *
     * @param zdmbViewId
     * @param zdmbViewYear
     * @return
     */
    @GetMapping("/isZdmbView")
    @ApiOperation(value = "查看判定", notes = "判断当前记录是否允许查看,对于未制定目标的不能进行查看,true能编辑，false不能编辑")
    public R<Boolean> isZdmbView(@RequestParam("zdmbViewId") String zdmbViewId, @RequestParam("zdmbViewYear") String zdmbViewYear) {
        boolean b = true;
        //根据记录id和年份确定实体,若实体为null则不能进行查看
        Leadertarget leaderTarget = leadertargetService.getOne(new QueryWrapper<Leadertarget>().eq("xsjlid", zdmbViewId).eq("year", zdmbViewYear));
        if (ObjectUtils.isEmpty(leaderTarget)) {
            //表明不能进行查看
            b = false;
        }
        return R.ok(b);
    }


    /**
     * 获取当前登录人分中心下的销售部下的非领导的用户
     *
     * @param fzxId
     * @return
     */
    @GetMapping("/getXsryData")
    @ApiOperation(value = "获取当前登录人分中心下的销售部下的非领导的用户", notes = "获取当前登录人分中心下的销售部下的非领导的用户")
    public R getXsryData(@RequestParam("fzxId") String fzxId) {
        List<XsryDataVo> list = sysUserService.getXsryDataUser(fzxId);
        return R.ok(list);
    }


    /**
     * 根据年份获取相应销售关联的数据
     *
     * @param leaderUserId
     * @param leaderYear
     * @return
     */
    @GetMapping("/getXsAndData")
    @ApiOperation(value = "根据年份获取相应销售关联的数据", notes = "根据年份获取相应销售关联的数据")
    public R getXsAndData(@RequestParam("leaderUserId") List<String> leaderUserId, @RequestParam("leaderYear") String leaderYear) {
        GetXsAndDataVo getXsAndDataVo = leadertargetService.getXsAndData(leaderUserId, leaderYear);
        return R.ok(getXsAndDataVo);
    }


    /**
     * 销售年度目标
     *
     * @param response
     * @param leadertargetParam
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出销售年度目标")
    public void export(HttpServletResponse response, LeadertargetParam leadertargetParam) {
        List<Leadertarget> list = leadertargetService.getExportData(leadertargetParam);
        for (int i = 0; i < list.size(); i++) {
            Leadertarget leadertarget = list.get(i);
            leadertarget.setRownum(i+1);
        }
        ExcelUtil<Leadertarget> util = new ExcelUtil<Leadertarget>(Leadertarget.class);
        util.exportExcel(response, list, "销售年度目标");
    }



    /**
     * 导入目标
     *
     * @param param
     * @return
     */
    @PostMapping("/importYearTarget")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "导入年度目标", notes = "导入年度目标")
    @Log(title = "导入年度目标", businessType = BusinessType.IMPORT)
    public R importYearTarget(ImportTargetParam param) {

        if (Objects.isNull(param.getFile())) {
            throw new GlobalException("请选择上传文件！");
        }
        String fileName = param.getFile().getOriginalFilename();
        //判断文件后缀
        String endSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if (!endSuffix.toLowerCase().endsWith("xls") && !endSuffix.toLowerCase().endsWith("xlsx")) {
            throw new GlobalException("请选择正确的文件类型，必须是以.xls或.xlsx结尾！");
        }
        if (StringUtils.isBlank(param.getYear())) {
            throw new GlobalException("年份不能为空！");
        }
        Boolean b = leadertargetService.importYearTarget(param);
        return R.ok(b);
    }
}

