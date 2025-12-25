package com.center.medical.center.common.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.service.SectionResultMainService;
import com.center.medical.center.common.bean.model.RilinSyncResult;
import com.center.medical.center.common.bean.model.RilinSyncTime;
import com.center.medical.center.common.bean.model.RilinTotalVerdict;
import com.center.medical.center.common.constant.RilinConstant;
import com.center.medical.center.common.dao.RilinSyncResultMapper;
import com.center.medical.center.common.dao.RilinSyncTimeMapper;
import com.center.medical.center.common.service.RilinSyncTimeService;
import com.center.medical.center.common.service.RilinTotalVerdictService;
import com.center.medical.center.common.util.RilinUtil;
import com.center.medical.common.config.RilinConfig;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.olddata.bean.model.MdSectionTotal;
import com.center.medical.olddata.service.MdSectionTotalService;
import com.center.medical.reception.service.PeispatientexamitemService;
import com.center.medical.service.DescribeService;
import com.center.medical.service.PacsResultService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 对接瑞林萨尔健康管理系统，用于记录上一次上传数据的止时间。(RilinSyncTime)服务实现类
 * @since 2025-03-21 14:53:06
 */
@Slf4j
@Service("rilinSyncTimeService")
@RequiredArgsConstructor
public class RilinSyncTimeServiceImpl extends ServiceImpl<RilinSyncTimeMapper, RilinSyncTime> implements RilinSyncTimeService {

	private final RilinSyncTimeMapper rilinSyncTimeMapper;
	private final ISysConfigService iSysConfigService;
	private final PeispatientService peispatientService;
	private final PeispatientfeeitemService peispatientfeeitemService;
	private final PacsResultService pacsResultService;
	private final SectionResultMainService sectionResultMainService;
	private final DescribeService describeService;
	private final PeispatientexamitemService peispatientexamitemService;
	private final MdSectionTotalService mdSectionTotalService;
	private final RilinTotalVerdictService rilinTotalVerdictService;
	private final RilinSyncResultMapper rilinSyncResultMapper;
	private final ISysBranchService iSysBranchService;

