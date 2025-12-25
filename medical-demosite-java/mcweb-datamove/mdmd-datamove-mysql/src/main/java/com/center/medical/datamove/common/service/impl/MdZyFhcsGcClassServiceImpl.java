package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdZyFhcsGcClassMapper;
import com.center.medical.datamove.common.bean.model.MdZyFhcsGcClass;
import com.center.medical.datamove.common.service.MdZyFhcsGcClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC工程防护种类(MdZyFhcsGcClass)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:28
 */
@Slf4j
@Service("mdZyFhcsGcClassService")
@RequiredArgsConstructor
public class MdZyFhcsGcClassServiceImpl extends ServiceImpl<MdZyFhcsGcClassMapper, MdZyFhcsGcClass> implements MdZyFhcsGcClassService {

    private final MdZyFhcsGcClassMapper mdZyFhcsGcClassMapper;

    /**
     * 分页查询[JC工程防护种类]列表
     *
     * @param page  分页参数
     * @param param MdZyFhcsGcClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdZyFhcsGcClass> getPage(PageParam<MdZyFhcsGcClass> page, MdZyFhcsGcClass param) {
        return mdZyFhcsGcClassMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdZyFhcsGcClass getInfoById(String id) {
        return mdZyFhcsGcClassMapper.getInfoById(id);
    }

}


