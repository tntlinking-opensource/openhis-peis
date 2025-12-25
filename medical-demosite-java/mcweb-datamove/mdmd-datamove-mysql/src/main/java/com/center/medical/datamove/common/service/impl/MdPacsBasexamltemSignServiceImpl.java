package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPacsBasexamltemSignMapper;
import com.center.medical.datamove.common.bean.model.MdPacsBasexamltemSign;
import com.center.medical.datamove.common.service.MdPacsBasexamltemSignService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS-体征词(MdPacsBasexamltemSign)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:33
 */
@Slf4j
@Service("mdPacsBasexamltemSignService")
@RequiredArgsConstructor
public class MdPacsBasexamltemSignServiceImpl extends ServiceImpl<MdPacsBasexamltemSignMapper, MdPacsBasexamltemSign> implements MdPacsBasexamltemSignService {

    private final MdPacsBasexamltemSignMapper mdPacsBasexamltemSignMapper;

    /**
     * 分页查询[PACS-体征词]列表
     *
     * @param page  分页参数
     * @param param MdPacsBasexamltemSign查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPacsBasexamltemSign> getPage(PageParam<MdPacsBasexamltemSign> page, MdPacsBasexamltemSign param) {
        return mdPacsBasexamltemSignMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPacsBasexamltemSign getInfoById(String id) {
        return mdPacsBasexamltemSignMapper.getInfoById(id);
    }

}


