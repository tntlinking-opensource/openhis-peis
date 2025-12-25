package com.center.medical.dicom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dicom.dao.FailedDicomFileMapper;
import com.center.medical.dicom.bean.model.FailedDicomFile;
import com.center.medical.dicom.service.FailedDicomFileService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 接收失败的dicom文件(FailedDicomFile)服务实现类
 *
 * @author makejava
 * @since 2023-09-12 14:34:44
 */
@Slf4j
@Service("failedDicomFileService")
@RequiredArgsConstructor
public class FailedDicomFileServiceImpl extends ServiceImpl<FailedDicomFileMapper, FailedDicomFile> implements FailedDicomFileService {

	private final FailedDicomFileMapper failedDicomFileMapper;

	/**
	* 分页查询[接收失败的dicom文件]列表
	*
	* @param page 分页参数
	* @param param FailedDicomFile查询参数
	* @return 分页数据
	*/
	@Override
	public IPage<FailedDicomFile> getPage(PageParam<FailedDicomFile> page, FailedDicomFile param) {
		return failedDicomFileMapper.getPage(page, param);
	}

		/**
	* 根据主键id获取记录详情
	*
	* @param id 主键id
	* @return 详情信息
	*/
	@Override
	public FailedDicomFile getInfoById(String id){
		return failedDicomFileMapper.getInfoById(id);
	};
	
}
