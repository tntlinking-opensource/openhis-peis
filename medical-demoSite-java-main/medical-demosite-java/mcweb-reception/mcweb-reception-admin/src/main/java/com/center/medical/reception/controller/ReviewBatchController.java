package com.center.medical.reception.controller;

import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.reception.bean.param.RBatchParam;
import com.center.medical.reception.bean.param.ReviewBatchParam;
import com.center.medical.reception.bean.vo.RBListDataVo;
import com.center.medical.reception.bean.vo.ReviewBatchVo;
import com.center.medical.reception.service.ReviewBatchService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职业复查(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-02-02 15:30:08
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业复查")
@RequestMapping("/reception/reviewBatch")
public class ReviewBatchController extends BaseController {
	/**
	* 服务对象
	*/
	private final ReviewBatchService reviewBatchService;
    private final MapperFacade mapperFacade;

	/**
	* 分页查询所有数据
	*
	* @param param 查询条件
	* @return 所有数据
	*/
	@GetMapping("/getItemsListData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "获取收费项目数据", notes = "获取收费项目数据")
	public R<List<ReviewBatchVo>> getItemsListData(ReviewBatchParam param) {
		return R.ok(this.reviewBatchService.getItemsListData(param));
	}



	/**
	* 职业复查保存
	*
	* @param RBatchParam 实体对象
	* @return 新增结果
	*/
	@PostMapping("/reviewBatch")
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "职业复查保存", notes = "职业复查保存")
	@Log(title = "QT体检者表", businessType = BusinessType.INSERT)
	@ApiOperationSupport(ignoreParameters = {"peispatient.id"})
	public R reviewBatch(@RequestBody RBatchParam RBatchParam) {
		return R.toResult(this.reviewBatchService.reviewBatch(RBatchParam));
	}


	/**
	 * 获取数据
	 * @param param
	 * @return
	 */
	@GetMapping("/getListData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "获取数据", notes = "获取数据")
	public R<List<RBListDataVo>> getListData(ReviewBatchParam param) {
		return R.ok(this.reviewBatchService.getListData(param));
	}



}

