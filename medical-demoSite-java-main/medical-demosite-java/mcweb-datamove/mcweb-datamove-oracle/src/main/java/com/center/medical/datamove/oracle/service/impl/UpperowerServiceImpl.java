package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.UpperowerMapper;
import com.center.medical.datamove.oracle.bean.model.Upperower;
import com.center.medical.datamove.oracle.service.UpperowerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 上下级关系管理表(Upperower)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:42
 */
@Slf4j
@Service("upperowerService")
@RequiredArgsConstructor
public class UpperowerServiceImpl extends ServiceImpl<UpperowerMapper, Upperower> implements UpperowerService {

    private final UpperowerMapper upperowerMapper;

    /**
     * 分页查询[上下级关系管理表]列表
     *
     * @param page  分页参数
     * @param param Upperower查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Upperower> getPage(PageParam<Upperower> page, Upperower param) {
        return upperowerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Upperower getInfoById(String id) {
        return upperowerMapper.getInfoById(id);
    }

    ;

}


