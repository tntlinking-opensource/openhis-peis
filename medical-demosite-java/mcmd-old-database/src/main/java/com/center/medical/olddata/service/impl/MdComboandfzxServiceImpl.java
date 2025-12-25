package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdComboandfzx;
import com.center.medical.olddata.dao.MdComboandfzxMapper;
import com.center.medical.olddata.service.MdComboandfzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 最小套餐与分中心关联表(MdComboandfzx)服务实现类
 *
 * @author ay
 * @since 2023-07-25 22:05:51
 */
@Slf4j
@Service("mdComboandfzxService")
@RequiredArgsConstructor
public class MdComboandfzxServiceImpl extends ServiceImpl<MdComboandfzxMapper, MdComboandfzx> implements MdComboandfzxService {

    private final MdComboandfzxMapper mdComboandfzxMapper;

    /**
     * 分页查询[最小套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param MdComboandfzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdComboandfzx> getPage(PageParam<MdComboandfzx> page, MdComboandfzx param) {
        return mdComboandfzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdComboandfzx getInfoById(String id) {
        return mdComboandfzxMapper.getInfoById(id);
    }


    /**
     * 批量插入或更新
     *
     * @param mapAsList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdComboandfzx> mapAsList) {
        saveOrUpdateBatch(mapAsList);
    }


    /**
     * 通过套餐id获取数据
     * @param tcid
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<MdComboandfzx> getByTcid(String tcid) {
        return mdComboandfzxMapper.selectList(new LambdaQueryWrapper<MdComboandfzx>().eq(MdComboandfzx::getTcid, tcid));
    }


    /**
     * 通过套餐id和分中心id查询
     * @param tcid
     * @param fzxId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public MdComboandfzx getByTcidAndFzx(String tcid, String fzxId) {
        return mdComboandfzxMapper.selectOne(new LambdaQueryWrapper<MdComboandfzx>()
                .eq(MdComboandfzx::getTcid, tcid)
                .eq(MdComboandfzx::getFzxid,fzxId)
        );
    }
}


