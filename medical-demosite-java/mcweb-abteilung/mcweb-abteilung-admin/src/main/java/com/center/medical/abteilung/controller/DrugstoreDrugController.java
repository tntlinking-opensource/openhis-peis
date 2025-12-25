package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.DrugstoreDrug;
import com.center.medical.abteilung.bean.param.DrugstoreDrugParam;
import com.center.medical.abteilung.bean.vo.ClassCheckDataVo;
import com.center.medical.abteilung.bean.vo.DrugstoreDrugVo;
import com.center.medical.abteilung.service.DrugstoreClassService;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.abteilung.service.DrugstoreDrugService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 药房管理-药品管理(DrugstoreDrug)表控制层
 *
 * @author ay
 * @since 2023-01-12 15:23:12
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "药房管理-药品管理")
@RequestMapping("/drugstore/drug")
public class DrugstoreDrugController extends BaseController {
	/**
	* 服务对象
	*/
	private final DrugstoreDrugService drugstoreDrugService;
    private final MapperFacade mapperFacade;
    private final DrugstoreClassService drugstoreClassService;


	/**
	 * 【药房管理-药品管理】功能接口总结
	 */
	@GetMapping("/getInterfaceList")
	@ApiOperation(value = "接口说明", notes = "获取【药房管理-药品管理】这个块业务所有接口及接口说明")
	public R<FunctionVo> getInterfaceList() {
		List<InterfaceVo> interfaceVos = Arrays.asList(
				new InterfaceVo("分页查询", "GET", "/drugstore/drug/page", "09.科室系统->科室管理-药房管理-药品管理->分页查询", null),
				new InterfaceVo("详情", "GET", "/drugstore/drug/{id}", "09.科室系统->科室管理-药房管理-药品管理->详情", null),
				new InterfaceVo("添加或保存", "POST", "/drugstore/drug/saveOrUpdate", "09.科室系统->科室管理-药房管理-药品管理->添加或保存", null),
				new InterfaceVo("删除", "DELETE", "/drugstore/drug/{ids}", "09.科室系统->科室管理-药房管理-药品管理->删除", null),
				new InterfaceVo("通过药品名称查询", "GET", "/drugstore/drug/getSelectData", "09.科室系统->科室管理-药房管理-药品管理->通过药品名称查询", null),
				new InterfaceVo("获取类别代号下拉列表", "GET", "/drugstore/drug/getClassCheckData", "09.科室系统->科室管理-药房管理-药品管理->获取类别代号下拉列表", null)
		);
		return R.ok(new FunctionVo("09.科室系统", "药房管理-药品管理", interfaceVos, "09.科室系统"));
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
	@ApiOperation(value = "分页查询", notes = "分页查询药品基础表")
	public R<IPage<DrugstoreDrugVo>> getPage(PageParamSimple pageParamSimple, DrugstoreDrugParam param) {
		PageParam<DrugstoreDrugVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.drugstoreDrugService.getList(page, param));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查药品基础表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
	public R<DrugstoreDrug> selectOne(@PathVariable String id) {
		return R.ok(this.drugstoreDrugService.getInfoById(id));
	}

	/**
	* 添加或保存
	*
	* @param drugstoreDrug 实体对象
	* @return 新增结果
	*/
	@PostMapping("/saveOrUpdate")
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "添加或保存", notes = "添加或保存，老系统操作不了，参数可能少一些，到时候联系我添加")
	public R insert(@RequestBody DrugstoreDrug drugstoreDrug) {
		return R.toResult(this.drugstoreDrugService.saOrUp(drugstoreDrug));
	}


	/**
	* 删除数据
	*
	* @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
	* @return 删除结果
	*/
	@DeleteMapping("/{ids}")
	//@PreAuthorize("@ss.hasPermi('::remove')")
	@ApiOperation(value = "删除", notes = "删除药品基础表")
	@Log(title = "药品基础表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
	public R delete(@PathVariable List<String> ids) {
		return R.toResult(this.drugstoreDrugService.removeByIds(ids));
	}


	/**
	 * 通过药品名称查询
	 * @param drugName
	 * @return
	 */
	@GetMapping("/getSelectData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "通过药品名称查询", notes = "通过药品名称查询，不分页")
	public R<List<DrugstoreDrugVo>> getSelectData(String drugName) {
		DrugstoreDrugParam param = new DrugstoreDrugParam();
		// 去空格
		if (ObjectUtils.isNotEmpty(drugName)){
			param.setDrugName(drugName.trim());
		}
		List<DrugstoreDrugVo> list = drugstoreDrugService.getSelectData(param);
		return R.ok(list);
	}


	/**
	 * 获取类别代号下拉列表
	 * @param key
	 * @return
	 */
	@GetMapping("/getClassCheckData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "获取类别代号下拉列表", notes = "获取类别代号下拉列表")
	public R<List<ClassCheckDataVo>> getClassCheckData(String key) {
		List<ClassCheckDataVo> list = drugstoreClassService.getClassCheckData(key);
		return R.ok(list);
	}




}

