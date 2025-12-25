package com.center.medical.center.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.center.common.bean.model.RilinSyncTime;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 对接瑞林萨尔健康管理系统，用于记录上一次上传数据的止时间。(RilinSyncTime)数据库访问层
 * @since 2025-03-21 14:53:05
 */
public interface RilinSyncTimeMapper extends BaseMapper<RilinSyncTime> {

	/**
	 * 查询数据库时间
	 */
	Date getCurrentDbTime();

	/**
	 * 按修改时间段，查询有体检者的日期
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Date> selectPatientDateList(@Param("startDate")Date startDate,@Param("endDate")Date endDate);

	/**
	 * 按修改时间段，查询所有体检者ID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<String> selectPatientIdList(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
}
