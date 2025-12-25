package com.center.medical.sellcrm.controller.crm;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.service.SectionResultTwoService;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.service.ItemsService;
import com.center.medical.reception.bean.model.ReviewProject;
import com.center.medical.reception.bean.vo.ReviewItemVo;
import com.center.medical.reception.bean.vo.ReviewPrintVo;
import com.center.medical.reception.dao.ReviewMapper;
import com.center.medical.reception.dao.ReviewProjectMapper;
import com.center.medical.reception.service.ReviewProjectService;
import com.center.medical.sellcrm.bean.param.RecheckePrintParam;
import com.center.medical.sellcrm.bean.vo.RecheckePrintVo;
import com.center.medical.sellcrm.service.RecheckePrintService;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysDeptMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 复查打印(Review)表控制层
 *
 * @author ay
 * @since 2023-02-08 11:58:35
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "复查打印")
@RequestMapping("crm/recheckePrint")
public class RecheckePrintController extends BaseController {
	/**
	* 服务对象
	*/
	private final RecheckePrintService recheckePrintService;
    private final MapperFacade mapperFacade;
    private final ReviewProjectService reviewProjectService;
    private final SectionResultTwoService sectionResultTwoService;
    private final ReviewMapper reviewMapper;
    private final ReviewProjectMapper reviewProjectMapper;
    private final SysDeptMapper sysDeptMapper;
    private final SysBranchMapper sysBranchMapper;
    private final ItemsService itemsService;

	/**
	 * 【复查单打印】功能接口总结
	 */
	@GetMapping("/getInterfaceList")
	@ApiOperation(value = "接口说明", notes = "获取【复查单打印】这个块业务所有接口及接口说明")
	public R<FunctionVo> getInterfaceList() {
		List<InterfaceVo> interfaceVos = Arrays.asList(
				new InterfaceVo("分页查询", "GET", "/crm/recheckePrint/page", "06.客户销售系统->销售管理-复查单打印->分页查询", null),
				new InterfaceVo("打印", "GET", "/crm/recheckePrint/printData", "06.客户销售系统->销售管理-复查单打印->打印", null),
				new InterfaceVo("团体名称下拉", "GET", "/abteilung/sectionResultPlan/getOrgs", "06.客户销售系统->销售管理-复查单打印->团体名称下拉", null)
		);
		return R.ok(new FunctionVo("06.客户销售系统", "销售管理-复查单打印", interfaceVos, "06.客户销售系统"));
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
	@ApiOperation(value = "分页查询", notes = "分页查询ZJ复查表")
	public R<IPage<RecheckePrintVo>> getPage(PageParamSimple pageParamSimple, RecheckePrintParam param) {
		PageParam<RecheckePrintVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		//取出数据
		IPage<RecheckePrintVo> iPage = recheckePrintService.getList(page, param);
		List<RecheckePrintVo> list = iPage.getRecords();
		for (RecheckePrintVo m : list) {
			//复查项目
			List<ReviewProject> rw = reviewProjectService.list(new QueryWrapper<ReviewProject>()
					.eq("review_id", m.getId()));
			if (ObjectUtils.isNotEmpty(rw)) {
				String s = "";
				for (int i = 0; i < rw.size(); i++) {
					//收费项目名称
					s += "" + rw.get(i).getItemsName();
				}
				m.setItemsName(s);
			}
		}
		iPage.setRecords(list);
		return R.ok(iPage);
	}


	/**
	 * 打印
	 * @param ids
	 * @return
	 */
	@GetMapping("/printData")
	@ApiOperation(value = "打印", notes = "打印")
	@ApiImplicitParam(name = "ids", value = "ids")
	public R printData(@RequestParam List<String> ids) {
		if (CollectionUtil.isEmpty(ids)) {
			throw new ServiceException("请选择要打印的数据！");
		}
		List<ReviewPrintVo> reviews = reviewMapper.getListByIds(ids);
		for (ReviewPrintVo review : reviews) {
			List<ReviewProject> rw = reviewProjectMapper.selectList(new LambdaQueryWrapper<ReviewProject>()
					.eq(ReviewProject::getReviewId, review.getId()));
			List<ReviewItemVo> itemVoList = new ArrayList();
			if (CollectionUtil.isNotEmpty(rw)) {
				for (ReviewProject project : rw) {
					Items items = itemsService.getInfoById(project.getItemsId());
					if (ObjectUtils.isNotEmpty(items)) {
						SysDept de = sysDeptMapper.getByDeptNo(items.getIdDepart());
						if (Objects.nonNull(de)) {
							ReviewItemVo itemVo = new ReviewItemVo();
							itemVo.setXm(project.getItemsName());
							itemVo.setKs(de.getDeptName());
							itemVoList.add(itemVo);
						}
					}
				}
			}
			review.setItems(itemVoList);
			// 当前分中心
			SysBranch branch = sysBranchMapper.getByBranchId(SecurityUtils.getCId());
			review.setFzxAddress(branch.getAddress());
			review.setSuccess(true);
		}

		List<String> resultIds = reviews.stream().map(ReviewPrintVo::getId).collect(Collectors.toList());
		int i = 0;
		for (String id : ids) {
			if (!CollectionUtil.contains(resultIds, id)) {
				ReviewPrintVo reviewPrintVo = new ReviewPrintVo();
				reviewPrintVo.setSuccess(false);
				reviewPrintVo.setMsg("复查单打印失败：第" + (i + 1) + "个体检者!");
				i++;
			}
		}

		return R.ok(reviews);
	}

}

