package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.AdvanceVisitWrite;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.member.bean.param.ARsaOrUpParam;
import com.center.medical.member.bean.param.AppointReturnParam;
import com.center.medical.member.bean.vo.AppointReturnVo;
import com.center.medical.member.service.AppointReturnService;
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
 * 主表记录体检者是否来检，如果来检，显示来检时间，便于筛选，在预约来检未检列表中显示来检时间且是否已检为未检的做出标记，在预约来检未检回访操作时将该状态、来检时间重置，以此进行无限循环(AdvanceVisitWrite)表控制层
 *
 * @author makejava
 * @since 2023-02-16 14:03:32
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "回访管理-预约来检回访")
@RequestMapping("/member/advanceVisitWrite")
public class AppointReturnController extends BaseController {
	/**
	* 服务对象
	*/
	private final AppointReturnService appointReturnService;
    private final MapperFacade mapperFacade;

	/**
	 * 接口说明
	 * @return
	 */
	@GetMapping("/getInterfaceList")
	@ApiOperation(value = "接口说明",notes = "获取【回访管理-预约来检回访】这个业务模块所有接口及接口说明")
	public R<FunctionVo> getInterfaceList(){
		List<InterfaceVo> interfaceVos = Arrays.asList(
				new InterfaceVo("分页查询","GET","/member/advanceVisitWrite/page","客服系统->客服管理-回访管理-预约来检回访->分页查询",null),
				new InterfaceVo("详情","GET","/member/advanceVisitWrite/{id}","客服系统->客服管理-回访管理-预约来检回访->详情",null),
				new InterfaceVo("导出Excel","POST","/member/advanceVisitWrite/export","客服系统->客服管理-回访管理-预约来检回访->导出Excel",null),
				new InterfaceVo("预约来检回访","POST","/member/advanceVisitWrite/saveOrUpdate","客服系统->客服管理-回访管理-预约来检回访->个检危险值回访",null)
		);
		return R.ok(new FunctionVo("客服系统","回访管理-预约来检回访",interfaceVos,"客服系统"));
	}

	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param param param
	* @return 所有数据
	*/
	@GetMapping("/page")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询主表记录体检者是否来检，如果来检，显示来检时间，便于筛选，在预约来检未检列表中显示来检时间且是否已检为未检的做出标记，在预约来检未检回访操作时将该状态、来检时间重置，以此进行无限循环")
	public R<IPage<AppointReturnVo>> getPage(PageParamSimple pageParamSimple, AppointReturnParam param) {
		PageParam<AppointReturnVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.appointReturnService.getPage(page, param));
	}

	/**
	 * 详情
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	@ApiOperation(value = "详情",notes = "根据id查QT体检者表详情")
	@ApiImplicitParam(name = "id",value = "要查询的对象的主键{id}")
	public R<AdvanceVisitWrite> selectOne(@PathVariable String id){
		return R.ok(this.appointReturnService.getInfoById(id));
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
	public void export(HttpServletResponse response, AppointReturnParam param){
		List<AppointReturnVo> list = appointReturnService.export(param);
		ExcelUtil<AppointReturnVo> util = new ExcelUtil<>(AppointReturnVo.class);
		util.exportExcel(response,list,"不合格样本回访");
	}

	/**
	 * 保存
	 *
	 * @param
	 * @return 新增结果
	 */
	@PostMapping("/saveOrUpdate")
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "保存", notes = "保存")
	public R saOrUp(@RequestBody ARsaOrUpParam param) {
		return R.toResult(this.appointReturnService.saOrUp(param));
	}

}

