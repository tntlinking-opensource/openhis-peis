package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.GeneralCheckingParam;
import com.center.medical.statistics.bean.vo.AnalyseTotalVo;
import com.center.medical.statistics.bean.vo.GeneralCheckingVo;

import javax.servlet.http.HttpServletResponse;

/**
 * 总检工作量(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-19 18:07:47
 */
public interface GeneralCheckingService extends IService<Peispatient> {

    /**
    * 分页查询[QT体检者表]列表
    *
    * @param page 分页参数
    * @param param 查询参数
    * @return 分页数据
    */
    IPage<GeneralCheckingVo> getList(PageParam<GeneralCheckingVo> page, GeneralCheckingParam param);


    /**
     * 总检医生工作量统计汇总
     * @param page
     * @param param
     * @return
     */
    IPage<AnalyseTotalVo> analyseTotal(PageParam<AnalyseTotalVo> page, GeneralCheckingParam param);

    /**
     * 导出Excel
     * @param response
     * @param param
     */
    void getExportData(HttpServletResponse response, GeneralCheckingParam param);
}

