package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TjbbXtjcMapper;
import com.center.medical.datamove.oracle.bean.model.TjbbXtjc;
import com.center.medical.datamove.oracle.service.TjbbXtjcService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS血糖检测体检报表(TjbbXtjc)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:27
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
    public IPage<TjbbXtjc> getPage(PageParam<TjbbXtjc> page, TjbbXtjc param) {
        return tjbbXtjcMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public TjbbXtjc getInfoById(String id) {
        return tjbbXtjcMapper.getInfoById(id);
    }

    ;

}


