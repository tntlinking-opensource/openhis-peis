package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.AjaxResult;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.file.FileUploadUtils;
import com.center.medical.common.utils.file.FileUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.data.service.BasconclusionService;
import com.center.medical.framework.config.ServerConfig;
import com.center.medical.system.bean.vo.DeptSimpleVo;
import com.center.medical.system.bean.param.SysDeptParam;
import com.center.medical.system.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 总检结论词维护(Basconclusion)表控制层
 *
 * @author ay
 * @since 2022-11-19 14:24:28
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "总检结论词维护")
@RequestMapping("basconclusion")
public class BasconclusionController extends BaseController {
	/**
	* 服务对象
	*/
	private final BasconclusionService basconclusionService;
    private final MapperFacade mapperFacade;
    private final ISysDeptService sysDeptService;
	private final ServerConfig serverConfig;


	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param basconclusion 查询实体
	* @return 所有数据
	*/
	@GetMapping("/list")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询总检结论词")
	public R<IPage<Basconclusion>> selectAll(PageParamSimple pageParamSimple, Basconclusion basconclusion) {
		PageParam<Basconclusion> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.basconclusionService.getList(page, basconclusion));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查总检结论词详情")
	public R<Basconclusion> selectOne(@PathVariable String id) {
		return R.ok(this.basconclusionService.getInfoById(id));
	}

	/**
	* 新增或更新
	*
	* @param basconclusion 实体对象
	* @return 新增结果
	*/
	@PostMapping
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "新增或更新", notes = "新增或更新总检结论词")
	@Log(title = "总检结论词", businessType = BusinessType.INSERT)
	public R insert(@RequestBody Basconclusion basconclusion) {
		return R.toResult(this.basconclusionService.saOrUp(basconclusion));
	}



	/**
	* 删除数据
	*
	* @param ids 主键结合
	* @return 删除结果
	*/
	@DeleteMapping("/{ids}")
	//@PreAuthorize("@ss.hasPermi('::remove')")
	@ApiOperation(value = "删除", notes = "删除总检结论词")
	@Log(title = "总检结论词", businessType = BusinessType.DELETE)
	public R delete(@PathVariable List<String> ids) {
		return R.toResult(this.basconclusionService.removebasconclusion(ids));
	}


	/**
	 * 根据拼音获取结论词
	 * @param pageParamSimple
	 * @param key
	 * @return
	 */
	@GetMapping("/getConclusion")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "根据拼音获取结论词", notes = "根据拼音获取结论词")
	public R<IPage<Basconclusion>> getConclusion(PageParamSimple pageParamSimple, String key) {
		PageParam<Basconclusion> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.basconclusionService.getConclusion(page, key));
	}


	/**
	 * 检查项目查询 下拉数据
	 * @param pageParamSimple
	 * @param key
	 * @param id
	 * @return
	 */
	@GetMapping("/getInspectConclusion")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "检查项目查询 下拉数据", notes = "检查项目查询 下拉数据")
	public R<IPage<Basconclusion>> getInspectConclusion(PageParamSimple pageParamSimple, String key,String id) {
		PageParam<Basconclusion> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.basconclusionService.getInspectConclusion(page, key ,id));
	}


	/**
	 * 分頁获取所有功能部门
	 * @param pageParamSimple
	 * @param param
	 * @return
	 */
	@GetMapping("/findAllDepartment")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分頁获取所有功能部门（科室）", notes = "分頁获取所有功能部门（科室）")
	public R<IPage<DeptSimpleVo>> findAllDepartment(PageParamSimple pageParamSimple, SysDeptParam param) {
		PageParam<DeptSimpleVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(sysDeptService.findAllDepartment(page, param));
	}


	/**
	 * 审核
	 * @param ids
	 * @return
	 */
	@PutMapping("/audit/{ids}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "审核", notes = "审核")
	public R<Basconclusion> audit(@PathVariable List<String> ids) {
		boolean b = basconclusionService.audit(ids);
		return R.toResult(b);
	}



	/**
	 * 通用上传请求（单个）
	 */
	@PostMapping("/upload")
	@ApiOperation(value = "上传", notes = "上传")
	public AjaxResult uploadFile(MultipartFile file) throws Exception {
		try {
			// TODO: 2022/11/20 文件上传路径 ？
			// 上传文件路径
//			String filePath = ZhongkangConfig.getUploadPath();
			String url = FileTypePath.NET_URL+"inter/conclusion_ol!receive.action";
			// 上传并返回新文件名称
			String fileName = FileUploadUtils.upload(url, file);
			AjaxResult ajax = AjaxResult.success();
			ajax.put("url", url);
			ajax.put("fileName", fileName);
			ajax.put("newFileName", FileUtils.getName(fileName));
			ajax.put("originalFilename", file.getOriginalFilename());
			return ajax;
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}





}

