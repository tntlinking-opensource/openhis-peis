package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.statistics.bean.param.GeneralCheckingParam;
import com.center.medical.statistics.bean.vo.AnalyseTotalVo;
import com.center.medical.statistics.bean.vo.GeneralCheckingVo;
import com.center.medical.statistics.service.GeneralCheckingService;
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
import java.util.Arrays;
import java.util.List;

/**
 * 总检工作量(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-19 18:07:47
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "工作量统计-总检工作量")
@RequestMapping("statistics/generalChecking")
public class GeneralCheckingController extends BaseController {
	/**
	* 服务对象
	*/
	private final GeneralCheckingService generalCheckingService;
    private final MapperFacade mapperFacade;


	@GetMapping("/getInterfaceList")
	@ApiOperation(value = "接口说明", notes = "获取【工作量统计-总检工作量】这个块业务所有接口及接口说明")
	public R<FunctionVo> getInterfaceList() {
		List<InterfaceVo> interfaceVos = Arrays.asList(
				new InterfaceVo("获取分中心信息", "GET", "/sell/createorder/getBranchData", "财务管理->体检卡管理-卡充值->获取分中心信息", null),
				new InterfaceVo("获取医师", "GET", "/drugstore/prescribe/getDoctor", "09.科室系统->科室管理-药房管理-审核发药->获取医师", null)
		);
		return R.ok(new FunctionVo("04.统计模块", "工作量统计-总检工作量", interfaceVos, "04.统计模块"));
	}


	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param param 查询条件
	* @return 所有数据
	*/
	@GetMapping("/analyse")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
	public R<IPage<GeneralCheckingVo>> getPage(PageParamSimple pageParamSimple, GeneralCheckingParam param) {
		PageParam<GeneralCheckingVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.generalCheckingService.getList(page, param));
	}


	/**
	 * 总检医生工作量统计汇总
	 * @param pageParamSimple
	 * @param param
	 * @return
	 */
	@GetMapping("/analyseTotal")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "总检医生工作量统计汇总", notes = "总检医生工作量统计汇总")
	public R<IPage<AnalyseTotalVo>> analyseTotal(PageParamSimple pageParamSimple, GeneralCheckingParam param) {
		PageParam<AnalyseTotalVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.generalCheckingService.analyseTotal(page, param));
	}



	/**
	 * 导出Excel
	 *
	 * @param response
	 * @param param    导出参数
	 */
	@PostMapping("/export")
	@ApiOperation(value = "导出Excel", notes = "导出Excel")
	@Log(title = "导出Excel", businessType = BusinessType.EXPORT)
	public void export(HttpServletResponse response, GeneralCheckingParam param) throws IOException {
		//导出多sheet
		generalCheckingService.getExportData(response,param);
	}
}

