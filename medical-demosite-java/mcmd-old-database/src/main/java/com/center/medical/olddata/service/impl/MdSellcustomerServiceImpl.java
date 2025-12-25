package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdSellcustomer;
import com.center.medical.olddata.dao.MdSellcustomerMapper;
import com.center.medical.olddata.service.MdSellcustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 我的客户表(CrmSellcustomer)服务实现类
 *
 * @author ay
 * @since 2023-07-25 21:02:13
 */
@Slf4j
@Service("mdSellcustomerService")
@RequiredArgsConstructor
public class MdSellcustomerServiceImpl extends ServiceImpl<MdSellcustomerMapper, MdSellcustomer> implements MdSellcustomerService {

    private final MdSellcustomerMapper mdSellcustomerMapper;

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param CrmSellcustomer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSellcustomer> getPage(PageParam<MdSellcustomer> page, MdSellcustomer param) {
        return mdSellcustomerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public MdSellcustomer getInfoById(String id) {
        return mdSellcustomerMapper.getInfoById(id);
    }


    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUp(MdSellcustomer map) {
        saveOrUpdate(map);
    }

    /**
     * 批量保存
     * @param mdSellcustomerList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdSellcustomer> mdSellcustomerList) {
        saveOrUpdateBatch(mdSellcustomerList);
    }
}


