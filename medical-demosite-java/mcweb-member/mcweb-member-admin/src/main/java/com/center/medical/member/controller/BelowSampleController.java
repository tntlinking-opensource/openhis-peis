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
import com.center.medical.member.bean.param.BelowSampleEditParam;
import com.center.medical.member.bean.param.BelowSampleParam;
import com.center.medical.member.bean.vo.BelowSampleEditVo;
import com.center.medical.member.bean.vo.BelowSampleVo;
import com.center.medical.member.service.BelowSampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 与回访管理-不合格样品回访(VisitMain)表控制层
 *
 * @author makejava
 * @since 2023-02-07 08:31:21
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "回访管理-不合格样品回访")
@RequestMapping("/member/belowSample")
public class BelowSampleController extends BaseController {
    /**
     * 服务对象
     */
    private final BelowSampleService belowSampleService;
    private final MapperFacade mapperFacade;

    /**
     * 接口说明
     *
     * @return
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【回访管理-不合格样品回访】这个业务模块所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/member/belowSample/page", "客服系统->客服管理-回访管理-不合格样品回访->分页查询", null),
                new InterfaceVo("导出Excel","POST","/member/belowSample/export","客服系统->客服管理-回访管理-不合格样品回访->导出Excel",null),
                new InterfaceVo("详情","GET","/member/belowSample/{id}","客服系统->客服管理-回访管理-不合格样品回访->详情",null),
                new InterfaceVo("不合格样品回访","POST","/member/belowSample/edit","客服系统->客服管理-回访管理-不合格样品回访->不合格样品回访",null)
        );
        return R.ok(new FunctionVo("客服系统", "回访管理-不合格样品回访", interfaceVos, "客服系统"));
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
    public R<IPage<BelowSampleVo>> getPage(PageParamSimple pageParamSimple, BelowSampleParam param) {
        PageParam<BelowSampleVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.belowSampleService.getList(page, param));
    }

	/**
	 * 导出Excel文件
	 * @param response
	 * @param param
	 */
	@Log(title = "不合格样品回访",businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasAnyPermi('monitor:operlog:export')")
	@ApiOperation(value = "导出Excel",notes = "导出Excel")
	@PostMapping("/export")
	public void export(HttpServletResponse response, BelowSampleParam param){
		List<BelowSampleVo> list = belowSampleService.export(param);
		ExcelUtil<BelowSampleVo> util = new ExcelUtil<>(BelowSampleVo.class);
		util.exportExcel(response,list,"不合格样本回访");
	}


	/**
	* 详情
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查与迟检、阳性、不合格样本回访表一对多关联，详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
	public R<BelowSampleEditVo> selectOne(@PathVariable String id) {
        BelowSampleEditVo infoById = this.belowSampleService.getInfoById(id);
        return R.ok(infoById);
	}

    /**
     * 不合格样品回访
     * @param param
     * @return
     */
	@PostMapping("/saveOrUpdate")
    @ApiOperation(value = "不合格样品回访保存",notes = "不合格样品回访保存")
    public R saOrUp(@RequestBody BelowSampleEditParam param){
		return R.toResult(this.belowSampleService.saOrUpBelowSample(param));
    }

}

