package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBasconclusiontypeMapper;
import com.center.medical.datamove.common.bean.model.MdBasconclusiontype;
import com.center.medical.datamove.common.service.MdBasconclusiontypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 总检结论词类型(MdBasconclusiontype)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:09
 */
@Slf4j
@Service("mdBasconclusiontypeService")
@RequiredArgsConstructor
public class MdBasconclusiontypeServiceImpl extends ServiceImpl<MdBasconclusiontypeMapper, MdBasconclusiontype> implements MdBasconclusiontypeService {

    private final MdBasconclusiontypeMapper mdBasconclusiontypeMapper;

    /**
     * 分页查询[总检结论词类型]列表
     *
     * @param page  分页参数
     * @param param MdBasconclusiontype查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBasconclusiontype> getPage(PageParam<MdBasconclusiontype> page, MdBasconclusiontype param) {
        return mdBasconclusiontypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBasconclusiontype getInfoById(String id) {
        return mdBasconclusiontypeMapper.getInfoById(id);
    }

}


