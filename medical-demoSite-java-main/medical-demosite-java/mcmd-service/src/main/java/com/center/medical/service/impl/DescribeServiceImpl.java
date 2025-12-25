package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.DescribeMapper;
import com.center.medical.bean.model.Describe;
import com.center.medical.service.DescribeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS科室描述、检查结果表(Describe)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:34
 */
@Slf4j
@Service("describeService")
@RequiredArgsConstructor
public class DescribeServiceImpl extends ServiceImpl<DescribeMapper, Describe> implements DescribeService {

    private final DescribeMapper describeMapper;

    /**
     * 分页查询[KS科室描述、检查结果表]列表
     *
     * @param page  分页参数
     * @param param Describe查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Describe> getList(PageParam<Describe> page, Describe param) {
        return describeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Describe getInfoById(String id) {
        return describeMapper.getInfoById(id);
    }

}

