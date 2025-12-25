package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.CustomerStatisticsParam;
import com.center.medical.statistics.bean.param.PhysicalExaminerParam;
import com.center.medical.statistics.bean.vo.CustomerStatisticsVo;
import com.center.medical.statistics.bean.vo.PhysicalExaminerVo;
import com.center.medical.statistics.dao.CustomerStatisticsMapper;
import com.center.medical.statistics.service.CustomerStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 来检客户统计(Peispatient)服务实现类
 *
 * @author ay
 * @since 2023-12-18 15:52:58
 */
@Slf4j
@Service("customerStatistics")
@RequiredArgsConstructor
public class CustomerStatisticsServiceImpl extends ServiceImpl<CustomerStatisticsMapper, Peispatient> implements CustomerStatisticsService {

    private final CustomerStatisticsMapper customerStatisticsMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CustomerStatisticsVo> getPage(PageParam<CustomerStatisticsVo> page, CustomerStatisticsParam param) {
        return customerStatisticsMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return customerStatisticsMapper.getInfoById(id);
    }

    /**
     * 获取体检者
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<PhysicalExaminerVo> getPhysicalExaminer(PageParam<PhysicalExaminerVo> page, PhysicalExaminerParam param) {
        return customerStatisticsMapper.getPhysicalExaminer(page,param);
    }


    /**
     * 导出订单信息
     * @param param
     * @return
     */
    @Override
    public List<CustomerStatisticsVo> exportOrg(CustomerStatisticsParam param) {
        return customerStatisticsMapper.exportOrg(param);
    }


    /**
     * 导出人员信息
     * @param param
     * @return
     */
    @Override
    public List<PhysicalExaminerVo> exportPer(PhysicalExaminerParam param) {
        return customerStatisticsMapper.exportPer(param);
    }
}

