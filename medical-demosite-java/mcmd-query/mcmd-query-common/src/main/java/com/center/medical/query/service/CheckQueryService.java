package com.center.medical.query.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.ConfirmOrderParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.param.IReportParam;

import java.util.List;

/**
 * 登记信息查询(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
public interface CheckQueryService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getList(PageParam<Peispatient> page, Peispatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 终审交接
     *
     * @param ids
     * @return
     */
    Boolean zsjj(List<String> ids);

    /**
     * 结案
     *
     * @param ids
     * @return
     */
    Boolean updateclose(List<String> ids);

    /**
     * 旧案召回
     *
     * @param ids
     * @return
     */
    Boolean reSaveHistory(List<String> ids);

    /**
     * 加急
     *
     * @param ids
     * @return
     */
    Boolean jiaji(List<String> ids);

    /**
     * 临时报告生成
     * @param param
     * @return
     */
    Boolean createTemp(IReportParam param);


    /**
     * 到检确认
     * @param id
     * @param captcha
     * @return
     */
    Boolean confirmOrder(ConfirmOrderParam param);
}