	/**
	 * 实时同步体检结果
	 *
	 * 体检者表的修改时间会在各体检阶段一直变，不容易漏掉
	 * 体检结果表，数据量大，都是真删，只能跟着体检者一起同步
	 */
	@Override
	public void syncPatient(){
		//读取sys_config,判断是否开启上传功能
		RilinConfig rilinConfig=iSysConfigService.getSysConfigObject(Constants.RILIN_CONFIG, RilinConfig.class);
		if(rilinConfig==null || rilinConfig.getIsPatientOpen()==null || !rilinConfig.getIsPatientOpen().booleanValue())return;
		if(!RilinUtil.isTaskAuthorizedIp(rilinConfig))return;

		//记录当前时间
		Date currentTime=rilinSyncTimeMapper.getCurrentDbTime();
		String tableName= RilinConstant.PATIENT;
		//查询上次上传时间
		RilinSyncTime rilinSyncTime=rilinSyncTimeMapper.selectOne(
				new LambdaQueryWrapper<RilinSyncTime>()
						.eq(RilinSyncTime::getTableName, tableName)
		);
		//本次上传开始时间(首次上传为Null)
		Date totalStartTime=null;
		//是否首次上传
		boolean isFirst=rilinSyncTime==null;
		if(!isFirst){
			totalStartTime=rilinSyncTime.getLastSyncTime();
		}

		//本次上传时间段内有体检者的日期
		List<Date> dates=rilinSyncTimeMapper.selectPatientDateList(totalStartTime,currentTime);
		if(dates.size()==0)return;

		//分中心简码
		String fzxjm = StringUtils.isBlank(ZhongkangConfig.getFzxjm()) ? iSysBranchService.getBranchPrefix() : ZhongkangConfig.getFzxjm();

		for(int i=0;i<dates.size();i++){
			//当天
			Date date=dates.get(i);
			//当天开始时间
			Date startTime=null;
			//当天结束时间
			Date endTime=null;
			//如果不是第一次上传，并且是第一天，开始时间=上一次上传结束时间
			if(!isFirst && i==0){
				startTime=totalStartTime;
			//否则开始时间为当天的开始
			}else{
				startTime=DateUtil.beginOfDay(date);
			}
			//如果是最后一天，结束时间为当前时间
			if(i==dates.size()-1){
				endTime=currentTime;
			//否则结束时间为当天的最后
			}else{
				endTime=DateUtil.endOfDay(date);
			}

			//按修改时间段，查询所有体检者ID(分页查询再上传，时间长，很可能漏数据)
			List<String> patientIds=rilinSyncTimeMapper.selectPatientIdList(startTime,endTime);
			if(patientIds.size()==0)continue;

			//记录上传总数
			int patientCount=0;
			int feeItemCount=0;
			int pacsResultCount=0;
			int mainCount=0;
			int describeCount=0;
			int examitemCount=0;
			int totalCount=0;
			int verdictCount=0;

			//分批次上传（有一个特殊日期，一天有2W条被修改的数据）
			List<List<String>> patientIdLists= ListUtil.split(patientIds,RilinConstant.PATIENT_SIZE);
			for(List<String> patientIdList:patientIdLists){
				//使用id查询所有体检者
				List<Peispatient> peispatients=peispatientService.list(
						new LambdaQueryWrapper<Peispatient>()
							.in(Peispatient::getId,patientIdList)
				);

				//需上传的体检号
				List<String> patientcodes=new ArrayList<>();
				//需上传的体检者
				List<Peispatient> peispatientsUp=new ArrayList<>();

				//判断是否可以覆盖（多中心可能有相同体检者）
				for(Peispatient peispatient:peispatients){
					Peispatient peispatientYilin=peispatientService.getByIdRilin(peispatient.getId());
					if(peispatientYilin!=null){
						//如果原数据已登记，当前数据未登记，不覆盖
						if(
								peispatientYilin.getFRegistered()!=null
								&& peispatientYilin.getFRegistered().intValue()==1
								&&(
										peispatient.getFRegistered()==null || peispatient.getFRegistered().intValue()==0
										)
						){
							continue;
						}
						//如果原数据已总检，当前数据未总检 或 当前数据总检时间不晚于原数据（因总检后流程导致修改时间改变，但结果已经不会再变了），不覆盖
						/**
						 *  修改为，当前数据和原数据总检时间一样也覆盖。
						 *  因为，如果上传出现异常，或者重启服务，peispatient已上传，但其他数据没有上传，需要重新上传的时候。Peispatient已经上传并且是已总检状态，这些没上传完的数据会被跳过。
						 *  只对不同中心有同一个体检者的情况进行过滤
						 */
						if(peispatientYilin.getJktjzt()!=null && peispatientYilin.getJktjzt()>0){
							if(peispatient.getJktjzt()==null || peispatient.getJktjzt().intValue()==0){
								continue;
							}
							if(
									!(
											peispatient.getDatefinalexamed()!=null
													&&
													peispatientYilin.getDatefinalexamed()!=null
													&&
//													peispatient.getDatefinalexamed().after(peispatientYilin.getDatefinalexamed())
													!peispatient.getDatefinalexamed().before(peispatientYilin.getDatefinalexamed())
											)
							){
								continue;
							}
						}
					}
					peispatientsUp.add(peispatient);
					patientcodes.add(peispatient.getPatientcode());
				}

				if(patientcodes.size()==0)continue;

				//开始上传
				//md_peispatient
				if(peispatientsUp.size()>0)peispatientService.saveOrUpdateBatchRilin(peispatientsUp);
				patientCount+=peispatientsUp.size();

				//md_peispatientfeeitem
				List<Peispatientfeeitem> peispatientfeeitems=peispatientfeeitemService.list(
						new LambdaQueryWrapper<Peispatientfeeitem>()
							.in(Peispatientfeeitem::getIdPatient,patientcodes)
				);
				//不一定能删掉，因为体检号可能从线上体检号变成了线下体检号,没有找到记录线上体检号的字段。
				//这一步可以保证这个体检号的收费项目不会多
				peispatientfeeitemService.removeRilin(
						new LambdaQueryWrapper<Peispatientfeeitem>()
							.in(Peispatientfeeitem::getIdPatient,patientcodes)
				);
				if(peispatientfeeitems.size()>0){
					try{
						peispatientfeeitemService.saveBatchRilin(peispatientfeeitems);
					}catch(Exception e){
						//如果不同中心，出现重复id，在id后面拼上中心简码再次尝试上传
						String errorMsg= ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT);
						if(errorMsg!=null && errorMsg.indexOf(RilinConstant.ERROR_MSG_DUPLICATE_ENTRY)!=-1){
							for(Peispatientfeeitem peispatientfeeitem:peispatientfeeitems){
								peispatientfeeitem.setId(RilinUtil.handleId(peispatientfeeitem.getId(),fzxjm));
							}
							/**
							 * 将saveBatchRilin改为saveOrUpdateBatchRilin
							 * 出现了带后缀的id报重复的异常
							 * 1.线下将体检号为APP01的体检者和收费项目id为001的项目上传至线上，发现线上已经存在另一个体检号的重复收费项目id：001。将收费项目id处理为001_03后上传。
							 * 此时线上体检号APP01,收费项目id两个，001、001_03。
							 * 2.再次上传APP01和001，没问题
							 * 3.线下APP01变成0301，再上传，001_03因为还是线上体检号不会被删除掉，先001重复抛异常，然后001_03依然重复，再抛异常,上传失败。
							 *
							 * 也可能是自己的Id没重复，但同组其他id重复了。自己的id虽然没重复，但也被加上后缀。
							 *
							 * 带了后缀的id，一定不会重复，用saveOrUpdateBatchRilin覆盖是没有问题的。
							 * 这样做的问题是，存在线上号转换为线下号的情况时，一定会在正常情况下捕获一次异常，带有线上体检号的peispatientfeeitem无法被删除，会一直保留在数据库中。
							 * 最好是删除的时候能按线下体检号和线上体检号删除，但是没有在peispatient表中找到存储线上体检号的字段。
							 * 最最好是不要有重复id
							 */
							peispatientfeeitemService.saveOrUpdateBatchRilin(peispatientfeeitems);
						//如果不是重复id问题，就抛出异常，记录日志
						}else{
							throw e;
						}
					}
				}
				feeItemCount+=peispatientfeeitems.size();

				//md_pacs_result
				List<PacsResult> pacsResults = pacsResultService.list(
						new LambdaQueryWrapper<PacsResult>()
								.in(PacsResult::getPatientcode,patientcodes)
				);
				pacsResultService.removeRilin(
						new LambdaQueryWrapper<PacsResult>()
								.in(PacsResult::getPatientcode,patientcodes)
				);
				if(pacsResults.size()>0){
					try{
						pacsResultService.saveBatchRilin(pacsResults);
					}catch(Exception e){
						String errorMsg= ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT);
						if(errorMsg!=null && errorMsg.indexOf(RilinConstant.ERROR_MSG_DUPLICATE_ENTRY)!=-1){
							for(PacsResult pacsResult:pacsResults){
								pacsResult.setId(RilinUtil.handleId(pacsResult.getId(),fzxjm));
							}
							pacsResultService.saveBatchRilin(pacsResults);
						}else{
							throw e;
						}
					}
				}
				pacsResultCount+=pacsResults.size();

				//md_section_result_main
				List<SectionResultMain> sectionResultMains = sectionResultMainService.list(
						new LambdaQueryWrapper<SectionResultMain>()
								.in(SectionResultMain::getPatientcode,patientcodes)
				);
				sectionResultMainService.removeRilin(
						new LambdaQueryWrapper<SectionResultMain>()
							.in(SectionResultMain::getPatientcode,patientcodes)
				);
				if(sectionResultMains.size()>0){
					try{
						sectionResultMainService.saveBatchRilin(sectionResultMains);
					}catch(Exception e){
						String errorMsg= ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT);
						if(errorMsg!=null && errorMsg.indexOf(RilinConstant.ERROR_MSG_DUPLICATE_ENTRY)!=-1){
							for(SectionResultMain sectionResultMain:sectionResultMains){
								sectionResultMain.setId(RilinUtil.handleId(sectionResultMain.getId(),fzxjm));
							}
							sectionResultMainService.saveBatchRilin(sectionResultMains);
						}else{
							throw e;
						}
					}
				}
				mainCount+=sectionResultMains.size();

