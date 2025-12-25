package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdCreateorderMapper;
import com.center.medical.datamove.common.bean.model.MdCreateorder;
import com.center.medical.datamove.common.service.MdCreateorderService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单表(MdCreateorder)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:20
 */
@Slf4j
@Service("mdCreateorderService")
@RequiredArgsConstructor
public class MdCreateorderServiceImpl extends ServiceImpl<MdCreateorderMapper, MdCreateorder> implements MdCreateorderService {

    private final MdCreateorderMapper mdCreateorderMapper;

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param MdCreateorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCreateorder> getPage(PageParam<MdCreateorder> page, MdCreateorder param) {
        return mdCreateorderMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdCreateorder getInfoById(String id) {
        return mdCreateorderMapper.getInfoById(id);
    }

}


