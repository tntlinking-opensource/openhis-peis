package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.report.bean.param.PeispatientParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.vo.PeispatientVo;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:51
 */
public interface ProfessionReportService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表职业报告数据
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeispatientVo> getPage(PageParam<Peispatient> page, PeispatientParam param);


    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 增加打印次数
     *
     * @param patientcode,diseaseHealth 体检号，类型
     * @return 详情信息
     */
    Boolean append(String patientcode, Integer diseaseHealth);
}

