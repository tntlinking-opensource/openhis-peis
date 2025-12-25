package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppFaqMapper;
import com.center.medical.datamove.oracle.bean.model.AppFaq;
import com.center.medical.datamove.oracle.service.AppFaqService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * APP常见问题(AppFaq)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:35
 */
@Slf4j
@Service("appFaqService")
@RequiredArgsConstructor
public class AppFaqServiceImpl extends ServiceImpl<AppFaqMapper, AppFaq> implements AppFaqService {

    private final AppFaqMapper appFaqMapper;

    /**
     * 分页查询[APP常见问题]列表
     *
     * @param page  分页参数
     * @param param AppFaq查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppFaq> getPage(PageParam<AppFaq> page, AppFaq param) {
        return appFaqMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppFaq getInfoById(String id) {
        return appFaqMapper.getInfoById(id);
    }

}


