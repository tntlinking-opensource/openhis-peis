package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.SampleSaOrUpParam;
import com.center.medical.abteilung.bean.vo.SampleConnectVo;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.abteilung.service.SampleConnectService;
import com.center.medical.service.PeispatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 检验样本-样本交接(SampleConnect)表控制层
 *
 * @author ay
 * @since 2023-01-09 15:22:05
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "检验样本-样本交接")
@RequestMapping("/handle/sampleConnect")
public class SampleConnectController extends BaseController {
	/**
	* 服务对象
	*/
	private final SampleConnectService sampleConnectService;
    private final MapperFacade mapperFacade;
    private final PeispatientService peispatientService;

	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param param 查询条件
	* @return 所有数据
	*/
	@GetMapping("/page")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询KS样本交接")
	public R<IPage<SampleConnectVo>> getPage(PageParamSimple pageParamSimple, BaseParam param) {
		PageParam<SampleConnectVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.sampleConnectService.getList(page, param));
	}


	/**
	 * 样本交接展现数据
	 * @param pageParamSimple
	 * @param patientCode
	 * @return
	 */
	@GetMapping("/getItemData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "样本交接展现数据", notes = "样本交接展现数据")
	@ApiImplicitParam(name = "patientCode", value = "体检号")
	public R getItemData(PageParamSimple pageParamSimple,String patientCode) {
		PageParam<SampleConnectVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		HashMap<String, Object> map = sampleConnectService.getItemData(page,patientCode);
		return R.ok(map);
	}


	/**
	 * 保存
	 * @return
	 */
	@PostMapping("/saveOrUpdate")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "保存", notes = "保存")
	public R saveOrUpdate(@RequestBody SampleSaOrUpParam param) {
		Boolean b = sampleConnectService.saOrUp(param);
		return R.toResult(b);
	}

}

