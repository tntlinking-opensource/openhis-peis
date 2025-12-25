package com.center.medical.dicom.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.dicom.bean.model.FailedDicomFile;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;
/**
 * 接收失败的dicom文件(FailedDicomFile)数据库访问层
 *
 * @author makejava
 * @since 2023-09-12 14:34:44
 */
public interface FailedDicomFileMapper extends BaseMapper<FailedDicomFile> {

	/**
	* 分页查询[接收失败的dicom文件]列表
	*
	* @param page 分页参数
	* @param param FailedDicomFile查询参数
	* @return 分页数据
	*/
	IPage<FailedDicomFile> getPage(PageParam<FailedDicomFile> page, @Param("param") FailedDicomFile param);

		/**
	* 根据主键id获取记录详情
	*
	* @param id 主键id
	* @return 详情信息
	*/
		FailedDicomFile getInfoById(@Param("id") String id);
	
}
