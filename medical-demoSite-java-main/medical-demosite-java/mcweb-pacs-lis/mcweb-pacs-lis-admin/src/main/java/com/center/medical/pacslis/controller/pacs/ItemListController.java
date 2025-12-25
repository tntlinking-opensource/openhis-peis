package com.center.medical.pacslis.controller.pacs;

import com.center.medical.data.service.ItemsService;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.pacslis.bean.model.PacsPeispatient;
import com.center.medical.pacslis.bean.param.ItemListParam;
import com.center.medical.pacslis.bean.vo.ItemListVo;
import com.center.medical.pacslis.service.PacsPeispatientService;
import com.center.medical.sellcrm.bean.vo.GetOrgsVo;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.service.DictmarriageService;
import com.center.medical.pacslis.service.PacsPeispatientfeeitemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * PACS登记信息查询
 *
 * @author ay
 * @since 2022-12-29 11:39:01
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "PACS-PACS登记信息查询")
@RequestMapping("/pacs/itemList")
public class ItemListController extends BaseController {
	/**
	* 服务对象
	*/
	private final PacsPeispatientService pacsPeispatientService;
    private final MapperFacade mapperFacade;
    private final SellcustomerService sellcustomerService;
    private final DictmarriageService dictmarriageService;
    private final PacsPeispatientfeeitemService peispatientfeeitemService;
    private final ItemsService itemsService;


	/**
	 * 【PACS-PACS登记信息查询】功能接口总结
	 */
	@GetMapping("/getInterfaceList")
	@ApiOperation(value = "接口说明", notes = "获取【PACS-PACS登记信息查询】这个块业务所有接口及接口说明")
	public R<FunctionVo> getInterfaceList() {
		List<InterfaceVo> interfaceVos = Arrays.asList(
				new InterfaceVo("分页查询", "GET", "/pacs/itemList/page", "11.pacs/lis->PACS-PACS登记信息查询->分页查询", null),
				new InterfaceVo("团体名称下拉", "GET", "/pacs/itemList/getOrgs", "11.pacs/lis->PACS-PACS登记信息查询->团体名称下拉", null),
				new InterfaceVo("获取会员类型", "GET", "/total/RecordManage/getPatientTypeData", "11.pacs/lis->PACS-PACS登记信息查询->获取会员类型", null)
		);
		return R.ok(new FunctionVo("11.pacs/lis", "PACS-PACS登记信息查询", interfaceVos, "11.pacs/lis"));
	}


	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param param 查询条件
	* @return 所有数据
	*/
	@GetMapping("/page")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询PACS-体检者表")
	public R<ItemListVo> getPage(PageParamSimple pageParamSimple, ItemListParam param) {
		PageParam<PacsPeispatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.pacsPeispatientService.getPage(page, param));
	}


	/**
	 * 团体名称下拉
	 * @param key
	 * @return
	 */
	@GetMapping("/getOrgs")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "团体名称下拉", notes = "团体名称下拉")
	@ApiImplicitParam(name = "key", value = "团体名称或输入码")
	public R getOrgs(String key) {
		if (ObjectUtils.isNotEmpty(key)){
			key = key.trim();
		}
		List<GetOrgsVo> list = sellcustomerService.getOrgs(key);
		return R.ok(list);
	}




}

