package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TjbbXzjcMapper;
import com.center.medical.datamove.oracle.bean.model.TjbbXzjc;
import com.center.medical.datamove.oracle.service.TjbbXzjcService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS血脂检测体检报表(TjbbXzjc)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:31
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
    public IPage<TjbbXzjc> getPage(PageParam<TjbbXzjc> page, TjbbXzjc param) {
        return tjbbXzjcMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public TjbbXzjc getInfoById(String id) {
        return tjbbXzjcMapper.getInfoById(id);
    }

    ;

}


