package com.center.medical.center.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.center.common.bean.model.RilinSysUser;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 用户信息表(SysUser)服务接口
 *
 * @author makejava
 * @since 2025-03-23 09:15:02
 */
public interface RilinSysUserService extends IService<RilinSysUser> {

	/**
	* 分页查询[用户信息表]列表
	*
	* @param page 分页参数
	* @param param 查询参数
	* @return 分页数据
	*/
	IPage<RilinSysUser> getPage(PageParam<RilinSysUser> page, RilinSysUser param);

	/**
	* 根据主键id获取记录详情
	*
	* @param id 主键userId
	* @return 详情信息
	*/
	RilinSysUser getInfoById(Long id);

	/**
	 * 批量修改插入 对接瑞林萨尔健康管理系统
	 *
	 * @param entityList 实体对象集合
	 */
	@DataSource(value = DataSourceType.RILIN)
	@Transactional(rollbackFor = Exception.class)
	default boolean saveOrUpdateBatchRilin(Collection<RilinSysUser> entityList) {
		return saveOrUpdateBatch(entityList, DEFAULT_BATCH_SIZE);
	}
	
}
