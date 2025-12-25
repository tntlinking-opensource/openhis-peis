package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.InspectionExportParam;
import com.center.medical.abteilung.bean.vo.InspectionExportVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.MdPeispatient;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(MdPeispatient)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:10
 */
public interface InspectionExportMapper extends BaseMapper<MdPeispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatient查询参数
     * @return 分页数据
     */
    IPage<InspectionExportVo> getPage(PageParam<InspectionExportVo> page, @Param("param") InspectionExportParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatient getInfoById(@Param("id") String id);

    /**
     * 获取检验数据导出数据
     * @param param
     * @return
     */
    List<InspectionExportVo> getExportData(@Param("param") InspectionExportParam param);
}
