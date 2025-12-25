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
import com.center.medical.data.bean.model.BaseDictionary;
import com.center.medical.data.bean.model.BaseDictionaryClass;
import com.center.medical.data.bean.model.BaseDictionaryMatch;
import com.center.medical.data.bean.param.BaseDictionaryParam;
import com.center.medical.data.bean.vo.BaseDictionaryVo;
import com.center.medical.data.service.BaseDictionaryClassService;
import com.center.medical.data.service.BaseDictionaryMatchService;
import com.center.medical.data.service.BaseDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职业检查设置-省市级平台数据对照
 *
 * @author ay
 * @since 2022-11-18 18:16:13
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业检查设置-省市级平台数据对照")
@RequestMapping("baseDictionary")
public class BaseDictionaryController extends BaseController {
	/**
	* 服务对象
	*/
	private final BaseDictionaryService baseDictionaryService;
    private final MapperFacade mapperFacade;
    private final BaseDictionaryClassService baseDictionaryClassService;
    private final BaseDictionaryMatchService baseDictionaryMatchService;

	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param baseDictionaryParam 查询实体
	* @return 所有数据
	*/
	@GetMapping("/getOurDictionary")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询字典")
	public R<IPage<BaseDictionaryVo>> getOurDictionary(PageParamSimple pageParamSimple, BaseDictionaryParam baseDictionaryParam) {
		PageParam<BaseDictionaryVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		IPage<BaseDictionaryVo> data = baseDictionaryService.getOurDictionary(page, baseDictionaryParam);
		return R.ok(data);
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("/{id}/{classCode}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查字典详情")
	public R<BaseDictionary> selectOne(@PathVariable String id,@PathVariable String classCode) {
		return R.ok(this.baseDictionaryService.getInfoById(id,classCode));
	}


	/**
	* 建立或更新数据字典匹配
	*
	* @param baseDictionaryMatch 实体对象
	* @return 新增结果
	*/
	@PostMapping("/setDataMatch")
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "建立或更新数据字典匹配", notes = "建立或更新数据字典匹配")
	@Log(title = "字典", businessType = BusinessType.INSERT)
	public R setDataMatch(@RequestBody BaseDictionaryMatch baseDictionaryMatch) {
		Boolean b = baseDictionaryMatchService.setDataMatch(baseDictionaryMatch);
		return R.toResult(b);
	}




	/**
	 * 获取数据字典分类
	 * @param
	 * @return
	 */
	@GetMapping("/getDataDictionary")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "获取数据字典分类", notes = "获取数据字典分类")
	public R getDataDictionary() {
		List<BaseDictionaryClass> list = baseDictionaryClassService.list(new QueryWrapper<BaseDictionaryClass>().eq("is_matchable", 1).orderByAsc("class_code"));
		return R.ok(list);
	}


	/**
	 * 搜索数据字典
	 * @param pageParamSimple
	 * @param baseDictionaryParam
	 * @return
	 */
	@GetMapping("/searchDataDictionary")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "搜索数据字典", notes = "搜索数据字典")
	public R searchDataDictionary(PageParamSimple pageParamSimple, BaseDictionaryParam baseDictionaryParam) {
		PageParam<BaseDictionary> page = mapperFacade.map(pageParamSimple, PageParam.class);
		//添加条件
		QueryWrapper<BaseDictionary> queryWrapper = new QueryWrapper<>();
		if(StringUtils.isNotEmpty(baseDictionaryParam.getClassCode())) {
			queryWrapper.eq("DICTIONARY_TYPE", baseDictionaryParam.getClassCode());
		}
		if(StringUtils.isNotEmpty(baseDictionaryParam.getKey())){
			queryWrapper.like("DICTIONARY_NAME", baseDictionaryParam.getKey());
		}
		queryWrapper.orderByAsc("DICTIONARY_CODE");
		PageParam<BaseDictionary> list = baseDictionaryService.page(page, queryWrapper);
		return R.ok(list);
	}


	/**
	 * 获取此条数据匹配情况
	 * @param pageParamSimple
	 * @param baseDictionaryParam
	 * @return
	 */
	@GetMapping("/getDataMatch")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "获取此条数据匹配情况", notes = "获取此条数据匹配情况")
	public R getDataMatch(PageParamSimple pageParamSimple, BaseDictionaryParam baseDictionaryParam) {
		PageParam<BaseDictionaryMatch> page = mapperFacade.map(pageParamSimple, PageParam.class);
		PageParam<BaseDictionaryMatch> list = baseDictionaryMatchService.getDataMatch(page,baseDictionaryParam);
		return R.ok(list);
	}









}

