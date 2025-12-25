package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Chest;
import com.center.medical.bean.param.ChestParam;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.service.ChestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 报告柜子管理(Chest)表控制层
 *
 * @author ay
 * @since 2022-12-17 11:41:35
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告柜子管理")
@RequestMapping("/report/chest")
public class ChestController extends BaseController {
	/**
	* 服务对象
	*/
	private final ChestService chestService;
    private final MapperFacade mapperFacade;
    private final CreateorderService createorderService;
    private final SellcustomerService sellcustomerService;


	/**
	 * 【报告柜子管理】功能接口总结
	 */
	@GetMapping("/getInterfaceList")
	@ApiOperation(value = "接口说明", notes = "获取【报告柜子管理】这个块业务所有接口及接口说明")
	public R<FunctionVo> getInterfaceList() {
		List<InterfaceVo> interfaceVos = Arrays.asList(
				new InterfaceVo("分页查询", "GET", "/report/chest/page", "10.总检-报告系统->报告管理-报告领取-报告柜子管理->分页查询", null),
				new InterfaceVo("详情", "GET", "/report/chest/{id}", "10.总检-报告系统->报告管理-报告领取-报告柜子管理->详情", null),
				new InterfaceVo("保存或更新", "POST", "/report/chest", "10.总检-报告系统->报告管理-报告领取-报告柜子管理->保存或更新", null),
				new InterfaceVo("删除", "DELETE", "/report/chest/{ids}", "10.总检-报告系统->报告管理-报告领取-报告柜子管理->删除", null),
				new InterfaceVo("获取订单名称", "GET", "/report/chest/getDdData/{ddh}", "10.总检-报告系统->报告管理-报告领取-报告柜子管理->获取订单名称", null),
				new InterfaceVo("导出列表", "POST", "/report/chest/export", "10.总检-报告系统->报告管理-报告领取-报告柜子管理->导出列表", null),
				new InterfaceVo("获取订单号下拉", "GET", "/report/chest/getDdhData", "10.总检-报告系统->报告管理-报告领取-报告柜子管理->获取订单号下拉", null),
				new InterfaceVo("发票", "GET", "/report/chest/getDdhDataFp", "10.总检-报告系统->报告管理-报告领取-报告柜子管理->发票", null)
				);
		return R.ok(new FunctionVo("10.总检-报告系统", "报告柜子管理", interfaceVos, "10.总检/报告系统"));
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
	@ApiOperation(value = "分页查询", notes = "分页查询订单柜子信息")
	public R<IPage<Chest>> getPage(PageParamSimple pageParamSimple, ChestParam param) {
		PageParam<Chest> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.chestService.getPage(page, param));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查订单柜子信息详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
	public R<Chest> selectOne(@PathVariable String id) {
		return R.ok(this.chestService.getInfoById(id));
	}

	/**
	* 保存或更新
	*
	* @param chest 实体对象
	* @return 新增结果
	*/
	@PostMapping
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "保存或更新", notes = "保存或更新")
	public R saOrUp(@RequestBody Chest chest) {
		return R.toResult(this.chestService.saOrUp(chest));
	}



	/**
	* 删除数据
	*
	* @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
	* @return 删除结果
	*/
	@DeleteMapping("/{ids}")
	//@PreAuthorize("@ss.hasPermi('::remove')")
	@ApiOperation(value = "删除", notes = "删除订单柜子信息")
	@Log(title = "订单柜子信息", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
	public R delete(@PathVariable List<String> ids) {
		Chest chest = new Chest();
		//将isDelete设置为1，为删除
		chest.setIsDelete(1);
		boolean b = chestService.update(chest, new UpdateWrapper<Chest>().in("id", ids));
		return R.toResult(b);
	}


	/**
	 * 获取订单号相关内容
	 *
	 * @param ddh 主键
	 * @return 单条数据
	 */
	@GetMapping("/getDdData/{ddh}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "获取订单名称", notes = "获取订单名称")
	@ApiImplicitParam(name = "ddh", value = "订单号{ddh}")
	public R getDdData(@PathVariable String ddh) {
		HashMap result = new HashMap();
		Createorder card = createorderService.getOne(new QueryWrapper<Createorder>().eq("ddh", ddh));
		if(ObjectUtils.isNotEmpty(card)){
			Sellcustomer sell = sellcustomerService.getInfoById(card.getKhdwmcid());
			result.put("dwmc", sell.getKhdwmc()==null?null:sell.getKhdwmc());
		}
		return R.ok(result);
	}



	/**
	 * 导出分中心管理列表
	 */
	@PostMapping("/export")
	@PreAuthorize("@ss.hasPermi('system:branch:export')")
	@ApiOperation(value = "导出列表", notes = "导出报告柜子管理")
	@Log(title = "报告柜子管理", businessType = BusinessType.EXPORT)
	public void export(HttpServletResponse response, Chest chest) {
		List<Chest> list = chestService.getExportData(chest);
		ExcelUtil<Chest> util = new ExcelUtil<Chest>(Chest.class);
		util.exportExcel(response, list, "柜子管理");
	}




	/**
	 * 获取订单号下拉
	 *
	 * @param ddh 主键
	 * @return 单条数据
	 */
	@GetMapping("/getDdhData")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "获取订单号下拉", notes = "获取订单号下拉")
	@ApiImplicitParam(name = "ddh", value = "订单号")
	public R getDdhData(PageParamSimple pageParamSimple,String ddh) {
		PageParam<Createorder> page = mapperFacade.map(pageParamSimple, PageParam.class);
		IPage<Createorder> iPage = createorderService.getDdhData(page,ddh,null);
		return R.ok(iPage);
	}


	/**
	 * 发票
	 *
	 * @param ddmc
	 * @return
	 */
	@GetMapping("/getDdhDataFp")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "发票", notes = "发票")
	@ApiImplicitParam(name = "ddmc", value = "订单名称")
	public R getDdhDataFp(PageParamSimple pageParamSimple,String ddmc) {
		PageParam<Createorder> page = mapperFacade.map(pageParamSimple, PageParam.class);
		IPage<Createorder> iPage = createorderService.getDdhData(page,null,ddmc);
		return R.ok(iPage);
	}




}

