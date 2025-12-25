package com.center.medical.sellcrm.controller.crm;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.bean.model.Report;
import com.center.medical.sellcrm.bean.param.ReportRemindParam;
import com.center.medical.sellcrm.bean.vo.ReportRemindVo;
import com.center.medical.sellcrm.service.ReportRemindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 待领取报告(Report)表控制层
 *
 * @author ay
 * @since 2023-02-08 15:34:05
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "待领取报告")
@RequestMapping("/crm/pendingReport")
public class PendingReportController extends BaseController {
	/**
	* 服务对象
	*/
	private final ReportRemindService reportService;
    private final MapperFacade mapperFacade;


	/**
	 * 【待领取报告】功能接口总结
	 */
	@GetMapping("/getInterfaceList")
	@ApiOperation(value = "接口说明", notes = "获取【待领取报告】这个块业务所有接口及接口说明")
	public R<FunctionVo> getInterfaceList() {
		List<InterfaceVo> interfaceVos = Arrays.asList(
				new InterfaceVo("分页查询", "GET", "/crm/pendingReport/page", "06.客户销售系统->销售管理-待领取报告->分页查询", null),
				new InterfaceVo("详情", "GET", "/crm/pendingReport/{id}", "06.客户销售系统->销售管理-待领取报告->详情", null)
		);
		return R.ok(new FunctionVo("06.客户销售系统", "销售管理-待领取报告", interfaceVos, "06.客户销售系统"));
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
	@ApiOperation(value = "分页查询", notes = "分页查询BG报告主表")
	public R<IPage<ReportRemindVo>> getPage(PageParamSimple pageParamSimple, ReportRemindParam param) {
		PageParam<ReportRemindVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.reportService.getList(page, param));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查BG报告主表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
	public R<Report> selectOne(@PathVariable String id) {
		return R.ok(this.reportService.getInfoById(id));
	}


}

