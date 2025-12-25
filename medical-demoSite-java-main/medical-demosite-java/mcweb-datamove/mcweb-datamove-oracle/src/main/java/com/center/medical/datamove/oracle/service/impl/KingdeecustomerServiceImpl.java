package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.KingdeecustomerMapper;
import com.center.medical.datamove.oracle.bean.model.Kingdeecustomer;
import com.center.medical.datamove.oracle.service.KingdeecustomerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Kingdeecustomer)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:25
 */
@Slf4j
@Service("kingdeecustomerService")
@RequiredArgsConstructor
public class KingdeecustomerServiceImpl extends ServiceImpl<KingdeecustomerMapper, Kingdeecustomer> implements KingdeecustomerService {

    private final KingdeecustomerMapper kingdeecustomerMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Kingdeecustomer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Kingdeecustomer> getPage(PageParam<Kingdeecustomer> page, Kingdeecustomer param) {
        return kingdeecustomerMapper.getPage(page, param);
    }


}


