package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.FailTotalVisit;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.member.bean.param.PeispatientParam;
import com.center.medical.member.bean.param.PositiveTracktParam;
import com.center.medical.member.bean.vo.PeispatientVo;
import com.center.medical.member.service.PositiveTrackService;
import com.center.medical.sellcrm.bean.model.Riskclient;
import com.center.medical.sellcrm.bean.model.Riskclientcon;
import com.center.medical.sellcrm.service.RiskclientService;
import com.center.medical.sellcrm.service.RiskclientconService;
import com.center.medical.service.FailTotalVisitService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 回访管理-个检危险值回访(Peispatient)表控制层
 *
 * @author makejava
 * @since 2023-02-02 10:12:36
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "回访管理-个检危急值回访")
@RequestMapping("/member/peispatient")
public class PositiveTracktController extends BaseController {
	/**
	* 服务对象
	*/
	private final PositiveTrackService positiveTrackService;
    private final MapperFacade mapperFacade;
    private final PeispatientService peispatientService;
    private final FailTotalVisitService failTotalVisitService;
    private final ISysUserService sysUserService;
    private final RiskclientService riskclientService;
    private final RiskclientconService riskclientconService;

	/**
	 * 接口说明
	 * @return
	 */
	@GetMapping("/getInterfaceList")
	@ApiOperation(value = "接口说明",notes = "获取【回访管理-个检危险值回访】这个业务模块所有接口及接口说明")
	public R<FunctionVo> getInterfaceList(){
		List<InterfaceVo> interfaceVos = Arrays.asList(
				new InterfaceVo("分页查询","GET","/member/peispatient/page","客服系统->客服管理-回访管理-个检危险值回访->分页查询",null),
				new InterfaceVo("详情","GET","/member/peispatient/{id}","客服系统->客服管理-回访管理-个检危险值回访->详情",null),
				new InterfaceVo("导出Excel","POST","/member/peispatient/export","客服系统->客服管理-回访管理-个检危险值回访->导出Excel",null),
				new InterfaceVo("个检危险值回访","POST","/member/peispatient/edit","客服系统->客服管理-回访管理-个检危险值回访->个检危险值回访",null)
		);
		return R.ok(new FunctionVo("客服系统","回访管理-个检危险值回访",interfaceVos,"客服系统"));
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
	@ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
	public R<IPage<PeispatientVo>> getPage(PageParamSimple pageParamSimple, PeispatientParam param) {
		PageParam<Peispatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.positiveTrackService.getList(page, param));
	}

	/**
	 * 导出Excel文件
	 * @param response
	 * @param param
	 */
	@Log(title = "个检危险值回访",businessType = BusinessType.EXPORT)
	@PreAuthorize("@ss.hasAnyPermi('monitor:operlog:export')")
	@ApiOperation(value = "导出Excel",notes = "导出Excel")
	@PostMapping("/export")
	public void export(HttpServletResponse response, PeispatientParam param){
		List<PeispatientVo> list = positiveTrackService.export(param);
		ExcelUtil<PeispatientVo> util = new ExcelUtil<>(PeispatientVo.class);
		util.exportExcel(response,list,"个检危险值回访");
	}

	/**
	 * 详情
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	@ApiOperation(value = "详情",notes = "根据id查QT体检者表详情")
	@ApiImplicitParam(name = "id",value = "要查询的对象的主键{id}")
	public R selectOne(@PathVariable String id){
		//返回数据
		Map<String,Object> map = new HashMap<>();
		//体检者表
		Peispatient peispatient = peispatientService.getByPatientCode(id);
		map.put("patientname",peispatient.getPatientname());
		map.put("sex",peispatient.getIdSex());
		map.put("age",peispatient.getAge());
		map.put("phone",peispatient.getPhone());
		map.put("patientcode",peispatient.getPatientcode());
		map.put("idExamtype",peispatient.getIdExamtype());
		map.put("doctorapply",peispatient.getDoctorapply());
		map.put("dateregister",peispatient.getDateregister());
		//不合格样本回访
		FailTotalVisit failTotalVisit = failTotalVisitService.getOne(new QueryWrapper<FailTotalVisit>()
				.eq("personcode",id).eq("visit_type",1));
		if (ObjectUtils.isNotEmpty(failTotalVisit)){
			map.put("visitTime",failTotalVisit.getVisitTime());
			map.put("memo",failTotalVisit.getMemo());
		}
		//高危人员管理表
		List<Riskclient> riskclients = riskclientService.list(new QueryWrapper<Riskclient>().eq("tjid", peispatient.getPatientcode()));
		if (ObjectUtils.isNotEmpty(riskclients)){
			String str = "";
			String res = "";
			for (Riskclient riskclient : riskclients) {
				Riskclientcon sr = riskclientconService.getOne(new QueryWrapper<Riskclientcon>().eq("riskid", riskclient.getId()));
				if (StringUtils.isNotBlank(sr.getGwxm())){
					str += sr.getGwxm() + ";";
				}
				res += sr.getWjzxj() + ";";
			}
			map.put("gwxm",str);
			map.put("wjzxj",res);

		}
		return R.ok(map);
	}

	/**
	 * 阳性结果回访
	 *
	 * @param param 实体对象
	 * @return 新增结果
	 */
	@PostMapping("/saveOrUpdate")
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "阳性结果回访", notes = "阳性结果回访")
	public R saOrUp(@RequestBody PositiveTracktParam param) {
		return R.toResult(this.positiveTrackService.saOrUpPositiveTrackt(param));
	}



}

