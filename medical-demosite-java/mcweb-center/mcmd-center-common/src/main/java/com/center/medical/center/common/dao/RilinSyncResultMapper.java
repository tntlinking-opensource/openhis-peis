package com.center.medical.center.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.center.common.bean.model.RilinSyncResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;
/**
 * 对接瑞林萨尔健康管理系统,记录数据上传结果(RilinSyncResult)数据库访问层
 * @since 2025-03-21 14:46:35
 */
public interface RilinSyncResultMapper extends BaseMapper<RilinSyncResult> {

	/**
	* 分页查询[对接瑞林萨尔健康管理系统,记录数据上传结果]列表
	*
	* @param page 分页参数
	* @param param RilinSyncResult查询参数
	* @return 分页数据
	*/
	IPage<RilinSyncResult> getPage(PageParam<RilinSyncResult> page, @Param("param") RilinSyncResult param);

	/**
	* 根据主键id获取记录详情
	*
	* @param id 主键id
	* @return 详情信息
	*/
	RilinSyncResult getInfoById(@Param("id") String id);
	
}
