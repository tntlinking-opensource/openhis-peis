package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFxItemscheckMapper;
import com.center.medical.datamove.common.bean.model.MdFxItemscheck;
import com.center.medical.datamove.common.service.MdFxItemscheckService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 综合分析-项目參检（健康）(MdFxItemscheck)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
@Slf4j
@Service("mdFxItemscheckService")
@RequiredArgsConstructor
public class MdFxItemscheckServiceImpl extends ServiceImpl<MdFxItemscheckMapper, MdFxItemscheck> implements MdFxItemscheckService {

    private final MdFxItemscheckMapper mdFxItemscheckMapper;

    /**
     * 分页查询[综合分析-项目參检（健康）]列表
     *
     * @param page  分页参数
     * @param param MdFxItemscheck查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFxItemscheck> getPage(PageParam<MdFxItemscheck> page, MdFxItemscheck param) {
        return mdFxItemscheckMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFxItemscheck getInfoById(String id) {
        return mdFxItemscheckMapper.getInfoById(id);
    }

}


