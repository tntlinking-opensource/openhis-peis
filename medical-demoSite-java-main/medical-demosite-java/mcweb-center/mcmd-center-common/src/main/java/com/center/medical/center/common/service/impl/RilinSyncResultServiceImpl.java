package com.center.medical.center.common.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.center.common.bean.model.RilinSyncResult;
import com.center.medical.center.common.bean.model.RilinSyncTime;
import com.center.medical.center.common.bean.model.RilinSysUser;
import com.center.medical.center.common.constant.RilinConstant;
import com.center.medical.center.common.dao.RilinSyncResultMapper;
import com.center.medical.center.common.dao.RilinSyncTimeMapper;
import com.center.medical.center.common.service.RilinSyncResultService;
import com.center.medical.center.common.service.RilinSysUserService;
import com.center.medical.center.common.util.RilinUtil;
import com.center.medical.common.config.RilinConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.InspectCharge;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.service.BasexamltemService;
import com.center.medical.data.service.InspectChargeService;
import com.center.medical.data.service.ItemsService;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 对接瑞林萨尔健康管理系统,记录数据上传结果(RilinSyncResult)服务实现类
 * @since 2025-03-21 14:46:36
 */
@Slf4j
@Service("rilinSyncResultService")
@RequiredArgsConstructor
public class RilinSyncResultServiceImpl extends ServiceImpl<RilinSyncResultMapper, RilinSyncResult> implements RilinSyncResultService {

	private final RilinSyncResultMapper rilinSyncResultMapper;
	private final ISysConfigService iSysConfigService;
	private final RilinSyncTimeMapper rilinSyncTimeMapper;
	private final SellcustomerService sellcustomerService;
	private final ItemsService itemsService;
	private final InspectChargeService inspectChargeService;
	private final BasexamltemService basexamltemService;
	private final RilinSysUserService rilinSysUserService;

	/**
	 * 实时上传基础数据
	 */
	@Override
	public void syncBasicIncremental(){
		syncBasic(false);
	}

	/**
	 * 全量上传基础数据
	 */
	@Override
	public void syncBasicFull(){
		syncBasic(true);
	}

	/**
	 * 上传基础数据
	 *
	 * 线上没有center服务，只能将线下的基础数据上传
	 *
	 * 使用创建时间和修改时间上传
	 * 部分表存在创建时间、修改时间为空的情况，应该是以前的老数据
	 * 第一次上传时，上传所有数据，包括时间为空的数据。
	 * 按创建时间升序排序，防止上传时有并发操作，创建时间为空的数据可能默认按主键升序排列，显式指定一下、
	 * 以后的数据应可以保证，创建时有创建时间，修改后有修改时间
	 * NULL排序时算最小，且无法参与比较，第二次上传不会再上传时间为NULL的数据
	 * 由于存在修改时间为空的情况，第二次上传，创建时间和修改时间都要判断。
	 *
	 * 都是假删,不用考虑删除
	 * 数据少
	 * 用到的字段少，不同中心id相同的数据可以直接覆盖
	 * 都是统一在线上维护
	 *
	 */
	@Override
	public void syncBasic(boolean isFull){
		//读取sys_config,判断是否开启上传功能
		RilinConfig rilinConfig=iSysConfigService.getSysConfigObject(Constants.RILIN_CONFIG, RilinConfig.class);
		if(rilinConfig==null || rilinConfig.getIsBasicOpen()==null || !rilinConfig.getIsBasicOpen().booleanValue())return;
		if(!RilinUtil.isTaskAuthorizedIp(rilinConfig))return;

		try{
			//上传crm_sellcustomer
			syncSellcustomer(isFull);
		}catch(Exception e){
			log.error(RilinConstant.SELLCUSTOMER+RilinConstant.REMARK_FAILED,e);
			//记录上传失败
			recordFailed(ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT),RilinConstant.SELLCUSTOMER);
		}

