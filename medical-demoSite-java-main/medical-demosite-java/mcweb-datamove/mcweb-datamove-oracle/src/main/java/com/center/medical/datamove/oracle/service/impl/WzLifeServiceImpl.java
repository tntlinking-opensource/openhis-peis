package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WzLifeMapper;
import com.center.medical.datamove.oracle.bean.model.WzLife;
import com.center.medical.datamove.oracle.service.WzLifeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——个人生活史(WzLife)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:51
 */
@Slf4j
@Service("wzLifeService")
@RequiredArgsConstructor
public class WzLifeServiceImpl extends ServiceImpl<WzLifeMapper, WzLife> implements WzLifeService {

    private final WzLifeMapper wzLifeMapper;

    /**
     * 分页查询[KS问诊——个人生活史]列表
     *
     * @param page  分页参数
     * @param param WzLife查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WzLife> getPage(PageParam<WzLife> page, WzLife param) {
        return wzLifeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WzLife getInfoById(String id) {
        return wzLifeMapper.getInfoById(id);
    }

}


