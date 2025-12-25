package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.KingdeereserMapper;
import com.center.medical.datamove.oracle.bean.model.Kingdeereser;
import com.center.medical.datamove.oracle.service.KingdeereserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Kingdeereser)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:19:31
 */
@Slf4j
@Service("kingdeereserService")
@RequiredArgsConstructor
public class KingdeereserServiceImpl extends ServiceImpl<KingdeereserMapper, Kingdeereser> implements KingdeereserService {

    private final KingdeereserMapper kingdeereserMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Kingdeereser查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Kingdeereser> getPage(PageParam<Kingdeereser> page, Kingdeereser param) {
        return kingdeereserMapper.getPage(page, param);
    }


}


