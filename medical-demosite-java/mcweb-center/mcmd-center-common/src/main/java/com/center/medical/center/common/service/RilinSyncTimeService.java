package com.center.medical.center.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.center.common.bean.model.RilinSyncTime;

/**
 * 对接瑞林萨尔健康管理系统，用于记录上一次上传数据的止时间。(RilinSyncTime)服务接口
 * @since 2025-03-21 14:53:06
 */
public interface RilinSyncTimeService extends IService<RilinSyncTime> {

	/**
	 * 实时同步体检结果
	 */
	void syncPatient();
}
