package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CustomerfollowMapper;
import com.center.medical.datamove.oracle.bean.model.Customerfollow;
import com.center.medical.datamove.oracle.service.CustomerfollowService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户跟踪表(Customerfollow)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:07
 */
@Slf4j
@Service("customerfollowService")
@RequiredArgsConstructor
public class CustomerfollowServiceImpl extends ServiceImpl<CustomerfollowMapper, Customerfollow> implements CustomerfollowService {

    private final CustomerfollowMapper customerfollowMapper;

    /**
     * 分页查询[客户跟踪表]列表
     *
     * @param page  分页参数
     * @param param Customerfollow查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Customerfollow> getPage(PageParam<Customerfollow> page, Customerfollow param) {
        return customerfollowMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Customerfollow getInfoById(String id) {
        return customerfollowMapper.getInfoById(id);
    }

}


