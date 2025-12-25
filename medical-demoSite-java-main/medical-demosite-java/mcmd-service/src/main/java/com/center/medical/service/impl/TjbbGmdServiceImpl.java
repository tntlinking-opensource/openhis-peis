package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.TjbbGmdMapper;
import com.center.medical.bean.model.TjbbGmd;
import com.center.medical.service.TjbbGmdService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS骨密度体检报表(TjbbGmd)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:39
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
    public IPage<TjbbGmd> getList(PageParam<TjbbGmd> page, TjbbGmd param) {
        return tjbbGmdMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public TjbbGmd getInfoById(String id) {
        return tjbbGmdMapper.getInfoById(id);
    }

    ;

}

