package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdTjdwBranchMapper;
import com.center.medical.datamove.common.bean.model.MdTjdwBranch;
import com.center.medical.datamove.common.service.MdTjdwBranchService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * XS体检单位：部门信息(MdTjdwBranch)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:07
 */
@Slf4j
@Service("mdTjdwBranchService")
@RequiredArgsConstructor
public class MdTjdwBranchServiceImpl extends ServiceImpl<MdTjdwBranchMapper, MdTjdwBranch> implements MdTjdwBranchService {

    private final MdTjdwBranchMapper mdTjdwBranchMapper;

    /**
     * 分页查询[XS体检单位：部门信息]列表
     *
     * @param page  分页参数
     * @param param MdTjdwBranch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdTjdwBranch> getPage(PageParam<MdTjdwBranch> page, MdTjdwBranch param) {
        return mdTjdwBranchMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdTjdwBranch getInfoById(String id) {
        return mdTjdwBranchMapper.getInfoById(id);
    }

}


