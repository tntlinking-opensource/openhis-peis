package com.center.medical.query.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Report;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.ReportQueryParam;
import com.center.medical.query.bean.vo.ReportQueryVo;
import com.center.medical.query.dao.ReportQueryMapper;
import com.center.medical.query.service.ReportQueryService;
import com.center.medical.report.dao.ReportMapper;
import com.center.medical.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 报告信息查询(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
@Slf4j
@Service("reportQueryService")
@RequiredArgsConstructor
public class ReportQueryServiceImpl extends ServiceImpl<ReportQueryMapper, Peispatient> implements ReportQueryService {

    private final ReportQueryMapper reportQueryMapper;
    private final ReportMapper reportMapper;
    private final ReportService reportService;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReportQueryVo> getList(PageParam<ReportQueryVo> page, ReportQueryParam param) {
        if (ObjectUtils.isNotEmpty(param.getYjlx())) {
            //当前时间减七天的00点
            DateTime dateTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
            log.info(dateTime.toString());
            param.setBefore7(dateTime);
        }
        return reportQueryMapper.getList(page, param);
    }


    /**
     * 导出报告信息
     *
     * @param param
     * @return
     */
    @Override
    public List<ReportQueryVo> getExportData(ReportQueryParam param) {
        if (ObjectUtils.isNotEmpty(param.getYjlx())) {
            //当前时间减七天的00点
            DateTime dateTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
            log.info(dateTime.toString());
            param.setBefore7(dateTime);
        }
        return reportQueryMapper.getExportData(param);
    }

    /**
     * 保存发放方式
     *
     * @param ids
     * @param idInformway
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveConsumex(List<String> ids, String idInformway) {
        //查询报告
        List<Report> cos = reportMapper.selectList(new QueryWrapper<Report>().in("id", ids));
        for (Report report : cos) {
            //交接状态
            Integer status = report.getStatus();
            if (status != null && status >= 9) {
                throw new ServiceException("体检号【"
                        + report.getPatientcode() + "】报告已交接，不能修改。");
            }
            //修改发放方式
            report.setGrantId(idInformway);
        }
        //批量更新
        boolean b = reportService.updateBatchById(cos);
        return b;
    }
}

