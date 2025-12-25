package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.OtherReportMapper;
import com.center.medical.bean.model.OtherReport;
import com.center.medical.service.OtherReportService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 其他报告(OtherReport)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:48
 */
@Slf4j
@Service("otherReportService")
@RequiredArgsConstructor
public class OtherReportServiceImpl extends ServiceImpl<OtherReportMapper, OtherReport> implements OtherReportService {

    private final OtherReportMapper otherReportMapper;

    /**
     * 分页查询[其他报告]列表
     *
     * @param page  分页参数
     * @param param OtherReport查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OtherReport> getList(PageParam<OtherReport> page, OtherReport param) {
        return otherReportMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public OtherReport getInfoById(String id) {
        return otherReportMapper.getInfoById(id);
    }

    /**
     * 根据体检号和类型查询
     * @param patientcode
     * @param type
     * @return
     */
    @Override
    public OtherReport getInfoByCode(String patientcode, String type) {
        return otherReportMapper.getInfoByCode(patientcode,type);
    }

}

