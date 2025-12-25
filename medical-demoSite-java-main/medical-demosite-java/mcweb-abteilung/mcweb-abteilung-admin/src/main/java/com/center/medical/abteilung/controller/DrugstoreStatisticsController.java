package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.DrugstorePreParam;
import com.center.medical.abteilung.bean.vo.DrugstorePreVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.abteilung.service.DrugstorePrescribeService;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 药房管理-售药统计(DrugstorePrescribe)表控制层
 *
 * @author ay
 * @since 2023-01-13 13:42:26
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "药房管理-售药统计")
@RequestMapping("/drugstore/statistics")
public class DrugstoreStatisticsController extends BaseController {
	/**
	* 服务对象
	*/
	private final DrugstorePrescribeService drugstorePrescribeService;
    private final MapperFacade mapperFacade;
    private final ISysUserService sysUserService;




	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param param 查询条件
	* @return 所有数据
	*/
	@GetMapping("/getStatisticsListData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询药房管理售药统计", notes = "分页查询药房管理-售药统计")
	public R<IPage<DrugstorePreVo>> getStatisticsListData(PageParamSimple pageParamSimple, DrugstorePreParam param) {
		PageParam<DrugstorePreVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.drugstorePrescribeService.getStatisticsListData(page, param));
	}



	@Log(title = "售药统计", businessType = BusinessType.EXPORT)
//	@PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
	@ApiOperation(value = "导出", notes = "导出")
	@PostMapping("/export")
	public void export(HttpServletResponse response, DrugstorePreParam param) {
		List<DrugstorePreVo> list = drugstorePrescribeService.getExportData(param);
		ExcelUtil<DrugstorePreVo> util = new ExcelUtil<DrugstorePreVo>(DrugstorePreVo.class);
		util.exportExcel(response, list, "售药统计");
	}



}

