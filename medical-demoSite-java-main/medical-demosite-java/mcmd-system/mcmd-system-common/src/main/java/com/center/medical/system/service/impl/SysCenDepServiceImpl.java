package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.core.domain.entity.SysCenDep;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.dao.SysCenDepMapper;
import com.center.medical.system.service.SysCenDepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 分中心部门表(SysCenDep)表服务实现类
 *
 * @author ay
 * @since 2023-04-26 08:43:56
 */
@Slf4j
@Service("sysCenDepService")
@RequiredArgsConstructor
public class SysCenDepServiceImpl extends ServiceImpl<SysCenDepMapper, SysCenDep> implements SysCenDepService {

    private final SysCenDepMapper sysCenDepMapper;

    /**
     * 分页查询[分中心部门表]列表
     *
     * @param page  分页参数
     * @param param SysCenDep查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysCenDep> getList(PageParam<SysCenDep> page, SysCenDep param) {
        return sysCenDepMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysCenDep getInfoById(String id) {
        return sysCenDepMapper.getInfoById(id);
    }

}

