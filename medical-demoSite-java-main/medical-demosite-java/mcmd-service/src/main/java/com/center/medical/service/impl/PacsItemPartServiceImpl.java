package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PacsItemPartMapper;
import com.center.medical.bean.model.PacsItemPart;
import com.center.medical.service.PacsItemPartService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 项目部位表(PacsItemPart)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:29
 */
@Slf4j
@Service("pacsItemPartService")
@RequiredArgsConstructor
public class PacsItemPartServiceImpl extends ServiceImpl<PacsItemPartMapper, PacsItemPart> implements PacsItemPartService {

    private final PacsItemPartMapper pacsItemPartMapper;

    /**
     * 分页查询[项目部位表]列表
     *
     * @param page  分页参数
     * @param param PacsItemPart查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsItemPart> getList(PageParam<PacsItemPart> page, PacsItemPart param) {
        return pacsItemPartMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PacsItemPart getInfoById(String id) {
        return pacsItemPartMapper.getInfoById(id);
    }

}

