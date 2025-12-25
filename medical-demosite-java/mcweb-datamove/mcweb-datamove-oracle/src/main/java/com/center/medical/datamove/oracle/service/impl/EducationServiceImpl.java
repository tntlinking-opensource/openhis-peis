package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.EducationMapper;
import com.center.medical.datamove.oracle.bean.model.Education;
import com.center.medical.datamove.oracle.service.EducationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC教育程度(Education)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:34
 */
@Slf4j
@Service("educationService")
@RequiredArgsConstructor
public class EducationServiceImpl extends ServiceImpl<EducationMapper, Education> implements EducationService {

    private final EducationMapper educationMapper;

    /**
     * 分页查询[JC教育程度]列表
     *
     * @param page  分页参数
     * @param param Education查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Education> getPage(PageParam<Education> page, Education param) {
        return educationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Education getInfoById(String id) {
        return educationMapper.getInfoById(id);
    }

}


