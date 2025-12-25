package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.member.bean.model.Satisfaction;
import com.center.medical.member.bean.param.SatisfactionParam;
import com.center.medical.member.bean.vo.BmyHfVo;
import com.center.medical.member.bean.vo.FirstInterviewVo;
import com.center.medical.member.service.SatisfactionService;
import com.center.medical.service.PeispatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 满意度管理-满意度回访(Satisfaction)表控制层
 *
 * @author ay
 * @since 2022-11-28 11:23:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "满意度管理-满意度回访")
@RequestMapping("/member/firstInterview")
public class FirstInterviewController extends BaseController {
	/**
	* 服务对象
	*/
	private final SatisfactionService satisfactionService;
    private final MapperFacade mapperFacade;
    private final PeispatientService peispatientService;

	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param satisfactionParam 查询条件
	* @return 所有数据
	*/
	@GetMapping("/page")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询KF满意度")
	public R<IPage<FirstInterviewVo>> getPage(PageParamSimple pageParamSimple, SatisfactionParam satisfactionParam) {
		PageParam<FirstInterviewVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.satisfactionService.getFirstInterviewPage(page, satisfactionParam));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("/bmyHf")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "不满意客户回访", notes = "不满意客户回访")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "patientcode", value = "体检号"),
			@ApiImplicitParam(name = "id", value = "分页的sid,参数可以不传")
	})
	public R<BmyHfVo> selectOne(@RequestParam(value="patientcode", required=true)String patientcode,
								@RequestParam(value="id", required=false)String id) {
		//返回对象
		BmyHfVo vo = new BmyHfVo();
		Peispatient peispatient = peispatientService.getByPatientCode(patientcode);
		vo.setPeispatient(peispatient);

		//客户满意度
		if(StringUtils.isNotEmpty(id)){
			Satisfaction satisfaction = satisfactionService.getInfoById(id);
			if(ObjectUtils.isEmpty(satisfaction)){
				satisfaction = new Satisfaction();
				satisfaction.setAppraiseResult("0");
			}
			vo.setSatisfaction(satisfaction);
		}


		return R.ok(vo);
	}

	/**
	* 新增或修改数据
	*
	* @param satisfaction 实体对象
	* @return 新增结果
	*/
	@PostMapping
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "新增或修改数据", notes = "新增或修改数据")
	public R saOrUp(@RequestBody Satisfaction satisfaction) {
		return R.toResult(this.satisfactionService.saOrUpFirstInterview(satisfaction));
	}




	/**
	 * 获取回访全部为非常满意的数据
	 * @param pageParamSimple
	 * @param satisfactionParam
	 * @return
	 */
	@GetMapping("/getFcmyListData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "获取回访全部为非常满意的数据", notes = "获取回访全部为非常满意的数据")
	public R<IPage<FirstInterviewVo>> getFcmyListData(PageParamSimple pageParamSimple, SatisfactionParam satisfactionParam) {
		PageParam<FirstInterviewVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.satisfactionService.getFcmyListData(page, satisfactionParam));
	}


	/**
	 * 不满意客户回访导出excel
	 * @param response
	 * @param satisfactionParam
	 */
	@Log(title = "满意度回访", businessType = BusinessType.EXPORT)
//	@PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
	@ApiOperation(value = "不满意客户回访导出excel", notes = "不满意客户回访导出excel")
	@PostMapping("/export")
	public void export(HttpServletResponse response,SatisfactionParam satisfactionParam) {
		List<FirstInterviewVo>  list = satisfactionService.exportFirstInterview(satisfactionParam);
		ExcelUtil<FirstInterviewVo> util = new ExcelUtil<FirstInterviewVo>(FirstInterviewVo.class);
		util.exportExcel(response, list, "满意度回访");
	}



	/**
	 * 导出回访全部为非常满意的数据
	 * @param response
	 * @param satisfactionParam
	 */
	@Log(title = "满意度回访", businessType = BusinessType.EXPORT)
//	@PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
	@ApiOperation(value = "导出回访全部为非常满意的数据", notes = "导出回访全部为非常满意的数据")
	@PostMapping("/exports")
	public void exports(HttpServletResponse response,SatisfactionParam satisfactionParam) {
		List<Satisfaction>  list = satisfactionService.exportsFirstInterview(satisfactionParam);
		ExcelUtil<Satisfaction> util = new ExcelUtil<Satisfaction>(Satisfaction.class);
		util.exportExcel(response, list, "满意度回访");
	}





}

