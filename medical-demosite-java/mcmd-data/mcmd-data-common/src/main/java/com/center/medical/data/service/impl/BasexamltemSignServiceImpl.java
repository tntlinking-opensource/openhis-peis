package com.center.medical.data.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BasexamltemSign;
import com.center.medical.data.bean.param.FeatureListParam;
import com.center.medical.data.bean.vo.FeatureListVo;
import com.center.medical.data.dao.BasexamltemSignMapper;
import com.center.medical.data.service.BasexamltemSignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * JC检查项目体证词关联表(BasexamltemSign)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:24
 */
@Slf4j
@Service("basexamltemSignService")
@RequiredArgsConstructor
public class BasexamltemSignServiceImpl extends ServiceImpl<BasexamltemSignMapper, BasexamltemSign> implements BasexamltemSignService {

    private final BasexamltemSignMapper basexamltemSignMapper;

    /**
     * 分页查询[JC检查项目体证词关联表]列表
     *
     * @param page  分页参数
     * @param param BasexamltemSign查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BasexamltemSign> getList(PageParam<BasexamltemSign> page, BasexamltemSign param) {
        return basexamltemSignMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BasexamltemSign getInfoById(String id) {
        return basexamltemSignMapper.getInfoById(id);
    }

    /**
     * 根据检查项目id删除体征词
     *
     * @param inspectId 检查项目id
     */
    @Override
    public void removeByInspectId(String inspectId) {
        List<BasexamltemSign> list = basexamltemSignMapper.selectList(new LambdaQueryWrapper<BasexamltemSign>()
                .eq(BasexamltemSign::getInspectId, inspectId).eq(BasexamltemSign::getIsDelete, 0));
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(item -> {
                item.setIsDelete(1);
            });
            updateBatchById(list);
        }
    }

    /**
     * 根据检查项目ID获取相对应的体征词
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<FeatureListVo> getFeatureListData(PageParam<FeatureListVo> page, FeatureListParam param) {
        return basexamltemSignMapper.getFeatureListData(page,param);
    }
}

