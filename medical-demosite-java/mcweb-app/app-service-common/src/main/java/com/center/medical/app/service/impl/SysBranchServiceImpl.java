package com.center.medical.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.model.SysBranch;
import com.center.medical.app.common.util.PageParam;
import com.center.medical.app.dao.SysBranchMapper;
import com.center.medical.app.service.SysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 分中心维护表(SysBranch)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-03-28 18:26:14
 */
@Slf4j
@Service("sysBranchService")
@RequiredArgsConstructor
public class SysBranchServiceImpl extends ServiceImpl<SysBranchMapper, SysBranch> implements SysBranchService {

    private final SysBranchMapper sysBranchMapper;

    /**
     * 分页查询[分中心维护表]列表
     *
     * @param page  分页参数
     * @param param SysBranch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysBranch> getPage(PageParam<SysBranch> page, SysBranch param) {
        return sysBranchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysBranch getInfoById(Integer id) {
        return sysBranchMapper.getInfoById(id);
    }

}

