package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.report.bean.param.PeispatientParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.vo.PeispatientVo;

import java.util.List;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:51
 */
public interface HealthReportService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表健康报告数据
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeispatientVo> getPage(PageParam<Peispatient> page, PeispatientParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param reportIds 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String reportIds);

    /**
     * 增加打印次数
     *
     * @param patientcode,diseaseHealth 体检号，类型
     * @return 详情信息
     */
    Boolean append(String patientcode,Integer diseaseHealth);


    /**
     * 健康报告，未交接的，报告发放方式为电子版报告，点击电子报告处理，直接已交接。
     */
    void electronicReportHandover(String ids);

    /**
     * 终审交接查询
     *
     * @param patientcodes     体检号
     * @return 所有数据
     */
    List<Peispatient> searchZsjjCode(String patientcodes);

    /**
     * 终审交接保存
     *
     * @param ids     id,逗号隔开
     * @return 所有数据
     */
    Boolean saveZsjj(String ids);
}

