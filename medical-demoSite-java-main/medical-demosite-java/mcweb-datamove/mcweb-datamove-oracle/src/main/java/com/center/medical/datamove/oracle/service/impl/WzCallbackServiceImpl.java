package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.WzCallbackMapper;
import com.center.medical.datamove.oracle.bean.model.WzCallback;
import com.center.medical.datamove.oracle.service.WzCallbackService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——复查随访(WzCallback)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:30:48
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
    public IPage<WzCallback> getPage(PageParam<WzCallback> page, WzCallback param) {
        return wzCallbackMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WzCallback getInfoById(String id) {
        return wzCallbackMapper.getInfoById(id);
    }

}


