package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TotalVerdictMapper;
import com.center.medical.datamove.oracle.bean.model.TotalVerdict;
import com.center.medical.datamove.oracle.service.TotalVerdictService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ZJ总检结论词表(TotalVerdict)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:37
 */
@Slf4j
@Service("totalVerdictService")
@RequiredArgsConstructor
public class TotalVerdictServiceImpl extends ServiceImpl<TotalVerdictMapper, TotalVerdict> implements TotalVerdictService {

    private final TotalVerdictMapper totalVerdictMapper;

    /**
     * 分页查询[ZJ总检结论词表]列表
     *
     * @param page  分页参数
     * @param param TotalVerdict查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TotalVerdict> getPage(PageParam<TotalVerdict> page, TotalVerdict param) {
        return totalVerdictMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public TotalVerdict getInfoById(String id) {
        return totalVerdictMapper.getInfoById(id);
    }

    ;

}


