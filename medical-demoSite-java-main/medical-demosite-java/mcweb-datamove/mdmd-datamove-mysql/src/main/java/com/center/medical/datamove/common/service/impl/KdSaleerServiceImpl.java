package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.KdSaleerMapper;
import com.center.medical.datamove.common.bean.model.KdSaleer;
import com.center.medical.datamove.common.service.KdSaleerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 金蝶销售员(KdSaleer)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:05
 */
@Slf4j
@Service("kdSaleerService")
@RequiredArgsConstructor
public class KdSaleerServiceImpl extends ServiceImpl<KdSaleerMapper, KdSaleer> implements KdSaleerService {

    private final KdSaleerMapper kdSaleerMapper;

    /**
     * 分页查询[金蝶销售员]列表
     *
     * @param page  分页参数
     * @param param KdSaleer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdSaleer> getPage(PageParam<KdSaleer> page, KdSaleer param) {
        return kdSaleerMapper.getPage(page, param);
    }


}


