package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ZyFhcsGcClassMapper;
import com.center.medical.datamove.oracle.bean.model.ZyFhcsGcClass;
import com.center.medical.datamove.oracle.service.ZyFhcsGcClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC工程防护种类(ZyFhcsGcClass)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:56
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
    public IPage<ZyFhcsGcClass> getPage(PageParam<ZyFhcsGcClass> page, ZyFhcsGcClass param) {
        return zyFhcsGcClassMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ZyFhcsGcClass getInfoById(String id) {
        return zyFhcsGcClassMapper.getInfoById(id);
    }

    ;

}


