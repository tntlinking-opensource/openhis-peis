package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.KingdeereserwayMapper;
import com.center.medical.datamove.oracle.bean.model.Kingdeereserway;
import com.center.medical.datamove.oracle.service.KingdeereserwayService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Kingdeereserway)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:32
 */
@Slf4j
@Service("kingdeereserwayService")
@RequiredArgsConstructor
public class KingdeereserwayServiceImpl extends ServiceImpl<KingdeereserwayMapper, Kingdeereserway> implements KingdeereserwayService {

    private final KingdeereserwayMapper kingdeereserwayMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Kingdeereserway查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Kingdeereserway> getPage(PageParam<Kingdeereserway> page, Kingdeereserway param) {
        return kingdeereserwayMapper.getPage(page, param);
    }


}


