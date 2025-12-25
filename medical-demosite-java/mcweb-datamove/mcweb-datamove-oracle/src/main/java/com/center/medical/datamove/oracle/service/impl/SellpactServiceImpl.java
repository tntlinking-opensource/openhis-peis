package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SellpactMapper;
import com.center.medical.datamove.oracle.bean.model.Sellpact;
import com.center.medical.datamove.oracle.service.SellpactService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 销售合同维护表(Sellpact)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:54
 */
@Slf4j
@Service("sellpactService")
@RequiredArgsConstructor
public class SellpactServiceImpl extends ServiceImpl<SellpactMapper, Sellpact> implements SellpactService {

    private final SellpactMapper sellpactMapper;

    /**
     * 分页查询[销售合同维护表]列表
     *
     * @param page  分页参数
     * @param param Sellpact查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Sellpact> getPage(PageParam<Sellpact> page, Sellpact param) {
        return sellpactMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Sellpact getInfoById(String id) {
        return sellpactMapper.getInfoById(id);
    }

    ;

}


