package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.KingdeeorganizationMapper;
import com.center.medical.datamove.oracle.bean.model.Kingdeeorganization;
import com.center.medical.datamove.oracle.service.KingdeeorganizationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Kingdeeorganization)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:27
 */
@Slf4j
@Service("kingdeeorganizationService")
@RequiredArgsConstructor
public class KingdeeorganizationServiceImpl extends ServiceImpl<KingdeeorganizationMapper, Kingdeeorganization> implements KingdeeorganizationService {

    private final KingdeeorganizationMapper kingdeeorganizationMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Kingdeeorganization查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Kingdeeorganization> getPage(PageParam<Kingdeeorganization> page, Kingdeeorganization param) {
        return kingdeeorganizationMapper.getPage(page, param);
    }


}


