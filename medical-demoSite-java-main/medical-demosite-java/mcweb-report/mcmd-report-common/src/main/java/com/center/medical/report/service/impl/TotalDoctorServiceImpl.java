package com.center.medical.report.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.TotalDoctor;
import com.center.medical.report.dao.TotalDoctorMapper;
import com.center.medical.report.service.TotalDoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 总检-医生 关联表(TotalDoctor)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-12-08 17:32:58
 */
@Slf4j
@Service("totalDoctorService")
@RequiredArgsConstructor
public class TotalDoctorServiceImpl extends ServiceImpl<TotalDoctorMapper, TotalDoctor> implements TotalDoctorService {

    private final TotalDoctorMapper totalDoctorMapper;

    /**
     * 分页查询[总检-医生 关联表]列表
     *
     * @param page  分页参数
     * @param param TotalDoctor查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TotalDoctor> getPage(PageParam<TotalDoctor> page, TotalDoctor param) {
        return totalDoctorMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public TotalDoctor getInfoById(String id) {
        return totalDoctorMapper.getInfoById(id);
    }

}

