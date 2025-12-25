package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSortexamLimitMapper;
import com.center.medical.datamove.common.bean.model.MdSortexamLimit;
import com.center.medical.datamove.common.service.MdSortexamLimitService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 每日排检上限(MdSortexamLimit)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:45
 */
@Slf4j
@Service("mdSortexamLimitService")
@RequiredArgsConstructor
public class MdSortexamLimitServiceImpl extends ServiceImpl<MdSortexamLimitMapper, MdSortexamLimit> implements MdSortexamLimitService {

    private final MdSortexamLimitMapper mdSortexamLimitMapper;

    /**
     * 分页查询[每日排检上限]列表
     *
     * @param page  分页参数
     * @param param MdSortexamLimit查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSortexamLimit> getPage(PageParam<MdSortexamLimit> page, MdSortexamLimit param) {
        return mdSortexamLimitMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSortexamLimit getInfoById(String id) {
        return mdSortexamLimitMapper.getInfoById(id);
    }

}


