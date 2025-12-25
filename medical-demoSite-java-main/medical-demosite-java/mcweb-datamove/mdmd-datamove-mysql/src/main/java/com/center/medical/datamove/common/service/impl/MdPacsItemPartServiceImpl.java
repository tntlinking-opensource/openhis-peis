package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPacsItemPartMapper;
import com.center.medical.datamove.common.bean.model.MdPacsItemPart;
import com.center.medical.datamove.common.service.MdPacsItemPartService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 项目部位表(MdPacsItemPart)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:34
 */
@Slf4j
@Service("mdPacsItemPartService")
@RequiredArgsConstructor
public class MdPacsItemPartServiceImpl extends ServiceImpl<MdPacsItemPartMapper, MdPacsItemPart> implements MdPacsItemPartService {

    private final MdPacsItemPartMapper mdPacsItemPartMapper;

    /**
     * 分页查询[项目部位表]列表
     *
     * @param page  分页参数
     * @param param MdPacsItemPart查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPacsItemPart> getPage(PageParam<MdPacsItemPart> page, MdPacsItemPart param) {
        return mdPacsItemPartMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPacsItemPart getInfoById(String id) {
        return mdPacsItemPartMapper.getInfoById(id);
    }

}


