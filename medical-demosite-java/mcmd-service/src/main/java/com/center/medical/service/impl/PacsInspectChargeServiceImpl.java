package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.PacsInspectChargeMapper;
import com.center.medical.bean.model.PacsInspectCharge;
import com.center.medical.service.PacsInspectChargeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS-项目检查费用(PacsInspectCharge)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:51
 */
@Slf4j
@Service("pacsInspectChargeService")
@RequiredArgsConstructor
public class PacsInspectChargeServiceImpl extends ServiceImpl<PacsInspectChargeMapper, PacsInspectCharge> implements PacsInspectChargeService {

    private final PacsInspectChargeMapper pacsInspectChargeMapper;

    /**
     * 分页查询[PACS-项目检查费用]列表
     *
     * @param page  分页参数
     * @param param PacsInspectCharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsInspectCharge> getList(PageParam<PacsInspectCharge> page, PacsInspectCharge param) {
        return pacsInspectChargeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PacsInspectCharge getInfoById(String id) {
        return pacsInspectChargeMapper.getInfoById(id);
    }

}