				//md_describe
				List<Describe> describes=describeService.list(
						new LambdaQueryWrapper<Describe>()
							.in(Describe::getPatientcode,patientcodes)
				);
				describeService.removeRilin(
						new LambdaQueryWrapper<Describe>()
								.in(Describe::getPatientcode,patientcodes)
				);
				if(describes.size()>0){
					try{
						describeService.saveBatchRilin(describes);
					}catch(Exception e){
						String errorMsg= ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT);
						if(errorMsg!=null && errorMsg.indexOf(RilinConstant.ERROR_MSG_DUPLICATE_ENTRY)!=-1){
							for(Describe describe:describes){
								describe.setId(RilinUtil.handleId(describe.getId(),fzxjm));
							}
							describeService.saveBatchRilin(describes);
						}else{
							throw e;
						}
					}
				}
				describeCount+=describes.size();

				//md_peispatientexamitem
				List<Peispatientexamitem> peispatientexamitems=peispatientexamitemService.list(
						new LambdaQueryWrapper<Peispatientexamitem>()
							.in(Peispatientexamitem::getPatientcode,patientcodes)
				);
				peispatientexamitemService.removeRilin(
						new LambdaQueryWrapper<Peispatientexamitem>()
								.in(Peispatientexamitem::getPatientcode,patientcodes)
				);
				if(peispatientexamitems.size()>0){
					try{
						peispatientexamitemService.saveBatchRilin(peispatientexamitems);
					}catch(Exception e){
						String errorMsg= ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT);
						if(errorMsg!=null && errorMsg.indexOf(RilinConstant.ERROR_MSG_DUPLICATE_ENTRY)!=-1){
							for(Peispatientexamitem peispatientexamitem:peispatientexamitems){
								peispatientexamitem.setId(RilinUtil.handleId(peispatientexamitem.getId(),fzxjm));
							}
							peispatientexamitemService.saveBatchRilin(peispatientexamitems);
						}else{
							throw e;
						}
					}
				}
				examitemCount+=peispatientexamitems.size();

				//md_section_total
				List<MdSectionTotal> mdSectionTotals=mdSectionTotalService.list(
						new LambdaQueryWrapper<MdSectionTotal>()
							.in(MdSectionTotal::getPatientcode,patientcodes)
				);
				mdSectionTotalService.removeRilin(
						new LambdaQueryWrapper<MdSectionTotal>()
								.in(MdSectionTotal::getPatientcode,patientcodes)
				);
				//记录未处理过的section_total表id
				List<String> totalIds=null;
				//是否存在重复Id
				boolean hasRepeatId=false;
				if(mdSectionTotals.size()>0){
					totalIds=new ArrayList<>();
					for(MdSectionTotal sectionTotal:mdSectionTotals){
						totalIds.add(sectionTotal.getId());
					}
					try{
						mdSectionTotalService.saveBatchRilin(mdSectionTotals);
					}catch(Exception e){
						String errorMsg= ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT);
						if(errorMsg!=null && errorMsg.indexOf(RilinConstant.ERROR_MSG_DUPLICATE_ENTRY)!=-1){
							for(MdSectionTotal sectionTotal:mdSectionTotals){
								sectionTotal.setId(RilinUtil.handleId(sectionTotal.getId(),fzxjm));
							}
							mdSectionTotalService.saveBatchRilin(mdSectionTotals);
							hasRepeatId=true;
						}else{
							throw e;
						}
					}
				}
				totalCount+=mdSectionTotals.size();

				//md_total_verdict
				log.info("Rilin删除md_total_verdict体检号:{}",patientcodes);
				rilinTotalVerdictService.removeByPatientcodesRilin(patientcodes);
				if(mdSectionTotals.size()>0){
					List<RilinTotalVerdict> rilinTotalVerdicts=rilinTotalVerdictService.list(
							new LambdaQueryWrapper<RilinTotalVerdict>()
								.in(RilinTotalVerdict::getTotalId,totalIds)
					);
					if(rilinTotalVerdicts.size()>0){
						if(hasRepeatId){
							for(RilinTotalVerdict totalVerdict:rilinTotalVerdicts){
								totalVerdict.setTotalId(RilinUtil.handleId(totalVerdict.getTotalId(),fzxjm));
							}
						}
						try{
							rilinTotalVerdictService.saveBatchRilin(rilinTotalVerdicts);
						}catch(Exception e){
							String errorMsg= ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT);
							if(errorMsg!=null && errorMsg.indexOf(RilinConstant.ERROR_MSG_DUPLICATE_ENTRY)!=-1){
								for(RilinTotalVerdict totalVerdict:rilinTotalVerdicts){
									totalVerdict.setId(RilinUtil.handleId(totalVerdict.getId(),fzxjm));
								}
								rilinTotalVerdictService.saveBatchRilin(rilinTotalVerdicts);
							}else{
								throw e;
							}
						}
					}
					verdictCount+=rilinTotalVerdicts.size();
				}
			}

			if(patientCount==0)continue;

			//记录本次上传时间
			if(rilinSyncTime==null){
				rilinSyncTime=new RilinSyncTime();
				rilinSyncTime.setTableName(tableName);
				rilinSyncTime.setLastSyncTime(endTime);
				rilinSyncTime.setCreatedate(new Date());
				rilinSyncTimeMapper.insert(rilinSyncTime);
			}else{
				rilinSyncTime.setLastSyncTime(endTime);
				rilinSyncTime.setModifydate(new Date());
				rilinSyncTimeMapper.updateById(rilinSyncTime);
			}

			//记录上传结果
			RilinSyncResult rilinSyncResult=new RilinSyncResult();
			rilinSyncResult.setIsSuccess(1);
			rilinSyncResult.setTableName(tableName);
			rilinSyncResult.setStartTime(startTime);
			rilinSyncResult.setEndTime(endTime);
			rilinSyncResult.setRemarks(
					"上传成功，md_peispatient:"+patientCount+"条，"
					+"md_peispatientfeeitem:"+feeItemCount+"条，"
					+"md_pacs_result:"+pacsResultCount+"条，"
					+"md_section_result_main:"+mainCount+"条，"
					+"md_describe:"+describeCount+"条，"
					+"md_peispatientexamitem:"+examitemCount+"条，"
					+"md_total_verdict:"+verdictCount+"条，"
					+"md_section_total:"+totalCount+"条。"
					);
			rilinSyncResult.setCreatedate(new Date());
			rilinSyncResultMapper.insert(rilinSyncResult);

		}

	}





}
