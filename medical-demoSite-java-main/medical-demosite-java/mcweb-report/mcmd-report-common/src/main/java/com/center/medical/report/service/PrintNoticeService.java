package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.PrintNotice;
import com.center.medical.report.bean.param.PrintNoticeParam;
import com.center.medical.report.bean.vo.PrintNoticeVo;

import java.util.List;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:51
 */
public interface PrintNoticeService extends IService<PrintNotice> {


    /**
     * 分页查询职业结果告知书
     * @param page
     * @param param
     * @return
     */
    IPage<PrintNoticeVo> getPrintNoticePage(PageParam<PrintNoticeVo> page, PrintNoticeParam param);

    /**
     * 详情
     * @param id
     * @return
     */
    PrintNotice getInfoById(String id);

    /**
     * 打印
     * @param param
     * @return
     */
    boolean print(PrintNoticeParam param);



    /**
     * 获取所有体检者信息
     * @param patientcodes
     * @param page
     * @return
     */
    List<Peispatient> findAllPeispatientByPatientcode(List<String> patientcodes, PageParam<Peispatient> page);
}

