package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.BasMerge;
import com.center.medical.data.bean.model.BasMergeConclusion;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.data.bean.vo.BasMergeVo;
import com.center.medical.data.service.BasMergeConclusionService;
import com.center.medical.data.service.BasMergeService;
import com.center.medical.data.service.BasconclusionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 合并结论词维护(BasMerge)表控制层
 *
 * @author ay
 * @since 2022-11-21 08:44:04
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "合并结论词维护")
@RequestMapping("basMerge")
public class BasMergeController extends BaseController {
	/**
	* 服务对象
	*/
	private final BasMergeService basMergeService;
    private final MapperFacade mapperFacade;
    private final BasMergeConclusionService basMergeConclusionService;
    private final BasconclusionService basconclusionService;

	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param basMerge 查询实体
	* @return 所有数据
	*/
	@GetMapping("/list")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询合并结伦词")
	public R<IPage<BasMerge>> selectAll(PageParamSimple pageParamSimple, BasMerge basMerge) {
		PageParam<BasMerge> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.basMergeService.getList(page, basMerge));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查合并结伦词详情")
	public R<BasMerge> selectOne(@PathVariable String id) {
		return R.ok(this.basMergeService.getInfoById(id));
	}

	/**
	* 新增或更新数据
	*
	* @param basMergeVo 实体对象
	* @return 新增结果
	*/
	@PostMapping
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "新增或更新", notes = "新增或更新合并结伦词")
	@Log(title = "合并结伦词", businessType = BusinessType.INSERT)
	public R insert(@RequestBody BasMergeVo basMergeVo) {
		return R.toResult(this.basMergeService.saOrUp(basMergeVo));
	}


	/**
	* 删除数据
	*
	* @param ids 主键结合
	* @return 删除结果
	*/
	@DeleteMapping("/{ids}")
	//@PreAuthorize("@ss.hasPermi('::remove')")
	@ApiOperation(value = "删除", notes = "删除合并结伦词")
	@Log(title = "合并结伦词", businessType = BusinessType.DELETE)
	public R delete(@PathVariable List<String> ids) {
		if (CollectionUtils.isEmpty(ids)){
			throw new ServiceException("请选中一条或多条记录！");
		}
		//设置为已删除
		BasMerge basMerge = new BasMerge();
		basMerge.setModifydate(new Date());
		basMerge.setIsDelete(1);
		return R.toResult(this.basMergeService.update(basMerge, new UpdateWrapper<BasMerge>().in("id", ids)));
	}


	/**
	 * 分页获取初始化的数据（根据中间表的结论词id获取数据）
	 * @param pageParamSimple
	 * @param basMerge
	 * @return
	 */
	@GetMapping("/getInfoListData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页获取初始化的数据", notes = "根据中间表的结论词id获取数据")
	public R<IPage<Basconclusion>> getInfoListData(PageParamSimple pageParamSimple, BasMerge basMerge) {
		PageParam<Basconclusion> page = mapperFacade.map(pageParamSimple, PageParam.class);
		//获取合并结伦词中间表数据
		List<BasMergeConclusion> bmcs = basMergeConclusionService.list(new QueryWrapper<BasMergeConclusion>().eq("merge_id",basMerge.getId()));
		List<String> conclusionIdList = new ArrayList<>();
		//把id放进去
		if(CollectionUtils.isNotEmpty(bmcs)){
			 conclusionIdList = bmcs.stream().map(b -> b.getConclusionId()).collect(Collectors.toList());
		}
		//如果id的集合为空就返回空
		if (CollectionUtils.isEmpty(conclusionIdList)){
			return R.ok();
		}else{
			PageParam<Basconclusion> page1 = basconclusionService.page(page, new QueryWrapper<Basconclusion>().orderByAsc("name").eq("is_delete", 0).in("id", conclusionIdList));
			return R.ok(page1);
		}

	}


	/**
	 * 不分页获取初始化的数据（根据中间表的结论词id获取数据）
	 * @return
	 */
	@GetMapping("/getInfoList")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "不分页获取初始化的数据", notes = "不分页根据中间表的结论词id获取数据")
	public R getInfoList(BasMerge basMerge) {
		List<BasMergeConclusion> bmcs = basMergeConclusionService.list(new QueryWrapper<BasMergeConclusion>().eq("merge_id",basMerge.getId()));
		List<String> conclusionIdList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(bmcs)){
			conclusionIdList = bmcs.stream().map(b -> b.getConclusionId()).collect(Collectors.toList());
		}
		if (CollectionUtils.isEmpty(conclusionIdList)){
			return R.ok();
		}else{
			List<Basconclusion> list = basconclusionService.list(new QueryWrapper<Basconclusion>()
					.in("id", conclusionIdList)
					.eq("is_delete", 0));
			return R.ok(list);
		}

	}





}

