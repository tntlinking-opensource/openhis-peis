package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.member.bean.model.Satisfaction;
import com.center.medical.member.bean.param.SecondInterviewParam;
import com.center.medical.member.service.SatisfactionService;
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
 * 满意度管理-不满意客户深访(Satisfaction)表控制层
 *
 * @author ay
 * @since 2022-11-28 11:23:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "满意度管理-不满意客户深访")
@RequestMapping("member/secondInterview")
public class SecondInterviewController extends BaseController {
	/**
	* 服务对象
	*/
	private final SatisfactionService satisfactionService;
    private final MapperFacade mapperFacade;

	/**
	* 不满意客户深访分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param secondInterviewParam 查询条件
	* @return 所有数据
	*/
	@GetMapping("/page")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询KF满意度")
	public R<IPage<Satisfaction>> getPage(PageParamSimple pageParamSimple, SecondInterviewParam secondInterviewParam) {
		PageParam<Satisfaction> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.satisfactionService.getSecondInterviewPage(page, secondInterviewParam));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查KF满意度详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
	public R<Satisfaction> selectOne(@PathVariable String id) {
		return R.ok(this.satisfactionService.getInfoById(id));
	}

	/**
	* 更新
	*
	* @param satisfaction 实体对象
	* @return 新增结果
	*/
	@PostMapping
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "更新", notes = "更新")
	public R saOrUp(@RequestBody Satisfaction satisfaction) {
		return R.toResult(this.satisfactionService.saOrUpSecondInterview(satisfaction));
	}




	/**
	 * 再次不满意数据导出excel
	 * @param response
	 * @param secondInterviewParam
	 */
	@PostMapping("/export")
	@ApiOperation(value = "再次不满意数据导出excel", notes = "再次不满意数据导出excel")
	public void export(HttpServletResponse response, SecondInterviewParam secondInterviewParam) {
		//不分页，根据筛选条件查全部
		List<Satisfaction> list = satisfactionService.exportSecondInterview(secondInterviewParam);
		ExcelUtil<Satisfaction> util = new ExcelUtil<Satisfaction>(Satisfaction.class);
		util.exportExcel(response, list, "再次不满意数据");
	}


	/**
	 * 获取科室回访全部为非常满意的数据
	 * @param pageParamSimple
	 * @param secondInterviewParam
	 * @return
	 */
	@GetMapping("/getFcmyListData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "获取科室回访全部为非常满意的数据", notes = "获取科室回访全部为非常满意的数据")
	public R<IPage<Satisfaction>> getFcmyListData(PageParamSimple pageParamSimple, SecondInterviewParam secondInterviewParam) {
		PageParam<Satisfaction> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.satisfactionService.getFcmyListSecondData(page, secondInterviewParam));
	}


	/**
	 * 导出科室回访全部为非常满意数据
	 * @param response
	 * @param secondInterviewParam
	 */
	@PostMapping("/exports")
	@ApiOperation(value = "导出科室回访全部为非常满意数据", notes = "导出科室回访全部为非常满意数据")
	public void exports(HttpServletResponse response, SecondInterviewParam secondInterviewParam) {
		//不分页，根据筛选条件查全部
		List<Satisfaction> list = satisfactionService.exportsSecondInterview(secondInterviewParam);
		ExcelUtil<Satisfaction> util = new ExcelUtil<Satisfaction>(Satisfaction.class);
		util.exportExcel(response, list, "非常满意数据");
	}


}

