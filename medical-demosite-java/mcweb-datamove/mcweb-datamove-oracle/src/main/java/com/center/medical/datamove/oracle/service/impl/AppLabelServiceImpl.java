package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppLabelMapper;
import com.center.medical.datamove.oracle.bean.model.AppLabel;
import com.center.medical.datamove.oracle.service.AppLabelService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信小程序医生标签(AppLabel)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:41
 */
@Slf4j
@Service("appLabelService")
@RequiredArgsConstructor
public class AppLabelServiceImpl extends ServiceImpl<AppLabelMapper, AppLabel> implements AppLabelService {

    private final AppLabelMapper appLabelMapper;

    /**
     * 分页查询[微信小程序医生标签]列表
     *
     * @param page  分页参数
     * @param param AppLabel查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppLabel> getPage(PageParam<AppLabel> page, AppLabel param) {
        return appLabelMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppLabel getInfoById(String id) {
        return appLabelMapper.getInfoById(id);
    }

}


