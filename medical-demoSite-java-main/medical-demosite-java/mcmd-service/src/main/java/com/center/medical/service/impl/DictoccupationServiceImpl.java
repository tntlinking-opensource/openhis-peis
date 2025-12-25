package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.DictoccupationMapper;
import com.center.medical.bean.model.Dictoccupation;
import com.center.medical.service.DictoccupationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 职业类型表(Dictoccupation)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:19
 */
@Slf4j
@Service("dictoccupationService")
@RequiredArgsConstructor
public class DictoccupationServiceImpl extends ServiceImpl<DictoccupationMapper, Dictoccupation> implements DictoccupationService {

    private final DictoccupationMapper dictoccupationMapper;

    /**
     * 分页查询[职业类型表]列表
     *
     * @param page  分页参数
     * @param param Dictoccupation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Dictoccupation> getList(PageParam<Dictoccupation> page, Dictoccupation param) {
        return dictoccupationMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Dictoccupation getInfoById(String id) {
        return dictoccupationMapper.getInfoById(id);
    }

    ;

}

