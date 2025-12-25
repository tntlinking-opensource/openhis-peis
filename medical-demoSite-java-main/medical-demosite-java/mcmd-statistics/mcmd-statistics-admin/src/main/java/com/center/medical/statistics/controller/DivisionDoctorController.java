package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.service.BasexamltemService;
import com.center.medical.statistics.bean.param.AnalyseTestParam;
import com.center.medical.statistics.bean.vo.*;
import com.center.medical.statistics.service.DivisionDoctorService;
import com.center.medical.system.service.ISysDeptService;
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
import java.util.Arrays;
import java.util.List;

/**
 * 医生工作量(Peispatientexamitem)表控制层
 *
 * @author ay
 * @since 2023-04-19 11:35:12
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "工作量统计-医生工作量")
@RequestMapping("statistics/divisionDoctor")
public class DivisionDoctorController extends BaseController {
	/**
	* 服务对象
	*/
	private final DivisionDoctorService divisionDoctorService;
    private final MapperFacade mapperFacade;
    private final ISysDeptService sysDeptService;
    private final BasexamltemService basexamltemService;


	@GetMapping("/getInterfaceList")
	@ApiOperation(value = "接口说明", notes = "获取【工作量统计-医生工作量】这个块业务所有接口及接口说明")
	public R<FunctionVo> getInterfaceList() {
		List<InterfaceVo> interfaceVos = Arrays.asList(
				new InterfaceVo("获取分中心信息", "GET", "/sell/createorder/getBranchData", "财务管理->体检卡管理-卡充值->获取分中心信息", null),
				new InterfaceVo("获取医师", "GET", "/drugstore/prescribe/getDoctor", "09.科室系统->科室管理-药房管理-审核发药->获取医师", null),
				new InterfaceVo("PACS科室-收费项目", "GET", "/items/page", "基础数据->收费项目设置", null),
				new InterfaceVo("其他科室-科室", "GET", "/reception/register/getKsData", "前台->登记管理", null)
		);
		return R.ok(new FunctionVo("04.统计模块", "工作量统计-医生工作量", interfaceVos, "04.统计模块"));
	}

	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param param 查询条件
	* @return 所有数据
	*/
	@GetMapping("/analyseTest")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "检验科科室医生工作量统计", notes = "检验科科室医生工作量统计")
	public R<IPage<AnalyseTestVo>> getPage(PageParamSimple pageParamSimple, AnalyseTestParam param) {
		PageParam<AnalyseTestVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.divisionDoctorService.getList(page, param));
	}


	/**
	 * 检验科科室医生工作量统计总计
	 * @param pageParamSimple
	 * @param param
	 * @return
	 */
	@GetMapping("/analyseTestTotal")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "检验科科室医生工作量统计总计", notes = "检验科科室医生工作量统计总计")
	public R<IPage<AnalyseTestVo>> analyseTestTotal(PageParamSimple pageParamSimple, AnalyseTestParam param) {
		PageParam<AnalyseTestVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.divisionDoctorService.analyseTestTotal(page, param));
	}


	/**
	 * pacs科室医生工作量统计
	 * @param pageParamSimple
	 * @param param
	 * @return
	 */
	@GetMapping("/analysePacs")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "pacs科室医生工作量统计", notes = "pacs科室医生工作量统计")
	public R<IPage<AnalysePacsVo>> analysePacs(PageParamSimple pageParamSimple, AnalyseTestParam param) {
		// TODO: wait PACS查询表可能有问题，不用这些数据库了
		PageParam<AnalysePacsVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.divisionDoctorService.analysePacs(page, param));
	}


	/**
	 * pacs科室医生工作量统计
	 * @param pageParamSimple
	 * @param param
	 * @return
	 */
	@GetMapping("/analysePacsTotal")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "pacs科室医生工作量统计总计", notes = "pacs科室医生工作量统计总计")
	public R<IPage<AnalysePacsVo>> analysePacsTotal(PageParamSimple pageParamSimple, AnalyseTestParam param) {
		// TODO: wait PACS查询表可能有问题，不用这些数据库了
		PageParam<AnalysePacsVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.divisionDoctorService.analysePacsTotal(page, param));
	}


	/**
	 * 其他科室医生工作量统计
	 * @param pageParamSimple
	 * @param param
	 * @return
	 */
	@GetMapping("/analyse")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "其他科室医生工作量统计", notes = "其他科室医生工作量统计")
	public R<IPage<AnalysePacsVo>> analyse(PageParamSimple pageParamSimple, AnalyseTestParam param) {
		PageParam<AnalysePacsVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.divisionDoctorService.analyse(page, param));
	}


	/**
	 * 其他科室医生工作量统计
	 * @param pageParamSimple
	 * @param param
	 * @return
	 */
	@GetMapping("/analyseTotal")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "其他科室医生工作量统计总计 ", notes = "其他科室医生工作量统计总计")
	public R<IPage<AnalysePacsVo>> analyseTotal(PageParamSimple pageParamSimple, AnalyseTestParam param) {
		PageParam<AnalysePacsVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.divisionDoctorService.analyseTotal(page, param));
	}


	/**
	 * 检验科检查项目
	 * @param pageParamSimple
	 * @param key
	 * @return
	 */
	@GetMapping("/getInspectExamData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "检验科检查项目", notes = "检验科检查项目")
	@ApiImplicitParam(name = "key", value = "输入码")
	public R<IPage<Basexamltem>> analyseTotal(PageParamSimple pageParamSimple,String key) {
		PageParam<Basexamltem> page = mapperFacade.map(pageParamSimple, PageParam.class);
		//检验科室
		QueryWrapper<Basexamltem> con = new QueryWrapper<>();
		con.eq("division_id",19);
		con.eq("is_delete",0);
		if(StringUtils.isNotEmpty(key)){
			con.like("input_code", key.trim().toUpperCase());
		}
		IPage<Basexamltem> iPage = basexamltemService.page(page, con);
		return R.ok(iPage);
	}



	/**
	 * 导出pacs科室工作量
	 *
	 * @param response
	 * @param param
	 */
	@Log(title = "导出pacs科室工作量", businessType = BusinessType.EXPORT)
	@ApiOperation(value = "导出pacs科室工作量", notes = "导出pacs科室工作量")
	@PostMapping("/exportPacs")
	public void export(HttpServletResponse response, AnalyseTestParam param) {
		List<ExportPacsVo> list = divisionDoctorService.exportPacs(param);
		ExcelUtil<ExportPacsVo> util = new ExcelUtil<ExportPacsVo>(ExportPacsVo.class);
		util.exportExcel(response, list, "PACS科室医生工作量","PACS科室医生工作量");
	}


	/**
	 * 导出pacs科室工作量明细
	 * @param response
	 * @param param
	 */
	@Log(title = "导出pacs科室工作量明细", businessType = BusinessType.EXPORT)
	@ApiOperation(value = "导出pacs科室工作量明细", notes = "导出pacs科室工作量明细")
	@PostMapping("/exportPacsInfo")
	public void exportPacsInfo(HttpServletResponse response, AnalyseTestParam param) {
		List<ExportPacsInfoVo> list = divisionDoctorService.exportPacsInfoVo(param);
		ExcelUtil<ExportPacsInfoVo> util = new ExcelUtil<ExportPacsInfoVo>(ExportPacsInfoVo.class);
		util.exportExcel(response, list, "pacs科室工作量明细","pacs科室工作量明细");
	}




	/**
	 * 导出其他科室工作量
	 *
	 * @param response
	 * @param param
	 */
	@Log(title = "导出其他科室工作量", businessType = BusinessType.EXPORT)
	@ApiOperation(value = "导出其他科室工作量", notes = "导出其他科室工作量")
	@PostMapping("/exportOther")
	public void exportOther(HttpServletResponse response, AnalyseTestParam param) {
		List<ExportOtherVo> list = divisionDoctorService.exportOther(param);
		ExcelUtil<ExportOtherVo> util = new ExcelUtil<ExportOtherVo>(ExportOtherVo.class);
		util.exportExcel(response, list, "其他科室工作量","其他科室工作量");
	}




	/**
	 * 导出其他科室工作量
	 *
	 * @param response
	 * @param param
	 */
	@Log(title = "导出其他科室工作量明细", businessType = BusinessType.EXPORT)
	@ApiOperation(value = "导出其他科室工作量明细", notes = "导出其他科室工作量明细")
	@PostMapping("/exportOtherInfo")
	public void exportOtherInfo(HttpServletResponse response, AnalyseTestParam param) {
		List<ExportOtherInfoVo> list = divisionDoctorService.exportOtherInfo(param);
		ExcelUtil<ExportOtherInfoVo> util = new ExcelUtil<ExportOtherInfoVo>(ExportOtherInfoVo.class);
		util.exportExcel(response, list, "其他科室工作量明细","其他科室工作量明细");
	}




}

