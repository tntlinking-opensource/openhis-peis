package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseGuideMealitem;
import com.center.medical.data.dao.BaseGuideMealitemMapper;
import com.center.medical.data.service.BaseGuideMealitemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 基础收费项目(BaseGuideMealitem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:17
 */
@Slf4j
@Service("baseGuideMealitemService")
@RequiredArgsConstructor
public class BaseGuideMealitemServiceImpl extends ServiceImpl<BaseGuideMealitemMapper, BaseGuideMealitem> implements BaseGuideMealitemService {

    private final BaseGuideMealitemMapper baseGuideMealitemMapper;

    /**
     * 分页查询[基础收费项目]列表
     *
     * @param page  分页参数
     * @param param BaseGuideMealitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseGuideMealitem> getList(PageParam<BaseGuideMealitem> page, BaseGuideMealitem param) {
        return baseGuideMealitemMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BaseGuideMealitem getInfoById(String id) {
        return baseGuideMealitemMapper.getInfoById(id);
    }

}

