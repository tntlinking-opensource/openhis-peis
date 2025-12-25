package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.DictpaywayMapper;
import com.center.medical.datamove.oracle.bean.model.Dictpayway;
import com.center.medical.datamove.oracle.service.DictpaywayService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC支付方式表(Dictpayway)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:15
 */
@Slf4j
@Service("dictpaywayService")
@RequiredArgsConstructor
public class DictpaywayServiceImpl extends ServiceImpl<DictpaywayMapper, Dictpayway> implements DictpaywayService {

    private final DictpaywayMapper dictpaywayMapper;

    /**
     * 分页查询[JC支付方式表]列表
     *
     * @param page  分页参数
     * @param param Dictpayway查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Dictpayway> getPage(PageParam<Dictpayway> page, Dictpayway param) {
        return dictpaywayMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Dictpayway getInfoById(String id) {
        return dictpaywayMapper.getInfoById(id);
    }

}


