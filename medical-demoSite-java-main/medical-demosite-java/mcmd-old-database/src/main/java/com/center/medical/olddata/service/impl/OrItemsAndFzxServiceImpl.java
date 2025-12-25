package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrItemsAndFzx;
import com.center.medical.olddata.dao.OrItemsAndFzxMapper;
import com.center.medical.olddata.service.OrItemsAndFzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * JC收费项目和分中心关联表(ItemsAndFzx)服务实现类
 *
 * @author ay
 * @since 2024-07-13 14:27:29
 */
@Slf4j
@Service("orItemsAndFzxService")
@RequiredArgsConstructor
public class OrItemsAndFzxServiceImpl extends ServiceImpl<OrItemsAndFzxMapper, OrItemsAndFzx> implements OrItemsAndFzxService {

    private final OrItemsAndFzxMapper orItemsAndFzxMapper;

    /**
     * 分页查询[JC收费项目和分中心关联表]列表
     *
     * @param page  分页参数
     * @param param ItemsAndFzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrItemsAndFzx> getPage(PageParam<OrItemsAndFzx> page, OrItemsAndFzx param) {
        return orItemsAndFzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrItemsAndFzx getInfoById(String id) {
        return orItemsAndFzxMapper.getInfoById(id);
    }

    /**
     * 通过收费项目id查询
     * @param itemsId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrItemsAndFzx> getInfoByitemsId(String itemsId) {
        return orItemsAndFzxMapper.selectList(new LambdaQueryWrapper<OrItemsAndFzx>()
                .eq(OrItemsAndFzx::getItemsId,itemsId));
    }
}

