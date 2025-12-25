package com.center.medical.member.controller;

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
import com.center.medical.member.bean.param.ForinspectionViewEditParam;
import com.center.medical.member.bean.param.ForinspectionViewParam;
import com.center.medical.member.bean.vo.ForinspectionViewEditVo;
import com.center.medical.member.bean.vo.ForinspectionViewVo;
import com.center.medical.member.service.ForinspectionViewService;
import com.center.medical.member.service.VisitMainService;
import com.center.medical.service.FailTotalVisitService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientfeeitemService;
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
 * 回访管理-迟补检回访
 * 与迟检、阳性、不合格样本回访表一对多关联，(VisitMain)表控制层
 *
 * @author makejava
 * @since 2023-02-10 09:46:44
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "回访管理-迟补检回访")
@RequestMapping("/member/forinspectionView")
public class ForinspectionViewController<ComboExamItemDaoService, ForinspectionService> extends BaseController {
    /**
     * 服务对象
     */
    private final ForinspectionViewService forinspectionViewService;
    private final MapperFacade mapperFacade;
    private final VisitMainService visitMainService;
    private final PeispatientService peispatientService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final FailTotalVisitService failTotalVisitService;

    /**
     * 【回访管理-迟补检回访】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【回访管理-迟补检回访】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/member/forinspectionView/page", "客服系统->回访管理-迟补检回访->分页查询", null),
                new InterfaceVo("详情", "GET", "/handle/handleNewProjects/{id}", "09.科室系统->科室管理-检验样本-检验加项->详情", null),
                new InterfaceVo("处理", "GET", "/handle/handleNewProjects/saveOrUpdate", "09.科室系统->科室管理-检验样本-检验加项->处理", null)
        );
        return R.ok(new FunctionVo("09.科室系统", "检验样品-检验加项", interfaceVos, "09.科室系统"));
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
    public R<IPage<ForinspectionViewVo>> getPage(PageParamSimple pageParamSimple, ForinspectionViewParam param) {
        PageParam<ForinspectionViewVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.forinspectionViewService.getPage(page, param));
    }

    /**
     * 导出excel
     *
     * @param response
     * @param param
     */
    @Log(title = "迟补检回访", businessType = BusinessType.EXPORT)
//	@PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "迟补检回访导出excel", notes = "迟补检回访导出excel")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForinspectionViewParam param) {
        List<ForinspectionViewVo> list = forinspectionViewService.export(param);
        ExcelUtil<ForinspectionViewVo> util = new ExcelUtil<ForinspectionViewVo>(ForinspectionViewVo.class);
        util.exportExcel(response, list, "迟补检回访");
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情(可能缺字段)", notes = "根据id查与迟检、阳性、不合格样本回访表一对多关联，详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<ForinspectionViewEditVo> selectOne(@PathVariable String id) {
        return R.ok(this.forinspectionViewService.getInfoById(id));
    }

    /**
     * 迟补检回访
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "迟补检回访保存", notes = "迟补检回访保存")
    public R saOrUp(@RequestBody ForinspectionViewEditParam param) {
        return R.toResult(this.forinspectionViewService.saOrUp(param));
    }

}

