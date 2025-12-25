package com.center.medical.center.common.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.center.common.bean.model.RilinSysUser;
import com.center.medical.center.common.dao.RilinSysUserMapper;
import com.center.medical.center.common.service.RilinSysUserService;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户信息表(SysUser)服务实现类
 *
 * @author makejava
 * @since 2025-03-23 09:15:02
 */
@Slf4j
@Service("rilinSysUserService")
@RequiredArgsConstructor
public class RilinSysUserServiceImpl extends ServiceImpl<RilinSysUserMapper, RilinSysUser> implements RilinSysUserService {

	private final RilinSysUserMapper rilinSysUserMapper;

	/**
	* 分页查询[用户信息表]列表
	*
	* @param page 分页参数
	* @param param SysUser查询参数
	* @return 分页数据
	*/
	@Override
	public IPage<RilinSysUser> getPage(PageParam<RilinSysUser> page, RilinSysUser param) {
		return rilinSysUserMapper.getPage(page, param);
	}

	/**
	* 根据主键id获取记录详情
	*
	* @param id 主键userId
	* @return 详情信息
	*/
	@Override
	public RilinSysUser getInfoById(Long id){
		return rilinSysUserMapper.getInfoById(id);
	};
	
}
