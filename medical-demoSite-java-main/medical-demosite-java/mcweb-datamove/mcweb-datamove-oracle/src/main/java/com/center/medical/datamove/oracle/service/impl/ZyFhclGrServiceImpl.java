package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ZyFhclGrMapper;
import com.center.medical.datamove.oracle.bean.model.ZyFhclGr;
import com.center.medical.datamove.oracle.service.ZyFhclGrService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC个人防护用品(ZyFhclGr)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:54
 */
@Slf4j
@Service("zyFhclGrService")
@RequiredArgsConstructor
public class ZyFhclGrServiceImpl extends ServiceImpl<ZyFhclGrMapper, ZyFhclGr> implements ZyFhclGrService {

    private final ZyFhclGrMapper zyFhclGrMapper;

    /**
     * 分页查询[JC个人防护用品]列表
     *
     * @param page  分页参数
     * @param param ZyFhclGr查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ZyFhclGr> getPage(PageParam<ZyFhclGr> page, ZyFhclGr param) {
        return zyFhclGrMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ZyFhclGr getInfoById(String id) {
        return zyFhclGrMapper.getInfoById(id);
    }

}


