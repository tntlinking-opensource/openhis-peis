package com.center.medical.dicom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.dicom.bean.model.FailedDicomFile;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 接收失败的dicom文件(FailedDicomFile)服务接口
 *
 * @author makejava
 * @since 2023-09-12 14:34:44
 */
public interface FailedDicomFileService extends IService<FailedDicomFile> {

	/**
	* 分页查询[接收失败的dicom文件]列表
	*
	* @param page 分页参数
	* @param param 查询参数
	* @return 分页数据
	*/
	IPage<FailedDicomFile> getPage(PageParam<FailedDicomFile> page, FailedDicomFile param);

		/**
	* 根据主键id获取记录详情
	*
	* @param id 主键id
	* @return 详情信息
	*/
		FailedDicomFile getInfoById(String id);
	
}
