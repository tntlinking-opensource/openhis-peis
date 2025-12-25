package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DictoccupationMapper;
import com.center.medical.datamove.oracle.bean.model.Dictoccupation;
import com.center.medical.datamove.oracle.service.DictoccupationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业表(Dictoccupation)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:14
 */
@Slf4j
@Service("dictoccupationService")
@RequiredArgsConstructor
public class DictoccupationServiceImpl extends ServiceImpl<DictoccupationMapper, Dictoccupation> implements DictoccupationService {

    private final DictoccupationMapper dictoccupationMapper;

    /**
     * 分页查询[JC职业表]列表
     *
     * @param page  分页参数
     * @param param Dictoccupation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Dictoccupation> getPage(PageParam<Dictoccupation> page, Dictoccupation param) {
        return dictoccupationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Dictoccupation getInfoById(String id) {
        return dictoccupationMapper.getInfoById(id);
    }

}


