package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPacsBasePartMapper;
import com.center.medical.datamove.common.bean.model.MdPacsBasePart;
import com.center.medical.datamove.common.service.MdPacsBasePartService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS-部位(MdPacsBasePart)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:32
 */
@Slf4j
@Service("mdPacsBasePartService")
@RequiredArgsConstructor
public class MdPacsBasePartServiceImpl extends ServiceImpl<MdPacsBasePartMapper, MdPacsBasePart> implements MdPacsBasePartService {

    private final MdPacsBasePartMapper mdPacsBasePartMapper;

    /**
     * 分页查询[PACS-部位]列表
     *
     * @param page  分页参数
     * @param param MdPacsBasePart查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPacsBasePart> getPage(PageParam<MdPacsBasePart> page, MdPacsBasePart param) {
        return mdPacsBasePartMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPacsBasePart getInfoById(String id) {
        return mdPacsBasePartMapper.getInfoById(id);
    }

}


