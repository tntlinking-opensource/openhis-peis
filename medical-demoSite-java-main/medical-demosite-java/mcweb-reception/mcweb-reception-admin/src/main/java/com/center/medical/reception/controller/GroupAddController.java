package com.center.medical.reception.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.enums.MarriageType;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.service.ItemsService;
import com.center.medical.reception.bean.param.*;
import com.center.medical.reception.bean.vo.DiscountVo;
import com.center.medical.reception.bean.vo.ItemListVo;
import com.center.medical.reception.bean.vo.ListDataVo;
import com.center.medical.reception.service.ItemListService;
import com.center.medical.reception.service.OrderService;
import com.center.medical.system.bean.param.SysDeptParam;
import com.center.medical.system.bean.vo.DeptSimpleVo;
import com.center.medical.system.service.ISysDeptService;
import com.center.medical.system.service.ISysUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 团检加/弃项(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-02-03 18:12:49
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "团检加弃项")
@RequestMapping("/reception/groupAdd")
public class GroupAddController extends BaseController {
	/**
	* 服务对象
	*/
	private final ItemListService itemListService;
	private final MapperFacade mapperFacade;
	private final ISysDeptService sysDeptService;
	private final ItemsService itemsService;
	private final ISysUserService sysUserService;
	private final OrderService orderService;

	/**
	 * 分页查询所有数据
	 *
	 * @param pageParamSimple 分页参数
	 * @param param 查询条件
	 * @return 所有数据
	 */
	@GetMapping("/page")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
	public R<IPage<ItemListVo>> getPage(PageParamSimple pageParamSimple, ItemListParam param) {
		PageParam<ItemListVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.itemListService.getList(page, param));
	}



	/**
	 * 新增数据
	 *
	 * @param param 实体对象
	 * @return 新增结果
	 */
	@PostMapping("/saveOrUpdateItems2")
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "右侧保存", notes = "右侧保存")
	@Log(title = "QT体检者表", businessType = BusinessType.INSERT)
	@ApiOperationSupport(ignoreParameters = {"peispatient.id"})
	public R saveOrUpdateItems2(@RequestBody GCSaOrUpParam param) {
		return R.toResult(this.itemListService.saOrUp(param));
	}





	/**
	 * 分頁获取所有功能部门
	 * @param pageParamSimple
	 * @param param
	 * @return
	 */
	@GetMapping("/getKsData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分頁获取所有功能部门（科室）", notes = "分頁获取所有功能部门（科室）")
	public R<IPage<DeptSimpleVo>> getKsData(PageParamSimple pageParamSimple, SysDeptParam param) {
		PageParam<DeptSimpleVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(sysDeptService.findAllDepartment(page, param));
	}


	/**
	 * 收费项目下拉
	 * @param pageParamSimple
	 * @param param
	 * @return
	 */
	@GetMapping("/getListData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "收费项目下拉", notes = "右侧收费项目下拉")
	public R<IPage<ListDataVo>> getListData(PageParamSimple pageParamSimple, ListDataParam param) {
		PageParam<ListDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(itemListService.getListData(page, param));
	}


	/**
	 * 团检退项匹配最小套餐
	 * @param param
	 * @return
	 */
	@PostMapping("/compareMinTcContent")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "团检退项匹配最小套餐", notes = "团检退项匹配最小套餐")
	public R compareMinTcContent(@RequestBody CompareMinTcParam param) {
		Boolean b = itemListService.compareMinTcContent(param.getGriddata(),param.getIds());
		return R.toResult(b);
	}


	/**
	 * 团体批量折扣或优惠价页面展现
	 * @param id
	 * @return
	 */
	@GetMapping("/discount")
	@ApiOperation(value = "团体批量折扣或优惠价页面展现", notes = "团体批量折扣或优惠价页面展现")
	@ApiImplicitParam(name = "id", value = "收费项目id")
	public R<DiscountVo> discount(String id) {
		DiscountVo vo = new DiscountVo();
		vo.setId(id);
		//收费项目
		Items items = itemsService.getInfoById(id);
		if (ObjectUtils.isNotEmpty(items)) {
			vo.setPrice(items.getUnitprice());
			vo.setFactprice(items.getSuiteprice());
			vo.setZk(items.getZk());
			vo.setName(items.getExamfeeitemName());
		}
		// 获取折扣
		SysUser user = sysUserService.selectUserByUserNo(SecurityUtils.getUserNo());
		// 销售折扣
		vo.setMaxzk(user.getSdiscount());
		return R.ok(vo);
	}


	/**
	 * 禁检、反禁检操作
	 * @param param
	 * @return
	 */
	@PostMapping("/savePausedStatus")
	@ApiOperation(value = "禁检、反禁检操作", notes = "禁检、反禁检操作")
	@Log(title = "禁检、反禁检操作", businessType = BusinessType.INSERT)
	public R savePausedStatus(@RequestBody PausedStatusParam param) {
		return R.toResult(itemListService.savePausedStatus(param.getPaused(),param.getIds()));
	}

	/**
	 * 获取收费项目数据
	 * @param patientCode
	 * @param type
	 * @return
	 */
	@GetMapping("/getExamfeeByPatientCode")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "获取右侧收费项目", notes = "获取右侧收费项目")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "patientCode", value = "体检号"),
			@ApiImplicitParam(name = "type", value = "类型，0全部显示，1显示除去退项的，2显示退项的")
	})
	public R<List<Map<String, Object>>> getExamfeeByPatientCode(String patientCode,String type) {
		return R.ok(this.itemListService.getExamfeeByPatientCode(patientCode,type));
	}


	/**
	 * 婚姻下拉
	 * @return
	 */
	@GetMapping("/getMarriageData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "婚姻下拉", notes = "婚姻下拉")
	public R getMarriageData() {
		return R.ok(MarriageType.toMap());
	}


	/**
	 * 团检套餐加项
	 * @param param
	 * @return
	 */
	@PostMapping("/addItem")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@Log(title = "团检加妻项-团检套餐加项", businessType = BusinessType.INSERT)
	@ApiOperation(value = "团检套餐加项", notes = "团检套餐加项")
	public R addItem(OrderAddItemParam param) {
		return R.ok(orderService.addItem(param));
	}


}

