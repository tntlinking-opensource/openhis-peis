package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPacsInspectChargeMapper;
import com.center.medical.datamove.common.bean.model.MdPacsInspectCharge;
import com.center.medical.datamove.common.service.MdPacsInspectChargeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS-项目检查费用(MdPacsInspectCharge)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:33
 */
@Slf4j
@Service("mdPacsInspectChargeService")
@RequiredArgsConstructor
public class MdPacsInspectChargeServiceImpl extends ServiceImpl<MdPacsInspectChargeMapper, MdPacsInspectCharge> implements MdPacsInspectChargeService {

    private final MdPacsInspectChargeMapper mdPacsInspectChargeMapper;

    /**
     * 分页查询[PACS-项目检查费用]列表
     *
     * @param page  分页参数
     * @param param MdPacsInspectCharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPacsInspectCharge> getPage(PageParam<MdPacsInspectCharge> page, MdPacsInspectCharge param) {
        return mdPacsInspectChargeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPacsInspectCharge getInfoById(String id) {
        return mdPacsInspectChargeMapper.getInfoById(id);
    }

}


