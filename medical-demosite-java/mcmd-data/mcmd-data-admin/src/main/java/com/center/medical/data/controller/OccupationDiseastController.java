package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.OccupationDiseast;
import com.center.medical.data.bean.model.OccupationDiseastClass;
import com.center.medical.data.service.OccupationDiseastClassService;
import com.center.medical.data.service.OccupationDiseastService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 职业检查设置-职业病名称维护(OccupationDiseast)表控制层
 *
 * @author ay
 * @since 2022-11-17 08:50:29
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "职业检查设置-职业病名称维护")
@RequestMapping("occupationDiseast")
public class OccupationDiseastController extends BaseController {
	/**
	* 服务对象
	*/
	private final OccupationDiseastService occupationDiseastService;
    private final MapperFacade mapperFacade;
    private final OccupationDiseastClassService occupationDiseastClassService;

	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param occupationDiseast 查询实体
	* @return 所有数据
	*/
	@GetMapping("/list")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询JC职业病名称")
	public R<IPage<OccupationDiseast>> selectAll(PageParamSimple pageParamSimple, OccupationDiseast occupationDiseast) {
		PageParam<OccupationDiseast> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.occupationDiseastService.getList(page, occupationDiseast));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查JC职业病名称详情")
	public R<OccupationDiseast> selectOne(@PathVariable String id) {
		return R.ok(this.occupationDiseastService.getInfoById(id));
	}

	/**
	* 保存或更新数据
	*
	* @param occupationDiseast 实体对象
	* @return 新增结果
	*/
	@PostMapping("/saveOrUpdate")
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "保存或更新数据", notes = "保存或更新数据JC职业病名称")
	@Log(title = "JC职业病名称", businessType = BusinessType.INSERT)
	@ApiOperationSupport(ignoreParameters = {"occupationDiseast.id"})
	public R saveOrUpdate(@RequestBody OccupationDiseast occupationDiseast) {
		String s = occupationDiseastService.saveOrUpdateOccu(occupationDiseast);
		return R.ok(s);
	}

	/**
	* 编辑时返回数据
	*
	* @param id
	* @return 修改结果
	*/
	@GetMapping("/edit/{id}")
	//@PreAuthorize("@ss.hasPermi('::edit')")
	@ApiOperation(value = "编辑时返回数据", notes = "编辑时返回数据JC职业病名称")
	public R edit(@PathVariable String id) {
		OccupationDiseast occupationDiseast = occupationDiseastService.getInfoById(id);
		OccupationDiseastClass occupationDiseastClass = occupationDiseastClassService.getInfoById(occupationDiseast.getOccupationDiseastClass());
		Map map = new HashMap();
		map.put("OccupationDiseast",occupationDiseast);
		map.put("OccupationDiseastClass",occupationDiseastClass);
		return R.ok(map);
	}

	/**
	* 逻辑删除
	*
	* @param ids 主键结合
	* @return 删除结果
	*/
	@DeleteMapping("/remove/{ids}")
	//@PreAuthorize("@ss.hasPermi('::remove')")
	@ApiOperation(value = "逻辑删除", notes = "删除JC职业病名称")
	@Log(title = "JC职业病名称", businessType = BusinessType.DELETE)
	public R delete(@PathVariable String ids) {
		String s = occupationDiseastService.removeOccupa(ids);
		return R.ok(s);
	}


	/**
	 * 获取职业病分类
	 * @param pageParamSimple 分页对象
	 * @param occupationDiseastClass 接收实体类
	 * @return
	 */
	@GetMapping("/getFlData")
	//@PreAuthorize("@ss.hasPermi('::edit')")
	@ApiOperation(value = "获取职业病分类", notes = "获取职业病分类")
	public R getFlData(PageParamSimple pageParamSimple,OccupationDiseastClass occupationDiseastClass) {
		PageParam<OccupationDiseastClass> page = mapperFacade.map(pageParamSimple, PageParam.class);
		IPage<OccupationDiseastClass> occupationDiseastClassIPage = occupationDiseastClassService.getList(page,occupationDiseastClass);
		return R.ok(occupationDiseastClassIPage);
	}


	/**
	 * 获取职业病分类
	 * @param occupationDiseast
	 * @return
	 */
	@GetMapping("/getAutoCompleteData")
	//@PreAuthorize("@ss.hasPermi('::edit')")
	@ApiOperation(value = "获取职业病分类", notes = "获取职业病分类")
	public R getAutoCompleteData(OccupationDiseast occupationDiseast) {
		if (ObjectUtils.isEmpty(occupationDiseast.getInputCode())){
			throw new ServiceException("请输入输入码");
		}
		List<OccupationDiseast> list = occupationDiseastService.list(new QueryWrapper<OccupationDiseast>()
				.orderByAsc("input_code").eq("is_delete", 0)
				.and(wrapper -> wrapper.like("input_code", occupationDiseast.getInputCode().toUpperCase())
						.or().like("occupation_diseast", occupationDiseast.getInputCode())));
		return R.ok(list);
	}



}

