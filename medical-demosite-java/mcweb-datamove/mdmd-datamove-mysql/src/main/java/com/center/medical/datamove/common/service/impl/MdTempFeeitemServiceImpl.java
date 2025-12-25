package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdTempFeeitemMapper;
import com.center.medical.datamove.common.bean.model.MdTempFeeitem;
import com.center.medical.datamove.common.service.MdTempFeeitemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 科室临时加项表(MdTempFeeitem)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:55
 */
@Slf4j
@Service("mdTempFeeitemService")
@RequiredArgsConstructor
public class MdTempFeeitemServiceImpl extends ServiceImpl<MdTempFeeitemMapper, MdTempFeeitem> implements MdTempFeeitemService {

    private final MdTempFeeitemMapper mdTempFeeitemMapper;

    /**
     * 分页查询[科室临时加项表]列表
     *
     * @param page  分页参数
     * @param param MdTempFeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdTempFeeitem> getPage(PageParam<MdTempFeeitem> page, MdTempFeeitem param) {
        return mdTempFeeitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdTempFeeitem getInfoById(String id) {
        return mdTempFeeitemMapper.getInfoById(id);
    }

}


