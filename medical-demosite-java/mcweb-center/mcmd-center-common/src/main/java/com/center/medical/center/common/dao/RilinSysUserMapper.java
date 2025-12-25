package com.center.medical.center.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.center.common.bean.model.RilinSysUser;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;
/**
 * 用户信息表(SysUser)数据库访问层
 *
 * @author makejava
 * @since 2025-03-23 09:15:02
 */
public interface RilinSysUserMapper extends BaseMapper<RilinSysUser> {

	/**
	* 分页查询[用户信息表]列表
	*
	* @param page 分页参数
	* @param param SysUser查询参数
	* @return 分页数据
	*/
	IPage<RilinSysUser> getPage(PageParam<RilinSysUser> page, @Param("param") RilinSysUser param);

	/**
	* 根据主键id获取记录详情
	*
	* @param id 主键userId
	* @return 详情信息
	*/
	RilinSysUser getInfoById(@Param("id") Long id);
	
}
