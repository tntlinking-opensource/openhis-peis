package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdShReportMapper;
import com.center.medical.datamove.common.bean.model.MdShReport;
import com.center.medical.datamove.common.service.MdShReportService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 自助机-报告打印 操作记录(MdShReport)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:40
 */
@Slf4j
@Service("mdShReportService")
@RequiredArgsConstructor
public class MdShReportServiceImpl extends ServiceImpl<MdShReportMapper, MdShReport> implements MdShReportService {

    private final MdShReportMapper mdShReportMapper;

    /**
     * 分页查询[自助机-报告打印 操作记录]列表
     *
     * @param page  分页参数
     * @param param MdShReport查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdShReport> getPage(PageParam<MdShReport> page, MdShReport param) {
        return mdShReportMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdShReport getInfoById(String id) {
        return mdShReportMapper.getInfoById(id);
    }

}


