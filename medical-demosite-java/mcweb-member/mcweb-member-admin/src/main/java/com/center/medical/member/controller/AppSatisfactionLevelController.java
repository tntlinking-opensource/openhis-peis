package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Questionnaire;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.member.bean.param.AppSatisfactionLevelParam;
import com.center.medical.member.bean.vo.AppSatisfactionLevelVo;
import com.center.medical.report.service.QuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 满意度管理-前台满意度与科室满意度(Satisfaction)表控制层
 *
 * @author ay
 * @since 2022-11-25 16:24:04
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "满意度管理-小程序体检满意度")
@RequestMapping("/member/appSatisfactionLevel")
public class AppSatisfactionLevelController extends BaseController {
	/**
	* 服务对象
	*/
	private final QuestionnaireService questionnaireService;
    private final MapperFacade mapperFacade;

	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param param 查询条件
	* @return 所有数据
	*/
	@GetMapping("/page")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询KF满意度")
	public R<IPage<AppSatisfactionLevelVo>> getPage(PageParamSimple pageParamSimple, AppSatisfactionLevelParam param) {
		PageParam<AppSatisfactionLevelVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.questionnaireService.getAppSatisfactionLevel(page, param));
	}



	/**
	 * 导出
	 *
	 * @param response
	 * @param param
	 */
	@PostMapping("/export")
	@ApiOperation(value = "导出", notes = "导出小程序体检满意度")
	@Log(title = "小程序体检满意度", businessType = BusinessType.EXPORT)
	public void export(HttpServletResponse response, AppSatisfactionLevelParam param) {
		List<AppSatisfactionLevelVo> list = questionnaireService.getExportData(param);
		ExcelUtil<AppSatisfactionLevelVo> util = new ExcelUtil<AppSatisfactionLevelVo>(AppSatisfactionLevelVo.class);
		util.exportExcel(response, list, "小程序体检满意度");
	}


	/**
	 * 分页查询所有数据
	 *
	 * @param id id
	 * @return 所有数据
	 */
	@GetMapping("/details")
	@ApiOperation(value = "详情", notes = "详情")
	@ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
	public R<Questionnaire> details(String id) {
		return R.ok(this.questionnaireService.getInfoById(id));
	}



	/**
	 * 修改数据
	 *
	 * @param questionnaire 实体对象
	 * @return 修改结果
	 */
	@PutMapping("/update")
	@ApiOperation(value = "更新", notes = "更新满意度")
	public R update(@RequestBody Questionnaire questionnaire) {
		return R.toResult(this.questionnaireService.updateById(questionnaire));
	}

}

