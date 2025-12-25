package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdSellpact;
import com.center.medical.olddata.dao.MdSellpactMapper;
import com.center.medical.olddata.service.MdSellpactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 销售合同维护表(CrmSellpact)服务实现类
 *
 * @author ay
 * @since 2023-07-25 21:19:54
 */
@Slf4j
@Service("mdSellpactService")
@RequiredArgsConstructor
public class MdSellpactServiceImpl extends ServiceImpl<MdSellpactMapper, MdSellpact> implements MdSellpactService {

    private final MdSellpactMapper mdSellpactMapper;

    /**
     * 分页查询[销售合同维护表]列表
     *
     * @param page  分页参数
     * @param param CrmSellpact查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSellpact> getPage(PageParam<MdSellpact> page, MdSellpact param) {
        return mdSellpactMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSellpact getInfoById(String id) {
        return mdSellpactMapper.getInfoById(id);
    }

    /**
     * 保存或更新
     *
     * @param map
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUp(MdSellpact map) {
        saveOrUpdate(map);
    }


    /**
     * 通过合同编号获取
     * @param htbh
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public List<MdSellpact> getByHtbh(String htbh) {
        return mdSellpactMapper.selectList(new LambdaQueryWrapper<MdSellpact>().eq(MdSellpact::getHtbh, htbh));
    }


    /**
     * 批量保存
     * @param mdSellpactList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdSellpact> mdSellpactList) {
        saveOrUpdateBatch(mdSellpactList);
    }
}


