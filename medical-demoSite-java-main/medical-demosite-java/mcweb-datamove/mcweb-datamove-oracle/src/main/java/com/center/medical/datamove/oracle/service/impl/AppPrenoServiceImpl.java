package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppPrenoMapper;
import com.center.medical.datamove.oracle.bean.model.AppPreno;
import com.center.medical.datamove.oracle.service.AppPrenoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (AppPreno)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:50
 */
@Slf4j
@Service("appPrenoService")
@RequiredArgsConstructor
public class AppPrenoServiceImpl extends ServiceImpl<AppPrenoMapper, AppPreno> implements AppPrenoService {

    private final AppPrenoMapper appPrenoMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AppPreno查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppPreno> getPage(PageParam<AppPreno> page, AppPreno param) {
        return appPrenoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppPreno getInfoById(String id) {
        return appPrenoMapper.getInfoById(id);
    }

}


