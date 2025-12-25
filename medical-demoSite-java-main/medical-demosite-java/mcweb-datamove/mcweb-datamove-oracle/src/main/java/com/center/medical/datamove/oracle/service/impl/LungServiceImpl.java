package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.LungMapper;
import com.center.medical.datamove.oracle.bean.model.Lung;
import com.center.medical.datamove.oracle.service.LungService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS肺功能(Lung)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:40
 */
@Slf4j
@Service("lungService")
@RequiredArgsConstructor
public class LungServiceImpl extends ServiceImpl<LungMapper, Lung> implements LungService {

    private final LungMapper lungMapper;

    /**
     * 分页查询[KS肺功能]列表
     *
     * @param page  分页参数
     * @param param Lung查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Lung> getPage(PageParam<Lung> page, Lung param) {
        return lungMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Lung getInfoById(String id) {
        return lungMapper.getInfoById(id);
    }

    ;

}


