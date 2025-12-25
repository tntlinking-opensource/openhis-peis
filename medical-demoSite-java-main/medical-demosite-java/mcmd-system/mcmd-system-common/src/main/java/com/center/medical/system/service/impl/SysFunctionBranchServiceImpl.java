package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysFunctionBranch;
import com.center.medical.system.dao.SysFunctionBranchMapper;
import com.center.medical.system.service.SysFunctionBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 业务功能-分中心关联(SysFunctionBranch)服务实现类
 *
 * @author makejava
 * @since 2024-03-19 11:12:09
 */
@Slf4j
@Service("sysFunctionBranchService")
@RequiredArgsConstructor
public class SysFunctionBranchServiceImpl extends ServiceImpl<SysFunctionBranchMapper, SysFunctionBranch> implements SysFunctionBranchService {

    private final SysFunctionBranchMapper sysFunctionBranchMapper;

    /**
     * 分页查询[业务功能-分中心关联]列表
     *
     * @param page  分页参数
     * @param param SysFunctionBranch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SysFunctionBranch> getPage(PageParam<SysFunctionBranch> page, SysFunctionBranch param) {
        return sysFunctionBranchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SysFunctionBranch getInfoById(String id) {
        return sysFunctionBranchMapper.getInfoById(id);
    }


    /**
     * 查询该分中心是否开启该业务
     * @param functionId
     * @param branchId
     * @return
     */
    @Override
    public Integer getByfunIdAndCid(int functionId, String branchId) {
        return sysFunctionBranchMapper.getByfunIdAndCid(functionId,branchId);
    }
}

