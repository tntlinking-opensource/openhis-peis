package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdOrdersummaryMapper;
import com.center.medical.datamove.common.bean.model.MdOrdersummary;
import com.center.medical.datamove.common.service.MdOrdersummaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单总结表(MdOrdersummary)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:30
 */
@Slf4j
@Service("mdOrdersummaryService")
@RequiredArgsConstructor
public class MdOrdersummaryServiceImpl extends ServiceImpl<MdOrdersummaryMapper, MdOrdersummary> implements MdOrdersummaryService {

    private final MdOrdersummaryMapper mdOrdersummaryMapper;

    /**
     * 分页查询[订单总结表]列表
     *
     * @param page  分页参数
     * @param param MdOrdersummary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdOrdersummary> getPage(PageParam<MdOrdersummary> page, MdOrdersummary param) {
        return mdOrdersummaryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdOrdersummary getInfoById(String id) {
        return mdOrdersummaryMapper.getInfoById(id);
    }

}


