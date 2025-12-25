package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.param.InspectionExportParam;
import com.center.medical.abteilung.bean.vo.InspectionExportVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.MdPeispatient;


import java.util.List;

/**
 * QT体检者表(MdPeispatient)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:10
 */
public interface InspectionExportService extends IService<MdPeispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<InspectionExportVo> getPage(PageParam<InspectionExportVo> page, InspectionExportParam param);

    /**
     * 获取检验数据导出数据
     * @param param
     * @return
     */
    List<InspectionExportVo> getExportData(InspectionExportParam param);
}

