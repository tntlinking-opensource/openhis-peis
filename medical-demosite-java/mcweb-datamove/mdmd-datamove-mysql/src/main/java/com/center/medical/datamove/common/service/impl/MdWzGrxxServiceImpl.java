package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdWzGrxxMapper;
import com.center.medical.datamove.common.bean.model.MdWzGrxx;
import com.center.medical.datamove.common.service.MdWzGrxxService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——个人基本信息(MdWzGrxx)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:23
 */
@Slf4j
@Service("mdWzGrxxService")
@RequiredArgsConstructor
public class MdWzGrxxServiceImpl extends ServiceImpl<MdWzGrxxMapper, MdWzGrxx> implements MdWzGrxxService {

    private final MdWzGrxxMapper mdWzGrxxMapper;

    /**
     * 分页查询[KS问诊——个人基本信息]列表
     *
     * @param page  分页参数
     * @param param MdWzGrxx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdWzGrxx> getPage(PageParam<MdWzGrxx> page, MdWzGrxx param) {
        return mdWzGrxxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdWzGrxx getInfoById(String id) {
        return mdWzGrxxMapper.getInfoById(id);
    }

}


