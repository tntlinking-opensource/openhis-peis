package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.BaseGuideMealitemMapper;
import com.center.medical.datamove.oracle.bean.model.BaseGuideMealitem;
import com.center.medical.datamove.oracle.service.BaseGuideMealitemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (BaseGuideMealitem)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:12:35
 */
@Slf4j
@Service("baseGuideMealitemService")
@RequiredArgsConstructor
public class BaseGuideMealitemServiceImpl extends ServiceImpl<BaseGuideMealitemMapper, BaseGuideMealitem> implements BaseGuideMealitemService {

    private final BaseGuideMealitemMapper baseGuideMealitemMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BaseGuideMealitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseGuideMealitem> getPage(PageParam<BaseGuideMealitem> page, BaseGuideMealitem param) {
        return baseGuideMealitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BaseGuideMealitem getInfoById(String id) {
        return baseGuideMealitemMapper.getInfoById(id);
    }

}


