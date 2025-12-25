package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBasexamltemMapper;
import com.center.medical.datamove.common.bean.model.MdBasexamltem;
import com.center.medical.datamove.common.service.MdBasexamltemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC检查项目表(MdBasexamltem)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:12
 */
@Slf4j
@Service("mdBasexamltemService")
@RequiredArgsConstructor
public class MdBasexamltemServiceImpl extends ServiceImpl<MdBasexamltemMapper, MdBasexamltem> implements MdBasexamltemService {

    private final MdBasexamltemMapper mdBasexamltemMapper;

    /**
     * 分页查询[JC检查项目表]列表
     *
     * @param page  分页参数
     * @param param MdBasexamltem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBasexamltem> getPage(PageParam<MdBasexamltem> page, MdBasexamltem param) {
        return mdBasexamltemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBasexamltem getInfoById(String id) {
        return mdBasexamltemMapper.getInfoById(id);
    }

}


