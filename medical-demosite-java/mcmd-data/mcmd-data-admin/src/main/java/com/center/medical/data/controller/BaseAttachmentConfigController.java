package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.BaseAttachmentConfig;
import com.center.medical.data.service.BaseAttachmentConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 报告存储配置(BaseAttachmentConfig)表控制层
 *
 * @author ay
 * @since 2022-11-18 16:34:22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告存储配置")
@RequestMapping("baseAttachmentConfig")
public class BaseAttachmentConfigController extends BaseController {
	/**
	* 服务对象
	*/
	private final BaseAttachmentConfigService baseAttachmentConfigService;
    private final MapperFacade mapperFacade;

	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param baseAttachmentConfig 查询实体
	* @return 所有数据
	*/
	@GetMapping("/list")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询基础附件配置")
	public R<IPage<BaseAttachmentConfig>> selectAll(PageParamSimple pageParamSimple, BaseAttachmentConfig baseAttachmentConfig) {
		PageParam<BaseAttachmentConfig> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.baseAttachmentConfigService.getList(page, baseAttachmentConfig));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查基础附件配置详情")
	public R<BaseAttachmentConfig> selectOne(@PathVariable String id) {
		return R.ok(this.baseAttachmentConfigService.getInfoById(id));
	}

	/**
	* 新增或修改数据
	*
	* @param baseAttachmentConfig 实体对象
	* @return 新增结果
	*/
	@PostMapping("/saveOrUpdate")
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "新增或修改数据", notes = "新增或修改基础附件配置")
	@Log(title = "基础附件配置", businessType = BusinessType.INSERT)
	public R saveOrUpdate(@RequestBody BaseAttachmentConfig baseAttachmentConfig) {
		String s = baseAttachmentConfigService.saveOrUpdateBase(baseAttachmentConfig);
		return R.ok(s);
	}


	/**
	* 删除数据
	*
	* @param ids 主键结合
	* @return 删除结果
	*/
	@DeleteMapping("/delete/{ids}")
	//@PreAuthorize("@ss.hasPermi('::remove')")
	@ApiOperation(value = "删除", notes = "删除基础附件配置")
	@Log(title = "基础附件配置", businessType = BusinessType.DELETE)
	public R delete(@PathVariable String ids) {
		String s = baseAttachmentConfigService.deleteBase(ids);
		return R.ok(s);
	}
}

