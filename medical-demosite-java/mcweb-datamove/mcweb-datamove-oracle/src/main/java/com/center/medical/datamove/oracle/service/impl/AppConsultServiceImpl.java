package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.AppConsultMapper;
import com.center.medical.datamove.oracle.bean.model.AppConsult;
import com.center.medical.datamove.oracle.service.AppConsultService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信小程序医生咨询(AppConsult)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:11:24
 */
@Slf4j
@Service("appConsultService")
@RequiredArgsConstructor
public class AppConsultServiceImpl extends ServiceImpl<AppConsultMapper, AppConsult> implements AppConsultService {

    private final AppConsultMapper appConsultMapper;

    /**
     * 分页查询[微信小程序医生咨询]列表
     *
     * @param page  分页参数
     * @param param AppConsult查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppConsult> getPage(PageParam<AppConsult> page, AppConsult param) {
        return appConsultMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppConsult getInfoById(String id) {
        return appConsultMapper.getInfoById(id);
    }

}


