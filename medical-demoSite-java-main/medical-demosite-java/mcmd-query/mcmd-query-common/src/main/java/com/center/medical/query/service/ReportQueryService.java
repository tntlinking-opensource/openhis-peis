package com.center.medical.query.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.ReportQueryParam;
import com.center.medical.query.bean.vo.ReportQueryVo;

import java.util.List;

/**
 * 报告信息查询(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
public interface ReportQueryService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReportQueryVo> getList(PageParam<ReportQueryVo> page, ReportQueryParam param);


    /**
     * 导出报告信息
     *
     * @param param
     * @return
     */
    List<ReportQueryVo> getExportData(ReportQueryParam param);

    /**
     * 保存发放方式
     *
     * @param ids
     * @param idInformway
     * @return
     */
    Boolean saveConsumex(List<String> ids, String idInformway);
}

