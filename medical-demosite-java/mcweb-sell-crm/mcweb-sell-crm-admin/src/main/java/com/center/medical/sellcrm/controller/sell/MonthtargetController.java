package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.sellcrm.bean.model.Monthtarget;
import com.center.medical.sellcrm.bean.model.Selltarget;
import com.center.medical.sellcrm.bean.param.MTSaOrUpParam;
import com.center.medical.sellcrm.bean.param.MonthtargetParam;
import com.center.medical.sellcrm.bean.vo.MTSummaryVo;
import com.center.medical.sellcrm.bean.vo.MonthtargetVo;
import com.center.medical.sellcrm.service.LeadertargetService;
import com.center.medical.sellcrm.service.MonthtargetService;
import com.center.medical.sellcrm.service.SelltargetService;
import com.center.medical.system.service.BranchService;
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
import java.util.Arrays;
import java.util.List;

/**
 * 销售月度目标(Monthtarget)表控制层
 *
 * @author ay
 * @since 2023-02-09 17:32:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售月度目标")
@RequestMapping("sell/monthtarget")
public class MonthtargetController extends BaseController {
	/**
	* 服务对象
	*/
	private final MonthtargetService monthtargetService;
    private final MapperFacade mapperFacade;
    private final LeadertargetService leadertargetService;
    private final BranchService branchService;
    private final SelltargetService selltargetService;


	/**
	 * 【销售月度目标】功能接口总结
	 */
	@GetMapping("/getInterfaceList")
	@ApiOperation(value = "接口说明", notes = "获取【销售月度目标】这个块业务所有接口及接口说明")
	public R<FunctionVo> getInterfaceList() {
		List<InterfaceVo> interfaceVos = Arrays.asList(
				new InterfaceVo("分页查询", "GET", "/sell/monthtarget/page", "06.客户销售系统->销售管理-销售目标-销售月度目标->分页查询", null),
				new InterfaceVo("详情", "GET", "/sell/monthtarget/{id}", "06.客户销售系统->销售管理-销售目标-销售月度目标->详情", null),
				new InterfaceVo("返回年份", "GET", "/sell/monthtarget/getAllYear", "06.客户销售系统->销售管理-销售目标-销售月度目标->返回年份", null),
				new InterfaceVo("获取分中心数据", "GET", "/sell/monthtarget/getBranchData", "06.客户销售系统->销售管理-销售目标-销售月度目标->获取分中心数据", null),
				new InterfaceVo("获取总结数据", "GET", "/sell/monthtarget/getSummaryData", "06.客户销售系统->销售管理-销售目标-销售月度目标->获取总结数据", null),
				new InterfaceVo("判断之前是否制定了销售季度目标", "GET", "/sell/monthtarget/isXsjdmb", "06.客户销售系统->销售管理-销售目标-销售月度目标->判断之前是否制定了销售季度目标", null),
				new InterfaceVo("数据保存或编辑", "POST", "/sell/monthtarget/saveOrUpdate", "06.客户销售系统->销售管理-销售目标-销售月度目标->数据保存或编辑", null),
				new InterfaceVo("判断是否允许编辑", "GET", "/sell/monthtarget/isEdit", "06.客户销售系统->销售管理-销售目标-销售月度目标->判断是否允许编辑", null),
				new InterfaceVo("判断是否允许查看", "GET", "/sell/monthtarget/isView", "06.客户销售系统->销售管理-销售目标-销售月度目标->判断是否允许查看", null),
				new InterfaceVo("导出", "POST", "/sell/monthtarget/export", "06.客户销售系统->销售管理-销售目标-销售月度目标->导出", null)
		);
		return R.ok(new FunctionVo("06.客户销售系统", "销售目标-销售月度目标", interfaceVos, "06.客户销售系统"));
	}



	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param param 查询条件
	* @return 所有数据
	*/
	@GetMapping("/page")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询销售月度计划")
	public R<IPage<MonthtargetVo>> getPage(PageParamSimple pageParamSimple, MonthtargetParam param) {
		PageParam<MonthtargetVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.monthtargetService.getList(page, param));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查销售月度计划详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
	public R<Monthtarget> selectOne(@PathVariable String id) {
		return R.ok(this.monthtargetService.getInfoById(id));
	}





	/**
	 * 返回年份
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
	 * 获取分中心数据
	 * @return
	 */
	@GetMapping("/getBranchData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "获取分中心数据", notes = "获取分中心数据")
	public R<List<Branch>> getBranchData() {
		List<Branch> list = branchService.list(new QueryWrapper<Branch>().orderByAsc("branch_sort")
				.eq("is_delete", 0));
		return R.ok(list);
	}


	/**
	 * 获取总结数据
	 * @param param
	 * @return
	 */
	@GetMapping("/getSummaryData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "获取总结数据", notes = "获取总结数据")
	public R<List<MTSummaryVo>> getSummaryData(MonthtargetParam param) {
		List<MTSummaryVo> list = monthtargetService.getSummaryData(param);
		return R.ok(list);
	}


	/**
	 * 判断之前是否制定了销售季度目标
	 * @param isYear
	 * @param isUserid
	 * @return
	 */
	@GetMapping("/isXsjdmb")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "判断之前是否制定了销售季度目标", notes = "判断之前是否制定了销售季度目标")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "isYear", value = "年"),
			@ApiImplicitParam(name = "isUserid", value = "用户id")
	})
	public R isXsjdmb(String isYear,String isUserid) {
		Boolean state = true;
		//获取当前登录者分中心id
		String fzxId = SecurityUtils.getCId();
		Selltarget sellTarget = selltargetService.getOne(new QueryWrapper<Selltarget>()
				.eq("xsjlid", isUserid).eq("year", isYear).eq("fzxid", fzxId));
		if(ObjectUtils.isNotEmpty(sellTarget)){
			//之前已经制定过了季度目标
			state = false;
		}
		return R.ok(state);
	}


	/**
	 * 数据保存或编辑
	 * @param param
	 * @return
	 */
	@PostMapping("/saveOrUpdate")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "数据保存或编辑", notes = "数据保存或编辑")
	public R saveOrUpdate(@RequestBody MTSaOrUpParam param) {
		Boolean b = monthtargetService.saOrUp(param);
		return R.toResult(b);
	}


	/**
	 * 判断是否允许编辑
	 * @param selltyear
	 * @param selltuser
	 * @return
	 */
	@GetMapping("/isEdit")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "判断是否已经制定了季度目标", notes = "判断是否已经制定了季度目标,true是false否")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "selltyear", value = "年"),
			@ApiImplicitParam(name = "selltuser", value = "用户id")
	})
	public R isEdit(String selltyear,String selltuser) {
		String state="true";
		//获取当前登录者用户的分中心id
		String fzxId = SecurityUtils.getCId();
		long i = monthtargetService.count(new QueryWrapper<Monthtarget>()
				.eq("year", selltyear).eq("xsjlid", selltuser).eq("fzxid", fzxId));
		if(i == 0){
			//不能进行编辑
			state="false";
		}
		return R.ok(state);
	}


	/**
	 * 判断是否允许查看
	 * @param isviewyear
	 * @param isviewuserid
	 * @return
	 */
	@GetMapping("/isView")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "判断是否允许查看", notes = "判断是否允许查看,true是false否")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "isviewyear", value = "年"),
			@ApiImplicitParam(name = "isviewuserid", value = "用户id")
	})
	public R isView(String isviewyear,String isviewuserid) {
		String state="true";
		//获取当前登录者用户的分中心id
		String fzxId = SecurityUtils.getCId();
		long i = monthtargetService.count(new QueryWrapper<Monthtarget>()
				.eq("year", isviewyear).eq("xsjlid", isviewuserid).eq("fzxid", fzxId));
		if(i == 0){
			//不能进行编辑
			state="false";
		}
		return R.ok(state);
	}




	/**
	 * 导出
	 * @param response
	 * @param param
	 */
	@Log(title = "销售月度目标", businessType = BusinessType.EXPORT)
//	@PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
	@ApiOperation(value = "导出销售月度目标", notes = "导出销售月度目标")
	@PostMapping("/export")
	public void export(HttpServletResponse response, MonthtargetParam param) {
		List<MonthtargetVo> list = monthtargetService.getExportData(param);
		ExcelUtil<MonthtargetVo> util = new ExcelUtil<MonthtargetVo>(MonthtargetVo.class);
		util.exportExcel(response, list, "销售月度目标");
	}




}

