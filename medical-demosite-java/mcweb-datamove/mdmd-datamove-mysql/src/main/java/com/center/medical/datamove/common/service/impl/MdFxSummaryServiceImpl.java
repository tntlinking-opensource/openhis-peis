package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFxSummaryMapper;
import com.center.medical.datamove.common.bean.model.MdFxSummary;
import com.center.medical.datamove.common.service.MdFxSummaryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 本次职业健康检查危害因素人员检查情况汇总一览表(MdFxSummary)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
@Slf4j
@Service("mdFxSummaryService")
@RequiredArgsConstructor
public class MdFxSummaryServiceImpl extends ServiceImpl<MdFxSummaryMapper, MdFxSummary> implements MdFxSummaryService {

    private final MdFxSummaryMapper mdFxSummaryMapper;

    /**
     * 分页查询[本次职业健康检查危害因素人员检查情况汇总一览表]列表
     *
     * @param page  分页参数
     * @param param MdFxSummary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFxSummary> getPage(PageParam<MdFxSummary> page, MdFxSummary param) {
        return mdFxSummaryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFxSummary getInfoById(String id) {
        return mdFxSummaryMapper.getInfoById(id);
    }

}


