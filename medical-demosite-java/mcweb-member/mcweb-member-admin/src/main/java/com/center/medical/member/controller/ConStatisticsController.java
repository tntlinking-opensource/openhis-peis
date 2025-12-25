package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.member.bean.param.ConStatisticsParam;
import com.center.medical.member.bean.vo.StatisticsListVo;
import com.center.medical.member.service.ConsulationService;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 咨询与随访统计(Consulation)表控制层
 *
 * @author ay
 * @since 2023-02-09 10:27:03
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "咨询与随访统计")
@RequestMapping("/member/conStatistics")
public class ConStatisticsController extends BaseController {
	/**
	* 服务对象
	*/
	private final ConsulationService consulationService;
    private final MapperFacade mapperFacade;
    private final ISysUserService sysUserService;



	@GetMapping("/getInterfaceList")
	@ApiOperation(value = "接口说明", notes = "获取【咨询与随访统计】这个块业务所有接口及接口说明")
	public R<FunctionVo> getInterfaceList() {
		List<InterfaceVo> interfaceVos = Arrays.asList(
				new InterfaceVo("分页查询", "GET", "/member/conStatistics/getStatisticsListData", "07.会员系统->客服管理-咨询与随访->分页查询", null),
				new InterfaceVo("医生下拉", "GET", "/member/consulation/getAllUserData", "07.会员系统->客服管理-咨询与随访->医生下拉", null)
		);
		return R.ok(new FunctionVo("07.会员系统", "咨询与随访统计", interfaceVos, "07.会员系统"));
	}
	/**
	* 分页查询所有数据
	*
	* @param pageParamSimple 分页参数
	* @param param 查询条件
	* @return 所有数据
	*/
	@GetMapping("/getStatisticsListData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "咨询与随访统计分页查询", notes = "分页查询科室/总检咨询")
	public R<IPage<StatisticsListVo>> getStatisticsListData(PageParamSimple pageParamSimple, ConStatisticsParam param) {
		PageParam<StatisticsListVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.consulationService.getStatisticsListData(page, param));
	}


}

