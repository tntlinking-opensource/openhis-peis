package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeispatientchargeMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientcharge;
import com.center.medical.datamove.common.service.MdPeispatientchargeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者缴费表(MdPeispatientcharge)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:14
 */
@Slf4j
@Service("mdPeispatientchargeService")
@RequiredArgsConstructor
public class MdPeispatientchargeServiceImpl extends ServiceImpl<MdPeispatientchargeMapper, MdPeispatientcharge> implements MdPeispatientchargeService {

    private final MdPeispatientchargeMapper mdPeispatientchargeMapper;

    /**
     * 分页查询[体检者缴费表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientcharge查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatientcharge> getPage(PageParam<MdPeispatientcharge> page, MdPeispatientcharge param) {
        return mdPeispatientchargeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeispatientcharge getInfoById(String id) {
        return mdPeispatientchargeMapper.getInfoById(id);
    }

}


