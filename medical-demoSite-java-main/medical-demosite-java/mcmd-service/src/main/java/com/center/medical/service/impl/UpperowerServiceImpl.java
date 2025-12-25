package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.UpperowerMapper;
import com.center.medical.bean.model.Upperower;
import com.center.medical.service.UpperowerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 上下级关系管理表(Upperower)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:52
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
    public IPage<Upperower> getList(PageParam<Upperower> page, Upperower param) {
        return upperowerMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Upperower getInfoById(String id) {
        return upperowerMapper.getInfoById(id);
    }

}

