package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.TjbbXtjcMapper;
import com.center.medical.bean.model.TjbbXtjc;
import com.center.medical.service.TjbbXtjcService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS血糖检测体检报表(TjbbXtjc)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:32
 */
@Slf4j
@Service("tjbbXtjcService")
@RequiredArgsConstructor
public class TjbbXtjcServiceImpl extends ServiceImpl<TjbbXtjcMapper, TjbbXtjc> implements TjbbXtjcService {

    private final TjbbXtjcMapper tjbbXtjcMapper;

    /**
     * 分页查询[KS血糖检测体检报表]列表
     *
     * @param page  分页参数
     * @param param TjbbXtjc查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TjbbXtjc> getList(PageParam<TjbbXtjc> page, TjbbXtjc param) {
        return tjbbXtjcMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public TjbbXtjc getInfoById(String id) {
        return tjbbXtjcMapper.getInfoById(id);
    }

}

