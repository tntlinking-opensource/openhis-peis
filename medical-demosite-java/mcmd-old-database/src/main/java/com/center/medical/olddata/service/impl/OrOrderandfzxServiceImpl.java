package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrOrderandfzx;
import com.center.medical.olddata.dao.OrOrderandfzxMapper;
import com.center.medical.olddata.service.OrOrderandfzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单与分中心关联表(Orderandfzx)服务实现类
 *
 * @author ay
 * @since 2023-07-25 21:10:28
 */
@Slf4j
@Service("orOrderandfzxService")
@RequiredArgsConstructor
public class OrOrderandfzxServiceImpl extends ServiceImpl<OrOrderandfzxMapper, OrOrderandfzx> implements OrOrderandfzxService {

    private final OrOrderandfzxMapper orderandfzxMapper;

    /**
     * 分页查询[订单与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Orderandfzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrOrderandfzx> getPage(PageParam<OrOrderandfzx> page, OrOrderandfzx param) {
        return orderandfzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrOrderandfzx getInfoById(String id) {
        return orderandfzxMapper.getInfoById(id);
    }


    /**
     * 通过订单id查询
     * @param id
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrOrderandfzx> getByDdid(String id) {
        return orderandfzxMapper.selectList(new LambdaQueryWrapper<OrOrderandfzx>().eq(OrOrderandfzx::getDdid, id));
    }


    /**
     * 通过订单id和分中心查询
     * @param id
     * @param fzxId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrOrderandfzx getByDdidAndFzxId(String id, String fzxId) {
        return orderandfzxMapper.selectOne(new LambdaQueryWrapper<OrOrderandfzx>()
                .eq(OrOrderandfzx::getDdid, id).eq(OrOrderandfzx::getFzxid,fzxId));
    }
}


