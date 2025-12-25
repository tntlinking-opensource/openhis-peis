package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;

import com.center.medical.member.bean.param.TotalCountParam;
import com.center.medical.member.bean.vo.TotalCountVo;
import com.center.medical.member.service.SatisfactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 满意度管理-满意度统计(Satisfaction)表控制层
 *
 * @author ay
 * @since 2022-11-28 11:23:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "满意度管理-满意度统计")
@RequestMapping("/totalCount")
public class TotalCountController extends BaseController {
	/**
	* 服务对象
	*/
	private final SatisfactionService satisfactionService;
    private final MapperFacade mapperFacade;

	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param totalCountParam 查询条件
	* @return 所有数据
	*/
	@GetMapping("/page")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "分页查询", notes = "分页查询KF满意度")
	public R<IPage<TotalCountVo>> getPage(PageParamSimple pageParamSimple, TotalCountParam totalCountParam) {
		PageParam<TotalCountVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.satisfactionService.getTotalCountPage(page, totalCountParam));
	}





}

