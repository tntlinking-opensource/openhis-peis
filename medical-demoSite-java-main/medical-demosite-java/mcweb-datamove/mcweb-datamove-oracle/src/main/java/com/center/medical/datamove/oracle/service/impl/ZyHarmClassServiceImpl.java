package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ZyHarmClassMapper;
import com.center.medical.datamove.oracle.bean.model.ZyHarmClass;
import com.center.medical.datamove.oracle.service.ZyHarmClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (ZyHarmClass)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:59
 */
@Slf4j
@Service("zyHarmClassService")
@RequiredArgsConstructor
public class ZyHarmClassServiceImpl extends ServiceImpl<ZyHarmClassMapper, ZyHarmClass> implements ZyHarmClassService {

    private final ZyHarmClassMapper zyHarmClassMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param ZyHarmClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ZyHarmClass> getPage(PageParam<ZyHarmClass> page, ZyHarmClass param) {
        return zyHarmClassMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ZyHarmClass getInfoById(String id) {
        return zyHarmClassMapper.getInfoById(id);
    }

}


