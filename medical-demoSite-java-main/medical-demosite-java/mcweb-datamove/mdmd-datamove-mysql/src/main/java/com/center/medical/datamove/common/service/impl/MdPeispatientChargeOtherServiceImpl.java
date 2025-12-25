package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeispatientChargeOtherMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientChargeOther;
import com.center.medical.datamove.common.service.MdPeispatientChargeOtherService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者其他缴费(MdPeispatientChargeOther)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:11
 */
@Slf4j
@Service("mdPeispatientChargeOtherService")
@RequiredArgsConstructor
public class MdPeispatientChargeOtherServiceImpl extends ServiceImpl<MdPeispatientChargeOtherMapper, MdPeispatientChargeOther> implements MdPeispatientChargeOtherService {

    private final MdPeispatientChargeOtherMapper mdPeispatientChargeOtherMapper;

    /**
     * 分页查询[体检者其他缴费]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientChargeOther查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatientChargeOther> getPage(PageParam<MdPeispatientChargeOther> page, MdPeispatientChargeOther param) {
        return mdPeispatientChargeOtherMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeispatientChargeOther getInfoById(String id) {
        return mdPeispatientChargeOtherMapper.getInfoById(id);
    }

}


