package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.InspectReport;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.param.IRParam;
import com.center.medical.report.bean.param.inspectReportsParam;
import com.center.medical.report.bean.vo.IRPageVo;
import com.center.medical.report.bean.vo.InfoListDataVo;

/**
 * 检验报告生成记录(InspectReport)表服务接口
 *
 * @author ay
 * @since 2023-07-11 09:27:32
 */
public interface InspectReportsService extends IService<InspectReport> {

    /**
     * 分页查询[检验报告生成记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<IRPageVo> getList(PageParam<IRPageVo> page, IRParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    InspectReport getInfoById(String id);

    /**
     * 获取右侧详情
     * @param page
     * @param patientcode
     * @return
     */
    IPage<InfoListDataVo> getInfoListData(PageParam<InfoListDataVo> page, String patientcode);


    /**
     * 生成检验报告
     * @param param
     * @return
     */
    Boolean create(inspectReportsParam param);
}

