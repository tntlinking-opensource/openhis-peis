package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrBasexamltemSign;
import com.center.medical.olddata.dao.OrBasexamltemSignMapper;
import com.center.medical.olddata.service.OrBasexamltemSignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * JC检查项目体证词关联表(BasexamltemSign)服务实现类
 *
 * @author ay
 * @since 2024-07-13 14:27:48
 */
@Slf4j
@Service("orBasexamltemSignService")
@RequiredArgsConstructor
public class OrBasexamltemSignServiceImpl extends ServiceImpl<OrBasexamltemSignMapper, OrBasexamltemSign> implements OrBasexamltemSignService {

    private final OrBasexamltemSignMapper orBasexamltemSignMapper;

    /**
     * 分页查询[JC检查项目体证词关联表]列表
     *
     * @param page  分页参数
     * @param param BasexamltemSign查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrBasexamltemSign> getPage(PageParam<OrBasexamltemSign> page, OrBasexamltemSign param) {
        return orBasexamltemSignMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrBasexamltemSign getInfoById(String id) {
        return orBasexamltemSignMapper.getInfoById(id);
    }

    /**
     * 通过检查项目id查询
     * @param inspectId
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrBasexamltemSign> getInfoByInspectId(String inspectId) {
        return orBasexamltemSignMapper.selectList(new LambdaQueryWrapper<OrBasexamltemSign>()
                .eq(OrBasexamltemSign::getInspectId,inspectId)
        );
    }
}

