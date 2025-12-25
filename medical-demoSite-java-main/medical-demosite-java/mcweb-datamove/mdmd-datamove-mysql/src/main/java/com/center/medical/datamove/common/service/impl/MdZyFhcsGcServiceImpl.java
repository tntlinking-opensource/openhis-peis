package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdZyFhcsGcMapper;
import com.center.medical.datamove.common.bean.model.MdZyFhcsGc;
import com.center.medical.datamove.common.service.MdZyFhcsGcService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC工程防护(MdZyFhcsGc)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:28
 */
@Slf4j
@Service("mdZyFhcsGcService")
@RequiredArgsConstructor
public class MdZyFhcsGcServiceImpl extends ServiceImpl<MdZyFhcsGcMapper, MdZyFhcsGc> implements MdZyFhcsGcService {

    private final MdZyFhcsGcMapper mdZyFhcsGcMapper;

    /**
     * 分页查询[JC工程防护]列表
     *
     * @param page  分页参数
     * @param param MdZyFhcsGc查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdZyFhcsGc> getPage(PageParam<MdZyFhcsGc> page, MdZyFhcsGc param) {
        return mdZyFhcsGcMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdZyFhcsGc getInfoById(String id) {
        return mdZyFhcsGcMapper.getInfoById(id);
    }

}