		try{
			//上传sys_user
			syncUser(isFull);
		}catch(Exception e){
			log.error(RilinConstant.USER+RilinConstant.REMARK_FAILED,e);
			//记录上传失败
			recordFailed(ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT),RilinConstant.USER);
		}

		try{
			//上传md_items
			syncItems(isFull);
		}catch(Exception e){
			log.error(RilinConstant.ITEMS+RilinConstant.REMARK_FAILED,e);
			//记录上传失败
			recordFailed(ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT),RilinConstant.ITEMS);
		}

		try{
			//上传md_basexamltem
			syncBasexamltem(isFull);
		}catch(Exception e){
			log.error(RilinConstant.BASEXAMLTEM+RilinConstant.REMARK_FAILED,e);
			//记录上传失败
			recordFailed(ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT),RilinConstant.BASEXAMLTEM);
		}

		try{
			//上传md_inspect_charge
			syncInspectCharge(isFull);
		}catch(Exception e){
			log.error(RilinConstant.INSPECT_CHARGE+RilinConstant.REMARK_FAILED,e);
			//记录上传失败
			recordFailed(ExceptionUtil.stacktraceToString(e,RilinConstant.ERROR_MSG_LIMIT),RilinConstant.INSPECT_CHARGE);
		}


	}

	/**
	 * 上传crm_sellcustomer
	 */
	@Override
	public void syncSellcustomer(boolean isFull){
		//记录当前时间
		Date currentDate=rilinSyncTimeMapper.getCurrentDbTime();
		String tableName=RilinConstant.SELLCUSTOMER;

		//查询上次上传时间
		RilinSyncTime rilinSyncTime=rilinSyncTimeMapper.selectOne(
				new LambdaQueryWrapper<RilinSyncTime>()
					.eq(RilinSyncTime::getTableName, tableName)
				);

		//如果没有上次记录，说明是第一次上传，上传表中所有数据
		if(rilinSyncTime==null || isFull){
			//分页查询
			//从第一页开始
			int current=1;
			//共上传total条
			int total=0;
			while(true){
				PageParam<Sellcustomer> page=new PageParam<>();
				page.setCurrent(current);
				page.setSize(RilinConstant.PAGE_SIZE);

				//按创建时间排序
				page=sellcustomerService.page(page
						,new LambdaQueryWrapper<Sellcustomer>()
								.orderByAsc(Sellcustomer::getCreatedate)
								.orderByAsc(Sellcustomer::getId)
				);

				List<Sellcustomer> sellcustomerList=page.getRecords();
				if(sellcustomerList.size()==0)break;

				sellcustomerService.saveOrUpdateBatchRilin(sellcustomerList);

				current++;
				total+=sellcustomerList.size();
			}

			//记录本次上传时间
			if(rilinSyncTime==null){
				rilinSyncTime=new RilinSyncTime();
				rilinSyncTime.setTableName(tableName);
				rilinSyncTime.setLastSyncTime(currentDate);
				rilinSyncTime.setCreatedate(currentDate);
				rilinSyncTimeMapper.insert(rilinSyncTime);
			}else{
				rilinSyncTime.setLastSyncTime(currentDate);
				rilinSyncTime.setModifydate(currentDate);
				rilinSyncTimeMapper.updateById(rilinSyncTime);
			}


			//记录上传结果
			recordSuccess(tableName,null,currentDate,"上传成功，共上传"+total+"条");

		//如果有上次记录，从上次的上传时间开始上传
		}else{
			//按修改时间查询
			Date startTime=rilinSyncTime.getLastSyncTime();
			List<Sellcustomer> sellcustomers = sellcustomerService.list(
					new LambdaQueryWrapper<Sellcustomer>()
						.ge(Sellcustomer::getModifydate,startTime)
						.or()
						.ge(Sellcustomer::getCreatedate,startTime)
			);
			if(sellcustomers.size()==0)return;

			sellcustomerService.saveOrUpdateBatchRilin(sellcustomers);

			//更新本次上传时间
			rilinSyncTime.setLastSyncTime(currentDate);
			rilinSyncTime.setModifydate(currentDate);
			rilinSyncTimeMapper.updateById(rilinSyncTime);

			//记录上传结果
			recordSuccess(tableName,startTime,currentDate,"上传成功，共上传"+sellcustomers.size()+"条");
		}


	}

	/**
	 * 上传md_items
	 */
	@Override
	public void syncItems(boolean isFull) {
		//记录当前时间
		Date currentDate=rilinSyncTimeMapper.getCurrentDbTime();
		String tableName=RilinConstant.ITEMS;

		//查询上次上传时间
		RilinSyncTime rilinSyncTime=rilinSyncTimeMapper.selectOne(
				new LambdaQueryWrapper<RilinSyncTime>()
						.eq(RilinSyncTime::getTableName, tableName)
		);

		//如果没有上次记录，说明是第一次上传，上传表中所有数据
		if(rilinSyncTime==null || isFull){
			//分页查询
			//从第一页开始
			int current=1;
			//共上传total条
			int total=0;
			while(true){
				PageParam<Items> page=new PageParam<>();
				page.setCurrent(current);
				page.setSize(RilinConstant.PAGE_SIZE);

				//按创建时间排序
				page=itemsService.page(page
						,new LambdaQueryWrapper<Items>()
								.orderByAsc(Items::getCreatedate)
								.orderByAsc(Items::getId)
				);

				List<Items> items=page.getRecords();
				if(items.size()==0)break;

				itemsService.saveOrUpdateBatchRilin(items);

				current++;
				total+=items.size();
			}

			//记录本次上传时间
			if(rilinSyncTime==null){
				rilinSyncTime=new RilinSyncTime();
				rilinSyncTime.setTableName(tableName);
				rilinSyncTime.setLastSyncTime(currentDate);
				rilinSyncTime.setCreatedate(currentDate);
				rilinSyncTimeMapper.insert(rilinSyncTime);
			}else{
				rilinSyncTime.setLastSyncTime(currentDate);
				rilinSyncTime.setModifydate(currentDate);
				rilinSyncTimeMapper.updateById(rilinSyncTime);
			}

			//记录上传结果
			recordSuccess(tableName,null,currentDate,"上传成功，共上传"+total+"条");

		//如果有上次记录，从上次的上传时间开始上传
		}else{
			//按修改时间查询
			Date startTime=rilinSyncTime.getLastSyncTime();
			List<Items> items = itemsService.list(
					new LambdaQueryWrapper<Items>()
							.ge(Items::getModifydate,startTime)
							.or()
							.ge(Items::getCreatedate,startTime)
			);
			if(items.size()==0)return;

			itemsService.saveOrUpdateBatchRilin(items);

			//更新本次上传时间
			rilinSyncTime.setLastSyncTime(currentDate);
			rilinSyncTime.setModifydate(currentDate);
			rilinSyncTimeMapper.updateById(rilinSyncTime);

			//记录上传结果
			recordSuccess(tableName,startTime,currentDate,"上传成功，共上传"+items.size()+"条");
		}
	}

	/**
	 * 上传sys_user
	 */
	@Override
	public void syncUser(boolean isFull) {
		//记录当前时间
		Date currentDate=rilinSyncTimeMapper.getCurrentDbTime();
		String tableName=RilinConstant.USER;

		//查询上次上传时间
		RilinSyncTime rilinSyncTime=rilinSyncTimeMapper.selectOne(
				new LambdaQueryWrapper<RilinSyncTime>()
						.eq(RilinSyncTime::getTableName, tableName)
		);

		//如果没有上次记录，说明是第一次上传，上传表中所有数据
		if(rilinSyncTime==null || isFull){
			//分页查询
			//从第一页开始
			int current=1;
			//共上传total条
			int total=0;
			while(true){
				PageParam<RilinSysUser> page=new PageParam<>();
				page.setCurrent(current);
				page.setSize(RilinConstant.PAGE_SIZE);

				//按创建时间排序
				page=rilinSysUserService.page(page
						,new LambdaQueryWrapper<RilinSysUser>()
								.orderByAsc(RilinSysUser::getCreateTime)
								.orderByAsc(RilinSysUser::getUserId)
				);

				List<RilinSysUser> sysUsers=page.getRecords();
				if(sysUsers.size()==0)break;

				rilinSysUserService.saveOrUpdateBatchRilin(sysUsers);

				current++;
				total+=sysUsers.size();
			}

			//记录本次上传时间
			if(rilinSyncTime==null){
				rilinSyncTime=new RilinSyncTime();
				rilinSyncTime.setTableName(tableName);
				rilinSyncTime.setLastSyncTime(currentDate);
				rilinSyncTime.setCreatedate(currentDate);
				rilinSyncTimeMapper.insert(rilinSyncTime);
			}else{
				rilinSyncTime.setLastSyncTime(currentDate);
				rilinSyncTime.setModifydate(currentDate);
				rilinSyncTimeMapper.updateById(rilinSyncTime);
			}

			//记录上传结果
			recordSuccess(tableName,null,currentDate,"上传成功，共上传"+total+"条");

			//如果有上次记录，从上次的上传时间开始上传
		}else{
			//按修改时间查询
			Date startTime=rilinSyncTime.getLastSyncTime();
			List<RilinSysUser> sysUsers = rilinSysUserService.list(
					new LambdaQueryWrapper<RilinSysUser>()
							.ge(RilinSysUser::getUpdateTime,startTime)
							.or()
							.ge(RilinSysUser::getCreateTime,startTime)
			);
			if(sysUsers.size()==0)return;

			rilinSysUserService.saveOrUpdateBatchRilin(sysUsers);

			//更新本次上传时间
			rilinSyncTime.setLastSyncTime(currentDate);
			rilinSyncTime.setModifydate(currentDate);
			rilinSyncTimeMapper.updateById(rilinSyncTime);

			//记录上传结果
			recordSuccess(tableName,startTime,currentDate,"上传成功，共上传"+sysUsers.size()+"条");
		}
	}

	/**
	 * 上传md_inspect_charge
	 */
	@Override
	public void syncInspectCharge(boolean isFull) {
		//记录当前时间
		Date currentDate=rilinSyncTimeMapper.getCurrentDbTime();
		String tableName=RilinConstant.INSPECT_CHARGE;

		//查询上次上传时间
		RilinSyncTime rilinSyncTime=rilinSyncTimeMapper.selectOne(
				new LambdaQueryWrapper<RilinSyncTime>()
						.eq(RilinSyncTime::getTableName, tableName)
		);

		//如果没有上次记录，说明是第一次上传，上传表中所有数据
		if(rilinSyncTime==null || isFull){
			//分页查询
			//从第一页开始
			int current=1;
			//共上传total条
			int total=0;
			while(true){
				PageParam<InspectCharge> page=new PageParam<>();
				page.setCurrent(current);
				page.setSize(RilinConstant.PAGE_SIZE);

				//按创建时间排序
				page=inspectChargeService.page(page
						,new LambdaQueryWrapper<InspectCharge>()
								.orderByAsc(InspectCharge::getCreatedate)
								.orderByAsc(InspectCharge::getId)
				);

				List<InspectCharge> inspectCharges=page.getRecords();
				if(inspectCharges.size()==0)break;

				inspectChargeService.saveOrUpdateBatchRilin(inspectCharges);

				current++;
				total+=inspectCharges.size();
			}

			//记录本次上传时间
			if(rilinSyncTime==null){
				rilinSyncTime=new RilinSyncTime();
				rilinSyncTime.setTableName(tableName);
				rilinSyncTime.setLastSyncTime(currentDate);
				rilinSyncTime.setCreatedate(currentDate);
				rilinSyncTimeMapper.insert(rilinSyncTime);
			}else{
				rilinSyncTime.setLastSyncTime(currentDate);
				rilinSyncTime.setModifydate(currentDate);
				rilinSyncTimeMapper.updateById(rilinSyncTime);
			}

			//记录上传结果
			recordSuccess(tableName,null,currentDate,"上传成功，共上传"+total+"条");

		//如果有上次记录，从上次的上传时间开始上传
		}else{
			//按修改时间查询
			Date startTime=rilinSyncTime.getLastSyncTime();
			List<InspectCharge> inspectCharges = inspectChargeService.list(
					new LambdaQueryWrapper<InspectCharge>()
							.ge(InspectCharge::getModifydate,startTime)
							.or()
							.ge(InspectCharge::getCreatedate,startTime)
			);
			if(inspectCharges.size()==0)return;

			inspectChargeService.saveOrUpdateBatchRilin(inspectCharges);

			//更新本次上传时间
			rilinSyncTime.setLastSyncTime(currentDate);
			rilinSyncTime.setModifydate(currentDate);
			rilinSyncTimeMapper.updateById(rilinSyncTime);

			//记录上传结果
			recordSuccess(tableName,startTime,currentDate,"上传成功，共上传"+inspectCharges.size()+"条");
		}
	}

	/**
	 * 上传md_basexamltem
	 */
	@Override
	public void syncBasexamltem(boolean isFull) {
		//记录当前时间
		Date currentDate=rilinSyncTimeMapper.getCurrentDbTime();
		String tableName=RilinConstant.BASEXAMLTEM;

		//查询上次上传时间
		RilinSyncTime rilinSyncTime=rilinSyncTimeMapper.selectOne(
				new LambdaQueryWrapper<RilinSyncTime>()
						.eq(RilinSyncTime::getTableName, tableName)
		);

		//如果没有上次记录，说明是第一次上传，上传表中所有数据
		if(rilinSyncTime==null || isFull){
			//分页查询
			//从第一页开始
			int current=1;
			//共上传total条
			int total=0;
			while(true){
				PageParam<Basexamltem> page=new PageParam<>();
				page.setCurrent(current);
				page.setSize(RilinConstant.PAGE_SIZE);

				//按创建时间排序
				page=basexamltemService.page(page
						,new LambdaQueryWrapper<Basexamltem>()
								.orderByAsc(Basexamltem::getCreatedate)
								.orderByAsc(Basexamltem::getId)
				);

				List<Basexamltem> basexamltems=page.getRecords();
				if(basexamltems.size()==0)break;

				basexamltemService.saveOrUpdateBatchRilin(basexamltems);

				current++;
				total+=basexamltems.size();
			}

			//记录本次上传时间
			if(rilinSyncTime==null){
				rilinSyncTime=new RilinSyncTime();
				rilinSyncTime.setTableName(tableName);
				rilinSyncTime.setLastSyncTime(currentDate);
				rilinSyncTime.setCreatedate(currentDate);
				rilinSyncTimeMapper.insert(rilinSyncTime);
			}else{
				rilinSyncTime.setLastSyncTime(currentDate);
				rilinSyncTime.setModifydate(currentDate);
				rilinSyncTimeMapper.updateById(rilinSyncTime);
			}

			//记录上传结果
			recordSuccess(tableName,null,currentDate,"上传成功，共上传"+total+"条");

		//如果有上次记录，从上次的上传时间开始上传
		}else{
			//按修改时间查询
			Date startTime=rilinSyncTime.getLastSyncTime();
			List<Basexamltem> basexamltems = basexamltemService.list(
					new LambdaQueryWrapper<Basexamltem>()
							.ge(Basexamltem::getModifydate,startTime)
							.or()
							.ge(Basexamltem::getCreatedate,startTime)
			);
			if(basexamltems.size()==0)return;

			basexamltemService.saveOrUpdateBatchRilin(basexamltems);

			//更新本次上传时间
			rilinSyncTime.setLastSyncTime(currentDate);
			rilinSyncTime.setModifydate(currentDate);
			rilinSyncTimeMapper.updateById(rilinSyncTime);

			//记录上传结果
			recordSuccess(tableName,startTime,currentDate,"上传成功，共上传"+basexamltems.size()+"条");
		}

	}

	/**
	 * 记录上传失败
	 */
	@Override
	public void recordFailed(String errorMsg,String tableName){
		RilinSyncResult rilinSyncResult=new RilinSyncResult();
		Date currentDate=new Date();
		rilinSyncResult.setIsSuccess(0);
		rilinSyncResult.setErrorMsg(errorMsg);
		rilinSyncResult.setTableName(tableName);
		rilinSyncResult.setEndTime(currentDate);
		rilinSyncResult.setRemarks(RilinConstant.REMARK_FAILED);
		rilinSyncResult.setCreatedate(currentDate);
		rilinSyncResultMapper.insert(rilinSyncResult);
	}

	/**
	 * 记录上传成功
	 */
	@Override
	public void recordSuccess(String tableName, Date startTime,Date endTime,String remarks){
		RilinSyncResult rilinSyncResult=new RilinSyncResult();
		rilinSyncResult.setIsSuccess(1);
		rilinSyncResult.setTableName(tableName);
		rilinSyncResult.setStartTime(startTime);
		rilinSyncResult.setEndTime(endTime);
		rilinSyncResult.setRemarks(remarks);
		rilinSyncResult.setCreatedate(endTime);
		rilinSyncResultMapper.insert(rilinSyncResult);
	}

}
