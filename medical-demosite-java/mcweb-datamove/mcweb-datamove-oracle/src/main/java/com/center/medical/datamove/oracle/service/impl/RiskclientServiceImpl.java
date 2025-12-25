package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.RiskclientMapper;
import com.center.medical.datamove.oracle.bean.model.Riskclient;
import com.center.medical.datamove.oracle.service.RiskclientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 高危人员管理表(Riskclient)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:27
 */
@Slf4j
@Service("riskclientService")
@RequiredArgsConstructor
public class RiskclientServiceImpl extends ServiceImpl<RiskclientMapper, Riskclient> implements RiskclientService {

    private final RiskclientMapper riskclientMapper;

    /**
     * 分页查询[高危人员管理表]列表
     *
     * @param page  分页参数
     * @param param Riskclient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Riskclient> getPage(PageParam<Riskclient> page, Riskclient param) {
        return riskclientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Riskclient getInfoById(String id) {
        return riskclientMapper.getInfoById(id);
    }

}


