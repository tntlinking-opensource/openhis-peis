package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppCustserviceMapper;
import com.center.medical.datamove.oracle.bean.model.AppCustservice;
import com.center.medical.datamove.oracle.service.AppCustserviceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (AppCustservice)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:28
 */
@Slf4j
@Service("appCustserviceService")
@RequiredArgsConstructor
public class AppCustserviceServiceImpl extends ServiceImpl<AppCustserviceMapper, AppCustservice> implements AppCustserviceService {

    private final AppCustserviceMapper appCustserviceMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AppCustservice查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppCustservice> getPage(PageParam<AppCustservice> page, AppCustservice param) {
        return appCustserviceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppCustservice getInfoById(String id) {
        return appCustserviceMapper.getInfoById(id);
    }

}


