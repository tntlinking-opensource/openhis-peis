package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.SysBranchMapper;
import com.center.medical.datamove.common.bean.model.SysBranch;
import com.center.medical.datamove.common.service.SysBranchService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 分中心维护表(SysBranch)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:33
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


