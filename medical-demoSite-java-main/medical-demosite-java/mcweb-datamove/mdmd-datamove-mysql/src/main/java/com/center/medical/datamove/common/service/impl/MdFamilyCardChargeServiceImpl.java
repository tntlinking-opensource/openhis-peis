package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFamilyCardChargeMapper;
import com.center.medical.datamove.common.bean.model.MdFamilyCardCharge;
import com.center.medical.datamove.common.service.MdFamilyCardChargeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 家庭卡充值记录(MdFamilyCardCharge)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:16
 */
@Slf4j
@Service("mdFamilyCardChargeService")
@RequiredArgsConstructor
public class MdFamilyCardChargeServiceImpl extends ServiceImpl<MdFamilyCardChargeMapper, MdFamilyCardCharge> implements MdFamilyCardChargeService {

    private final MdFamilyCardChargeMapper mdFamilyCardChargeMapper;

    /**
     * 分页查询[家庭卡充值记录]列表
     *
     * @param page  分页参数
     * @param param MdFamilyCardCharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFamilyCardCharge> getPage(PageParam<MdFamilyCardCharge> page, MdFamilyCardCharge param) {
        return mdFamilyCardChargeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFamilyCardCharge getInfoById(String id) {
        return mdFamilyCardChargeMapper.getInfoById(id);
    }

}


