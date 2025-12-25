package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdComboanditem;
import com.center.medical.datamove.admin.dao.MdComboanditemMapper;
import com.center.medical.datamove.admin.service.MdComboanditemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 维护最小套餐与收费项目关联表(MdComboanditem)服务实现类
 *
 * @author ay
 * @since 2023-07-25 22:05:53
 */
@Slf4j
@Service("mdComboanditemService")
@RequiredArgsConstructor
public class MdComboanditemServiceImpl extends ServiceImpl<MdComboanditemMapper, MdComboanditem> implements MdComboanditemService {

    private final MdComboanditemMapper mdComboanditemMapper;

    /**
     * 分页查询[维护最小套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param MdComboanditem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdComboanditem> getPage(PageParam<MdComboanditem> page, MdComboanditem param) {
        return mdComboanditemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdComboanditem getInfoById(String id) {
        return mdComboanditemMapper.getInfoById(id);
    }


    /**
     * 批量条件或修改
     *
     * @param mapAsList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdComboanditem> mapAsList) {
        saveOrUpdateBatch(mapAsList);
    }
}


