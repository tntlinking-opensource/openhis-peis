package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TjbbXyjcMapper;
import com.center.medical.datamove.oracle.bean.model.TjbbXyjc;
import com.center.medical.datamove.oracle.service.TjbbXyjcService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS血压检测(TjbbXyjc)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:29
 */
@Slf4j
@Service("tjbbXyjcService")
@RequiredArgsConstructor
public class TjbbXyjcServiceImpl extends ServiceImpl<TjbbXyjcMapper, TjbbXyjc> implements TjbbXyjcService {

    private final TjbbXyjcMapper tjbbXyjcMapper;

    /**
     * 分页查询[KS血压检测]列表
     *
     * @param page  分页参数
     * @param param TjbbXyjc查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TjbbXyjc> getPage(PageParam<TjbbXyjc> page, TjbbXyjc param) {
        return tjbbXyjcMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public TjbbXyjc getInfoById(String id) {
        return tjbbXyjcMapper.getInfoById(id);
    }

}


