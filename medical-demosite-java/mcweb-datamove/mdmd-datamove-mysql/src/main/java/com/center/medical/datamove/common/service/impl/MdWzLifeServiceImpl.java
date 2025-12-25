package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdWzLifeMapper;
import com.center.medical.datamove.common.bean.model.MdWzLife;
import com.center.medical.datamove.common.service.MdWzLifeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——个人生活史(MdWzLife)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:24
 */
@Slf4j
@Service("mdWzLifeService")
@RequiredArgsConstructor
public class MdWzLifeServiceImpl extends ServiceImpl<MdWzLifeMapper, MdWzLife> implements MdWzLifeService {

    private final MdWzLifeMapper mdWzLifeMapper;

    /**
     * 分页查询[KS问诊——个人生活史]列表
     *
     * @param page  分页参数
     * @param param MdWzLife查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdWzLife> getPage(PageParam<MdWzLife> page, MdWzLife param) {
        return mdWzLifeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdWzLife getInfoById(String id) {
        return mdWzLifeMapper.getInfoById(id);
    }

}


