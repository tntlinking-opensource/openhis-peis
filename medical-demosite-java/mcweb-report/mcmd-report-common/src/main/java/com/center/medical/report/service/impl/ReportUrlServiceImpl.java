package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.ReportUrl;
import com.center.medical.report.dao.ReportUrlMapper;
import com.center.medical.report.service.ReportUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BG科室报告目录表(ReportUrl)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-23 17:11:15
 */
@Slf4j
@Service("reportUrlService")
@RequiredArgsConstructor
public class ReportUrlServiceImpl extends ServiceImpl<ReportUrlMapper, ReportUrl> implements ReportUrlService {

    private final ReportUrlMapper reportUrlMapper;

    /**
     * 分页查询[BG科室报告目录表]列表
     *
     * @param page  分页参数
     * @param param ReportUrl查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ReportUrl> getPage(PageParam<ReportUrl> page, ReportUrl param) {
        return reportUrlMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ReportUrl getInfoById(String id) {
        return reportUrlMapper.getInfoById(id);
    }

    @Override
    public List<ReportUrl> getList(String patientcode, int isHead, int diseaseHealth) {
        return reportUrlMapper.getList(patientcode,isHead,diseaseHealth);
    }

}

