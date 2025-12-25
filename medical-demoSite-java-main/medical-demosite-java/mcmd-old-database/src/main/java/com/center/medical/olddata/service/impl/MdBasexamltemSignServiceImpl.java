package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdBasexamltemSign;
import com.center.medical.olddata.dao.MdBasexamltemSignMapper;
import com.center.medical.olddata.service.MdBasexamltemSignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * JC检查项目体证词关联表(MdBasexamltemSign)服务实现类
 *
 * @author ay
 * @since 2024-07-13 13:49:10
 */
@Slf4j
@Service("mdBasexamltemSignService")
@RequiredArgsConstructor
public class MdBasexamltemSignServiceImpl extends ServiceImpl<MdBasexamltemSignMapper, MdBasexamltemSign> implements MdBasexamltemSignService {

    private final MdBasexamltemSignMapper mdBasexamltemSignMapper;

    /**
     * 分页查询[JC检查项目体证词关联表]列表
     *
     * @param page  分页参数
     * @param param MdBasexamltemSign查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBasexamltemSign> getPage(PageParam<MdBasexamltemSign> page, MdBasexamltemSign param) {
        return mdBasexamltemSignMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBasexamltemSign getInfoById(String id) {
        return mdBasexamltemSignMapper.getInfoById(id);
    }

}

