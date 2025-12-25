package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.BusinessSource;
import com.center.medical.system.bean.param.BusinessSourceParam;
import com.center.medical.system.dao.BusinessSourceMapper;
import com.center.medical.system.service.BusinessSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 合作第三方(BusinessSource)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-03-24 10:10:24
 */
@Slf4j
@Service("businessSourceService")
@RequiredArgsConstructor
public class BusinessSourceServiceImpl extends ServiceImpl<BusinessSourceMapper, BusinessSource> implements BusinessSourceService {

    private final BusinessSourceMapper businessSourceMapper;

    /**
     * 分页查询[合作第三方]列表
     *
     * @param page  分页参数
     * @param param BusinessSource查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BusinessSource> getPage(PageParam<BusinessSource> page, BusinessSourceParam param) {
        return businessSourceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public BusinessSource getInfoById(Long id) {
        return businessSourceMapper.getInfoById(id);
    }

}

