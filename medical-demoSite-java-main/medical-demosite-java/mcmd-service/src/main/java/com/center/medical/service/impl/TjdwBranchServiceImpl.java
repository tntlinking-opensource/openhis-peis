package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.TjdwBranchMapper;
import com.center.medical.bean.model.TjdwBranch;
import com.center.medical.service.TjdwBranchService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * XS体检单位：部门信息(TjdwBranch)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:39
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
    public IPage<TjdwBranch> getList(PageParam<TjdwBranch> page, TjdwBranch param) {
        return tjdwBranchMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public TjdwBranch getInfoById(String id) {
        return tjdwBranchMapper.getInfoById(id);
    }

}

