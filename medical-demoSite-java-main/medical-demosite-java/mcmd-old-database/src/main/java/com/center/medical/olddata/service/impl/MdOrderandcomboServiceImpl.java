package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdOrderandcombo;
import com.center.medical.olddata.dao.MdOrderandcomboMapper;
import com.center.medical.olddata.service.MdOrderandcomboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单与套餐关联表(MdOrderandcombo)服务实现类
 *
 * @author ay
 * @since 2023-07-25 21:38:00
 */
@Slf4j
@Service("mdOrderandcomboService")
@RequiredArgsConstructor
public class MdOrderandcomboServiceImpl extends ServiceImpl<MdOrderandcomboMapper, MdOrderandcombo> implements MdOrderandcomboService {

    private final MdOrderandcomboMapper mdOrderandcomboMapper;

    /**
     * 分页查询[订单与套餐关联表]列表
     *
     * @param page  分页参数
     * @param param MdOrderandcombo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdOrderandcombo> getPage(PageParam<MdOrderandcombo> page, MdOrderandcombo param) {
        return mdOrderandcomboMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdOrderandcombo getInfoById(String id) {
        return mdOrderandcomboMapper.getInfoById(id);
    }


    /**
     * 批量添加或修改
     *
     * @param mapAsList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdOrderandcombo> mapAsList) {
        saveOrUpdateBatch(mapAsList);
    }


    /**
     * 通过订单id查询
     *
     * @param id
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<MdOrderandcombo> getByDdId(String id) {
        return mdOrderandcomboMapper.selectList(new LambdaQueryWrapper<MdOrderandcombo>().eq(MdOrderandcombo::getDdid, id));
    }
}


