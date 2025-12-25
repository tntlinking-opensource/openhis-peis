package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Sellpact;
import com.center.medical.datamove.admin.dao.OrSellpactMapper;
import com.center.medical.datamove.admin.service.OrSellpactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 销售合同维护表(Sellpact)服务实现类
 *
 * @author ay
 * @since 2023-07-25 21:19:14
 */
@Slf4j
@Service("orSellpactService")
@RequiredArgsConstructor
public class OrSellpactServiceImpl extends ServiceImpl<OrSellpactMapper, Sellpact> implements OrSellpactService {

    private final OrSellpactMapper orSellpactMapper;

    /**
     * 分页查询[销售合同维护表]列表
     *
     * @param page  分页参数
     * @param param Sellpact查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Sellpact> getPage(PageParam<Sellpact> page, Sellpact param) {
        return orSellpactMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Sellpact getInfoById(String id) {
        return orSellpactMapper.getInfoById(id);
    }


    /**
     * 通过客户id查询合同
     *
     * @param khdwmcid
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public List<Sellpact> getByKhId(String khdwmcid) {
        return orSellpactMapper.selectList(new LambdaQueryWrapper<Sellpact>().eq(Sellpact::getKhdwmcid, khdwmcid));
    }


    /**
     * 通过合同编号查询
     *
     * @param htbh
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public Sellpact getByHtbh(String htbh) {
        return orSellpactMapper.selectOne(new LambdaQueryWrapper<Sellpact>().eq(Sellpact::getHtbh, htbh));
    }
}


