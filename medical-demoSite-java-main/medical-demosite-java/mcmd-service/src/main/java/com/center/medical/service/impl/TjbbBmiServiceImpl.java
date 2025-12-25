package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.TjbbBmiMapper;
import com.center.medical.bean.model.TjbbBmi;
import com.center.medical.service.TjbbBmiService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS体重指数体检报表(TjbbBmi)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:21
 */
@Slf4j
@Service("tjbbBmiService")
@RequiredArgsConstructor
public class TjbbBmiServiceImpl extends ServiceImpl<TjbbBmiMapper, TjbbBmi> implements TjbbBmiService {

    private final TjbbBmiMapper tjbbBmiMapper;

    /**
     * 分页查询[KS体重指数体检报表]列表
     *
     * @param page  分页参数
     * @param param TjbbBmi查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TjbbBmi> getList(PageParam<TjbbBmi> page, TjbbBmi param) {
        return tjbbBmiMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public TjbbBmi getInfoById(String id) {
        return tjbbBmiMapper.getInfoById(id);
    }

    ;

}

