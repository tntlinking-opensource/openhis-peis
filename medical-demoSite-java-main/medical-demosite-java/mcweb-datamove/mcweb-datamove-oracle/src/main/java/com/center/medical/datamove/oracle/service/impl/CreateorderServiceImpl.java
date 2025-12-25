package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.oracle.bean.model.Createorder;
import com.center.medical.datamove.oracle.dao.CreateorderMapper;
import com.center.medical.datamove.oracle.service.CreateorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 订单表(Createorder)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:00
 */
@Slf4j
@Service("createordersService")
@RequiredArgsConstructor
public class CreateorderServiceImpl extends ServiceImpl<CreateorderMapper, Createorder> implements CreateorderService {

    private final CreateorderMapper createorderMapper;

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Createorder> getPage(PageParam<Createorder> page, Createorder param) {
        return createorderMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Createorder getInfoById(String id) {
        return createorderMapper.getInfoById(id);
    }

}


