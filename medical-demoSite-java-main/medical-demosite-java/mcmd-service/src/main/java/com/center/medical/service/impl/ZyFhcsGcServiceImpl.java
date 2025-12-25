package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.ZyFhcsGcMapper;
import com.center.medical.bean.model.ZyFhcsGc;
import com.center.medical.service.ZyFhcsGcService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC工程防护(ZyFhcsGc)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:53
 */
@Slf4j
@Service("zyFhcsGcService")
@RequiredArgsConstructor
public class ZyFhcsGcServiceImpl extends ServiceImpl<ZyFhcsGcMapper, ZyFhcsGc> implements ZyFhcsGcService {

    private final ZyFhcsGcMapper zyFhcsGcMapper;

    /**
     * 分页查询[JC工程防护]列表
     *
     * @param page  分页参数
     * @param param ZyFhcsGc查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ZyFhcsGc> getList(PageParam<ZyFhcsGc> page, ZyFhcsGc param) {
        return zyFhcsGcMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public ZyFhcsGc getInfoById(String id) {
        return zyFhcsGcMapper.getInfoById(id);
    }

}

