package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.EducationMapper;
import com.center.medical.bean.model.Education;
import com.center.medical.service.EducationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 教育程度(Education)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:35
 */
@Slf4j
@Service("educationService")
@RequiredArgsConstructor
public class EducationServiceImpl extends ServiceImpl<EducationMapper, Education> implements EducationService {

    private final EducationMapper educationMapper;

    /**
     * 分页查询[教育程度]列表
     *
     * @param page  分页参数
     * @param param Education查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Education> getList(PageParam<Education> page, Education param) {
        return educationMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Education getInfoById(String id) {
        return educationMapper.getInfoById(id);
    }

    ;

}

