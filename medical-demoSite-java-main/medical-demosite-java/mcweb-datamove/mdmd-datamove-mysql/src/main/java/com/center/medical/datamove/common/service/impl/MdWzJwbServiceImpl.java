package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdWzJwbMapper;
import com.center.medical.datamove.common.bean.model.MdWzJwb;
import com.center.medical.datamove.common.service.MdWzJwbService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——既往病(MdWzJwb)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:23
 */
@Slf4j
@Service("mdWzJwbService")
@RequiredArgsConstructor
public class MdWzJwbServiceImpl extends ServiceImpl<MdWzJwbMapper, MdWzJwb> implements MdWzJwbService {

    private final MdWzJwbMapper mdWzJwbMapper;

    /**
     * 分页查询[KS问诊——既往病]列表
     *
     * @param page  分页参数
     * @param param MdWzJwb查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdWzJwb> getPage(PageParam<MdWzJwb> page, MdWzJwb param) {
        return mdWzJwbMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdWzJwb getInfoById(String id) {
        return mdWzJwbMapper.getInfoById(id);
    }

}


