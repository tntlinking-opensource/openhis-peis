package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.NucleinMapper;
import com.center.medical.datamove.oracle.bean.model.Nuclein;
import com.center.medical.datamove.oracle.service.NucleinService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Nuclein)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:16
 */
@Slf4j
@Service("nucleinService")
@RequiredArgsConstructor
public class NucleinServiceImpl extends ServiceImpl<NucleinMapper, Nuclein> implements NucleinService {

    private final NucleinMapper nucleinMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Nuclein查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Nuclein> getPage(PageParam<Nuclein> page, Nuclein param) {
        return nucleinMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Nuclein getInfoById(String id) {
        return nucleinMapper.getInfoById(id);
    }

}


