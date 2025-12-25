package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PacsResultMapper;
import com.center.medical.datamove.oracle.bean.model.PacsResult;
import com.center.medical.datamove.oracle.service.PacsResultService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Pacs数据(PacsResult)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:22:52
 */
@Slf4j
@Service("pacsResultService")
@RequiredArgsConstructor
public class PacsResultServiceImpl extends ServiceImpl<PacsResultMapper, PacsResult> implements PacsResultService {

    private final PacsResultMapper pacsResultMapper;

    /**
     * 分页查询[Pacs数据]列表
     *
     * @param page  分页参数
     * @param param PacsResult查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsResult> getPage(PageParam<PacsResult> page, PacsResult param) {
        return pacsResultMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PacsResult getInfoById(String id) {
        return pacsResultMapper.getInfoById(id);
    }

}


