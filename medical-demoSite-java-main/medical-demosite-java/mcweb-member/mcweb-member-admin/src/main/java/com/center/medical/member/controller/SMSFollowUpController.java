package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.SmsRecord;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Shortmessage;
import com.center.medical.data.service.ShortmessageService;
import com.center.medical.member.bean.param.SMSFollowUpParam;
import com.center.medical.member.bean.param.SMSSaOrUpParam;
import com.center.medical.member.bean.param.SMSTemplateParam;
import com.center.medical.member.bean.param.SmsDataParam;
import com.center.medical.member.bean.vo.SMSFollowUpVo;
import com.center.medical.member.service.SMSFollowUpService;
import com.center.medical.service.SmsRecordService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 回访管理-预约短信回访(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-02-10 14:25:16
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "回访管理-预约短信回访")
@RequestMapping("/member/smsFollowUp")
public class SMSFollowUpController extends BaseController {
	/**
	* 服务对象
	*/
	private final SMSFollowUpService smsFollowUpService;
    private final MapperFacade mapperFacade;
    private final SmsRecordService smsRecordService;
    private final ShortmessageService shortmessageService;


	@GetMapping("/getInterfaceList")
	@ApiOperation(value = "接口说明", notes = "获取【预约短信回访】这个块业务所有接口及接口说明")
	public R<FunctionVo> getInterfaceList() {
		List<InterfaceVo> interfaceVos = Arrays.asList(
				new InterfaceVo("分页查询", "GET", "/member/smsFollowUp/page", "07.会员系统->客服管理-回访管理-预约短信回访->分页查询", null),
				new InterfaceVo("保存", "POST", "/member/smsFollowUp/saveGroupData", "07.会员系统->客服管理-回访管理-预约短信回访->保存", null),
				new InterfaceVo("查看短信数据", "GET", "/member/smsFollowUp/getSmsData", "07.会员系统->客服管理-回访管理-预约短信回访->保存", null),
				new InterfaceVo("获取短信模板", "GET", "/member/smsFollowUp/getComboData", "07.会员系统->客服管理-回访管理-预约短信回访->获取短信模板", null),
				new InterfaceVo("取消发送", "PUT", "/member/smsFollowUp/cancleSMS", "07.会员系统->客服管理-回访管理-预约短信回访->取消发送", null)

		);
		return R.ok(new FunctionVo("07.会员系统", "预约短信回访", interfaceVos, "07.会员系统"));
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
	public R<IPage<SMSFollowUpVo>> getPage(PageParamSimple pageParamSimple, SMSFollowUpParam param) {
		PageParam<SMSFollowUpVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.smsFollowUpService.getList(page, param));
	}



	/**
	* 新增数据
	*
	* @param param 实体对象
	* @return 新增结果
	*/
	@PostMapping("/saveGroupData")
	//@PreAuthorize("@ss.hasPermi('::add')")
	@ApiOperation(value = "保存", notes = "群发短信编辑-保存")
	@Log(title = "QT体检者表", businessType = BusinessType.INSERT)
	@ApiOperationSupport(ignoreParameters = {"peispatient.id"})
	public R saveGroupData(@RequestBody SMSSaOrUpParam param) {
		return R.toResult(this.smsFollowUpService.saveGroupData(param));
	}


	/**
	 * 查看短信数据
	 * @param pageParamSimple
	 * @param param
	 * @return
	 */
	@GetMapping("/getSmsData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "查看短信数据", notes = "查看短信数据")
	public R<IPage<SmsRecord>> getSmsData(PageParamSimple pageParamSimple, SmsDataParam param) {
		PageParam<SmsRecord> page = mapperFacade.map(pageParamSimple, PageParam.class);
		return R.ok(this.smsFollowUpService.getSmsData(page, param));
	}


//	/**
//	 * 群发短信发送编辑
//	 * @param param
//	 * @return
//	 */
//	@GetMapping("/smssend")
//	//@PreAuthorize("@ss.hasPermi('::list')")
//	@ApiOperation(value = "群发短信发送编辑", notes = "群发短信发送编辑")
//	public R smssend(SmssendParam param) {
//		return R.ok(this.smsFollowUpService.smssend(param));
//	}
//
//
//	/**
//	 * 群发短信发送编辑
//	 * @param patientcode
//	 * @return
//	 */
//	@GetMapping("/smssends")
//	//@PreAuthorize("@ss.hasPermi('::list')")
//	@ApiOperation(value = "群发短信发送编辑", notes = "群发短信发送编辑")
//	@ApiImplicitParam(name = "patientcode", value = "体检号")
//	public R smssends(String patientcode) {
//		smssendsVo vo = new smssendsVo();
//		if(patientcode.indexOf(",")==-1){
//			//查询短信发送记录
//			SmsRecord smsRecord = smsRecordService.getOne(new QueryWrapper<SmsRecord>()
//					.eq("patientcode",patientcode)
//					.eq("notify_type","0")
//					.eq("notify_result",2));
//			vo.setSmsRecord(smsRecord);
//			if(smsRecord!=null){
//				//短信信息表
//				Shortmessage shortmessage = shortmessageService.getInfoById(smsRecord.getIdTemplate());
//				vo.setShortmessage(shortmessage);
//			}
//		}
//		return R.ok(vo);
//	}


	/**
	 * 获取短信模板
	 * @param messageType
	 * @param key
	 * @return
	 */
	@GetMapping("/getComboData")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "获取短信模板", notes = "获取短信模板")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "messageType", value = "短信类型"),
			@ApiImplicitParam(name = "key", value = "输入码模糊查询")
	})
	public R<List<Shortmessage>> getComboData(String messageType,String key) {
		QueryWrapper<Shortmessage> con = new QueryWrapper<>();
		con.eq("is_delete",0);
		if(StringUtils.isNotEmpty(messageType)){
			con.in("message_type",new String[]{messageType,"-1"});
		}
		if(StringUtils.isNotEmpty(key)){
			con.like("inputcode", key.trim().toUpperCase());
		}
		List<Shortmessage> list = shortmessageService.list(con);
		return R.ok(list);
	}


	/**
	 * 取消发送
	 * @param patientcode
	 * @return
	 */
	@PutMapping("/cancleSMS")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "取消发送", notes = "取消发送")
	@ApiImplicitParam(name = "patientcode", value = "多个体检号,逗号分割")
	public R cancleSMS(@RequestParam("patientcode") List<String> patientcode) {
		Boolean b = smsFollowUpService.cancleSMS(patientcode);
		return R.toResult(b);
	}




	/**
	 * 获取短信模板
	 *
	 * @return
	 */
	@GetMapping("/getSMSTemplate")
	//@PreAuthorize("@ss.hasPermi('::list')")
	@ApiOperation(value = "获取检前短信模板", notes = "获取检前短信模板")
	public R<List<Shortmessage>> getSMSTemplate(SMSTemplateParam param) {
		QueryWrapper<Shortmessage> con = new QueryWrapper<>();
		con.eq("is_delete",0);
		if(StringUtils.isNotEmpty(param.getMessageType())){
			con.in("message_type",param.getMessageType());
		}
		if(StringUtils.isNotEmpty(param.getKey())){
			con.like("inputcode", param.getKey().trim());
		}
		List<Shortmessage> list = shortmessageService.list(con);
		return R.ok(list);
	}

}

