package com.center.medical.olddata.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrMealandfzx;
import com.center.medical.olddata.dao.OrMealandfzxMapper;
import com.center.medical.olddata.service.OrMealandfzxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 普通套餐与分中心关联表(Mealandfzx)服务实现类
 *
 * @author ay
 * @since 2023-07-25 22:25:31
 */
@Slf4j
@Service("orMealandfzxService")
@RequiredArgsConstructor
public class OrMealandfzxServiceImpl extends ServiceImpl<OrMealandfzxMapper, OrMealandfzx> implements OrMealandfzxService {

    private final OrMealandfzxMapper mealandfzxMapper;

    /**
     * 分页查询[普通套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Mealandfzx查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrMealandfzx> getPage(PageParam<OrMealandfzx> page, OrMealandfzx param) {
        return mealandfzxMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrMealandfzx getInfoById(String id) {
        return mealandfzxMapper.getInfoById(id);
    }

    /**
     * 通过套餐id查询
     *
     * @param tcid
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrMealandfzx> getByTcid(String tcid) {
        return mealandfzxMapper.selectList(new LambdaQueryWrapper<OrMealandfzx>().eq(OrMealandfzx::getTcid, tcid));
    }


    /**
     * 通过套餐id和分中心查询
     * @param tcid
     * @param fzxId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public OrMealandfzx getByTcidAndFzx(String tcid, String fzxId) {
        List<OrMealandfzx> list = mealandfzxMapper.selectList(new LambdaQueryWrapper<OrMealandfzx>()
                .eq(OrMealandfzx::getTcid, tcid)
                .eq(OrMealandfzx::getFzxid, fzxId)
        );
        if (CollectionUtil.isNotEmpty(list)){
            return list.get(0);
        }else {
            return null;
        }

    }
}


