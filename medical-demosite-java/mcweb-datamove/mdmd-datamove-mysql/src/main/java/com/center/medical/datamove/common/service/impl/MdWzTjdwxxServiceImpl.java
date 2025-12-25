package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdWzTjdwxxMapper;
import com.center.medical.datamove.common.bean.model.MdWzTjdwxx;
import com.center.medical.datamove.common.service.MdWzTjdwxxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——体检单位信息(MdWzTjdwxx)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:25
 */
@Slf4j
@Service("mdWzTjdwxxService")
@RequiredArgsConstructor
public class MdWzTjdwxxServiceImpl extends ServiceImpl<MdWzTjdwxxMapper, MdWzTjdwxx> implements MdWzTjdwxxService {

    private final MdWzTjdwxxMapper mdWzTjdwxxMapper;

    /**
     * 分页查询[KS问诊——体检单位信息]列表
     *
     * @param page  分页参数
     * @param param MdWzTjdwxx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdWzTjdwxx> getPage(PageParam<MdWzTjdwxx> page, MdWzTjdwxx param) {
        return mdWzTjdwxxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdWzTjdwxx getInfoById(String id) {
        return mdWzTjdwxxMapper.getInfoById(id);
    }

}


