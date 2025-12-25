package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import com.center.medical.member.bean.model.Consulation;
import com.center.medical.member.bean.param.ConSaOrUpParam;
import com.center.medical.member.bean.param.ConsulationParam;
import com.center.medical.member.service.ConsulationService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.bean.vo.AllUserDataVo;
import com.center.medical.system.service.ISysUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 咨询与随访(Consulation)表控制层
 *
 * @author ay
 * @since 2023-02-09 10:27:03
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "咨询与随访")
@RequestMapping("/member/consulation")
public class ConsulationController extends BaseController {
	/**
	* 服务对象
	*/
	private final ConsulationService consulationService;
    private final MapperFacade mapperFacade;
    private final ISysUserService sysUserService;
    private final PeispatientService peispatientService;


	@GetMapping("/getInterfaceList")
	@ApiOperation(value = "接口说明", notes = "获取【咨询与随访】这个块业务所有接口及接口说明")
	public R<FunctionVo> getInterfaceList() {
		List<InterfaceVo> interfaceVos = Arrays.asList(
				new InterfaceVo("分页查询", "GET", "/member/consulation/page", "07.会员系统->客服管理-咨询与随访->分页查询", null),
				new InterfaceVo("详情", "GET", "/member/consulation/{id}", "07.会员系统->客服管理-咨询与随访->详情", null),
				new InterfaceVo("保存", "POST", "/member/consulation/saveOrUpdateConsulation", "07.会员系统->客服管理-咨询与随访->保存", null),
				new InterfaceVo("医生下拉", "GET", "/member/consulation/getAllUserData", "07.会员系统->客服管理-咨询与随访->医生下拉", null),
				new InterfaceVo("咨询与随访加载", "GET", "/member/consulation/loadPeople", "07.会员系统->客服管理-咨询与随访->咨询与随访加载", null)
		);
		return R.ok(new FunctionVo("07.会员系统", "咨询与随访", interfaceVos, "07.会员系统"));
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
	@ApiOperation(value = "分页查询", notes = "分页查询科室/总检咨询")
	public R<IPage<Consulation>> getPage(PageParamSimple pageParamSimple, ConsulationParam param) {
		PageParam<Consulation> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.consulationService.getList(page, param));
	}

	/**
	* 通过主键查询单条数据
	*
	* @param id 主键
	* @return 单条数据
	*/
	@GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "详情", notes = "根据id查科室/总检咨询详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
	public R<Consulation> selectOne(@PathVariable String id) {
		return R.ok(this.consulationService.getInfoById(id));
	}

	/**
	* 新增数据
	*
	* @param param 实体对象
	* @return 新增结果
	*/
	@PostMapping("/saveOrUpdateConsulation")
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "保存", notes = "保存")
	@Log(title = "科室/总检咨询", businessType = BusinessType.INSERT)
	@ApiOperationSupport(ignoreParameters = {"consulation.id"})
	public R insert(@RequestBody ConSaOrUpParam param) {
		return R.toResult(this.consulationService.saOrUp(param));
	}



	/**
	 * 医生下拉
	 * @param key
	 * @return
	 */
	@GetMapping("/getAllUserData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "医生下拉", notes = "医生下拉")
	@ApiImplicitParam(name = "key", value = "搜索的用户名或输入码")
	public R<List<AllUserDataVo>> getAllUserData(String key) {
		List<AllUserDataVo> list = sysUserService.getAllUserData(key);
		return R.ok(list);
	}



	/**
	 * 所有用户下拉
	 * @param key
	 * @return
	 */
	@GetMapping("/loadPeople")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "咨询与随访加载", notes = "咨询与随访加载(按姓名、电话、体检号 加载体检者信息)")
	@ApiImplicitParam(name = "key", value = "姓名、电话、体检号")
	public R loadPeople(String key) {
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
		if(StringUtils.isNotEmpty(key)) {
			//模糊查询
			QueryWrapper<Peispatient> dis = new QueryWrapper<>();
			dis.like("patientcode", key).or().like("phone", key).or().like("patientname", key);
			List<Peispatient> patients = peispatientService.list(dis);
			//封装数据
			for(Peispatient patient:patients) {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("patientcode", patient.getPatientcode());
				map.put("patientname", patient.getPatientname());
				map.put("phone", patient.getPhone());
				map.put("id", patient.getId());
				data.add(map);
			}
		}
		return R.ok(data);
	}



}

