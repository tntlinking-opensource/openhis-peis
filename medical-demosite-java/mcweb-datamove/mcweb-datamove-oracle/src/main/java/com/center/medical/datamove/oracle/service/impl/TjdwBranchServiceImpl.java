package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TjdwBranchMapper;
import com.center.medical.datamove.oracle.bean.model.TjdwBranch;
import com.center.medical.datamove.oracle.service.TjdwBranchService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * XS体检单位：部门信息(TjdwBranch)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:31
 */
@Slf4j
@Service("tjdwBranchService")
@RequiredArgsConstructor
public class TjdwBranchServiceImpl extends ServiceImpl<TjdwBranchMapper, TjdwBranch> implements TjdwBranchService {

    private final TjdwBranchMapper tjdwBranchMapper;

    /**
     * 分页查询[XS体检单位：部门信息]列表
     *
     * @param page  分页参数
     * @param param TjdwBranch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TjdwBranch> getPage(PageParam<TjdwBranch> page, TjdwBranch param) {
        return tjdwBranchMapper.getPage(page, param);
    }


}


