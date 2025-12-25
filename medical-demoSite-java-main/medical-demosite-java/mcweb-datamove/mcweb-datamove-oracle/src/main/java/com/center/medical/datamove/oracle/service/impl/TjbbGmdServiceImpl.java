package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TjbbGmdMapper;
import com.center.medical.datamove.oracle.bean.model.TjbbGmd;
import com.center.medical.datamove.oracle.service.TjbbGmdService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS骨密度体检报表(TjbbGmd)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:25
 */
@Slf4j
@Service("tjbbGmdService")
@RequiredArgsConstructor
public class TjbbGmdServiceImpl extends ServiceImpl<TjbbGmdMapper, TjbbGmd> implements TjbbGmdService {

    private final TjbbGmdMapper tjbbGmdMapper;

    /**
     * 分页查询[KS骨密度体检报表]列表
     *
     * @param page  分页参数
     * @param param TjbbGmd查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TjbbGmd> getPage(PageParam<TjbbGmd> page, TjbbGmd param) {
        return tjbbGmdMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public TjbbGmd getInfoById(String id) {
        return tjbbGmdMapper.getInfoById(id);
    }

}


