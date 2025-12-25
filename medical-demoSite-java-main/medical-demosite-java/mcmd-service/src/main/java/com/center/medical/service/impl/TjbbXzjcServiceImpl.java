package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.TjbbXzjcMapper;
import com.center.medical.bean.model.TjbbXzjc;
import com.center.medical.service.TjbbXzjcService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS血脂检测体检报表(TjbbXzjc)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:15
 */
@Slf4j
@Service("tjbbXzjcService")
@RequiredArgsConstructor
public class TjbbXzjcServiceImpl extends ServiceImpl<TjbbXzjcMapper, TjbbXzjc> implements TjbbXzjcService {

    private final TjbbXzjcMapper tjbbXzjcMapper;

    /**
     * 分页查询[KS血脂检测体检报表]列表
     *
     * @param page  分页参数
     * @param param TjbbXzjc查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TjbbXzjc> getList(PageParam<TjbbXzjc> page, TjbbXzjc param) {
        return tjbbXzjcMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public TjbbXzjc getInfoById(String id) {
        return tjbbXzjcMapper.getInfoById(id);
    }

}

