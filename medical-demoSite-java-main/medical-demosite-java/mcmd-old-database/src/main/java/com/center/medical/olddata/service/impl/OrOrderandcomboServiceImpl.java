package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrOrderandcombo;
import com.center.medical.olddata.dao.OrOrderandcomboMapper;
import com.center.medical.olddata.service.OrOrderandcomboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单与套餐关联表(Orderandcombo)服务实现类
 *
 * @author ay
 * @since 2023-07-25 21:38:48
 */
@Slf4j
@Service("orOrderandcomboService")
@RequiredArgsConstructor
public class OrOrderandcomboServiceImpl extends ServiceImpl<OrOrderandcomboMapper, OrOrderandcombo> implements OrOrderandcomboService {

    private final OrOrderandcomboMapper orderandcomboMapper;

    /**
     * 分页查询[订单与套餐关联表]列表
     *
     * @param page  分页参数
     * @param param Orderandcombo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrOrderandcombo> getPage(PageParam<OrOrderandcombo> page, OrOrderandcombo param) {
        return orderandcomboMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrOrderandcombo getInfoById(String id) {
        return orderandcomboMapper.getInfoById(id);
    }


    /**
     * 通过订单id查询
     *
     * @param id
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrOrderandcombo> getByDdId(String id) {
        return orderandcomboMapper.selectList(new LambdaQueryWrapper<OrOrderandcombo>().eq(OrOrderandcombo::getDdid, id));
    }
}


