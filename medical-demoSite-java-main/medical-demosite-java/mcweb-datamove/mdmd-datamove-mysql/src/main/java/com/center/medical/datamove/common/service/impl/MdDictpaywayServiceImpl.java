package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdDictpaywayMapper;
import com.center.medical.datamove.common.bean.model.MdDictpayway;
import com.center.medical.datamove.common.service.MdDictpaywayService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC支付方式表(MdDictpayway)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:13
 */
@Slf4j
@Service("mdDictpaywayService")
@RequiredArgsConstructor
public class MdDictpaywayServiceImpl extends ServiceImpl<MdDictpaywayMapper, MdDictpayway> implements MdDictpaywayService {

    private final MdDictpaywayMapper mdDictpaywayMapper;

    /**
     * 分页查询[JC支付方式表]列表
     *
     * @param page  分页参数
     * @param param MdDictpayway查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdDictpayway> getPage(PageParam<MdDictpayway> page, MdDictpayway param) {
        return mdDictpaywayMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdDictpayway getInfoById(String id) {
        return mdDictpaywayMapper.getInfoById(id);
    }

}


