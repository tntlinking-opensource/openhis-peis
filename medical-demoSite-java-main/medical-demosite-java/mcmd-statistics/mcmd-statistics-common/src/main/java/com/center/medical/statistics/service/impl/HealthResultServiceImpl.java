package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.HealthResultParam;
import com.center.medical.statistics.bean.vo.HealthResultVo;
import com.center.medical.statistics.dao.HealthResultMapper;
import com.center.medical.statistics.service.HealthResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * QT体检者表(Peispatient)服务实现类
 *
 * @author ay
 * @since 2023-10-25 09:06:03
 */
@Slf4j
@Service("healthResultService")
@RequiredArgsConstructor
public class HealthResultServiceImpl extends ServiceImpl<HealthResultMapper, Peispatient> implements HealthResultService {

    private final HealthResultMapper healthResultMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<HealthResultVo> getPage(PageParam<HealthResultVo> page, HealthResultParam param) {
        return healthResultMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return healthResultMapper.getInfoById(id);
    }


    /**
     * 导出健康检查结果附表
     * @param param
     * @return
     */
    @Override
    public List<HealthResultVo> getExportData(HealthResultParam param) {
        List<HealthResultVo> list = healthResultMapper.getExportData(param);
        for (int i = 0; i < list.size(); i++) {
            HealthResultVo healthResultVo = list.get(i);
            healthResultVo.setRownum(i+1);
        }
        return list;
    }
}

