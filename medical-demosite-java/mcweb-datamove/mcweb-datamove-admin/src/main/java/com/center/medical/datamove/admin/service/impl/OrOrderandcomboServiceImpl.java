package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Orderandcombo;
import com.center.medical.datamove.admin.dao.OrOrderandcomboMapper;
import com.center.medical.datamove.admin.service.OrOrderandcomboService;
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
public class OrOrderandcomboServiceImpl extends ServiceImpl<OrOrderandcomboMapper, Orderandcombo> implements OrOrderandcomboService {

    private final OrOrderandcomboMapper orOrderandcomboMapper;

    /**
     * 分页查询[订单与套餐关联表]列表
     *
     * @param page  分页参数
     * @param param Orderandcombo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Orderandcombo> getPage(PageParam<Orderandcombo> page, Orderandcombo param) {
        return orOrderandcomboMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Orderandcombo getInfoById(String id) {
        return orOrderandcomboMapper.getInfoById(id);
    }


    /**
     * 通过订单id查询
     *
     * @param id
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<Orderandcombo> getByDdId(String id) {
        return orOrderandcomboMapper.selectList(new LambdaQueryWrapper<Orderandcombo>().eq(Orderandcombo::getDdid, id));
    }
}


