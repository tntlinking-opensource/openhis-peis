package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdMealanditem;
import com.center.medical.olddata.dao.MdMealanditemMapper;
import com.center.medical.olddata.service.MdMealanditemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 普通套餐与收费项目关联表(MdMealanditem)服务实现类
 *
 * @author ay
 * @since 2023-07-25 22:26:23
 */
@Slf4j
@Service("mdMealanditemService")
@RequiredArgsConstructor
public class MdMealanditemServiceImpl extends ServiceImpl<MdMealanditemMapper, MdMealanditem> implements MdMealanditemService {

    private final MdMealanditemMapper mdMealanditemMapper;

    /**
     * 分页查询[普通套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param MdMealanditem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdMealanditem> getPage(PageParam<MdMealanditem> page, MdMealanditem param) {
        return mdMealanditemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdMealanditem getInfoById(String id) {
        return mdMealanditemMapper.getInfoById(id);
    }

    /**
     * 批量添加或修改
     *
     * @param mapAsList
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUpList(List<MdMealanditem> mapAsList) {
        saveOrUpdateBatch(mapAsList);
    }


    /**
     * 通过套餐id查询
     *
     * @param tcid
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public List<MdMealanditem> getByTcid(String tcid) {
        return mdMealanditemMapper.selectList(new LambdaQueryWrapper<MdMealanditem>().eq(MdMealanditem::getTcid, tcid));
    }
}


