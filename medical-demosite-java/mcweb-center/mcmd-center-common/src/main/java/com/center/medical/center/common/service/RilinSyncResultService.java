package com.center.medical.center.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.center.common.bean.model.RilinSyncResult;

import java.util.Date;

/**
 * 对接瑞林萨尔健康管理系统,记录数据上传结果(RilinSyncResult)服务接口
 * @since 2025-03-21 14:46:36
 */
public interface RilinSyncResultService extends IService<RilinSyncResult> {

	/**
	 * 实时上传基础数据
	 */
	void syncBasicIncremental();

	/**
	 * 全量上传基础数据
	 */
	void syncBasicFull();

	/**
	 * 上传基础数据
	 * @param isFull
	 */
	void syncBasic(boolean isFull);

	/**
	 * 上传crm_sellcustomer
	 */
	void syncSellcustomer(boolean isFull);

	/**
	 * 上传md_items
	 */
	void syncItems(boolean isFull);

	/**
	 * 上传sys_user
	 */
	void syncUser(boolean isFull);

	/**
	 * 上传md_inspect_charge
	 */
	void syncInspectCharge(boolean isFull);

	/**
	 * 上传md_basexamltem
	 */
	void syncBasexamltem(boolean isFull);

	/**
	 * 记录上传失败
	 */
	void recordFailed(String errorMsg,String tableName);

	/**
	 * 记录上传成功
	 */
	void recordSuccess(String tableName, Date startTime,Date endTime,String remarks);
}
