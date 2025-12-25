package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdWzJzbMapper;
import com.center.medical.datamove.common.bean.model.MdWzJzb;
import com.center.medical.datamove.common.service.MdWzJzbService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——家族病(MdWzJzb)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:24
 */
@Slf4j
@Service("mdWzJzbService")
@RequiredArgsConstructor
public class MdWzJzbServiceImpl extends ServiceImpl<MdWzJzbMapper, MdWzJzb> implements MdWzJzbService {

    private final MdWzJzbMapper mdWzJzbMapper;

    /**
     * 分页查询[KS问诊——家族病]列表
     *
     * @param page  分页参数
     * @param param MdWzJzb查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdWzJzb> getPage(PageParam<MdWzJzb> page, MdWzJzb param) {
        return mdWzJzbMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdWzJzb getInfoById(String id) {
        return mdWzJzbMapper.getInfoById(id);
    }

}


