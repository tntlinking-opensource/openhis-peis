package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PacsResultMapper;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.service.PacsResultService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS-数据(PacsResult)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:03
 */
@Slf4j
@Service("pacsResultService")
@RequiredArgsConstructor
public class PacsResultServiceImpl extends ServiceImpl<PacsResultMapper, PacsResult> implements PacsResultService {

    private final PacsResultMapper pacsResultMapper;

    /**
     * 分页查询[PACS-数据]列表
     *
     * @param page  分页参数
     * @param param PacsResult查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsResult> getList(PageParam<PacsResult> page, PacsResult param) {
        return pacsResultMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PacsResult getInfoById(String id) {
        return pacsResultMapper.getInfoById(id);
    }

}

