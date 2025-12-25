package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.member.bean.model.Satisfaction;
import com.center.medical.member.bean.param.SatisfactionParam;
import com.center.medical.member.service.SatisfactionService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.bean.model.SysDepartment;
import com.center.medical.system.service.ISysUserService;
import com.center.medical.system.service.SysDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 满意度管理-前台满意度与科室满意度(Satisfaction)表控制层
 *
 * @author ay
 * @since 2022-11-25 16:24:04
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "满意度管理-前台满意度与科室满意度")
@RequestMapping("satisfaction")
public class SatisfactionController extends BaseController {
	/**
	* 服务对象
	*/
	private final SatisfactionService satisfactionService;
    private final MapperFacade mapperFacade;
    private final SysDepartmentService sysDepartmentService;
    private final PeispatientService peispatientService;
    private final ISysUserService sysUserService;

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
	public R<IPage<Satisfaction>> getPage(PageParamSimple pageParamSimple, SatisfactionParam satisfactionParam) {
		PageParam<Satisfaction> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.satisfactionService.getPage(page, satisfactionParam));
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
	@ApiOperation(value = "更新", notes = "更新KF满意度")
	public R saOrUp(@RequestBody Satisfaction satisfaction) {
		return R.toResult(this.satisfactionService.saOrUp(satisfaction));
	}





	/**
	 * 获取对科室评价不是非常满意的数据
	 * @param pageParamSimple
	 * @param satisfactionParam
	 * @return
	 */
	@GetMapping("/getListData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "获取对科室评价不是非常满意的数据", notes = "获取对科室评价不是非常满意的数据")
	public R<IPage<Satisfaction>> getListData(PageParamSimple pageParamSimple, SatisfactionParam satisfactionParam) {
		PageParam<Satisfaction> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.satisfactionService.getListData(page, satisfactionParam));
	}


	/**
	 * 科室满意度回访
	 * @param id
	 * @return
	 */
	@GetMapping("/bmyHf/{id}")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "科室满意度回访", notes = "选中一条数据，通过体检号获取与体检号相关联的满意度实体类、体检者实体类、科室实体类的数据")
	public R bmyHf(@PathVariable String id) {
		Map map = new HashMap();
		Satisfaction satisfaction = satisfactionService.getInfoById(id);
		if (ObjectUtils.isNotEmpty(satisfaction)){
			SysDepartment sysDepartment = sysDepartmentService.getInfoById(satisfaction.getDivisionId());
			Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", satisfaction.getPersoncode()));
			map.put("SysDepartment",sysDepartment);
			map.put("Peispatient",peispatient);
		}
		map.put("Satisfaction",satisfaction);
		return R.ok(map);
	}


	/**
	 * 导出列表
	 * @param response
	 * @param satisfactionParam
	 */
	@PostMapping("/export")
	@ApiOperation(value = "导出列表", notes = "导出分中心管理列表")
	public void export(HttpServletResponse response, SatisfactionParam satisfactionParam) {
		//不分页，根据筛选条件查全部
		List<Satisfaction> list = satisfactionService.getAllList(satisfactionParam);
		ExcelUtil<Satisfaction> util = new ExcelUtil<Satisfaction>(Satisfaction.class);
		util.exportExcel(response, list, "分中心管理数据");
	}


	/**
	 * 取消不满意
	 * @param ids
	 * @return
	 */
	@PutMapping("/cancelDissat/{ids}")
	@ApiOperation(value = "取消不满意", notes = "取消不满意")
	public R cancelDissat(@PathVariable List<String> ids) {
		Boolean	result = satisfactionService.cancelDissat(ids);
		return R.toResult(result);
	}


	/**
	 * 满意度排名数据
	 * @param pageParamSimple
	 * @param satisfactionParam
	 * @return
	 */
	@GetMapping("/getAnalyseData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "满意度排名数据", notes = "满意度排名数据")
	public R getAnalyseData(PageParamSimple pageParamSimple, SatisfactionParam satisfactionParam) {
		PageParam<Satisfaction> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.satisfactionService.getAnalyseData(page, satisfactionParam));
	}




	/**
	 * 科室满意度回访
	 * @param id
	 * @return
	 */
	@GetMapping("/bmyHfQt/{id}")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "科室满意度回访Qt", notes = "选中一条数据，通过体检号获取与体检号相关联的满意度实体类、体检者实体类、科室实体类的数据")
	public R bmyHfQt(@PathVariable String id) {
		Satisfaction satisfaction = satisfactionService.getInfoById(id);
		Map map = new HashMap();
		if (ObjectUtils.isNotEmpty(satisfaction.getPersoncode())) {
			Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", satisfaction.getPersoncode()));
			map.put("peispatient",peispatient);
		}
		if (ObjectUtils.isNotEmpty(satisfaction.getKsDoctorId())){
			SysUser sysUser = sysUserService.selectUserByUserNo(satisfaction.getKsDoctorId());
			if(ObjectUtils.isNotEmpty(sysUser)){
				String doctorreg = sysUser.getUserName();
				map.put("doctorreg",doctorreg);
			}
		}


		map.put("Satisfaction",satisfaction);
		return R.ok(map);
	}




	/**
	 * 新增或更新qt
	 *
	 * @param satisfaction 实体对象
	 * @return 新增结果
	 */
	@PostMapping("/saveOrUpdateQt")
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "更新Qt", notes = "更新KF满意度Qt")
	public R saveOrUpdateQt(@RequestBody Satisfaction satisfaction) {
		return R.toResult(this.satisfactionService.saveOrUpdateQt(satisfaction));
	}



}

