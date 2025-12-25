package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.ReportReviewParam;
import com.center.medical.statistics.bean.vo.ReportReviewVo;
import com.center.medical.statistics.dao.ReportReviewMapper;
import com.center.medical.statistics.service.ReportReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生成报告内容(ReportContent)服务实现类
 *
 * @author ay
 * @since 2023-08-14 14:54:49
 */
@Slf4j
@Service("reportReviewService")
@RequiredArgsConstructor
public class ReportReviewServiceImpl extends ServiceImpl<ReportReviewMapper, ReportContent> implements ReportReviewService {

    private final ReportReviewMapper reportReviewMapper;

    /**
     * 分页查询[生成报告内容]列表
     *
     * @param page  分页参数
     * @param param ReportContent查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReportReviewVo> getPage(PageParam<ReportReviewVo> page, ReportReviewParam param) {
        return reportReviewMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReportContent getInfoById(String id) {
        return reportReviewMapper.getInfoById(id);
    }


    /**
     * 获取合计数据
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<ReportReviewVo> getTotalData(PageParam<ReportReviewVo> page, ReportReviewParam param) {
        return reportReviewMapper.getTotalData(page,param);
    }


    /**
     * 导出报告审核工作量
     * @param param
     * @return
     */
    @Override
    public List<ReportReviewVo> exportData(ReportReviewParam param) {
        List<ReportReviewVo> reportReviewVos = reportReviewMapper.exportData(param);
        for (int i = 0; i < reportReviewVos.size(); i++) {
            ReportReviewVo vo = reportReviewVos.get(i);
            vo.setRownum(i+1);
        }
        return reportReviewVos;
    }


    /**
     * 分页查询-一审
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<ReportReviewVo> getFistListData(PageParam<ReportReviewVo> page, ReportReviewParam param) {
        return reportReviewMapper.getFistListData(page,param);
    }


    /**
     * 获取合计数据-一审
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<ReportReviewVo> getFistTotalData(PageParam<ReportReviewVo> page, ReportReviewParam param) {
        return reportReviewMapper.getFistTotalData(page,param);
    }


    /**
     * 导出-一审
     * @param param
     * @return
     */
    @Override
    public List<ReportReviewVo> exportFistData(ReportReviewParam param) {
        return reportReviewMapper.exportFistData(param);
    }


    /**
     * 分页查询-二审
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<ReportReviewVo> getSecondListData(PageParam<ReportReviewVo> page, ReportReviewParam param) {
        return reportReviewMapper.getSecondListData(page,param);
    }


    /**
     * 获取合计数据-二审
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<ReportReviewVo> getSecondTotalData(PageParam<ReportReviewVo> page, ReportReviewParam param) {
        return reportReviewMapper.getSecondTotalData(page,param);
    }


    /**
     * 导出-二审
     * @param param
     * @return
     */
    @Override
    public List<ReportReviewVo> exportSecond(ReportReviewParam param) {
        return reportReviewMapper.exportSecond(param);
    }
}


