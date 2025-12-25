package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.WzCallbackMapper;
import com.center.medical.bean.model.WzCallback;
import com.center.medical.service.WzCallbackService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——复查随访(WzCallback)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:29
 */
@Slf4j
@Service("wzCallbackService")
@RequiredArgsConstructor
public class WzCallbackServiceImpl extends ServiceImpl<WzCallbackMapper, WzCallback> implements WzCallbackService {

    private final WzCallbackMapper wzCallbackMapper;

    /**
     * 分页查询[KS问诊——复查随访]列表
     *
     * @param page  分页参数
     * @param param WzCallback查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WzCallback> getList(PageParam<WzCallback> page, WzCallback param) {
        return wzCallbackMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public WzCallback getInfoById(String id) {
        return wzCallbackMapper.getInfoById(id);
    }

}

