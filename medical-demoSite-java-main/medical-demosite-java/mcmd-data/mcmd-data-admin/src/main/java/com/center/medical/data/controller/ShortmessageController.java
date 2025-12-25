package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.ShortMessageType;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Shortmessage;
import com.center.medical.data.service.ShortmessageService;
import com.center.medical.service.ShortMessageTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短信模板维护(Shortmessage)表控制层
 *
 * @author ay
 * @since 2022-11-18 08:53:57
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "短信模板维护")
@RequestMapping("shortmessage")
public class ShortmessageController extends BaseController {
	/**
	* 服务对象
	*/
	private final ShortmessageService shortmessageService;
    private final MapperFacade mapperFacade;
    private final ShortMessageTypeService shortMessageTypeService;

	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param shortmessage 查询实体
	* @return 所有数据
	*/
	@GetMapping("/getListData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询JC短信信息表")
	public R<IPage<Shortmessage>> getListData(PageParamSimple pageParamSimple, Shortmessage shortmessage) {
		PageParam<Shortmessage> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.shortmessageService.getList(page, shortmessage));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查JC短信信息表详情")
	public R<Shortmessage> selectOne(@PathVariable String id) {
		return R.ok(this.shortmessageService.getInfoById(id));
	}

	/**
	* 新增或修改数据
	*
	* @param shortmessage 实体对象
	* @return 新增结果
	*/
	@PostMapping("/saveOrUpdate")
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "新增或修改数据", notes = "新增或修改JC短信信息表")
	@Log(title = "JC短信信息表", businessType = BusinessType.INSERT)
	public R saveOrUpdate(@RequestBody Shortmessage shortmessage) {
		Boolean s = shortmessageService.saveOrUpdateShortmessage(shortmessage);
		return R.ok(s);
	}


	/**
	* 删除数据
	*
	* @param ids 主键结合
	* @return 删除结果
	*/
	@DeleteMapping("/remove/{ids}")
	//@PreAuthorize("@ss.hasPermi('::remove')")
	@ApiOperation(value = "删除", notes = "删除JC短信信息表")
	@Log(title = "JC短信信息表", businessType = BusinessType.DELETE)
	public R remove(@PathVariable String ids) {
		String s = shortmessageService.removeShortmessage(ids);
		return R.ok(s);
	}


	/**
	 * 获取短信分类名称
	 * @param pageParamSimple
	 * @return
	 */
	@GetMapping("/getMessageTypeData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "获取短信分类名称", notes = "获取短信分类名称")
	public R getMessageTypeData(PageParamSimple pageParamSimple) {
		PageParam<ShortMessageType> page = mapperFacade.map(pageParamSimple, PageParam.class);
		PageParam<ShortMessageType> messageTypePageParam = shortMessageTypeService.page(page, null);
		return R.ok(messageTypePageParam);
	}

	/**
	 * 获取短信分类表里的短消息类型
	 * @param pageParamSimple
	 * @param key
	 * @return
	 */
	@GetMapping("/findAllTypeName")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页获取短信分类名称", notes = "分页获取短信分类名称")
	public R findAllTypeName(PageParamSimple pageParamSimple,String key) {
		PageParam<ShortMessageType> page = mapperFacade.map(pageParamSimple, PageParam.class);
		QueryWrapper<ShortMessageType> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(key)) {
			queryWrapper.like("inputCode", key.toUpperCase());
		}
		queryWrapper.eq("is_delete", 0);
		PageParam<ShortMessageType> messageTypePageParam = shortMessageTypeService.page(page, queryWrapper);
		return R.ok(messageTypePageParam);
	}


	/**
	 * 根据筛选message_type，is_delete，inputcode筛选
	 * @param key
	 * @param messageType
	 * @return
	 */
	@GetMapping("/getComboData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "不分页获取短信分类", notes = "不分页获取短信分类名称")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "messageType", value = "短信类型"),
			@ApiImplicitParam(name = "key", value = "输入码")
	})
	public R getComboData(String key,String messageType) {
		QueryWrapper<Shortmessage> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("is_delete",0);
		if(StringUtils.isNotEmpty(messageType)){
			queryWrapper.in("message_type",new String[]{messageType});
		}
		if(StringUtils.isNotEmpty(key)){
			queryWrapper.like("inputcode", key.trim().toUpperCase());
		}
		List<Shortmessage> list = shortmessageService.list(queryWrapper);
		return R.ok(list);
	}

}

