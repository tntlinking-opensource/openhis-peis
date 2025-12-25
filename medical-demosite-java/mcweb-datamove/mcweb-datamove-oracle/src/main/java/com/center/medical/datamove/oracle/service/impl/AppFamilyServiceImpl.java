package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppFamilyMapper;
import com.center.medical.datamove.oracle.bean.model.AppFamily;
import com.center.medical.datamove.oracle.service.AppFamilyService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 家人档案(不含本人)(AppFamily)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:34
 */
@Slf4j
@Service("appFamilyService")
@RequiredArgsConstructor
public class AppFamilyServiceImpl extends ServiceImpl<AppFamilyMapper, AppFamily> implements AppFamilyService {

    private final AppFamilyMapper appFamilyMapper;

    /**
     * 分页查询[家人档案(不含本人)]列表
     *
     * @param page  分页参数
     * @param param AppFamily查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppFamily> getPage(PageParam<AppFamily> page, AppFamily param) {
        return appFamilyMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppFamily getInfoById(String id) {
        return appFamilyMapper.getInfoById(id);
    }

}


