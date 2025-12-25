package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.finance.bean.model.FinancialReporting;
import com.center.medical.finance.bean.param.FinancialReportingParam;
import com.center.medical.finance.service.FinancialReportingService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 财务提报(FinancialReporting)表控制层
 *
 * @author ay
 * @since 2023-06-27 16:01:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "财务提报")
@RequestMapping("finance/financialReporting")
public class FinancialReportingController extends BaseController {
	/**
	* 服务对象
	*/
	private final FinancialReportingService financialReportingService;
    private final MapperFacade mapperFacade;

	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param param 查询条件
	* @return 所有数据
	*/
	@GetMapping("/page")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询财务提报")
	public R<IPage<FinancialReporting>> getPage(PageParamSimple pageParamSimple, FinancialReportingParam param) {
		PageParam<FinancialReporting> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.financialReportingService.getList(page, param));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查财务提报详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
	public R<FinancialReporting> selectOne(@PathVariable String id) {
		return R.ok(this.financialReportingService.getInfoById(id));
	}

	/**
	* 新增数据
	*
	* @param financialReporting 实体对象
	* @return 新增结果
	*/
	@PostMapping
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "添加", notes = "添加财务提报")
	@Log(title = "财务提报", businessType = BusinessType.INSERT)
	@ApiOperationSupport(ignoreParameters = {"financialReporting.id"})
	public R insert(@RequestBody FinancialReporting financialReporting) {
		//查询有没有对应月份的数据
		long count = financialReportingService.count(new LambdaQueryWrapper<FinancialReporting>()
				.eq(FinancialReporting::getChoosedate, financialReporting.getChoosedate())
				.eq(FinancialReporting::getCid, financialReporting.getCid())
				.eq(FinancialReporting::getIsDelete, 0)
		);
		if (count>1){
			throw new ServiceException("该月份已经保存过数据了!");
		}
		financialReporting.setCreateUserid(SecurityUtils.getUserNo());
		financialReporting.setCreateUsername(SecurityUtils.getUsername());
		return R.toResult(this.financialReportingService.save(financialReporting));
	}

	/**
	* 修改数据
	*
	* @param financialReporting 实体对象
	* @return 修改结果
	*/
	@PutMapping
	//@PreAuthorize("@ss.hasPermi('::edit')")
	@ApiOperation(value = "更新", notes = "更新财务提报")
	@Log(title = "财务提报", businessType = BusinessType.UPDATE)
	public R update(@RequestBody FinancialReporting financialReporting) {
		financialReporting.setModifyUserid(SecurityUtils.getUserNo());
		financialReporting.setModifyUsername(SecurityUtils.getUsername());
		return R.toResult(this.financialReportingService.updateById(financialReporting));
	}

	/**
	* 删除数据
	*
	* @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
	* @return 删除结果
	*/
	@DeleteMapping("/delete")
	//@PreAuthorize("@ss.hasPermi('::remove')")
	@ApiOperation(value = "删除", notes = "删除财务提报")
	@Log(title = "财务提报", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
	public R delete(@RequestParam List<String> ids) {
		FinancialReporting financialReporting = new FinancialReporting();
		financialReporting.setIsDelete(1);
		financialReporting.setModifyUserid(SecurityUtils.getUserNo());
		financialReporting.setModifyUsername(SecurityUtils.getUsername());
		boolean b = financialReportingService.update(financialReporting, new UpdateWrapper<FinancialReporting>().in("id", ids));
		return R.toResult(b);
	}


	/**
	 * 获取上一次填的数据
	 * @param cid
	 * @return
	 */
	@GetMapping("/lastData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "获取上一次填的数据", notes = "获取上一次填的数据")
	public R<FinancialReporting> lastData(String cid) {
		FinancialReporting financialReporting = financialReportingService.getOne(
				new LambdaQueryWrapper<FinancialReporting>()
				.eq(FinancialReporting::getCid, cid)
				.orderByDesc(FinancialReporting::getChoosedate)
				.last("limit 1")
		);
		return R.ok(financialReporting);
	}
}

