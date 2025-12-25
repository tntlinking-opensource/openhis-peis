package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.abteilung.bean.dto.GetC13dataDto;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.param.AutoSaveParam;
import com.center.medical.abteilung.service.DivisionService;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.abteilung.service.VisionService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.data.service.PatienttypeService;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.service.PeisorgreservationgroupService;
import com.center.medical.service.PeispatientPhotoService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 科室列表-视力检查(SectionResultMain)表控制层
 *
 * @author ay
 * @since 2023-02-20 19:16:34
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室列表-视力检查")
@RequestMapping("abteilung/vision")
public class VisionController extends BaseController {
	/**
	* 服务对象
	*/
	private final VisionService visionService;
    private final MapperFacade mapperFacade;
    private final ISysBranchService iSysBranchService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final SectionResultMainService sectionResultMainService;
    private final PeispatientService peispatientService;
    private final PeispatientPhotoService peispatientPhotoService;
    private final PeisorgreservationgroupService peisorgreservationgroupService;
    private final PatienttypeService patienttypeService;
    private final DivisionService divisionService;



	/**
	* 读卡
	*
	* @return 单条数据
	*/
	@GetMapping("vision")
	//@PreAuthorize("@ss.hasPermi('::info')")
	@ApiOperation(value = "读卡", notes = "读卡")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "patientcode", value = "体检号"),
			@ApiImplicitParam(name = "gridSeclect", value = "是否是选择体检者列表（内科）"),
			@ApiImplicitParam(name = "autoFill", value = "是否自动补全"),
			@ApiImplicitParam(name = "ksID", value = "科室id")
	})
	public R vision(String patientcode,String gridSeclect,String autoFill,String ksID) {
		Map<String,Object> data = new HashMap<>();
		String create_url = FileTypePath.CREATE_IP;
		String lrrId = SecurityUtils.getUserNo();
		String lrr = SecurityUtils.getUsername();
		Date lrsj = new Date();
		data.put("create_url",create_url);
		data.put("lrrId",lrrId);
		data.put("lrr",lrr);
		data.put("lrsj",lrsj);

		String flag = "";
		if (StringUtils.isNotBlank(patientcode)) {
			if (gridSeclect == null || "false".equals(gridSeclect)) {
				if (autoFill != null && "true".equals(autoFill)) {
					//体检号补全
					patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
				} else {
					patientcode = patientcode.trim().toUpperCase();
				}
			}
			//体检者收费项目表
			List<Peispatientfeeitem> list1 = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
					.eq("id_patient", patientcode).eq("id_ks", ksID)
					.isNull("f_transferedhl7").eq("sfjj", 0)
					.eq("f_giveup", 0).eq("change_item", 0));
			if (list1 != null && list1.size() > 0) {
				boolean charged = false;// 判断是否有已缴费的费用项目
				boolean paid = true;// 是否全部缴费
				StringBuilder unpaid = new StringBuilder();
				List<Peispatientfeeitem> tjzsfxm = new ArrayList<Peispatientfeeitem>();
				for (Peispatientfeeitem feeitem : list1) {
					if (feeitem.getFFeecharged() != null && feeitem.getFFeecharged() == 1) {
						charged = true;
						tjzsfxm.add(feeitem);
					} else {
						paid = false;
						if (unpaid.length() == 0) {
							unpaid.append("该体检号存在未缴费的费用项目：" + feeitem.getExamfeeitemName());
						} else {
							unpaid.append("," + feeitem.getExamfeeitemName());
						}
					}
				}
				if (charged) {
					if (!paid) {
						flag = unpaid.toString();
					}
					//科室检查结果主表
					SectionResultMain main = sectionResultMainService.getOne(new QueryWrapper<SectionResultMain>()
							.eq("patientcode", patientcode).eq("dep_id", ksID));
					if (main != null && main.getIsAudit() != null && main.getIsAudit()==1) {
						flag = "audit";// 已审核 不能修改
					}
					//体检者表
					Peispatient user = peispatientService.getOne(new QueryWrapper<Peispatient>()
							.eq("patientcode", patientcode).eq("f_registered", 1));
					if (user == null) {
						throw new ServiceException("该体检号尚未登记!");
					}

					String picture = peispatientPhotoService.getPicture(user);
					data.put("peispatient",user);
					data.put("picture",picture);
					data.put("sectionResultMain",main);
					if (user.getFPaused() != null && user.getFPaused().intValue() == 1) {
						throw new ServiceException("该体检号已被禁检!");
					}
					//任务分组ID
					String idOrgreservationgroup = user.getIdOrgreservationgroup();
					if (idOrgreservationgroup != null) {
						Peisorgreservationgroup org = peisorgreservationgroupService.getInfoById(idOrgreservationgroup);
						//组禁用
						if (org != null && org.getFGrouppaused() != null && org.getFGrouppaused().intValue() == 1) {
							throw new ServiceException( "该体检号已被禁检!");
						}
					}
					String isVIP = patienttypeService.getIdPatientClass(user);
					data.put("isVIP",isVIP);
					String tjlx = user.getIdExamtype();// 体检类型
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("ksId", ksID);// 科室Id
					map.put("tjh", patientcode);// 体检号
					map.put("tjlx", tjlx);// 体检类型
					List<GetC13dataDto> sldata = visionService.getVisiondata(map, tjzsfxm);
					List<HashMap<String, String>> griddata = divisionService.jlcData(patientcode, ksID);
					data.put("sldata",sldata);
					data.put("griddata",griddata);
				} else {
					flag = "该体检号尚未缴费!";
				}
			} else {
				flag = "该体检号没有本科室收费项目!";
			}
		}
		data.put("flag",flag);
		return R.ok(data);
	}



	/**
	 * 审核(图像检查)
	 * @param param
	 * @return
	 */
	@PostMapping("/shenhe")
	//@PreAuthorize("@ss.hasPermi('::edit')")
	@ApiOperation(value = "审核", notes = "审核")
	public R shenhe(@RequestBody AutoSaveParam param) {
		Boolean b = divisionService.shenhe(param);
		return R.toResult(b);
	}


	/**
	 * 反审核
	 * @param patientcode
	 * @param ksID
	 * @return
	 */
	@PutMapping("/caseReverse")
	//@PreAuthorize("@ss.hasPermi('::edit')")
	@ApiOperation(value = "反审核", notes = "反审核")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "patientcode", value = "体检号"),
			@ApiImplicitParam(name = "ksID", value = "科室id")
	})
	public R caseReverse(String patientcode,String ksID) {
		Boolean b = divisionService.caseReverse(patientcode,ksID);
		return R.toResult(b);
	}






}

