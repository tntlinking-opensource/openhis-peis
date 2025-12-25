package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.ReportDefaultDoctor;
import com.center.medical.report.bean.param.DefaultDoctorPageParam;
import com.center.medical.report.bean.param.RDDoctorSaOrUpParam;
import com.center.medical.report.bean.vo.ReportDefaultDoctorVo;

/**
 * 报告中科室默认医生(ReportDefaultDoctor)服务接口
 *
 * @author ay
 * @since 2024-08-21 16:54:18
 */
public interface ReportDefaultDoctorService extends IService<ReportDefaultDoctor> {

    /**
     * 分页查询[报告中科室默认医生]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReportDefaultDoctorVo> getPage(PageParam<ReportDefaultDoctorVo> page, DefaultDoctorPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReportDefaultDoctor getInfoById(String id);

    /**
     * 通过科室id和类型查询
     * @param ksId
     * @param type
     * @return
     */
    String getinfoByKsIdAndType(String ksId, int type, int personnelType);

    /**
     * 添加或更新数据
     * @param param
     * @return
     */
    boolean saOrUp(RDDoctorSaOrUpParam param);
}

