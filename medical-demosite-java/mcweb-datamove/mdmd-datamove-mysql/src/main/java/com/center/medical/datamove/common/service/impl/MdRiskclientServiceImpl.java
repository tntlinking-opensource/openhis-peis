package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdRiskclientMapper;
import com.center.medical.datamove.common.bean.model.MdRiskclient;
import com.center.medical.datamove.common.service.MdRiskclientService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 高危人员管理表(MdRiskclient)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:20
 */
@Slf4j
@Service("mdRiskclientService")
@RequiredArgsConstructor
public class MdRiskclientServiceImpl extends ServiceImpl<MdRiskclientMapper, MdRiskclient> implements MdRiskclientService {

    private final MdRiskclientMapper mdRiskclientMapper;

    /**
     * 分页查询[高危人员管理表]列表
     *
     * @param page  分页参数
     * @param param MdRiskclient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdRiskclient> getPage(PageParam<MdRiskclient> page, MdRiskclient param) {
        return mdRiskclientMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdRiskclient getInfoById(String id) {
        return mdRiskclientMapper.getInfoById(id);
    }

}


