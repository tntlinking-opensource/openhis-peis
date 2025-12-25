package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPacsResultMapper;
import com.center.medical.datamove.common.bean.model.MdPacsResult;
import com.center.medical.datamove.common.service.MdPacsResultService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS-数据(MdPacsResult)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:35
 */
@Slf4j
@Service("mdPacsResultService")
@RequiredArgsConstructor
public class MdPacsResultServiceImpl extends ServiceImpl<MdPacsResultMapper, MdPacsResult> implements MdPacsResultService {

    private final MdPacsResultMapper mdPacsResultMapper;

    /**
     * 分页查询[PACS-数据]列表
     *
     * @param page  分页参数
     * @param param MdPacsResult查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPacsResult> getPage(PageParam<MdPacsResult> page, MdPacsResult param) {
        return mdPacsResultMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPacsResult getInfoById(String id) {
        return mdPacsResultMapper.getInfoById(id);
    }

}


