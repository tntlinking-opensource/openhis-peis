package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.ShReportMapper;
import com.center.medical.bean.model.ShReport;
import com.center.medical.service.ShReportService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 自助机-报告打印 操作记录(ShReport)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:25
 */
@Slf4j
@Service("shReportService")
@RequiredArgsConstructor
public class ShReportServiceImpl extends ServiceImpl<ShReportMapper, ShReport> implements ShReportService {

    private final ShReportMapper shReportMapper;

    /**
     * 分页查询[自助机-报告打印 操作记录]列表
     *
     * @param page  分页参数
     * @param param ShReport查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ShReport> getList(PageParam<ShReport> page, ShReport param) {
        return shReportMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public ShReport getInfoById(String id) {
        return shReportMapper.getInfoById(id);
    }

}

