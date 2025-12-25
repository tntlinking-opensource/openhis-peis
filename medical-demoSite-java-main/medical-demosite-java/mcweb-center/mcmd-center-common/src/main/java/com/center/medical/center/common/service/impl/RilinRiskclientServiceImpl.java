package com.center.medical.center.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.center.common.bean.model.RilinSyncResult;
import com.center.medical.center.common.bean.model.RilinSyncTime;
import com.center.medical.center.common.constant.RilinConstant;
import com.center.medical.center.common.dao.RilinSyncResultMapper;
import com.center.medical.center.common.dao.RilinSyncTimeMapper;
import com.center.medical.center.common.service.RilinRiskclientService;
import com.center.medical.center.common.util.RilinUtil;
import com.center.medical.common.config.RilinConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Riskclient;
import com.center.medical.sellcrm.bean.model.Riskclientcon;
import com.center.medical.sellcrm.dao.RiskclientMapper;
import com.center.medical.sellcrm.service.RiskclientService;
import com.center.medical.sellcrm.service.RiskclientconService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 对接瑞林萨尔健康管理系统,重大阳性
 * @since 2025-03-25 08:29:24
 */
@Slf4j
@Service("rilinRiskclientService")
@RequiredArgsConstructor
public class RilinRiskclientServiceImpl extends ServiceImpl<RiskclientMapper, Riskclient> implements RilinRiskclientService {

	private final ISysConfigService iSysConfigService;
	private final RilinSyncTimeMapper rilinSyncTimeMapper;
	private final RiskclientconService riskclientconService;
	private final RilinSyncResultMapper rilinSyncResultMapper;
	private final RiskclientService riskclientService;//不能调用this的方法切换数据源

	/**
	 * 实时上传，重大阳性
	 */
	@Override
	public void sync(){
		//读取sys_config,判断是否开启上传功能
		RilinConfig rilinConfig=iSysConfigService.getSysConfigObject(Constants.RILIN_CONFIG, RilinConfig.class);
		if(rilinConfig==null || rilinConfig.getIsBasicOpen()==null || !rilinConfig.getIsRiskOpen().booleanValue())return;
		if(!RilinUtil.isTaskAuthorizedIp(rilinConfig))return;
		
		//记录当前时间
		Date currentDate=rilinSyncTimeMapper.getCurrentDbTime();
		String tableName= RilinConstant.RISK;
		//查询上次上传时间
		RilinSyncTime rilinSyncTime=rilinSyncTimeMapper.selectOne(
				new LambdaQueryWrapper<RilinSyncTime>()
						.eq(RilinSyncTime::getTableName, tableName)
		);

		//如果没有上次记录，说明是第一次上传，上传表中所有数据
		if(rilinSyncTime==null ){
			//分页查询
			//从第一页开始
			int current=1;
			//md_riskclient共上传total条
			int total=0;
			while(true){
				PageParam<Riskclient> page=new PageParam<>();
				page.setCurrent(current);
				page.setSize(RilinConstant.PAGE_SIZE);

				//按创建时间排序
				page=riskclientService.page(page
						,new LambdaQueryWrapper<Riskclient>()
								.orderByAsc(Riskclient::getCreatedate)
								.orderByAsc(Riskclient::getId)
				);

				List<Riskclient> riskclients=page.getRecords();
				if(riskclients.size()==0)break;

				riskclientService.saveOrUpdateBatchRilin(riskclients);

				current++;
				total+=riskclients.size();
			}

			//分页查询
			//从第一页开始
			current=1;
			//md_riskclientcon共上传total条
			int conTotal=0;
			while(true){
				PageParam<Riskclientcon> page=new PageParam<>();
				page.setCurrent(current);
				page.setSize(RilinConstant.PAGE_SIZE);

				//按创建时间排序
				page=riskclientconService.page(page
						,new LambdaQueryWrapper<Riskclientcon>()
								.orderByAsc(Riskclientcon::getCreatedate)
								.orderByAsc(Riskclientcon::getId)
				);

				List<Riskclientcon> riskclientcons=page.getRecords();
				if(riskclientcons.size()==0)break;

				riskclientconService.saveOrUpdateBatchRilin(riskclientcons);

				current++;
				conTotal+=riskclientcons.size();
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
			RilinSyncResult rilinSyncResult=new RilinSyncResult();
			rilinSyncResult.setIsSuccess(1);
			rilinSyncResult.setTableName(tableName);
			rilinSyncResult.setEndTime(currentDate);
			rilinSyncResult.setRemarks("上传成功，md_riskclient上传"+total+"条，md_riskclientcon上传"+conTotal+"条。");
			rilinSyncResult.setCreatedate(currentDate);
			rilinSyncResultMapper.insert(rilinSyncResult);

		//如果有上次记录，从上次的上传时间开始上传
		}else{
			//按修改时间查询
			Date startTime=rilinSyncTime.getLastSyncTime();
			List<Riskclient> riskclients = riskclientService.list(
					new LambdaQueryWrapper<Riskclient>()
							.ge(Riskclient::getModifydate,startTime)
							.or()
							.ge(Riskclient::getCreatedate,startTime)
			);
			List<Riskclientcon> riskclientcons = riskclientconService.list(
					new LambdaQueryWrapper<Riskclientcon>()
							.ge(Riskclientcon::getModifydate,startTime)
							.or()
							.ge(Riskclientcon::getCreatedate,startTime)
			);
			int total=riskclients.size();
			int conTotal=riskclientcons.size();
			if(total==0 && conTotal==0)return;

			if(total>0)riskclientService.saveOrUpdateBatchRilin(riskclients);
			if(conTotal>0)riskclientconService.saveOrUpdateBatchRilin(riskclientcons);

			//更新本次上传时间
			rilinSyncTime.setLastSyncTime(currentDate);
			rilinSyncTime.setModifydate(currentDate);
			rilinSyncTimeMapper.updateById(rilinSyncTime);

			//记录上传结果
			RilinSyncResult rilinSyncResult=new RilinSyncResult();
			rilinSyncResult.setIsSuccess(1);
			rilinSyncResult.setTableName(tableName);
			rilinSyncResult.setStartTime(startTime);
			rilinSyncResult.setEndTime(currentDate);
			rilinSyncResult.setRemarks("上传成功，md_riskclient上传"+total+"条，md_riskclientcon上传"+conTotal+"条。");
			rilinSyncResult.setCreatedate(currentDate);
			rilinSyncResultMapper.insert(rilinSyncResult);
		}



	}
}
