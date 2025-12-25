package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdInspectCharge;
import com.center.medical.olddata.dao.MdInspectChargeMapper;
import com.center.medical.olddata.service.MdInspectChargeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * JC检查项目收费项目关联表(MdInspectCharge)服务实现类
 *
 * @author ay
 * @since 2024-07-13 13:47:00
 */
@Slf4j
@Service("mdInspectChargeService")
@RequiredArgsConstructor
public class MdInspectChargeServiceImpl extends ServiceImpl<MdInspectChargeMapper, MdInspectCharge> implements MdInspectChargeService {

    private final MdInspectChargeMapper mdInspectChargeMapper;

    /**
     * 分页查询[JC检查项目收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param MdInspectCharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdInspectCharge> getPage(PageParam<MdInspectCharge> page, MdInspectCharge param) {
        return mdInspectChargeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdInspectCharge getInfoById(String id) {
        return mdInspectChargeMapper.getInfoById(id);
    }

}

