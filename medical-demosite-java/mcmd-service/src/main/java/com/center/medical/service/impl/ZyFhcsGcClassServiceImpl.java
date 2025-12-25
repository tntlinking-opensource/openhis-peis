package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.ZyFhcsGcClassMapper;
import com.center.medical.bean.model.ZyFhcsGcClass;
import com.center.medical.service.ZyFhcsGcClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC工程防护种类(ZyFhcsGcClass)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:17
 */
@Slf4j
@Service("zyFhcsGcClassService")
@RequiredArgsConstructor
public class ZyFhcsGcClassServiceImpl extends ServiceImpl<ZyFhcsGcClassMapper, ZyFhcsGcClass> implements ZyFhcsGcClassService {

    private final ZyFhcsGcClassMapper zyFhcsGcClassMapper;

    /**
     * 分页查询[JC工程防护种类]列表
     *
     * @param page  分页参数
     * @param param ZyFhcsGcClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ZyFhcsGcClass> getList(PageParam<ZyFhcsGcClass> page, ZyFhcsGcClass param) {
        return zyFhcsGcClassMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public ZyFhcsGcClass getInfoById(String id) {
        return zyFhcsGcClassMapper.getInfoById(id);
    }

}

