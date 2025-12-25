package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ShortmessageMapper;
import com.center.medical.datamove.oracle.bean.model.Shortmessage;
import com.center.medical.datamove.oracle.service.ShortmessageService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC短信信息表(Shortmessage)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:00
 */
@Slf4j
@Service("shortmessageService")
@RequiredArgsConstructor
public class ShortmessageServiceImpl extends ServiceImpl<ShortmessageMapper, Shortmessage> implements ShortmessageService {

    private final ShortmessageMapper shortmessageMapper;

    /**
     * 分页查询[JC短信信息表]列表
     *
     * @param page  分页参数
     * @param param Shortmessage查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Shortmessage> getPage(PageParam<Shortmessage> page, Shortmessage param) {
        return shortmessageMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Shortmessage getInfoById(String id) {
        return shortmessageMapper.getInfoById(id);
    }

    ;

}


