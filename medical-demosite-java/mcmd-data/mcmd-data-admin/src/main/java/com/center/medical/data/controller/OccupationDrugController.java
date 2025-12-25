package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.OccupationDrug;
import com.center.medical.data.service.HarmService;
import com.center.medical.data.service.OccupationDrugService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 职业检查设置-危害因素诊断标准依据维护(OccupationDrug)表控制层
 *
 * @author ay
 * @since 2022-11-17 17:05:21
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "职业检查设置-危害因素诊断标准依据维护")
@RequestMapping("occupationDrug")
public class OccupationDrugController extends BaseController {
	/**
	* 服务对象
	*/
	private final OccupationDrugService occupationDrugService;
    private final MapperFacade mapperFacade;
    private final HarmService harmService;

	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param occupationDrug 查询实体
	* @return 所有数据
	*/
	@GetMapping("/list")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询JC职业病/禁忌症")
	public R<IPage<OccupationDrug>> selectAll(PageParamSimple pageParamSimple, OccupationDrug occupationDrug) {
		PageParam<OccupationDrug> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.occupationDrugService.getList(page, occupationDrug));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查JC职业病/禁忌症详情")
	public R<OccupationDrug> selectOne(@PathVariable String id) {
		return R.ok(this.occupationDrugService.getInfoById(id));
	}

	/**
	* 新增或保存数据
	*
	* @param occupationDrug 实体对象
	* @return 新增结果
	*/
	@PostMapping("/saveOrUpdate")
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "保存或更新", notes = "保存或更新")
	@Log(title = "JC职业病/禁忌症", businessType = BusinessType.INSERT)
	public R saveOrUpdate(@RequestBody OccupationDrug occupationDrug) {
		Boolean s = occupationDrugService.saveOrUpdateOccup(occupationDrug);
		return R.toResult(s);
	}



	/**
	* 删除数据
	*
	* @param ids 主键结合
	* @return 删除结果
	*/
	@DeleteMapping("/remove/{ids}")
	//@PreAuthorize("@ss.hasPermi('::remove')")
	@ApiOperation(value = "删除", notes = "删除JC职业病/禁忌症")
	@Log(title = "JC职业病/禁忌症", businessType = BusinessType.DELETE)
	public R remove(@PathVariable String ids) {
		String s = occupationDrugService.removeOccupa(ids);
		return R.ok(s);
	}


	/**
	 * 根据inout获取危害因素
	 * @param inputCode
	 * @return
	 */
	@GetMapping("/getHarmData")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "根据inout获取危害因素", notes = "根据inout获取危害因素")
	public R getHarmData(String inputCode) {
		QueryWrapper<Harm> queryWrapper = new QueryWrapper<>();
		if(StringUtils.isNotEmpty(inputCode)){
			queryWrapper.like("input_code", inputCode.toUpperCase());
		}
		List<Harm> list = harmService.list(queryWrapper);
		return R.ok(list);
	}








}

