package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBasMergeMapper;
import com.center.medical.datamove.common.bean.model.MdBasMerge;
import com.center.medical.datamove.common.service.MdBasMergeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 合并结伦词(MdBasMerge)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:08
 */
@Slf4j
@Service("mdBasMergeService")
@RequiredArgsConstructor
public class MdBasMergeServiceImpl extends ServiceImpl<MdBasMergeMapper, MdBasMerge> implements MdBasMergeService {

    private final MdBasMergeMapper mdBasMergeMapper;

    /**
     * 分页查询[合并结伦词]列表
     *
     * @param page  分页参数
     * @param param MdBasMerge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBasMerge> getPage(PageParam<MdBasMerge> page, MdBasMerge param) {
        return mdBasMergeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBasMerge getInfoById(String id) {
        return mdBasMergeMapper.getInfoById(id);
    }

}


