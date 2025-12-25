package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdOtherReportMapper;
import com.center.medical.datamove.common.bean.model.MdOtherReport;
import com.center.medical.datamove.common.service.MdOtherReportService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 其他报告(MdOtherReport)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:30
 */
@Slf4j
@Service("mdOtherReportService")
@RequiredArgsConstructor
public class MdOtherReportServiceImpl extends ServiceImpl<MdOtherReportMapper, MdOtherReport> implements MdOtherReportService {

    private final MdOtherReportMapper mdOtherReportMapper;

    /**
     * 分页查询[其他报告]列表
     *
     * @param page  分页参数
     * @param param MdOtherReport查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdOtherReport> getPage(PageParam<MdOtherReport> page, MdOtherReport param) {
        return mdOtherReportMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdOtherReport getInfoById(String id) {
        return mdOtherReportMapper.getInfoById(id);
    }

}


