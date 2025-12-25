package com.center.medical.abteilung.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.model.SectionAndRemind;
import com.center.medical.abteilung.dao.SectionAndRemindMapper;
import com.center.medical.abteilung.service.SectionAndRemindService;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 科室提醒和科室关联表(SectionAndRemind)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:17
 */
@Slf4j
@Service("sectionAndRemindService")
@RequiredArgsConstructor
public class SectionAndRemindServiceImpl extends ServiceImpl<SectionAndRemindMapper, SectionAndRemind> implements SectionAndRemindService {

    private final SectionAndRemindMapper sectionAndRemindMapper;

    /**
     * 分页查询[科室提醒和科室关联表]列表
     *
     * @param page  分页参数
     * @param param SectionAndRemind查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SectionAndRemind> getPage(PageParam<SectionAndRemind> page, SectionAndRemind param) {
        return sectionAndRemindMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SectionAndRemind getInfoById(String id) {
        return sectionAndRemindMapper.getInfoById(id);
    }

}

