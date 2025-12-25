package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.oracle.bean.model.Branch;
import com.center.medical.datamove.oracle.dao.BranchMapper;
import com.center.medical.datamove.oracle.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 分中心维护表(Branch)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:52
 */
@Slf4j
@Service("branchsService")
@RequiredArgsConstructor
public class BranchServiceImpl extends ServiceImpl<BranchMapper, Branch> implements BranchService {

    private final BranchMapper branchMapper;

    /**
     * 分页查询[分中心维护表]列表
     *
     * @param page  分页参数
     * @param param Branch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Branch> getPage(PageParam<Branch> page, Branch param) {
        return branchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Branch getInfoById(String id) {
        return branchMapper.getInfoById(id);
    }

}


