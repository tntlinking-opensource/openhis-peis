package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TotalDoctorMapper;
import com.center.medical.datamove.oracle.bean.model.TotalDoctor;
import com.center.medical.datamove.oracle.service.TotalDoctorService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (TotalDoctor)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:35
 */
@Slf4j
@Service("totalDoctorService")
@RequiredArgsConstructor
public class TotalDoctorServiceImpl extends ServiceImpl<TotalDoctorMapper, TotalDoctor> implements TotalDoctorService {

    private final TotalDoctorMapper totalDoctorMapper;

    /**
     * 分页查询[]列表
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


