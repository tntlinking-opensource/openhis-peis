package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.KingdeeremittanceMapper;
import com.center.medical.datamove.oracle.bean.model.Kingdeeremittance;
import com.center.medical.datamove.oracle.service.KingdeeremittanceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Kingdeeremittance)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:30
 */
@Slf4j
@Service("kingdeeremittanceService")
@RequiredArgsConstructor
public class KingdeeremittanceServiceImpl extends ServiceImpl<KingdeeremittanceMapper, Kingdeeremittance> implements KingdeeremittanceService {

    private final KingdeeremittanceMapper kingdeeremittanceMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Kingdeeremittance查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Kingdeeremittance> getPage(PageParam<Kingdeeremittance> page, Kingdeeremittance param) {
        return kingdeeremittanceMapper.getPage(page, param);
    }


}


